package com.ytoxl.module.uhome.uhomebase.mapper;

import org.apache.ibatis.annotations.Param;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomebase.dataobject.FormNumber;

public interface FormNumberMapper<T extends FormNumber> extends BaseSqlMapper<T> {

	public FormNumber getFormNumberByPrefix(String formPrefix);
	
	public Integer updateIndex(@Param("formNumberId") Integer formNumberId,@Param("formIndex") Integer formIndex);
}
