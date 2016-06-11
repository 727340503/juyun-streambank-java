package com.woyun.streambank.model;

import java.io.Serializable;

/**
 * 号码区段实体类
 * @author 芮浩
 * @date 2016-6-7
 *
 */
public class NumberSection implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7496025641030450838L;

	private String number;
	private String city;
	private String province;
	private Integer operator;
	public NumberSection(String number, String city, String province,
			Integer operator) {
		this.number = number;
		this.city = city;
		this.province = province;
		this.operator = operator;
	}
	public NumberSection() {
		
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
	public Integer getOperator() {
		return operator;
	}
	public void setOperator(Integer operator) {
		this.operator = operator;
	}
	@Override
	public String toString() {
		return "NumberSection [number=" + number + ", city=" + city
				+ ", province=" + province + ", operator=" + operator + "]";
	}
	
	
}
