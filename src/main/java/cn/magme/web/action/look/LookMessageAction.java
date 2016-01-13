package cn.magme.web.action.look;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.common.Page;
import cn.magme.pojo.look.LooStartPic;
import cn.magme.service.look.LookMessageService;
import cn.magme.util.FileOperate;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

/**
 * LOOK消息管理
 * @author jasper
 * @date 2013.10.24
 *
 */
@Results({@Result(name="success",location="/WEB-INF/pages/looker/admin/message.ftl")})
public class LookMessageAction extends BaseAction {
	@Resource
	private LookMessageService lookMessageService;
	private static final Logger log=Logger.getLogger(LookMessageAction.class);
	
	private String startDate;
	private String endDate;
	private String nickName;
	private String message;
	private Integer type;
	private Long userId;
	private Integer currentPage = 1;
	
	
	
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
		Page p = lookMessageService.searchMessage(startDate, endDate, type, userId, nickName, message, currentPage);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		List<Map> rl = p.getResults();
		this.jsonResult.put("commondatas", rl);
		this.jsonResult.put("pageNo", p.getTotalPage());
		return JSON;
	}
	//发布消息
	public String releaseMessageJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(type==null)
		{
			log.warn("类型为空");
			this.jsonResult.setMessage("类型为空");
			return JSON;
		}
		if(StringUtil.isBlank(message))
		{
			log.warn("消息为空");
			this.jsonResult.setMessage("消息为空");
			return JSON;
		}
		this.jsonResult = this.lookMessageService.sendMessage(type, userId, nickName, message);
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
}
