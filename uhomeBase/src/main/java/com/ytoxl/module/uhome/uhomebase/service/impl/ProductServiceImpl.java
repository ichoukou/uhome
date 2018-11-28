package com.ytoxl.module.uhome.uhomebase.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.core.common.utils.SpringContextUtils;
import com.ytoxl.module.file.core.service.FileHessianService;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.common.utils.CookieUtils;
import com.ytoxl.module.uhome.uhomebase.common.utils.DateUtil;
import com.ytoxl.module.uhome.uhomebase.common.utils.ExcelUtils;
import com.ytoxl.module.uhome.uhomebase.common.utils.StringUtils;
import com.ytoxl.module.uhome.uhomebase.common.utils.UncompressZip;
import com.ytoxl.module.uhome.uhomebase.dataobject.Brand;
import com.ytoxl.module.uhome.uhomebase.dataobject.Plan;
import com.ytoxl.module.uhome.uhomebase.dataobject.PlanProduct;
import com.ytoxl.module.uhome.uhomebase.dataobject.Postage;
import com.ytoxl.module.uhome.uhomebase.dataobject.Product;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductCategory;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductSku;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductSkuOptionValue;
import com.ytoxl.module.uhome.uhomebase.dataobject.SkuOption;
import com.ytoxl.module.uhome.uhomebase.dataobject.SkuOptionValue;
import com.ytoxl.module.uhome.uhomebase.dataobject.UserInfo;
import com.ytoxl.module.uhome.uhomebase.mapper.BrandMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.PlanMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.PlanProductMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.PostageMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.ProductCategoryMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.ProductMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.ProductSkuMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.ProductSkuOptionValueMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.SkuOptionMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.SkuOptionValueMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.UserInfoMapper;
import com.ytoxl.module.uhome.uhomebase.service.PostageService;
import com.ytoxl.module.uhome.uhomebase.service.ProductService;
import com.ytoxl.module.uhome.uhomebase.service.UserInfoService;
import com.ytoxl.module.user.dataobject.User;

@Service
public class ProductServiceImpl implements ProductService {
	private static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	@Autowired
	private ProductMapper<Product> productMapper;
	
	@Autowired
	private ProductSkuMapper<ProductSku> productSkuMapper;
	
	@Autowired
	private ProductSkuOptionValueMapper<ProductSkuOptionValue> productSkuOptionValueMapper;
	
	@Autowired
	private SkuOptionMapper<SkuOption> skuOptionMapper;
	
	@Autowired
	private SkuOptionValueMapper<SkuOptionValue> skuOptionValueMapper;
	
	@Autowired
	private PlanMapper<Plan> planMapper;
	
	@Autowired
	private PlanProductMapper<PlanProduct> planProductMapper;
	
	@Autowired
	private BrandMapper<Brand> brandMapper;
	
	@Autowired
	private ProductCategoryMapper<ProductCategory> productCategoryMapper;
	
	@Value("${userProductHistoryNum}")
	private Integer userProductHistoryNum;
	
	@Value("${search_char_length}")
	private Integer searchCharLength;
	
	@Autowired
	private UserInfoMapper<UserInfo> userInfoMapper;
	
	@Autowired
	private PostageMapper<Postage> postageMapper;
	@Autowired
	private PostageService postageService;
	
	protected String filterParamReg = "[\\<\\>\\\"\\']";//需要过了掉param值中的字符  重定义  取出空格
	
	@Autowired
	private UserInfoService userInfoService;
	/*@Resource(name="fileHessianServiceClient")*/
	private FileHessianService fileHessianServiceClient;
	@Value("${file.project}")
	private String project;
	@Value("${file.dataPatten}")
	private String dataPatten;
	@Value("${file.zipUploadPath}")
	private String zipUploadPath;
	@Value("${jspvar._filePath}")
	private String filePath;
	
	//自定义分页数量
	@Value("${limit_plan_products}")
	private Integer defaultLimit;
	
	/**
	 * 获取商品类别
	 * @return
	 */
	@Override
	public List<ProductCategory> listProductCategories() throws UhomeStoreException {
		return productCategoryMapper.listProductCategory();
	}
	
	/**
	 * 获取商品子类别
	 * @return
	 */
	@Override
	public List<ProductCategory> listChildProductCategories() throws UhomeStoreException {
		return productCategoryMapper.listChildProductCategory();
	}
	
	/**
	 * 获取SKU选项及相应值信息
	 * @return
	 */
	@Override
	public List<SkuOption> getSkuOptions() throws UhomeStoreException {
		List<SkuOption> skuOptions = skuOptionMapper.listSkuOptions();
		for(SkuOption skuOption : skuOptions){
			List<SkuOptionValue> skuOptionValues = skuOptionValueMapper.listSkuOptionValues(skuOption.getSkuOptionId());
			skuOption.setSkuOptionValues(skuOptionValues);
		}
		return skuOptions;
	}
	
	/**
	 * 得到商品信息副本用于预览、复制
	 * @param product
	 * @return
	 */
	@Override
	public Product getProductCopy(Product product) throws UhomeStoreException {
		product.setProductId(null);
		
		if(product.getSellStartTime()!=null&&product.getSellEndTime()!=null){
			GregorianCalendar gc = new GregorianCalendar();
			//开始时间
			gc.setTime(product.getSellStartTime());
			gc.add(Calendar.HOUR_OF_DAY, 10);
			product.setSellStartTime(gc.getTime());
			//结束时间
			gc.setTime(product.getSellEndTime());
			gc.add(Calendar.HOUR_OF_DAY, 10);
			gc.add(Calendar.SECOND, -1);
			product.setSellEndTime(gc.getTime());
		}
		
		Brand brand = brandMapper.get(product.getBrandId());
		product.setBrand(brand);
		
		List<ProductSku> productSkus = product.getProductSkus();
		Integer saleInventoryCount = 0;
		
		List<SkuOption> skuOptions = new ArrayList<SkuOption>();
		Map<Integer,Object> map = new LinkedHashMap<Integer,Object>();

		if(productSkus != null){
			for(ProductSku proSku : productSkus){
				proSku.setProductSkuId(null);
				Integer inventory = proSku.getInventory();
				saleInventoryCount += inventory == null ? 0 : inventory;
				List<ProductSkuOptionValue> proSkuOptValues = proSku.getProductSkuOptionValues();
				if(proSkuOptValues != null && proSkuOptValues.size() > 0){
					for(ProductSkuOptionValue proSkuOptValue : proSkuOptValues){
						Integer skuOptValueId = proSkuOptValue.getSkuOptionValueId();
						if(!map.containsKey(skuOptValueId)){
							SkuOptionValue skuOptValue = skuOptionValueMapper.get(skuOptValueId);
							String overrideSkuOptionValue = proSkuOptValue.getOverrideSkuOptionValue();
							if(overrideSkuOptionValue != null){
								skuOptValue.setSkuOptionValue(overrideSkuOptionValue);
								skuOptValue.setOverrideFlag("1");
							}
							map.put(skuOptValueId, skuOptValue);
						}
					}
				}
			}
		}
		
		if(map.size() > 0){
			List<Integer> ids = new ArrayList<Integer>(); 
			for(Integer skuOptValueId : map.keySet()){
				SkuOptionValue skuOptValue = (SkuOptionValue) map.get(skuOptValueId);
				ids.add(skuOptValue.getSkuOptionId());
			}
			skuOptions = skuOptionMapper.getSkuOptionsByIds(ids);
			for(SkuOption skuOption : skuOptions){
				List<SkuOptionValue> skuOptValues =  new ArrayList<SkuOptionValue>();
				for(Integer skuOptValueId : map.keySet()){
					SkuOptionValue skuOptValue = (SkuOptionValue) map.get(skuOptValueId);
					if(skuOptValue.getSkuOptionId().equals(skuOption.getSkuOptionId())){
						skuOptValues.add(skuOptValue);
					}
				}
				skuOption.setSkuOptionValues(skuOptValues);
			}
		}
		
		product.setSaleInventoryCount(saleInventoryCount);
		product.setSkuOptions(skuOptions);
		return product;
	}
	
