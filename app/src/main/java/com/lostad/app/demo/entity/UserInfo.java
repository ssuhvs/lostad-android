/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// FIXME: review and cleanup

package com.lostad.app.demo.entity;

import com.lostad.applib.entity.ILoginConfig;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

@Table(name = "UserInfo")
public class UserInfo implements Serializable,ILoginConfig {
	/**
	 *
	 */

	@Column(name = "id", isId = true)
	public String id;
	public String name;
	public String nickname;
	public String pwd;
	public String headUrl;
	public String phone;
	public String phoneType;
	// 贝壳数目
	public String   loginType="0" ;//0:表示跑伴登录1:表示QQ登录2:微信登录3:新浪微博登录
	public String    uid3;//第三方登录 ｉｄ
	public String    sex;
	public String token;// 用于推送
	public Double longitude;
	public Double latitude;

	public UserInfo(String phone, String pwd) {
		this.phone = phone;
		this.pwd = pwd;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public UserInfo() {
	}

	
	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}


	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getUid3() {
		return uid3;
	}

	public void setUid3(String uid3) {
		this.uid3 = uid3;
	}


	public void setName(String name) {
		this.name = name;
	}


	


	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}


	public String getSexStr() {
		if("1".equals(sex)){
			return "女";
		}else{
			return "男";
		}
	}

	@Override
	public String getUserId() {
		return this.id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getLoginType() {
		return loginType;
	}

	@Override
	public String getPassword() {
		return this.pwd;
	}



}
