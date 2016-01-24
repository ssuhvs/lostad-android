package com.lostad.app.demo.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lostad.app.base.view.BaseActivity;
import com.lostad.app.demo.IConst;
import com.lostad.app.demo.R;
import com.lostad.app.demo.entity.LoginConfig;
import com.lostad.app.demo.manager.UserManager;
import com.lostad.app.demo.task.LoginTask;
import com.lostad.applib.core.MyCallback;
import com.lostad.applib.entity.BaseBeanRsult;
import com.lostad.applib.util.DialogUtil;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 *
 * 用户注册
 *  @Author  sszvip@qq.com
 * */

public class FindPwd1Activity extends BaseActivity {

	@ViewInject(R.id.tb_toolbar)
	private Toolbar  tb_toolbar;

	@ViewInject(R.id.et_register_pass)
	private EditText  et_register_pass;

	@ViewInject(R.id.et_register_repass)
	private EditText  et_register_repass;

	public String mPhone;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_update_pwd1);
		x.view().inject(this);
		super.initToolBarWithBack(tb_toolbar);
		setTitle("修改密码");

		mPhone = getIntent().getStringExtra("phone");
	}


	@Event(R.id.tv_protocol)
	public void onClickProtocal(View arg0) {
		String url = IConst.URL_BASE + IConst.API_PROTOCOL;
		Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		startActivity(it);
	}

	@Event(R.id.btn_register)
	public void onClickReg(View arg0) {
		updatePwd();
	}
	/**
	 *
	 */
	private void updatePwd() {

		final String psw = et_register_pass.getText().toString();
		String pew_next = et_register_repass.getText().toString();
		if ("".equals(psw)) {
			Toast.makeText(this, "请输入您的登录密码!", Toast.LENGTH_LONG).show();
			return;
		}
		if (psw.length() < 6) {
			Toast.makeText(this, "密码长度应该大于6位!", Toast.LENGTH_LONG).show();
			return;
		}

		if("".equals(pew_next)) {
			Toast.makeText(this, "请输入您的确认密码!", Toast.LENGTH_LONG).show();
			return;
		}

		if (!psw.equals(pew_next)) {
			Toast.makeText(this, "您两次输入的密码不一致，请确认!", Toast.LENGTH_LONG).show();
			return;
		}

		final LoginConfig lc = new LoginConfig();
		DialogUtil.showProgress(this);
		new Thread() {
			BaseBeanRsult b;
			public void run() {
				b = UserManager.getInstance().findPwd(mPhone,psw);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						DialogUtil.dismissProgress();
						if (b.isSuccess()) { //注册成功后直接登陆
							lc.password = psw;
							lc.phone = mPhone;
							LoginTask lt = new LoginTask(FindPwd1Activity.this, lc, new MyCallback<Boolean>() {
								@Override
								public void onCallback(Boolean success) {
									if(success){
										toMainActivty();
									}
								}
							});
							lt.execute();
						}
						Toast.makeText(FindPwd1Activity.this,b.getMsg(),Toast.LENGTH_LONG).show();
					}
				});

			}
		}.start();
	}

}