package cn.magme.web.action.look;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.common.Page;
import cn.magme.pojo.look.LooStartPic;
import cn.magme.service.look.LookCategoryService;
import cn.magme.service.look.LookStartPicService;
import cn.magme.service.look.LookStatService;
import cn.magme.util.FileOperate;
import cn.magme.util.ImageUtil;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

/**
 * LOOK统计,分类（栏目）浏览量报表
 * @author jasper
 * @date 2013.11.7
 *
 */
@Results({@Result(name="success",location="/WEB-INF/pages/looker/stat/statCategoryPvUv.ftl")})
public class LookStatCategoryPvUvAction extends BaseAction {
	@Resource
	private LookStatService lookStatService;
	@Resource
	private LookCategoryService lookCategoryService;
	private static final Logger log=Logger.getLogger(LookStatCategoryPvUvAction.class);
	
	private Long appId = 903L;//默认APP
	private String startDate;
	private String endDate;
	private Long categoryId;
	private List categoryList;
	
	
	public String execute()
	{
		this.categoryList = this.lookCategoryService.getAllLooCategory();
		return SUCCESS;
	}
	
	//查询
	public String searchCategoryPvUvJson()
	{
		this.jsonResult=JsonResult.getFailure();
		List<Map> l = null;
		if(categoryId==null||categoryId<=0)
			l = this.lookStatService.searchCategoryPvUv(startDate, endDate, appId);
		else
			l = this.lookStatService.searchItemPvUv(startDate, endDate, categoryId, appId);
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

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public List getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List categoryList) {
		this.categoryList = categoryList;
	}
}
