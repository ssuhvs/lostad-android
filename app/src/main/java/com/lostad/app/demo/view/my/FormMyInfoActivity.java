package com.lostad.app.demo.view.my;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lostad.app.base.util.DownloadUtil;
import com.lostad.app.base.util.ImageChooserUtil;
import com.lostad.app.base.util.ImageTools;
import com.lostad.app.base.util.Validator;
import com.lostad.app.base.view.BaseActivity;
import com.lostad.app.base.view.component.FormNumActivity;
import com.lostad.app.base.view.component.FormTextActivity;
import com.lostad.app.base.view.component.FormTextChinaeseActivity;
import com.lostad.app.demo.IConst;
import com.lostad.app.demo.R;
import com.lostad.app.demo.entity.UserInfo;
import com.lostad.applib.util.DialogUtil;
import com.lostad.applib.util.FileDataUtil;

import java.io.File;

public class FormMyInfoActivity extends BaseActivity {
	@ViewInject(R.id.iv_head)
	private ImageView iv_head;

	@ViewInject(R.id.tv_nickname)
	private TextView tv_nickname;

	@ViewInject(R.id.tv_sex)
	private TextView tv_sex;

	@ViewInject(R.id.tv_weight)
	private TextView tv_weight;

	@ViewInject(R.id.tv_height)
	private TextView tv_height;

	@ViewInject(R.id.tv_age)
	private TextView tv_age;

	@ViewInject(R.id.tv_addr)
	private TextView tv_addr;

	private UserInfo mSysConfig;
	private File mFileHead;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_form_myinfo);

		setTitle("个人资料");
		initUI(mSysConfig);
	}

	@OnClick(R.id.iv_head)
	public void onClickHead(View v) {
	  ImageChooserUtil.showPicturePicker(this, true);
//      Intent i = new Intent(this,HeadGridActivity.class);
//      startActivityForResult(i, 100);
	}

