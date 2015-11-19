package com.lostad.app.demo.entity;

import com.lostad.applib.entity.BaseBeanRsult;

import java.util.List;

/**
 * Tour
 */
public class BaseTourResult extends BaseBeanRsult {
	public List<Tour> list;

	private BaseTourResult() {}
	public BaseTourResult(boolean isSuccess, String msg) {
		super(isSuccess,msg);
	}


}
