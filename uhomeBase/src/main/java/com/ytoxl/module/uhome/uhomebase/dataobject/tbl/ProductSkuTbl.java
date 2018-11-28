package com.ytoxl.module.uhome.uhomebase.dataobject.tbl;

import java.util.Date;

/**
 * 
 * @author wangguoqing
 * 
 */
public class ProductSkuTbl {
	protected Integer productSkuId;
	protected Integer productId;
	protected Integer secKillInventory; // 秒杀库存
	protected Integer inventory;
	protected String skuCode; // sku编码
	protected String internationalCode; // 国际码
	protected Integer salesQuantity; // 销售 数量
	protected Short isDelete;	// 是否做逻辑删除 默认0   0=否，1=是
	protected Date createTime;//创建时间
	protected Date updateTime;//更新时间

	public Integer getProductSkuId() {
		return productSkuId;
	}

	public void setProductSkuId(Integer productSkuId) {
		this.productSkuId = productSkuId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getSecKillInventory() {
		return secKillInventory;
	}

	public void setSecKillInventory(Integer secKillInventory) {
		this.secKillInventory = secKillInventory;
	}

	public Integer getInventory() {
		return inventory;
	}

	public void setInventory(Integer inventory) {
		this.inventory = inventory;
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

	public Integer getSalesQuantity() {
		return salesQuantity;
	}

	public void setSalesQuantity(Integer salesQuantity) {
		this.salesQuantity = salesQuantity;
	}

	public Short getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Short isDelete) {
		this.isDelete = isDelete;
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
