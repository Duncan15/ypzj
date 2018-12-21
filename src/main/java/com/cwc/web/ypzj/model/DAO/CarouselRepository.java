package com.cwc.web.ypzj.model.DAO;

import com.cwc.web.ypzj.model.pool.DBManager;
import com.cwc.web.ypzj.model.mapper.CarouselMapper;
import com.cwc.web.ypzj.model.obj.Carousel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarouselRepository {
    enum Arg{
        id,
        image_name
    }
    static private String TABLE_NAME="carousels_table";

    public static String saveCarousels(Carousel carousel){
        DBManager dbManager=null;
        try{
            dbManager=new DBManager();
            String sql="insert into "+TABLE_NAME+"("+Arg.image_name.toString()+")values(?);";
            if(dbManager.insert(sql,carousel.getImageName())!=-1){
                return carousel.getImageName();
            }else {
                return null;
            }
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }finally {
            if (dbManager!=null)dbManager.close();
        }
    }
    public static List<Carousel> getCarousels(int requiredNum){
        DBManager dbManager=null;
        try{
            dbManager=new DBManager();
            String sql="select * from "+TABLE_NAME+" order by "+Arg.id.toString()+" desc limit "+requiredNum;
            List<Carousel> list=dbManager.findAll(new CarouselMapper(false),sql);
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
//    private static  List<Carousel> transfer(List<Object> t) {
//        List<Carousel> ans=null;
//        if(t!=null) {
//            ans=new ArrayList<>(t.size());
//            for(Object o:t) {
//                ans.add((Carousel) o);
//            }
//        }
//        return ans;
//    }
}
