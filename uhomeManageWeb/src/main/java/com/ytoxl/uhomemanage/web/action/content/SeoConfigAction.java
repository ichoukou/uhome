package com.ytoxl.uhomemanage.web.action.content;


import java.util.List;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.dataobject.SeoConfig;
import com.ytoxl.module.uhome.uhomecontent.service.SeoConfigService;
import com.ytoxl.uhomemanage.web.action.BaseAction;

public class SeoConfigAction extends BaseAction{

	private static final long serialVersionUID = 191262624010442706L;
	private static Logger logger = LoggerFactory.getLogger(SeoConfigAction.class);
	@Autowired
	private SeoConfigService seoConfigService;

	private BasePagination<SeoConfig> seoConfigPage;
	private List<SeoConfig> seoConfigList;
	private SeoConfig seoConfig;
	private String nextAction;
	private String urlKey;
	
	/**
	 * 选中记录的ID
	 */
	private Integer seoConfigId;
	/**
	 * 查询SEO
	 * @return
	 */
	public String listSeoConfigs(){
		/*try {
			seoConfigList = seoConfigService.listSeoConfigs();
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		*/
		if (seoConfigPage == null) {
			seoConfigPage=new BasePagination<SeoConfig>();
		}
		try {
			seoConfigService.searchSeoConfigs(seoConfigPage);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return "listSeoConfigs";
	}
	
	/**
	 * 根据ID删除记录
	 * @return
	 */
	public String deleteSeoConfig(){
		try {
			seoConfigService.deleteSeoConfig(seoConfigId);
		} catch (UhomeStoreException e) {
			setMessage(e.getMessage());
			logger.error("===deleteSeoConfig()===删除SEO记录异常！SEOID：{}",seoConfigId);
		}
		return JSONMSG;
	}
	/**
	 * 添加更新SEO记录
	 * @return
	 */
	public String saveSeoConfig(){
		try {
			if(seoConfig!=null && seoConfig.getSeoConfigId()!=null){
				seoConfigService.updateSeoConfig(this.seoConfig);
			}else{
				seoConfigService.addSeoConfig(this.seoConfig);
			}
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		this.setNextAction("seo-listSeoConfigs.htm");
		return "saveSeoConfig";
	}
	
	/**
	 * 通过ID获得一条记录
	 * @return
	 */
	public String getSeoconfigById(){
		try {
			if(seoConfig!=null && seoConfig.getSeoConfigId()!=null){
				seoConfig=seoConfigService.getSeoConfigBySeoConfigId(seoConfig.getSeoConfigId());
			}
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return "getSeoconfigById";
	}
	
	/**
	 * 通过ID获得一条记录
	 * @return
	 */
	public String getSeoconfigByIdAjax(){
		try {
			if(seoConfig!=null && seoConfig.getSeoConfigId()!=null){
				seoConfig=seoConfigService.getSeoConfigBySeoConfigId(seoConfig.getSeoConfigId());
			}
			String json = JSONObject.fromObject(seoConfig).toString();
			setMessage("true", json);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}
	
	/**
	 * 验证urlKey是否重复
	 * @return
	 */
	public String repeatUrlKey(){
		try {
			seoConfig = seoConfigService.repeatUrlKey(seoConfig.getUrlKey());
			if(seoConfig!=null){
			// setMessage(seoConfig.getUrlKey());//urlKey重复
				String json = JSONObject.fromObject(seoConfig).toString();
				setMessage("true", json);
		}else{
			seoConfig=new SeoConfig();
			String json = JSONObject.fromObject(seoConfig).toString();
			setMessage("true", json);
		}
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}
	
	public List<SeoConfig> getSeoConfigList() {
		return seoConfigList;
	}


	public void setSeoConfigList(List<SeoConfig> seoConfigList) {
		this.seoConfigList = seoConfigList;
	}


	public BasePagination<SeoConfig> getSeoConfigPage() {
		return seoConfigPage;
	}


	public void setSeoConfigPage(BasePagination<SeoConfig> seoConfigPage) {
		this.seoConfigPage = seoConfigPage;
	}


	public SeoConfig getSeoConfig() {
		return seoConfig;
	}


	public void setSeoConfig(SeoConfig seoConfig) {
		this.seoConfig = seoConfig;
	}

	public Integer getSeoConfigId() {
		return seoConfigId;
	}

	public void setSeoConfigId(Integer seoConfigId) {
		this.seoConfigId = seoConfigId;
	}

	public String getNextAction() {
		return nextAction;
	}

	public void setNextAction(String nextAction) {
		this.nextAction = nextAction;
	}

	public String getUrlKey() {
		return urlKey;
	}

	public void setUrlKey(String urlKey) {
		this.urlKey = urlKey;
	}

	
}
