package com.lostad.applib.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期操作工具类.
 * 
 * @author shimiso
 */

public class DateUtil {

	private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static long reduce(String date1,String date2,String format){
		if(Validator.isBlank(format)){
			format = "yyyy-MM-dd HH:mm:ss";
		}
		Date d1 = DateUtil.getDate(date1, format);
		Date d2 = DateUtil.getDate(date2, format);
		
		return d2.getTime() - d1.getTime();
	}

	
	public static String getDateNext(Date date,int dayInterval){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE,dayInterval);//把日期往后增加一天.整数往后推,负数往前移动
		date=calendar.getTime(); //这个时间就是日期往后推一天的结果
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(date);
		return dateString;
	}
	
	public static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                "The birthDay is before Now.It's unbelievable!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH)+1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
       
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                //monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                //monthNow>monthBirth
                age--;
            }
        }

        return age ;
    }


	
	public static String getDateStr(int year,int month,int day){
        Calendar c = Calendar.getInstance();		
		c.set(year, month, day);
		String d = date2Str(c,"yyyy-MM-dd");
		return d;
	}
    public static String getTimeStr(int HH,int mm){
    	StringBuilder sb = new StringBuilder();
    	if(HH<10){
    		sb.append("0").append(HH);
    	}else{
    		sb.append(HH);
    	}
    	sb.append(":");
    	if(mm<10){
    		sb.append("0").append(mm);
    	}else{
    		sb.append(mm);
    	}
  		return sb.toString();
	}
	public static Date str2Date(String str) {
		return str2Date(str, null);
	}

	public static Date str2Date(String str, String format) {
		if (str == null || str.length() == 0) {
			return null;
		}
		if (format == null || format.length() == 0) {
			format = FORMAT;
		}
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			date = sdf.parse(str);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;

	}

	public static Calendar str2Calendar(String str) {
		return str2Calendar(str, null);

	}

	public static Calendar str2Calendar(String str, String format) {

		Date date = str2Date(str, format);
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		return c;

	}

	public static String date2Str(Calendar c) {// yyyy-MM-dd HH:mm:ss
		return date2Str(c, null);
	}

	public static String date2Str(Calendar c, String format) {
		if (c == null) {
			return null;
		}
		return date2Str(c.getTime(), format);
	}

	public static String date2Str(Date d) {// yyyy-MM-dd HH:mm:ss
		return date2Str(d, null);
	}

	public static String date2Str(Date d, String format) {// yyyy-MM-dd HH:mm:ss
		if (d == null) {
			return null;
		}
		if (format == null || format.length() == 0) {
			format = FORMAT;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String s = sdf.format(d);
		return s;
	}

	public static String getCurrDateStr(String format) {
		SimpleDateFormat sdf ;
		if(Validator.isBlank(format)){
			 sdf = new SimpleDateFormat(FORMAT);
		}else{
			 sdf = new SimpleDateFormat(format);
		}
		
		String s = sdf.format(new Date());
		return s;
	}

	public static String getCurrDateStr() {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
		String s = sdf.format(new Date());
		return s;
	}
//	/**
//	 * 获得当前日期的字符串格式
//	 * 
//	 * @param format
//	 * @return
//	 */
//	public static String getCurDateStr(String format) {
//		Calendar c = Calendar.getInstance();
//		return date2Str(c, format);
//	}

	// 格式到秒
	public static String getMillon(long time) {

		return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(time);

	}

	// 格式到天
	public static String getDay(long time) {

		return new SimpleDateFormat("yyyy-MM-dd").format(time);

	}

	// 格式到毫秒
	public static String getSMillon(long time) {

		return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS").format(time);

	}

	
	public static void calcuInterval(String start,String end,CallbackTime callback){
        try {
      	  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       	  Date  d1 = format.parse(start);
          Date  d2 = format.parse(end);
          calcuInterval(d1, d2, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }

    } 

	
	public static void calcuInterval(Date start,Date end,CallbackTime callback){
	        try {
	            //毫秒ms
	            long diff = end.getTime() - start.getTime();

	            long seconds = diff / 1000 % 60;
	            long minutes = diff / (60 * 1000) % 60;
	            long hours = diff / (60 * 60 * 1000) % 24;
	            long days = diff / (24 * 60 * 60 * 1000);
	            callback.onCallback(days, hours, minutes, seconds);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	    } 
	public static String getTimeIntervalStr(Date start,Date end){
		
        return getTimeIntervalStr(start.getTime(), end.getTime());
    } 
	public static String getTimeIntervalStr(long start,long end){
		StringBuilder sb = new StringBuilder();
        try {
            //毫秒ms
            long diff = end - start;

            long seconds = diff / 1000 % 60;
            long minutes = diff / (60 * 1000) % 60;
            long hours = diff / (60 * 60 * 1000) % 24;
            long days = diff / (24 * 60 * 60 * 1000);
            
            if(days>0){
            	sb.append(days).append("天");
            }
            if(hours>0){
            	sb.append(hours).append("小时");
            }
            if(minutes>0){
            	sb.append(minutes).append("分");
            }
            if(seconds>0){
            	sb.append(seconds).append("秒");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    } 

	public static String getWeek(Date date) {

		String[] weeks = { "周日", "周一", "周二", "周三", "周四", "周五", "周六"};
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    int days = cal.get(Calendar.DAY_OF_WEEK);
	    return weeks[days-1];
	}
	public interface CallbackTime{
		void onCallback(long days, long hours, long minutes, long seconds);
	}
	

	public static Date getDate(String dateStr, String format) {
		Date date = null ; 
        SimpleDateFormat   sdf   =   new SimpleDateFormat(format);  
		try {
			
			date =  sdf.parse(dateStr);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * 截取年 2012-01-01
	 * @param date
	 * @return
	 */
	public static String getYear(String date) {
		String year = null ;
		try{
			year = date.substring(0,4);
		}catch(Exception e){
			e.printStackTrace();
		}
		return year;
	}
    //20140501
	public static String getMonth(String date) {
		String month = null ;
		try{
			month = date.substring(5,7);
		}catch(Exception e){
			e.printStackTrace();
		}
		return month;
	}
	public static String getDay(String date) {
		String day = null ;
		try{
			day = date.substring(8);
		}catch(Exception e){
			e.printStackTrace();
		}
		return day;
	}
	/**
	 * @param birthDate
	 * @return
	 */
	public static CharSequence getAge(String birthDate) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public  static  String getInterval(int seconds) 
	{ //传入的时间格式必须类似于2012-8-21 17:53:20这样的格式  
        String interval = null;  
          
        //用现在距离1970年的时间间隔new Date().getTime()减去以前的时间距离1970年的时间间隔d1.getTime()得出的就是以前的时间与现在时间的时间间隔  
        long time = new Date().getTime()/1000 - seconds;// 得出的时间间隔是秒  
          
        if(time/3600 < 24 && time/3600 >= 1) {  
        //如果时间间隔小于24小时则显示多少小时前  
            int h = (int) (time/3600);//得出的时间间隔的单位是小时  
            interval = h + "小时前";  
              
        } else if(time/60 < 60 && time/60 >=1) {  
        //如果时间间隔小于60分钟则显示多少分钟前  
            int m = (int)(time/60) ;//得出的时间间隔的单位是分钟  
            interval = m + "分钟前";  
        } else if(time < 60 && time >= 10) {  
        //如果时间间隔小于60秒则显示多少秒前  
            interval = time + "秒前";  
        }else if(time < 10 && time >= 0) {  
        //如果时间间隔小于10秒则显示“刚刚”time/10得出的时间间隔的单位是秒  
            interval ="刚刚";  
        } else{  
            //大于24小时，则显示正常的时间，但是不显示秒  
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");  
            interval = sdf.format(seconds*1000);  
        }  
        return interval;  
    } 
	
	public static int getCurrSeconds(){
		return (int)(System.currentTimeMillis()/1000);
	}
	
}
