package com.ytoxl.module.uhome.uhomereport.dataobject.resultmap;

import com.ytoxl.module.uhome.uhomebase.common.utils.excel.ExcelField;



public class AreaSellReport {
 
	
	@ExcelField(filedName="品牌", sort=1)
	private String name;
	
	private String regionProvince ;

	private String regionCity;
	
	private String regionArea;
	@ExcelField(filedName="订单数量", sort=3)
	private Integer orderNum;
	@ExcelField(filedName="销售数量", sort=4)
	private Integer  itemNum;
	@ExcelField(filedName="收货地址", sort=2)
	private String adress;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegionProvince() {
		return regionProvince;
	}

	public void setRegionProvince(String regionProvince) {
		this.regionProvince = regionProvince;
	}

	public String getRegionCity() {
		return regionCity;
	}

	public void setRegionCity(String regionCity) {
		this.regionCity = regionCity;
	}

	public String getRegionArea() {
		return regionArea;
	}

	public void setRegionArea(String regionArea) {
		this.regionArea = regionArea;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}
	
	 
	
}
