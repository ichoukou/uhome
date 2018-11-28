package com.ytoxl.module.uhome.uhomebase.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomebase.dataobject.CouponConfig;

public interface CouponConfigMapper<T extends CouponConfig> extends BaseSqlMapper<T> {
	
	/**
	 * 根据eventId查询优惠券配置
	 * @param eventId
	 * @return
	 */
	public List<CouponConfig> listCouponConfigByEventId(Integer eventId) throws DataAccessException;
	
	/**
	 * 查询所有的优惠券金额
	 * @return
	 */
	public List<BigDecimal> listAllowances() throws DataAccessException;
	
	/**
	 * 通过活动id查询优惠券  优惠的总额
	 * @param eventId
	 * @return
	 * @throws DataAccessException
	 */
	public BigDecimal getCouponsAmount(Integer eventId) throws DataAccessException;
	
}
