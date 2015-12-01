package com.lostad.app.demo.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.lostad.app.base.view.BaseActivity;
import com.lostad.app.base.view.fragment.BaseFragment;
import com.lostad.app.demo.R;
import com.lostad.app.demo.view.fragment.IntegrationFragment;
import com.lostad.app.demo.view.fragment.ListTourFragment;
import com.lostad.app.demo.view.fragment.SettingsFragment;

public class MainActivity2 extends BaseActivity {
    private FragmentManager fragmentManager;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_radiogroup);

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


    public BaseFragment getInstanceByIndex(int index) {
        BaseFragment fragment = null;
        switch (index) {
            case R.id.rb_0:
                fragment = new IntegrationFragment();
                break;
            case R.id.rb_1:
                fragment = new ListTourFragment();
                Bundle b1 = new Bundle();
                b1.putString("type", "0");
                fragment.setArguments(b1);

                break;
            case R.id.rb_2:
                fragment = new ListTourFragment();
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

}
