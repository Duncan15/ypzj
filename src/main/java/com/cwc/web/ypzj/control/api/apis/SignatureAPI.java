package com.cwc.web.ypzj.control.api.apis;

import com.cwc.web.ypzj.control.api.format.format.Errno;
import com.cwc.web.ypzj.control.api.format.req.JsonRequest;
import com.cwc.web.ypzj.control.api.format.resp.RespWrapper;
import com.cwc.web.ypzj.model.DAO.SignatureRepository;
import com.cwc.web.ypzj.model.obj.Signature;
import com.cwc.web.ypzj.model.obj.User;
import com.cwc.web.ypzj.common.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@WebServlet(name = "SignatureAPI",urlPatterns = {"/api/user/manage/signature"})
public class SignatureAPI extends HttpServlet {
    private static final int SIGNATURE_LEST_LENGTH=3;
    private static final int SIGNATURE_MOST_LENGTH=100;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonRequest jsRequest=new JsonRequest(request,0);
        Map<String,String> jsonMap= JsonUtil.readString2StringMap(jsRequest.getJsonBody());
        String signature=jsonMap.get("signature");
        if(signature==null||signature.length()<SIGNATURE_LEST_LENGTH||signature.length()>SIGNATURE_MOST_LENGTH){
            RespWrapper.failReturn(response, Errno.PARAMERR);
            return;
        }
        HttpSession session=request.getSession();
        User usr=(User)session.getAttribute("currentUser");
        Signature sign=new Signature();
        sign.setContent(signature);
        sign.setAuthorId(usr.getId());
        sign.setCreatedTime(new Date());
        if(SignatureRepository.saveSignature(sign)){
            RespWrapper.successReturn(response,null);
        }else {
            RespWrapper.failReturn(response, Errno.SYSERR);
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
