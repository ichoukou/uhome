package com.ytoxl.module.uhome.uhomecontent.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.dataobject.MailQueue;
import com.ytoxl.module.uhome.uhomecontent.dataobject.MailSubscribe;
import com.ytoxl.module.uhome.uhomecontent.mapper.MailQueueMapper;
import com.ytoxl.module.uhome.uhomecontent.service.MailSubscribeService;
import com.ytoxl.module.uhome.uhomecontent.service.SendMail4TimerService;
import com.ytoxl.module.uhome.uhomecontent.service.SendMailService;

/**
 * @author wangguoqing
 *
 */
@Service
public class SendMail4TimerServiceImpl implements SendMail4TimerService {
	private static Logger logger = LoggerFactory.getLogger(SendMail4TimerServiceImpl.class);
	
	@Autowired
	private MailQueueMapper<MailQueue> mailQueueMapper;
	@Autowired
	private SendMailService sendMailService;
	@Value("${everytime_send_mail_queue_num}")
	private Integer num;
	@Autowired
	private MailSubscribeService mailSubscribeService;

	/**
	 * 发送邮件
	 * @throws UhomeStoreException
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=Exception.class)
	public void autoSendMail() throws UhomeStoreException{
		/*//获取要发送邮件的总数量
		int sendMailTotal = mailQueueMapper.getMailQueuesByStatusAndSendTimeCount();
		while(sendMailTotal > 0){
			//按照分页的模式发邮件
			int sendMailNum = 0; //成功发送多少条邮件
			List<MailQueue> mqs = mailQueueMapper.getMailQueuesByStatusAndSendTime(num);
			if(mqs != null && mqs.size() > 0){
				//发送邮件
				sendMailNum = mqs.size();
				for(MailQueue mq : mqs){
					try {
						sendMailService.sendMail(mq.getReceiver(), mq.getTitle(), mq.getContent());
						//更新状态
						mailQueueMapper.updateMailQueueStatusByMailQueueId(mq.getMailQueueId(), MailQueue.STATUS_SEND_SUCCESS);
						logger.error("发送成功，邮箱：" + mq.getReceiver() + " 邮件类型：" + mq.getType());
					} catch (UhomeStoreException e) {
						logger.error("发送失败，邮箱：" + mq.getReceiver() + " 邮件类型：" + mq.getType() + "\n" + e.getMessage());
						mailQueueMapper.updateMailQueueStatusByMailQueueId(mq.getMailQueueId(), MailQueue.STATUS_SEND_FAIL);
						sendMailNum--;
						continue;
					}
				}
			}
			sendMailTotal -= num;
			logger.error("【"+new Date(System.currentTimeMillis())+"】发送成功:"+sendMailNum+"条");
		}*/
		
		List<MailQueue> mqs = mailQueueMapper.getMailQueuesByStatusAndSendTime(num);
		mailQueueMapper.updateMailQueueStatusByMailQueueId(996, MailQueue.STATUS_SEND_FAIL);
		try {
			sendMailService.sendMails(mqs);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 删除发送成功的邮件
	 * @throws UhomeStoreException
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
	public void autoDeleteMail() throws UhomeStoreException{
		mailQueueMapper.deleteMails(num);
	}
	
	/**
	 * 插入品牌订阅邮件
	 * @throws UhomeStoreException
	 */
	@Override
	public void saveBrandSubscribeMailQueue() throws UhomeStoreException {
		mailSubscribeService.saveMergeMailSubscribe(MailSubscribe.TYPE_BRAND_SUBSCRIBE, num);
	}

	/**
	 * 插入品牌特卖订阅邮件
	 * @throws UhomeStoreException
	 */
	@Override
	public void saveSpecialSellerSubscribeMailQueue() throws UhomeStoreException {
		mailSubscribeService.saveMergeMailSubscribe(MailSubscribe.TYPE_SPECIAL_SELLER, num);
	}
	
	/**
	 * 插入即将上线订阅邮件
	 * @throws UhomeStoreException
	 */
	@Override
	public void savePlanSubscribeMailQueue() throws UhomeStoreException {
		mailSubscribeService.saveMergeMailSubscribe(MailSubscribe.TYPE_TOBEONTHELINE, num);
	}

}
