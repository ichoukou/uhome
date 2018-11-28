package com.ytoxl.module.uhome.uhomecontent.service;

import java.util.List;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;

public interface MailSubscribe4TestService {
	
	/**
	 * 将订阅邮件的邮件和所对应的模版合并
	 * @throws UhomeStoreException
	 */
	public void testSendEmail(List<Short> typeList, String email) throws UhomeStoreException;
	
}
