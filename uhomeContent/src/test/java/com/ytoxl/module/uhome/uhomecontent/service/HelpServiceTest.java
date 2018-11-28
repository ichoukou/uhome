package com.ytoxl.module.uhome.uhomecontent.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.core.common.utils.EncodeUtils;
import com.ytoxl.module.uhome.uhomecontent.BaseTest;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;

/**
 * @author wangguoqing
 *
 */
public class HelpServiceTest extends BaseTest {
	
	@Autowired
	private HelpService helpService;
	
	@Test
	public void getHelp4ProductDetail() throws UhomeStoreException {
		helpService.getHelp4ProductDetail();

	}
	
	@Test
	public void testBase64(){
		String t = EncodeUtils.base64Encode(System.currentTimeMillis()+":ytoxl123");
		log.info(t);
		log.info(EncodeUtils.base64Decode(t));
	}

}
