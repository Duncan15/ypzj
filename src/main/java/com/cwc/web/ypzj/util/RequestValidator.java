package com.cwc.web.ypzj.util;

import com.cwc.web.ypzj.model.obj.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class RequestValidator {
    public static String validateIdRequest(HttpServletRequest request)throws ServletException{
        String mode="";
        String authorId=request.getParameter("id");//当外部访问时
        User usr=(User) request.getSession(true).getAttribute("currentUser");//当内部访问时
        if(authorId==null&&usr==null) {
            request.setAttribute("reason", "无法处理请求");
            throw new ServletException();
        }else if(usr!=null) {
            mode = "user";
        }else {
            mode = "visitor";
        }
        return mode;
    }
}
