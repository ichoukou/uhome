package com.ytoxl.module.uhome.uhomebase.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.mail.service.MailService;
import com.ytoxl.module.uhome.uhomebase.BaseTest;

import freemarker.template.TemplateException;

public class SenderMailTest extends BaseTest{
	
	@Autowired
	private MailService mailService;
	
	@Test
	public void testSenderMail() throws UnsupportedEncodingException, MessagingException, IOException, TemplateException {
		Map<String,String> map = new HashMap<String,String>();
		mailService.sendMail("594566751@qq.com", "rePassword",map , "123456");
	}
}

