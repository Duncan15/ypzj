<%@ page import="java.util.List" %>
<%@ page import="com.cwc.web.ypzj.model.obj.FriendLink" %>
<%@ page import="com.cwc.web.ypzj.model.DAO.FriendLinkRepository" %><%--
  Created by IntelliJ IDEA.
  User: cwc
  Date: 2018/6/4
  Time: 下午11:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%
    List<FriendLink> friendLinkList= FriendLinkRepository.getTopNFriendLink(6);
%>
<div class="fwl fcl friend-link-panel">
    <% if(friendLinkList.size()>0){%>
    <div class="title">
        欢迎访问
    </div>
    <%for(FriendLink each:friendLinkList){%>
    <div class="col-sm-4 item tac">
        <a target="_blank" href="<%=each.getLink().getLink()%>">
            <%=each.getLink().getIntro()%>
        </a>
    </div>
    <%}%>
    <%}%>
</div>
