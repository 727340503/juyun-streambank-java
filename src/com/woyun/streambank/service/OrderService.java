package com.woyun.streambank.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.woyun.streambank.model.Order;
import com.woyun.streambank.model.PushMsg;
import com.woyun.streambank.model.Result;

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
	public String createOrder(HttpServletRequest request,String payType,HttpServletResponse response) throws Exception;
	/**
	 * 充值推送回调
	 * @author 芮浩
	 * @date 2016-6-1
	 * 
	 * @param pushMsgs
	 * @return
	 */
	public String rechargeFlowNotify(List<PushMsg> pushMsgs) throws Exception ;
	/**
	 * 更新订单支付状态
	 * @author 芮浩
	 * @date 2016-6-6
	 * 
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public boolean updOrderPayState(Order order) throws Exception;
	/**
	 * 创建app订单
	 * @author 芮浩
	 * @date 2016-6-8
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception 
	 */
	public Result createAppOrder(Map<String, Object> paramMap) throws Exception;
}
