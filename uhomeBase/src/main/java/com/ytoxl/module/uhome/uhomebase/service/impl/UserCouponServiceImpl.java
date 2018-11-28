package com.ytoxl.module.uhome.uhomebase.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.core.common.utils.DateUtil;
import com.ytoxl.module.core.common.utils.StringUtils;
import com.ytoxl.module.uhome.uhomebase.common.CodeConstants;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Coupon;
import com.ytoxl.module.uhome.uhomebase.dataobject.CouponConfig;
import com.ytoxl.module.uhome.uhomebase.dataobject.Event;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductSku;
import com.ytoxl.module.uhome.uhomebase.dataobject.UserCoupon;
import com.ytoxl.module.uhome.uhomebase.mapper.CouponConfigMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.CouponMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.EventMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.ProductSkuMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.UserCouponMapper;
import com.ytoxl.module.uhome.uhomebase.service.CouponService;
import com.ytoxl.module.uhome.uhomebase.service.UserCouponService;
@Service
public class UserCouponServiceImpl implements UserCouponService {
	private static Logger logger = LoggerFactory.getLogger(UserCouponServiceImpl.class);
	@Autowired
	private UserCouponMapper<UserCoupon> userCouponMapper;
	@Autowired
	private CouponMapper<Coupon> couponMapper;
	@Autowired
	private CouponConfigMapper<CouponConfig> couponConfigMapper;
	@Autowired
	private EventMapper<Event> eventMapper;
	@Autowired
	private ProductSkuMapper<ProductSku> productSkuMapper;
	
	@Autowired
	private CouponService couponService;

	@Override
	public void searchUserCouponsByStatus(
			BasePagination<UserCoupon> userCouponPage)
			throws UhomeStoreException {
		Map<String, Object> searchParams = userCouponPage.getSearchParamsMap();
		if (userCouponPage.isNeedSetTotal()) {
			Integer total = userCouponMapper.searchUserCouponsByStatusCount(searchParams);
			userCouponPage.setTotal(total);
		}
		List<UserCoupon> listUserConpons = userCouponMapper.searchUserCouponsByStatus(searchParams);
		//获取使用范围
		for(UserCoupon uc : listUserConpons){
			Integer eventId = uc.getEvent().getEventId();
			uc.setCouponUseScope(couponService.getEventRangeNames(eventId));
		}
		userCouponPage.setResult(listUserConpons);
	}

