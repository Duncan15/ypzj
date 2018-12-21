package com.cwc.web.ypzj.model.DAO;

import com.cwc.web.ypzj.model.mapper.AttentionRelationMapper;
import com.cwc.web.ypzj.model.obj.AttentionRelation;
import com.cwc.web.ypzj.model.pool.DBManager;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class AttentionRelationRepository {
    public enum Arg{
        id,
        time,
        start_point,
        end_point,
        concerned_time,
    }
    public static final String ATTENTION_TABLE="attention_graph";
    public static final String USER_TABLE="user_table";
    public static Boolean connect(AttentionRelation attentionRelation){
        DBManager dbManager=null;
        try {
            dbManager=new DBManager();
            dbManager.supportTransaction(true);
            String sql="insert into "+ATTENTION_TABLE+"("+Arg.start_point.name()+","+Arg.end_point.name()+","+Arg.time+")values(?,?,?);";
            if(dbManager.insert(sql,attentionRelation.getStartPoint(),attentionRelation.getEndPoint(),attentionRelation.getTime())!=-1){
                sql="update "+USER_TABLE+" set "+Arg.concerned_time.name()+" = "+Arg.concerned_time.name()+" +1 where id = ? ;";
                if(dbManager.update(sql,attentionRelation.getEndPoint())!=-1){
                    return true;
                }
            }
            dbManager.rollback();
            return false;
        }catch (SQLException e){
            e.printStackTrace();
            dbManager.rollback();
            return null;
        }finally {
            if(dbManager!=null){
                dbManager.commit();
                dbManager.close();
            }
        }
    }
    public static Boolean isExist(AttentionRelation attentionRelation){
        DBManager dbManager=null;
        try{
            dbManager=new DBManager();
            String sql="select * from "+ATTENTION_TABLE+" where "+Arg.start_point.name()+" = ? and "+Arg.end_point.name()+" = ? ;";
            if(dbManager.queryObject(new AttentionRelationMapper(),sql,attentionRelation.getStartPoint(),attentionRelation.getEndPoint())==null){
                return false;
            }
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }finally {
            if(dbManager!=null)dbManager.close();
        }
    }
    public static List<AttentionRelation> getTopNAttention(Long startPoint,int N){
        return getAttentionLimitStartAndLen(startPoint,0,N);
    }
    public static List<AttentionRelation> getAttentionLimitStartAndLen(Long startPoint,int start,int len){
        DBManager dbManager=null;
        try {
            dbManager=new DBManager();
            String sql="select * from "+ATTENTION_TABLE+" where "+Arg.start_point.name()+" = ? order by "+Arg.time.name()+" asc limit ? , ? ;";
            return dbManager.findAll(new AttentionRelationMapper(),sql,startPoint,start,len);
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }finally {
            if(dbManager!=null)dbManager.close();
        }
    }
    public static List<AttentionRelation> getTopNAttentioned(Long endPoint,int N){
        return getAttentionedLimitStartAndLen(endPoint,0,N);
    }
    public static List<AttentionRelation> getAttentionedLimitStartAndLen(Long endPoint,int start,int len){
        DBManager dbManager=null;
        try {
            dbManager=new DBManager();
            String sql="select * from "+ATTENTION_TABLE+" where "+Arg.end_point.name()+" = ? order by "+Arg.time.name()+" asc limit ? , ? ;";
            return dbManager.findAll(new AttentionRelationMapper(),sql,endPoint,start,len);
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }finally {
            if(dbManager!=null)dbManager.close();
        }
    }
}
