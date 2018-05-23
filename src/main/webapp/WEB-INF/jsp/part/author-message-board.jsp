<%@ page import="com.cwc.web.ypzj.model.obj.User" %>
<%@ page import="com.cwc.web.ypzj.model.DAO.UserRepository" %>
<%@ page import="com.cwc.web.ypzj.model.obj.Signature" %>
<%@ page import="com.cwc.web.ypzj.model.DAO.SignatureRepository" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%Long authorId=Long.parseLong(request.getParameter("authorId"));
  User author= UserRepository.getUserById(authorId);
  Signature signature= SignatureRepository.getNewestSignature(authorId);
%>
<div class="info-card side-bar-card bcw fcb fsm">
  <div class="item card-top  fwb">作者信息</div>
  <div class="item card-content">
    <div class="hidden" id="authorId">
      <%=authorId%>
    </div>
    <a class="item author-name fwn" href="personal?id=<%=authorId%>"><%=author.getUserName()%></a>
    <div class="item signature fcl fwn"><%=signature.getContent()%></div>
    <div class="item button-line clearfix">
      <div id="attention-btn" class=" btn btn-success btn-xs">关注</div>
      <div class="fr"><%=author.getConcernedTime()%>人</div>
    </div>
  </div>
</div>
