package cn.magme.web.action.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant;
import cn.magme.constants.WebConstant;
import cn.magme.constants.PojoConstant.SORT;
import cn.magme.pojo.Advertise;
import cn.magme.pojo.ChannelBanner;
import cn.magme.pojo.FpageEvent;
import cn.magme.pojo.FpageEventComment;
import cn.magme.pojo.HomePageItem;
import cn.magme.pojo.Issue;
import cn.magme.pojo.Sort;
import cn.magme.pojo.UserSettings;
import cn.magme.result.sns.CreativeCommentResult;
import cn.magme.service.AdvertiseService;
import cn.magme.service.ChannelBannerService;
import cn.magme.service.DetailPageService;
import cn.magme.service.FpageEventCommentService;
import cn.magme.service.FpageEventService;
import cn.magme.service.HomePageItemService;
import cn.magme.service.IssueService;
import cn.magme.service.LuceneService;
import cn.magme.service.SortService;
import cn.magme.service.UserSettingsService;
import cn.magme.service.impl.LuceneServiceImpl;
import cn.magme.util.SearchIssueSorter;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

@SuppressWarnings("serial")
public class PadAction extends BaseAction {

	/** 首页元素service<br/>[ioc] */
	@Resource
	private HomePageItemService homePageItemService;
	@Resource
	private ChannelBannerService channelBannerService;
	@Resource
	private FpageEventService fpageEventService;
	@Resource
	private SortService sortService;
	@Resource
	private AdvertiseService advertiseService;
	@Resource
	private UserSettingsService userSettingsService;
	@Resource
	private DetailPageService detailPageService;
	
	private Long publicationId;
	private Long issueId;
	private Long sortId;
	private String sortNm;
	private Integer begin;
	private Integer size;
	private Integer pageNo;
	// 搜索关键字
	private String keyWords;
	// 有效收藏分类字符串
	private String sortStr;
	private Long eventId;
	private String type;

	/**
	 * 首页
	 * @return
	 */
	public String execute(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		String url;
		Matcher matcher;
		try {
			List<ChannelBanner> bannerLst = channelBannerService.getForIpadBanner();
			for (ChannelBanner banner: bannerLst) {
				url = banner.getUrl();
				matcher = Pattern.compile("id=(\\d+)&pageId=(\\d+)").matcher(url);
				while (matcher.find()) {
					banner.setIssueId(matcher.group(1));
					banner.setPageNo(matcher.group(2));
				}
				if (banner.getIssueId() == null && url.indexOf("eventId") !=-1) {
					matcher = Pattern.compile("eventId=(\\d+)").matcher(url);
					while (matcher.find()) {
						FpageEvent fpageEvent = fpageEventService.getFpageEventById(Long.parseLong(matcher.group(1)));
						banner.setIssueId(fpageEvent.getIssueId().toString());
						banner.setPageNo(fpageEvent.getPageNo().toString());
					}
				}
			}
			
			this.jsonResult.put("bannerLst", bannerLst);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		} catch (Exception e) {
			System.out.println(e);
		}
		return JSON;
	}
	
	/**
	 * 分类
	 * @return
	 */
	public String sort(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		try {
			List<Sort> sortLst = sortService.getLstWithLatestEvent(SORT.NEW_SORT_TYPE);
			this.jsonResult.put("sortLst", sortLst);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		} catch (Exception e) {
			System.out.println(e);
		}
		return JSON;
	}

	/**
	 * 用户收藏分类查询
	 * @return
	 */
	public String sortSel(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		String defaultPath = WebConstant.PAD.DEFAULTPATH;

		UserSettings userSettings = userSettingsService.getByUserId(this.getSessionUserId());
		Sort sort;
		sort = new Sort();
		sort.setId(0l);
		sort.setPath(defaultPath);
		if (this.getSessionUserId() != null) {
			List<HomePageItem> fpageList = homePageItemService.getEnjoyLstOfHomePageItems(this.getSessionUserId(), 0, 1);
			if (fpageList.size()==1) {
			    sort.setName(WebConstant.PAD.DEFAULTPATH);
				sort.setLatestEvent(fpageList.get(0));
			}else{
				HomePageItem homePage = new HomePageItem();
				homePage.setItemImagepath(defaultPath);
				homePage.setType("");
				sort.setLatestEvent(homePage);
				sort.setName(WebConstant.PAD.DEFAULTPATH);
			}
		}
		userSettings.getSorts().add(0, sort);
		List<Sort> sorts= userSettings.getSorts();
		List<HomePageItem> pageLst;
		for (Sort pro: sorts) {
			pageLst = homePageItemService.getHomePageItemList(pro.getName(), null, true, 0, 1);
			if (pageLst!=null && pageLst.size() == 1){
				pro.setLatestEvent(pageLst.get(0));
			}
		}

		this.jsonResult.put("sortLst", userSettings.getSorts());
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}

