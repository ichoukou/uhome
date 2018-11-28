package com.ytoxl.module.uhome.uhomebase.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductSku;

/**
 * 产品SKU
 * @author huayucai
 * 
 * @param <T>
 */
public interface ProductSkuMapper<T extends ProductSku> extends BaseSqlMapper<T> {
	
	/**
	 * 逻辑删除多条SKU数据
	 * @param productSkus
	 */
	public void logicDelBatch(List<ProductSku> productSkus) throws DataAccessException;
	
	/**
	 * 根据productId, sellerId查询商品SKU信息
	 * @param productId
	 * @param sellerId
	 * @return
	 */
	public List<Integer> listProductSkuIdsByProductIdAndSellerId(@Param("productId") Integer productId, @Param("sellerId") Integer sellerId) throws DataAccessException;
	
	/**
	 * 更新库存为0
	 * @param productSkuIds
	 */
	public void updateInventoryByIds(List<Integer> productSkuIds) throws DataAccessException;

	/**
	 * 根据productId查询商品SKU信息
	 * @param productId
	 * @return
	 */
	public List<ProductSku> listProductSkusByProductId(Integer productId)
			throws DataAccessException;

	/**
	 * 根据productId删除商品SKU信息
	 * @param productId
	 */
	public void delProductSkuByProductId(Integer productId)
			throws DataAccessException;
	
	/**
	 * 根据卖家ID与品品牌ID查询可参加秒杀活动的商品 
	 * @param sellerId
	 * @param brandId
	 * @return
	 */
	public List<ProductSku> listSecKillProductsBysellerId(@Param("startTime") Date startTime,
			@Param("sellerId") Integer sellerId, @Param("brandId") Integer brandId,
			@Param("productCategoryId") Integer productCategoryId, @Param("planId") Integer planId) throws DataAccessException;
	
	/**
	 * 根据购物车中的productIds获取商品信息
	 * @param produc
	 * @return
	 * @throws DataAccessException
	 */
	public List<ProductSku> listShoppingCartProducts(List<Integer> producIds) throws DataAccessException;
	
	/**
	 * 根据扣减库存类型和产品id，去增加或减少库存
	 * @param type
	 * @param productSkuId
	 * @param num
	 * @return
	 * @throws DataAccessException
	 */
	public Integer changeProductSkuInventory(@Param(value = "type") Short type, @Param("productSkuId") Integer productSkuId, @Param("num") Integer num)  throws DataAccessException;
	
	/**
	 * 发货时  添加销售量
	 * @param productSkuId
	 * @param num
	 * @return
	 * @throws DataAccessException
	 */
	public Integer updateSaleQualtity(@Param("productSkuId") Integer productSkuId,@Param("num") Integer num)throws DataAccessException;
	
	/**
	 * 取消订单根据库存类型和产品id，去归还库存
	 * @param type
	 * @param productSkuId
	 * @param num
	 * @return
	 * @throws DataAccessException
	 */
	public Integer revertProductSkuInventory(@Param(value = "type") Short type, @Param("productSkuId") Integer productSkuId, @Param("num") Integer num)  throws DataAccessException;
	
	/**
	 * 根据skuId获取sku对象
	 * @param productSkuId
	 * @return
	 * @throws DataAccessException
	 */
	public ProductSku getProductSkuById(@Param("productSkuId") Integer productSkuId) throws DataAccessException;
	/**
	 * 根据SKUId和productId获取sku对象
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public ProductSku getProductSkuByIdMap(Map<String ,Object> map) throws DataAccessException;
	
	/**
	 * 根据skuId获取库存
	 * @param productSkuId
	 * @return
	 * @throws DataAccessException
	 */
	public Integer getProductSkuInventoryByProductSkuId(Integer productSkuId)throws DataAccessException;
}
