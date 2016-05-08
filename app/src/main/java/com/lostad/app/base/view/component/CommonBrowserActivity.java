package com.lostad.app.base.view.component;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lostad.app.base.util.LogMe;
import com.lostad.app.base.util.Validator;
import com.lostad.app.base.view.BaseActivity;
import com.lostad.app.demo.R;
import com.lostad.applib.core.MyCallback;
import com.lostad.applib.util.DialogUtil;

import org.xutils.x;

public class CommonBrowserActivity extends BaseActivity {
	private String reportURL;
	private WebView mWebView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.getWindow().requestFeature(Window.FEATURE_PROGRESS);//
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report_link);

		x.view().inject(this);
		super.initToolBarWithBack((Toolbar)findViewById(R.id.toolbar));

		Intent i = getIntent();
		String title = i.getStringExtra("title");
		reportURL = i.getStringExtra("url");
		if (!Validator.isBlank(title)) {
			setTitle(title);
		}

		LogMe.d("mark", "地址为：" + reportURL);
		mWebView = (WebView) findViewById(R.id.web_report);
		// mWebView.getSettings().setBuiltInZoomControls(true);
		mWebView.getSettings().setSupportZoom(true);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		mWebView.getSettings().setDefaultTextEncodingName("gbk");
		mWebView.requestFocus(); // 触摸焦点起作用.如果不设置，则在点击网页文本输入框时，不能弹出软键盘及不响应其他的一些事件。
		// /////////////////////////////////////////////////////////////////////////////
		// webView.loadUrl(reportURL);
		// webView.setWebViewClient(new WebViewClient() {
		// @Override
		// public boolean shouldOverrideUrlLoading(WebView view, String url) {
		// // TODO Auto-generated method stub
		// return super.shouldOverrideUrlLoading(view, url);
		// view.loadUrl(url);
		// return true;
		// }
		// });
		// /////////////////////////////////////////////////////////////////////////////

		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// return super.shouldOverrideUrlLoading(view, url);
				view.loadUrl(url);
				return true;
			}
		});
		mWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					final JsResult result) {
				try {
					AlertDialog.Builder b = new AlertDialog.Builder(
							getApplicationContext());
					b.setTitle("Alert");
					b.setMessage(message);
					b.setPositiveButton(android.R.string.ok,
							new AlertDialog.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									result.confirm();
								}
							});
					b.setCancelable(false);
					b.create();
				} catch (Exception e) {
					e.printStackTrace();
				}

				return true;
			}

			@Override
			public boolean onJsConfirm(WebView view, String url,
					String message, final JsResult result) {

				try {

					AlertDialog.Builder b = new AlertDialog.Builder(
							CommonBrowserActivity.this);
					b.setTitle("Confirm");
					b.setMessage(message);
					b.setPositiveButton(android.R.string.ok,
							new AlertDialog.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									result.confirm();
								}
							});
					b.setNegativeButton(android.R.string.cancel,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									result.cancel();
								}
							});
					b.setCancelable(false);
					b.create();
					b.show();
				} catch (Exception e) {
					e.printStackTrace();
				}

				return true;
			}

			@Override
			public boolean onJsPrompt(WebView view, String url, String message,
				final String defaultValue, final JsPromptResult result) {
				DialogUtil.showAlertOkCancel(CommonBrowserActivity.this, message, new MyCallback<Boolean>() {
					@Override
					public void onCallback(Boolean ok) {
						if(ok){
							result.confirm(defaultValue);
						}else{
							result.cancel();
						}
					}
				});
				return true;
			}

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				CommonBrowserActivity.this.getWindow().setFeatureInt(
						Window.FEATURE_PROGRESS, newProgress * 100);
				super.onProgressChanged(view, newProgress);
			}

			@Override
			public void onReceivedTitle(WebView view, String title) {
				CommonBrowserActivity.this.setTitle(title);
				super.onReceivedTitle(view, title);
			}
		});

		mWebView.loadUrl(this.reportURL);

	}// end method onCreate

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
			mWebView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}


}
