package com.ytoxl.uhomefront.web.action.brand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Brand;
import com.ytoxl.module.uhome.uhomebase.dataobject.Product;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductCategory;
import com.ytoxl.module.uhome.uhomebase.service.BrandService;
import com.ytoxl.uhomefront.web.action.BaseAction;

/**
 * @author wangguoqing
 *
 */
@SuppressWarnings("serial")
public class BrandAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(BrandAction.class);
	private static final String[] str = new String[]{"A","B","C","D","E",
		"F","G","H","I","J","K","L","M","N","O",
		"P","Q","R","S","T","U","V","W","X","Y","Z","0","其它"};
	//品牌详情页面
	private static String BRANDDETAIL = "brandDetail";
	//品牌集中营
	private static String BRANDS = "brands";
	private Map<String,Object> brandMap;
	@Autowired
	private BrandService brandService;
	
	private Brand brand;
	//热卖品牌
	private List<Brand> hotBrands;
	
	//搜索条件  分页
	private BasePagination<Product> productPage;
	//品牌集中营
	private List<Brand> brands;
	
	@Value("${jspvar._domain}")
	private String domain;
	
	//所有子目录
	List<ProductCategory> productCategorys;
	
	//品牌详情页面
	public String brandDetail(){
		try {
			brand = brandService.getBrandByBrandId4Front(brand);
			super.setSeoConfigByUrlKey(ServletActionContext.getRequest().getServletPath(), brand);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return BRANDDETAIL;
	}
	
	//将搜索引擎的以前抓去的连接301
	public void brandDetail301(){
		// 301到新的url
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		StringBuffer newUrl = new StringBuffer(domain);
//		StringBuffer newUrl = new StringBuffer();
		if(null != brand){
			newUrl.append("p/").append(brand.getBrandId()).append("/");
		}	
		response.setHeader("Location",newUrl.toString());
	}
	//品牌集中营
	public String brands(){
		brandMap = new LinkedHashMap<String,Object>();
		try {
			List<Brand> brandsList = brandService.listBrands();
			for(int i=0;i<str.length;i++){
				String mark = str[i];
				List<Brand> brandList = new ArrayList<Brand>();
				for(Brand brand:brandsList){
					String brandPinYin = brand.getBrandPinYin() == null?str[27]:brand.getBrandPinYin().toUpperCase();
					if(brandPinYin.indexOf(str[i]) != -1 && isNum(brandPinYin) == false){
						brandList.add(brand);
					}else if(isNum(brandPinYin) && isNum(str[i])){
						mark = "0-9";
						brandList.add(brand);
					}else if(str[27].equals(mark) && str[27].equals(brandPinYin)){
						brandList.add(brand);  
					}
				}
				brandMap.put(mark, brandList);
			}
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return BRANDS;
	}
	/**
	 * 判断字符串是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNum(String str){
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}
	
//	public String brands(){
//		//TODO
//		try {
//			brands = brandService.listBrands();
//		} catch (UhomeStoreException e) {
//			logger.error(e.getMessage());
//		}
//		return BRANDS;
//	}
	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public List<Brand> getHotBrands() {
		return hotBrands;
	}

	public void setHotBrands(List<Brand> hotBrands) {
		this.hotBrands = hotBrands;
	}

	public BasePagination<Product> getProductPage() {
		return productPage;
	}

	public void setProductPage(BasePagination<Product> productPage) {
		this.productPage = productPage;
	}

	public List<Brand> getBrands() {
		return brands;
	}

	public void setBrands(List<Brand> brands) {
		this.brands = brands;
	}

	public Map<String, Object> getBrandMap() {
		return brandMap;
	}

	public void setBrandMap(Map<String, Object> brandMap) {
		this.brandMap = brandMap;
	}


}
