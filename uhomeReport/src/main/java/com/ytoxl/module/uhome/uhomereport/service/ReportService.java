package com.ytoxl.module.uhome.uhomereport.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.MonthReport;


public interface ReportService {
	/**
	 * 分页查询
	 * @param map
	 * @return
	 */
	public void searchMonthReports(BasePagination<MonthReport> monthReportPage) throws  UhomeStoreException;
	
	/**
	 * 订单总数量及金额
	 * @return
	 * @throws UhomeStoreException
	 */
	public MonthReport getTotalPrice(BasePagination<MonthReport> monthReportPage)  throws UhomeStoreException;
	
	/**
	 * 退货订单总数量及金额
	 * @return
	 * @throws UhomeStoreException
	 */
	public MonthReport getTotalReturnPrice(BasePagination<MonthReport> monthReportPage)  throws UhomeStoreException;
	
	/**
	 * 导出报表列表
	 * @return
	 * @throws DataAccessException
	 */
	public List<MonthReport> listMonthReports (BasePagination<MonthReport> monthReportPage)  throws UhomeStoreException;

 
 }
 
 