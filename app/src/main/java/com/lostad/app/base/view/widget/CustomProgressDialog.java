package com.lostad.app.base.view.widget;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.lostad.app.demo.R;


/******************************************************************** 
 * [Summary] 
 *       TODO 请在此处简要描述此类所实现的功能。因为这项注释主要是为了在IDE环境中生成tip帮助，务必简明扼要 
 * [Remarks] 
 *       TODO 请在此处详细描述类的功能、调用方法、注意事项、以及与其它类的关系. 
 *******************************************************************/  
  
public class CustomProgressDialog extends Dialog {  
    private Context context = null;  
    private static CustomProgressDialog customProgressDialog = null;  
      
    public CustomProgressDialog(Context context){  
        super(context);  
        this.context = context;  
    }  
      
    public CustomProgressDialog(Context context, int theme) {  
        super(context, theme);  
    }  
      @Override
    protected void onCreate(Bundle savedInstanceState) {
    	setCanceledOnTouchOutside(false);
    	super.onCreate(savedInstanceState);
    }

    public static CustomProgressDialog createDialog(Context context){  
        customProgressDialog = new CustomProgressDialog(context, R.style.CustomProgressDialog);
        customProgressDialog.setContentView(R.layout.customprogressdialog);
        customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;  

        return customProgressDialog;  
    }  
   @Override
    public void onWindowFocusChanged(boolean hasFocus){  
          
        if (customProgressDialog == null){  
            return;  
        }  
//        int imgId = ResUtil.getId(context, "id", "loadingImageView");
//        ImageView imageView = (ImageView) customProgressDialog.findViewById(imgId);  
        ImageView imageView = (ImageView) customProgressDialog.findViewById(R.id.loadingImageView);  
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();  
        animationDrawable.start();  
    }  
   
    /** 
     *  
     * [Summary] 
     *       setTitile 标题 
     * @param strTitle 
     * @return 
     * 
     */  
    public CustomProgressDialog setTitile(String strTitle){  
        return customProgressDialog;  
    }  
      
    /** 
     *  
     * [Summary] 
     *       setMessage 提示内容 
     * @param strMessage 
     * @return 
     * 
     */  
    public CustomProgressDialog setMessage(String strMessage){  
    	// int id = ResUtil.getId(context, "id", "id_tv_loadingmsg");
        //TextView tvMsg = (TextView)customProgressDialog.findViewById(id);  
    	TextView tvMsg = (TextView)customProgressDialog.findViewById(R.id.id_tv_loadingmsg);  
        if (tvMsg != null){  
            tvMsg.setText(strMessage);  
        }  
          
        return customProgressDialog;  
    }  
}  
