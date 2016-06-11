package com.woyun.streambank.util.weichatpay;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Map;

public class WeiChatUtil {
    /**
     * 通过反射的方式遍历对象的属性和属性值，方便调试
     *
     * @param o 要遍历的对象
     * @throws Exception
     */
    public static void reflect(Object o) throws Exception {
        Class<? extends Object> cls = o.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            f.setAccessible(true);
        }
    }

    /**
     * 读取输入流的数据
     * @author 芮浩
     * @date 2016-6-5
     * 
     * @param in
     * @return
     * @throws IOException
     */
    public static byte[] readInput(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int len = 0;
        byte[] buffer = new byte[1024];
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
        out.close();
        in.close();
        return out.toByteArray();
    }

    /**
     * 将输入流的数据转为字符串
     * @author 芮浩
     * @date 2016-6-5
     * 
     * @param is
     * @return
     * @throws IOException
     */
    public static String inputStreamToString(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i;
        while ((i = is.read()) != -1) {
            baos.write(i);
        }
        return baos.toString();
    }


    /**
     * 字符串转为输入流
     * @author 芮浩
     * @date 2016-6-5
     * 
     * @param sInputString
     * @return
     */
    public static InputStream getStringStream(String sInputString) {
        ByteArrayInputStream tInputStringStream = null;
        if (sInputString != null && !sInputString.trim().equals("")) {
            tInputStringStream = new ByteArrayInputStream(sInputString.getBytes());
        }
        return tInputStringStream;
    }

//    public static Object getObjectFromXML(String xml, Class tClass) {
//        //将从API返回的XML数据映射到Java对象
//        XStream xStreamForResponseData = new XStream();
//        xStreamForResponseData.alias("xml", tClass);
//        xStreamForResponseData.ignoreUnknownElements();//暂时忽略掉一些新增的字段
//        return xStreamForResponseData.fromXML(xml);
//    }

    /**
     * 从map获取key
     * 对应的value
     * @author 芮浩
     * @date 2016-6-5
     * 
     * @param map
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getStringFromMap(Map<String, Object> map, String key, String defaultValue) {
        if (key == "" || key == null) {
            return defaultValue;
        }
        String result = (String) map.get(key);
        if (result == null) {
            return defaultValue;
        } else {
            return result;
        }
    }

    /**
     * 
     * @author 芮浩
     * @date 2016-6-5
     * 
     * @param map
     * @param key
     * @return
     */
    public static int getIntFromMap(Map<String, Object> map, String key) {
        if (key == "" || key == null) {
            return 0;
        }
        if (map.get(key) == null) {
            return 0;
        }
        return Integer.parseInt((String) map.get(key));
    }


    /**
     * 读取本地的xml数据，一般用来自测用
     * @param localPath 本地xml文件路径
     * @return 读到的xml字符串
     */
    public static String getLocalXMLString(String localPath) throws IOException {
        return WeiChatUtil.inputStreamToString(WeiChatUtil.class.getResourceAsStream(localPath));
    }
}
