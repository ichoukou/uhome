package com.ytoxl.module.uhome.uhomebase.mapper;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomebase.dataobject.Express;

/**
 * @author wangguoqing
 *
 */
public interface ExpressMapper<T extends Express> extends BaseSqlMapper<T> {
	/**
	 * 查询快递公司数据
	 * @param productId
	 * @return
	 */
	public List<Express> listExpresses() throws DataAccessException;
	
}
