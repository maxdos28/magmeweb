/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.stat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.pojo.CreativeCategory;
import cn.magme.pojo.stat.DmDetailsCreative;
import cn.magme.pojo.stat.DmPvUvCreative;
import cn.magme.pojo.stat.StatDmPagenator;
import cn.magme.result.stat.DmEventResult;
import cn.magme.result.stat.DmEventWithPublicationResult;
import cn.magme.service.sns.CreativeCategoryService;
import cn.magme.service.stat.DmEventService;
import cn.magme.service.stat.DmPvUvCreativeService;
import cn.magme.web.action.BaseAction;

/**
 * @author devin.song
 * @date 2013-03-06
 */
@SuppressWarnings("serial")
@Results({@Result(name="success",location="/WEB-INF/pages/stat/dmcreative.ftl"),
		@Result(name="success_line",location="/WEB-INF/pages/stat/dmcreativeline.ftl")})
public class DmPvUvCreativeAction extends BaseAction{

	private static final Logger log=Logger.getLogger(DmReaderActAction.class);
	
	
	@Resource
	private DmPvUvCreativeService dmPvUvCreativeService;
	
	@Resource
	private CreativeCategoryService creativeCategoryService;
	
	
	public String execute(){
		detail();
		ccList = creativeCategoryService.queryCategoryTree();
		return SUCCESS;
	}
	
	public String line(){
		detailLine();
		ccList = creativeCategoryService.queryCategoryTree();
		return "success_line";
	}
	
	public String detail(){
		this.jsonResult = JsonResult.getFailure();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("startDate",startDate);
		map.put("endDate",endDate);
		map.put("firstLevelId", categoryId);
		creativeList=this.dmPvUvCreativeService.listDmPvUvCreativeByMap(map);
		this.jsonResult = JsonResult.getSuccess();
		this.jsonResult.put("creativeList", creativeList);
		return JSON;
	}
	
	public String detailLine(){
		this.jsonResult = JsonResult.getFailure();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("startDate",startDate);
		map.put("endDate",endDate);
		map.put("secondLevelId", categoryId);
		creativeList=this.dmPvUvCreativeService.listDmPvUvCreativeByMapLine(map);
		
		ccList = creativeCategoryService.queryCategoryTree();
		
		this.jsonResult = JsonResult.getSuccess();
		this.jsonResult.put("creativeList", creativeList);
		this.jsonResult.put("category", "全部");
		for (CreativeCategory cc : ccList) {
			if(cc.getId().compareTo(categoryId)==0){
				this.jsonResult.put("category", cc.getName());
				break;
			}
		}
		return JSON;
	}
	
	private Date startDate;
	
	private Date endDate;

	private Integer startRow=0;
	
	private Integer pagesize=1000;
	
	private Long categoryId=0L;
	
	private List<DmPvUvCreative> creativeList;
	
	private List<CreativeCategory> ccList;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public Integer getStartRow() {
		return startRow;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public Integer getPagesize() {
		return pagesize;
	}

	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}

	public List<DmPvUvCreative> getCreativeList() {
		return creativeList;
	}

	public void setCreativeList(List<DmPvUvCreative> creativeList) {
		this.creativeList = creativeList;
	}

	public List<CreativeCategory> getCcList() {
		return ccList;
	}

	public void setCcList(List<CreativeCategory> ccList) {
		this.ccList = ccList;
	}
	
}
