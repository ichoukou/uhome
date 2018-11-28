package com.ytoxl.module.uhome.uhomebase.mapper;


import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomebase.dataobject.Coupon;

public interface CouponMapper<T extends Coupon> extends BaseSqlMapper<T> {

	/**
	 * 根据优惠券号查询优惠券信息
	 * @param couponNo 优惠券号
	 * @return
	 * @throws DataAccessException
	 */
	public Coupon getCouponByCouponNo(String couponNo) throws DataAccessException;;
	/**
	 * 根据微信用户标识和微信包标识查询对应优惠券信息
	 * @param wxuFlag 微信用户标识
	 * @param packageId 微信包标识
	 * @return
	 * @throws DataAccessException
	 */
	public List<Coupon> searchCoupon(Map<String,Object> map) throws DataAccessException;
	
	/**
	 * 根据优惠券号集合查询优惠券
	 * @param couponNos
	 * @return
	 */
	public Integer listCountByCouponNos(List<String> couponNos) throws DataAccessException;
	
	/**
	 * 批量插入优惠券信息
	 * @param coupons
	 */
	public int insertBatch(List<Coupon> coupons) throws DataAccessException;
	
	/**
	 * 查询优惠券
	 * @param map
	 * @return
	 */
	public List<Coupon> searchCoupons(Map<String, Object> map) throws DataAccessException;
	public Integer searchCouponsCount(Map<String, Object> map) throws DataAccessException;

	/**
	 * 根据eventId查询优惠券信息
	 * @param eventId
	 * @return
	 */
	public List<Coupon> listCoupons(Integer eventId) throws DataAccessException;
	
	/**
	 * 删除已过期优惠券明细表记录
	 * @param num
	 * @return
	 */
	public void deleteOverdueCoupons(Integer num) throws DataAccessException;
	
	/**
	 * 删除未激活优惠券明细表记录
	 * @param num
	 * @return
	 */
	public void deleteNotActiveCoupons(Integer eventId) throws DataAccessException;
}
