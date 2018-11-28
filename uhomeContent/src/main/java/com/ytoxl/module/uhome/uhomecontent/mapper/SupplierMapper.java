package com.ytoxl.module.uhome.uhomecontent.mapper;

import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.dataobject.Supplier;

public interface SupplierMapper<T extends Supplier> extends BaseSqlMapper<T> {
	
	/**
	 * 分页查询供应商
	 * @param sellerPage
	 * @throws UhomeStoreException
	 */
	public List<Supplier> searchSupplier(Map<String, Object> sellerPage) throws DataAccessException;
	
	public Integer searchSupplierCount(Map<String, Object> sellerPage) throws DataAccessException;
	
	
}
