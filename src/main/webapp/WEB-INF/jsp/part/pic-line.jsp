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
<%@ page import="com.cwc.web.ypzj.model.DAO.CarouselRepository" %>
<%@ page import="com.cwc.web.ypzj.model.obj.Carousel" %>
<div class="line-list">
    <%
        String staticHref=request.getServletContext().getAttribute("static.href")==null?"":(String) request.getServletContext().getAttribute("static.href");
        String mode=request.getParameter("mode");
        int itemNum=Integer.parseInt(request.getParameter("itemNum"));
        String numAttr="col-xs-"+12/itemNum;
        Object list=null;
        if("carousel".equals(mode)){
            list= CarouselRepository.getCarousels(itemNum);
        }
    %>
    <div class="line-content clearfix">
        <%if("carousel".equals(mode)){
            List<Carousel> carouselsList=(List<Carousel>)list;
            if(carouselsList==null){itemNum=0;}
            if(carouselsList.size()<itemNum){itemNum=carouselsList.size();}
            for(int i=0;i<itemNum;i++){
        %>
            <div class="<%=numAttr%>">
                <img src="<%=staticHref%>img/thumbnail/<%=carouselsList.get(i).getImageName()%>">
            </div>
            <%}
        }%>
    </div>
</div>
