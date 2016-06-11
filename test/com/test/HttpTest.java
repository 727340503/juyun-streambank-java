package com.test;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.woyun.streambank.model.Order;
import com.woyun.streambank.model.RechargeRequest;
import com.woyun.streambank.service.WeiChatOrderService;
import com.woyun.streambank.service.impl.WeiChatOrderSericeImpl;
import com.woyun.streambank.util.common.HttpClientUtil;
import com.woyun.streambank.util.common.JsonUtil;
import com.woyun.streambank.util.common.maiyuan.MaiYuanConfig;
import com.woyun.streambank.util.common.maiyuan.MaiYuanParamUtil;

public class HttpTest {

	@Test
	public void test1() throws UnsupportedEncodingException {
		// 根据手机号所属获取发送参数
		String param = MaiYuanParamUtil.getPackageParam(2 + "");
		// 发送请求
		String httpResult = HttpClientUtil.sendPost(MaiYuanConfig.HTTP_URL,
				param);
		System.err.println(httpResult);
		if (!"".equals(httpResult)) {
			String str = "{\"Code\":\"0\",\"Message\":\"OK\",\"Packages\":[]}";
			Map<String, Object> resultMap = JsonUtil.strToMap(str);
			List<Map<String, Object>> packageList = JsonUtil
					.strToMapList(resultMap.get("Packages").toString());
			System.out.println(packageList.size());
		}
	}

	@Test
	public void test2() throws Exception {
		RechargeRequest r = new RechargeRequest();
		r.setOrderId(58);
		r.setPackageId(5+"");
		r.setPackageName("5M");
		r.setRechaegeResult(null);
		r.setRechargeMobile("1898859041");
		r.setRechargeNum(1);
		r.setRechargeRequestId(25);
		r.setRechargeState(1);
		String paramStr = MaiYuanParamUtil.getRquestRechargeFlowParam(r);
		System.out.println(paramStr);
		String httpResult = HttpClientUtil.sendPost(MaiYuanConfig.HTTP_URL, paramStr);
		System.out.println(httpResult);
	}
	
	@Test
	public void test3() throws Exception{
		Order order = new Order();
		order.setOutTradeNo("weichat2016060518988590416");
		order.setTotal(0.01);
		order.setPackageName("5M测试");
		WeiChatOrderService w = new WeiChatOrderSericeImpl();
		String str = w.getWeiChatOrderPayUrl(order);
		System.out.println(str);
	}
}
