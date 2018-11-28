package com.ytoxl.module.uhome.uhomereport.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.CommonConstants;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.common.utils.StringUtils;
import com.ytoxl.module.uhome.uhomebase.dataobject.Region;
import com.ytoxl.module.uhome.uhomebase.mapper.RegionMapper;
import com.ytoxl.module.uhome.uhomeorder.dataobject.OrderHead;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.OrderWaitRefundReport;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.OrderWaitSendReport;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.ReportProductSkuOptionValue;
import com.ytoxl.module.uhome.uhomereport.mapper.OrderWaitRefundReportMapper;
import com.ytoxl.module.uhome.uhomereport.mapper.OrderWaitSendReportMapper;
import com.ytoxl.module.uhome.uhomereport.mapper.ReportProductSkuOptionValueMapper;
import com.ytoxl.module.uhome.uhomereport.service.SaleStatementReportService;

@Service
public class SaleStatementReportServiceImpl implements
		SaleStatementReportService {

	@Autowired
	private OrderWaitSendReportMapper<OrderWaitSendReport> orderWaitSendReportMapper;

	@Autowired
	private ReportProductSkuOptionValueMapper<ReportProductSkuOptionValue> reportProductSkuOptionValueMapper;

	@Autowired
	private RegionMapper<Region> regionMapper;

	@Autowired
	private OrderWaitRefundReportMapper<OrderWaitRefundReport> orderWaitRefundReportMapper;

	/**
	 * 分页查询待发货订单 by xupf
	 */
	@Override
	public void searchOrderWaitSendReport(
			BasePagination<OrderWaitSendReport> orderWaitSendReportPage)
			throws UhomeStoreException {
		Map<String, Object> searchParams = orderWaitSendReportPage
				.getSearchParamsMap();
		searchParams.put(CommonConstants.PAGE_DIR, "desc");
		if (orderWaitSendReportPage.isNeedSetTotal()) {
			Integer total = orderWaitSendReportMapper
					.getOrderWaitSendCount(searchParams);
			orderWaitSendReportPage.setTotal(total);
		}
		Collection<OrderWaitSendReport> result = orderWaitSendReportMapper
				.searchOrderWaitSendReport(searchParams);
		result = getOrderWaitSendOthers(result);
		orderWaitSendReportPage.setResult(result);
	}

	/**
	 * 获取待发货订单其它信息
	 * 
	 * @param result
	 * @return
	 */
	public Collection<OrderWaitSendReport> getOrderWaitSendOthers(
			Collection<OrderWaitSendReport> result) {
		Collection<OrderWaitSendReport> resetResult = result;
		for (OrderWaitSendReport ows : resetResult) {
			// 设置订单状态
			ows.setOrderStatus(OrderWaitSendReport.STATUS_WAITSEND);
			// 设置优惠类型
			if (ows.getRebatePrice() != null) {
				ows.setRebateType(OrderWaitSendReport.REBATE_TYPE_COUPON);
			}
			//设置发票抬头
			if(OrderWaitSendReport.HAS_INVOICE==ows.getHasInvoice() && StringUtils.isEmpty(ows.getInvoiceTitle())){
				ows.setInvoiceTitle(OrderWaitSendReport.INVOICE_TITLE_PERSON);
			}
			// 获取商品的颜色和尺寸
			List<ReportProductSkuOptionValue> psovs = reportProductSkuOptionValueMapper
					.listProductSkuOptionValue(ows.getProductSkuId());
			for (ReportProductSkuOptionValue psov : psovs) {
				if (psov.getSkuType() == ReportProductSkuOptionValue.COLOR) {
					ows.setColor(psov.getSkuOptionValue());
				} else {
					ows.setSpecification(psov.getSkuOptionValue());
				}
			}
			// 获取收货地址
			StringBuffer address = new StringBuffer("");
			Region region = regionMapper.getDetailInfoByRegionId(ows
					.getRegionId());
			if (region != null) {
				address.append(region.getProvince() + region.getCity()
						+ region.getCounty());
			}
			// 详细地址
			if (ows.getReceiveAddress() != null) {
				address.append(ows.getReceiveAddress());
			}
			ows.setReceiveAddress(address.toString());
		}
		return resetResult;
	}

	/**
	 * 查询所有符合条件的待发货订单
	 */
	@Override
	public List<OrderWaitSendReport> listOrderWaitSendReport(
			BasePagination<OrderWaitSendReport> orderWaitSendReportPage)
			throws UhomeStoreException {
		Map<String, Object> searchParams = orderWaitSendReportPage
				.getSearchParamsMap();
		searchParams.put(CommonConstants.PAGE_DIR, "asc");
		List<OrderWaitSendReport> result = orderWaitSendReportMapper.listOrderWaitSendReport(searchParams);
		result = (List<OrderWaitSendReport>)getOrderWaitSendOthers(result);
		return result;
	}

	/**
	 * 每日新增待退款订单
	 * 
	 * @author hwx
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public void searchOrderWaitRefundReports(
			BasePagination<OrderWaitRefundReport> orderWaitRefundPage)
			throws UhomeStoreException {
		Map<String, Object> searchParams = orderWaitRefundPage
				.getSearchParamsMap();
		searchParams.put(CommonConstants.PAGE_SORT, "t.userName");
		searchParams.put(CommonConstants.PAGE_DIR, "desc");
		if (orderWaitRefundPage.isNeedSetTotal()) {
			Integer total = orderWaitRefundReportMapper
					.searchReportsCount(searchParams);
			orderWaitRefundPage.setTotal(total);
		}
		List<OrderWaitRefundReport> result = orderWaitRefundReportMapper
				.searchReports(searchParams);
		for (OrderWaitRefundReport sdr : result) {
			searchParams.put("productSkuId", sdr.getProductSkuId());
			List<OrderWaitRefundReport> csList = orderWaitRefundReportMapper
					.searchColourAndSize(searchParams);
			for (OrderWaitRefundReport csder : csList) {
				if (csder.getSkuOptionId() != null
						&& csder.getSkuOptionId().equals("100000")) {
					sdr.setColour(csder.getSkuOptionValue());
				} else if (csder.getSkuOptionId() != null
						&& csder.getSkuOptionId().equals("100001")) {
					sdr.setSize(csder.getSkuOptionValue());
				}
			}
			if (sdr.getOrderStatus() != null
					&& sdr.getOrderStatus().equals(
							OrderHead.STATUS_RETURN.toString())) {
				sdr.setOrderStatus("部分退货");
			} else if (sdr.getOrderStatus() != null
					&& sdr.getOrderStatus().equals(
							OrderHead.STATUS_ALLRETURN.toString())) {
				sdr.setOrderStatus("全部退货");
			}
			if(sdr.getRebatePrice() != null && !sdr.getRebatePrice().equals("")){
				sdr.setPreferentialType("优惠券");
			}
		}
		orderWaitRefundPage.setResult(result);
	}

	/**
	 * 每日新增待退款订单导出
	 * 
	 * @author hwx
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public List<OrderWaitRefundReport> listOrderWaitRefundReports(
			BasePagination<OrderWaitRefundReport> orderWaitRefundPage)
			throws UhomeStoreException {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if (orderWaitRefundPage != null) {
			searchParams = orderWaitRefundPage.getSearchParamsMap();
		}
		searchParams.put(CommonConstants.PAGE_SORT, "t.userName");
		searchParams.put(CommonConstants.PAGE_DIR, "desc");
		List<OrderWaitRefundReport> result = orderWaitRefundReportMapper
				.listReports(searchParams);
		for (OrderWaitRefundReport sdr : result) {
			searchParams.put("productSkuId", sdr.getProductSkuId());
			List<OrderWaitRefundReport> csList = orderWaitRefundReportMapper
					.searchColourAndSize(searchParams);
			for (OrderWaitRefundReport csder : csList) {
				if (csder.getSkuOptionId() != null
						&& csder.getSkuOptionId().equals("100000")) {
					sdr.setColour(csder.getSkuOptionValue());
				} else if (csder.getSkuOptionId() != null
						&& csder.getSkuOptionId().equals("100001")) {
					sdr.setSize(csder.getSkuOptionValue());
				}
			}
			if (sdr.getOrderStatus() != null
					&& sdr.getOrderStatus().equals(
							OrderHead.STATUS_RETURN.toString())) {
				sdr.setOrderStatus("部分退货");
			} else if (sdr.getOrderStatus() != null
					&& sdr.getOrderStatus().equals(
							OrderHead.STATUS_ALLRETURN.toString())) {
				sdr.setOrderStatus("全部退货");
			}
			sdr.setPreferentialType("优惠券");
		}
		return result;
	}

}
