package com.lostad.app.demo.task;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.lostad.app.base.util.DialogUtil;
import com.lostad.app.base.util.LogMe;
import com.lostad.app.demo.MyApplication;
import com.lostad.app.demo.entity.LoginConfig;
import com.lostad.app.demo.entity.UserInfo;
import com.lostad.app.demo.entity.UserInfo4j;
import com.lostad.app.demo.manager.UserManager;
import com.lostad.applib.core.MyCallback;

/**
 * 
 * 登录异步任务.
 * 
 * @author sszvip
 */
@SuppressLint("NewApi")
public class LoginTask extends AsyncTask<String, Integer, Integer> {
	private Activity ctx;
	private LoginConfig mLoginConfig;
    private MyApplication mApp;
    private UserInfo4j mU4j;
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
	protected Integer doInBackground(String... params) {
		mU4j = UserManager.getInstance().login(mLoginConfig.phone,mLoginConfig.pwd);
		 return 0;
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
	}

	@Override
	protected void onPostExecute(Integer result) {
		DialogUtil.dismissProgress();
		if(myCallback!=null){
			myCallback.onCallback(mU4j.isSuccess());
		}
		if(mU4j.isSuccess()){//登录业务系统成功
			boolean success = saveLoginConfig(mU4j.data);
            if(!success){
				LogMe.e("登陆信息保存失败！！！！");
			}

		}else{
			Toast.makeText(ctx, mU4j.getMsg(), Toast.LENGTH_SHORT).show();
//			Intent i = new Intent(ctx,LoginActivity.class);
//			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//			ctx.startActivity(i);
		}
	}

	private boolean saveLoginConfig(UserInfo u) {
		boolean success = false;
		try{
			mLoginConfig.setId(u.getUserId());
			mLoginConfig.setName(u.name);
			mLoginConfig.setNickname(u.nickname);

			mApp.saveLoginConfig(mLoginConfig);
			mApp.getDb().saveOrUpdate(u);
			success = true;
		}catch (Exception e){
			e.printStackTrace();
		}


		return success;
	}

}
