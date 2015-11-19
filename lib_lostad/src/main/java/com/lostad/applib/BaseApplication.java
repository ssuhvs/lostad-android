package com.lostad.applib;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.lidroid.xutils.DbUtils;
import com.lostad.applib.entity.ILoginConfig;

import java.util.LinkedList;
import java.util.List;
public abstract class BaseApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		//initImageLoader(this);
	}

	private List<Activity> activityList = new LinkedList<Activity>();
	// 添加Activity到容器中
	public void addActivity(Activity activity) {
		activityList.add(activity);
	}
	public void popupActivity(Activity activity) {
		activityList.remove(activity);
	}

	// 遍历所有Activity并finish
	public void clearActivity() {
		for (Activity activity : activityList) {
			activity.finish();
		}
		activityList.clear();
	}


	public abstract void quit(boolean isClearData);
	public abstract void startService();
	public abstract void stopService();
	public abstract DbUtils getDb();
	public abstract ILoginConfig getLoginConfig();
	public abstract void saveLoginConfig(ILoginConfig mLoginConfig);
}
