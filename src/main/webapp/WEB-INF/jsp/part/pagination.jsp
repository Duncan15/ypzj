<%@ page import="com.sun.corba.se.spi.activation.LocatorPackage.ServerLocation" %><%--
  Created by IntelliJ IDEA.
  User: cwc
  Date: 2018/4/13
  Time: 上午12:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String path=request.getParameter("path");
   Integer curNum=Integer.parseInt(request.getParameter("curNum"));
   Integer totalNum=Integer.parseInt(request.getParameter("totalNum"));
   Integer btnNum=Integer.parseInt(request.getParameter("btnNum"));
   int halfNum=btnNum/2;
   int startNum=1,endNum=5;
%>
<div class="pagination-tool" >
    <ul class="pagination">

        <%  if(curNum>totalNum||curNum<1)throw new ServletException("页面越界");
            if(totalNum>btnNum){
                startNum=curNum-halfNum;
                endNum=curNum+halfNum;
                if(curNum-halfNum<1){
                    startNum=1;
                    endNum=btnNum;
                }else if(curNum+halfNum>totalNum){
                    startNum=totalNum-btnNum+1;
                    endNum=totalNum;
                }
            }else {
                startNum=1;
                endNum=totalNum;
            }
            if(curNum!=1){
        %>
        <li class="" >
            <a href="<%=path+(curNum-1)%>">Pre</a>
        </li>
        <%  }
            for(int i=startNum,j=endNum;i<=j;i++){

        %>
        <li class="<%=i==curNum?"active":""%>">
            <a href="<%=path+i%>"><%=i%></a>
        </li>
        <%}
            if(curNum!=totalNum){
        %>
        <li class="" >
            <a href="<%=path+(curNum+1)%>" >Next</a>
        </li>
        <%}%>
    </ul>
</div>
