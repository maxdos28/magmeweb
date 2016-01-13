package cn.magme.web.action;

import javax.annotation.Resource;

import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant;
import cn.magme.constants.PojoConstant.IOSBUYORDER.SERVICE_TYPE;
import cn.magme.constants.WebConstant;
import cn.magme.pojo.IosBuyUser;
import cn.magme.pojo.charge.UserDeviceBind;
import cn.magme.service.IosAppNorthAmericaReceiptService;
import cn.magme.service.IosBuyOrderService;
import cn.magme.service.IosBuyUserService;
import cn.magme.util.NumberUtil;
import cn.magme.util.StringUtil;

import com.opensymphony.xwork2.ActionContext;

/**
 * 
 * @author fredy
 * @since 2012-11-22
 */
public class NorthAmericaAppAction extends BaseAction {
	@Resource
	private IosBuyUserService iosBuyUserService;
	@Resource
	private IosBuyOrderService iosBuyOrderService;
	
	@Resource
	private IosAppNorthAmericaReceiptService iosAppNorthAmericaReceiptService;
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1407778233189302002L;

	/**
	 * 删除之前的购买记录
	 * @return
	 */
	public String delPurchaseJson(){
		this.jsonResult=JsonResult.getFailure();
		Long appId=this.querySessionAppId();
		if(NumberUtil.isLessThan0(appId)){
			this.jsonResult.setMessage("appId必须大于0");
			return JSON;
		}
		String deviceToken=this.getDeviceTokenFromSession();
		if(StringUtil.isBlank(deviceToken)){
			this.jsonResult.setMessage("必须先登录");
			return JSON;
		}
		//appId=890L;
		//deviceToken="8ef360fcca01b85447812d8f56e7e477";
		
		IosBuyUser iosUser=iosBuyUserService.queryByDeviceTokenAndAppId(appId, deviceToken);
		this.jsonResult=this.iosAppNorthAmericaReceiptService.delByAppIdAndDeviceToken(deviceToken, appId);
		if(this.jsonResult.getCode()==JsonResult.CODE.SUCCESS){
			this.jsonResult=this.iosBuyUserService.delByDeviceTokenAndAppId(deviceToken, appId);
		}
		if(this.jsonResult.getCode()==JsonResult.CODE.SUCCESS && iosUser!=null){
			this.jsonResult=this.iosBuyOrderService.delByBuyUserId(iosUser.getId());
		}
		return JSON;
	}
	public String registerJson(){
		this.jsonResult=JsonResult.getFailure();
		Long appId=this.querySessionAppId();
		if(NumberUtil.isLessThan0(appId)){
			this.jsonResult.setMessage("appId必须大于0");
			return JSON;
		}
		String deviceToken=this.getDeviceTokenFromSession();
		if(StringUtil.isBlank(deviceToken)){
			this.jsonResult.setMessage("必须先登录");
			return JSON;
		}
		ActionContext.getContext().getSession().put(WebConstant.SESSION.SESSION_APP_ID, appId);
		IosBuyUser iosBuyUser=new IosBuyUser();
		iosBuyUser.setAppId(appId);
		iosBuyUser.setDeviceToken(deviceToken);
		iosBuyUser.setStatus(PojoConstant.IOSBUYUSER.STATUS_OK);
		this.jsonResult=this.iosBuyUserService.addUser(iosBuyUser);
		//成功写回账单信息
		if(this.jsonResult.getCode()==JsonResult.CODE.SUCCESS){
			this.jsonResult=iosAppNorthAmericaReceiptService.querReceiptFromApple(deviceToken,appId);
		}
		return JSON;
	}
	
	
	
	
	public String subscribeJson(){
		this.jsonResult=JsonResult.getFailure();
		String deviceToken=this.getDeviceTokenFromSession();
		Long appId=this.querySessionAppId();
		if(NumberUtil.isLessThan0(serviceType) || NumberUtil.isLessThan0(appId) || StringUtil.isBlank(deviceToken)){
			this.jsonResult.setMessage("appId必须大于0，deviceToken不能为空,serviceType必须为1,6,12");
			return JSON;
		}
		IosBuyUser iosBuyUser=new IosBuyUser();
		iosBuyUser.setAppId(appId);
		iosBuyUser.setDeviceToken(this.getDeviceTokenFromSession());
		iosBuyUser.setStatus(PojoConstant.IOSBUYUSER.STATUS_OK);
		SERVICE_TYPE serviceTypeEnum=null;
		if(serviceType==PojoConstant.IOSBUYORDER.SERVICE_TYPE.SERVICE_TYPE_1_MONTH.getMonth()){
			serviceTypeEnum=PojoConstant.IOSBUYORDER.SERVICE_TYPE.SERVICE_TYPE_1_MONTH;
		}else if(serviceType==PojoConstant.IOSBUYORDER.SERVICE_TYPE.SERVICE_TYPE_6_MONTH.getMonth()){
			serviceTypeEnum=PojoConstant.IOSBUYORDER.SERVICE_TYPE.SERVICE_TYPE_6_MONTH;
		}else if(serviceType==PojoConstant.IOSBUYORDER.SERVICE_TYPE.SERVICE_TYPE_12_MONTH.getMonth()){
			serviceTypeEnum=PojoConstant.IOSBUYORDER.SERVICE_TYPE.SERVICE_TYPE_12_MONTH;
		}else{
			this.jsonResult.setMessage("serviceType必须为1,6,12");
			return JSON;
		}
		this.jsonResult=this.iosBuyOrderService.subscribe(iosBuyUser, serviceTypeEnum);
		return JSON;
	}
	
	/**
	 * 第一次注册账单
	 * @return
	 */
	public String registerReceiptJson(){
		this.jsonResult=JsonResult.getFailure();
		String deviceToken=this.getDeviceTokenFromSession();
		Long appId=this.querySessionAppId();
		if(appId==null || appId<=0){
			appId=this.querySessionAppId();
		}
		if(NumberUtil.isLessThan0(appId)){
			this.jsonResult.setMessage("appId必须大于0");
			return JSON;
		}
		if(StringUtil.isBlank(receiptKey) || StringUtil.isBlank(deviceToken)){
			this.jsonResult.setMessage("receiptKey和deviceToken不能为空");
			return JSON;
		}
		this.jsonResult=iosAppNorthAmericaReceiptService.addFisrtReceipt(deviceToken, appId, receiptKey);
		return JSON;
	}
	
	
	
	/**
	 * 从session中查询deviceToken
	 * @return
	 */
	private String getDeviceTokenFromSession(){
		UserDeviceBind userDeviceBind=(UserDeviceBind)ActionContext.getContext().getSession().get(WebConstant.SESSION.USER_DEVICE_BIND);
		if(userDeviceBind==null || StringUtil.isBlank(userDeviceBind.getMac())){
			return null;
		}
		return userDeviceBind.getMac();
	}
	
	private Long querySessionAppId(){
		Long appId=(Long)ActionContext.getContext().getSession().get(WebConstant.SESSION.SESSION_APP_ID);
		return appId;
	}
	
	private Integer serviceType;
	
	private String receiptKey;
	
	
	
	public String getReceiptKey() {
		return receiptKey;
	}




	public void setReceiptKey(String receiptKey) {
		this.receiptKey = receiptKey;
	}


	public Integer getServiceType() {
		return serviceType;
	}

	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}


}
