package com.woyun.streambank.timertask;

import java.util.List;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.woyun.streambank.model.RechargeRequest;
import com.woyun.streambank.service.RechargeRequestService;
import com.woyun.streambank.util.common.Log4jUtil;

@Component("sendRechargeFlowRequestTask")
public class SendRechargeFlowRequestTask  extends TimerTask{
	
	@Autowired
	private RechargeRequestService rechargeRequestService;

	@Override
	public void run() {
		//获取需要发送充值流量请求的列表
		List<RechargeRequest> rechargeRequests = rechargeRequestService.getWaitRechargeRequestList();
		if(rechargeRequests.size() != 0){//判断充值列表不为0,遍历列表,发送充值流量的请求
			for(RechargeRequest rechargeRequest : rechargeRequests){
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				System.out.println(rechargeRequest.toString());
				boolean flag = rechargeRequestService.sendRechargeRequest(rechargeRequest);
				Log4jUtil.LOG4J.info(rechargeRequest.getRechargeMobile()+"发送充值请求的结果---->"+flag);
			}
		}
	}
	
}
