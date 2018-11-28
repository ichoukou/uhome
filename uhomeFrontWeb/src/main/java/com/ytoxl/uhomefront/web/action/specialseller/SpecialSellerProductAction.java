package com.ytoxl.uhomefront.web.action.specialseller;

import java.util.Map.Entry;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Brand;
import com.ytoxl.module.uhome.uhomebase.dataobject.Product;
import com.ytoxl.module.uhome.uhomebase.service.PlanService;
import com.ytoxl.module.uhome.uhomebase.service.ProductCategoryService;
import com.ytoxl.uhomefront.web.action.BaseAction;

/**
 * @author wangguoqing
 *
 */
@SuppressWarnings("serial")
public class SpecialSellerProductAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(SpecialSellerProductAction.class);
	
	//返回所有在此排期内的所有商品
	private static String SPECIALSELLERPRODUCT="specialSellerProduct";
	//分页 
	private BasePagination<Product> productPage;
	//最低折扣的商品信息
	private Product minRebateProduct;
	
	@Autowired
	private PlanService planService;

	//目录
	@Autowired
	private ProductCategoryService productCategoryService;
	
	private String[] linkArr;
	//默认跳到品牌特卖列表页面
	public String specialSellerProduct() {
		//如果productPage为空
		if(productPage == null){
			productPage = new BasePagination<Product>();
		}
		try {
			planService.searchProductListByPlanId(productPage);
			minRebateProduct = planService.getMinRebateProductByPlanId(productPage);
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
			Brand brand = null;
			if(productPage.getResult()!=null && productPage.getResult().size()>0){
				brand = productPage.getResult().iterator().next().getBrand();
			}
			super.setSeoConfigByUrlKey(ServletActionContext.getRequest().getServletPath(),brand);
			if(null != minRebateProduct){
				linkArr = productCategoryService.getProductCategoryLink(minRebateProduct.getProductCategoryId());
			}
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return SPECIALSELLERPRODUCT;
	}


	public BasePagination<Product> getProductPage() {
		return productPage;
	}


	public void setProductPage(BasePagination<Product> productPage) {
		this.productPage = productPage;
	}


	public Product getMinRebateProduct() {
		return minRebateProduct;
	}


	public void setMinRebateProduct(Product minRebateProduct) {
		this.minRebateProduct = minRebateProduct;
	}


	public String[] getLinkArr() {
		return linkArr;
	}


	public void setLinkArr(String[] linkArr) {
		this.linkArr = linkArr;
	}
}
