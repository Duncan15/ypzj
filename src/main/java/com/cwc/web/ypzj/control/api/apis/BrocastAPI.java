package com.cwc.web.ypzj.control.api.apis;

import com.cwc.web.ypzj.common.util.LogUtil;
import com.cwc.web.ypzj.control.api.format.format.Errno;
import com.cwc.web.ypzj.control.api.format.resp.RespWrapper;
import com.cwc.web.ypzj.model.DAO.BrocastRepository;
import com.cwc.web.ypzj.model.obj.Brocast;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet(name = "Brocast",urlPatterns ={"/api/admin/brocast"} )
public class BrocastAPI extends HttpServlet {
    private ObjectMapper objectMapper=new ObjectMapper();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String content=request.getParameter("content");
        Brocast brocast=new Brocast();
        brocast.setContent(content);
        brocast.setTime(new Date());
        if(BrocastRepository.saveBrocast(brocast)!=null){
            RespWrapper.successReturn(response,null);
        }else {
            LogUtil.logger.error("db error when insert brocast");
            RespWrapper.failReturn(response,Errno.SYSERR);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
