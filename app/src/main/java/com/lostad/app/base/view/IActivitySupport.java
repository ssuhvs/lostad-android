package com.lostad.app.base.view;


import android.app.ProgressDialog;
import android.content.Context;

import com.lidroid.xutils.DbUtils;
import com.lostad.applib.BaseApplication;
import com.lostad.applib.entity.ILoginConfig;


/**
 * Activity帮助支持类接口.
 * 
 * @author sszvip
 */
public interface IActivitySupport {
	/**
	 * 
	 * 获取EimApplication.
	 * 
	 * @author sszvip
	 * @update 2012-7-6 上午9:05:51
	 */
	BaseApplication getBaseApplication();

	
	/**
	 * 
	 * 校验网络-如果没有网络就弹出设置,并返回true.
	 * 
	 * @return
	 * @author sszvip
	 * @update 2012-7-6 上午9:03:56
	 */
	boolean validateInternet();

	/**
	 * 
	 * 校验网络-如果没有网络就返回true.
	 * 
	 * @return
	 * @author sszvip
	 * @update 2012-7-6 上午9:05:15
	 */
	boolean hasInternetConnected();

	/**
	 * 
	 * 判断GPS是否已经开启.
	 * 
	 * @return
	 * @author sszvip
	 * @update 2012-7-6 上午9:04:07
	 */
	boolean hasLocationGPS();

	/**
	 * 
	 * 判断基站是否已经开启.
	 * 
	 * @return
	 * @author sszvip
	 * @update 2012-7-6 上午9:07:34
	 */
	boolean hasLocationNetWork();

	/**
	 * 
	 * 检查内存卡.
	 * 
	 * @author sszvip
	 * @update 2012-7-6 上午9:07:51
	 */
	void checkMemoryCard();

	/**
	 * 
	 * 显示toast.
	 * 
	 * @param text
	 *            内容
	 * @param longint
	 *            内容显示多长时间
	 * @author sszvip
	 * @update 2012-7-6 上午9:12:02
	 */
	void showToast(String text, int longint);

	/**
	 * 
	 * 短时间显示toast.
	 * 
	 * @param text
	 * @author sszvip
	 * @update 2012-7-6 上午9:12:46
	 */
	void showToast(String text);

	/**
	 * 
	 * 获取进度条.
	 * 
	 * @return
	 * @author sszvip
	 * @update 2012-7-6 上午9:14:38
	 */
	ProgressDialog getProgressDialog();

	/**
	 * 
	 * 返回当前Activity上下文.
	 * 
	 * @return
	 * @author sszvip
	 * @update 2012-7-6 上午9:19:54
	 */
	Context getContext();

	/**
	 * 
	 * 获取用户配置.
	 * 
	 * @param 
	 * @author sszvip
	 * @update 2012-7-6 上午9:59:49
	 */
	ILoginConfig getLoginConfig();
	DbUtils getDb();
	/**
	 * 
	 * 发出Notification的method.
	 * 
	 * @param iconId
	 *            图标
	 * @param contentTitle
	 *            标题
	 * @param contentText
	 *            你内容
	 * @param activity
	 * @author sszvip
	 * @update 2012-5-14 下午12:01:55
	 */
	void setNotiType(int iconId, String contentTitle,
					 String contentText, Class activity, String from);
}
