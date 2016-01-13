package cn.magme.web.action.sms;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.pojo.sms.SmsDm800Detail;
import cn.magme.pojo.sms.SmsDm800Stat;
import cn.magme.service.sms.SmsDm800DetailService;
import cn.magme.service.sms.SmsDm800StatService;
/**
 * 
 * @author fredy
 * @since 2013-3-28
 */
@Results({@Result(name="success",location="/WEB-INF/pages/sms/smsDmEzzStat.ftl")})
public class SmsDmEzzStatAction extends SmsStatBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9015781505338426525L;
	
	@Resource
	private SmsDm800StatService smsDm800StatService;
	
	@Resource
	private SmsDm800DetailService smsDm800DetailService;
	
	public String execute(){
		this.getProjectIdAndList();
		return SUCCESS;
	}
	
	public String statJson(){
		this.jsonResult=JsonResult.getFailure();
		if(smsProjectId==null){
			return JSON;
		}
		SmsDm800Stat smsDm800Stat=smsDm800StatService.queryByDateAndProjId(startDate, endDate, smsProjectId);
		List<SmsDm800Detail> smsDm800DetailList=smsDm800DetailService.queryByDateAndProjId(startDate, endDate, smsProjectId);
		if (smsDm800Stat != null && smsDm800DetailList!=null && smsDm800DetailList.size()>0) {
			this.jsonResult.put("smsDm800Stat", smsDm800Stat);
			this.jsonResult.put("smsDm800DetailList", smsDm800DetailList);
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