	/**
	 * 用户收藏分类增加删除操作
	 * @return
	 */
	public String sortSave() {
		if (this.getSessionUser() == null) {
			this.generateJsonResult(JsonResult.CODE.FAILURE, JsonResult.MESSAGE.FAILURE);
		}else{
			UserSettings userSettings = userSettingsService.getByUserId(this.getSessionUserId());
			userSettings.setSortIds(sortStr);
			this.jsonResult = userSettingsService.update(userSettings);
		}
		
		return JSON;
	}
	
//	/**
//	 * 单本期刊事件
//	 * @return
//	 */
//	public String issueEventLst(){
//		this.jsonResult=new JsonResult();
//		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
//		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
//		try {
//			List<FpageEvent> eventLst = new ArrayList<FpageEvent>();
//			if (issueId != null) {
//				eventLst = fpageEventService.getEventLstByIssueId(this.getSessionUserId(),issueId, null, null);
//			}
//			this.jsonResult.put("eventLst", eventLst);
//			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
//			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//		return JSON;
//	}

	/**
	 * 分类事件
	 * @return
	 */
	public String sortEvent(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		try {
			List<HomePageItem> pageLst = new ArrayList<HomePageItem>();

			// 用户收藏
			if (StringUtil.isBlank(sortNm)) {
				if (this.getSessionUser() == null) {
					this.jsonResult.setMessage("not login");
					return JSON;
				}else{
					pageLst = homePageItemService.getEnjoyLstOfHomePageItems(this.getSessionUserId(), begin, size);
				}
			}else{
				pageLst = homePageItemService.getHomePageItemList(sortNm,null,true,begin,size);
//					eventLst = fpageEventService.getSortEventList(map);
			}
			
			this.jsonResult.put("eventLst", pageLst);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		} catch (Exception e) {
			System.out.println(e);
		}
		return JSON;
	}
	
//	/**
//	 * 最新分类期刊
//	 * @return
//	 */
//	public String pubSort() {
//		this.jsonResult=new JsonResult();
//		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
//		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
//		try {
//			List<Issue> issueLst = issueService.queryLastestIssues(sortId,begin,size);
//			
//			this.jsonResult.put("issueLst", issueLst);
//			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
//			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//		return JSON;
//	}
	
//	/**
//	 * 查询单本杂志所有期刊
//	 * @return
//	 */
//	public String issueNum() {
//		this.jsonResult=new JsonResult();
//		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
//		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
//		try {
//			List<Issue> issueLst = issueService.queryByPubIdAndStatuses(publicationId,new int[]{1});
//			
//			this.jsonResult.put("issueLst", issueLst);
//			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
//			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//		return JSON;
//	}

	 /**
	 * 查询事件信息
	 * @return
	 */
	public String eventInfo() {
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		try {
			FpageEvent fpageEvent = fpageEventService.getFpageEventById(eventId);
	
			this.jsonResult.put("fpageEvent", fpageEvent);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		} catch (Exception e) {
			System.out.println(e);
		}
		return JSON;
	}
	