	/**
	 * 分页查询卖家商品
	 * @param productPage
	 * @param sellerId
	 */
	@Override
	public void searchSellerProducts(BasePagination<Product> productPage, Integer sellerId)
			throws UhomeStoreException {
		Map<String, Object> searchParams = productPage.getSearchParamsMap();
		searchParams.put("sellerId", sellerId);
		if(productPage.isNeedSetTotal()){
			Integer total = productMapper.searchSellerProductsCount(searchParams);
			productPage.setTotal(total);
		}
		Collection<Product> result = productMapper.searchSellerProducts(searchParams);
		handleResult(result);
		productPage.setResult(result);
	}
	
	/**
	 * 管理员查询商品
	 * @param productPage
	 */
	@Override
	public void searchProducts(BasePagination<Product> productPage) throws UhomeStoreException {
		Map<String, Object> searchParams = productPage.getSearchParamsMap();
		if(productPage.isNeedSetTotal()){
			Integer total = productMapper.searchProductsCount(searchParams);
			productPage.setTotal(total);
		}
		Collection<Product> result = productMapper.searchProducts(searchParams);
		handleResult(result);
		productPage.setResult(result);
	}

	private void handleResult(Collection<Product> result) {
		if(result != null && result.size() > 0){
			//得到商品productId集合
			List<Integer> productIds = new ArrayList<Integer>();
			for(Product product : result){
				productIds.add(product.getProductId());
			}
			//根据productId集合获取排期数据
			List<Plan> plans = productMapper.listPlansByProductIds(productIds);
			if(plans != null && plans.size() > 0){
				List<Integer> planIds = new ArrayList<Integer>();
				for(Plan plan : plans){
					planIds.add(plan.getPlanId());
				}
				//根据planId集合获取排期商品数据
				List<PlanProduct> planProducts = planProductMapper.listPlanProuductsByPlanIds(planIds);
				//将排期商品与对应排期关联
				for(Plan plan : plans){
					List<PlanProduct> list = new ArrayList<PlanProduct>();
					for(PlanProduct planProduct : planProducts){
						if(planProduct.getPlanId().equals(plan.getPlanId())){
							list.add(planProduct);
						}
					}
					plan.setPlanProducts(list);
				}
				//将排期与对应商品关联
				for(Product product : result){
					List<Plan> planList = new ArrayList<Plan>();
					for(Plan plan : plans){
						List<PlanProduct> list = plan.getPlanProducts();
						if(list.size() > 0){
							for(PlanProduct planProduct : list){
								if(planProduct.getProductId().equals(product.getProductId())){
									planList.add(plan);
									break;
								}
							}
						}
					}
					product.setPlans(planList);
				}
			}
		}
	}

	/**
	 * 查询上一条记录
	 * @param productId
	 * @return
	 */
	@Override
	public Integer getPreviousProduct(Integer productId) throws UhomeStoreException {
		return productMapper.getPreviousProductId(productId);
	}

	/**
	 * 查询下一条记录
	 * @param productId
	 * @return
	 */
	@Override
	public Integer getNextProduct(Integer productId) throws UhomeStoreException {
		return productMapper.getNextProductId(productId);
	}
	
	/**
	 * 查询下一条待审核商品
	 * @param productId
	 * @return
	 */
	@Override
	public Integer getNextPendingProduct(Integer productId) throws UhomeStoreException {
		return productMapper.getNextPendingProduct(productId);
	}

	/**
	 * 保存商品信息
	 * @param product
	 */
	@Override
	public void saveProduct(Product product) throws UhomeStoreException {
		if(product.getSellStartTime()!=null&&product.getSellEndTime()!=null){
			GregorianCalendar gc = new GregorianCalendar();
			//开始时间
			gc.setTime(product.getSellStartTime());
			gc.add(Calendar.HOUR_OF_DAY, 10);
			product.setSellStartTime(gc.getTime());
			//结束时间
			gc.setTime(product.getSellEndTime());
			gc.add(Calendar.HOUR_OF_DAY, 10);
			gc.add(Calendar.SECOND, -1);
			product.setSellEndTime(gc.getTime());
		}
		
		if(product.getProductId() == null){
			//保存操作
			productMapper.add(product);
			List<ProductSku> productSkus = product.getProductSkus();
			if(productSkus != null && productSkus.size() > 0){
				for(ProductSku productSku : productSkus){
					productSku.setProductId(product.getProductId());
					productSkuMapper.add(productSku);
					
					List<ProductSkuOptionValue> proSkuOptionValues = productSku.getProductSkuOptionValues();
					if(proSkuOptionValues != null && proSkuOptionValues.size() > 0){
						productSkuOptionValueMapper.addBatch(productSku.getProductSkuId(), proSkuOptionValues);
					}
				}
			}
		}else{
			//修改操作
			productMapper.update(product);
			List<ProductSku> originalProSkus = productSkuMapper
					.listProductSkusByProductId(product.getProductId());
			List<ProductSku> currentProSkus = product.getProductSkus();
			
			//得到新增的productsku
			Iterator<ProductSku> it1 = currentProSkus.iterator();
			while(it1.hasNext()){ 
				ProductSku proSku = it1.next();
				//保存新增项
				if(proSku.getProductSkuId() == null){
					proSku.setProductId(product.getProductId());
					productSkuMapper.add(proSku);
					List<ProductSkuOptionValue> proSkuOptValues = proSku.getProductSkuOptionValues();
					if(proSkuOptValues != null && proSkuOptValues.size() > 0){
						productSkuOptionValueMapper.addBatch(proSku.getProductSkuId(), proSkuOptValues);
					}
					//从集合中移除新增项
					it1.remove();
				}
			} 
			
			//得到修改项
			Iterator<ProductSku> it2 = originalProSkus.iterator();
			while(it2.hasNext()){ 
				ProductSku proSku = it2.next();
				for(ProductSku currentProSku : currentProSkus){
					//更新修改项
					if(proSku.getProductSkuId().equals(currentProSku.getProductSkuId())){
						productSkuMapper.update(currentProSku);
						//得到修改后的sku属性信息
						List<ProductSkuOptionValue> proSkuOptValues = currentProSku.getProductSkuOptionValues();
						//删除原有的sku属性信息保存更新后的信息
						productSkuOptionValueMapper.delProductSkuOptionValueByProductSkuId(currentProSku.getProductSkuId());
						if(proSkuOptValues != null && proSkuOptValues.size() > 0){
							productSkuOptionValueMapper.addBatch(currentProSku.getProductSkuId(), proSkuOptValues);
						}
						//从originalProSkus集合中移除修改项
						it2.remove();
						break;
					}
				}
			}
			
			//得到删除项
			if(originalProSkus.size() > 0){
				productSkuMapper.logicDelBatch(originalProSkus);
			}
		}
		//保存邮费信息
		Postage postage = product.getPostage();
		postage.setOutId(product.getProductId());
		postage.setType(Postage.TYPE_PRODUCT);
		postageService.savePostage(postage);
	}
	
	/**
	 * 快速更新商品信息
	 * @param product
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
	public void quickUpdateProduct(Product product) throws UhomeStoreException {
		productMapper.updateProductName(product);
		for(ProductSku proSku : product.getProductSkus()){
			productSkuMapper.update(proSku);
		}
	}
	
	/**
	 * 根据商品ID查找一件商品信息
	 * @param productId
	 * @return
	 */
	@Override
	public Product getProductByProductId(Integer productId) throws UhomeStoreException {
		Product product = productMapper.get(productId);
		//获取商品的颜色、尺寸信息
		List<SkuOption> skuOptions = productMapper.listProductSkuOptionsByProductId(productId);
		for(SkuOption skuOption :skuOptions){
			List<SkuOptionValue> skuOptionValues = productMapper
					.listProductSkuOptionValuesBySkuOptionId(skuOption.getSkuOptionId(), productId);
			skuOption.setSkuOptionValues(skuOptionValues);
		}
		product.setSkuOptions(skuOptions);
		return product;
	}
	
