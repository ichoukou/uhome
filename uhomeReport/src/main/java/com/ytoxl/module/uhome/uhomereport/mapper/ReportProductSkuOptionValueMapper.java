package com.ytoxl.module.uhome.uhomereport.mapper;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.ReportProductSkuOptionValue;

public interface ReportProductSkuOptionValueMapper<T extends ReportProductSkuOptionValue> extends BaseSqlMapper<T> {

	/**
	 * 根据productSkuId查询该商品的规格数据
	 * 
	 * @param productSkuId
	 * @return
	 * @throws DataAccessException
	 */
	public List<ReportProductSkuOptionValue> listProductSkuOptionValue(Integer productSkuId) throws DataAccessException;

}
