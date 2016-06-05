package com.woyun.streambank.util.common;

import java.util.ArrayList;
import java.util.List;

import com.woyun.streambank.model.PushMsg;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PushMsgUtil {
	public static List<PushMsg> getPushMsgList(String str){
		List<PushMsg> pushList = new ArrayList<PushMsg>();
		JSONArray jsonArray = JSONArray.fromObject(str);
		for(int i=0; i<jsonArray.size(); i++){
			Object obj = jsonArray.get(i);
			JSONObject jsonObj = JSONObject.fromObject(obj);
			PushMsg pushMsg = new PushMsg();
			pushMsg.setMobile(jsonObj.getString("Mobile"));
			pushMsg.setOutTradeNo(jsonObj.getString("OutTradeNo"));
			pushMsg.setReportCode(jsonObj.getString("ReportCode"));
			pushMsg.setReportTime(jsonObj.getString("ReportTime"));
			pushMsg.setStatus(jsonObj.getString("Status"));
			pushMsg.setTaskID(jsonObj.getString("TaskID"));
			pushList.add(pushMsg);
		}
		if(pushList.size() == 0){
			return null;
		}
		return pushList;
	}
}
