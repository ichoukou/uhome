package com.ytoxl.module.uhome.uhomebase.service;

import java.util.List;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.MyUrole;
import com.ytoxl.module.uhome.uhomebase.dataobject.MyUser;
import com.ytoxl.module.user.dataobject.Uresource;

public interface AuthorityService {
	/**
	 * 根据创建人Id查询创建的角色
	 * @param userId
	 * @return
	 * @throws UhomeStoreException
	 */
	List<MyUrole> searchUroleByCreateUserId(Integer userId) throws UhomeStoreException;	
	/**
	 * 根据角色id查询所拥有资源信息
	 * @param uroleId
	 * @return
	 * @throws UhomeStoreException
	 */
	List<Uresource> getUresourcesByUroleId(Integer uroleId) throws UhomeStoreException;	
	/**
	 * 保存或更新角色及对应资源信息
	 * @param uroleId
	 * @param listUresource
	 * @throws UhomeStoreException
	 */
	void saveOrUpdateUroleAndUresource(Integer uroleId,String uroleName,Integer createUserId,List<Integer> uresourceIds) throws UhomeStoreException;
	/**
	 * 根据创建人ID查询创建的用户
	 * @param userId
	 * @return
	 * @throws UhomeStoreException
	 */
	List<MyUser> listUserBycreateUserId(Integer userId)
			throws UhomeStoreException;
	/**
	 * 根据uroleid查询urole信息
	 * @param uroleId
	 * @return
	 * @throws UhomeStoreException
	 */
	MyUrole getUroleByUroleId(Integer uroleId) throws UhomeStoreException;
	/**
	 * 根据userid查询user信息
	 * @param userId
	 * @return
	 * @throws UhomeStoreException
	 */
	MyUser getMyUserByUserId(Integer userId) throws UhomeStoreException;
	/**
	 * 更新用户角色
	 * @param myUser
	 * @throws UhomeStoreException
	 */
	void addUserUroles(MyUser myUser) throws UhomeStoreException;
	/**
	 * 根据uroleId删除角色
	 * @param uroleId
	 * @throws UhomeStoreException
	 */
	void deleteUrole(Integer uroleId) throws UhomeStoreException;
	/**
	 * 更新用户状态
	 * @param myUser
	 * @throws UhomeStoreException
	 */
	void updateStatusByUserId(MyUser myUser) throws UhomeStoreException;
	/**
	 * 删除子账户
	 * @param userId
	 * @throws UhomeStoreException
	 */
	void deleteUser(Integer userId) throws UhomeStoreException;
	
	
}
