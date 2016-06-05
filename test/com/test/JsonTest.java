package com.test;

import java.sql.Timestamp;

import net.sf.json.JSONObject;

import org.junit.Test;

public class JsonTest {
	
	
	@Test
	public void test(){
		String str = "{\"head\":{\"action\":\"alarm\"},\"body\":{\"staff_code\":\"18988590416\",\"alarm_cont\":\"ALARM_NO:11986\r\n设备名称:汕头中医药技工学校\r\n告警内容:OFFLINE:\r\n告警次数:126\r\n告警时间:2016-01-22 09:11:56\"}}";
		JSONObject json = JSONObject.fromObject(str.replaceAll("\n", "\\\\n").replaceAll("\r", "\\\\r"));
		
		System.out.println(json);
	}
	@Test
	public void test2() {
		// TODO Auto-generated method stub
		System.out.println(new Timestamp(System.currentTimeMillis()));
	}
}
