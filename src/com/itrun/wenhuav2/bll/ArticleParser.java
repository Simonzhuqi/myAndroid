package com.itrun.wenhuav2.bll;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;

import com.itrun.wenhuav2.entity.Article;

public class ArticleParser {
	

	@SuppressLint("SimpleDateFormat")
	public List<Article> parse(String json) throws JSONException{
		
		JSONObject obj = new JSONObject(json);
		int total = Integer.valueOf(obj.getString("total"));
		JSONArray rows = obj.getJSONArray("rows");
		ArrayList<Article> list = new ArrayList<Article>();
		for(int i = 0; i < total;i++){
			Article article = new Article();
			JSONObject subObj = rows.getJSONObject(i);
			
			String id = subObj.getString("id");
			String title = subObj.getString("title");
			String content = subObj.getString("content");
			long time = subObj.getLong("publishTime");
			//long time = 1435800794389L;
			String auther = "·¢²¼ÈË£º"+subObj.getJSONObject("user").getString("userName");
			String cTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date(time));
			int status = subObj.getInt("status");
			long likeCount = subObj.getLong("znum"); 
			article.setArticleId(Integer.valueOf(id));
			article.setTitle(title);
			article.setAuther(auther);
			article.setContent(content);
			article.setDate(cTime);
			article.setLikeCount(likeCount);
			article.setStatus(status);
			list.add(article);
			article = null;
		}
		return list;
		
	}
	
	public List<Article> parseTitle(String json) throws JSONException{
		JSONObject obj = new JSONObject(json);
		int total = obj.getInt("total");
		//Log.i("tag", "length = "+total);
		JSONArray rows = obj.getJSONArray("rows");
		//Log.i("tag", "length2 = "+rows.length());
		
		ArrayList<Article> list = new ArrayList<Article>();
		
		if(rows.length() == total){
			for(int i = 0; i < total;i++){
				Article article = new Article();
				JSONObject subObj = rows.getJSONObject(i);
				String title = subObj.getString("title");
				article.setTitle(title);
				list.add(article);
				article = null;
			}
		}
		
		return list;
	}

}
