package com.lostad.app.demo.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lostad.app.base.view.BaseActivity;
import com.lostad.app.base.view.fragment.BaseFragment;
import com.lostad.app.demo.R;
import com.lostad.app.demo.view.fragment.IntegrationFragment;
import com.lostad.app.demo.view.fragment.ListWaterFragment;
import com.lostad.app.demo.view.fragment.SettingsFragment;
import com.zxing.view.CaptureActivity;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class MainActivity extends BaseActivity {

    @ViewInject(R.id.tb_toolbar)
    private Toolbar tb_toolbar;

    private FragmentManager fragmentManager;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_radiogroup);

        x.view().inject(this);
        super.initToolBar(tb_toolbar);
        //tb_toolbar.setLogo(R.mipmap.logo);
        setTitle(R.string.app_name);

        fragmentManager = getSupportFragmentManager();
        radioGroup = (RadioGroup) findViewById(R.id.rg_tab);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                changeFragment(checkedId);
            }
        });

        changeFragment(R.id.rb_0);
    }

    private void changeFragment(int checkedId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        BaseFragment fragment = getInstanceByIndex(checkedId);
        transaction.replace(R.id.content, fragment);
        transaction.commit();
    }


    private void resetColor(int resId) {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            RadioButton radio = (RadioButton) radioGroup.getChildAt(i);
            radio.setTextColor(ContextCompat.getColor(this, R.color.txt_gray));
        }
        RadioButton radio = (RadioButton) findViewById(resId);
        radio.setTextColor(ContextCompat.getColor(this, R.color.bg_title));
    }

    public BaseFragment getInstanceByIndex(int resId) {
        BaseFragment fragment = null;

        resetColor(resId);
        switch (resId) {
            case R.id.rb_0:
                fragment = new IntegrationFragment();
                break;
            case R.id.rb_1:
                fragment = new ListWaterFragment();
                Bundle b1 = new Bundle();
                b1.putString("type", "0");
                fragment.setArguments(b1);

                break;
            case R.id.rb_2:
                fragment = new ListWaterFragment();
                Bundle b2 = new Bundle();
                b2.putString("type", "1");
                fragment.setArguments(b2);
                break;
            case R.id.rb_3:
                fragment = new SettingsFragment();
                break;

        }
        return fragment;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id==R.id.action_settings){
            Intent i = new Intent(this, CaptureActivity.class);
            startActivityForResult(i,0);
            return true;
        }else if(id==R.id.action_more){
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }else{
        }

        return super.onOptionsItemSelected(item);
    }

    public void checkVersionInfo(){
        new Thread(){
//			Version4j v;
//			public void run() {
//				v = SysManger.getInstance().loadVersion();
//				runOnUiThread(new Runnable() {
//					@Override
//					public void run() {
//						if(v.isSuccess()){
//							int code = PhoneUtil.getVersionCode(context);
//							if(code<v.versioninfo.versionCode){
//								if("1".equals(v.versioninfo.mandatory)){
//									toUpdate(v.versioninfo,"强制升级！");
//								}else{
//									showAlert(v.versioninfo);
//								}
//
//							}else{
//								login();
//							}
//						}else{
//							//showToast(v.errorDesc);
//							login();
//						}
//					}
//
//
//				});
//			};
        }.start();
    }

}
