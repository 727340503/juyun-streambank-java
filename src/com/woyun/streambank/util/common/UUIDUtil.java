package com.woyun.streambank.util.common;

import java.util.UUID;

/**
 *	生成随机ID
 */
public class UUIDUtil {

	public static String getUID() {
		return UUID.randomUUID().toString();
	}

}
