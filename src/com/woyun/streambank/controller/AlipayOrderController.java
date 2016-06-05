package com.woyun.streambank.controller;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.woyun.streambank.service.AlipayOrderService;
import com.woyun.streambank.util.alipay.AlipayConfig;
import com.woyun.streambank.util.alipay.AlipayNotify;
import com.woyun.streambank.util.common.Log4jUtil;
import com.woyun.streambank.util.common.ParameterCommon;


@Controller
@RequestMapping("/alipay")
public class AlipayOrderController {
	
	@Autowired
	private AlipayOrderService alipayOrderService;
	
	@RequestMapping(value="/alipay_notify.do",method=RequestMethod.POST)
	public String alipayNotify(HttpServletRequest request,HttpServletResponse response){
		PrintWriter out = null;
		String result = "";
		try{
			out = response.getWriter();//获取输出流
			Map<String, String> inMap = ParameterCommon.getParameters(request);
			Log4jUtil.LOG4J.info("获取到的支付宝回调的参数");
			Log4jUtil.LOG4J.info(String.valueOf(inMap));
			if(AlipayNotify.verify(inMap)){//验证是否是支付宝发起的请求
				if("TRADE_SUCCESS".equals(inMap.get("trade_status"))){//判断支付的结果是否为成功,只处理这一个状态
					result = alipayOrderService.alipayNotify(inMap);
				}else{
					result = AlipayConfig.ALIPAY_NOTIFY_SUCCESS;
				}
			}else{
				result = AlipayConfig.ALIPAY_NOTIFY_ERROR_SIGN;
			}
		}catch (Exception e) {
			Log4jUtil.LOG4J.error(e);
			e.printStackTrace();
		}finally{
			out.print(result);
		}
		return null;
	}
	
	@RequestMapping(value="/alipay_return.do",method=RequestMethod.GET)
	public String alipayReturn(HttpServletRequest request,HttpServletResponse response){
		HttpSession session = null;
		try{
			session = request.getSession();
			Map<String, String> inMap = ParameterCommon.getParameters(request);
			String subject = new String(inMap.get("subject").getBytes("iso8859-1"),"utf-8");
			inMap.put("subject", subject);
			Log4jUtil.LOG4J.info("获取到的支付宝return的参数");
			Log4jUtil.LOG4J.info(String.valueOf(inMap));
//			if(AlipayNotify.verifyReturn(inMap)){//验证是否是支付宝发起的请求
				return alipayOrderService.alipayReturn(inMap,session);
//			}else{
//				session.setAttribute("errorMsg", "签名错误");
//			}
		}catch (Exception e) {
			e.printStackTrace();
			Log4jUtil.LOG4J.error(e);
			session.setAttribute("errorMsg", "服务器繁忙,稍后请重试!");
		}
		return "/web/error";
	}
}
