package com.cwc.web.ypzj.control.api.format.resp;

import com.cwc.web.ypzj.control.api.format.format.Errno;
import com.cwc.web.ypzj.control.api.format.format.RespStruct;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RespWrapper {
    private static ObjectMapper objectMapper=new ObjectMapper();
    public static void successReturn(HttpServletResponse resp, Object data){
        RespStruct ans=new RespStruct(data, Errno.SUCCESS);
        Return(resp,ans);
    }
    public static void failReturn(HttpServletResponse resp,int errno){
        RespStruct ans=new RespStruct(null,errno);
        Return(resp,ans);
    }
    private static void Return(HttpServletResponse resp,RespStruct struct){
        resp.setContentType("text/json");
        /*设置字符集为'UTF-8'*/
        resp.setCharacterEncoding("UTF-8");
        try {
            objectMapper.writeValue(resp.getWriter(),struct);
        }catch (IOException e){
            e.printStackTrace();
            return;
        }
    }
}
