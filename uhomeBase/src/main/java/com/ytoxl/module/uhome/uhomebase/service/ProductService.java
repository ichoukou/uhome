package com.ytoxl.module.uhome.uhomebase.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Brand;
import com.ytoxl.module.uhome.uhomebase.dataobject.Plan;
import com.ytoxl.module.uhome.uhomebase.dataobject.Postage;
import com.ytoxl.module.uhome.uhomebase.dataobject.Product;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductCategory;
import com.ytoxl.module.uhome.uhomebase.dataobject.SkuOption;

/**
 * 商品
 * @author wangguoqing
 *
 */
public interface ProductService {
	
	/**
	 * 获取商品类别
	 * @return
	 */
	public List<ProductCategory> listProductCategories() throws UhomeStoreException;
	
	/**
	 * 获取商品子类别
	 * @return
	 */
	public List<ProductCategory> listChildProductCategories() throws UhomeStoreException;
	
	/**
	 * 获取SKU选项信息
	 * @return
	 */
	public List<SkuOption> getSkuOptions() throws UhomeStoreException;	
	
	/**
	 * 得到商品信息副本用于预览、复制
	 * @param product
	 * @return
	 */
	public Product getProductCopy(Product product) throws UhomeStoreException;
	
	/**
	 * 查询卖家商品
	 * @param productPage
	 * @param sellerId
	 */
	public void searchSellerProducts(BasePagination<Product> productPage, Integer sellerId) throws UhomeStoreException;
	
	/**
	 * 管理员查询商品
	 * @param productPage
	 */
	public void searchProducts(BasePagination<Product> productPage) throws UhomeStoreException;
	
	/**
	 * 查询上一条记录
	 * @param productId
	 * @return
	 */
	public Integer getPreviousProduct(Integer productId) throws UhomeStoreException;
	
	/**
	 * 查询下一条记录
	 * @param productId
	 * @return
	 */
	public Integer getNextProduct(Integer productId) throws UhomeStoreException;
	
	/**
	 * 查询下一条待审核商品
	 * @param productId
	 * @return
	 */
	public Integer getNextPendingProduct(Integer productId) throws UhomeStoreException;
	
	/**
	 * 前台搜索商品并分页
	 * 
	 * @param productPage
	 * @throws UhomeStoreException
	 */
	public void searchProducts4Front(BasePagination<Product> productPage) throws UhomeStoreException;
	
	/**
	 * 最受欢迎的商品10个
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<Product> getProductListByHits(Integer num)throws UhomeStoreException;
	
	/**
	 * 最优惠的商品10个
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<Product> getProductListByMostCheap() throws UhomeStoreException;
	
	/**
	 * 通过productId查询商品   前台商品详细页面用
	 * @param productId
	 * @return
	 * @throws UhomeStoreException
	 */
	public Product getProductByProductId4Front(Product product)throws UhomeStoreException;
	
	/**
	 * 通过productId查询商品   前台秒杀商品详细页面用
	 * @param product
	 * @return
	 * @throws UhomeStoreException
	 */
	public Product getProductByProductId4FrontSecKillDetail(Plan plan) throws UhomeStoreException;
	
	/**
	 * 保存商品
	 * @param product
	 */
	public void saveProduct(Product product) throws UhomeStoreException;
	
	/**
	 * 快速更新商品信息
	 * @param product
	 */
	public void quickUpdateProduct(Product product) throws UhomeStoreException;
	
	/**
	 * 根据商品ID查找一类商品
	 * @param productId
	 * @return
	 */
	public Product getProductByProductId(Integer productId) throws UhomeStoreException;
	
	/**
	 * 获取商家商品
	 * @param productId
	 * @param sellerId
	 * @return
	 */
	public Product getProductByProductIdSellerId(Integer productId, Integer sellerId) throws UhomeStoreException;
	
	/**
	 * 获取商品信息用于修改页面
	 * @param productId
	 * @return
	 */
	public Product getProduct4Edit(Integer productId, Integer sellerId) throws UhomeStoreException;
	
	/**
	 * 根据商品ID删除一件商品，同时删除对应的SKU。
	 * @param productId
	 */
	public void deleteProduct(Product product) throws UhomeStoreException;
	
	/**
	 * 保存审核结果审核
	 * @param product
	 */
	public void saveReviewResult(Product product) throws UhomeStoreException;
	
	/**
	 * 获取商品库存总数
	 * @param product
	 * @return
	 * @throws UhomeStoreException
	 */
	public Integer getProductInventoryCount(Product product)throws UhomeStoreException;
	
	/**
	 * 售馨
	 * @param product
	 * @throws UhomeStoreException
	 */
	public void sellOut(Product product) throws UhomeStoreException;
	
	/**
	 * 根据skuId判断是否在排期内   true  在排期内
	 * @param productSkuId
	 * @return
	 * @throws UhomeStoreException
	 */
	public boolean isPlanByProductSkuId(Integer productSkuId)throws UhomeStoreException;
	
	/**
	 * 根据productid 获取简单的商品信息并此商品的当前时间排期内  只获取 product表内容
	 * @param productId
	 * @return
	 * @throws UhomeStoreException
	 */
	public Product getProductByProdcutIdAndCurrentTime(Integer productId)throws UhomeStoreException;
	
	/**
	 * 先获取用户浏览商品的记录  再保存用户的浏览商品记录
	 * @param request
	 * @param response
	 * @param productId
	 * @return
	 * @throws DataAccessException
	 */
	public List<Product> getUserProductHistory(HttpServletRequest request, HttpServletResponse response,Integer productId)throws UhomeStoreException;
	
	/**
	 * 根据商品的id 将商品的 hits加一   商品详情页面用
	 * 
	 * @param product
	 * @throws UhomeStoreException
	 */
	public void updateProductHits(Product product)throws UhomeStoreException;
	
	/**
	 * 如果商品下架了 通过他来查找 历史信息
	 * @param ProductSkuId
	 * @return
	 * @throws DataAccessException
	 */
	public Product getOutOfStockProductByProductId(Integer productId) throws UhomeStoreException;
	
	/**
	 * 前台查询进口商品
	 * @param productPage
	 * @return
	 * @throws DataAccessException
	 */
	public void searchImportProducts4Front(BasePagination<Product> productPage) throws UhomeStoreException;
	
	/**
	 * 用于收索和进口商品  将品牌提取出来 且不重复
	 * @param type  search 是搜索  import是 进口
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<Brand> getBrandsSearchAndImport(Short type,String keyWord) throws UhomeStoreException;
	
	/**
	 * 批量上传商品
	 * @param file
	 * @param sellerId
	 * @throws UhomeStoreException
	 */
	public Map<String,String> batchUploadProduct(File file,String fileName,Integer sellerId) throws UhomeStoreException;
	
	/**
	 * 根据类目查询在排期中的推荐商品
	 * @param productCategoryId
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<Product> getRecommendByProductCategoryId(Integer productCategoryId)throws UhomeStoreException;
	
	/**
	 * 通过商品id查询是否有邮费
	 * @param productId
	 * @return
	 * @throws UhomeStoreException
	 */
	public Postage getProductPostageByProductId(Product product)throws UhomeStoreException;
}
