package com.cwc.web.ypzj.model.mapper;

import com.cwc.web.ypzj.model.obj.Carousel;
import com.cwc.web.ypzj.model.obj.Image;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarouselMapper extends RowMapper{
    private boolean getMd5OrNot;
    /*
    @param md5 represent whether get md5 or not
     */
    public CarouselMapper(boolean md5){
        this.getMd5OrNot=md5;
    }
    @Override
    public Object map(ResultSet resultSet) throws SQLException {
        Long id=resultSet.getLong("id");
        String name=resultSet.getString("image_name");
        byte[] md5=null;
        if(this.getMd5OrNot){
            md5=resultSet.getBytes("md5");
        }
        Carousel ans=new Carousel(id);
        ans.setImageName(name);
        ans.setMD5(md5);
        return ans;
    }
}
