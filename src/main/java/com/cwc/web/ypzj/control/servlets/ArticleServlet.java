package com.cwc.web.ypzj.control.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cwc.web.ypzj.model.DAO.ArticleRepository;
import com.cwc.web.ypzj.model.obj.ArticleInfo;
import com.cwc.web.ypzj.model.obj.User;

/**
 * Servlet implementation class ArticleServlet
 */
@WebServlet(description = "deal with the article display operation")
public class ArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ArticleServlet() {
        super();
        // TODO Auto-generated constructor stub
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
		if(title==null||content==null||labelId==null){
			req.setAttribute("reason","post格式错误");
			throw new ServletException();
		}
		if("".equals(title)||"".equals(content)){
			req.setAttribute("reason","标题或内容不能为空");
			throw new ServletException();
		}

		User usr= (User) req.getSession().getAttribute("currentUser");
		if(usr==null){
			req.setAttribute("reason","无账户信息");
			throw new ServletException();
		}
		Long authorId=usr.getId();
		Long topLabelId=0l;
		if(!"".equals(labelId)){
			try{
				topLabelId=Long.parseLong(labelId);
			}catch (NumberFormatException e){
				e.printStackTrace();
				req.setAttribute("reason","labelId格式错误");
				throw new ServletException();
			}
		}
		HttpSession session=req.getSession();
		String avatarId=null;
		if(session.getAttribute("avatar_id")!=null){
			avatarId=(String) session.getAttribute("avatar_id");
			session.removeAttribute("avatar_id");
		}
		Long articleId=ArticleRepository.addArticle(title,topLabelId,authorId,content,avatarId);
		if(articleId==null){
			req.setAttribute("reason","新建文章错误");
			throw new ServletException();
		}
		resp.getWriter().println(articleId);
	}
}