	@Override
	public synchronized void updateActivateCoupon(UserCoupon userCoupon)
			throws UhomeStoreException {
		//优惠券号或者激活码
		String couponNo = userCoupon.getCouponNo();
		Event event = eventMapper.getEventByActiveCode(couponNo);
		if(event!=null){//是激活码
			List<CouponConfig> ccList = couponConfigMapper.listCouponConfigByEventId(event.getEventId());
			for(CouponConfig cc:ccList){
				if(cc.getEndTime().getTime() < new Date().getTime()){
					throw new UhomeStoreException("100-"+DateUtil.format(cc.getStartTime(), "yyyy年MM月dd日")+"-"+DateUtil.format(cc.getEndTime(), "yyyy年MM月dd日"));//该活动已过期！
				}
			}
			userCoupon.setActiveCode(couponNo);
			Integer uaCount = userCouponMapper.checkUserActiveCode(userCoupon);
			if(uaCount>0){
				logger.error("===updateActivateCoupon()===该激活码已被激活！激活码：{}",couponNo);
				throw new UhomeStoreException("6");//该激活码已被激活！
			}
			if(event.getStatus()!=0){
				logger.error("===updateActivateCoupon()===该活动已被禁止或删除！激活码：{}",couponNo);
				throw new UhomeStoreException("5");//该活动已被禁止或删除！
			}
			List<String> coupons = null;
			try{
				coupons = couponService.createCoupon(event.getEventId(), null);
			}catch (Exception e) {
				if(CodeConstants.QUOTA_FULLF_ERROR.equals(e.getMessage())){
					logger.error("===updateActivateCoupon()===激活名额已满，激活码：{}",couponNo);
					throw new UhomeStoreException("99");//激活名额已满！
				}else{
					logger.error("===updateActivateCoupon()===根据激活码获取优惠券异常，激活码：{}",couponNo);
					throw new UhomeStoreException("根据激活码获取优惠券异常");
				}
			}
			if(coupons==null||coupons.size()<1){
				logger.error("===updateActivateCoupon()===根据激活码获取优惠券异常，激活码：{}",couponNo);
				throw new UhomeStoreException("7");//该活动已被禁止或删除！
			}
			couponNo = coupons.get(0);
		}
		
		Coupon coupon = couponMapper.getCouponByCouponNo(couponNo);
		if(coupon==null){
			logger.error("===updateActivateCoupon()===该优惠券不存在！优惠券号：{}",couponNo);
			throw new UhomeStoreException("0");//该优惠券不存在！
		}
		CouponConfig cc = couponConfigMapper.get(coupon.getCouponConfigId());
		//活动状态  0：正常 1：禁止 2 ：删除
		Short status = eventMapper.get(cc.getEventId()).getStatus();
		if(status!=0){
			logger.error("===updateActivateCoupon()===该活动已被禁止或删除！优惠券号：{}",couponNo);
			throw new UhomeStoreException("5");//该活动已被禁止或删除！
		}
		if(coupon.getIsActive()==Coupon.ACTIVE_STATUS_ON){
			logger.error("===updateActivateCoupon()===该优惠券已激活，不能再次激活！优惠券号：{}",couponNo);
			throw new UhomeStoreException("1");//该优惠券已激活，不能再次激活！
		}
		
		long endTime = cc.getEndTime().getTime();
		
		if(endTime<new Date().getTime()){
			logger.error("===updateActivateCoupon()===该优惠券已过期！优惠券号：{}",couponNo);
			throw new UhomeStoreException("2");//该优惠券已过期！
		}
		//用户id
		Integer userId = userCoupon.getUserId();
		//用户名
		String username = userCoupon.getUsername();
		//微信用户标识
		String wxuFlag = coupon.getWxuFlag();
		if(StringUtils.isNotEmpty(wxuFlag)){//微信激活用户
			Integer packageId = coupon.getPackageId();
			//查找当前批次优惠券
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("wxuFlag", wxuFlag);
			map.put("packageId", packageId);
			List<Coupon> list = couponMapper.searchCoupon(map);
			if(list==null||list.size()<1){
				logger.error("===updateActivateCoupon()===获取微信优惠券异常！优惠券号：{}",couponNo);
				throw new UhomeStoreException("8");//该优惠券已过期！
			}
			boolean isFirst = true;
			for(Coupon c:list){
				UserCoupon uc = new UserCoupon();
				uc.setCouponNo(c.getCouponNo());
				uc.setCouponStatus(UserCoupon.COUPON_STATUS_0);
				uc.setCouponConfigId(c.getCouponConfigId());
				uc.setUserId(userId);
				uc.setUsername(username);
				uc.setCouponId(c.getCouponId());
				if(userCoupon.getActiveCode()!=null&&isFirst){
					uc.setActiveCode(userCoupon.getActiveCode());
					isFirst = false;
				}
				//保存用户优惠券关联信息
				userCouponMapper.add(uc);
				
				c.setIsActive(Coupon.ACTIVE_STATUS_ON);
				c.setActiveTime(new Date());
				//修改激活状态
				couponMapper.update(c);
			}
		}else{//普通用户
			userCoupon.setCouponStatus(UserCoupon.COUPON_STATUS_0);
			userCoupon.setCouponConfigId(cc.getCouponConfigId());
			userCoupon.setCouponId(coupon.getCouponId());
			//保存用户优惠券关联信息
			userCouponMapper.add(userCoupon);
			
			coupon.setIsActive(Coupon.ACTIVE_STATUS_ON);
			coupon.setActiveTime(new Date());
			//修改优惠券明细激活状态
			couponMapper.update(coupon);
		}
	}

	@Override
	public Short getUserCouponStatus(String couponNo) throws UhomeStoreException {
		Short result = null;
		UserCoupon userCoupon = userCouponMapper.getUserCouponByCouponNo(couponNo);
		if(userCoupon==null){
			logger.error("===getUserCouponStatus()===该优惠券不存在！优惠券号：{}",couponNo);
			throw new UhomeStoreException("0");
		}
		CouponConfig cc = couponConfigMapper.get(userCoupon.getCouponConfigId());
		//活动状态  0：正常 1：禁止 2 ：删除
		Short status = eventMapper.get(cc.getEventId()).getStatus();
		if(status==Event.STATUS_NORMAL){
			//结束时间
			long endTime = cc.getEndTime().getTime();
			//开始时间
			long startTime = cc.getStartTime().getTime();
			//当前时间
			long nowTime = new Date().getTime();
			//开始时间大于当前时间
			if(startTime>nowTime){
				result = UserCoupon.COUPON_STATUS_6;//未到期
			}else if(startTime<=nowTime&&nowTime<=endTime){
				result = userCoupon.getCouponStatus();
			}else
			//如果结束时间小于当前时间
			if(endTime<nowTime){
				result = UserCoupon.COUPON_STATUS_2;//已过期
			}
		}else{
			result = status==Event.STATUS_DISBALED?UserCoupon.COUPON_STATUS_4:UserCoupon.COUPON_STATUS_5;//4：活动已禁止 5：活动已删除
		}
		return result;
	}

