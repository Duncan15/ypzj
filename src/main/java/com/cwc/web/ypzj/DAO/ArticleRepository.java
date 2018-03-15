package com.cwc.web.ypzj.DAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

import com.cwc.web.ypzj.db.DBUtil;
import com.cwc.web.ypzj.db.mapper.ArticleContentMapper;
import com.cwc.web.ypzj.db.mapper.ArticleInfoMapper;
import com.cwc.web.ypzj.servletObj.ArticleContent;
import com.cwc.web.ypzj.servletObj.ArticleInfo;

public class ArticleRepository {
	private final static String INFO_TABLE="article_info_table";
	private final static String CONTENT_TABLE="article_content_table";
	public enum Arg{
		id,
		article_name,
		created_time,
		supported_time,
		top_label_id,
		author_id
	}
	/*
	 * add article
	 */
	public static Boolean addArticle(String articleName,Long labelId,String authorId,String content) {
		
		return false;
	}
	private static Long addArticleInfo(String artcleName,Long labelId,String authorId) {
		String sql="insert into "+INFO_TABLE+"("+Arg.article_name.toString()+","+Arg.top_label_id.toString()+","+Arg.author_id.toString()+")values(\""
				+artcleName+"\","+labelId+",\""+authorId+"\");";
		return DBUtil.insertAndGetKey(sql, null);
	}
	private static 
	
	
	
	
	public static ArticleInfo getArticleInfoById(long id)//return null when haven't this article
	{
		String sql="select * from article_info_table where id="+id;
		return (ArticleInfo)DBUtil.queryObject(sql, null,new ArticleInfoMapper());
	}
	public static ArticleContent getArticleContentByArticleId(long id)//return null when haven't this article
	{
		String sql="select * from article_content_table where id="+id;
		return (ArticleContent)DBUtil.queryObject(sql,null, new ArticleContentMapper());
	}

	public static List<ArticleInfo> findByLabelIdOrderByCreatedTime(long labelId)
	{
		return transfer(DBUtil.findByArgAOrderByArgB("article_info_table",Arg.top_label_id.toString(), labelId+"", Arg.created_time.toString(), new ArticleInfoMapper()));
	}
	public static List<ArticleInfo> findByLabelIdOrderByCreatedTime(long labelId,int requireNum)
	{
		 List<ArticleInfo> t=findByLabelIdOrderByCreatedTime(labelId);
		 return getTopN(t, requireNum);
	}
	public static List<ArticleInfo> findByLabelIdOrderBySupportedTime(long labelId)
	{
		return transfer(DBUtil.findByArgAOrderByArgB("article_info_table", Arg.top_label_id.toString(), labelId+"", Arg.supported_time.toString(), new ArticleContentMapper()));
	}
	
	public static List<ArticleInfo> findByLabelIdOrderBySupportedTime(long labelId,int requireNum){
		List<ArticleInfo> t=findByLabelIdOrderByCreatedTime(labelId);
		return getTopN(t, requireNum);
	}
	public static List<ArticleInfo> findByAuthorIdOrderByCreatedTime(String authorId){
		return transfer(DBUtil.findByArgAOrderByArgB("article_info_table",Arg.author_id.toString(), authorId+"", Arg.created_time.toString(), new ArticleInfoMapper()));
	}
	public static List<ArticleInfo> findByAuthorIdOrderByCreatedTime(String authorId,int requireNum)
	{
		List<ArticleInfo> t=findByAuthorIdOrderByCreatedTime(authorId);
		return getTopN(t, requireNum);
	}
	public static List<ArticleInfo> findByAuthorIdOrderBySupportedTime(String authorId){
		return transfer(DBUtil.findByArgAOrderByArgB("article_info_table",Arg.author_id.toString(), authorId+"", Arg.supported_time.toString(), new ArticleInfoMapper()));
	}
	public static List<ArticleInfo> findByAuthorIdOrderBySupportedTime(String authorId,int requireNum){
		List<ArticleInfo> t=findByAuthorIdOrderBySupportedTime(authorId);
		return getTopN(t, requireNum);
	}
	private static List<ArticleInfo> transfer(List<Object> t) {
		ArrayList<ArticleInfo> ans=null;
		if(t!=null) {
			ans=new ArrayList<>(t.size());
			for(Object o:t) {
				ans.add((ArticleInfo)o);
			}
		}
		return ans;
	}
	private static List<ArticleInfo> getTopN(List<ArticleInfo> t,int n){
		if(t!=null) {
			 if(n<t.size()) {
				 ArticleInfo[] ans=new ArticleInfo[n];
				 System.arraycopy(t.toArray(new ArticleInfo[t.size()]), 0, ans, 0, n);
				 t=Arrays.asList(ans);
			 }
		 }
		 return t;
	}
}