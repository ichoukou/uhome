package com.ytoxl.uhomefront.web.action.content;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Brand;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductCategory;
import com.ytoxl.module.uhome.uhomebase.service.BrandService;
import com.ytoxl.module.uhome.uhomebase.service.ProductCategoryService;
import com.ytoxl.uhomefront.web.action.BaseAction;
import com.ytoxl.uhomefront.web.action.brand.BrandAction;

public class SiteMapAction extends BaseAction{

	private static final long serialVersionUID = 6475778701348323567L;
	private static Logger logger = LoggerFactory.getLogger(SiteMapAction.class);
	//网站地图
	private static String SITEMAP = "siteMap";
	//品牌集中营
	private List<Brand> brands;
	
	//所有子目录
	List<ProductCategory> productCategorys;
	
	@Autowired
	private BrandService brandService;

	public String siteMap(){
		//获取所有目录 
		try {
		brands = brandService.listBrands();
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return SITEMAP;
	}

	public List<Brand> getBrands() {
		return brands;
	}

	public void setBrands(List<Brand> brands) {
		this.brands = brands;
	}

	public List<ProductCategory> getProductCategorys() {
		return productCategorys;
	}

	public void setProductCategorys(List<ProductCategory> productCategorys) {
		this.productCategorys = productCategorys;
	}
	
}
