package com.lostad.app.base.view.component;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.lostad.app.demo.R;

/**
 * Alert 弹出框
 * @author xssong
 *
 */
public class WarningDialog {
    Context context;
    android.app.AlertDialog ad;
    TextView titleView;
    TextView messageView;
    TextView messageNext;
    TextView btnCancel;
    TextView btnOK;
    View spaceView;
    
    public WarningDialog(Context context) {
        this.context=context;
        ad=new android.app.AlertDialog.Builder(context).create();
        ad.show();
        //关键在下面的两行,使用window.setContentView,替换整个对话框窗口的布局
        Window window = ad.getWindow();
        window.setContentView(R.layout.alert_warning_view);
        titleView = (TextView)window.findViewById(R.id.alert_dialog_title);
        messageView = (TextView)window.findViewById(R.id.alert_dialog_message);
        messageNext = (TextView) window.findViewById(R.id.alert_dialog_message_next);
        btnCancel = (TextView) window.findViewById(R.id.alert_dialog_cancel);
        btnOK = (TextView) window.findViewById(R.id.alert_dialog_ok);
        spaceView = window.findViewById(R.id.alert_dialog_space);
    }
    public void setTitle(int resId){
        titleView.setText(resId);
    }
    public void setTitle(String title) {
        titleView.setText(title);
        titleView.setVisibility(View.VISIBLE);
    }

    public void setMessage(int resId) {
        messageView.setText(resId);
    }
 
    public void setMessage(String message){
        messageView.setText(Html.fromHtml(message));
    }
    public void setMessageNext(String value) {
        messageNext.setText(value);
        messageNext.setVisibility(View.VISIBLE);
    }
    public TextView getMessageNextView(){
        return messageNext;
    }
    /**
     * 设置确认按钮
     * @param text
     * @param listener
     */
    public void setPositiveButton(String text, final View.OnClickListener listener){
        if (btnOK != null){
            btnOK.setText(text);
            btnOK.setOnClickListener(listener);
        }
    }
    
    /**
     * 设置拒绝按钮
     * @param text
     * @param listener
     */
    public void setNegativeButton(String text, final View.OnClickListener listener){
        if (btnCancel != null){
            btnCancel.setVisibility(View.VISIBLE);
            spaceView.setVisibility(View.VISIBLE);
            btnCancel.setText(text);
            btnCancel.setOnClickListener(listener);
        }
    }
    /**
     * 关闭对话框
     */
    public void dismiss() {
        ad.dismiss();
    }
}
