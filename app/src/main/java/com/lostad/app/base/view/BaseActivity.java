package com.lostad.app.base.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.lostad.app.demo.MyApplication;
import com.lostad.app.demo.view.LoginActivity;
import com.lostad.app.demo.view.MainActivity;
import com.lostad.applib.view.BaseAppActivity;


public class BaseActivity extends BaseAppActivity{
	protected  BaseActivity ctx;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ctx = this;
	}

	protected MyApplication getMyApp(){
		return MyApplication.getInstance();
	}
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.menu_main, menu);
//		return true;
//	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if(item.getItemId()==android.R.id.home){
			finish();
			return true;
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

	public void  toMainActivty(){
		Intent intent = new Intent(ctx,MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	public void  toLoginActivty(){
		Intent intent = new Intent(ctx, LoginActivity.class);
		startActivity(intent);
	}


}
