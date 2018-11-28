package com.ytoxl.module.uhome.uhomebase.service;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Partner;

public interface PartnerService {
	
	public Partner getPartnerById(Integer partnerId) throws UhomeStoreException;

}
