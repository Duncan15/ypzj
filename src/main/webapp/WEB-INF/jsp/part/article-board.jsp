<%@ page import="com.cwc.web.ypzj.model.DAO.ArticleRepository" %>
<%@ page import="java.util.List" %>
<%@ page import="com.cwc.web.ypzj.model.obj.ArticleInfo" %>
<%@ page import="com.cwc.web.ypzj.model.obj.User" %>
<%@ page import="com.cwc.web.ypzj.model.DAO.UserRepository" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<% String title=request.getParameter("title");
   String mode=request.getParameter("mode");
   Long authorId=Long.parseLong(request.getParameter("authorId"));
   User author= UserRepository.getUserById(authorId);
   List<ArticleInfo> list=null;
   if("total".equals(mode)){
     list=ArticleRepository.findByAuthorIdLimitStartAndNum(authorId,0,5);
   } else if("new".equals(mode)){
       list=ArticleRepository.findByAuthorIdOrderByCreatedTime(authorId,5);
   } else if("hot".equals(mode)){
       list=ArticleRepository.findByAuthorIdOrderBySupportedTime(authorId,5);
   }

%>

<div class="article-card side-bar-card bcw fcb fsm">
  <div class="item card-top fwb"><%=title %>
    <%if("total".equals(mode)){%>
    <a href="total?id=<%=authorId%>"> ...... </a>
    <%}%>
  </div>
  <div class="item card-content">
    <div class="article-list fwn">
      <%for(ArticleInfo each:list){%>
      <div class="item article">
        <a class="article-title fcl" href="article?id=<%=each.getId()%>"><%=each.getArticleName()%></a>
        <span class="article-recommend">(<%=each.getSupportedTime()%>人推荐)</span>
      </div>
      <%}%>
    </div>
  </div>
</div>
