package com.woyun.streambank.dao;

import com.woyun.streambank.model.Order;




@MyBatisRepository
public interface OrderMapper {
	/*
	 * 添加订单
	 * */
	public int addOrder(Order order);
	/*
	 * 根据商品交易号和支付状态查询订单
	 */
	public Order findOrderByOutTradeNo(Order order);
	
	/*
	 * 更新支付状态
	 */
	public int updOrderById(Order updOrder);
	/*
	 * 根据订单id查询订单
	 */
	public Order findOrderById(int orderId);
}

