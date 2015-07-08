package com.itrun.mywidget;


import com.nineoldandroids.view.ViewHelper;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class SlidingMenu extends HorizontalScrollView {
	
	
	private static final int SNAP_VELOCITY = 200;
	/** 
     * 屏幕宽度 
     */  
    private int mScreenWidth;  
    /** 
     * dp 
     */  
    private int mMenuRightPadding = 50;  
    /** 
     * 菜单的宽度 
     */  
    private int mMenuWidth;  
    private int mHalfMenuWidth;  
  
    private boolean once;
	private boolean isOpen;
	private ViewGroup menu;
	private ViewGroup content;  
  
	private VelocityTracker mVelocityTracker; //用于计算手指滑动的速度
    
	public SlidingMenu(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);

	}
    
    public SlidingMenu(Context context, AttributeSet attrs,int defStyle)  
    {  
        super(context, attrs);  
        WindowManager window  = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		window.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels; 
    } 
    
	public SlidingMenu(Context context)
	{
		this(context, null, 0);
	}
  
    @Override  
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)  
    {  
        /** 
         * 显示的设置一个宽度 
         */  
        if (!once)  
        {  
        	LinearLayout wrapper = (LinearLayout) getChildAt(0);
        	menu = (ViewGroup) wrapper.getChildAt(0);
        	content = (ViewGroup) wrapper.getChildAt(1);
            // dp to px  
            mMenuRightPadding = (int) TypedValue.applyDimension(  
                    TypedValue.COMPLEX_UNIT_DIP, mMenuRightPadding, content  
                            .getResources().getDisplayMetrics());  
  
            mMenuWidth = mScreenWidth - mMenuRightPadding;  
            mHalfMenuWidth = mMenuWidth / 2;  
            menu.getLayoutParams().width = mMenuWidth;  
            content.getLayoutParams().width = mScreenWidth;  
  
        }  
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);  
  
    }  
  
    @Override  
    protected void onLayout(boolean changed, int l, int t, int r, int b)  
    {  
        super.onLayout(changed, l, t, r, b);  
        if (changed)  
        {  
            // 将菜单隐藏  
            this.scrollTo(mMenuWidth, 0);  
            once = true;  
        }  
    }  
  
    @Override  
    public boolean onTouchEvent(MotionEvent ev)  {  
    	createVelocityTracker(ev); 
        int action = ev.getAction();  
        switch (action)  {  
        // Up时，进行判断，如果显示区域大于菜单宽度一半则完全显示，否则隐藏  
        case MotionEvent.ACTION_UP:  
            int scrollX = getScrollX();  
            if (scrollX > mHalfMenuWidth || getScrollVelocity()>SNAP_VELOCITY)  {
                this.smoothScrollTo(mMenuWidth, 0); 
            	isOpen = false;
            }
            else{  
                this.smoothScrollTo(0, 0);
                isOpen = true;
            }
            recycleVelocityTracker();  
            return true;  
        }  
        return super.onTouchEvent(ev);  
    }  
    public void openMenu()
	{
		if (isOpen)
			return;
		this.smoothScrollTo(0, 0);
		isOpen = true;
	}
    public void closeMenu()
	{
		if (isOpen)
		{
			this.smoothScrollTo(mMenuWidth, 0);
			isOpen = false;
		}
	}
    public void toggle()
	{
		if (isOpen)
		{
			closeMenu();
		} else
		{
			openMenu();
		}
	}
    @Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt)
	{
		super.onScrollChanged(l, t, oldl, oldt);
		float scale = l * 1.0f / mMenuWidth;
		float leftScale = 1 - 0.3f * scale;
		float rightScale = 0.8f + scale * 0.2f;
		ViewHelper.setScaleX(menu, leftScale);
		ViewHelper.setScaleY(menu, leftScale);
		ViewHelper.setAlpha(menu, 0.6f + 0.4f * (1 - scale));
		ViewHelper.setTranslationX(menu, mMenuWidth * scale * 0.7f);

		ViewHelper.setPivotX(content, 0);
		ViewHelper.setPivotY(content, content.getHeight() / 2);
		ViewHelper.setScaleX(content, rightScale);
		ViewHelper.setScaleY(content, rightScale);
		
		
	}

    /** 
     * 创建VelocityTracker对象，并将触摸content界面的滑动事件加入到VelocityTracker当中。 
     *  
     * @param event 
     *            content界面的滑动事件 
     */  
    private void createVelocityTracker(MotionEvent event) {  
        if (mVelocityTracker == null) {  
            mVelocityTracker = VelocityTracker.obtain();  
        }  
        mVelocityTracker.addMovement(event);  
    }  
  
    /** 
     * 获取手指在content界面滑动的速度。 
     *  
     * @return 滑动速度，以每秒钟移动了多少像素值为单位。 
     */  
    private int getScrollVelocity() {  
        mVelocityTracker.computeCurrentVelocity(1000);  
        int velocity = (int) mVelocityTracker.getXVelocity();  
        return Math.abs(velocity);  
    }  
  
    /** 
     * 回收VelocityTracker对象。 
     */  
    private void recycleVelocityTracker() {  
        mVelocityTracker.recycle();  
        mVelocityTracker = null;  
    } 
}
