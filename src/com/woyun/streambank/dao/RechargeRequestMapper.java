package com.woyun.streambank.dao;

import java.util.List;

import com.woyun.streambank.model.RechargeRequest;




@MyBatisRepository
public interface RechargeRequestMapper {
	/*
	 * 添加充值请求
	 * */
	public int addRechargeRequest(RechargeRequest  rechargeRequest);
	/**
	 * 获取待发送请求列表
	 * @author 芮浩
	 * @date 2016-6-1
	 * 
	 * @return
	 */
	public List<RechargeRequest> getWaitRechargeRequestList();
	/**
	 * 根据id更新
	 * @author 芮浩
	 * @date 2016-6-1
	 * 
	 * @param newRechargeRequest
	 * @return 
	 */
	public int updateByID(RechargeRequest newRechargeRequest);
}

