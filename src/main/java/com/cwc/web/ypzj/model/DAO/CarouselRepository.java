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
    static private DBManager dbManager;
    public static String saveCarousels(Carousel carousel){
        try{
            dbManager=new DBManager();
            String sql="insert into "+TABLE_NAME+"("+Arg.image_name.toString()+")values(?);";
            if(dbManager.insert(sql,carousel.getImageName())!=null){
                return carousel.getImageName();
            }else {
                return null;
            }
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }finally {
            dbManager.close();
        }
    }
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
