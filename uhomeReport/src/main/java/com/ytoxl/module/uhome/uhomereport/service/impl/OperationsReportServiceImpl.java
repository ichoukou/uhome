package com.ytoxl.module.uhome.uhomereport.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.BuyerOperationsReport;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.ProductSalesReport;
import com.ytoxl.module.uhome.uhomereport.mapper.BuyerOperationsReportMapper;
import com.ytoxl.module.uhome.uhomereport.mapper.ProductSalesReportMapper;
import com.ytoxl.module.uhome.uhomereport.service.OperationsReportService;

@Service
public class OperationsReportServiceImpl implements OperationsReportService {

	protected static Logger logger = LoggerFactory
			.getLogger(OperationsReportServiceImpl.class);

	@Autowired
	private ProductSalesReportMapper<ProductSalesReport> productSalesReportMapper;

	@Autowired
	private BuyerOperationsReportMapper<BuyerOperationsReport> buyerOperationsReportMapper;

	/**
	 * 商家销售报表 By xupf
	 */
	@Override
	public ProductSalesReport searchProductSalesReport(
			Map<String, Object> searchParams) throws UhomeStoreException {
		ProductSalesReport productSalesReport = productSalesReportMapper
				.searchProductSalesReport(searchParams);
		// 设置统计时间
		String date = searchParams.get("stime") + "至"
				+ searchParams.get("etime");
		productSalesReport.setDate(date);
		// 计算客单价
		if (productSalesReport.getOrderAmount() != null) {
			BigDecimal amount = productSalesReport.getOrderAmount(); // 销售金额
			BigDecimal num = new BigDecimal(productSalesReport.getOrderNum());// 订单数
			// 除法运算，需指定“精度”及“舍入模式”，否则出现无限循环小数时，会抛出ArithmeticException
			BigDecimal unitPrice = amount.divide(num, 2,
					BigDecimal.ROUND_HALF_UP);// 客单价
			productSalesReport.setUnitPrice(unitPrice);
		}

		return productSalesReport;
	}

	/**
	 * 买家运营报表显示查询
	 * 
	 * @author hwx
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public BuyerOperationsReport searchBuyerOperationsReport(
			BasePagination<BuyerOperationsReport> buyerOperationsReportPage)
			throws UhomeStoreException {
		Map<String, Object> searchParams = buyerOperationsReportPage
				.getSearchParamsMap();
		BuyerOperationsReport result = buyerOperationsReportMapper
				.searchBuyerOperationsReports(searchParams);
		// 设置统计时间
		String date = searchParams.get("startTime") + "至"
				+ searchParams.get("endTime");
		result.setPeriod(date);
		if (result.getUsers() != null && !result.getUsers().equals(0)
				&& result.getMoreBuy() != null
				&& !result.getMoreBuy().equals(0)) {
			double k = (double) result.getMoreBuy() / result.getUsers() * 100;
			java.math.BigDecimal big = new java.math.BigDecimal(k);
			String l = big.setScale(2, java.math.BigDecimal.ROUND_HALF_UP)
					.doubleValue() + "%";
			result.setRepeatBuy(l);
		} else {
			result.setRepeatBuy("0%");
		}
		return result;
	}

}
