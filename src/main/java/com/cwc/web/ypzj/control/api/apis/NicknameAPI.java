package com.cwc.web.ypzj.control.api.apis;

import com.cwc.web.ypzj.control.api.format.format.Errno;
import com.cwc.web.ypzj.control.api.format.req.JsonRequest;
import com.cwc.web.ypzj.control.api.format.resp.RespWrapper;
import com.cwc.web.ypzj.model.DAO.UserRepository;
import com.cwc.web.ypzj.model.obj.User;
import com.cwc.web.ypzj.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "NicknameAPI",urlPatterns = {"/user/manage/nickname"})
public class NicknameAPI extends HttpServlet {
    private static final int NAME_LEST_LENGTH=3;
    private static final int NAME_MOST_LENGTH=20;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonRequest jsRequest=new JsonRequest(request,0);
        Map<String,String> jsonMap= JsonUtil.readString2StringMap(jsRequest.getJsonBody());
        String nickname=jsonMap.get("nickname");
        if(nickname==null||nickname.length()<NAME_LEST_LENGTH||nickname.length()>NAME_MOST_LENGTH){
            RespWrapper.failReturn(response, Errno.PARAMERR);
            return;
        }
        HttpSession session=request.getSession();
        User usr=(User)session.getAttribute("currentUser");
        usr.setUserName(nickname);
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
