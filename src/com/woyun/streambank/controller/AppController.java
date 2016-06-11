package com.woyun.streambank.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.woyun.streambank.model.Result;
import com.woyun.streambank.service.AppRechargeService;
import com.woyun.streambank.util.app.AppConfig;
import com.woyun.streambank.util.common.Log4jUtil;
import com.woyun.streambank.util.common.ParameterCommon;

@Controller
public class AppController {

	@Autowired
	private AppRechargeService appRechargeService;
	
	@RequestMapping(value="/api.json",method=RequestMethod.GET)
	public Result apiRequest(HttpServletRequest request,HttpServletResponse response){
		Result result = new Result();
		try{
			Map<String, Object> paramMap = ParameterCommon.buildParameter(request);
			if(!paramMap.containsKey("action") || StringUtils.isEmpty(paramMap.get("action"))){
				result.setMessage("参数不完整");
				result.setStatus(1);
				return result;
			}
			if(!paramMap.containsKey("dealer") || StringUtils.isEmpty(paramMap.get("dealer"))){
				result.setMessage("参数不完整");
				result.setStatus(1);
				return result;
			}
			if(!paramMap.containsKey("sign") || StringUtils.isEmpty(paramMap.get("sign"))){
				result.setMessage("参数不完整");
				result.setStatus(1);
				return result;
			}
			String action = paramMap.get("action").toString();
			if(action.equals(AppConfig.GET_PACKAGE_ACTION)){
				result = appRechargeService.getPackagesApi(request,response,paramMap);
			}else if(action.equals(AppConfig.CREATE_ORDER_ACTION)){
				result = appRechargeService.createOrderApi(request,response,paramMap);
			}
		}catch (Exception e) {
			e.printStackTrace();
			Log4jUtil.LOG4J.error(e);
			result.setStatus(999);
			result.setMessage("服务器繁忙,请重试");
		}
		return result;
	}
	
}
