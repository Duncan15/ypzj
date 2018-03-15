<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<% String userName=request.getParameter("userName"); %>
<div class="user-list-card side-bar-card bcw fcb fsm clearfix">
  <div class="col-xs-6">
  	<div class="item card-top fwb"><%=userName %>的关注
  		<a>......</a>
  	</div>
  	<div class="item card-content">
    		<div class="user-list fwn">
      		<div class="item user">
        			<a class="article-title fcl">cwc</a>
      		</div>
      		<div class="item user">
        			<a class="article-title fcl">hwt</a>
      		</div>
      		<div class="item user">
        			<a class="article-title fcl">ysw</a>
      		</div>
      		<div class="item user">
        			<a class="article-title fcl">zsy</a>
      		</div>
    		</div>
  	</div>
  </div>
  <div class="col-xs-6">
  	<div class="item card-top fwb">关注<%=userName %>
  		<a>......</a>
  	</div>
  	<div class="item card-content">
    		<div class="user-list fwn">
      		<div class="item user">
        			<a class="article-title fcl">cwc</a>
      		</div>
      		<div class="item user">
        			<a class="article-title fcl">hwt</a>
      		</div>
      		<div class="item user">
        			<a class="article-title fcl">ysw</a>
      		</div>
      		<div class="item user">
        			<a class="article-title fcl">zsy</a>
      		</div>
    		</div>
  	</div>
  </div>
</div>
