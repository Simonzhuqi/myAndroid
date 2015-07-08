package com.itrun.logmanagement.ui;

import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itrun.wenhuav2.R;
import com.itrun.wenhuav2.bll.ModifyPswTask;
import com.itrun.wenhuav2.utils.GlobalConstants;
import com.itrun.wenhuav2.utils.HttpUtils;

public class MotifyPasswordActivity extends Activity {

	private static final int MOTIFY_SUCCESS = 0;
	private static final int MOTIFY_FAIL = 1;
	private static final int CONNECTION_FAIL = 2;
	private EditText etOldpsw;
	private EditText etNewpsw;
	private EditText etNewpsw2;
	private Button btBack;
	private Button btSave;

//	private Handler handler = new Handler(){
//		public void handleMessage(Message msg) {
//			switch (msg.what) {
//			case MOTIFY_SUCCESS:
//				Toast.makeText(getApplicationContext(), "密码修改成功", Toast.LENGTH_SHORT).show();				
//				break;
//
//			case MOTIFY_FAIL:
//				Toast.makeText(getApplicationContext(), "密码修改失败", Toast.LENGTH_SHORT).show();	
//				break;
//			case CONNECTION_FAIL:
//				Toast.makeText(getApplicationContext(), "网络异常", Toast.LENGTH_SHORT).show();
//				break;
//			}
//		};
//	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_motify_password);
		setViews();
		setLiseners();
	}

	private void setLiseners() {
		btBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				back();
				
			}
		});
		btSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				save();
				
			}
		});
	}

	protected void save() {
		if(TextUtils.isEmpty(etOldpsw.getText().toString())||
				TextUtils.isEmpty(etNewpsw.getText().toString())||
				TextUtils.isEmpty(etNewpsw2.getText().toString())){
			Toast.makeText(getApplicationContext(), "输入不能为空，请填写完整", Toast.LENGTH_SHORT).show();
			return;
		}
		if(verity(etOldpsw.getText().toString(),etNewpsw.getText().toString(),etNewpsw2.getText().toString())){
//			new Thread(new Runnable(){
//				public void run() {
//					connectToServer();
//				};
//			}).start();
			
			
			connectToServer();
		}
	}

	protected void connectToServer() {
	
		String oldPsw = etOldpsw.getText().toString();
		String newPsw = etNewpsw.getText().toString();
		//不是动态数据====================
		//String croPid = "60904480";
		String userId = "385667";  
		//=============================
		String uri = GlobalConstants.GetServer(MotifyPasswordActivity.this)+"userAction.action?command=editPwd";
		
		ModifyPswTask task = new ModifyPswTask(MotifyPasswordActivity.this);
		task.execute(uri,oldPsw,newPsw,userId);
		
		
		
		
//		HashMap<String, String> paramsMap = new HashMap<String, String>();
//		paramsMap.put("nowPwd", oldPsw);   
//		paramsMap.put("newPwd", newPsw);     
//		paramsMap.put("user.id", userId);
//		try {
//			if(HttpUtils.getHttpResponse(uri, paramsMap).getStatusLine().getStatusCode() == 200){
//				HttpEntity entity = HttpUtils.getHttpResponse(uri, paramsMap).getEntity();
//				String json = EntityUtils.toString(entity);
//				Log.i("tag", "json:------"+json);
//				JSONArray array = new JSONArray(json);
//				JSONObject jsonObj = (JSONObject) array.get(0);
//			
//				if("true".equals(jsonObj.getString("success"))){
//					
//					handler.sendEmptyMessage(MOTIFY_SUCCESS);
//				}else{
//				
//					handler.sendEmptyMessage(MOTIFY_FAIL);
//				}
//			
//			
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//	
//			handler.sendEmptyMessage(CONNECTION_FAIL);
//		}
//		
		
		
	}

	private boolean verity(String oldpsw, String newpsw, String newpsw2) {
		if(!"1234".equals(oldpsw)){
			Toast.makeText(getApplicationContext(), "输入的原密码错误", Toast.LENGTH_SHORT).show();
		}else{
			if(!newpsw.equals(newpsw2)){
				Toast.makeText(getApplicationContext(), "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
			}else{
				return true;
			}
		}
		
		return false;
	}

	protected void back() {
		// TODO Auto-generated method stub
		
	}

	private void setViews() {
		etOldpsw = (EditText)findViewById(R.id.yuanpass);
		etNewpsw = (EditText)findViewById(R.id.new_pass);
		etNewpsw2 = (EditText)findViewById(R.id.que_pass);
		btBack = (Button)findViewById(R.id.back_btn);
		btSave = (Button)findViewById(R.id.save_btn);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.motify_password, menu);
		return true;
	}

}
