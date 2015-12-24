package com.lostad.app.demo.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lostad.app.base.util.LogMe;
import com.lostad.app.base.view.BaseActivity;
import com.lostad.app.base.view.fragment.BaseFragment;
import com.lostad.app.demo.MyApplication;
import com.lostad.app.demo.R;
import com.lostad.app.demo.view.fragment.IntegrationFragment;
import com.lostad.app.demo.view.fragment.ListWaterFragment;
import com.lostad.app.demo.view.fragment.SettingsFragment;
import com.zxing.view.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sszvip@qq.com
 */
public class MainActivity extends BaseActivity {

    private static MainActivity ctx;

    @ViewInject(R.id.ll_tabItems)
    private LinearLayout ll_tabItems;
    @ViewInject(R.id.viewpager)
    private ViewPager viewPager;

    @ViewInject(R.id.tab_under_line)
    private ImageView tab_under_line;

    private PagerAdapter pagerAdapter;
    //当前页面
    private int currentIndex;
    //屏幕宽度
    private int screenWidth;
    //页面总个数
    private LayoutInflater mInflater;
    private static MyApplication mApp;
    //三个tab页面的列表
    private List<BaseFragment> fragments = new ArrayList<BaseFragment>();
    private int[] mImgTabs0 = {R.xml.tab_icon0, R.xml.tab_icon1, R.xml.tab_icon2, R.xml.tab_icon3};
    private int[] mImgTabs1 = {R.mipmap.icon_tab0_1, R.mipmap.icon_tab1_1, R.mipmap.icon_tab2_1, R.mipmap.icon_tab3_1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
        ctx = this;
        currentIndex = getIntent().getIntExtra("indexTab", 0);

        com.lidroid.xutils.ViewUtils.inject(this);
        this.mInflater = LayoutInflater.from(this);
        setTitle(R.string.app_name);
        mApp = (MyApplication) getApplication();
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        screenWidth = outMetrics.widthPixels;
        //底部tab
        initTabs();
        setTabSelected(currentIndex);
        //pageView
        pagerAdapter = new MyPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(pageChangeListener);
        viewPager.setOffscreenPageLimit(3);//缓存两个fragment
        //切换tab
        viewPager.setCurrentItem(currentIndex);


        //setSystemBarStyle(R.color.red);
        //注册广播接收器
//			IntentFilter filterLogout = new IntentFilter();
//			filterLogout.addAction("android.intent.action.ACTION_SHUTDOWN");
//			registerReceiver(receiverLogout, filterLogout);

        LogMe.e("=================================");
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
            Intent i = new Intent(this, MainActivity2.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
//			unregisterReceiver(receiver);
//			unregisterReceiver(receiverLogout);
        //主界面退出的时候退出sevice
        LogMe.e("=====MainActivity onDestroy========");
        super.onDestroy();
    }


    private void initTabs() {

        fragments.add(new IntegrationFragment());
        fragments.add(new ListWaterFragment());
        fragments.add(new SettingsFragment());
        fragments.add(new SettingsFragment());

        int width = screenWidth / fragments.size();

        View v1 = createTabItem(width, "去吧", R.xml.tab_icon0);
        View v2 = createTabItem(width, "看吧", R.xml.tab_icon1);
        View v3 = createTabItem(width, "炫吧", R.xml.tab_icon2);
        View v4 = createTabItem(width, "我的", R.xml.tab_icon3);
        ll_tabItems.addView(v1);
        ll_tabItems.addView(v2);
        ll_tabItems.addView(v3);
        ll_tabItems.addView(v4);

        //初始化tab选中后的下划线
        initTabUnderLine();

//      example 给第一个tab加上badge
//		BadgeView badge;
//		badge = new BadgeView(ListGoodsActivity.this, firstTab);
//		badge.setText("1");
//		badge.show();
    }

    private View createTabItem(int width, String text, int imgResId) {
        LinearLayout ll = (LinearLayout) mInflater.inflate(R.layout.tab_item, null);

        ll.setLayoutParams(new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT));

        TextView tv = (TextView) ll.findViewById(R.id.tv_tab);
        tv.setText(text);
        tv.setWidth(width);

        ImageView iv = (ImageView) ll.findViewById(R.id.iv_tab);
        iv.setImageResource(imgResId);
        return ll;
    }

    private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

        @Override
        public void onPageSelected(int currIndex) {
            currentIndex = currIndex;
            resetTabTextView();
            setTabSelected(currIndex);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //从左到右
            if (currentIndex == position) {
                LinearLayout.LayoutParams layoutParam = (android.widget.LinearLayout.LayoutParams) tab_under_line
                        .getLayoutParams();
                layoutParam.leftMargin = (int) (positionOffset * (screenWidth * 1.0 / fragments.size()) + currentIndex * (screenWidth / fragments.size()));
                tab_under_line.setLayoutParams(layoutParam);
            }
            //从右到左
            else if (currentIndex > position) {
                LinearLayout.LayoutParams layoutParam = (android.widget.LinearLayout.LayoutParams) tab_under_line
                        .getLayoutParams();
                layoutParam.leftMargin = (int) (-(1 - positionOffset) * (screenWidth * 1.0 / fragments.size()) + currentIndex * (screenWidth / fragments.size()));
                tab_under_line.setLayoutParams(layoutParam);
            }
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }
    };


    private class MyPageAdapter extends FragmentPagerAdapter {

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int index) {
            BaseFragment f = fragments.get(index);
            return f;
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

    }

    //初始化tab下划线
    private void initTabUnderLine() {
        LinearLayout.LayoutParams layoutParam = (android.widget.LinearLayout.LayoutParams) tab_under_line.getLayoutParams();
        layoutParam.width = screenWidth / fragments.size();
        tab_under_line.setLayoutParams(layoutParam);

        for (int i = 0; i < fragments.size(); i++) {
            final int index = i;
            View v = ll_tabItems.getChildAt(i);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    viewPager.setCurrentItem(index);
                }
            });
        }

    }

    //重置tab标题颜色
    private void resetTabTextView() {
        int count = ll_tabItems.getChildCount();
        for (int i = 0; i < count; i++) {
            LinearLayout ll = (LinearLayout) ll_tabItems.getChildAt(i);
            ImageView iv = (ImageView) ll.getChildAt(0);
            TextView tv = (TextView) ll.getChildAt(1);

            iv.setImageResource(mImgTabs0[i]);
            tv.setTextColor(getResources().getColor(R.color.gray));

        }

    }

    private void setTabSelected(int index) {
        LinearLayout ll = (LinearLayout) ll_tabItems.getChildAt(index);
        ImageView iv = (ImageView) ll.getChildAt(0);
        TextView tv = (TextView) ll.getChildAt(1);

        iv.setImageResource(mImgTabs1[index]);
        tv.setTextColor(getResources().getColor(R.color.red));
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            super.quitApp();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}