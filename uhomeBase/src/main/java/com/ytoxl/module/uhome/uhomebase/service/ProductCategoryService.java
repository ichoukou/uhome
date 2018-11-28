package com.ytoxl.module.uhome.uhomebase.service;

import java.util.List;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductCategory;

public interface ProductCategoryService {
	/**
	 * 查询所有产品类别信息
	 * @return
	 */
	public List<ProductCategory> listProductCategory();
	
	/**
	 * 查询所有产品子类别信息
	 * @return
	 */
	public List<ProductCategory> listChildProductCategory() throws UhomeStoreException;
	
	/**
	 * 根据cateId查询名称  返回一个字符串数组 第一个存uri 第二存放 name
	 * @return
	 * @throws UhomeStoreException
	 */
	public String[] getProductCategoryLink(Integer pcId) throws UhomeStoreException;
	
}
