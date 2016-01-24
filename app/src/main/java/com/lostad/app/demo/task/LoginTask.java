package com.lostad.app.demo.task;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;

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

		LoginConfig4j mU4j = UserManager.getInstance().login(mLoginConfig.phone,mLoginConfig.password);
		return mU4j;
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
	}

	@Override
	protected void onPostExecute(LoginConfig4j mU4j) {
		DialogUtil.dismissProgress();
		boolean success = mU4j.isSuccess();
		if(success){//保存成功
			success = saveLoginConfig(mU4j.data);
		}else{
			DialogUtil.showToastCust(ctx, mU4j.msg);
		}

		myCallback.onCallback(success);

	}

	private boolean saveLoginConfig(LoginConfig u) {
		boolean success = false;
		try{
			u.password = mLoginConfig.password;
			mApp.saveLoginConfig(u);
			success = true;
		}catch (Exception e){
			e.printStackTrace();
			DialogUtil.showToastCust(ctx, e.getMessage());
		}

		return success;
	}

}
