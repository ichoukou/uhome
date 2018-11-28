package com.ytoxl.module.uhome.uhomebase.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Coupon;
import com.ytoxl.module.uhome.uhomebase.dataobject.Event;
import com.ytoxl.module.uhome.uhomebase.dataobject.EventRange;

public interface CouponService {

	/**
	 * 保存活动及相应优惠券信息
	 * @param event
	 */
	public void saveEvent(Event event) throws UhomeStoreException;
	
	/**
	 * 根的据eventId获取活动信息
	 * @param eventId
	 * @return
	 */
	public Event getEventByEventId(Integer eventId) throws UhomeStoreException;
	
	/**
	 * 根据活动类型获取活动列表
	 * @param type
	 */
	public List<Event> listEvents(Short type) throws UhomeStoreException;
	
	/**
	 * 删除活动
	 * @param eventId
	 */
	public void deleteEvent(Event event) throws UhomeStoreException;
	
	/**
	 * 非常规删除活动
	 * @param eventId
	 */
	public void abnormalDeleteEvent(Event event) throws UhomeStoreException;
	
	/**
	 * 导出优惠券
	 * @param eventId
	 * @return
	 */
	public List<Coupon> listCoupons(Integer eventId) throws UhomeStoreException;
	
	/**
	 * 生成优惠券,供微信接口调用
	 * @param eventId
	 * @param wxuFlag
	 * @return
	 */
	public List<String> createCoupon4Weixin(Integer eventId, String wxuFlag) throws UhomeStoreException;
	
	/**
	 * 生成优惠券
	 * @param eventId
	 */
	public List<String> createCoupon(Integer eventId, String wxuFlag) throws UhomeStoreException;
	
	/**
	 * 查询优券惠
	 * @param couponPage
	 */
	public void searchCoupons(BasePagination<Coupon> couponPage) throws UhomeStoreException;
	
	/**
	 * 查询所有的优惠券金额
	 * @return
	 */
	public List<BigDecimal> listAllowances() throws UhomeStoreException;
	
	/**
	 * 通过活动id查询优惠券  优惠的总额
	 * @param eventId
	 * @return
	 * @throws UhomeStoreException
	 */
	public BigDecimal getCouponsAmount(Integer eventId) throws UhomeStoreException;
	
	/**
	 * 获取适用范围
	 * @return
	 */
	public List<EventRange> getEventRanges(Date startTime, Date endTime) throws UhomeStoreException;
	
	/**
	 * 获取适用范围名称列表
	 * @param eventId
	 * @return
	 */
	public String getEventRangeNames(Integer eventId) throws UhomeStoreException;
	
}
