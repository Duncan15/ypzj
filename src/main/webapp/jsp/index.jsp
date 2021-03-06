<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ page import="java.util.List"%>
<%@ page import="com.cwc.web.ypzj.model.obj.Label" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <%@include file="/WEB-INF/jsp/part/head.jsp" %>
  <link rel="stylesheet" type="text/css" href="css/main.css">
  <title>一偏之荐</title>
</head>
<body>
  <jsp:include page="/WEB-INF/jsp/part/navbar.jsp"/>
  <div class="top-panel container clearfix">
    <div class="col-xs-9">
      <jsp:include page="/WEB-INF/jsp/part/carousels.jsp">
        <jsp:param name="num" value="3"/>
      </jsp:include>
    </div>
    <div class="col-xs-3">
      <%@include file="/WEB-INF/jsp/part/anounce-board.jsp" %>
    </div>
  </div>
  <div class="main-panel container">
    <%List<Label> allLabelList=(List<Label>)session.getAttribute("labelList");%>
    <%for(int i=0;i<allLabelList.size();i++){%>
    <jsp:include page="/WEB-INF/jsp/part/article-line.jsp">
    	  <jsp:param name="public" value="true" />
      <jsp:param name="labelName" value="<%=allLabelList.get(i).getName()%>"/>
      <jsp:param name="labelId" value="<%=allLabelList.get(i).getId()%>" />
      <jsp:param name="sortAttr" value="new" />
      <jsp:param name="articleNum" value="4" />
    </jsp:include>
    <%}%>
    <jsp:include page="/WEB-INF/jsp/part/friend-link-panel.jsp">
      <jsp:param name="" value=""/>
    </jsp:include>

  </div>

  <%@include file="/WEB-INF/jsp/part/footer.jsp" %>
</body>
</html>
