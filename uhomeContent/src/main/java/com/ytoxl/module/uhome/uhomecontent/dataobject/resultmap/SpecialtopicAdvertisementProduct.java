package com.ytoxl.module.uhome.uhomecontent.dataobject.resultmap;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

/**
 * 专题广告所需产品信息
 * @author xu
 *
 */
public class SpecialtopicAdvertisementProduct {

	private Integer productId; //产品ID
	private String imageUrls;  //产品图片url，多个用逗号分隔
	private String previewImage; //用于广告预览的图片
	
	private String name;	//产品名字
	private BigDecimal salePrice;	//产品销售价
	private Double rebate;	//产品折扣
	private BigDecimal marketPrice;	//产品市场价
	
	public static final String ERROR_IMAGE = "/error.jpg";
	
	/**
	 * 获取商品第一张默认图片，添加相应前后缀可以访问缩略图
	 * @return
	 */
	public String getDefaultImage(){
		String thumbnail = null;
		if(StringUtils.isNotEmpty(imageUrls)){
			String[] thumbnails = imageUrls.split(",");
			thumbnail = thumbnails != null  && thumbnails.length > 0 ? thumbnails[0] : "";
		}
		return thumbnail;
	}
	
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getImageUrls() {
		return imageUrls;
	}
	public void setImageUrls(String imageUrls) {
		this.imageUrls = imageUrls;
	}

	public String getPreviewImage() {
		return previewImage;
	}

	public void setPreviewImage(String previewImage) {
		this.previewImage = previewImage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public Double getRebate() {
		return rebate;
	}

	public void setRebate(Double rebate) {
		this.rebate = rebate;
	}

	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}


	
}
