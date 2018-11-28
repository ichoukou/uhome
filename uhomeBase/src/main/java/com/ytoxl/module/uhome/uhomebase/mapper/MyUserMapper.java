package com.ytoxl.module.uhome.uhomebase.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomebase.dataobject.MyUser;

public interface MyUserMapper<T extends MyUser> extends BaseSqlMapper<T> {
	List<MyUser> listUserBycreateUserId(Integer createUserId) throws DataAccessException;
	void addUserUroles(@Param("uroleId")Integer uroleId,@Param("userId")Integer userId)throws DataAccessException;
	void delUserUroles(Integer userId) throws DataAccessException;
	void updateStatusByUserId(MyUser myUser) throws DataAccessException;
}
