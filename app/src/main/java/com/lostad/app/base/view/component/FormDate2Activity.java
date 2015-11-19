package com.lostad.app.base.view.component;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TextView;
import android.widget.TimePicker;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lostad.app.demo.R;
import com.lostad.app.base.util.Validator;
import com.lostad.applib.util.DateUtil;

import java.util.Calendar;
import java.util.Date;


public class FormDate2Activity extends BaseFormActivity {
	public static final String KEY_MAX_DATE = "max_date";
	public static final String KEY_MIN_DATE = "min_date";
	@ViewInject(R.id.tv_desc)
	private TextView tv_desc;
	
	@ViewInject(R.id.dpPicker_s)
	private DatePicker dpPicker_s;
	@ViewInject(R.id.dpPicker_e)
	private DatePicker dpPicker_e;
	
	@ViewInject(R.id.tpPicker_s)
	private TimePicker tpPicker_s;
		
	@ViewInject(R.id.tpPicker_e)
	private TimePicker tpPicker_e;
	
	private String value  = null;
	private String desc = null;
	private boolean isDate ;
	private boolean isTime ;
	private int y,m,d;
	private long mMaxDate,mMinDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_date_time);
        Intent i = getIntent();
        value = i.getStringExtra("value");
        desc = i.getStringExtra("desc");
        isDate   = i.getBooleanExtra("isDate", false);
        isTime   = i.getBooleanExtra("isTime", false);
        
        mMaxDate = i.getLongExtra(KEY_MAX_DATE,0);
        mMinDate = i.getLongExtra(KEY_MIN_DATE,0);
        
        tv_desc.setText(desc);
        
        Calendar c = Calendar.getInstance();
	    c.setTime(new Date());
	    y=c.get(Calendar.YEAR);
	    m=c.get(Calendar.MONTH);
	    d=c.get(Calendar.DAY_OF_MONTH);
        if(isDate){
        	   dpPicker_s.setVisibility(View.VISIBLE);
               dpPicker_e.setVisibility(View.VISIBLE);
               if(mMaxDate>0){
            	   dpPicker_s.setMaxDate(mMaxDate);
            	   dpPicker_e.setMaxDate(mMaxDate);
        	   }
        	   if(mMinDate>0){
        		   dpPicker_s.setMinDate(mMinDate);
        		   dpPicker_e.setMinDate(mMinDate);
        	   }
               dpPicker_s.init(y,m,d, new OnDateChangedListener() {

				@Override
				public void onDateChanged(DatePicker arg0, int y, int m,int d) {
					Calendar c = Calendar.getInstance();
					c.set(y, m, d, 0, 0, 0);
					dpPicker_e.setMinDate(c.getTimeInMillis());
				}
               });
               dpPicker_e.init(y, m, d+1, new OnDateChangedListener() {
   				@Override
   				public void onDateChanged(DatePicker arg0, int y, int m,int d) {
   				}
               	   
                  });
        }else{
        	   dpPicker_s.setVisibility(View.GONE);
               dpPicker_e.setVisibility(View.GONE);
        }
        if(isTime){
        	tpPicker_s.setVisibility(View.VISIBLE);
        	tpPicker_e.setVisibility(View.VISIBLE);
        	tpPicker_s.setIs24HourView(true);
        	tpPicker_e.setIs24HourView(true);
        	tpPicker_s.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener(){
				@Override
				public void onTimeChanged(TimePicker arg0, int hourOfDay, int minute) {
					
				}
        	});

        	tpPicker_e.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener(){
				@Override
				public void onTimeChanged(TimePicker arg0, int hourOfDay, int minute) {
				}
        		
        	});
        }else{
        	tpPicker_s.setVisibility(View.GONE);
        	tpPicker_e.setVisibility(View.GONE);
        }
     
    }

  
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_ok, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_ok) {
			String sb = getData();
			if(Validator.isBlank(sb)){
				showToast("请输选择日期！");
				return true;
			}
			if(isDate & !isTime){
				String[] arr = sb.split("至");
				if(arr[1].trim().compareTo(arr[0].trim())<0){
					showToast("结束日期不能早于开始日期！");
					return true;
				}
				String str = DateUtil.getCurrDateStr("yyyy-MM-dd");
				if(arr[0].trim().compareTo(str)<0){
					showToast("开始日期不能早于当前日期！");
					return true;
				}
			}
			
			if(!isDate & isTime){//只选时间
				String[] arr = sb.split("至");
				if(arr[1].trim().compareTo(arr[0].trim())<0){
					showToast("结束时间不能早于开始时间！");
					return true;
				}
			}
			
			Intent intent = new Intent();
			intent.putExtra("data",sb.toString());
			setResult(RESULT_OK, intent);
			finish();
		}

		return super.onOptionsItemSelected(item);
	}


	private String getData() {
		StringBuilder sb = new StringBuilder();
		if(isDate){
			String date = DateUtil.getDateStr(dpPicker_s.getYear(),dpPicker_s.getMonth(),dpPicker_s.getDayOfMonth());
			sb.append(date);
		}
		if(isTime){
			sb.append(DateUtil.getTimeStr(tpPicker_s.getCurrentHour(),tpPicker_s.getCurrentMinute()));
		}
		sb.append(" 至 ");
		if(isDate){
			String date = DateUtil.getDateStr(dpPicker_e.getYear(),dpPicker_e.getMonth(),dpPicker_e.getDayOfMonth());
			sb.append(date);
		}
		if(isTime){
			sb.append(DateUtil.getTimeStr(tpPicker_e.getCurrentHour(),tpPicker_e.getCurrentMinute()));
		}
		return sb.toString();
	}

	
}
