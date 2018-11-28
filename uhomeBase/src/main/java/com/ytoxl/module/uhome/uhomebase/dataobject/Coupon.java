package com.ytoxl.module.uhome.uhomebase.dataobject;

import com.ytoxl.module.uhome.uhomebase.common.utils.excel.ExcelField;
import com.ytoxl.module.uhome.uhomebase.common.utils.excel.Type;
import com.ytoxl.module.uhome.uhomebase.dataobject.tbl.CouponTbl;

public class Coupon extends CouponTbl {

	/**
	 * 激活状态：已激活
	 */
	public final static Short ACTIVE_STATUS_ON = 1;
	/**
	 * 激活状态：未激活
	 */
	public final static Short ACTIVE_STATUS_OFF = 0;
	
	public static final Short[] ACTIVESTATUSES = new Short[]{ACTIVE_STATUS_OFF, ACTIVE_STATUS_ON};
	
	public final static Short STATUS_USED_NO = 0; //未使用
	public final static Short STATUS_USED_YES = 1; //已使用
	public final static Short STATUS_EXPIRED = 2; //已过期
	public final static Short STATUS_DELETE = 3; //已删除
	public static final Short[] STATUSES = new Short[] { STATUS_USED_NO, STATUS_USED_YES, STATUS_EXPIRED, STATUS_DELETE };

	@ExcelField(type=Type.OBJECT)
	protected Event event; // 关联活动
	@ExcelField(type=Type.OBJECT)
	protected CouponConfig couponConfig; // 关联优惠券配置
	protected UserCoupon userCoupon; // 关联用户优惠券
	protected Short status; // 优惠券状态
	@ExcelField(filedName="活动时间",sort=4)
	protected String eventTime; //活动时间

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public CouponConfig getCouponConfig() {
		return couponConfig;
	}

	public void setCouponConfig(CouponConfig couponConfig) {
		this.couponConfig = couponConfig;
	}

	public UserCoupon getUserCoupon() {
		return userCoupon;
	}

	public void setUserCoupon(UserCoupon userCoupon) {
		this.userCoupon = userCoupon;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getEventTime() {
		return eventTime;
	}

	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}
}
