/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.lostad.applib.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/*******************************************************************************
 * @Author   lostad.com
 * 2015-11-05
 * 服务器返回数据为空时，自动显示默认图片，支持点击，再次加载
 *******************************************************************************/
@SuppressLint("ViewConstructor")
public class RelativeLayoutCust extends RelativeLayout {

	static final String LOG_TAG = "ListViewPullExt";

	public RelativeLayoutCust(Context context) {
		super(context);
	}

	public RelativeLayoutCust(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public final void setHeight(int height) {
		ViewGroup.LayoutParams lp = getLayoutParams();
		lp.height = height;
		requestLayout();
	}

	public final void setWidth(int width) {
		ViewGroup.LayoutParams lp = getLayoutParams();
		lp.width = width;
		requestLayout();
	}



}
