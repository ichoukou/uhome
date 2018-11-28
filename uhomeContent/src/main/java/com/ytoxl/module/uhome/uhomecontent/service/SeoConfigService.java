package com.ytoxl.module.uhome.uhomecontent.service;

import java.util.List;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.dataobject.SeoConfig;

/**
 * SEO
 * @author liysi
 *
 */
public interface SeoConfigService {
	/**
	 * 根据urlkey查询seo模板信息
	 * @param urlKey
	 * @param data
	 * @return
	 * @throws UhomeStoreException
	 */
	public String[] getSeoConfigByUrlKey(String urlKey,Object data) throws UhomeStoreException;
	
	/**
	 * 判断urlKey是否重复
	 * @param urlKey
	 * @return boolean true :非重复 false:重复
	 * @throws UhomeStoreException
	 */
	public SeoConfig repeatUrlKey(String urlKey)throws UhomeStoreException;
		

	/**
	 * 查询所有的SEO记录
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<SeoConfig> listSeoConfigs()throws UhomeStoreException;
	
	
	/**
	 * 分页查询SEO
	 * @param seoConfigPage
	 * @throws UhomeStoreException
	 */
	public void searchSeoConfigs(BasePagination<SeoConfig> seoConfigPage) throws UhomeStoreException;
	
	/**
	 * 根据ID删除某条记录
	 * @param seoConfigId
	 * @throws UhomeStoreException
	 */
	public void deleteSeoConfig(Integer seoConfigId)throws UhomeStoreException;
	
	/**
	 * 添加SEO记录
	 * @param seller
	 * @return
	 * @throws UhomeStoreException
	 */
	public Integer addSeoConfig(SeoConfig seoConfig) throws UhomeStoreException;
	
	/**
	 * 更新SEO记录
	 * @param seller
	 * @throws UhomeStoreException
	 */
	public void updateSeoConfig(SeoConfig seoConfig) throws UhomeStoreException;
	
	/**
	 * 根据ID获取一条数据
	 * @param seoConfigId
	 * @return
	 * @throws UhomeStoreException
	 */
	public SeoConfig getSeoConfigBySeoConfigId(Integer seoConfigId)throws UhomeStoreException;
}
