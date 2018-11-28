package com.ytoxl.module.uhome.uhomereport.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.ClassifySellReport;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.MemberSellReport;
/**
 * 分类销售明细报表
 * @author Administrator
 *
 * @param <T>
 */
public interface ClassifySellReportMapper<T extends ClassifySellReport> extends BaseSqlMapper<T> {
	
	/**
	 * 分页查询
	 * @param searchParams
	 * @return
	 * @throws DataAccessException
	 */
	public List<ClassifySellReport> searchClassifySellReports(Map<String, Object> searchParams) throws DataAccessException;
	public Integer searchClassifySellCount(Map<String, Object> searchParams) throws DataAccessException;
	/**
	 * 获取分类报表信息
	 * @param searchParams
	 * @return
	 * @throws DataAccessException
	 */
	public List<ClassifySellReport> listClassifySellReports(Map<String, Object> searchParams) throws DataAccessException;
}
