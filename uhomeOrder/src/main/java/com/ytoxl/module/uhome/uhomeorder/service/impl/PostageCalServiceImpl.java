package com.ytoxl.module.uhome.uhomeorder.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ytoxl.module.uhome.uhomebase.common.CommonConstants;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Postage;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductSku;
import com.ytoxl.module.uhome.uhomebase.dataobject.Seller;
import com.ytoxl.module.uhome.uhomebase.mapper.PostageMapper;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderHead;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderItem;
import com.ytoxl.module.uhome.uhomeorder.service.PostageCalService;

@Service
public class PostageCalServiceImpl implements PostageCalService{
	private static Logger logger = LoggerFactory.getLogger(PostageCalServiceImpl.class);
	@Value("${postage_value}")
	private BigDecimal postageValue;
	@Autowired
	private PostageMapper<Postage> postageMapper;
	
	@Override
	public BigDecimal getPostage(Seller seller, short type) throws UhomeStoreException{
		List<ProductSku> productSkus = seller.getProductSkus();
		BigDecimal postageOrder = new BigDecimal(999); 
		if(CommonConstants.POSTAGE_PLAN==type){
			for(ProductSku productSku:productSkus){
				BigDecimal tempPostage = getPostageByProudctIdInPlan(productSku.getProduct().getProductId());
				if(tempPostage.compareTo(new BigDecimal(0))==0){
					postageOrder = new BigDecimal(0);
					productSku.setPostage(tempPostage);
				}else{
					productSku.setPostage(postageValue);
				}
			}
		}
		if(postageOrder.compareTo(new BigDecimal(0))==0){
			return postageOrder;
		}else{
			return postageValue;
		}
	}
	
	@Override
	public BigDecimal getPostage(OrderHead order, short type) throws UhomeStoreException {
		List<OrderItem> itemList = 	order.getItems();
		List<Integer> productSkuIds = new ArrayList<Integer>();   
		for(OrderItem item:itemList){
			productSkuIds.add(item.getProductSkuId());
		}
		BigDecimal postage = new BigDecimal(0); 
		if(CommonConstants.POSTAGE_PLAN==type){
			postage = getPostageByPlan(productSkuIds);
		}
		return postage;
	}

	
	public BigDecimal getPostage(Integer productId, short type) throws UhomeStoreException{
		BigDecimal postage = new BigDecimal(0);
		//按照排期计算邮费策略(先判断商品所在排期是否免邮，如果免邮，商品就免邮，如果不免邮，就查看商品本身邮费)
		if(CommonConstants.POSTAGE_PLAN==type){
			postage = getPostageByProudctIdInPlan(productId);
		}
		return postage;
	}
	

	public BigDecimal getPostageByPlan(List<Integer> productSkuIds) throws UhomeStoreException{
		List<Integer> productIds = postageMapper.productSkuIdToProuctId(productSkuIds);
		return getPostageByProductIds(productIds);
	}
	
	/**
	 * 统一计算邮费
	 * 如果排期免邮，排期中的商品都免邮，如果排期未设置邮费，默认就商品来计算邮费
	 * @param productIds
	 * @return
	 * @throws UhomeStoreException
	 */
	public BigDecimal getPostageByProductIds(List<Integer> productIds) throws UhomeStoreException{
		List<Integer> planList = postageMapper.getPlansByProductIds(productIds);
		//如果商品下架，查不到排期，直接返回邮费0
		if(planList==null||planList.size()==0){
			return new BigDecimal(0);
		}
		List<Postage> planPostageList = postageMapper.getPostagesByOutIdsAndType(planList, Postage.TYPE_PLAN);
		if(planList.size()==planPostageList.size()){
			return new BigDecimal(0); 
		}else{
			List<Postage> productPostageList = postageMapper.getPostagesByOutIdsAndType(productIds, Postage.TYPE_PRODUCT);
			if(productPostageList.size()==productIds.size()){
				return postageValue;
			}else{
				return new BigDecimal(0);
			}
		}
	}
	
	/**
	 * 根据productId按照排期查询商品邮费
	 * @param productId
	 * @return
	 * @throws UhomeStoreException
	 */
	private BigDecimal getPostageByProudctIdInPlan(Integer productId) throws UhomeStoreException{
		BigDecimal postage = new BigDecimal(0);
		postage = postageMapper.getPostageByProductIdInPlan(productId);
		//请求不是从排期过来的，即商品未关联排期，商品就有可能查不到邮费
		if(postage==null){
			return new BigDecimal(0);
		}
		if(new BigDecimal(0).compareTo(postage)==0){
			return postage;
		}else{
			postage = postageMapper.getPostageByProductIdInsSelf(productId);
			if(postage==null){
				return new BigDecimal(0);
			}
			if(new BigDecimal(0).compareTo(postage)==0){
				return postage;
			}else{
				return postageValue;
			}
		}
	}
}
