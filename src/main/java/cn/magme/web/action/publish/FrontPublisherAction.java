/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.publish;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.web.util.HtmlUtils;

import cn.magme.common.JsonResult;
import cn.magme.common.SenderMail;
import cn.magme.constants.PojoConstant;
import cn.magme.constants.WebConstant;
import cn.magme.pojo.AdAgency;
import cn.magme.pojo.AdMagme;
import cn.magme.pojo.AdUser;
import cn.magme.pojo.Publisher;
import cn.magme.service.AdAgencyService;
import cn.magme.service.AdMagmeService;
import cn.magme.service.MailTemplateService;
import cn.magme.service.MessageService;
import cn.magme.service.PublisherService;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

import com.opensymphony.xwork2.ActionContext;

/**
 * @author fredy.liu
 * @date 2011-5-25
 * @version $id$
 * 出版商功能
 */
@Results({@Result(name="success",location="/WEB-INF/pages/publish/publisherLogin.ftl")})
public class FrontPublisherAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7333701978132501742L;

	@Resource
	private PublisherService publisherService;
	
	@Resource
    private MailTemplateService mailTemplateService;
    
    @Resource
    private SenderMail senderMail;
    
    @Resource
    private AdAgencyService adAgencyService;
    
    @Resource
    private AdMagmeService adMagmeService;
    
    @Resource
    private MessageService messageService;
    
    private static final Logger log=Logger.getLogger(FrontPublisherAction.class);
    
    /**
     * 登录类型，出版商
     */
    private static final int PUBLISHER_LOGIN_TYPE=1;
    
    /**
     * 登录类型，广告商
     */
    private static final int ADAGENCY_LOGIN_TYPE=2;
    
    /**
     * 登录类型，麦米
     */
    private static final int ADMAGME_LOGIN_TYPE=3;
    
    
    
    /**
     * 校验输入信息
     * @return
     */
    public String validatePublisher(){
    	try {
			Publisher publisher=new Publisher();
			publisher.setUserName(userName);
			publisher.setPassword(password);
			publisher.setPassword2(password2);
			publisher.setEmail(email);
			this.jsonResult=publisherService.validatePublisher(publisher,false);
		} catch (Exception e) {
			log.error("", e);
			this.jsonResult.setCode(JsonResult.CODE.EXCEPTION);
			this.jsonResult.setMessage(JsonResult.MESSAGE.EXCEPTION);
		}
    	return JSON;
    }
    

    public String execute(){
    	return SUCCESS;
    }
    /**
     * 用户登陆
     * @return
     */
    public String loginJson(){
       String session_authcode = (String) (ActionContext.getContext().getSession().get(WebConstant.SESSION.PUBLISHER_AUTHCODE));
        if(StringUtil.isNotBlank(session_authcode)&&session_authcode.equals(this.authcode)){
         if(loginType==0 || loginType==PUBLISHER_LOGIN_TYPE){
        	 this.jsonResult=publisherService.login(userName,password); 
         }else if(loginType==ADAGENCY_LOGIN_TYPE){
        	 this.jsonResult=adAgencyService.login(userName, password);
         }else if(loginType==ADMAGME_LOGIN_TYPE){
        	 this.jsonResult=adMagmeService.login(userName, password);
         }else{
        	 log.error("错误登录类型");
        	 this.jsonResult.setCode(JsonResult.CODE.FAILURE);
        	 this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
        	 return JSON;
         }
         
         if(this.jsonResult.getCode()==JsonResult.CODE.SUCCESS){
        	 Map<String,Object> session= ActionContext.getContext().getSession();
        	 AdUser adUser = new AdUser();
        	 //clear session
        	 
        	 
        	 //set session
        	 Integer [] fromTypes=new Integer[]{PojoConstant.MESSAGE.FROMTYPE_PUBLISHER,PojoConstant.MESSAGE.FROMTYPE_ADVERTISER};
        	 if(loginType==0 || loginType==PUBLISHER_LOGIN_TYPE){
        		 Publisher publisher = (Publisher) this.jsonResult.getData().get("publisher");
        		 int messageCount=messageService.getMsgCountByToUserIdAndToTypeFromTypes(publisher.getId(),
        					PojoConstant.MESSAGE.TOTYPE_PUBLISHER,fromTypes, PojoConstant.MESSAGE.READED_NO);
        		 publisher.setMessageCount(messageCount);
        		 session.put(WebConstant.SESSION.PUBLISHER, publisher);
        		 adUser.setId(publisher.getId());
                 adUser.setName(publisher.getPublishName());
                 adUser.setLogo(publisher.getLogo());
        		 adUser.setLevel(PojoConstant.ADUSER.LEVEL_PUBLISHER); 
        		 session.remove(WebConstant.SESSION.ADAGENCY);
            	 session.remove(WebConstant.SESSION.ADMAGEME);
            	 session.remove(WebConstant.SESSION.USER);
        	 }else if(loginType==ADAGENCY_LOGIN_TYPE){
        		 AdAgency adAgency=(AdAgency)this.jsonResult.getData().get("loginUser");
        		 int messageCount=messageService.getMsgCountByToUserIdAndToTypeFromTypes(adAgency.getId(),
     					PojoConstant.MESSAGE.TOTYPE_ADVERTISER,fromTypes, PojoConstant.MESSAGE.READED_NO);
        		 adAgency.setMessageCount(messageCount);
        		 session.put(WebConstant.SESSION.ADAGENCY,adAgency );
        		 adUser.setId(adAgency.getId());
        		 adUser.setLogo(adAgency.getLogo());
        		 adUser.setName(adAgency.getUserName());
        		 adUser.setLevel(PojoConstant.ADUSER.LEVEL_ADAGENCY); 
        		 session.remove(WebConstant.SESSION.PUBLISHER);
            	 session.remove(WebConstant.SESSION.ADMAGEME);
            	 session.remove(WebConstant.SESSION.USER);
             }else if(loginType==ADMAGME_LOGIN_TYPE){
            	 AdMagme adMagme=(AdMagme)this.jsonResult.getData().get("loginUser");
            	 session.put(WebConstant.SESSION.ADMAGEME, this.jsonResult.getData().get("loginUser"));
            	 adUser.setId(adMagme.getId());
            	 adUser.setName(adMagme.getUserName());
        		 adUser.setLevel(PojoConstant.ADUSER.LEVEL_ADMAGME); 
        		 session.remove(WebConstant.SESSION.PUBLISHER);
        		 session.remove(WebConstant.SESSION.ADAGENCY);
            	 session.remove(WebConstant.SESSION.USER);
            	 
             }
        	 ActionContext.getContext().getSession().put(WebConstant.SESSION.ADUSER, adUser);
        	 session.put(WebConstant.SESSION.ADUSER, adUser);
        	 this.jsonResult.put("aduser", adUser);
        	 //jsonResult.put("loginType", loginType);
         }
            
       }else{
            this.generateJsonResult(JsonResult.CODE.FAILURE, JsonResult.MESSAGE.FAILURE,"authcode","验证码错误");
       }
       return JSON;
    }
    
    /**
     * 用户退出登陆
     * @return
     */
    public String logoutJson(){
    	Map<String,Object> session=ActionContext.getContext().getSession();
        session.remove(WebConstant.SESSION.PUBLISHER);
	   	session.remove(WebConstant.SESSION.ADAGENCY);
	   	session.remove(WebConstant.SESSION.ADMAGEME);
	   	session.remove(WebConstant.SESSION.ADUSER);
        this.generateJsonResult(JsonResult.CODE.SUCCESS, JsonResult.MESSAGE.SUCCESS);
        return JSON;
    }
    
    /**
     * 只给前段flash使用，请注意 add by liufosheng in 2011-09-22
     * 
     */
    public String getPublisherJson(){
    	AdUser adUser=this.getSessionAdUser();
    	this.jsonResult=new JsonResult();
    	if(adUser!=null){
    		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
    		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
    		//pub.setPublishName(HtmlUtils.htmlEscape(pub.getPublishName()));
    		this.jsonResult.put("adUser", adUser);
    		return JSON;
    	}
    	this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage("用户没有登录");
		return JSON;
    	
    }
    
    
    /**
     * 用户注册
     * @return
     */
    public String registerJson(){
        String session_authcode = (String) (ActionContext.getContext().getSession().get(WebConstant.SESSION.PUBLISHER_AUTHCODE));
        if(StringUtil.isNotBlank(session_authcode)&&session_authcode.equals(this.authcode)){
        	Publisher publisher=new Publisher();
            try {
				publisher.setUserName(userName);
				publisher.setPassword(password);
				publisher.setPassword2(password2);
				publisher.setEmail(email);
				publisher.setContact1(contact1);
				publisher.setContactPhone1(contactPhone1);
				publisher.setPublishName(publishName);
				publisher.setNormalContact(normalContact);
				publisher.setNormalContactType(normalContactType);
				publisher.setFollowNum(0);
				this.jsonResult=publisherService.register(publisher);
				//注册成功 直接登录
				//ActionContext.getContext().getSession().put(WebConstant.SESSION.PUBLISHER, publisher);
				
			} catch (Exception e) {
				this.jsonResult.setCode(JsonResult.CODE.EXCEPTION);
				this.jsonResult.setMessage(JsonResult.MESSAGE.EXCEPTION);
			}
            
            try {
				//发欢迎邮件
				if(jsonResult.getCode()==JsonResult.CODE.SUCCESS){
					String text = mailTemplateService.getTemplateStr(PojoConstant.EMAILTEMPLATE.CONTENT.FILE_WELCOME_PUBLISHER.getFileName());
				    senderMail.sendMail(email, text, PojoConstant.EMAILTEMPLATE.CONTENT.FILE_WELCOME_PUBLISHER.getSubject(), 0);
				}
			} catch (Exception e) {
				log.error("发送邮件错误", e);
			}
            
       }else{
            this.generateJsonResult(JsonResult.CODE.FAILURE, JsonResult.MESSAGE.FAILURE,"authcode","验证码错误");
       }
       return JSON;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	
    
    private Long id;
	private Date createdTime;
	private Date updatedTime;
	private String userName;
	private String password;
	private String publishName;
	private String email;
	private Integer status;
	private Date lastLoginTime;
	private String contact1;
	private String contactPhone1;
	private String contact2;
	private String contactPhone2;
	private String owner;
	private String companyPhone;
	private String fax;
	private String address;
	private String provinceId;
	private String cityId;
	private String logo;
	private String password2;
	
	private String weiboUid;
		
	private String weiboVerifier;
	
	private Integer normalContactType;
	
	private String webSite;
	
	private String normalContact;
	
	
	private int loginType=PUBLISHER_LOGIN_TYPE;
	
	
	
	


	public int getLoginType() {
		return loginType;
	}


	public void setLoginType(int loginType) {
		this.loginType = loginType;
	}


	public String getWeiboUid() {
		return weiboUid;
	}


	public void setWeiboUid(String weiboUid) {
		this.weiboUid = weiboUid;
	}


	public String getWeiboVerifier() {
		return weiboVerifier;
	}


	public void setWeiboVerifier(String weiboVerifier) {
		this.weiboVerifier = weiboVerifier;
	}


	public Integer getNormalContactType() {
		return normalContactType;
	}


	public void setNormalContactType(Integer normalContactType) {
		this.normalContactType = normalContactType;
	}


	public String getWebSite() {
		return webSite;
	}


	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}


	public String getNormalContact() {
		return normalContact;
	}


	public void setNormalContact(String normalContact) {
		this.normalContact = normalContact;
	}

















	/**
     * 认证码
     */
    private String authcode;
    
	
	public String getAuthcode() {
		return authcode;
	}



	public void setAuthcode(String authcode) {
		this.authcode = authcode;
	}



	public String getPassword2() {
		return password2;
	}
	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
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
	public String getPublishName() {
		return publishName;
	}
	public void setPublishName(String publishName) {
		this.publishName = publishName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getContact1() {
		return contact1;
	}
	public void setContact1(String contact1) {
		this.contact1 = contact1;
	}
	public String getContactPhone1() {
		return contactPhone1;
	}
	public void setContactPhone1(String contactPhone1) {
		this.contactPhone1 = contactPhone1;
	}
	public String getContact2() {
		return contact2;
	}
	public void setContact2(String contact2) {
		this.contact2 = contact2;
	}
	public String getContactPhone2() {
		return contactPhone2;
	}
	public void setContactPhone2(String contactPhone2) {
		this.contactPhone2 = contactPhone2;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getCompanyPhone() {
		return companyPhone;
	}
	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	
	

}
