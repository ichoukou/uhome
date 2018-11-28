package com.ytoxl.uhomemanage.web.action.content;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.core.common.utils.StringUtils;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.dataobject.HotWord;
import com.ytoxl.module.uhome.uhomecontent.service.HotWordService;
import com.ytoxl.uhomemanage.web.action.BaseAction;

public class HotWordAction extends BaseAction  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1563032240216719019L;
	private static Logger logger = LoggerFactory.getLogger(HotWordAction.class);
	private static final String SEARCH_HOTWORDS = "searchHotWords";
	private static final String NEXT_ACTION="nextAction";
	private List<HotWord> hotWords;
	private HotWord hotWord;
	private String nextAction;
	//记录要切换的id
	private Integer firstId;
	private Integer secondId;
	
	@Autowired
	private HotWordService hotWordService;

	public HotWordService getHotWordService() {
		return hotWordService;
	}

	public void setHotWordService(HotWordService hotWordService) {
		this.hotWordService = hotWordService;
	}

	public List<HotWord> getHotWords() {
		return hotWords;
	}

	public void setHotWords(List<HotWord> hotWords) {
		this.hotWords = hotWords;
	}
	
	public HotWord getHotWord() {
		return hotWord;
	}

	public void setHotWord(HotWord hotWord) {
		this.hotWord = hotWord;
	}

	public String getNextAction() {
		return nextAction;
	}

	public void setNextAction(String nextAction) {
		this.nextAction = nextAction;
	}

	public Integer getFirstId() {
		return firstId;
	}

	public void setFirstId(Integer firstId) {
		this.firstId = firstId;
	}

	public Integer getSecondId() {
		return secondId;
	}

	public void setSecondId(Integer secondId) {
		this.secondId = secondId;
	}

	public String listHotWords(){
		try {
			this.hotWords=hotWordService.listHotWords();
		} catch (UhomeStoreException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		return SEARCH_HOTWORDS;
	}
	
	public String saveHotWord(){
		try {
				hotWordService.addHotWord(this.hotWord);
		} catch (UhomeStoreException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		this.nextAction="hotword-listHotWords.htm";
		return NEXT_ACTION;
	}
	
	public String updateHotWord(){
		try {
				hotWordService.updateHotWordById(this.hotWord);
				setMessage(Boolean.TRUE.toString(),"更新成功");
		} catch (UhomeStoreException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(),e.getMessage());
		}
		return JSONMSG;
	}
	
	public String moveHotWordOrder(){
		try {
			if(StringUtils.isNotEmpty(""+this.firstId) && StringUtils.isNotEmpty(""+this.secondId)){
				HotWord firstHotWord=hotWordService.getHotWordById(this.firstId);
				HotWord secondHotWord=hotWordService.getHotWordById(this.secondId);
				int firstRank=firstHotWord.getRank();
				firstHotWord.setRank(secondHotWord.getRank());
				
				hotWordService.updateHotWordById(firstHotWord);
				secondHotWord.setRank(firstRank);
				hotWordService.updateHotWordById(secondHotWord);
			}
		} catch (UhomeStoreException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(),e.getMessage());
		}
		return JSONMSG;
	}
	
	public String delete(){
		try {
			hotWordService.deleteHotWordById(this.hotWord.getHotWordId());
			setMessage(Boolean.TRUE.toString(),"删除成功");
		} catch (UhomeStoreException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			setMessage(Boolean.FALSE.toString(),e.getMessage());
		}
		return JSONMSG;
	}
	
}
