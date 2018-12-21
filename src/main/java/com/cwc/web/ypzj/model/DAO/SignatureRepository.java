package com.cwc.web.ypzj.model.DAO;

import com.cwc.web.ypzj.model.mapper.SignatureMapper;
import com.cwc.web.ypzj.model.obj.Signature;
import com.cwc.web.ypzj.model.pool.DBManager;

import java.sql.SQLException;


public class SignatureRepository {
    private static final String TABLE_NAME="signature_table";
    enum Arg{
        id,
        content,
        created_time,
        author_id
    }
    public static boolean saveSignature(Signature signature){
        String sql="insert into "+TABLE_NAME+"("+Arg.content.name()+","+Arg.created_time.name()+","+Arg.author_id.name()+")values(?,?,?);";
        DBManager dbManager=null;
        try{
            dbManager=new DBManager();
            Integer ans=dbManager.insert(sql,signature.getContent(),signature.getCreatedTime(),signature.getAuthorId());
            if(ans!=null){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(dbManager!=null){
                dbManager.close();
            }
        }
        return false;
    }
    public static Signature getNewestSignature(Long authorId){
        String sql="select * from "+TABLE_NAME+" where "+Arg.author_id.name()+" = ? order by "+Arg.created_time+" desc;";
        DBManager dbManager=null;
        Signature signature=null;
        try {
            dbManager=new DBManager();
            signature= dbManager.queryObject(new SignatureMapper(),sql,authorId);

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(dbManager!=null)dbManager.close();
            if(signature==null)signature=new Signature();
        }
        return signature;

    }
}
