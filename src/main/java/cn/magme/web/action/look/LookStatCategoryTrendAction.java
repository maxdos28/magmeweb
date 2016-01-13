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
import cn.magme.service.look.LookCategoryService;
import cn.magme.service.look.LookStartPicService;
import cn.magme.service.look.LookStatService;
import cn.magme.util.FileOperate;
import cn.magme.util.ImageUtil;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

/**
 * LOOK统计,分类（栏目）浏览趋势报表
 * @author jasper
 * @date 2013.11.7
 *
 */
@Results({@Result(name="success",location="/WEB-INF/pages/looker/stat/statCategoryTrend.ftl")})
public class LookStatCategoryTrendAction extends BaseAction {
	@Resource
	private LookStatService lookStatService;
	@Resource
	private LookCategoryService lookCategoryService;
	private static final Logger log=Logger.getLogger(LookStatCategoryTrendAction.class);
	
	private Long appId = 903L;//默认APP
	private String startDate;
	private String endDate;
	private Long categoryId;
	private Long itemId;
	private List categoryList;
	
	
	public String execute()
	{
		this.categoryList = this.lookCategoryService.getAllLooCategory();
		return SUCCESS;
	}
	
	//查询
	public String searchCategoryTrendJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(categoryId==null||categoryId<=0)
		{
			this.jsonResult.setMessage("请选择分类");
			return JSON;
		}
		List<Map> l = null;
		if(itemId==null||itemId<=0)
			l = this.lookStatService.searchCategoryTrend(startDate, endDate,categoryId, appId);
		else
			l = this.lookStatService.searchItemTrend(startDate, endDate, itemId, appId);
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

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
}
