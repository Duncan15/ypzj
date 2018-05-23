package com.cwc.web.ypzj.common.util.mailservice;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class LocalAuthenticator extends Authenticator {
    private String username;
    private String password;//这里的password是验证码

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }
}
