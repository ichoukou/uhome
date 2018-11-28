package com.ytoxl.module.uhome.uhomereport.service.impl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.CommonConstants;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.common.utils.DateUtil;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.AreaSellReport;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.BrandSellPlanReport;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.ClassifySellReport;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.EverydaySalesDetailReport;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.MemberSellReport;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.OrderDetailReport;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.ProductReturnReport;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.SalesDetailReport;
import com.ytoxl.module.uhome.uhomereport.dataobject.resultmap.SellerReport;
import com.ytoxl.module.uhome.uhomereport.mapper.AreaSellReportMapper;
import com.ytoxl.module.uhome.uhomereport.mapper.BrandSellPlanReportMapper;
import com.ytoxl.module.uhome.uhomereport.mapper.ClassifySellReportMapper;
import com.ytoxl.module.uhome.uhomereport.mapper.EverydaySalesDetailReportMapper;
import com.ytoxl.module.uhome.uhomereport.mapper.MemberSellReportMapper;
import com.ytoxl.module.uhome.uhomereport.mapper.OrderDetailReportMapper;
import com.ytoxl.module.uhome.uhomereport.mapper.ProductReturnReportMapper;
import com.ytoxl.module.uhome.uhomereport.mapper.SalesDetailReportMapper;
import com.ytoxl.module.uhome.uhomereport.mapper.SellerReportMapper;
import com.ytoxl.module.uhome.uhomereport.service.ProductReportService;

@Service
public class ProductReportServiceImpl implements ProductReportService {
	protected static Logger logger = LoggerFactory
			.getLogger(ProductReportServiceImpl.class);

	protected String filterParamReg = "[\\<\\>\\\"\\']";
	@Autowired
	private MemberSellReportMapper<MemberSellReport> memberSellReportMapper;
	@Autowired
	private ClassifySellReportMapper<ClassifySellReport> classifySellReportMapper;
	@Autowired
	private OrderDetailReportMapper<OrderDetailReport> orderDetailReportMapper;

	@Autowired
	private SellerReportMapper<SellerReport> sellerReportMapper;

	@Autowired
	private AreaSellReportMapper<AreaSellReport> areaSellReportMapper;

	@Autowired
	private ProductReturnReportMapper<ProductReturnReport> productReturnReportMapper;

	@Autowired
	private SalesDetailReportMapper<SalesDetailReport> salesDetailReportMapper;

	@Autowired
	private BrandSellPlanReportMapper<BrandSellPlanReport> brandSellPlanReportMapper;

	@Autowired
	private EverydaySalesDetailReportMapper<EverydaySalesDetailReport> everydaySalesDetailReportMapper;

	@Override
	public void searchOrdersByCreateTime(
			BasePagination<OrderDetailReport> orderReportDetailPage)
			throws UhomeStoreException {
		orderReportDetailPage.setFilterParamReg(filterParamReg);
		Map<String, Object> searchParams = orderReportDetailPage
				.getSearchParamsMap();
		searchParams.put(CommonConstants.PAGE_DIR, "desc");
		if (orderReportDetailPage.isNeedSetTotal()) {
			Integer total = orderDetailReportMapper
					.searchOrderByCreateTimeCount(searchParams);
			orderReportDetailPage.setTotal(total);
		}
		Collection<OrderDetailReport> result = orderDetailReportMapper
				.searchOrderByCreateTime(searchParams);
		orderReportDetailPage.setResult(result);
	}

	@Override
	public void searchSellerByUserName(
			BasePagination<SellerReport> sellerReportPage)
			throws UhomeStoreException {
		sellerReportPage.setFilterParamReg(filterParamReg);
		Map<String, Object> searchParams = sellerReportPage
				.getSearchParamsMap();
		if (sellerReportPage.isNeedSetTotal()) {
			Integer total = sellerReportMapper
					.searchSellerByUserNameCount(searchParams);
			sellerReportPage.setTotal(total);
		}
		Collection<SellerReport> result = sellerReportMapper
				.searchSellerByUserName(searchParams);
		sellerReportPage.setResult(result);
	}

