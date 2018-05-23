package com.cwc.web.ypzj.model.DAO;

import com.cwc.web.ypzj.model.obj.Recommendation;
import com.cwc.web.ypzj.model.pool.DBManager;

import java.sql.SQLException;

public class RecommendRepository {
    private static final String ARTICLE_TABLE="article_info_table";
    private static final String RECOMMEND_TABLE="recommend_table";
    enum Arg{
        supported_time,

        id,
        author_id,
        article_id,
        time,
    }

    public static boolean addRecommend(Recommendation recommendation){
        DBManager dbManager=null;
        try {
            dbManager=new DBManager();
            dbManager.supportTransaction(true);
            String sql="update "+ARTICLE_TABLE+" set "+Arg.supported_time.name()+" = "+Arg.supported_time+"+ 1 where id = ?";
            dbManager.update(sql,recommendation.getArticleId());
            sql="insert into "+RECOMMEND_TABLE+"("+Arg.author_id.name()+","+Arg.article_id.name()+","+Arg.time.name()+")values(?,?,?);";
            Integer ans=dbManager.insert(sql,recommendation.getAuthorId(),recommendation.getArticleId(),recommendation.getTime());
            if(ans==null){
                dbManager.rollback();
                return false;
            }
            dbManager.commit();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            dbManager.rollback();
            System.out.println("can't record");
            return false;
        } finally{
            if(dbManager!=null)dbManager.close();
        }
    }
}
