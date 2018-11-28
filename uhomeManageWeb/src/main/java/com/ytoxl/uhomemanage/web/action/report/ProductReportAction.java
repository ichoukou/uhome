package com.ytoxl.uhomemanage.web.action.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import net.sf.json.JSONArray;

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
import com.ytoxl.module.uhome.uhomebase.common.utils.PropertyUtil;
import com.ytoxl.module.uhome.uhomebase.common.utils.excel.ExportExcel;
import com.ytoxl.module.uhome.uhomebase.dataobject.Brand;
import com.ytoxl.module.uhome.uhomebase.dataobject.Plan;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductCategory;
import com.ytoxl.module.uhome.uhomebase.dataobject.Seller;
import com.ytoxl.module.uhome.uhomebase.service.BrandService;
import com.ytoxl.module.uhome.uhomebase.service.PlanService;
import com.ytoxl.module.uhome.uhomebase.service.ProductCategoryService;
import com.ytoxl.module.uhome.uhomebase.service.UserInfoService;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.AreaSellReport;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.BrandSellPlanReport;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.ClassifySellReport;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.EverydaySalesDetailReport;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.MemberSellReport;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.OrderDetailReport;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.ProductReturnReport;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.SalesDetailReport;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.SellerReport;
import com.ytoxl.module.uhome.uhomereport.service.ProductReportService;
import com.ytoxl.uhomemanage.web.action.BaseAction;

