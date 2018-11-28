package com.ytoxl.module.uhome.uhomereport.dataobject.resultmap;

import java.math.BigDecimal;
import java.util.Date;

import com.ytoxl.module.uhome.uhomebase.common.utils.excel.ExcelField;
import com.ytoxl.module.uhome.uhomebase.common.utils.excel.FormatType;

/**
 * 商家销售管理明细
 * @author hwx
 *
 */
public class SalesDetailReport {
	
	/** 商家名称  */
	@ExcelField(filedName="商家",sort=1)
	private String companyName;
	
	/** 品牌名称  */
	@ExcelField(filedName="品牌",sort=2)
	private String brandName;
	
	/** 分类名称 */
	@ExcelField(filedName="分类",sort=3)
	private String categoryName;
	
	/** 上线日期 */
	@ExcelField(filedName="上线日期",sort=4,formatType=FormatType.DATE,pattern="yyyy-MM-dd HH:mm:ss")
	private Date planStartTime;
	
	/** 下线日期 */
	@ExcelField(filedName="下线日期",sort=5,formatType=FormatType.DATE,pattern="yyyy-MM-dd HH:mm:ss")
	private Date planEndTime;
	
	/** 销售数量 */
	@ExcelField(filedName="销售数量",sort=6)
	private Integer salesNum;
	
	/** 销售金额 */
	@ExcelField(filedName="销售金额",sort=7)
	private BigDecimal salesPrice;
	
	private String productSkuId;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Date getPlanStartTime() {
		return planStartTime;
	}

	public void setPlanStartTime(Date planStartTime) {
		this.planStartTime = planStartTime;
	}

	public Date getPlanEndTime() {
		return planEndTime;
	}

	public void setPlanEndTime(Date planEndTime) {
		this.planEndTime = planEndTime;
	}

	public Integer getSalesNum() {
		return salesNum;
	}

	public BigDecimal getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(BigDecimal salesPrice) {
		this.salesPrice = salesPrice;
	}

	public void setSalesNum(Integer salesNum) {
		this.salesNum = salesNum;
	}

	public String getProductSkuId() {
		return productSkuId;
	}

	public void setProductSkuId(String productSkuId) {
		this.productSkuId = productSkuId;
	}
	
	
}
