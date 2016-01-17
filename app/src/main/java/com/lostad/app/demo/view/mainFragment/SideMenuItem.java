package com.lostad.app.demo.view.mainFragment;

import android.app.Activity;
public class SideMenuItem {

    public String title;
    public int resourceId;
    public Class<? extends Activity> activity;

    public SideMenuItem() {
    }

    public SideMenuItem(String title, int resourceId, Class<? extends Activity> act) {
        this.resourceId = resourceId;
        this.title = title;
        this.activity = act;
    }


}