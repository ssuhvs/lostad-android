package com.lostad.app.demo.view.mainFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lostad.app.base.view.fragment.BaseFragment;
import com.lostad.app.demo.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class MainFragment extends BaseFragment {

    @ViewInject(R.id.tb_toolbar)
    private Toolbar tb_toolbar;

    @ViewInject(R.id.rg_tab)
    private RadioGroup radioGroup;

    private FragmentManager fragmentManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main_radiogroup, container, false);
        x.view().inject(this, rootView);

        fragmentManager = getChildFragmentManager();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                changeFragment(checkedId);
            }
        });

        changeFragment(R.id.rb_0);

        return rootView;
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
            radio.setTextColor(ContextCompat.getColor(ctx, R.color.txt_gray));
        }
        RadioButton radio = (RadioButton) radioGroup.findViewById(resId);
        radio.setTextColor(ContextCompat.getColor(ctx, R.color.bg_title));
    }

    public BaseFragment getInstanceByIndex(int resId) {
        BaseFragment fragment = null;

        resetColor(resId);
        switch (resId) {
            case R.id.rb_0:
                fragment = new IntegrationFragment();
                break;
            case R.id.rb_1:
                fragment = new IntegrationFragment1();
                Bundle b1 = new Bundle();
                b1.putString("type", "0");
                fragment.setArguments(b1);
                break;
            case R.id.rb_2:
                fragment = new IntegrationFragment2();
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
