package com.woyun.streambank.service.impl;

import java.io.PrintWriter;
import java.sql.Timestamp;
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
import com.woyun.streambank.service.AlipayOrderService;
import com.woyun.streambank.service.DealerService;
import com.woyun.streambank.service.OrderService;
import com.woyun.streambank.service.PackageService;
import com.woyun.streambank.util.common.Base64Util;
import com.woyun.streambank.util.common.DESUtils;
import com.woyun.streambank.util.common.DateUtil;
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
				Dealer dealer = dealerService.findDealerByNo(StreamBankConfig.APP_DEALER_NO);
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

	public String createOrder(HttpSession session, String payType,HttpServletResponse response) throws Exception {
		try{
			String token = session.getAttribute("token") == null?null:session.getAttribute("token").toString();
			session.removeAttribute("token");//订单创建成功后移除token,防止多次请求
			if(StringUtils.isEmpty(token)){
				session.setAttribute("errorMsg", "请求过于频繁,请重试");
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
			Dealer dealer = dealerService.findDealerByNo(StreamBankConfig.APP_DEALER_NO);
			String outTradeNo = "alipay" + DateUtil.getOrderTime() +UUID.randomUUID().toString().replaceAll("-", "")+ phone.trim();
			Order order = getOrder(phone,flowPackage,dealer,Integer.parseInt(payType),1,outTradeNo);
			int orderId = orderMapper.addOrder(order);//创建订单
			if(orderId > 0){
				//根据订单结果生成相应的支付链接
				order.setTotal(0.01);
				order.setDiscountPrice(0.01);
				String html = alipayOrderService.getAlipayUrl(order);
				// 返回信息
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.print(html);
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
		order.setPayType(payType);//支付方式
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
	
}
