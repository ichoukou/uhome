package com.ytoxl.uhomefront.web.action.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Brand;
import com.ytoxl.module.uhome.uhomebase.dataobject.Product;
import com.ytoxl.module.uhome.uhomebase.service.BrandService;
import com.ytoxl.module.uhome.uhomebase.service.ProductService;
import com.ytoxl.uhomefront.web.action.BaseAction;

/**
 * @author wangguoqing
 *
 */

@SuppressWarnings("serial")
public class SearchAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(SearchAction.class);
	
	private static String SEARCHPRODCUT = "searchProduct";
	
	@Autowired
	private ProductService productService;
	@Autowired
	private BrandService brandService;
	
	//搜索条件  分页
	private BasePagination<Product> productPage;
	
	//url优化后用的
	private String keyWord;
	
	// 热卖品牌
	private List<Brand> hotBrands;
	
	//搜索品牌分类
	private List<Brand> brandCategorys;
	
	/**
	 * 跟商品的关键词搜索
	 * @return
	 */
	public String searchProduct(){
		try {
			//如果productPage为空
			if(productPage == null){
				productPage = new BasePagination<Product>();
			}
			//设置要所有的关键词
			Map<String,String> map = productPage.getParams();
			if(map == null){
				map = new HashMap<String,String>();
			}
			map.put("keyWord", keyWord);
			productPage.setParams(map);
			productService.searchProducts4Front(productPage);
			hotBrands = brandService.listHotBrands();
			
			//在没有点击单个的品牌情况查询 TODO
			brandCategorys = productService.getBrandsSearchAndImport(Product.PAGE_TYPE_SEARCH,keyWord);
			//将url中的排序字段和desc转化成数字
			String sort = productPage.getSort();
			String dir = productPage.getDir();
			if("asc".equals(dir)){
				productPage.setDir("100000");
			}else if("desc".equals(dir)){
				productPage.setDir("100001");
			}
			for(Entry<String,String> entry:Product.SORTSMAP.entrySet()){
				if(entry.getValue().equals(sort)){
					productPage.setSort(entry.getKey());
				}
			}
			
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return SEARCHPRODCUT;
	}

	public BasePagination<Product> getProductPage() {
		return productPage;
	}

	public void setProductPage(BasePagination<Product> productPage) {
		this.productPage = productPage;
	}

	public List<Brand> getHotBrands() {
		return hotBrands;
	}

	public void setHotBrands(List<Brand> hotBrands) {
		this.hotBrands = hotBrands;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public List<Brand> getBrandCategorys() {
		return brandCategorys;
	}

	public void setBrandCategorys(List<Brand> brandCategorys) {
		this.brandCategorys = brandCategorys;
	}
	
}
