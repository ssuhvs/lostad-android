package com.lostad.app.demo.view.mainFragment;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lostad.app.base.view.fragment.BaseFragment;
import com.lostad.app.demo.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sszvip@qq.com
 * 
 */
public class IntegrationFragment1 extends BaseFragment {
	@ViewInject(R.id.viewpagertab)
	private SmartTabLayout tabs_viewpagertab;
	@ViewInject(R.id.viewpager)
	private ViewPager viewPager;

	private PagerAdapter pagerAdapter;
	// 当前页面
	private int currentIndex;
	// 屏幕宽度
	private int screenWidth;
	// 三个tab页面的列表
	private List<ListWaterFragment> fragments = new ArrayList<ListWaterFragment>();
	private List<String> mListTabNames = new ArrayList<String>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_intergration1, container, false);
		x.view().inject(this, rootView);

		mListTabNames.add("Tab1");
		mListTabNames.add("Tab2");
		mListTabNames.add("Tab3");
		mListTabNames.add("Tab4");
		mListTabNames.add("Tab5");
		mListTabNames.add("Tab6");
		mListTabNames.add("Tab7");
		mListTabNames.add("Tab8");
		mListTabNames.add("Tab9");
		initTabs(inflater);

		return rootView;
	}

	private void initTabs(final LayoutInflater inflater) {

		FragmentPagerItems pages = new FragmentPagerItems(ctx);
		for (String name : mListTabNames) {
			pages.add(FragmentPagerItem.of(name, ListWaterFragment.class));
		}
		FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getChildFragmentManager(), pages);
		viewPager.setAdapter(adapter);
		tabs_viewpagertab.setViewPager(viewPager);

		tabs_viewpagertab.setCustomTabView(new SmartTabLayout.TabProvider() {
			@Override
			public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
				String tabName = mListTabNames.get(position);

				TextView tv_tab = createTabItem(inflater,80,tabName);
				tv_tab.setText(tabName);

				return tv_tab;
			}
		});
	}

	private TextView createTabItem(LayoutInflater inflater,int width, String text) {
		TextView v = (TextView) inflater.inflate(R.layout.tab_textview, null);
		v.setWidth(width);
		v.setText(text);
		return v;
	}

   
}
