package com.cwc.web.ypzj.model.mapper;

import com.cwc.web.ypzj.model.obj.Signature;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SignatureMapper extends RowMapper {

    @Override
    public Signature map(ResultSet resultSet) throws SQLException {
        Signature signature=new Signature();
        if(resultSet==null)return signature;
        String content=resultSet.getString("content");
        Long time=resultSet.getLong("created_time");
        Long authorId=resultSet.getLong("author_id");
        signature.setCreatedTime(time);
        signature.setAuthorId(authorId);
        signature.setContent(content);
        return signature;
    }
}
