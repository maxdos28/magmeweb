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
import cn.magme.pojo.stat.DmRetention;
import cn.magme.result.stat.StatPieChart;
import cn.magme.service.PublicationService;
import cn.magme.service.stat.DmRetentionService;
import cn.magme.util.DateUtil;
import cn.magme.util.ExpressDate;
import cn.magme.web.action.BaseAction;

/**
 * @author devin.song
 * @date   2012-09-06
 */

@Results({
	@Result(name="success",location="/WEB-INF/pages/newPublisherStat/dmretention.ftl")
})
public class DmRetentionAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4765572425087593130L;
	private static final Logger log=Logger.getLogger(DmRetentionAction.class);
	@Resource
	private DmRetentionService dmRetentionService;
	@Resource
	private PublicationService publicationService;
	private String publicationName;

	public String editorExecute() throws Exception {
		return execute();
	}
	@Override
	public String execute() throws Exception {
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
		this.retentionJson();
		return "success";
	}
	
	public String retentionJson(){
		this.jsonResult = JsonResult.getFailure();
		if (publicationName != null) {
			Publication publication = publicationService.queryByPublicationName(publicationName);
			if (publication != null) {
				publicationId = publication.getId();
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
		List<DmRetention> tempList = dmRetentionService.queryDmRetentionByPublicationId(startDate, endDate, publicationId);
		for (DmRetention dmRetention : tempList) {
			if(dmRetention.getRetentionTime()!=null && dmRetention.getTimes()!=null){
				//Long tempTime = dmRetention.getRetentionTime()/dmRetention.getTimes();
				Long tempTime = dmRetention.getRetentionTime();///dmRetention.getTimes();
				String hms = DateUtil.formatLongToTimeStr(tempTime);  
				dmRetention.setPublicationName(hms);//查询结果，临时把杂志名称替换为平均停留时间
			}else{
				dmRetention.setPublicationName(null);//查询结果，临时把杂志名称换为平均停留时间
			}
		}
		statPieChartList = dmRetentionService.statPieChartByDmRetentionList(tempList);
		if(statPieChartList!=null){
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			this.jsonResult.put("statPieChartList", statPieChartList);
			this.jsonResult.put("retentionList", tempList);
		}
		return JSON;
	}
	
	private Date startDate;
	private Date endDate;
	private List<StatPieChart> statPieChartList;
	private Long publicationId;
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
