package com.ytoxl.module.uhome.uhomeInterface.model.duomai;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.ytoxl.module.core.common.utils.StringUtils;
import com.ytoxl.module.uhome.uhomebase.common.utils.PropertyUtil;

/** 订单集合对象
 * @author zhiming.zeng
 *
 */
public class OrderContent{
	
	/** 网站或子联盟编号	 */
	protected String feedback;
	/** 订单时间	 */
	protected Date  order_time;
	/** 订单编号	 */
	protected String order_sn;
	/** 订单价格 */
	protected BigDecimal  orders_price;
	/** 联盟会员id */
	protected String mid;
	/** 1是新客户，0不是新客户 */
//	protected Integer is_new_custom;
	/** 总订单产生的金额 */
	protected BigDecimal commission;
	/** 订单状态 */
	protected String status;
	
	
	
	protected List<OrderProduct>  details;
	
	
	public BigDecimal getOrders_price() {
		return orders_price;
	}

	public void setOrders_price(BigDecimal orders_price) {
		this.orders_price = orders_price;
	}

	public BigDecimal getCommission() {
		/*
		BigDecimal total = new BigDecimal(0.00);
		if(details!=null&&details.size()>0){
			for(OrderProduct product:details){
				BigDecimal price = new BigDecimal(0.00);
				if(product.getRebate_price()==null){//为空 标识未使用优惠卷
					price = product.getGoods_price().multiply(new BigDecimal(product.getGoods_ta()));//返回单价乘以数量的总额
				}else{
					price = product.getRebate_price();
				}
				total = total.add(price);
			}
		}
		return total.multiply(CommonConstants.COMMISSION_REBATE).setScale(2, BigDecimal.ROUND_HALF_UP);
		return this.orders_price.multiply(CommonConstants.COMMISSION_REBATE).setScale(2, BigDecimal.ROUND_HALF_UP);*/
		return commission;
	}
	
	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")   
	public Date getOrder_time() {
		return order_time;
	}

	public void setOrder_time(Date order_time) {
		this.order_time = order_time;
	}

	public String getOrder_sn() {
		return order_sn;
	}

	public void setOrder_sn(String order_sn) {
		this.order_sn = order_sn;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}




	public String getStatus() {
		if(!StringUtils.isEmpty(status)){
			return PropertyUtil.getPropertyValue("order.status."+status,null);
		}
		return "";
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<OrderProduct> getDetails() {
		return details;
	}

	public void setDetails(List<OrderProduct> details) {
		this.details = details;
	}
	
}
