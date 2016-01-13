/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.common.PageBar;
import cn.magme.common.SystemProp;
import cn.magme.common.ThirdConfig;
import cn.magme.constants.PojoConstant;
import cn.magme.constants.WebConstant;
import cn.magme.pojo.ActionLog;
import cn.magme.pojo.AdUser;
import cn.magme.pojo.Admin;
import cn.magme.pojo.NewsManageUser;
import cn.magme.pojo.Publisher;
import cn.magme.pojo.User;
import cn.magme.pojo.charge.UserDeviceBind;
import cn.magme.pojo.phoenix.PhoenixUser;
import cn.magme.pojo.sms.SmsUser;
import cn.magme.util.IpUtil;
import cn.magme.util.StringUtil;
import cn.magme.util.qqip.IpSeeker;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author jacky_zhou
 * @date 2011-5-19
 * @version $id$
 */
@Results({ @Result(name = "json", type = "json", params = { "root", "jsonResult" }) })
@SuppressWarnings("serial")
public abstract class BaseAction extends ActionSupport {
    /**
     * JSON请求的返回值
     */
    public final static String JSON = "json";

    /**
     * 系统配置
     */
    protected SystemProp systemProp;

    /**
     * 第三方配置
     */
    protected ThirdConfig thirdConfig;

    /**
     * JSON请求返回的JSON数据
     */
    protected JsonResult jsonResult;
    private Integer editor;

    public JsonResult getJsonResult() {
        return jsonResult;
    }

    public void setJsonResult(JsonResult jsonResult) {
        this.jsonResult = jsonResult;
    }

    public JsonResult generateJsonResult(int code, String message, String key, Object value) {
        this.jsonResult = new JsonResult();
        this.jsonResult.setCode(code);
        this.jsonResult.setMessage(message);
        this.jsonResult.put(key, value);
        return this.jsonResult;
    }

    public JsonResult generateJsonResult(String key, Object value) {
        this.jsonResult = new JsonResult();
        this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
        this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
        this.jsonResult.put(key, value);
        return this.jsonResult;
    }

    public JsonResult generateJsonResult(int code, String message) {
        this.jsonResult = new JsonResult();
        this.jsonResult.setCode(code);
        this.jsonResult.setMessage(message);
        return this.jsonResult;
    }

    /**
     * 获取当前登陆的用户
     * 
     * @return
     */
    public User getSessionUser() {
        return (User) ActionContext.getContext().getSession().get(WebConstant.SESSION.USER);
    }
    
    /**
     * 获取当前登陆的凤凰周刊用户
     * 
     * @return
     */
    public PhoenixUser getSessionPhoenixUser() {
        return (PhoenixUser) ActionContext.getContext().getSession().get(WebConstant.SESSION.PHOENIX_USER);
    }
    
    /**
     * 获取当前短信平台用户
     * @return
     */
    public SmsUser getSessionSmsUser() {
        return (SmsUser) ActionContext.getContext().getSession().get(WebConstant.SESSION.SMS_USER);
    }
    
    /**
     * 获取当前移动用户设备mac地址
     * 
     * @return
     */
    public UserDeviceBind getSessionUserDeviceBind() {
    	return (UserDeviceBind) ActionContext.getContext().getSession().get(WebConstant.SESSION.USER_DEVICE_BIND);
    }

    /**
     * 获取当前登录的publisher
     * 
     * @return
     */
    public Publisher getSessionPublisher() {
        return (Publisher) ActionContext.getContext().getSession().get(WebConstant.SESSION.PUBLISHER);
    }
    
    /**
     * 获取当前登录的admin
     * @return
     */
    public Admin getSessionAdmin(){
    	return (Admin) ActionContext.getContext().getSession().get(WebConstant.SESSION.ADMIN);
    }
    
    /**
     * golf新闻后台管理员
     * @return
     */
    public NewsManageUser getSessionGolfUser(){
    	return (NewsManageUser) ActionContext.getContext().getSession().get(WebConstant.SESSION.USER_GOLF);
    }

    /**
     * 获取当前登录的aduser
     * @return
     */
    public AdUser getSessionAdUser() {
        return (AdUser) ActionContext.getContext().getSession().get(WebConstant.SESSION.ADUSER);
    }
    /**
     * 获取当前登陆的出版商id
     * 
     * @return
     */
    public Long getSessionPublisherId() {
        Publisher publisher = this.getSessionPublisher();
        if (publisher == null) {
            return null;
        }
        return publisher.getId();
    }
    
    /**
     * 
     * 获取当前登陆的后台用户id
     * @return
     */
    public Long getSessionAdUserId() {
    	AdUser adUser = (AdUser)ActionContext.getContext().getSession().get(WebConstant.SESSION.ADUSER);
        if (adUser == null) {
            return null;
        }
        return adUser.getId();
    }

