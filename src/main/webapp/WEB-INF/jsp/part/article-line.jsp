<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ page import="java.util.List"%>
<%@ page import="com.cwc.web.ypzj.model.DAO.ArticleRepository" %>
<%@ page import="com.cwc.web.ypzj.model.obj.ArticleInfo" %>
<div class="line-list">
  <%
	 String staticHref=request.getServletContext().getAttribute("static.href")==null?"":(String) request.getServletContext().getAttribute("static.href");

  	 String isPublic=request.getParameter("public");
  	 List<ArticleInfo> list=null;
  	 int articleNum=Integer.parseInt(request.getParameter("articleNum"));
	 String sortAttr=request.getParameter("sortAttr");
	 String title=request.getParameter("labelName");
  	 String numAttr="col-xs-"+12/articleNum;
  	 if("true".equals(isPublic)){
  		 long id=Long.parseLong(request.getParameter("labelId"));
  	     if("new".equals(sortAttr))
  	     {
  	       list=ArticleRepository.findByLabelIdOrderByCreatedTime(id,articleNum);
  	     }
  	     else if("support".equals(sortAttr))
  	     {
  	       list=ArticleRepository.findByLabelIdOrderBySupportedTime(id,articleNum);
  	     }
  	     else list=ArticleRepository.findByLabelIdOrderByCreatedTime(id,articleNum);//default
  	 }
  	 else{
  		 Long authorId=Long.parseLong(request.getParameter("authorId"));
  		 if("new".equals(sortAttr)){
  			 list=ArticleRepository.findByAuthorIdOrderByCreatedTime(authorId,articleNum);
  		 }
  		 else if("support".equals(sortAttr)){
  			 list=ArticleRepository.findByAuthorIdOrderBySupportedTime(authorId, articleNum);
  		 }
  		 else list=ArticleRepository.findByAuthorIdOrderByCreatedTime(authorId,articleNum);

  	 }
     if(list.size()<articleNum)articleNum=list.size();
  %>
  <div class="line-title item fcb fwb fsb"><%=title%></div>
  <div class="line-content clearfix tac">
    <%for(int i=0;i<articleNum;i++){
      ArticleInfo ar=list.get(i);
    %>
    <div class="<%=numAttr%>">
      <a class="card" href="article?id=<%=ar.getId()%>">
		<%String avatarId=ar.getAvatarId();
		  if(avatarId==null){%>
		<img src="img/default.png">
		<%}else {%>
        <img src="<%=staticHref%>img/thumbnail/<%=avatarId%>">
		<%}%>
        <div class="card-title fwn fss fcn">
          <%=ar.getArticleName()%>
        </div>
      </a>
    </div>
    <%}%>
  </div>
</div>
