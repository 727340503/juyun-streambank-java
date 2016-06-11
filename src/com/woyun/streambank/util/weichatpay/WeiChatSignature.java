package com.woyun.streambank.util.weichatpay;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.woyun.streambank.util.common.Log4jUtil;

/**
 * User: rizenguo
 * Date: 2014/10/29
 * Time: 15:23
 */
public class WeiChatSignature {
    /**
     * 签名算法
     * @param o 要参与签名的数据对象
     * @return 签名
     * @throws IllegalAccessException
     */
    public static String getSign(Object o) throws IllegalAccessException {
        ArrayList<String> list = new ArrayList<String>();
        Class<? extends Object> cls = o.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            if (f.get(o) != null && f.get(o) != "") {
                list.add(f.getName() + "=" + f.get(o) + "&");
            }
        }
        int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + WeiChatPayConfig.KEY;
//        Log4jUtil.LOG4J.info("Sign Before MD5:" + result);
        result = MD5.MD5Encode(result).toUpperCase();
//        Log4jUtil.LOG4J.info("Sign Result:" + result);
        return result;
    }

    public static String getSign(Map<String,Object> map){
        ArrayList<String> list = new ArrayList<String>();
        for(Map.Entry<String,Object> entry:map.entrySet()){
            if(entry.getValue()!=""){
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + WeiChatPayConfig.KEY;
        //Util.log("Sign Before MD5:" + result);
        result = MD5.MD5Encode(result).toUpperCase();
        //Util.log("Sign Result:" + result);
        return result;
    }

    /**
     * 从API返回的XML数据里面重新计算一次签名
     * @param responseString API返回的XML数据
     * @return 新鲜出炉的签名
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static String getSignFromResponseString(String responseString) throws Exception {
        Map<String,Object> map = XMLParser.getMapFromXML(responseString);
        //清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
        map.put("sign","");
        //将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
        return WeiChatSignature.getSign(map);
    }

    /**
     * 检验API返回的数据里面的签名是否合法，避免数据在传输的过程中被第三方篡改
     * @param responseString API返回的XML数据字符串
     * @return API签名是否合法
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static boolean checkIsSignValidFromResponseString(String responseString) throws Exception {

        Map<String,Object> map = XMLParser.getMapFromXML(responseString);
        Log4jUtil.LOG4J.info(map.toString());

        String signFromAPIResponse = map.get("sign").toString();
        if(signFromAPIResponse=="" || signFromAPIResponse == null){
        	Log4jUtil.LOG4J.info("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
            return false;
        }
        Log4jUtil.LOG4J.info("服务器回包里面的签名是:" + signFromAPIResponse);
        //清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
        map.put("sign","");
        //将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
        String signForAPIResponse = WeiChatSignature.getSign(map);

        if(!signForAPIResponse.equals(signFromAPIResponse)){
            //签名验不过，表示这个API返回的数据有可能已经被篡改了
            Log4jUtil.LOG4J.info("API返回的数据签名验证不通过，有可能被第三方篡改!!!");
            return false;
        }
        Log4jUtil.LOG4J.info("恭喜，API返回的数据签名验证通过!!!");
        return true;
    }

    /**
     * 获取发送统一下单请求的xml格式参数
     * @author 芮浩
     * @date 2016-6-5
     * 
     * @return
     * @throws Exception 
     */
    public static String getRequestXmlInfoByMap(Map<String,String> paramMap) throws Exception{
    	String sign = getSign(paramMap);
    	if(sign == null){
    		return null;
    	}
    	paramMap.put("sign", sign);
    	return XMLParser.getXmlInfoByMap(paramMap);
    }
    
}
