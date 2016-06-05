package com.woyun.streambank.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.woyun.streambank.model.Order;

public interface AlipayOrderService {
	/**
	 * 生成支付宝连接
	 */
	public String getAlipayUrl(Order order) throws Exception;

	/**
	 * 支付宝异步回调
	 */
	public String alipayNotify(Map<String, String> alipayMap) throws Exception;
	/**
	 * 支付宝同步跳转回调
	 * @author 芮浩
	 * @date 2016-6-3
	 * 
	 * @param inMap
	 * @return
	 * @throws Exception
	 */
	public String alipayReturn(Map<String,String> inMap,HttpSession session) throws Exception;
}
