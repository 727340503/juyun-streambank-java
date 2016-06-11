package com.test;

import java.io.UnsupportedEncodingException;

import net.sf.json.JSONObject;

import org.junit.Test;

import com.woyun.streambank.model.RechargeRequest;
import com.woyun.streambank.util.common.Base64Util;
import com.woyun.streambank.util.common.DESUtils;
import com.woyun.streambank.util.common.maiyuan.MaiYuanParamUtil;

public class JsonTest {
	
	
	@Test
	public void test(){
		String str = "{\"head\":{\"action\":\"alarm\"},\"body\":{\"staff_code\":\"18988590416\",\"alarm_cont\":\"ALARM_NO:11986\r\n设备名称:汕头中医药技工学校\r\n告警内容:OFFLINE:\r\n告警次数:126\r\n告警时间:2016-01-22 09:11:56\"}}";
		JSONObject json = JSONObject.fromObject(str.replaceAll("\n", "\\\\n").replaceAll("\r", "\\\\r"));
		
		System.out.println(json);
	}
	@Test
	public void test2() throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
//		System.out.println(new Timestamp(System.currentTimeMillis()));
		System.out.println(Base64Util.getFromBase64(DESUtils.decrypt("47CC189B4F1D77BF8B11EA87BA0405E2F12FB22BC519EB0A04F577A9F43F2E3D6D9E14627738593AD86C0B39AD82DAC1E53C3725959398CA4697293F92370716247A992EFF171ADD4D5A9322E689D5F9F2E970B5DBBED81A5761CFDA0C2DB2881014D8928F999357CB24B951EE05B664DBABF5FA4D19346DDB6227C1DFDDBC170B37A63B57A02E0B547127DEC1073748FCD6127D7359F6822AAEF8A719A3121203C93540B27855FF")));
	}
	@Test
	public void test3() throws Exception{
//		String str = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[您没有JSAPI支付权限]]></return_msg></xml>";
//		System.out.println(XMLParser.getMapFromXML(str));
		RechargeRequest r = new RechargeRequest();
		r.setOrderId(123);
		r.setPackageId(10+"");
		r.setRechargeMobile("18988590416");
		System.out.println(MaiYuanParamUtil.getRquestRechargeFlowParam(r));
		
	}
}
