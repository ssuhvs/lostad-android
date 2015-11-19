package com.lostad.applib.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

import java.util.UUID;

public class PhoneUtil {
	private static String imei = null;

	public static String getImei(Activity ctx) {

//		if (imei == null) {
//			try {
//				imei = ((TelephonyManager) ctx
//						.getSystemService(ctx.TELEPHONY_SERVICE)).getDeviceId();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//
//		if (imei == null) {
//			imei = "pad";
//		}
		
		return getAndroidId(ctx);
	}
	
	public static String getVersionName(Context context)//获取版本号
	{
		try {
			PackageInfo pi=context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return pi.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "获取版本名称失败！";
		}
	}
	public static int getVersionCode(Context context)//获取版本号(内部识别号)
	{
		try {
			PackageInfo pi=context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return pi.versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	public static String getAndroidId(Activity ctx){
	String id =	Secure.getString(ctx.getContentResolver(), Secure.ANDROID_ID);
    return id;
	}
	public static void main(String[] args) {
		imei = "1";
		if (imei.length() < 15) {// 不够15位的补够15位
			String str = "01234567890abcdefghijklmn";
			imei = imei + str.substring(0, 15 - imei.length());
		} else if (imei.length() > 15) {
			imei = imei.substring(0, 15);
		}

		System.out.println(imei);
		System.out.println(imei.length());
	}

	public static String getPhoneType() {
		return android.os.Build.MODEL;
	}

	public static String getDeviceId(Context ctx){
		  TelephonyManager mTelephonyManager = (TelephonyManager)ctx.getSystemService(Context.TELEPHONY_SERVICE);
	        return  mTelephonyManager.getDeviceId();
	}
	// public static String getLocalIpAddress() {
	// try {
	//
	// for (Enumeration<NetworkInterface> en = NetworkInterface
	// .getNetworkInterfaces(); en.hasMoreElements();) {
	// NetworkInterface intf = en.nextElement();
	// for (Enumeration<InetAddress> enumIpAddr = intf
	// .getInetAddresses(); enumIpAddr.hasMoreElements();) {
	// InetAddress inetAddress = enumIpAddr.nextElement();
	// if (!inetAddress.isLoopbackAddress()) {
	// return inetAddress.getHostAddress().toString();
	// }
	// }
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// return "";
	// }

//	public static String getLocalIpAddress() {
//		try {
//			for (Enumeration<NetworkInterface> en = NetworkInterface
//					.getNetworkInterfaces(); en.hasMoreElements();) {
//				NetworkInterface intf = en.nextElement();
//				for (Enumeration<InetAddress> enumIpAddr = intf
//						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
//					InetAddress inetAddress = enumIpAddr.nextElement();
//					if (!inetAddress.isLoopbackAddress()) {
//						return inetAddress.getHostAddress().toString();
//					}
//				}
//			}
//		} catch (SocketException ex) {
//			ex.printStackTrace();
//		}
//		return null;
//	}

//	public static String getLocalIpAddress_v4() {
//		try {
//			for (Enumeration<NetworkInterface> en = NetworkInterface
//					.getNetworkInterfaces(); en.hasMoreElements();) {
//				NetworkInterface intf = en.nextElement();
//				for (Enumeration<InetAddress> enumIpAddr = intf
//						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
//					InetAddress inetAddress = enumIpAddr.nextElement();
//					if (!inetAddress.isLoopbackAddress()
//							&& InetAddressUtils.isIPv4Address(inetAddress
//									.getHostAddress())) {
//						return inetAddress.getHostAddress().toString();
//					}
//				}
//			}
//		} catch (SocketException e) {
//			e.printStackTrace();
//		}
//
//		return null;
//	}
	
	
	/**
	 * 不可变
	 * @param ctx
	 * @return
	 */
	public static String getMyStaticUUID(Activity ctx) {

		final TelephonyManager tm = (TelephonyManager) ctx.getBaseContext()
				.getSystemService(Context.TELEPHONY_SERVICE);
		final String tmDevice, tmSerial, tmPhone, androidId;
		tmDevice = "" + tm.getDeviceId();
		tmSerial = "" + tm.getSimSerialNumber();
		androidId = ""+ Secure.getString(
						ctx.getContentResolver(),
						Secure.ANDROID_ID);
		UUID deviceUuid = new UUID(androidId.hashCode(),
				((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
		String uniqueId = deviceUuid.toString();
		LogMe.d("debug", "uuid=" + uniqueId);
		return uniqueId;
	}

	/**
	 *  动态可变
	 * @return
	 */
	public static String generateUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