	@Override
	public void searchAreaSellByBrand(
			BasePagination<AreaSellReport> areaSellReportDetailPage)
			throws UhomeStoreException {
		areaSellReportDetailPage.setFilterParamReg(filterParamReg);
		Map<String, Object> searchParams = areaSellReportDetailPage
				.getSearchParamsMap();
		searchParams.put(CommonConstants.PAGE_DIR, "desc");
		if (areaSellReportDetailPage.isNeedSetTotal()) {
			Integer total = areaSellReportMapper
					.searchAreaSellByBrandCount(searchParams);
			areaSellReportDetailPage.setTotal(total);
		}
		Collection<AreaSellReport> result = areaSellReportMapper
				.searchAreaSellByBrand(searchParams);
		areaSellReportDetailPage.setResult(result);
	}

	@Override
	public void searchMemberSellReports(
			BasePagination<MemberSellReport> memberSellReportPage)
			throws UhomeStoreException {
		Map<String, Object> searchParams = memberSellReportPage
				.getSearchParamsMap();
		if (memberSellReportPage.isNeedSetTotal()) {
			Integer total = memberSellReportMapper
					.searchMemberSellCount(searchParams);
			memberSellReportPage.setTotal(total);
		}
		Collection<MemberSellReport> result = memberSellReportMapper
				.searchMemberSellReports(searchParams);
		memberSellReportPage.setResult(result);
	}

	@Override
	public void searchClassifySellReports(
			BasePagination<ClassifySellReport> classifySellReportPage)
			throws UhomeStoreException {

		Map<String, Object> searchParams = classifySellReportPage
				.getSearchParamsMap();
		if (classifySellReportPage.isNeedSetTotal()) {
			Integer total = classifySellReportMapper
					.searchClassifySellCount(searchParams);
			classifySellReportPage.setTotal(total);
		}
		Collection<ClassifySellReport> result = classifySellReportMapper
				.searchClassifySellReports(searchParams);
		classifySellReportPage.setResult(result);

	}

	/**
	 * 分页查询退货商品 By xupf
	 */
	@Override
	public void searchProductReturn(
			BasePagination<ProductReturnReport> productReturnPage)
			throws UhomeStoreException {
		Map<String, Object> searchParams = productReturnPage
				.getSearchParamsMap();
		searchParams.put(CommonConstants.PAGE_DIR, "desc");
		if (productReturnPage.isNeedSetTotal()) {
			Integer total = productReturnReportMapper
					.searchProductReturnCount(searchParams);
			productReturnPage.setTotal(total);
		}
		Collection<ProductReturnReport> result = productReturnReportMapper
				.searchProductReturnReport(searchParams);
		productReturnPage.setResult(result);
	}

	/**
	 * 查询所有退货商品明细 By xupf
	 */
	@Override
	public List<ProductReturnReport> listProductReturn(
			BasePagination<ProductReturnReport> productReturnPage)
			throws UhomeStoreException {
		Map<String, Object> searchParams = productReturnPage
				.getSearchParamsMap();
		searchParams.put(CommonConstants.PAGE_DIR, "asc");
		return productReturnReportMapper.listProductReturnReport(searchParams);

	}

