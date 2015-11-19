package com.lostad.applib.util;

import java.text.NumberFormat;

/**
 * @author gaobin
 */
public class NumUtil {

	/**
	 * 判断是否可以访问网络
	 * @param context
	 * @return
	 */
	public static String format(Double x,Integer maximumFractionDigits) {
		String s= null;
		try{
			NumberFormat ddf1=NumberFormat.getNumberInstance() ;
	        if(maximumFractionDigits==null){
	        	ddf1.setMaximumFractionDigits(2);
	        }else{
	        	ddf1.setMaximumFractionDigits(maximumFractionDigits);
	        }
	        s = ddf1.format(x);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return s;
	}

	public static void main(String[] args){
		System.out.println(format(1.51234, 1));
		System.out.println(format(1.52234, 1));
		System.out.println(format(1.55234, 1));
	}
}
