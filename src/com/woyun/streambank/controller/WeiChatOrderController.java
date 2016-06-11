package com.woyun.streambank.controller;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.woyun.streambank.service.WeiChatOrderService;
import com.woyun.streambank.util.alipay.AlipayConfig;
import com.woyun.streambank.util.common.Log4jUtil;
import com.woyun.streambank.util.common.ParameterCommon;
import com.woyun.streambank.util.weichatpay.WeiChatPayConfig;
import com.woyun.streambank.util.weichatpay.WeiChatSignature;
import com.woyun.streambank.util.weichatpay.XMLParser;


@Controller("/weiChatPay")
public class WeiChatOrderController {
	
	private WeiChatOrderService weiChatOrderService;
	
	@RequestMapping(value="/notify_url.do",method=RequestMethod.POST)
	public String weiChatPayNotify(HttpServletRequest request,HttpServletResponse response){
		PrintWriter out = null;
		String result = "";
		try{
			out = response.getWriter();//获取输出流
			String paramStr = ParameterCommon.getRequestPets(request);
			Map<String, Object> inMap = XMLParser.getMapFromXML(paramStr);
			if(WeiChatPayConfig.PAY_RESULT_SUCCESS.equals(inMap.get("return_code").toString())){
				if(WeiChatSignature.checkIsSignValidFromResponseString(paramStr)){//验证签名
					if(WeiChatPayConfig.PAY_RESULT_SUCCESS.equals(inMap.get("result_code"))){//判断支付的结果是否为成功,只处理这一个状态
						result = weiChatOrderService.weiChatPayNotify(inMap);
					}else{
						result = AlipayConfig.ALIPAY_NOTIFY_SUCCESS;
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			Log4jUtil.LOG4J.error(e);
		}finally{
			out.print(result);
		}
		return null;
	}
	
}
