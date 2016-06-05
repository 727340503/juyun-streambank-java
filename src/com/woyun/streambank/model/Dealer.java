package com.woyun.streambank.model;

import java.io.Serializable;

public class Dealer  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5775573270914795032L;

	private Integer dealerId;//代理商id
	private String dealerName;//代理商名称
	private String dealerNo;//代理商编号
	private Double retailDiscount;//代理折扣
	private Double settlementDiscount;//销售折扣
	public Integer getDealerId() {
		return dealerId;
	}
	public void setDealerId(Integer dealerId) {
		this.dealerId = dealerId;
	}
	public String getDealerName() {
		return dealerName;
	}
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	public String getDealerNo() {
		return dealerNo;
	}
	public void setDealerNo(String dealerNo) {
		this.dealerNo = dealerNo;
	}
	public Double getRetailDiscount() {
		return retailDiscount;
	}
	public void setRetailDiscount(Double retailDiscount) {
		this.retailDiscount = retailDiscount;
	}
	public Double getSettlementDiscount() {
		return settlementDiscount;
	}
	public void setSettlementDiscount(Double settlementDiscount) {
		this.settlementDiscount = settlementDiscount;
	}
}
