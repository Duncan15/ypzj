<%@ page import="com.cwc.web.ypzj.model.DAO.FriendLinkRepository" %>
<%@ page import="com.cwc.web.ypzj.model.obj.FriendLink" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%
  List<FriendLink> friendLinkList= FriendLinkRepository.getTopNFriendLink(6);
%>

<ul class="list-group">
  <%for(FriendLink each:friendLinkList){%>
  <li class="list-group-item tac">
    <a target="_blank" href="<%=each.getLink().getLink()%>">
      <%=each.getLink().getIntro()%>
    </a>
  </li>
  <%}%>
</ul>