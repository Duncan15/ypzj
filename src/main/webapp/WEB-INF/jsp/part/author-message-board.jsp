<%@ page import="com.cwc.web.ypzj.db.dbObj.User" %>
<%@ page import="com.cwc.web.ypzj.db.DAO.UserRepository" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%Long authorId=Long.parseLong(request.getParameter("authorId"));
  User author= UserRepository.getUserById(authorId);%>
<div class="info-card side-bar-card bcw fcb fsm">
  <div class="item card-top  fwb">作者信息</div>
  <div class="item card-content">
    <a class="item author-name fwn" href="user/personal?id=<%=authorId%>"><%=author.getUserName()%></a>
    <div class="item signature fcl fwn">人生若只如初见</div>
    <div class="item button-line clearfix">
      <div class=" btn btn-success btn-xs">关注</div>
      <div class="fr">111人</div>
    </div>
  </div>
</div>
