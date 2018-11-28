package com.ytoxl.uhomemanage.web.action.product;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Brand;
import com.ytoxl.module.uhome.uhomebase.dataobject.Plan;
import com.ytoxl.module.uhome.uhomebase.dataobject.Postage;
import com.ytoxl.module.uhome.uhomebase.dataobject.Product;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductCategory;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductSku;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductSkuOptionValue;
import com.ytoxl.module.uhome.uhomebase.dataobject.SkuOption;
import com.ytoxl.module.uhome.uhomebase.service.BrandService;
import com.ytoxl.module.uhome.uhomebase.service.ProductService;
import com.ytoxl.module.uhome.uhomebase.service.UserInfoService;
import com.ytoxl.module.uhome.uhomecontent.dataobject.HelpCategory;
import com.ytoxl.module.uhome.uhomecontent.service.HelpService;
import com.ytoxl.module.user.dataobject.User;
import com.ytoxl.uhomemanage.web.action.BaseAction;

@SuppressWarnings("serial")
public class ProductAction extends BaseAction {
	
	private static Logger logger = LoggerFactory.getLogger(ProductAction.class);
	
	private static final String SEARCH_SELLER_PRODUCTS = "searchSellerProducts";
	private static final String SEARCH_PRODUCTS = "searchProducts";
	private static final String VIEW_PRODUCT = "viewProduct";
	private static final String REVIEW_PRODUCT = "reviewProduct";
	private static final String EDIT_PRODUCT = "editProduct";
	private static final String QUICK_EDIT_PRODUCT = "quickEditProduct";
	private static final String PREVIEW_PRODUCT = "previewProduct";
	private static final String GET_TEMPLATE = "getTemplate";
	
	private BasePagination<Product> productPage;
	private Product product;
	private Integer productCategoryId;
	private Map<String,List<HelpCategory>> helpCategoryMaps;
	
	@Autowired
	private ProductService productService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private HelpService helpService;
	//批量上传文件
	private File file;
	//批量上传文件的文件名称
	private String fileFileName;
	//商品批量上传
	private User user;
	
	private InputStream excelStream;
	
	/**
	 * 查询商品(卖家)
	 * @return
	 */
	public String searchSellerProducts(){
		try {
			if (productPage == null) {
				productPage = new BasePagination<Product>();
				/*Map<String, String > params=new HashMap<String, String>();
				//默认创建日期近一周
				Date curDate = new Date();
				String beginTime = DateUtil.toDay(DateUtil.add(curDate, Calendar.DATE, -6));
				String endTime = DateUtil.toDay(curDate);
				params.put("beginTime", beginTime);
				params.put("endTime", endTime);
				productPage.setParams(params);*/
			}
			Integer sellerId = userInfoService.getCurrentSellerId();
			productService.searchSellerProducts(productPage, sellerId);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return SEARCH_SELLER_PRODUCTS;
	}
	
	/**
	 * 查询商品（管理员）
	 * @return
	 */
	public String searchProducts(){
		try {
			if (productPage == null) {
				productPage = new BasePagination<Product>();
				/*Map<String, String > params=new HashMap<String, String>();
				//默认创建日期近一周
				Date curDate = new Date();
				String beginTime = DateUtil.toDay(DateUtil.add(curDate, Calendar.DATE, -6));
				String endTime = DateUtil.toDay(curDate);
				params.put("beginTime", beginTime);
				params.put("endTime", endTime);
				productPage.setParams(params);*/
			}
			productService.searchProducts(productPage);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return SEARCH_PRODUCTS;
	}
	
	/**
	 * 查看商品
	 * @return
	 */
	public String view(){
		try {
			product = productService.getProductByProductId(product.getProductId());
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return VIEW_PRODUCT;
	}
	
	/**
	 * 获取上一条商品ID
	 * @return
	 */
	public String previous(){
		try {
			Integer previousProductId = productService.getPreviousProduct(product.getProductId());
			if(previousProductId != null){
				setMessage(String.valueOf(previousProductId));
			}else{
				setMessage(null);
			}
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(), e.getLocalizedMessage());
		}
		return JSONMSG;
	}
	
	/**
	 * 获取下一条商品ID
	 * @return
	 */
	public String next(){
		try {
			Integer nextProductId = productService.getNextProduct(product.getProductId());
			if(nextProductId != null){
				setMessage(String.valueOf(nextProductId));
			}else{
				setMessage(null);
			}
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(), e.getLocalizedMessage());
		}
		return JSONMSG;
	}
	
	/**
	 * 获取下一条待审核商品ID
	 * @return
	 */
	public String nextPending(){
		try {
			Integer productId = productService.getNextPendingProduct(product.getProductId());
			if(productId != null){
				setMessage(String.valueOf(productId));
			}else{
				setMessage(null);
			}
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(), e.getLocalizedMessage());
		}
		return JSONMSG;
	}
	
