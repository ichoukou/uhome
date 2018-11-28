package com.ytoxl.uhomeInterface.web.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.service.SeoConfigService;
import com.ytoxl.uhomeInterface.web.bean.Message;

public class BaseAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private Message message;
	private static final Log log = LogFactory.getLog(BaseAction.class);
	public static final String JSONMSG = "jsonMsg";
	
	@Autowired
	private SeoConfigService seoConfigService;
	private String seoTitle;
	private String seoKeyWords;
	private String seoDescription;
	
	public Message getMessage() {
		return message;
	}

	public void setMessage(String code, String info, String[] infoValues) {
		message = new Message(code, info, infoValues);
	}

	public void setMessage(String info, String[] infoValues) {
		setMessage(null, info, infoValues);
	}

	public void setMessage(String code, String info) {
		setMessage(code, info, null);
	}

	public void setMessage(String info) {
		setMessage(null, info, null);
	}
	public String getSeoTitle() {
		return seoTitle;
	}

	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}

	public String getSeoKeyWords() {
		return seoKeyWords;
	}

	public void setSeoKeyWords(String seoKeyWords) {
		this.seoKeyWords = seoKeyWords;
	}

	public String getSeoDescription() {
		return seoDescription;
	}

	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}
	
	public void setSeoConfigByUrlKey(String urlKey,Object product){
		String[] seoInfo = null;
		try{
		seoInfo = seoConfigService.getSeoConfigByUrlKey(urlKey, product);
		}catch(UhomeStoreException e){
			log.error(e.getMessage());
		}
		if(seoInfo!=null){
			seoTitle = seoInfo[0];
			seoKeyWords = seoInfo[1];
			seoDescription = seoInfo[2];
		}
	}
}
