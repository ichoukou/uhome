package com.ytoxl.module.uhome.uhomebase.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.CodeConstants;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.common.utils.DateUtil;
import com.ytoxl.module.uhome.uhomebase.dataobject.Coupon;
import com.ytoxl.module.uhome.uhomebase.dataobject.CouponConfig;
import com.ytoxl.module.uhome.uhomebase.dataobject.Event;
import com.ytoxl.module.uhome.uhomebase.dataobject.EventRange;
import com.ytoxl.module.uhome.uhomebase.dataobject.Plan;
import com.ytoxl.module.uhome.uhomebase.dataobject.UserCoupon;
import com.ytoxl.module.uhome.uhomebase.mapper.CouponConfigMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.CouponMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.EventMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.EventRangeMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.PlanMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.UserCouponMapper;
import com.ytoxl.module.uhome.uhomebase.service.CouponService;
import com.ytoxl.module.uhome.uhomebase.service.DocNumSystemService;

@Service
public class CouponServiceImpl implements CouponService {
	private static Logger logger = LoggerFactory.getLogger(CouponServiceImpl.class);
	
	@Autowired
	private EventMapper<Event> eventMapper;
	@Autowired
	private CouponConfigMapper<CouponConfig> couponConfigMapper;
	@Autowired
	private CouponMapper<Coupon> couponMapper;
	@Autowired
	private UserCouponMapper<UserCoupon> userCouponMapper;
	@Autowired
	private EventRangeMapper<EventRange> eventRangeMapper;
	@Autowired
	private PlanMapper<Plan> planMapper;
	@Autowired
	private DocNumSystemService docNumSystemService;
	@Value("${weixin_prefix}")
	private String wechatCouponNoPrefix;

	/**
	 * 保存活动及相应优惠券信息
	 * @param event
	 */
	@Override
	public void saveEvent(Event event) throws UhomeStoreException {
		List<CouponConfig> couponConfigList = event.getCouponConfigList();
		//活动时间
		CouponConfig firstConfig = couponConfigList.get(0);
		Date startTime = firstConfig.getStartTime();
		Date endTime = firstConfig.getEndTime();
		Date tempDate = DateUtil.add(endTime, Calendar.HOUR_OF_DAY, 24);
		Date newEndDate = DateUtil.add(tempDate, Calendar.SECOND, -1);
		
		//新增操作
		if(event.getEventId() == null){
			Short type = event.getType();
			if(Event.TYPE_PROMOTION.equals(type)){
				String activeCode = docNumSystemService.getCouponActiveCode();
				event.setActiveCode(activeCode);
				event.setActiveNum(0);
			}
			eventMapper.add(event);
			for(CouponConfig config : couponConfigList){
				config.setEventId(event.getEventId());
				config.setStartTime(startTime);
				config.setEndTime(newEndDate);
				couponConfigMapper.add(config);
			}
			//保存适用范围
			List<EventRange> eventRanges = event.getEventRanges();
			if(eventRanges != null && eventRanges.size() > 0){
				eventRangeMapper.addBatch(event.getEventId(), eventRanges);
			}
			
			if(Event.TYPE_COMMON.equals(type)){
				//生成普通优惠券号
				createCoupon(event.getEventId(), null); 
			}
			return;
		}
		//更新操作
		eventMapper.update(event);
		for(CouponConfig config : couponConfigList){
			config.setStartTime(startTime);
			config.setEndTime(newEndDate);
			couponConfigMapper.update(config);
		}
	}

	/**
	 * 根的据eventId获取活动信息
	 * @param eventId
	 * @return
	 */
	@Override
	public Event getEventByEventId(Integer eventId) throws UhomeStoreException {
		Event event = eventMapper.get(eventId);
		List<CouponConfig> list = couponConfigMapper.listCouponConfigByEventId(eventId);
		event.setCouponConfigList(list);
		return event;
	}
	
