<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ page import="java.util.List"%>
<%@ page import="com.cwc.web.ypzj.model.obj.Label" %>
<%@ page import="com.cwc.web.ypzj.model.obj.User" %>
<%@ page import="com.cwc.web.ypzj.common.util.RequestValidator" %>
<%String prefix=request.getParameter("prefix");
  if(prefix==null){
      prefix="";
  }
%>
<div class="navbar navbar-default bcy navbar-fixed-top">
  <div class="container ">
    <div class="navbar-header">
      <a href="<%=prefix%>index.html" class="navbar-brand"></a>
    </div>
    <ul class="nav navbar-nav">
      <%! boolean checkId(long id,long labelId)
      {
          if(labelId==id) return true;
          else return false;
        }
       %>
       <%List<Label> allLabelList=(List<Label>)session.getAttribute("labelList");
         long labelId=request.getParameter("labelId")==null?-1:Long.parseLong(request.getParameter("labelId"));%>
      <li class="<%=checkId(-1,labelId)?"active-local":""%>">
        <a href="<%=prefix%>index.html">首页</a>
      </li>
      <% for(Label each:allLabelList){ %>
      <li class="<%=checkId(each.getId(),labelId)?"active-local":""%>">
        <a href="<%=prefix%>kind?kindId=<%=each.getId() %>"><%=each.getName() %></a>
      </li>
      <% } %>
    </ul>
    <% User userObj=(User)(session.getAttribute("currentUser"));
       String currentUser=null;
       Long userId=null;
       if(userObj!=null)
       {
         currentUser=userObj.getUserName();
         userId=userObj.getId();
       }
    %>
    <ul class="nav navbar-nav navbar-right">
      <% if(currentUser==null){ %>
      <li>
        <a href="<%=prefix%>login">登录</a>
      </li>
      <li>
        <a href="<%=prefix%>register">注册</a>
      </li>
      <% }else{ %>
      <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" data-target="#" href="#"><%=currentUser %><b class="caret"></b></a>
      	<ul class="dropdown-menu">
      		<li><a href="<%=prefix%>personal?id=<%=userId%>">个人主页</a></li>
      		<li><a href="<%=prefix%>user/edit">新建文章</a></li>
      		<li><a href="<%=prefix%>user/manage">账号管理</a></li>
            <%if(RequestValidator.validateAdminRequst(request)){%>
              <li><a href="<%=prefix%>admin">平台管理</a></li>
            <%}%>
      		<li><a href="<%=prefix%>logout">退出</a></li>

      	</ul>
      </li>
      <% } %>
    </ul>
    <div class="hidden">
      <div id="senderId"><%=userId%></div>
    </div>
  </div>
</div>
