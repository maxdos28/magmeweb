/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.stat;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.pojo.stat.StatDmPagenator;
import cn.magme.result.stat.DmEventResult;
import cn.magme.result.stat.DmEventWithPublicationResult;
import cn.magme.service.stat.DmEventService;
import cn.magme.web.action.BaseAction;

/**
 * @author fredy.liu
 * @date 2012-3-22
 * @version $id$
 */
@SuppressWarnings("serial")
@Results({@Result(name="success",location="/WEB-INF/pages/stat/dmevent.ftl")
	,@Result(name="detailSuccess",location="/WEB-INF/pages/stat/dmeventdetail.ftl")})
public class DmEventAction extends BaseAction{

	private static final Logger log=Logger.getLogger(DmReaderActAction.class);
	
	private static final String DETAIL_SUCCESS="detailSuccess";
	
	@Resource
	private DmEventService dmEventService;
	
	
	public String execute(){
		return SUCCESS;
	}
	
	public String detail(){
		eventList=this.dmEventService.queryAllCategory();
		return DETAIL_SUCCESS;
	}
	
	public String queryEventDetailJson(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		StatDmPagenator pagenator=new StatDmPagenator();
		pagenator.setEndDate(endDate);
		pagenator.setPagesize(pagesize);
		pagenator.setStartDate(startDate);
		pagenator.setStartRow(startRow);
		try {
			List<DmEventWithPublicationResult> dmEventDetailList=dmEventService.queryByPagenatorAndCatId(pagenator, categoryId);
			this.jsonResult.put("dmEventDetailList", dmEventDetailList);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		} catch (Exception e) {
			log.error("", e);
		}
		return JSON;
	}
	
	public String queryEventJson(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		StatDmPagenator pagenator=new StatDmPagenator();
		pagenator.setEndDate(endDate);
		pagenator.setPagesize(pagesize);
		pagenator.setStartDate(startDate);
		pagenator.setStartRow(startRow);
		try {
			List<DmEventResult> dmEventlList=dmEventService.queryByPagenator(pagenator);
			this.jsonResult.put("dmEventlList", dmEventlList);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		} catch (Exception e) {
			log.error("", e);
		}
		return JSON;
	}
	
	
	private Date startDate;
	
	private Date endDate;

	private Integer startRow=0;
	
	private Integer pagesize=1000;
	
	private Long categoryId;
	
	private List<DmEventResult> eventList;
	

	public List<DmEventResult> getEventList() {
		return eventList;
	}

	public void setEventList(List<DmEventResult> eventList) {
		this.eventList = eventList;
	}

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
	
	
	
	
	
	

}