	/**
	 * 根据活动类型获取活动列表
	 * @param type
	 */
	@Override
	public List<Event> listEvents(Short type) throws UhomeStoreException {
		List<Event> events = eventMapper.listEvents(type);
		for(int i=0; events!=null&&i<events.size(); i++){
			Event event = events.get(i);
			List<CouponConfig> list = couponConfigMapper.listCouponConfigByEventId(event.getEventId());
			event.setCouponConfigList(list);
			//适用范围
			event.setEventRangeNames(getEventRangeNames(event.getEventId()));
			//设置活动状态
			if(Event.STATUS_ABNORMAL_DELETE.equals(event.getStatus())){
				event.setActivityStatus(Event.ACTIVITYSTATUS_DELETE);
			}else if(list.size() > 0){
				CouponConfig config = list.get(0);
				Date startTime = config.getStartTime();
				Date endTime = config.getEndTime();
				Date currentTime = new Date();
				if(startTime.compareTo(currentTime) > 0){
					event.setActivityStatus(Event.ACTIVITYSTATUS_NOT_START);
				}else if(endTime.compareTo(currentTime) < 0){
					event.setActivityStatus(Event.ACTIVITYSTATUS_EXPIRED);
				}else if(currentTime.compareTo(startTime) >= 0 && currentTime.compareTo(endTime) <= 0){
					event.setActivityStatus(Event.ACTIVITYSTATUS_IN_PROGRESS);
				}
			}
		}
		return events;
	}

	/**
	 * 删除活动
	 * @param eventId
	 */
	@Override
	public void deleteEvent(Event event) throws UhomeStoreException {
		event.setStatus(Event.STATUS_DELETE);
		eventMapper.update(event);
	}

	/**
	 * 非常规删除活动
	 * @param eventId
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
	public void abnormalDeleteEvent(Event event) throws UhomeStoreException {
		event.setStatus(Event.STATUS_ABNORMAL_DELETE);
		eventMapper.update(event);
		Integer eventId = event.getEventId();
		//更新已激活未使用的优惠券为已删除状态
		userCouponMapper.updateCouponStatus2Deleted(eventId);
		//删除未激活的优惠券
		couponMapper.deleteNotActiveCoupons(eventId);
	}

	/**
	 * 导出优惠券
	 * @param eventId
	 * @return
	 */
	@Override
	public List<Coupon> listCoupons(Integer eventId) throws UhomeStoreException {
		return couponMapper.listCoupons(eventId);
	}
	
