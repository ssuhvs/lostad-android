package com.lostad.app.demo.view.my;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.lostad.app.base.view.BaseActivity;
import com.lostad.app.demo.MyApplication;
import com.lostad.app.demo.R;
import com.lostad.app.demo.entity.Tour;
import com.lostad.app.demo.entity.TourList4j;
import com.lostad.app.demo.manager.TourManager;
import com.lostad.app.demo.view.mainFragment.ListWaterAdapter;
import com.lostad.applib.view.widget.WaterDropListView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sszvip
 * 
 */
public class ListMyTourActivity extends BaseActivity implements WaterDropListView.IWaterDropListViewListener,OnItemClickListener {

	private MyApplication mApp;

	@ViewInject(R.id.toolbar)
	private Toolbar toolbar;

	@ViewInject(R.id.lv_data)
	private WaterDropListView lv_data;

	//正在加载

	@ViewInject( R.id.tv_loading)
	private TextView tv_loading;

	@ViewInject(R.id.iv_loading)
	private ImageView iv_loading;

	private ListWaterAdapter mAdapter;
	private List<Tour> mListData = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_water);
		x.view().inject(this);
		super.initToolBarWithBack(toolbar);
		setTitle("我的项目");
		mApp = (MyApplication)ctx.getApplication();

		mListData= new ArrayList<Tour>();
		mAdapter = new ListWaterAdapter("",ctx, mListData);
		lv_data.setAdapter(mAdapter);
		lv_data.setWaterDropListViewListener(this);
		lv_data.setPullLoadEnable(true);

		loadData(false);

	}

	@Override
	public void onRefresh() {
//		ExecutorService executorService = Executors.newSingleThreadExecutor();
//		executorService.execute(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					Thread.sleep(2000);
//					handler.sendEmptyMessage(1);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//		});

		loadData(false);

	}

	@Override
	public void onLoadMore() {
		loadData(true);
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

	}

	/**
	 * 如果是下拉刷新，先不要清空数据，以免闪屏体验不好。
	 * 上拉加载数据时，不清空数据
	 * 功能描述:       isLoadMore 是否加载更多操作
	 * @param:
	 * @return:
	 * @Author:      sszvip@qq.com
	 * @Create Date: 2015-9-18下午5:10:16
	 */

	private void loadData(final boolean isLoadMore) {
//		if(lv_data.isLoading()){
//			return;
//		}
		showLoading();
		String url = "";
		new AsyncTask<String,String,TourList4j>(){
			@Override
			protected TourList4j doInBackground(String... params) {
				int start = 0;
				if(isLoadMore){
					start = mListData.size();
				}

				TourList4j g4j = TourManager.getInstance().listTourAll(start);

				return g4j;
			}

			@Override
			protected void onPostExecute(TourList4j g4j) {
				boolean isTheEnd = false ;
				if(g4j.isSuccess()){
					if(g4j.list.size()==0){
						isTheEnd = true;
					}else{
						if(!isLoadMore){//刷新
							mListData.clear();
						}
						mListData.addAll(g4j.list);
						mAdapter.notifyDataSetChanged();
					}
				}

				dismissLoding(isTheEnd);
			}

			@Override
			protected void onCancelled() {
				dismissLoding(true);
			}

		}.execute();
	}

	//////////////////加载效果,以下代码可以直接复制粘贴////////////////////////////////////////////////////////////////////////////////
	private void showLoading() {
		if (mListData == null || mListData.size() == 0) {
			iv_loading.setVisibility(View.GONE);

			tv_loading.setVisibility(View.VISIBLE);
			tv_loading.setText("正在加载...");
		}
	}


	private void dismissLoding(boolean isNoData) {
		try{
			lv_data.stopLoading();
			if(isNoData){
				lv_data.end();
			}
			/// ((AnimationDrawable) iv_loading.getDrawable()).stop();
			if (mListData == null || mListData.size() == 0) {
				iv_loading.setVisibility(View.VISIBLE);
				tv_loading.setVisibility(View.VISIBLE);
				iv_loading.setImageResource(R.mipmap.img_no_data);
			} else {
				iv_loading.setVisibility(View.GONE);
				tv_loading.setVisibility(View.GONE);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
