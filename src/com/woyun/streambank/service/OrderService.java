package com.woyun.streambank.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.woyun.streambank.model.PushMsg;

public interface OrderService {
	
	/***
	 * 获取创建订单的token
	 * @author 芮浩
	 * @date 2016-5-31
	 * 
	 */
	public String getPayToken(HttpServletRequest request) throws Exception;
	/**
	 * 创建订单
	 * @author 芮浩
	 * @date 2016-5-31
	 * 
	 */
	public String createOrder(HttpSession session,String payType,HttpServletResponse response) throws Exception;
	/**
	 * 充值推送回调
	 * @author 芮浩
	 * @date 2016-6-1
	 * 
	 * @param pushMsgs
	 * @return
	 */
	public String rechargeFlowNotify(List<PushMsg> pushMsgs) throws Exception ;
}
