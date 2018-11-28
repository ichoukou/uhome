package com.ytoxl.uhomemanage.web.action.content;


import java.util.List;


import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.dataobject.Links;
import com.ytoxl.module.uhome.uhomecontent.service.LinksService;
import com.ytoxl.uhomemanage.web.action.BaseAction;

public class LinksAction extends BaseAction{
	
	private static final long serialVersionUID = -710695379756662711L;
	private static Logger logger = LoggerFactory.getLogger(LinksAction.class);
	@Autowired
	private LinksService linksService;

	private BasePagination<Links> linksPage;
	private List<Links> linksList;
	private Links links;
	private String nextAction;
	private String name;
	/**
	 * 选中记录的ID
	 */
	private Integer linkId;
	/**
	 * 查询所有链接
	 * @return
	 */
	public String searchLinks(){
		/*if (linksPage == null) {
			linksPage=new BasePagination<Links>();
		}
		try {
			linksService.searchLinks(linksPage);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}*/
		try {
			linksList = linksService.listLinks();
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return "searchLinks";
	}
	
	/**
	 * 根据ID删除链接
	 * @return
	 */
	public String deleteLinks(){
		try {
			linksService.deleteLinks(linkId);
		} catch (UhomeStoreException e) {
			setMessage(e.getMessage());
			logger.error("===deleteSeoConfig()===删除链接异常！LinkID：{}",linkId);
		}
		return JSONMSG;
	}
	/**
	 * 添加更新链接
	 * @return
	 */
	public String saveLinks(){
		try {
			if(links!=null && links.getLinkId()!=null){
				linksService.updateLinks(links);
			}else{
				linksService.addLinks(links);
			}
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		this.setNextAction("link-searchLinks.htm");
		return "saveLinks";
	}
	
	/**
	 * 通过ID获得一条链接记录
	 * @return
	 */
	public String getLinksByLinkIdAjax(){
		try {
			if(links!=null && links.getLinkId()!=null){
				links=linksService.getLinksByLinkId(links.getLinkId());
			}
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}

	/**
	 * 验证网站名称是否重复
	 * @return
	 */
	public String repeatName(){
		try {
			links = linksService.getLinksByName(links.getName());
			if(links==null){
				links = new Links();
				String json = JSONObject.fromObject(links).toString();
				setMessage("true", json);
			}
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}

	public BasePagination<Links> getLinksPage() {
		return linksPage;
	}

	public void setLinksPage(BasePagination<Links> linksPage) {
		this.linksPage = linksPage;
	}

	public List<Links> getLinksList() {
		return linksList;
	}

	public void setLinksList(List<Links> linksList) {
		this.linksList = linksList;
	}

	public Links getLinks() {
		return links;
	}

	public void setLinks(Links links) {
		this.links = links;
	}

	public Integer getLinkId() {
		return linkId;
	}

	public void setLinkId(Integer linkId) {
		this.linkId = linkId;
	}

	public String getNextAction() {
		return nextAction;
	}

	public void setNextAction(String nextAction) {
		this.nextAction = nextAction;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	
}
