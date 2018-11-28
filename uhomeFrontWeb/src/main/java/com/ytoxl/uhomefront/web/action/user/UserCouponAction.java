package com.ytoxl.uhomefront.web.action.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.core.common.utils.StringUtils;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.common.utils.DateJsonValueProcessor;
import com.ytoxl.module.uhome.uhomebase.dataobject.UserCoupon;
import com.ytoxl.module.uhome.uhomebase.service.UserCouponService;
import com.ytoxl.module.user.common.exception.YtoxlUserException;
import com.ytoxl.module.user.security.CustomUserDetails;
import com.ytoxl.module.user.service.UserService;
import com.ytoxl.uhomefront.web.action.BaseAction;

public class UserCouponAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(UserCouponAction.class);
	@Autowired
	private UserService userService;
	@Autowired
	private UserCouponService userCouponService;
	/**
	 * 用户优惠券分页
	 */
	private BasePagination<UserCoupon> userCouponPage;
	//用户ID
	public final static String USERID = "userId";
	//优惠券状态
	public final static String COUPONSTATUS = "couponStatus";
	//是否分页
	public final static String IS_PAGE = "isPage";
	//接收页面优惠券状态
	private String couponStatus;
	//接收页面优惠券号
	private String couponNo;
	
	private UserCoupon userCoupon;
	
	private static final Object synObj = new Object(); 

	/**
	 * 查看我的优惠券
	 * 
	 * @return
	 */
	public String listCoupons() {
		try {
			if(userCouponPage==null){
				this.userCouponPage = new BasePagination<UserCoupon>();
				userCouponPage.setLimit(20);
				/**
				 * 分页过滤条件
				 */
				Map<String,String> params = new HashMap<String,String>();
				// 获取当前用户id
				Integer userId = userService.getCurrentUser().getUserId();
				params.put(USERID, userId.toString());
				if(StringUtils.isBlank(couponStatus))
					couponStatus = "-1";
				params.put(COUPONSTATUS, couponStatus);
				userCouponPage.setParams(params);
			}
			userCouponService.searchUserCouponsByStatus(userCouponPage);
		} catch (YtoxlUserException e) {
			logger.error("===listCoupons()===获取当前用户信息异常：{}",e.getMessage());
		} catch (UhomeStoreException e) {
			logger.error("===listCoupons()===查询优惠券列表异常：{}",e.getMessage());
		}

		return SUCCESS;
	}
	
	/**
	 * 查看未使用优惠券
	 * 
	 * @return
	 */
	public String listValidCoupons() {
		/**
		 * 分页过滤条件
		 */
		Map<String,String> params = new HashMap<String,String>();
		// 获取当前用户id
		Integer userId;
		try {
			userId = userService.getCurrentUser().getUserId();
			params.put(UserCouponAction.USERID, userId.toString());
		} catch (YtoxlUserException e) {
			logger.error("===listValidCoupons()===获取当前用户信息异常：{}",e.getMessage());
		}
		params.put(UserCouponAction.COUPONSTATUS, UserCoupon.COUPON_STATUS_0.toString());
		params.put(UserCouponAction.IS_PAGE, UserCoupon.COUPON_STATUS_0.toString());
		BasePagination<UserCoupon> bp = new BasePagination<UserCoupon>();
		bp.setParams(params);
		try {
			userCouponService.searchUserCouponsByStatus(bp);
		} catch (UhomeStoreException e) {
			logger.error("===listValidCoupons()===查看未使用优惠券异常：{}",e.getMessage());
		}
		Collection<UserCoupon> userCouponList =  bp.getResult();
		JsonConfig jsonConfig = new JsonConfig();  
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));  
		setMessage(JSONArray.fromObject(userCouponList,jsonConfig).toString());
		return JSONMSG;
	}
	
	public String selectCanUseUserConpon(){
		List<UserCoupon>  userCouponList = new ArrayList<UserCoupon>();
		try {
			String productInfo = userCouponPage.getParams().get("_allProduct");
			if(StringUtils.isNotEmptyTrim(productInfo)){
				Integer userId = userService.getCurrentUser().getUserId();
				userCouponList = userCouponService.getCanUseUserCoupon(productInfo,userId);
			}
		} catch (YtoxlUserException e) {
			logger.error("===listValidCoupons()===获取当前用户信息异常：{}",e.getMessage());
		}catch (UhomeStoreException e) {
			logger.error("===listValidCoupons()===查看未使用优惠券异常：{}",e.getMessage());
		} 
		JsonConfig jsonConfig = new JsonConfig();  
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd")); 
		try {
			setMessage(JSONArray.fromObject(userCouponList,jsonConfig).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return JSONMSG;
	}
	
	public String activateCoupon(){
		// 获取当前用户id
		CustomUserDetails user;
		try {
			user = userService.getCurrentUser();
			Integer userId = user.getUserId();
			String username = user.getUsername();
			userCoupon = new UserCoupon();
			userCoupon.setUserId(userId);
			userCoupon.setUsername(username);
			userCoupon.setCouponNo(couponNo);
			synchronized (synObj) {
				userCouponService.updateActivateCoupon(userCoupon);
			}
			setMessage("3");
		} catch (YtoxlUserException e) {
			logger.error("===listValidCoupons()===获取当前用户信息异常：{}",e.getMessage());
			setMessage(e.getMessage());
		}catch (UhomeStoreException e){
			logger.error("===listValidCoupons()===激活优惠券异常：{}",e.getMessage());
			setMessage(e.getMessage());
		}
		return JSONMSG;
	}

	public BasePagination<UserCoupon> getUserCouponPage() {
		return userCouponPage;
	}

	public void setUserCouponPage(BasePagination<UserCoupon> userCouponPage) {
		this.userCouponPage = userCouponPage;
	}

	public String getCouponStatus() {
		return couponStatus;
	}

	public void setCouponStatus(String couponStatus) {
		this.couponStatus = couponStatus;
	}

	public String getCouponNo() {
		return couponNo;
	}

	public void setCouponNo(String couponNo) {
		this.couponNo = couponNo;
	}

}
