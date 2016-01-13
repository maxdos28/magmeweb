package cn.magme.web.action.app;

import javax.annotation.Resource;

import org.apache.commons.jocl.JOCLContentHandler;
import org.apache.log4j.Logger;

import cn.magme.common.JsonResult;
import cn.magme.pojo.IosDeviceToken;
import cn.magme.service.IosDeviceTokenService;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

@SuppressWarnings("serial")
public class DeviceTokenAction extends BaseAction {
	private static final Logger log = Logger.getLogger(DeviceTokenAction.class);
	
	@Resource
	private IosDeviceTokenService iosDeviceTokenService;
	
	
	@Override
	public String execute() throws Exception {
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		
		if(StringUtil.isBlank(appKey)){
			this.jsonResult.setMessage("appKey值不能为空");
			return JSON;
		}
		
		if(StringUtil.isBlank(deviceToken)){
			this.jsonResult.setMessage("deviceToken值不能为空");
			return JSON;
		}
		IosDeviceToken iosDeviceToken = new IosDeviceToken();
		iosDeviceToken.setAppKey(appKey);
		iosDeviceToken.setDeviceToken(deviceToken);
		IosDeviceToken dbID = iosDeviceTokenService.queryIosDeviceTokenOne(iosDeviceToken);
		if(dbID!=null){
			//aap的deviceToken值已存在
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}else{
			//添加该app的deviceToken值
			iosDeviceTokenService.addIosDeviceToken(iosDeviceToken);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		System.out.println("appKey:"+appKey+" deviceToken:"+deviceToken);
		return JSON;
	}
	
	/**
	 * 进入app时对app图标右上角的数字减1
	 * @return
	 */
	public String cut(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		
		if(StringUtil.isBlank(appKey)){
			this.jsonResult.setMessage("appKey值不能为空");
			return JSON;
		}
		
		if(StringUtil.isBlank(deviceToken)){
			this.jsonResult.setMessage("deviceToken值不能为空");
			return JSON;
		}
		
		IosDeviceToken iosDeviceToken = new IosDeviceToken();
		iosDeviceToken.setAppKey(appKey);
		iosDeviceToken.setDeviceToken(deviceToken);
		
		IosDeviceToken dbID = iosDeviceTokenService.queryIosDeviceTokenOne(iosDeviceToken);
		if(dbID!=null){
			dbID.setBadge(0);//badge设置为0
			iosDeviceTokenService.updaupdateIosDeviceToken(dbID);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}else{
			log.error("DeviceTokenAction---：cut()--：queryIosDeviceTokenOne：查询失败");
		}
		return JSON;
	}

	private String deviceToken;
	private String appKey;
	private Integer badge;
	private Long superId;
	public String getDeviceToken() {
		return deviceToken;
	}
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public Integer getBadge() {
		return badge;
	}
	public void setBadge(Integer badge) {
		this.badge = badge;
	}

	public Long getSuperId() {
		return superId;
	}

	public void setSuperId(Long superId) {
		this.superId = superId;
	}
	
	
	
}
