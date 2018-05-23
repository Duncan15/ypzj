package com.cwc.web.ypzj.control.api.apis;

import com.cwc.web.ypzj.common.constant.MessageType;
import com.cwc.web.ypzj.common.constant.Order;
import com.cwc.web.ypzj.common.util.JsonUtil;
import com.cwc.web.ypzj.control.api.format.format.Errno;
import com.cwc.web.ypzj.control.api.format.req.JsonRequest;
import com.cwc.web.ypzj.control.api.format.resp.RespWrapper;
import com.cwc.web.ypzj.model.DAO.CommentRepository;
import com.cwc.web.ypzj.model.DAO.UserRepository;
import com.cwc.web.ypzj.model.obj.Comment;
import com.cwc.web.ypzj.model.obj.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet(name = "CommentAPI",urlPatterns = {"/api/user/comment","/api/comment"})
public class CommentAPI extends HttpServlet {

    private final SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(!request.getRequestURI().contains("user")){
            RespWrapper.failReturn(response,Errno.NOFOUND);
            return;
        }
        JsonRequest jsRequest=new JsonRequest(request,0);
        Map<String,Object> jsonMap= JsonUtil.readStirng2ObjectMap(jsRequest.getJsonBody());
        Integer topCommentId=(Integer) jsonMap.get("topCommentId");
        String comment=(String)jsonMap.get("comment");
        Integer hostId=(Integer)jsonMap.get("hostId");
        Integer senderId=(Integer)jsonMap.get("senderId");
        Integer receiverId=(Integer)jsonMap.get("receiverId");
        Integer messageType=(Integer) jsonMap.get("messageType");
        Comment commentObj=new Comment();
        commentObj.setHostId((long)hostId.intValue());
        commentObj.setSenderId((long)senderId.intValue());
        commentObj.setMessageType((byte)messageType.intValue());
        commentObj.setComment(comment);
        commentObj.setCreatedTime(new Date());
        Long ans=0l;
        if(topCommentId==null){
            //for save topComment
            ans=CommentRepository.saveTopCommnet(commentObj);
        }else {
            //for save secondLevelComment
            commentObj.setTopCommentId((long)topCommentId.intValue());
            commentObj.setReceiverId((long)receiverId.intValue());
            ans=CommentRepository.saveSecondLevelComment(commentObj);
        }
        if(ans==-1||ans==null){
            RespWrapper.failReturn(response, Errno.SYSERR);
            return;
        }else {
            RespWrapper.successReturn(response,null);
            return;
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String messageTypeStr=request.getParameter("messageType");
        String hostIdStr=request.getParameter("hostId");
        String pageCountStr=request.getParameter("pageCount");//非必须
        String pageSizeStr=request.getParameter("pageSize");//非必须
        String topCommentIdStr=request.getParameter("topCommentId");//当需要对话详情时可以只有topCommentId
        byte messageType=0;
        long hostId=0l;
        long topCommentId=0;
        int pageCount=1;
        int pageSize=10;
        List<Map<String,Object>> list=new ArrayList<>();
        List<Comment> dbList=null;
        Map<Long,User> userMap=new HashMap<>();
        if(pageCountStr!=null){
            pageCount=Integer.parseInt(pageCountStr);
        }
        if(pageSizeStr!=null){
            pageSize=Integer.parseInt(pageSizeStr);
        }
        if(topCommentIdStr!=null) {
            topCommentId = Long.parseLong(topCommentIdStr);
            dbList=CommentRepository.getCommentDetails(topCommentId, pageSize, pageCount, Order.desc);
        }else {
            if(messageTypeStr==null||hostIdStr==null){
                RespWrapper.failReturn(response,Errno.PARAMERR);
                return;
            }else {
                messageType=Byte.parseByte(messageTypeStr);
                hostId=Long.parseLong(hostIdStr);
                if(messageType==MessageType.USER_COMMENT){
                    dbList=CommentRepository.getComments(MessageType.USER_COMMENT,hostId,pageSize,pageCount,Order.desc);
                }else if(messageType==MessageType.ARTICLE_COMMENT){
                    dbList=CommentRepository.getComments(MessageType.ARTICLE_COMMENT,hostId,pageSize,pageCount,Order.asc);
                }
            }
        }
        for(Comment each:dbList){
            User sender=userMap.get(each.getSenderId());
            User receiver=userMap.get(each.getReceiverId());
            if(sender==null){
                sender= UserRepository.getUserById(each.getSenderId());
                userMap.put(each.getSenderId(),sender);
            }
            if(receiver==null){
                receiver=UserRepository.getUserById(each.getReceiverId());
                if(receiver==null){
                    receiver=new User();
                }
                userMap.put(each.getReceiverId(),receiver);
            }
            Map<String,Object> ansUnit=new HashMap<>();
            ansUnit.put("senderId",sender.getId());
            ansUnit.put("receiverId",receiver.getId());
            ansUnit.put("senderName",sender.getUserName());
            ansUnit.put("receiverName",receiver.getUserName());
            ansUnit.put("messageType",each.getMessageType());
            ansUnit.put("hostId",each.getHostId());
            ansUnit.put("createdTime",df.format(new Date(each.getCreatedTime()*1000)));
            ansUnit.put("topCommentId",each.getTopCommentId());
            ansUnit.put("comment",each.getComment());
            list.add(ansUnit);
        }
        RespWrapper.successReturn(response,list);
    }
}
