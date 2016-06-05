package com.woyun.streambank.util.common;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XMLUtil {
	
	@SuppressWarnings("unchecked")
	public static Map<String, String> xmlToMap(String xmlStr) {
		Map<String, String> resMap = new HashMap<String, String>();
		Document document = null;
		try {
			SAXReader reader = new SAXReader();
			InputStream in = new ByteArrayInputStream(
					xmlStr.getBytes("iso8859-1"));
			InputStreamReader strInStream = new InputStreamReader(in,
					"iso8859-1");
			document = reader.read(strInStream);
			Element root = document.getRootElement();
			Iterator<Element> it = root.elementIterator();
			while (it.hasNext()) {
				Element e = it.next();
				resMap.put(e.getName(), e.getStringValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resMap;
	}
	
}
