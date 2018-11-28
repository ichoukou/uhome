package com.ytoxl.module.uhome.uhomecontent.service;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.dataobject.Supplier;

public interface SupplierService {
	
	/**
	 * 分页查询供应商
	 * @param sellerPage
	 * @throws UhomeStoreException
	 */
	public void searchSupplier(BasePagination<Supplier> sellerPage) throws UhomeStoreException;

	/**
	 * 保存供应商信息
	 * @param supplier
	 */
	public void save(Supplier supplier) throws UhomeStoreException;
	
	/**
	 * 根据id删除供应商
	 * @param id
	 * @return
	 */
	public Integer deleteSupplier(Integer id);
	
	
}