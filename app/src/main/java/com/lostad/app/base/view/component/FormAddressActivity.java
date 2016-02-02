package com.lostad.app.base.view.component;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.lostad.app.base.view.BaseActivity;
import com.lostad.applib.R;
import com.lostad.applib.view.city.views.GridPop;

import org.xutils.x;


public class FormAddressActivity extends BaseActivity {


    private GridPop gridpop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_address);

        x.view().inject(this);
        super.initToolBarWithBack((Toolbar)findViewById(R.id.tb_toolbar));

        initView();

    }

    private void initView() {

        Button btn1 = (Button) findViewById(R.id.id_btn_1);
        Button btn2 = (Button) findViewById(R.id.id_btn_2);


        gridpop = new GridPop(FormAddressActivity.this, R.layout.city_pop_grid_view);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gridpop != null) {
                    gridpop.toggle(v);
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (gridpop != null) {
                    gridpop.toggle(v);
                }
            }
        });

    }



}


