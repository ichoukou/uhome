package com.ytoxl.module.uhome.uhomecontent.mapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.uhome.uhomecontent.BaseTest;
import com.ytoxl.module.uhome.uhomecontent.dataobject.MailTemplate;

/**
 * @author wangguoqing
 *
 */
public class MailTemplateMapperTest extends BaseTest  {

	@Autowired
	private MailTemplateMapper<MailTemplate> mailTemplateMapper;
	
	@Test
	public void getAllMailTemplate() {
		// TODO Auto-generated method stub
		mailTemplateMapper.getAllMailTemplate();
	}
	
	@Test
	public void getMailTemplate(){
		MailTemplate mt = mailTemplateMapper.getMailTemplateByType((short)5);
		log.info("~~~~~~~~~~~~~~~~:"+mt);
	}

}
