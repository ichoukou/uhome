package com.ytoxl.module.uhome.uhomebase.mapper;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomebase.dataobject.MyUrole;

public interface MyUroleMapper<T extends MyUrole> extends BaseSqlMapper<T> {
	List<Integer> listUserIdsByUroleId(Integer uroleId) throws DataAccessException;
	List<MyUrole> searchUroleByCreateUserId(Integer createByUserId)throws DataAccessException;
	MyUrole checkUroleName(MyUrole urole)throws DataAccessException;
	MyUrole getUroleByuserId(Integer userId)throws DataAccessException;
}
