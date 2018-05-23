package com.cwc.web.ypzj.control.servlets;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cwc.web.ypzj.common.util.CryptologyUtil;
import com.cwc.web.ypzj.common.util.mailservice.MailSenderFactory;
import com.cwc.web.ypzj.common.util.multithread.LocalThreadPool;
import com.cwc.web.ypzj.model.DAO.UserRepository;
import com.cwc.web.ypzj.model.obj.User;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet(description = "deal with the register operation",name = "RegisterServlet",urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String domain;
	private transient String mailAccount;
	private transient String mailPassword;
	private transient String encryptKey;
	private String mailContentTMPL="";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	public void init() throws ServletException {
		super.init();
		Properties prop=new Properties();
		ServletContext context=this.getServletContext();
		String mailContentPath=(String) context.getAttribute("mailTmplPath");
		domain=(String)context.getAttribute("domain");
		try{
			prop.load(new FileInputStream(new File((String)context.getAttribute("mailConfigPath"))));
		}catch (IOException e){
			e.printStackTrace();
		}


		try {
			BufferedReader br=null;
			br=new BufferedReader(new InputStreamReader(new FileInputStream(new File(mailContentPath))));

			StringBuffer sb=new StringBuffer();
			while (true){
				String tmp=br.readLine();
				if(tmp==null){
					break;
				}
				sb.append(tmp);
			}
			mailContentTMPL=sb.toString();

			if(br!=null)br.close();
		}catch (IOException e){
			e.printStackTrace();
		}

		//when properties can't be loaded, these attributes would be null
		mailAccount=prop.getProperty("ypzj.mail.account");
		mailPassword=prop.getProperty("ypzj.mail.password");
		encryptKey=prop.getProperty("ypzj.mail.encryptKey");
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
		User newUser=new User(0l,userName,account,password,0l);
		newUser=UserRepository.createNewAccount(newUser);
		if(newUser==null)
		{
			request.setAttribute("reason", "服务器内部错误，注册失败");
			throw new ServletException();
		}
		String token=null;
		try{
			token=CryptologyUtil.aesEncrypt(encryptKey+newUser.getId(),encryptKey);
		}catch (Exception e){
			e.printStackTrace();
			request.setAttribute("reason","不好意思，服务器内部错误～_～");
			throw new ServletException();
		}

		String href=domain+"activation?account="+newUser.getAccount()+"&token="+token;
		String mailContent=mailContentTMPL.replaceAll("\\$\\{\\{\\}\\}",href);
		MailOperation op=new MailOperation(this.mailAccount,this.mailPassword,account,mailContent);
		LocalThreadPool.getInstance().getExecutor().submit(op);

		request.setAttribute("reason","您的账号注册成功，我们已经向您的注册邮箱发送一封激活邮件，请登录邮箱进行激活，该页面5秒后自动跳转。");
		request.setAttribute("target","index");
		RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/jsp/skip-page.jsp");
		dispatcher.forward(request,response);
	}

}
class MailOperation implements Runnable{
	private String account;
	private String password;
	private String recipient;
	private String mailContent;
	public MailOperation(final String senderAccout,final String sendPassword,final String recipient,final String mailContent){
		this.account=senderAccout;
		this.password=sendPassword;
		this.recipient=recipient;
		this.mailContent=mailContent;
	}
	@Override
	public void run() {
		MailSenderFactory.getSimpleMailSender(account,password).send("来自\"一偏之荐\"的邀请",mailContent,recipient);
	}
}
