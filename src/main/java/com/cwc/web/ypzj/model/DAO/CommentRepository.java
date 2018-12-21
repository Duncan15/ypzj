package com.cwc.web.ypzj.model.DAO;

import com.cwc.web.ypzj.common.constant.Type;
import com.cwc.web.ypzj.model.mapper.CommentMapper;
import com.cwc.web.ypzj.model.obj.Comment;
import com.cwc.web.ypzj.model.pool.DBManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentRepository {
    enum Arg{
        id,
        top_comment_id,
        sender_id,
        receiver_id,
        message_type,
        host_id,
        created_time,
        comment,
        comment_times
    }
    private static final String TABLE_NAME="comment_table";

    public static long getCommentTimes(Type.MessageType messageType,long hostId){
        DBManager dbManager=null;
        try {
            dbManager=new DBManager();
            String sql="select count(*) as "+Arg.comment_times.name()+" from "+TABLE_NAME+" where "+Arg.message_type.name()+" = ? and "+Arg.host_id.name()+" = ? ;";
            return dbManager.getNum(sql,messageType.getValue(),hostId);
        }catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }finally {
            if(dbManager!=null)dbManager.close();
        }
    }
    public static List<Comment> getComments(Type.MessageType messageType, long hostId, int pageSize, int pageCount, Type.Order order){
        DBManager dbManager=null;
        try {
            dbManager=new DBManager();
            String sql="select * from "+TABLE_NAME+" where "+Arg.message_type.name()+" = ? and "+Arg.host_id.name()+" = ? order by "+Arg.created_time.name()
                    +" "+order+" limit "+(pageCount-1)*pageSize+","+pageSize+";";
            return dbManager.findAll(new CommentMapper(),sql,messageType.getValue(),hostId);
        }catch (SQLException e){
            e.printStackTrace();
            return new ArrayList<>();
        }finally {
            if(dbManager!=null)dbManager.close();

        }
    }
    public static List<Comment> getCommentDetails(long topCommentId,int pageSize,int pageCount,Type.Order order){
        DBManager dbManager=null;
        try{
            dbManager=new DBManager();
            String sql="select * from "+TABLE_NAME+" where "+Arg.top_comment_id.name()+" = ? order by "+Arg.created_time.name()
                    +" "+order+" limit "+(pageCount-1)*pageSize+","+pageSize+";";
            return dbManager.findAll(new CommentMapper(),sql,topCommentId);
        }catch (SQLException e){
            e.printStackTrace();
            return new ArrayList<>();
        }finally {
            if(dbManager!=null)dbManager.close();
        }
    }

    public static Long saveTopCommnet(Comment comment){
        DBManager dbManager=null;
        try {
            dbManager=new DBManager();
            dbManager.supportTransaction(true);
            String sql="insert into "+TABLE_NAME+"("
                    +Arg.host_id.name()+","
                    +Arg.message_type.name()+","
                    +Arg.sender_id.name()+","
                    +Arg.comment.name()+","
                    +Arg.created_time.name()+")"
                    +"values(?,?,?,?,?);";
            Long id=dbManager.insertAndGetKey(sql,comment.getHostId(),comment.getMessageType().getValue(),comment.getSenderId(),comment.getComment(),comment.getCreatedTime());
            if(id!=-1){
                sql="update "+TABLE_NAME+" set "+Arg.top_comment_id.name()+" = ? where "+Arg.id.name()+" = ? ";
                dbManager.update(sql,id,id);
                dbManager.commit();
                return id;
            }else {
                dbManager.rollback();
                return -1l;
            }
        }catch (SQLException e){
            e.printStackTrace();
            if(dbManager!=null){
                dbManager.rollback();
            }
            return -1l;
        }finally {
            if(dbManager!=null)dbManager.close();
        }
    }
    public static Long saveSecondLevelComment(Comment comment){
        DBManager dbManager=null;
        try{
            dbManager=new DBManager();
            String sql="insert into "+TABLE_NAME+"("
                    +Arg.host_id.name()+","
                    +Arg.message_type.name()+","
                    +Arg.sender_id.name()+","
                    +Arg.receiver_id.name()+","
                    +Arg.comment.name()+","
                    +Arg.created_time.name()+","
                    +Arg.top_comment_id.name()+")values(?,?,?,?,?,?,?);";
            Long id=dbManager.insertAndGetKey(sql,comment.getHostId(),comment.getMessageType().getValue(),comment.getSenderId(),comment.getReceiverId(),comment.getComment(),comment.getCreatedTime(),comment.getTopCommentId());
            return id;

        }catch (SQLException e){
            e.printStackTrace();
            return -1l;
        }finally {
            if(dbManager!=null){
                dbManager.close();
            }
        }
    }
}
