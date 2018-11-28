package com.ytoxl.uhomemanage.web.action.content;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.dataobject.Supplier;
import com.ytoxl.module.uhome.uhomecontent.service.SupplierService;
import com.ytoxl.uhomemanage.web.action.BaseAction;
public class SupplierAction extends BaseAction  {
	
	private static final long serialVersionUID = 1563032240216719019L;
	private static Logger logger = LoggerFactory.getLogger(SupplierAction.class);
	private static final String SEARCH_SUPPLIERS = "searchSuppliers";
	private BasePagination<Supplier> supplierPage;
	@Autowired
	private SupplierService supplierService;
	private Integer supplierId;//供应商id
	
	 /**
	  * 供应商分页查询方法
	  * @return
	  */
	public String searchSuppliers(){
		if (supplierPage == null) {
			supplierPage=new BasePagination<Supplier>();
		}
		try {
			supplierService.searchSupplier(supplierPage);
		} catch (UhomeStoreException e) {
			
			logger.error(e.getMessage());
		}
		return SEARCH_SUPPLIERS;
	}
	
	 /**
	 * 删除供应商
	 * @return
	 */
	public String deleteSupplier(){
		try {
			if (supplierId==null) {
				setMessage(Boolean.FALSE.toString(), "删除失败");
			}else {
				supplierService.deleteSupplier(supplierId);
				setMessage(Boolean.TRUE.toString(), "删除成功");
			}
		} catch (Exception e) {
			
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(), "删除失败");
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
	
	public Integer getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}
	public BasePagination<Supplier> getSupplierPage() {
		return supplierPage;
	}
	public void setSupplierPage(BasePagination<Supplier> supplierPage) {
		this.supplierPage = supplierPage;
	}
}
