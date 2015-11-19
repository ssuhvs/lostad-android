package com.lostad.applib.util;

import com.lostad.applib.util.DateUtil;
import com.lostad.applib.util.LogMe;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
public class FileDataUtil {
	/**
	     * 文件转化为字节数组
	     * @Author Sean.guo
	     * @EditTime 2007-8-13 上午11:45:28
	     */
	    public static byte[] getBytesFromFile(File f) {
	        if (f == null) {
	            return null;
	        }
	        try {
	            FileInputStream stream = new FileInputStream(f);
	            ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
	            byte[] b = new byte[1000];
	            int n;
	            while ((n = stream.read(b)) != -1)
	                out.write(b, 0, n);
	            stream.close();
	            out.close();
	            return out.toByteArray();
	        } catch (IOException e) {
	        }
	        return null;
	    }
	    
	    
		public static String createJpgFileName(String prefix) {
			String fileName = prefix+"_"+ DateUtil.getCurrDateStr("yyyyMMddHHmmss")+".jpg";
			LogMe.d(fileName);
			return fileName;
		}

}

