package com.ytoxl.module.uhome.uhomebase.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomebase.dataobject.Brand;
import com.ytoxl.module.uhome.uhomebase.dataobject.Plan;
import com.ytoxl.module.uhome.uhomebase.dataobject.Product;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductSku;
import com.ytoxl.module.uhome.uhomebase.dataobject.SkuOption;
import com.ytoxl.module.uhome.uhomebase.dataobject.SkuOptionValue;

/**
 * 产品
 * @author user
 *
 * @param <T>
 */
public interface ProductMapper<T extends Product> extends BaseSqlMapper<T> {
	
	/**
	 * 查询一条卖家商品信息 
	 * @param productId
	 * @param sellerId
	 */
	public Product getProductByProductIdAndSellerId(@Param("productId") Integer productId, @Param("sellerId") Integer sellerId) throws DataAccessException;
	
	/**
	 * 卖家更新商品状态
	 * @param product
	 */
	public void updateProductStatus(Product product) throws DataAccessException;
	
	/**
	 * 卖家更新商品名称
	 * @param product
	 */
	public void updateProductName(Product product) throws DataAccessException;
	
	/**
	 * 更新商品审核结果
	 * @param product
	 */
	public void updateProductReviewResult(Product product) throws DataAccessException;
	
	/**
	 *  卖家后台搜索查询商品
	 * @param map
	 * @return
	 */
	public List<Product> searchSellerProducts(Map<String, Object> map) throws DataAccessException;
	public Integer searchSellerProductsCount(Map<String, Object> map) throws DataAccessException;
	
