/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.third;


import javax.annotation.Resource;
import cn.magme.common.JsonResult;
import cn.magme.pojo.Issue;
import cn.magme.service.IssueService;
import cn.magme.web.action.BaseAction;

/**
 * @author devin.song
 * @date 2012-11-07
 * @version $id$
 */
@SuppressWarnings("serial")
public class IssueCommAction extends BaseAction {
	@Resource
	private IssueService issueService;
	
	
	private Long sortId;
	private Issue issue;
	
	/**
	 * 最新期刊查询
	 * @return
	 * @throws Exception
	 */
	public String newIssue() throws Exception {
		jsonResult = new JsonResult();
		//默认失败
		jsonResult.setCode(JsonResult.CODE.FAILURE);
		jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		try{
			issue = issueService.queryQplusLastestIssue(sortId);
			jsonResult.setCode(JsonResult.CODE.SUCCESS);
			jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			jsonResult.put("newIssue", issue);
		}catch (Exception e) {
			return JSON;
		}
		return JSON;
	}

	public Long getSortId() {
		return sortId;
	}

	public void setSortId(Long sortId) {
		this.sortId = sortId;
	}

	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}
	
	
}
