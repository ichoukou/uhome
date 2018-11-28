package com.ytoxl.module.uhome.uhomebase.service;

import java.util.List;
import java.util.Map;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Express;
import com.ytoxl.module.uhome.uhomebase.dataobject.resultmap.ExpressMessage;

/**
 * 快递公司
 * @author guoxinzhi
 *
 */
public interface ExpressService {
	/**
	 * 查询所有产品类别信息
	 * @return
	 * @throws UhomeStoreException 
	 */
	public List<Express> listExpresses() throws UhomeStoreException;
	
	/**
	 * 查询运单详细信息
	 * @return
	 * @throws UhomeStoreException 
	 */
	public List<Map> getExpressDetailInfo(String mailNo) throws UhomeStoreException;
	
	/**
	 * 调用快递100API返回运单信息
	 * @param comCode
	 * @param mailNo
	 * @return
	 */
	public ExpressMessage getExpressDetailInfoFromKuaidi100(String companyCode, String mailNo) throws UhomeStoreException;
}
