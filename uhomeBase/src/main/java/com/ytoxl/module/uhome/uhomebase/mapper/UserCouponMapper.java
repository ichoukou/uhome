package com.ytoxl.module.uhome.uhomebase.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomebase.dataobject.UserCoupon;

public interface UserCouponMapper<T extends UserCoupon> extends BaseSqlMapper<T> {
	
	/**
	 * 分页查询用户优惠券信息
	 * @param couponStatus 优惠券状态
	 * @return
	 * @throws DataAccessException
	 */
	public List<UserCoupon> searchUserCouponsByStatus(Map<String, Object> map) throws DataAccessException;
	public Integer searchUserCouponsByStatusCount(Map<String, Object> map) throws DataAccessException;
	/**
	 * 根据优惠券号查询用户优惠券信息
	 * @param couponNo 优惠券号
	 * @return
	 * @throws DataAccessException
	 */
	public UserCoupon getUserCouponByCouponNo(String couponNo) throws DataAccessException;
	
	/**
	 * 
	 * @param couponNo
	 * @return
	 * @throws DataAccessException
	 */
	public UserCoupon getUserCouponById(Integer userCouponId) throws DataAccessException;
	/**
	 * 根据用户id查询用户可用优惠券
	 * @param userId
	 * @return 用户可用优惠券
	 * @throws DataAccessException
	 */
	public List<UserCoupon> searchValidCoupons(Integer userId) throws DataAccessException;
	/**
	 * 修改用户优惠券状态
	 * userCoupon
	 * @throws DataAccessException
	 */
	public void updateCouponStatus(UserCoupon userCoupon)throws DataAccessException;
	
	/**
	 * 修改已激活但未使用的优惠券状态为已删除
	 * @param eventId
	 * @throws DataAccessException
	 */
	public void updateCouponStatus2Deleted(Integer eventId) throws DataAccessException;
	
	public Integer checkUserActiveCode(UserCoupon userCoupon) throws DataAccessException;
	/**
	 * 根据用户id,couponConfigIds获得用户优惠劵
	 * @param userid
	 * @param couponConfigs
	 * @return
	 * @throws DataAccessException
	 */
	public List<UserCoupon> getUserCouponByCouponConfigAndUserId(@Param("userId")Integer userId,@Param("list")List<Integer> couponConfigs) throws DataAccessException;
	
	/**
	 * 根据用户id获得能全场使用的优惠劵
	 * @param userId
	 * @return
	 * @throws DataAccessException
	 */
	public List<UserCoupon> getUserCouponByUserIdWithAll(Integer userId) throws DataAccessException;
}
