package com.cwc.web.ypzj.db.DAO;

import com.cwc.web.common.db.DBManager;
import com.cwc.web.ypzj.db.mapper.CarouselMapper;
import com.cwc.web.ypzj.db.dbObj.Carousel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
enum Arg{
    id,
    image_name
}
public class CarouselRepository {
    static private String TABLE_NAME="carousels_table";
    static private DBManager dbManager;
    public static List<Carousel> getCarousels(int requiredNum){
        try{
            dbManager=new DBManager();
            String sql="select * from "+TABLE_NAME+" order by "+Arg.id.toString()+" desc limit "+requiredNum;
            List<Carousel> list=transfer(dbManager.findAll(new CarouselMapper(false),sql));
            return list;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }finally {
            if (dbManager!=null){
                dbManager.close();
            }
        }

    }
    private static  List<Carousel> transfer(List<Object> t) {
        List<Carousel> ans=null;
        if(t!=null) {
            ans=new ArrayList<>(t.size());
            for(Object o:t) {
                ans.add((Carousel) o);
            }
        }
        return ans;
    }
}
