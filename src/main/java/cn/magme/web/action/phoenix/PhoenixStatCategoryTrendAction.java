package cn.magme.web.action.phoenix;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.service.phoenix.PhoenixCategoryService;
import cn.magme.service.phoenix.PhoenixStatService;
import cn.magme.web.action.BaseAction;

/**
 * LOOK统计,分类（栏目）浏览趋势报表
 * @author jasper
 * @date 2013.11.7
 *
 */
@Results({@Result(name="success",location="/WEB-INF/pages/phoenix/statCategoryTrend.ftl")})
public class PhoenixStatCategoryTrendAction extends BaseAction {
	@Resource
	private PhoenixStatService phoenixStatService;
	@Resource
	private PhoenixCategoryService phoenixCategoryService;
	private static final Logger log=Logger.getLogger(PhoenixStatCategoryTrendAction.class);
	
	private Long appId = 901L;//默认APP
	private String startDate;
	private String endDate;
	private Long categoryId;
	private List categoryList;
	
	
	public String execute()
	{
		this.categoryList = this.phoenixCategoryService.queryByCondition(this.getSessionPhoenixUser().getAppId(), null, null, null, null);
		return SUCCESS;
	}
	
	//查询
	public String searchCategoryTrendJson()
	{
		this.jsonResult=JsonResult.getFailure();
		List<Map> l = this.phoenixStatService.searchCategoryTrend(startDate, endDate,categoryId, appId);
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
