package com.ytoxl.uhomemanage.web.action.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ibm.icu.text.SimpleDateFormat;
import com.opensymphony.xwork2.ActionContext;
import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.common.utils.excel.ExportExcel;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.OrderWaitRefundReport;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.OrderWaitSendReport;
import com.ytoxl.module.uhome.uhomereport.service.SaleStatementReportService;
import com.ytoxl.uhomemanage.web.action.BaseAction;

@SuppressWarnings("serial")
public class SaleStatementReportAction extends BaseAction {

	private static Logger logger = LoggerFactory
			.getLogger(SaleStatementReportAction.class);

	@Autowired
	private SaleStatementReportService saleStatementReportSevice;

	private BasePagination<OrderWaitSendReport> orderWaitSendReportPage;

	private BasePagination<OrderWaitRefundReport> orderWaitRefundReportPage;

	/** 报表菜单ID */
	private String reportMenuId;

	/** Excel 文件导出 */
	private InputStream excelStream;
	private String fileName;

	/**
	 * 跳转到“每日新增待发货订单”报表页面
	 * 
	 * @return
	 */
	public String orderWaitSendReport() {
		return "orderwaitsend";
	}

	/**
	 * 查询“每日新增待发货订单”报表
	 * 
	 * @return
	 */
	public String searchOrderWaitSendReport() {
		if (orderWaitSendReportPage == null) {
			orderWaitSendReportPage = new BasePagination<OrderWaitSendReport>();
		}
		try {
			saleStatementReportSevice
					.searchOrderWaitSendReport(orderWaitSendReportPage);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return "orderwaitsend";
	}

	/**
	 * 导出“每日新增待发货订单”报表
	 * 
	 * @return
	 */
	public String exportOrderWaitSendReport() {
		try {
			List<OrderWaitSendReport> reports = saleStatementReportSevice
					.listOrderWaitSendReport(orderWaitSendReportPage);
			ActionContext ac = ActionContext.getContext();
			ServletContext sc = (ServletContext) ac
					.get(ServletActionContext.SERVLET_CONTEXT);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			this.setFileName(sdf.format(new Date()).replace("-", "")
					.replace(" ", "").replace(":", ""));
			String x = sc.getRealPath("/") + "orderWaitSendReport.xls";
			if (reports == null || reports.size() == 0) {
				reports = new ArrayList<OrderWaitSendReport>();
				reports.add(new OrderWaitSendReport());
			}
			ExportExcel<OrderWaitSendReport> ee = new ExportExcel<OrderWaitSendReport>(
					reports);
			ee.setRowi(ee.getRowi() + 1);
			// title row
			Row titleRow = ee.getSheet().createRow(0);
			titleRow.setHeightInPoints(20);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellValue("新龙直销每日新增待发货订单报表");
			ee.getSheet().addMergedRegion(
					CellRangeAddress.valueOf("$A$1:$Y$1"));
			ee.setMerge(true);
			ee.export(x);
			
			File file = new File(x);
			this.setExcelStream(new FileInputStream(file));
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}

		return "excel";
	}

	/**
	 * 跳转到每日新增待退款订单页面
	 * 
	 * @return
	 */
	public String orderWaitRefundReport() {
		return "orderWaitRefundReport";
	}

	/**
	 * 分页查询每日特卖会销售明细报表
	 * 
	 * @return
	 */
	public String searchEverydaySalesDetailReports() {
		if (orderWaitRefundReportPage == null) {
			orderWaitRefundReportPage = new BasePagination<OrderWaitRefundReport>();
		}
		try {
			saleStatementReportSevice
					.searchOrderWaitRefundReports(orderWaitRefundReportPage);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return "orderWaitRefundReport";
	}

	/**
	 * 导出每日特卖会销售明细报表
	 * 
	 * @return
	 */
	public String exportOrderWaitRefundReports() {
		try {
			List<OrderWaitRefundReport> reports = saleStatementReportSevice
					.listOrderWaitRefundReports(orderWaitRefundReportPage);
			ActionContext ac = ActionContext.getContext();
			ServletContext sc = (ServletContext) ac
					.get(ServletActionContext.SERVLET_CONTEXT);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			this.setFileName(sdf.format(new Date()).replace("-", "")
					.replace(" ", "").replace(":", ""));
			String x = sc.getRealPath("/")
					+ "orderWaitRefundReport.xls";
			if(reports==null || reports.size()==0){
				reports = new ArrayList<OrderWaitRefundReport>();
				reports.add(new OrderWaitRefundReport());
			}
			ExportExcel<OrderWaitRefundReport> ee = new ExportExcel<OrderWaitRefundReport>(reports);
			ee.setRowi(ee.getRowi()+1);
			// title row
			Row titleRow = ee.getSheet().createRow(0);
			titleRow.setHeightInPoints(20);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellValue("每日新增待退款订单报表");
			ee.getSheet().addMergedRegion(
					CellRangeAddress.valueOf("$A$1:$U$1"));
			ee.setMerge(true);
			ee.export(x);
			File file = new File(x);
			this.setExcelStream(new FileInputStream(file));

		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return "excel";
	}

	public BasePagination<OrderWaitSendReport> getOrderWaitSendReportPage() {
		return orderWaitSendReportPage;
	}

	public void setOrderWaitSendReportPage(
			BasePagination<OrderWaitSendReport> orderWaitSendReportPage) {
		this.orderWaitSendReportPage = orderWaitSendReportPage;
	}

	public String getReportMenuId() {
		return reportMenuId;
	}

	public void setReportMenuId(String reportMenuId) {
		this.reportMenuId = reportMenuId;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public BasePagination<OrderWaitRefundReport> getOrderWaitRefundReportPage() {
		return orderWaitRefundReportPage;
	}

	public void setOrderWaitRefundReportPage(
			BasePagination<OrderWaitRefundReport> orderWaitRefundReportPage) {
		this.orderWaitRefundReportPage = orderWaitRefundReportPage;
	}

}
