package com.woyun.streambank.model;

import java.io.Serializable;

public class FlowPackage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5444954764636897623L;
	private String packageName;
	private String packageId;
	private Double price;
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "{\"packageName\":\""+packageName+"\",\"packageId\":\""+packageId+"\",\"price\":\""+price+"\"}";
	}
	
	
}
