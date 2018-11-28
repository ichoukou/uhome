package com.ytoxl.module.uhome.uhomecontent.service;

import java.util.List;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.dataobject.AdvPosition;
import com.ytoxl.module.uhome.uhomecontent.dataobject.Advertisement;
/**
 * 广告service
 * @author guoxinzhi
 *
 */
public interface AdvService {
	/*
	 * 根据位置查询有效的广告
	 */
	public List<Advertisement> listAvailableAdvsByPosition(Advertisement advertisement) throws  UhomeStoreException;
	
	/*
	 * 分页查询广告
	 */
	public void searchAdvs(BasePagination<Advertisement> advertisementPage) throws  UhomeStoreException;
	
	/*
	 * 添加广告
	 */
	public void addAdv(Advertisement advertisement) throws UhomeStoreException;
	
	/*
	 * 更新广告
	 */
	public void updateAdvById(Advertisement advertisement) throws UhomeStoreException;
	
	/*
	 * 根据id查询广告
	 */
	public Advertisement getAdvById(Integer id) throws UhomeStoreException;
	
	/*
	 * 查询广告位信息
	 */
	public List<AdvPosition> listAdvPositons() throws UhomeStoreException;
	
	/**
	 * 获取首页秒广告列表
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<Advertisement> getHomeDefaultSecKillAdvList() throws UhomeStoreException;
	
	/**
	 * 获取首页往期特卖广告
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<Advertisement> listHomeHistorySpecialAdv() throws UhomeStoreException;
	
	
	/**获得首页右边5个广告
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<Advertisement> listHomeRightAdvertisement()throws UhomeStoreException;
	/**
	 * 获取首页轮播广告
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<Advertisement> listHomeBroadcastAdv() throws UhomeStoreException;
	/**
	 * 首页顶部广告
	 * @return
	 * @throws UhomeStoreException
	 */
	public Advertisement getHomeTopAdv() throws UhomeStoreException;
}
