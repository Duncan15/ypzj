package com.cwc.web.ypzj.model.DAO;

import com.cwc.web.ypzj.model.mapper.BrocastMapper;
import com.cwc.web.ypzj.model.obj.Brocast;
import com.cwc.web.ypzj.model.pool.DBManager;

import java.io.IOException;
import java.sql.SQLException;
public class BrocastRepository {
    enum Arg{
        id,
        content,
        deletd,
        time
    }
    private static final String TABLE_NAME="brocast_table";
    public static Brocast getNewestBrocast(){
        DBManager<Brocast> dbManager=null;
        try{
            dbManager=new DBManager();
            String sql="select * from "+TABLE_NAME+" order by "+Arg.time.toString()+" desc";
            Brocast brocast=dbManager.queryObject(new BrocastMapper(),sql);
            return brocast;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }finally {
            if(dbManager!=null)dbManager.close();
        }
    }
    public static Long saveBrocast(Brocast brocast) {
        DBManager dbManager=null;
        String sql = "insert into " + TABLE_NAME + " (" + Arg.content.toString() + "," + Arg.time.toString() + ")values(?,?);";
        try {
            dbManager = new DBManager();
            return (long) dbManager.insert(sql, brocast.getContent(), brocast.getTime().getTime() / 1000);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if(dbManager!=null)dbManager.close();

        }

    }
}
