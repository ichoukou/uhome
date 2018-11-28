package com.ytoxl.module.uhome.uhomereport.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.OrderWaitRefundReport;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.OrderWaitSendReport;

public interface SaleStatementReportService {

	/**
	 * 分页查询待发货订单
	 * 
	 * @param map
	 * @return
	 */
	public void searchOrderWaitSendReport(BasePagination<OrderWaitSendReport> orderWaitSendReportPage) throws UhomeStoreException;
	
	/**
	 * 查询所有新增待发货订单
	 * 
	 * @param orderWaitSendReportPage
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<OrderWaitSendReport> listOrderWaitSendReport(BasePagination<OrderWaitSendReport> orderWaitSendReportPage) throws UhomeStoreException;
	
	/**
	 * 每日新增待退款订单
	 * 
	 * @author hwx
	 * @return
	 * @throws DataAccessException
	 */
	public void searchOrderWaitRefundReports(
			BasePagination<OrderWaitRefundReport> orderWaitRefundPage)
			throws UhomeStoreException;
	
	/**
	 * 每日新增待退款订单导出
	 * 
	 * @author hwx
	 * @return
	 * @throws DataAccessException
	 */
	public List<OrderWaitRefundReport> listOrderWaitRefundReports(
			BasePagination<OrderWaitRefundReport> orderWaitRefundPage)
			throws UhomeStoreException;
	
}
