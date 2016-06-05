package com.woyun.streambank.service;

import java.util.List;

import com.woyun.streambank.model.RechargeRequest;

/**
 * 充值请求的service
 * @author 芮浩
 * @date 2016-6-1
 *
 */
public interface RechargeRequestService {
	
	/**
	 * 获取等待发送的请求列表
	 * @author 芮浩
	 * @date 2016-6-1
	 * 
	 * @return
	 */
	public List<RechargeRequest> getWaitRechargeRequestList();
	
	public boolean sendRechargeRequest(RechargeRequest rechargeRequest);
}	
