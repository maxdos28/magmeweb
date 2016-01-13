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
import cn.magme.result.stat.StatCommonResult;
import cn.magme.service.stat.DmAdsideClickService;
import cn.magme.web.action.BaseAction;

/**
 * @author fredy.liu
 * @date 2012-3-20
 * @version $id$
 */
@Results({@Result(name="success",location="/WEB-INF/pages/stat/dmadsideclick.ftl")})
public class DmAdsideClickAction extends BaseAction{
	
	@Resource
	private DmAdsideClickService dmAdsideClickService;
	
	private static final Logger log=Logger.getLogger(DmAdsideClickAction.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -709296607399203186L;
	
	public String execute(){
		//dmAdsideClickService.queryByDaterangeAndDomainForPage(startDate, endDate, domain, startRow, pagesize);
		listDomain=dmAdsideClickService.queryAllDomain();
		return SUCCESS;
	}
	
	public String getAdsideClickJson(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		try {
			List<StatCommonResult> dmAdsideClickList=dmAdsideClickService.queryByDaterangeAndDomainForPage(startDate, endDate, domain, startRow, pagesize);
			this.jsonResult.put("dmAdsideClickList", dmAdsideClickList);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		} catch (Exception e) {
			log.error("", e);
		}
		return JSON;
	}
	
	public String queryAllDomainJson(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		try {
			List<String> allDomains=dmAdsideClickService.queryAllDomain();
			this.jsonResult.put("allDomains", allDomains);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		} catch (Exception e) {
			log.error("", e);
		}
		return JSON;
	}
	
	private Date startDate;
	
	private Date endDate;
	
	private String domain;
	
	private List<String> listDomain;
	
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

	public String getDomain() {
		return domain;
	}
	
	public List<String> getListDomain() {
		return listDomain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
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
