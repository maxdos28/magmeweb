package cn.magme.web.action.newpublisherstat;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.pojo.Publication;
import cn.magme.pojo.stat.AppUsage;
import cn.magme.result.stat.StatPieChart;
import cn.magme.service.PublicationService;
import cn.magme.service.stat.AppUsageService;
import cn.magme.service.stat.DmVisitDevicePvUvService;
import cn.magme.util.ExpressDate;
import cn.magme.web.action.BaseAction;

/**
 * @author devin.song
 * @date 2012-09-03
 */

@Results({
	@Result(name="success",location="/WEB-INF/pages/newPublisherStat/dmvisitdevicepvuv.ftl")
})
public class DmVisitDevicePvUvAction extends BaseAction {
	private static final long serialVersionUID = 8524160004084773902L;
	private static final Logger log=Logger.getLogger(DmVisitDevicePvUvAction.class);
	
	@Resource
	private DmVisitDevicePvUvService dmVisitDevicePvUvService;
	@Resource
	private PublicationService publicationService;
	@Resource
	private AppUsageService appUsageService;
	
	private List<AppUsage> appUsages;

	public String editorExecute() {
		return execute();
	}
	public String execute(){
		Map<String,Date> dateMap=ExpressDate.judgeStatDate(startDate, endDate);
		this.startDate=dateMap.get("startDate");
		this.endDate=dateMap.get("endDate");
		pubList=publicationService.queryNormalByPublisherId(this.getSessionAdUserId());
		if(this.publicationId==null || this.publicationId<=0){
			if(pubList==null || pubList.size()<=0){
				log.error("no parameter publicationid,and we can not get the publicationid from db,method:DmVisitDevicePvUvAction.execute");
				return SUCCESS;
			}
			this.publicationId=pubList.get(0).getId();
		}
		processAppUsages();
//		this.newUserNum = dmInstalledTimesService.deviceSumNewUser(this.getSessionAdUserId());
//		this.installedNum =dmInstalledTimesService.deviceSumNum(this.getSessionAdUserId());
//		this.startNum =dmStartTimesService.deviceSumStartSum(this.getSessionAdUserId());
		this.devicePvUv();
		return "success";
	}
	private void processAppUsages() {
		Long publisherId = getSessionPublisherId();
		if (publisherId != null) {
			appUsages = appUsageService.queryByPublisherId(publisherId);
		} else if (publicationId != null) {
			appUsages = appUsageService.queryByPublicationId(publicationId);
		}
		if (appUsages != null) {
			AppUsage appUsage = new AppUsage();
			appUsage.setAppName("总计");
			appUsage.setNewUser(0);
			appUsage.setInstallNum(0);
			appUsage.setStartUpNum(0);
			for (AppUsage app : appUsages) {
				appUsage.setNewUser(appUsage.getNewUser() + app.getNewUser());
				appUsage.setInstallNum(appUsage.getInstallNum() + app.getInstallNum());
				appUsage.setStartUpNum(appUsage.getStartUpNum() + app.getStartUpNum());
			}
			appUsages.add(appUsage);
		}
	}
	public String devicePvUv(){
		this.jsonResult = JsonResult.getFailure();
		if (publicationName != null) {
			Publication publication = publicationService.queryByPublicationName(publicationName);
			if (publication != null) {
				publicationId = publication.getId();
				processAppUsages();
				jsonResult.put("appUsages", appUsages);
			} else {
				jsonResult.setMessage("杂志不存在");
				return JSON;
			}
		}
		if(this.publicationId==null || this.publicationId<=0){
			pubList=publicationService.queryNormalByPublisherId(this.getSessionAdUserId());
			if(pubList==null || pubList.size()<=0){
				log.error("no parameter publicationid,and we can not get the publicationid from db,method:DmVisitDevicePvUvAction.devicePvUv");
				return SUCCESS;
			}
			this.publicationId=pubList.get(0).getId();
		}
		statPieChartList = dmVisitDevicePvUvService.queryByDateAndPagenator(startDate, endDate, publicationId);
		if(statPieChartList!=null){
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			this.jsonResult.put("statPieChartList", statPieChartList);
			Double tempSum=0.0;
			for (StatPieChart spc : statPieChartList) {
				tempSum=tempSum+spc.getValue();
			}
			this.jsonResult.put("statPieChartSum", tempSum);
		}
		return JSON;
	}
	
	
	private Integer newUserNum;
	private Integer installedNum;
	private Integer startNum;
	
	private Date startDate;
	private Date endDate;
	private List<StatPieChart> statPieChartList;
	private Long publicationId;
	private String publicationName;
	private List<Publication> pubList;
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
	public List<StatPieChart> getStatPieChartList() {
		return statPieChartList;
	}
	public void setStatPieChartList(List<StatPieChart> statPieChartList) {
		this.statPieChartList = statPieChartList;
	}
	public Long getPublicationId() {
		return publicationId;
	}
	public void setPublicationId(Long publicationId) {
		this.publicationId = publicationId;
	}
	public List<Publication> getPubList() {
		return pubList;
	}
	public void setPubList(List<Publication> pubList) {
		this.pubList = pubList;
	}
	public Integer getNewUserNum() {
		return newUserNum;
	}
	public void setNewUserNum(Integer newUserNum) {
		this.newUserNum = newUserNum;
	}
	public Integer getStartNum() {
		return startNum;
	}
	public void setStartNum(Integer startNum) {
		this.startNum = startNum;
	}
	public Integer getInstalledNum() {
		return installedNum;
	}
	public void setInstalledNum(Integer installedNum) {
		this.installedNum = installedNum;
	}
	/**
	 * @param appUsages the appUsages to set
	 */
	public void setAppUsages(List<AppUsage> appUsages) {
		this.appUsages = appUsages;
	}
	/**
	 * @return the appUsages
	 */
	public List<AppUsage> getAppUsages() {
		return appUsages;
	}
	/**
	 * @param publicationName the publicationName to set
	 */
	public void setPublicationName(String publicationName) {
		this.publicationName = publicationName;
	}
	/**
	 * @return the publicationName
	 */
	public String getPublicationName() {
		return publicationName;
	}
	
}
