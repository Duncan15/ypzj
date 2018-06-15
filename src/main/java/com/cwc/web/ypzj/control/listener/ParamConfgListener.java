package com.cwc.web.ypzj.control.listener;

import com.cwc.web.ypzj.common.util.LogUtil;
import com.cwc.web.ypzj.model.pool.ConnectionPool;
import org.apache.log4j.xml.DOMConfigurator;

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
public class ParamConfgListener implements ServletContextListener {

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
        /*
        本地部署情况下，在web.xml的context-param中配置配置文件路径，并在下面的代码块中load进文件
        线上环境中，将配置文件路径配置在web.xml的externalConfigLocation所指向的文件中，并在下面的try-catch代码块中load进文件
         */
        ServletContext context=sce.getServletContext();
        context.setAttribute("domain",context.getInitParameter("domain"));
        context.setAttribute("appConfigPath",context.getRealPath(context.getInitParameter("appConfigLocation")));
        context.setAttribute("mailConfigPath",context.getRealPath(context.getInitParameter("mailConfigLocation")));
        context.setAttribute("adminConfigPath",context.getRealPath(context.getInitParameter("adminConfigLocation")));
        context.setAttribute("mailTmplPath",context.getRealPath(context.getInitParameter("mailContentPath")));
        context.setAttribute("log4jConfigPath",context.getRealPath(context.getInitParameter("log4jConfigLocation")));


        Properties prop=new Properties();
        String mode="online";
        try{//本地部署情况下，此处报错
            String externalLocation=context.getInitParameter("externalConfigLocation");
            prop.load(new FileInputStream(new File(externalLocation)));
            context.setAttribute("domain",prop.getProperty("config.addr.domain"));
            context.setAttribute("appConfigPath",externalLocation);

            context.setAttribute("mailConfigPath",new URI(externalLocation).resolve(prop.getProperty("config.addr.mail")).getRawPath());
            context.setAttribute("adminConfigPath",new URI(externalLocation).resolve(prop.getProperty("config.addr.admin")).getPath());
            context.setAttribute("log4jConfigPath",new URI(externalLocation).resolve(prop.getProperty("config.addr.log4j")).getPath());
        }catch (Exception e){
            try{
                prop.load(new FileInputStream(new File((String) context.getAttribute("appConfigPath"))));
                mode="dev";
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
        DOMConfigurator.configure((String) context.getAttribute("log4jConfigPath"));
        LogUtil.logger.info("start up in "+mode+" mode ");
    }

    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context 
         (the Web application) is undeployed or 
         Application Server shuts down.
      */

    }
}
