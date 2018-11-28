package com.ytoxl.module.uhome.uhomecontent.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.BaseTest;
import com.ytoxl.module.uhome.uhomecontent.dataobject.AdvPosition;

public class AdvServiceTest extends BaseTest {
	@Autowired
	private AdvService advService;

	@Test
	public void testlistAdvPositons() {
		List<AdvPosition> advPositions=new ArrayList<AdvPosition>();
		try {
			 advService.listAdvPositons();
			log.info(""+advPositions.size());
		} catch (UhomeStoreException e) {
			log.info("存在异常", e);
		}
	}

	public AdvService getAdvService() {
		return advService;
	}

	public void setAdvService(AdvService advService) {
		this.advService = advService;
	}
	
	@Test
	public void getHomeDefaultSecKillAdvListTest() throws UhomeStoreException{
		log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@:"+advService.getHomeDefaultSecKillAdvList().size());
	}
	
}
