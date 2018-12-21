package com.cwc.web.ypzj.model.DAO;


import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.cwc.web.ypzj.common.constant.Type;
import com.cwc.web.ypzj.model.pool.DBManager;
import com.cwc.web.ypzj.model.mapper.ArticleContentMapper;
import com.cwc.web.ypzj.model.mapper.ArticleInfoMapper;
import com.cwc.web.ypzj.model.obj.ArticleContent;
import com.cwc.web.ypzj.model.obj.ArticleInfo;

public class ArticleRepository {
	private final static String INFO_TABLE="article_info_table";
	private final static String CONTENT_TABLE="article_content_table";
	public enum Arg{
		id,
		article_name,
		created_time,
		supported_time,
		top_label_id,
		author_id,
		content,
		avatar_id,
		status
	}
	/*
	 * add article
	 */
	public static Long addArticle(String articleName,Long labelId,Long authorId,String content,String avatarId) {
		DBManager dbManager=null;
		try{
			dbManager=new DBManager();
			dbManager.supportTransaction(true);
			String sql="insert into "+INFO_TABLE+"("+Arg.article_name.toString()+","+Arg.top_label_id.toString()+","+Arg.author_id.toString()+","+Arg.created_time.toString()+","+Arg.avatar_id.toString()+")values(?,?,?,?,?);";

			Long articleId=dbManager.insertAndGetKey(sql,articleName,labelId,authorId,new Date().getTime()/1000,avatarId);
			if(articleId!=-1){
				sql="insert into "+CONTENT_TABLE+"("+Arg.id.toString()+","+Arg.content.toString()+")values(?,?);";
				if(dbManager.insert(sql,articleId,content)!=-1){
					dbManager.commit();
					return articleId;
				}
			}
			dbManager.rollback();
			return null;
		}catch (SQLException e){
			e.printStackTrace();
			return null;
		}
		finally {
			if(dbManager!=null){
				dbManager.close();
			}
		}
	}
	
	public static ArticleInfo getArticleInfoById(Long id)//return null when haven't this article
	{
		String sql="select * from article_info_table where id=? and status =?;";
		DBManager dbManager=null;
		try{
			dbManager=new DBManager();
			return dbManager.queryObject(new ArticleInfoMapper(),sql,id, Type.ContentStatus.UNBAN.getValue());
		}catch (SQLException e){
			e.printStackTrace();
			return null;
		}finally {
			if(dbManager!=null)dbManager.close();
		}
	}
	public static ArticleContent getArticleContentByArticleId(Long id)//return null when haven't this article
	{
		String sql="select * from article_content_table where id=?;";
		DBManager dbManager=null;
		try {
			dbManager=new DBManager();
			return dbManager.queryObject(new ArticleContentMapper(),sql,id);
		}catch (SQLException e){
			e.printStackTrace();
			return null;
		}finally {
			if(dbManager!=null)dbManager.close();
		}
	}

	public static List<ArticleInfo> findByLabelIdOrderByCreatedTime(long labelId,int requireNum) {
		 List<ArticleInfo> t=findByArgAOrderByArgBLimitStartAndLen(Arg.top_label_id.toString(),labelId,Arg.created_time.toString(),0,requireNum);
		 return t;
	}

	
	public static List<ArticleInfo> findByLabelIdOrderBySupportedTime(long labelId,int requireNum){
		List<ArticleInfo> t=findByArgAOrderByArgBLimitStartAndLen(Arg.top_label_id.toString(),labelId,Arg.supported_time.toString(),0,requireNum);
		return t;
	}

	public static List<ArticleInfo> findByAuthorIdOrderByCreatedTime(Long authorId,int requireNum) {
		List<ArticleInfo> t=findByArgAOrderByArgBLimitStartAndLen(Arg.author_id.toString(),authorId,Arg.created_time.toString(),0,requireNum);
		return t;
	}


	public static List<ArticleInfo> findByAuthorIdOrderBySupportedTime(Long authorId,int requireNum){
		List<ArticleInfo> t=findByArgAOrderByArgBLimitStartAndLen(Arg.author_id.toString(),authorId,Arg.supported_time.toString(),0,requireNum);
		return t;
	}

	public static List<ArticleInfo> findByAuthorIdLimitStartAndNum(Long authorId,int start,int end){
		List<ArticleInfo> t=findByArgAOrderByArgBLimitStartAndLen(Arg.author_id.toString(),authorId,null,start,end);
		return t;
	}
	public static Long getCountByAuthorId(Long authorId){
		DBManager dbManager=null;
		String sql="select count(*) from "+INFO_TABLE+" where status=?";
		String cond="";
		if(authorId!=null){
			cond+=" and "+Arg.author_id.toString()+" = ? ";
		}
		sql+=cond;
		try{
			dbManager=new DBManager();
			return dbManager.getNum(sql,Type.ContentStatus.UNBAN.getValue(),authorId);
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}finally {
			if(dbManager!=null)dbManager.close();
		}
	}

	private static List<ArticleInfo> findByArgAOrderByArgBLimitStartAndLen(String argA,Object valueA,String argB,int start,int len){
		DBManager dbManager=null;
		String sql="select * from "+INFO_TABLE;
		String cond=" where "+argA+" = ? and status = ? ";
		sql+=cond;
		if(argB!=null){
			String order=" order by "+argB+" desc ";
			sql+=order;
		}
		sql+=" limit "+start+","+len+";";
		try{
			dbManager=new DBManager();
			return dbManager.findAll(new ArticleInfoMapper(),sql,valueA,Type.ContentStatus.UNBAN.getValue());
		}catch (SQLException e){
			e.printStackTrace();
			return null;
		}finally {
			if(dbManager!=null)dbManager.close();
		}
	}

	public static ArticleInfo getStatus(String title,long authorId){
		DBManager dbManager=null;
		String sql="select * from "+INFO_TABLE+" where article_name=? and author_id=? ";
		try {
			dbManager=new DBManager();
			ArticleInfo target=dbManager.queryObject(new ArticleInfoMapper(),sql,title,authorId);
			return target;
		}catch (SQLException e){
			e.printStackTrace();
			return null;
		}finally {
			if(dbManager!=null)dbManager.close();
		}
	}
	public static boolean changeStatus(long articleId,byte status){
		DBManager dbManager=null;
		String sql="update "+INFO_TABLE+" set status = ? where id = ?";
		try {
			dbManager = new DBManager();
			if (dbManager.update(sql, status, articleId) == -1)
				return false;
			return true;
		}catch (SQLException e){
			e.printStackTrace();
			return false;
		}finally {
			if(dbManager!=null){
				dbManager.close();
			}
		}
	}


}
