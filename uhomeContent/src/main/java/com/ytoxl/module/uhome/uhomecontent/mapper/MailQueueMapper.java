package com.ytoxl.module.uhome.uhomecontent.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomecontent.dataobject.MailQueue;

/**
 * @author wangguoqing
 *
 */
public interface MailQueueMapper<T extends MailQueue> extends BaseSqlMapper<T> {
	
	/**
	 * 通过状态和发送时间读取要发送的邮件
	 * 默认12小时内要发送的邮件
	 * @param num 每次读取的条数
	 * @return
	 * @throws DataAccessException
	 */
	public List<MailQueue> getMailQueuesByStatusAndSendTime(Integer num) throws DataAccessException;
	
	/**
	 * 通过状态和发送时间读取要发送邮件的总数量
	 * @return
	 * @throws DataAccessException
	 */
	public Integer getMailQueuesByStatusAndSendTimeCount() throws DataAccessException;
	
	/**
	 * 发送成功改变状态
	 * @param mailQueueId
	 * @throws DataAccessException
	 */
	public void updateMailQueueStatusByMailQueueId(@Param("mailQueueId")Integer mailQueueId,@Param("status")Short status) throws DataAccessException;

	/**
	 * 根据状态查询邮件队列
	 * @param num  每次读取的条数
	 * @param status
	 * @throws DataAccessException
	 */
	public List<MailQueue> getMailQueuesByStatus(@Param("num")Integer num,@Param("status")Short status) throws DataAccessException;
	
	/**
	 * 定时删除队列表中已发送成功的邮件
	 * @param num
	 * @throws DataAccessException
	 */
	public void deleteMails(Integer num) throws DataAccessException;
	
	/**
	 * 删除已退订还未发送的邮件
	 */
	public void deleteMailsByUnSubscribe(@Param("email") String email, @Param("type") Short type) throws DataAccessException;
	
}
