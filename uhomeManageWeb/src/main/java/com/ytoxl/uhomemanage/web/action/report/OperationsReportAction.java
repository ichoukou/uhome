package com.ytoxl.uhomemanage.web.action.report;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.BuyerOperationsReport;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.ProductSalesReport;
import com.ytoxl.module.uhome.uhomereport.service.OperationsReportService;
import com.ytoxl.uhomemanage.web.action.BaseAction;

@SuppressWarnings("serial")
public class OperationsReportAction extends BaseAction {
	private static Logger logger = LoggerFactory
			.getLogger(OperationsReportAction.class);

	@Autowired
	private OperationsReportService operationsReportService;

	/** 查询条件 */
	private HashMap<String, Object> searchParams;

	/** 商品销售报表 */
	private ProductSalesReport productSalesReport;

	/** 买家运营报表 */
	private BuyerOperationsReport buyerOperationsReport;

	private BasePagination<BuyerOperationsReport> buyerOperationsReportPage;
	
	/** 报表菜单ID */
	private String reportMenuId;

	private InputStream excelStream;
	private String fileName;

	/**
	 * 跳转到商品销售报表页面
	 * 
	 * @return
	 */
	public String productSalesReport() {
		return "productSales";
	}

	/**
	 * 查询商品销售报表
	 * 
	 * @return
	 */
	public String searchProductSalesReport() {
		if (searchParams == null) {
			searchParams = new HashMap<String, Object>();
		}
		try {
			productSalesReport = operationsReportService
					.searchProductSalesReport(searchParams);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return "productSales";
	}

	/**
	 * 导出商品销售报表
	 * 
	 * @return
	 */
	public String exportProductSalesReport() {
		try {
			productSalesReport = operationsReportService.searchProductSalesReport(searchParams);
			ActionContext ac = ActionContext.getContext();
			ServletContext sc = (ServletContext) ac
					.get(ServletActionContext.SERVLET_CONTEXT);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			this.setFileName(sdf.format(new Date()).replace("-", "")
					.replace(" ", "").replace(":", ""));
			String x = sc.getRealPath("/") + "productSalesReport.xls";
			if (productSalesReport == null) {
				FileOutputStream fos = new FileOutputStream(new File(x));
				BufferedOutputStream buff = new BufferedOutputStream(fos);
				buff.write("".getBytes());
			} else {
				List<ProductSalesReport> reports = new ArrayList<ProductSalesReport>();
				reports.add(productSalesReport);
				ExportExcel<ProductSalesReport> ee = new ExportExcel<ProductSalesReport>(
						reports);
				ee.setRowi(ee.getRowi() + 1);
				// title row
				Row titleRow = ee.getSheet().createRow(0);
				//titleRow.setHeightInPoints(20);
				Cell titleCell = titleRow.createCell(0);
				titleCell.setCellValue("新龙直销商品销售报表");
				ee.getSheet().addMergedRegion(
						CellRangeAddress.valueOf("$A$1:$D$1"));
				ee.setMerge(true);
				ee.export(x);
			}
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
	 * 跳转到买家运营报表页面
	 * 
	 * @return
	 */
	public String buyerOperationsReport() {
		return "buyerOperationsReport";
	}

	/**
	 * 查询买家运营报表
	 * 
	 * @return
	 */
	public String searchBuyerOperationsReport() {
		if (buyerOperationsReportPage == null) {
			buyerOperationsReportPage = new BasePagination<BuyerOperationsReport>();
		}
		try {
			buyerOperationsReport = operationsReportService
					.searchBuyerOperationsReport(buyerOperationsReportPage);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return "buyerOperationsReport";
	}

	/**
	 * 导出买家运营报表
	 * 
	 * @return
	 */
	public String exportBuyerOperationsReport() {
		try {
			buyerOperationsReport = operationsReportService
					.searchBuyerOperationsReport(buyerOperationsReportPage);
			ActionContext ac = ActionContext.getContext();
			ServletContext sc = (ServletContext) ac
					.get(ServletActionContext.SERVLET_CONTEXT);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			this.setFileName(sdf.format(new Date()).replace("-", "")
					.replace(" ", "").replace(":", ""));
			String x = sc.getRealPath("/") + "salesDetailReport.xls";
			if (buyerOperationsReport == null) {
				FileOutputStream fos = new FileOutputStream(new File(x));
				BufferedOutputStream buff = new BufferedOutputStream(fos);
				buff.write("".getBytes());
			} else {
				List<BuyerOperationsReport> reports = new ArrayList<BuyerOperationsReport>();
				reports.add(buyerOperationsReport);
				ExportExcel<BuyerOperationsReport> ee = new ExportExcel<BuyerOperationsReport>(
						reports);
				ee.setRowi(ee.getRowi() + 1);
				// title row
				Row titleRow = ee.getSheet().createRow(0);
				//titleRow.setHeightInPoints(20);
				Cell titleCell = titleRow.createCell(0);
				titleCell.setCellValue("新龙直销买家运营报表");
				ee.getSheet().addMergedRegion(
						CellRangeAddress.valueOf("$A$1:$G$1"));
				ee.setMerge(true);
				ee.export(x);
			}
			File file = new File(x);
			this.setExcelStream(new FileInputStream(file));

		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return "excel";
	}

	public ProductSalesReport getProductSalesReport() {
		return productSalesReport;
	}

	public void setProductSalesReport(ProductSalesReport productSalesReport) {
		this.productSalesReport = productSalesReport;
	}

	public HashMap<String, Object> getSearchParams() {
		return searchParams;
	}

	public void setSearchParams(HashMap<String, Object> searchParams) {
		this.searchParams = searchParams;
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

	public BuyerOperationsReport getBuyerOperationsReport() {
		return buyerOperationsReport;
	}

	public void setBuyerOperationsReport(
			BuyerOperationsReport buyerOperationsReport) {
		this.buyerOperationsReport = buyerOperationsReport;
	}

	public BasePagination<BuyerOperationsReport> getBuyerOperationsReportPage() {
		return buyerOperationsReportPage;
	}

	public void setBuyerOperationsReportPage(
			BasePagination<BuyerOperationsReport> buyerOperationsReportPage) {
		this.buyerOperationsReportPage = buyerOperationsReportPage;
	}

}
