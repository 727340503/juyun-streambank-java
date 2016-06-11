package com.woyun.streambank.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.woyun.streambank.model.Result;

public interface AppRechargeService {
	/**
	 * 获取套餐api接口
	 * @author 芮浩
	 * @date 2016-6-8
	 * 
	 * @param request
	 * @param response 
	 * @param paramMap
	 * @return
	 * @throws Exception 
	 */
	public Result getPackagesApi(HttpServletRequest request, HttpServletResponse response, Map<String, Object> paramMap) throws Exception;
	/**
	 * 创建订单api接口
	 * @author 芮浩
	 * @date 2016-6-8
	 * 
	 * @param request
	 * @param paramMap
	 * @return
	 * @throws Exception 
	 */
	public Result createOrderApi(HttpServletRequest request, HttpServletResponse response, Map<String, Object> paramMap) throws Exception;
	
}
