package cn.magme.web.action.newpublisherstat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.pojo.Publication;
import cn.magme.pojo.stat.DmVisitDevicePvUv;
import cn.magme.result.stat.DmDeviceAnalysisResult;
import cn.magme.service.PublicationService;
import cn.magme.service.stat.DmInstalledTimesService;
import cn.magme.service.stat.DmStartTimesService;
import cn.magme.service.stat.DmVisitDevicePvUvService;
import cn.magme.web.action.BaseAction;

/**
 * @author xiao.chen
 * @date 2012-9-3
 * @version $id$
 */
@Results({ 
	@Result(name = "success", location = "/WEB-INF/pages/newPublisherStat/dmDeviceAnalysisUser.ftl") 
})
public class DmDeviceAnalysisAction extends BaseAction {
	private static final long serialVersionUID = -3738925569925894527L;

	private static final Logger log = Logger.getLogger(DmDeviceAnalysisAction.class);
	
	@Resource
	private PublicationService publicationService;
	@Resource
	private DmStartTimesService dmStartTimesService;
	@Resource
	private DmInstalledTimesService dmInstalledTimesService;
	@Resource
	private DmVisitDevicePvUvService dmVisitDevicePvUvService;

	private String publicationName;
	
	public String editorExecute() throws Exception {
		return execute();
	}
	@Override
	public String execute() throws Exception {
		pubList=publicationService.queryNormalByPublisherId(this.getSessionAdUserId());
		
		//获取前一天的数据
		SimpleDateFormat sf =new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		String preDate=sf.format(date);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pid", getSessionPublisherId());
		map.put("startDate", preDate);
		rList=dmStartTimesService.deviceSumAvgCount(map);
		
		if(rList!=null && rList.size()>0){
			
			Integer ydStartUser=rList.get(0).getCount();
			Integer ydNewUser = dmInstalledTimesService.deviceNewUser(map);
			
			Double at = rList.get(0).getAvg();
			String atime = at+"毫秒";
			if(at!=null){
				if(at>1000)
					atime=opTime(at.longValue());
			}else
				atime="";
			result.put("ydAvgTime", atime);
			result.put("ydStartSum", rList.get(0).getSum());
			result.put("ydStartUser", ydStartUser);
			result.put("ydNewUser", ydNewUser);
			float rate =0f;
			if(ydNewUser!=null && ydStartUser!=null && ydStartUser>0 )
				rate=(ydNewUser.floatValue()/ydStartUser.floatValue())*100;
			else{
				if(ydNewUser==null)
					ydNewUser=0;
				rate=ydNewUser.floatValue()*100;
			}
			result.put("ydNewUserRate", rate);
		}else{
			result.put("ydAvgTime",  0);
			result.put("ydStartSum", 0);
			result.put("ydStartUser", 0);
			result.put("ydNewUser", 0);
			result.put("ydNewUserRate", 0.0);
		}
		
		result.put("dayAvgStartSum",  dmStartTimesService.deviceAvgStartSum(getSessionPublisherId()));
		
		Double dat = dmStartTimesService.deviceAvgStartUserTime(getSessionPublisherId());
		String datime = dat + "毫秒";
		if(dat!=null){
			if(dat>1000)
				datime=opTime(dat.longValue());
		}else
			datime="";
		result.put("dayAvgUseTime", datime );
		
		Double startUser= dmStartTimesService.deviceAvgStartUser(getSessionPublisherId());
		Double newUser = dmInstalledTimesService.deviceAvgNewUser(getSessionPublisherId());
		float dayRate=0f;
		if(startUser!=null &&  startUser>0d )
			dayRate=(newUser.floatValue()/startUser.floatValue())*100;
		else{
			if(newUser==null)
				newUser=0d;
			dayRate=newUser.floatValue()*100;
		}
		result.put("dayAvgStartUser", startUser);
		result.put("dayAvgNewUser", newUser);
		result.put("dayNewUserRate", dayRate);
		
		result.put("TopStartSum", dmStartTimesService.deviceTopStartSum(getSessionPublisherId()));
		Integer topUser= dmStartTimesService.deviceTopStartSum(getSessionPublisherId());
		Integer topNewUser = dmInstalledTimesService.deviceTopNewUserSum(getSessionPublisherId());
		float topRate=0f;
		if(topUser!=null && topUser>0 )
			topRate=(topNewUser.floatValue()/topUser.floatValue())*100;
		else{
			if(topNewUser==null)
				topNewUser=0;
			topRate=topNewUser*100;
		}
		result.put("topStartUser", topUser);
		result.put("topNewUser", topNewUser);
		result.put("topNewUserRate", topRate);
		
		return SUCCESS;
	}

	public String analysisJson() {
		this.jsonResult = JsonResult.getFailure();
		if (publicationName != null) {
			Publication publication = publicationService.queryByPublicationName(publicationName);
			if (publication != null) {
				publicationId = publication.getId();
			}
		}
		if(this.publicationId==null || this.publicationId<=0){
			log.error("no parameter publicationid,and we can not get the publicationid from db,DmDeviceAnalysisAction.analysisJson");
			jsonResult.setMessage("杂志不存在");
		}else{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pid", getSessionPublisherId());
			map.put("publicationid", publicationId);
			map.put("device", device);
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			
			List<DmVisitDevicePvUv> list = dmVisitDevicePvUvService.queryDeviceAnalysisUser(map);
			if(list!=null){
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
				this.jsonResult.put("deviceAnalysis", list);
			}
		}
		
		return JSON;
	}
	
	public String topJson(){
		this.jsonResult = JsonResult.getFailure();
		if (publicationName != null) {
			Publication publication = publicationService.queryByPublicationName(publicationName);
			if (publication != null) {
				publicationId = publication.getId();
			}
		}
		if(this.publicationId==null || this.publicationId<=0){
			log.error("no parameter publicationid,and we can not get the publicationid from db,DmDeviceAnalysisAction.topJson");
		}else{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pid", getSessionPublisherId());
			map.put("publicationid", publicationId);
			map.put("device", device);
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			
			
			this.jsonResult.put("insertSum",dmInstalledTimesService.deviceInsertedSum(map));
			this.jsonResult.put("deviceStartSum",dmStartTimesService.deviceStartSum(map));
			map.put("startDate", null);
			map.put("endDate", null);
			this.jsonResult.put("newUser",dmInstalledTimesService.deviceNewUser(map));
			this.jsonResult.put("totalDeviceStartSum",dmStartTimesService.deviceStartSum(map));
			
			
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}
	
	private String opTime(Long time){
		String str = "";
        int hour = 0;
        int minute = 0;
        int second = 0;

        second = time.intValue() / 1000;

        if (second > 60) {
            minute = second / 60;
            second = second % 60;
        }
        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        return hour+ "时" + minute  + "分" + second+"秒";  
	}
	
	private List<Publication> pubList;
	private Date startDate;
	private Date endDate;
	private String device;
	private Long publicationId;
	private List<DmDeviceAnalysisResult> rList;
	private Map<String, Object> result=new HashMap<String, Object>();
	
	public Map<String, Object> getResult() {
		return result;
	}
	
	public Long getPublicationId() {
		return publicationId;
	}

	public void setPublicationId(Long publicationId) {
		this.publicationId = publicationId;
	}

	public List<DmDeviceAnalysisResult> getrList() {
		return rList;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public List<Publication> getPubList() {
		return pubList;
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
