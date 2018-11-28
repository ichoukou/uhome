package com.ytoxl.module.uhome.uhomebase.dataobject.resultmap;

import java.util.List;

public class ExpressMessage {
	
	public static final Integer STATUS_RESULTLESS = 0;
	public static final Integer STATUS_SUCCESS = 1;
	public static final Integer STATUS_EXCEPTION = 2;
	
	private String message; // 消息体
	private List<DataItem> data; // 消息集合
	private Integer state; // 快递当前状态(0：在途中,1：已发货，2：疑难件，3： 已签收 ，4：已退货)
	private Integer status; // 查询结果状态(0：运单暂无结果，1：查询成功，2：接口出现异常)

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<DataItem> getData() {
		return data;
	}

	public void setData(List<DataItem> data) {
		this.data = data;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
