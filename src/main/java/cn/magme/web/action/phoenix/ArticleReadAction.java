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
//import cn.magme.mongo.dao.PhoenixArticleReadDao;
//import cn.magme.mongo.domain.PhoenixArticleRead;
//import cn.magme.mongo.result.LineResult;
import cn.magme.pojo.phoenix.PhoenixCategory;
import cn.magme.service.phoenix.PhoenixCategoryService;
import cn.magme.web.action.BaseAction;
/**
 * 文章阅读
 * @author fredy
 * @since 2013-5-22
 */
@Results({@Result(name="success",location="/WEB-INF/pages/phoenix/articleRead.ftl")})
public class ArticleReadAction extends BaseAction {
	
//	@Resource
//	private PhoenixArticleReadDao phoenixArticleReadDao;
//	
//	@Resource
//	private PhoenixCategoryService phoenixCategoryService;
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 6779037128740680711L;
//	
//	private static final Logger log=Logger.getLogger(ArticleReadAction.class);
//	
//	public String execute(){
//		this.clist=this.phoenixCategoryService.queryByCondition(this.getSessionPhoenixUser().getAppId(), null, null, null, null);
//		return SUCCESS;
//	}
//
//	/**
//	 * 
//	 */
//	public String addJson(){
//		this.jsonResult=JsonResult.getFailure();
//		if(this.phoenixArticleId==null || this.phoenixCategoryId==null  || this.isFree==null
//				|| appId==null || StringUtils.isBlank(deviceBrand) || StringUtils.isBlank(muid)
//				|| StringUtils.isBlank(deviceModel) || StringUtils.isBlank(os) || StringUtils.isBlank(this.osVersion)){
//			this.jsonResult.setMessage("参数不足，无法保存");
//			return JSON;
//		}
//		try {
//			PhoenixArticleRead p = new PhoenixArticleRead();
//			p.setAppId(appId);
//			p.setCreatedTime(new Date());
//			p.setDeviceBrand(deviceBrand.toLowerCase());
//			p.setDeviceModel(deviceModel.toLowerCase());
//			p.setDeviceNo(deviceNo.toLowerCase());
//			p.setIp(ServletActionContext.getRequest().getRemoteAddr());
//			p.setIsFree(isFree);
//			p.setMuid(muid);
//			p.setOs(os.toLowerCase());
//			p.setOsVersion(osVersion.toLowerCase());
//			p.setPhoenixArticleId(phoenixArticleId);
//			p.setPhoenixCategoryId(phoenixCategoryId);
//			p.setPhoenixCategoryName(phoenixCategoryService.queryCachedById(appId,phoenixCategoryId).getName());
//			this.phoenixArticleReadDao.insert(p);
//			this.jsonResult=JsonResult.getSuccess();
//		} catch (Exception e) {
//			log.error("", e);
//			return JSON;
//		}
//		
//		return JSON;
//	}
//	
//	public String readJson(){
//		this.jsonResult=JsonResult.getFailure();
//		try {
//			List<Integer> actions=new ArrayList<Integer>();
//			actions.add(1);
//			List<LineResult> resList=this.phoenixArticleReadDao.queryPvUvByDateRangeFreeCid(this.getSessionPhoenixUser().getAppId(),startDate, endDate, isFree, phoenixCategoryId);
//			if(resList!=null){
//				this.jsonResult.put("resList",resList);
//				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
//				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
//			}
//		} catch (Exception e) {
//			log.error("", e);
//		}
//		return JSON;
//	}
//	
//	
//	private Date startDate;
//	
//	private Date endDate;
//	
//	/**
//	 * 设备品牌
//	 */
//	private String deviceBrand;
//	/**
//	 * 设备型号
//	 */
//	private String deviceModel;
//	/**
//	 * 操作系统
//	 */
//	private String os;
//	/**
//	 * 操作系统版本
//	 */
//	private String osVersion;
//	/**
//	 * appid应用id
//	 */
//	private Long appId;
//	/**
//	 * deviceNo
//	 */
//	private String deviceNo;
//	
//	/**
//	 * 阅读的文章id
//	 */
//	private Long phoenixArticleId;
//	/**
//	 * muid
//	 */
//	private String muid;
//	/**
//	 * 类目id
//	 */
//	private Long phoenixCategoryId;
//	
//	/**
//	 * 是否免费 0 收费 1 免费
//	 */
//	private Integer isFree;
//	
//	private List<PhoenixCategory> clist;
//	
//	
//
//
//	
//
//	public List<PhoenixCategory> getClist() {
//		return clist;
//	}
//
//	public void setClist(List<PhoenixCategory> clist) {
//		this.clist = clist;
//	}
//
//	public Date getStartDate() {
//		return startDate;
//	}
//
//	public void setStartDate(Date startDate) {
//		this.startDate = startDate;
//	}
//
//	public Date getEndDate() {
//		return endDate;
//	}
//
//	public void setEndDate(Date endDate) {
//		this.endDate = endDate;
//	}
//
//	public String getDeviceBrand() {
//		return deviceBrand;
//	}
//
//	public void setDeviceBrand(String deviceBrand) {
//		this.deviceBrand = deviceBrand;
//	}
//
//	public String getDeviceModel() {
//		return deviceModel;
//	}
//
//	public void setDeviceModel(String deviceModel) {
//		this.deviceModel = deviceModel;
//	}
//
//	public String getOs() {
//		return os;
//	}
//
//	public void setOs(String os) {
//		this.os = os;
//	}
//
//	public String getOsVersion() {
//		return osVersion;
//	}
//
//	public void setOsVersion(String osVersion) {
//		this.osVersion = osVersion;
//	}
//
//
//	public Long getAppId() {
//		return appId;
//	}
//
//	public void setAppId(Long appId) {
//		this.appId = appId;
//	}
//
//	public String getDeviceNo() {
//		return deviceNo;
//	}
//
//	public void setDeviceNo(String deviceNo) {
//		this.deviceNo = deviceNo;
//	}
//
//	public Long getPhoenixArticleId() {
//		return phoenixArticleId;
//	}
//
//	public void setPhoenixArticleId(Long phoenixArticleId) {
//		this.phoenixArticleId = phoenixArticleId;
//	}
//
//	public String getMuid() {
//		return muid;
//	}
//
//	public void setMuid(String muid) {
//		this.muid = muid;
//	}
//
//	public Long getPhoenixCategoryId() {
//		return phoenixCategoryId;
//	}
//
//	public void setPhoenixCategoryId(Long phoenixCategoryId) {
//		this.phoenixCategoryId = phoenixCategoryId;
//	}
//
//	public Integer getIsFree() {
//		return isFree;
//	}
//
//	public void setIsFree(Integer isFree) {
//		this.isFree = isFree;
//	}
	
	
	
}
