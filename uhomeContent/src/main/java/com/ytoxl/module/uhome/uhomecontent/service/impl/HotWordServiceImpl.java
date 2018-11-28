package com.ytoxl.module.uhome.uhomecontent.service.impl;

import java.util.Iterator;
import java.util.List;

import javassist.expr.Instanceof;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.dataobject.HotWord;
import com.ytoxl.module.uhome.uhomecontent.mapper.HotWordMapper;
import com.ytoxl.module.uhome.uhomecontent.service.HotWordService;
/**
 * 热关键字
 * @author guoxinzhi
 *
 */
@Service
public class HotWordServiceImpl implements HotWordService {
	
	@Autowired
	private HotWordMapper<HotWord> hotWordMapper;
	
	@Value("${hot_word_num}")
	private String hotWordNum;
	
	public HotWordMapper<HotWord> getHotWordMapper() {
		return hotWordMapper;
	}

	public void setHotWordMapper(HotWordMapper<HotWord> hotWordMapper) {
		this.hotWordMapper = hotWordMapper;
	}

	@Override
	public void addHotWord(HotWord hotWord) throws UhomeStoreException {
		// TODO Auto-generated method stub
		hotWord.setRank(hotWordMapper.listHotWordCounts()+1);
		hotWordMapper.add(hotWord);
	}

	/**
	 * 查询热关键字
	 * @throws UhomeStoreException
	 */
	@Override
	public List<HotWord> listHotWords() throws UhomeStoreException {
		// TODO Auto-generated method stub
		return hotWordMapper.listHotWords();
	}
	
	/**
	 * 删除热关键字
	 */
	@Override
	public void deleteHotWordById(Integer id) throws UhomeStoreException {
		// TODO Auto-generated method stub
		HotWord hotWord=this.getHotWordById(id);
		hotWordMapper.del(id);
		List<HotWord> lists=hotWordMapper.listHotWordByRank(hotWord.getRank());
		for (Iterator iterator = lists.iterator(); iterator.hasNext();) {
			HotWord hw = (HotWord) iterator.next();
			hw.setRank(hw.getRank()-1);
			this.updateHotWordById(hw);
		}
	}
	
	/**
	 * 更新热关键字
	 * @param id
	 * @throws UhomeStoreException
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void updateHotWordById(HotWord hotWord) throws UhomeStoreException {
		// TODO Auto-generated method stub
		hotWordMapper.update(hotWord);
	}
	
	/**
	 * 根据Id查询热关键字
	 * @throws UhomeStoreException
	 */
	@Override
	public HotWord getHotWordById(Integer id) throws UhomeStoreException {
		// TODO Auto-generated method stub
		return hotWordMapper.get(id);
	}
	
	/**
	 *查询热关键字
	 * @throws UhomeStoreException
	 */
	public HotWord getHotWord(HotWord hotWord) throws UhomeStoreException{
		return hotWordMapper.get(hotWord);
	}

	/* (non-Javadoc)
	 * @see com.ytoxl.module.uhome.uhomecontent.service.HotWordService#litHotWords4Front()
	 */
	@Override
	public List<HotWord> litHotWords4Front() throws UhomeStoreException {
		//return hotWordMapper.listHotWordsNum(3);
		return hotWordMapper.listHotWordsNum(Integer.parseInt(hotWordNum));
	}

}
