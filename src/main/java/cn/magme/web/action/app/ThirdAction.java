package cn.magme.web.action.app;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;

import cn.magme.common.JsonResult;
import cn.magme.common.QQConfig;
import cn.magme.common.SystemProp;
import cn.magme.common.ThirdConfig;
import cn.magme.constants.PojoConstant;
import cn.magme.constants.WebConstant;
import cn.magme.pojo.User;
import cn.magme.service.OAuthService;
import cn.magme.service.UserService;
import cn.magme.util.StringUtil;
import cn.magme.util.oauth.ConnectUtils;
import cn.magme.web.action.BaseAction;

public class ThirdAction extends BaseAction{
	
	@Resource
    private ThirdConfig thirdConfig;	
	@Resource
    private UserService userService; 
	@Resource
	private QQConfig qqConfig;
	@Resource
	private OAuthService oAuthService;
	@Resource
	private SystemProp systemProp;
	
    private String code;

	private String id;

    private String name;

    private String imageUrl;

    private String gender;
    
    private String address;

    private String flag = "false";
    
    public String sina() {
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		
    	if (StringUtil.isBlank(id) || StringUtil.isBlank(name)) {
            //缺乏有效参数，跳转到登录页去
            return JSON;
        }

        String userName = id + "@weibo.com";
        User user = this.userService.getUserByName(userName);
        if (user == null || user.getId() == null) {
            flag = "true";
            user = new User();
            user.setUserName(userName);
            user.setNickName(name);
            user.setType(PojoConstant.USER.USER_TYPE_WB);
            user.setEmail(userName);
            user.setPassword("");
            user.setGender(gender.equals("m") ? 1 : 2);
            user.setAddress(address);
            user.setStatus(PojoConstant.USER.STATUS_ACTIVE);
            //user.setReserve1(accessToken); //临时字段1,用来暂存access_token //每个人人账号唯一
            //添加用户
            user = this.userService.addUser(user, imageUrl);
        }
        this.userService.updateUserLastLoginTime(user.getId());

        //查询用户的统计信息
        if (user != null) {
            user.setStatsMap(this.userService.getUserStatsMapByUserId(user.getId()));
            userService.setUserEnjoyList(user);
        }
        //写入当前session
        ServletActionContext.getRequest().getSession().setAttribute(WebConstant.SESSION.USER, user);
        
        this.jsonResult.put("user", user);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
        //已登录，跳转到个人主页
    	return JSON;
    }

    public String userstr;
    
    public String openid;

	public String accesstoken;

	public String qq(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		
		try {    //将user信息写入数据库
			Map<String,Object> userInfo = ConnectUtils.parseXmlToMap(userstr);
			//获取用户名 检查用户是否已经存在
			String  userName = openid + "@qq.com";
			User user = null;
			user = this.userService.getUserByName(userName);			
			boolean flg = true;
			if(user == null || user.getId() == null){
				user = new User();
				user.setUserName(openid + "@qq.com");
				user.setNickName((String) userInfo.get("nickname"));
				String url = (String) userInfo.get("figureurl_2");
				user.setType(PojoConstant.USER.USER_TYPE_QQ);
				user.setEmail(openid + "@qq.com");
				user.setPassword("");
				user.setStatus(PojoConstant.USER.STATUS_ACTIVE);
				user.setReserve1(accesstoken); //临时字段1,用来暂存第五步返回的access_token //每个QQ号码唯一
				//添加用户
				user = this.userService.addUser(user,url);
			} else {
			    flg = false;
			}
			this.userService.updateUserLastLoginTime(user.getId());
			//查询用户的统计信息
			if(user!=null){
			    user.setStatsMap(this.userService.getUserStatsMapByUserId(user.getId()));
			    userService.setUserEnjoyList(user);
			}
			//写入当前session
			ServletActionContext.getRequest().getSession().setAttribute(WebConstant.SESSION.USER, user);
			this.jsonResult.put("user", user);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			
		} catch (Exception e) {
			//获取用户信息错误
			e.printStackTrace();
		}
		return JSON;
    }
    
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
    
    public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
    
    public String getUserstr() {
		return userstr;
	}

	public void setUserstr(String userstr) {
		this.userstr = userstr;
	}

	public String getAccesstoken() {
		return accesstoken;
	}

	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}
}
