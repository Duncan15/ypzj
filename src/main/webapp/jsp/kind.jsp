<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <%@include file="/WEB-INF/jsp/part/head.jsp" %>
  <link rel="stylesheet" type="text/css" href="css/main.css">
  <title>一偏之荐</title>
</head>
<body>
  <%long labelId=0;
      	try{
      		labelId=Long.parseLong(request.getParameter("kindId"));
      	}
      	catch(Exception e){
      		request.setAttribute("reason", "kindID格式错误。");
      		throw new Exception();
      	}
  %>
  <jsp:include page="/WEB-INF/jsp/part/navbar.jsp">
  	<jsp:param name="labelId" value="<%=labelId %>"/>
  </jsp:include>
  <div class="main-panel container">
    <div class="col-xs-9">
      <jsp:include page="/WEB-INF/jsp/part/article-line.jsp">
        <jsp:param name="public" value="true"/>
        <jsp:param name="labelName" value="最新推荐"/>
        <jsp:param name="labelId" value="<%=labelId%>" />
        <jsp:param name="sortAttr" value="new" />
        <jsp:param name="articleNum" value="3" />
      </jsp:include>
      <jsp:include page="/WEB-INF/jsp/part/article-line.jsp">
        <jsp:param name="public" value="true"/>
        <jsp:param name="labelName" value="最热推荐"/>
        <jsp:param name="labelId" value="<%=labelId%>" />
        <jsp:param name="sortAttr" value="support" />
        <jsp:param name="articleNum" value="3" />
      </jsp:include>
    </div>
    <div class="col-xs-3 side-bar">
      <%@include file="/WEB-INF/jsp/part/anounce-board.jsp" %>
    </div>
  </div>
  <%@include file="/WEB-INF/jsp/part/footer.jsp" %>
</body>
</html>
