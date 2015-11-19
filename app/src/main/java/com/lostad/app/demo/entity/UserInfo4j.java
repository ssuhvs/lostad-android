package com.lostad.app.demo.entity;

import com.lostad.applib.entity.BaseBeanRsult;

import java.util.List;

/**
 * Tour
 */
public class UserInfo4j extends BaseBeanRsult {
	public UserInfo data;

	private UserInfo4j() {}
	public UserInfo4j(boolean isSuccess, String msg) {
		super(isSuccess,msg);
	}


}
