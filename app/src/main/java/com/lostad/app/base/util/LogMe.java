package com.lostad.app.base.util;

import android.os.Environment;
import android.util.Log;

import com.lostad.applib.Config;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Date;


/**
 * 日志类，可以设设置是否输出到logcat、sd卡。
 * @author sszvip@qq.com
 * @since  2014-7-2
 */
public class LogMe {

	public static void d(Object msg) {
        if(msg==null){
        	if (Config.isTestMode) {
        	Log.d("LogMe:", " >>>> msg is null !!!");
        	}
        	return ;
        }
		if (Config.isTestMode) {
			Log.d("LogMe:", msg.toString());
		}

		if(Config.isWrittenToSD){
			writeFileToSD("--------》"+msg.toString());
		}
	}

	public static void e(Object msg) {
		Log.e("LogMe:", msg+"");
		if(Config.isWrittenToSD){
			writeFileToSD("--------》"+msg.toString());
		}
	}

	public static void e(String tag,Object msg) {
		Log.e("LogMe:"+tag, msg+"");
		if(Config.isWrittenToSD){
			writeFileToSD("--------》"+msg.toString());
		}
	}

	
	public static void d(String tag, Object msg) {

		if (Config.isTestMode) {
			Log.d(tag, ""+msg);
		}
		if(Config.isWrittenToSD){
			writeFileToSD(tag+"--------》"+msg);
		}
	}
	
	
	public static boolean writeFileToSD(String info) {
		boolean success = true;
		info +=new Date() ;
		// 使用RandomAccessFile 写文件 还是蛮好用的..推荐给大家使用...
		String sdStatus = Environment.getExternalStorageState();
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
			Log.d("TestFile", "SD card is not avaiable/writeable right now.");
			return false;
		}
		
		String pathName = Environment.getExternalStorageDirectory()+"/";
		String fileName = "1_log.txt";
		try {
			File dir = new File(pathName);
			if (!dir.exists()) {
				dir.mkdir();
			}
			File file = new File(pathName + "/" + fileName);
			if (!file.exists()) {
				Log.d("TestFile", "Create the file:" + fileName);
				file.createNewFile();
			}
			RandomAccessFile raf = new RandomAccessFile(file, "rw");
			raf.seek(file.length());
			
			info+="\n";
			raf.write(info.getBytes());
			raf.close();
			// 注释的也是写文件..但是每次写入都会把之前的覆盖..
			/*
			 * String pathName = "/sdcard/"; String fileName = "log.txt"; File
			 * path = new File(pathName); File file = new File(pathName +
			 * fileName); if (!path.exists()) { Log.d("TestFile",
			 * "Create the path:" + pathName); path.mkdir(); } if
			 * (!file.exists()) { Log.d("TestFile", "Create the file:" +
			 * fileName); file.createNewFile(); } FileOutputStream stream = new
			 * FileOutputStream(file); String s = context; byte[] buf =
			 * s.getBytes(); stream.write(buf); stream.close();
			 */

		} catch (Exception e) {
			Log.e("TestFile", "Error on writeFilToSD.");
			success = false;
		}
		return success;
	}

	
}
