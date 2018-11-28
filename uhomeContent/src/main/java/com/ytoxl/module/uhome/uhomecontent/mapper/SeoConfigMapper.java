package com.ytoxl.module.uhome.uhomecontent.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.dataobject.SeoConfig;


public interface SeoConfigMapper<T extends SeoConfig> extends BaseSqlMapper<T> {
	
	
	/**
	 * 查询SEO所有记录
	 * @return
	 * @throws DataAccessException
	 */
	public List<SeoConfig> listSeoConfigs() throws DataAccessException;
	
	/**
	 * 分页查询SEO
	 * @param sellerPage
	 * @throws UhomeStoreException
	 */
	public List<SeoConfig> searchSeoConfigs(Map<String, Object> seoConfigPage) throws DataAccessException;
	public Integer searchSeoConfigCount(Map<String, Object> sellerPage) throws DataAccessException;
	/**
	 * 根据urlkey查询seoconfig
	 * @return
	 * @throws DataAccessException
	 */
	public SeoConfig getSeoConfigByUrlKey(String urlKey) throws DataAccessException; 
	
}