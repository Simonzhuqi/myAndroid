package com.itrun.wenhuav2.bll;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import android.os.AsyncTask;

import com.itrun.logmanagement.ui.ArticleLikeActivity;
import com.itrun.wenhuav2.entity.Article;
import com.itrun.wenhuav2.utils.HttpUtils;

public class LordArticleTask extends AsyncTask<String, Integer, String>{

	private ArticleLikeActivity context;
	private List<Article> list = new ArrayList<Article>();
	
	
	
	
	public LordArticleTask(ArticleLikeActivity context) {
		this.context = context;
	}

	@Override
	protected String doInBackground(String... params) {
		
		String json = "";
		HashMap<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("corpId", params[1]);
		//paramsMap.put("deeds.status", params[2]);
		//paramsMap.put("startDate", params[3]);
		//paramsMap.put("endDate", params[4]);
		try {

			HttpResponse response = HttpUtils.getHttpResponse(params[0], paramsMap);
			json = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	@Override
	protected void onPostExecute(String result) {
		
		try {
			list = new ArticleParser().parse(result);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		context.updateListView(list);
	}


}
