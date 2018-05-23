package com.cwc.web.ypzj.control.api.apis;

import com.cwc.web.ypzj.control.api.format.format.Errno;
import com.cwc.web.ypzj.control.api.format.resp.RespWrapper;
import com.cwc.web.ypzj.model.DAO.UserRepository;
import com.cwc.web.ypzj.model.obj.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DetectionAPI",urlPatterns = {"/api/detection","/api/user/detection"})
public class DetectionAPI extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User targetUser=null;
        if(request.getParameter("account")!=null){
            targetUser= UserRepository.getUserByAccount(request.getParameter("account"));
        }else if(request.getParameter("userName")!=null){
            targetUser=UserRepository.getUserByUserName(request.getParameter("userName"));
        }
        if(targetUser==null){
            RespWrapper.failReturn(response, Errno.NOEXIST);
            return;
        }else {
            RespWrapper.failReturn(response,Errno.HASEXIST);
            return;
        }
    }
}
