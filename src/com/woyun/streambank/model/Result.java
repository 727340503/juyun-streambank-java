package com.woyun.streambank.model;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Result implements Serializable{

	private static final long serialVersionUID = -4027778560853812030L;

	/**
	 * 状态，0成功，1失败。
	 */
	private int status;

	/**
	 * 提示信息
	 */
	private String message;

	/**
	 * 相关数据
	 */
	private Object data;
	
	/**
	 * 总页数
	 */
	private Integer allPage;

	public Result() {
		super();
	}
	
	public Result(int status, String message, Object data, Integer allPage) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
		this.allPage = allPage;
	}

	public Result(Object data) {
		this.data = data;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Integer getAllPage() {
		return allPage;
	}

	public void setAllPage(Integer allPage) {
		this.allPage = allPage;
	}

}
