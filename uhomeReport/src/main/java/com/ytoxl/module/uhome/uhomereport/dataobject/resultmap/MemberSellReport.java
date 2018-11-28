package com.ytoxl.module.uhome.uhomereport.dataobject.resultmap;

import java.math.BigDecimal;

import com.ytoxl.module.uhome.uhomebase.common.utils.excel.ExcelField;
/**
 * 会员销售管理报表
 * @author zhaopengfei
 *
 */
public class MemberSellReport {
	@ExcelField(filedName="登录名",sort=1)
	protected String memberName;//会员名称
	@ExcelField(filedName="品牌",sort=2)
	protected String brandName;//品牌名称
	@ExcelField(filedName="订单数",sort=3)
	protected Integer orderNum;//订单数
	@ExcelField(filedName="性别",sort=4)
	protected String gender;;//性别
	@ExcelField(filedName="购买金额",sort=5)
	protected BigDecimal totalPrice;//购买金额
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		if("1".equals(gender)){
			this.gender = "男";
		}else{
			this.gender = "女";
		}
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
}
