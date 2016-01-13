package cn.magme.web.action.app;

import java.util.List;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ActionContext;

import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant;
import cn.magme.constants.WebConstant;
import cn.magme.pojo.ActionLog;
import cn.magme.pojo.FeedBack;
import cn.magme.pojo.User;
import cn.magme.pojo.charge.UserDeviceBind;
import cn.magme.service.ActionLogService;
import cn.magme.service.FeedBackService;
import cn.magme.service.UserService;
import cn.magme.service.charge.UserDeviceBindService;
import cn.magme.web.action.BaseAction;

/**
 * user class
 * @author xwan
 * @date 2012-2-8
 * @version $id$
 */
public class UsrAction extends BaseAction {

	private static final long serialVersionUID = -7786797322299151386L;
	private Long userId;
	private String userName;
    private String password;
    private String password2;
    private String email;
	private Long categoryId;
    private String content;
    private Integer type;
    private String mac;//移动设备mac地址或机器码
    private String os;//移动设备操作系统
    private String version;//移动设备操作系统版本
    @Resource
    private UserDeviceBindService userDeviceBindService;
    
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

	@Resource
    private UserService userService;
	@Resource
    private ActionLogService actionLogService;
	@Resource
    private FeedBackService feedBackService;
	
	public String login(){
		this.jsonResult=userService.login(userName,password);
        if(this.jsonResult.getCode()==JsonResult.CODE.SUCCESS){
            //校验通过,将登陆用户信息存放在SESSION
            ActionContext.getContext().getSession().put(WebConstant.SESSION.USER, this.jsonResult.getData().get("user"));
            //纪录用户的操作日志
            ActionLog actionLog=this.generateActionLog(
                    PojoConstant.ACTIONLOG.ACTIONTYPEID_LOGIN,
                    ((User)this.jsonResult.getData().get("user")).getId());
            actionLogService.insertActionLog(actionLog);                
        }
        return JSON;
	}
	
	public String autoLogin(){
		return this.login();
	}
	
    public String iosLogin(){
        // 非自动登录，区分网站与微博用户 
        // 网站用户或者golf用户
        if (type == PojoConstant.USER.USER_TYPE_NORMAL || type==PojoConstant.USER.USER_TYPE_GOLF) {
            this.login();
        // 微博用户
        }else if (type == PojoConstant.USER.USER_TYPE_WB){
            User user = userService.getUserByName(userName + "@weibo.com");
            if (user == null){
                User userWeibo = new User();
                userWeibo.setUserName(userName + "@weibo.com");
                userWeibo.setNickName("");
                userWeibo.setPassword("");
                userWeibo.setEmail(userName + "@weibo.com");
                userWeibo.setStatus(PojoConstant.USER.STATUS_ACTIVE);
                userWeibo.setType(PojoConstant.USER.USER_TYPE_WB);
                user = userService.addUser(userWeibo,"");
            }
            ActionContext.getContext().getSession().put(WebConstant.SESSION.USER, user);
            this.generateJsonResult("user",user);
        }else if (type == PojoConstant.USER.USER_TYPE_MAC){//mac地址登录
        	UserDeviceBind userDeviceBind = new UserDeviceBind(null, os, mac);
            ActionContext.getContext().getSession().put(WebConstant.SESSION.USER_DEVICE_BIND, userDeviceBind);
            ActionContext.getContext().getSession().put(WebConstant.SESSION.SESSION_APP_ID, appId);
            this.jsonResult = JsonResult.getSuccess();
            return JSON;
        }else{
            this.generateJsonResult(JsonResult.CODE.FAILURE, JsonResult.MESSAGE.FAILURE);
        }
        User user = getSessionUser();
    	if(user != null && mac != null){
    		userId = user.getId();
    		UserDeviceBind userDeviceBind = null;
    		List<UserDeviceBind> list = userDeviceBindService.getList(new UserDeviceBind(userId, os));
    		if(list != null && !list.isEmpty()){
    			for (UserDeviceBind bind : list) {
					if(mac.equals(bind.getMac())){
						userDeviceBind = bind;
						break;
					}
				}
    		}
    		if(userDeviceBind == null){
//    			int max = 5;//同一个os的设备最多登录5台
//    			if(list != null && list.size() >= max){//不允许登录
//    	            ActionContext.getContext().getSession().remove(WebConstant.SESSION.USER);
//    	            ActionContext.getContext().getSession().remove(WebConstant.SESSION.USER_DEVICE_BIND);
//    	            this.generateJsonResult(JsonResult.CODE.FAILURE, "同一个账户最多能绑定" + max + "台 " + os + " 设备");
//    	            return JSON;
//    			} else {
    				userDeviceBind = new UserDeviceBind(userId, os, mac);
    				userDeviceBind.setStatus(1);
    				userDeviceBind.setVersion(version);
    				userDeviceBindService.insert(userDeviceBind);
//    			}
    		}
            ActionContext.getContext().getSession().put(WebConstant.SESSION.USER_DEVICE_BIND, userDeviceBind);
    	}
        return JSON;
    }
	
	public String register(){
        User user=new User();
        user.setUserName(userName);
        user.setPassword(password);
        user.setPassword2(password2);
        user.setEmail(email);
        user.setType(type);
        this.jsonResult=userService.validateUser(user,false);
        if(this.jsonResult.getCode()==JsonResult.CODE.SUCCESS){
        	this.jsonResult=userService.register(user);
            //注册成功
            if(this.jsonResult.getCode()==JsonResult.CODE.SUCCESS){
                //发欢迎邮件
                //String text = mailTemplateService.getTemplateStr(PojoConstant.EMAILTEMPLATE.FILE_WELCOME_READER);
                //senderMail.sendMail(email, text, PojoConstant.EMAILTEMPLATE.SUBJECT_WELCOME_READER, 0);
                //新注册的用户视为已登陆,用户信息保存到SESSION
                ActionContext.getContext().getSession().put(WebConstant.SESSION.USER, this.jsonResult.getData().get("user"));
                //纪录用户的操作日志
                ActionLog actionLog=this.generateActionLog(
                        PojoConstant.ACTIONLOG.ACTIONTYPEID_REGISTER,
                        ((User)this.jsonResult.getData().get("user")).getId());
                actionLogService.insertActionLog(actionLog);
            }
        }
        return JSON;
	}
	
	public String logout(){
    	//米客登出
    	ActionContext.getContext().getSession().remove(WebConstant.SESSION.USER);
    	ActionContext.getContext().getSession().remove(WebConstant.SESSION.USER_DEVICE_BIND);
        
    	//米商登出
        //ActionContext.getContext().getSession().remove(WebConstant.SESSION.PUBLISHER);
        this.generateJsonResult(JsonResult.CODE.SUCCESS, JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}
	
	public String feedback(){
        FeedBack feedBack=new FeedBack();
        feedBack.setCategoryId(categoryId);
        feedBack.setContent(this.content);
        feedBack.setUserId(this.getSessionUserId());
        feedBack.setIpAddress(this.getRequestIpLong());
        feedBack.setProvince(this.getRequestProvince());
        feedBack.setCity(this.getRequestCity());
        feedBack.setAppId(appId);
        feedBack.setComment(contact);
        this.jsonResult=feedBackService.insertFeedBack(feedBack);
        return JSON;
	}


	private Long appId;
	private String contact;
	
	

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getMac() {
		return mac;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getVersion() {
		return version;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getOs() {
		return os;
	}
   
}