	/**
	 * 获取商家商品
	 * @param productId
	 * @param sellerId
	 * @return
	 */
	@Override
	public Product getProductByProductIdSellerId(Integer productId,
			Integer sellerId) throws UhomeStoreException {
		Product product = productMapper.getProductByProductIdAndSellerId(productId, sellerId);
		//获取商品的颜色、尺寸信息
		List<SkuOption> skuOptions = productMapper.listProductSkuOptionsByProductId(productId);
		for(SkuOption skuOption :skuOptions){
			List<SkuOptionValue> skuOptionValues = productMapper
					.listProductSkuOptionValuesBySkuOptionId(skuOption.getSkuOptionId(), productId);
			skuOption.setSkuOptionValues(skuOptionValues);
			String skuOptionName = skuOption.getSkuOptionName();
			if(skuOptionName.trim().equals("颜色")){
				product.setProductSkuColorNum(skuOptionValues.size());
			} else if (skuOptionName.trim().equals("尺码/规格")){
				product.setProductSkuSizeNum(skuOptionValues.size());
			}
		}
		product.setSkuOptions(skuOptions);
		
		//获取product_sku表数据
		List<ProductSku> productSkus = productSkuMapper.listProductSkusByProductId(productId);
		for(ProductSku productSku : productSkus){
			//获取product_sku_option_value表数据
			List<ProductSkuOptionValue> proSkuOptValues = productSkuOptionValueMapper
					.listProductSkuOptionValuesByProductSkuId(productSku.getProductSkuId());
			productSku.setProductSkuOptionValues(proSkuOptValues);
			
			for(ProductSkuOptionValue proSkuOptValue : proSkuOptValues){
				String skuOptionName = proSkuOptValue.getSkuOptionValue().getSkuOption().getSkuOptionName();
				String skuOptionValue = proSkuOptValue.getSkuOptionValue().getSkuOptionValue();
				String overrideSkuOptionValue = proSkuOptValue.getOverrideSkuOptionValue();
				if(overrideSkuOptionValue != null){
					skuOptionValue = overrideSkuOptionValue;
				}
				if(skuOptionName.trim().equals("颜色")){
					productSku.setProductSkuColor(skuOptionValue);
				} else if (skuOptionName.trim().equals("尺码/规格")){
					productSku.setProductSkuSize(skuOptionValue);
				}
			}
		}
		product.setProductSkus(productSkus);
		return product;
	}

	/**
	 * 获取商品信息用于修改页面
	 * @param productId
	 * @param sellerId
	 * @return
	 */
	@Override
	public Product getProduct4Edit(Integer productId, Integer sellerId) throws UhomeStoreException {
		//获取商品的基本属性信息
		Product product = productMapper.getProductByProductIdAndSellerId(productId, sellerId);
		//获取商品的颜色、尺寸信息
		List<SkuOption> skuOptions = productMapper.listProductSkuOptionsByProductId(productId);
		for(SkuOption skuOption :skuOptions){
			List<SkuOptionValue> skuOptionValues = productMapper
					.listProductSkuOptionValuesBySkuOptionId(skuOption.getSkuOptionId(), productId);
			skuOption.setSkuOptionValues(skuOptionValues);
		}
		product.setSkuOptions(skuOptions);
		
		//获取product_sku表数据
		List<ProductSku> productSkus = productSkuMapper.listProductSkusByProductId(productId);
		for(ProductSku productSku : productSkus){
			//获取product_sku_option_value表数据
			List<ProductSkuOptionValue> proSkuOptValues = productSkuOptionValueMapper
					.listProductSkuOptionValuesByProductSkuId(productSku.getProductSkuId());
			productSku.setProductSkuOptionValues(proSkuOptValues);
		}
		product.setProductSkus(productSkus);
		
		Plan currentPlan = planMapper.getCurrentPlanByProductId(productId); //商品当前所在排期
		Plan lastPlan = planMapper.getLastPlanByProductId(productId);	//商品当前时间之后最后一个排期
		Date maxSellStartTime = null; //最大销售开始时间
		Date minSellEndTime = null;	//最小销售结束时间
		if(currentPlan != null){
			maxSellStartTime = currentPlan.getStartTime();
			if(lastPlan == null){
				if(currentPlan.getType().equals(Plan.TYPE_SPECIAL_SELLER)){
					minSellEndTime = currentPlan.getEndTime();	
				}else{
					minSellEndTime = DateUtil.nextDate(maxSellStartTime);
				}
			}else{
				minSellEndTime = lastPlan.getEndTime();
			}
		}else if(lastPlan != null){
				maxSellStartTime = lastPlan.getStartTime();
				if(lastPlan.getType().equals(Plan.TYPE_SPECIAL_SELLER)){
					minSellEndTime = lastPlan.getEndTime();	
				}else{
					minSellEndTime = DateUtil.nextDate(maxSellStartTime);
				}
		}
		product.setMaxSellStartTime(maxSellStartTime);
		product.setMinSellEndTime(minSellEndTime);
		
		//查询邮费信息
		Postage postage = postageMapper.getPostageByOutIdAndType(productId, Postage.TYPE_PRODUCT);
		if(postage != null){
			postage.setOption(Postage.OPTION_TEN);
			product.setPostage(postage);
		}
		
		return product;
	}
	
	/**
	 * 根据productId,sellerId删除一件商品
	 * @param product
	 */
	@Override
	public void deleteProduct(Product product) throws UhomeStoreException {
		//逻辑删除
		product.setStatus(Product.STATUS_DELETE);
		productMapper.updateProductStatus(product);
	}

	/**
	 * 保存审核结果审核
	 * @param product
	 */
	@Override
	public void saveReviewResult(Product product) throws UhomeStoreException {
		productMapper.updateProductReviewResult(product);
	}
	
