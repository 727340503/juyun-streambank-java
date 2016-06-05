package com.woyun.streambank.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.woyun.streambank.util.common.Log4jUtil;


/**
 * 保存请求日志的filter
 * @author 芮浩
 * @date 2016-6-4
 *
 */
public class SaveRequestLogFiler implements Filter{

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String uri = request.getRequestURI();
		if((uri.indexOf("/streambank/js")!=-1)||(uri.indexOf("/strembank/css") != -1)||(uri.indexOf("/strembank/res") != -1)||(uri.indexOf("/strembank/image") != -1)){
			chain.doFilter(request, res);
		}else{
			String uriParam = request.getQueryString();
			Log4jUtil.LOG4J.info(uri+(uriParam==null?"":"?"+uriParam));
			chain.doFilter(request, res);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
