package com.itrun.wenhuav2.adapter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.itrun.wenhuav2.R;
import com.itrun.wenhuav2.entity.Article;
import com.itrun.wenhuav2.utils.GlobalConstants;
import com.itrun.wenhuav2.utils.HttpUtils;
import com.itrun.wenhuav2.utils.StringUtils;

public class ArticleLikeAdapter extends BaseAdapter {

	List<Article> list;
	private ViewHolder holder;
	private Context context;
	private Article article;
	private boolean[] flags;//true �ѱ�����     false δ������
	private ImageView currentIv;
	private TextView currentTv;
	private int currentPosition;

	public ArticleLikeAdapter(List<Article> list, Context context) {

		this.list = list;
		flags = new boolean[list.size()];
		this.context = context;
	}


	@Override
	public int getCount() {

		return list.size();
	}

	@Override
	public Article getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return list.indexOf(list.get(position));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if(convertView == null){
			convertView = View.inflate(context, R.layout.item_article, null);
			holder = new ViewHolder();
			holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
			holder.tvAuther = (TextView) convertView.findViewById(R.id.auther);			
			holder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);			
			holder.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
			holder.tvLikeCount = (TextView) convertView.findViewById(R.id.tv_like_count);			
			holder.ivLike = (ImageView)convertView.findViewById(R.id.iv_like);
			holder.tvLable = (TextView) convertView.findViewById(R.id.iv_lable);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}

		article = getItem(position);
		holder.tvTitle.setText(StringUtils.titleLimit(article.getTitle()));
		holder.tvAuther.setText(article.getAuther());
		holder.tvContent.setText(article.getContent());
		holder.tvDate.setText(article.getDate());
		holder.tvLikeCount.setText(article.getLikeCount()+"");		

		if(article.getStatus() == 6){
			holder.tvLable.setText("����");
			holder.tvLable.setBackgroundResource(R.drawable.icon_jh);
		}else{
			holder.tvLable.setText("��ͨ");
			holder.tvLable.setBackgroundResource(R.drawable.icon_pt);
		}
		// �жϱ����positionλ�����Ƿ��ѵ���
		if(flags[position]){
			holder.ivLike.setImageResource(R.drawable.dianzan_red);
			holder.ivLike.setClickable(false);
		}else{
			holder.ivLike.setImageResource(R.drawable.dianzan_grey);
			holder.ivLike.setClickable(true);
			holder.ivLike.setOnClickListener(new LikeListener(position, holder.ivLike, holder.tvLikeCount));
		}

		return convertView;
	}

	class LikeListener implements OnClickListener{

		private int position;
		private ImageView iv;
		private TextView tv;

		public LikeListener(int position, ImageView iv,TextView tv) {
			this.position = position;
			this.iv = iv;
			this.tv = tv;
		}

		@Override
		public void onClick(View v) {

			currentPosition = position;
			currentIv = iv;
			currentTv = tv;
			new LikeTask().execute(article.getArticleId()+"");
		}

	}

	class ViewHolder{
		private TextView tvTitle;
		private TextView tvAuther;
		private TextView tvContent;
		private TextView tvDate;
		private TextView tvLikeCount;
		private ImageView ivLike;
		private TextView tvLable;
	}
	
	//�첽�����������ύ����
	private class LikeTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			String userId = "385667";
			String uri = 
					GlobalConstants.GetServer(context)+"sunshineAction!znum.action";
			HashMap<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("userId", userId);
			paramsMap.put("deeds.id", article.getArticleId()+"");
			HttpResponse response = null;
			try {
				response = HttpUtils.getHttpResponse(uri , paramsMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String json = null;
			try {
				json = EntityUtils.toString(response.getEntity());
			} catch (org.apache.http.ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return json;
		}

		@Override
		protected void onPostExecute(String result) {
			if(result != null){
				try {
					JSONObject obj = new JSONObject(result);		
					if(obj.getBoolean(("success"))){
						currentIv.setImageResource(R.drawable.dianzan_red);
						currentTv.setText(list.get(currentPosition).getLikeCount()+1+"");
						flags[currentPosition] = true;
					}else{
						currentIv.setImageResource(R.drawable.dianzan_red);
						currentTv.setText(list.get(currentPosition).getLikeCount()+1+"");
						flags[currentPosition]= true;
					}					
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
