package com.ytoxl.module.uhome.uhomecontent.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.dataobject.Links;
import com.ytoxl.module.uhome.uhomecontent.mapper.LinksMapper;
import com.ytoxl.module.uhome.uhomecontent.service.LinksService;
@Service
public class LinksServiceImpl implements LinksService{
	@Autowired
	private LinksMapper<Links> linksMapper;

	@Override
	public List<Links> listLinks() throws UhomeStoreException {
		return linksMapper.listLinks();
	}
	@Override
	public void searchLinks(BasePagination<Links> linksPage)
			throws UhomeStoreException {
		Map<String, Object> searchParams = linksPage.getSearchParamsMap();
		if(linksPage.isNeedSetTotal()){
			Integer total = linksMapper.searchLinksCount(searchParams);
			linksPage.setTotal(total);
		}
		Collection<Links> result = linksMapper.searchLinks(searchParams);
		linksPage.setResult(result);
		
	}
	@Override
	public void deleteLinks(Integer linkId) throws UhomeStoreException {
		linksMapper.del(linkId);
	}
	@Override
	public Integer addLinks(Links links) throws UhomeStoreException {
		return linksMapper.add(links);
	}
	@Override
	public void updateLinks(Links links) throws UhomeStoreException {
		linksMapper.update(links);
	}
	@Override
	public Links getLinksByLinkId(Integer linkId) throws UhomeStoreException {
		return linksMapper.get(linkId);
	}
	@Override
	public Links getLinksByName(String name) throws UhomeStoreException {
		return linksMapper.getLinksByName(name);
	}

	
}