	/**
	 * 审核商品（管理员）
	 * @return
	 */
	public String review(){
		try {
			product = productService.getProductByProductId(product.getProductId());
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return REVIEW_PRODUCT;
	}
	
	/**
	 * 保存审核结果（管理员）
	 * @return
	 */
	public String saveReviewResult(){
		try {
			productService.saveReviewResult(product);
			setMessage(Boolean.TRUE.toString(),"审核成功");
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(), e.getLocalizedMessage());
		}
		return JSONMSG;
	}
	
	/**
	 * 编辑商品
	 * @return
	 */
	public String edit(){
		if(product!=null&&product.getProductId()!=null){
			try {
				Integer sellerId = userInfoService.getCurrentSellerId();
				product = productService.getProduct4Edit(product.getProductId(), sellerId);
			} catch (UhomeStoreException e) {
				logger.error(e.getMessage());
			}
		}
		return EDIT_PRODUCT;
	}
	public String addProduct(){
		return edit();
	}
	
	/**
	 * 保存商品
	 * @return
	 */
	public String save(){
		try {
			Integer sellerId = userInfoService.getCurrentSellerId();
			product.setSellerId(sellerId);
			productService.saveProduct(product);
			String str = "";
			List<ProductSku> productSkus = product.getProductSkus();
			for(int i=0; productSkus!=null&&i<productSkus.size(); i++){
				ProductSku productSku = productSkus.get(i);
				String id = "";
				List<ProductSkuOptionValue> proSkuValues = productSku.getProductSkuOptionValues();
				for(int j=0; proSkuValues!=null&&j<proSkuValues.size(); j++){
					ProductSkuOptionValue proSkuValue = proSkuValues.get(j);
					if(proSkuValue!=null){
						id += proSkuValue.getSkuOptionValueId() + "_";
					}
				}
				if(id.length() > 0){
					id = id.substring(0, id.length() -1);
				}else{
					id = "_";
				}
				str += "{\"id\":\""+id+"\",\"productSkuId\":\""+productSku.getProductSkuId()+"\"," +
						"\"secKillInventory\":\""+productSku.getSecKillInventory()+"\"," +
						"\"inventory\":\""+productSku.getInventory()+"\"," +
						"\"skuCode\":\""+productSku.getSkuCode()+"\"},";
			}
			if(str.length() > 0){
				str = "[" + str.substring(0, str.length()-1) + "]";	
			}
			str = "{\"productId\":\""+product.getProductId()+"\",\"productSkus\":"+str+"}";
			setMessage(Boolean.TRUE.toString(),str);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(), e.getLocalizedMessage());
		}
    	return JSONMSG;
	}
	
	/**
	 * 快速编辑商品
	 * @return
	 */
	public String quickEdit(){
		if(product!=null&&product.getProductId()!=null){
			try {
				Integer sellerId = userInfoService.getCurrentSellerId();
				product = productService.getProductByProductIdSellerId(product.getProductId(), sellerId);
			} catch (UhomeStoreException e) {
				logger.error(e.getMessage());
			}
		}
		return QUICK_EDIT_PRODUCT;
	}
	
	/**
	 * 快速保存
	 * @return
	 */
	public String quickSave(){
		try {
			Integer sellerId = userInfoService.getCurrentSellerId();
			product.setSellerId(sellerId);
			productService.quickUpdateProduct(product);
			setMessage(Boolean.TRUE.toString(),"修改成功");
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(), e.getLocalizedMessage());
		}
		return JSONMSG;
	}
	
	/**
	 * 删除商品
	 */
	public String delete(){
		try {
			Integer sellerId = userInfoService.getCurrentSellerId();
			product.setSellerId(sellerId);
			productService.deleteProduct(product);
			setMessage(Boolean.TRUE.toString(),"删除成功");
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(), e.getLocalizedMessage());
		}
    	return JSONMSG;
	}
	
	/**
	 * 售罄
	 * @return
	 */
	public String sellOut(){
		try {
			Integer sellerId = userInfoService.getCurrentSellerId();
			product.setSellerId(sellerId);
			productService.sellOut(product);
			setMessage(Boolean.TRUE.toString(),"售罄成功");
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(), e.getLocalizedMessage());
		}
		return JSONMSG;
	}
	
	/**
	 * 提交审核
	 */
	public String submitReview(){
		try {
			Integer sellerId = userInfoService.getCurrentSellerId();
			product.setSellerId(sellerId);
			productService.saveProduct(product);
			setMessage(Boolean.TRUE.toString(),"提交成功");
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(), e.getLocalizedMessage());
		}
		return JSONMSG;
	}
	
	/**
	 * 提交审核并复制
	 */
	public String submitReviewAndCopy(){
		try {
			product = productService.getProductCopy(product);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return EDIT_PRODUCT;
	}
	
	/**
	 * 预览商品信息
	 * @return
	 */
	public String preview(){
		try {
			product = productService.getProductCopy(product);
			helpCategoryMaps = helpService.getHelp4ProductDetail();
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return PREVIEW_PRODUCT;
	}
	
	/**
	 * 获取商品描述模板
	 * @return
	 */
	public String getTemplate(){
		return GET_TEMPLATE;
	}

	/**
	 * 获取商品大分类
	 * @return
	 */
	public List<ProductCategory> getProductCategories(){
		List<ProductCategory> productCategories = new ArrayList<ProductCategory>();
		try {
			productCategories = productService.listProductCategories();
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return productCategories;
	}
	
	/**
	 * 获取商品子分类
	 * @return
	 */
	public List<ProductCategory> getChildProductCategories() {
		List<ProductCategory> productCategories = new ArrayList<ProductCategory>();
		try {
			productCategories = productService.listChildProductCategories();
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return productCategories;
	}
	
	/**
	 * 获取全部品牌
	 * @return
	 */
	public List<Brand> getBrands() {
		List<Brand> brands = new ArrayList<Brand>();
		try {
			brands = brandService.listBrands();
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return brands;
	}
	
	/**
	 * 获取卖家可售品牌
	 * @return
	 */
	public List<Brand> getSellerBrands() {
		List<Brand> sellerBrands = new ArrayList<Brand>();
		try {
			Integer sellerId = userInfoService.getCurrentSellerId();
			sellerBrands = brandService.listBrandsBySellerId(sellerId);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return sellerBrands;
	}

	/**
	 * 获取系统默认sku数据
	 * @return
	 */
	public List<SkuOption> getSkuOptions() {
		List<SkuOption> skuOptions = new ArrayList<SkuOption>();
		try {
			skuOptions = productService.getSkuOptions();
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return skuOptions;
	}
	
	/**
	 * 批量上传商品
	 * @return
	 */
	public String batchUpload(){
		try {
			Integer sellerId = null;//100000;
			sellerId = userInfoService.getSellerByUserId(user.getUserId()).getSellerId();
//			sellerId = seller.getSellerId();
//			System.out.println("~~~~~~~~~~~~~~~~~~~~~~sellerId:"+sellerId);
			Map<String, String> map = productService.batchUploadProduct(this.file,fileFileName,sellerId);
			setMessage("导入完成!\n导入信息如下:\n"+map.get("info"));
			//提示 上传成功多少商品 多少sku
			//1.获取zip压缩文件
			//2.service判断是否是zip文件,并解压zip文件
			//3.获取excel数据并验证
			//4.找到excel中图片的图片名称,并上传
			//5.找到excel中对商品描述的图片,并上传替换成img
			//6.将数据封装成product
			//7.保存到数据库
		} catch (UhomeStoreException e) {
			logger.error("上传文件出错",e.getMessage());
			setMessage("上传文件出错");
		}
		catch (Exception e) {
			logger.error("导入商品数据出错",e.getMessage());
			setMessage("导入商品数据出错!");
		}
		return JSONMSG;
	}
	
	/**
	 * 下载批量上传商品模版
	 * @return
	 */
	public String downloadTemplate(){
		try{
			ActionContext ac = ActionContext.getContext();
	        ServletContext sc = (ServletContext) ac.get(ServletActionContext.SERVLET_CONTEXT);
	        String x = sc.getRealPath("/")+"/products_template.zip";
	        this.setExcelStream(new FileInputStream(new File(x)));
		}catch (Exception e) {
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(),e.getMessage());
		}
		return "template";
	}
	
	/**
	 * 是否进口
	 * @return
	 */
	public Short[] getIsImports(){
		return Product.ISIMPORTS;
	}
	
	/**
	 * 商品状态
	 * @return
	 */
	public Short[] getStatuses() {
		return Product.STATUSES;
	}
	
	/**
	 * 审核状态
	 * @return
	 */
	public Short[] getReviewStatuses() {
		return Product.REVIEWSTATUSES;
	}
	
	/**
	 * 活动状态
	 * @return
	 */
	public Short[] getActivityStatuses(){
		return Plan.ACTIVITYSTATUSES;
	}
	
	public Short getIsImportNo(){
		return Product.ISIMPORT_NO;
	}
	
	/**
	 * 邮费
	 * @return
	 */
	public Short[] getPostageOptions(){
		return Postage.OPTIONS;
	}
	
	/**
	 * 默认邮费选项
	 * @return
	 */
	public Short getDefaultPostageOption(){
		return Postage.OPTION_FREE;
	}
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public BasePagination<Product> getProductPage() {
		return productPage;
	}

	public void setProductPage(BasePagination<Product> productPage) {
		this.productPage = productPage;
	}
	
	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Integer getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(Integer productCategoryId) {
		this.productCategoryId = productCategoryId;
	}
	
	public Map<String, List<HelpCategory>> getHelpCategoryMaps() {
		return helpCategoryMaps;
	}

	public void setHelpCategoryMaps(Map<String, List<HelpCategory>> helpCategoryMaps) {
		this.helpCategoryMaps = helpCategoryMaps;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	
}
