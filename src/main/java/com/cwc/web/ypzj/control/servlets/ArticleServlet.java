package com.cwc.web.ypzj.control.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
			request.setAttribute("reason", "文章ID格式错误");
			throw new ServletException();
		}
		ArticleInfo targetArticleInfo=ArticleRepository.getArticleInfoById(articleId);
		if(targetArticleInfo==null)
		{
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
			ans.put("reason","post格式错误");
			RespWrapper.failReturn(resp, Errno.PARAMERR);
			return;
		}
		if("".equals(title)||"".equals(content)){
			ans.put("reason","标题或内容不能为空");
			RespWrapper.failReturn(resp, Errno.PARAMERR);
			return;
		}

		User usr= (User) req.getSession().getAttribute("currentUser");
		if(usr==null||usr.getStatus()==0){
			ans.put("reason","无账户信息");
			RespWrapper.failReturn(resp, Errno.PARAMERR);
			return;
		}
		Long authorId=usr.getId();
		Long topLabelId=0l;
		if(!"".equals(labelId)){
			try{
				topLabelId=Long.parseLong(labelId);
			}catch (NumberFormatException e){
				e.printStackTrace();
				ans.put("reason","labelId格式错误");
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
			ans.put("reason","新建文章错误");
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