	/**
	 * 售罄
	 * @param product
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
	public void sellOut(Product product) throws UhomeStoreException {
		//更新特卖库存为0
		List<Integer> productSkuIds = productSkuMapper.listProductSkuIdsByProductIdAndSellerId(product.getProductId(), product.getSellerId());
		if(productSkuIds !=null && productSkuIds.size() > 0){
			productSkuMapper.updateInventoryByIds(productSkuIds);
		}
		
	}
 
	@Override
	public void searchProducts4Front(BasePagination<Product> productPage) throws UhomeStoreException {
		//url转化  将相应的数字转化成对应的字符  sort dir  TODO
		String sort = productPage.getSort();
		String dir = productPage.getDir();
		if("100000".equals(dir)){
			productPage.setDir("asc");
		}else if("100001".equals(dir)){
			productPage.setDir("desc");
		}
		for(Entry<String,String> entry:Product.SORTSMAP.entrySet()){
			if(entry.getKey().equals(sort)){
				productPage.setSort(entry.getValue());
			}
		}
		
		//替换具体的正则表达式
		productPage.setFilterParamReg(filterParamReg);
		Map<String, Object> searchParams = productPage.getSearchParamsMap();
		//TODO 前台传入 如果前台搜索字符大于30  截取30
		Object obj = searchParams.get("keyWord");
		if(obj != null){
			String keyWord = obj.toString();
			if(keyWord != null && keyWord.length() > searchCharLength){
				keyWord = keyWord.substring(0, searchCharLength);
				searchParams.put("keyWord", keyWord);
			}
		}
		if(productPage.isNeedSetTotal()){
			Integer total = productMapper.searchProducts4FrontCount(searchParams);
			productPage.setTotal(total);
		}
		
		Collection<Product> result;
		
		//如果是按销售数量排序  则执行另外一条 sql
		if(Product.SALESQUANTITY.equals(productPage.getSort())){
			//TODO
			result = productMapper.searchProducts4FrontBySalesQuantity(searchParams);
		}else{
			result = productMapper.searchProducts4Front(searchParams);
		}
		if(result != null){
			//商品获取品牌属性
			for(Product product : result){
				//关联卖家如果卖家被禁用  前台显示  该商品 已售馨  TODO
				User u = userInfoMapper.getUserBySellerId(product.getSellerId());
				if(u != null && u.getStatus() != null && u.getStatus().intValue() == User.STATUS_UNABLE){
					//禁用
					product.setIsSellerOff(true);
				}
				
				//Brand brand = brandMapper.getBrandByBrandId(product.getBrandId());
				Brand brand = brandMapper.getBrandById(product.getBrandId());
				product.setBrand(brand);
				//获取此商品的排期 理论上当前时间内一件商品只属于一个排期  
				List<Plan> plans = productMapper.listPlansByProductIdAndCurrentTime(product.getProductId());
				product.setPlans(plans);
				//获取sku信息
				List<ProductSku> productSkus = productMapper.getProductSkuListByProductId(product.getProductId());
				product.setProductSkus(productSkus);
				//设置商品库存
				product.setSaleInventoryCount(getProductInventoryCount(product));
			}
		}
		productPage.setResult(result);
	}

	
	@Override
	public List<Product> getProductListByHits(Integer num) throws UhomeStoreException {
		List<Product> products = productMapper.getProductListByHits(num);
		return products;
	}


	
	@Override
	public List<Product> getProductListByMostCheap() throws UhomeStoreException {
		List<Product> products = productMapper.getProductListByMostCheap(10);
		return products;
	}

	
	@Override
	public Product getProductByProductId4Front(Product p) throws UhomeStoreException {
		//根据productId查询一件商品信息
		List<Product> list = productMapper.getProductByProductId(p.getProductId());
		//如果没有查询到产品返回空
		if(list == null || list.size() == 0){
			return null;
		}
		Product product = list.get(0);

		//关联卖家如果卖家被禁用  前台显示  该商品 已售馨  TODO
		User u = userInfoMapper.getUserBySellerId(product.getSellerId());
		if(u !=null && u.getStatus() != null && u.getStatus().intValue() == User.STATUS_UNABLE){
			//禁用
			product.setIsSellerOff(true);
		}
		
		//获取设置相应的品牌信息
//		Brand brand = brandMapper.get(product.getBrandId());
		Brand brand = brandMapper.getBrandById(product.getBrandId());
		product.setBrand(brand);
		//获取排期信息
		List<Plan> plans = productMapper.listPlansByProductIdAndCurrentTime(product.getProductId());
		//设置排期信息  用于 倒计时
		product.setPlans(plans);
		//获取sku信息
		List<ProductSku> productSkus = productMapper.getProductSkuListByProductId(product.getProductId());
		//设置sku
		product.setProductSkus(productSkus);
		//获取每个sku对应的sku_option_value
		for(ProductSku ps : productSkus){
			List<ProductSkuOptionValue> psovs = productSkuOptionValueMapper.listProductSkuOptionValuesByProductSkuId(ps.getProductSkuId());
			ps.setProductSkuOptionValues(psovs);
		}
		//存放颜色-->对应所有的可能,规格所有的可能
		Map<String,List<ProductSkuOptionValue>> productSkuOptionValueMap = new HashMap<String,List<ProductSkuOptionValue>>();
		
		//存放颜色-->红色，黄色，蓝色      规格-->190/90,180/80 等
		Map<String,Set<SkuOptionValue>> skuOptionValueMap = new HashMap<String,Set<SkuOptionValue>>();
		
		//列出所有的 选项   例如：颜色，规格  等
		List<SkuOption> skuOptions = skuOptionMapper.listSkuOptions();
		
		//遍历skuOptions 将选项的值分类
		for(SkuOption sp : skuOptions){
			List<ProductSkuOptionValue> proSkuOptValue = new ArrayList<ProductSkuOptionValue>();
			Set<SkuOptionValue> skuOV = new LinkedHashSet<SkuOptionValue>();
			//遍历每个sku
			for(ProductSku ps : productSkus){
				for(ProductSkuOptionValue psov : ps.getProductSkuOptionValues()){
					//若skuOptionValue中的skuOptionId与skuOption中的skuOptionId相等
					if(sp.getSkuOptionId().equals(psov.getSkuOptionValue().getSkuOptionId())){
						proSkuOptValue.add(psov);
						String override = psov.getOverrideSkuOptionValue();
						//如果系统的选项值被修改了,系统默认的值被覆盖 TODO
						if(override != null){
							psov.getSkuOptionValue().setSkuOptionValue(override);
						}
						skuOV.add(psov.getSkuOptionValue());
					}
				}
			}
			productSkuOptionValueMap.put(sp.getSkuOptionName(), proSkuOptValue);
			skuOptionValueMap.put(sp.getSkuOptionName(), skuOV);
		}
		
		//判断颜色 规格下是否有内容 TODO  后面可以分开  前台有颜色 显示颜色  有规格 显示规格
		for(Iterator<String> ite = skuOptionValueMap.keySet().iterator();ite.hasNext();){
			boolean b = false; // 默认是没有
			Set<SkuOptionValue> skuOptionValue = skuOptionValueMap.get(ite.next());
			if(skuOptionValue.size() > 0){
				b = true;
				product.setIsSkuOptionValue(b);
				break;
			}
		}
		
		//TODO SkuOptionValue对应的数据封装成map
		Map<String,String> maps = new HashMap<String,String>();
		//TODO ProductSku对应的库存
		Map<Integer,Integer> productSkuInventoryMap = new HashMap<Integer,Integer>();
		for(Iterator<String> ite = productSkuOptionValueMap.keySet().iterator();ite.hasNext();){
			List<ProductSkuOptionValue> psov1=productSkuOptionValueMap.get(ite.next());
			List<ProductSkuOptionValue> psov2=productSkuOptionValueMap.get(ite.next());
			//2个选项都有 颜色 规格
			if(psov2 != null && psov1 != null &&psov1.size()>0 && psov2.size()>0){
				//选一个大的选项
				int pTemp = (psov1.size() > psov2.size()) ? (psov1.size()) : (psov2.size()) ;
				for(int i = 0; i < pTemp; i++){
					//取出SkuOptionValue的id
					Integer skuOptionValueId1 = null;
					Integer skuOptionValueId2 = null;
					Integer productSkuId = null;
					if(psov1.size() >= i){
						skuOptionValueId1 = psov1.get(i).getSkuOptionValueId();
						//取出sku的id
						productSkuId = psov1.get(i).getProductSkuId();
					}
					if(psov2.size() >= i){
						skuOptionValueId2 = psov2.get(i).getSkuOptionValueId();
						if(productSkuId == null){
							productSkuId = psov2.get(i).getProductSkuId();
						}
					}
					maps.put(skuOptionValueId1+"-"+skuOptionValueId2, productSkuId+"");
				}
			}
			//只有一个选项时  且只有规格没有颜色
			else if(psov1 != null && psov1.size() > 0){
				for(int i = 0; i < psov1.size(); i++){
					Integer skuOptionValueId1 = psov1.get(i).getSkuOptionValueId();
					//取出sku的id
					Integer productSkuId = psov1.get(i).getProductSkuId();
					maps.put(skuOptionValueId1+"", productSkuId+"");
				}
			}
			//只有一个选项时  且只有颜色没有规格
			else if(psov2 != null && psov2.size() > 0){
				for(int i = 0; i < psov2.size(); i++){
					Integer skuOptionValueId2 = psov2.get(i).getSkuOptionValueId();
					//取出sku的id
					Integer productSkuId = psov2.get(i).getProductSkuId();
					maps.put(skuOptionValueId2+"", productSkuId+"");
				}
			}
		}
		//将数据转化成json格式		
		JSONObject jsonObj = new JSONObject();
		jsonObj.putAll(maps);
		String str = jsonObj.toString();
		//存储json格式的数据
		product.setProductSkuMapJson(str);
		
		//累加库存
		Integer inventoryCount = new Integer(0);
		Integer secKillInventoryCount = new Integer(0);
		for(ProductSku ps : productSkus){
			//TODO 取sku对应的库存
			//如果库存为空 默认为0
			Integer inventory = ps.getInventory();
			if(null == inventory){
				inventory = new Integer(0);
			}
			productSkuInventoryMap.put(ps.getProductSkuId(), inventory);
			if(ps.getInventory() != null){
				inventoryCount += ps.getInventory();
			}
			if(ps.getSecKillInventory() != null){
				secKillInventoryCount += ps.getSecKillInventory();
			}
		}
		//存放总库存
		product.setSaleInventoryCount(inventoryCount);
		//存放每个sku对应的库存json格式
		//将数据转化成json格式		
		JSONObject json = new JSONObject();
		json.putAll(productSkuInventoryMap);
		String sTemp = json.toString();
		product.setProductSkuInventoryMapJson(sTemp);
		
		//存放所有的  选项   例如：颜色，规格  等
		product.setSkuOptionValueMap(skuOptionValueMap);
		//存放所有sku的数据
		product.setProductSkuMap(maps);
		return product;
	}

	/* (non-Javadoc)
	 * @see com.ytoxl.module.uhome.uhomebase.service.ProductService#getProductInventoryCount(com.ytoxl.module.uhome.uhomebase.dataobject.Product)
	 */
	@Override
	public Integer getProductInventoryCount(Product product)
			throws UhomeStoreException {
		//累加库存
		Integer inventoryCount = new Integer(0);
		Integer secKillInventoryCount = new Integer(0);
		//获取sku信息
		List<ProductSku> productSkus = product.getProductSkus();
		for(ProductSku ps : productSkus){
			//如果库存为0  不统计
			if(ps.getInventory() != null){
				inventoryCount += ps.getInventory();
			}
			if(ps.getSecKillInventory() != null){
				secKillInventoryCount += ps.getSecKillInventory();
			}
		}
		return inventoryCount;
	}

