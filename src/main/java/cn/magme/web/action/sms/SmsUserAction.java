package cn.magme.web.action.sms;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.constants.WebConstant;
import cn.magme.pojo.sms.SmsUser;
import cn.magme.service.sms.SmsUserService;
import cn.magme.util.Md5Util;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

import com.opensymphony.xwork2.ActionContext;
/**
 * 
 * @author fredy
 * @since 2013-3-18
 */
@Results({@Result(name="success",location="/WEB-INF/pages/sms/smsUserLogin.ftl"),
	@Result(name="edit_success",location="/WEB-INF/pages/sms/smsUserEdit.ftl")})
public class SmsUserAction extends BaseAction {
	
	private static final Logger log=Logger.getLogger(SmsUserAction.class);
	
	private static final long serialVersionUID = 4077098283454301954L;
	@Resource
	private SmsUserService smsUserService;
	
	public String execute(){
		//smsUser=this.getSessionSmsUser();
		return SUCCESS;
	}
	
	public String edit(){
		smsUser=this.getSessionSmsUser();
		return "edit_success";
	}
	
	public String loginJson(){
		this.jsonResult=JsonResult.getFailure();
		try {
			this.jsonResult=this.smsUserService.login(userName, password);
			if(this.jsonResult.getCode()==JsonResult.CODE.SUCCESS && this.jsonResult.get("smsUser")!=null){
				ActionContext.getContext().getSession().put(WebConstant.SESSION.SMS_USER, this.jsonResult.get("smsUser"));
			}
		} catch (Exception e) {
			log.error("",e);
		}
		return JSON;
	}
	
	public String logoutJson(){
		this.jsonResult=JsonResult.getSuccess();
		ActionContext.getContext().getSession().remove(WebConstant.SESSION.SMS_USER);
		return JSON;
	}
	
	public String updJson(){
		this.jsonResult=JsonResult.getFailure();
		SmsUser u=this.getSessionSmsUser();
		
		if( (StringUtil.isBlank(password) && StringUtil.isNotBlank(password2)) 
				|| (StringUtil.isNotBlank(password) && StringUtil.isBlank(password2)) ){
			this.jsonResult.setMessage("登录密码和确认密码不一致");
			return JSON;
		}
		
		
		if(StringUtil.isNotBlank(password) && StringUtil.isNotBlank(password2) && !password.equals(password2)){
			this.jsonResult.setMessage("登录密码和确认密码不一致");
			return JSON;
		}else if(StringUtil.isNotBlank(password) && StringUtil.isNotBlank(password2) && password.equals(password2)){
			u.setPassword(Md5Util.MD5Encode(password));
		}
		
		//修改密码
		/*if(StringUtil.isNotBlank(password) && StringUtil.isNotBlank(password2)){
			if(!u.getPassword().endsWith(Md5Util.MD5Encode(password))){
				this.jsonResult.setMessage("登陆密码错误，不能修改信息");
				return JSON;
			}
			u.setPassword(Md5Util.MD5Encode(password2));
		}*/
		u.setNickName(nickName);
		
		if(this.smsUserService.updateByPrimaryKey(u)>0){
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}
	
	
	
	private String userName;
	
	private String password;
	
	private String password2;
	
	private String nickName;

    private Long balance;

    private Integer status;
    
    private Integer type;

    private String weixin;

    private String qq;

    private String tel;

    private String weibo;
    
    private SmsUser smsUser;
    
    

	public SmsUser getSmsUser() {
		return smsUser;
	}


	public void setSmsUser(SmsUser smsUser) {
		this.smsUser = smsUser;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
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


	public String getNickName() {
		return nickName;
	}


	public void setNickName(String nickName) {
		this.nickName = nickName;
	}




	public Long getBalance() {
		return balance;
	}


	public void setBalance(Long balance) {
		this.balance = balance;
	}



	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public String getWeixin() {
		return weixin;
	}


	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}


	public String getQq() {
		return qq;
	}


	public void setQq(String qq) {
		this.qq = qq;
	}


	public String getTel() {
		return tel;
	}


	public void setTel(String tel) {
		this.tel = tel;
	}


	public String getWeibo() {
		return weibo;
	}


	public void setWeibo(String weibo) {
		this.weibo = weibo;
	}
	
	
	
	
	
	

}
