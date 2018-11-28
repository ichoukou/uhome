package com.ytoxl.module.uhome.uhomeorder.service;

import java.math.BigDecimal;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Seller;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderHead;
/**
 * 邮费计算
 * @author chenjian
 */
public interface PostageCalService {

	/**
	 * 订单号成之前计算邮费
	 * @param seller
	 * @return 
	 */
	public BigDecimal getPostage(Seller seller,short type) throws UhomeStoreException;
	
	/**
	 * 订单号成之前后计算邮费
	 * @param seller
	 * @return 
	 */
	public BigDecimal getPostage(OrderHead order,short type) throws UhomeStoreException;
	
	
	/**
	 * 根据商品id计算邮费
	 * @param productId
	 * @return
	 * @throws UhomeStoreException
	 */
	public BigDecimal getPostage(Integer productId,short type) throws UhomeStoreException;
	
}
