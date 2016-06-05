package com.woyun.streambank.util.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	private static final String FORMAT_LONG = "yyyyMMddHHmmss";
	
	public static String getOrderTime(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_LONG);
		return sdf.format(date);
	}
}
