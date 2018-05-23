<%@ page import="com.cwc.web.ypzj.model.DAO.ArticleRepository" %>
<%@ page import="com.cwc.web.ypzj.model.obj.ArticleInfo" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %><%--
  Created by IntelliJ IDEA.
  User: cwc
  Date: 2018/4/12
  Time: 下午10:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%
    Long authorId=Long.parseLong(request.getParameter("authorId"));
    Integer curNum=Integer.parseInt(request.getParameter("curNum"));
    Integer totalNum=(int)ArticleRepository.getCountByAuthorId(authorId).longValue();
    if(totalNum%10==0){
        totalNum=totalNum/10;
        if(totalNum<1)totalNum=1;
    }else {
        totalNum=totalNum/10+1;
    }
    if (curNum>totalNum){
        request.setAttribute("reason","该页面不存在");
    }
    List<ArticleInfo> list=ArticleRepository.findByAuthorIdLimitStartAndNum(authorId,(curNum-1)*10,10);
    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
%>
<div class="article-list list-group" >
    <%
        for(ArticleInfo each:list){
    %>
    <a href="article?id=<%=each.getId()%>" >
        <div class="list-group-item article-list-item" >
            <div class="article-list-item-title fssm fcb fwl">
                <%=each.getArticleName()%>
            </div>
            <div class="article-list-item-content fsm fcn fwn" >
                Spark从核心思想和架构来看，它依然是那个Spark，但是我已经找了一个新的东家。我的年龄不知不觉中又长了两岁，Spark也在大数据领域从 “新贵”变成了“老人”。Spark的版本从0.X.X到2.X.X所用的时间和我的两年基本相同。

                Spark从核心思想和架构来看，它依然是那个Spark，但是我已经找了一个新的东家。我的年龄不知不觉中又长了两岁，Spark也在大数据领域从 “新贵”变成了“老人

            </div>
            <div class="article-list-item-footer fsm fcb fwl clearfix">
                <div class="fl"><%=format.format(each.getCreatedTime())%></div>
                <div class="fl">推荐 <%=each.getSupportedTime()%></div>
            </div>
        </div>
    </a>
    <%
        }
    %>
    <%
        String prefix="total?id=";
        String sufix="&pageNum=";
    %>
    <jsp:include page="/WEB-INF/jsp/part/pagination.jsp">
        <jsp:param name="path" value="<%=prefix+authorId+sufix%>" />
        <jsp:param name="curNum" value="<%=curNum%>" />
        <jsp:param name="totalNum" value="<%=totalNum%>" />
        <jsp:param name="btnNum" value="5" />
    </jsp:include>
</div>
