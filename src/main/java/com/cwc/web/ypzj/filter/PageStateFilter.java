package com.cwc.web.ypzj.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.cwc.web.ypzj.DAO.UserRepository;
import com.cwc.web.ypzj.servletObj.User;

/**
 * Servlet Filter implementation class PageStateFilter
 */
@WebFilter(description = "deal with whether display the user status")
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
		User user=(User)hsrt.getSession().getAttribute("currentUser");
		if(user!=null)
		{
			String account=user.getAccount();
			String passwordMD5=user.getPasswordMD5();
			User trueUser=UserRepository.getUserByAccount(account);
			//check validation here
			if(trueUser==null)
			{
				request.setAttribute("reason", "账号不存在");
				try {
					throw new Exception();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(!trueUser.getPasswordMD5().equals(passwordMD5))
			{
				request.setAttribute("reason", "密码错误");
				try {
					throw new Exception();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
