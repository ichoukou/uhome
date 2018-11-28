package com.ytoxl.uhomemanage.web.action.report;



import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.ytoxl.module.core.common.utils.DateUtil;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.common.utils.excel.ExportExcel;
import com.ytoxl.module.uhome.uhomebase.dataobject.Brand;
import com.ytoxl.module.uhome.uhomebase.dataobject.Seller;
import com.ytoxl.module.uhome.uhomebase.service.BrandService;
import com.ytoxl.module.uhome.uhomebase.service.UserInfoService;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.MonthReport;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.SellerReport;
import com.ytoxl.module.uhome.uhomereport.service.ReportService;
import com.ytoxl.uhomemanage.web.action.BaseAction;

public class ReportAction extends BaseAction {

	private static final long serialVersionUID = -1683456422079577697L;
	private static Logger logger = LoggerFactory.getLogger(ReportAction.class);
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private ReportService reportService;
	
	private BasePagination<MonthReport> monthReportPage;
	private MonthReport monthReport;
	
	private InputStream excelStream;
	private String fileName;
	
	public String report(){
			return "report";
	}
	
	public String searchMonthReports(){
		if (monthReportPage == null) {
			monthReportPage=new BasePagination<MonthReport>();
		}
		try {
			reportService.searchMonthReports(monthReportPage);
			this.monthReport=reportService.getTotalPrice(monthReportPage);
			MonthReport monthReturnReport=reportService.getTotalReturnPrice(monthReportPage);
			this.monthReport.setReturnOrderAmount(monthReturnReport.getReturnOrderAmount());
			this.monthReport.setReturnOrderCount(monthReturnReport.getReturnOrderCount());
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return "report";
	}
	
	public String exportMonthReports(){
		try {
			List<MonthReport> reports=reportService.listMonthReports(monthReportPage);
			
			ActionContext ac = ActionContext.getContext();
	        ServletContext sc = (ServletContext) ac.get(ServletActionContext.SERVLET_CONTEXT);
	        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        this.setFileName(sdf.format(new Date()).replace("-", "").replace(" ", "").replace(":", ""));
	        String x = sc.getRealPath("/")+"monthReport.xls";
			if(reports==null || reports.size()==0){
				reports = new ArrayList<MonthReport>(); 
				reports.add(new MonthReport());
			}
			
			this.monthReport=reportService.getTotalPrice(monthReportPage);
			MonthReport monthReturnReport=reportService.getTotalReturnPrice(monthReportPage);
			
			ExportExcel<MonthReport> ee = new ExportExcel<MonthReport>(reports);
			ee.setRowi(ee.getRowi()+1);
			//title row
			Row titleRow = ee.getSheet().createRow(0);
			titleRow.setHeightInPoints(45);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellValue("新龙直销 "+monthReportPage.getParams().get("time")+" 月结报表\r\n成功订单"
					+this.monthReport.getSellOrderCount()+"笔、"+"销售总额¥"+this.monthReport.getSellOrderAmount()+"元、"+
					"退货订单"+monthReturnReport.getReturnOrderCount()+"笔、"+"退货总额¥"+monthReturnReport.getReturnOrderAmount()+"元、"+
					"应付总额¥"+(this.monthReport.getSellOrderAmount().subtract(monthReturnReport.getReturnOrderAmount()))+"元");
			ee.getSheet().addMergedRegion(CellRangeAddress.valueOf("$A$1:$H$1"));
			
	        ee.setMerge(true);
	        ee.export(x);
	        
			File file=new File(x);
			this.setExcelStream(new FileInputStream(file));
			
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return "excel";
	}
	
	/**
	 * 获取全部品牌
	 * @return
	 */
	public List<Brand> getBrands() {
		List<Brand> brands = new ArrayList<Brand>();
		try {
			brands = brandService.listBrands();
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return brands;
	}

	/**
	 * 获取全部商家
	 * @return
	 */
	public List<Seller> getSellers() {
		List<Seller> sellers = new ArrayList<Seller>();
		try {
			sellers = userInfoService.listSellers();
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return sellers;
	}

	public BasePagination<MonthReport> getMonthReportPage() {
		return monthReportPage;
	}

	public void setMonthReportPage(BasePagination<MonthReport> monthReportPage) {
		this.monthReportPage = monthReportPage;
	}

	public MonthReport getMonthReport() {
		return monthReport;
	}

	public void setMonthReport(MonthReport monthReport) {
		this.monthReport = monthReport;
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
	
	public String getNowDateTime() {
		Calendar calendar = Calendar.getInstance();
		if(calendar.get(Calendar.DAY_OF_MONTH)>15){
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)-1);
		}else{
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)-2);
		}
		return DateUtil.format(calendar.getTime(), "yyyy-MM");
	}
	
}
