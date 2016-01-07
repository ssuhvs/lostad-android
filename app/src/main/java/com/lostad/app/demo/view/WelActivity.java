package com.lostad.app.demo.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lostad.app.demo.R;

/**
 *
 */
public class WelActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wel);
		toMain();
	}

	private void toMain(){
         new Thread(){
			 @Override
			 public void run() {
				 try {
					 sleep(1000);
				 } catch (InterruptedException e) {
					 e.printStackTrace();
				 }

				 Intent i = new Intent(WelActivity.this,MainActivity.class);
				 startActivity(i);
				 finish();
			 }
		 }.start();
	}

//	private void login() {
//		new Thread(){
//			LoginConfig mLoginConfig;
//			public void run() {
//				try {
//					mLoginConfig = (LoginConfig)getLoginConfig();
//					///sleep(2000);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//
//				runOnUiThread(new Runnable() {
//					@Override
//					public void run() {
//						if(mLoginConfig!=null){
//							LoginTask loginTask = new LoginTask(WelActivity.this,mLoginConfig);
//							loginTask.execute();
//						}else{
//							Intent intent = new Intent(WelActivity.this, LoginActivity.class);
//							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//							startActivity(intent);
//						}
//					}
//				});
//			};
//		}.start();
//	}
//
//	private void showAlert(final Versioninfo v) {
//		Dialog dialog = new AlertDialog.Builder(this).setTitle("升级提示")
//				.setCancelable(false)
//				.setMessage("发现新版本:\n"+v.releaseNotes +"\n 是否进行升级？")// 设置内容
//				.setPositiveButton("是",// 设置确定按钮
//
//						new DialogInterface.OnClickListener() {
//							public void onClick(DialogInterface dialog,	int which) {
//								toUpdate(v,"升级中");
//							}
//
//
//
//						}).setNegativeButton("否",
//						new DialogInterface.OnClickListener() {
//							public void onClick(DialogInterface dialog,
//									int whichButton) {
//								login();
//							}
//						}).show();
//	}
//
//
//	private void toUpdate(final Versioninfo v,String title) {
//		Intent i = new Intent(WelActivity.this,AppUpdateAcitvity.class);
//		i.putExtra("bean", v);
//		i.putExtra("title", title);
//		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//		startActivity(i);
//		finish();
//	}
	
}
