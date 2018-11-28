package com.ytoxl.module.uhome.uhomereport.service;

import java.util.List;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.AreaSellReport;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.BrandSellPlanReport;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.ClassifySellReport;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.EverydaySalesDetailReport;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.MemberSellReport;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.OrderDetailReport;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.ProductReturnReport;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.SalesDetailReport;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.SellerReport;


public interface ProductReportService {
	 
	/**
	 * 获取订单信息列表
	 * 
	 * @return
	 * @throws UhomeStoreException
	 */
	public void searchOrdersByCreateTime(BasePagination<OrderDetailReport> orderReportDetailPage) throws UhomeStoreException; 

 	
	
	/**
	 * 获取商家列表
	 *  
	 * @return
	 * @throws UhomeStoreException
	 */
	public void searchSellerByUserName(BasePagination<SellerReport> sellerReportPage) throws UhomeStoreException; 
	
	/**
	 * 获取地区品牌销售情况
	 *  
	 * @return
	 * @throws UhomeStoreException
	 */
	public void searchAreaSellByBrand(BasePagination<AreaSellReport> areaSellReportDetailPage) throws UhomeStoreException;
	
	/**
	 * 分页查询退货商品管理
	 * 
	 * @param productReturnPage
	 * @throws UhomeStoreException
	 */
	public void searchProductReturn(BasePagination<ProductReturnReport> productReturnPage) throws UhomeStoreException;
	
	/**
	 * 查询所有退货商品明细
	 * 
	 * @param productReturnPage
	 * @throws UhomeStoreException
	 */
	public List<ProductReturnReport> listProductReturn(BasePagination<ProductReturnReport> productReturnPage)  throws UhomeStoreException;


	/**
	 * 分页查询,会员销售管理报表
	 * @param map
	 * @return
	 */
	public void searchMemberSellReports(BasePagination<MemberSellReport> memberSellReportPage) throws  UhomeStoreException;
	/**
	 * 导出会员销售管理报表数据
	 * @param memberSellReportPage
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<MemberSellReport> listMemberSellReports(BasePagination<MemberSellReport> memberSellReportPage) throws UhomeStoreException; 
	/**
	 * 分页查询,分类销售明细报表
	 * @param map
	 * @return
	 */
	public void searchClassifySellReports(BasePagination<ClassifySellReport> classifySellReportPage) throws  UhomeStoreException;
	
	/**
	 * 分类销售报表信息导出
	 * @param classifySellReportPage
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<ClassifySellReport> listClassifySellReports(BasePagination<ClassifySellReport> classifySellReportPage) throws UhomeStoreException;
	
	/**
	 * 商家销售管理分页显示查询
	 * 
	 * @param map
	 * @return
	 */
	public void searchSalesDetailReports(
			BasePagination<SalesDetailReport> salesDetailReportPage)
			throws UhomeStoreException;

	/**
	 * 商家销售管理报表导出查询
	 * 
	 * @param map
	 * @return
	 */
	public List<SalesDetailReport> listSalesDetailReports(
			BasePagination<SalesDetailReport> salesDetailReportPage)
			throws UhomeStoreException;
	
	/**
	 * 分页查询品牌销售明细
	 * 
	 * @param sellerBrandReportPage
	 * @throws UhomeStoreException
	 */
	public void searchBrandSellReport(BasePagination<BrandSellPlanReport> brandSellReportPage) throws UhomeStoreException;
	
	/**
	 * 查询所有品牌销售明细
	 * 
	 * @param sellerBrandReportPage
	 * @throws UhomeStoreException
	 */
	public List<BrandSellPlanReport> listBrandSellReport(BasePagination<BrandSellPlanReport> brandSellReportPage) throws UhomeStoreException;
	
	/**
	 * 每日特卖会销售明细分页显示查询
	 * 
	 * @param sellerBrandReportPage
	 * @throws UhomeStoreException
	 */
	public void searchEverydaySalesDetailReports(
			BasePagination<EverydaySalesDetailReport> everydaySalesDetailReportPage)
			throws UhomeStoreException;
	
	
	/**
	 * 每日特卖会销售明细导出查询
	 * 
	 * @param map
	 * @return
	 */
	public List<EverydaySalesDetailReport> listEverydaySalesDetailReports(
			BasePagination<EverydaySalesDetailReport> everydaySalesDetailReportPage)
			throws UhomeStoreException;
}
