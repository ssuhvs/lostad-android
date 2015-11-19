package com.lostad.app.base.entity;

import java.io.Serializable;

public class BaseBean implements Serializable{
	public boolean success ;
	public String  msg;
	public BaseBean() {
	}
	public BaseBean(boolean success, String message) {
		this.success=success;
		this.msg = message;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
