package com.itrun.wenhuav2.bll;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.itrun.wenhuav2.utils.HttpUtils;

public class ModifyPswTask extends AsyncTask<String, Integer, String> {
	
	private Context context;
	public ModifyPswTask(Context context){
		this.context = context;
	}

	@Override
	protected String doInBackground(String... params) {
		HashMap<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("nowPwd", params[1]);   
		paramsMap.put("newPwd", params[2]);     
		paramsMap.put("user.id", params[3]);
		try {
			if(HttpUtils.getHttpResponse(params[0], paramsMap).getStatusLine().getStatusCode() == 200){
				HttpEntity entity = HttpUtils.getHttpResponse(params[0], paramsMap).getEntity();

				return EntityUtils.toString(entity);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	
		return null;
	}
	

	
	@Override
	protected void onPostExecute(String result) {
		Log.i("tag", "json:"+result);
		if(result != null){
			try {
				JSONArray array = new JSONArray(result);
				JSONObject jsonObj = (JSONObject) array.get(0);
				
				if("true".equals(jsonObj.getString("success"))){
					Toast.makeText(context, "√‹¬Î–ﬁ∏ƒ≥…π¶", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(context, "√‹¬Î–ﬁ∏ƒ ß∞‹", Toast.LENGTH_SHORT).show();
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}else{
			Toast.makeText(context, "Õ¯¬Á“Ï≥£", Toast.LENGTH_SHORT).show();
		}
	
	}
	

}
