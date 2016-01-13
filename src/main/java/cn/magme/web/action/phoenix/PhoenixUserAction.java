package cn.magme.web.action.phoenix;

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
import cn.magme.service.phoenix.PhoenixUserService;
import cn.magme.util.Md5Util;
import cn.magme.web.action.BaseAction;

import com.opensymphony.xwork2.ActionContext;
/**
 * 
 * @author fredy
 * @since 2013-5-13
 */
@Results({@Result(name="success",location="/WEB-INF/pages/phoenix/login.ftl"),
	@Result(name="special_success",location="/WEB-INF/pages/phoenix/specialAccount.ftl")})
public class PhoenixUserAction extends BaseAction {
	
	private static final long serialVersionUID = 2448272383871579484L;
	private static final Logger log=Logger.getLogger(PhoenixUserAction.class);
	
	@Resource
	private PhoenixUserService phoenixUserService;
	
	public String execute(){
		return SUCCESS;
	}
	
	
	/**
	 * 内部账号
	 * @return
	 */
	public String specialAccount(){
		userList=phoenixUserService.queryByAppIdUserNameTypeAndRegisterSource(this.getSessionPhoenixUser().getAppId(),
				null, PhoenixConstants.PHOENIX_USER.TYPE_SPECIAL_USER, null);
		return "special_success";
	}
	/**
	 * 删除
	 * @return
	 */
	public String delJson(){
		this.jsonResult=JsonResult.getFailure();
		if(this.phoenixUserService.deleteById(id)>0){
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
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
		this.jsonResult=phoenixUserService.login(appId, userName,password,type);
		if(this.jsonResult.getCode()==JsonResult.CODE.SUCCESS){
			ActionContext.getContext().getSession().put(WebConstant.SESSION.PHOENIX_USER, this.jsonResult.getData().get("user"));
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
		ActionContext.getContext().getSession().remove(WebConstant.SESSION.PHOENIX_USER);
		return SUCCESS;
	}
	
	/**
	 * 登出
	 * @return
	 */
	public String logoutJson(){
		this.jsonResult=JsonResult.getSuccess();
		ActionContext.getContext().getSession().remove(WebConstant.SESSION.PHOENIX_USER);
		return JSON;
	}
	/**
	 * 注册接口,并且具有给qq，weibo登陆回调记录信息的功能
	 * @return
	 */
	public String registerJson(){
		this.jsonResult=JsonResult.getFailure();
		if(appId==null || appId<=0 || StringUtil.isBlank(userName) ){
			this.jsonResult.setMessage("appId小于0或者用户名为空");
			return JSON;
		}
		if(type==null || type<=0 || (type!=PhoenixConstants.PHOENIX_USER.TYPE_FRONT_USER && type!=PhoenixConstants.PHOENIX_USER.TYPE_BACKEND_USER)){
			this.jsonResult.setMessage("type只能为1(前台用户)或者2(后台用户)");
			return JSON;
		}
		if(registerSource==null || registerSource<=0 ||
				( registerSource!=PhoenixConstants.PHOENIX_USER.REGISTER_SOURCE_MAGME 
						&& registerSource!=PhoenixConstants.PHOENIX_USER.REGISTER_SOURCE_QQ
						&& registerSource!=PhoenixConstants.PHOENIX_USER.REGISTER_SOURCE_WEIBO)){
			this.jsonResult.setMessage("registerSource只能为1(magme),2(qq)或者3(weibo)");
			return JSON;
		}
		/**
		 * magme注册必须要有密码
		 */
		if(registerSource==PhoenixConstants.PHOENIX_USER.REGISTER_SOURCE_MAGME){
			if(StringUtil.isBlank(password) || StringUtil.isBlank(password2) || !this.password.endsWith(this.password2)){
				this.jsonResult.setMessage("第一次输入密码和第二次输入密码不一致或为空");
				this.password=this.password.trim();
				this.password2=this.password2.trim();
				return JSON;
			}
		}
		List<PhoenixUser> registedUser=this.phoenixUserService.queryByAppIdUserNameTypeAndRegisterSource(appId, userName, type, registerSource);
		if(registedUser!=null && registedUser.size()>0){
			if(registerSource==PhoenixConstants.PHOENIX_USER.REGISTER_SOURCE_MAGME){
				this.jsonResult.setMessage("重复注册，这个用户名已经被注册了");
				return JSON;
			}else{
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
				this.jsonResult.put("user", registedUser);
				return JSON;
			}
		}
		
		this.userName=this.userName.trim();
		PhoenixUser u=new PhoenixUser();
		u.setAppId(appId);
		u.setAvatar(avatar);
		u.setEmail(email);
		if(StringUtils.isNotBlank(this.password)){
			u.setPassword(Md5Util.MD5Encode(password));
		}
		u.setStatus(PhoenixConstants.PHOENIX_USER.STATUS_OK);
		u.setType(type);
		u.setUserName(userName);
		u.setRegisterSource(registerSource);
		u.setNickName(nickName);
		Long id=this.phoenixUserService.insert(u);
		if(id!=null && id>0){
			u.setId(id);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			u.setPassword("");
			this.jsonResult.put("user", u);
		}
		return JSON;
	}
	/**
	 * 
	 * @return
	 */
	public String addOrEditJson(){
		this.jsonResult=JsonResult.getFailure();
		this.appId=this.getSessionPhoenixUser().getAppId();
		
		//新增
		if(id==null){
			if(StringUtils.isBlank(this.userName) || StringUtils.isBlank(this.password) 
					|| StringUtils.isBlank(this.password2) || !this.password.equalsIgnoreCase(this.password2)){
				this.jsonResult.setMessage("用户名，密码为空，或者两个密码不一致！");
				return JSON;
			}
			List<PhoenixUser> registedUser=this.phoenixUserService.queryByAppIdUserNameTypeAndRegisterSource(appId, userName, type, registerSource);
			if(registedUser!=null && registedUser.size()>0){
				this.jsonResult.setMessage("重复注册，这个用户名已经被注册了");
				return JSON;
			}
			this.userName=this.userName.trim();
			PhoenixUser u=new PhoenixUser();
			u.setAppId(appId);
			u.setAvatar(avatar);
			u.setEmail(email);
			u.setPassword(Md5Util.MD5Encode(password));
			u.setStatus(PhoenixConstants.PHOENIX_USER.STATUS_OK);
			u.setType(type);
			u.setUserName(userName);
			u.setRegisterSource(registerSource);
			u.setNickName(nickName);
			Long id=this.phoenixUserService.insert(u);
			if(id!=null && id>0){
				u.setId(id);
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
				u.setPassword("");
				this.jsonResult.put("user", u);
			}
		}else{
		   //修改
			if(StringUtils.isBlank(this.userName)){
				this.jsonResult.setMessage("用户名为空");
				return JSON;
			}
			PhoenixUser u=new PhoenixUser();
			if(id==null || id<=0){
				this.jsonResult.setMessage("id为空");
				return JSON;
			}
			if( StringUtils.isNotBlank(this.password) && StringUtils.isNotBlank(this.password2)  ){
				if(!this.password.equalsIgnoreCase(this.password2)){
					this.jsonResult.setMessage("两个密码不一致！");
					return JSON;
				}
				u.setPassword(Md5Util.MD5Encode(password));
			}
			u.setId(id);
			u.setStatus(PhoenixConstants.PHOENIX_USER.STATUS_OK);
			u.setType(type);
			u.setUserName(userName);
			u.setRegisterSource(registerSource);
			if(this.phoenixUserService.updateByIdSelective(u)>0){
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			}
		}
		return JSON;
	}
	
	private Long id;
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
	/**
	 * 重复输入的密码
	 */
	private String password2;
	/**
	 * 电子邮件
	 */
	private String email;
	/**
	 * 头像地址
	 */
	private String avatar;
	/**
	 * 用户类型 1 普通用户 2 后台操作用户
	 */
	private Integer type;
	/**
	 * 注册来源 1,magme 2 qq 3 weibo
	 */
	private Integer registerSource;
	/**
	 * 昵称
	 */
	private String nickName;
	
	private List<PhoenixUser> userList;
	
	

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public List<PhoenixUser> getUserList() {
		return userList;
	}


	public void setUserList(List<PhoenixUser> userList) {
		this.userList = userList;
	}


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
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getRegisterSource() {
		return registerSource;
	}
	public void setRegisterSource(Integer registerSource) {
		this.registerSource = registerSource;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	
	
	

	
	
	
	
	

}
