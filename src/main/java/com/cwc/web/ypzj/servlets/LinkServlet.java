package com.cwc.web.ypzj.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cwc.web.ypzj.DAO.LinkRepository;
import com.cwc.web.ypzj.jsonObj.LinkResponser;
import com.cwc.web.ypzj.servletObj.Link;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class LinkServlet
 */
public class LinkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LinkServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        String intro=request.getParameter("intro");
        String link=request.getParameter("link");
        Link linkObj=LinkRepository.createLink(intro, link);
        LinkResponser responser=null;
        if(linkObj!=null) {
        		responser=new LinkResponser(1, "add success",linkObj.getId());
        }
        else {
        		responser=new LinkResponser(0, "add failed", null);
        }
        ObjectMapper mapper=new ObjectMapper();
        String ans=mapper.writeValueAsString(responser);
        System.out.println(ans);
        out.println(ans);
	}

}
