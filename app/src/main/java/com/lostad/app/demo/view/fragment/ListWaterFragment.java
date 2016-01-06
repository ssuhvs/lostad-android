package com.lostad.app.demo.view.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lostad.app.base.view.fragment.BaseFragment;
import com.lostad.app.demo.R;
import com.lostad.app.demo.entity.Tour;
import com.lostad.app.demo.entity.TourList4j;
import com.lostad.applib.view.widget.WaterDropListView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sszvip
 * 
 */
public class ListWaterFragment extends BaseFragment implements WaterDropListView.IWaterDropListViewListener,AdapterView.OnItemClickListener {

	@ViewInject(R.id.lv_data)
	private WaterDropListView lv_data;

    //正在加载

	@ViewInject( R.id.tv_loading)
	private TextView tv_loading;

	@ViewInject(R.id.iv_loading)
	private ImageView iv_loading;

	private ListTourAdapter mAdapter;
	private List<Tour> mListData = null;
    private String mType = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_list_water, container, false);
		x.view().inject(this, rootView);

		mListData= new ArrayList<Tour>();
		mAdapter = new ListTourAdapter(mType,ctx, mListData);
		lv_data.setAdapter(mAdapter);
		lv_data.setWaterDropListViewListener(this);
		lv_data.setPullLoadEnable(true);

		loadData(false);

		return rootView;
	}

//////////////////////////////////////////////////////////////////
//  切换fragment界面时需要刷新时，打开这里 。此方法在fragment初始化后才有效
//	@Override
//	public void setUserVisibleHint(boolean isVisibleToUser) {
//		super.setUserVisibleHint(isVisibleToUser);
//		LogMe.d("fragment", this.getClass().getName() + "===========setUserVisibleHint  isVisibleToUser:" + isVisibleToUser);
//		if(isVisibleToUser && lv_data!=null) {//可见，且界面已经初始化
//			lv_data.setRefreshing();
//		} else {//
//
//		}
//	}
////////////////////////////////////////////////////////////////////

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
//	private void loadData(final boolean isLoadMore) {
//		showLoading();
//		new AsyncTask<Integer,Integer,TourList4j>(){
//			@Override
//			protected TourList4j doInBackground(Integer... integers) {
//				int start = 0;
//				if(isLoadMore){//加载更多
//					start = mListData.size();
//				}
//				TourList4j g4j = TourManager.getInstance().listTourAll(mType, start);
//
//				return g4j;
//			}
//
//			@Override
//			protected void onPostExecute(TourList4j g4j) {
//				if(g4j.isSuccess()) {
//					if(g4j.list!=null && g4j.list.size()>0){
//						if(isLoadMore==false){//如果是刷新数据
//							mListData.clear();//清空以前的
//							mAdapter.notifyDataSetChanged();
//						}
//						mListData.addAll(g4j.list);
//						mAdapter.notifyDataSetChanged();
//					}else{
//						lv_data.end();
//					}
//				} else {
//					DialogUtil.showToastCust(ctx, g4j.getMsg());
//
//				}
//				dismissLoding(isLoadMore,null);
//			}
//		}.execute(); //线程池里的线程逐个顺次执行
//	}

	private void loadData(final boolean isLoadMore) {
//		if(lv_data.isLoading()){
//			return;
//		}
		showLoading();
        String url = "";
        new AsyncTask<String,String,TourList4j>(){
			@Override
			protected TourList4j doInBackground(String... params) {
				showLoading();
				TourList4j g4j = null ;
				try {
					Thread.sleep(1000);
					List<Tour> list = new ArrayList<Tour>();
					list.add(new Tour("1", "11", null, "1111"));
					list.add(new Tour("1","11",null,"1111"));
					list.add(new Tour("1","11",null,"1111"));
					list.add(new Tour("1","11",null,"1111"));
					list.add(new Tour("1","11",null,"1111"));
					list.add(new Tour("1","11",null,"1111"));
					list.add(new Tour("1","11",null,"1111"));

					g4j = new TourList4j(true,"success");
					g4j.list = list;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return g4j;
			}

			@Override
			protected void onPostExecute(TourList4j g4j) {
				boolean isTheEnd = false ;
                if(g4j.isSuccess()){
					if(g4j.list.size()==0){
						isTheEnd = true;
					}else{
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
