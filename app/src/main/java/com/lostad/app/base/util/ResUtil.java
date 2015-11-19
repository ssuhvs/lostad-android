package com.lostad.app.base.util;

import java.lang.reflect.Field;

import android.content.Context;
import android.util.Log;

public class ResUtil {

	/** 
	 * 功能描述:      获取lib工程的资源文件   
	 * @param:         
	 * @return:        
	 * @Author:      sszvip@qq.com
	 * @Create Date: 2015-8-10上午11:10:44
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private static Context ctx;
	public void initResUtil(Context ctx){
		ResUtil.ctx = ctx;
	}
	
	public static int  getDrawableId(Context ctx,String resName){
		Integer id = ctx.getResources().getIdentifier(resName, "drawable", ctx.getPackageName());
		return id;
	}
	
	public static int  getLayoutId(Context ctx,String layoutName){
		Integer id = ctx.getResources().getIdentifier(layoutName, "layout", ctx.getPackageName());
		return id;
	}
	
	
	public static int  getXmlId(Context ctx,String resName){
		Integer id = ctx.getResources().getIdentifier(resName, "xml", ctx.getPackageName());
		return id;
	}
	
	public static int  getResId(Context ctx,String resName,String resType){
		Integer id = ctx.getResources().getIdentifier(resName,resType, ctx.getPackageName());
		return id;
	}
	
	public static int getId(Context context, String className, String fieldName)
    {
        try
        {
        	String classz = getPackageName() + ".R$" + className;
            Class localClass = Class.forName(classz);
            Field localField = localClass.getField(fieldName);
            int i = Integer.parseInt(localField.get(localField.getName()).toString());
            return i;
        } catch (Exception localException)
        {
            Log.e("getIdByReflection error", localException.getMessage());
        }
 
        return 0;
    }

	private static String getPackageName() {
		return ctx.getPackageName();
		//return "com.lostad.app";
	}
}
