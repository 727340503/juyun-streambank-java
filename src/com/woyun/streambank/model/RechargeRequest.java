package com.woyun.streambank.model;

import java.io.Serializable;

public class RechargeRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3860632356072174908L;
	
	private Integer rechargeRequestId;//充值请求id
	private String rechargeMobile;//充值号码
	private String packageId;//套餐id
	private String packageName;//套餐名称
	private Integer orderId;//订单id
	private Integer rechargeNum;//充值次数
	private Integer rechargeState;//充值状态   1 未发送请求  2已发送请求   3请求返回结果失败   4请求返回结果成功
	private String rechaegeResult;//充值结果  返回失败的原因
	
	public RechargeRequest() {
	}

	public RechargeRequest(Integer rechargeRequestId, String rechargeMobile,
			String packageId, String packageName, Integer orderId,
			Integer rechargeNum, Integer rechargeState, String rechaegeResult) {
		super();
		this.rechargeRequestId = rechargeRequestId;
		this.rechargeMobile = rechargeMobile;
		this.packageId = packageId;
		this.packageName = packageName;
		this.orderId = orderId;
		this.rechargeNum = rechargeNum;
		this.rechargeState = rechargeState;
		this.rechaegeResult = rechaegeResult;
	}
	public Integer getRechargeRequestId() {
		return rechargeRequestId;
	}

	public void setRechargeRequestId(Integer rechargeRequestId) {
		this.rechargeRequestId = rechargeRequestId;
	}

	public String getRechargeMobile() {
		return rechargeMobile;
	}

	public void setRechargeMobile(String rechargeMobile) {
		this.rechargeMobile = rechargeMobile;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getRechargeNum() {
		return rechargeNum;
	}

	public void setRechargeNum(Integer rechargeNum) {
		this.rechargeNum = rechargeNum;
	}

	public Integer getRechargeState() {
		return rechargeState;
	}

	public void setRechargeState(Integer rechargeState) {
		this.rechargeState = rechargeState;
	}

	public String getRechaegeResult() {
		return rechaegeResult;
	}

	public void setRechaegeResult(String rechaegeResult) {
		this.rechaegeResult = rechaegeResult;
	}

	@Override
	public String toString() {
		return "RechargeRequest [rechargeRequestId=" + rechargeRequestId
				+ ", rechargeMobile=" + rechargeMobile + ", packageId="
				+ packageId + ", packageName=" + packageName + ", orderId="
				+ orderId + ", rechargeNum=" + rechargeNum + ", rechargeState="
				+ rechargeState + ", rechaegeResult=" + rechaegeResult + "]";
	}


	
}
