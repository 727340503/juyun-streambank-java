package com.woyun.streambank.util.common.maiyuan;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.woyun.streambank.model.RechargeRequest;
import com.woyun.streambank.util.common.Md5Util;


public class MaiYuanParamUtil {
	/**
	 * 获取发送获取套餐的参数
	 * @author 芮浩
	 * @date 2016-5-27
	 * 
	 * @param type
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String getPackageParam(String type) throws UnsupportedEncodingException{
		StringBuilder signParam = new StringBuilder();
		signParam.append(MaiYuanConfig.PARAM_TYPE);
		signParam.append("=");
		signParam.append(type);
		signParam.append("&");
		signParam.append(MaiYuanConfig.PARAM_KEY.toLowerCase());
		signParam.append("=");
		signParam.append(MaiYuanConfig.KEY);
		String sign = getSign(signParam.toString());
		StringBuilder param = new StringBuilder();
		param.append(MaiYuanConfig.PARAM_ACCOUNT);
		param.append("=");
		param.append(URLEncoder.encode(MaiYuanConfig.ACCOUNT,"UTF-8"));
		param.append("&");
		param.append(MaiYuanConfig.PARAM_TYPE);
		param.append("=");
		param.append(type);
		param.append("&");
		param.append(MaiYuanConfig.PARAM_SIGN);
		param.append("=");
		param.append(sign);
		param.append("&");
		param.append(MaiYuanConfig.PARAM_ACTION);
		param.append("=");
		param.append(MaiYuanConfig.GET_PACKAGE_ACTION);
		param.append("&");
		param.append(MaiYuanConfig.PARAM_VERSION);
		param.append("=");
		param.append(MaiYuanConfig.VERSION);
		return param.toString();
	}
	/**
	 * 获取请求充值流量接口的参数
	 * @author 芮浩
	 * @date 2016-6-1
	 * 
	 * @param order
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String getRquestRechargeFlowParam(RechargeRequest rechargeRequest) throws UnsupportedEncodingException {
		StringBuilder signParam = new StringBuilder();
		signParam.append(MaiYuanConfig.PARAM_MOBILE);
		signParam.append("=");
		signParam.append(rechargeRequest.getRechargeMobile());
		signParam.append("&");
		signParam.append(MaiYuanConfig.PARAM_PACKAGE);
		signParam.append("=");
		signParam.append(rechargeRequest.getPackageId());
		signParam.append("&");
		signParam.append(MaiYuanConfig.PARAM_KEY.toLowerCase());
		signParam.append("=");
		signParam.append(MaiYuanConfig.KEY);
		String sign = getSign(signParam.toString());
		StringBuilder param = new StringBuilder();
		param.append(MaiYuanConfig.PARAM_MOBILE);
		param.append("=");
		param.append(rechargeRequest.getRechargeMobile());
		param.append("&");
		param.append(MaiYuanConfig.PARAM_PACKAGE);
		param.append("=");
		param.append(rechargeRequest.getPackageId());
		param.append("&");
		param.append(MaiYuanConfig.PARAM_ACCOUNT);
		param.append("=");
		param.append(URLEncoder.encode(MaiYuanConfig.ACCOUNT,"UTF-8"));
		param.append("&");
		param.append(MaiYuanConfig.PARAM_VERSION);
		param.append("=");
		param.append(MaiYuanConfig.VERSION);
		param.append("&");
		param.append(MaiYuanConfig.PARAM_ACTION);
		param.append("=");
		param.append(MaiYuanConfig.CHARGE_FLOW_ACTION);
		param.append("&");
		param.append(MaiYuanConfig.PARAM_OUT_TRADE_NO);
		param.append("=");
		param.append(rechargeRequest.getOrderId());
		param.append("&");
		param.append(MaiYuanConfig.PARAM_SIGN);
		param.append("=");
		param.append(sign);
		param.append("&");
		param.append(MaiYuanConfig.PARAM_RANGE);
		param.append("=");
		param.append(0);//该参数不带时，默认为0
		return param.toString();
	}
	/**
	 * 获取加密后的字符串
	 * @author 芮浩
	 * @date 2016-5-27
	 * 
	 * @param paramStr
	 * @return
	 */
	private static String getSign(String paramStr){
		StringBuilder param = new StringBuilder();
		param.append(MaiYuanConfig.PARAM_ACCOUNT);
		param.append("=");
		param.append(MaiYuanConfig.ACCOUNT);
		param.append("&");
		param.append(paramStr);
		System.out.println(param.toString());
		return Md5Util.md5(param.toString());
	}

	
}
