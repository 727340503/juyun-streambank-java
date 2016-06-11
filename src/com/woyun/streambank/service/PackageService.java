package com.woyun.streambank.service;

import com.woyun.streambank.model.FlowPackage;
import com.woyun.streambank.model.Result;



public interface PackageService {
	/**
	 * 根据手机号获取充值套餐
	 * @author 芮浩
	 * @date 2016-5-30
	 * 
	 * @param phone
	 * @param dealerNo
	 * @return
	 * @throws Exception
	 */
	public Result getPackageList(String phone,String dealerName) throws Exception;
	/**
	 * 获取单个套餐信息
	 * @author 芮浩
	 * @date 2016-5-30
	 * 
	 * @param phone
	 * @param flowName
	 * @return
	 * @throws Exception
	 */
	public FlowPackage getPackageByPackageId(String phone,String flowName) throws Exception;
}
