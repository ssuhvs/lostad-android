package com.lostad.applib.view.city.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.lostad.applib.R;

import java.util.ArrayList;


public class GridViewAdapter extends BaseAdapter {

    private ArrayList<String> adapterList;
    private Context mContext;
    private LayoutInflater inflater;

    public GridViewAdapter(Context context, ArrayList<String> dataList) {
        mContext = context;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        adapterList = dataList;
    }

    @Override
    public int getCount() {
        return adapterList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.city_select_btn_item, null);
        Button btn = (Button) convertView.findViewById(R.id.id_item_btn);
        btn.setText(adapterList.get(position));
        return convertView;
    }

}
