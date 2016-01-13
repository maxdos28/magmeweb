/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.widget;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.constants.PojoConstant;
import cn.magme.constants.PojoConstant.SORT;
import cn.magme.pojo.FpageEvent;
import cn.magme.pojo.Issue;
import cn.magme.pojo.Sort;
import cn.magme.service.FpageEventService;
import cn.magme.service.IssueService;
import cn.magme.service.PopularService;
import cn.magme.service.SortService;
import cn.magme.web.action.BaseAction;

/**
 * @author shenhao
 * @date 2012-6-7
 * @version $id$
 */
@SuppressWarnings("serial")
@Results({@Result(name = "success", location = "/WEB-INF/pages/widget/qplusReader.ftl")})
public class GleasyReadAction extends BaseAction {

	@Resource
	private IssueService issueService;
	@Resource
	private PopularService popularService;
	@Resource
	private SortService sortService;
	@Resource
	private FpageEventService fpageEventService;
	private static final Logger log = Logger.getLogger(GleasyReadAction.class);
	private Long id;
	private Long pageId;
	private Issue issue;
	private String backUrl;
	private Integer backPageNo;
	private Long eventId;
	private Long adId;
	private List<Sort> sortList;
	private String xx;

	/**
	 * 插件阅读器
	 */
	public String execute() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, post-check=0, pre-check=0");

		if (this.id == null) {
			FpageEvent fpageEvent = fpageEventService.getFpageEventById(this.eventId);
			if (fpageEvent != null && !fpageEvent.getStatus().equals(PojoConstant.FPAGEEVENT.STATUS_DELETE)) {
				this.id = fpageEvent.getIssueId();
				this.adId = fpageEvent.getAdId();
				if (this.pageId == null)
					this.pageId = (long) (fpageEvent.getPageNo());
			}
		}
		//对应的点击数+1
		popularService.click(this.id, PojoConstant.POPULAR.TYPE_ISSUE);
		try {
			issue = this.issueService.queryById(id);
		} catch (Exception e) {
			log.error("", e);
		}
		if (this.pageId == null)
			this.pageId = 1L;
		sortList = sortService.getListByGroup(SORT.GROUP_COMPUTER);
		return "success";
	}
	

	public Long getAdId() {
		return adId;
	}

	public void setAdId(Long adId) {
		this.adId = adId;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPageId() {
		return pageId;
	}

	public void setPageId(Long pageId) {
		this.pageId = pageId;
	}

	public String getBackUrl() {
		return backUrl;
	}

	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}

	public Integer getBackPageNo() {
		return backPageNo;
	}

	public void setBackPageNo(Integer backPageNo) {
		this.backPageNo = backPageNo;
	}

	public List<Sort> getSortList() {
		return sortList;
	}

	public void setSortList(List<Sort> sortList) {
		this.sortList = sortList;
	}


	public String getXx() {
		return xx;
	}


	public void setXx(String xx) {
		this.xx = xx;
	}
}
