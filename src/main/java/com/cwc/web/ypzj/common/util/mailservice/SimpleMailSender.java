package com.cwc.web.ypzj.common.util.mailservice;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SimpleMailSender {
    private final transient Properties props=new Properties();
    private transient LocalAuthenticator authenticator;
    private transient Session session;
    public SimpleMailSender(final String smtpHostName,final String username,final String password){
        init(smtpHostName,username,password);
    }
    public SimpleMailSender(final String username,final String password){
        //通过邮箱地址解析出smtp服务器，对大多数邮箱都管用
        final String smtpHostName = "smtp." + username.split("@")[1];
        init(smtpHostName,username,password);
    }
    private void init(final String smtpHostName,final String username,final String password){
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", smtpHostName);
        authenticator=new LocalAuthenticator();
        authenticator.setUsername(username);
        authenticator.setPassword(password);
        session = Session.getInstance(props, authenticator);
    }
    public void send(String subject,Object content,String... recipients){
        try {
            final MimeMessage mimeMessage=new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(authenticator.getUsername()));
            mimeMessage.addRecipient(Message.RecipientType.CC,new InternetAddress(authenticator.getUsername()));
            for(String recipient:recipients){
                mimeMessage.addRecipient(Message.RecipientType.TO,new InternetAddress(recipient));
                mimeMessage.setSubject(subject,"utf-8");
                mimeMessage.setContent(content,"text/html;charset=utf-8");
                Transport.send(mimeMessage);
            }
        }catch (MessagingException e){
            e.printStackTrace();
        }

    }
}
