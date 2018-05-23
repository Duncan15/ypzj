package com.cwc.web.ypzj.control.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
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
@WebServlet(description = "deal with the login operation",name = "LoginServlet",urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String[] adminAcountArray=null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	@Override
	public void init() throws ServletException {
		super.init();
		Properties prop=new Properties();
		ServletContext context=this.getServletContext();
		try {
			prop.load(new FileInputStream(new File((String) context.getAttribute("adminConfigPath"))));
		}catch (IOException e){
			e.printStackTrace();
		}

		//when properties can't be loaded, these attributes would be null
		String adminAccountListStr=prop.getProperty("admin.accountList");
		adminAcountArray=adminAccountListStr.split(",");
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
		if(user.getStatus()==0){
			request.setAttribute("reason","请先通过邮件激活该账号");
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
		session.setAttribute("adminMode",false);
		for(String each:adminAcountArray){
			if(user.getAccount().equals(each)){
				session.setAttribute("adminMode",true);
				break;
			}
		}
		response.sendRedirect("index");
	}
}