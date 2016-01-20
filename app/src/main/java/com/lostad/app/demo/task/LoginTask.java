package com.lostad.app.demo.task;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.lostad.app.demo.MyApplication;
import com.lostad.app.demo.entity.LoginConfig;
import com.lostad.app.demo.entity.LoginConfig4j;
import com.lostad.app.demo.manager.UserManager;
import com.lostad.applib.core.MyCallback;
import com.lostad.applib.util.DialogUtil;

/**
 * 
 * 登录异步任务.
 * 
 * @author sszvip
 */
@SuppressLint("NewApi")
public class LoginTask extends AsyncTask<String, Integer, LoginConfig4j> {
	private Activity ctx;
	private LoginConfig mLoginConfig;
    private MyApplication mApp;

	private MyCallback myCallback;

	public LoginTask(Activity activitySupport, LoginConfig loginConfig,MyCallback<Boolean> callback) {
		this.ctx = activitySupport;
		this.mLoginConfig = loginConfig;
		mApp = (MyApplication)ctx.getApplication();
        this.myCallback = callback;
	}
	@Override
	protected void onPreExecute() {
		DialogUtil.showProgress(ctx);
		super.onPreExecute();
	}

	@Override
	protected LoginConfig4j doInBackground(String... params) {

		LoginConfig4j mU4j = UserManager.getInstance().login(mLoginConfig.phone,mLoginConfig.pwd);
		return mU4j;
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
	}

	@Override
	protected void onPostExecute(LoginConfig4j mU4j) {
		DialogUtil.dismissProgress();
		if(myCallback!=null){
			myCallback.onCallback(mU4j.isSuccess());
		}
		if(mU4j.isSuccess()){//登录业务系统成功
			LoginConfig u = mU4j.data;
			u.setPwd(mLoginConfig.getPwd());
			mApp.saveLoginConfig(mLoginConfig);
		}else{
			Toast.makeText(ctx, mU4j.getMsg(), Toast.LENGTH_SHORT).show();
//			Intent i = new Intent(ctx,LoginActivity.class);
//			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//			ctx.startActivity(i);
		}
	}

	private boolean saveLoginConfig(LoginConfig u) {
		boolean success = false;
		try{


			success = true;
		}catch (Exception e){
			e.printStackTrace();
		}


		return success;
	}

}
