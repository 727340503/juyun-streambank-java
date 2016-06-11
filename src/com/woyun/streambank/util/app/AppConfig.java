package com.woyun.streambank.util.app;

import java.util.ArrayList;
import java.util.List;

public class AppConfig {
	//获取套餐的action
	public static final String GET_PACKAGE_ACTION = "getPackages";
	//转发获取套餐的接口
	public static final String GET_PACKAGE_URL = "/package/getProducts.json";
	//创建订单的action
	public static final String CREATE_ORDER_ACTION = "createOrder";
	//创建订单的接口地址
	public static final String CREATE_ORDER_URL = "/order/createOrder.json";
	//过滤请求地址的url
	public static final List<String> DISABLE_EXTERNAL_URI_FILTERS = new ArrayList<String>();
	static{
		DISABLE_EXTERNAL_URI_FILTERS.add("/createOrder.json");
	}
}