	/**
	 *  管理员后台搜索查询商品
	 * @param map
	 * @return
	 */
	public List<Product> searchProducts(Map<String, Object> map) throws DataAccessException;
	public Integer searchProductsCount(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 查询上一条记录
	 * @param productId
	 * @return
	 */
	public Integer getPreviousProductId(Integer productId) throws DataAccessException;
	
	/**
	 * 查询下一条记录
	 * @param productId
	 * @return
	 */
	public Integer getNextProductId(Integer productId) throws DataAccessException;
	
	/**
	 * 查询下一条待审核商品
	 * @param productId
	 * @return
	 */
	public Integer getNextPendingProduct(Integer productId) throws DataAccessException;
	
	/**
	 * 前台搜索查询  通过关键字查询 商品
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<Product> searchProducts4Front(Map<String, Object> map) throws DataAccessException;
	public Integer searchProducts4FrontCount(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 前台进口商品
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<Product> searchImportProducts4Front(Map<String, Object> map) throws DataAccessException;
	public List<Product> searchImportProdcuts4FrontBySalesQuantity(Map<String, Object> map) throws DataAccessException;
	public Integer searchImportProducts4FrontCount(Map<String, Object> map)throws DataAccessException;
	/**
	 * 获取进口商品的品牌
	 * @return
	 * @throws DataAccessException
	 */
	public List<Brand> listImportProductBrands4Front() throws DataAccessException;
	
	/**
	 * 获取搜索商品的品牌
	 * @param keyWord
	 * @return
	 * @throws DataAccessException
	 */
	public List<Brand> listSearchProductBrands4Front(String keyWord) throws DataAccessException;
	
	/**
	 * 按销售数量排序
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<Product> searchProducts4FrontBySalesQuantity(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 品牌详细页面的热卖商品  要分页
	 * @param brandId
	 * @return
	 * @throws DataAccessException
	 */
	public List<Product> searchProducts4FrontByBrandId(Map<String, Object> map) throws DataAccessException;
	public Integer searchProducts4FrontByBrandIdCount(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 根据productId查询商品SKU选项数据
	 * @param productId
	 * @return
	 */
	public List<SkuOption> listProductSkuOptionsByProductId(Integer productId) throws DataAccessException;
	
	/**
	 * 根据skuOptionId查询商品SKU选项值数据
	 * @param skuOptionId
	 * @return
	 */
	public List<SkuOptionValue> listProductSkuOptionValuesBySkuOptionId(
			@Param("skuOptionId") Integer skuOptionId,
			@Param("productId") Integer productId) throws DataAccessException;
	
	/**
	 * 根据productId查询排期
	 * @param productIds
	 * @return
	 */
	public List<Plan> listPlansByProductIds(List<Integer> productIds) throws DataAccessException;
	
	/**
	 * 根据planId查询商品
	 * @param planId
	 */
	public List<Product> listProductsByPlanId(Integer planId) throws DataAccessException;
	
	/**
	 * 根据卖家ID与品牌ID查询可参加特卖活动的商品
	 * @param sellerId
	 * @param brandId
	 * @return
	 */
	public List<Product> listSpecialSellProductsBysellerId(@Param("startTime")Date startTime, 
			@Param("endTime")Date endTime, @Param("sellerId") Integer sellerId, @Param("brandId") Integer brandId,
			@Param("productCategoryId") Integer productCategoryId, @Param("planId") Integer planId) throws DataAccessException;
	
	/**
	 * 根据productId查询当前时间所在的排期  前台搜索页面和品牌热卖商品列表用
	 * @param productId
	 * @return
	 * @throws DataAccessException
	 */
	public List<Plan> listPlansByProductIdAndCurrentTime(Integer productId) throws DataAccessException;
	
	/**
	 * 通过productId 查询对应所有的sku
	 * @param productId
	 * @return
	 * @throws DataAccessException
	 */
	public List<ProductSku> getProductSkuListByProductId(Integer productId) throws DataAccessException;
	
	/**
	 * 通过productSkuId 查询对应的product
	 * @param productSkuId
	 * @return
	 * @throws DataAccessException
	 */
	public Product getProductByProductSkuId(Integer productSkuId) throws DataAccessException;
	
	/**
	 * 按点击率查询商品 
	 * @param maxNum
	 * @return
	 * @throws DataAccessException
	 */
	public List<Product> getProductListByHits(Integer maxNum) throws DataAccessException;
	
	/**
	 * 优惠最多的商品
	 * @param maxNum
	 * @return
	 * @throws DataAccessException
	 */
	public List<Product> getProductListByMostCheap(Integer maxNum) throws DataAccessException;
	
	/**
	 * 通过品牌的id查询和当前时间查询在排期内的商品  前台品牌详情用
	 * @param prodcutId
	 * @return
	 * @throws DataAccessException
	 */
	public List<Product> getProductsByBrandIdAndCurrentTime(Integer brandId)throws DataAccessException;
	
	/**
	 * 根据skuId  查询排期信息
	 * @param productSkuId
	 * @return
	 * @throws DataAccessException
	 */
	public List<Plan> listPlansByProductSkuId(Integer productSkuId)throws DataAccessException;
	
	/**
	 * 根据商品的id查询商品  并且  商品在排期内  通过审核的      正常应该返回prodcut  改写成List<Product>防止运营出错 
	 * @param productId
	 * @return
	 * @throws DataAccessException
	 */
	public List<Product> getProductByProductId(Integer productId)throws DataAccessException;
	
	/**
	 * 根据商品的id 将商品的 hits加一   商品详情页面用
	 * @param product
	 * @throws DataAccessException
	 */
	public void updateProductHits(Product product) throws DataAccessException;
	
	/**
	 *  根据类目查询在排期中的推荐商品
	 * @param productCategoryId
	 * @return
	 * @throws DataAccessException
	 */
	public List<Product> getRecommendByProductCategoryId(Integer productCategoryId) throws DataAccessException;
	/**
	 * 根据品牌Id查询该品牌下的商品
	 * @param brandId
	 * @return
	 * @throws DataAccessException
	 */
	public List<Product> listProductsByBrandId(Integer brandId) throws DataAccessException;
}
