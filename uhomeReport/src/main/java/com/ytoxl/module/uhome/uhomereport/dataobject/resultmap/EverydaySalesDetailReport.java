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
public class EverydaySalesDetailReport {
	
	/** 商家名称  */
	@ExcelField(filedName="商家",sort=1)
	private String companyName;
	
	/** 品牌名称  */
	@ExcelField(filedName="品牌",sort=2)
	private String brandName;
	
	/** 商品品类 */
	@ExcelField(filedName="商品品类",sort=3)
	private String categoryName;
	
	/** 商品名称 */
	@ExcelField(filedName="商品名称",sort=4)
	private String productName;
	
	/** SKU CODE */
	@ExcelField(filedName="SKU",sort=5)
	private String skuCode;
	
	/** 国际码 */
	@ExcelField(filedName="国际码",sort=6)
	private String internationalCode;
	
	/** 尺寸 */
	@ExcelField(filedName="尺寸",sort=7)
	private String size;
	
	/** 颜色 */
	@ExcelField(filedName="颜色",sort=8)
	private String colour;
	
	/** 特卖价 */
	@ExcelField(filedName="特卖价",sort=9)
	private String salePrice;
	
	/** D1销售数量 */
	@ExcelField(filedName="D1销售数量",sort=10)
	private Integer salesNumD1;
	
	/** D1销售金额 */
	@ExcelField(filedName="D1销售金额",sort=11)
	private BigDecimal salesPriceD1;
	
	/** D2销售数量 */
	@ExcelField(filedName="D2销售数量",sort=12)
	private Integer salesNumD2;
	
	/** D2销售金额 */
	@ExcelField(filedName="D2销售金额",sort=13)
	private BigDecimal salesPriceD2;
	
	/** D3销售数量 */
	@ExcelField(filedName="D3销售数量",sort=14)
	private Integer salesNumD3;
	
	/** D3销售金额 */
	@ExcelField(filedName="D3销售金额",sort=15)
	private BigDecimal salesPriceD3;
	
	/** D4销售数量 */
	@ExcelField(filedName="D4销售数量",sort=16)
	private Integer salesNumD4;
	
	/** D4销售金额 */
	@ExcelField(filedName="D4销售金额",sort=17)
	private BigDecimal salesPriceD4;
	
	/** D5销售数量 */
	@ExcelField(filedName="D5销售数量",sort=18)
	private Integer salesNumD5;
	
	/** D5销售金额 */
	@ExcelField(filedName="D5销售金额",sort=19)
	private BigDecimal salesPriceD5;
	
	/** D6销售数量 */
	@ExcelField(filedName="D6销售数量",sort=20)
	private Integer salesNumD6;
	
	/** D6销售金额 */
	@ExcelField(filedName="D6销售金额",sort=21)
	private BigDecimal salesPriceD6;
	
	/** D7销售数量 */
	@ExcelField(filedName="D7销售数量",sort=22)
	private Integer salesNumD7;
	
	/** D7销售金额 */
	@ExcelField(filedName="D7销售金额",sort=23)
	private BigDecimal salesPriceD7;
	
	/** D8销售数量 */
	@ExcelField(filedName="D8销售数量",sort=24)
	private Integer salesNumD8;
	
	/** D8销售金额 */
	@ExcelField(filedName="D8销售金额",sort=25)
	private BigDecimal salesPriceD8;
	
	/** D9销售数量 */
	@ExcelField(filedName="D9销售数量",sort=26)
	private Integer salesNumD9;
	
	/** D9销售金额 */
	@ExcelField(filedName="D9销售金额",sort=27)
	private BigDecimal salesPriceD9;
	
	/** D10销售数量 */
	@ExcelField(filedName="D10销售数量",sort=28)
	private Integer salesNumD10;
	
	/** D10销售金额 */
	@ExcelField(filedName="D10销售金额",sort=29)
	private BigDecimal salesPriceD10;
	
	/** D11销售数量 */
	@ExcelField(filedName="D11销售数量",sort=30)
	private Integer salesNumD11;
	
	/** D11销售金额 */
	@ExcelField(filedName="D11销售金额",sort=31)
	private BigDecimal salesPriceD11;
	