    /**
     * 获取当前登陆的用户id
     * 
     * @return
     */
    public Long getSessionUserId() {
        User user = this.getSessionUser();
        if (user == null)
            return null;
        return user.getId();
    }
    
    public String getCookieByName(String cookieName){
    	if(StringUtil.isBlank(cookieName)){
    		return null;
    	}
    	HttpServletRequest req=(HttpServletRequest)ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
    	Cookie[] cookies=req.getCookies();
    	if(cookies!=null && cookies.length>0){
    		for(Cookie cookie:cookies){
    			if(cookie.getName().equals(cookieName)){
    				return cookie.getValue();
    			}
    		}
    	}
    	return null;
    }
    
    public String getSessionJsessionid(){
    	return this.getCookieByName("JSESSIONID");
    }

    private static final String IP_HEADER_DEFAULT = "X-Forwarded-For";
    private static final String IP_HEADER_BALANCE = "CLIENT_IP";

    /**
     * 获取IP地址
     * 
     * @return
     */
    public String getRequestIp() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String ip = request.getHeader(IP_HEADER_DEFAULT);

        if ("".equals(ip)) {
            ip = null;
        }

        if (ip != null) {
            int commaIndex = ip.indexOf(",");
            if (commaIndex > 0) {
                ip = ip.substring(0, commaIndex);
            }

            if (ip.startsWith("10.")) {
                ip = null;
            }
        }

        if (ip == null) {
            ip = request.getHeader(IP_HEADER_BALANCE);
        }

        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取编码成Long类型的IP地址
     * 
     * @return
     */
    public Long getRequestIpLong() {
        return IpUtil.ip2Long(this.getRequestIp());
    }

    /**
     * 根据IP地址获取物理位置
     * 
     * @return
     */
    public String getRequestLocation() {
        return IpSeeker.getCountry(this.getRequestIp());
    }

    /**
     * 根据IP地址获取所处省份
     * 
     * @return
     */
    public String getRequestProvince() {
        return IpSeeker.getProvince(this.getRequestIp());
    }

    /**
     * 根据IP地址获取所处城市
     * 
     * @return
     */
    public String getRequestCity() {
        return IpSeeker.getCity(this.getRequestIp());
    }

    public SystemProp getSystemProp() {
        return systemProp;
    }

    public void setSystemProp(SystemProp systemProp) {
        this.systemProp = systemProp;
    }

    public ThirdConfig getThirdConfig() {
        return thirdConfig;
    }

    public void setThirdConfig(ThirdConfig thirdConfig) {
        this.thirdConfig = thirdConfig;
    }

    /**
     * 生成操作日志对象
     * 
     * @return
     */
    public ActionLog generateActionLog(Integer actionTypeId, Long actionId) {
        ActionLog actionLog = new ActionLog();
        actionLog.setIpAddress(this.getRequestIpLong());
        actionLog.setUserId(this.getSessionUserId());
        actionLog.setUserTypeId(PojoConstant.ACTIONLOG.USERTYPEID_USER);
        actionLog.setActionTypeId(actionTypeId);
        actionLog.setActionId(actionId);
        return actionLog;
    }

    //一级菜单
    protected String firstMenu;
    //二级菜单
    protected String secondMenu;

    public String getFirstMenu() {
        return firstMenu;
    }

    public void setFirstMenu(String firstMenu) {
        this.firstMenu = firstMenu;
    }

    public String getSecondMenu() {
        return secondMenu;
    }

    public void setSecondMenu(String secondMenu) {
        this.secondMenu = secondMenu;
    }
    
    /**
     * 分页条
     */
    protected PageBar pageBar;
    /**
     * 当前页数
     */
    protected Integer pageNum;
    /**
     * 每页加载多少条数据
     */
    protected Integer pageSize;

    public PageBar getPageBar() {
        return pageBar;
    }

    public void setPageBar(PageBar pageBar) {
        this.pageBar = pageBar;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    
    /**
     * 动态栏目
     */
    protected String dynamicMenu;

	public String getDynamicMenu() {
		return dynamicMenu;
	}

	public void setDynamicMenu(String dynamicMenu) {
		this.dynamicMenu = dynamicMenu;
	}
	/**
	 * convert string array to long array
	 * 
	 * @param strArr
	 * @return
	 */
	protected Long[] strArrToLongArr(String[] strArr) {
		Long[] idArr = new Long[strArr.length];
		for (int i = 0; i < strArr.length; i++) {
			idArr[i] = Long.valueOf(strArr[i]);
		}
		return idArr;
	}

	/**
	 * @param editor the editor to set
	 */
	public void setEditor(Integer editor) {
		this.editor = editor;
	}

	/**
	 * @return the editor
	 */
	public Integer getEditor() {
		return editor;
	}
}
