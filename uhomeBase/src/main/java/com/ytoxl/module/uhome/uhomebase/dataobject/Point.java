package com.ytoxl.module.uhome.uhomebase.dataobject;

import java.util.List;

import com.ytoxl.module.uhome.uhomebase.dataobject.tbl.PointTbl;

public class Point extends PointTbl {
	
	public static final Short TYPE_MINUS = 0; //扣减积分
	public static final Short TYPE_ADD = 1;	//增加积分
	public static final Short POINTSOURCE_RETURNORDER = 0; //退货
	public static final Short POINTSOURCE_ADDORDER = 1; //新增订单
	public static final Short POINTSOURCE_REGISTER = 2; // 注册用户
	
	protected List<PointDetail> pointDetails;

	public List<PointDetail> getPointDetails() {
		return pointDetails;
	}

	public void setPointDetails(List<PointDetail> pointDetails) {
		this.pointDetails = pointDetails;
	}
}
