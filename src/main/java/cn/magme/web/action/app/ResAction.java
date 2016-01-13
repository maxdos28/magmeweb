package cn.magme.web.action.app;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import cn.magme.common.JsonResult;
import cn.magme.pojo.FpageEventDto;
import cn.magme.pojo.Issue;
import cn.magme.pojo.Sort;
import cn.magme.service.FpageEventService;
import cn.magme.service.IssueService;
import cn.magme.service.SortService;
import cn.magme.service.UserService;
import cn.magme.web.action.BaseAction;

@SuppressWarnings("serial")
public class ResAction extends BaseAction{

	private Long sortId;
	private Long publicationId;
	private String client;
	private Integer begin;
	private Integer size;
	public Long getSortId() {
		return sortId;
	}

	public void setSortId(Long sortId) {
		this.sortId = sortId;
	}

	public Long getPublicationId() {
		return publicationId;
	}

	public void setPublicationId(Long publicationId) {
		this.publicationId = publicationId;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
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
	@Resource
	private FpageEventService fpageEventService;
	@Resource
	private IssueService issueService;
	@Resource
	private SortService sortService;
	@Resource
	private UserService userService;
	
	public String fetchEvents(){
		jsonResult = new JsonResult();
		//默认失败
		jsonResult.setCode(JsonResult.CODE.FAILURE);
		jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);

		List<FpageEventDto> eventList = fpageEventService.getHomeEventListForMobile(sortId, publicationId, true, begin, size);
		jsonResult.put("eventList", eventList);
		jsonResult.setCode(JsonResult.CODE.SUCCESS);
		jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}
	
	/**
	 * 最新杂志（移动端）
	 * @return
	 */
    public String fetchMags(){
		jsonResult=new JsonResult();
		//默认失败
		jsonResult.setCode(JsonResult.CODE.FAILURE);
		jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		List<Issue> issueList = this.issueService.queryLastestIssuesForMobile(sortId, begin, size);
		jsonResult.put("issueList", issueList);
		jsonResult.setCode(JsonResult.CODE.SUCCESS);
		jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}
	
	public String fetchIssues(){
		jsonResult=new JsonResult();
		//默认失败
		jsonResult.setCode(JsonResult.CODE.FAILURE);
		jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		List<Issue> issueList =this.issueService.queryByPubIdForMobile(publicationId, null, null);
		jsonResult.put("issueList", issueList);
		jsonResult.setCode(JsonResult.CODE.SUCCESS);
		jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}
	
	public String fetchFavs(){
		jsonResult=new JsonResult();
		// 默认失败
		jsonResult.setCode(JsonResult.CODE.FAILURE);
		jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		
		List<FpageEventDto> eventList = fpageEventService.getMobileEnjoyEventList(this.getSessionUserId(), begin, size);
		jsonResult.put("eventList", eventList);
		jsonResult.setCode(JsonResult.CODE.SUCCESS);
		jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}
	
	public String sortType() {
		jsonResult = new JsonResult();
		//默认失败
		jsonResult.setCode(JsonResult.CODE.FAILURE);
		jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		
		// 移动端分类
		List<Sort> sortLst = sortService.getAllList();
		List<Sort> sortLstM = new ArrayList<Sort>();
		for (Sort sort: sortLst) {
			if (sort.getGroup().equalsIgnoreCase("mobile")) {
				sortLstM.add(sort);
			}
		}
		Long usrid = this.getSessionUserId();
		if (usrid != null) {
			int enjoyCount = userService.getEnjoyNumByUserId(usrid,new Integer[]{3});
			jsonResult.put("enjoyCount", enjoyCount);
		}
		jsonResult.put("sortLst", sortLstM);
		jsonResult.setCode(JsonResult.CODE.SUCCESS);
		jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		
		return JSON;
	}
}
