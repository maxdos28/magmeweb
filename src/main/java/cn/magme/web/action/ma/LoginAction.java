package cn.magme.web.action.ma;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.jsoup.helper.StringUtil;

import cn.magme.common.JsonResult;
import cn.magme.constants.WebConstant;
import cn.magme.service.ma.MaLoginService;
import cn.magme.web.action.BaseAction;

import com.opensymphony.xwork2.ActionContext;

/**
 * MA登录
 * @author jasper
 * @date 2014.3.20
 *
 */
@Results({@Result(name="success",location="/WEB-INF/pages/ma/login.ftl")})
public class LoginAction extends BaseAction {
	
	private static final long serialVersionUID = 2448272383871579484L;
	private static final Logger log=Logger.getLogger(LoginAction.class);
	
	@Resource
	private MaLoginService maLoginService;
	
	public String execute(){
		return SUCCESS;
	}
	
	
	/**
	 * 登陆
	 * @return
	 */
	public String loginJson(){
		this.jsonResult=JsonResult.getFailure();
		if( StringUtil.isBlank(userName) || StringUtil.isBlank(password)){
			this.jsonResult.setMessage("用户名，密码不能为空");
			return JSON;
		}
		this.userName=this.userName.trim();
		this.password=this.password.trim();
		this.jsonResult=maLoginService.login(userName,password);
		if(this.jsonResult.getCode()==JsonResult.CODE.SUCCESS){
			ActionContext.getContext().getSession().put(WebConstant.SESSION.MA_USER, this.jsonResult.getData().get("user"));
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
		ActionContext.getContext().getSession().remove(WebConstant.SESSION.MA_USER);
		return SUCCESS;
	}
	
	/**
	 * 登出
	 * @return
	 */
	public String logoutJson(){
		this.jsonResult=JsonResult.getSuccess();
		ActionContext.getContext().getSession().remove(WebConstant.SESSION.MA_USER);
		return JSON;
	}	
	
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 密码
	 */
	private String password;


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
