package com.cwc.web.ypzj.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cwc.web.ypzj.DAO.ArticleRepository;
import com.cwc.web.ypzj.servletObj.ArticleInfo;

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
		String requestUrl=request.getRequestURL().toString();
		long articleId=0;
		try
		{
			articleId=Long.parseLong(requestUrl.substring(requestUrl.indexOf("article/")+8));
		}
		catch (NumberFormatException e) 
		{
			// TODO: handle exception
			request.setAttribute("reason", "文章ID格式错误");
			try {
				throw new Exception();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		ArticleInfo targetArticleInfo=ArticleRepository.getArticleInfoById(articleId);
		if(targetArticleInfo==null)
		{
			request.setAttribute("reason", "ID所指向的文章不存在");
			try {
				throw new Exception();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		request.setAttribute("labelId", targetArticleInfo.getTopLabelId());//for navbar.jsp
		request.setAttribute("ArticleInfo",targetArticleInfo);
		RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/jsp/article.jsp");
		dispatcher.forward(request, response);
	}

}
