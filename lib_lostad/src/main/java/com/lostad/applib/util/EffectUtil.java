package com.lostad.applib.util;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.lostad.applib.R;
import com.nineoldandroids.animation.ObjectAnimator;
/**
 * 窗口工具类,提供可重用的窗口
 * 
 * @author sszvip@qq.com
 * @copyright lostad.com
 */
public class EffectUtil {

	public static void showShake(Activity ctx, View v) {
		Animation shake = AnimationUtils
				.loadAnimation(ctx, R.anim.effect_shake);
		v.startAnimation(shake);
	}

	public void showOrExpandTextView(TextView tv_text, boolean show) {
		if (show) {
			tv_text.setEllipsize(null); // 展开
			tv_text.setSingleLine(false);
		} else {
			tv_text.setEllipsize(TextUtils.TruncateAt.END); // 收缩
			tv_text.setSingleLine(true);
		}
	}

	/**
	 * 文字放大缩小。用于点赞等操作
	 * @param target
	 */
	public static void showScaleView(View target) {
		ObjectAnimator.ofFloat(target, "scaleY", 1, 2, 1).setDuration(2000).start();
		ObjectAnimator.ofFloat(target, "scaleX", 1, 2, 1).setDuration(2000).start();
	}

	/**
	 *  转动效果
	 * @param ctx 上下文
	 * @param ivIcon 组件
	 */
	public static void startAnimation(Context ctx, ImageView ivIcon){
		try{
			Animation operatingAnim = AnimationUtils.loadAnimation(ctx, R.anim.progress_loading);
			LinearInterpolator lin = new LinearInterpolator();
			operatingAnim.setInterpolator(lin);
			if (operatingAnim != null && ivIcon != null) {
				ivIcon.startAnimation(operatingAnim);
			}
		}catch (Exception e){
			e.printStackTrace();
		}

	}

}
