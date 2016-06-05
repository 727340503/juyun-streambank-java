package com.woyun.streambank.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.woyun.streambank.dao.PackageMapper;
import com.woyun.streambank.model.Dealer;
import com.woyun.streambank.model.FlowPackage;
import com.woyun.streambank.model.Result;
import com.woyun.streambank.service.DealerService;
import com.woyun.streambank.service.PackageService;
import com.woyun.streambank.util.common.HttpClientUtil;
import com.woyun.streambank.util.common.JsonUtil;
import com.woyun.streambank.util.common.Log4jUtil;
import com.woyun.streambank.util.common.maiyuan.MaiYuanConfig;
import com.woyun.streambank.util.common.maiyuan.MaiYuanParamUtil;

@Service("productService")
public class PackageServiceImpl implements PackageService{

	@Autowired
	private PackageMapper packageMapper;
	@Autowired
	private DealerService dealerService;
	
	public Result getPackageList(String phone,String dealerNo) throws Exception {
		Result result = new Result();
		try{
			//判断手机号所属运营商
			Integer type = packageMapper.getPhoneOperator(phone.substring(0,7));
			if(type == null){
				result.setStatus(3);
				result.setMessage("暂不支持该手机号充值,请联系客服");
			}else{
				//根据手机号所属获取发送参数
				String param = MaiYuanParamUtil.getPackageParam(type+"");
				//发送请求
				String httpResult = HttpClientUtil.sendPost(MaiYuanConfig.HTTP_URL, param);
				//接收结果，封装套餐
				if(!StringUtils.isEmpty(httpResult)){
					Map<String, Object> resultMap = JsonUtil.strToMap(httpResult);
					if(MaiYuanConfig.SUCCESS_CODE.equals(resultMap.get("Code").toString())){
						List<Map<String, Object>> packageList = JsonUtil.strToMapList(resultMap.get("Packages").toString());
						if(packageList.size() == 0){
							result.setStatus(2);
							result.setMessage("暂无可充值套餐");
						}else{
							//查询套餐销售折扣
							Dealer dealer = dealerService.findDealerByNo(dealerNo);
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("packageList", packageList);//数据
							map.put("discount", dealer.getSettlementDiscount());//销售折扣
							result.setStatus(0);
							result.setData(map);
						}
					}else{
						result.setStatus(999);
						result.setMessage("获取套餐失败,请稍后重试!");
					}
				}else{
					result.setStatus(999);
					result.setMessage("获取套餐失败,请稍后重试!");
				}
				Log4jUtil.LOG4J.info("发送获取套餐请求的结果----------"+httpResult);
			}
		}catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	public FlowPackage getPackageByPackageId(String phone,String flowName) throws Exception{
		//判断手机号所属运营商
		Integer type = packageMapper.getPhoneOperator(phone.substring(0,7));
		//根据手机号所属获取发送参数
		String param = MaiYuanParamUtil.getPackageParam(type+"");
		//发送请求
		String httpResult = HttpClientUtil.sendPost(MaiYuanConfig.HTTP_URL, param);
		//接收结果，封装套餐
		if(!"".equals(httpResult)){
			Map<String, Object> resultMap = JsonUtil.strToMap(httpResult);
			if(MaiYuanConfig.SUCCESS_CODE.equals(resultMap.get("Code").toString())){
				List<Map<String, Object>> packageList = JsonUtil.strToMapList(resultMap.get("Packages").toString());
				if(packageList.size() == 0){
					return null;
				}else{
					FlowPackage flowPackage = null;
					//遍历查询回来的套餐数据
					for(Map<String, Object> packageMap : packageList){
						if(flowName.equals(packageMap.get("Package").toString())){
							flowPackage = new FlowPackage();
							flowPackage.setPackageId(packageMap.get("Package").toString());
							flowPackage.setPackageName(packageMap.get("Name").toString());
							flowPackage.setPrice(Double.parseDouble(packageMap.get("Price").toString()));
							break;
						}
					}
					return flowPackage;
				}
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
}
