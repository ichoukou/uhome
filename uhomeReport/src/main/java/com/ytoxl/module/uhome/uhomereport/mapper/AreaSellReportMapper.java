package com.ytoxl.module.uhome.uhomereport.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.AreaSellReport;

/**
 *  
 * @author 
 *
 * @param <T>
 */
public interface AreaSellReportMapper<T extends AreaSellReport> extends BaseSqlMapper<T> {
 
	
	/**
	 * 根据订单时间获取品牌地区销售情况
	 * @param orderNo
	 * @return
	 * @throws DataAccessException
	 */
	public List<AreaSellReport> searchAreaSellByBrand( Map<String,Object> map)  throws DataAccessException;
	public Integer searchAreaSellByBrandCount(Map<String,Object> map) throws DataAccessException;
	
 
}
