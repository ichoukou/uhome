package com.ytoxl.module.uhome.uhomebase.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.user.dataobject.Uresource;

public interface MyUresourceMapper<T extends Uresource> extends BaseSqlMapper<T> {
	List<Uresource> getUresourcesByUroleId(Integer uroleId) throws DataAccessException;
	void deleteUroleUresourceByUroleId(Integer uroleId) throws DataAccessException;
	void addUroleUresources(@Param("uroleId")Integer uroleId,@Param("uresourceIds")List<Integer> uresourceIds)throws DataAccessException;
}
