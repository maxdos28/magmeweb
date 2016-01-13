package cn.magme.web.action.publish;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.constants.PojoConstant;
import cn.magme.pojo.Issue;
import cn.magme.pojo.TextPage;
import cn.magme.service.IssueService;
import cn.magme.service.TextPageService;
import cn.magme.web.action.BaseAction;

@Results({@Result(name="recordAjax",location="/WEB-INF/pages/publishadmin/textPageRecord.ftl")})
public class TextPageAction  extends BaseAction{

	private static final long serialVersionUID = 4651024664083223624L;

	@Resource
	private IssueService issueService;
	
	@Resource
	private TextPageService textPageService;
	
	public String recordAjax(){
		this.issue=issueService.queryById(this.issueId);
		if(this.pageNo==null||this.pageNo<0){
			this.pageNo=0;
		}
		this.textPage=textPageService.getByIssueIdAndPageNo(this.issueId, this.pageNo);
		return "recordAjax";
	}
	
	public String saveJson(){
		TextPage textPage=new TextPage();
		textPage.setIssueId(this.issueId);
		textPage.setPageNo(this.pageNo);
		textPage.setUserId(this.getSessionPublisherId());
		textPage.setUserType(PojoConstant.TEXTPAGE.USERTYPE_PUBLISHER);
		textPage.setContent(content);
		this.jsonResult=textPageService.save(textPage);
		return JSON;
	}
	
	private Issue issue;
	private TextPage textPage;
	private Long issueId;
	private Integer pageNo;
	private String content;

	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	public TextPage getTextPage() {
		return textPage;
	}

	public void setTextPage(TextPage textPage) {
		this.textPage = textPage;
	}

	public Long getIssueId() {
		return issueId;
	}

	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
