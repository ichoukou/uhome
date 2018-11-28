package com.ytoxl.module.uhome.uhomecontent.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.BaseTest;
import com.ytoxl.module.uhome.uhomecontent.dataobject.SpecialtopicAdvPosition;
import com.ytoxl.module.uhome.uhomecontent.dataobject.SpecialtopicAdvertisement;

public class SpecialTopicServiceTest extends BaseTest {

	@Autowired
	private SpecialTopicService topicService;
	
	//@Test
	public void TestListAdvPositions() throws UhomeStoreException{
		List<SpecialtopicAdvPosition> advPositions = topicService.listAdvPositions();
		for(SpecialtopicAdvPosition advPosition :advPositions){
			log.info("advPositionId:"+advPosition.getSpecialTopicAdvPositionId()
					+",advPositionName:"+advPosition.getName()
					+",advPositionCode:"+advPosition.getPositionCode());
		}
	}
	
	//@Test
	public void TestSaveAdvPositions() throws UhomeStoreException{
		SpecialtopicAdvertisement advertisement = new SpecialtopicAdvertisement();
		advertisement.setSpecialTopicAdvId(1);
		advertisement.setSpecialTopicTempletId(1);
		advertisement.setSpecialTopicAdvPositionId(1);
		advertisement.setImageUrl("/2013/10/12/test1.png");
		advertisement.setTurnUrl("/aaa1.htm");
		Integer num = topicService.saveAdvertisement(advertisement);
		log.info("num=========="+num);
	}
	
	@Test
	public void TestGetAdvertisementById() throws UhomeStoreException{
		SpecialtopicAdvertisement advertisement = topicService.getAdvertisementById(1);
		log.info("advId:"+advertisement.getSpecialTopicAdvId()
				+",advPositionId:"+advertisement.getSpecialTopicAdvPositionId()
				+",advPositionName:"+advertisement.getAdvPosition().getName()
				+",advTemplateId:"+advertisement.getSpecialTopicTempletId()
				+",advTemplateName:"+advertisement.getTopicTemplate().getName()
				+",advImageUrl:"+advertisement.getImageUrl()
				+",advTurnUrl:"+advertisement.getTurnUrl());
	}
	
	//@Test
	public void TestPagination() throws UhomeStoreException{
		BasePagination<SpecialtopicAdvertisement> advertisementPage = new BasePagination<SpecialtopicAdvertisement>();
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("specialTopicTempletId", "1");
		map.put("specialTopicAdvPositionId", "1");
		
		advertisementPage.setParams(map);
		advertisementPage.setCurrentPage(1);
		advertisementPage.setLimit(10);
		
		topicService.searchAdvertisement(advertisementPage);
		log.info("@@@@@@@@@@size=="+advertisementPage.getResult().size());
	}
}
