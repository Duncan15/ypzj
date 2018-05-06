<%@ page import="com.cwc.web.ypzj.model.obj.User" %>
<%@ page import="com.cwc.web.ypzj.model.DAO.UserRepository" %>
<%@ page import="com.cwc.web.ypzj.util.RequestValidator" %><%--
  Created by IntelliJ IDEA.
  User: cwc
  Date: 2018/4/12
  Time: 下午9:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%  String mode= RequestValidator.validateIdRequest(request);
    Long authorId=null;
    Long pageNum=null;
    try{
        authorId=Long.parseLong(request.getParameter("id"));
    }catch (NumberFormatException e){
        request.setAttribute("reason","id格式错误");
        throw new ServletException();
    }

    if(request.getParameter("pageNum")==null){
        pageNum=1l;
    }else {
        try {
            pageNum=Long.parseLong(request.getParameter("pageNum"));
        }catch (NumberFormatException e){
            request.setAttribute("reason","page格式错误");
            throw new ServletException();
        }
    }
    User user= UserRepository.getUserById(authorId);

%>
<html>
<head>
    <%@include file="/WEB-INF/jsp/part/head.jsp" %>
    <link rel="stylesheet" type="text/css" href="css/total.css">
    <title>一偏之荐</title>
</head>
<body>
    <jsp:include page="/WEB-INF/jsp/part/navbar.jsp"/>
    <div class="main-panel container">
        <div class="info-bar" >
            <h2 class="fwn"><%=user.getUserName()%>的文章</h2>
        </div>
        <div class="col-lg-8">
            <jsp:include page="/WEB-INF/jsp/part/article-list.jsp">
                <jsp:param name="authorId" value="<%=authorId%>"/>
                <jsp:param name="curNum" value="<%=pageNum%>" />
            </jsp:include>
        </div>
        <div class="col-xs-4">
            <jsp:include page="/WEB-INF/jsp/part/author-message-board.jsp" >
                <jsp:param name="authorId" value="<%=authorId%>" />
            </jsp:include>
            <jsp:include page="/WEB-INF/jsp/part/user-list-board.jsp" />
        </div>
    </div>

    <%@include file="/WEB-INF/jsp/part/footer.jsp" %>
</body>
</html>
