package com.ytoxl.module.uhome.uhomecontent.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.BaseTest;

/**
 * @author wangguoqing
 *
 */
public class MailSubscribeServiceTest extends BaseTest {
	
	@Autowired
	private MailSubscribeService mailSubscribeService;
	
	
	@Test
	public void saveMergeMailSubscribe() throws UhomeStoreException {
		mailSubscribeService.saveMergeMailSubscribe((short)1,10);
		
		
		
	}

}