	/** D12销售数量 */
	@ExcelField(filedName="D12销售数量",sort=32)
	private Integer salesNumD12;
	
	/** D12销售金额 */
	@ExcelField(filedName="D12销售金额",sort=33)
	private BigDecimal salesPriceD12;
	
	/** D13销售数量 */
	@ExcelField(filedName="D13销售数量",sort=34)
	private Integer salesNumD13;
	
	/** D13销售金额 */
	@ExcelField(filedName="D13销售金额",sort=35)
	private BigDecimal salesPriceD13;
	
	/** D14销售数量 */
	@ExcelField(filedName="D14销售数量",sort=36)
	private Integer salesNumD14;
	
	/** D14销售金额 */
	@ExcelField(filedName="D14销售金额",sort=37)
	private BigDecimal salesPriceD14;
	
	/** D15销售数量 */
	@ExcelField(filedName="D15销售数量",sort=38)
	private Integer salesNumD15;
	
	/** D15销售金额 */
	@ExcelField(filedName="D15销售金额",sort=39)
	private BigDecimal salesPriceD15;
	
	/** D16销售数量 */
	@ExcelField(filedName="D16销售数量",sort=40)
	private Integer salesNumD16;
	
	/** D16销售金额 */
	@ExcelField(filedName="D16销售金额",sort=41)
	private BigDecimal salesPriceD16;
	
	/** D17销售数量 */
	@ExcelField(filedName="D17销售数量",sort=42)
	private Integer salesNumD17;
	
	/** D17销售金额 */
	@ExcelField(filedName="D17销售金额",sort=43)
	private BigDecimal salesPriceD17;
	
	/** D18销售数量 */
	@ExcelField(filedName="D18销售数量",sort=44)
	private Integer salesNumD18;
	
	/** D18销售金额 */
	@ExcelField(filedName="D18销售金额",sort=45)
	private BigDecimal salesPriceD18;
	
	/** D19销售数量 */
	@ExcelField(filedName="D19销售数量",sort=46)
	private Integer salesNumD19;
	
	/** D19销售金额 */
	@ExcelField(filedName="D19销售金额",sort=47)
	private BigDecimal salesPriceD19;
	
	/** D20销售数量 */
	@ExcelField(filedName="D20销售数量",sort=48)
	private Integer salesNumD20;
	
	/** D20销售金额 */
	@ExcelField(filedName="D20销售金额",sort=49)
	private BigDecimal salesPriceD20;
	
	/** D21销售数量 */
	@ExcelField(filedName="D21销售数量",sort=50)
	private Integer salesNumD21;
	
	/** D21销售金额 */
	@ExcelField(filedName="D21销售金额",sort=51)
	private BigDecimal salesPriceD21;
	
	/** D22销售数量 */
	@ExcelField(filedName="D22销售数量",sort=52)
	private Integer salesNumD22;
	
	/** D22销售金额 */
	@ExcelField(filedName="D22销售金额",sort=53)
	private BigDecimal salesPriceD22;
	
	/** D23销售数量 */
	@ExcelField(filedName="D23销售数量",sort=54)
	private Integer salesNumD23;
	
	/** D23销售金额 */
	@ExcelField(filedName="D23销售金额",sort=55)
	private BigDecimal salesPriceD23;
	
	/** D24销售数量 */
	@ExcelField(filedName="D24销售数量",sort=56)
	private Integer salesNumD24;
	
	/** D24销售金额 */
	@ExcelField(filedName="D24销售金额",sort=57)
	private BigDecimal salesPriceD24;
	
	/** D25销售数量 */
	@ExcelField(filedName="D25销售数量",sort=58)
	private Integer salesNumD25;
	
	/** D25销售金额 */
	@ExcelField(filedName="D25销售金额",sort=59)
	private BigDecimal salesPriceD25;
	
	/** D26销售数量 */
	@ExcelField(filedName="D26销售数量",sort=60)
	private Integer salesNumD26;
	
	/** D26销售金额 */
	@ExcelField(filedName="D26销售金额",sort=61)
	private BigDecimal salesPriceD26;
	
