package com.lostad.applib.entity;

import java.io.Serializable;

public interface ILoginConfig extends Serializable{
	String  getUserId();//用户id
	String  getName();//用户名称
	String  getPhone();//用户手机
	String  getUid3();//第三方登陆openId
	
	String  getPassword();
	String  getSex();
	String  getLoginType();
	
	String getToken();
	String getHeadUrl();

}
