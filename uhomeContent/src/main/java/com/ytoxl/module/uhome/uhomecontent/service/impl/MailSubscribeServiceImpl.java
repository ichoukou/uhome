package com.ytoxl.module.uhome.uhomecontent.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.core.common.utils.EncodeUtils;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.common.utils.DateUtil;
import com.ytoxl.module.uhome.uhomebase.dataobject.Plan;
import com.ytoxl.module.uhome.uhomebase.service.PlanService;
import com.ytoxl.module.uhome.uhomecontent.dataobject.MailQueue;
import com.ytoxl.module.uhome.uhomecontent.dataobject.MailSubscribe;
import com.ytoxl.module.uhome.uhomecontent.mapper.MailQueueMapper;
import com.ytoxl.module.uhome.uhomecontent.mapper.MailSubscribeMapper;
import com.ytoxl.module.uhome.uhomecontent.service.MailSubscribeService;
import com.ytoxl.module.uhome.uhomecontent.service.SendMailService;

@Service
public class MailSubscribeServiceImpl implements MailSubscribeService {
	private static Logger logger = LoggerFactory.getLogger(MailSubscribeServiceImpl.class);
	
	@Autowired
	private MailSubscribeMapper<MailSubscribe> mailSubscribeMapper;
	@Autowired
	private MailQueueMapper<MailQueue> mailQueueMapper;
	@Autowired
	private PlanService planService;
	@Autowired
	private SendMailService sendMailService;
	@Value("${mail.sender.email}")
	private String senderEmail;
	@Value("${contextPath}")
	private String contextPath;
	@Value("${fileThumbPath}")
	private String fileThumbPath;
	@Value("${imagesPath}")
	private String imagesPath;
	
	/**
	 * 查询用户订阅的品牌
	 * @param userId
	 * @return
	 */
	@Override
	public void searchMailSubBrands(BasePagination<MailSubscribe> mailSubPage)
			throws UhomeStoreException {
		Map<String, Object> searchParams = mailSubPage.getSearchParamsMap();
		if (mailSubPage.isNeedSetTotal()) {
			Integer total = mailSubscribeMapper.searchMailSubBrandsCount(searchParams);
			mailSubPage.setTotal(total);
		}
		Collection<MailSubscribe> result = mailSubscribeMapper.searchMailSubBrands(searchParams);
		mailSubPage.setResult(result);
	}

	/**
	 * 更新订阅状态
	 * @param mailSubscribe
	 * @return
	 */
	@Override
	public void updateMailSubscribeStatus(MailSubscribe mailSubscribe) throws UhomeStoreException {
		mailSubscribeMapper.updateMailSubscribeStatus(mailSubscribe);
	}

	@Override
	public void addMailSubscribe(MailSubscribe mailSubscribe) throws UhomeStoreException {
		String email = mailSubscribe.getEmail();
		Short type = mailSubscribe.getType();
		if (MailSubscribe.TYPE_BRAND_SUBSCRIBE.equals(type)) {//品牌订阅
			// 获取要订阅品牌的ids
			String brandIds = mailSubscribe.getBrandIds();
			// 清除要订阅品牌的ids
			mailSubscribe.setBrandIds(null);
			// 品牌集中营订阅多个品牌
			if (MailSubscribe.TYPE_BRAND_SUBSCRIBE.equals(type) && brandIds != null && brandIds.length() > 0) {
				// 按逗号切割字符串
				String[] brandIdArr = brandIds.split(",");
				for (String s : brandIdArr) {
					// 查询是否重复了 TODO 通过email,brandId,type,status查询
					int brandId = Integer.parseInt(s);
					List<MailSubscribe> mss = mailSubscribeMapper.listMailSubscribeByEmailAndBrandIdAndType(email, type, brandId);
					if (mss != null && mss.size() > 0) {
						continue;
					}
					// 分别设置要订阅品牌的id
					mailSubscribe.setBrandId(brandId);
					mailSubscribeMapper.add(mailSubscribe);
				}
				return;
			} 
			List<MailSubscribe> ms = mailSubscribeMapper.listMailSubscribeByEmailAndBrandIdAndType(email, type, mailSubscribe.getBrandId());
			if (ms != null && ms.size() > 0) {
				// 说明该邮箱已经订阅过该品牌了 直接返回
				return;
			}
			
		}else if(MailSubscribe.TYPE_SPECIAL_SELLER.equals(type)) {//特卖订阅
			List<MailSubscribe> list = mailSubscribeMapper.listMailSubscribeByEmailAndType(email, type);
			if (list.size()>0) {
				// 特卖订阅已经订阅过了
				return ;
			}
		}else if(MailSubscribe.TYPE_TOBEONTHELINE.equals(type)){//即将上线订阅
			List <MailSubscribe> list = mailSubscribeMapper.listMailSubscribeByEmailAndPlanIdAndType(email, type, mailSubscribe.getPlanId());
			if (list.size()>0) {
				// 即将上线订阅已经订阅过了 直接返回
				return ;
			}		
		}
		mailSubscribeMapper.add(mailSubscribe);
	}
	
