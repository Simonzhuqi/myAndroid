package com.itrun.zhuqi.ui;

import com.itrun.mywidget.R;
import com.itrun.mywidget.R.layout;
import com.itrun.mywidget.R.menu;
import com.itrun.mywidget.SlidingMenu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.Window;

public class SlidingMenuActivity extends Activity {

	private SlidingMenu slidingMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_sliding_menu);
		slidingMenu = (SlidingMenu) findViewById(R.id.slidingMenu);
		
	}
	public void toggleMenu(View view)
	{
		slidingMenu.toggle();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sliding_menu, menu);
		return true;
	}

}
