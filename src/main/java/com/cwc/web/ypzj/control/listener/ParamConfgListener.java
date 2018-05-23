package com.cwc.web.ypzj.control.listener;

import com.cwc.web.ypzj.model.pool.ConnectionPool;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@WebListener()
public class ParamConfgListener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {

    // Public constructor is required by servlet spec
    public ParamConfgListener() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
      /* This method is called when the servlet context is
         initialized(when the Web application is deployed). 
         You can initialize servlet context related data here.
      */
        ServletContext context=sce.getServletContext();
        context.setAttribute("domain",context.getInitParameter("domain"));
        context.setAttribute("appConfigPath",context.getRealPath(context.getInitParameter("appConfigLocation")));
        context.setAttribute("mailConfigPath",context.getRealPath(context.getInitParameter("mailConfigLocation")));
        context.setAttribute("adminConfigPath",context.getRealPath(context.getInitParameter("adminConfigLocation")));
        context.setAttribute("mailTmplPath",context.getRealPath(context.getInitParameter("mailContentPath")));
        Properties prop=new Properties();
        try{
            String externalLocation=context.getInitParameter("externalConfigLocation");
            prop.load(new FileInputStream(new File(externalLocation)));
            context.setAttribute("domain",prop.getProperty("config.addr.domain"));
            context.setAttribute("appConfigPath",externalLocation);

            context.setAttribute("mailConfigPath",new URI(externalLocation).resolve(prop.getProperty("config.addr.mail")).getRawPath());
            context.setAttribute("adminConfigPath",new URI(externalLocation).resolve(prop.getProperty("config.addr.admin")).getPath());
        }catch (Exception e){
            e.printStackTrace();
            try{
                prop.load(new FileInputStream(new File((String) context.getAttribute("appConfigPath"))));
            }catch (IOException s){
                s.printStackTrace();
            }
        }

        if("true".equals(prop.getProperty("static.switch"))){
            context.setAttribute("static.href",prop.getProperty("static.href"));
        }
        ConnectionPool.connectionString=prop.getProperty("db.connectionURL");
        ConnectionPool.username=prop.getProperty("db.userName");
        ConnectionPool.password=prop.getProperty("db.password");
        ConnectionPool.poolMaxSize=Integer.parseInt(prop.getProperty("db.poolMaxSize"));
        ConnectionPool.poolMinSize=Integer.parseInt(prop.getProperty("db.poolMinSize"));
    }

    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context 
         (the Web application) is undeployed or 
         Application Server shuts down.
      */

    }

    // -------------------------------------------------------
    // HttpSessionListener implementation
    // -------------------------------------------------------
    public void sessionCreated(HttpSessionEvent se) {
        /* Session is created. */
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        /* Session is destroyed. */
    }

    // -------------------------------------------------------
    // HttpSessionAttributeListener implementation
    // -------------------------------------------------------

    public void attributeAdded(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute 
         is added to a session.
      */
    }

    public void attributeRemoved(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute
         is removed from a session.
      */
    }

    public void attributeReplaced(HttpSessionBindingEvent sbe) {
      /* This method is invoked when an attibute
         is replaced in a session.
      */
    }
}
