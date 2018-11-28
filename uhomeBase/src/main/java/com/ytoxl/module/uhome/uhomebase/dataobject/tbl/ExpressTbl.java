package com.ytoxl.module.uhome.uhomebase.dataobject.tbl;

/**
 * 快递
 * @author wangguoqing
 *
 */
public class ExpressTbl {
	protected Integer expressId;
	protected String expressName;
	protected String websiteUrl;
	protected String companyCode;

	public Integer getExpressId() {
		return expressId;
	}

	public void setExpressId(Integer expressId) {
		this.expressId = expressId;
	}

	public String getExpressName() {
		return expressName;
	}

	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}

	public String getWebsiteUrl() {
		return websiteUrl;
	}

	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

}
