package com.ytoxl.module.uhome.uhomereport.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.CommonConstants;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.MonthReport;
import com.ytoxl.module.uhome.uhomereport.mapper.MonthReportMapper;
import com.ytoxl.module.uhome.uhomereport.service.ReportService;
@Service
public class ReportServiceImpl implements ReportService {
	@Autowired
	private MonthReportMapper<MonthReport> reportMapper;
 
	@Override
	public void searchMonthReports(BasePagination<MonthReport> monthReportPage)
			throws UhomeStoreException {
		// TODO Auto-generated method stub
		Map<String, Object> searchParams = monthReportPage.getSearchParamsMap();
		searchParams.put(CommonConstants.PAGE_SORT, "t.payTime");
		searchParams.put(CommonConstants.PAGE_DIR, "desc");
		if (monthReportPage.isNeedSetTotal()) {
			Integer total = reportMapper.searchMonthReportsCount(searchParams);
			monthReportPage.setTotal(total);
		}
		Collection<MonthReport> result = reportMapper.searchMonthReports(searchParams);
		monthReportPage.setResult(result);
	}

	@Override
	public MonthReport getTotalPrice(BasePagination<MonthReport> monthReportPage)
			throws UhomeStoreException {
		// TODO Auto-generated method stub
		return reportMapper.getTotalPrice(monthReportPage.getSearchParamsMap());
	}

	@Override
	public MonthReport getTotalReturnPrice(BasePagination<MonthReport> monthReportPage)
			throws UhomeStoreException {
		// TODO Auto-generated method stub
		return reportMapper.getTotalReturnPrice(monthReportPage.getSearchParamsMap());
	}
	
	/**
	 * 导出报表列表
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public List<MonthReport> listMonthReports(BasePagination<MonthReport> monthReportPage) throws UhomeStoreException {
		Map<String, Object> searchParams = monthReportPage.getSearchParamsMap();
		searchParams.put(CommonConstants.PAGE_SORT, "t.payTime");
		searchParams.put(CommonConstants.PAGE_DIR, "asc");
		return reportMapper.listMonthReports(monthReportPage.getSearchParamsMap());
	}
	

	 
}
