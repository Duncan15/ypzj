package com.cwc.web.ypzj.model.DAO;

import com.cwc.web.ypzj.model.mapper.FriendMapper;
import com.cwc.web.ypzj.model.obj.FriendLink;
import com.cwc.web.ypzj.model.obj.Link;
import com.cwc.web.ypzj.model.pool.DBManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FriendLinkRepository {
    public enum Arg{
        id,
        ref_id,
        link,
        intro
    }
    public static List<FriendLink> getTopNFriendLink(int n){
        DBManager dbManager=null;
        try{
            dbManager=new DBManager();
            String sql="select a.id as id,b.link as link,b.intro as intro from friend_link_table as a left outer join link_table as b on a.ref_id=b.id order by id desc;";
            return dbManager.findAll(new FriendMapper(),sql);
        }catch (SQLException e){
            e.printStackTrace();
            return new ArrayList<>();
        }finally {
            if(dbManager!=null)dbManager.close();
        }
    }
    public static boolean addNewLink(FriendLink friendLink){
        DBManager dbManager=null;
        try {
            dbManager=new DBManager();
            dbManager.supportTransaction(true);
            Link link=friendLink.getLink();
            String sql="insert into link_table(link,intro) values(?,?);";
            long ans=dbManager.insertAndGetKey(sql,link.getLink(),link.getIntro());
            if(ans==-1){
                return false;
            }
            sql="insert into friend_link_table(ref_id) values(?);";
            ans=dbManager.insert(sql,ans);
            if(ans==-1){
                dbManager.rollback();
                return false;
            }
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            dbManager.rollback();
            return false;
        }finally {
            if(dbManager!=null){
                dbManager.commit();
                dbManager.close();
            }
        }
    }
}
