package com.cwc.web.ypzj.control.api.apis;

import com.cwc.web.ypzj.common.util.JsonUtil;
import com.cwc.web.ypzj.control.api.format.format.Errno;
import com.cwc.web.ypzj.control.api.format.req.JsonRequest;
import com.cwc.web.ypzj.control.api.format.resp.RespWrapper;
import com.cwc.web.ypzj.model.DAO.FriendLinkRepository;
import com.cwc.web.ypzj.model.obj.FriendLink;
import com.cwc.web.ypzj.model.obj.Link;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "FriendLinkAPI",urlPatterns = {"/api/admin/friendLink"})
public class FriendLinkAPI extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonRequest jsRequest=new JsonRequest(request,0);
        Map<String,Object> jsonMap= JsonUtil.readStirng2ObjectMap(jsRequest.getJsonBody());
        String intro=(String)jsonMap.get("intro");
        String link=(String)jsonMap.get("link");
        Link innerLink=new Link(null,intro,link,null);
        FriendLink friendLink=new FriendLink(null,innerLink);
        boolean ans=FriendLinkRepository.addNewLink(friendLink);
        if(!ans){
            RespWrapper.failReturn(response, Errno.SYSERR);
        }else {
            Map<String,String> ansMap=new HashMap<>();
            ansMap.put("newLink",intro);
            RespWrapper.successReturn(response,ansMap);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
