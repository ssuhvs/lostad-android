package com.lostad.app.base.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lostad.app.base.view.widget.CustomProgressDialog;
import com.lostad.app.demo.R;
import com.lostad.applib.core.MyCallback;

import java.util.List;

/**
 * 窗口工具类,提供可重用的窗口
 * @author     sszvip@qq.com
 * @copyright  lostad.com
 */
public class DialogUtil {

	private static CustomProgressDialog progressDialogMy;
    private static ProgressDialog progDialog;
	private static Toast mToast;//为了实现疯狂模式下toast延时消失的问题
	private static Toast mToastCust ;
//    public static void showDateTimeDlg(Context ctx,final TextView target){
//    	DateTimeSelectorDialogBuilder dialogBuilder = DateTimeSelectorDialogBuilder.getInstance(ctx);
//		dialogBuilder.setOnSaveListener(new OnSaveListener() {
//			@Override
//			public void onSaveSelectedDate(String selectedDate) {
//				target.setText(selectedDate);
//			}
//		});
//		dialogBuilder.show();
//    }
//    public static void showAddrDlg(Context ctx,final TextView target){
//    	LocationSelectorDialogBuilder dialogBuilder = LocationSelectorDialogBuilder.getInstance(ctx);
//		dialogBuilder.setOnSaveLocationLister(new OnSaveLocationLister() {
//			@Override
//			public void onSaveLocation(String location, String provinceId, String cityId) {
//			    target.setText(location);
//			}
//		});
//		dialogBuilder.show();
//    }
    
	public static void showProgress(Activity ctx,String msg) {
		if (progDialog == null){
			progDialog = new ProgressDialog(ctx);
		}else{
			if(progDialog.isShowing()){
				return;
			}
		}
		progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progDialog.setIndeterminate(false);
		//progDialog.setCancelable(false);//按返回取消
		progDialog.setCanceledOnTouchOutside(false);//点区域外不取消quxiao
		if(!Validator.isBlank(msg)){
			progDialog.setMessage(msg);
		}
		progDialog.show();
	}

	/**
	 * 显示等待条
	 */
	public static void showProgress(Context ctx) {
		progressDialogMy = CustomProgressDialog.createDialog(ctx);
		progressDialogMy.setCanceledOnTouchOutside(false);//点区域外quxiao
		// 添加按键监听
		progressDialogMy.setOnKeyListener(new DialogInterface.OnKeyListener() {
			public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {
				if (arg1 == KeyEvent.KEYCODE_BACK) {

					if ((progressDialogMy != null) && progressDialogMy.isShowing()) {
						progressDialogMy.cancel();
					}
				}
				return false;
			}
		});
		progressDialogMy.show();
	}

	/**
	 * 隐藏progress
	 */
	public static void dismissProgress() {
		if ((progressDialogMy != null) && progressDialogMy.isShowing()) {
			progressDialogMy.dismiss();
		}
		if(progDialog!=null){
			progDialog.dismiss();
		}
	}