	/**
	 * 商家销售管理分页显示查询
	 * 
	 * @author hwx
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public void searchSalesDetailReports(
			BasePagination<SalesDetailReport> salesDetailReportPage)
			throws UhomeStoreException {
		Map<String, Object> searchParams = salesDetailReportPage
				.getSearchParamsMap();
		searchParams.put(CommonConstants.PAGE_SORT, "t.companyName");
		searchParams.put(CommonConstants.PAGE_DIR, "desc");
		if (salesDetailReportPage.isNeedSetTotal()) {
			Integer total = salesDetailReportMapper
					.searchSalesDetailReportsCount(searchParams);
			salesDetailReportPage.setTotal(total);
		}
		List<SalesDetailReport> result = salesDetailReportMapper
				.searchSalesDetailReports(searchParams);
		for (SalesDetailReport sdr : result) {
			searchParams.put("productSkuId", sdr.getProductSkuId());
			searchParams.put("planStartTime",
					DateUtil.toSeconds(sdr.getPlanStartTime()));
			searchParams.put("planEndTime",
					DateUtil.toSeconds(sdr.getPlanEndTime()));
			List<SalesDetailReport> totalList = salesDetailReportMapper
					.searchTotalNumAndAmount(searchParams);
			for (SalesDetailReport everyder : totalList) {
				sdr.setSalesNum(everyder.getSalesNum());
				sdr.setSalesPrice(everyder.getSalesPrice());
			}
		}
		salesDetailReportPage.setResult(result);
	}

	/**
	 * 商家销售管理报表导出查询
	 * 
	 * @author hwx
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public List<SalesDetailReport> listSalesDetailReports(
			BasePagination<SalesDetailReport> salesDetailReportPage)
			throws UhomeStoreException {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if (salesDetailReportPage != null) {
			searchParams = salesDetailReportPage.getSearchParamsMap();
		}
		searchParams.put(CommonConstants.PAGE_SORT, "t.companyName");
		searchParams.put(CommonConstants.PAGE_DIR, "desc");
		List<SalesDetailReport> result = salesDetailReportMapper
				.listSalesDetailReports(searchParams);
		for (SalesDetailReport sdr : result) {
			searchParams.put("productSkuId", sdr.getProductSkuId());
			searchParams.put("planStartTime",
					DateUtil.toSeconds(sdr.getPlanStartTime()));
			searchParams.put("planEndTime",
					DateUtil.toSeconds(sdr.getPlanEndTime()));
			List<SalesDetailReport> totalList = salesDetailReportMapper
					.searchTotalNumAndAmount(searchParams);
			for (SalesDetailReport everyder : totalList) {
				sdr.setSalesNum(everyder.getSalesNum());
				sdr.setSalesPrice(everyder.getSalesPrice());
			}
		}
		return result;
	}

	/**
	 * 每日特卖会销售明细分页显示查询
	 * 
	 * @author hwx
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public void searchEverydaySalesDetailReports(
			BasePagination<EverydaySalesDetailReport> everydaySalesDetailReportPage)
			throws UhomeStoreException {
		Map<String, Object> searchParams = everydaySalesDetailReportPage
				.getSearchParamsMap();
		searchParams.put(CommonConstants.PAGE_SORT, "t.companyName");
		searchParams.put(CommonConstants.PAGE_DIR, "desc");
		String time = (String) searchParams.get("time");
		searchParams.put("startTime", time + "-01");
		searchParams.put("endTime", time + "-31");
		if (everydaySalesDetailReportPage.isNeedSetTotal()) {
			Integer total = everydaySalesDetailReportMapper
					.searchEverydaySalesDetailReportsCount(searchParams);
			everydaySalesDetailReportPage.setTotal(total);
		}
		List<EverydaySalesDetailReport> result = everydaySalesDetailReportMapper
				.searchEverydaySalesDetailReports(searchParams);
		for (EverydaySalesDetailReport sdr : result) {
			searchParams.put("productSkuId", sdr.getProductSkuId());
			List<EverydaySalesDetailReport> csList = everydaySalesDetailReportMapper
					.searchColourAndSize(searchParams);
			for (EverydaySalesDetailReport csder : csList) {
				if (csder.getSkuOptionId() != null
						&& csder.getSkuOptionId().equals("100000")) {
					sdr.setColour(csder.getSkuOptionValue());
				} else if (csder.getSkuOptionId() != null
						&& csder.getSkuOptionId().equals("100001")) {
					sdr.setSize(csder.getSkuOptionValue());
				}
			}
			searchParams.put("startDate",
					DateUtil.toSeconds(sdr.getStartTime()));
			searchParams.put("endDate", DateUtil.toSeconds(sdr.getEndTime()));
			List<EverydaySalesDetailReport> everydayList = everydaySalesDetailReportMapper
					.searchEverydaySalesDetail(searchParams);
			Integer num = new Integer(0);
			BigDecimal sd = new BigDecimal(0.00);
			for (EverydaySalesDetailReport everyder : everydayList) {
				Class clazz = sdr.getClass();
				Field[] fields = clazz.getDeclaredFields();
				for (Field f : fields) {
					try {
						PropertyDescriptor pd = new PropertyDescriptor(
								f.getName(), clazz);
						Method wM = pd.getWriteMethod();
						int day = (int) DateUtil.dayInterval(everyder.getDay(),
								sdr.getStartDay());
						if (pd.getName().equals("salesNumD" + day)) {
							wM.invoke(sdr, everyder.getSalesNum());
							num += everyder.getSalesNum();
						}
						if (pd.getName().equals("salesPriceD" + day)) {
							wM.invoke(sdr, everyder.getSalesPrice());
							sd = sd.add(everyder.getSalesPrice());
						}
					} catch (Exception e) {
						logger.error("每日特卖销售数据出错:" + e.getMessage());
					}
				}
			}
			sdr.setSalesTotalNum(num);
			sdr.setSalesTotalPrice(sd);
		}
		everydaySalesDetailReportPage.setResult(result);
	}

	/**
	 * 商家销售管理报表导出查询
	 * 
	 * @author hwx
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public List<EverydaySalesDetailReport> listEverydaySalesDetailReports(
			BasePagination<EverydaySalesDetailReport> everydaySalesDetailReportPage)
			throws UhomeStoreException {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if (everydaySalesDetailReportPage != null) {
			searchParams = everydaySalesDetailReportPage.getSearchParamsMap();
		}
		searchParams.put(CommonConstants.PAGE_SORT, "t.companyName");
		searchParams.put(CommonConstants.PAGE_DIR, "desc");
		String time = (String) searchParams.get("time");
		searchParams.put("startTime", time + "-01");
		searchParams.put("endTime", time + "-31");
		List<EverydaySalesDetailReport> result = everydaySalesDetailReportMapper
				.listEverydaySalesDetailReports(searchParams);
		for (EverydaySalesDetailReport sdr : result) {
			searchParams.put("productSkuId", sdr.getProductSkuId());
			List<EverydaySalesDetailReport> csList = everydaySalesDetailReportMapper
					.searchColourAndSize(searchParams);
			for (EverydaySalesDetailReport csder : csList) {
				if (csder.getSkuOptionId() != null
						&& csder.getSkuOptionId().equals("100000")) {
					sdr.setColour(csder.getSkuOptionValue());
				} else if (csder.getSkuOptionId() != null
						&& csder.getSkuOptionId().equals("100001")) {
					sdr.setSize(csder.getSkuOptionValue());
				}
			}
			searchParams.put("startDate",
					DateUtil.toSeconds(sdr.getStartTime()));
			searchParams.put("endDate", DateUtil.toSeconds(sdr.getEndTime()));
			List<EverydaySalesDetailReport> everydayList = everydaySalesDetailReportMapper
					.searchEverydaySalesDetail(searchParams);
			Integer num = new Integer(0);
			BigDecimal sd = new BigDecimal(0.00);
			for (EverydaySalesDetailReport everyder : everydayList) {
				Class clazz = sdr.getClass();
				Field[] fields = clazz.getDeclaredFields();
				for (Field f : fields) {
					try {
						PropertyDescriptor pd = new PropertyDescriptor(
								f.getName(), clazz);
						Method wM = pd.getWriteMethod();
						int day = (int) DateUtil.dayInterval(everyder.getDay(),
								sdr.getStartDay());
						if (pd.getName().equals("salesNumD" + day)) {
							wM.invoke(sdr, everyder.getSalesNum());
							num += everyder.getSalesNum();
						}
						if (pd.getName().equals("salesPriceD" + day)) {
							wM.invoke(sdr, everyder.getSalesPrice());
							sd = sd.add(everyder.getSalesPrice());
						}
					} catch (Exception e) {
						logger.error("每日特卖销售数据出错:" + e.getMessage());
					}
				}
			}
			sdr.setSalesTotalNum(num);
			sdr.setSalesTotalPrice(sd);
		}
		return result;
	}

	/**
	 * 会员管理报表导出信息
	 */
	@Override
	public List<MemberSellReport> listMemberSellReports(
			BasePagination<MemberSellReport> memberSellReportPage)
			throws UhomeStoreException {
		Map<String, Object> searchParams = memberSellReportPage
				.getSearchParamsMap();
		return memberSellReportMapper.listMemberSellReports(searchParams);
	}

