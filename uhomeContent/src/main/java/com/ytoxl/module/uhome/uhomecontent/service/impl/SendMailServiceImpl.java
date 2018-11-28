package com.ytoxl.module.uhome.uhomecontent.service.impl;

import java.util.List;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ytoxl.module.core.common.constant.StringConstant;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.common.utils.TemplateUtils;
import com.ytoxl.module.uhome.uhomecontent.dataobject.MailQueue;
import com.ytoxl.module.uhome.uhomecontent.dataobject.MailTemplate;
import com.ytoxl.module.uhome.uhomecontent.mapper.MailQueueMapper;
import com.ytoxl.module.uhome.uhomecontent.mapper.MailTemplateMapper;
import com.ytoxl.module.uhome.uhomecontent.service.SendMailService;

/**
 * @author wangguoqing
 *
 */
@Service
public class SendMailServiceImpl implements SendMailService {
	private static Logger logger = LoggerFactory.getLogger(SendMailServiceImpl.class);
	
	@Autowired
	private JavaMailSender javaMailSender;
	@Value("${mail.sender.email}")
	private String senderEmail;
	@Value("${mail.sender.name}")
	private String senderName;
	
	@Autowired
	private MailTemplateMapper<MailTemplate> mailTemplateMapper;
	@Autowired
	private TemplateUtils templateUtils;
	@Autowired
	private MailQueueMapper<MailQueue> mailQueueMapper;


	@Override
	public String getMailTitle(Short type, Object data) throws UhomeStoreException {
		MailTemplate mt = mailTemplateMapper.getMailTemplateByType(type);
		if(null == mt){
			return "没有找到模版";
		}
		String hemtlText = templateUtils.getFreeMarkerText(mt.getTitle(), data);
		return hemtlText;
	}

	/* (non-Javadoc)
	 * @see com.ytoxl.module.uhome.uhomebase.service.SendEmailService#getMailContent(java.lang.String, java.util.Map)
	 */
	@Override
	public String getMailContent(Short type, Object data) throws UhomeStoreException {
		MailTemplate mt = mailTemplateMapper.getMailTemplateByType(type);
		if(null == mt){
			return "没有找到模版";
		}
		String hemtlText = templateUtils.getFreeMarkerText(mt.getContent(), data);
		return hemtlText;
	}

	/* (non-Javadoc)
	 * @see com.ytoxl.module.uhome.uhomebase.service.SendEmailService#saveDataToMailQueue(java.lang.Object)
	 */
	@Override
	public void saveDataToMailQueue(MailQueue mq) {
		mailQueueMapper.add(mq);
	}

	/* (non-Javadoc)
	 * @see com.ytoxl.module.uhome.uhomebase.service.SendEmailService#sendMail(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void sendMail(String to, String subject, String conetent) throws UhomeStoreException{
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,StringConstant.UTF8);
		try {
			messageHelper.setFrom(senderEmail, senderName);
			messageHelper.setTo(to);
			messageHelper.setSubject(subject);
			messageHelper.setText(conetent,true);
			javaMailSender.send(mimeMessage);
		} catch (Exception e) {
			throw new UhomeStoreException(e.getMessage());
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void sendMails(List<MailQueue> mqs) throws UhomeStoreException {
		if(mqs != null && mqs.size() > 0){
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,StringConstant.UTF8);
			int num = 0;
			for(MailQueue mq : mqs){
				try {
					messageHelper.setFrom(senderEmail, senderName);
					messageHelper.setTo(mq.getReceiver());
					messageHelper.setSubject(mq.getTitle());
					messageHelper.setText(mq.getContent(), true);
					javaMailSender.send(mimeMessage);
					mailQueueMapper.updateMailQueueStatusByMailQueueId(mq.getMailQueueId(), MailQueue.STATUS_SEND_SUCCESS);
					num++;
					logger.error("发送成功，邮箱：" + mq.getReceiver() + " 邮件类型：" + mq.getType());
				} catch (Exception e) {
					mailQueueMapper.updateMailQueueStatusByMailQueueId(mq.getMailQueueId(), MailQueue.STATUS_SEND_FAIL);
					logger.error("发送失败，邮箱：" + mq.getReceiver() + " 邮件类型：" + mq.getType() + "\n" + e.getMessage());
					continue;
				}
			}
			logger.error("成功发送" + num + "封邮件");
		}
	}

}