@SuppressWarnings("serial")
public class ProductReportAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(ProductReportAction.class);
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private PlanService planService;
	@Autowired
	private ProductCategoryService productCategoryService;
	@Autowired
	private ProductReportService productReportService;
	private BasePagination<SalesDetailReport> salesDetailReportPage;
	private BasePagination<ClassifySellReport> classifyReportPage;
	private BasePagination<MemberSellReport> memberReportPage;
	private SalesDetailReport salesDetailReport;
	private List<ProductCategory> listProductCategorie;
	private BasePagination<SellerReport> sellerReportPage;
	private BasePagination<AreaSellReport> areaSellReportPage;
	private BasePagination<OrderDetailReport> orderDetailReportPage;
	private BasePagination<EverydaySalesDetailReport> everySalesDetailReportPage;
	/**退货商品明细分页*/
	private BasePagination<ProductReturnReport> productReturnPage;
	/**品牌销售明细分页*/
	private BasePagination<BrandSellPlanReport> brandSellReportPage;
	private InputStream excelStream;
	private String fileName;
	/** 报表菜单ID */
	private String reportMenuId;
	
	
	private List<Brand> brands ;
	private List<Plan> plans ;
	private Integer sellerId;
	
	private Integer brandId;
	
	private String time; //ajax调用防止使用缓存
	
	public String memberSell(){
		return "memberSell";
	}
	/**
	 * 分页查询会员销售详细报表
	 * @return
	 */
	public String searchMemberSellInfo(){
		if(memberReportPage == null){
			memberReportPage = new BasePagination<MemberSellReport>();
		}
		try {
			productReportService.searchMemberSellReports(memberReportPage);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return "memberSell";
	}
	
	public String exportMemberSellReports(){
		try {
			List<MemberSellReport> reports = productReportService.listMemberSellReports(memberReportPage);
			ActionContext ac = ActionContext.getContext();
			ServletContext sc = (ServletContext) ac
					.get(ServletActionContext.SERVLET_CONTEXT);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			this.setFileName(sdf.format(new Date()).replace("-", "")
					.replace(" ", "").replace(":", ""));
			String x = sc.getRealPath("/") + "memberSellDetailReport.xls";
			if (reports == null || reports.size() == 0) {
				reports = new ArrayList<MemberSellReport>();
				reports.add(new MemberSellReport());
			}
			ExportExcel<MemberSellReport> ee = new ExportExcel<MemberSellReport>(reports);
			ee.setRowi(ee.getRowi() + 1);
			// title row
			Row titleRow = ee.getSheet().createRow(0);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellValue("新龙直销会员销售明细报表");
			ee.getSheet().addMergedRegion(
				CellRangeAddress.valueOf("$A$1:$E$1"));
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
	 * 跳转到分类销售明细页面
	 * @return
	 */
	public String classifySell(){
		return "classifySell";
	}
	/**
	 * 分页查询分类销售详细报表
	 * @return
	 */
	public String searchClassifySellInfo(){
		if(classifyReportPage == null){
			classifyReportPage = new BasePagination<ClassifySellReport>();
		}
		try {
			productReportService.searchClassifySellReports(classifyReportPage);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return "classifySell";
	}
	/**
	 * 导出分类销售明细报表
	 * @return
	 */
	public String exportClassifySellReports(){
		try {
			List<ClassifySellReport> reports = productReportService.listClassifySellReports(classifyReportPage);
			ActionContext ac = ActionContext.getContext();
			ServletContext sc = (ServletContext) ac
					.get(ServletActionContext.SERVLET_CONTEXT);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			this.setFileName(sdf.format(new Date()).replace("-", "")
					.replace(" ", "").replace(":", ""));
			String x = sc.getRealPath("/") + "classifySellDetailReport.xls";
			if (reports == null || reports.size() == 0) {
				reports = new ArrayList<ClassifySellReport>();
				reports.add(new ClassifySellReport());
//				FileOutputStream fos = new FileOutputStream(new File(x));
//				BufferedOutputStream buff = new BufferedOutputStream(fos);
//				buff.write("".getBytes());
			} 
			ExportExcel<ClassifySellReport> ee = new ExportExcel<ClassifySellReport>(reports);
			ee.setRowi(ee.getRowi() + 1);
			// title row
			Row titleRow = ee.getSheet().createRow(0);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellValue("新龙直销分类销售明细报表");
			ee.getSheet().addMergedRegion(
			CellRangeAddress.valueOf("$A$1:$E$1"));
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
	 * 跳转到商家销售管理页面
	 * @return
	 */
	public String salesDetailReport() {
		return "salesDetailReport";
	}

	/**
	 * 分页查询商家销售管理报表
	 * @return
	 */
	public String searchSalesDetailReports() {
		if (salesDetailReportPage == null) {
			salesDetailReportPage = new BasePagination<SalesDetailReport>();
		}
		try {
			productReportService
					.searchSalesDetailReports(salesDetailReportPage);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return "salesDetailReport";
	}

	/**
	 * 导出商家销售管理报表
	 * @return
	 */
	public String exportSalesDetailReports() {
		try {
			List<SalesDetailReport> reports = productReportService
					.listSalesDetailReports(salesDetailReportPage);
			ActionContext ac = ActionContext.getContext();
			ServletContext sc = (ServletContext) ac
					.get(ServletActionContext.SERVLET_CONTEXT);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			this.setFileName(sdf.format(new Date()).replace("-", "")
					.replace(" ", "").replace(":", ""));
			String x = sc.getRealPath("/") + "salesDetailReport.xls";
			if(reports==null || reports.size()==0){
				reports = new ArrayList<SalesDetailReport>();
				reports.add(new SalesDetailReport());
			}
			ExportExcel<SalesDetailReport> ee = new ExportExcel<SalesDetailReport>(reports);
			ee.setRowi(ee.getRowi()+1);
			// title row
			Row titleRow = ee.getSheet().createRow(0);
			//titleRow.setHeightInPoints(20);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellValue("新龙直销商家销售管理报表");
			ee.getSheet().addMergedRegion(
					CellRangeAddress.valueOf("$A$1:$G$1"));
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
	 * 跳转到每日特卖会销售明细页面
	 * @return
	 */
	public String everydaySalesDetailReport() {
		return "everySalesDetailReport";
	}

	/**
	 * 分页查询每日特卖会销售明细报表
	 * @return
	 */
	public String searchEverydaySalesDetailReports() {
		if (everySalesDetailReportPage == null) {
			everySalesDetailReportPage = new BasePagination<EverydaySalesDetailReport>();
		}
		try {
			productReportService.searchEverydaySalesDetailReports(everySalesDetailReportPage);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return "everySalesDetailReport";
	}
	
	/**
	 * 导出每日特卖会销售明细报表
	 * @return
	 */
	public String exportEverydaySalesDetailReports() {
		try {
			List<EverydaySalesDetailReport> reports = productReportService
					.listEverydaySalesDetailReports(everySalesDetailReportPage);
			ActionContext ac = ActionContext.getContext();
			ServletContext sc = (ServletContext) ac
					.get(ServletActionContext.SERVLET_CONTEXT);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			this.setFileName(sdf.format(new Date()).replace("-", "")
					.replace(" ", "").replace(":", ""));
			String x = sc.getRealPath("/") + "everydaySalesDetailReportPage.xls";
			if(reports==null || reports.size()==0){
				reports = new ArrayList<EverydaySalesDetailReport>();
				reports.add(new EverydaySalesDetailReport());
			}
			ExportExcel<EverydaySalesDetailReport> ee = new ExportExcel<EverydaySalesDetailReport>(reports);
			ee.setRowi(ee.getRowi()+1);
			// title row
			Row titleRow = ee.getSheet().createRow(0);
			//titleRow.setHeightInPoints(20);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellValue("每日特卖会销售明细报表");
			ee.getSheet().addMergedRegion(
					CellRangeAddress.valueOf("$A$1:$BW$1"));
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
	 * 跳转到退货商品管理页面
	 * @return
	 */
	public String productReturn(){
		return "productReturn";
	}
	
	/**
	 * 查询退货商品明细
	 * @return
	 */
	public String searchProductReturn(){
		if(productReturnPage ==null){
			productReturnPage = new BasePagination<ProductReturnReport>();
		}
		try {
			productReportService.searchProductReturn(productReturnPage);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "productReturn";
	}
	
	/**
	 * 导出退货商品明细报表
	 * @return
	 */
	public String exportProudctReturn(){
		try {
			List<ProductReturnReport> reports = productReportService.listProductReturn(productReturnPage);
			
			ActionContext ac = ActionContext.getContext();
	        ServletContext sc = (ServletContext) ac.get(ServletActionContext.SERVLET_CONTEXT);
	        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        this.setFileName(sdf.format(new Date()).replace("-", "").replace(" ", "").replace(":", ""));
	        String x = sc.getRealPath("/")+"productReturnReport.xls";
			if(reports==null || reports.size()==0){
				reports =new ArrayList<ProductReturnReport>();
				reports.add(new ProductReturnReport());
			}
			ExportExcel<ProductReturnReport> ee = new ExportExcel<ProductReturnReport>(reports);
			ee.setRowi(ee.getRowi()+1);//填充数据起始行数
			//title row
			Row titleRow = ee.getSheet().createRow(0);
			//titleRow.setHeightInPoints(20);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellValue("新龙直销退货商品管理表");
			ee.getSheet().addMergedRegion(CellRangeAddress.valueOf("$A$1:$G$1"));
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
	 * 
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
	 * 
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
	
	/**
	 * 获取全部排期
	 * 
	 * @return
	 */
	public List<Plan> getPlans() {
		List<Plan> plans = new ArrayList<Plan>();
		try {
			plans = planService.listAllPlans();
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return plans;
	}
	
	/**
	 * 根据商家获取品牌
	 * 
	 * @return
	 */
	public String getBrandsBySeller() {		
		try {
			if(sellerId == null){
				brands = brandService.listBrands();
			}else {
				brands = brandService.listBrandsBySellerId(sellerId);
			}
			JSONArray jsonArray = JSONArray.fromObject(brands); 
			try {
				ServletActionContext.getResponse().getWriter().print(jsonArray);
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return null;		 
	}
	
	
	/**
	 * 根据商家获取排期
	 * 
	 * @return
	 */
	public String getPlansBySeller() {		
		try {
			if(everySalesDetailReportPage == null || everySalesDetailReportPage.getParams() == null){
				plans = planService.listPlansBySellerId(new HashMap<String,String>());
			}else{
				plans = planService.listPlansBySellerId(everySalesDetailReportPage.getParams());
			}
			JSONArray jsonArray = JSONArray.fromObject(plans); 
			try {
				ServletActionContext.getResponse().getWriter().print(jsonArray);
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return null;		 
	}
	
	/**
	 * 跳转到商家信息
	 * @return
	 */
	
	public String sellerReport(){
		return "searchSellerReport";
	}
	
	/**
	 * 获取商家信息
	 * @return
	 */
	
	public String searchSellerReport(){
		if (sellerReportPage == null) {
			sellerReportPage=new BasePagination<SellerReport>();
		}
		try {
			productReportService.searchSellerByUserName(sellerReportPage);
		 
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return "searchSellerReport";
	}
	
	/**
	 * 导出获取商家信息
	 * @return
	 */ 
	public String exportSellerReports(){
		try {	
			if (sellerReportPage == null) {
				sellerReportPage=new BasePagination<SellerReport>();
			}
			sellerReportPage.setLimit(Integer.MAX_VALUE);
			productReportService.searchSellerByUserName(sellerReportPage);	
 			List<SellerReport> reports= (List<SellerReport>)sellerReportPage.getResult();
			
			ActionContext ac = ActionContext.getContext();
	        ServletContext sc = (ServletContext) ac.get(ServletActionContext.SERVLET_CONTEXT);
	        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        this.setFileName(sdf.format(new Date()).replace("-", "").replace(" ", "").replace(":", ""));
	        String x = sc.getRealPath("/")+"sellerReport.xls";
			if(reports==null || reports.size()==0){
				reports = new ArrayList<SellerReport>();
				reports.add(new SellerReport());
			}
			ExportExcel<SellerReport> ee = new ExportExcel<SellerReport>(reports);
			ee.setRowi(ee.getRowi()+1);
			//title row
			Row titleRow = ee.getSheet().createRow(0);
			//titleRow.setHeightInPoints(45);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellValue("新龙直销商家信息表");
			ee.getSheet().addMergedRegion(CellRangeAddress.valueOf("$A$1:$F$1"));
			
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
	 * 跳转到品牌区域销售情况
	 * @return
	 */
	public String areaSellReport(){
		 
		return "searchAreaSellReport";
	}
	/**
	 * 获取品牌区域销售情况
	 * @return
	 */
	public String searchAreaSellReport(){
		if (areaSellReportPage == null) {
			areaSellReportPage=new BasePagination<AreaSellReport>();
		}
		try {
			productReportService.searchAreaSellByBrand(areaSellReportPage);
		 
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return "searchAreaSellReport";
	}
	
	/**
	 * 获取品牌区域销售情况
	 * @return
	 */
	public String exportAreaSellReports(){
		try {	
			if (areaSellReportPage == null) {
				areaSellReportPage=new BasePagination<AreaSellReport>();
			}
			areaSellReportPage.setLimit(Integer.MAX_VALUE);
			productReportService.searchAreaSellByBrand(areaSellReportPage);	
 			List<AreaSellReport> reports= (List<AreaSellReport>)areaSellReportPage.getResult();
			
			ActionContext ac = ActionContext.getContext();
	        ServletContext sc = (ServletContext) ac.get(ServletActionContext.SERVLET_CONTEXT);
	        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        this.setFileName(sdf.format(new Date()).replace("-", "").replace(" ", "").replace(":", ""));
	        String x = sc.getRealPath("/")+"areaSellReport.xls";
			if(reports==null || reports.size()==0){
				reports = new ArrayList<AreaSellReport>();
				reports.add(new AreaSellReport());
			}
 				
			ExportExcel<AreaSellReport> ee = new ExportExcel<AreaSellReport>(reports);
			ee.setRowi(ee.getRowi()+1);
			//title row
			Row titleRow = ee.getSheet().createRow(0);
			//titleRow.setHeightInPoints(45);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellValue("新龙直销品牌区域销售列表");
			ee.getSheet().addMergedRegion(CellRangeAddress.valueOf("$A$1:$D$1"));
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
	 * 跳转到订单情况
	 * @return
	 */
	public String orderDetailReport(){
		return "searchOrderDetailReport";
	}
	
	/**
	 * 获取订单情况
	 * @return
	 */
	public String searchOrderDetailReport(){
		if (orderDetailReportPage == null) {
			orderDetailReportPage=new BasePagination<OrderDetailReport>();
		}
		try {
			productReportService.searchOrdersByCreateTime(orderDetailReportPage);
		 
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return "searchOrderDetailReport";
	}
	
	/**
	 * 导出订单情况
	 * @return
	 */
	public String exportOrderDetailReports(){
		try {	
			if (orderDetailReportPage == null) {
				orderDetailReportPage=new BasePagination<OrderDetailReport>();
			}
			orderDetailReportPage.setLimit(Integer.MAX_VALUE);
			productReportService.searchOrdersByCreateTime(orderDetailReportPage);	
 			List<OrderDetailReport> items= (List<OrderDetailReport>)orderDetailReportPage.getResult();
 			List<OrderDetailReport> reports =  new ArrayList<OrderDetailReport>();
 			for(OrderDetailReport orderDetail : items){ 
 				orderDetail.setOrderStatus(PropertyUtil.getPropertyValue("order.status."+orderDetail.getStatus(), null));
  				reports.add(orderDetail);
			}
			ActionContext ac = ActionContext.getContext();
	        ServletContext sc = (ServletContext) ac.get(ServletActionContext.SERVLET_CONTEXT);
	        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        this.setFileName(sdf.format(new Date()).replace("-", "").replace(" ", "").replace(":", ""));
	        String x = sc.getRealPath("/")+"orderDetailReport.xls";
			if(reports==null || reports.size()==0){
				reports = new ArrayList<OrderDetailReport>(); 
				reports.add(new OrderDetailReport());
  				 
			} 
			ExportExcel<OrderDetailReport> ee = new ExportExcel<OrderDetailReport>(reports);
			ee.setRowi(ee.getRowi()+1);
			//title row
			Row titleRow = ee.getSheet().createRow(0);
			//titleRow.setHeightInPoints(45);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellValue("新龙直销订单详细表");
			ee.getSheet().addMergedRegion(CellRangeAddress.valueOf("$A$1:$F$1"));
			
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
	 * 获取子类别
	 * @return
	 */
	public List<ProductCategory> getChildProductCategorys() {
		List<ProductCategory> childProductCategorys = new ArrayList<ProductCategory>();
		try {
			childProductCategorys = productCategoryService.listChildProductCategory();
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return childProductCategorys;
	}
	
	/**
	 * 跳转到品牌销售页面
	 * @return
	 */
	public String brandSell(){
		return "brandSell";
	}
	
	/**
	 * 品牌销售明细
	 * @return
	 */
	public String searchBrandSellReport(){
		if(brandSellReportPage ==null){
			brandSellReportPage = new BasePagination<BrandSellPlanReport>();
		}
		try {
			productReportService.searchBrandSellReport(brandSellReportPage);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "brandSell";
	}
	
	/**
	 * 导出品牌销售明细
	 * @return
	 */
	public String exportBrandSellReport(){
		try {
			List<BrandSellPlanReport> reports = productReportService.listBrandSellReport(brandSellReportPage);
			
			ActionContext ac = ActionContext.getContext();
	        ServletContext sc = (ServletContext) ac.get(ServletActionContext.SERVLET_CONTEXT);
	        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        this.setFileName(sdf.format(new Date()).replace("-", "").replace(" ", "").replace(":", ""));
	        String x = sc.getRealPath("/")+"brandSellPlanReport.xls";
			if(reports==null || reports.size()==0){
				reports = new ArrayList<BrandSellPlanReport>();
				reports.add(new BrandSellPlanReport());
			}
			ExportExcel<BrandSellPlanReport> ee = new ExportExcel<BrandSellPlanReport>(reports);
			ee.setRowi(ee.getRowi()+1);//填充数据起始行数
			//title row
			Row titleRow = ee.getSheet().createRow(0);
			//titleRow.setHeightInPoints(20);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellValue("新龙直销品牌销售明细表");
			ee.getSheet().addMergedRegion(CellRangeAddress.valueOf("$A$1:$I$1"));
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
	
	public BasePagination<ProductReturnReport> getProductReturnPage() {
		return productReturnPage;
	}

	public void setProductReturnPage(
			BasePagination<ProductReturnReport> productReturnPage) {
		this.productReturnPage = productReturnPage;
	}

	public BasePagination<SalesDetailReport> getSalesDetailReportPage() {
		return salesDetailReportPage;
	}

	public void setSalesDetailReportPage(
			BasePagination<SalesDetailReport> salesDetailReportPage) {
		this.salesDetailReportPage = salesDetailReportPage;
	}
	
	public BasePagination<EverydaySalesDetailReport> getEverySalesDetailReportPage() {
		return everySalesDetailReportPage;
	}
	public void setEverySalesDetailReportPage(
			BasePagination<EverydaySalesDetailReport> everySalesDetailReportPage) {
		this.everySalesDetailReportPage = everySalesDetailReportPage;
	}
	public BasePagination<SellerReport> getSellerReportPage() {
		return sellerReportPage;
	}

	public void setSellerReportPage(BasePagination<SellerReport> sellerReportPage) {
		this.sellerReportPage = sellerReportPage;
	}

	public BasePagination<AreaSellReport> getAreaSellReportPage() {
		return areaSellReportPage;
	}

	public void setAreaSellReportPage(
			BasePagination<AreaSellReport> areaSellReportPage) {
		this.areaSellReportPage = areaSellReportPage;
	}

	public BasePagination<OrderDetailReport> getOrderDetailReportPage() {
		return orderDetailReportPage;
	}

	public void setOrderDetailReportPage(
			BasePagination<OrderDetailReport> orderDetailReportPage) {
		this.orderDetailReportPage = orderDetailReportPage;
	}

	public SalesDetailReport getSalesDetailReport() {
		return salesDetailReport;
	}

	public void setSalesDetailReport(SalesDetailReport salesDetailReport) {
		this.salesDetailReport = salesDetailReport;
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

	public List<ProductCategory> getListProductCategorie() {
		return listProductCategorie;
	}

	public void setListProductCategorie(List<ProductCategory> listProductCategorie) {
		this.listProductCategorie = listProductCategorie;
	}
	public BasePagination<ClassifySellReport> getClassifyReportPage() {
		return classifyReportPage;
	}
	public void setClassifyReportPage(
			BasePagination<ClassifySellReport> classifyReportPage) {
		this.classifyReportPage = classifyReportPage;
	}
	public BasePagination<MemberSellReport> getMemberReportPage() {
		return memberReportPage;
	}
	public void setMemberReportPage(
			BasePagination<MemberSellReport> memberReportPage) {
		this.memberReportPage = memberReportPage;
	}
	public String getReportMenuId() {
		return reportMenuId;
	}
	public void setReportMenuId(String reportMenuId) {
		this.reportMenuId = reportMenuId;
	}
	public BasePagination<BrandSellPlanReport> getBrandSellReportPage() {
		return brandSellReportPage;
	}
	public void setBrandSellReportPage(
			BasePagination<BrandSellPlanReport> brandSellReportPage) {
		this.brandSellReportPage = brandSellReportPage;
	}
	public Integer getSellerId() {
		return sellerId;
	}
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public void setBrands(List<Brand> brands) {
		this.brands = brands;
	}
	public void setPlans(List<Plan> plans) {
		this.plans = plans;
	}
	
	 
	
}