	@Override
	public void updateUserCouponStatus(Integer userCouponId,Short couponStatus)
			throws UhomeStoreException {
		UserCoupon userCoupon = new UserCoupon();
		userCoupon.setUserCouponId(userCouponId);
		userCoupon.setCouponStatus(couponStatus);
		userCouponMapper.updateCouponStatus(userCoupon);
	}

	
	@Override
	public Map<ProductSku, List<CouponConfig>> getProductSkuCanJoinCoupon(List<ProductSku> productSkus) throws UhomeStoreException {
		Map<ProductSku,List<CouponConfig>> productSkuCouponConfigMap = new HashMap<ProductSku,List<CouponConfig>>();
		List<Event> canUseAllPlansEventList = eventMapper.getCurrentEventByAllPlans();//所有适用于全场的活动
		List<CouponConfig> canUseAllPlansCouponConfigList = new ArrayList<CouponConfig>();
		for(Event event:canUseAllPlansEventList){
			canUseAllPlansCouponConfigList.addAll(event.getCouponConfigList());
		}
		if(productSkus!=null){
			for(ProductSku productSku:productSkus){
				if(productSku!=null){
					List<Event> eventList = eventMapper.getEventByProductSkuId(productSku.getProductSkuId());
					List<CouponConfig> couponConfigList = new ArrayList<CouponConfig>();
					for(Event event:eventList){
						if(event!=null){
							//List<CouponConfig> ccList = couponConfigMapper.listCouponConfigByEventId(event.getEventId());
							List<CouponConfig> ccList = event.getCouponConfigList();
							couponConfigList.addAll(ccList);
							
						}
					}
					if(canUseAllPlansCouponConfigList.size()>0){
						couponConfigList.addAll(canUseAllPlansCouponConfigList);
					}
					productSkuCouponConfigMap.put(productSku, couponConfigList);
				}
			}
		}
		return productSkuCouponConfigMap;
	}

		
	@Override
	public Map<CouponConfig, List<ProductSku>> getCouponContentProductSku(List<ProductSku> productSkus) throws UhomeStoreException {
		Map<ProductSku,List<CouponConfig>> productSkuCouponConfigMap = getProductSkuCanJoinCoupon(productSkus);
		Map<CouponConfig,List<ProductSku>> couponConfigProductSkuMap= new HashMap<CouponConfig,List<ProductSku>>();
		Map<Integer,List<ProductSku>> couponConfigIdProductSkuMap= new HashMap<Integer,List<ProductSku>>();
		Map<Integer,CouponConfig> couponConfigMap = new HashMap<Integer,CouponConfig>();
		for(Iterator<ProductSku> it=productSkuCouponConfigMap.keySet().iterator();it.hasNext();){
			ProductSku productSku = it.next();
			List<CouponConfig> couponConfigList = productSkuCouponConfigMap.get(productSku);
			if(couponConfigList.size()>0){
				for(CouponConfig couponConfig:couponConfigList){
					if(couponConfigIdProductSkuMap.containsKey(couponConfig.getCouponConfigId())){
						List<ProductSku> productSkuList = couponConfigIdProductSkuMap.get(couponConfig.getCouponConfigId());
						boolean productFlag = false;
						for(ProductSku tempProductSku:productSkuList){
							if(productSku.getProductSkuId().equals(tempProductSku.getProductSkuId())){
								productFlag = true;
								break;
							}
						}
						if(!productFlag){
							productSkuList.add(productSku);
							couponConfigIdProductSkuMap.put(couponConfig.getCouponConfigId(), productSkuList);
						}
					}else{
						List<ProductSku> productSkuList = new ArrayList<ProductSku>();
						productSkuList.add(productSku);
						couponConfigIdProductSkuMap.put(couponConfig.getCouponConfigId(), productSkuList);
					}
					couponConfigMap.put(couponConfig.getCouponConfigId(), couponConfig);
				}
			}
		}
		for(Iterator<Integer> it=couponConfigIdProductSkuMap.keySet().iterator();it.hasNext();){
			Integer couponConfigId = it.next();
			couponConfigProductSkuMap.put(couponConfigMap.get(couponConfigId), couponConfigIdProductSkuMap.get(couponConfigId));
		}
		return couponConfigProductSkuMap;
	}

