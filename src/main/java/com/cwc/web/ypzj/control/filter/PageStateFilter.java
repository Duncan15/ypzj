package com.cwc.web.ypzj.control.filter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cwc.web.ypzj.control.api.format.format.Errno;
import com.cwc.web.ypzj.control.api.format.resp.RespWrapper;
import com.cwc.web.ypzj.model.obj.User;
import com.cwc.web.ypzj.common.util.RequestValidator;

/**
 * Servlet Filter implementation class PageStateFilter
 */
@WebFilter(description = "deal with whether display the user status",filterName = "PageStateFilter",urlPatterns = {"/user/*","/api/user/*"})
public class PageStateFilter implements Filter {

    /**
     * Default constructor. 
     */
    public PageStateFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest hsrt=(HttpServletRequest)request;
		HttpServletResponse hsrp=(HttpServletResponse)response;
		User user=(User)hsrt.getSession().getAttribute("currentUser");
		if(user==null||user.getStatus()==0)
		{
			if(RequestValidator.validateApiRequest(hsrt)){
				RespWrapper.failReturn(hsrp, Errno.NOAUTHORITY);
				return;
			}else {
				hsrp.sendRedirect(hsrt.getContextPath().concat("/login"));
				return;
			}
		}
		// pass the request along the filter chain
		chain.doFilter(request, response);
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
