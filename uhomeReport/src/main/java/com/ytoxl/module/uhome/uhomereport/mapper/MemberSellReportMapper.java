package com.ytoxl.module.uhome.uhomereport.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.MemberSellReport;
/**
 * 会员销售管理报表
 * @author Administrator
 *
 * @param <T>
 */
public interface MemberSellReportMapper<T extends MemberSellReport> extends BaseSqlMapper<T> {
	
	/**
	 * 分页查询
	 * @param searchParams
	 * @return
	 * @throws DataAccessException
	 */
	public List<MemberSellReport> searchMemberSellReports(Map<String, Object> searchParams) throws DataAccessException;
	public Integer searchMemberSellCount(Map<String, Object> searchParams) throws DataAccessException;
	
	public List<MemberSellReport> listMemberSellReports(Map<String, Object> searchParams)
			throws DataAccessException;

}
