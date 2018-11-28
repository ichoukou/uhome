package com.ytoxl.module.uhome.uhomebase.service.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import com.ytoxl.module.core.common.constant.CommonConstants;
import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.common.utils.StringUtils;
import com.ytoxl.module.uhome.uhomebase.dataobject.Brand;
import com.ytoxl.module.uhome.uhomebase.dataobject.Plan;
import com.ytoxl.module.uhome.uhomebase.dataobject.Product;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductSku;
import com.ytoxl.module.uhome.uhomebase.dataobject.Seller;
import com.ytoxl.module.uhome.uhomebase.dataobject.UserInfo;
import com.ytoxl.module.uhome.uhomebase.mapper.BrandMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.PlanMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.ProductMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.UserInfoMapper;
import com.ytoxl.module.uhome.uhomebase.service.BrandService;
import com.ytoxl.module.uhome.uhomebase.service.ProductService;
import com.ytoxl.module.user.dataobject.User;

/**
 * @author wangguoqing
 *
 */

@Service
public class BrandServiceImpl implements BrandService {
	
	@Autowired
	private BrandMapper<Brand> brandMapper;
	@Autowired
	private ProductMapper<Product> productMapper;
	@Autowired
	private ProductService productService;
	@Autowired
	private PlanMapper<Plan> planMapper;
	@Autowired
	private UserInfoMapper<UserInfo> userInfoMapper;
	
	@Value("${hotBrandNum}")
	private Integer hotBrandNum;//热卖品牌个数
	@Value("${hotBrand4BrandDetailNum}")
	private Integer hotBrand4BrandDetailNum;
	@Value("${subscribeBrandNum}")
	private Integer subscribeBrandNum;
	
	protected String filterParamReg = "[\\<\\>\\\"\\']";//需要过了掉param值中的字符  重定义  取出空格
	
	//自定义分页数量
	@Value("${limit_plan_products}")
	private Integer defaultLimit;

	@Override
	public Brand getBrandByBrandId(Integer brandId) throws UhomeStoreException {
		return brandMapper.getBrandByBrandId(brandId);
	}

	
	@Override
	public Integer updateBrandByBrandId(Brand brand) throws UhomeStoreException {
		brand.setBrandPinYin(brand.getBrandPinYin().toUpperCase());
		brand.setDescribe(HtmlUtils.htmlEscape(brand.getDescribe()));
		return brandMapper.update(brand);
	}

