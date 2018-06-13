package com.cwc.web.ypzj.control.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cwc.web.ypzj.common.util.LogUtil;
import com.cwc.web.ypzj.control.api.format.format.Errno;
import com.cwc.web.ypzj.control.api.format.resp.RespWrapper;
import com.cwc.web.ypzj.model.DAO.ArticleRepository;
import com.cwc.web.ypzj.model.obj.ArticleInfo;
import com.cwc.web.ypzj.model.obj.User;

/**
 * Servlet implementation class ArticleServlet
 */
@WebServlet(description = "deal with the article display operation",name = "ArticleServlet",urlPatterns = {"/article"})
public class ArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String staticHref=null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ArticleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
	public void init()throws ServletException{
    	super.init();
		ServletContext context=this.getServletContext();
		if(context.getAttribute("static.href")!=null){
			staticHref=(String)context.getAttribute("static.href");
		}
	}


	/**
	 * @throws IOException 
	 * @throws ServletException 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		long articleId=0;
		try
		{
			articleId=Long.parseLong(request.getParameter("id"));
		}
		catch (NumberFormatException e) 
		{
			// TODO: handle exception
			LogUtil.logger.error("article ID pattern error",e);
			request.setAttribute("reason", "文章ID格式错误");
			throw new ServletException();
		}
		ArticleInfo targetArticleInfo=ArticleRepository.getArticleInfoById(articleId);
		if(targetArticleInfo==null)
		{
			LogUtil.logger.warn("the article pointed to by ID no exist");
			request.setAttribute("reason", "ID所指向的文章不存在");
			throw new ServletException();
		}
		request.setAttribute("ArticleInfo",targetArticleInfo);
		RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/jsp/article.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String title=req.getParameter("title");
		String content=req.getParameter("content");
		String labelId=req.getParameter("labelId");
		Map<String,Object> ans=new HashMap<>();
		if(title==null||content==null||labelId==null){
			LogUtil.logger.warn("post pattern error");
			RespWrapper.failReturn(resp, Errno.PARAMERR);
			return;
		}
		if("".equals(title)||"".equals(content)){
			LogUtil.logger.warn("title or content can't be empty");
			RespWrapper.failReturn(resp, Errno.PARAMERR);
			return;
		}

		User usr= (User) req.getSession().getAttribute("currentUser");
		if(usr==null||usr.getStatus()==0){
			LogUtil.logger.warn("illegal create article action，no account infomation");
			RespWrapper.failReturn(resp, Errno.PARAMERR);
			return;
		}
		Long authorId=usr.getId();
		Long topLabelId=0l;
		if(!"".equals(labelId)){
			try{
				topLabelId=Long.parseLong(labelId);
			}catch (NumberFormatException e){
				LogUtil.logger.error("labelID pattern error",e);
				RespWrapper.failReturn(resp, Errno.PARAMERR);
				return;
			}
		}
		HttpSession session=req.getSession();
		String avatarId=null;
		if(session.getAttribute("avatar_id")!=null){
			avatarId=(String) session.getAttribute("avatar_id");
			session.removeAttribute("avatar_id");
		}

		content=dealArticleContent(content);
		Long articleId=ArticleRepository.addArticle(title,topLabelId,authorId,content,avatarId);
		if(articleId==null){
			LogUtil.logger.error("create article action error");
			RespWrapper.failReturn(resp, Errno.SYSERR);
			return;
		}
		RespWrapper.successReturn(resp,null);
	}
	private String dealArticleContent(String rawContent){
		if(staticHref!=null){
			rawContent=rawContent.replaceAll("<img\\s+?src=\"","<img src=\""+staticHref);
		}
		return rawContent;
	}
}
