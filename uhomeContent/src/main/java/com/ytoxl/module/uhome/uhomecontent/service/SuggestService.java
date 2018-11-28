package com.ytoxl.module.uhome.uhomecontent.service;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.dataobject.Suggest;

public interface SuggestService {
	public void addSuggest(Suggest suggest) throws UhomeStoreException;
	
	/**
	 * 分页查询建议
	 * @param suggestPage
	 * @throws UhomeStoreException
	 */
	public void searchSuggests(BasePagination<Suggest> suggestPage) throws UhomeStoreException;
	/**
	 * 删除建议
	 */
	public void deleteSuggestById(Integer id)throws UhomeStoreException;
	
	/**
	 * 更新建议状态
	 * @param id
	 * @throws UhomeStoreException
	 */
	public void updateSuggestStatusById(Suggest suggest) throws UhomeStoreException;
}
