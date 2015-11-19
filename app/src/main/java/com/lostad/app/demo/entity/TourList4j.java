package com.lostad.app.demo.entity;

import com.lostad.applib.entity.BaseBeanRsult;

import java.util.List;
/**
 * Tour
 */
public class TourList4j extends BaseBeanRsult {
	public List<Tour> list;

	private TourList4j() {}
	public TourList4j(boolean isSuccess, String msg) {
		super(isSuccess,msg);
	}


}
