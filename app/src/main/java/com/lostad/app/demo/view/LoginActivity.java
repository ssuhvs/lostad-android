package com.lostad.app.demo.view;


import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lostad.app.base.util.EffectUtil;
import com.lostad.app.base.util.PrefManager;
import com.lostad.app.base.view.BaseActivity;
import com.lostad.app.demo.R;
import com.lostad.app.demo.entity.LoginConfig;
import com.lostad.app.demo.task.LoginTask;
import com.lostad.applib.core.MyCallback;
import com.lostad.applib.entity.ILoginConfig;
import com.lostad.applib.util.Validator;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class LoginActivity extends BaseActivity {
    @ViewInject(R.id.et_phone)
    private TextView et_phone;
    @ViewInject(R.id.et_password)
    private TextView et_password;

    @ViewInject(R.id.btn_login)
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        x.view().inject(this);
        //社会化分享
        // UmengPlatUtil.getInstance(this);
        ILoginConfig login = getLoginConfig();
        if (login != null) {
            et_phone.setText(login.getPhone());
            et_password.setText(login.getPassword());
        }

    }

    @Event(R.id.tv_reg)
    private void onClickToReg(View v) {
        Intent i = new Intent(ctx, Register0Activity.class);
        startActivity(i);
    }

    @Event(R.id.tv_find_pwd)
    private void onClickToFindPwd(View v) {
        Intent i = new Intent(ctx, FindPwd0Activity.class);
        startActivity(i);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
//    public void onBtnClick(View v) {
//        if(!validateInternet()){
//            return;
//        }
//        int id = v.getId();
//        switch (id) {
//            case R.id.iv_qq:
//                UmengPlatUtil.getInstance(LoginActivity.this).doOauthVerify(LoginActivity.this, SHARE_MEDIA.QQ,new UmCallback() {
//                    @Override
//                    public void onCallback(String uid, Map info) {
//                        loginQQ(uid, info,"1");
//                    }
//                });
//                break;
//
//            case R.id.iv_weixin:
//                UmengPlatUtil.getInstance(LoginActivity.this).doOauthVerify(LoginActivity.this,SHARE_MEDIA.WEIXIN,new UmCallback() {
//                    @Override
//                    public void onCallback(String uid, Map info) {
//                        loginWixin(uid, info,"2");
//                    }
//                });
//                break;
//            case R.id.iv_weibo:
//                UmengPlatUtil.getInstance(LoginActivity.this).doOauthVerify(LoginActivity.this,SHARE_MEDIA.SINA,new UmCallback() {
//                    @Override
//                    public void onCallback(String uid, Map info) {
//                        loginWeibo(uid, info,"3");
//                    }
//                });
//                break;
//            case R.id.tv_protocol:
//                Intent i = new Intent(LoginActivity.this,ProtocalActivity.class);
//                String url = IConst.URL+"/xieyi.html";
//                i.putExtra("title", "用户使用协议");
//                i.putExtra("url",url);
//                startActivity(i);
//                break;
//            case R.id.btn_login:
//                loginByPhone();
//                break;
//            case R.id.iv_protocol:
//                DialogUtil.showAlertOkCancel(this, "你确定不同意此用户协议吗？取消选中状态后，您将不能使用跑伴儿服务！", new MyCallback<Boolean>() {
//                    @Override
//                    public void onCallback(Boolean yes) {
//                        if (yes) {
//                            finish();
//                        }
//                    }
//                });
//                break;
//
//        }
//    }
//    /**
//     * {sex=1, nickname=a ✨在水一方发盐人✨, unionid=olw_2sqw686P-VO6dmu4Ku5zM41E, province=, openid=oKyqRs__4PNvT2LvKrMByTmi6bo4, language=zh_CN,
//     * headimgurl=http://wx.qlogo.cn/mmopen/WD4FduqfeKJlxGhs5fdn0UCltPaOdNib2aCbObibMq4yKMhVU8vAX3XM3wicaq1ksYYpxiaKoESox36tkIUDPbiaSug/0,
//     * country=, city=}
//     * @param uid
//     * @param info
//     * @param type
//     */
//    private void loginWixin(String uid, Map info,String type) {
//        if(!validateInternet()){
//            return;
//        }
//        String nickname = info.get("nickname").toString();
//        String head = info.get("headimgurl").toString();
//        String sex = info.get("sex")+"";// null+""
//
//        if(mLoginConfig==null){
//            mLoginConfig = new LoginConfig();
//        }
//        mLoginConfig.setLoginType(type);
//        mLoginConfig.setUid3(uid);//第三方id
//        mLoginConfig.setName(nickname);
//        mLoginConfig.setHeadUrl(head);
//        mLoginConfig.setPwd("");
//        mLoginConfig.setSex("1".equals(sex)?"1":"2");
//        LoginTask loginTask = new LoginTask(LoginActivity.this,mLoginConfig);
//        loginTask.execute();
//    }
//    private void loginQQ(String uid, Map info,String type) {
//        if(!validateInternet()){
//            return;
//        }
//        String nickname = info.get("screen_name").toString();
//        String head = info.get("profile_image_url").toString();
//        String gender = info.get("gender").toString();
//        if(mLoginConfig==null){
//            mLoginConfig = new LoginConfig();
//        }
//        mLoginConfig.setLoginType(type+"");
//        mLoginConfig.setUid3(uid);//第三方id
//        mLoginConfig.setName(nickname);
//        mLoginConfig.setHeadUrl(head);
//        mLoginConfig.setPwd("");
//        mLoginConfig.setSex("女".equals(gender)?"1":"2");
//        LoginTask loginTask = new LoginTask(LoginActivity.this,mLoginConfig);
//        loginTask.execute();
//    }
//
//    /**
//     *  following.screenName = String.valueOf(user.get("name"));
//     following.description = String.valueOf(user.get("description"));
//     following.icon = String.valueOf(user.get("profile_image_url"));
//     */
//    private void loginWeibo(String uid, Map info,String type) {
//        if(!validateInternet()){
//            return;
//        }
//        String nickname = info.get("name").toString();
//        String head = info.get("profile_image_url").toString();
//        String gender = info.get("gender").toString();
//        if(mLoginConfig==null){
//            mLoginConfig = new LoginConfig();
//        }
//        mLoginConfig.setLoginType(type);
//        mLoginConfig.setUid3(uid);//第三方id
//        mLoginConfig.setName(nickname);
//        mLoginConfig.setHeadUrl(head);
//        mLoginConfig.setPwd("");
//        mLoginConfig.setSex("女".equals(gender)?"1":"2");
//        LoginTask loginTask = new LoginTask(LoginActivity.this,mLoginConfig);
//        loginTask.execute();
//    }
    @Event(R.id.btn_login)
    private void onClickLoginByPhone(View v) {

        String username = et_phone.getText().toString();
        String pwd = et_password.getText().toString();
        if (Validator.isBlank(username)) {
            et_phone.requestFocus();
            et_phone.setError(Html.fromHtml("<font color=#FFFFFF>手机号不能为空</font>"));
            EffectUtil.showShake(this, et_phone);
            return;
        }

        if (!Validator.isMobile(username)) {
            et_phone.setError(Html.fromHtml("<font color=#FFFFFF>手机号不正确</font>"));
            et_phone.requestFocus();
            et_phone.setText("");
            return;
        }
        LoginConfig mLoginConfig = new LoginConfig();
        mLoginConfig.setPhone(username);
        if (Validator.isBlank(pwd)) {
            et_password.setError(Html.fromHtml("<font color=#FFFFFF>请输入验密码</font>"));
            et_password.requestFocus();
            return;
        }

        mLoginConfig.setPwd(pwd);

        PrefManager.saveString(this, "phone", et_phone.getText().toString());
        LoginTask loginTask = new LoginTask(LoginActivity.this, mLoginConfig, new MyCallback<Boolean>() {
            @Override
            public void onCallback(Boolean success) {
                if (success) {
                    finish();
                }
            }
        });
        loginTask.execute();
    }

}

