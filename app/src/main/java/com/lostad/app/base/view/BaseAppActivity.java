package com.lostad.app.base.view;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.lidroid.xutils.DbUtils;
import com.lostad.app.demo.LoginActivity;
import com.lostad.app.demo.MainActivity;
import com.lostad.app.demo.R;
import com.lostad.applib.BaseApplication;
import com.lostad.applib.entity.ILoginConfig;


public class BaseAppActivity extends ActivitySupport implements IActivitySupport{

	protected BaseAppActivity ctx = null;
	protected BaseApplication mBaseApplication;
	protected ProgressDialog pg = null;
	protected NotificationManager notificationManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ctx = this;
		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		pg = new ProgressDialog(context);
		mBaseApplication = (BaseApplication) getApplication();
		mBaseApplication.addActivity(this);
		setSystemBarStyle(R.color.red);
	}

//	public void showHomeButton(){
//		ActionBar actionBar = getActionBar();
//		actionBar.setDisplayShowHomeEnabled(false);//
//		actionBar.setHomeButtonEnabled(true);
//		actionBar.setDisplayHomeAsUpEnabled(true);
//		//actionBar.setBackgroundDrawable(R.mipmap.bg_title);
//	}
	

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

	public void setContentView(View view, LayoutParams params) {
		super.setContentView(view, params);
	}


	public void setContentView(View view) {
		super.setContentView(view);
	}
	


	@Override
	public BaseApplication getBaseApplication() {
		return (BaseApplication)getApplication();
	}


	@Override
	public boolean hasInternetConnected() {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(CONNECTIVITY_SERVICE);
		if (manager != null) {
			NetworkInfo network = manager.getActiveNetworkInfo();
			if (network != null && network.isConnectedOrConnecting()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean validateInternet() {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(CONNECTIVITY_SERVICE);
		if (manager == null) {
			openWirelessSet();
			return false;
		} else {
			NetworkInfo[] info = manager.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		openWirelessSet();
		return false;
	}

	@Override
	public boolean hasLocationGPS() {
		LocationManager manager = (LocationManager) context
				.getSystemService(LOCATION_SERVICE);
		return manager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	@Override
	public boolean hasLocationNetWork() {
		LocationManager manager = (LocationManager) context
				.getSystemService(LOCATION_SERVICE);
		return manager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	}

	@Override
	public void checkMemoryCard() {
		if (!Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			new AlertDialog.Builder(context)
					.setTitle(R.string.prompt)
					.setMessage("请检查内存卡")
					.setPositiveButton(R.string.menu_settings,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.cancel();
									Intent intent = new Intent(
											Settings.ACTION_SETTINGS);
									context.startActivity(intent);
								}
							})
					.setNegativeButton("退出",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.cancel();
									mBaseApplication.quit(false);
								}
							}).create().show();
		}
	}

	public void openWirelessSet() {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
		dialogBuilder
				.setTitle(R.string.prompt)
				.setMessage(context.getString(R.string.check_connection))
				.setPositiveButton(R.string.menu_settings,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
								Intent intent = new Intent(
										Settings.ACTION_WIRELESS_SETTINGS);
								context.startActivity(intent);
							}
						})
				.setNegativeButton(R.string.close,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int whichButton) {
								dialog.cancel();
							}
						});
		dialogBuilder.show();
	}

	/**
	 * 
	 * 显示toast
	 * 
	 * @param text
	 * @param longint
	 * @author shimiso
	 * @update 2012-6-28 下午3:46:18
	 */
	public void showToast(String text, int longint) {
		Toast.makeText(this, text, longint).show();
	}

	@Override
	public void showToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}
	@Override
	public ProgressDialog getProgressDialog() {
		return pg;
	}

	@Override
	public Context getContext() {
		return this;
	}

	@Override
	public ILoginConfig getLoginConfig() {
		return getBaseApplication().getLoginConfig();
	}

	@Override
	public DbUtils getDb() {
		return getBaseApplication().getDb();
	}

	@Override
	public void setNotiType(int iconId, String contentTitle,
			String contentText, Class activity, String from) {
		// TODO Auto-generated method stub
		
	}
	
	////////////////////////////////////////////////////////////////////////
	//导航条渐变
	//
    ////////////////////////////////////////////////////////////////////////
	  public void setSystemBarStyle(Integer colorId){
		  if(colorId==null){
			  setSystemBarStyle(R.color.red);
		  }
	  }
	    /**
	     * 设置导航渐变样式
	     * @param colorId
	     */
	    public void setSystemBarStyle(int colorId){
	        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
	            setTranslucentStatus(true);
	        }
	        SystemBarTintManager tintManager = new SystemBarTintManager(this);
	        tintManager.setStatusBarTintEnabled(true);
	        tintManager.setStatusBarTintResource(colorId);//通知栏所需颜色
	    }
	    @TargetApi(19) 
	    private void setTranslucentStatus(boolean on) {
	        Window win = getWindow();
	        WindowManager.LayoutParams winParams = win.getAttributes();
	        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
	        if (on) {
	            winParams.flags |= bits;
	        } else {
	            winParams.flags &= ~bits;
	        }
	        win.setAttributes(winParams);
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
		Intent intent = new Intent(ctx, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	public void  toLoginActivty(){
		Intent intent = new Intent(ctx, LoginActivity.class);
		startActivity(intent);
	}
}
