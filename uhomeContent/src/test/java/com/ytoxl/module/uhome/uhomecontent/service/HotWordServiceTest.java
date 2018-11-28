package com.ytoxl.module.uhome.uhomecontent.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.uhome.uhomecontent.BaseTest;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;

/**
 * @author wangguoqing
 *
 */
public class HotWordServiceTest extends BaseTest{
	
	@Autowired
	private HotWordService hotWordService;
	
	@Test
	public void litHotWords4Front() throws UhomeStoreException{
		log.info("--------------------------------"+hotWordService.litHotWords4Front().size());
	}

}
