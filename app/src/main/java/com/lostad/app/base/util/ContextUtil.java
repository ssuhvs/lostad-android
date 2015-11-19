package com.lostad.app.base.util;

import android.content.Context;
/**
 * 窗口工具类,提供可重用的窗口
 * @author sszvip
 *
 */
public class ContextUtil {

	public static String getRunningActivityName(Context ctx) {
		String contextString = ctx.toString();
		return contextString.substring(contextString.lastIndexOf(".") + 1,contextString.indexOf("@"));
	}
	
	
}
