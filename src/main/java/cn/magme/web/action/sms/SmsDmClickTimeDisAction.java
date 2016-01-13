package cn.magme.web.action.sms;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.pojo.sms.SmsDmClickTimeDis;
import cn.magme.service.sms.SmsDmClickTimeDisService;
/**
 * 
 * @author fredy
 * @since 2013-3-28
 */
@Results({@Result(name="success",location="/WEB-INF/pages/sms/smsDmClickTimeDis.ftl")})
public class SmsDmClickTimeDisAction extends SmsStatBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9015781505338426525L;
	
	@Resource
	private SmsDmClickTimeDisService smsDmClickTimeDisService;
	
	public String execute(){
		this.getProjectIdAndList();
		return SUCCESS;
	}
	
	public String clickTimeJson(){
		this.jsonResult=JsonResult.getFailure();
		if(smsProjectId==null){
			return JSON;
		}
		List<SmsDmClickTimeDis> smsDmClickTimeList=smsDmClickTimeDisService.queryByDateAndProjId(startDate, endDate, smsProjectId);
		if (smsDmClickTimeList != null
				&& smsDmClickTimeList.size() >= 0) {
			this.jsonResult.put("smsDmClickTimeDis", smsDmClickTimeList);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
		
	}
	
	
	
	private Date startDate;
	
	private Date endDate;
	
	

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

	

}
