package com.woyun.streambank.util.common.maiyuan;

import java.util.HashMap;
import java.util.Map;

public class MaiYuanConfig {
	//公共的参数
	public static final String HTTP_URL = "http://119.29.222.72:8080/api.aspx";
	public static final int RE_SEND_REQUEST_NUM = 3;//重复发送充值请求的次数
//	public static final String HTTP_URL = "http://127.0.0.1:8080/streambank/package/getProduct.json";
	public static final String KEY = "6f1935e580f3484e992f8c27a189afa7";
	public static final String GET_PACKAGE_ACTION = "getPackage";
	public static final String CHARGE_FLOW_ACTION = "charge";
	public static final String VERSION = "1.1";
	public static final String ACCOUNT = "沃云珠海";
	public static final String PACKAGE_YD = "1";
	public static final String PACKAGE_LT = "2";
	public static final String PACKAGE_DX = "3";
	
	//加密参数
	public static final String PARAM_KEY = "key";
	//请求参数名称
	public static final String PARAM_ACTION = "action";
	public static final String PARAM_VERSION = "v";
	public static final String PARAM_ACCOUNT = "account";
	public static final String PARAM_TYPE = "type";
	public static final String PARAM_SIGN = "sign";
	public static final String PARAM_MOBILE = "mobile";
	public static final String PARAM_PACKAGE = "package";
	public static final String PARAM_OUT_TRADE_NO = "OutTradeNo";
	public static final String PARAM_RANGE = "Range";
	
	
	//相应参数
	public static final String RESULT_CODE = "Code";
	public static final String RESULT_MESSAGE = "Message";
	public static final String RESULT_PACKAGES = "Packages";
	public static final String RESULT_PACKAGE = "Package";
	public static final String RESULT_NAME = "Name";
	public static final String RESULT_PRICE = "Price";
	
	//error错误码
	public static final String SUCCESS_CODE = "0";
	public static final String NOTIFY_SUCCESS_MSG = "ok";
	public static final int RECHARGE_SUCCESS_OK = 2;
	public static final int RECHARGE_SUCCESS_FAIL = 3;
	public static final Map<String, String> ERROR_MAP = new HashMap<String, String>();
	static{
		ERROR_MAP.put("001", "参数错误");
		ERROR_MAP.put("002", "充值号码不合法");
		ERROR_MAP.put("003", "帐号密码错误");
		ERROR_MAP.put("004", "余额不足");
		ERROR_MAP.put("005", "不存在指定流量包");
		ERROR_MAP.put("006", "不支持该地区");
		ERROR_MAP.put("007", "卡号或者密码错误");
		ERROR_MAP.put("008", "该卡已使用过");
		ERROR_MAP.put("009", "该卡不支持(移动/电信/联通)号码");
		ERROR_MAP.put("010", "协议版本错误");
		ERROR_MAP.put("100", "签名验证错误");
		ERROR_MAP.put("999", "其他错误");
	}
}
