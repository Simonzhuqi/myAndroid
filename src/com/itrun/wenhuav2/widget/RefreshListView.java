package com.itrun.wenhuav2.widget;

import java.text.SimpleDateFormat;
import java.util.Date;



import com.itrun.wenhuav2.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

public class RefreshListView extends ListView implements OnScrollListener{
	private View headView;
	private int downY;
	private int moveY;

	private int headheight;
	private final int PULL_DOWN_REFRESH=0;
	private final int RELEASE_REFRESH=1;
	private final int REFRESHING=2;
	private int curentState=PULL_DOWN_REFRESH;
	private ImageView headArrow;
	private ProgressBar headprogress;
	private TextView headstate;
	private TextView headlasttime;
	private Animation upAnimation;
	private Animation downAnimation;
	private OnRefreshListViewChangeListener changeListener;
	private View footView;
	private int footHeight;
	private boolean isMore=false;

	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initHeadView();
		initFootView();
		initAnimation();
		setOnScrollListener(this);

	}

	private void initHeadView() {
		headView=View.inflate(getContext(), R.layout.list_header, null);
		headArrow=(ImageView)headView.findViewById(R.id.head_arrow);
		headprogress=(ProgressBar)headView.findViewById(R.id.head_progressbar);
		headstate=(TextView)headView.findViewById(R.id.heade_state);
		headlasttime=(TextView)headView.findViewById(R.id.head_lasttime);
		headView.measure(0, 0);
		headheight=headView.getMeasuredHeight();//测高度
		headView.setPadding(0, -headheight, 0, 0);//隐藏抬头
		addHeaderView(headView);

	}
	private void initFootView() {
		footView=View.inflate(getContext(), R.layout.list_footer, null);
		footView.measure(0, 0);
		footHeight=footView.getMeasuredHeight();
		footView.setPadding(0, 0, 0, -footHeight);
		addFooterView(footView);

	}
	private void initAnimation() {
		upAnimation=new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		upAnimation.setDuration(500);
		upAnimation.setFillAfter(true);
		downAnimation=new RotateAnimation(-180, -360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		downAnimation.setDuration(500);
		downAnimation.setFillAfter(true);
	}
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch(ev.getAction()){
		case MotionEvent.ACTION_DOWN:
			downY=(int)ev.getY(); 

			break;
		case MotionEvent.ACTION_MOVE:
			if(curentState==REFRESHING){
				break;
			}
			moveY=(int)ev.getY();
			int diff=moveY-downY;
			if(diff>0&&getFirstVisiblePosition()==0){
				int paddingTop=-headheight+diff;
				if(paddingTop>0&&curentState==PULL_DOWN_REFRESH){
					curentState=RELEASE_REFRESH;
					//System.out.println("释放刷新状态");
					selectCurrentState();
				}else if(paddingTop<0&&curentState==RELEASE_REFRESH){
					curentState=PULL_DOWN_REFRESH;
					//System.out.println("下拉刷新"); 
					selectCurrentState();
				}
				headView.setPadding(0, paddingTop, 0, 0);
				//这里要自己处理滑动状态
				return true;
			}
			break;
		case MotionEvent.ACTION_UP:
			if(curentState==PULL_DOWN_REFRESH){
				headView.setPadding(0, -headheight, 0, 0);
			}else if(curentState==RELEASE_REFRESH){
				curentState=REFRESHING;
				selectCurrentState();
				headView.setPadding(0, 0, 0, 0);
				if(changeListener!=null){
					changeListener.pull_donwn_refresh();
				}

			}

		}
		return super.onTouchEvent(ev);
	}
	private void selectCurrentState() {
		switch (curentState) {
		case PULL_DOWN_REFRESH:
			headstate.setText("下拉刷新");
			headArrow.startAnimation(downAnimation);
			break;
		case RELEASE_REFRESH:
			headstate.setText("释放刷新");
			headArrow.startAnimation(upAnimation);
			break;
		case REFRESHING:
			headstate.setText("刷新中...");
			headArrow.clearAnimation();
			headArrow.setVisibility(View.INVISIBLE);
			headprogress.setVisibility(View.VISIBLE);
			break;

		default:
			break;
		}

	}
	public void setOnListenerChanger(OnRefreshListViewChangeListener listener){
		changeListener=listener;
	}
	public void hideHeadView(){
		headstate.setText("下拉刷新");
		curentState=PULL_DOWN_REFRESH;
		headArrow.setVisibility(View.VISIBLE);
		headprogress.setVisibility(View.INVISIBLE);
		headView.setPadding(0, -headheight, 0, 0);
		headlasttime.setText("最后更新时间："+getlastTime());
	}
	public void hideFootView() {
		footView.setPadding(0, 0, 0, -footHeight);
		isMore=false;

	}
	private String getlastTime() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}

	//滚动的时候调用
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {


	}
	//滚动的状态改变的时候调用
	public static int SCROLL_STATE_IDLE=0;
	public static int SCROLL_STATE_TOUCH_SCROLL=1;
	public static int SCROLL_STATE_FLING=2;
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if((scrollState==SCROLL_STATE_IDLE||scrollState==SCROLL_STATE_FLING)&&getLastVisiblePosition()==getCount()-1){
			if(!isMore){
				isMore=!isMore;
				footView.setPadding(0, 0, 0, 0);
				if(changeListener!=null){
					changeListener.loadmore();
				}
				setSelection(getCount()-1);
			}

		}

	}
}
