package com.ytoxl.module.uhome.uhomereport.dataobject.resultmap;

import java.math.BigDecimal;

import com.ytoxl.module.uhome.uhomebase.common.utils.excel.ExcelField;

/**
 * 买家运营报表
 * @author hwx
 *
 */
public class BuyerOperationsReport {
	
	/** 周期 */
	@ExcelField(filedName="周期",sort=1)
	private String period;
	
	/** 新增买家  */
	@ExcelField(filedName="新增买家",sort=2)
	private Integer newBuyers;
	
	/** 买家总数 */
	@ExcelField(filedName="买家总数",sort=3)
	private Integer buyers;
	
	/** 新增注册会员 */
	@ExcelField(filedName="新增注册会员",sort=4)
	private Integer newUsers;
	
	/** 网站会员总数 */
	@ExcelField(filedName="网站会员总数",sort=5)
	private Integer users;
	
	/** 2次购买以上的买家人数 */
	@ExcelField(filedName="2次购买以上的买家人数",sort=6)
	private Integer moreBuy;
	
	/** 复购率 */
	@ExcelField(filedName="复购率",sort=7)
	private String repeatBuy;

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public Integer getNewBuyers() {
		return newBuyers;
	}

	public void setNewBuyers(Integer newBuyers) {
		this.newBuyers = newBuyers;
	}

	public Integer getBuyers() {
		return buyers;
	}

	public void setBuyers(Integer buyers) {
		this.buyers = buyers;
	}

	public Integer getNewUsers() {
		return newUsers;
	}

	public void setNewUsers(Integer newUsers) {
		this.newUsers = newUsers;
	}

	public Integer getUsers() {
		return users;
	}

	public void setUsers(Integer users) {
		this.users = users;
	}

	public Integer getMoreBuy() {
		return moreBuy;
	}

	public void setMoreBuy(Integer moreBuy) {
		this.moreBuy = moreBuy;
	}

	public String getRepeatBuy() {
		return repeatBuy;
	}

	public void setRepeatBuy(String repeatBuy) {
		this.repeatBuy = repeatBuy;
	}




}
