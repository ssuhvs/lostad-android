package com.lostad.app.base.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.lostad.app.base.util.LogMe;
import com.lostad.applib.BaseApplication;
import com.lostad.applib.entity.ILoginConfig;


/**
 * @author sszvip
 * 
 */
public abstract class BaseFragment extends Fragment {
    private BaseApplication mApp;
    protected Activity ctx;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);//启用onCreateOptionMenu
		ctx = getActivity();
		mApp = (BaseApplication)ctx.getApplication();

		LogMe.d("fragment", this.getClass().getName() + "===========onCreate");

	}

	@Override
	public void onResume() {
		super.onResume();
		LogMe.d("fragment", this.getClass().getName() + "===========onResume");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		LogMe.d("fragment", this.getClass().getName() + "===========onCreateView");
		return super.onCreateView(inflater, container, savedInstanceState);
	}

//   @Override
//   public void setUserVisibleHint(boolean isVisibleToUser) {
//       super.setUserVisibleHint(isVisibleToUser);
//       if (isVisibleToUser) {
////   	 setUIStatus();
//       } else {
//           //相当于Fragment的onPause
//       }
//   }
	
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
