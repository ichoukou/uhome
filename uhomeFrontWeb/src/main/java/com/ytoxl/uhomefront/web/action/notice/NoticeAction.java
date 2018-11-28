package com.ytoxl.uhomefront.web.action.notice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Brand;
import com.ytoxl.module.uhome.uhomebase.dataobject.Product;
import com.ytoxl.module.uhome.uhomebase.service.BrandService;
import com.ytoxl.module.uhome.uhomebase.service.ProductService;
import com.ytoxl.module.uhome.uhomebase.service.UserInfoService;
import com.ytoxl.module.uhome.uhomecontent.dataobject.Notice;
import com.ytoxl.module.uhome.uhomecontent.service.NoticeService;
import com.ytoxl.module.user.dataobject.User;
import com.ytoxl.uhomefront.web.action.BaseAction;

/**
 * @author huangwenxuan
 * 
 */
@SuppressWarnings("serial")
public class NoticeAction extends BaseAction {

	private static Logger logger = LoggerFactory.getLogger(NoticeAction.class);

	@Autowired
	private NoticeService noticeService;

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private ProductService productService;

	@Autowired
	private BrandService brandService;

	/** 公告分页 */
	private BasePagination<Notice> noticePage;

	private Integer noticeId;

	private Notice notice;

	private Notice previousNotice;

	private Notice nextNotice;

	// 人气商品
	private List<Product> mostHits;

	// 热销品牌
	private List<Brand> hotBrands;
	
	/**
	 * 默认一页数量
	 */
	private int notice_limit;
	
	// 显示主页公告
	public String noticeList() {
		if (noticePage == null) {
			noticePage = new BasePagination<Notice>();
		}
		try {
			noticePage.setLimit(notice_limit);
			noticeService.searchNoticeList(noticePage);
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return "noticeList";
	}

	// 显示专题下的所有广告
	public String noticeDetail() {
		try {
			notice = noticeService.getNoticeById(noticeId);
			User user = userInfoService.getUserById(notice.getAuthorId());
			notice.setUserName(user.getUsername());
			previousNotice = noticeService.previousNotice(noticeId,
					notice.getType());
			nextNotice = noticeService.nextNotice(noticeId, notice.getType());
			mostHits = productService.getProductListByHits(8);
			// 热销品牌
			hotBrands = brandService.listHotBrands();
			if (notice != null && notice.getContent() != null) {
				int imgNo = notice.getContent().indexOf("img");
				if (imgNo != -1) {
					int begin = notice.getContent().indexOf("src=\"",imgNo) + 5;
					int end = notice.getContent().indexOf("\"", begin + 5);
					if (begin > 4) {
						notice.setImgUrl(notice.getContent().substring(begin,
								end));
					}
				}
			}
			if (noticePage == null) {
				noticePage = new BasePagination<Notice>();
				Map<String, String> map = new HashMap<String, String>();
				map.put("type", String.valueOf(notice.getType()));
				noticePage.setParams(map);
				noticeService.searchNotices(noticePage);
			}
		} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}

		return "noticeDetail";
	}

	public BasePagination<Notice> getNoticePage() {
		return noticePage;
	}

	public void setNoticePage(BasePagination<Notice> noticePage) {
		this.noticePage = noticePage;
	}

	public Integer getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(Integer noticeId) {
		this.noticeId = noticeId;
	}

	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

	public Notice getPreviousNotice() {
		return previousNotice;
	}

	public void setPreviousNotice(Notice previousNotice) {
		this.previousNotice = previousNotice;
	}

	public Notice getNextNotice() {
		return nextNotice;
	}

	public void setNextNotice(Notice nextNotice) {
		this.nextNotice = nextNotice;
	}

	public List<Product> getMostHits() {
		return mostHits;
	}

	public void setMostHits(List<Product> mostHits) {
		this.mostHits = mostHits;
	}

	public List<Brand> getHotBrands() {
		return hotBrands;
	}

	public void setHotBrands(List<Brand> hotBrands) {
		this.hotBrands = hotBrands;
	}

	@Value("${notice_limit}")
	public void setNotice_limit(int notice_limit) {
		this.notice_limit = notice_limit;
	}
}
