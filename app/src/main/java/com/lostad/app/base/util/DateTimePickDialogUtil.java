package com.lostad.app.base.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import com.lostad.app.demo.R;
import com.lostad.app.base.core.MyCallback;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



/**
	 * 日期时间选择控件 
	 * 使用方法：
	   private EditText inputDate;//需要设置的日期时间文本编辑框
	   private String initDateTime="2013年3月3日 14:44",//初始日期时间值 
	       在点击事件中使用：
	   inputDate.setOnClickListener(new OnClickListener() {
				
			@Override
			public void onClick(View v) {
				DateTimePickDialogUtil dateTimePicKDialog=new DateTimePickDialogUtil(SinvestigateActivity.this,initDateTime);
				dateTimePicKDialog.dateTimePicKDialog(inputDate);
				
			}
		});
	   
	 * @version 1.0
	 */
public class DateTimePickDialogUtil implements  OnDateChangedListener,OnTimeChangedListener{
		private DatePicker datePicker;
		private TimePicker timePicker;
		private AlertDialog ad;
		private String dateTime;
		private Activity activity;
		Calendar calendar = Calendar.getInstance();
		DatePickerDialog.OnDateSetListener dateListener;
		DatePickerDialog dialogDate;
		/**
		 * 日期时间弹出选择框构造函数
		 * @param activity：调用的父activity
		 * @param initDateTime 初始日期时间值，作为弹出窗口的标题和日期时间初始值
		 */
		public DateTimePickDialogUtil(Activity activity)
		{
			this.activity = activity;
			
		}
		
		public void init(DatePicker datePicker,TimePicker timePicker,String initDateTime)
		{
			Calendar calendar= Calendar.getInstance();
			if(!(null==initDateTime||"".equals(initDateTime)))
			{
				calendar = this.getCalendarByInintData(initDateTime,"yyyy-MM-dd HH:mm");
			}else
			{
				initDateTime=calendar.get(Calendar.YEAR)+"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.DAY_OF_MONTH)+" "+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE);
			}
			
			datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH), this);
			int h = calendar.get(Calendar.HOUR_OF_DAY);
			timePicker.setCurrentHour(h);
			timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
		}

		/**
		 * 弹出日期时间选择框方法
		 * @param inputDate:为需要设置的日期时间文本编辑框
		 * @return
		 */
		public AlertDialog dateTimePicKDialog(final String initDateTime,final MyCallback callBack)
		{
			LinearLayout dateTimeLayout  = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.common_datetime, null);
			datePicker = (DatePicker) dateTimeLayout.findViewById(R.id.datepicker);
			timePicker = (TimePicker) dateTimeLayout.findViewById(R.id.timepicker);
			timePicker.setIs24HourView(true);
			init(datePicker,timePicker,initDateTime);
			timePicker.setOnTimeChangedListener(this);
					
			ad = new AlertDialog.Builder(activity).setTitle(initDateTime).setView(dateTimeLayout).setPositiveButton("设置",
							new DialogInterface.OnClickListener()
							{
								public void onClick(DialogInterface dialog,
										int whichButton)
								{
									callBack.onCallback(dateTime);
								}
							}).setNegativeButton("取消",
							new DialogInterface.OnClickListener()
							{
								public void onClick(DialogInterface dialog,
										int whichButton)
								{
									callBack.onCallback("");
								}
							}).show();
			
			onDateChanged(null, 0, 0, 0);
			return ad;
		}
		
		/**
		 * 弹出日期时间选择框方法
		 * @param inputDate:为需要设置的日期时间文本编辑框
		 * @return
		 */
		public Dialog datePicKDialogBefore(String initDate,final MyCallback myCallback)
		{
			
			dateListener = new DatePickerDialog.OnDateSetListener() {
				@Override
				public void onDateSet(DatePicker datePicker, int year, int month,
						int day) {
					String date = year + "-" + (month + 1) + "-" + day;
					myCallback.onCallback(date);
				}
			};
			dialogDate = new DatePickerDialog(activity, dateListener,
					calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DAY_OF_MONTH));
			DatePicker datePicker = dialogDate.getDatePicker();
			datePicker.setMaxDate(calendar.getTimeInMillis());
			dialogDate.show();
			return dialogDate;
		}
		/**
		 * 弹出日期时间选择框方法
		 * @param inputDate:为需要设置的日期时间文本编辑框
		 * @return
		 */
		public Dialog datePicKDialogLast(String initDate,final MyCallback myCallback)
		{
			
			dateListener = new DatePickerDialog.OnDateSetListener() {
				@Override
				public void onDateSet(DatePicker datePicker, int year, int month,
						int day) {
					String date = year + "-" + (month + 1) + "-" + day;
					myCallback.onCallback(date);
				}
			};
			dialogDate = new DatePickerDialog(activity, dateListener,
					calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DAY_OF_MONTH));
			DatePicker datePicker = dialogDate.getDatePicker();
			datePicker.setMinDate(calendar.getTimeInMillis());
			dialogDate.show();
			return dialogDate;
		}

		public void onTimeChanged(TimePicker view, int hourOfDay, int minute)
		{
			onDateChanged(null, 0, 0, 0);
		}

		public void onDateChanged(DatePicker view, int year, int monthOfYear,
				int dayOfMonth)
		{
			SimpleDateFormat sdf = null;
			Calendar calendar = Calendar.getInstance();
			int h = 0;
			int min = 0;
			
			if(timePicker!=null){
				 h = timePicker.getCurrentHour() ;
				 min = timePicker.getCurrentMinute();
				 sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				 calendar.set(datePicker.getYear(), datePicker.getMonth(),
							datePicker.getDayOfMonth(), h,min);
			}else{
				sdf = new SimpleDateFormat("yyyy-MM-dd");
				calendar.set(datePicker.getYear(), datePicker.getMonth(),
						datePicker.getDayOfMonth());
			}
			dateTime=sdf.format(calendar.getTime());
			ad.setTitle(dateTime);
		}
		
		/**
		 * 初始化
		 * @param initDateTime 初始日期时间值 字符串型
		 * @return Calendar
		 */
		private Calendar getCalendarByInintData(String currDateTime,String fomat) {
			Calendar calendar = Calendar.getInstance();
			
			// 将初始日期时间2013年03月02日 16:45 拆分成年 月 日 时 分 秒
			// TODO Auto-generated method stub      
	        SimpleDateFormat df = new SimpleDateFormat(fomat);  
	        try {  
	            Date date = df.parse(currDateTime);  
	            calendar.setTime(date);
	        } catch (Exception ex) {  
	            System.out.println(ex.getMessage());  
	        }  
			return calendar;
		}

		public static String spliteString(String srcStr,String pattern,String indexOrLast,String frontOrBack)
		{
			String result="";
			int loc=-1;
			if(indexOrLast.equalsIgnoreCase("index"))
			{
				loc=srcStr.indexOf(pattern);
			}else
			{
				loc=srcStr.lastIndexOf(pattern);
			}
			if(frontOrBack.equalsIgnoreCase("front"))
			{
				if(loc!=-1)result=srcStr.substring(0,loc);
			}else
			{
				if(loc!=-1)result=srcStr.substring(loc+1,srcStr.length());
			}
			return result;
		}

		
		
}

