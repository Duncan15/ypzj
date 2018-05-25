package com.cwc.web.ypzj.control.api.apis;

import com.cwc.web.ypzj.common.util.JsonUtil;
import com.cwc.web.ypzj.control.api.format.format.Errno;
import com.cwc.web.ypzj.control.api.format.req.JsonRequest;
import com.cwc.web.ypzj.control.api.format.resp.RespWrapper;
import com.cwc.web.ypzj.model.DAO.AttentionRelationRepository;
import com.cwc.web.ypzj.model.DAO.UserRepository;
import com.cwc.web.ypzj.model.obj.AttentionRelation;
import com.cwc.web.ypzj.model.obj.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "AttentionAPI",urlPatterns = {"/api/user/attention"})
public class AttentionAPI extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonRequest jsRequest=new JsonRequest(request,0);
        Map<String,Object> jsonMap= JsonUtil.readStirng2ObjectMap(jsRequest.getJsonBody());
        String targetUserIdStr=(String)jsonMap.get("targetUserId");
        Long targetUserId=Long.parseLong(targetUserIdStr);
        User currentUser=(User)request.getSession().getAttribute("currentUser");
        Long currentUserId= currentUser.getId();
        AttentionRelation attentionRelation=new AttentionRelation();
        attentionRelation.setStartPoint(currentUserId);
        attentionRelation.setEndPoint(targetUserId);
        attentionRelation.setTime(new Date());
        User targetUser=null;
        if((targetUser=UserRepository.getUserById(targetUserId))!=null){
            Boolean exist;
            if((exist=AttentionRelationRepository.isExist(attentionRelation))!=null){
                if(exist==false){
                    if(AttentionRelationRepository.connect(attentionRelation)){
                        Map<String,Object> ansMap=new HashMap<>();
                        ansMap.put("targetUserName",targetUser.getUserName());
                        RespWrapper.successReturn(response,ansMap);
                        return;
                    }
                }else {
                    RespWrapper.failReturn(response,Errno.HASEXIST);
                    return;
                }
            }
        }
        RespWrapper.failReturn(response, Errno.SYSERR);
        return;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