	/**
	 * 检索
	 * @return
	 */
	public String searchEvent() {
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		try {
//			// 查询标题
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("userId", this.getSessionUserId());
//			map.put("title", keyWords);
//			List<FpageEvent> eventLst1 
//				= fpageEventService.getSortEventList(map);
//			// 查询描述
//			map = new HashMap<String, Object>();
//			map.put("userId", this.getSessionUserId());
//			map.put("description", keyWords);
//			List<FpageEvent> eventLst2 
//				= fpageEventService.getSortEventList(map);
//			for (FpageEvent event: eventLst2) {
//				if (!eventLst1.contains(event)) {
//					eventLst1.add(event);
//				}
//			}
//
//			// 查询结果为空，查询最新一星期事件
//			if (eventLst1.size() == 0) {
//				this.jsonResult.put("msg", "当前关键字没有查询到任何内容，以下是最新发布的事件");
//				map = new HashMap<String, Object>();
//				map.put("userId", this.getSessionUserId());
//				map.put("lastestFlg", true);
//				eventLst1 = fpageEventService.getSortEventList(map);
//			}else{
//				this.jsonResult.put("msg", "");
//			}
//			eventLst1 = eventLst1.subList(begin,
//					(begin + size) > eventLst1.size() ? eventLst1.size() : (begin + size));
		    
		    List<HomePageItem> eventLst = homePageItemService.getSearchOfHomePageItems(keyWords, begin, size);
            // 查询结果为空，查询最新一星期事件
            if (eventLst==null || eventLst.size() == 0) {
                this.jsonResult.put("msg", "没有更多内容");
            }else{
                this.jsonResult.put("msg", "");
            }
			this.jsonResult.put("eventLst", eventLst);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		} catch (Exception e) {
			System.out.println(e);
		}
		return JSON;
	}

//	/**
//	 * 检索
//	 * 杂志
//	 * @return
//	 */
//	public String searchPub() {
//		this.jsonResult=new JsonResult();
//		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
//		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
//		try {
//		if (StringUtil.isBlank(keyWords)) {
//			this.jsonResult.setMessage("搜索关键词为空");
//			return JSON;
//			}
//			keyWords = keyWords.trim();
//			
//			Long[] objIds = luceneService.seacher(keyWords, LuceneServiceImpl.CLASS_TYPE_ISSUE, null);
//			List<Issue> issueList = new ArrayList<Issue>();
//			for (Long objId : objIds) {
//				Issue issue = this.issueService.queryById(objId);
//				if (issue != null) {
//					issueList.add(issue);
//				}
//			}
//			issueList = SearchIssueSorter.sort(issueList, StringUtil.containsNumber(keyWords));
//			issueList = issueList.subList(begin,
//					(begin + size) > issueList.size() ? issueList.size() : (begin + size));
//			this.jsonResult.put("issueLst", issueList);
//			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
//			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//		return JSON;
//	}
	
	@SuppressWarnings("unchecked")
	public String ads() {
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		if(this.issueId==null || this.issueId<=0 || pageNo == null){
			return JSON;
		}
		try {
			Map map = new HashMap();
			map.put("issueId", issueId);
			map.put("pageNo", pageNo);
			map.put("status", new Integer[]{5});
			map.put("adType", new Integer[]{2,3});
			
			List<Advertise> adList = this.advertiseService.getPageAds(map);
			this.jsonResult.put("adLst", adList);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		} catch (Exception e) {
			System.out.println(e);
		}
		return JSON;
	}
	
	public String eventComment() {
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		if(this.eventId==null){
			return JSON;
		}
		try {
			List<CreativeCommentResult> commentLst = detailPageService.selCommentLst(eventId, type, begin, size);
			this.jsonResult.put("commentLst", commentLst);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return JSON;
	}
	
	/**
	 * 发表新评论
	 * @return
	 */
	public String eventCommentAdd() {
		this.jsonResult = detailPageService.insertComment(this.getSessionUserId(), eventId, keyWords, type);
		return JSON;
	}
	
	public Long getPublicationId() {
		return publicationId;
	}

	public void setPublicationId(Long publicationId) {
		this.publicationId = publicationId;
	}
	
	public Long getIssueId() {
		return issueId;
	}

	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}
	
	public Long getSortId() {
		return sortId;
	}

	public void setSortId(Long sortId) {
		this.sortId = sortId;
	}
	
	public String getSortNm() {
		return sortNm;
	}

	public void setSortNm(String sortNm) {
		this.sortNm = sortNm;
	}
	
	public Integer getBegin() {
		return begin;
	}

	public void setBegin(Integer begin) {
		this.begin = begin;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}
	
	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	
	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	
	public String getSortStr() {
		return sortStr;
	}

	public void setSortStr(String sortStr) {
		this.sortStr = sortStr;
	}
	
	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
