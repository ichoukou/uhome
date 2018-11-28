package com.ytoxl.module.uhome.uhomecontent.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.dataobject.Links;


public interface LinksMapper<T extends Links> extends BaseSqlMapper<T> {
	
	
	/**
	 * 查询所有链接
	 * @return
	 * @throws DataAccessException
	 */
	public List<Links> listLinks() throws DataAccessException;
	
	/**
	 * 分页查询所有链接
	 * @param linksPage
	 * @throws UhomeStoreException
	 */
	public List<Links> searchLinks(Map<String, Object> linksPage) throws DataAccessException;
	public Integer searchLinksCount(Map<String, Object> linksPage) throws DataAccessException;
	
	/**
	 * 根据网站名称查询数据
	 * @param name
	 * @return
	 * @throws DataAccessException
	 */
	public Links getLinksByName(String name)throws DataAccessException;
}