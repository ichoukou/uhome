package com.ytoxl.module.uhome.uhomecontent.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomecontent.dataobject.Suggest;

public interface SuggestMapper <T extends Suggest> extends BaseSqlMapper<T> {
	/**
	 * 分页查询建议
	 * @param map
	 * @return
	 */
	public List<Suggest> searchSuggests(Map<String, Object> searchParams) throws DataAccessException;
	public Integer searchSuggestsCount(Map<String, Object> searchParams) throws DataAccessException;
	
}
