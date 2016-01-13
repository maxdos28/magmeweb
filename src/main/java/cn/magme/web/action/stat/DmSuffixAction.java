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
import cn.magme.pojo.stat.DmSuffix;
import cn.magme.pojo.stat.StatDmPagenator;
import cn.magme.service.stat.DmSuffixService;
import cn.magme.web.action.BaseAction;

/**
 * @author fredy.liu
 * @date 2012-3-22
 * @version $id$
 */
@Results({@Result(name="success",location="/WEB-INF/pages/stat/dmsuffix.ftl")})
public class DmSuffixAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8983049371364169467L;

	/**
	 * 
	 */
	
	private static final Logger log=Logger.getLogger(DmSuffixAction.class);
	
	@Resource
	private DmSuffixService dmSuffixService;
	
	
	public String execute(){
		return SUCCESS;
	}
	
	public String querySuffixJson(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		StatDmPagenator pagenator=new StatDmPagenator();
		pagenator.setEndDate(endDate);
		pagenator.setPagesize(pagesize);
		pagenator.setStartDate(startDate);
		pagenator.setStartRow(startRow);
		try {
			List<DmSuffix> dmSuffixList=dmSuffixService.queryByPagenatorAndSuffix(pagenator, suffix);
			this.jsonResult.put("dmSuffixList", dmSuffixList);
			this.jsonResult.put("showType", showType);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		} catch (Exception e) {
			log.error("", e);
		}
		return JSON;
	}
	
	private String showType;
	
	
	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}
	
	private Date startDate;
	
	private Date endDate;

	private Integer startRow=0;
	
	private Integer pagesize=1000;
	
	private String suffix; 
	
	


	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
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
