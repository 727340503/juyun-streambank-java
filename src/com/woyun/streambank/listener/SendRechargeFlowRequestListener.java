package com.woyun.streambank.listener;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.woyun.streambank.timertask.SendRechargeFlowRequestTask;

public class SendRechargeFlowRequestListener implements ServletContextListener{
	//发送充值流量请求的timertask
	private SendRechargeFlowRequestTask sendRechargeFlowRequestTask;
	
	public void contextDestroyed(ServletContextEvent context) {
		
	}

	public void contextInitialized(ServletContextEvent event) {
		//需要手动的方式对bean进行实例化
		ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext());
		sendRechargeFlowRequestTask = (SendRechargeFlowRequestTask) context.getBean("sendRechargeFlowRequestTask");
		//通过配置定时器，让首页的数据每隔一小时同步一次(配置为守护线程，项目关闭的时候该线程自动消失)
		new Timer(true).schedule(sendRechargeFlowRequestTask, 0, 1*60*1000);//每5分钟执行一次
	}

}
