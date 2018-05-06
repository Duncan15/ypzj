<%@ page import="com.cwc.web.ypzj.model.obj.User" %>
<%@ page import="com.cwc.web.ypzj.util.RequestValidator" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <%@include file="/WEB-INF/jsp/part/head.jsp" %>
  <link rel="stylesheet" type="text/css" href="css/main.css">
  <link rel="stylesheet" type="text/css" href="css/personal.css">
  <title>一偏之荐</title>
</head>
<body>
  <!--注意：id不在本页使用，因此为解析成String-->
  <%String mode= RequestValidator.validateIdRequest(request);
    String authorId=request.getParameter("id");//当外部访问时
  %>
  <jsp:include page="/WEB-INF/jsp/part/navbar.jsp"/>
  <div class="main-panel container">
    <div class="col-xs-9">
      <div class="top user-name item fcb fwb fsb fssb">cwc</div>
      <div class="nav nav-tabs fwn fcl fsm">
        <li class="active"><a>个人主页</a></li>
      </div>
      <div class="main-page-list">
        <jsp:include page="/WEB-INF/jsp/part/article-line.jsp">
        	  <jsp:param name="public" value="private" />
      	  <jsp:param name="labelName" value="最新文章"/>
          <jsp:param name="sortAttr" value="new" />
          <jsp:param name="articleNum" value="4" />
          <jsp:param name="authorId" value="<%=authorId%>" />

        </jsp:include>
        <jsp:include page="/WEB-INF/jsp/part/article-line.jsp">
        	  <jsp:param name="public" value="private" />
      	  <jsp:param name="labelName" value="最热文章"/>
          <jsp:param name="sortAttr" value="support" />
          <jsp:param name="articleNum" value="4" />
          <jsp:param name="authorId" value="<%=authorId%>" />
        </jsp:include>
      </div>
      <div class="comment-panel">
        <jsp:include page="/WEB-INF/jsp/part/comment-panel.jsp" />
      </div>

    </div>
    <div class="col-xs-3">
      <jsp:include page="/WEB-INF/jsp/part/author-message-board.jsp" >
        <jsp:param name="authorId" value="<%=authorId%>" />
      </jsp:include>
      <jsp:include page="/WEB-INF/jsp/part/article-board.jsp">
      	<jsp:param value="作者全部文章" name="title"/>
        <jsp:param name="mode" value="total" />
        <jsp:param name="authorId" value="<%=authorId%>" />
      </jsp:include>
      <jsp:include page="/WEB-INF/jsp/part/user-list-board.jsp">
      	<jsp:param value="cwc" name="userName"/>
      </jsp:include>
    </div>
  </div>
  <%@include file="/WEB-INF/jsp/part/footer.jsp" %>
</body>
</html>
