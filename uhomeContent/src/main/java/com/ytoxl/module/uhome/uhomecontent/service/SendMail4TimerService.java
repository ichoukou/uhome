package com.ytoxl.module.uhome.uhomecontent.service;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;

/**
 * @author wangguoqing
 *
 */
public interface SendMail4TimerService {
	
	/**
	 * 发送邮件
	 * @throws UhomeStoreException
	 */
	public void autoSendMail() throws UhomeStoreException;
	
	/**
	 * 删除发送成功的邮件
	 * @throws UhomeStoreException
	 */
	public void autoDeleteMail() throws UhomeStoreException;
	
	/**
	 * 插入品牌订阅邮件
	 * @throws UhomeStoreException
	 */
	public void saveBrandSubscribeMailQueue() throws UhomeStoreException;
	
	/**
	 * 插入品牌特卖订阅邮件
	 * @throws UhomeStoreException
	 */
	public void saveSpecialSellerSubscribeMailQueue() throws UhomeStoreException;
	
	/**
	 * 插入即将上线订阅邮件
	 * @throws UhomeStoreException
	 */
	public void savePlanSubscribeMailQueue() throws UhomeStoreException;
}
