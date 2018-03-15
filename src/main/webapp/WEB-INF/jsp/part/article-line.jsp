<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ page import="java.util.List"%>
<%@ page import="com.cwc.web.ypzj.DAO.ArticleRepository" %>
<%@ page import="com.cwc.web.ypzj.servletObj.ArticleInfo" %>
<%@ page import="com.cwc.web.ypzj.servletObj.User" %>
<div class="line-list">
  <% 
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
  		 Object userObj=session.getAttribute("currentUser");
  		 if(userObj==null){
  			 request.setAttribute("reason", "账号已注销。");
  			 throw new Exception();
  		 }
  		 User currentUser=(User)userObj;
  		 String userName=currentUser.getUserName();
  		 String authorId=currentUser.getAccount();
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
  <div class="line-content clearfix">
    <%for(int i=0;i<articleNum;i++){
      ArticleInfo ar=list.get(i);
    %>
    <div class="<%=numAttr%>">
      <a class="card" href="article/<%=ar.getId()%>">
        <img src="img/default.png">
        <div class="card-title fwn fss fcn">
          <%=ar.getArticleName()%>
        </div>
      </a>
    </div>
    <%}%>
  </div>
</div>
