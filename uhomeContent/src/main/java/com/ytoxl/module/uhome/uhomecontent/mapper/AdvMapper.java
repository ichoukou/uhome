package com.ytoxl.module.uhome.uhomecontent.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomebase.dataobject.Product;
import com.ytoxl.module.uhome.uhomecontent.dataobject.AdvPosition;
import com.ytoxl.module.uhome.uhomecontent.dataobject.Advertisement;

/**
 * 积分
 * @author huayucai
 *
 * @param <T>
 */
public interface AdvMapper<T extends Advertisement> extends BaseSqlMapper<T> {
	
	/**
	 * 查询广告信息
	 * @return
	 */
	public List<Advertisement> listAvailableAdvsByPosition(Advertisement adv) throws DataAccessException;
	/**
	 * 分页查询
	 * @param map
	 * @return
	 */
	public List<Advertisement> searchAdvs(Map<String, Object> searchParams) throws DataAccessException;
	public Integer searchAdvsCount(Map<String, Object> searchParams) throws DataAccessException;
	
	/**
	 * 根据广告位和备用广告字段查询广告 
	 * @param map
	 * @return
	 */
	public List<Advertisement> getAdvByPositionAndIsDefault(Advertisement adv) throws DataAccessException;
	
	/**
	 * 通过code 广告位和备用广告字段查询广告
	 * @param codes
	 * @return
	 * @throws DataAccessException
	 */
	public List<Advertisement> getAdvByPositionAndIsDefaultAndCode(String codes)throws DataAccessException;
	
	/**
	 * 查询广告位信息
	 * @return
	 */
	public List<AdvPosition> listAdvPositons() throws DataAccessException;
	
	/**
	 * 首页没有秒杀是  没有投放的广告  是默认广告
	 * @param codes
	 * @return
	 * @throws DataAccessException
	 */
	public List<Advertisement> getHomeSecKillAdvList(List<String> codes) throws DataAccessException;
	
	/**
	 * 首页没有秒杀是 默认是投放的广告
	 * @param codes
	 * @return
	 * @throws DataAccessException
	 */
	public List<Advertisement> getHomeSecKillAdvs(List<String> codes) throws DataAccessException;
	
	/**
	 * 更改广告类型
	 * @param searchParams
	 * @return
	 */
	public Integer updateIsDefaultValue(Advertisement searchParams) throws DataAccessException;
	
	/**
	 * 更新备用广告  用户添加备用广告  将之前的备用广告改成 非广告
	 * @param adv
	 * @return
	 * @throws DataAccessException
	 */
	public Integer updateIsDefault(Advertisement adv) throws DataAccessException;
	
	/**
	 * 根据广告code获取所有在时间内的广告
	 * @param codes
	 * @return
	 * @throws DataAccessException
	 */
	public List<Advertisement> listAdvertisementByAdvCode(List<String> codes) throws DataAccessException;
	/**
	 * 根据广告code获取所有在时间内的右部广告
	 * @param codes
	 * @return
	 * @throws DataAccessException
	 */
	public List<Advertisement> listAdvertisementRightByAdvCode(List<String> codes)throws DataAccessException;
	/**
	 * 查询首页滚动广告
	 * @return
	 * @throws DataAccessException
	 */
	public List<Advertisement> listHomeBroadcastAdv() throws DataAccessException;
	
}
