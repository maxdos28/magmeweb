package cn.magme.web.action.newpublisherstat;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.pojo.IosApp;
import cn.magme.pojo.stat.DmAppUsageNewDetails;
import cn.magme.service.IosAppService;
import cn.magme.service.stat.DmAppUsageNewDetailsService;
import cn.magme.util.ConvertDmAppUsageDetailsToPieChart;
import cn.magme.util.ExpressDate;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;


@Results({@Result(name="success",location="/WEB-INF/pages/newPublisherStat/dmreachable.ftl")})
public class DmAppUsageNewDetailsAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8566682036542568071L;
	@Resource
	private IosAppService iosAppService;
	
	@Resource
	private DmAppUsageNewDetailsService dmAppUsageNewDetailsService;

	public String execute(){
		Map<String,Date> dateMap=ExpressDate.judgeStatDate(startDate, endDate);
		this.startDate=dateMap.get("startDate");
		this.endDate=dateMap.get("endDate");
		apps=iosAppService.queryIosAppByUserIdUserTypeAndStatus(this.getSessionPublisherId(), 1, 1);
		this.detailsJson();
		
		return SUCCESS;
	}
	
	public String editorExecute(){
		return execute();
	}
	
	public String detailsJson(){
		this.jsonResult=JsonResult.getFailure();
		if(StringUtil.isNotBlank(appName)){
			IosApp app=iosAppService.queryByName(appName);
			if(app!=null && app.getId()!=null && app.getId()>0){
				appId=app.getId();
			}
		}
		details=dmAppUsageNewDetailsService.queryGroupReachableByAppId(appId, startDate, endDate);
		if(details!=null && details.size()>0){
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			this.jsonResult.put("statPieChartList", ConvertDmAppUsageDetailsToPieChart.convertAppUsageToPieChart(details));
			this.jsonResult.put("details", details);
		}
		
		return JSON;
	}

	
	private Date startDate;
	
	private Date endDate;
	
	private Long appId;
	
	private String appName;
	
	private List<IosApp> apps;
	
	private List<DmAppUsageNewDetails> details;
	
	

	public List<DmAppUsageNewDetails> getDetails() {
		return details;
	}

	public void setDetails(List<DmAppUsageNewDetails> details) {
		this.details = details;
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

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public List<IosApp> getApps() {
		return apps;
	}

	public void setApps(List<IosApp> apps) {
		this.apps = apps;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	
	
	
}
