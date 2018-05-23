<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <%@include file="/WEB-INF/jsp/part/head.jsp" %>
  <link rel="stylesheet" type="text/css" href="css/error.css">
  <title>一偏之荐</title>
</head>
<body>
  <jsp:include page="/WEB-INF/jsp/part/navbar.jsp" />
  <div class="container main-panel">
    <div class="panel-top tac fcb fwb fssb">跳转中...</div>
    <div class="content tac fcl fwb fssm"><%=request.getAttribute("reason")%></div>
    <div class="hidden" id="target"><%=request.getAttribute("target")%></div>
  </div>
  <%@include file="/WEB-INF/jsp/part/footer.jsp" %>
  <script type="text/javascript" src="js/skip.js"></script>
</body>
</html>
