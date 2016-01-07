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




	public UserInfo4j login(String phone,String password) {
		UserInfo4j c = new UserInfo4j(true,"success");
        c.data = new UserInfo();
		c.data.id = "testId";
		c.data.nickname = "nickname";
		c.data.name = "TestName";
		c.data.phone = "15865257900";

		try {
//			Gson g = new Gson();
//
//          Map map = new HashMap();
//			map.put("phone",phone);
//			map.put("password",password);
//
//			String param = g.toJson(map);
//			LogMe.d("param", param);
//			String j = RequestUtil.postJson( IConst.URL_SERVICE , param);
//			LogMe.d("data", j);
//			c = g.fromJson(j, UserInfo4j.class);
//			if(c==null ){
//				c = new UserInfo4j(false,"服务器返回数据异常");
//			}
		} catch (Exception e) {
			c = new UserInfo4j(false,"服务器返回数据异常！"+e.getMessage());
			e.printStackTrace();
		}
		return c;
	}

	public UserInfo4j saveUser(UserInfo u) {
		UserInfo4j c = null;
		try {
			Gson g = new Gson();

			Map map = new HashMap();
			List<UserInfo> list = new ArrayList<>();
			list.add(u);
            map.put("mprList",list);

			String param = g.toJson(map);
			LogMe.d("param", param);
			String j = RequestUtil.postJson( IConst.URL_SERVICE , param);
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
	 * { 
		 "ACTIONNAME":"setPassWord_zc",
		 "DATA":{"SJHM":"13365319122","DLMM":"123456"},
		 "KEY": " ",
		 "MODULE": "",
		 "MSG": "",
		 "REQUESTFLAG": "",
		 "TARGET": "",
		 "USERINFO": "{}" }
	 * @param phone 手机号
	 * @param pwd   密码
	 * @return
	 */
	public BaseBeanRsult register(String phone,String pwd) {
		BaseBeanRsult c = null;

		Gson g = new Gson();
		try {

			Map map = new HashMap();
			map.put("SJHM", phone);
			map.put("DLMM",pwd);

			String param = g.toJson(map);
			LogMe.d("param", param);
			String j = RequestUtil.postJson(IConst.URL_SERVICE, param);
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

	public BaseBeanRsult updatePwd(String phone,String pwd) {
		BaseBeanRsult c = null;

		Gson g = new Gson();
		try {

			Map map = new HashMap();
			map.put("SJHM", phone);
			map.put("DLMM",pwd);

			String param = g.toJson(map);
			LogMe.d("param", param);
			String j = RequestUtil.postJson(IConst.URL_SERVICE, param);
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
			String j = RequestUtil.postJson( IConst.URL_SERVICE , param);
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
