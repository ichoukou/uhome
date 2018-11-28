package com.ytoxl.uhomemanage.web.action.notice;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.common.utils.StringUtils;
import com.ytoxl.module.uhome.uhomecontent.dataobject.Notice;
import com.ytoxl.module.uhome.uhomecontent.service.NoticeService;
import com.ytoxl.module.user.common.exception.YtoxlUserException;
import com.ytoxl.module.user.security.CustomUserDetails;
import com.ytoxl.module.user.service.UserService;
import com.ytoxl.uhomemanage.web.action.BaseAction;

@SuppressWarnings("serial")
public class NoticeAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(NoticeAction.class);
	
	public static final String VIEW_NOITCE = "viewNoitce"; //查看公告信息
	public static final String SEARCH_NOITCE = "searchNoitce"; //查询公告
	public static final String EDIT_NOITCE = "editNoitce"; //编辑公告
	public static final String TURN_ACTION= "turnAction"; //重定向
	
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private UserService userService;
	/** 公告分页 */
	private BasePagination<Notice> noticePage;
	/** 公告实体 */
	private Notice notice;
	/** 下一步action路径 */
	private String nextAction;
	/** 公告ID串，以逗号分隔 */
	private String noticeIds;
	
	/**
	 * 跳转新增公告页面
	 * 
	 * @return
	 */
	public String addNotice(){
		notice = new Notice();
		return EDIT_NOITCE;
	}
	
	/**
	 * 跳转修改公告页面
	 * 
	 * @return
	 */
	public String updateNotice(){
		try {
			if(notice !=null && notice.getNoticeId() != null){
				notice = noticeService.getNoticeById(notice.getNoticeId());
			}
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return EDIT_NOITCE;
	}
	
	/**
	 * 保存公告
	 * 
	 * @return
	 */
	public String saveNotice(){
		try {
			if(notice !=null){
				//处理标题，去除前后空格
				String title = StringUtils.trim(notice.getTitle());
				notice.setTitle(title);
				//处理标题来源，去除前后空格
				String source = StringUtils.trim(notice.getSource());
				notice.setSource(source);
				if(notice.getNoticeId() !=null){ //更新广告
					notice.setStatus(Notice.CHECKED_NO); //未审核
					noticeService.updateNotice(notice);
				}else{ //新增公告
					//获取当前用户
					CustomUserDetails user = userService.getCurrentUser();
					notice.setAuthorId(user.getUserId());//撰稿人
					notice.setStatus(Notice.CHECKED_NO); //未审核
					notice.setIsTop(Notice.TOP_NO);//未置顶
					noticeService.addNotice(notice);
				}
			}
		} catch (YtoxlUserException e) {
			logger.error(e.getMessage());
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		this.setNextAction("notice-searchNotice.htm");
		return TURN_ACTION;
	}
	
	/**
	 * 根据ID删除公告(Ajax)
	 * 
	 * @return
	 */
	public String deleteNotice(){
		try {
			if(StringUtils.isNotEmpty(noticeIds)){
				String str[] = noticeIds.split(",");
				//将String数组转换为Integer数组
				Integer ids[] = new Integer[str.length];
				for(int i=0; i<str.length; i++){
					ids[i] = Integer.valueOf(str[i]);
				}
				noticeService.deleteNoticeByIds(ids);
			}
		} catch (UhomeStoreException e) {
			setMessage(e.getMessage());
			logger.error("===deleteNoticeConfig()===删除公告异常！NoticeIDs：{}",noticeIds);
		}
		return JSONMSG;
	}
	
	/**
	 * 查看公告信息
	 * 
	 * @return
	 */
	public String viewNotice(){
		try {
			notice = noticeService.getNoticeById(notice.getNoticeId());
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return VIEW_NOITCE;
	}
	
	/**
	 * 分页查询公告信息
	 * 
	 * @return
	 */
	public String searchNotice(){
		if(noticePage == null){
			noticePage = new BasePagination<Notice>();
		}
		try {
			//按标题查询时，去除前后空格
			if(noticePage.getParams() !=null && StringUtils.isNotEmpty(noticePage.getParams().get("title"))){
				//标题
				String title = noticePage.getParams().get("title");
				title = StringUtils.trim(title);
				noticePage.getParams().put("title", title);
			}
			noticeService.searchNotices(noticePage);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return SEARCH_NOITCE;
	}
	
	/**
	 * 审核公告(Ajax)
	 * 
	 * @return
	 */
	public String checkNotice(){
		try {
			if(StringUtils.isNotEmpty(noticeIds)){
				String str[] = noticeIds.split(",");
				//将String数组转换为Integer数组
				Integer ids[] = new Integer[str.length];
				for(int i=0; i<str.length; i++){
					ids[i] = Integer.valueOf(str[i]);
				}
				//获取当前用户
				CustomUserDetails user = userService.getCurrentUser();
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("ids", ids);
				params.put("checkerId", user.getUserId());
				noticeService.updateCheck(params);
			}
		} catch (YtoxlUserException e) {
			setMessage(e.getMessage());
			logger.error("===checkNoticeConfig()===审核公告异常！NoticeIDs：{}",noticeIds);
		} catch (UhomeStoreException e) {
			setMessage(e.getMessage());
			logger.error("===checkNoticeConfig()===审核公告异常！NoticeIDs：{}",noticeIds);
		}
		return JSONMSG;
	}
	
	/**
	 * 更新公告置顶
	 * 
	 * @return
	 */
	public String updateNoticeTop(){
		try {
			if(notice !=null && notice.getNoticeId() !=null){
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("noticeId", notice.getNoticeId());
				params.put("isTop", notice.getIsTop());
				noticeService.updateIsTop(params);
			}
		} catch (Exception e) {
			setMessage(e.getMessage());
			logger.error("===updateNoticeTopConfig()===置顶或取消置顶公告异常！NoticeID：{}",notice.getNoticeId());
		}
		return JSONMSG;
	}

	public BasePagination<Notice> getNoticePage() {
		return noticePage;
	}

	public void setNoticePage(BasePagination<Notice> noticePage) {
		this.noticePage = noticePage;
	}

	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

	public String getNextAction() {
		return nextAction;
	}

	public void setNextAction(String nextAction) {
		this.nextAction = nextAction;
	}

	public String getNoticeIds() {
		return noticeIds;
	}

	public void setNoticeIds(String noticeIds) {
		this.noticeIds = noticeIds;
	}
	
}
