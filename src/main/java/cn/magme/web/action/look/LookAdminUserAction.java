package cn.magme.web.action.look;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.jsoup.helper.StringUtil;

import cn.magme.common.JsonResult;
import cn.magme.constants.WebConstant;
import cn.magme.constants.phoenix.PhoenixConstants;
import cn.magme.pojo.phoenix.PhoenixUser;
import cn.magme.service.look.LookAdminUserService;
import cn.magme.util.Md5Util;
import cn.magme.web.action.BaseAction;

import com.opensymphony.xwork2.ActionContext;
/**
 * 
 * @author fredy
 * @since 2013-5-13
 */
@Results({@Result(name="success",location="/WEB-INF/pages/looker/admin/login.ftl")})
public class LookAdminUserAction extends BaseAction {
	
	private static final long serialVersionUID = 2448272383871579484L;
	private static final Logger log=Logger.getLogger(LookAdminUserAction.class);
	
	@Resource
	private LookAdminUserService lookAdminUserService;
	
	public String execute(){
		return SUCCESS;
	}
	
	
	/**
	 * 登陆
	 * @return
	 */
	public String loginJson(){
		this.jsonResult=JsonResult.getFailure();
		if(appId==null || appId<=0 || StringUtil.isBlank(userName) || StringUtil.isBlank(password)){
			this.jsonResult.setMessage("appId小于0或者用户名，密码能空");
			return JSON;
		}
		this.userName=this.userName.trim();
		this.password=this.password.trim();
		this.jsonResult=lookAdminUserService.login(appId, userName,password);
		if(this.jsonResult.getCode()==JsonResult.CODE.SUCCESS){
			ActionContext.getContext().getSession().put(WebConstant.SESSION.LOOK_USER, this.jsonResult.getData().get("user"));
		}
		log.info("用户:"+userName+" 登陆，时间"+new Date());
		return JSON;
	}
	
	/**
	 * 登出
	 * @return
	 */
	public String logout(){
		this.jsonResult=JsonResult.getSuccess();
		ActionContext.getContext().getSession().remove(WebConstant.SESSION.LOOK_USER);
		return SUCCESS;
	}
	
	/**
	 * 登出
	 * @return
	 */
	public String logoutJson(){
		this.jsonResult=JsonResult.getSuccess();
		ActionContext.getContext().getSession().remove(WebConstant.SESSION.LOOK_USER);
		return JSON;
	}
	
	/**
	 * 应用id
	 */
	private Long appId;
	
	
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 密码
	 */
	private String password;

	public Long getAppId() {
		return appId;
	}


	public void setAppId(Long appId) {
		this.appId = appId;
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
	

}
