package com.lostad.app.base.view.component;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TextView;
import android.widget.TimePicker;

import com.lostad.app.base.util.Validator;
import com.lostad.app.demo.R;
import com.lostad.applib.util.DateUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Calendar;
import java.util.Date;


public class FormDateActivity extends BaseFormActivity {
	public static final String KEY_MAX_DATE = "max_date";
	public static final String KEY_MIN_DATE = "min_date";
	public static final String KEY_IS_DATE  = "isDate";
	public static final String KEY_IS_TIME  = "isTime";

	@ViewInject(R.id.toolbar)
	private Toolbar toolbar;

	@ViewInject(R.id.tv_desc)
	private TextView tv_desc;
	
	@ViewInject(R.id.dpPicker)
	private DatePicker dpPicker;
	
	@ViewInject(R.id.tpPicker)
	private TimePicker tpPicker;


	private String desc = null;
	private boolean isDate ;
	private boolean isTime ;
	private int y,m,d;
	private long mMaxDate,mMinDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_date_lib);

		x.view().inject(this);
		super.initToolBarWithBack((Toolbar)findViewById(R.id.toolbar));

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
        	   dpPicker.setVisibility(View.VISIBLE);
        	   if(mMaxDate>0){
        		   dpPicker.setMaxDate(mMaxDate);
        	   }
        	   if(mMinDate>0){
        		   dpPicker.setMinDate(mMinDate);
        	   }
               dpPicker.init(y,m,d, new OnDateChangedListener() {

				@Override
				public void onDateChanged(DatePicker arg0, int y, int m,int d) {
				}
               });
           
        }else{
        	   dpPicker.setVisibility(View.GONE);
        }
        if(isTime){
        	tpPicker.setVisibility(View.VISIBLE);
        	tpPicker.setIs24HourView(true);
        	tpPicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener(){
				@Override
				public void onTimeChanged(TimePicker arg0, int hourOfDay, int minute) {
					 String e = String.format("%d:%d",hourOfDay,minute);
				}
        		
        	});
        }else{
        	tpPicker.setVisibility(View.GONE);
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
			String date = DateUtil.getDateStr(dpPicker.getYear(), dpPicker.getMonth(), dpPicker.getDayOfMonth());
			sb.append(date);
		}
		if(isTime){
			sb.append(DateUtil.getTimeStr(tpPicker.getCurrentHour(),tpPicker.getCurrentMinute()));
		}
		
		return sb.toString();
	}

	
}
