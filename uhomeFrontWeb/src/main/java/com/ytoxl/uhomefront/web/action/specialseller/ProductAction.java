package com.ytoxl.uhomefront.web.action.specialseller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.uhome.uhomebase.common.CommonConstants;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Postage;
import com.ytoxl.module.uhome.uhomebase.dataobject.Product;
import com.ytoxl.module.uhome.uhomebase.service.ProductCategoryService;
import com.ytoxl.module.uhome.uhomebase.service.ProductService;
import com.ytoxl.module.uhome.uhomecontent.dataobject.HelpCategory;
import com.ytoxl.module.uhome.uhomeorder.service.PostageCalService;
import com.ytoxl.uhomefront.web.action.BaseAction;

/**
 * @author wangguoqing
 *
 */
@SuppressWarnings("serial")
public class ProductAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(ProductAction.class);
	
	private static String PRODUCTDETAIL = "productDetail";
	private static String RECOMMEND = "recommend";
	
	@Autowired
	private ProductService productService;
	@Autowired
	private PostageCalService postageCalService;
	//页面上显示的商品信息
	private Product product;
	//用户的浏览记录
	private List<Product> products;
	
	//帮助
	private Map<String,List<HelpCategory>> helpCategoryMaps;
	
	//目录
	@Autowired
	private ProductCategoryService productCategoryService;
	
	private String[] linkArr;
	
	public String productDetail(){
		HttpServletRequest request = ServletActionContext.getRequest();
    	HttpServletResponse response = ServletActionContext.getResponse();
		try {
			Integer productId = product.getProductId(); // 保存传过来的参数副本
			product = productService.getProductByProductId4Front(product);
			//TODO 如果product 为空 说明此商品有可能下架了  再次查询
			if(product == null){
				product = productService.getOutOfStockProductByProductId(productId);
				if(product == null){
					return "error";
				}
			}
			//helpCategoryMaps = helpService.getHelp4ProductDetail();
			//读取cookies用户的浏览记录  并向浏览器添加用户的浏览记录
			products = productService.getUserProductHistory(request, response, product.getProductId());
			//商品pv+1
			productService.updateProductHits(product);
			//邮费
			Postage postage = new Postage();
			postage.setPostage(postageCalService.getPostage(product.getProductId(),CommonConstants.POSTAGE_PLAN));
			//Postage postage = productService.getProductPostageByProductId(product);
			product.setPostage(postage);
			//设置seo信息
			super.setSeoConfigByUrlKey(request.getServletPath(), product);
			//面包屑
			linkArr = productCategoryService.getProductCategoryLink(product.getProductCategoryId());
		} catch (UhomeStoreException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return PRODUCTDETAIL;
	}
	
	/**
	 * 购物后推荐页面action 
	 * @return
	 */
	public String recommend(){
		//TODO 获取productCategoryId
		if(null != product){
			Integer productCategoryId = product.getProductCategoryId();
			try {
				if(null != productCategoryId){
					products = productService.getRecommendByProductCategoryId(productCategoryId);
				}
			} catch (UhomeStoreException e) {
				logger.error("查询推荐商品出错:"+e.getMessage());
			}
		}
		return RECOMMEND;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Map<String, List<HelpCategory>> getHelpCategoryMaps() {
		return helpCategoryMaps;
	}

	public void setHelpCategoryMaps(Map<String, List<HelpCategory>> helpCategoryMaps) {
		this.helpCategoryMaps = helpCategoryMaps;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public String[] getLinkArr() {
		return linkArr;
	}

	public void setLinkArr(String[] linkArr) {
		this.linkArr = linkArr;
	}

}
