package com.ytoxl.uhomefront.web.action.content;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.code.jcaptcha4struts2.core.validation.JCaptchaValidator;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.dataobject.Supplier;
import com.ytoxl.module.uhome.uhomecontent.service.SupplierService;
import com.ytoxl.uhomefront.web.action.BaseAction;

@SuppressWarnings("serial")
public class SupplierAction extends BaseAction {

	private static Logger logger = LoggerFactory.getLogger(SuggestAction.class);
	
	private Supplier supplier;
	@Autowired
	private SupplierService supplierService;
	
	/**
	 * 显示新增供应商页面
	 * @return
	 */
	public String editSupplier(){
		return SUCCESS;
	}
	
	/**
	 * 保存供应商信息
	 * @return
	 */
	public String saveSupplier(){
		boolean flag = JCaptchaValidator.validate();
		if(flag){
			try {
				supplierService.save(supplier);
				setMessage(Boolean.TRUE.toString(),"提交成功！");
			} catch (UhomeStoreException e) {
				logger.error(e.getMessage());
				setMessage(Boolean.FALSE.toString(), e.getLocalizedMessage());
			}
		}else{
			setMessage("captchaError");//输入的验证码不正确
		}
		return JSONMSG;
	}
	
	/**
	 * 获取商家性质
	 * @return
	 */
	public Short[] getTypes(){
		return Supplier.TYPES;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
}
