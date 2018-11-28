package com.ytoxl.module.uhome.uhomecontent.mapper;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomecontent.dataobject.SpecialtopicAdvPosition;

public interface SpecialtopicAdvPositionMapper<T extends SpecialtopicAdvPosition> extends BaseSqlMapper<T> {

	/**
	 * 查询广告位列表
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	public List<SpecialtopicAdvPosition> listAdvPositions() throws DataAccessException;
	
	/**
	 * 根据专题模板ID，查询还未使用的广告位
	 * 
	 * @param templateId
	 * @return
	 * @throws DataAccessException
	 */
	public List<SpecialtopicAdvPosition> listAdvPositionsByTemplateId(Integer templateId) throws DataAccessException;
}
