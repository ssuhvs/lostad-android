package com.lostad.applib.view;
import com.lostad.applib.BaseApplication;
import com.lostad.applib.entity.ILoginConfig;

/**
 * Activity帮助支持类接口.
 * 
 * @author shimiso
 */
public interface IActivitySupport {

	BaseApplication getBaseApplication();
	boolean validateInternet();
	void checkMemoryCard();

	void showToast(String text, int longint);

	void showToast(String text);

	ILoginConfig getLoginConfig();
	//是否连网状态
	boolean hasInternetConnected();
	/**
	 * 
	 * 发出Notification的method.
	 * @param iconId       图标
	 * @param contentTitle 标题
	 * @param contentText  你内容
	 * @param activity
	 *
	 */
	void setNotiType(int iconId, String contentTitle,
					 String contentText, Class activity, String from);
}
