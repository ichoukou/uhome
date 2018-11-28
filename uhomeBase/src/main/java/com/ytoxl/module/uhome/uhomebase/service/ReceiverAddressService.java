package com.ytoxl.module.uhome.uhomebase.service;

import java.util.List;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.ReceiverAddress;

public interface ReceiverAddressService {
	/**
	 * 根据ID查询地址详细信息
	 * @param userId
	 * @return
	 */
	public List<ReceiverAddress> getRAddressDetailList(Integer userId)throws UhomeStoreException;
	/**
	 * 根据用户ID查询默认地址
	 * @param userId
	 * @return
	 */
	public ReceiverAddress getDefaultAddress(Integer userId)throws UhomeStoreException;
	/**
	 * 根据用户ID查询所有的非默认的地址
	 * @param userId
	 * @return
	 */
	public List<ReceiverAddress> getOtherAddress(Integer userId)throws UhomeStoreException;
	/**
	 * 根据收货地址ID删除对应的地址
	 * @param receiverAddressId
	 * @return
	 */
	public Integer delAddress(Integer id)throws UhomeStoreException;
	/**
	 * 增加一个新的收货地址
	 * 
	 * @param receiverAddress
	 * @return
	 */
	public Integer addAddress(ReceiverAddress receiverAddress)throws UhomeStoreException;
	
	/**
	 * 根据adressId  更新一个收货地址
	 * 
	 * @param receiverAddressId
	 * @return
	 */
	public Integer updateAddress(ReceiverAddress receiverAddress)throws UhomeStoreException;
	
	/**
	 * 根据ReceiverAddress  设置成新的收货地址
	 * 
	 * @param ReceiverAddress
	 * @return
	 */
	public void updateDefualtAddress(ReceiverAddress receiverAddress)throws UhomeStoreException;
	/**
	 * 根据id 查询单独的收货地址
	 * 
	 * @param regionId
	 * @return
	 */
	public ReceiverAddress getAddress(Integer regionId)throws UhomeStoreException;
	/**
	 * 根据userid 设置所有的收货地址
	 * 
	 * @param regionId
	 * @return
	 */
	public void updatesNotDefaultAddress(Integer userId)throws UhomeStoreException;
	/**
	 *根据用户的Id获取用户的收货地址条数
	 * @param userId
	 * @return
	 * @throws UhomeStoreException
	 */
	public int myAddress (Integer userId) throws UhomeStoreException;
	
	/**
	 * 判断地址是不是当前用户的
	 * @return
	 * @throws UhomeStoreException
	 */
	public Boolean checkAddressIsMine(Integer addressId) throws UhomeStoreException;
}
