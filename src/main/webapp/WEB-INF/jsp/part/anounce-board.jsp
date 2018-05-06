<%@ page import="com.cwc.web.ypzj.model.obj.Brocast" %>
<%@ page import="com.cwc.web.ypzj.model.DAO.BrocastRepository" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<div class="side-bar-card bcw">
  <div class="fsm fcl item">
    你好，欢迎来到<br>一偏之荐<br>一个自由分享与推荐的网站
  </div>
  <div class="announce-title fsm fcb fwb item">
    公告
  </div>
  <div class="announce-content fsm fcl item">
    <%Brocast brocast= BrocastRepository.getNewestBrocast();
      if(brocast!=null){
    %>
    <%=brocast.getContent()%>
    <%}%>
  </div>
</div>
