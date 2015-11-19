package com.lostad.app.base.util;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import com.nineoldandroids.animation.*;
import com.lostad.app.demo.R;

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


}