	/** D27销售数量 */
	@ExcelField(filedName="D27销售数量",sort=62)
	private Integer salesNumD27;
	
	/** D27销售金额 */
	@ExcelField(filedName="D27销售金额",sort=63)
	private BigDecimal salesPriceD27;
	
	/** D28销售数量 */
	@ExcelField(filedName="D28销售数量",sort=64)
	private Integer salesNumD28;
	
	/** D28销售金额 */
	@ExcelField(filedName="D28销售金额",sort=65)
	private BigDecimal salesPriceD28;
	
	/** D29销售数量 */
	@ExcelField(filedName="D29销售数量",sort=66)
	private Integer salesNumD29;
	
	/** D29销售金额 */
	@ExcelField(filedName="D29销售金额",sort=67)
	private BigDecimal salesPriceD29;
	
	/** D30销售数量 */
	@ExcelField(filedName="D30销售数量",sort=68)
	private Integer salesNumD30;
	
	/** D30销售金额 */
	@ExcelField(filedName="D30销售金额",sort=69)
	private BigDecimal salesPriceD30;
	
	/** D31销售数量 */
	@ExcelField(filedName="D31销售数量",sort=70)
	private Integer salesNumD31;
	
	/** D31销售金额 */
	@ExcelField(filedName="D31销售金额",sort=71)
	private BigDecimal salesPriceD31;
	
	/** 销售总数量 */
	@ExcelField(filedName="销售总数量",sort=72)
	private Integer salesTotalNum;
	
	/** 销售总金额 */
	@ExcelField(filedName="销售总金额",sort=73)
	private BigDecimal salesTotalPrice;
	
