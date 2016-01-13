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
@Results({@Result(name="success",location="/WEB-INF/pages/looker/stat/statArticleOrPub.ftl")})
public class LookStatArticleOrPubAction extends BaseAction {
	@Resource
	private LookStatService lookStatService;
	@Resource
	private LookItemService lookItemService;
	private static final Logger log=Logger.getLogger(LookStatArticleOrPubAction.class);
	
	private Long appId = 903L;//默认APP
	private List itemList;
	private String puType;
	private Integer limit;
	private String startDate;
	private String endDate;
	private Long itemId;
	
	public String execute()
	{
		this.itemList = this.lookItemService.getAllLooItem();
		return SUCCESS;
	}
	
	//查询
	public String searchStatArticleOrPubJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(itemId==null||itemId<=0)
		{
			this.jsonResult.setMessage("请选择栏目");
			return JSON;
		}
		List<Map> l = this.lookStatService.searchArticleOrPut(startDate, endDate, itemId, puType, limit, appId);
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

	public List getItemList() {
		return itemList;
	}

	public void setItemList(List itemList) {
		this.itemList = itemList;
	}

	public String getPuType() {
		return puType;
	}

	public void setPuType(String puType) {
		this.puType = puType;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
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
}
