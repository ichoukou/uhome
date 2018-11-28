package com.ytoxl.module.uhome.uhomebase.service;

import java.util.List;
import java.util.Map;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.CouponConfig;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductSku;
import com.ytoxl.module.uhome.uhomebase.dataobject.UserCoupon;

/**
 * 前台优惠券操作接口
 * @author anqian
 *
 */
public interface UserCouponService {
	/**
	 * 分页查询用户优惠券
	 * @param userCouponPage
	 * @throws UhomeStoreException
	 */
	public void searchUserCouponsByStatus(BasePagination<UserCoupon> userCouponPage) throws UhomeStoreException;
	/**
	 * 激活用户优惠券
	 * @param userCoupon 用户优惠券关联信息
	 * @throws UhomeStoreException
	 */
	public void updateActivateCoupon(UserCoupon userCoupon) throws UhomeStoreException;
	/**
	 * 根据优惠券号查询用户优惠券状态
	 * @param userCoupon
	 * @return 0：未使用	1：已使用 2：已过期 4：活动已禁止 5：活动已删除
	 * @throws UhomeStoreException
	 */
	public Short getUserCouponStatus(String couponNo) throws UhomeStoreException;
	/**
	 * 更新用户优惠券状态
	 * @param userCouponId 优惠券id
	 * @param couponStatus 优惠券状态
	 * @throws UhomeStoreException
	 */
	public void updateUserCouponStatus(Integer userCouponId,Short couponStatus) throws UhomeStoreException;
	
	
	/**
	 * 查询商品可参与的优惠活动
	 * @param productSkus
	 * @return
	 * @throws UhomeStoreException
	 */
	public Map<ProductSku,List<CouponConfig>> getProductSkuCanJoinCoupon(List<ProductSku> productSkus) throws UhomeStoreException; 
	
	/**
	 * 查询优惠劵包含的商品
	 * @param productSkus
	 * @return
	 * @throws UhomeStoreException
	 */
	public Map<CouponConfig,List<ProductSku>> getCouponContentProductSku(List<ProductSku> productSkus) throws UhomeStoreException; 
	/**
	 * 查询可用的优惠劵
	 * @param productInfo
	 * @param userId
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<UserCoupon> getCanUseUserCoupon(String productInfo,Integer userId) throws UhomeStoreException;
}
