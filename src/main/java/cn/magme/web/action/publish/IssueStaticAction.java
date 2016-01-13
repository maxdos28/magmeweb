package cn.magme.web.action.publish;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.pojo.Issue;
import cn.magme.service.IssueService;
import cn.magme.web.action.BaseAction;
@Results({@Result(name="success",location="/WEB-INF/pages/publish/issueStatic.ftl")})
public class IssueStaticAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3242829707179843188L;
	private static final String SUCCESS="success";
	private static final Logger log=Logger.getLogger(IssueStaticAction.class);
	@Resource
	private IssueService issueService;
	
	private Long issueId;
	private String publicationName;
	private Date publishDate;
	public Long getIssueId() {
		return issueId;
	}
	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}
	public String getPublicationName() {
		return publicationName;
	}
	public void setPublicationName(String publicationName) {
		this.publicationName = publicationName;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	
	
	public String execute(){
		try {
			Issue issue=issueService.queryById(this.issueId);
			if(issue!=null){
				this.publicationName=issue.getPublicationName();
				this.publishDate=issue.getPublishDate();
			}
		} catch (Exception e) {
			log.error("查询期刊错误", e);
		}
		return SUCCESS;
	}
	

}
