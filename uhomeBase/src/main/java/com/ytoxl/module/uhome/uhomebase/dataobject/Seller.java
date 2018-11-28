package com.ytoxl.module.uhome.uhomebase.dataobject;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ytoxl.module.uhome.uhomebase.dataobject.resultmap.RegionModel;
import com.ytoxl.module.uhome.uhomebase.dataobject.tbl.SellerTbl;
import com.ytoxl.module.user.dataobject.User;

@SuppressWarnings("serial")
public class Seller extends SellerTbl {

	public static final Short SUPPLIER_TYPE_DEFAULT = 0; // 请选择
	public static final Short SUPPLIER_TYPE_MANUFACTURER = 1; // 厂商
	public static final Short SUPPLIER_TYPE_AGENCY = 2; // 总代理
	public static final Short SUPPLIER_TYPE_FIRST_AGENCY = 3; // 一级代理
	public static final Short SUPPLIER_TYPE_SECOND_AGENCY = 4; // 二级代理
	public static final Short[] SUPPLIER_TYPES = new Short[] {
			SUPPLIER_TYPE_DEFAULT, SUPPLIER_TYPE_MANUFACTURER,
			SUPPLIER_TYPE_AGENCY, SUPPLIER_TYPE_FIRST_AGENCY,
			SUPPLIER_TYPE_SECOND_AGENCY };

	protected List<Product> products;
	protected List<ProductSku> productSkus;// 卖家对应的商品
	protected User user;
	protected String brandNames; // 记录该商家拥有的品牌名称集合
	protected String shiperRegionCodes; // 发货地址codes
	protected String receiverRegionCodes; // 退货地址codes
	protected String companyRegionCodes; // 公司地址codes
	protected List<Integer> listBrandIds; // 记录选择的品牌
	protected Integer brandId; // 记录选择的品牌id
	protected String brandIds; // 记录用户选择的ids
	protected String newPassword;
	protected String confirmNewPassword;

	private RegionModel companyModel; // 新的地址插件 公司地址
	private RegionModel shiperModel; // 新的地址插件 发货地址
	private RegionModel receiverModel; // 新的地址插件 退货地址

	public RegionModel getCompanyModel() {
		return companyModel;
	}

	public void setCompanyModel(RegionModel companyModel) {
		this.companyModel = companyModel;
	}

	public RegionModel getShiperModel() {
		return shiperModel;
	}

	public void setShiperModel(RegionModel shiperModel) {
		this.shiperModel = shiperModel;
	}

	public RegionModel getReceiverModel() {
		return receiverModel;
	}

	public void setReceiverModel(RegionModel receiverModel) {
		this.receiverModel = receiverModel;
	}

	// 获取regionCode数组

	
	protected BigDecimal postage;
	
	//获取regionCode数组

	public String[] getCodeArray(String regionCodes) {
		String[] regioncodes = null;
		if (StringUtils.isNotBlank(regionCodes)) {
			regioncodes = regionCodes.split(",");
		}
		return regioncodes;
	}

	// 获取发货地址省级code
	public String getShiperProvinceCode() {
		String regionCode = null;
		String[] regioncodes = this.getCodeArray(shiperRegionCodes);
		if (regioncodes != null && regioncodes.length > 0) {
			regionCode = regioncodes[0];
		}
		return regionCode;
	}

	// 获取发货地址市级code
	public String getShiperCityCode() {
		String regionCode = null;
		String[] regioncodes = this.getCodeArray(shiperRegionCodes);
		if (regioncodes != null && regioncodes.length > 0) {
			regionCode = regioncodes[1];
		}
		return regionCode;
	}

	// 获取发货地址最后一级code
	public String getShiperRegionCode() {
		String regionCode = null;
		String[] regioncodes = this.getCodeArray(shiperRegionCodes);
		if (regioncodes != null && regioncodes.length > 0) {
			regionCode = regioncodes[2];
		}
		return regionCode;
	}

	// 获取退货地址省级code
	public String getReceiverProvinceCode() {
		String regionCode = null;
		String[] regioncodes = this.getCodeArray(receiverRegionCodes);
		if (regioncodes != null && regioncodes.length > 0) {
			regionCode = regioncodes[0];
		}
		return regionCode;
	}

	// 获取退货地址市级code
	public String getReceiverCityCode() {
		String regionCode = null;
		String[] regioncodes = this.getCodeArray(receiverRegionCodes);
		if (regioncodes != null && regioncodes.length > 0) {
			regionCode = regioncodes[1];
		}
		return regionCode;
	}

	// 获取退货地址最后一级code
	public String getReceiverRegionCode() {
		String regionCode = null;
		String[] regioncodes = this.getCodeArray(receiverRegionCodes);
		if (regioncodes != null && regioncodes.length > 0) {
			regionCode = regioncodes[2];
		}
		return regionCode;
	}

	// 获取公司地址省级code
	public String getCompanyProvinceCode() {
		String regionCode = null;
		String[] regioncodes = this.getCodeArray(companyRegionCodes);
		if (regioncodes != null && regioncodes.length > 0) {
			regionCode = regioncodes[0];
		}
		return regionCode;
	}

	// 获取公司地址市级code
	public String getCompanyCityCode() {
		String regionCode = null;
		String[] regioncodes = this.getCodeArray(companyRegionCodes);
		if (regioncodes != null && regioncodes.length > 0) {
			regionCode = regioncodes[1];
		}
		return regionCode;
	}

	// 获取公司地址最后一级code
	public String getCompanyRegionCode() {
		String regionCode = null;
		String[] regioncodes = this.getCodeArray(companyRegionCodes);
		if (regioncodes != null && regioncodes.length > 0) {
			regionCode = regioncodes[2];
		}
		return regionCode;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<ProductSku> getProductSkus() {
		return productSkus;
	}

	public void setProductSkus(List<ProductSku> productSkus) {
		this.productSkus = productSkus;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getBrandNames() {
		return brandNames;
	}

	public void setBrandNames(String brandNames) {
		this.brandNames = brandNames;
	}

	public String getShiperRegionCodes() {
		return shiperRegionCodes;
	}

	public void setShiperRegionCodes(String shiperRegionCodes) {
		this.shiperRegionCodes = shiperRegionCodes;
	}

	public String getReceiverRegionCodes() {
		return receiverRegionCodes;
	}

	public void setReceiverRegionCodes(String receiverRegionCodes) {
		this.receiverRegionCodes = receiverRegionCodes;
	}

	public String getCompanyRegionCodes() {
		return companyRegionCodes;
	}

	public void setCompanyRegionCodes(String companyRegionCodes) {
		this.companyRegionCodes = companyRegionCodes;
	}

	public List<Integer> getListBrandIds() {
		return listBrandIds;
	}

	public void setListBrandIds(List<Integer> listBrandIds) {
		this.listBrandIds = listBrandIds;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getBrandIds() {
		return brandIds;
	}

	public void setBrandIds(String brandIds) {
		this.brandIds = brandIds;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}
	public BigDecimal getPostage() {
		return postage;
	}
	public void setPostage(BigDecimal postage) {
		this.postage = postage;
	}
	
}
