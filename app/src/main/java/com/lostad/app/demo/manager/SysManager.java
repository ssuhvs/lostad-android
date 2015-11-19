package com.lostad.app.demo.manager;

import com.google.gson.Gson;
import com.lostad.app.IConst;
import com.lostad.app.base.util.RequestUtil;
import com.lostad.applib.entity.BaseBeanRsult;
import com.lostad.applib.util.LogMe;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sszvip@qq.com
 * @date   2015-10-21
 *
 */
public class SysManager
{
	private static SysManager instance;

	private SysManager(){
		
	}
	
	public static synchronized SysManager getInstance(){
		if(instance==null){
			instance = new SysManager();
		}
		
		return instance;
	}



	/**
     * 获取验证码
	 *
	 * {     "ACTIONNAME": "getVerifyCode",     "DATA":{"SJHM":"13365319122"},
			 "KEY": " ",
			 "MODULE": "",
			 "MSG": "",
			 "REQUESTFLAG": "",
			 "TARGET": "",
			 "USERINFO": "{}" }
     *"
     */
	public BaseBeanRsult getVerifyCode(String phone) {
		BaseBeanRsult c = null;
		Gson g = new Gson();
		try {
            Map m = new HashMap();
			m.put("SJHM",phone);

			String data = g.toJson(m);
			LogMe.d("data", data);
			String j = RequestUtil.postJson( IConst.URL_SERVICE , data);
			LogMe.d("data",data);
			c = g.fromJson(j, BaseBeanRsult.class);
			if(c==null ){
				c = new BaseBeanRsult(false,"服务器返回数据异常");
			}
		} catch (Exception e) {
			c = new BaseBeanRsult(false,"服务器返回数据异常！"+e.getMessage());
			e.printStackTrace();
		}
		return c;
	}

	public BaseBeanRsult getVerifyCodeForUpdatePwd(String phone) {
		BaseBeanRsult c = null;
		Gson g = new Gson();
		try {

			Map m = new HashMap();
			m.put("phone",phone);

			String data = g.toJson(m);
			LogMe.d("data", data);
			String j = RequestUtil.postJson( IConst.URL_SERVICE , data);
			LogMe.d("data",data);
			c = g.fromJson(j, BaseBeanRsult.class);
			if(c==null ){
				c = new BaseBeanRsult(false,"服务器返回数据异常");
			}
		} catch (Exception e) {
			c = new BaseBeanRsult(false,"服务器返回数据异常！"+e.getMessage());
			e.printStackTrace();
		}
		return c;
	}

	public BaseBeanRsult validateCode(String phone,String code) {
		BaseBeanRsult c = null;
		Gson g = new Gson();
		try {

			Map m = new HashMap();
			m.put("phone",phone);
			m.put("vercode",phone);
			String data = g.toJson(m);
			LogMe.d("data", data);
			String j = RequestUtil.postJson( IConst.URL_SERVICE , data);
			LogMe.d("data",data);
			c = g.fromJson(j, BaseBeanRsult.class);
			if(c==null ){
				c = new BaseBeanRsult(false,"服务器返回数据异常");
			}
		} catch (Exception e) {
			c = new BaseBeanRsult(false,"服务器返回数据异常！"+e.getMessage());
			e.printStackTrace();
		}
		return c;
	}

	public BaseBeanRsult validateCodeForUpdatePwd(String phone,String code) {
		BaseBeanRsult c = null;
		Gson g = new Gson();
		try {
			Map m = new HashMap();

			String data = g.toJson(m);
			LogMe.d("data", data);
			String j = RequestUtil.postJson( IConst.URL_SERVICE , data);
			LogMe.d("data",data);
			c = g.fromJson(j, BaseBeanRsult.class);
			if(c==null ){
				c = new BaseBeanRsult(false,"服务器返回数据异常");
			}
		} catch (Exception e) {
			c = new BaseBeanRsult(false,"服务器返回数据异常！"+e.getMessage());
			e.printStackTrace();
		}
		return c;
	}
}
