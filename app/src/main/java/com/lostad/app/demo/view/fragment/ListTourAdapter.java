package com.lostad.app.demo.view.fragment;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lostad.app.base.util.DownloadUtil;
import com.lostad.app.demo.R;
import com.lostad.app.demo.entity.Tour;
import com.lostad.applib.util.Validator;

import java.util.List;

public class ListTourAdapter extends BaseAdapter {

	private Activity mContext;
    private String mType;
	List<Tour> mListData = null;

	private LayoutInflater mInflater;
	private String myId;
	public ListTourAdapter(String type, Activity context, List<Tour> list) {
		this.mType = type;
		mContext = context;
		this.mListData = list;

		mInflater =  LayoutInflater.from(mContext);

	}
	
	public int getCount() {
		return mListData.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder ;
		if (convertView == null) {
			convertView     = mInflater.inflate(R.layout.list_item_tour, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		} 

		final Tour f = mListData.get(position);
		holder.tv_title.setText(f.title);
		holder.tv_desc.setText(f.desc);
		if(Validator.isNotEmpty(f.picUrl)){
			//DownloadUtil.loadImage(holder.iv_pic, IConst.URL_BASE+f.XMTP,R.drawable.loading_frame1,R.mipmap.img_default,R.mipmap.load_fail);
			DownloadUtil.loadImage(mContext,holder.iv_pic, f.picUrl);
		}


		return convertView;
	}

	
	
	public void checkAll(){
		notifyDataSetChanged();
	}
	
	
	public class ViewHolder {    
		public ViewHolder(View convertView) {

    		ViewUtils.inject(this,convertView);
		}
		
		@ViewInject(R.id.iv_pic)
		public ImageView  iv_pic;

		@ViewInject(R.id.tv_title)
		public TextView tv_title;

		@ViewInject(R.id.tv_desc)
		public TextView tv_desc;

	}
	
	
}
