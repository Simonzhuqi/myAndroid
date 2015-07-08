package com.itrun.zhuqi.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.itrun.mywidget.R;

public class MainActivity extends Activity implements OnClickListener{

	private TextView siderMeun;
	private TextView tweenanimation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		siderMeun = (TextView)findViewById(R.id.textView1);
		siderMeun.setOnClickListener(this);
		
		tweenanimation = (TextView)findViewById(R.id.textView4);
		tweenanimation.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.textView1:
			startActivity(new Intent(MainActivity.this,SiderMeunActivity.class));
			break;
			
		case R.id.textView4:
			startActivity(new Intent(MainActivity.this,TweenAnimationActivity.class));
			break;
		}
		
		
	}

}
