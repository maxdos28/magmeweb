package cn.magme.web.action.phoenix;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
//import org.springframework.data.mongodb.core.mapreduce.GroupByResults;

import cn.magme.common.JsonResult;
//import cn.magme.mongo.dao.PhoenixAppStartDao;
//import cn.magme.mongo.domain.PhoenixAppStart;
//import cn.magme.mongo.result.DateCountResult;
//import cn.magme.mongo.result.PieResult;
import cn.magme.web.action.BaseAction;
/**
 * 文章阅读
 * @author fredy
 * @since 2013-5-22
 */
@Results({@Result(name="success",location="/WEB-INF/pages/phoenix/appStart.ftl"),
	@Result(name="index",location="/WEB-INF/pages/phoenix/appStartIndex.ftl")})
public class AppStartAction extends BaseAction {
	
	//@Resource
	//PhoenixAppStartDao phoenixAppStartDao;

	/**
	 * 
	 */
	private static final long serialVersionUID = -2420595924020498016L;
	private static final Logger log=Logger.getLogger(AppStartAction.class);
	
	
	public String execute(){
		return SUCCESS;
	}
	
	public String index(){
		return "index";
	}
	
	public String addJson(){
		this.jsonResult=JsonResult.getFailure();
//		if(action==null || appId==null || StringUtils.isBlank(deviceBrand)
//				|| StringUtils.isBlank(deviceModel) || StringUtils.isBlank(os) || StringUtils.isBlank(this.osVersion)){
//			this.jsonResult.setMessage("参数不足，无法保存");
//			return JSON;
//		}
//		try {
//			PhoenixAppStart p=new PhoenixAppStart();
//			p.setAction(action);
//			p.setAppId(appId);
//			p.setCreatedTime(new Date());
//			p.setDeviceBrand(deviceBrand.toLowerCase());
//			p.setDeviceModel(deviceModel.toLowerCase());
//			p.setDeviceNo(deviceNo.toLowerCase());
//			p.setIp(ServletActionContext.getRequest().getRemoteAddr());
//			p.setOs(os.toLowerCase());
//			p.setOsVersion(osVersion.toLowerCase());
//			phoenixAppStartDao.insert(p);
//			this.jsonResult=JsonResult.getSuccess();
//		} catch (Exception e) {
//			log.error("", e);
//			return JSON;
//		}
		return JSON;
	}
	/**
	 * 2、	系统使用量统计
	 * @return
	 */
	public String appUseJson(){
		this.jsonResult=JsonResult.getFailure();
//		GroupByResults<PieResult> res=null;
//		if(StringUtils.isBlank(this.os)){
//			res=this.phoenixAppStartDao.queryByDateRangeGroupByOs(this.getSessionPhoenixUser().getAppId(),startDate, endDate);
//		}else{
//			res=this.phoenixAppStartDao.queryByDateRangeOsGroupByOsVersion(this.getSessionPhoenixUser().getAppId(),startDate, endDate, os);
//		}
//		
//		if(res!=null){
//			List<PieResult> pieList=new ArrayList<PieResult>();
//			for(PieResult p:res){
//				pieList.add(p);
//			}
//			this.jsonResult.put("statPieChartList",pieList);
//			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
//			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
//		}
		return JSON;
	}
	/**
	 * 1、	用户APP安装量统计
	 * @return
	 */
	public String appInstallJson(){
		this.jsonResult=JsonResult.getFailure();
//		List<Integer> actions=new ArrayList<Integer>();
//		actions.add(1);
//		GroupByResults<DateCountResult> res=this.phoenixAppStartDao.queryByDateRangeAndOsGroupByTime(this.getSessionPhoenixUser().getAppId(),actions, startDate, endDate, os);
//		if(res!=null){
//			List<DateCountResult> resList=new ArrayList<DateCountResult>();
//			for(DateCountResult p:res){
//				resList.add(p);
//			}
//			this.jsonResult.put("resList",resList);
//			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
//			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
//		}
		return JSON;
	}
	
	/**
	 * 开始日期
	 */
	private Date startDate;
	/**
	 * 结束日期
	 */
	private Date endDate;
	
	
	
	/**
	 * 设备品牌
	 */
	private String deviceBrand;
	/**
	 * 设备型号
	 */
	private String deviceModel;
	/**
	 * 操作系统
	 */
	private String os;
	/**
	 * 操作系统版本
	 */
	private String osVersion;
	
	/**
	 * appid应用id
	 */
	private Long appId;
	/**
	 * deviceNo
	 */
	private String deviceNo;
	/**
	 * 动作类型 1 首次打开app 2 非首次打开app
	 */
	private Integer action;


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
	public String getDeviceBrand() {
		return deviceBrand;
	}
	public void setDeviceBrand(String deviceBrand) {
		this.deviceBrand = deviceBrand;
	}
	public String getDeviceModel() {
		return deviceModel;
	}
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getOsVersion() {
		return osVersion;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	
	public Long getAppId() {
		return appId;
	}
	public void setAppId(Long appId) {
		this.appId = appId;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public Integer getAction() {
		return action;
	}
	public void setAction(Integer action) {
		this.action = action;
	}

}
