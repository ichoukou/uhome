package com.ytoxl.module.uhome.uhomebase.dataobject;

import java.util.List;

import com.ytoxl.module.uhome.uhomebase.dataobject.tbl.EventTbl;

public class Event extends EventTbl {
	
	public static final Short TYPE_COMMON = 0; //普通活动
	public static final Short TYPE_WECHAT = 1; //微信活动
	public static final Short TYPE_PROMOTION = 2; 
	
	public static final Short STATUS_NORMAL = 0; //正常 
	public static final Short STATUS_DISBALED = 1; //已禁用
	public static final Short STATUS_DELETE = 2; //已删除
	public static final Short STATUS_ABNORMAL_DELETE = 3; //非常规删除

	public static final Short ACTIVITYSTATUS_NOT_START = 0; // 未开始
	public static final Short ACTIVITYSTATUS_IN_PROGRESS = 1; //优惠中
	public static final Short ACTIVITYSTATUS_EXPIRED = 2; //已过期
	public static final Short ACTIVITYSTATUS_DELETE = 3; //非常规删除
	
	protected List<CouponConfig> couponConfigList; //关联优惠券配置
	protected Short activityStatus; // 活动状态
	protected List<EventRange> eventRanges; //适用范围
	protected String eventRangeNames;
	
	public List<CouponConfig> getCouponConfigList() {
		return couponConfigList;
	}

	public void setCouponConfigList(List<CouponConfig> couponConfigList) {
		this.couponConfigList = couponConfigList;
	}

	public Short getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(Short activityStatus) {
		this.activityStatus = activityStatus;
	}

	public List<EventRange> getEventRanges() {
		return eventRanges;
	}

	public void setEventRanges(List<EventRange> eventRanges) {
		this.eventRanges = eventRanges;
	}

	public String getEventRangeNames() {
		return eventRangeNames;
	}

	public void setEventRangeNames(String eventRangeNames) {
		this.eventRangeNames = eventRangeNames;
	}

}
