package com.woyun.streambank.service;

import java.util.Map;

import com.woyun.streambank.model.Order;

public interface WeiChatOrderService {

	
	/**
	 * 获取统一下单返回的预支付id
	 * @author 芮浩
	 * @date 2016-6-5
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String getWeiChatOrderPayUrl(Order order) throws Exception;
	/**
	 * 微信异步回调
	 */
	public String weiChatPayNotify(Map<String, Object> weiChatMap) throws Exception;
	
}
