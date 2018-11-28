package com.ytoxl.module.uhome.uhomebase.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Partner;
import com.ytoxl.module.uhome.uhomebase.mapper.PartnerMapper;
import com.ytoxl.module.uhome.uhomebase.service.PartnerService;

@Service
public class PartnerServiceImpl implements PartnerService {
	
	@Autowired
	private PartnerMapper<Partner> partnerMapper;
	
	@Override
	public Partner getPartnerById(Integer partnerId) throws UhomeStoreException {
		Partner partner = partnerMapper.get(partnerId);
		return partner;
	}

}
