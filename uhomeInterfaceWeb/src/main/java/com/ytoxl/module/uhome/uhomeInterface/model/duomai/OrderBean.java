package com.ytoxl.module.uhome.uhomeInterface.model.duomai;

import java.util.ArrayList;
import java.util.List;

/** 订单查询实体model
 * 
 * @author zhiming.zeng
 *
 */
public class OrderBean {
	/** 请求是否成功 0 失败 1 成功	 */
	private Integer success;
	
	/** 错误信息	 */
	private String errors;
	
	/**	订单集合 */
	private List<OrderContent> data;
	
	/**当前页码数*/
	private Integer current_num;
	
	/** 是否最后最后一页 true是 false否*/
	private Boolean hasmore = false;

	public OrderBean(Integer success,String errors, List<OrderContent> data, Integer current_num,Boolean hasmore){
		this.success = success;
		this.errors = errors;
		this.data = data;
		this.current_num = current_num;
		this.hasmore = hasmore;
	}
	public OrderBean(){
		
	}
	
	
	public Integer getCurrent_num() {
		return current_num;
	}
	public void setCurrent_num(Integer current_num) {
		this.current_num = current_num;
	}
	
	public Boolean getHasmore() {
		return hasmore;
	}
	public void setHasmore(Boolean hasmore) {
		this.hasmore = hasmore;
	}
	public String getErrors() {
		if(errors==null){
			return "";
		}
		return errors;
	}

	public void setErrors(String errors) {
		this.errors = errors;
	}

	public List<OrderContent> getData() {
		if(data==null||data.size()<=0){
			return new ArrayList<OrderContent>();
		}
		return data;
	}

	public void setData(List<OrderContent> data) {
		this.data = data;
	}

	public Integer getSuccess() {
		return success;
	}

	public void setSuccess(Integer success) {
		this.success = success;
	}
	
	
	
	
	
}
