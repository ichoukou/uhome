package com.ytoxl.module.uhome.uhomereport.dataobject.resultmap;

import java.math.BigDecimal;

import com.ytoxl.module.uhome.uhomebase.common.utils.excel.ExcelField;

/**
 * 分类销售明细
 * @author zpf
 *
 */
public class ClassifySellReport {
	@ExcelField(filedName="商品品类",sort=1)
	protected String classifyName;//商品分类
	@ExcelField(filedName="子类",sort=2)
	protected String childClassifyName;//商品子类
	@ExcelField(filedName="品牌",sort=3)
	protected String brandName;//商品品牌
	@ExcelField(filedName="销售数量",sort=4)
	protected Integer num;//销售数量
	@ExcelField(filedName="销售金额",sort=5)
	protected BigDecimal price;//销售金额
	public String getClassifyName() {
		return classifyName;
	}
	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getChildClassifyName() {
		return childClassifyName;
	}
	public void setChildClassifyName(String childClassifyName) {
		this.childClassifyName = childClassifyName;
	}
}
