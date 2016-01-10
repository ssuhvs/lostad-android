package com.lostad.applib.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.lostad.applib.BaseApplication;
import com.lostad.applib.entity.ILoginConfig;


/**
 * @author sszvip
 * 
 */
public class BaseAppFragment extends Fragment {
    private BaseApplication mApp;
    protected Activity ctx;
	public BaseAppFragment(){
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);//启用onCreateOptionMenu

		ctx = getActivity();
		mApp = (BaseApplication)ctx.getApplication();
	}
	
   @Override 
   public void setUserVisibleHint(boolean isVisibleToUser) { 
       super.setUserVisibleHint(isVisibleToUser); 
       if (isVisibleToUser) { 
//   	 setUIStatus();
       } else { 
           //相当于Fragment的onPause 
       } 
   }
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}
	


	public BaseApplication getApp(){
		return mApp;
	}
	public ILoginConfig getLoginConfig(){
       return mApp.getLoginConfig();
	}
}
