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
import cn.magme.pojo.look.LooStartPic;
import cn.magme.service.look.LookUserService;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

/**
 * LOOK用户反馈管理
 * @author jasper
 * @date 2013.10.24
 *
 */
@Results({@Result(name="success",location="/WEB-INF/pages/looker/admin/userFeedback.ftl")})
public class LookUserFeedbackAction extends BaseAction {
	@Resource
	private LookUserService lookUserService;
	private static final Logger log = Logger
			.getLogger(LookUserFeedbackAction.class);
	
	private String startDate;
	private String endDate;
	private String nickName;
	private Integer replyStatus;
	private Long userId;
	private Integer currentPage = 1;
	private String message;
	private Long userFeedbackId;
	
	private String os;
	
	
	public String execute()
	{
		return SUCCESS;
	}
	
	//查询用户反馈
	public String searchUserFeedbackJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(currentPage==null||currentPage<=0)
			currentPage = 1;
		Page p = lookUserService.searchUserFeedback(startDate, endDate, userId, nickName, replyStatus,os, currentPage);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		List<Map> rl = p.getResults();
		this.jsonResult.put("commondatas", rl);
		this.jsonResult.put("pageNo", p.getTotalPage());
		return JSON;
	}
	//回复用户反馈
	public String replayUserFeedbackJson()
	{
		this.jsonResult = JsonResult.getFailure();
		if (userFeedbackId == null || userFeedbackId <= 0) {
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		if (StringUtil.isBlank(message)) {
			log.warn("消息为空");
			this.jsonResult.setMessage("消息为空");
			return JSON;
		}
		int r = this.lookUserService.replyUserFeedback(userFeedbackId, message);
		if (r <= 0) {
			this.jsonResult.setMessage("发送失败");
		} else {
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
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
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getReplyStatus() {
		return replyStatus;
	}

	public void setReplyStatus(Integer replyStatus) {
		this.replyStatus = replyStatus;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getUserFeedbackId() {
		return userFeedbackId;
	}

	public void setUserFeedbackId(Long userFeedbackId) {
		this.userFeedbackId = userFeedbackId;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

}
