package com.ytoxl.module.uhome.uhomecontent.service;

import java.util.List;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.dataobject.Links;

/**
 * 友情链接
 * @author liysi
 *
 */
public interface LinksService {

	/**
	 * 查询所有的链接
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<Links> listLinks()throws UhomeStoreException;
	
	
	/**
	 * 分页查询所有链接
	 * @param linksPage
	 * @throws UhomeStoreException
	 */
	public void searchLinks(BasePagination<Links> linksPage) throws UhomeStoreException;
	
	/**
	 * 根据ID删除某个链接
	 * @param linkId
	 * @throws UhomeStoreException
	 */
	public void deleteLinks(Integer linkId)throws UhomeStoreException;
	
	/**
	 * 添加链接
	 * @param links
	 * @return
	 * @throws UhomeStoreException
	 */
	public Integer addLinks(Links links) throws UhomeStoreException;
	
	/**
	 * 更新链接记录
	 * @param links
	 * @throws UhomeStoreException
	 */
	public void updateLinks(Links links) throws UhomeStoreException;
	
	/**
	 * 根据ID获取一条数据
	 * @param linkId
	 * @return
	 * @throws UhomeStoreException
	 */
	public Links getLinksByLinkId(Integer linkId)throws UhomeStoreException;
	
	/**
	 * 根据网站名称查询数据
	 * @param name
	 * @return
	 * @throws UhomeStoreException
	 */
	public Links getLinksByName(String name)throws UhomeStoreException;
}
