package com.ytoxl.module.uhome.uhomebase.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.uhome.uhomebase.BaseTest;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Region;

public class DeliveryServiceTest extends BaseTest {
	@Autowired
	private DeliveryService regionService;

	@Test
	public void testgetRegionByCode() {
		Region region;
		try {
			region = regionService.getRegionByCode("");
			log.info(region.getRegionName());
		} catch (UhomeStoreException e) {
			log.info("存在异常", e);
		}
		

	}
}
