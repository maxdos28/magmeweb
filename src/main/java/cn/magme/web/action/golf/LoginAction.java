package cn.magme.web.action.golf;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionContext;

import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant;
import cn.magme.constants.WebConstant;
import cn.magme.pojo.AdUser;
import cn.magme.pojo.NewsManageUser;
import cn.magme.pojo.Publisher;
import cn.magme.service.NewsMagageUserService;
import cn.magme.service.PublisherService;
import cn.magme.web.action.BaseAction;

@SuppressWarnings("serial")
@Results({@Result(name="login",location="/WEB-INF/pages/golf/login.ftl")})

public class LoginAction extends BaseAction {
	@Resource
	private NewsMagageUserService newsMagageUserService;
	
	public String to(){
		return "login";
	}
	
	public String doJson(){
		//检测新闻后台管理员
			NewsManageUser newsUser = new NewsManageUser();
			newsUser.setUserName(userName);
			newsUser.setPassword(password);
			boolean loginFlag = newsMagageUserService.login(newsUser,ActionContext.getContext()); 
			this.jsonResult = JsonResult.getFailure();
			//登陆成功
			if(loginFlag){
				//将登陆用户写入SESSION
				NewsManageUser newsUser_db = newsMagageUserService.selectByName(userName);
				if(newsUser_db.getType()!=null && newsUser_db.getType()==1){//Golf用户
					Map<String,Object> session= ActionContext.getContext().getSession();
					session.put(WebConstant.SESSION.USER_GOLF, newsUser_db);
	                 session.remove(WebConstant.SESSION.PUBLISHER);
	            	 session.remove(WebConstant.SESSION.USER);
	            	 session.remove(WebConstant.SESSION.ADMIN);
					jsonResult.put("role", "manage_news");
					jsonResult.setCode(JsonResult.CODE.SUCCESS);
					jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
				}
			}
		return JSON;
	}
	
	public String exit(){
		ActionContext.getContext().getSession().remove(WebConstant.SESSION.PUBLISHER);
		ActionContext.getContext().getSession().remove(WebConstant.SESSION.ADMIN);
		ActionContext.getContext().getSession().remove(WebConstant.SESSION.USER_GOLF);
		return "golf_login";
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
