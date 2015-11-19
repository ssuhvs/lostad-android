package com.lostad.app.demo.entity;

import java.io.Serializable;

/**
 * {XMJJ=去山东游玩, OBJID=1, XMTB=山东图标, XMMC=我要去山东，XMTP=xxxxxxxxxxx},
 *------------下面是活动基础信息-----------------------------------------------
 * {
	 "OBJID":"1",//
	 "HDTS":"5",//活动天数
	 "HDDD":"济南",//活动地点
	 "MQRS":"50",//每期人数
	 "BMJZRQ":"2015-12-31"//报名截止日期
	 }
 */
public class Tour implements Serializable{
	//列表内容
	public String id;
	public String desc;//简介
	public String picUrl;//名称
	public String title;//图片

	public Tour() {
	}


}
