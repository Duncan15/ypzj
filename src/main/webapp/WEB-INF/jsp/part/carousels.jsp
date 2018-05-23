<%@ page import="com.cwc.web.ypzj.model.DAO.CarouselRepository" %>
<%@ page import="java.util.List" %>
<%@ page import="com.cwc.web.ypzj.model.obj.Carousel" %><%--
  Created by IntelliJ IDEA.
  User: cwc
  Date: 2018/5/7
  Time: 下午4:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
    require param
    num
--%>
<%
    String staticHref=request.getServletContext().getAttribute("static.href")==null?"":(String) request.getServletContext().getAttribute("static.href");

    int num=Integer.parseInt(request.getParameter("num"));
    List<Carousel> carousels=CarouselRepository.getCarousels(num);
    if(carousels.size()==0){
%>
<div class="slider">
    <img src="img/default.jpg" alt="轮播图片">
</div>
<%}else{%>
<div id="slider" class="carousel slide">
    <!-- 轮播（Carousel）指标 -->
    <ol class="carousel-indicators">
        <%for(int i=0;i<carousels.size();i++){%>
        <li data-target="#slider" data-slide-to="<%=i%>" class="<%=i==0?"active":""%>"></li>
        <%}%>
    </ol>
    <!-- 轮播（Carousel）项目 -->
    <div class="carousel-inner">
        <%for(int i=0;i<carousels.size();i++){%>
        <div class="item <%=i==0?"active":""%>" style="padding: 0 0;">
            <img src="<%=staticHref%>img/<%=carousels.get(i).getImageName()%>" style="height: 500px;margin: 0 auto;" alt="<%=i%> slide">
        </div>
        <%}%>
    </div>
    <!-- 轮播（Carousel）导航 -->
    <a class="left carousel-control" href="#slider" role="button" data-slide="prev">
        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
    </a>
    <a class="right carousel-control" href="#slider" role="button" data-slide="next">
        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
    </a>
</div>
<%}%>