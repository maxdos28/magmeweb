package cn.magme.web.action.newPublisher;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.pojo.IosApp;
import cn.magme.pojo.IosAppPub;
import cn.magme.pojo.Publication;
import cn.magme.pojo.Publisher;
import cn.magme.service.IosAppPubService;
import cn.magme.service.IosAppService;
import cn.magme.service.PublicationService;
import cn.magme.service.PublisherService;
import cn.magme.util.GetFirstLetter;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;
/**
 * @author devin.song
 * @date 2012-09-14
 */
@Results({@Result(name="success",location="/WEB-INF/pages/newPublisher/appNew.ftl")})
public class AppNewAction extends BaseAction {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7655451143788483220L;

	private static final Logger log=Logger.getLogger(AppNewAction.class);
		
	@Resource
	private IosAppService iosAppService;
	@Resource
	private PublicationService publicationService;
	@Resource
	private IosAppPubService iosAppPubService;
	@Resource
	private PublisherService publisherService;
	
	@Override
	public String execute(){
		publisherList = publisherService.getAll();
		for (Publisher person : publisherList) {
//			webSite
			String personName = person.getPublishName();
			if(person!=null && personName!=null){
				String firstName = GetFirstLetter.firstStr(personName);
				person.setWebSite(firstName);//临时存放首字母
			}
		}
		return "success";
	}
	
	/**
	 * 添加iosApp对象
	 * @return
	 */
	public String addIosApp(){
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		IosApp iosApp = new IosApp();
		if(!StringUtil.isNotBlank(name)){
			this.jsonResult.setMessage("应用名称不能为空!");
			return JSON;
		}
		if(!StringUtil.isNotBlank(information)){
			this.jsonResult.setMessage("版权信息不能为空!");
			return JSON;
		}
				
		if(userType==0){//magme用户
			iosApp.setUserId(this.getSessionAdmin().getId());
		}else{//出版商的id
			Publication pubPojo = publicationService.queryById(publicationId);
			if(pubPojo!=null&&publisherId!=null&&pubPojo.getPublisherId().equals(publisherId)){
				iosApp.setUserId(pubPojo.getPublisherId());
			}else{
				this.jsonResult.setMessage("选择的杂志有不属于杂志社的,请保持清醒状态");
				return JSON;
			}
		}
		
		iosApp.setName(name);
		iosApp.setFirstType(firstType);
		iosApp.setSecondType(secondType);
		iosApp.setUserType(userType);
		iosApp.setAppKeyword(appKeyword);
		iosApp.setDescription(description);
		iosApp.setInformation(information);
		iosApp.setStatus(status);
		try{
			Long appId = iosAppService.addIosApp(iosApp);
			IosApp tempApp = new IosApp();
			tempApp.setId(appId);
			tempApp.setAppKey("MagmePeriodical"+appId);
			iosAppService.updateIosApp(tempApp);//更新appKey值
			Long[] pubIdArray = StringUtil.splitToLongArray(publicationStr);
			if(pubIdArray!=null && pubIdArray.length>0){
				for (int i = 0; i < pubIdArray.length; i++) {
					Long tempPubId = pubIdArray[i];
					IosAppPub appPub = new IosAppPub();
					appPub.setAppId(appId);
					appPub.setPublicationId(tempPubId);
					appPub.setStatus(status);//正常状态
					iosAppPubService.addIosAppPub(appPub);
				}
				
			}else{
				this.jsonResult.setMessage("请选择需要加入App的杂志");
				return JSON;
			}
		}catch (Exception e) {
			log.error(e);
			this.jsonResult.setMessage("添加失败");
			return JSON;
		}
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}
	
	/**
	 * 根据出版商id获取对应的杂志
	 * @return
	 */
	public String publicationListByPublisherId(){
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		Long[] publisherIdArray = StringUtil.splitToLongArray(publisherStr);
		if(publisherIdArray!=null && publisherIdArray.length>0){
			try{
				publicationList = new ArrayList<Publication>();
					for (int j = 0; j < publisherIdArray.length; j++) {
						Long publisherId = publisherIdArray[j];
						List<Publication> tmpList = publicationService.queryAuditByPublisherId(publisherId);
						publicationList.addAll(tmpList);
					}
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
				this.jsonResult.put("publicationList",publicationList);
			}catch (Exception e) {	
				log.error(e);
				return JSON;
			}
		}
		return JSON;
	}
	
	private Long publicationId;
	private String publicationStr;
	private String publisherStr;
	private Long publisherId;
	
	private String name;
	private String firstType;
	private String secondType;
	private Long userId;
	private Integer userType=0;
	private String information;
	private String description;
	private String appKeyword;
	private String appKey;
	private Integer status =1;
	private List<Publisher> publisherList;
	private List<Publication> publicationList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstType() {
		return firstType;
	}

	public void setFirstType(String firstType) {
		this.firstType = firstType;
	}

	public String getSecondType() {
		return secondType;
	}

	public void setSecondType(String secondType) {
		this.secondType = secondType;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAppKeyword() {
		return appKeyword;
	}

	public void setAppKeyword(String appKeyword) {
		this.appKeyword = appKeyword;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getPublicationId() {
		return publicationId;
	}

	public void setPublicationId(Long publicationId) {
		this.publicationId = publicationId;
	}

	public String getPublicationStr() {
		return publicationStr;
	}

	public void setPublicationStr(String publicationStr) {
		this.publicationStr = publicationStr;
	}

	public List<Publisher> getPublisherList() {
		return publisherList;
	}

	public void setPublisherList(List<Publisher> publisherList) {
		this.publisherList = publisherList;
	}

	public String getPublisherStr() {
		return publisherStr;
	}

	public void setPublisherStr(String publisherStr) {
		this.publisherStr = publisherStr;
	}

	public List<Publication> getPublicationList() {
		return publicationList;
	}

	public void setPublicationList(List<Publication> publicationList) {
		this.publicationList = publicationList;
	}

	public Long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}
	
	
}
