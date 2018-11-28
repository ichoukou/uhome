package com.ytoxl.module.uhome.uhomecontent.service;

import java.util.List;
import java.util.Map;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.dataobject.Help;
import com.ytoxl.module.uhome.uhomecontent.dataobject.HelpCategory;

public interface HelpService {
	/**
	 * 查询帮助列表
	 * @param suggestPage
	 * @throws UhomeStoreException
	 */
	public List<HelpCategory> listHelpCategorys() throws UhomeStoreException;
	
	/**
	 * 更新帮助内容
	 * @param id
	 * @throws UhomeStoreException
	 */
	public void updateContentById(Help help) throws UhomeStoreException;
	
	/**
	 * 查询帮助内容
	 * @param id
	 * @throws UhomeStoreException
	 */
	public Help getContentByHelpId(Integer helpId) throws UhomeStoreException;
	
	/**
	 * 商品详情页面  在帮助菜单中抓取 内容
	 * @return
	 * @throws UhomeStoreException
	 */
	public Map<String,List<HelpCategory>> getHelp4ProductDetail() throws UhomeStoreException;
}