	@Override
	public List<UserCoupon> getCanUseUserCoupon(String productInfo,Integer userId) throws UhomeStoreException {
		List<UserCoupon> userCouponList = new ArrayList<UserCoupon>();
		Map<ProductSku,Integer> productSkuMountMap = new HashMap<ProductSku,Integer>();
		List<Integer> canUserCouponConfigList = new ArrayList<Integer>();
		Map<Integer,BigDecimal> couponMountMap = new HashMap<Integer,BigDecimal>();//优惠劵实际对应优惠价格
		Map<Integer,List<ProductSku>> sellerProductSkusMap = new HashMap<Integer,List<ProductSku>>();
		BigDecimal allProductSkuMount = new BigDecimal(0);
		String arr1[] =  productInfo.split("\\$");
		for(String str:arr1){
			if(StringUtils.isNotEmptyTrim(str)){
				String arr2[] = str.split("-");
				if(arr2.length==2){
					ProductSku productSku = productSkuMapper.getProductSkuById(Integer.parseInt(arr2[0]));
					if(productSku!=null){
						Integer sellerId = productSku.getProduct().getSellerId();
						List<ProductSku> productSkuList = sellerProductSkusMap.get(sellerId);
						if(productSkuList!=null){
							productSkuList.add(productSku);
						}else{
							productSkuList = new ArrayList<ProductSku>();
							productSkuList.add(productSku);
							sellerProductSkusMap.put(sellerId, productSkuList);
						}
					}
					productSkuMountMap.put(productSku, Integer.parseInt(arr2[1]));
				}
			}
		}
		Map<CouponConfig,List<ProductSku>> couponConfigProductSkuMap = getCouponContentProductSku(new ArrayList(productSkuMountMap.keySet()));
		//计算订单总金额
		for(Iterator<ProductSku> it=productSkuMountMap.keySet().iterator();it.hasNext();){
			ProductSku productSku = it.next();
			if(productSku!=null){
				BigDecimal countProductSku = productSku.getProduct().getSalePrice().multiply(new BigDecimal(productSkuMountMap.get(productSku)));  
				allProductSkuMount = allProductSkuMount.add(countProductSku);
			}
		}

		//计算商品总金额是否满足优惠劵最小消费金额
		for(Iterator<CouponConfig> it=couponConfigProductSkuMap.keySet().iterator();it.hasNext();){
			CouponConfig couponConfig = it.next();
			List<ProductSku> productSkuList = couponConfigProductSkuMap.get(couponConfig);
			BigDecimal limitCharge =  couponConfig.getLimitCharge();//优惠劵最小消费金额
			BigDecimal tempMount = new BigDecimal(0);
			for(ProductSku productSku:productSkuList){
				BigDecimal countProductSku = productSku.getProduct().getSalePrice().multiply(new BigDecimal(productSkuMountMap.get(productSku)));  
				tempMount = tempMount.add(countProductSku);
			}
			if(limitCharge.compareTo(tempMount)!=1){
				BigDecimal realCouponMoney = null;
				if(couponConfig.getAllowance().compareTo(tempMount)==-1){
					realCouponMoney = couponConfig.getAllowance();
				}else{
					realCouponMoney = couponConfig.getAllowance().subtract(tempMount.subtract(couponConfig.getAllowance()).abs());
				}
				couponMountMap.put(couponConfig.getCouponConfigId(), realCouponMoney);
				canUserCouponConfigList.add(couponConfig.getCouponConfigId());
			}
		}
		if(canUserCouponConfigList.size()>0){
			userCouponList.addAll(userCouponMapper.getUserCouponByCouponConfigAndUserId(userId, canUserCouponConfigList));
		}
		for(UserCoupon userCoupon:userCouponList){
			userCoupon.setCouponUseScope(couponService.getEventRangeNames(userCoupon.getEvent().getEventId()));
			BigDecimal preferentialMoney = couponMountMap.get(userCoupon.getCouponConfigId());
			if(preferentialMoney!=null){
				userCoupon.setPreferentialMoney(preferentialMoney);
			}else{
				userCoupon.setPreferentialMoney(userCoupon.getCouponConfig().getAllowance());
			}
		}
		return userCouponList;
	}
	
	
}