package com.woyun.streambank.controller;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.woyun.streambank.model.PushMsg;
import com.woyun.streambank.model.Result;
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
	
	/**
	 * 创建订单接口
	 * @author 芮浩
	 * @date 2016-6-8
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/createOrder.do",method=RequestMethod.GET)
	public String createOrder(HttpServletRequest request,HttpServletResponse response){
		String result = null;
		HttpSession session = request.getSession();
		try{
			String payType = request.getParameter("pay");
			if(StringUtils.isEmpty(payType)){
				session.setAttribute("errorMsg", "非法访问");
				result = "/web/error";
			}else{
				result = orderService.createOrder(request, payType,response);
			}
		}catch (Exception e) {
			Log4jUtil.LOG4J.error(e);
			e.printStackTrace();
			session.setAttribute("errorMsg", "服务器繁忙,请重试");
			result = "/web/error";
		}
		return result;
	}
	
	/**
	 * 流量充值异步回调接口
	 * @author 芮浩
	 * @date 2016-6-8
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/recharge_notify.do",method=RequestMethod.POST)
	public String rechargeFlowNotify(HttpServletRequest request,HttpServletResponse response){
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
	
	@RequestMapping(value="/createOrder.json",method=RequestMethod.GET)
	public Result createAppRechargeOrder(HttpServletRequest request,HttpServletResponse response){
		Result result = new Result();
		try{
			String param = request.getQueryString();
			if(param.indexOf("action=createOrder") == -1 || param.indexOf("action=createOrder") != 0){//判断请求是否是通过转发过来的,转发的请求中有该参数
				result.setStatus(100);
				result.setMessage("非法访问");
				return result;
			}
			Map<String, Object> paramMap = ParameterCommon.buildParameter(request);
			if(StringUtils.isEmpty(paramMap.get("phone"))){
				result.setStatus(1);
				result.setMessage("参数不完整");
				return result;
			}
			if(StringUtils.isEmpty(paramMap.get("package"))){
				result.setStatus(1);
				result.setMessage("参数不完整");
				return result;
			}
			if(StringUtils.isEmpty(paramMap.get("pay"))){
				result.setStatus(1);
				result.setMessage("参数不完整");
				return result;
			}
			if(StringUtils.isEmpty(paramMap.get("dealer"))){
				result.setStatus(1);
				result.setMessage("参数不完整");
				return result;
			}
			if(StringUtils.isEmpty(paramMap.get("num"))){
				result.setStatus(1);
				result.setMessage("参数不完整");
				return result;
			}
			result = orderService.createAppOrder(paramMap);
		}catch (Exception e) {
			e.printStackTrace();
			Log4jUtil.LOG4J.error(e);
			result.setStatus(999);
			result.setMessage("服务器繁忙,请重试");
		}
		return result;
	}
}
