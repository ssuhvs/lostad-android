package com.lostad.app.base.view.component;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.method.NumberKeyListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lostad.app.demo.R;
import com.lostad.app.base.util.Validator;


public class FormNumActivity extends BaseFormActivity {
	public static final String KEY_VALUE = "value";
	public static final String KEY_DESC = "desc";
	public static final String KEY_MAX_VALUE = "max_value";
	public static final String KEY_MIN_VALUE = "min_value";
	public static final String KEY_MIN_DESC = "min_desc";
	public static final String KEY_MAX_DESC = "max_desc";
	public static final String KEY_IS_INT = "key_is_int";
	public static final String KEY_NUM_AFTER_POINT = "key_num_after_point";
	@ViewInject( R.id.tv_desc)
	private TextView tv_desc;

	@ViewInject( R.id.et_input)
	private EditText et_input;

	private String value = null;
	private String desc = null;

	private Number maxV, minV;
	private Integer numAfterPoint;
	private String maxDesc, minDesc;
	private boolean isInt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_form_edit_text_lib);
		Intent i = getIntent();
		value = i.getStringExtra(KEY_VALUE);
		desc = i.getStringExtra(KEY_DESC);

		maxDesc = i.getStringExtra(KEY_MAX_DESC);
		minDesc = i.getStringExtra(KEY_MIN_DESC);
		// 小数点后的位数
		numAfterPoint = i.getIntExtra(KEY_NUM_AFTER_POINT, 1);
		isInt = i.getBooleanExtra(KEY_IS_INT, false);
		et_input = (EditText) findViewById(R.id.et_input);
		initMaxLen(et_input);
		if (isInt) {
			maxV = i.getIntExtra(KEY_MAX_VALUE, Integer.MAX_VALUE);
			minV = i.getIntExtra(KEY_MIN_VALUE, Integer.MIN_VALUE);
		} else {
			
			maxV = i.getDoubleExtra(KEY_MAX_VALUE, Double.MIN_VALUE);
			minV = i.getDoubleExtra(KEY_MIN_VALUE, Double.MIN_VALUE);
		}

		if (Validator.isNotEmpty(value)) {
			et_input.setText(value);
		}
		if (Validator.isNotEmpty(desc)) {
			tv_desc.setText(desc);
		}

		if (isInt) {
			et_input.setKeyListener(new NumberKeyListener() {
				@Override
				public int getInputType() {

					return InputType.TYPE_CLASS_NUMBER
							| InputType.TYPE_NUMBER_VARIATION_NORMAL;
				}

				@Override
				protected char[] getAcceptedChars() {
					char[] numberChars = { '1', '2', '3', '4', '5', '6', '7',
							'8', '9', '0' };
					return numberChars;
				}
			});
		} else {// 浮点类型
			et_input.setKeyListener(new DigitsKeyListener(false, true));
			et_input.addTextChangedListener(new TextWatcher() {
				@Override
				public void onTextChanged(CharSequence ss, int start,
						int before, int count) {
					String s = ss.toString().trim();
					if (s.toString().trim().substring(0).equals(".")) {
						s = "0" + s;
						et_input.setText(s);
						et_input.setSelection(2);
					}

					if (s.toString().contains(".")) {
						if (s.length() - 1 - s.indexOf(".") > numAfterPoint) {
							int end = s.indexOf(".") + numAfterPoint;
							s = s.substring(0, end + 1);
							et_input.setText(s);
							et_input.setSelection(s.length());
						}
					}

					if (s.startsWith("0") && s.length() > 1) {// 以0打头，
						if (!s.toString().substring(1, 2).equals(".")) {// 不以.随后
							et_input.setText(s.subSequence(0, 1));// 只保留0
							et_input.setSelection(1);

							return;
						}
					}
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {

				}

				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub

				}

			});
		}

		// et_input.setKeyListener(new NumberKeyListener(){
		// @Override
		// protected char[] getAcceptedChars(){
		// char[] numberChars = {'1','2','3','4','5','6','7','8','9','0','.'};
		// return numberChars;
		// }
		//
		// @Override
		// public int getInputType() {
		// return android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL;
		// }
		//
		// });
		// et_input.requestFocus();
		// InputMethodManager inputManager
		// =(InputMethodManager)et_input.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		// inputManager.showSoftInput(et_input, 0);
		// et_input.setFocusable(true);
		// et_input.requestFocus();
		// openInput();

		et_input.setFocusable(true);
		et_input.requestFocus();
		openInput();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_ok, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_ok) {
			String v = et_input.getText().toString();
			if (!super.isNullAble) {// 不可以为空
				if (Validator.isBlank(v)) {// 如果是空
					showToast("内容不能为空！");
					return true;
				}
			}
			try {
				if (isInt) {

					int d = Integer.parseInt(v);
					int maxV1 = maxV.intValue();
					int minV1 = minV.intValue();
					if (maxV1 != Integer.MIN_VALUE) {
						if (d > maxV1) {
							if (Validator.isNotEmpty(minDesc)) {
								showToast(maxDesc);
							} else {
								showToast("数值不应大于最大值" + maxV);
							}
							return true;
						}
					}

					if (minV1 != Integer.MIN_VALUE) {

						if (d < minV1) {
							if (Validator.isNotEmpty(minDesc)) {
								showToast(minDesc);
							} else {
								showToast("数值不应最小值" + minV);
							}

							return true;
						}

					}
					
				} else {
					double d = Double.parseDouble(v);
					double maxV1 = maxV.doubleValue();
					double minV1 = minV.doubleValue();
					if (maxV1 != Double.MIN_VALUE) {
						if (d > maxV1) {
							if (Validator.isNotEmpty(minDesc)) {
								showToast(maxDesc);
							} else {
								showToast("数值不应大于最大值" + maxV);
							}
							return true;
						}
					}

					if (minV1 != Double.MIN_VALUE) {

						if (d < minV1) {
							if (Validator.isNotEmpty(minDesc)) {
								showToast(minDesc);
							} else {
								showToast("数值不应最小值" + minV);
							}

							return true;
						}

					}
				}
			} catch (Exception e) {
				showToast("请输入数字！");
				e.printStackTrace();
				return true;
			}

			if (v.endsWith(".")) {
				showToast("请输入正确的数字！");
				return true;
			}
			if (v.startsWith("0") && !v.equals("0")) {
				if (!v.startsWith("0.")) {
					showToast("请输入正确的数字！");
					return true;
				}
			}

			Intent intent = new Intent();
			String data = et_input.getText().toString();
			intent.putExtra("data", data);
			setResult(RESULT_OK, intent);
			finish();
		}

		return super.onOptionsItemSelected(item);
	}

}
