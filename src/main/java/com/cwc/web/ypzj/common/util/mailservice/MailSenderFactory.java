package com.cwc.web.ypzj.common.util.mailservice;

public class MailSenderFactory {
    public static enum MailSenderType{
        SIMPLE_MAIL
    }
    public static SimpleMailSender getSimpleMailSender(final String username,final String password){
        return new SimpleMailSender(username,password);
    }
}
