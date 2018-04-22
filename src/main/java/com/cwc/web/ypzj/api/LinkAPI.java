package com.cwc.web.ypzj.api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cwc.web.common.util.PathParser;
import com.cwc.web.ypzj.db.DAO.LinkRepository;
import com.cwc.web.ypzj.api.jsonObj.LinkResponser;
import com.cwc.web.ypzj.db.dbObj.Link;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class LinkAPI
 */
public class LinkAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LinkAPI() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");
        String[] param= PathParser.parse(request.getRequestURI(),2);
        if("article".equals(param[1])){
            articleLinkDeal(request,response);
        }
	}
	private void articleLinkDeal(HttpServletRequest request, HttpServletResponse response)throws IOException{
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
