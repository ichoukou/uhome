package com.ytoxl.module.uhome.uhomebase.dataobject;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ytoxl.module.uhome.uhomebase.dataobject.resultmap.RegionModel;
import com.ytoxl.module.uhome.uhomebase.dataobject.tbl.UserInfoTbl;
import com.ytoxl.module.user.dataobject.User;

public class UserInfo extends UserInfoTbl {
	public static final Integer USER_ROLE_BUYER=100000;  //买家
	public static final Integer USER_ROLE_SELLER=100003; //商家
	public static final Integer USER_ROLE_ADMIN=100002;   //管理员
	
	public static final Short GENDER_MALE=1;   //男
	public static final Short GENDER_FEMALE=0;  //女
	
	private User user;
	private int orderCount;         //记录订单数量
	private BigDecimal totalPrice;     //记录总交易金额
	private Date payTime;     //记录总交易金额
	private Point point;      //记录用户积分
	private String brandNames; //用户定义的品牌名称
	private List<ReceiverAddress> receAddress;  //用户的收货地址
	private Region region;
	//新的地址插件
	private RegionModel regionModel;
	private String province;                    //列表显示省份
//	private List<Brand> subscriptionBrand;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public String getBrandNames() {
		return brandNames;
	}

	public void setBrandNames(String brandNames) {
		this.brandNames = brandNames;
	}

	public List<ReceiverAddress> getReceAddress() {
		return receAddress;
	}

	public void setReceAddress(List<ReceiverAddress> receAddress) {
		this.receAddress = receAddress;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public RegionModel getRegionModel() {
		return regionModel;
	}

	public void setRegionModel(RegionModel regionModel) {
		this.regionModel = regionModel;
	}
	
}