	@Override
	public void saveMergeMailSubscribe(Short type, Integer limit) throws UhomeStoreException {
		Integer totalCount = mailSubscribeMapper.searchMailSubscribeCount(type);
		for (int i = 0; totalCount != null && i < totalCount; i++) {
			List<MailSubscribe> mss = mailSubscribeMapper.searchMailSubscribe(type, i * limit, limit);
			Map<String, List<MailSubscribe>> mailSubscribeMap = new HashMap<String, List<MailSubscribe>>();
			for (MailSubscribe ms : mss) {
				String email = ms.getEmail();
				//判断是否已创建过邮件
				
				if (!mailSubscribeMap.containsKey(email)) {
					List<MailSubscribe> list = new ArrayList<MailSubscribe>();
					list.add(ms);
					mailSubscribeMap.put(email, list);
				} else {
					mailSubscribeMap.get(email).add(ms);
				}
			}
			//针对每个邮箱订阅创建邮件队列
			for (String email : mailSubscribeMap.keySet()) {
				if (MailSubscribe.TYPE_SPECIAL_SELLER.equals(type)) {
					saveSpecialSellerSubscribeEmail(email, type);
					
				} else if (MailSubscribe.TYPE_BRAND_SUBSCRIBE.equals(type)) {
					List<MailSubscribe> list = mailSubscribeMap.get(email);
					List<Integer> brandIds = new ArrayList<Integer>();
					for (MailSubscribe ms : list) {
						brandIds.add(ms.getBrandId());
					}
					saveBrandSubscribeEmail(email, type, brandIds);
				
				} else if (MailSubscribe.TYPE_TOBEONTHELINE.equals(type)) {
					List<MailSubscribe> list = mailSubscribeMap.get(email);
					List<Integer> planIds = new ArrayList<Integer>();
					for (MailSubscribe ms : list) {
						planIds.add(ms.getPlanId());
					}
					savePlanSubscribeEmail(email, type, planIds);
				}
			}
		}
	}
	
	private void savePlanSubscribeEmail(String email, Short type,
			List<Integer> planIds) throws UhomeStoreException {
		List<Plan> plans = planService.listTodaySpecialSellerPlansByPlanIds(planIds);
		if (plans.size() > 0) {
			Map<String, Object> contentMap = new HashMap<String, Object>();
			Map<String, Object> titleMap = new HashMap<String, Object>();
			contentMap.put("plans", plans);
			List<String> planNames = new ArrayList<String>();
			List<Double> rebates = new ArrayList<Double>();
			Double[] rebateArray = new Double[plans.size()];
			for (int j = 0; j < plans.size(); j++) {
				String planName = plans.get(j).getName();
				if(!planNames.contains(planName)){
					planNames.add(planName);
				}
				rebates.add(plans.get(j).getMinRebate());
			}
			rebateArray = rebates.toArray(rebateArray);
			Arrays.sort(rebateArray);
			titleMap.put("minRebate", rebateArray[0]);
			titleMap.put("planNames", planNames);
			saveEmailToQueue(email, type, contentMap, titleMap);
		}
	}
	
	private void saveBrandSubscribeEmail(String email, Short type, List<Integer> brandIds)
			throws UhomeStoreException {
		List<Plan> plans = planService.listTodaySpecialSellerPlansByBrandIds(brandIds);
		if (plans.size() > 0) {
			Map<String, Object> contentMap = new HashMap<String, Object>();
			Map<String, Object> titleMap = new HashMap<String, Object>();
			contentMap.put("plans", plans);
			List<String> brandNames = new ArrayList<String>();
			List<Double> rebates = new ArrayList<Double>();
			Double[] rebateArray = new Double[plans.size()];
			for (int j = 0; j < plans.size(); j++) {
				String brandName = plans.get(j).getBrand().getName();
				if(!brandNames.contains(brandName)){
					brandNames.add(brandName);
				}
				rebates.add(plans.get(j).getMinRebate());
			}
			rebateArray = rebates.toArray(rebateArray);
			Arrays.sort(rebateArray);
			titleMap.put("minRebate", rebateArray[0]);
			titleMap.put("brandNames", brandNames);
			saveEmailToQueue(email, type, contentMap, titleMap);
		}
	}

