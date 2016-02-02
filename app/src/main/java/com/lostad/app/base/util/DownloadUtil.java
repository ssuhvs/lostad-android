/*
 * Copyright 2010 Renren, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lostad.app.base.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Looper;
import android.widget.ImageView;

import com.lostad.app.demo.R;
import com.lostad.applib.core.MyCallback;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * s
 * 请求服务器工具类
 * @author sszvip@qq.com
 * @since  2013-7-2
 */
public class DownloadUtil {


//	public static void loadImage(ImageView imageView,String url,Integer loadingImage,Integer emptyImage,Integer failImage){
//        try{
//			DisplayImageOptions.Builder b =  new DisplayImageOptions.Builder();
//			if(loadingImage!=null){
//				b.showImageOnLoading(loadingImage);
//			}
//			if(emptyImage!=null){
//				b.showImageForEmptyUri(emptyImage);
//			}
//			if(failImage!=null){
//				b.showImageOnFail(failImage);
//			}
//
//			b.cacheInMemory(true);
//			b.cacheOnDisc(true);
//			b.bitmapConfig(Bitmap.Config.RGB_565);
//			DisplayImageOptions options = b.build();
//			ImageLoader.getInstance().displayImage(url, imageView, options);
//		}catch (Exception e){
//			e.printStackTrace();
//		}
//
//	}
//public static void loadImage(ImageView imageView,String url,DisplayImageOptions options){
//	ImageLoader.getInstance().displayImage(url, imageView, options);
//}
	private static ImageOptions mImageOptions;
	public static void loadImage(Activity ctx,ImageView iv_pic,String url){
		loadImage(ctx, iv_pic,R.mipmap.ic_launcher,url);
	}

	public static void loadImage(Activity ctx,ImageView iv_pic,int defalutImgId,String url){
		try{
			if(mImageOptions==null){
				mImageOptions = new ImageOptions.Builder()
						// 加载中或错误图片的ScaleType
						//.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
						// 默认自动适应大小
						// .setSize(...)
						.setFailureDrawableId(R.mipmap.load_fail)
						.setLoadingDrawableId(R.mipmap.ic_launcher)
						.setIgnoreGif(false)
						.setUseMemCache(true)
						.setImageScaleType(ImageView.ScaleType.CENTER).build();
			}
			x.image().bind(iv_pic, url, mImageOptions);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

public static void loadImage(ImageView iv_pic,String url,ImageOptions options){
	x.image().bind(iv_pic, url, options);
}


	public static void downFileAsyn(final Activity ctx, final String upgradeUrl,
			final String savedPath, final MyCallback<Boolean> callback) {
		final ProgressDialog xh_pDialog = new ProgressDialog(ctx);
		// 设置进度条风格，风格为圆形，旋转的
		xh_pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		// 设置ProgressDialog 标题
		xh_pDialog.setTitle("提示信息");
		// 设置ProgressDialog提示信息
		xh_pDialog.setMessage("正在打开...");
		// 设置ProgressDialog标题图标
		// xh_pDialog.setIcon(R.drawable.img2);
		// 设置ProgressDialog 的进度条是否不明确 false 就是不设置为不明确
		xh_pDialog.setIndeterminate(false);
		// 设置ProgressDialog 进度条进度
		// xh_pDialog.setProgress(100);
		// 设置ProgressDialog 是否可以按退回键取消
		xh_pDialog.setCancelable(true);
		// 让ProgressDialog显示
		xh_pDialog.show();

		new Thread() {
			public void run() {

				boolean downloadSuccess = true;
				FileOutputStream fileOutputStream = null;
				try {
					Looper.prepare();
					HttpClient client = new DefaultHttpClient();
					HttpGet get = new HttpGet(upgradeUrl);

					File f = new File(savedPath);
					if (!f.exists()) {
						f.createNewFile();
					}

					HttpResponse response = client.execute(get);
					HttpEntity entity = response.getEntity();
					// 设置进度条的总长度
					Long length = entity.getContentLength();
					xh_pDialog.setMax(length.intValue());
					//
					InputStream is = entity.getContent();
					 fileOutputStream = null;

					if (is != null && length > 0) {

						fileOutputStream = new FileOutputStream(f);

						byte[] buf = new byte[1024];
						int ch = -1;
						int count = 0;
						while ((ch = is.read(buf)) != -1) {

							if (xh_pDialog.isShowing()) {
								fileOutputStream.write(buf, 0, ch);
								// 设置进度条的值
								count += ch;
								xh_pDialog.setProgress(count);
							} else {
								downloadSuccess = false;
								break;// 取消下载
							}
						}

					} else {
						callback.onCallback(false);
					}

					if (downloadSuccess && fileOutputStream != null) {
						xh_pDialog.dismiss();
						fileOutputStream.flush();
						fileOutputStream.close();
						callback.onCallback(true);// 成功
					}
					Looper.loop();
				} catch (FileNotFoundException e) {
					xh_pDialog.dismiss();
					e.printStackTrace();
					callback.onCallback(false);
					xh_pDialog.dismiss();
				} catch (Exception e) {
					xh_pDialog.dismiss();
					e.printStackTrace();
					callback.onCallback(false);
				} finally {
					try {
						fileOutputStream.flush();
						fileOutputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}.start();

	}
}
