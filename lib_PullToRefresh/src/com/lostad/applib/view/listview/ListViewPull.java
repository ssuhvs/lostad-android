/*******************************************************************************
 * @Author   lostad.com
 * 2015-11-05
 * 继承自开源项目PullToRefreshListView，实现一进入界面就显示加载状态
 * 调用户方法：lv_data.setRefreshing();
 *******************************************************************************/
package com.lostad.applib.view.listview;


import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class ListViewPull extends PullToRefreshListView {
    public ListViewPull(Context context) {
        super(context);
    }

    public ListViewPull(Context context, AttributeSet attrs) {
        super(context, attrs);

    }


    public final boolean isRefreshing() {
        State status = super.getState();
        return status == State.REFRESHING || status == State.MANUAL_REFRESHING;
    }

    public boolean isHeaderShown() {
        return getHeaderLayout().isShown();
    }

    public boolean isFooterShown() {
        return getFooterLayout().isShown();
    }


    public void setLoadingMore() {
        if(!isRefreshing()){
            super.setCurrentMode(Mode.PULL_FROM_END);
            super.setState(State.REFRESHING, true);
            super.setRefreshing();
        }else{
            Log.d("setLoadingMore","setLoadingMore ignored  ");
        }
    }

    @Override
    protected void onRefreshing(final boolean doScroll) {
        if (doScroll) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    superRefresh(doScroll);
                    // 下拉刷新时的提示文本设置
                    }
            }, 200);//延时200 毫秒，等待header初始化。
        } else {
            superRefresh(doScroll);
        }
    }

    private void superRefresh(boolean doScroll){
        getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新");
        getLoadingLayoutProxy(true, false).setRefreshingLabel("正在刷新");
        getLoadingLayoutProxy(true, false).setReleaseLabel("放开以刷新");
        // 上拉加载更多时的提示文本设置
        getLoadingLayoutProxy(false, true).setPullLabel("上拉加载更多");
        getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载...");
        getLoadingLayoutProxy(false, true).setReleaseLabel("放开以加载");

        super.onRefreshing(doScroll);
    }

}
