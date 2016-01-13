package cn.magme.web.action.phoenix;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.pojo.FeedBack;
import cn.magme.service.FeedBackService;
import cn.magme.web.action.BaseAction;
/**
 * 意见反馈
 * @author fredy
 * @since 2013-6-18
 */
@Results({@Result(name="success",location="/WEB-INF/pages/phoenix/feedback.ftl"),@Result(name="mobile",location="/WEB-INF/pages/phoenix/commitFeedback.ftl")})
public class FeedBackAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -559328875082054657L;
	@Resource
	private FeedBackService feedBackService;
	
	private String content;
	
	public String execute(){
		return SUCCESS;
	}
	
	public String show(){
		return "mobile";
	}
	
	public String feedBackJson(){
		this.jsonResult=JsonResult.getFailure();
		rowCount=feedBackService.queryByAppIdCount(this.getSessionPhoenixUser().getAppId(),startDate , endDate);
		countPage();
		this.begin=(currentPage - 1) * pageSize;
		if(rowCount>0){
			this.feedBackList=feedBackService.queryByAppIdPagenator(this.getSessionPhoenixUser().getAppId(), begin, pageSize, startDate ,endDate);
		}
		if(this.feedBackList!=null && this.feedBackList.size()>0){
			this.jsonResult=JsonResult.getSuccess();
			this.jsonResult.put("list", this.feedBackList);
			this.jsonResult.put("currentPage", currentPage);
			this.jsonResult.put("pageCount", pageCount);
		}
		return JSON;
	}
	
	private void countPage() {
		if (rowCount % this.pageSize == 0)
			pageCount = rowCount / this.pageSize;
		else
			pageCount = rowCount / this.pageSize + 1;// 总页数
		if (currentPage <= 0)
			currentPage = 1;
		if (currentPage > pageCount)
			currentPage = pageCount;
	}
	
	private List<FeedBack> feedBackList;
	private Long begin=0L;
	private long currentPage;
	private long pageCount;
	private Long rowCount;
	private final Long pageSize = 15L;
	private Date startDate;
	private Date endDate;
	private String os;
	private String v;
	 private Long appId;
	
	public long getCurrentPage() {
		return currentPage;
	}


	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}


	public long getPageCount() {
		return pageCount;
	}


	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}


	public long getRowCount() {
		return rowCount;
	}


	public void setRowCount(long rowCount) {
		this.rowCount = rowCount;
	}

	public List<FeedBack> getFeedBackList() {
		return this.feedBackList;
	}

	public void setFeedBackList(List<FeedBack> feedBackList) {
		this.feedBackList = feedBackList;
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getV() {
		return v;
	}

	public void setV(String v) {
		this.v = v;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}
	
	
}
