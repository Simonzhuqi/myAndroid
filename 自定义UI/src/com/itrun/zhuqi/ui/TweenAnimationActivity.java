package com.itrun.zhuqi.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.itrun.mywidget.R;

public class TweenAnimationActivity extends Activity {
	private ImageView imageView; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tween_animation);
		imageView = (ImageView) findViewById(R.id.imageView1); 
	}
	
	// ͸������  
    public void alphaImpl(View v) {  
  
        Animation animation = AnimationUtils.loadAnimation(this,  
                R.anim.alpha_demo);  
        imageView.startAnimation(animation);  
    }  
  
    // ��ת����  
    public void rotateImpl(View v) {  
        Animation animation = AnimationUtils.loadAnimation(this,  
                R.anim.rotate_demo);  
        imageView.startAnimation(animation);  
    }  
  
    // ���Ŷ���  
    public void scaleImpl(View v) {  
        Animation animation = AnimationUtils.loadAnimation(this,  
                R.anim.scale_demo);  
        imageView.startAnimation(animation);  
    }  
  
    // �ƶ�Ч��  
    public void translateImpl(View v) {  
        // XML�ļ�  
        Animation animation = AnimationUtils.loadAnimation(this,  
                R.anim.translate_demo);  
  
        animation.setRepeatCount(Animation.INFINITE);//ѭ����ʾ  
        imageView.startAnimation(animation);  
  
        /* 
         * ��һ�� imageView.setAnimation(animation); animation.start(); 
         */  
        // �ڶ���  
  
        // Java����  
        /* 
         * TranslateAnimation translateAnimation = new TranslateAnimation(0, 
         * 200, 0, 0); translateAnimation.setDuration(2000); 
         * imageView.startAnimation(translateAnimation); 
         */  
    }  
  
    // �ۺ�ʵ��set_demo.xml�еĶ���  
    public void setAll(View v) {  
        Animation animation = AnimationUtils.loadAnimation(this,  
                R.anim.set_demo);  
        imageView.startAnimation(animation);  
    }  
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tween_animation, menu);
		return true;
	}

}
