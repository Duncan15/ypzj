<%@ page import="com.cwc.web.ypzj.model.DAO.AttentionRelationRepository" %>
<%@ page import="java.util.List" %>
<%@ page import="com.cwc.web.ypzj.model.obj.AttentionRelation" %>
<%@ page import="com.cwc.web.ypzj.model.obj.User" %>
<%@ page import="com.cwc.web.ypzj.model.DAO.UserRepository" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%
   String userId=request.getParameter("userId");
   User usr=UserRepository.getUserById(Long.parseLong(userId));
	List<AttentionRelation> attentionList=AttentionRelationRepository.getTopNAttention(Long.parseLong(userId),5);
	List<AttentionRelation> attentionedList=AttentionRelationRepository.getTopNAttentioned(Long.parseLong(userId),5);
%>
<div class="user-list-card side-bar-card bcw fcb fsm clearfix">
  <div class="col-xs-6">
  	<div class="item card-top fwb"><%=usr.getUserName() %>的关注
  		<a>......</a>
  	</div>
  	<div class="item card-content">
    		<div class="user-list fwn">
				<%for(int i=0;i<attentionList.size();i++){%>
      		<div class="item user">
					<%
						User user= UserRepository.getUserById(attentionList.get(i).getEndPoint());
					%>
        			<a class="article-title fcl" href="personal?id=<%=user.getId()%>"><%=user.getUserName()%></a>
      		</div>
				<%}%>
    		</div>
  	</div>
  </div>
  <div class="col-xs-6">
  	<div class="item card-top fwb">关注<%=usr.getUserName() %>
  		<a>......</a>
  	</div>
  	<div class="item card-content">
    		<div class="user-list fwn">
				<%for(int i=0;i<attentionedList.size();i++){%>
      		<div class="item user">
				<%
					User user=UserRepository.getUserById(attentionedList.get(i).getStartPoint());
				%>
        			<a class="article-title fcl" href="personal?id=<%=user.getId()%>"><%=user.getUserName()%></a>
      		</div>
				<%}%>
    		</div>
  	</div>
  </div>
</div>