	/**
	 * 分类销售报表导出信息
	 */
	@Override
	public List<ClassifySellReport> listClassifySellReports(
			BasePagination<ClassifySellReport> classifySellReportPage)
			throws UhomeStoreException {
		Map<String, Object> searchParams = classifySellReportPage
				.getSearchParamsMap();
		return classifySellReportMapper.listClassifySellReports(searchParams);
	}

	/**
	 * 分页查询品牌销售明细 By xupf
	 */
	@Override
	public void searchBrandSellReport(
			BasePagination<BrandSellPlanReport> brandSellReportPage)
			throws UhomeStoreException {
		Map<String, Object> searchParams = brandSellReportPage
				.getSearchParamsMap();
		searchParams.put(CommonConstants.PAGE_DIR, "desc");
		if (brandSellReportPage.isNeedSetTotal()) {
			Integer total = brandSellPlanReportMapper
					.searchSellerCountByPlan(searchParams);
			brandSellReportPage.setTotal(total);
		}
		// 按排期查询商家品牌基本信息
		Collection<BrandSellPlanReport> result = brandSellPlanReportMapper
				.searchSellerByPlan(searchParams);
		result = getBranSellOtherCount(result);
		brandSellReportPage.setResult(result);

	}

	/**
	 * 查询所有品牌销售明细 By xupf
	 */
	@Override
	public List<BrandSellPlanReport> listBrandSellReport(
			BasePagination<BrandSellPlanReport> brandSellReportPage)
			throws UhomeStoreException {
		Map<String, Object> searchParams = brandSellReportPage
				.getSearchParamsMap();
		searchParams.put(CommonConstants.PAGE_DIR, "asc");
		// 按排期查询商家品牌基本信息
		List<BrandSellPlanReport> brandSellPlanReports = brandSellPlanReportMapper
				.listSellerByPlan(searchParams);
		brandSellPlanReports = (List<BrandSellPlanReport>) getBranSellOtherCount(brandSellPlanReports);
		return brandSellPlanReports;
	}

