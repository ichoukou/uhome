package com.ytoxl.module.uhome.uhomebase.mapper;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.ReceiverAddress;

/**
 * @author wangguoqing
 *
 */
public interface ReceiverAddressMapper<T extends ReceiverAddress> extends BaseSqlMapper<T> {
	
	/**
	 * 根据用户id查询该用户所有的收货地址
	 * @param userId
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<ReceiverAddress> getReceiverAddressList(Integer userId) throws DataAccessException;

	/**
	 * 获取该用户默认的地址
	 * @param userId
	 * @return
	 * @throws DataAccessException
	 */
	public ReceiverAddress getDefaultAddress(Integer userId) throws DataAccessException;
	
	/**
	 * 根据用户id查询该用户所有的收货详细信息地址
	 * @param userId
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<ReceiverAddress> getRAddressDetailList(Integer userId) throws DataAccessException;
	
	/**
	 * 根据用户id的查询所有的非默认的收货地址
	 * @param userId
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<ReceiverAddress> getOtherAddress(Integer userId) throws DataAccessException;
	/**
	 * 根据用户receiverAddressId的设置其默认的收货地址
	 * @param receiverAddressId
	 * @return
	 * @throws UhomeStoreException
	 */
	public void updateDefaultAddress(Integer receiverAddressId) throws DataAccessException;
	/**
	 * 根据用户receiverAddressId的设置其默认的收货地址
	 * @param receiverAddressId
	 * @return
	 * @throws UhomeStoreException
	 */
	public void updateNotDefaultAddress(ReceiverAddress receiverAddress) throws DataAccessException;
	
	/**
	 * 根据用户userId的设置其默认的收货地址
	 * @param receiverAddressId
	 * @return
	 * @throws UhomeStoreException
	 */
	public void updatesNotDefaultAddress(Integer userId) throws DataAccessException;
	/**
	 *根据用户的Id 获取总收货地址条数
	 * @param userId
	 * @throws DataAccessException
	 */
	public int myAddress(Integer userId) throws DataAccessException;
	
}
