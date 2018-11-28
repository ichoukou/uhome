package com.ytoxl.module.uhome.uhomecontent.service.impl;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.dataobject.Suggest;
import com.ytoxl.module.uhome.uhomecontent.mapper.SuggestMapper;
import com.ytoxl.module.uhome.uhomecontent.service.SuggestService;
@Service
public class SuggestServiceImpl implements SuggestService {
	@Autowired
	private SuggestMapper<Suggest> suggestMapper;
	
	@Override
	public void addSuggest(Suggest suggest) throws UhomeStoreException {
		suggest.setStatus(Suggest.STATUS_UNREAD);
		suggestMapper.add(suggest);
	}
	
	/**
	 * 分页查询建议
	 * @param suggestPage
	 * @throws UhomeStoreException
	 */
	@Override
	public void searchSuggests(BasePagination<Suggest> suggestPage)
			throws UhomeStoreException {
		Map<String, Object> searchParams = suggestPage.getSearchParamsMap();
		if (suggestPage.isNeedSetTotal()) {
			Integer total = suggestMapper.searchSuggestsCount(searchParams);
			suggestPage.setTotal(total);
		}
		Collection<Suggest> result = suggestMapper.searchSuggests(searchParams);
		suggestPage.setResult(result);
	}
	
	/**
	 * 删除建议
	 */
	@Override
	public void deleteSuggestById(Integer id) throws UhomeStoreException {
		// TODO Auto-generated method stub
		suggestMapper.del(id);
	}
	
	/**
	 * 更新建议状态
	 * @param id
	 * @throws UhomeStoreException
	 */
	@Override
	public void updateSuggestStatusById(Suggest suggest) throws UhomeStoreException {
		// TODO Auto-generated method stub
		suggestMapper.update(suggest);
	}
}
