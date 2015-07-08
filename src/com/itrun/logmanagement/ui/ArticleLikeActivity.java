package com.itrun.logmanagement.ui;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.itrun.wenhuav2.R;
import com.itrun.wenhuav2.adapter.ArticleLikeAdapter;
import com.itrun.wenhuav2.bll.ArticleParser;
import com.itrun.wenhuav2.bll.LordArticleTask;
import com.itrun.wenhuav2.entity.Article;
import com.itrun.wenhuav2.utils.GlobalConstants;
import com.itrun.wenhuav2.utils.HttpUtils;

public class ArticleLikeActivity extends Activity {

	protected static final int SEARCH_SUCCESS = 1;
	protected static final int SEARCH_FAIL = 0;
	protected static final int QUERYLIST = 2;

	private String userId;
	private ListView listView;
	private List<Article> list = new ArrayList<Article>();
	private ArticleLikeAdapter articleLikeAdapter;
	private Button btSearch;
	private TextView tvPost;
	private TextView tvBack;
	private AutoCompleteTextView etSearch;


	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SEARCH_SUCCESS:

				break;

			case SEARCH_FAIL:

				break;
				//获取查询列表数据，设置AutoCompleteTextView的适配器
			case QUERYLIST:
				String[] data = (String[]) msg.obj;
				ArrayAdapter<String> adapter = 
						new ArrayAdapter<String>(
								ArticleLikeActivity.this, 
								android.R.layout.simple_dropdown_item_1line,
								data);
				etSearch.setAdapter(adapter);
				break;
			}

		};
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_article_like);
		setViews();
		setData();
		articleLikeAdapter = new ArticleLikeAdapter(list, getApplicationContext());
		listView.setAdapter(articleLikeAdapter);
		setLiseners();
		getQueryList();
		//setUpdate();
	}

	private void setViews() {
		listView = (ListView)findViewById(R.id.listView1);
		btSearch = (Button)findViewById(R.id.bt_search);
		etSearch = (AutoCompleteTextView)findViewById(R.id.et_search);
		tvPost = (TextView)findViewById(R.id.tv_post);
		tvBack = (TextView)findViewById(R.id.tv_back);
	}


	//获取查询列表
	private void getQueryList() {
		new Thread(){
			@Override
			public void run() {
				HttpResponse response= null;
				String startDate = null;
				String endDate = null;					
				String uri = GlobalConstants.GetServer(ArticleLikeActivity.this)+"sunshineAction!queryList.action";
				HashMap<String,String> params = new HashMap<String,String>();
				params.put("corpId", "60904480");
				params.put("userId", "385667");
				//params.put("deeds.status", status);
				params.put("startDate", startDate);
				params.put("endDate", endDate);
				try {
					response = HttpUtils.getHttpResponse(uri, params);
				} catch (Exception e) {
					e.printStackTrace();
				}
				String json = null;
				List<Article> queryList = new ArrayList<Article>();
				try {
					json = EntityUtils.toString(response.getEntity());
				} catch (org.apache.http.ParseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(json != null){


					try {
						queryList = new ArticleParser().parseTitle(json);


					} catch (JSONException e) {
						e.printStackTrace();
						//Log.i("tag", "JSONException");
					}

					String[] data = new String[queryList.size()];
					for(int i = 0; i<queryList.size(); i++){
						data[i] = queryList.get(i).getTitle();

					}

					Message msg = Message.obtain();
					msg.what = QUERYLIST;
					msg.obj = data;
					handler.sendMessage(msg);
				}


			}
		}.start();

	}
	private void setLiseners() {
		//点击搜索
		btSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread(){
					@Override
					public void run() {
						//==========搜索===========



					}
				}.start();
			}
		});

		//点击发布
		tvPost.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//============发布===============
			}
		});
		//点击后退
		tvBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//============后退===============
			}
		});


	}



	public void updateListView(List<Article> list2) {

		articleLikeAdapter.notifyDataSetChanged();

	}
	
	//从服务端获取数据
	@SuppressLint("SimpleDateFormat")
	private void setData() {
		String uri = 
				GlobalConstants.GetServer(ArticleLikeActivity.this)+"sunshineAction!queryShowList.action";
		LordArticleTask task = new LordArticleTask(ArticleLikeActivity.this);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date start = null;
		Date end = null;
		try {
			start = format.parse("2015-02-24");
			end = format.parse("2015-05-30");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		task.execute(uri,"60904480",null,start.getTime()+"",end.getTime()+"");
		ArticleParser parser = new ArticleParser();
		try {
			list = parser.parse(task.get());

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.article_like, menu);
		return true;
	}

}
