package cn.magme.web.action.newPublisher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.pojo.IssueCategory;
import cn.magme.service.IssueCategoryService;
import cn.magme.service.IssueContentsService;
import cn.magme.web.action.BaseAction;

@Results({ @Result(name = "success", location = "/WEB-INF/pages/newPublisher/issueCategoryManage.ftl") })
public class IssueCategoryManageAction extends BaseAction {
	@Resource
	private IssueCategoryService issueCategoryService;
	@Resource
	private IssueContentsService issueContentsService;

	public String execute() {
		return SUCCESS;
	}

	/**
	 * 增加期刊栏目
	 * @return
	 */
	public String addIssueCategory() {
		this.jsonResult = JsonResult.getFailure();

		if (issueId == null) {
			jsonResult.setMessage("期刊ID必须填写");
			return JSON;
		}
		if (name == null || name.trim().length() == 0) {
			jsonResult.setMessage("栏目名称必须填写");
			return JSON;
		}
		if (fromIssueContentsId == null) {
			jsonResult.setMessage("起始文章必须填写");
			return JSON;
		}
		if (endIssueContentsId == null) {
			jsonResult.setMessage("结束文章必须填写");
			return JSON;
		}
		IssueCategory ic = new IssueCategory();
		ic.setName(name);
		ic.setIssueId(issueId);
		ic.setFromIssueContentsId(fromIssueContentsId);
		ic.setEndIssueContentsId(endIssueContentsId);
		this.issueCategoryService.insert(ic);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}

	/**
	 * 修改期刊栏目
	 * @return
	 */
	public String updateIssueCategory() {
		this.jsonResult = JsonResult.getFailure();

		if (name == null || name.trim().length() == 0) {
			jsonResult.setMessage("栏目名称必须填写");
			return JSON;
		}
		if (fromIssueContentsId == null) {
			jsonResult.setMessage("起始文章必须填写");
			return JSON;
		}
		if (endIssueContentsId == null) {
			jsonResult.setMessage("结束文章必须填写");
			return JSON;
		}
		IssueCategory ic = new IssueCategory();
		ic.setId(id);
		ic.setName(name);
		ic.setFromIssueContentsId(fromIssueContentsId);
		ic.setEndIssueContentsId(endIssueContentsId);
		this.issueCategoryService.update(ic);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}
	
	/**
	 * 删除期刊栏目
	 * @return
	 */
	public String deleteIssueCategory() {
		this.jsonResult = JsonResult.getFailure();
		if (id == null) {
			jsonResult.setMessage("ID必须填写");
			return JSON;
		}
		this.issueCategoryService.delete(id);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}

	/**
	 * 得到期刊目录
	 * @return
	 */
	public String searchIssueCatalog() {
		this.jsonResult = JsonResult.getFailure();
		if (issueId == null) {
			jsonResult.setMessage("期刊ID必须填写");
			return JSON;
		}
		Map m = new HashMap();
		m.put("issueId", issueId);
		List l = this.issueContentsService.queryAllByIssueId(issueId,1,null);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		this.jsonResult.put("issueCatalogList", l);
		return JSON;
	}

	/**
	 * 查询期刊栏目
	 * @return
	 */
	public String searchIssueCategory() {
		this.jsonResult = JsonResult.getFailure();
		if (issueId == null) {
			jsonResult.setMessage("期刊ID必须填写");
			return JSON;
		}
		Map m = new HashMap();
		m.put("issueId", issueId);
		List l = this.issueCategoryService.queryIssueCategory2(m);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		this.jsonResult.put("issueCategoryList", l);
		return JSON;
	}

	private String name;
	private Long fromIssueContentsId;
	private Long endIssueContentsId;
	private Long issueId;
	private Long id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getFromIssueContentsId() {
		return fromIssueContentsId;
	}

	public void setFromIssueContentsId(Long fromIssueContentsId) {
		this.fromIssueContentsId = fromIssueContentsId;
	}

	public Long getEndIssueContentsId() {
		return endIssueContentsId;
	}

	public void setEndIssueContentsId(Long endIssueContentsId) {
		this.endIssueContentsId = endIssueContentsId;
	}

	public Long getIssueId() {
		return issueId;
	}

	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
