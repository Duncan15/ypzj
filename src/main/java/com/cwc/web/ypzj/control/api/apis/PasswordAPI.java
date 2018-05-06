package com.cwc.web.ypzj.control.api.apis;

import com.cwc.web.ypzj.control.api.format.format.Errno;
import com.cwc.web.ypzj.control.api.format.req.JsonRequest;
import com.cwc.web.ypzj.control.api.format.resp.RespWrapper;
import com.cwc.web.ypzj.model.DAO.UserRepository;
import com.cwc.web.ypzj.model.obj.User;
import com.cwc.web.ypzj.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "PasswordAPI",urlPatterns = {"/user/manage/password"})
public class PasswordAPI extends HttpServlet {
    private static final int PASSWORD_LEST_LENGTH=6;
    private static final int PASSWORD_MOST_LENGTH=9;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonRequest jsRequest=new JsonRequest(request,0);
        Map<String,String> jsonMap= JsonUtil.readString2StringMap(jsRequest.getJsonBody());
        String passwordMD5=jsonMap.get("password");
        if(passwordMD5==null||passwordMD5.length()<PASSWORD_LEST_LENGTH||passwordMD5.length()>PASSWORD_MOST_LENGTH){
            RespWrapper.failReturn(response,Errno.PARAMERR);
            return;
        }
        HttpSession session=request.getSession();
        User usr=(User)session.getAttribute("currentUser");
        usr.setPasswordMD5(passwordMD5);
        if(UserRepository.updateUser(usr)){
            session.setAttribute("currentUser",usr);
            RespWrapper.successReturn(response,null);
        }else {
            RespWrapper.failReturn(response, Errno.SYSERR);
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
