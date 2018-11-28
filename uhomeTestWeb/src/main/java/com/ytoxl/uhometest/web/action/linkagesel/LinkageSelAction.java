package com.ytoxl.uhometest.web.action.linkagesel;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.service.RegionService;
import com.ytoxl.uhometest.web.action.BaseAction;

public class LinkageSelAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(LinkageSelAction.class);
	private static final long serialVersionUID = 1944774200038698212L;
	
	private String a;
	
	private String pid;
	
	private String cid;
	
	@Autowired
	private RegionService regionService;
	
	/*public String province() {
		try {
			String info = regionService.getProvince();
			setMessage(info);
		} catch (UhomeStoreException e) {
			logger.error("查询省份出错:"+e.getMessage());
		}
		return JSONMSG;
	}

	public String city(){
		if(StringUtils.isNotEmpty(pid)){
			try {
				String info = regionService.getCity(pid);
				setMessage(info);
			} catch (UhomeStoreException e) {
				logger.error("查询城市出错:"+e.getMessage());
			}
		}
		return JSONMSG;
		
	}

	public String area(){
		if(StringUtils.isNotEmpty(cid)){
			try {
				String info = regionService.getArea(cid);
				setMessage(info);
			} catch (UhomeStoreException e) {
				logger.error("查询地区出错:"+e.getMessage());
			}
		}
		return JSONMSG;
	}*/
	
	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

}
