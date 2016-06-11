package com.woyun.streambank.service.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.woyun.streambank.model.Dealer;
import com.woyun.streambank.model.Result;
import com.woyun.streambank.service.AppRechargeService;
import com.woyun.streambank.service.DealerService;
import com.woyun.streambank.util.app.AppConfig;
import com.woyun.streambank.util.common.Md5Util;

@Service("appRechargeService")
public class AppRechargeServiceImpl implements AppRechargeService{

	@Autowired
	private DealerService dealerService;
	
	public Result getPackagesApi(HttpServletRequest request, HttpServletResponse response,Map<String, Object> paramMap) throws Exception {
		Result result = new Result();
		try{
			if(StringUtils.isEmpty(paramMap.get("phone"))){
				result.setStatus(1);
				result.setMessage("参数不完整");
				return result;
			}
			String dealerName = paramMap.get("dealer").toString();
			String phone = paramMap.get("phone").toString();
			String sign = paramMap.get("sign").toString();
			if(phone.length() != 11){
				result.setStatus(2);
				result.setMessage("参数不符合要求");
				return result;
			}
			//根据代理商查询代理商的key
			Dealer dealer = dealerService.findDealerByName(dealerName);
			if(dealer != null ){
				String mSign = Md5Util.md5("dealer="+dealerName+"&key="+dealer.getDealerNo()+"&phone="+phone);
				if(mSign.equals(sign)){
					request.getRequestDispatcher(AppConfig.GET_PACKAGE_URL).forward(request, response);
					return null;
				}
			}
			result.setStatus(3);
			result.setMessage("签名错误");
		}catch (Exception e) {
			throw e;
		}
		return result;
	}

	public Result createOrderApi(HttpServletRequest request, HttpServletResponse response,Map<String, Object> paramMap) throws Exception {
		Result result = new Result();
		try{
			if(StringUtils.isEmpty(paramMap.get("phone"))){
				result.setStatus(1);
				result.setMessage("参数不完整");
				return result;
			}
			if(StringUtils.isEmpty(paramMap.get("package"))){
				result.setStatus(1);
				result.setMessage("参数不完整");
				return result;
			}
			if(StringUtils.isEmpty(paramMap.get("pay"))){
				result.setStatus(1);
				result.setMessage("参数不完整");
				return result;
			}
			if(StringUtils.isEmpty(paramMap.get("num"))){
				result.setStatus(1);
				result.setMessage("参数不完整");
				return result;
			}
			String dealerName = paramMap.get("dealer").toString();
			String phone = paramMap.get("phone").toString();
			String sign = paramMap.get("sign").toString();
			String packageId = paramMap.get("package").toString();
			String payType = paramMap.get("pay").toString();
			String num = paramMap.get("num").toString();
			if(phone.length() != 11){
				result.setStatus(2);
				result.setMessage("参数不符合要求");
				return result;
			}
			//根据代理商名称查询代理商的key
			Dealer dealer = dealerService.findDealerByName(dealerName);
			if(dealer != null){
				String mSign = Md5Util.md5("dealer="+dealerName+"&key="+dealer.getDealerNo()+"&num="+num+"&package="+packageId+"&pay="+payType+"&phone="+phone);
				if(sign.equals(mSign)){
					request.getRequestDispatcher(AppConfig.CREATE_ORDER_URL).forward(request, response);
					return null;
				}
			}
			result.setStatus(3);
			result.setMessage("签名错误");
		}catch (Exception e) {
			throw e;
		}
		return result;
	}

}
