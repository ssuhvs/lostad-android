package com.lostad.app.demo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lostad.app.base.util.DialogUtil;
import com.lostad.app.base.view.fragment.BaseFragment;
import com.lostad.app.demo.LoginActivity;
import com.lostad.app.demo.MyApplication;
import com.lostad.app.demo.R;
import com.lostad.app.demo.my.ListMyTourActivity;
import com.lostad.applib.core.MyCallback;
import com.lostad.applib.entity.ILoginConfig;


/**
 * @author sszvip
 * 
 */
public class SettingsFragment extends BaseFragment {

	private MyApplication mApp;

	@ViewInject(R.id.tv_name)
	private TextView tv_name;

	@ViewInject(R.id.tv_phone)
	private TextView tv_phone;

	@ViewInject(R.id.btn_quit)
	private TextView btn_quit;
	ILoginConfig mLogin;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        ViewUtils.inject(this,rootView);    
        mApp = (MyApplication)getApp();

        //initVersionInof(mSysconfig.getVersion(),v.versioninfo.versionCode);
		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		mLogin = getLoginConfig();
		if(mLogin!=null){
			tv_name.setText(getLoginConfig().getName());
			tv_phone.setText(getLoginConfig().getPhone());
			btn_quit.setText("退出");
		}else{
			tv_name.setText("未登陆");
			tv_phone.setText("");
			btn_quit.setText("注册/登陆");
		}
	}

	@OnClick(R.id.ll_userinfo)
	public void onClickUserInfo(View v){
		if(mLogin==null){
             toLoginActivity();
		}else{
			try {
//				UserInfo u = getApp().getDb().findById(UserInfo.class,mLogin.getUserId());
//				Intent i =  new Intent(ctx, FormMyActivity.class);
//				i.putExtra("bean",u);
//				startActivityForResult(i,0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}



	@OnClick(R.id.ll_10)
	public void onClick_ll_11(View v){
		if(mLogin==null){
			toLoginActivity();
		}else {
//			Intent i = new Intent(ctx, ListMyOrderActivity.class);
//			startActivity(i);
		}
	}
	@OnClick(R.id.ll_11)
	public void onClick_ll_12(View v){
		if(mLogin==null){
			toLoginActivity();
		}else {
			Intent i = new Intent(ctx, ListMyTourActivity.class);
			startActivity(i);
		}
	}


	@OnClick(R.id.ll_20)
	public void onClick_ll_20(View v){
		if(mLogin==null){
			toLoginActivity();
		}else {
//			Intent i = new Intent(ctx, ListUserActivity.class);
//			startActivity(i);
		}
	}

	@OnClick(R.id.ll_21)
	public void onClick_ll_21(View v){
		if(mLogin==null){
			toLoginActivity();
		}else {
//			Intent i = new Intent(ctx, LoginActivity.class);
//			startActivity(i);
	    }
	}

	@OnClick(R.id.ll_30)
	public void onClick_ll_30(View v){
		if(mLogin==null){
			toLoginActivity();
		}else {
			Intent i = new Intent(ctx, LoginActivity.class);
			startActivity(i);
		}
	}


	@OnClick(R.id.btn_quit)
	public void onClickQuit(View v){
		if(mLogin==null) {
			toLoginActivity();
		}else{
			DialogUtil.showAlertYesNo(ctx, "确定要退出吗？", new MyCallback<Boolean>() {
				@Override
				public void onCallback(Boolean yes) {
					if (yes) {
						mApp.quit(true);
					}
				}
			});
		}

	}

	private void toLoginActivity(){
		Intent i = new Intent(ctx, LoginActivity.class);
		startActivity(i);
	}
}
