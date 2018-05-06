package com.cwc.web.ypzj.control.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cwc.web.ypzj.model.DAO.UserRepository;
import com.cwc.web.ypzj.model.obj.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(description = "deal with the login operation")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setAttribute("targetPage", "login");
		RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/jsp/validate.jsp");
		dispatcher.forward(request, response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String account=request.getParameter("account");//the front end must promise here is not null
		String password=request.getParameter("password");
		User user=UserRepository.getUserByAccount(account);
		if(user==null)
		{
			request.setAttribute("reason", "账号不存在");
			throw new ServletException();
		}
		if(!password.equals(user.getPasswordMD5()))
		{
			request.setAttribute("reason", "密码错误");
			throw new ServletException();
		}
		HttpSession session=request.getSession();
		session.setMaxInactiveInterval(3600*24);
		session.setAttribute("currentUser", user);
		response.sendRedirect("index");
	}
}