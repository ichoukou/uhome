package com.ytoxl.uhomefront.web.action.specialseller;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Brand;
import com.ytoxl.module.uhome.uhomebase.dataobject.Plan;
import com.ytoxl.module.uhome.uhomebase.dataobject.Product;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductCategory;
import com.ytoxl.module.uhome.uhomebase.service.BrandService;
import com.ytoxl.module.uhome.uhomebase.service.PlanService;
import com.ytoxl.module.uhome.uhomebase.service.ProductCategoryService;
import com.ytoxl.module.uhome.uhomebase.service.ProductService;
import com.ytoxl.uhomefront.web.action.BaseAction;

/**
 * @author wangguoqing
 * 
 */
@SuppressWarnings("serial")
public class SpecialSellerAction extends BaseAction {

	private static Logger logger = LoggerFactory
			.getLogger(SpecialSellerAction.class);

	// 默认  加载 最受欢迎品牌信息
	private static String SPECIALSELLER = "specialSeller";
	//最受欢迎的品牌  用于ajax调用
	private static String SPECIALSELLERAJAX = "specialSellerAjax";
	// 折扣最多
	private static String LOWESTDISCOUNTBRAND = "lowestDiscountBrand";
	// 最新上线
	private static String LASTONLINEBRAND = "lastOnLineBrand";
	// 即将上线
	private static String SOONBRAND = "soonBrand";
	//品牌属于那个子目录 执行方法的返回值
	private static String CATEGORYBRAND = "categoryBrand";
	//进口商品
	private static String IMPORTGOODS = "importGoods";
	
	@Autowired
	private PlanService planService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private ProductService productService;
	@Autowired 
	private ProductCategoryService productCategoryService;

	//最新上线的品牌
	private List<Plan> lastOnLineBrands;
	//按品牌的折扣最低排序
	private List<Plan> lowestDiscountBrands;
	//最受欢迎的品牌 排序
	private List<Plan> mostPopularBrands;
	//排期按照类别存储
	private Map<Integer, Map<String,List<Plan>>> planCategorys;
	
	// 页面显示特卖的信息  不用ajax 就可以不要
	private List<Plan> specialSellers;
	// 页面显示即将上线的信息
	private List<Plan> soonBrands;
	// 热卖品牌
	private List<Brand> hotBrands;
	//人气商品
	private List<Product> mostHits;
	//优惠最多的商品
	private List<Product> mostCheaps;
	
	//品牌属于那个目录
	private ProductCategory productCategory;
	//所有子目录
	List<ProductCategory> productCategorys;
	
	//进口商品分页 
	private BasePagination<Product> productPage;
	//进口品牌分类
	private List<Brand> brandCategorys;
	

