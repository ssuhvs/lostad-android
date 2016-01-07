package com.lostad.app.demo.manager;

import com.google.gson.Gson;
import com.lostad.app.demo.IConst;
import com.lostad.app.base.util.LogMe;
import com.lostad.app.base.util.RequestUtil;
import com.lostad.app.demo.entity.Tour;
import com.lostad.app.demo.entity.TourList4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sszvip@qq.com
 * @date   2015-10-21
 *
 */
public class TourManager
{
	private static TourManager instance;
	
	private  TourManager(){
		
	}
	
	public static synchronized TourManager getInstance(){
		if(instance==null){
			instance = new TourManager();
		}
		
		return instance;
	}



	/**
     * 旅游活动
     *"DATA":{"start":"0","limit":"20"}
     */
	public TourList4j listTourAll(int start) {
		TourList4j g4j = null;

		List<Tour> list = new ArrayList<Tour>();
		list.add(new Tour("1", "title1", null,"Lostad-android framework"));
		list.add(new Tour("2","title12",null,"Lostad-android framework"));
		list.add(new Tour("3","title13",null,"Lostad-android framework"));
		list.add(new Tour("4","title14",null,"Lostad-android framework"));
		list.add(new Tour("5","title15",null,"Lostad-android framework"));
		list.add(new Tour("6","title16",null,"Lostad-android framework"));
		list.add(new Tour("7","title17",null,"Lostad-android framework"));

		g4j = new TourList4j(true,"success");
		g4j.list = list;
		return g4j;
	}


	public TourList4j listTourMy(String userId, int start) {
		TourList4j c = null;

		Gson g = new Gson();
		try {

            Map map = new HashMap();//"":"0",//会员ID
			map.put("userId",userId);
			map.put("start",start);
			map.put("limit",20);

			String data = g.toJson(map);
			LogMe.d("request", data);
			String j = RequestUtil.postJson( IConst.URL_SERVICE , data);
			LogMe.d("response",j);
			c = g.fromJson(j, TourList4j.class);
			if(c==null ){
				c = new TourList4j(false,"服务器返回数据异常");
			}
		} catch (Exception e) {
			c = new TourList4j(false,"服务器返回数据异常！"+e.getMessage());
			e.printStackTrace();
		}
		return c;
	}

}

