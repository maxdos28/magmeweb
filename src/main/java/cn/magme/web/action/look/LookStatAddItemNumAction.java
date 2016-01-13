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
import cn.magme.service.look.LookItemService;
import cn.magme.service.look.LookStartPicService;
import cn.magme.service.look.LookStatService;
import cn.magme.util.FileOperate;
import cn.magme.util.ImageUtil;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

/**
 * LOOK统计,增加栏目报表
 * @author jasper
 * @date 2014.5.16
 *
 */
@Results({@Result(name="success",location="/WEB-INF/pages/looker/stat/statAddItemNum.ftl")})
public class LookStatAddItemNumAction extends BaseAction {
	@Resource
	private LookStatService lookStatService;
	@Resource
	private LookItemService lookItemService;
	private static final Logger log=Logger.getLogger(LookStatAddItemNumAction.class);
	
	private Long appId = 903L;//默认APP
	private String startDate;
	private String endDate;
	private Long itemId;
	private List itemList;
	
	
	public String execute()
	{
		this.itemList = this.lookItemService.getAllLooItem();
		return SUCCESS;
	}
	
	//查询
	public String searchAddItemNumJson()
	{
		this.jsonResult=JsonResult.getFailure();
		List<Map> l = null;
		if(itemId==null||itemId<=0)
			l = this.lookStatService.searchAddItemNum(startDate, endDate, appId);
		else
			l = this.lookStatService.searchAddItemNum(startDate, endDate, itemId, appId);
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

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public List getItemList() {
		return itemList;
	}

	public void setItemList(List itemList) {
		this.itemList = itemList;
	}
}
