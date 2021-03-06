package com.cwc.web.ypzj.control.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cwc.web.ypzj.common.util.LogUtil;
import com.cwc.web.ypzj.model.DAO.LabelRepository;
import com.cwc.web.ypzj.model.obj.Label;

/**
 * Servlet Filter implementation class PageDataFilter
 */
@WebFilter(description = "deal with the display of page data",filterName = "PageDataFilter",urlPatterns = {"/*"})
public class PageDataFilter implements Filter {

    /**
     * Default constructor. 
     */
    public PageDataFilter() {
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
		HttpServletRequest httpServletRequest=(HttpServletRequest)request;


		HttpSession session=httpServletRequest.getSession();
		if(session.getAttribute("labelList")==null)
		{
			List<Label> allLabelList=LabelRepository.getAllLabels();
			session.setAttribute("labelList", allLabelList);
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
