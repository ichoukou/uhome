package com.ytoxl.uhomefront.web.action.content;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.dataobject.Links;
import com.ytoxl.module.uhome.uhomecontent.service.LinksService;
import com.ytoxl.uhomefront.web.action.BaseAction;

public class LinksAction extends BaseAction{
	
	private static final long serialVersionUID = -710695379756662711L;
	private static Logger logger = LoggerFactory.getLogger(LinksAction.class);
	@Autowired
	private LinksService linksService;

	private List<Links> linksList;
	
	/**
	 * 查询链接
	 * @return
	 */
	public String listLinks(){
		try {
			linksList = linksService.listLinks();
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return "listLinks";
	}

	public List<Links> getLinksList() {
		return linksList;
	}

	public void setLinksList(List<Links> linksList) {
		this.linksList = linksList;
	}
	
}
