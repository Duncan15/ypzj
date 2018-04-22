package com.cwc.web.ypzj.db.mapper;

import com.cwc.web.ypzj.db.dbObj.Carousel;
import com.cwc.web.ypzj.db.dbObj.Image;

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
        return new Carousel(id,new Image(name,md5));
    }
}
