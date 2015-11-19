package com.lostad.applib.core;

import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.TextView;

/**
 * 自动清除还还原 hint 
 * @author sszvip
 *
 */
public class OnFocusChangeHintListener implements OnFocusChangeListener {
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		try{
			TextView textView = (TextView) v;
			String hint;
			if (hasFocus) {
				hint = textView.getHint().toString();
				textView.setTag(hint);
				textView.setHint("");
			} else {
				hint = textView.getTag().toString();
				textView.setHint(hint);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