	/* (non-Javadoc)
	 * @see com.ytoxl.module.uhome.uhomebase.service.ProductService#isPlanByProductSkuId(java.lang.Integer)
	 */
	@Override
	public boolean isPlanByProductSkuId(Integer productSkuId) throws UhomeStoreException {
		Boolean result = false;
		List<Plan> plans = productMapper.listPlansByProductSkuId(productSkuId);
		if(plans != null && plans.size() > 0){
			result = true;
		}
		Product product = productMapper.getProductByProductSkuId(productSkuId);
		//关联卖家如果卖家被禁用  前台显示  该商品 已售馨
		User u = userInfoMapper.getUserBySellerId(product.getSellerId());
		if(u != null && u.getStatus() != null && u.getStatus().intValue() == User.STATUS_UNABLE){
			//禁用  如果排期的是今天且当前时间是10点整 库存设置为  0
			result = false;
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see com.ytoxl.module.uhome.uhomebase.service.ProductService#getSimpleProductByProdcutId(java.lang.Integer)
	 */
	@Override
	public Product getProductByProdcutIdAndCurrentTime(Integer productId)
			throws UhomeStoreException {
		//查询在排期中的商品
		List<Product> products = productMapper.getProductByProductId(productId);
		//如果没有查询到产品返回空
		if(products == null || products.size() == 0){
			return null;
		}
		Product product = products.get(0);
		return product;
	}

	/* (non-Javadoc)
	 * @see com.ytoxl.module.uhome.uhomebase.service.ProductService#getUserProductHistory(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Integer)
	 */
	@Override
	public List<Product> getUserProductHistory(HttpServletRequest request,
			HttpServletResponse response, Integer productId)
			throws UhomeStoreException {
		//获取用户浏览商品的记录
		String userProductHistory = CookieUtils.getCookie(request, com.ytoxl.module.uhome.uhomebase.common.CommonConstants.COOKIE_USER_PRODUCT_HISTORY);
		String[] str = userProductHistory.split(":");
		List<Product> products = new ArrayList<Product>();
		if(str != null && str.length > 0){
			for(String s :str){
				if("".equals(s.trim())){
					continue;
				}
				//TODO
				Product p = getProductByProdcutIdAndCurrentTime(Integer.parseInt(s));
				if(p == null){
					continue;
				}
				products.add(p);
			}
		}
		//TODO
		//System.out.println("@@@@@@@@@@@@@@@@@@products:"+products);
		//如果大于规定的个数截取
		if(products.size() >= userProductHistoryNum){
			str = Arrays.copyOfRange(str, str.length-userProductHistoryNum, str.length-1);
			//清空userProductHistory
			userProductHistory = "";
			for(String s : str ){
				if("".equals(s.trim())){
					continue;
				}
				userProductHistory += s + ":";
			}
		}
		//添加现在浏览的商品     商品id之间以：隔开
		userProductHistory = productId + ":" + userProductHistory;
		//如果已经包含了浏览的记录  不再向浏览记录中添加
		for(String s : str){
			if(s.equals(String.valueOf(productId))){
				return products;
			}
		}
		CookieUtils.addCookie(response,com.ytoxl.module.uhome.uhomebase.common.CommonConstants.COOKIE_USER_PRODUCT_HISTORY, userProductHistory);
		return products;
	}

	/* (non-Javadoc)
	 * @see com.ytoxl.module.uhome.uhomebase.service.ProductService#getProductByProductId4FrontSecKillDetail(com.ytoxl.module.uhome.uhomebase.dataobject.Product)
	 */
	@Override
	public Product getProductByProductId4FrontSecKillDetail(Plan plan)
			throws UhomeStoreException {
		//根据排期id获取商品及诉sku信息
		Product product = planMapper.getPlanSecKillProductByPlanId(plan.getPlanId());
		//如果product为null直接返回
		if(product == null){
			return null;
		}
		ProductSku productSku = planMapper.getPlanSecKillProductSkuByPlanId(plan.getPlanId());
		List<ProductSku> productSkus = new ArrayList<ProductSku>();
		productSkus.add(productSku);
		product.setProductSkus(productSkus);
		//获取排期信息
		Plan p = planMapper.get(plan.getPlanId());
		List<Plan> plans = new ArrayList<Plan>();
		plans.add(p);
		product.setPlans(plans);
		//库存 
		product.setSecKillInventoryCount(productSku.getSecKillInventory());
		
		//关联卖家如果卖家被禁用  前台显示  该商品 已售馨  TODO
		User u = userInfoMapper.getUserBySellerId(product.getSellerId());
		if(u != null && u.getStatus() != null && u.getStatus().intValue() == User.STATUS_UNABLE){
			//禁用  如果排期的是今天且当前时间是10点整 库存设置为  0
			long l = p.getStartTime().getTime() - System.currentTimeMillis();
			if(l <= 0){
				product.setSecKillInventoryCount(0);
			}
			product.setIsSellerOff(true);
		}
		
		//获取每个sku对应的sku_option_value
		List<ProductSkuOptionValue> psovs = productSkuOptionValueMapper.listProductSkuOptionValuesByProductSkuId(productSku.getProductSkuId());
		productSku.setProductSkuOptionValues(psovs);
		
		//存放颜色-->对应所有的可能,规格所有的可能
		Map<String,List<ProductSkuOptionValue>> productSkuOptionValueMap = new HashMap<String,List<ProductSkuOptionValue>>();
		
		//存放颜色-->红色，黄色，蓝色      规格-->190/90,180/80 等
		Map<String,Set<SkuOptionValue>> skuOptionValueMap = new HashMap<String,Set<SkuOptionValue>>();
		
		//列出所有的 选项   例如：颜色，规格  等
		List<SkuOption> skuOptions = skuOptionMapper.listSkuOptions();
		
		//遍历skuOptions 将选项的值分类
		for(SkuOption sp : skuOptions){
			List<ProductSkuOptionValue> proSkuOptValue = new ArrayList<ProductSkuOptionValue>();
			Set<SkuOptionValue> skuOV = new HashSet<SkuOptionValue>();
			for(ProductSkuOptionValue psov : productSku.getProductSkuOptionValues()){
				//若skuOptionValue中的skuOptionId与skuOption中的skuOptionId相等
				if(sp.getSkuOptionId().equals(psov.getSkuOptionValue().getSkuOptionId())){
					proSkuOptValue.add(psov);
					String override = psov.getOverrideSkuOptionValue();
					//如果系统的选项值被修改了,系统默认的值被覆盖 TODO
					if(override != null){
						psov.getSkuOptionValue().setSkuOptionValue(override);
					}
					skuOV.add(psov.getSkuOptionValue());
				}
			}
			productSkuOptionValueMap.put(sp.getSkuOptionName(), proSkuOptValue);
			skuOptionValueMap.put(sp.getSkuOptionName(), skuOV);
		}
		
		
		//TODO SkuOptionValue对应的数据封装成map
		Map<String,String> maps = new HashMap<String,String>();
		
		for(Iterator<String> ite = productSkuOptionValueMap.keySet().iterator();ite.hasNext();){
			List<ProductSkuOptionValue> psov1=productSkuOptionValueMap.get(ite.next());
			List<ProductSkuOptionValue> psov2=productSkuOptionValueMap.get(ite.next());
			//2个选项都有 颜色 规格
			if(psov2 != null && psov1 != null &&psov1.size()>0 && psov2.size()>0){
				//选一个大的选项
				int pTemp = (psov1.size() > psov2.size()) ? (psov1.size()) : (psov2.size()) ;
				for(int i = 0; i < pTemp; i++){
					//取出SkuOptionValue的id
					Integer skuOptionValueId1 = null;
					Integer skuOptionValueId2 = null;
					Integer productSkuId = null;
					if(psov1.size() >= i){
						skuOptionValueId1 = psov1.get(i).getSkuOptionValueId();
						//取出sku的id
						productSkuId = psov1.get(i).getProductSkuId();
					}
					if(psov2.size() >= i){
						skuOptionValueId2 = psov2.get(i).getSkuOptionValueId();
						if(productSkuId == null){
							productSkuId = psov2.get(i).getProductSkuId();
						}
					}
					maps.put(skuOptionValueId1+"-"+skuOptionValueId2, productSkuId+"");
				}
			}
			//只有一个选项时  且只有规格没有颜色
			else if(psov1 != null && psov1.size() > 0){
				for(int i = 0; i < psov1.size(); i++){
					Integer skuOptionValueId1 = psov1.get(i).getSkuOptionValueId();
					//取出sku的id
					Integer productSkuId = psov1.get(i).getProductSkuId();
					maps.put(skuOptionValueId1+"", productSkuId+"");
				}
			}
			//只有一个选项时  且只有颜色没有规格
			else if(psov2 != null && psov2.size() > 0){
				for(int i = 0; i < psov2.size(); i++){
					Integer skuOptionValueId2 = psov2.get(i).getSkuOptionValueId();
					//取出sku的id
					Integer productSkuId = psov2.get(i).getProductSkuId();
					maps.put(skuOptionValueId2+"", productSkuId+"");
				}
			}
		}
		
		//将数据转化成json格式		
		JSONObject jsonObj = new JSONObject();
		jsonObj.putAll(maps);
		String str = jsonObj.toString();
		//存储json格式的数据
		product.setProductSkuMapJson(str);
		
		//存放所有的  选项   例如：颜色，规格  等
		product.setSkuOptionValueMap(skuOptionValueMap);
		//存放所有sku的数据
		product.setProductSkuMap(maps);
		
		//判断颜色 规格下是否有内容 TODO  后面可以分开  前台有颜色 显示颜色  有规格 显示规格
		for(Iterator<String> ite = skuOptionValueMap.keySet().iterator();ite.hasNext();){
			boolean b = false; // 默认是没有
			Set<SkuOptionValue> skuOptionValue = skuOptionValueMap.get(ite.next());
			if(skuOptionValue.size() > 0){
				b = true;
				product.setIsSkuOptionValue(b);
				break;
			}
		}
		
		return product;
	}

	/* (non-Javadoc)
	 * @see com.ytoxl.module.uhome.uhomebase.service.ProductService#updateProductHits(com.ytoxl.module.uhome.uhomebase.dataobject.Product)
	 */
	@Override
	public void updateProductHits(Product product) throws UhomeStoreException {
		if(product.getHits() == null || product.getHits() <= 0){
			product.setHits(1);
		}else{
			product.setHits(product.getHits()+1);
		}
		productMapper.updateProductHits(product);
	}

	/* (non-Javadoc)
	 * @see com.ytoxl.module.uhome.uhomebase.service.ProductService#getOutOfStockProductByProductId(com.ytoxl.module.uhome.uhomebase.dataobject.Product)
	 */
	@Override
	public Product getOutOfStockProductByProductId(Integer productId)
			throws UhomeStoreException {
		Product p = productMapper.get(productId);
		if(p != null){
			//设置商品已下架属性
			p.setIsOutOfStock(true);
			//获取设置相应的品牌信息
			Brand brand = brandMapper.getBrandById(p.getBrandId());
			p.setBrand(brand);
			return p;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ytoxl.module.uhome.uhomebase.service.ProductService#searchImportProducts4Front(com.ytoxl.module.core.common.pagination.BasePagination)
	 */
	@Override
	public void searchImportProducts4Front( BasePagination<Product> productPage) throws UhomeStoreException {
		//url转化  将相应的数字转化成对应的字符  sort dir  TODO
		String sort = productPage.getSort();
		String dir = productPage.getDir();
		if("100000".equals(dir)){
			productPage.setDir("asc");
		}else if("100001".equals(dir)){
			productPage.setDir("desc");
		}
		for(Entry<String,String> entry:Product.SORTSMAP.entrySet()){
			if(entry.getKey().equals(sort)){
				productPage.setSort(entry.getValue());
			}
		}
		//设置每页显示的数量 TODO
		productPage.setLimit(defaultLimit);
		//替换具体的正则表达式
		productPage.setFilterParamReg(filterParamReg);
		Map<String, Object> searchParams = productPage.getSearchParamsMap();
		if(productPage.isNeedSetTotal()){
			Integer total = productMapper.searchImportProducts4FrontCount(searchParams);
			productPage.setTotal(total);
		}
		
		Collection<Product> result;
		
		//如果是按销售数量排序  则执行另外一条 sql
		if(Product.SALESQUANTITY.equals(productPage.getSort())){
			//TODO
			result = productMapper.searchImportProdcuts4FrontBySalesQuantity(searchParams);
		}else{
			result = productMapper.searchImportProducts4Front(searchParams);
		}
		if(result != null){
			//商品获取品牌属性
			for(Product product : result){
				//关联卖家如果卖家被禁用  前台显示  该商品 已售馨  TODO
				User u = userInfoMapper.getUserBySellerId(product.getSellerId());
				if(u != null && u.getStatus() != null && u.getStatus().intValue() == User.STATUS_UNABLE){
					//禁用
					product.setIsSellerOff(true);
				}
				
				//Brand brand = brandMapper.getBrandByBrandId(product.getBrandId());
				Brand brand = brandMapper.getBrandById(product.getBrandId());
				product.setBrand(brand);
				//获取此商品的排期 理论上当前时间内一件商品只属于一个排期  
				List<Plan> plans = productMapper.listPlansByProductIdAndCurrentTime(product.getProductId());
				product.setPlans(plans);
				//获取sku信息
				List<ProductSku> productSkus = productMapper.getProductSkuListByProductId(product.getProductId());
				product.setProductSkus(productSkus);
				//设置商品库存
				product.setSaleInventoryCount(getProductInventoryCount(product));
			}
		}
		productPage.setResult(result);
	}

	/* (non-Javadoc)
	 * @see com.ytoxl.module.uhome.uhomebase.service.ProductService#getBrandsForBasePagination(com.ytoxl.module.core.common.pagination.BasePagination)
	 */
	@Override
	public List<Brand> getBrandsSearchAndImport(Short type,String keyWord) throws UhomeStoreException {
		List<Brand> brands = null;
		if(Product.PAGE_TYPE_IMPORT == type){
			//进口商品页面
			brands = productMapper.listImportProductBrands4Front();
		}
		if(Product.PAGE_TYPE_SEARCH == type){
			//搜索商品页面
			brands = productMapper.listSearchProductBrands4Front(keyWord);
		}
		return brands;
	}

	/* (non-Javadoc)
	 * @see com.ytoxl.module.uhome.uhomebase.service.ProductService#batchUploadProduct(java.io.File, java.lang.Integer)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
	public Map<String,String> batchUploadProduct(File file, String fileName,Integer sellerId)throws UhomeStoreException {
		//获取买家id
		//Integer sellerId = userInfoService.getCurrentSellerId();
		
		Map<String,String> returnUploadResult = new HashMap<String,String>();
		
		byte[] fileByte;
		try {
			//将上传的临时文件读到byte数组
			fileByte = FileUtils.readFileToByteArray(file);
		} catch (IOException e) {
			logger.error("上传zip文件出错!",e.getMessage());
			throw new UhomeStoreException("上传zip文件出错");
		}
		//String fileUri = fileHessianServiceClient.createFile(fileByte, true, fileName, project, "zip", null, this.dataPatten);
		try {
			String filefix = getFileFix(fileName);
			String times = DateUtil.toSecond(new Date());
			String newFilePath = this.zipUploadPath+File.separator+times+filefix;
			//复制上传的文件到指定目录
			FileUtils.writeByteArrayToFile(new File(newFilePath), fileByte);
			ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(new File(newFilePath)));
			//以文件名为解压文件夹  并创建此目录
			String outputDir = this.zipUploadPath+File.separator+times;
			File f = new File(outputDir);
			f.mkdir();
			//提示 上传成功多少商品 多少sku
			//1.获取zip压缩文件
			//解压到指定的目录
			//2.service判断是否是zip文件,并解压zip文件
			UncompressZip.unzip(zipInputStream, outputDir);
			//获取压缩文件中的excel
			String excelName = getExcelName(new File(outputDir));
			//3.获取excel数据并验证  判断是否是同一个product 如果有哪行数据不正确立即终止导入
//			String excelName = this.zipUploadPath+File.separator+times+File.separator+"import-products.xlsx";
			List<String[]> excelDataList = getExcelData(new File(excelName));
			//excel数据为空
			if(null == excelDataList || excelDataList.size() == 0){
				returnUploadResult.put("info", "excel数据为空!");
				return returnUploadResult;
			}
			//记录错误信息
			List<String> errorform = new ArrayList<String>();
			List<Product> products = validatorExcelData(excelDataList,errorform,sellerId);
			//返回错误信息
			if(errorform.size() > 0){
				String errorInfo = "";
				for(String s : errorform){
					errorInfo += s + "\n";
				}
				returnUploadResult.put("info", errorInfo);
				return returnUploadResult;
			}
			//4.将数据封装成product
			//5.找到excel中图片的图片名称,并上传
			//6.找到excel中对商品描述的图片,并上传替换成img
			uploadProductImage(products, times);
			//7.保存到数据库 TODO 设置卖家
			int pCount = 0;
			int skuCount = 0;
			for(Product product:products){
				product.setSellerId(sellerId);
//				SpringContextUtils.getContext().getBean(ProductService.class).saveProduct(product);
				saveProduct(product);
				pCount++;
				skuCount += product.getProductSkus().size();
			}
			returnUploadResult.put("info", "成功上传:【"+pCount+"】商品,【"+skuCount+"】sku");
		}catch (IOException e) {
			logger.error("读取zip文件出错!",e.getMessage());
			throw new UhomeStoreException("读取zip文件出错!");
		}
		return returnUploadResult;
	}
	
	/**
	 * 查找excel文件
	 * @param file
	 * @return
	 */
	private String getExcelName(File file){
		String excelName = "";
		String[] files = file.list();
		for(String str : files){
			if(str.endsWith("xlsx")){
				excelName = str;
			}
		}
		return file.getPath()+File.separator+excelName;
	}
	
	/**
	 * 上传商品图片
	 * @param products
	 * @throws IOException 
	 */
	private void uploadProductImage(List<Product> products,String picPath) throws IOException{
		for(Product p : products){
			//找到商品的图片 并上传 修改新图片的名称
			List<String> list = p.getImageList();
			List<String> listImage = uploadProductImage(list,picPath,"product");
			p.setImageList(listImage);
			p.setImageUrls(listImage.toString().substring(1, listImage.toString().length()-1).replaceAll(" ", ""));
			
			//上传描述图片
			List<String> describeList = new ArrayList<String>();
			String describe = p.getDescribe();
			Pattern pattern = Pattern.compile("\\[image\\](.*?)\\[/image\\]");
			Matcher m = pattern.matcher(describe);
			while(m.find()){
//				System.out.println(m.group().replaceAll("\\[image\\]", "").replaceAll("\\[/image\\]", ""));
				String str = m.group().replaceAll("\\[image\\]", "").replaceAll("\\[/image\\]", "");
				describeList.add(str);
			}
			List<String> newDescribeList = uploadProductImage(describeList, picPath, "productDescribe");
			//替换原有的[image]imageName[/image]文本
			if(describeList.size() == newDescribeList.size()){
				for(int i = 0; i<describeList.size(); i++){
					String regex = "\\[image\\]"+describeList.get(i)+"\\[/image\\]";
					String newImagePath = this.filePath+newDescribeList.get(i).trim();
					String replacement = "<img src=\""+newImagePath+"\"/>";
					describe = describe.replaceAll(regex, replacement);
				}
			}
			p.setDescribe(describe);
		}
	}
	
	/**
	 * 调用FileHessianServiceClient实现上传图片
	 * @param list
	 * @param picPath
	 * @param category
	 * @return
	 * @throws IOException
	 */
	private List<String> uploadProductImage(List<String> list,String picPath,String category) throws IOException{
		List<String> newList = new ArrayList<String>();
		for(String str : list){
			String imagePath = this.zipUploadPath+File.separator+picPath+str.trim();
			File file = new File(imagePath);
			byte[] fileByte = FileUtils.readFileToByteArray(file);
//			String fileUri = fileHessianServiceClient.createFile(fileByte, false, str, project, category, null, this.dataPatten);
			String fileUri = SpringContextUtils.getContext().getBean(FileHessianService.class).createFile(fileByte, false, str, project, category, null, this.dataPatten);
			newList.add(fileUri);
		}
		return newList;
	}
	
	/**
	 * 验证excel数据并封装成初始的product
	 * @param list
	 */
	private List<Product> validatorExcelData(List<String[]> list,List<String> errorform,Integer sellerId){
		//记录excel数据格式错误
//		List<String> errorform = new ArrayList<String>();
		List<Product> products = new ArrayList<Product>();
//		for(String[] strArr : list){
		for(int i = 0; i < list.size(); i++){
			String[] strArr = list.get(i);
			if(strArr.length == 20){
				Product p = new Product();
				//商品编号
				p.setProductNo(strArr[0].trim());
				if(StringUtils.isEmpty(p.getProductNo())){
					errorform.add("第【"+(i+1)+"】行:商品编号不能为空!");
					continue;
				}
				//判断List中是否有这个商品 有的话 只导入sku信息
				Product oldProduct = isProductRepeat(products,p.getProductNo());
				if(null != oldProduct){
					//重复 只用添加sku信息 
					ProductSku sku = getExcelProductSku(strArr,oldProduct);
					if(null == sku){
						errorform.add("第【"+(i+1)+"】行:系统默认的颜色或者规格不够!");
						continue;
					}
//					List<ProductSku> skus = new ArrayList<ProductSku>();
//					skus.add(sku);
//					oldProduct.setProductSkus(skus);
					oldProduct.getProductSkus().add(sku);
				}else{
					//没有重复添加商品+sku信息
					//商品品牌通过品牌名称查询id
					Brand b = new Brand();
					b.setName(strArr[1]);
					p.setBrand(b);
					if(StringUtils.isEmpty(p.getBrand().getName())){
						errorform.add("第【"+(i+1)+"】行:品牌名称不能为空!");
						continue;
					}
					//查询商家可以销售那些品牌 TODO
					List<Brand> canSellBrands = brandMapper.getBrandListBySeller(sellerId);
					List<Brand> brands = brandMapper.searchBrandByName(p.getBrand().getName().trim());
					if(canSellBrands!= null && canSellBrands.containsAll(brands)){
						if(null != brands && brands.size() > 0){
							p.setBrandId(brands.get(0).getBrandId());
						}else{
							errorform.add("第【"+(i+1)+"】行:数据库中没有【"+p.getBrand().getName()+"】这个品牌!");
							continue;
						}
					}else{
						errorform.add("第【"+(i+1)+"】行:此【"+p.getBrand().getName()+"】品牌不在你的可售品牌内!");
						continue;
					}
					
					
					//商品品类 通过品类名称查询id
					ProductCategory pc = new ProductCategory();
					pc.setName(strArr[2]);
					p.setProductCategory(pc);
					if(StringUtils.isEmpty(p.getProductCategory().getName())){
						errorform.add("第【"+(i+1)+"】行:商品品类不能为空!");
						continue;
					}
					pc = productCategoryMapper.getProductCategoryByName(p.getProductCategory().getName().trim());
					if(null != pc.getProductCategoryId()){
						p.setProductCategoryId(pc.getProductCategoryId());
					}else{
						errorform.add("第【"+(i+1)+"】行:数据库中没有【"+p.getProductCategory().getName()+"】这个品类!");
						continue;
					}
					
					//商品名称
					p.setName(strArr[3]);
					if(StringUtils.isEmpty(p.getName())){
						errorform.add("第【"+(i+1)+"】行:商品名称不能为空!");
						continue;
					}
					//商品的keyWord
					p.setKeyWord(p.getName());
					
					//价格
					if(StringUtils.isEmpty(strArr[4])){
						errorform.add("第【"+(i+1)+"】行:商品原价不能为空!");
						continue;
					}
					BigDecimal marketPrice = new BigDecimal(strArr[4].trim());
					p.setMarketPrice(marketPrice);
					if(StringUtils.isEmpty(strArr[5])){
						errorform.add("第【"+(i+1)+"】行:商品特价格不能为空!");
						continue;
					}
					BigDecimal salePrice = new BigDecimal("0");
					if(StringUtils.isNotEmpty(strArr[5])){
						salePrice = new BigDecimal(strArr[5].trim());
						p.setSalePrice(salePrice);
					}
					if(StringUtils.isNotEmpty(strArr[6])){
						p.setSecKillPrice(new BigDecimal(strArr[6].trim()));
					}
					//折扣
					p.setRebate(salePrice.divide(marketPrice,2,BigDecimal.ROUND_HALF_EVEN).multiply(new BigDecimal(10)).doubleValue());
					//可售日期
					if(StringUtils.isNotEmpty(strArr[7])){
						String availableDate = strArr[7].trim();
						p.setSellStartTime(DateUtil.valueof(availableDate.split("-")[0], "yyyy/MM/dd"));
						p.setSellEndTime(DateUtil.valueof(availableDate.split("-")[1], "yyyy/MM/dd"));
					}
					//是否进口
					String isImport = strArr[8].trim();
					p.setIsImport(new Short("是".equals(isImport) ? (short)1 : (short)0));
					
					//========sku信息开始 TODO
					ProductSku sku = getExcelProductSku(strArr,null);
					List<ProductSku> skus = new ArrayList<ProductSku>();
					skus.add(sku);
					p.setProductSkus(skus);
					//========sku信息结束
					
					//商品图片 上传图片 
					if(StringUtils.isEmpty(strArr[15])){
						errorform.add("第【"+(i+1)+"】行:商品图片不能为空!");
						continue;
					}
					List<String> imageList = new ArrayList<String>();
					imageList.add(strArr[15].trim());
					if(StringUtils.isNotEmpty(strArr[16])){
						imageList.add(strArr[16].trim());
					}
					if(StringUtils.isNotEmpty(strArr[17])){
						imageList.add(strArr[17].trim());
					}
					if(StringUtils.isNotEmpty(strArr[18])){
						imageList.add(strArr[18].trim());
					}
					String imageStr = imageList.toString();
					p.setImageList(imageList);
					p.setImageUrls(imageStr.substring(1, imageStr.length()-1));
					//商品描述 查找图片并上传
					p.setDescribe(strArr[19]);
					//商品状态  待审核
					p.setStatus(Product.STATUS_CHECK_PEND);
					//存到list中
					products.add(p);
				}
			}
		}
		return products;
	}
	
	/**
	 * 判断同意个excel中是否有同一个商品(不是sku) 如果重复 说明是同一个商品不同sku
	 * @param products
	 * @param productNo
	 * @return
	 */
	private Product isProductRepeat(List<Product> products,String productNo){
		for(Product p : products){
			if(p.getProductNo().equals(productNo)){
				return p;
			}
		}
		return null;
	}
	
	/**
	 * 解析excel中的sku信息
	 * @param strArr
	 * @return
	 */
	private ProductSku getExcelProductSku(String[] strArr,Product oldProduct){
		ProductSku ps = new ProductSku();
		//颜色和尺码还要通过查询获取对应的id 如果没有 overrideSkuOptionValue 并设置第一个诉skuOptionValueId
		List<ProductSkuOptionValue> psovs = new ArrayList<ProductSkuOptionValue>();
		if(null != strArr[9] && StringUtils.isNotEmpty(strArr[9])){
			ps.setProductSkuColor(strArr[9].trim());
			ProductSkuOptionValue psov = getProductSkuOptionValue(ps.getProductSkuColor(), "颜色",oldProduct);
			if(null == psov){
				return null;
			}
			psovs.add(psov);
		}
		if(null != strArr[10] && StringUtils.isNotEmpty(strArr[10])){
			ps.setProductSkuSize(strArr[10].trim());
			ProductSkuOptionValue psov = getProductSkuOptionValue(ps.getProductSkuSize(), "尺码/规格",oldProduct);
			if(null == psov){
				return null;
			}
			psovs.add(psov);
		}
		if(StringUtils.isEmpty(strArr[11])){
			strArr[11] = "0";
		}
		//特卖库存
		ps.setInventory(new Double(strArr[11]).intValue());
		if(StringUtils.isEmpty(strArr[12])){
			strArr[12] = "0";
		}
		//秒杀库存
		if(StringUtils.isNotEmpty(strArr[12])){
			ps.setSecKillInventory(new Double(strArr[12]).intValue());
		}
		//国际码
		ps.setInternationalCode(strArr[13]);
		//根据国际码查询是否重复 //TODO
		ps.setSkuCode(strArr[14]);
		//设置颜色规格信息
		ps.setProductSkuOptionValues(psovs);
		return ps;
	}
	
	/**
	 * 根据所填写的颜色或者尺码规格查询skuOptionValueId 并封装成ProductSkuOptionValue
	 * @param skuOptionValue
	 * @param skuOption
	 * @return
	 */
	private ProductSkuOptionValue getProductSkuOptionValue(String skuOptionValue,String skuOption,Product oldProduct){
		SkuOptionValue sov = skuOptionValueMapper.getSkuOptionValuesBySkuOptionValue(skuOptionValue);
		//如果数据库没有这个规格  设置一个overrideSkuOptionValue
		ProductSkuOptionValue psov = new ProductSkuOptionValue();
		if(null == sov){
			//此颜色和规格在数据库中没有    获取 所有的颜色或者规格
			if(null == oldProduct){
				sov = skuOptionValueMapper.getFirstSkuOptionValues(skuOption);
			}else{
				//如果已经有的sku中有颜色或者尺码    去除重复
				//获取已经有的颜色或者尺码
				String skuOptionValues = "";
				for(ProductSku ps : oldProduct.getProductSkus()){
					for(ProductSkuOptionValue psov2 : ps.getProductSkuOptionValues()){
						//如果这个颜色或者规格已经有了直接返回以前的
						if(skuOptionValue.equals(psov2.getOverrideSkuOptionValue())){
							psov.setSkuOptionValueId(psov2.getSkuOptionValueId());
							psov.setOverrideSkuOptionValue(skuOptionValue);
							return psov;
						}
						skuOptionValues += psov2.getSkuOptionValueId()+",";
					}
				}
				skuOptionValues = skuOptionValues.substring(0, skuOptionValues.length()-1);
				//TODO 获取此product中唯一没有的颜色或者尺码  如果 系统默认的尺码或者用完了  告诉用户
				sov = skuOptionValueMapper.getUniqueSkuOptionValues(skuOption, skuOptionValues);
				if(null == sov){
					return null;
				}
			}
			psov.setSkuOptionValueId(sov.getSkuOptionValueId());
			psov.setOverrideSkuOptionValue(skuOptionValue);
		}else{
			psov.setSkuOptionValueId(sov.getSkuOptionValueId());
		}
		return psov;
	}
	
	/**
	 * 获取excel数据
	 * @param file
	 * @return
	 * @throws UhomeStoreException 
	 */
	private List<String[]> getExcelData(File file) throws UhomeStoreException{
		List<String[]> list = null;
		ExcelUtils<Product> excel = new ExcelUtils<Product>();
		excel.setSheetName("Sheet1");
		try {
			list = excel.read(new FileInputStream(file));
		} catch (UhomeStoreException e) {
			throw new UhomeStoreException("读取excel出错!");
		} catch (FileNotFoundException e) {
			throw new UhomeStoreException("读取excel出错!");
		}
		return list;
	}
	
	/**
	 * 获取文件的后缀名
	 * @param fileName
	 * @return
	 * @throws UhomeStoreException
	 */
	private String getFileFix(String fileName) throws UhomeStoreException{
		String dot = ".";
		int lastDotIndex = fileName.lastIndexOf(dot);
		if(lastDotIndex == -1){
			throw new UhomeStoreException("上传的zip文件后缀名不对!");
		}
		String filefix = fileName.substring(lastDotIndex);
		return filefix;
	}

	public static String cutString(String str,Integer len){
//		return StringUtils.multiSubStr(str, len.intValue());
		String s = "";
		if(StringUtils.isNotEmpty(str) && str.length() > len.intValue()){
			s = str.substring(0, len);
			s += "...";
		}else{
			s = str;
		}
		return s;
	}

	@Override
	public List<Product> getRecommendByProductCategoryId(Integer productCategoryId) throws UhomeStoreException {
		//是否获取对应商品的sku TODO
		return productMapper.getRecommendByProductCategoryId(productCategoryId);
	}

	@Override
	public Postage getProductPostageByProductId(Product product) throws UhomeStoreException {
		Postage postage = null;
		Integer outId = null;
		//1.先看此排期是否收运费
		outId = product.getPlans().get(0).getPlanId();
		postage = postageMapper.getPostageByOutIdAndType(outId, Postage.TYPE_PLAN);
		if(null == postage){
			//2.如果排期不收运费 再看商品是否收运费
			outId = product.getProductId();
			postage = postageMapper.getPostageByOutIdAndType(outId, Postage.TYPE_PRODUCT);
			
		}
		return postage;
	}
}
