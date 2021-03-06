package com.cwc.web.ypzj.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Enumeration;

public class LogUtil {
    public static Logger logger= LoggerFactory.getLogger("ROOT");
    public static Logger dbLogger=LoggerFactory.getLogger("com.cwc.web.ypzj.model");
    public static HttpRequestTracer getRequestTracer(){
        return new HttpRequestTracer();
    }
    public static class HttpRequestTracer{
        private long duration;
        private String api;
        public HttpRequestTracer(){
            this.duration=new Date().getTime();
        }
        public void headerRecord(HttpServletRequest request){
            StringBuffer stringBuffer=request.getRequestURL();
            if(request.getQueryString()!=null){
                stringBuffer.append("?"+request.getQueryString());
            }
            api=stringBuffer.toString();
            Enumeration<String> enumeration=request.getHeaderNames();
            stringBuffer=new StringBuffer();
            while(enumeration.hasMoreElements()){
                String headerName=enumeration.nextElement();
                String headerValue=request.getHeader(headerName);
                stringBuffer.append(headerName+"="+headerValue+"\t");
            }
            String ip=request.getRemoteAddr();
            if(request.getHeader("x-forwarded-for")!=null){
                ip=request.getHeader("x-forwarded-for");
            }
            LogUtil.logger.info("visit {} {} {} visitor={}",request.getMethod(),api,stringBuffer.toString(),ip);
        }
        public void leaveRecord(){
            duration=(new Date().getTime()-duration);
            LogUtil.logger.info("leave {} duration={}",api,duration);
        }
    }
}

