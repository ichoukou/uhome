package com.ytoxl.module.uhome.uhomecontent.service.impl;

import java.util.Collection;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.dataobject.Supplier;
import com.ytoxl.module.uhome.uhomecontent.mapper.SupplierMapper;
import com.ytoxl.module.uhome.uhomecontent.service.SupplierService;


@Service
public class SupplierServiceImpl implements SupplierService {
	@Autowired
	private SupplierMapper<Supplier> supplierMapper;
	
	/**
	 * 分页查询供应商
	 * 
	 * @param supplierPage
	 * @throws UhomeStoreException
	 */
	@Override
	public void searchSupplier(BasePagination<Supplier> supplierPage)
			throws UhomeStoreException {
		Map<String, Object> searchParams = supplierPage.getSearchParamsMap();
		if (supplierPage.isNeedSetTotal()) {
			Integer total = supplierMapper.searchSupplierCount(searchParams);
			supplierPage.setTotal(total);
		}
		Collection<Supplier> result = supplierMapper.searchSupplier(searchParams);
		supplierPage.setResult(result);
	}
	
	/**
	 * 保存供应商信息
	 * 
	 * @param supplier
	 */
	@Override
	public void save(Supplier supplier) throws UhomeStoreException {
		supplierMapper.add(supplier);
	}

	/**
	 * 根据id删除供应商
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public Integer deleteSupplier(Integer id) {
		return supplierMapper.del(id);
	}
}
