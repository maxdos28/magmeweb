package cn.magme.web.action.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant;
import cn.magme.constants.PojoConstant.FPAGEEVENT;
import cn.magme.pojo.FpageEvent;
import cn.magme.pojo.FpageEventDto;
import cn.magme.pojo.Issue;
import cn.magme.pojo.TextPage;
import cn.magme.pojo.TextPageDto;
import cn.magme.service.FpageEventService;
import cn.magme.service.IssueService;
import cn.magme.service.TextPageService;
import cn.magme.service.UserEnjoyService;
import cn.magme.service.UserService;
import cn.magme.util.ObjectUtil;
import cn.magme.web.action.BaseAction;

@SuppressWarnings("serial")
public class ReadAction extends BaseAction {

	@Resource
	private FpageEventService fpageEventService;
	@Resource
	private IssueService issueService;
	@Resource
	private TextPageService textPageService;
	@Resource
	private UserEnjoyService userEnjoyService;
	@Resource
	private UserService userService;
	
	private Long eventId=0L;
	private Long issueId;
	private Integer pageNo;
	private Long size;
	// 杂志分类
	private Long sortId;
	// 阅读模式
	private Long readMode;
	// 喜欢对象类型
	private Integer type;

	/**
	 * 翻页请求
	 * @return
	 */
	public String flipPage(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		if(pageNo==null || pageNo<=0){
			pageNo=1;
		}
		Issue issue=this.issueService.queryById(issueId);
		if(issue==null || issue.getStatus()!=PojoConstant.ISSUE.STATUS.ON_SALE.getCode()){
			this.jsonResult.setMessage("issue not exist or issue not on shelf");
			return JSON;
		}
		this.jsonResult.put("publicationId", issue.getPublicationId());
		TextPageDto textPage=textPageService.queryNormalByIssueIdAndPageNo(issue.getId(), pageNo);
		if (textPage == null) {
			textPage =new TextPageDto();
			textPage.setIssueId(issue.getId());
			textPage.setPageNo(pageNo);
			textPage.setContent("");
		}
		
		this.jsonResult.put("textPage", textPage);

		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}
	
	public String allTextPages(){
		this.jsonResult = JsonResult.getFailure();
		Issue issue=this.issueService.queryById(issueId);
		if(issue==null || issue.getStatus()!=PojoConstant.ISSUE.STATUS.ON_SALE.getCode()){
			this.jsonResult.setMessage("issue not exist or issue not on shelf");
			return JSON;
		}
		List<TextPage> pages = textPageService.getByIssueId(issueId);
		List<TextPageDto> dtos = new ArrayList<TextPageDto>();
		if (pages != null) {
			for (TextPage textPage : pages) {
				if (textPage != null && textPage.getId() != null && 
						textPage.getContent() != null && !"".equals(textPage.getContent())) {//忽略空记录
					TextPageDto dto = new TextPageDto();
					ObjectUtil.copyProperty(textPage, dto);
					dtos.add(dto);
				}
			}
		}
		this.jsonResult = JsonResult.getSuccess();
		jsonResult.put("textPages", dtos);
		return JSON;
	}
	
	/**
	 * 进阅读器的请求
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String eventPage(){
		Integer flg = 0;
		
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		if((eventId==null || eventId<=0) && issueId == null ){
			this.jsonResult.setMessage("parameter is wrong");
			return JSON;
		}
		FpageEvent fpageEvent = new FpageEvent();
		Map map = new HashMap();
		if (issueId != null) {
			map.put("issueId", issueId);
		}else{
			map.put("eventId", eventId);
		}
		fpageEvent = fpageEventService.getFpageEventForMobile(map);

		if(fpageEvent==null||fpageEvent.getStatus()!=FPAGEEVENT.STATUS_OK.intValue()){
			this.jsonResult.setMessage("fpageEvent abnormal");
			return JSON;
		}
		if (issueId == null) {
			List<FpageEventDto> eventList = new ArrayList<FpageEventDto>();
			if (sortId == null || size == null || size <= 0) {
				this.jsonResult.setMessage("wrong parameters");
				return JSON;
			}else if (sortId == 0) {
				// 全部分类
				eventList = fpageEventService.getHomeEventList(null, null, "mobile", true, null, null);
			}else if (sortId == -1) {
				// 我喜欢的
				if (this.getSessionUserId() == null) {
					this.jsonResult.setMessage("no user");
					return JSON;
				}
				eventList = fpageEventService.getMobileEnjoyEventList(this.getSessionUserId(), null, null);
			}else{
				// 其它
				eventList = fpageEventService.getHomeEventList(sortId, null, "mobile", true, null, null);
			}
	
			boolean nextFlg = false;
			List<FpageEventDto> eventForCacheList = new ArrayList<FpageEventDto>();
			Long initNum = 0l;
			for (FpageEventDto property: eventList) {
				if (property.getId().equals(eventId) || nextFlg) {
					initNum = initNum+1;
					flg = userEnjoyService.isEnjoy(this.getSessionUserId(), property.getId(), PojoConstant.USERENJOY.TYPE_EVENT);
					property.setEnjoyFlg(flg);
					eventForCacheList.add(property);
					flg = 0;
					if (initNum == size) {
						break;
					}
					nextFlg = true;
				}
			}
			this.jsonResult.put("eventList", eventForCacheList);
		}else{
			Integer pageNo = 0;
			Integer startPageNo=fpageEvent.getPageNo();
			Integer lastPageNo=fpageEvent.getEndPageNo();
			if(pageNo==null || pageNo<=0){
				pageNo=startPageNo;
			}
			if(pageNo==null || pageNo<=0){
				pageNo=1;
			}
			this.jsonResult.put("startPageNo", pageNo);
			this.jsonResult.put("lastPageNo", lastPageNo);
			this.jsonResult.put("eventId", fpageEvent.getId());
			this.jsonResult.put("eventTitle", fpageEvent.getTitle());
			
			// 判断用户是否已经喜欢事件
			if (issueId == null && this.getSessionUserId() != null) {
				flg = userEnjoyService.isEnjoy(this.getSessionUserId(), fpageEvent.getId(), PojoConstant.USERENJOY.TYPE_EVENT);
			}
			this.jsonResult.put("enjoyFlg", flg);
			
			Long issueId=fpageEvent.getIssueId();
			Issue issue=this.issueService.queryById(issueId);
			if(issue==null || issue.getStatus()!=PojoConstant.ISSUE.STATUS.ON_SALE.getCode()){
				this.jsonResult.setMessage("issue not exist or issue not on shelf");
				return JSON;
			}
			
			this.jsonResult.put("publicationId", issue.getPublicationId());
			this.jsonResult.put("publicationName", issue.getPublicationName() + " " + issue.getIssueNumber());
			TextPageDto textPage=textPageService.queryNormalByIssueIdAndPageNo(issue.getId(), pageNo);
			if (textPage == null) {
				textPage = new TextPageDto();
				textPage.setIssueId(issue.getId());
				textPage.setPageNo(pageNo);
				textPage.setContent("");
			}
			this.jsonResult.put("textPage", textPage);
		}
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}
	
	public String favEvent(){
		this.jsonResult=userEnjoyService.change(this.getSessionUserId(), eventId, type);
		userService.setUserEnjoyList(this.getSessionUser());
		return JSON;
	}
	
	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
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
	
	public Long getReadMode() {
		return readMode;
	}

	public void setReadMode(Long readMode) {
		this.readMode = readMode;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}
	
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
