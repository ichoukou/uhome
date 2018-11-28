package com.ytoxl.module.uhome.uhomereport.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.SellerReport;

/**
 *  
 * @author  
 *
 * @param <T>
 */
public interface SellerReportMapper<T extends SellerReport> extends BaseSqlMapper<T> {
	/**
	 * 根据订用户名获取商家信息
	 * @param  
	 * @return
	 * @throws DataAccessException
	 */
	public List<SellerReport> searchSellerByUserName( Map<String,Object> map)  throws DataAccessException;
	public Integer searchSellerByUserNameCount(Map<String,Object> map) throws DataAccessException;
	
	 
}
