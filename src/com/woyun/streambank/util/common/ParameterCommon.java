package com.woyun.streambank.util.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


public class ParameterCommon {
	
	
	/**
	 * 内部方法：获取前台参数
	 * @return Map<String,Object>
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,Object> buildParameter(HttpServletRequest request){
		Map<String, Object> parameter = new HashMap<String, Object>();
		java.util.Enumeration<String> paremEnu = null;
		paremEnu = request.getParameterNames();
		while (paremEnu.hasMoreElements()) {
			String paramName = paremEnu.nextElement();
			parameter.put(paramName, request.getParameter(paramName));
		}
		return parameter;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,String> getParameters(HttpServletRequest request){
		Map<String, String> parameter = new HashMap<String, String>();
		java.util.Enumeration<String> paremEnu = null;
		paremEnu = request.getParameterNames();
		while (paremEnu.hasMoreElements()) {
			String paramName = paremEnu.nextElement();
			parameter.put(paramName, request.getParameter(paramName));
		}
		return parameter;
	}
	
	/**
	 * 获取前端传的xml数据
	 * @param req
	 * @return
	 */
	public static String getRequestPets(HttpServletRequest request) {  
        StringBuilder sb = new StringBuilder();  
        BufferedReader bufferedReader = null;  
        try {  
            bufferedReader = request.getReader();  
            String line = null;  
            while((line=bufferedReader.readLine()) != null) {  
                sb.append(line);  
            }  
        }   
        catch (IOException e) {  
            e.printStackTrace();  
        } finally{  
            try {  
                bufferedReader.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return sb.toString();  
    }  
}
