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
import cn.magme.constants.stat.DcRuleType;
import cn.magme.pojo.stat.DcRule;
import cn.magme.pojo.stat.DmWebAction;
import cn.magme.pojo.stat.StatDmPagenator;
import cn.magme.service.stat.DcRuleService;
import cn.magme.service.stat.DmWebActionService;
import cn.magme.web.action.BaseAction;

/**
 * @author fredy.liu
 * @date 2012-3-22
 * @version $id$
 */
@Results({@Result(name="success",location="/WEB-INF/pages/stat/dmwebact.ftl")})
public class DmWebActAction extends BaseAction{


	/**
	 * 
	 */
	private static final long serialVersionUID = 6958425973139028784L;

	private static final Logger log=Logger.getLogger(DmWebActAction.class);
	
	@Resource
	private DmWebActionService dmWebActionService;
	@Resource
	private DcRuleService dcRuleService;
	
	public String execute(){
		dcRuleList=dcRuleService.queryByType(DcRuleType.TYPE_PC_ACTION);
		return SUCCESS;
	}
	
	public String queryWebActJson(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		StatDmPagenator pagenator=new StatDmPagenator();
		pagenator.setEndDate(endDate);
		pagenator.setPagesize(pagesize);
		pagenator.setStartDate(startDate);
		pagenator.setStartRow(startRow);
		try {
			List<DmWebAction> dmWebActionList=dmWebActionService.queryByRuleIdAndPagenator(pagenator, ruleId);
			this.jsonResult.put("dmWebActionList", dmWebActionList);
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
	
	private Integer ruleId; 
	
	private List<DcRule> dcRuleList;
	
	public List<DcRule> getDcRuleList() {
		return dcRuleList;
	}

	public void setDcRuleList(List<DcRule> dcRuleList) {
		this.dcRuleList = dcRuleList;
	}
	
	public Integer getRuleId() {
		return ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
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