	private void saveSpecialSellerSubscribeEmail(String email, Short type)
			throws UhomeStoreException {
		List<Plan> mostPopularBrands = planService.listPlanMostPopularBrands();
		if (mostPopularBrands.size() > 0) {
			Map<String, Object> contentMap = new HashMap<String, Object>();
			Map<String, Object> titleMap = new HashMap<String, Object>();
			contentMap.put("mostPopularBrands", mostPopularBrands);
			// 取品牌名称与所有排期最低折扣
			List<String> brandNames = new ArrayList<String>();
			List<Double> rebates = new ArrayList<Double>();
			for (Plan plan : mostPopularBrands) {
				String brandName = plan.getBrand().getName();
				if (!brandNames.contains(brandName)) {
					brandNames.add(brandName);
				}
				rebates.add(plan.getProduct().getRebate());
			}
			// 取最低折扣
			Double[] rebateArray = new Double[rebates.size()];
			rebateArray = rebates.toArray(rebateArray);
			Arrays.sort(rebateArray);

			titleMap.put("brandNames", brandNames);
			titleMap.put("minRebate", rebateArray[0]);
			saveEmailToQueue(email, type, contentMap, titleMap);
		}
	}
	
	private void saveEmailToQueue(String email, Short type,
			Map<String, Object> contentMap, Map<String, Object> titleMap)
			throws UhomeStoreException {
		contentMap.put("contextPath", contextPath);
		contentMap.put("fileThumbPath", fileThumbPath);
		contentMap.put("imagesPath", imagesPath);
		//邮件退订链接
		String mailUrl = contextPath+"/unSubscriptionEmail.htm?email="+ EncodeUtils.base64Encode(email)+"&type="+EncodeUtils.base64Encode(type.toString());
		contentMap.put("mailUrl", mailUrl);
		
		String content = sendMailService.getMailContent(type, contentMap);
		String title = sendMailService.getMailTitle(type, titleMap);
		// 发送时间
		String dateStr = DateUtil.format(new Date(), "yyyy-MM-dd");
		Date date = DateUtil.valueOfStandard(dateStr);
		Date sendTime = DateUtil.add(date, Calendar.HOUR_OF_DAY, 10);

		MailQueue mq = new MailQueue();
		mq.setTitle(title);
		mq.setReceiver(email);
		mq.setSender(senderEmail);
		mq.setContent(content);
		mq.setSendTime(sendTime);
		mq.setType(type);
		mailQueueMapper.add(mq);
	}
	
	/**
	 * 更新邮件订阅状态为取消
	 */
	@Override
	public void updateMailSubscribeStatus2Cancel(String email, Short type) throws UhomeStoreException {
		Integer num = mailSubscribeMapper.updateMailSubscribeStatus2Cancel(email, type);
		if(num > 0){
			logger.error("订阅邮件退订成功，订阅邮箱:" + email + "订阅类型:" + type);
		}
		mailQueueMapper.deleteMailsByUnSubscribe(email, type);
	}

	/**
	 * 根据邮箱和类型查询订阅数量
	 * @param email
	 * @param type
	 * @return
	 */
	@Override
	public Integer listCountByEmailAndType(String email, Short type)
			throws UhomeStoreException {
		Integer count = mailSubscribeMapper.listCountByEmailAndType(email, type);
		return count;
	}

	@Override
	public boolean isUnsubscribe(String email, Short type)
			throws UhomeStoreException {
		Integer subscribeCount = mailSubscribeMapper.
				listCountByEmailAndTypeAndStatus(email, type, MailSubscribe.STATUS_SUBSCRIBE);
		if(subscribeCount == 0){
			Integer cancelCount = mailSubscribeMapper.
					listCountByEmailAndTypeAndStatus(email, type, MailSubscribe.STATUS_CANCEL);
			if(cancelCount > 0){
				return true;
			}
		}
		return false;
	}
	
}