//	public void onClickNext(View v) {
//		next();
//	}

	public void onClickName(View v) {
		try{
			Intent i = new Intent(FormMyInfoActivity.this, FormTextChinaeseActivity.class);
			i.putExtra("value",tv_nickname.getText());
			i.putExtra(FormTextActivity.KEY_MAX_LEN, 12);
			startActivityForResult(i, 0);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void onClickSex(View v) {
		try{
			String[] itemList = {"男","女","取消"};
//			DialogUtil.showAlertMenuCust(ctx, "选择性别", itemList,new MyCallback<Integer>(){
//				@Override
//				public void onCallback(Integer index) {
//					if(0==index){
//						tv_sex.setText("男");
//						mSysConfig.setSex("1");
//					}else if(1==index){
//						tv_sex.setText("女");
//						mSysConfig.setSex("2");
//					}else{
//						tv_sex.setText("");
//						mSysConfig.setSex("");
//					}
//					update(mSysConfig);
//				}
//
//			});
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public void onClickWeight(View v) {
		Intent i = new Intent(FormMyInfoActivity.this, FormNumActivity.class);
		i.putExtra(FormNumActivity.KEY_IS_INT,true);
		i.putExtra("value",tv_weight.getText());
		i.putExtra("desc","填写体重，让系统对您的运动做出更合理的评估");
		i.putExtra(FormNumActivity.KEY_MIN_VALUE,30);
		i.putExtra(FormNumActivity.KEY_MAX_VALUE,150);
		i.putExtra(FormNumActivity.KEY_MAX_DESC, "体重不能大于150kg");
		i.putExtra(FormNumActivity.KEY_MIN_DESC, "体重不能小于30kg");
		
		i.putExtra(FormNumActivity.KEY_NULL_ABLE,false);
		startActivityForResult(i, 1);
	}

	public void onClickHeight(View v) {
		Intent i = new Intent(FormMyInfoActivity.this, FormNumActivity.class);
		i.putExtra("value",tv_height.getText());
		i.putExtra("desc","填写身高，让系统对您的运动做出更合理的评估");
		i.putExtra(FormNumActivity.KEY_MAX_VALUE, 240.0);
		i.putExtra(FormNumActivity.KEY_MAX_DESC, "身高不能高于240cm");
		i.putExtra(FormNumActivity.KEY_MIN_VALUE, 80.0);
		i.putExtra(FormNumActivity.KEY_MIN_DESC, "身高不能低于80cm");
		i.putExtra(FormNumActivity.KEY_NULL_ABLE,false);
		startActivityForResult(i, 2);
	}

	public void onClickBirt(View v) {
		Intent i = new Intent(FormMyInfoActivity.this, FormNumActivity.class);
		i.putExtra(FormNumActivity.KEY_MAX_LEN,2);
		i.putExtra(FormNumActivity.KEY_IS_INT, true);
		i.putExtra(FormNumActivity.KEY_MAX_VALUE,65);
		i.putExtra(FormNumActivity.KEY_MIN_VALUE,14);
		i.putExtra("value",tv_age.getText());
		i.putExtra("desc","填写年龄,让系统帮助您匹配更合适的健身伙伴");
		i.putExtra(FormNumActivity.KEY_NULL_ABLE,false);
		startActivityForResult(i, 3);
	}



	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {
			return;
		}
		String d;
		switch (requestCode) {
		case 0:
//			d = data.getStringExtra("data");
//			tv_nickname.setText(d);
//			mSysConfig.name(d);
//
//			if (!Validator.isBlank(d)) {
//				update(mSysConfig);
//			}
			break;
		case 1:
//			d = data.getStringExtra("data");
//			tv_weight.setText(d);
//			if (!Validator.isBlank(d)) {
//				mSysConfig.setWeight(d);
//				update(mSysConfig);
//			}
			break;
		case 2:
//			d = data.getStringExtra("data");
//			tv_height.setText(d);
//			if (!Validator.isBlank(d)) {
//				mSysConfig.setHeight(d);
//				update(mSysConfig);
//			}
			break;
		case 3:
//			d = data.getStringExtra("data");
//			tv_age.setText(d);
//			mSysConfig.setAge(d);
//			if (!Validator.isBlank(d)) {
//				update(mSysConfig);
//			}
			break;
		case 4:
//			d = data.getStringExtra("data");
//			tv_addr.setText(d);
//			mSysConfig.setProvince(PrefManager.getString(FormUserInfoActivity.this, IConst.KEY_GIS_PROVINCE));
//			mSysConfig.setCity(PrefManager.getString(FormUserInfoActivity.this,IConst.KEY_GIS_CITY));
//			if (!Validator.isBlank(d)) {
//				update(mSysConfig);
//			}
			break;
			
		case 100:
//			d = data.getStringExtra("data");
//			DownloadUtil.loadImage(iv_head, d, R.drawable.head_default, R.drawable.head_default, R.drawable.head_default);
//			mSysConfig.setPicpath(d);
//			mSysConfig.setProvince(PrefManager.getString(FormUserInfoActivity.this,IConst.KEY_GIS_PROVINCE));
//			mSysConfig.setCity(PrefManager.getString(FormUserInfoActivity.this,IConst.KEY_GIS_CITY));
//			if (!Validator.isBlank(d)) {
//				update(mSysConfig);
//			}
			break;
			
		default://图片相关的
			ImageChooserUtil.onActivityResult(ctx, requestCode, resultCode, data, new ImageChooserUtil.PicCallback() {
				@Override
				public void onPicSelected(Bitmap bitmap) {
					//System.out.println(bitmap);
					iv_head.setImageBitmap(bitmap);
					String fileName = FileDataUtil.createJpgFileName(getLoginConfig().getUserId() + "");
					mFileHead = ImageTools.savePhotoToSDCard(bitmap, IConst.PATH_ROOT, fileName);

					if (mFileHead!=null && mFileHead.exists()) {
						uploadHead(mFileHead);
					} else {
						DialogUtil.showAlert(ctx, "文件不存在！！！", null);
					}
				}
			});
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void initUI(final UserInfo config) {

		if(config == null )
			return;
		
		if(Validator.isNotEmpty(config.headUrl)){
			DownloadUtil.loadImage(this,iv_head, config.headUrl);
		}
		tv_nickname.setText(config.getName());
		String sex = mSysConfig.getSex();
		setSexValue(sex);
	}


	public void setSexValue(String sex) {
		try{
			if (sex==null || "1".equals(sex)) {//默认是男
				tv_sex.setText("男");
			} else {
				tv_sex.setText("女");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//        ComponentName c = getCallingActivity();
//       
//    	if(!mSysConfig.isInfoReay()){
//	        getMenuInflater().inflate(R.menu.menu_pass, menu);
//		}
//
//		return true;
//	}

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		
//		if (item.getItemId() == R.id.action_next) {
//			next();
//		}
//
//		return super.onOptionsItemSelected(item);
//	}


	private void uploadHead(final File mFileSelected) {
		if(mFileSelected==null){
			return ;
		}
//		new Thread(){
//			public void run() {
//				final String key =IConst.ALIYUN_OSS_KEY_PREFIX_HEDAD+"/"+FileDataUtil.createJpgFileName(getLoginConfig().getId().toString());
//				FileManager.getInstance(ctx).resumableUpload(mFileSelected.getAbsolutePath(), key,new SaveCallback() {
//					@Override
//					public void onSuccess(final String objectKey) {
//
//						mSysConfig.setPicpath(objectKey);
//						LogMe.d(key+"<<<<<<<<<<<<<<<<"+objectKey);
//						runOnUiThread(new Runnable() {
//							@Override
//							public void run() {
//								DialogUtil.showToasMsg(ctx, "上传成功！");
//								mSysConfig.setPicpath(objectKey);
//								update(mSysConfig);
//								String url = getMyApplication().getPicSSO()+objectKey;
//								//DownloadUtil.loadImage(iv_head, url,R.drawable.head_default, R.drawable.head_default, R.drawable.head_default);
//							}
//						});
//					}
//
//					@Override
//					public void onProgress(String objectKey, int byteCount, int totalSize) {
//					}
//
//					@Override
//					public void onFailure(String objectKey, OSSException ossException) {
//						ossException.printStackTrace();
//						runOnUiThread(new Runnable() {
//							@Override
//							public void run() {
//								DialogUtil.showToasMsg(ctx, "上传失败！");
//								//getFinalBitmap().display(iv_head, mSysConfig.getPicpath());
//							}
//						});
//
//					}
//				});
//
//			};
//		}.start();
	
	}

	
}
