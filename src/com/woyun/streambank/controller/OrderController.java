package com.woyun.streambank.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.woyun.streambank.model.PushMsg;
import com.woyun.streambank.service.OrderService;
import com.woyun.streambank.util.common.Log4jUtil;
import com.woyun.streambank.util.common.ParameterCommon;
import com.woyun.streambank.util.common.PushMsgUtil;


@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value="/getPayToken.do",method=RequestMethod.GET)
	public String getPayToken(HttpServletRequest request, HttpServletResponse response){
		String pageName = "index";
		try{
			pageName = orderService.getPayToken(request);
		}catch (Exception e) {
			Log4jUtil.LOG4J.error(e);
			e.printStackTrace();
			request.getSession().setAttribute("errorMsg", "服务器繁忙,请稍后重试！");
			pageName = "/web/error";
		}
		return pageName;
	}
	
	@RequestMapping(value="/createOrder.do",method=RequestMethod.GET)
	public String createAlipaiOrder(HttpServletRequest request,HttpServletResponse response){
		String result = null;
		HttpSession session = request.getSession();
		try{
			String payType = request.getParameter("pay");
			if(StringUtils.isEmpty(payType)){
				session.setAttribute("errorMsg", "非法访问");
				result = "/web/error";
			}else{
				result = orderService.createOrder(session, payType,response);
			}
		}catch (Exception e) {
			Log4jUtil.LOG4J.error(e);
			e.printStackTrace();
			session.setAttribute("errorMsg", "服务器繁忙,请重试");
			result = "/web/error";
		}
		return result;
	}
	
	@RequestMapping(value="/recharge_notify.do",method=RequestMethod.POST)
	public String rechargeFlowNotify(HttpServletRequest request,HttpServletResponse response){
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>rechatge_notify.do");
		PrintWriter out = null;
		String result = "fail";
		try{
			out = response.getWriter();
			String paramStr = ParameterCommon.getRequestPets(request);
			System.out.println(paramStr);
			List<PushMsg> pushMsgs = PushMsgUtil.getPushMsgList(paramStr);
			if(pushMsgs != null){
				result = orderService.rechargeFlowNotify(pushMsgs);
			}
		}catch (Exception e) {
			Log4jUtil.LOG4J.error(e);
			e.printStackTrace();
		}finally{
			out.write(result);
		}
		return null;
	}
}
