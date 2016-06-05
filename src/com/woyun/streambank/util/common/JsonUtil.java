package com.woyun.streambank.util.common;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtil {

	@SuppressWarnings("unchecked")
	public static Map<String, Object> strToMap(String jsonStr) {
		JSONObject json = JSONObject.fromObject(jsonStr);
		return json;
	}

	public static String mapToJsonstr(Map<String, Object> map) {
		JSONObject json = JSONObject.fromObject(map);
		return json.toString();
	}

	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> strToMapList(String str) {
		JSONArray jsonArray = JSONArray.fromObject(str);
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < jsonArray.size(); i++) {
			resultList.add(jsonArray.getJSONObject(i));
		}
		return resultList;
	}

	public static Map<String, Object> convertBean(Object bean) throws Exception {
		Class<? extends Object> type = bean.getClass();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);

		PropertyDescriptor[] propertyDescriptors = beanInfo
				.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					returnMap.put(propertyName, result);
				} else {
					returnMap.put(propertyName, "");
				}
			}
		}
		return returnMap;
	}

}
