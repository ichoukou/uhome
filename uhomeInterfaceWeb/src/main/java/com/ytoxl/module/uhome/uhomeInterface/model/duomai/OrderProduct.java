package com.ytoxl.module.uhome.uhomeInterface.model.duomai;

import java.math.BigDecimal;

/** 订单商品
 * @author zhiming.zeng
 *
 */
public class OrderProduct {
	
	/** 商品编号 */
	protected String goods_id;
	/** 商品数量 */
	protected Integer goods_ta;
	/** 商品单价 */
	protected BigDecimal goods_price;
	/** 商品名称 */
	protected String goods_name;
	/** 佣金分类编号*/
	//protected String goods_cate;
	/** 佣金分类名称 */
	//protected String goods_cate_name;
	/** 佣金比率*/
	//protected BigDecimal rate;
	/** 此类别商品有效总金额*/
	//protected BigDecimal totalPrice;
	/** 优惠金额 */
	//protected BigDecimal discount_amount;  
	/** 优惠卷号 */
	//protected String promotion_code;  
	/** 使用优惠卷之后的金额*/
    //protected BigDecimal rebate_price;
	
	
	
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	
	public Integer getGoods_ta() {
		return goods_ta;
	}
	public void setGoods_ta(Integer goods_ta) {
		this.goods_ta = goods_ta;
	}
	public BigDecimal getGoods_price() {
		return goods_price;
	}
	public void setGoods_price(BigDecimal goods_price) {
		this.goods_price = goods_price;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	
	/*
	public BigDecimal getRate() {
		return CommonConstants.COMMISSION_REBATE.setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public BigDecimal getTotalPrice() {
		if(rebate_price==null){//为空 标识未使用优惠卷
			return goods_price.multiply(new BigDecimal(goods_ta));//返回单价乘以数量的总额
		}
		return rebate_price;//返回使用优惠卷之后的总金额
	}
	*/
	
	
}
