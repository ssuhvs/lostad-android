package com.lostad.applib.view.city.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

import com.lostad.applib.R;
import com.lostad.applib.view.city.adapter.GridViewAdapter;
import com.lostad.applib.view.city.db.CityDB;

import java.util.ArrayList;

/**
 * 省市县选择
 * by andyiac 2015年3月29日
 */
public class GridPop extends PopupWindow {
    private Context context;
    private LayoutInflater layoutInflater;
    public View allView;
    private int resId;
    private GridView allItemGrid;

    private GridView gv;
    private CityDB cityDB;
    private GridViewAdapter gridViewAdapter;

    //选择城市标志位
    private FlagCitySelected flagCitySelected = FlagCitySelected.ZERO;

    private String mCurrentProvince;

    private ArrayList<String> mCurrentStringArray;


    private Button mCurrentClickedBtn;

    public interface onCitySelectedListener {
        void onCitySelected(String city);
    }

    private onCitySelectedListener mOnCitySelectedListener;

    public void setOnCitySelectedListener(onCitySelectedListener l) {
        this.mOnCitySelectedListener = l;
    }

    public GridPop(Context context, int resourceId) {
        super(context);
        this.context = context;
        this.resId = resourceId;
        initAllPop();
        cityDB = new CityDB(context);

        initView();
    }



    private void initView() {

        mCurrentStringArray = (ArrayList<String>) cityDB.getAllProvince();

        gv = getAllItemGrid();
        gridViewAdapter = new GridViewAdapter(context, mCurrentStringArray);

        gv.setAdapter(gridViewAdapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (flagCitySelected) {
                    case ZERO:
                        mOnCitySelectedListener.onCitySelected(mCurrentStringArray.get(position));
                        mCurrentProvince = mCurrentStringArray.get(position);
                        flagCitySelected = FlagCitySelected.PROVINCE;
                        update2AllCity(mCurrentProvince);
                        break;
                    case PROVINCE:
                        mOnCitySelectedListener.onCitySelected(mCurrentStringArray.get(position));
                        String city = mCurrentStringArray.get(position);
                        flagCitySelected = FlagCitySelected.CITY;
                        update2AllCountry(mCurrentProvince, city);
                        break;
                    case CITY:
                        mOnCitySelectedListener.onCitySelected(mCurrentStringArray.get(position));
                        flagCitySelected = FlagCitySelected.ZERO;
                        GridPop.this.dismiss();
                        update2AllProvince();
                        break;
                }
            }
        });


    }


    public void initAllPop() {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        allView = layoutInflater.inflate(resId, null);
        allView.setFocusable(true);
        allView.setFocusableInTouchMode(true);
        allItemGrid = (GridView) allView.findViewById(R.id.id_grid_view);
        allView.setFocusableInTouchMode(true);
        allView.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (isShowing()) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });

        setContentView(allView);
        setWidth(LayoutParams.MATCH_PARENT);
        setHeight(LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new ColorDrawable(Color.rgb(252, 252, 252)));
        setTouchable(true);
        setFocusable(true);
        setOutsideTouchable(false);
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });

    }

    /**
     * @return 返回该pop中GridView, 可以其他地方设置该GridView
     */
    public GridView getAllItemGrid() {
        return allItemGrid;
    }

    /**
     * 所有省
     */
    private void update2AllProvince() {
        flagCitySelected = FlagCitySelected.ZERO;
        mCurrentStringArray.clear();
        mCurrentStringArray.addAll(cityDB.getAllProvince());
        gridViewAdapter.notifyDataSetChanged();
    }

    /**
     * 所有 市
     *
     * @param province
     */
    private void update2AllCity(String province) {
        mCurrentStringArray.clear();
        ArrayList<String> cityList = (ArrayList<String>) cityDB.getProvinceAllCity(province);
        mCurrentStringArray.addAll(cityList);
        gridViewAdapter.notifyDataSetChanged();
    }

    /**
     * 所有县
     *
     * @param province
     * @param city
     */
    private void update2AllCountry(String province, String city) {
        ArrayList<String> cityList = (ArrayList<String>) cityDB.getAllCountry(province, city);
        if (cityList.size() <= 1) {
            flagCitySelected = FlagCitySelected.ZERO;
            GridPop.this.dismiss();
            update2AllProvince();
            return;
        }
        mCurrentStringArray.clear();
        mCurrentStringArray.addAll(cityList);
        gridViewAdapter.notifyDataSetChanged();
    }

    /**
     * toggle gridPop windows
     *
     * @param v
     */
    public void toggle(final View v) {

        mCurrentClickedBtn = (Button) v;
        if (this.isShowing()) {
            this.dismiss();
        } else {
            update2AllProvince();
            this.showAsDropDown(v, 0, 2);
            this.setOnCitySelectedListener(new onCitySelectedListener() {
                @Override
                public void onCitySelected(String city) {
                    ((Button) v).setText(city);
                }
            });
        }
    }


    /**
     * 标志位
     */
    private enum FlagCitySelected {
        ZERO, PROVINCE, CITY, COUNTRY
    }
}