	// 默认执行的方法 最受欢迎的品牌
	public String specialSeller() {
		try {
			//获取所有目录 TODO
			productCategorys = productCategoryService.listProductCategory();
			
			mostHits = productService.getProductListByHits(8);
			mostCheaps = productService.getProductListByMostCheap();
			hotBrands = brandService.listHotBrands();
			//最受欢迎的品牌 排序
			mostPopularBrands = planService.listPlanMostPopularBrands();
			//最新上线的品牌
			lastOnLineBrands = planService.listPlanLastOnlineBrands();
			//按品牌的折扣最低排序
			lowestDiscountBrands = planService.listPlanLowestDiscountBrands();
			//即将上线
			soonBrands = planService.listPlanSoonBrands();
			//排期按照类别存储
			planCategorys = planService.listPlanByCategorys();	
			//specialSellers = planService.listPlanMostPopularBrands();
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return SPECIALSELLER;
	}
	
	//最受欢迎的品牌  用于ajax调用
	public String specialSellerAjax(){
		try {
			specialSellers = planService.listPlanMostPopularBrands();
			//如果点击了子目录则这里显示与子目录有关的排期
			if(productCategory !=null ){
				Integer categoryId = productCategory.getProductCategoryId();
				if(categoryId != null){
					specialSellers = planService.getPlansByCategoryId(specialSellers, productCategory);
				}
			}
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return SPECIALSELLERAJAX;
	}

	// 折扣最多  用于ajax调用
	public String lowestDiscountBrand() {
		try {
			//mostHits = productService.getProductListByHits();
			//mostCheaps = productService.getProductListByMostCheap();
			//hotBrands = brandService.listHotBrands();
			//soonBrands = planService.listPlanSoonBrands();
			specialSellers = planService.listPlanLowestDiscountBrands();
			//如果点击了子目录则这里显示与子目录有关的排期
			//如果点击了子目录则这里显示与子目录有关的排期
			if(productCategory !=null ){
				Integer categoryId = productCategory.getProductCategoryId();
				if(categoryId != null){
					specialSellers = planService.getPlansByCategoryId(specialSellers, productCategory);
				}
			}
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return LOWESTDISCOUNTBRAND;
	}

	// 最新上线  用于ajax调用
	public String lastOnLineBrand() {
		try {
			//mostHits = productService.getProductListByHits();
			//mostCheaps = productService.getProductListByMostCheap();
			//hotBrands = brandService.listHotBrands();
			//soonBrands = planService.listPlanSoonBrands();
			specialSellers = planService.listPlanLastOnlineBrands();
			//如果点击了子目录则这里显示与子目录有关的排期
			if(productCategory !=null ){
				Integer categoryId = productCategory.getProductCategoryId();
				if(categoryId != null){
					specialSellers = planService.getPlansByCategoryId(specialSellers, productCategory);
				}
			}
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return LASTONLINEBRAND;
	}

	// 即将上线 可以不要 TODO  用ajax了   只改变了  3个子目录内容
	public String soonBrand() {
		try {
			soonBrands = planService.listPlanSoonBrands();
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return SOONBRAND;
	}
	
	//点击特卖的3个子目录执行的方法
	public String categoryBrand(){
		try{
			productCategorys = productCategoryService.listProductCategory();
			mostHits = productService.getProductListByHits(8);
			mostCheaps = productService.getProductListByMostCheap();
			hotBrands = brandService.listHotBrands();
			soonBrands = planService.listPlanSoonBrands();
			//先查询出在当前时间内所有的排期
			specialSellers = planService.listPlanMostPopularBrands();
			//查询属于固定目录的品牌的当前时间有效的排期  TODO
			specialSellers = planService.getPlansByCategoryId(specialSellers, productCategory);
		}catch(UhomeStoreException u){
			logger.error(u.getMessage());
			u.printStackTrace();
		}
		return CATEGORYBRAND;
	}
	
	//进口商品
	public String importGoods(){
		//TODO
		//如果productPage为空
		if(productPage == null){
			productPage = new BasePagination<Product>();
		}
		try {
			productService.searchImportProducts4Front(productPage);
			hotBrands = brandService.listHotBrands();
			//在没有点击单个的品牌情况查询 TODO
			brandCategorys = productService.getBrandsSearchAndImport(Product.PAGE_TYPE_IMPORT,"");
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
		return IMPORTGOODS;
	}

	public List<Plan> getLastOnLineBrands() {
		return lastOnLineBrands;
	}

	public void setLastOnLineBrands(List<Plan> lastOnLineBrands) {
		this.lastOnLineBrands = lastOnLineBrands;
	}

	public List<Plan> getLowestDiscountBrands() {
		return lowestDiscountBrands;
	}

	public void setLowestDiscountBrands(List<Plan> lowestDiscountBrands) {
		this.lowestDiscountBrands = lowestDiscountBrands;
	}

	public List<Plan> getMostPopularBrands() {
		return mostPopularBrands;
	}

	public void setMostPopularBrands(List<Plan> mostPopularBrands) {
		this.mostPopularBrands = mostPopularBrands;
	}

	public Map<Integer, Map<String, List<Plan>>> getPlanCategorys() {
		return planCategorys;
	}

	public void setPlanCategorys(Map<Integer, Map<String, List<Plan>>> planCategorys) {
		this.planCategorys = planCategorys;
	}

	public List<Plan> getSpecialSellers() {
		return specialSellers;
	}

	public void setSpecialSellers(List<Plan> specialSellers) {
		this.specialSellers = specialSellers;
	}

	public List<Plan> getSoonBrands() {
		return soonBrands;
	}

	public void setSoonBrands(List<Plan> soonBrands) {
		this.soonBrands = soonBrands;
	}

	public List<Brand> getHotBrands() {
		return hotBrands;
	}

	public void setHotBrands(List<Brand> hotBrands) {
		this.hotBrands = hotBrands;
	}

	public List<Product> getMostHits() {
		return mostHits;
	}

	public void setMostHits(List<Product> mostHits) {
		this.mostHits = mostHits;
	}

	public List<Product> getMostCheaps() {
		return mostCheaps;
	}

	public void setMostCheaps(List<Product> mostCheaps) {
		this.mostCheaps = mostCheaps;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public List<ProductCategory> getProductCategorys() {
		return productCategorys;
	}

	public void setProductCategorys(List<ProductCategory> productCategorys) {
		this.productCategorys = productCategorys;
	}

	public List<Brand> getBrandCategorys() {
		return brandCategorys;
	}

	public void setBrandCategorys(List<Brand> brandCategorys) {
		this.brandCategorys = brandCategorys;
	}

	public BasePagination<Product> getProductPage() {
		return productPage;
	}

	public void setProductPage(BasePagination<Product> productPage) {
		this.productPage = productPage;
	}

}
