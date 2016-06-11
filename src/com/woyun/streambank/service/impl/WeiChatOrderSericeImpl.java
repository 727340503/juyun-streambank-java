package com.woyun.streambank.service.impl;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.woyun.streambank.dao.OrderMapper;
import com.woyun.streambank.dao.RechargeRequestMapper;
import com.woyun.streambank.model.Order;
import com.woyun.streambank.model.RechargeRequest;
import com.woyun.streambank.service.OrderService;
import com.woyun.streambank.service.WeiChatOrderService;
import com.woyun.streambank.util.alipay.AlipayConfig;
import com.woyun.streambank.util.common.HttpClientUtil;
import com.woyun.streambank.util.common.Log4jUtil;
import com.woyun.streambank.util.weichatpay.RandomStringGenerator;
import com.woyun.streambank.util.weichatpay.WeiChatPayConfig;
import com.woyun.streambank.util.weichatpay.WeiChatSignature;
import com.woyun.streambank.util.weichatpay.XMLParser;


@Service("weiChatOrderService")
public class WeiChatOrderSericeImpl implements WeiChatOrderService{
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private RechargeRequestMapper rechargeRequestMapper;
	@Autowired
	private OrderService orderService;
	public String getWeiChatOrderPayUrl(Order order) throws Exception {
		try{
			System.out.println(getUnifiedorderPayParamMap(order));
			String param = getUnifiedorderPayParamMap(order);
			String responseStr = HttpClientUtil.sendPost(WeiChatPayConfig.PAY_UNIFIEDORDER_API, param);
			if(!StringUtils.isEmpty(responseStr)){
				Map<String, Object> resultMap = XMLParser.getMapFromXML(responseStr);
				if(WeiChatPayConfig.PAY_RESULT_SUCCESS.equals(resultMap.get("return_code").toString())){
					if(WeiChatSignature.checkIsSignValidFromResponseString(responseStr)){
						if(WeiChatPayConfig.PAY_RESULT_SUCCESS.equals(resultMap.get("result_code"))){
							String requestStr = URLEncoder.encode(getWapPayParamString(resultMap),"utf-8");
							return WeiChatPayConfig.PAY_WAP_API + "?" + requestStr;
						}
					}else{
						Log4jUtil.LOG4J.info(order.getPhone()+"--获取微信预支付id时返回结果签名校验失败---"+responseStr);
					}
				}else{
					Log4jUtil.LOG4J.info(order.getPhone()+"--获取微信预支付id是返回结果FAIL--"+responseStr);
				}
			}
		}catch (Exception e) {
			throw e;
		}
		return null;
	}

	/**
	 * 根据订单获取请求微信统一下单api参数组
	 * @author 芮浩
	 * @date 2016-6-5
	 * 
	 * @param order
	 * @return
	 * @throws Exception 
	 */
	private String getUnifiedorderPayParamMap(Order order) throws Exception{
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("appid", WeiChatPayConfig.APPID);
		paramMap.put("mch_id", WeiChatPayConfig.MCHID);
		paramMap.put("nonce_str", RandomStringGenerator.getRandomStringByLength(32));
		paramMap.put("body","流量充值-"+order.getPackageName());
		paramMap.put("out_trade_no", order.getOutTradeNo());
		paramMap.put("total_fee", order.getTotal()+"");
		paramMap.put("spbill_create_ip", WeiChatPayConfig.IP);
		paramMap.put("notify_url", WeiChatPayConfig.NOTIFY_URL);
		paramMap.put("trade_type", WeiChatPayConfig.WEICHAT_PAY_TYPE);
		String param = WeiChatSignature.getRequestXmlInfoByMap(paramMap);
		return param;
	}
	
	private String getWapPayParamString(Map<String, Object> inMap) throws Exception{
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("appid", WeiChatPayConfig.APPID);
		paramMap.put("noncestr", RandomStringGenerator.getRandomStringByLength(32));
		paramMap.put("package", "WAP");
		paramMap.put("prepayid", inMap.get("prepay_id").toString());
		paramMap.put("timestamp", new Date().getTime()/1000+"");
		String sign = WeiChatSignature.getRequestXmlInfoByMap(paramMap);
		paramMap.put("sign", sign);
		String param = WeiChatSignature.getRequestXmlInfoByMap(paramMap);
		return param;
	}

	public String weiChatPayNotify(Map<String, Object> weiChatMap) throws Exception {
		try{
			//根据商品订单号和支付状态查询订单
			String outTradeNo = weiChatMap.get("out_trade_no").toString();
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
				if(addCode != 0){//判断添加充值订单请求结果状态
					order.setTradeNo(weiChatMap.get("transaction_id").toString());//微信支付订单好号
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
}
