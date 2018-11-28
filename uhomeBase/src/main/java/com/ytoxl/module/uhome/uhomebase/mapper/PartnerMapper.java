package com.ytoxl.module.uhome.uhomebase.mapper;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomebase.dataobject.Partner;

public interface PartnerMapper<T extends Partner> extends BaseSqlMapper<T> {
	/** 根据uniondId得到 partner对象
	 * @param unionId
	 * @return
	 */
	public Partner getPartnerModelByUnionId(String unionId);
}
