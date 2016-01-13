package cn.magme.web.action.sms;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.result.stat.DmDataMapResult;
import cn.magme.service.sms.SmsDmUserAreaService;
/**
 * 
 * @author fredy
 * @since 2013-3-28
 */
@Results({@Result(name="success",location="/WEB-INF/pages/sms/smsDmUserArea.ftl")})
public class SmsDmUserAreaAction extends SmsStatBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9015781505338426525L;
	
	@Resource
	private SmsDmUserAreaService smsDmUserAreaService;
	
	public String execute(){
		this.getProjectIdAndList();
		return SUCCESS;
		
	}
	
	public String areaJson(){
		this.jsonResult=JsonResult.getFailure();
		if(smsProjectId==null){
			return JSON;
		}
		List<DmDataMapResult> dmAreaList=smsDmUserAreaService.queryByProjIdAndDate(startDate, endDate, smsProjectId);
		if (dmAreaList != null
				&& dmAreaList.size() >= 0) {
			this.jsonResult.put("datamapList", dmAreaList);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}
	
	private Date startDate;
	
	private Date endDate;
	

	public SmsDmUserAreaService getSmsDmUserAreaService() {
		return smsDmUserAreaService;
	}

	public void setSmsDmUserAreaService(SmsDmUserAreaService smsDmUserAreaService) {
		this.smsDmUserAreaService = smsDmUserAreaService;
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

	

}
