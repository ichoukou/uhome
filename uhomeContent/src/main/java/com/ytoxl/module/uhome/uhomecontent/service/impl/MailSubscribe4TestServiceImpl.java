package com.ytoxl.module.uhome.uhomecontent.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.ytoxl.module.core.common.utils.EncodeUtils;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Plan;
import com.ytoxl.module.uhome.uhomebase.dataobject.Product;
import com.ytoxl.module.uhome.uhomebase.service.PlanService;
import com.ytoxl.module.uhome.uhomecontent.dataobject.MailSubscribe;
import com.ytoxl.module.uhome.uhomecontent.service.MailSubscribe4TestService;
import com.ytoxl.module.uhome.uhomecontent.service.SendMailService;

@Service
public class MailSubscribe4TestServiceImpl implements MailSubscribe4TestService {

	@Autowired
	private PlanService planService;
	@Autowired
	private SendMailService sendMailService;
	@Value("${contextPath}")
	private String contextPath;
	@Value("${fileThumbPath}")
	private String fileThumbPath;
	@Value("${imagesPath}")
	private String imagesPath;

	@Override
	public void testSendEmail(List<Short> typeList, String email) throws UhomeStoreException {
		for(int i=0; typeList!=null && i<typeList.size(); i++){
			Short type = typeList.get(i);
			if (type.equals(MailSubscribe.TYPE_SPECIAL_SELLER)) {
				sendSpecialSellerSubscribeEmail(email, type);
			} else if (type.equals(MailSubscribe.TYPE_BRAND_SUBSCRIBE)) {
				sendBrandSubscribeEmail(email, type);
			} else if (type.equals(MailSubscribe.TYPE_TOBEONTHELINE)) {
				sendPlanSubscribeEmail(email, type);
		  }
		}
	}
	
	private void sendPlanSubscribeEmail(String email, Short type) throws UhomeStoreException {
		List<Plan> plans = planService.listPlanMostPopularBrands();
		if (plans.size() > 0) {
			List<Plan> list = new ArrayList<Plan>();
			list.add(plans.get(0));
			
			Map<String, Object> contentMap = new HashMap<String, Object>();
			Map<String, Object> titleMap = new HashMap<String, Object>();
			contentMap.put("plans", list);
			
			List<String> planNames = new ArrayList<String>();
			List<Double> rebates = new ArrayList<Double>();
			
			for (Plan plan : list) {
				plan.setBrandId(plan.getBrand().getBrandId());
				String planName = plan.getName();
				if (!planNames.contains(planName)) {
					planNames.add(planName);
				}
				Product pro = plan.getProduct();
				rebates.add(pro.getRebate());
			}
			
			Double[] rebateArray = new Double[rebates.size()];
			rebateArray = rebates.toArray(rebateArray);
			Arrays.sort(rebateArray);

			titleMap.put("planNames", planNames);
			titleMap.put("minRebate", rebateArray[0]);
			sendEmail(email, type, contentMap, titleMap);
		}
	}
	
	private void sendBrandSubscribeEmail(String email, Short type) throws UhomeStoreException {
		List<Plan> plans = planService.listPlanMostPopularBrands();
		if (plans.size() > 0) {
			List<Plan> list = new ArrayList<Plan>();
			list.add(plans.get(0));
			
			Map<String, Object> contentMap = new HashMap<String, Object>();
			Map<String, Object> titleMap = new HashMap<String, Object>();
			contentMap.put("plans", list);
		
			List<String> brandNames = new ArrayList<String>();
			List<Double> rebates = new ArrayList<Double>();
			
			for (Plan plan : list) {
				plan.setBrandId(plan.getBrand().getBrandId());
				String brandName = plan.getBrand().getName();
				if (!brandNames.contains(brandName)) {
					brandNames.add(brandName);
				}
				Product pro = plan.getProduct();
				rebates.add(pro.getRebate());
			}
			
			Double[] rebateArray = new Double[rebates.size()];
			rebateArray = rebates.toArray(rebateArray);
			Arrays.sort(rebateArray);

			titleMap.put("brandNames", brandNames);
			titleMap.put("minRebate", rebateArray[0]);
			sendEmail(email, type, contentMap, titleMap);
		}
	}
	
	private void sendSpecialSellerSubscribeEmail(String email, Short type) throws UhomeStoreException {
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
				Product pro = plan.getProduct();
				rebates.add(pro.getRebate());
			}
			// 取最低折扣
			Double[] rebateArray = new Double[rebates.size()];
			rebateArray = rebates.toArray(rebateArray);
			Arrays.sort(rebateArray);

			titleMap.put("brandNames", brandNames);
			titleMap.put("minRebate", rebateArray[0]);
			sendEmail(email, type, contentMap, titleMap);
		}
	}
	
	private void sendEmail(String email, Short type,
			Map<String, Object> contentMap, Map<String, Object> titleMap) throws UhomeStoreException{
		contentMap.put("contextPath", contextPath);
		contentMap.put("fileThumbPath", fileThumbPath);
		contentMap.put("imagesPath", imagesPath);
		//邮件退订链接
		String mailUrl = contextPath+"/unSubscriptionEmail.htm?email="+ EncodeUtils.base64Encode(email)+"&type="+EncodeUtils.base64Encode(type.toString());
		contentMap.put("mailUrl", mailUrl);
		String content = sendMailService.getMailContent(type, contentMap);
		String title = sendMailService.getMailTitle(type, titleMap);
		sendMailService.sendMail(email, title, content);
	}
}