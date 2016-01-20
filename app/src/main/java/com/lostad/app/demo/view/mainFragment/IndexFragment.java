package com.lostad.app.demo.view.mainFragment;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.lostad.app.base.view.fragment.BaseFragment;
import com.lostad.app.base.view.widget.MyScrollView;
import com.lostad.app.demo.MyApplication;
import com.lostad.app.demo.R;
import com.lostad.applib.entity.ILoginConfig;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * viewFlipper 案例
 * @author sszvip
 * 
 */
public class IndexFragment extends BaseFragment implements OnGestureListener, OnTouchListener{

	private MyApplication mApp;

	@ViewInject(R.id.ll_points)
	private View ll_points;

	@ViewInject(R.id.vf_pic)
	private ViewFlipper vf_pic;

	@ViewInject(R.id.sv_my)
	private MyScrollView sv_my;

	ILoginConfig mLogin;
	private int currentPage = 0;
	private boolean showNext = true;
	private final int SHOW_NEXT = 0011;
	private static final int FLING_MIN_DISTANCE = 50;
	private GestureDetector mGestureDetector;

	int[] mPointIds = {R.id.iv_p0, R.id.iv_p1, R.id.iv_p2};
	int[] mPicIds  = {R.id.iv_pic0, R.id.iv_pic1, R.id.iv_pic2};
	View mView ;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_view_flipper, container, false);
		x.view().inject(this, mView);
        mApp = (MyApplication)getApp();
		ll_points.getBackground().setAlpha(100);
		mGestureDetector = new GestureDetector(ctx,this);
		sv_my.setGestureDetector(mGestureDetector);
		vf_pic.setOnTouchListener(this);
		dian_select(currentPage);
		thread.start();
		//注入view
		return mView;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,float velocityY) {
		if(e1.getX() - e2.getX() >FLING_MIN_DISTANCE ){
			System.out.println("==============开始向左滑动了================");
			showNextView();
			showNext = true;

		}else if(e2.getX() - e1.getX()>FLING_MIN_DISTANCE ){
			System.out.println("==============开始向右滑动了================");
			showPreviousView();
			showNext = true;

		}
		return false;
	}

	private void showNextView() {
		vf_pic.setInAnimation(AnimationUtils.loadAnimation(ctx, R.anim.push_left_in));
		vf_pic.setOutAnimation(AnimationUtils.loadAnimation(ctx, R.anim.push_left_out));
		vf_pic.showNext();
		currentPage++;
		if(currentPage == vf_pic.getChildCount()){
			dian_unselect(currentPage-1);
			currentPage = 0;
			dian_select(currentPage);
		}else{
			dian_select(currentPage);
			dian_unselect(currentPage-1);
		}
	}

	private void showPreviousView() {
		dian_select(currentPage);
		vf_pic.setInAnimation(AnimationUtils.loadAnimation(ctx, R.anim.push_right_in));
		vf_pic.setOutAnimation(AnimationUtils.loadAnimation(ctx, R.anim.push_right_out));
		vf_pic.showPrevious();
		currentPage--;
		if(currentPage == -1){
			dian_unselect(currentPage + 1);
			currentPage = vf_pic.getChildCount()-1;
			dian_select(currentPage);
		}else{
			dian_select(currentPage);
			dian_unselect(currentPage+1);
		}
	}
	/**
	 * 对应被选中的点的图片
	 * @param index
	 */
	private void dian_select(int index) {
		ImageView img = (ImageView)mView.findViewById(mPointIds[index]);
		img.setSelected(true);
	}
	/**
	 * 对应未被选中的点的图片
	 * @param id
	 */
	private void dian_unselect(int id){
		ImageView img = (ImageView)mView.findViewById(mPointIds[id]);
		img.setSelected(false);
	}

	@Override
	public void onLongPress(MotionEvent arg0) {
	}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
							float arg3) {
		return false;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent arg0) {
	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return mGestureDetector.onTouchEvent(event);
	}

	@Override
	public void onDestroy() {
		isRun = false;
		super.onDestroy();
	}

	Thread thread = new Thread(){
		@Override
		public void run() {
			while(isRun){
				try {
					Thread.sleep(3800);
					if(isRun){
						ctx.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								showNextView();
							}
						});
					}

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	};

	private boolean isRun = true;
}
