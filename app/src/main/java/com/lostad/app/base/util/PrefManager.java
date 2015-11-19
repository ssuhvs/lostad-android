package com.lostad.app.base.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 本地数据存维护（用户信息在SysManager中） SharedPreferences中的 健值对 尽量放在这个类中维护，不要出现在其它文件中，
 * 其它类通过 setter getter 来调用，以避免拼写key时出现错误。
 * 
 * @author Administrator
 * 
 */
public class PrefManager {

	public static SharedPreferences getSharedPreferences_PARAM(Context ctx) {
		return ctx.getSharedPreferences("_PARAM_", 0);
	}

	public static SharedPreferences getSharedPreferences_PACKAGE(Activity ctx) {
		return ctx.getSharedPreferences(ctx.getPackageName(), 0);
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////
	//
	//
	// ///////////////////////////////////////////////////////////////////////////////////////////

	public static boolean getBoolean(Context ctx,String key,boolean defaultValue) {
		SharedPreferences s = getSharedPreferences_PARAM(ctx);
		return s.getBoolean(key, defaultValue);
	}

	public static void saveBoolean(Context ctx, String key,boolean value) {
		SharedPreferences share = getSharedPreferences_PARAM(ctx);
		Editor ed = share.edit();
		ed.putBoolean(key, value);
		ed.commit();
	}
	

	public static boolean isFirstTime(Activity ctx) {
		SharedPreferences s = getSharedPreferences_PACKAGE(ctx);
		boolean is = s.getBoolean("_firstTime_", true);
		// 立刻设置为false
		Editor ed = s.edit();
		ed.putBoolean("_firstTime_", false);
		ed.commit();
		// TODO
		return is;
	}

	public static void saveString(Context ctx, String key, String value) {
		SharedPreferences share = getSharedPreferences_PARAM(ctx);
		Editor ed = share.edit();
		ed.putString(key, value);
		ed.commit();
	}

	public static void saveInt(Context ctx, String key, int value) {
		SharedPreferences share = getSharedPreferences_PARAM(ctx);
		Editor ed = share.edit();
		ed.putInt(key, value);
		ed.commit();
	}

	public static void saveFloat(Context ctx, String key, Float value) {
		SharedPreferences share = getSharedPreferences_PARAM(ctx);
		Editor ed = share.edit();
		ed.putFloat(key, value);
		ed.commit();
	}

	public static void clear(Context ctx) {
		SharedPreferences share = getSharedPreferences_PARAM(ctx);
		Editor ed = share.edit();
		ed.clear();
		ed.commit();
	}

	public static String getString(Context ctx, String key) {
		SharedPreferences share = getSharedPreferences_PARAM(ctx);
		return share.getString(key, "");
	}
	public static Float getFloat(Context ctx, String key) {
		SharedPreferences share = getSharedPreferences_PARAM(ctx);
		return share.getFloat(key,0);
	}
	public static String getString(Context ctx, String key,String defaultValue) {
		SharedPreferences share = getSharedPreferences_PARAM(ctx);
		return share.getString(key, defaultValue);
	}

	public static int getInt(Context ctx, String key) {
		SharedPreferences share = getSharedPreferences_PARAM(ctx);
		return share.getInt(key, 0);
	}
	

	
}