	public static void showToastOnUIThread(final Activity act,final String msg) {
		act.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				showToastCust(act,msg);
			}
		});
		
	}
	
	public static void showToasMsg(final Context ctx,final int id) {
		if(mToast==null){
			mToast = Toast.makeText(ctx, id, Toast.LENGTH_LONG);
			mToast.show();
		}else{
			mToast.setText(id);
			mToast.setDuration(Toast.LENGTH_LONG);
			mToast.show();
		}
	}
	
	
	public static void showToastCust(Context ctx, String msg) {
		View toastRoot = ((Activity) ctx).getLayoutInflater().inflate(R.layout.toast_my, null);
		TextView message = (TextView) toastRoot.findViewById(R.id.tv_toast);
		message.setText(msg);
        if(mToast==null){
        	mToast = new Toast(ctx);
        	mToast.setGravity(Gravity.CENTER, 0, 0);
        	mToast.setDuration(Toast.LENGTH_SHORT);
        	mToast.setView(toastRoot);
        }
        mToast.show();
	}
	public static void showNoNet(Context ctx) {
		showToastCust(ctx, "网络不可用，请检查网络！");
	}

	
	
	public static void showToastNoNet(Context ctx) {
		View toastRoot = ((Activity) ctx).getLayoutInflater().inflate(
				R.layout.toast_my, null);
		TextView message = (TextView) toastRoot.findViewById(R.id.tv_toast);
		message.setText("网络不可用！");

		Toast toastStart = new Toast(ctx);
		toastStart.setGravity(Gravity.CENTER, 0, 0);
		toastStart.setDuration(Toast.LENGTH_SHORT);
		toastStart.setView(toastRoot);
		toastStart.show();
	}
	
	public static void showAlertOnUIThread(final Activity ctx,final String msg,final MyCallback callback) {
		ctx.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				showAlert(ctx, msg, callback);
			}
		});
	}
	
	
	public static void showAlert(final Activity ctx, final String msg,final MyCallback callback) {
		new AlertDialog.Builder(ctx)
		.setTitle("提示信息")
		.setIcon(android.R.drawable.ic_dialog_alert)//图标
		.setMessage(msg)
		.setCancelable(false)
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				if(callback!=null){
					callback.onCallback(true);
				}
				dialog.cancel();
			}
		}
		).show();
	}
	public static void showAlertYesNo(final Activity ctx,final String msg,final MyCallback callback) {
			new AlertDialog.Builder(ctx)
			.setMessage(msg)
			.setTitle("提示信息")
			.setIcon(android.R.drawable.ic_dialog_alert)//图标
			.setCancelable(false)
			.setPositiveButton("是", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					callback.onCallback(true);
				}
			})
			.setNegativeButton("否", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					callback.onCallback(false);
					dialog.dismiss();
				}
			}).show();
	}
	public static void showAlertYesOrNoOnUIThread(final Activity ctx,final String msg,final MyCallback callback) {
		ctx.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				showAlertYesNo(ctx, msg, callback);
			}
		});
    }
	public static void showAlertOkCancel(final Activity ctx,final String msg,final MyCallback callback) {
			new AlertDialog.Builder(ctx)
			.setMessage(msg)
			.setTitle("提示信息")
			.setIcon(android.R.drawable.ic_dialog_alert)//图标
			.setCancelable(false)
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					callback.onCallback(true);
				}
			})
			.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					callback.onCallback(false);
					dialog.dismiss();
				}
			}).show();
	}
	
	
	public static void showAlertMenu(final Activity ctx,String title,final String[] arr,final MyCallback<Integer> callback){
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		if(Validator.isNotEmpty(title)){
		     builder.setTitle(title);
		}
        builder.setCancelable(true);
        /**
         * 第一个参数指定我们要显示的一组下拉单选框的数据集合
         * 第二个参数代表索引，指定默认哪一个单选框被勾选上，0表示默认选中第一个
         * 第三个参数给每一个单选项绑定一个监听器
         */
        builder.setItems(arr, new DialogInterface.OnClickListener() {
     	     @Override
     	     public void onClick(DialogInterface dialog, int which) {
     	    	 callback.onCallback(which);
           	     dialog.cancel();
     	     }
     	});
        builder.show();
	}
	
	public static void showAlertMenuCust(final Activity ctx,String title,final String[] itemList,final MyCallback<Integer> callback) {
		try{
		    final AlertDialog mAlertDialog = new AlertDialog.Builder(ctx).create();
		    //内容
			ListAdapter mAdapter = new ArrayAdapter(ctx, R.layout.alertdialog_item, itemList);
	        LayoutInflater inflater = LayoutInflater.from(ctx);  
	        View view = inflater.inflate(R.layout.alertdialog, null);  
	        
	        TextView titleView = (TextView)view.findViewById(R.id.tv_title);
	        if(Validator.isNotEmpty(title)){
	        	 titleView.setText(title);
	        	 titleView.setVisibility(View.VISIBLE);
	        }else{
	        	 titleView.setVisibility(View.GONE);
	        }
	       
	        ListView listview = (ListView)view.findViewById(android.R.id.list);
	        listview.setAdapter(mAdapter);
	        listview.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int pos,long arg3) {
					callback.onCallback(pos);
					mAlertDialog.cancel();
				}
			});
	        
	        mAlertDialog.show();
	        mAlertDialog.getWindow().setContentView(view);
	        //mAlertDialog.getWindow().setLayout(150, 320);
	        
		}catch(Exception e){
			e.printStackTrace();
		}
    }

	public static void showAlertMenuCust(final Activity ctx,String title,final List<String> itemList,final MyCallback<Integer> callback) {
		try{
			final AlertDialog mAlertDialog = new AlertDialog.Builder(ctx).create();
			//内容
			ListAdapter mAdapter = new ArrayAdapter(ctx, R.layout.alertdialog_item, itemList);
			LayoutInflater inflater = LayoutInflater.from(ctx);
			View view = inflater.inflate(R.layout.alertdialog, null);

			TextView titleView = (TextView)view.findViewById(R.id.tv_title);
			if(Validator.isNotEmpty(title)){
				titleView.setText(title);
				titleView.setVisibility(View.VISIBLE);
			}else{
				titleView.setVisibility(View.GONE);
			}

			ListView listview = (ListView)view.findViewById(android.R.id.list);
			listview.setAdapter(mAdapter);
			listview.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int pos,long arg3) {
					callback.onCallback(pos);
					mAlertDialog.cancel();
				}
			});

			mAlertDialog.show();
			mAlertDialog.getWindow().setContentView(view);
			//mAlertDialog.getWindow().setLayout(150, 320);

		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
