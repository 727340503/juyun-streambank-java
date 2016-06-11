package com.woyun.streambank.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.woyun.streambank.model.Result;
import com.woyun.streambank.service.PackageService;
import com.woyun.streambank.util.common.Log4jUtil;
import com.woyun.streambank.util.common.maiyuan.StreamBankConfig;


@Controller
@RequestMapping("/package")
public class PackageController {
	
	@Autowired
	private PackageService packageService;
	
	@RequestMapping(value="/getProducts.json",method=RequestMethod.GET)
	public Result getProductList(HttpServletRequest request, HttpServletResponse response){
		Result result = new Result();
		try{
			String phone = request.getParameter("phone");
			if(StringUtils.isEmpty(phone)){
				result.setStatus(1);
				result.setMessage("参数不完整");
			}else{
				String dealer = request.getParameter("dealer");
				if(StringUtils.isEmpty(dealer)){
					dealer = StreamBankConfig.APP_DEALER_NAME;
				}
				result = packageService.getPackageList(phone,dealer);
			}
		}catch (Exception e) {
			Log4jUtil.LOG4J.error(e);
			result.setStatus(999);
			result.setMessage("查询套餐出错,请重试");
		}
		return result;
	}
}
