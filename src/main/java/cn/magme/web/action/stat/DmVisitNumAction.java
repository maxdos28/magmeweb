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
import cn.magme.pojo.stat.DmVisitNum;
import cn.magme.pojo.stat.StatDmPagenator;
import cn.magme.service.stat.DmVisitNumService;
import cn.magme.web.action.BaseAction;

/**
 * @author fredy.liu
 * @date 2012-3-22
 * @version $id$
 */
@SuppressWarnings("serial")
@Results({@Result(name="success",location="/WEB-INF/pages/stat/dmvisitnum.ftl")})
public class DmVisitNumAction extends BaseAction{

	/**
	 * 
	 */
	
	private static final Logger log=Logger.getLogger(DmVisitNumAction.class);
	
	@Resource
	private DmVisitNumService dmVisitNumService;
	
	public String execute(){
		return SUCCESS;
	}
	
	public String queryVisitNumJson(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		StatDmPagenator pagenator=new StatDmPagenator();
		pagenator.setEndDate(endDate);
		pagenator.setPagesize(pagesize);
		pagenator.setStartDate(startDate);
		pagenator.setStartRow(startRow);
		try {
			List<DmVisitNum> dmResList=dmVisitNumService.queryByPagenator(pagenator);
			Integer totalNum=dmResList.get(0).getNewVisit()+dmResList.get(0).getOldVisit();
			this.jsonResult.put("totalNum", totalNum);
			this.jsonResult.put("dmVisitNumList", dmResList);
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
