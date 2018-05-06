package com.cwc.web.ypzj.model.mapper;

import com.cwc.web.ypzj.model.obj.Brocast;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BrocastMapper extends RowMapper {
    @Override
    public Object map(ResultSet resultSet) throws SQLException {
        long id=resultSet.getLong("id");
        String content=resultSet.getString("content");
        byte deletd=resultSet.getByte("deleted");
        long dbTime=resultSet.getLong("time");
        Date time=new Date(dbTime*1000);
        return new Brocast(id,content,deletd,time);

    }
}
