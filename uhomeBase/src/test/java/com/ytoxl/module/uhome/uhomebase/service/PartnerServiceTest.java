package com.ytoxl.module.uhome.uhomebase.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.uhome.uhomebase.BaseTest;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Partner;

public class PartnerServiceTest extends BaseTest {
	@Autowired
	private PartnerService partnerService;
	
	@Test
	public void testGetPartnerById() throws UhomeStoreException{
		Partner partner = partnerService.getPartnerById(Partner.CPS_DUOMAI_ID);
		log.info(partner.getUnionId());
	}

}
