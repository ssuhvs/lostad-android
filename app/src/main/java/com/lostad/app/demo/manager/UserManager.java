package com.lostad.app.demo.manager;

import com.google.gson.Gson;
import com.lostad.app.demo.IConst;
import com.lostad.app.base.util.LogMe;
import com.lostad.app.base.util.RequestUtil;
import com.lostad.app.demo.entity.UserInfo4j;
import com.lostad.applib.entity.BaseBeanRsult;
import com.lostad.app.demo.entity.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sszvip@qq.com
 * @date   2015-10-21
 *
 */
public class UserManager
{
	private static UserManager instance;

	private UserManager(){

	}

	public static synchronized UserManager getInstance(){
		if(instance==null){
			instance = new UserManager();
		}

		return instance;
	}




	public LoginConfig4j login(String phone,String password) {
		LoginConfig4j c = null;
		try {
			Gson g = new Gson();
			String url = IConst.URL_BASE+IConst.API_LOGIN;
			url = String.format(url,phone,password);
			String j = RequestUtil.getRequest(url,false);
			LogMe.d("data", j);
			c = g.fromJson(j, LoginConfig4j.class);
			if(c==null ){
				c = new LoginConfig4j(false,"服务器返回数据异常");
			}
		} catch (Exception e) {
			c = new LoginConfig4j(false,"服务器返回数据异常！"+e.getMessage());
			e.printStackTrace();
		}
		return c;
	}

	public UserInfo4j updateUser(UserInfo u) {
		UserInfo4j c = null;
		try {
			Gson g = new Gson();

			Map map = new HashMap();
			List<UserInfo> list = new ArrayList<>();
			list.add(u);
			map.put("mprList", list);

			String param = g.toJson(map);
			LogMe.d("param", param);
			String url = IConst.URL_BASE + IConst.API_USER_UPDATE;
			String j = RequestUtil.postJson(url, param);
			LogMe.d("data",j);
			c = g.fromJson(j, UserInfo4j.class);
			if(c==null ){
				c = new UserInfo4j(false,"服务器返回数据异常");
			}
		} catch (Exception e) {
			c = new UserInfo4j(false,"服务器返回数据异常！"+e.getMessage());
			e.printStackTrace();
		}
		return c;
	}

	/**
	 * 注册
	 * @param phone 手机号
	 * @param password   密码
	 * @return
	 */
	public BaseBeanRsult register(String phone,String password) {
		BaseBeanRsult c = null;

		Gson g = new Gson();
		try {

			Map map = new HashMap();
			map.put("phone", phone);
			map.put("password", password);

			String param = g.toJson(map);
			LogMe.d("param", param);
			String j = RequestUtil.postJson(IConst.API_REGISTER, param);
			LogMe.d("data",j);
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

	public BaseBeanRsult findPwd(String phone,String pwd) {
		BaseBeanRsult c = null;

		Gson g = new Gson();
		try {
			String url = IConst.URL_BASE+IConst.API_PWD_UPDATE;
			url = String.format(url,phone,pwd);
			String j = RequestUtil.getRequest(url, false);
			LogMe.d("data",j);
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

	public BaseBeanRsult updatePwd(String phone,String pwd,String newPwd) {
		BaseBeanRsult c = null;

		Gson g = new Gson();
		try {
			String url = IConst.URL_BASE+IConst.API_PWD_UPDATE;
			url = String.format(url,phone,pwd,newPwd);
			String j = RequestUtil.getRequest(url, false);
			LogMe.d("data",j);
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

	public UserInfo4j updateMyInfo(UserInfo u) {
		UserInfo4j c = null;
		try {
			Gson g = new Gson();

			String param = g.toJson(u);
			LogMe.d("param", param);
			String url = IConst.URL_BASE+IConst.API_USER_UPDATE;
			String j = RequestUtil.postJson( url , param);
			LogMe.d("data",j);
			c = g.fromJson(j, UserInfo4j.class);
			if(c==null ){
				c = new UserInfo4j(false,"服务器返回数据异常");
			}
		} catch (Exception e) {
			c = new UserInfo4j(false,"服务器返回数据异常！"+e.getMessage());
			e.printStackTrace();
		}
		return c;
	}
}
