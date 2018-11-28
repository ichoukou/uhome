package com.ytoxl.module.uhome.uhomebase.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.service.UserRegisterService;
import com.ytoxl.module.user.common.utils.Md5EncryptionUtils;
import com.ytoxl.module.user.dataobject.User;
import com.ytoxl.module.user.mapper.UserMapper;
/**
 * 用户
 * @author guoxinzhi
 *
 */
@Service
public class UserRegisterImpl implements UserRegisterService {
	@Autowired
	private UserMapper<User> userMapper;
	@Value("${password_salt}")
    private String passwordSaltAction;	
	@Override
	public void addRegisterUser(User user) throws UhomeStoreException {
		if(user.getCreateByUserId()==null||0==user.getCreateByUserId()){
			user.setPassword(Md5EncryptionUtils.MD5SaltPassword(user.getPassword(), passwordSaltAction));
			user.setCreateByUserId(User.STATUS_UNABLE);//默认被系统创建
			user.setStatus(User.STATUS_ABLE);//默认设置成激活状态
		}
		userMapper.add(user);
	}
	
	

}
