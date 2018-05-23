package com.cwc.web.ypzj.model.mapper;

import com.cwc.web.ypzj.model.obj.AttentionRelation;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AttentionRelationMapper extends RowMapper<AttentionRelation> {
    @Override
    public AttentionRelation map(ResultSet resultSet) throws SQLException {
        AttentionRelation attentionRelation=new AttentionRelation();
        attentionRelation.setId(resultSet.getLong("id"));
        attentionRelation.setTime(resultSet.getLong("time"));
        attentionRelation.setStartPoint(resultSet.getLong("start_point"));
        attentionRelation.setEndPoint(resultSet.getLong("end_point"));
        return attentionRelation;
    }
}
