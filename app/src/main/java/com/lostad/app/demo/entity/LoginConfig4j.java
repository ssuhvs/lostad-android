package com.lostad.app.demo.entity;

import com.lostad.applib.entity.BaseBeanRsult;

/**
 * Tour
 */
public class LoginConfig4j extends BaseBeanRsult {
	public LoginConfig data;

	private LoginConfig4j() {}
	public LoginConfig4j(boolean isSuccess, String msg) {
		super(isSuccess,msg);
	}


}
