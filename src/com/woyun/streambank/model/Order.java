package com.woyun.streambank.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Order implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6557892269056409950L;
	private Integer orderId;//订单id
	private String packageId;//套餐id
	private String packageName;//套餐名称
	private Timestamp payTime;//支付时间
	private Timestamp createTime;//创建时间
	private Double price;//单价
	private Double total;//总价
	private Integer num;//数量
	private String phone;//手机号
	private Integer payState;//支付状态   1为未支付  2为已支付
	private Integer payType;//支付方式  1 为支付宝  2 为微信
	private Integer rechargeState;//流量充值状态  1未充值   2充值成功  3充值失败
	private String tradeNo;//交易号
	private String outTradeNo;//商户订单号
	private Dealer dealer;//代理商
	private Double discountPrice;//折扣价格
	private Double discount;//当前折扣
	private String rechargeResult;//充值流量结果
	public Order() {
	}
	
	public Order(String outTradeNo,Integer payState) {
		this.payState = payState;
		this.outTradeNo = outTradeNo;
	}

	public Order(Integer orderId, String packageId, String packageName,
			Timestamp payTime, Timestamp createTime, Double price,
			Double total, Integer num, String phone, Integer payState,
			Integer payType, Integer rechargeState, String tradeNo,
			String outTradeNo, Dealer dealer, Double discountPrice,
			Double discount, String rechargeResult) {
		super();
		this.orderId = orderId;
		this.packageId = packageId;
		this.packageName = packageName;
		this.payTime = payTime;
		this.createTime = createTime;
		this.price = price;
		this.total = total;
		this.num = num;
		this.phone = phone;
		this.payState = payState;
		this.payType = payType;
		this.rechargeState = rechargeState;
		this.tradeNo = tradeNo;
		this.outTradeNo = outTradeNo;
		this.dealer = dealer;
		this.discountPrice = discountPrice;
		this.discount = discount;
		this.rechargeResult = rechargeResult;
	}

	public String getRechargeResult() {
		return rechargeResult;
	}

	public void setRechargeResult(String rechargeResult) {
		this.rechargeResult = rechargeResult;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
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

	public Timestamp getPayTime() {
		return payTime;
	}

	public void setPayTime(Timestamp payTime) {
		this.payTime = payTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getPayState() {
		return payState;
	}

	public void setPayState(Integer payState) {
		this.payState = payState;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Integer getRechargeState() {
		return rechargeState;
	}

	public void setRechargeState(Integer rechargeState) {
		this.rechargeState = rechargeState;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public Dealer getDealer() {
		return dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	public Double getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(Double discountPrice) {
		this.discountPrice = discountPrice;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	
}