	/* (non-Javadoc)
	 * @see com.ytoxl.module.uhome.uhomebase.service.BrandService#addBrand(com.ytoxl.module.uhome.uhomebase.dataobject.Brand)
	 */
	@Override
	public Integer addBrand(Brand brand) throws UhomeStoreException {
		brand.setBrandPinYin(brand.getBrandPinYin().toUpperCase());
		brand.setDescribe(HtmlUtils.htmlUnescape(brand.getDescribe()));
		return  brandMapper.add(brand);
		
	}
	
	
	public String getPinyinName(String name) throws UhomeStoreException {
//		if(StringUtils.isEmpty(name)){
//			String englishName = brand.getEnglishName();
//			char ch = englishName.charAt(0);
//			if(Character.isLetter(ch) && Character.isLowerCase(ch)){
//				ch = Character.toUpperCase(ch);
//			}
//			return String.valueOf(ch);
//		}
//		
		String str = String.valueOf(name.charAt(0));
		if(!str.matches("[A-Za-z\\d\\u4E00-\\u9FA5]+")){
			return null;
		}
		//拼音工具类的转换类型
		HanyuPinyinOutputFormat format= new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
	 	format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
		
		StringBuffer strBuf = new StringBuffer();
		try {
			char ch = name.charAt(0);
			if(String.valueOf(ch).matches("[\\u4E00-\\u9FA5]+")){
				String pinyinArray[] = PinyinHelper.toHanyuPinyinStringArray(ch, format);
				for(String pinyinStr : pinyinArray){
					char firstLetter = pinyinStr.charAt(0);
					if(strBuf.indexOf(String.valueOf(firstLetter)) == -1){
						strBuf.append(firstLetter);
						strBuf.append(",");	
					}
				}
				strBuf.deleteCharAt(strBuf.length() - 1);
			}else{
				if(Character.isLetter(ch) && Character.isLowerCase(ch)){
					ch = Character.toUpperCase(ch);
				}
				strBuf.append(ch);
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			throw new UhomeStoreException(e.getMessage());
		}
		return  strBuf.toString();
	}
	
	
	
	@Override
	public void  searchBrands(BasePagination<Brand> brandPage)
			throws UhomeStoreException {
		Map<String, Object> searchParams = brandPage.getSearchParamsMap();
		searchParams.put(CommonConstants.PAGE_SORT, "a.createTime");
		searchParams.put(CommonConstants.PAGE_DIR, "desc");
		List<Brand> brans = brandMapper.searchBrands(searchParams);
		if(brandPage.isNeedSetTotal()){
			Integer total = brandMapper.searchBrandsTotal(searchParams);
			brandPage.setTotal(total);
		}
		brandPage.setResult(brans);
	}
	
	

	
	@Override
	public List<Brand> listBrands() throws UhomeStoreException {
		return brandMapper.getBrandList();
	}

	
	@Override
	public List<Brand> listHotBrands() throws UhomeStoreException {
		return brandMapper.getHotBrands(hotBrandNum);
	}

	@Override
	public List<Brand> listSubscribeBrands() throws UhomeStoreException {
		List<Brand> brands = brandMapper.getlistSubscribeBrands(subscribeBrandNum);
		return brands;
	}
	
	/**
	 * 查询卖家可售品牌
	 * @param sellerid
	 * @return
	 */
	@Override
	public List<Brand> listBrandsBySellerId(Integer sellerid)
			throws UhomeStoreException {
		return brandMapper.listBrandsBySellerId(sellerid);
	}

	
	@Override
	public Brand getBrandByBrandId4Front(Brand b)throws UhomeStoreException {
		Brand brand = brandMapper.get(b.getBrandId());
		return brand;
	}

	
	@Override
	public List<Brand> listHotBrands4FrontBrandDetail()
			throws UhomeStoreException {
		//TODO  品牌最低最高折扣
		return brandMapper.getHotBrands(hotBrand4BrandDetailNum);
	}
	
	/**
	 * 根据brandPinYin来查询品牌
	 * @return
	 * @throws UhomeStoreException
	 */
	@Override
	public List<Brand> listBrandsByBrandPinYin(Brand brand) throws UhomeStoreException {
		List<Brand> brands = brandMapper.listBrandsByBrandPinYin(brand);
		
		Seller seller = brand.getSeller();
		if(seller != null && seller.getSellerId() != null){
			List<Brand> selectedBrands = this.listBrandsBySellerId(seller.getSellerId());
			for (Iterator<Brand> iterator = brands.iterator(); iterator.hasNext();) {
				Brand brand1 = (Brand) iterator.next();
				for (Iterator<Brand> iterator2 = selectedBrands.iterator(); iterator2.hasNext();) {
					Brand brand2 = (Brand) iterator2.next();
					if(brand1.getBrandId().equals(brand2.getBrandId())){
						brand1.setIsChecked(true);
						break;
					}
				}
			}
		}
		
		return brands;
	}

	@Override
	public List<Brand> getDistinctList() throws UhomeStoreException {
		// TODO Auto-generated method stub
		return brandMapper.getBrandList();
	}
	

	
	@Override
	public void searchProductByBrandId(BasePagination<Product> productPage)
			throws UhomeStoreException {
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
			Integer total = productMapper.searchProducts4FrontByBrandIdCount(searchParams);
			productPage.setTotal(total);
		}
		
		Collection<Product> result;
		result = productMapper.searchProducts4FrontByBrandId(searchParams);
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
				if(brand != null){
					brand.setIsHistoryRecord(false); //不是历史销售记录
				}
				product.setBrand(brand);
				//获取此商品的排期 理论上当前时间内一件商品只属于一个排期  
				List<Plan> plans = productMapper.listPlansByProductIdAndCurrentTime(product.getProductId());
				product.setPlans(plans);
				//获取sku信息
				List<ProductSku> productSkus = productMapper.getProductSkuListByProductId(product.getProductId());
				product.setProductSkus(productSkus);
				//设置商品库存
				product.setSaleInventoryCount(productService.getProductInventoryCount(product));
			}
		}
		//如果在当前时间没有热卖的商品显示历史热卖商品
		if(result == null){
			Plan p = planMapper.getNearPlanByBrandId((Integer)searchParams.get("brandId"));
			if(p != null){
				//TODO  通过品牌id -- > 排期id --> 商品
				searchParams.put("planId", p.getPlanId());
				productPage.setTotal(planMapper.searchSpecialSellerPlansCount(searchParams));
				
				result = planMapper.searchPlanProductList4FrontByPlanId(searchParams);
				//商品获取品牌属性
				for(Product product : result){
					//Brand brand = brandMapper.getBrandByBrandId(product.getBrandId());
					Brand brand = brandMapper.getBrandById(product.getBrandId());
					brand.setIsHistoryRecord(true); //是历史销售记录
					product.setBrand(brand);
					//获取此商品的排期 理论上当前时间内一件商品只属于一个排期  
					//List<Plan> plans = productMapper.listPlansByProductIdAndCurrentTime(product.getProductId());
					//product.setPlans(plans);
					//获取sku信息
					List<ProductSku> productSkus = productMapper.getProductSkuListByProductId(product.getProductId());
					product.setProductSkus(productSkus);
					//设置商品库存
					product.setSaleInventoryCount(productService.getProductInventoryCount(product));
				}
			}
		}
		productPage.setResult(result);
	}
	@Override
	public Brand searchBrandByName(Brand brand) throws UhomeStoreException {
		return brandMapper.getBrandByName(brand);
	}
	
	@Override
	public List<Plan> listPlansByBrandIdAndEndTime(Integer brandId)
			throws UhomeStoreException {
		return planMapper.listPlansByBrandIdAndEndTime(brandId);
	}
	@Override
	public List<Product> listProductsByBrandId(Integer brandId)
			throws UhomeStoreException {
		return productMapper.listProductsByBrandId(brandId);
	}


	@Override
	public void updateIsForbbdenByBrandId(Integer brandId,Short isForbidden)
			throws UhomeStoreException {
		brandMapper.updateIsForbbdenByBrandId(brandId,isForbidden);
	}


	@Override
	public List<Brand> listBrandOrderBySort(Integer type) throws UhomeStoreException {
		List<Brand> brandList=brandMapper.getBrandListOrderBySort(type);
		return brandList;
	}
	
	@Override
	public List<Brand> listBrandBySort(Integer type) throws UhomeStoreException {
		List<Brand> brandList=brandMapper.getBrandListBySort(type);
		return brandList;
	}
}
