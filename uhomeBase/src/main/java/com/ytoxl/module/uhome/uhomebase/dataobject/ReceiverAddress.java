package com.ytoxl.module.uhome.uhomebase.dataobject;

import org.apache.commons.lang.StringUtils;

import com.ytoxl.module.uhome.uhomebase.common.utils.excel.ExcelField;
import com.ytoxl.module.uhome.uhomebase.dataobject.resultmap.RegionModel;
import com.ytoxl.module.uhome.uhomebase.dataobject.tbl.ReceiverAddressTbl;

public class ReceiverAddress extends ReceiverAddressTbl {
	public static final Short ISDEFAULT_TRUE=1; //默认地址
	public static final Short ISDEFAULT_FALSE=0; //非默认地址
	
	private String email; //用于订单审核页面的快速注册
	private Region region;
	//新的地址插件
	private RegionModel regionModel;
	
	//获取regionCode数组
	public String[] getCodeArray() {
		String[] regioncodes = null;
		if(StringUtils.isNotBlank(regionCodes)){
			regioncodes = this.regionCodes.split(",");
		}
		return regioncodes;
	}
	//获取省级code
	public String getProvinceCode(){
		String regionCode = null; 
		String[] regioncodes = this.getCodeArray();
		if(regioncodes != null && regioncodes.length > 0){
			regionCode = regioncodes[0];
		}
		return regionCode;
	}
	//获取市级code
	public String getCityCode(){
		String regionCode = null; 
		String[] regioncodes = this.getCodeArray();
		if(regioncodes != null && regioncodes.length > 0){
			regionCode = regioncodes[1];
		}
		return regionCode;
	}
	//获取region最后一级code
	public String getRegionCode(){
		String regionCode = null; 
		String[] regioncodes = this.getCodeArray();
		if(regioncodes != null && regioncodes.length > 0){
			regionCode = regioncodes[2];
		}
		return regionCode;
	}
	
	//获取电话号码明细
	public String[] getPhoneNums(){
		String[] telNums = null;
		if(StringUtils.isNotBlank(telephone)){
			telNums = this.telephone.split("-");
		}
		return telNums;
	}
	//获取电话区号
	public String getAreaCode(){
		String areaCode = null; 
		String[] telNums = this.getPhoneNums();
		if(telNums != null && telNums.length > 0){
			areaCode = telNums[0];
		}
		return areaCode;
	}
	//获取电话号码
	public String getPhoneNum(){
		String phoneNum = null; 
		String[] telNums = this.getPhoneNums();
		if(telNums != null && telNums.length > 0){
			phoneNum = telNums[1];
		}
		return phoneNum;
	}
	//获取分机号
	public String getExtNum(){
		String extNum = null; 
		String[] telNums = this.getPhoneNums();
		if(telNums != null && telNums.length > 0){
			extNum = telNums[2];
		}
		return extNum;
	}
	
	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public RegionModel getRegionModel() {
		return regionModel;
	}
	public void setRegionModel(RegionModel regionModel) {
		this.regionModel = regionModel;
	}
}
