package com.cwc.web.ypzj.model.mapper;

import com.cwc.web.ypzj.model.obj.FriendLink;
import com.cwc.web.ypzj.model.obj.Link;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FriendMapper extends RowMapper<FriendLink> {
    @Override
    public FriendLink map(ResultSet resultSet) throws SQLException {
        if(resultSet==null){
            return new FriendLink();
        }
        long id=resultSet.getLong("id");
        String link=resultSet.getString("link");
        String intro=resultSet.getString("intro");
        Link linkObj=new Link(-1l,intro,link,-1l);
        return new FriendLink(id,linkObj);
    }
}
