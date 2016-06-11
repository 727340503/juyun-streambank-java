package com.woyun.streambank.service.impl;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.woyun.streambank.dao.OrderMapper;
import com.woyun.streambank.model.Dealer;
import com.woyun.streambank.model.FlowPackage;
import com.woyun.streambank.model.Order;
import com.woyun.streambank.model.PushMsg;
import com.woyun.streambank.model.Result;
import com.woyun.streambank.service.AlipayOrderService;
import com.woyun.streambank.service.DealerService;
import com.woyun.streambank.service.OrderService;
import com.woyun.streambank.service.PackageService;
import com.woyun.streambank.service.WeiChatOrderService;
import com.woyun.streambank.util.common.Base64Util;
import com.woyun.streambank.util.common.DESUtils;
import com.woyun.streambank.util.common.JsonUtil;
import com.woyun.streambank.util.common.Log4jUtil;
import com.woyun.streambank.util.common.maiyuan.MaiYuanConfig;
import com.woyun.streambank.util.common.maiyuan.StreamBankConfig;

@Service("orderService")
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private PackageService packageService;
	@Autowired
	private DealerService dealerService;
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private AlipayOrderService alipayOrderService;
	@Autowired
	private WeiChatOrderService weiChatOrderService;
	public String getPayToken(HttpServletRequest request) throws Exception {
		try{
			HttpSession session = request.getSession();
			String paramStr = request.getParameter("data");
			if(StringUtils.isEmpty(paramStr)){
				session.setAttribute("errorMsg", "参数不完整");
				return "/web/error";
			}else{
				Map<String,Object> inMap = JsonUtil.strToMap(Base64Util.getFromBase64(paramStr));
				if(!inMap.containsKey("phone") || StringUtils.isEmpty(inMap.get("phone"))){
					session.setAttribute("errorMsg", "非法访问");
					return "/web/error";
				}
				if(!inMap.containsKey("flow") || StringUtils.isEmpty(inMap.get("flow"))){
					session.setAttribute("errorMsg", "非法访问");
					return "/web/error";
				}
				String phone = inMap.get("phone").toString();
				//查询套餐
				FlowPackage flowPackage = packageService.getPackageByPackageId(phone, inMap.get("flow").toString());
				if(flowPackage == null){
					session.setAttribute("errorMsg", "套餐不存在,请刷新后重试");
					return "/web/error";
				}
				//查询折扣
				Dealer dealer = dealerService.findDealerByName(StreamBankConfig.APP_DEALER_NAME);
				StringBuilder sb = new StringBuilder();
				sb.append("{\"phone\":\"");
				sb.append(phone);
				sb.append("\",\"packageId\":\"");
				sb.append(flowPackage.getPackageId());
				sb.append("\"}");
				String token = DESUtils.encrypt(sb.toString());
				session.setAttribute("token", token);
				session.setAttribute("packageName", flowPackage.getPackageName());
				session.setAttribute("price", flowPackage.getPrice()*dealer.getSettlementDiscount());
				session.setAttribute("phone", phone);
				return "/web/payInfo";
			}
		}catch (Exception e) {
			throw e;
		}
	}

	public String createOrder(HttpServletRequest request, String payType,HttpServletResponse response) throws Exception {
		try{
			HttpSession session = request.getSession();
			String token = session.getAttribute("token") == null?null:session.getAttribute("token").toString();
			session.removeAttribute("token");//订单创建成功后移除token,防止多次请求
			if(StringUtils.isEmpty(token)){
				session.setAttribute("errorMsg", "请求次数过多,请重试");
				return "/web/error";
			}
			String jsonStr = DESUtils.decrypt(token);
			Map<String, Object> inMap = JsonUtil.strToMap(jsonStr);
			String phone = inMap.get("phone").toString();
			//查询套餐
			FlowPackage flowPackage = packageService.getPackageByPackageId(phone, inMap.get("packageId").toString());
			if(flowPackage == null){
				session.setAttribute("errorMsg", "套餐不存在,请刷新后重试");
				return "/web/error";
			}
			//查询折扣
			Dealer dealer = dealerService.findDealerByName(StreamBankConfig.APP_DEALER_NAME);
			String outTradeNo = UUID.randomUUID().toString().replaceAll("-", "");
			Order order = getOrder(phone,flowPackage,dealer,Integer.parseInt(payType),1,outTradeNo);
			int orderId = orderMapper.addOrder(order);//创建订单
			if(orderId > 0){
				if("1".equals(payType)){//判断是否为支付宝充值,支付宝充值创建支付宝连接
					//根据订单结果生成相应的支付链接
					order.setTotal(0.01);
					order.setDiscountPrice(0.01);
					String html = alipayOrderService.getAlipayUrl(order);
					// 返回信息
					response.setContentType("text/html;charset=utf-8");
					PrintWriter out = response.getWriter();
					out.print(html);
				}else{//创建微信支付连接
					//请求统一下单api获取prepayid号
					String dispatcherUrl = weiChatOrderService.getWeiChatOrderPayUrl(order);
					if(dispatcherUrl != null){
						response.sendRedirect(dispatcherUrl);//重定向
					}else{
						session.setAttribute("errorMsg", "调用微信支付,请重试");
						return "/web/error";
					}
				}
			}else{
				session.setAttribute("errorMsg", "创建订单失败,请稍后重试");
				return "/web/error";
			}
		}catch (Exception e) {
			throw e;
		}
		return null;
	}

	private Order getOrder(String phone,FlowPackage flowPackage,Dealer dealer,int payType,int num,String outTradeNo){
		Timestamp now = new Timestamp(System.currentTimeMillis());
		double discount = dealer.getSettlementDiscount();//获取当前销售折扣价格
		double price = flowPackage.getPrice();
		Order order = new Order();
		order.setPackageId(flowPackage.getPackageId());//套餐id
		order.setPackageName(flowPackage.getPackageName());//套餐名称
		order.setDealer(dealer);//代理商
		order.setPayTime(now);//支付时间
		order.setCreateTime(now);//创建时间
		if(payType == 1){
			order.setPayType(1);//支付方式支付宝
		}else{
			order.setPayType(2);//支付方式微信
		}
		order.setPhone(phone);//充值号码
		order.setPrice(price);//原始单价
		order.setTotal(price*discount*num);//总价:单价*折扣*数量
		order.setNum(num);//数量
		order.setOutTradeNo(outTradeNo);//商户交易号
		order.setDiscount(discount);//本次充值用户享受的折扣
		order.setDiscountPrice(price*dealer.getSettlementDiscount()*num);//本次充值实际需要支付的价格(折后价格)
		return order;
	}

	public String rechargeFlowNotify(List<PushMsg> pushMsgs) throws Exception {
		try{
			for(PushMsg pushMsg : pushMsgs){
				System.out.println(pushMsg);
				//根据订单号查询订单
				Order order = orderMapper.findOrderById(Integer.parseInt(pushMsg.getOutTradeNo()));
				if(order != null){//判断订单不为空
					Order updOrder = new Order();
					updOrder.setOrderId(order.getOrderId());
					//判断流量充值状态
					if("4".equals(pushMsg.getStatus())){//判断状态为4则充值成功
						updOrder.setPhone(order.getPhone());
						updOrder.setRechargeState(MaiYuanConfig.RECHARGE_SUCCESS_OK);
						updOrder.setRechargeResult(pushMsg.getReportCode());
					}else{//不为4则充值失败
						updOrder.setRechargeState(MaiYuanConfig.RECHARGE_SUCCESS_FAIL);
						updOrder.setRechargeResult(pushMsg.getReportCode());
					}
					//修改订单状态
					orderMapper.updOrderById(updOrder);
				}else{
					Log4jUtil.LOG4J.info("流量充值回调查询订单无结果的请求------>"+pushMsg.toString());
				}
			}
			return MaiYuanConfig.NOTIFY_SUCCESS_MSG;
		}catch (Exception e) {
			throw e;
		}
	}

	public boolean updOrderPayState(Order order) throws Exception {
		Order updOrder = new Order();
		updOrder.setTradeNo(order.getTradeNo());
		updOrder.setOrderId(order.getOrderId());
		updOrder.setPayState(2);//交易状态2为已支付
		updOrder.setPhone(order.getPhone());
		int result = orderMapper.updOrderById(updOrder);
		if(result != 0){
			return true;
		}
		return false;
	}

	public Result createAppOrder(Map<String, Object> paramMap) throws Exception {
		Result result = new Result();
		try{
			String phone = paramMap.get("phone").toString();
			String payType = paramMap.get("pay").toString();
			int num = Integer.parseInt(paramMap.get("num").toString());
			//查询套餐
			FlowPackage flowPackage = packageService.getPackageByPackageId(phone, paramMap.get("package").toString());
			if(flowPackage == null){
				result.setStatus(4);
				result.setMessage("选择的套餐不正确");
				return result;
			}
			//查询折扣
			Dealer dealer = dealerService.findDealerByName(paramMap.get("dealer").toString());
			String outTradeNo = UUID.randomUUID().toString().replaceAll("-", "");
			Order order = getOrder(phone,flowPackage,dealer,Integer.parseInt(payType),num,outTradeNo);
			int orderId = orderMapper.addOrder(order);//创建订单
			if(orderId > 0){
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("price", order.getPrice());
				resultMap.put("outTradeNo",outTradeNo);
				resultMap.put("total", order.getTotal());
				resultMap.put("package", order.getPackageId());
				resultMap.put("packageName","流量充值-"+order.getPackageName());
				if("2".equals(payType)){//判断是否为微信支付
					//获取预支付id
					String prepareId = "123";
					resultMap.put("prepareId", prepareId);
				}
				String resParam = DESUtils.encrypt(Base64Util.getBase64(JsonUtil.mapToJsonstr(resultMap)));
				result.setStatus(0);
				result.setMessage("创建订单成功");
				result.setData(resParam);
			}else{
				result.setStatus(5);
				result.setMessage("订单创建失败，请重试");
			}
		}catch (Exception e) {
			throw e;
		}
		return result;
	}
	
}
