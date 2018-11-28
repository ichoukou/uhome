package com.ytoxl.module.uhome.uhomebase.service;

import java.util.List;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.BrandSort;


/**
 * @author shengjianming
 *
 */
public interface BrandSortService {
	
	/**
	 * 品牌排序
	 * @return
	 * @throws UhomeStoreException
	 */
	public void updateBrandSort(String[] args,Integer type) throws UhomeStoreException;
	/**
	 * 查询所有排序的品牌 
	 * @param name
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<BrandSort> getBrandListGroupByType() throws UhomeStoreException;
	
} 
