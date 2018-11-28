package com.ytoxl.module.uhome.uhomecontent.mapper;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.uhome.uhomecontent.BaseTest;
import com.ytoxl.module.uhome.uhomecontent.dataobject.MailQueue;

/**
 * @author wangguoqing
 *
 */
public class MailQueueMapperTest extends BaseTest {

	@Autowired
	private MailQueueMapper<MailQueue> mailQueueMapper;
	
	@Test
	public void get(){
		mailQueueMapper.get(100000);
	}
	
	@Test
	public void getMailQueuesByStatusAndSendTime(){
		List<MailQueue> mqs = mailQueueMapper.getMailQueuesByStatusAndSendTime(5);
		for(MailQueue mq : mqs){
			log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~:"+mq.getSender());
		}
	}
	
	@Test
	public void getMailQueuesByStstus(){
		List<MailQueue> mqs = mailQueueMapper.getMailQueuesByStatus(5, (short)2);
		for(MailQueue mq : mqs){
			log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~:"+mq.getSender());
		}
	}
}
