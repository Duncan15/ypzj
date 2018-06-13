package com.cwc.web.ypzj.control.servlets;

import com.cwc.web.ypzj.common.constant.Type;
import com.cwc.web.ypzj.common.util.CryptologyUtil;
import com.cwc.web.ypzj.common.util.LogUtil;
import com.cwc.web.ypzj.model.DAO.UserRepository;
import com.cwc.web.ypzj.model.obj.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Properties;

@WebServlet(name = "ActivateServlet",urlPatterns = {"/activation"})
public class ActivateServlet extends HttpServlet {
    private transient String encryptKey;
    public void init() throws ServletException {
        super.init();
        ServletContext context=this.getServletContext();
        Properties properties=new Properties();

        try {
            properties.load(new FileInputStream(new File((String)context.getAttribute("mailConfigPath"))));
        }catch (IOException e){
            LogUtil.logger.error("error happen when load mainConfigFile at:"+(String)context.getAttribute("mailConfigPath"),e);
        }
        //when properties can't be loaded, these attributes would be null
        encryptKey=properties.getProperty("ypzj.mail.encryptKey");
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String account=request.getParameter("account");
        String token=request.getParameter("token");
        String decryptStr=null;
        try{
            decryptStr=CryptologyUtil.aesDecrypt(token,encryptKey);
        }catch (Exception e){
            LogUtil.logger.error("error happen when decript activate token for account:"+account,e);
            request.setAttribute("reason","激活失败，请重试");
            throw new ServletException();
        }
        Long userId=Long.parseLong(decryptStr.replaceFirst(encryptKey,""));
        User user=UserRepository.getUserByAccount(account);
        if(user==null){
            LogUtil.logger.warn("the account to be activate no exist:"+account);
            request.setAttribute("reason","所要激活的账号不存在");
            throw new ServletException();
        }
        if(user.getStatus()==1){
            LogUtil.logger.warn("the account to be activate has been valid before:"+account);
            request.setAttribute("reason","该账号已被激活过，不可再次激活");
            throw new ServletException();
        }
        if(user.getId()==userId){
            user.setStatus(Type.UserStatus.VALID);
            UserRepository.updateUser(user);
        }else {
            LogUtil.logger.warn("the activate token is invalid："+account);
            request.setAttribute("reason","对不起，激活令牌无效");
            throw new ServletException();
        }
        request.setAttribute("reason","您的账号激活成功，本页面5秒后自动跳转。");
        request.setAttribute("target","login");
        RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/jsp/skip-page.jsp");
        dispatcher.forward(request, response);
    }
}
