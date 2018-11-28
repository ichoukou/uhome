package com.ytoxl.module.uhome.uhomecontent.service;

import java.util.List;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.dataobject.MailQueue;

/**
 * @author wangguoqing
 *
 */
public interface SendMailService{
	
	public String getMailTitle(Short type, Object data)throws UhomeStoreException;
	/**
	 * 获取发送邮件的内容  
	 * @param type
	 * @param data   FreeMarker动态合并内容的model
	 * @return
	 * @throws UhomeStoreException
	 */
	public String getMailContent(Short type,Object data)throws UhomeStoreException;
	
	public void saveDataToMailQueue(MailQueue mq)throws UhomeStoreException;
	
	public void sendMail(String to,String subject,String conetent) throws UhomeStoreException;
	
	public void sendMails(List<MailQueue> mqs) throws UhomeStoreException;
}