	/** 上线日期 */
	@ExcelField(filedName="上线日期",sort=74,formatType=FormatType.DATE,pattern="yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	
	/** 下线日期 */
	@ExcelField(filedName="下线日期",sort=75,formatType=FormatType.DATE,pattern="yyyy-MM-dd HH:mm:ss")
	private Date endTime;
	
	private String productSkuId;
	
	private String startDate;
	
	private String endDate;
	
	private String skuOptionId;
	
	private String skuOptionValue;
	
	private Date day;
	
	private Date startDay;
	
	private Integer salesNum;
	
	private BigDecimal salesPrice;

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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getInternationalCode() {
		return internationalCode;
	}

	public void setInternationalCode(String internationalCode) {
		this.internationalCode = internationalCode;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public String getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}

	public Integer getSalesNumD1() {
		return salesNumD1;
	}

	public void setSalesNumD1(Integer salesNumD1) {
		this.salesNumD1 = salesNumD1;
	}

	public BigDecimal getSalesPriceD1() {
		return salesPriceD1;
	}

	public void setSalesPriceD1(BigDecimal salesPriceD1) {
		this.salesPriceD1 = salesPriceD1;
	}

	public Integer getSalesNumD2() {
		return salesNumD2;
	}

	public void setSalesNumD2(Integer salesNumD2) {
		this.salesNumD2 = salesNumD2;
	}

	public BigDecimal getSalesPriceD2() {
		return salesPriceD2;
	}

	public void setSalesPriceD2(BigDecimal salesPriceD2) {
		this.salesPriceD2 = salesPriceD2;
	}

	public Integer getSalesNumD3() {
		return salesNumD3;
	}

	public void setSalesNumD3(Integer salesNumD3) {
		this.salesNumD3 = salesNumD3;
	}

	public BigDecimal getSalesPriceD3() {
		return salesPriceD3;
	}

	public void setSalesPriceD3(BigDecimal salesPriceD3) {
		this.salesPriceD3 = salesPriceD3;
	}

	public Integer getSalesNumD4() {
		return salesNumD4;
	}

	public void setSalesNumD4(Integer salesNumD4) {
		this.salesNumD4 = salesNumD4;
	}

	public BigDecimal getSalesPriceD4() {
		return salesPriceD4;
	}

	public void setSalesPriceD4(BigDecimal salesPriceD4) {
		this.salesPriceD4 = salesPriceD4;
	}

	public Integer getSalesNumD5() {
		return salesNumD5;
	}

	public void setSalesNumD5(Integer salesNumD5) {
		this.salesNumD5 = salesNumD5;
	}

	public BigDecimal getSalesPriceD5() {
		return salesPriceD5;
	}

	public void setSalesPriceD5(BigDecimal salesPriceD5) {
		this.salesPriceD5 = salesPriceD5;
	}

	public Integer getSalesNumD6() {
		return salesNumD6;
	}

	public void setSalesNumD6(Integer salesNumD6) {
		this.salesNumD6 = salesNumD6;
	}

	public BigDecimal getSalesPriceD6() {
		return salesPriceD6;
	}

	public void setSalesPriceD6(BigDecimal salesPriceD6) {
		this.salesPriceD6 = salesPriceD6;
	}

	public Integer getSalesNumD7() {
		return salesNumD7;
	}

	public void setSalesNumD7(Integer salesNumD7) {
		this.salesNumD7 = salesNumD7;
	}

	public BigDecimal getSalesPriceD7() {
		return salesPriceD7;
	}

	public void setSalesPriceD7(BigDecimal salesPriceD7) {
		this.salesPriceD7 = salesPriceD7;
	}

	public Integer getSalesNumD8() {
		return salesNumD8;
	}

	public void setSalesNumD8(Integer salesNumD8) {
		this.salesNumD8 = salesNumD8;
	}

	public BigDecimal getSalesPriceD8() {
		return salesPriceD8;
	}

	public void setSalesPriceD8(BigDecimal salesPriceD8) {
		this.salesPriceD8 = salesPriceD8;
	}

	public Integer getSalesNumD9() {
		return salesNumD9;
	}

	public void setSalesNumD9(Integer salesNumD9) {
		this.salesNumD9 = salesNumD9;
	}

	public BigDecimal getSalesPriceD9() {
		return salesPriceD9;
	}

	public void setSalesPriceD9(BigDecimal salesPriceD9) {
		this.salesPriceD9 = salesPriceD9;
	}

	public Integer getSalesNumD10() {
		return salesNumD10;
	}

	public void setSalesNumD10(Integer salesNumD10) {
		this.salesNumD10 = salesNumD10;
	}

	public BigDecimal getSalesPriceD10() {
		return salesPriceD10;
	}

	public void setSalesPriceD10(BigDecimal salesPriceD10) {
		this.salesPriceD10 = salesPriceD10;
	}

	public Integer getSalesNumD11() {
		return salesNumD11;
	}

	public void setSalesNumD11(Integer salesNumD11) {
		this.salesNumD11 = salesNumD11;
	}

	public BigDecimal getSalesPriceD11() {
		return salesPriceD11;
	}

	public void setSalesPriceD11(BigDecimal salesPriceD11) {
		this.salesPriceD11 = salesPriceD11;
	}

	public Integer getSalesNumD12() {
		return salesNumD12;
	}

	public void setSalesNumD12(Integer salesNumD12) {
		this.salesNumD12 = salesNumD12;
	}

	public BigDecimal getSalesPriceD12() {
		return salesPriceD12;
	}

	public void setSalesPriceD12(BigDecimal salesPriceD12) {
		this.salesPriceD12 = salesPriceD12;
	}

	public Integer getSalesNumD13() {
		return salesNumD13;
	}

	public void setSalesNumD13(Integer salesNumD13) {
		this.salesNumD13 = salesNumD13;
	}

	public BigDecimal getSalesPriceD13() {
		return salesPriceD13;
	}

	public void setSalesPriceD13(BigDecimal salesPriceD13) {
		this.salesPriceD13 = salesPriceD13;
	}

	public Integer getSalesNumD14() {
		return salesNumD14;
	}

	public void setSalesNumD14(Integer salesNumD14) {
		this.salesNumD14 = salesNumD14;
	}

	public BigDecimal getSalesPriceD14() {
		return salesPriceD14;
	}

	public void setSalesPriceD14(BigDecimal salesPriceD14) {
		this.salesPriceD14 = salesPriceD14;
	}

	public Integer getSalesNumD15() {
		return salesNumD15;
	}

	public void setSalesNumD15(Integer salesNumD15) {
		this.salesNumD15 = salesNumD15;
	}

	public BigDecimal getSalesPriceD15() {
		return salesPriceD15;
	}

	public void setSalesPriceD15(BigDecimal salesPriceD15) {
		this.salesPriceD15 = salesPriceD15;
	}

	public Integer getSalesNumD16() {
		return salesNumD16;
	}

	public void setSalesNumD16(Integer salesNumD16) {
		this.salesNumD16 = salesNumD16;
	}

	public BigDecimal getSalesPriceD16() {
		return salesPriceD16;
	}

	public void setSalesPriceD16(BigDecimal salesPriceD16) {
		this.salesPriceD16 = salesPriceD16;
	}

	public Integer getSalesNumD17() {
		return salesNumD17;
	}

	public void setSalesNumD17(Integer salesNumD17) {
		this.salesNumD17 = salesNumD17;
	}

	public BigDecimal getSalesPriceD17() {
		return salesPriceD17;
	}

	public void setSalesPriceD17(BigDecimal salesPriceD17) {
		this.salesPriceD17 = salesPriceD17;
	}

	public Integer getSalesNumD18() {
		return salesNumD18;
	}

	public void setSalesNumD18(Integer salesNumD18) {
		this.salesNumD18 = salesNumD18;
	}

	public BigDecimal getSalesPriceD18() {
		return salesPriceD18;
	}

	public void setSalesPriceD18(BigDecimal salesPriceD18) {
		this.salesPriceD18 = salesPriceD18;
	}

	public Integer getSalesNumD19() {
		return salesNumD19;
	}

	public void setSalesNumD19(Integer salesNumD19) {
		this.salesNumD19 = salesNumD19;
	}

	public BigDecimal getSalesPriceD19() {
		return salesPriceD19;
	}

	public void setSalesPriceD19(BigDecimal salesPriceD19) {
		this.salesPriceD19 = salesPriceD19;
	}

	public Integer getSalesNumD20() {
		return salesNumD20;
	}

	public void setSalesNumD20(Integer salesNumD20) {
		this.salesNumD20 = salesNumD20;
	}

	public BigDecimal getSalesPriceD20() {
		return salesPriceD20;
	}

	public void setSalesPriceD20(BigDecimal salesPriceD20) {
		this.salesPriceD20 = salesPriceD20;
	}

	public Integer getSalesNumD21() {
		return salesNumD21;
	}

	public void setSalesNumD21(Integer salesNumD21) {
		this.salesNumD21 = salesNumD21;
	}

	public BigDecimal getSalesPriceD21() {
		return salesPriceD21;
	}

	public void setSalesPriceD21(BigDecimal salesPriceD21) {
		this.salesPriceD21 = salesPriceD21;
	}

	public Integer getSalesNumD22() {
		return salesNumD22;
	}

	public void setSalesNumD22(Integer salesNumD22) {
		this.salesNumD22 = salesNumD22;
	}

	public BigDecimal getSalesPriceD22() {
		return salesPriceD22;
	}

	public void setSalesPriceD22(BigDecimal salesPriceD22) {
		this.salesPriceD22 = salesPriceD22;
	}

	public Integer getSalesNumD23() {
		return salesNumD23;
	}

	public void setSalesNumD23(Integer salesNumD23) {
		this.salesNumD23 = salesNumD23;
	}

	public BigDecimal getSalesPriceD23() {
		return salesPriceD23;
	}

	public void setSalesPriceD23(BigDecimal salesPriceD23) {
		this.salesPriceD23 = salesPriceD23;
	}

	public Integer getSalesNumD24() {
		return salesNumD24;
	}

	public void setSalesNumD24(Integer salesNumD24) {
		this.salesNumD24 = salesNumD24;
	}

	public BigDecimal getSalesPriceD24() {
		return salesPriceD24;
	}

	public void setSalesPriceD24(BigDecimal salesPriceD24) {
		this.salesPriceD24 = salesPriceD24;
	}

	public Integer getSalesNumD25() {
		return salesNumD25;
	}

	public void setSalesNumD25(Integer salesNumD25) {
		this.salesNumD25 = salesNumD25;
	}

	public BigDecimal getSalesPriceD25() {
		return salesPriceD25;
	}

	public void setSalesPriceD25(BigDecimal salesPriceD25) {
		this.salesPriceD25 = salesPriceD25;
	}

	public Integer getSalesNumD26() {
		return salesNumD26;
	}

	public void setSalesNumD26(Integer salesNumD26) {
		this.salesNumD26 = salesNumD26;
	}

	public BigDecimal getSalesPriceD26() {
		return salesPriceD26;
	}

	public void setSalesPriceD26(BigDecimal salesPriceD26) {
		this.salesPriceD26 = salesPriceD26;
	}

	public Integer getSalesNumD27() {
		return salesNumD27;
	}

	public void setSalesNumD27(Integer salesNumD27) {
		this.salesNumD27 = salesNumD27;
	}

	public BigDecimal getSalesPriceD27() {
		return salesPriceD27;
	}

	public void setSalesPriceD27(BigDecimal salesPriceD27) {
		this.salesPriceD27 = salesPriceD27;
	}

	public Integer getSalesNumD28() {
		return salesNumD28;
	}

	public void setSalesNumD28(Integer salesNumD28) {
		this.salesNumD28 = salesNumD28;
	}

	public BigDecimal getSalesPriceD28() {
		return salesPriceD28;
	}

	public void setSalesPriceD28(BigDecimal salesPriceD28) {
		this.salesPriceD28 = salesPriceD28;
	}

	public Integer getSalesNumD29() {
		return salesNumD29;
	}

	public void setSalesNumD29(Integer salesNumD29) {
		this.salesNumD29 = salesNumD29;
	}

	public BigDecimal getSalesPriceD29() {
		return salesPriceD29;
	}

	public void setSalesPriceD29(BigDecimal salesPriceD29) {
		this.salesPriceD29 = salesPriceD29;
	}

	public Integer getSalesNumD30() {
		return salesNumD30;
	}

	public void setSalesNumD30(Integer salesNumD30) {
		this.salesNumD30 = salesNumD30;
	}

	public BigDecimal getSalesPriceD30() {
		return salesPriceD30;
	}

	public void setSalesPriceD30(BigDecimal salesPriceD30) {
		this.salesPriceD30 = salesPriceD30;
	}

	public Integer getSalesNumD31() {
		return salesNumD31;
	}

	public void setSalesNumD31(Integer salesNumD31) {
		this.salesNumD31 = salesNumD31;
	}

	public BigDecimal getSalesPriceD31() {
		return salesPriceD31;
	}

	public void setSalesPriceD31(BigDecimal salesPriceD31) {
		this.salesPriceD31 = salesPriceD31;
	}

	public Integer getSalesTotalNum() {
		return salesTotalNum;
	}

	public void setSalesTotalNum(Integer salesTotalNum) {
		this.salesTotalNum = salesTotalNum;
	}

	public BigDecimal getSalesTotalPrice() {
		return salesTotalPrice;
	}

	public void setSalesTotalPrice(BigDecimal salesTotalPrice) {
		this.salesTotalPrice = salesTotalPrice;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getProductSkuId() {
		return productSkuId;
	}

	public void setProductSkuId(String productSkuId) {
		this.productSkuId = productSkuId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getSkuOptionId() {
		return skuOptionId;
	}

	public void setSkuOptionId(String skuOptionId) {
		this.skuOptionId = skuOptionId;
	}

	public String getSkuOptionValue() {
		return skuOptionValue;
	}

	public void setSkuOptionValue(String skuOptionValue) {
		this.skuOptionValue = skuOptionValue;
	}

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public Integer getSalesNum() {
		return salesNum;
	}

	public void setSalesNum(Integer salesNum) {
		this.salesNum = salesNum;
	}

	public BigDecimal getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(BigDecimal salesPrice) {
		this.salesPrice = salesPrice;
	}

	public Date getStartDay() {
		return startDay;
	}

	public void setStartDay(Date startDay) {
		this.startDay = startDay;
	}
	
	
}
