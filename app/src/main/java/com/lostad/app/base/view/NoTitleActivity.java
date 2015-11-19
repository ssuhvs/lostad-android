package com.lostad.app.base.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;


public abstract class NoTitleActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

    
}
