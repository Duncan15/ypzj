<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <%@include file="part/head.jsp" %>
  <link rel="stylesheet" type="text/css" href="css/validate.css">
  <title>一偏之荐</title>
</head>
<body>
  <jsp:include page="part/navbar.jsp" />
  <div class="validate-panel">
    <div class="container validate-container">
      <% if(request.getAttribute("targetPage").equals("register")) { %>
      <div class="header fwb fsb fssm ">
        <a href="login">登录</a> /
        <a href="javascript:return false" class="fcb">注册</a>
      </div>
      <% } %>
    	  <% if(request.getAttribute("targetPage").equals("login")) { %>
      <div class="header fwb fsb fssm ">
        <a href="javascript:return false" class="fcb">登录</a> /
        <a href="register" class="">注册</a>
      </div>
      <% } %>
      <% if(request.getAttribute("targetPage").equals("login")) { %>
      <form id="login-form" action="login" method="post" class="form-horizontal">
        <div class="form-group validate-group">
          <label class="col-xs-2 control-label">账号</label>
          <div class="col-xs-10">
            <input type="text" name="account" class="form-control" placeholder="请输入账号">
          </div>
        </div>
        <div class="form-group validate-group">
          <label class="col-xs-2 control-label">密码</label>
          <div class="col-xs-10">
            <input type="password" name="password" class="form-control">
          </div>
        </div>
        <div class="form-group tac">
          <button type="submit" class="btn btn-success">登录</button>
        </div>
      </form>
      <% } %>
      <% if(request.getAttribute("targetPage").equals("register")) { %>
      <form id="register-form" action="register" method="post" class="form-horizontal">
        <div class="form-group validate-group">
          <label class="col-xs-2 control-label">邮箱</label>
          <div class="col-xs-10">
            <input id="new-email" type="email" name="account" class="form-control" placeholder="请使用常用邮箱作为账号唯一标识">
            <div class="validate-alarm" id="email-alarm"></div>
          </div>
        </div>
        <div class="form-group validate-group">
          <label class="col-xs-2 control-label">昵称</label>
          <div class="col-xs-10">
            <input id="new-username" type="text" name="user-name" class="form-control" placeholder="请为自己取一个好听的昵称">
            <div class="validate-alarm" id="nickname-alarm"></div>
          </div>
        </div>
        <div class="form-group validate-group">
          <label class="col-xs-2 control-label">密码</label>
          <div class="col-xs-10">
            <input id="new-password" type="password" name="password" class="form-control">
            <div class="validate-alarm" id="password-alarm"></div>
          </div>
        </div>
        <div class="form-group tac">
          <button type="submit" class="btn btn-success" id="register-btn" onclick="register(event)">注册</button>
        </div>
      </form>
      <% } %>
    </div>
  </div>
  <%@include file="part/footer.jsp" %>
  <script type="text/javascript" src="js/validate.js"></script>
</body>
</html>
