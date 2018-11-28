package com.ytoxl.uhomemanage.web.action.brand;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.core.common.utils.StringUtils;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Brand;
import com.ytoxl.module.uhome.uhomebase.dataobject.Plan;
import com.ytoxl.module.uhome.uhomebase.dataobject.Product;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductCategory;
import com.ytoxl.module.uhome.uhomebase.dataobject.Seller;
import com.ytoxl.module.uhome.uhomebase.service.BrandService;
import com.ytoxl.module.uhome.uhomebase.service.ProductService;
import com.ytoxl.uhomemanage.web.action.BaseAction;

public class BrandAction extends BaseAction  {
	private static final long serialVersionUID = 1563032240216719019L;
	
	private static Logger logger = LoggerFactory.getLogger(BrandAction.class);
	private static final String LIST_BRANDS = "listBrands";
	private static final String ALTER_BRAND = "alterBrand";
	private static final String LIST_USERBRANDS = "listUserBrands";
	
	@Autowired
	private BrandService brandService;
	@Autowired
	private ProductService productService;
	
	private String nextAction;
	private BasePagination<Brand> pagebrands;
	private List<Brand> brands;
	private List<ProductCategory> productCategory;
	private List<Brand> brandList;
	private String opert;
	private Brand brand;
	private Seller seller;
	
	public String listBrands(){
		try {
			this.brands=brandService.listBrandsByBrandPinYin(this.getBrand());
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return LIST_BRANDS;
	}
	//管理员品牌列表
	public String listUserBrans(){
		SetlistProduct();
		try {
			if(pagebrands==null){
				pagebrands =new BasePagination<Brand>();
			}
			brandService.searchBrands(pagebrands);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return LIST_USERBRANDS;
	}
	//产品类别	
	public void SetlistProduct(){
		try {
			productCategory = productService.listProductCategories();
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
	}
	//根据id 获取单个品牌
	public  String singleBrand(){
		SetlistProduct();
		if(brand!=null){
		try {
				brand = brandService.getBrandByBrandId(brand.getBrandId());
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		}
		return ALTER_BRAND;
	}
	//获取品牌的首字符
	public String getBrandPinYinByName(){
		try {
			String brandPinYin = brandService.getPinyinName(brand.getBrandPinYin());
			setMessage("pinyin", brandPinYin);//添加或修改成功
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		} 
		return JSONMSG;
	}
	//修改或添加单个品牌
	public String  singleBrandSset(){
		if(opert!=null){
			if(opert.equals("edit")){
				try {
					if(!checkBrand(brand)){
						brandService.updateBrandByBrandId(brand);
						setMessage(Boolean.TRUE.toString(), "2");//添加或修改成功
					}else{
						setMessage(Boolean.TRUE.toString(), "1");//品牌重复
					}
				} catch (UhomeStoreException e) {
					logger.error(e.getMessage());
				}
			}else{
				try {
					if(!checkBrand(brand)){
						brandService.addBrand(brand);
						setMessage(Boolean.TRUE.toString(), "2");//添加或修改成功
					}else{
						setMessage(Boolean.TRUE.toString(), "1");//品牌重复
					}
				} catch (UhomeStoreException e) {
					logger.error(e.getMessage());
				}
			}
		}
		return JSONMSG;
	}
	
	public boolean checkBrand(Brand brand){
		boolean flag =false;
		try {
			Brand brandZ = new Brand();
			brandZ.setBrandId(brand.getBrandId());
			brandZ.setName(brand.getName());
			brandZ.setEnglishName("");
			Brand brandE = new Brand();
			brandE.setBrandId(brand.getBrandId());
			brandE.setName("");
			brandE.setEnglishName(brand.getEnglishName());
			if(StringUtils.isNotEmpty(brand.getName()) && StringUtils.isNotEmpty(brand.getEnglishName()) ){
				brandZ = brandService.searchBrandByName(brandZ);
				brandE = brandService.searchBrandByName(brandE);
				if(brandZ != null || brandE != null) flag = true;
			}else{
				brand = brandService.searchBrandByName(brand);
				if(brand !=null) flag = true;
			}
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return flag;
	}
	//品牌禁用
	public String  forbiddenBrand(){
		List<Product> listProduct = null;
		List<Plan> listPlan = null;
		try {
			listPlan=brandService.listPlansByBrandIdAndEndTime(brand.getBrandId());
			if(listPlan.size()>0){
				setMessage(Boolean.TRUE.toString(), "该品牌还存在排期");//品牌还存在排期
			}else{
				listProduct = brandService.listProductsByBrandId(brand.getBrandId());
				if(listProduct.size()>0){
					setMessage(Boolean.TRUE.toString(), "请先删除该品牌下的商品");//品牌还存在商品
				}else{
					brandService.updateIsForbbdenByBrandId(brand.getBrandId(),brand.getIsForbidden());
					if(brand.getIsForbidden()==1){
						setMessage(Boolean.TRUE.toString(), "该品牌已禁用");//品牌还存在商品
					}else{
						setMessage(Boolean.TRUE.toString(), "该品牌已激活");//品牌还存在商品
					}
				}
			}
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return JSONMSG;
	}

	public String getOpert() {
		return opert;
	}
	public void setOpert(String opert) {
		this.opert = opert;
	}
	public List<Brand> getBrandList() {
		return brandList;
	}

	public void setBrandList(List<Brand> brandList) {
		this.brandList = brandList;
	}
	public BasePagination<Brand> getPagebrands() {
		return pagebrands;
	}

	public void setPagebrands(BasePagination<Brand> pagebrands) {
		this.pagebrands = pagebrands;
	}

	public List<ProductCategory> getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(List<ProductCategory> productCategory) {
		this.productCategory = productCategory;
	}

	public List<Brand> getBrands() {
		return brands;
	}
	
	public void setBrands(List<Brand> brands) {
		this.brands = brands;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public String getNextAction() {
		return nextAction;
	}

	public void setNextAction(String nextAction) {
		this.nextAction = nextAction;
	}

	public BrandService getBrandService() {
		return brandService;
	}

	public void setBrandService(BrandService brandService) {
		this.brandService = brandService;
	}
	

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}
}
