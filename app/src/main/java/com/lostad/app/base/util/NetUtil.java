package com.lostad.app.base.util;

import android.content.Context;

/**
 * @author gaobin
 */
public class NetUtil {

	/**
	 * 判断是否可以访问网络
	 * @param context
	 * @return
	 */
	public static Boolean isNetWork(Context context) {
//		ConnectivityManager cm = (ConnectivityManager) context
//				.getSystemService(Context.CONNECTIVITY_SERVICE);
//		if(cm != null){
//			NetworkInfo[] info = cm.getAllNetworkInfo();
//			for(NetworkInfo n:info){
//				if(n.getState() == NetworkInfo.State.CONNECTED){
//					return true;
//				}
//			}
//		}
		return true;
	}

	
//	public static WifiInfo getWinifManager(Context context) {
//		WifiManager m=(WifiManager)context.getSystemService(Context.WIFI_SERVICE);
//		WifiInfo mWifiInfo=m.getConnectionInfo();
//		return mWifiInfo;
//	}
//

}
