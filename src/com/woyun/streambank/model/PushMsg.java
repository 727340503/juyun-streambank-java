package com.woyun.streambank.model;

import java.io.Serializable;


/**
 * 流量推送的充值消息
 * @author 芮浩
 * @date 2016-6-1
 *
 */
public class PushMsg implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7084107914103117094L;
	private String TaskID;
	private String Mobile;
	private String Status;
	private String ReportTime;
	private String ReportCode;
	private String OutTradeNo;
	
	public PushMsg() {
	}
	public PushMsg(String taskID, String mobile, String status,
			String reportTime, String reportCode, String outTradeNo) {
		super();
		TaskID = taskID;
		Mobile = mobile;
		Status = status;
		ReportTime = reportTime;
		ReportCode = reportCode;
		OutTradeNo = outTradeNo;
	}
	public String getTaskID() {
		return TaskID;
	}
	public void setTaskID(String taskID) {
		TaskID = taskID;
	}
	public String getMobile() {
		return Mobile;
	}
	public void setMobile(String mobile) {
		Mobile = mobile;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getReportTime() {
		return ReportTime;
	}
	public void setReportTime(String reportTime) {
		ReportTime = reportTime;
	}
	public String getReportCode() {
		return ReportCode;
	}
	public void setReportCode(String reportCode) {
		ReportCode = reportCode;
	}
	public String getOutTradeNo() {
		return OutTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		OutTradeNo = outTradeNo;
	}
	@Override
	public String toString() {
		return "TaskID=" + TaskID + "&Mobile=" + Mobile + "&Status="
				+ Status + "&ReportTime=" + ReportTime + "&ReportCode="
				+ ReportCode + "&OutTradeNo=" + OutTradeNo;
	}
	
	
}
