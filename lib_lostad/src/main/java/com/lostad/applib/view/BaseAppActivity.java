package com.lostad.applib.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.lostad.applib.R;
import com.lostad.applib.util.DialogUtil;


public class BaseAppActivity extends ActivitySupport implements IActivitySupport{

	protected BaseAppActivity ctx = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ctx = this;
		setSystemBarStyle(R.color.bg_title);
	}

	protected void initToolBar(Toolbar tb_toolbar) {
		if(tb_toolbar!=null) {
			tb_toolbar.setTitleTextColor(getResources().getColor(R.color.bg_title));
			setSupportActionBar(tb_toolbar);
			tb_toolbar.setTitleTextColor(Color.WHITE);
		}else{
			DialogUtil.showToastCust(this, R.string.msg_toolbar_no_found);
		}
	}
	protected void initToolBarWithBack(Toolbar tb_toolbar) {
		if(tb_toolbar!=null){
			tb_toolbar.setTitleTextColor(getResources().getColor(R.color.bg_title));
			setSupportActionBar(tb_toolbar);
			tb_toolbar.setNavigationIcon(R.drawable.ic_action_back);
			tb_toolbar.setTitleTextColor(Color.WHITE);
		}else{
			DialogUtil.showToastCust(this,R.string.msg_toolbar_no_found);
		}

	}
	protected void initToolBarWithBack(Toolbar tb_toolbar,String title) {
		if(tb_toolbar!=null){
			tb_toolbar.setTitleTextColor(getResources().getColor(R.color.bg_title));
			setSupportActionBar(tb_toolbar);
			tb_toolbar.setNavigationIcon(R.drawable.ic_action_back);
			tb_toolbar.setTitleTextColor(Color.WHITE);
			super.setTitle(title);
		}else{
			DialogUtil.showToastCust(this,R.string.msg_toolbar_no_found);
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if(item.getItemId()==android.R.id.home){
			finish();
		}

		return super.onOptionsItemSelected(item);
	}

	    
/////////////////////////////////////////////////////////////////////////
// 退出事件
//
///////////////////////////////////////////////////////////////////////////

		public boolean onKeyDown(int keyCode, KeyEvent event) {
//			if (keyCode == KeyEvent.KEYCODE_BACK) {
//				super.quitApp();
//				return false;
//			}
			return super.onKeyDown(keyCode, event);
		}

		public void quitApp() {
			if (!isExit) {
				isExit = true;
				Toast.makeText(getApplicationContext(), "再按一次退出程序",Toast.LENGTH_SHORT).show();
				mHandler.sendEmptyMessageDelayed(0, 2000);
			} else {
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
				startActivity(intent);
				finish();
			}
		}

		Handler mHandler = new Handler() {
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				isExit = false;
			}

		};
		boolean isExit;
}
