package com.lostad.app.demo;

import android.app.Service;
import android.location.Location;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.igexin.sdk.PushManager;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.lostad.app.demo.entity.LoginConfig;
import com.lostad.app.base.util.PrefManager;
import com.lostad.applib.BaseApplication;
import com.lostad.applib.entity.ILoginConfig;

import java.io.File;
import java.util.List;

/**
 * 存放全局变量
 * 
 * @author sszvip
 * 
 */
public class MyApplication extends BaseApplication implements AMapLocationListener {

	public Vibrator mVibrator;
	private LoginConfig mLoginConfig;
	private DbUtils mDb;

    private static MyApplication instance;  
    
    public static MyApplication getInstance() {  
        return instance;  
    }  

	@Override
	public void onCreate() {
		super.onCreate();
		initFiles();
		initLocation();
		mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
		PushManager.getInstance().initialize(this.getApplicationContext());
	}

	
	private void initFiles() {
		File dir = new File(IConst.PATH_ROOT);
		if (!dir.exists()) {
			dir.mkdir();
		}
	}

	// 遍历所有Activity并finish
	public void exit() {

		clearActivity();
	}

	// public Member getCurrMember(){
	// LoginConfig lc = getLoginConfig();
	// Integer id = lc.getId();
	// if(mMember == null){
	// mMember = getDb().findById(id,Member.class);
	// }
	// return mMember;
	// }


	@Override
	public void startService() {
		// 好友联系人服务
		// Intent server = new Intent(this, IMContactService.class);
		// startService(server);
		// // 聊天服务
		// Intent chatServer = new Intent(this, IMChatService.class);
		// startService(chatServer);
		// // 系统消息连接服务
		// Intent imSystemMsgService = new
		// Intent(this,IMSystemMsgService.class);
		// startService(imSystemMsgService);

		// 自动恢复连接服务
	}

	/**
	 * 
	 * 销毁服务.
	 * 
	 * @author shimiso
	 * @update 2012-5-16 下午12:16:08
	 */
	public void stopService() {
		// // 好友联系人服务
		// Intent server = new Intent(this, IMContactService.class);
		// stopService(server);
		// // 聊天服务
		// Intent chatServer = new Intent(this, IMChatService.class);
		// stopService(chatServer);
		//
		//
		// // 系统消息连接服务
		// Intent imSystemMsgService = new Intent(this,
		// IMSystemMsgService.class);
		// stopService(imSystemMsgService);

		// 自动恢复连接服务
//		Intent reConnectService = new Intent(this, ReConnectService.class);
//		stopService(reConnectService);
	}

	@Override
	public DbUtils getDb() {
		if(mDb==null){
           mDb = DbUtils.create(this,IConst.PATH_ROOT,IConst.DB_NAME);
		}
		return mDb;
	}

	@Override
	public ILoginConfig getLoginConfig() {
		if(mLoginConfig==null){
			try {//数据库只存储一个用户
				List<LoginConfig> list = getDb().findAll(LoginConfig.class);
				if(list!=null && list.size()>0){
					mLoginConfig = list.get(0);
				}
			} catch (DbException e) {
				e.printStackTrace();
			}
		}
		return mLoginConfig;
	}

	@Override
	public void saveLoginConfig(ILoginConfig login) {
        if(login!=null){
			try {
				mLoginConfig = (LoginConfig)login;
				getDb().saveOrUpdate(mLoginConfig);
			} catch (DbException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 实现实位回调监听
	 */
//	 public class MyLocationListener implements BDLocationListener {
//	
//	 @Override
//	 public void onReceiveLocation(BDLocation location) {
//	 String province = location.getProvince();
//	 String city = location.getCity();
//	 String district = location.getDistrict();//区
//	 Double lat = location.getLatitude();
//	 Double log = location.getLongitude();
//	
//	 PrefManager.saveToPref(getApplicationContext(), IConst.KEY_GIS_PROVINCE,
//	 province);
//	 PrefManager.saveToPref(getApplicationContext(), IConst.KEY_GIS_CITY,
//	 city);
//	 PrefManager.saveToPref(getApplicationContext(), IConst.KEY_GIS_DISTRICT,
//	 district);
//	
//	 PrefManager.saveFloatToPref(getApplicationContext(),
//	 IConst.KEY_GIS_LATITUDE, lat.floatValue());
//	 PrefManager.saveFloatToPref(getApplicationContext(),
//	 IConst.KEY_GIS_LONGTITUDE, log.floatValue());
//	 }
//	
//	 }
	//

	/**
	 * 初始化定位
	 */
	private void initLocation() {
		// 初始化定位，只采用网络定位
		mLocationManagerProxy = LocationManagerProxy.getInstance(this);
		mLocationManagerProxy.setGpsEnable(false);
		// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
		// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用removeUpdates()方法来取消定位请求
		// 在定位结束后，在合适的生命周期调用destroy()方法
		// 其中如果间隔时间为-1，则定位只定一次,
		// 在单次定位情况下，定位无论成功与否，都无需调用removeUpdates()方法移除请求，定位sdk内部会移除
		mLocationManagerProxy.requestLocationData(
				LocationProviderProxy.AMapNetwork, 60 * 1000, 15, this);

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
	}

	@Override
	public void onProviderEnabled(String arg0) {
	}

	@Override
	public void onProviderDisabled(String arg0) {
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLocationChanged(AMapLocation location) {

		if (location != null && location.getAMapException().getErrorCode() == 0) {
			// 定位成功回调信息，设置相关消息

			String province = location.getProvince();
			String city = location.getCity();
			String district = location.getDistrict();// 区
			Double lat = location.getLatitude();
			Double log = location.getLongitude();

			PrefManager.saveString(getApplicationContext(),
					IConst.KEY_GIS_PROVINCE, province);
			PrefManager.saveString(getApplicationContext(),
					IConst.KEY_GIS_CITY, city);
			PrefManager.saveString(getApplicationContext(),
					IConst.KEY_GIS_DISTRICT, district);

			PrefManager.saveFloat(getApplicationContext(),
					IConst.KEY_GIS_LATITUDE, lat.floatValue());
			PrefManager.saveFloat(getApplicationContext(),
					IConst.KEY_GIS_LONGTITUDE, log.floatValue());

		} else {
			Log.e("AmapErr", "Location ERR:"
					+ location.getAMapException().getErrorCode());
		}

	}

	private LocationManagerProxy mLocationManagerProxy;

	@Override
	public void quit(boolean isClearData) {
		try {
			stopService();
			clearActivity();
			if(isClearData){
				getDb().deleteAll(LoginConfig.class);
			}
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
