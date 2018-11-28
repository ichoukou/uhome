package com.ytoxl.module.uhome.uhomebase.service;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Postage;

public interface PostageService {
	
	/**
	 * 保存邮费信息
	 * @param postage
	 */
	public void savePostage(Postage postage) throws UhomeStoreException;
}
