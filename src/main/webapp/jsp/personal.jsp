<%@ page import="com.cwc.web.ypzj.model.obj.User" %>
<%@ page import="com.cwc.web.ypzj.model.DAO.UserRepository" %>
<%@ page import="com.cwc.web.ypzj.common.constant.MessageType" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <%@include file="/WEB-INF/jsp/part/head.jsp" %>
  <link rel="stylesheet" type="text/css" href="node_modules/jqmodal/examples/jqModal.css">
  <link rel="stylesheet" type="text/css" href="css/main.css">
  <link rel="stylesheet" type="text/css" href="css/personal.css">
  <title>一偏之荐</title>
</head>
<body>
  <!--注意：id不在本页使用，因此为解析成String-->
  <%
    String authorId=request.getParameter("id");//当外部访问时
    User usr=null;
        try {
          usr= UserRepository.getUserById(Long.parseLong(authorId));
        }catch (NumberFormatException e){
            request.setAttribute("reason","id格式错误");
        }
  %>
  <jsp:include page="/WEB-INF/jsp/part/navbar.jsp"/>
  <div class="main-panel container">
    <div class="col-xs-9">
      <div class="top user-name item fcb fwb fsb fssb"><%=usr.getUserName()%></div>
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
        <jsp:include page="/WEB-INF/jsp/part/comment-panel.jsp" >
          <jsp:param name="hostId" value="<%=authorId%>" />
          <jsp:param name="messageType" value="<%=MessageType.USER_COMMENT%>" />
        </jsp:include>
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
        <jsp:param name="userId" value="<%=authorId%>" />
      </jsp:include>
    </div>
  </div>
  <%@include file="/WEB-INF/jsp/part/footer.jsp" %>
  <script src="https://cdn.bootcss.com/jqModal/1.4.2/jqModal.min.js"></script>
  <script src="js/template-web.js"></script>
  <script id="comment-list-tmpl" style="text/html">
  {{each $data value index}}
    <div class="comment line">
      <div class="content-info line clearfix ">
        <a href="personal?id={{value.senderId}}" class="fl comment-editor">{{value.senderName}}</a>
        {{ if value.receiverId}}
          <span class="fcl">回复</span>
          <a href="personal?id={{value.receiverId}}" class="comment-editor">{{value.receiverName}}</a>
        {{/if}}
        <div class="fr comment-time">{{value.createdTime}}</div>
      </div>
      <div class="hidden">
        <div class="topCommentId">{{value.topCommentId}}</div>
        <div class="senderId">{{value.senderId}}</div>
        <div class="receiverId">{{value.receiverId}}</div>
      </div>
      <div class="content-main">
        {{value.comment}}
      </div>
      <div class="button-line clearfix">
        <div class="fr btn btn-xs btn-success reply-btn">回复</div>
      </div>
    </div>
  {{/each}}
  </script>
  <script src="js/comment-list.js"></script>
  <script src="js/user-op.js"></script>
</body>
</html>
