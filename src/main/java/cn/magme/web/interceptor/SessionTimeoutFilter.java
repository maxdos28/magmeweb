/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.interceptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author shenhao
 * @date 2011-8-11
 * @version $id$
 */
public class SessionTimeoutFilter implements Filter{
	public void destroy() {  
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest servletRequest = (HttpServletRequest)request;  
		HttpServletResponse servletResponse = (HttpServletResponse)response;  
		HttpSession session = servletRequest.getSession(false);  
		if (session == null || session.getAttribute("session_admin") == null) {  
			if (servletRequest.getHeader("x-requested-with") != null  
			  && servletRequest.getHeader("x-requested-with").equalsIgnoreCase(  
				"XMLHttpRequest")) {  
			    servletResponse.setHeader("sessionstatus", "timeout");  
			}
		}
		chain.doFilter(request, response);
	}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
}
