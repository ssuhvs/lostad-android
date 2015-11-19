package com.lostad.app.base.view.component;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import com.lostad.app.demo.R;
import com.lostad.app.base.util.LogMe;
import com.lostad.app.base.util.Validator;
import com.lostad.app.base.view.BaseActivity;

public class CommonBrowserActivity extends BaseActivity {
	private String reportURL;
	private WebView mWebView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.getWindow().requestFeature(Window.FEATURE_PROGRESS);//
		super.onCreate(savedInstanceState);

		setContentView(R.layout.report_link);
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
					String defaultValue, final JsPromptResult result) {
				final LayoutInflater factory = LayoutInflater
						.from(CommonBrowserActivity.this);
				final View v = factory.inflate(
						R.layout.browser_js_prompt_dialog_lib, null);
				((TextView) v.findViewById(R.id.prompt_message_text))
						.setText(message);
				((EditText) v.findViewById(R.id.prompt_input_field))
						.setText(defaultValue);

				AlertDialog.Builder b = new AlertDialog.Builder(
						CommonBrowserActivity.this);
				b.setTitle("Prompt");
				b.setView(v);
				b.setPositiveButton(android.R.string.ok,
						new AlertDialog.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								String value = ((EditText) v
										.findViewById(R.id.prompt_input_field))
										.getText().toString();
								result.confirm(value);
							}
						});
				b.setNegativeButton(android.R.string.cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								result.cancel();
							}
						});
				b.setOnCancelListener(new DialogInterface.OnCancelListener() {
					public void onCancel(DialogInterface dialog) {
						result.cancel();
					}
				});
				b.show();
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
