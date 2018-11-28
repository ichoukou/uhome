package com.ytoxl.module.uhome.uhomecontent.dataobject.tbl;

/**
 * SEO优化
 * @author liyasi
 *
 */

import java.util.Date;

public class SeoConfigTbl {
	protected Integer seoConfigId;
	protected String title;
	protected String keyWords;
	protected String description;
	protected String urlKey;
	protected Date creatTime;
	protected String urlMean;
	
	public Integer getSeoConfigId() {
		return seoConfigId;
	}
	public void setSeoConfigId(Integer seoConfigId) {
		this.seoConfigId = seoConfigId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrlKey() {
		return urlKey;
	}
	public void setUrlKey(String urlKey) {
		this.urlKey = urlKey;
	}
	public Date getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}
	public String getUrlMean() {
		return urlMean;
	}
	public void setUrlMean(String urlMean) {
		this.urlMean = urlMean;
	}
	
	
}
