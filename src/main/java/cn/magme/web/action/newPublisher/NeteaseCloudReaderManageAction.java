package cn.magme.web.action.newPublisher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.common.Page;
import cn.magme.pojo.IosApp;
import cn.magme.service.IosAppPubService;
import cn.magme.service.netease.NeteaseService;
import cn.magme.web.action.BaseAction;

/**
 * 网易云阅读队列管理
 * 
 * @author devin.song
 * @date 2012-09-14
 */
@Results({ @Result(name = "success", location = "/WEB-INF/pages/newPublisher/neteaseCloudReaderManage.ftl") })
public class NeteaseCloudReaderManageAction extends BaseAction {

	private static final Logger log = Logger
			.getLogger(NeteaseCloudReaderManageAction.class);

	@Resource
	private NeteaseService neteaseService;

	private String startDate;
	private String endDate;
	private Integer status;
	private Long publicationId;
	private Long issueId;
	private Long adDetailId;
	private String publicationName;
	private String issueName;
	private String adName;
	
	private Long taskId;

	private Integer currentPage = 1;

	@Override
	public String execute() {
		return "success";
	}

	// 查询网易任务
	public String searchNeteaseJson() {
		this.jsonResult=JsonResult.getFailure();
		if (currentPage == null || currentPage <= 0)
			currentPage = 1;
		Page p = this.neteaseService.searchNeteasePublicationTask(
				publicationId, issueId, adDetailId, publicationName, issueName,
				adName, startDate, endDate, status, currentPage);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		List<Map> rl = p.getResults();
		this.jsonResult.put("commondatas", rl);
		this.jsonResult.put("pageNo", p.getTotalPage());
		return JSON;
	}
	
	//取消任务
	public String cancelNeteaseTaskJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(taskId==null||taskId<=0)
		{
			this.jsonResult.setMessage("任务ID为空");
			return JSON;
		}
		int i = this.neteaseService.cancelNeteasePublicationTask(taskId);
		if(i<=0)
		{
			this.jsonResult.setMessage("任务取消失败");
			return JSON;
		}
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Long getAdDetailId() {
		return adDetailId;
	}

	public void setAdDetailId(Long adDetailId) {
		this.adDetailId = adDetailId;
	}

	public String getPublicationName() {
		return publicationName;
	}

	public void setPublicationName(String publicationName) {
		this.publicationName = publicationName;
	}

	public String getIssueName() {
		return issueName;
	}

	public void setIssueName(String issueName) {
		this.issueName = issueName;
	}

	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

}
