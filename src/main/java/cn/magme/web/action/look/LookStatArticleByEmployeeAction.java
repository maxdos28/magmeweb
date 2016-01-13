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
import cn.magme.service.look.LookItemService;
import cn.magme.service.look.LookStartPicService;
import cn.magme.service.look.LookStatService;
import cn.magme.util.FileOperate;
import cn.magme.util.ImageUtil;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

/**
 * LOOK统计,杂志（文章）访问量报表
 * @author jasper
 * @date 2013.11.7
 *
 */
@Results({@Result(name="success",location="/WEB-INF/pages/looker/stat/statArticleByEmployee.ftl")})
public class LookStatArticleByEmployeeAction extends BaseAction {
	@Resource
	private LookStatService lookStatService;
	private static final Logger log=Logger.getLogger(LookStatArticleByEmployeeAction.class);
	
	private Long appId = 903L;//默认APP
	private String startDate;
	private String endDate;
	
	public String execute()
	{
		return SUCCESS;
	}
	
	//查询
	public String searchStatArticleByEmployeeJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(StringUtil.isBlank(startDate)||StringUtil.isBlank(endDate))
		{
			this.jsonResult.setMessage("请选择起止时间");
			return JSON;
		}
		List<Map> l = this.lookStatService.searchArticleByEmployee(startDate, endDate, appId);
		this.jsonResult.put("resultList", l);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}
	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
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
}
