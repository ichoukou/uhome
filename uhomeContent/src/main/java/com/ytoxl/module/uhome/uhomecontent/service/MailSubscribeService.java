package com.ytoxl.module.uhome.uhomecontent.service;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.dataobject.MailSubscribe;

public interface MailSubscribeService {
	/**
	 * 查询用户订阅的品牌
	 * @param userId
	 * @return
	 */
	public void searchMailSubBrands(BasePagination<MailSubscribe> mailSubPage) throws UhomeStoreException;
	/**
	 * 更新订阅状态
	 * @param mailSubscribe
	 * @return
	 */
	public void updateMailSubscribeStatus(MailSubscribe mailSubscribe) throws UhomeStoreException;
	
	/**
	 * 保存邮件订阅
	 * @param mailSubscribe
	 * @throws UhomeStoreException
	 */
	public void addMailSubscribe(MailSubscribe mailSubscribe) throws UhomeStoreException;
	
	/**
	 * 将订阅邮件的邮件和所对应的模版合并
	 * @throws UhomeStoreException
	 */
	public void saveMergeMailSubscribe(Short type, Integer limit) throws UhomeStoreException;
	
	/**
	 * 更新邮件订阅状态为取消
	 * @param mail
	 * @param type
	 */
	public void updateMailSubscribeStatus2Cancel(String email, Short type) throws UhomeStoreException;
	
	/**
	 * 根据邮箱和类型查询订阅数量
	 * @param email
	 * @param type
	 * @return
	 */
	public Integer listCountByEmailAndType(String email, Short type) throws UhomeStoreException;
	
	public boolean isUnsubscribe(String email, Short type) throws UhomeStoreException;
	
}
