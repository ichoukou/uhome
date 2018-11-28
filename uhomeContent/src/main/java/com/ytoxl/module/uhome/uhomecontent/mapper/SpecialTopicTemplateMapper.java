package com.ytoxl.module.uhome.uhomecontent.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomecontent.dataobject.SpecialTopicTemplate;

public interface SpecialTopicTemplateMapper extends BaseSqlMapper<SpecialTopicTemplate> {

	public List<SpecialTopicTemplate> searchSpecialTopicTemplate(Map<String,Object> searchParams) throws DataAccessException;
	
	public Integer searchSpecialTopicTemplateCount(Map<String,Object> searchParams) throws DataAccessException;
	
	public Integer searchOverdueSpecialTopicTemplateCount(Integer searchParams) throws DataAccessException;
}
