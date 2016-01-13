package cn.magme.web.action.look;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.common.Page;
import cn.magme.constants.PojoConstant;
import cn.magme.constants.WebConstant;
import cn.magme.pojo.look.LooAdminUser;
import cn.magme.service.look.LookSendService;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

import com.opensymphony.xwork2.ActionContext;

/**
 * LOOK消息管理
 * @author jasper
 * @date 2013.10.24
 *
 */
@Results({@Result(name="success",location="/WEB-INF/pages/looker/admin/send.ftl")})
public class LookSendAction extends BaseAction {
	@Resource
	private LookSendService lookSendService;
	private static final Logger log=Logger.getLogger(LookSendAction.class);
	
	private String startDate;
	private String endDate;
	private String message;
	private Integer type;
	private Long articleId;
	private Integer currentPage = 1;
	private Long appId = 903L;
	
	
	
	public String execute()
	{
		return SUCCESS;
	}
	
	//查询消息
	public String searchMessageJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(currentPage==null||currentPage<=0)
			currentPage = 1;
		Page p = lookSendService.searchMessage(startDate, endDate, type, articleId, message, currentPage);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		List<Map> rl = p.getResults();
		this.jsonResult.put("commondatas", rl);
		this.jsonResult.put("pageNo", p.getTotalPage());
		return JSON;
	}
	//推送消息
	public String sendMessageJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(type==null||type<0)
		{
			log.warn("类型为空");
			this.jsonResult.setMessage("类型为空");
			return JSON;
		}
		if((type==PojoConstant.Look.SEND_TYPE_1)&&(articleId==null||articleId<=0))
		{
			log.warn("文章ID");
			this.jsonResult.setMessage("文章ID");
			return JSON;
		}
		if((type==PojoConstant.Look.SEND_TYPE_2)&&StringUtil.isBlank(message))
		{
			log.warn("消息为空");
			this.jsonResult.setMessage("消息为空");
			return JSON;
		}
		LooAdminUser u = (LooAdminUser)ActionContext.getContext().getSession().get(WebConstant.SESSION.LOOK_USER);
		this.jsonResult = this.lookSendService.sendMessage(appId, type, articleId, message, u.getId(), u.getUserNo());
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}
}
