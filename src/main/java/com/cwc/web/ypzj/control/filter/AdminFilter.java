package com.cwc.web.ypzj.control.filter;

import com.cwc.web.ypzj.common.util.LogUtil;
import com.cwc.web.ypzj.common.util.RequestValidator;
import com.cwc.web.ypzj.control.api.format.format.Errno;
import com.cwc.web.ypzj.control.api.format.resp.RespWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "AdminFilter",urlPatterns = {"/admin/*","/api/admin/*"})
public class AdminFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest hsrt=(HttpServletRequest)req;
        HttpServletResponse hsrp=(HttpServletResponse)resp;
        HttpSession session=hsrt.getSession();
        if(session!=null){
            Object adminMode=session.getAttribute("adminMode");
            if(adminMode!=null&&(boolean)session.getAttribute("adminMode")==true){
                LogUtil.logger.info("legal administrator login");
                chain.doFilter(req, resp);
                return;
            }
        }
        LogUtil.logger.warn("invalid administrator try to login");
        if(RequestValidator.validateApiRequest(hsrt)){
            RespWrapper.failReturn(hsrp, Errno.NOAUTHORITY);
            return;
        }else {
            hsrp.sendRedirect(hsrt.getContextPath().concat("/login"));
            return;
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
