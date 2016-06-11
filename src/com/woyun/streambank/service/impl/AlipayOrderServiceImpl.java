package com.woyun.streambank.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woyun.streambank.dao.OrderMapper;
import com.woyun.streambank.dao.RechargeRequestMapper;
import com.woyun.streambank.model.Order;
import com.woyun.streambank.model.RechargeRequest;
import com.woyun.streambank.service.AlipayOrderService;
import com.woyun.streambank.service.OrderService;
import com.woyun.streambank.util.alipay.AlipayConfig;
import com.woyun.streambank.util.alipay.AlipaySubmit;


@Service("alipayOrderService")
public class AlipayOrderServiceImpl implements AlipayOrderService{

	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private RechargeRequestMapper rechargeRequestMapper;
	@Autowired
	private OrderService orderService;
	public String getAlipayUrl(Order order) throws Exception {
		try{
			String subject = "流量充值-"+order.getPackageName();
			Map<String, String> alipayMap = new HashMap<String, String>();
			alipayMap.put("service", AlipayConfig.ALIPAY_SERVICE);
			alipayMap.put("partner", AlipayConfig.PARTNER);
			alipayMap.put("notify_url", AlipayConfig.NOTIFY_URL);
			alipayMap.put("return_url", AlipayConfig.RETURN_URL);
			alipayMap.put("out_trade_no", order.getOutTradeNo());
			alipayMap.put("_input_charset",AlipayConfig.INPUT_CHARSET);
			alipayMap.put("subject",subject);
			alipayMap.put("payment_type", "1");
			alipayMap.put("total_fee", order.getTotal()+"");
			alipayMap.put("seller_email", AlipayConfig.SELLER_EMAIL);
			alipayMap.put("price", order.getDiscountPrice()+"");
			alipayMap.put("quantity", "1");
			System.out.println(alipayMap);
			return AlipaySubmit.buildRequest(alipayMap, AlipayConfig.COMMIT_TYPE, "submit");
		}catch (Exception e) {
			throw e;
		}
	}

	public String alipayNotify(Map<String, String> alipayMap) throws Exception {
		try{
			//根据商品订单号和支付状态查询订单
			String outTradeNo = alipayMap.get("out_trade_no");
			Order order = orderMapper.findOrderByOutTradeNo(new Order(outTradeNo,1));
			if(order != null && order.getOrderId() != null){
//				double totalFeel = Double.parseDouble(alipayMap.get("total_fee"));
//				if(totalFeel != order.getTotal()){
//					return AlipayConfig.ALIPAY_NOTIFY_ERROR;
//				}
				//保存充值请求到充值表中
				RechargeRequest rechargeRequest = new RechargeRequest();
				rechargeRequest.setOrderId(order.getOrderId());
				rechargeRequest.setPackageId(order.getPackageId());
				rechargeRequest.setPackageName(order.getPackageName());
				rechargeRequest.setRechargeMobile(order.getPhone());
				int addCode = rechargeRequestMapper.addRechargeRequest(rechargeRequest);
				if(addCode != 0){
					order.setTradeNo(alipayMap.get("trade_no"));//支付宝交易号
					boolean flag = orderService.updOrderPayState(order);
					//完成回调
					if(flag){
						return AlipayConfig.ALIPAY_NOTIFY_SUCCESS;
					}
				}
				return AlipayConfig.ALIPAY_NOTIFY_ERROR;
			}else{
				return AlipayConfig.ALIPAY_NOTIFY_ERROR;
			}
		}catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 支付宝同步跳转
	 * @author 芮浩
	 * @date 2016-6-3
	 * 
	 * @return
	 */
	public String alipayReturn(Map<String,String> inMap,HttpSession session) throws Exception{
		Thread.sleep(5000);//线程释放5秒,等待异步回调优先完成
		Order order = new Order();
		order.setOutTradeNo(inMap.get("out_trade_no"));
		order.setPayType(1);
		order = orderMapper.findOrderByOutTradeNo(order);
		if(order != null){
			if(order.getPayState() == 2){
				return "/web/success";
			}else{
				session.setAttribute("errorMsg", "订单充值异常,请联系客服");
			}
		}else{
			session.setAttribute("errorMsg", "发生了未知错误,请联系客服");
		}
		return "/web/error";
	}
	
	/**
	 * 更新订单支付状态
	 * @author 芮浩
	 * @date 2016-6-1
	 * 
	 * @param order
	 * @return
	 */
//	private boolean updOrderPayState(Order order){
//		Order updOrder = new Order();
//		updOrder.setTradeNo(order.getTradeNo());
//		updOrder.setOrderId(order.getOrderId());
//		updOrder.setPayState(2);//支付宝交易状态2为已支付
//		updOrder.setPhone(order.getPhone());
//		int result = orderMapper.updOrderById(updOrder);
//		if(result != 0){
//			return true;
//		}
//		return false;
//	}
	
}
