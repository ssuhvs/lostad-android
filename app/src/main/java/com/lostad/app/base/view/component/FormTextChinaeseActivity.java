package com.lostad.app.base.view.component;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.lostad.app.base.util.Validator;
import com.lostad.app.demo.R;
import com.lostad.applib.util.StringUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


public class FormTextChinaeseActivity extends BaseFormActivity {
	public static final String KEY_VALUE  = "value";
	public static final String KEY_DESC   = "desc";
	public static final String KEY_MAX_LEN  = "max_len";
	public static final String KEY_MIN_LEN  = "min_len";
	public static final String KEY_NULL_ABLE    = "key_null_able";
	
	@ViewInject(R.id.tv_desc)
	private TextView tv_desc;
		
	@ViewInject(R.id.et_input)
	private EditText et_input;


	@ViewInject(R.id.tb_toolbar)
	private Toolbar tb_toolbar;

	private String value  = null;
	private String desc = null;
	private Integer maxLen;
	private Integer minLen;
	private boolean isNullAble;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_edit_text);
		x.view().inject(this);
		super.initToolBar(tb_toolbar);
		Intent i = getIntent();
        value = i.getStringExtra(KEY_VALUE);
        desc = i.getStringExtra(KEY_DESC);
        
        maxLen = i.getIntExtra(KEY_MAX_LEN,-1);
        minLen = i.getIntExtra(KEY_MIN_LEN,-1);
        isNullAble = i.getBooleanExtra(KEY_NULL_ABLE,false); 
        
    	if(Validator.isNotEmpty(value)){
			et_input.setText(value);
		}
		if(Validator.isNotEmpty(desc)){
			tv_desc.setText(desc);
		}
		if(maxLen!=-1){
		    InputFilter[] filters = {new LengthFilter(maxLen)};  
		    et_input.setFilters(filters);  
		}
        et_input.setFocusable(true);
    	et_input.requestFocus();
    }

  
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_ok, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_ok) {
			String v = et_input.getText().toString();
			
			if(!isNullAble){//不允许空
				if(Validator.isBlank(v)){
					showToast("请输入具体内容！");
					return true;
				}
			}
			
			
			String str =  et_input.getText().toString().trim();
			int countChar = str.length();
			int countChinest = StringUtil.getChineseCharacterCount(str);
			
			int len = countChar + countChinest;//一个汉字当做两个字母
			
			if(maxLen!=-1){
				if(len>maxLen){
					showToast("您输入的字符长度不应超过"+maxLen+"个英文字符 或"+(maxLen/2)+"个中文字符");
					return true;
				}
			}
			
			if(minLen!=-1){
				if(len<minLen){
					showToast("您输入的字符长度不应少于"+minLen);
					return true;
				}
			}
			
			Intent intent = new Intent();
			String data = et_input.getText().toString();
			intent.putExtra("data",data);
			setResult(RESULT_OK, intent);
			finish();
		}

		return super.onOptionsItemSelected(item);
	}


	
	
}
