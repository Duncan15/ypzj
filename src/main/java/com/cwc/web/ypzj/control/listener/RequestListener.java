package com.cwc.web.ypzj.control.listener;

import com.cwc.web.ypzj.common.util.LogUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;
import java.io.IOException;

@WebListener()
public class RequestListener implements ServletRequestListener {

    // Public constructor is required by servlet spec
    public RequestListener() {
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        ServletRequest servletRequest=sre.getServletRequest();
        if(servletRequest instanceof HttpServletRequest){
            HttpServletRequest httpServletRequest=(HttpServletRequest)servletRequest;
            LogUtil.HttpRequestTracer tracer=(LogUtil.HttpRequestTracer)httpServletRequest.getAttribute("tracer");
            tracer.leaveRecord();
        }
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        ServletRequest servletRequest=sre.getServletRequest();
        try {
            servletRequest.setCharacterEncoding("utf-8");
        }catch (IOException e){
            LogUtil.logger.error("set servlet character encoding error",e);
        }
        if (servletRequest instanceof HttpServletRequest){
            HttpServletRequest httpServletRequest=(HttpServletRequest)servletRequest;
            //record the request trace
            LogUtil.HttpRequestTracer tracer=LogUtil.getRequestTracer();
            tracer.headerRecord(httpServletRequest);
            httpServletRequest.setAttribute("tracer",tracer);
        }

    }
}
