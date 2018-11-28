package com.ytoxl.module.uhome.uhomecontent.service;

import java.util.List;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.dataobject.HotWord;
/**
 * 热关键字
 * @author guoxinzhi
 *
 */
public interface HotWordService {
	public void addHotWord(HotWord hotWord) throws UhomeStoreException;
	
	/**
	 * 查询热关键字
	 * @throws UhomeStoreException
	 */
	public List<HotWord> listHotWords() throws UhomeStoreException;
	/**
	 * 删除热关键字
	 */
	public void deleteHotWordById(Integer id)throws UhomeStoreException;
	
	/**
	 * 更新热关键字
	 * @param id
	 * @throws UhomeStoreException
	 */
	public void updateHotWordById(HotWord hotWord) throws UhomeStoreException;
	
	/**
	 *查询热关键字
	 * @throws UhomeStoreException
	 */
	public HotWord getHotWord(HotWord hotWord) throws UhomeStoreException;
	
	/**
	 * 根据Id查询热关键字
	 * @throws UhomeStoreException
	 */
	public HotWord getHotWordById(Integer id) throws UhomeStoreException;
	
	/**
	 * 前台显示的热搜词 默认 3个
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<HotWord> litHotWords4Front() throws UhomeStoreException;
	
}
