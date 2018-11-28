package com.ytoxl.module.uhome.uhomebase.dataobject.resultmap;

public class RegionModel {
	private Integer id;
	private String name;
	
	//用于存储省市区的id
	private Integer countyId;    //县名称Id
	private Integer cityId;      //市名称Id
	private Integer provinceId;  //省名称Id
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public Integer getCountyId() {
		return countyId;
	}
	public void setCountyId(Integer countyId) {
		this.countyId = countyId;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public Integer getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
	
	

}
