package com.ytoxl.module.uhome.uhomecontent.service;

import javax.swing.text.html.HTML;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.uhome.uhomecontent.BaseTest;
import com.ytoxl.module.uhome.uhomecontent.dataobject.MailSubscribe;

/**
 * @author wangguoqing
 *
 */
public class SendMailServiceTest extends BaseTest  {
	
	@Autowired
	private SendMailService sendEmailService;
	
	@Test
	public void sendEmail(){
		try{
			sendEmailService.sendMail("594566751@qq.com", "subject", "content");
		}catch (Exception e) {
			e.printStackTrace();
			log.info("!!!!!!!!!!!!!!!!!!!!!!!!!send email exception!");
			return;
		}
		log.info("~~~~~~~~~~~~~~~~~~~~~send email success!");
	}
	
	@Test
	public void getMailContent(){
		try{
			MailSubscribe ms = new MailSubscribe();
			ms.setEmail("594566751@qq.com");
			String htmlText = sendEmailService.getMailContent((short)5,ms);
			sendEmailService.sendMail(ms.getEmail(), "subject", htmlText);
			log.info("~~~~~~~~~~~~~~~~~~~~~send email success!"+htmlText);
		}catch (Exception e) {
			e.printStackTrace();
			log.info("!!!!!!!!!!!!!!!!!!!!!!!!!send email exception!");
			return;
		}
	}

}
