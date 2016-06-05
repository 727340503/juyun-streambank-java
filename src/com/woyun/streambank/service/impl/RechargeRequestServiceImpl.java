package com.woyun.streambank.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.woyun.streambank.dao.RechargeRequestMapper;
import com.woyun.streambank.model.RechargeRequest;
import com.woyun.streambank.service.RechargeRequestService;
import com.woyun.streambank.util.common.HttpClientUtil;
import com.woyun.streambank.util.common.JsonUtil;
import com.woyun.streambank.util.common.Log4jUtil;
import com.woyun.streambank.util.common.maiyuan.MaiYuanConfig;
import com.woyun.streambank.util.common.maiyuan.MaiYuanParamUtil;


@Service("rechargeRequestService")
public class RechargeRequestServiceImpl implements RechargeRequestService{

	@Autowired
	private RechargeRequestMapper rechargeRequestMapper;
	
	public List<RechargeRequest> getWaitRechargeRequestList() {
		return rechargeRequestMapper.getWaitRechargeRequestList();
	}

	public boolean sendRechargeRequest(RechargeRequest rechargeRequest) {
		try{
			String paramStr = MaiYuanParamUtil.getRquestRechargeFlowParam(rechargeRequest);
			String httpResult = HttpClientUtil.sendPost(MaiYuanConfig.HTTP_URL, paramStr);
			if(!StringUtils.isEmpty(httpResult)){
				RechargeRequest newRechargeRequest = new RechargeRequest();
				newRechargeRequest.setRechargeRequestId(rechargeRequest.getRechargeRequestId());
				int num = rechargeRequest.getRechargeNum()+1;
				newRechargeRequest.setRechargeNum(num);
				Map<String, Object> resultMap = JsonUtil.strToMap(httpResult);
				if(MaiYuanConfig.SUCCESS_CODE.equals(resultMap.get("Code").toString())){
					newRechargeRequest.setRechargeState(2);
					newRechargeRequest.setRechaegeResult(resultMap.get("Code").toString());
					rechargeRequestMapper.updateByID(newRechargeRequest);
					return true;
				}else{
					//如果发送失败,则判断发送次数,次数达到规定要求的，则不在发送，并将最后一次发送的结果保存到数据库
					if(num >= MaiYuanConfig.RE_SEND_REQUEST_NUM){
						newRechargeRequest.setRechaegeResult(resultMap.get("Message").toString());
						newRechargeRequest.setRechargeState(3);
					}
				}
				rechargeRequestMapper.updateByID(newRechargeRequest);
				Log4jUtil.LOG4J.info(rechargeRequest.getRechargeMobile()+"发送充值流量请求的结果---->"+httpResult);
			}
		}catch (Exception e) {
			Log4jUtil.LOG4J.error(e);
			e.printStackTrace();
		}
		return false;
	}
	
}