	/**
	 * 生成优惠券,供微信接口调用
	 * @param eventId
	 * @param wxuFlag
	 * @return
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
	public List<String> createCoupon4Weixin(Integer eventId, String wxuFlag) throws UhomeStoreException {
		return createCoupon(eventId, wxuFlag);
	}

	/**
	 * 生成优惠券
	 * @param eventId
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public List<String> createCoupon(Integer eventId, String wxuFlag) throws UhomeStoreException {
		Event event = eventMapper.get(eventId);
		Short type = event.getType();
		//优惠券号前缀
		String prefix = "";
		//活动包 id
		Integer packageId = null;
		if(Event.TYPE_WECHAT.equals(type)){
			prefix = wechatCouponNoPrefix;
			packageId = eventId;
		}else if(Event.TYPE_PROMOTION.equals(type)){
			if(event.getActiveNum().equals(event.getEventCount())){
				throw new UhomeStoreException(CodeConstants.QUOTA_FULLF_ERROR);
			}
			wxuFlag = UUID.randomUUID().toString();
			packageId = eventId;
		}else{
			prefix = getPrefix(event.getEventName());
		}
		//优惠券配置
		List<CouponConfig> configList = couponConfigMapper.listCouponConfigByEventId(eventId);
		//批量保存优惠券
		List<Coupon> coupons = new ArrayList<Coupon>();
		List<String> couponNos = new ArrayList<String>();
		for(CouponConfig config : configList){
			Integer count = config.getCouponCount();
			//生成一定数量的优惠券号
			List<String> list = docNumSystemService.getCouponNos(count, prefix);
			couponNos.addAll(list);
			//生成优惠券
			try {
				for(int i=0; i<count; i++){
					Coupon coupon = new Coupon();
					//设置优惠券号
					String couponNo = list.get(i);
					coupon.setCouponNo(couponNo);
					coupon.setIsActive(Coupon.ACTIVE_STATUS_OFF);
					coupon.setWxuFlag(wxuFlag);
					coupon.setPackageId(packageId);
					coupon.setCouponConfigId(config.getCouponConfigId());
					coupons.add(coupon);
					
					if((i + 1) % 1000 == 0){
						couponMapper.insertBatch(coupons);
						coupons.clear();
					}
					
				}
				if(coupons.size() > 0){
					couponMapper.insertBatch(coupons);
					coupons.clear();
				}
			} catch (DataAccessException e) {
				logger.error("coupon表插入数据出错" + e.getMessage());
				throw new UhomeStoreException(e.getMessage());
			}
		}
		
		if(Event.TYPE_PROMOTION.equals(type)){
			Integer num = eventMapper.updateActiveNumById(eventId);
			if(num == 0 || event.getActiveNum().equals(event.getEventCount())){
				throw new UhomeStoreException(CodeConstants.QUOTA_FULLF_ERROR);
			}
		}
		return couponNos;
	}

	/**
	 * 根据活动名称获取两位字符的优惠券号前缀
	 * @param eventName
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	private String getPrefix(String eventName) throws UhomeStoreException {
		//替换活动名称中的特殊字符
		eventName = eventName.replaceAll("[^\\w\\u4E00-\\u9FA5]+", "");
		StringBuffer strBuf = new StringBuffer();
		if(eventName.length() > 1){
			HanyuPinyinOutputFormat format= new HanyuPinyinOutputFormat();//拼音工具类的转换类型
			format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		 	format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
			format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE); 
			
			for(int i=0;i<2;i++){
				try {
					char ch = eventName.charAt(i);
					if(String.valueOf(ch).matches("[\\u4E00-\\u9FA5]+")){
						//如果是汉字则取其拼音首字母
						String pinyinArray[] =PinyinHelper.toHanyuPinyinStringArray(ch, format);
						strBuf.append(pinyinArray[0].charAt(0));	
					}else{
						if(Character.isLetter(ch) && Character.isLowerCase(ch)){
							ch = Character.toUpperCase(ch);
						}
						strBuf.append(ch);
					}
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					throw new UhomeStoreException(e.getMessage());
				}
			}
			
		}
		return strBuf.toString();
	}

	/**
	 * 查询优券惠
	 * @param couponPage
	 */
	@Override
	public void searchCoupons(BasePagination<Coupon> couponPage) throws UhomeStoreException {
		Map<String, Object> searchParams = couponPage.getSearchParamsMap();
		if(couponPage.isNeedSetTotal()){
			Integer total = couponMapper.searchCouponsCount(searchParams);
			couponPage.setTotal(total);
		}
		Collection<Coupon> result = couponMapper.searchCoupons(searchParams);
		if(result != null && result.size() > 0){
			Date currentTime = new Date();
			for(Coupon coupon : result){
				UserCoupon userCoupon = coupon.getUserCoupon();
				if(userCoupon != null){
					Short couponStatus = userCoupon.getCouponStatus();
					if(UserCoupon.COUPON_STATUS_1.equals(couponStatus)){
						coupon.setStatus(Coupon.STATUS_USED_YES);//已使用
					}else if(UserCoupon.COUPON_STATUS_0.equals(couponStatus)){
						if(currentTime.compareTo(coupon.getCouponConfig().getEndTime()) > 0){
							coupon.setStatus(Coupon.STATUS_EXPIRED);//已过期
						}else{
							coupon.setStatus(Coupon.STATUS_USED_NO);//未使用
						}
					}else{
						coupon.setStatus(Coupon.STATUS_DELETE);//已删除
					}
				}else{
					if(currentTime.compareTo(coupon.getCouponConfig().getEndTime()) > 0){
						coupon.setStatus(Coupon.STATUS_EXPIRED);//已过期
					}else{
						coupon.setStatus(Coupon.STATUS_USED_NO);//未使用
					}
				}
			}
		}
		couponPage.setResult(result);
	}

