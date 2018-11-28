package com.ytoxl.module.uhome.uhomecontent.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.BaseTest;

/**
 * @author wangguoqing
 *
 */
public class SendMail4TimerServiceTest extends BaseTest {
	
	@Autowired
	private SendMail4TimerService sendMail4TimerService;

	@Test
	public void saveSubscribeMailQueue() throws UhomeStoreException{
//		sendMail4TimerService.saveBrandSubscribeMailQueue();
//		sendMail4TimerService.savePlanSubscribeMailQueue();
		sendMail4TimerService.saveSpecialSellerSubscribeMailQueue();
	}
	
	@Test
	public void autoSendMail() throws UhomeStoreException{
		sendMail4TimerService.autoSendMail();
	}
	
	@Test
	public void autoDelMail() throws UhomeStoreException{
		sendMail4TimerService.autoDeleteMail();
	}
}
