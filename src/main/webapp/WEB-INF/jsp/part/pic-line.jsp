<%--
  Created by IntelliJ IDEA.
  User: cwc
  Date: 2018/4/21
  Time: 下午5:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ page import="java.util.List"%>
<div class="line-list">
    <%
        int articleNum=Integer.parseInt(request.getParameter("articleNum"));
        String numAttr="col-xs-"+12/articleNum;

        if(list.size()<articleNum)articleNum=list.size();
    %>
    <div class="line-content clearfix">
        <%for(int i=0;i<articleNum;i++){
            ArticleInfo ar=list.get(i);
        %>
        <div class="<%=numAttr%>">
            <a class="card" href="article?id=<%=ar.getId()%>">
                <%String avatarId=ar.getAvatarId();
                    if(avatarId==null){%>
                <img src="img/default.png">
                <%}else {%>
                <img src="img/<%=avatarId%>">
                <%}%>
                <div class="card-title fwn fss fcn">
                    <%=ar.getArticleName()%>
                </div>
            </a>
        </div>
        <%}%>
    </div>
</div>