	/**
	 * 查询所有的优惠券金额
	 * @return
	 */
	@Override
	public List<BigDecimal> listAllowances() throws UhomeStoreException {
		return couponConfigMapper.listAllowances();
	}

	@Override
	public BigDecimal getCouponsAmount(Integer eventId) throws UhomeStoreException {
		return couponConfigMapper.getCouponsAmount(eventId);
	}

	/**
	 * 获取适用范围
	 * @return
	 */
	@Override
	public List<EventRange> getEventRanges(Date startTime, Date endTime)
			throws UhomeStoreException {
		
		EventRange allPlanEventRange = new EventRange();
		allPlanEventRange.setName("全场");
		allPlanEventRange.setType(EventRange.TYPE_ALLPLAN);
		
		EventRange notOnLinePlanEventRange = new EventRange();
		notOnLinePlanEventRange.setName("未上线");
		notOnLinePlanEventRange.setChildrenEventRanges(new ArrayList<EventRange>());
		
		EventRange onLinePlanEventRange = new EventRange();
		onLinePlanEventRange.setName("已上线");
		onLinePlanEventRange.setChildrenEventRanges(new ArrayList<EventRange>());
		
		List<EventRange> eventRanges = new ArrayList<EventRange>();
		eventRanges.add(allPlanEventRange);
		eventRanges.add(notOnLinePlanEventRange);
		eventRanges.add(onLinePlanEventRange);
		
		startTime = DateUtil.roundToDay(startTime);
		Date tempDate = DateUtil.add(DateUtil.roundToDay(endTime), Calendar.HOUR_OF_DAY, 24);
		endTime = DateUtil.add(tempDate, Calendar.SECOND, -1);
		List<Plan> plans = planMapper.listPlans4EventRange(startTime, endTime);
		Date currentTime = new Date();
		for (Plan plan : plans) {
			EventRange er = new EventRange(plan.getPlanId(), plan.getName(), EventRange.TYPE_PLAN);
			if (plan.getStartTime().compareTo(currentTime) >= 0) {
				notOnLinePlanEventRange.getChildrenEventRanges().add(er);
			} else if (plan.getStartTime().compareTo(currentTime) < 0){
				onLinePlanEventRange.getChildrenEventRanges().add(er);
			}
		}
		 
		return eventRanges;
	}
	
	/**
	 * 获取适用范围名称列表
	 * @param eventId
	 * @return
	 */
	public String getEventRangeNames(Integer eventId) {
		StringBuilder strBuilder = new StringBuilder();
		List<EventRange> eventRanges = eventRangeMapper.listEventRangesByEventId(eventId);
		if(eventRanges != null){
			if(eventRanges.size() == 0){
				strBuilder.append("全场,");
			}else{
				List<Integer> planIds = new ArrayList<Integer>();
				for(EventRange eventRange : eventRanges){
					Short eventRangeType = eventRange.getType();
					if(EventRange.TYPE_ALLPLAN.equals(eventRangeType)){
						strBuilder.append("全场,");
					}else if(EventRange.TYPE_PLAN.equals(eventRangeType)){
						planIds.add(eventRange.getOutId());
					}
				}
				if(planIds.size() > 0){
					String planNames = planMapper.getNamesByPlanIds(planIds);
					strBuilder.append(planNames);
					strBuilder.append(",");
				}
			}
		}
		if(strBuilder.length() > 0){
			strBuilder.deleteCharAt(strBuilder.length() - 1);
		}
		return strBuilder.toString();
	}
	
}
