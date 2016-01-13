/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.stat;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.pojo.stat.StatDmPagenator;
import cn.magme.result.stat.DmReferResult;
import cn.magme.service.stat.DmReferService;
import cn.magme.web.action.BaseAction;

/**
 * @author fredy.liu
 * @date 2012-3-22
 * @version $id$
 */
@Results({@Result(name="success",location="/WEB-INF/pages/stat/dmrefer.ftl"),
	@Result(name="embedsuccess",location="/WEB-INF/pages/stat/dmembed.ftl"),
	@Result(name="snssuccess",location="/WEB-INF/pages/stat/dmsns.ftl"),
	@Result(name="detailsuccess",location="/WEB-INF/pages/stat/dmreferdetail.ftl")})
public class DmReferAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5741530507356464017L;

	/**
	 * 
	 */
	
	private static final Logger log=Logger.getLogger(DmReferAction.class);
	
	private static final String EMBED_SUCCESS="embedsuccess";
	
	private static final String SNS_SUCCESS="snssuccess";
	
	private static final String DETAIL_SUCCESS="detailsuccess";
	
	@Resource
	private DmReferService dmReferService;
	
	
	public String execute(){
		if(this.type==null || this.type==1){
			return SUCCESS;
		}
		if(this.type==2){
			return EMBED_SUCCESS;
		}
		if(this.type==3){
			return SNS_SUCCESS;
		}
		return SUCCESS;
	}
	
	public String queryReferJson(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		StatDmPagenator pagenator=new StatDmPagenator();
		pagenator.setEndDate(endDate);
		pagenator.setPagesize(pagesize);
		pagenator.setStartDate(startDate);
		pagenator.setStartRow(startRow);
		try {
			List<DmReferResult> dmReferList=dmReferService.queryByPagenator(pagenator,type);
			Integer totalNum=dmReferService.queryTotalCountByPagenator(pagenator, type);
			this.jsonResult.put("dmReferList", dmReferList);
			this.jsonResult.put("totalNum", totalNum);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		} catch (Exception e) {
			log.error("", e);
		}
		return JSON;
	}
	
	public String referDetail(){
		return DETAIL_SUCCESS;
	}
	
	public String queryReferByDomainJson(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		if(StringUtils.isBlank(domain)){
			this.jsonResult.setMessage("domain不能为空");
			return JSON;
		}
		
		StatDmPagenator pagenator=new StatDmPagenator();
		pagenator.setEndDate(endDate);
		pagenator.setPagesize(pagesize);
		pagenator.setStartDate(startDate);
		pagenator.setStartRow(startRow);
		try {
			List<DmReferResult> dmReferList=dmReferService.queryByPagenatorAndDomain(pagenator, domain,type);
			this.jsonResult.put("dmReferList", dmReferList);
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

	private Integer startRow=0;
	
	private Integer pagesize=1000;
	
	private Integer type; 
	
	


	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public String getDomain() {
		return domain;
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
