package com.cwc.web.ypzj.control.api.apis;

import com.cwc.web.ypzj.common.constant.Type;
import com.cwc.web.ypzj.common.util.JsonUtil;
import com.cwc.web.ypzj.control.api.format.format.Errno;
import com.cwc.web.ypzj.control.api.format.req.JsonRequest;
import com.cwc.web.ypzj.control.api.format.resp.RespWrapper;
import com.cwc.web.ypzj.model.DAO.ArticleRepository;
import com.cwc.web.ypzj.model.DAO.UserRepository;
import com.cwc.web.ypzj.model.obj.ArticleInfo;
import com.cwc.web.ypzj.model.obj.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "ContentAPI",urlPatterns = {"/api/admin/article","/api/admin/user"})
public class ContentAPI extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonRequest jsRequest=new JsonRequest(request,1);
        Map<String,Object> jsonMap= JsonUtil.readStirng2ObjectMap(jsRequest.getJsonBody());
        String action=(String)jsonMap.get("action");
        String target=jsRequest.getPathValue()[0];
        if("article".equals(target)){
            String name=(String)jsonMap.get("name");
            String author=(String)jsonMap.get("author");
            int ans=articleManage(name,author,action);

            if(ans!=Errno.SUCCESS){
                RespWrapper.failReturn(response,ans);
            }else {
                RespWrapper.successReturn(response,null);
            }
        }else if("user".equals(target)){
            String name=(String)jsonMap.get("name");
            int ans=userManage(name,action);
            if(ans!=Errno.SUCCESS){
                RespWrapper.failReturn(response,ans);
            }else {
                RespWrapper.successReturn(response,null);
            }
        }

    }
    private int userManage(String name,String action){
        User user=UserRepository.getUserByUserName(name);
        if(user==null)return Errno.NOEXIST;
        if("ban".equals(action)){
            if(user.getStatus()==Type.UserStatus.INVALID.getValue()){
                return Errno.BAN;
            }
            user.setStatus(Type.UserStatus.INVALID);
            if(UserRepository.updateUser(user)){
                return Errno.SUCCESS;
            }else return Errno.SYSERR;
        }else if("unban".equals(action)){
            if(user.getStatus()==Type.UserStatus.VALID.getValue()){
                return Errno.UNBAN;
            }
            user.setStatus(Type.UserStatus.VALID);
            if(UserRepository.updateUser(user)){
                return Errno.SUCCESS;
            }else return Errno.SYSERR;
        }else return Errno.PARAMERR;
    }
    private int articleManage(String name, String author,String action){
        User user=UserRepository.getUserByUserName(author);
        if(user==null)return Errno.NOEXIST;
        ArticleInfo ans=ArticleRepository.getStatus(name,user.getId());
        if(ans==null){
            return Errno.NOEXIST;
        }
        if("ban".equals(action)){
            if(ans.getStatus()==Type.ContentStatus.BAN.getValue()){
                return Errno.BAN;
            }
            if(ArticleRepository.changeStatus(ans.getId(),Type.ContentStatus.BAN.getValue())){
                return Errno.SUCCESS;
            }else {
                return Errno.SYSERR;
            }

        }else if("unban".equals(action)){
            if(ans.getStatus()==Type.ContentStatus.UNBAN.getValue()){
                return Errno.UNBAN;
            }
            if(ArticleRepository.changeStatus(ans.getId(),Type.ContentStatus.UNBAN.getValue())){
                return Errno.SUCCESS;
            }else {
                return Errno.SYSERR;
            }
        }else return Errno.PARAMERR;
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
