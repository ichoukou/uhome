package com.ytoxl.module.uhome.uhomecontent.dataobject;

import com.ytoxl.module.uhome.uhomecontent.dataobject.tbl.SpecialtopicAdvertisementTbl;

public class SpecialtopicAdvertisement extends SpecialtopicAdvertisementTbl {

	private SpecialtopicAdvPosition advPosition; //广告位信息
	private SpecialTopicTemplate topicTemplate;//专题模板信息
	
	private String productNames;	//产品名字，多个以逗号分隔
	private String productSalePrices;	//产品销售价，多个以逗号分隔
	private String productRebates;	//产品折扣，多个以逗号分隔
	private String productMarketPrices;	//产品市场价，多个以逗号分隔
	
	public SpecialtopicAdvPosition getAdvPosition() {
		return advPosition;
	}
	public void setAdvPosition(SpecialtopicAdvPosition advPosition) {
		this.advPosition = advPosition;
	}
	public SpecialTopicTemplate getTopicTemplate() {
		return topicTemplate;
	}
	public void setTopicTemplate(SpecialTopicTemplate topicTemplate) {
		this.topicTemplate = topicTemplate;
	}
	public String getProductNames() {
		return productNames;
	}
	public void setProductNames(String productNames) {
		this.productNames = productNames;
	}
	public String getProductSalePrices() {
		return productSalePrices;
	}
	public void setProductSalePrices(String productSalePrices) {
		this.productSalePrices = productSalePrices;
	}
	public String getProductRebates() {
		return productRebates;
	}
	public void setProductRebates(String productRebates) {
		this.productRebates = productRebates;
	}
	public String getProductMarketPrices() {
		return productMarketPrices;
	}
	public void setProductMarketPrices(String productMarketPrices) {
		this.productMarketPrices = productMarketPrices;
	}
	
	
	
	
}
