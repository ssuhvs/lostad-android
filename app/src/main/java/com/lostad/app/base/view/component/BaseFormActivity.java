package com.lostad.app.base.view.component;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.widget.EditText;

import com.lostad.app.base.view.BaseActivity;


public class BaseFormActivity extends BaseActivity {
	public static final String KEY_VALUE  = "value";
	public static final String KEY_DESC   = "desc";
	public static final String KEY_MAX_LEN  = "max_len";
	public static final String KEY_MIN_LEN  = "min_len";
	public static final String KEY_NULL_ABLE    = "key_null_able";
	
	protected String value  = null;
	protected String desc = null;
	protected Integer maxLen;
	protected Integer minLen;
	protected boolean isNullAble;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Intent i = getIntent();
        value = i.getStringExtra(KEY_VALUE);
        desc = i.getStringExtra(KEY_DESC);
        
        maxLen = i.getIntExtra(KEY_MAX_LEN,-1);
        minLen = i.getIntExtra(KEY_MIN_LEN,-1);
        isNullAble = i.getBooleanExtra(KEY_NULL_ABLE,false); //默认不为空
    }
    
    protected void initMaxLen(EditText et_input){
    	if(maxLen!=-1){
		    InputFilter[] filters = {new LengthFilter(maxLen)};  
		    et_input.setFilters(filters);  
		}
    }
}
