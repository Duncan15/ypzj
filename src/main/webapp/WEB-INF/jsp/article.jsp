<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Date" %>
<%@ page import="com.cwc.web.ypzj.model.DAO.ArticleRepository" %>
<%@ page import="com.cwc.web.ypzj.model.DAO.UserRepository"%>
<%@ page import="com.cwc.web.ypzj.model.obj.ArticleInfo" %>
<%@ page import="com.cwc.web.ypzj.model.obj.ArticleContent"%>
<%@ page import="com.cwc.web.ypzj.model.obj.User"%>
<%@ page import="com.cwc.web.ypzj.model.obj.Label"%>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <%@include file="part/head.jsp" %>
  <link rel="stylesheet" type="text/css" href="css/article.css">
  <title>一偏之荐</title>
</head>
<body>
  <jsp:include page="part/navbar.jsp" />
  <div class="container">
    <%ArticleInfo ai=(ArticleInfo)(request.getAttribute("ArticleInfo"));
      long articleId=ai.getId();
      String articleName=ai.getArticleName();
      Date createdTime=ai.getCreatedTime();
      long supportTime=ai.getSupportedTime();
      long topLabelId=ai.getTopLabelId();
      Long authorId=ai.getAuthorId();
      ArticleContent ac=ArticleRepository.getArticleContentByArticleId(articleId);
      String content=ac.getContent();
      User author=UserRepository.getUserById(authorId);
      String userName=author.getUserName();
      List<Label> allLabelList=(List<Label>)(session.getAttribute("labelList"));
      String topLabelName=null;
      for(Label each:allLabelList)
      {
        if(each.getId()==topLabelId)
        {
          topLabelName=each.getName();
          break;
        }
      }
      SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    %>
    <div class="article-panel col-xs-8">
      <div class="title-line line fcb fwb fssb fssm">
        <%=articleName%>
      </div>
      <div class="info-line line fwn fsm clearfix">
        <a class="fl item editor-name" href="user/personal?id=<%=authorId%>"><%=userName%></a>
        <div class="fl item edit-time fcl"><%=simpleDateFormat.format(ai.getCreatedTime())%></div>
        <div class="fr item label label-info">推荐（<%=supportTime%>）</div>
        <div class="fr item label label-info">评论（111）</div>
      </div>
      <div class="label-line line fwb fss">
        <a href="#" class="label label-success"><%=topLabelName%></a>
      </div>
      <div class="content-line line fcn fwn fsm">
        <%=content %>
      </div>
      <div class="declare-line line float-card item fcl fwn fsm">
        ©️版权声明：本站原创文章，由叶千雯于<span>2017-9-12</span>发表<br>
        转载请联系作者。
      </div>
      <div class="button-line line tac">
        <div class="btn btn-success">推荐</div>
      </div>
      <jsp:include page="part/comment-panel.jsp" />
    </div>

    <div class="col-xs-4 side-bar">
      <jsp:include page="part/author-message-board.jsp">
        <jsp:param name="authorId" value="<%=authorId%>" />
      </jsp:include>
      <jsp:include page="part/article-board.jsp">
      	<jsp:param value="作者最新文章" name="title"/>
        <jsp:param name="mode" value="new" />
        <jsp:param name="authorId" value="<%=authorId%>" />
      </jsp:include>
      <jsp:include page="part/article-board.jsp">
      	<jsp:param value="作者最热文章" name="title"/>
        <jsp:param name="mode" value="hot" />
        <jsp:param name="authorId" value="<%=authorId%>" />
      </jsp:include>
      <jsp:include page="part/user-list-board.jsp">
      	<jsp:param value="<%=authorId %>" name="authorId"/>
      </jsp:include>
    </div>
  </div>
  <%@include file="part/footer.jsp" %>
</body>
</html>
