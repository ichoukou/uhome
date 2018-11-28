package com.ytoxl.module.uhome.uhomecontent.dataobject.tbl;

import java.util.Date;

/**
 * 
 * 供应商实体表
 * */
public class SupplierTbl {
	/**
	 * 供应商id
	 */
    protected Integer supplierId;
    /**
	 * 供应商名字 
	 */
    protected String brandName;
    /**
	 * 供应商名公司名称
	 */
    protected String companyName;
    /**
	 * 联系人 
	 */
    protected String contactName;
    /**
	 * 联系电话
	 */
    protected String telephone;
    /**
	 * 供应商类型 
	 */
    protected Short type;
    /**
	 * 品牌说明 
	 */
    protected String describe;
    /**
	 * 创建时间 
	 */
    protected Date createTime;
    /**
	 * 更新时间 
	 */
    protected Date updateTime;
    
    
    
	public Integer getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public Short getType() {
		return type;
	}
	public void setType(Short type) {
		this.type = type;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
    
    
    
}
