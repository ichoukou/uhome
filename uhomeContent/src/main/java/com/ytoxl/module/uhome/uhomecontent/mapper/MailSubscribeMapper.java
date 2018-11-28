package com.ytoxl.module.uhome.uhomecontent.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomecontent.dataobject.MailSubscribe;
/**
 * 邮件订阅
 * @author guoxinzhi
 *
 * @param <T>
 */
public interface MailSubscribeMapper <T extends MailSubscribe> extends BaseSqlMapper<T> {
	
	/**
	 * 查询用户订阅的品牌
	 * @param userId
	 * @return
	 */
	public List<MailSubscribe> searchMailSubBrands(Map<String, Object> searchParams) throws DataAccessException;
	public Integer searchMailSubBrandsCount(Map<String, Object> searchParams) throws DataAccessException;
	
	/**
	 * 更新订阅状态
	 * @param mailSubscribe
	 * @return
	 */
	public Integer updateMailSubscribeStatus(MailSubscribe mailSubscribe) throws DataAccessException;
	
	/**
	 * 获取所有已经订阅的mailsubscribe
	 * @return
	 * @throws DataAccessException
	 */
	public List<MailSubscribe> getAllMailSubscribeByStatus(Integer status) throws DataAccessException;
	
	/**
	 * 根据类型查找所有的已经订阅的
	 * @param type
	 * @return
	 * @throws DataAccessException
	 */
	public List<MailSubscribe> listMailSubscribeByType(Short type) throws DataAccessException;
	
	/**
	 * 通过email,brandId,type,status查询
	 * @param email
	 * @param type
	 * @param brandId
	 * @return
	 * @throws DataAccessException
	 */
	public List<MailSubscribe> listMailSubscribeByEmailAndBrandIdAndType(@Param("email")String email,@Param("type")Short type,@Param("brandId")Integer brandId) throws DataAccessException;
	/**
	 * 通过email,planid,type,status查询
	 * @param email
	 * @param type
	 * @param planid
	 * @return
	 * @throws DataAccessException
	 */
	public List<MailSubscribe> listMailSubscribeByEmailAndPlanIdAndType(@Param("email")String email,@Param("type")Short type,@Param("planId")Integer planId) throws DataAccessException;
	/**
	 * 通过email，type,status查询，判断是否重复
	 * @param email
	 * @param type
	 * @return
	 * @throws DataAccessException
	 */
	public List<MailSubscribe> listMailSubscribeByEmailAndType(@Param("email")String email,@Param("type")Short type) throws DataAccessException;
	
	public List<MailSubscribe> searchMailSubscribe(@Param("type") Short type, @Param("start") Integer start, @Param("limit") Integer limit) throws DataAccessException;
	
	public Integer searchMailSubscribeCount(@Param("type") Short type) throws DataAccessException;
	
	/**
	 * 更新邮件订阅状态为取消
	 * @param mail
	 * @param type
	 */
	public Integer updateMailSubscribeStatus2Cancel(@Param("email") String email, @Param("type") Short type) throws DataAccessException;
	
	/**
	 * 根据邮箱和类型查询订阅数量
	 * @param email
	 * @param type
	 * @return
	 */
	public Integer listCountByEmailAndTypeAndStatus(@Param("email") String email, @Param("type") Short type, @Param("status") Short status) throws DataAccessException;
	
	public Integer listCountByEmailAndType(@Param("email") String email, @Param("type") Short type) throws DataAccessException;

}
