package cn.magme.web.action.newPublisher;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant;
import cn.magme.constants.WebConstant;
import cn.magme.pojo.AdUser;
import cn.magme.pojo.Admin;
import cn.magme.pojo.Publisher;
import cn.magme.service.AdminService;
import cn.magme.service.PublisherService;

import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
@Results({@Result(name="login",location="/WEB-INF/pages/newPublisher/login.ftl")})
public class LoginAction extends PublisherBaseAction {
	
	@Resource
	private PublisherService publisherService;
	@Resource
	private AdminService adminService;

	public String to(){
		return "login";
	}
	
	public String doJson(){
		//检测admin用户
		Admin admin = adminService.getByName(userName);
		if(admin!=null){//编辑用户
			this.jsonResult = new JsonResult();
			if(admin.getPassword().equals(password)){
				//密码正确
				Map<String,Object> session= ActionContext.getContext().getSession();
				 session.put(WebConstant.SESSION.ADMIN, admin);
				 AdUser adUser = new AdUser();
				 adUser.setId(admin.getId());
                 adUser.setName(admin.getUserName());
        		 adUser.setLevel(PojoConstant.ADUSER.LEVEL_ADMAGME); //特殊的出版商  实际为编辑用户
        		 session.put(WebConstant.SESSION.ADUSER, adUser);
        		 session.remove(WebConstant.SESSION.ADAGENCY);
            	 session.remove(WebConstant.SESSION.ADMAGEME);
            	 session.remove(WebConstant.SESSION.USER);
            	 session.remove(WebConstant.SESSION.PUBLISHER);
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
				jsonResult.put("role", "admin");
			}else{
				//密码错误
				this.jsonResult.setCode(JsonResult.CODE.FAILURE);
				this.jsonResult.setMessage("用户名或密码错误");
				this.jsonResult.put("userName", "用户名或密码错误");
				jsonResult.put("role", "admin");
			}
		}else{//出版商检测
			this.jsonResult=publisherService.login(userName,password); 
			//登陆成功
			if(jsonResult.getCode()==JsonResult.CODE.SUCCESS){
				//将登陆用户写入SESSION
				Publisher publisher = (Publisher) this.jsonResult.getData().get("publisher");
				Map<String,Object> session= ActionContext.getContext().getSession();
				session.put(WebConstant.SESSION.PUBLISHER, publisher);
				 AdUser adUser = new AdUser();
				 adUser.setId(publisher.getId());
                 adUser.setName(publisher.getPublishName());
                 adUser.setLogo(publisher.getLogo());
                 adUser.setLevel(PojoConstant.ADUSER.LEVEL_PUBLISHER); 
                 session.put(WebConstant.SESSION.ADUSER, adUser);
                 session.remove(WebConstant.SESSION.ADAGENCY);
            	 session.remove(WebConstant.SESSION.ADMAGEME);
            	 session.remove(WebConstant.SESSION.USER);
            	 session.remove(WebConstant.SESSION.ADMIN);
				jsonResult.put("role", "publisher");
			}
		}
		return JSON;
	}
	
	public String exit(){
		ActionContext.getContext().getSession().remove(WebConstant.SESSION.PUBLISHER);
		ActionContext.getContext().getSession().remove(WebConstant.SESSION.ADMIN);
		ActionContext.getContext().getSession().remove(WebConstant.SESSION.ADUSER);
		return "new_publisher_login";
	}
	
	private String userName;
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
