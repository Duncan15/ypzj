package com.cwc.web.ypzj.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cwc.web.ypzj.db.DAO.UserRepository;
import com.cwc.web.ypzj.db.dbObj.User;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet(description = "deal with the register operation")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setAttribute("targetPage", "register");
		RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/jsp/validate.jsp");
		dispatcher.forward(request, response);
	}
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String account=request.getParameter("account");//the front end must promise here is not null
		String password=request.getParameter("password");
		String userName=request.getParameter("user-name");
		if(UserRepository.getUserByAccount(account)!=null)
		{
			request.setAttribute("reason", "账号已存在");
			throw new ServletException();
		}
		User newUser=UserRepository.createNewAccount(account, userName, password);
		if(newUser==null)
		{
			request.setAttribute("reason", "服务器内部错误，注册失败");
			throw new ServletException();
		}
		response.sendRedirect("login");
	}

}
