package com.lostad.applib.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lostad.applib.R;
import com.lostad.applib.core.MyCallback;
import com.lostad.applib.view.widget.CustomProgressDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 窗口工具类,提供可重用的窗口
 * @author     sszvip@qq.com
 * @copyright  weibo.com/lostbottle
 */
public class PopupWinUtil {

	private static PopupWindow mPopupWin = null ;

	public static void showMenuDown(final Activity ctx, View anchor,
									final List<String> itemList, final MyCallback<Integer> callback) {
		if (mPopupWin != null && mPopupWin.isShowing()) {
			mPopupWin.dismiss();
			return;
		}

		try {
			List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
			for (String data : itemList) {
				HashMap<String, String> mapTemp = new HashMap<String, String>();
				mapTemp.put("item", data);
				listMap.add(mapTemp);
			}
			//获取自定义布局文件pop.xml的视图
			View customView = ctx.getLayoutInflater().inflate(R.layout.menu_list, null, false);
			mPopupWin = new PopupWindow(customView, LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
			// 设置动画效果 [R.style.AnimationFade 是自己事先定义好的]
			mPopupWin.setAnimationStyle(R.style.AnimationFade);
			mPopupWin.setOutsideTouchable(true);
			mPopupWin.setTouchInterceptor(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// 如果点击了popupwindow的外部，popupwindow也会消失
					if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
						mPopupWin.dismiss();
						return true;
					}
					return false;
				}
			});
			// 自定义view添加触摸事件
			ListView lv = (ListView) customView.findViewById(R.id.lv_data);
			lv.getBackground().setAlpha(200);
			SimpleAdapter adp = new SimpleAdapter(ctx, listMap,
					R.layout.pop_menuitem, new String[] { "item" },
					new int[] { R.id.menuitem });
			lv.setAdapter(adp);
			lv.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
										int pos, long arg3) {
					callback.onCallback(pos);
					mPopupWin.dismiss();
				}
			});

			Rect frame = new Rect();
			ctx.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);// /取得整个视图部分,注意，如果你要设置标题样式，这个必须出现在标题样式之后，否则会出错
			View v = ctx.getWindow().findViewById(Window.ID_ANDROID_CONTENT);// /获得根视图
			// 状态栏高度
			int contentTop = v.getTop();
			// statusBarHeight是上面所求的状态栏的高度
			int statusBarHeight = frame.top;
			int titleBarHeight = contentTop - statusBarHeight;

			int yOff = contentTop;
			mPopupWin.showAsDropDown(anchor, 0, yOff);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