	/**
	 * 查询商家品牌的其他统计信息 By xupf
	 * 
	 * @param result
	 * @param time
	 * @return
	 */
	public Collection<BrandSellPlanReport> getBranSellOtherCount(
			Collection<BrandSellPlanReport> result) {
		Collection<BrandSellPlanReport> resetResult = result;
		for (BrandSellPlanReport b : resetResult) {
			// 设置查询参数
			HashMap<String, Object> otherSearchParams = new HashMap<String, Object>();
			otherSearchParams.put("stime", b.getStartTime()); // 开始时间
			otherSearchParams.put("etime", b.getEndTime()); // 结束时间
			otherSearchParams.put("planId", b.getPlanId()); // 排期ID
			otherSearchParams.put("brandId", b.getBrandId()); // 品牌ID
			otherSearchParams.put("sellerId", b.getSellerId()); // 卖家ID
			// 查询商家的其他统计信息
			BrandSellPlanReport temp = brandSellPlanReportMapper
					.getSellerOtherCount(otherSearchParams);

			b.setSkuSellNum(temp.getSkuSellNum());
			// 判断销售数量是否为NULL
			if (temp.getSellNum() != null) {
				b.setSellNum(temp.getSellNum());
			} else {
				b.setSellNum(0);
			}
			// 判断销售金额是否为NULL
			if (temp.getSellAmount() != null) {
				b.setSellAmount(temp.getSellAmount());
			} else {
				b.setSellAmount(new BigDecimal(0.00));
			}
		}
		return resetResult;
	}
}
