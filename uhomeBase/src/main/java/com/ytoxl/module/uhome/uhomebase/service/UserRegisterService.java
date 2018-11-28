package com.ytoxl.module.uhome.uhomebase.service;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.user.dataobject.User;

public interface UserRegisterService {
	
	/**
	 * 根据
	 * @param user 添加用户
	 * @return
	 * @throws UhomeStoreException
	 */
	
	public void addRegisterUser(User user)throws UhomeStoreException;
}
