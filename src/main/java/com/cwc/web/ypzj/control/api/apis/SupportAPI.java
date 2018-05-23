package com.cwc.web.ypzj.control.api.apis;

import com.cwc.web.ypzj.control.api.format.format.Errno;
import com.cwc.web.ypzj.control.api.format.req.JsonRequest;
import com.cwc.web.ypzj.control.api.format.resp.RespWrapper;
import com.cwc.web.ypzj.model.DAO.ArticleRepository;
import com.cwc.web.ypzj.model.DAO.RecommendRepository;
import com.cwc.web.ypzj.model.obj.ArticleInfo;
import com.cwc.web.ypzj.model.obj.Recommendation;
import com.cwc.web.ypzj.model.obj.User;
import com.cwc.web.ypzj.common.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@WebServlet(name = "SupportAPI",urlPatterns = {"/api/user/support"})
public class SupportAPI extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonRequest jsRequest=new JsonRequest(request,0);
        Map<String,Object> jsonMap= JsonUtil.readStirng2ObjectMap(jsRequest.getJsonBody());
        Integer articleId=(Integer) jsonMap.get("articleId");
        if(articleId==null){
            RespWrapper.failReturn(response, Errno.SYSERR);
            return;
        }
        HttpSession session=request.getSession();
        User usr=(User)session.getAttribute("currentUser");
        Recommendation recommendation=new Recommendation();
        recommendation.setArticleId(articleId);
        recommendation.setAuthorId(usr.getId());
        recommendation.setTime(new Date());
        if(RecommendRepository.addRecommend(recommendation)){
            ArticleInfo article=ArticleRepository.getArticleInfoById((long)articleId);
            RespWrapper.successReturn(response,article);
            return;
        }else {
            RespWrapper.failReturn(response,Errno.SYSERR);
            return;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
