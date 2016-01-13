/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.ad;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.common.SenderMail;
import cn.magme.constants.PojoConstant;
import cn.magme.constants.WebConstant;
import cn.magme.pojo.AdAgency;
import cn.magme.pojo.AdUser;
import cn.magme.pojo.Message;
import cn.magme.service.AdAgencyService;
import cn.magme.service.MailTemplateService;
import cn.magme.service.MessageService;
import cn.magme.service.PublisherService;
import cn.magme.util.Md5Util;
import cn.magme.util.StringUtil;
import cn.magme.util.UploadPictureUtil;
import cn.magme.web.action.BaseAction;

import com.opensymphony.xwork2.ActionContext;

/**
 * @author fredy.liu
 * @date 2011-10-20
 * @version $id$
 */
@Results( { @Result(name ="upload_ad_json", type = "json", params = { "root","jsonResult", "contentType", "text/html" }),
		    @Result(name="ad_message_success",location="/WEB-INF/pages/publishadmin/manageMgzMsg.ftl"),
	        @Result(name="ad_message_detail_success",location="/WEB-INF/pages/publishadmin/manageMgzMsgDetail.ftl"),
	        @Result(name="ad_writeMessage_success",location="/WEB-INF/pages/publishadmin/manageMgzMsgWrite.ftl") })
public class AdAgencyAction extends BaseAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7968195502888462214L;

	@Resource
	private AdAgencyService adAgencyService;
	@Resource
	private MessageService messageService;
	@Resource
	private PublisherService publisherService;
	
	private static final Logger log=Logger.getLogger(AdAgencyAction.class);
	
   @Resource
    private MailTemplateService mailTemplateService;
    
    @Resource
    private SenderMail senderMail;
	/**
	 * 更新信息
	 * @return
	 */
	public String update(){
		try {
			AdAgency adAgency = (AdAgency) ActionContext.getContext().getSession().get(WebConstant.SESSION.ADAGENCY);
			this.jsonResult = new JsonResult();
			adAgency = this.adAgencyService.queryById(adAgency.getId());
			if(StringUtil.isNotBlank(this.userName)){
				adAgency.setUserName(userName);
			}
			if(StringUtil.isNotBlank(this.companyName)){
				adAgency.setCompanyName(companyName);
			}
			if(StringUtil.isNotBlank(this.email)){
				adAgency.setEmail(email);
			}
			if(StringUtil.isNotBlank(this.contact)){
				adAgency.setContact(contact);
			}
			if(StringUtil.isNotBlank(contactPhone)){
				adAgency.setContactPhone(contactPhone);
			}
			if(StringUtil.isNotBlank(contactType)){
				adAgency.setContactType(contactType);
			}
			adAgency.setFax(fax);
			adAgency.setAddress(address);
			adAgency.setWebSite(webSite);
			
			
			if(StringUtil.isBlank(this.getContact())){
				this.jsonResult.setCode(JsonResult.CODE.FAILURE);
				this.jsonResult.setMessage("常用联系方式不能为空");
				return JSON;
            }
			
			if(StringUtil.isBlank(this.contactPhone)){
				this.jsonResult.setCode(JsonResult.CODE.FAILURE);
				this.jsonResult.setMessage("联系人电话不能为空");
				return JSON;
            }
			
			if(StringUtil.isBlank(this.userName)){
				this.jsonResult.setCode(JsonResult.CODE.FAILURE);
				this.jsonResult.setMessage("名称不能为空");
				return JSON;
            }
			
			if(StringUtil.isBlank(this.email)){
				this.jsonResult.setCode(JsonResult.CODE.FAILURE);
				this.jsonResult.setMessage("email不能为空");
				return JSON;
            }
			
			
			if(StringUtil.isBlank(this.contactNumber)){
				this.jsonResult.setCode(JsonResult.CODE.FAILURE);
				this.jsonResult.setMessage("常用号码不能为空");
				return JSON;
            }
			
			if (this.oldPassword != null && this.getSessionPublisher().getPassword().equals(Md5Util.MD5Encode(this.oldPassword))) {
				if(StringUtil.isBlank(this.password) || StringUtil.isBlank(this.password2)){
					this.jsonResult.setCode(JsonResult.CODE.FAILURE);
					this.jsonResult.setMessage("新密码与确认密码不能为空");
					return JSON;
				}
				if (this.password != null
						&& this.password.equals(this.password2)) {
					adAgency.setPassword(Md5Util.MD5Encode(this.password));
				}else{
					this.jsonResult.setCode(JsonResult.CODE.FAILURE);
					this.jsonResult.setMessage("新密码与确认密码不一致");
					return JSON;
				}
			}else if(this.oldPassword != null){//原密码不正确
				this.jsonResult.setCode(JsonResult.CODE.FAILURE);
				this.jsonResult.setMessage("原密码不正确");
				return JSON;
			}
			
			if((StringUtil.isNotBlank(this.password) || StringUtil.isNotBlank(password2)) && StringUtil.isBlank(this.oldPassword)){
				this.jsonResult.setCode(JsonResult.CODE.FAILURE);
				this.jsonResult.setMessage("修改密码需要输入原密码");
				return JSON;
			}
			//this.publisherService.updatePublisherById(publisher);
			this.adAgencyService.updateById(adAgency);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			// 更新session
			ActionContext.getContext().getSession().put(
					WebConstant.SESSION.ADAGENCY, adAgency);
		} catch (Exception e) {
			if (this.jsonResult == null) {
				this.jsonResult = new JsonResult();
			}
			this.jsonResult.setCode(JsonResult.CODE.FAILURE);
			this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		}
		return JSON;
	}
	
	/**
	 * 上传头像
	 * @return
	 */
	public String uploadLogoJson(){
		AdUser adUser = (AdUser) ActionContext.getContext()
					.getSession().get(WebConstant.SESSION.ADUSER);
			try {
				this.jsonResult=UploadPictureUtil.uploadLogo(adUser.getId(),true,logoFile, logoFileContentType, logoFileFileName
						,this.systemProp.getAdProfileLocalUrlTmp());
				this.jsonResult.put("logoFileName", this.jsonResult
						.get("avatarFileName"));
			} catch (Exception e) {
				log.error("", e);
			}
		return "upload_ad_json";
	}
	
	/**
	 * 保存头像
	 * @return
	 */
	public String saveLogoJson(){
		AdAgency adAgency = (AdAgency) ActionContext.getContext().getSession().get(WebConstant.SESSION.ADAGENCY);
		adAgency.setLogo("/" + adAgency.getId() + "/"
				+ this.logoFileName);
		//更新publisher的logo路径
		this.adAgencyService.updateById(adAgency);
		//将文件拷贝到url
		this.jsonResult = UploadPictureUtil.saveLogo(adAgency.getId(), this.logoFileName, x, y, width, height
				,this.systemProp.getAdProfileLocalUrlTmp(),this.systemProp.getAdProfileLocalUrl());
		return JSON;
	}
	
	
	
	//=======================message
	
	/**
	 * 消息列表
	 */
	public String msgList(){
		try {
			Integer []fromTypeArr=new Integer[]{PojoConstant.MESSAGE.FROMTYPE_ADVERTISER,PojoConstant.MESSAGE.FROMTYPE_PUBLISHER};
			this.messageList=messageService.getByToTypeToUserIdFromTypeArr(this.getSessionAdUserId(),
					PojoConstant.MESSAGE.TOTYPE_ADVERTISER, fromTypeArr, null, null);
			((AdAgency)ActionContext.getContext().getSession().get(WebConstant.SESSION.ADAGENCY)).setMessageCount(0);
		} catch (Exception e) {
			log.error("", e);
		}
		return "ad_message_success";
	}
	
	
	/**
	 * 消息详情
	 * @return
	 */
	public String msgDetail(){
		try {
			this.messageList=messageService.getMessageList(
					    fromUserId, this.fromType, 
			            this.getSessionAdUserId(), PojoConstant.MESSAGE.TOTYPE_ADVERTISER,null, null);     
			
			if(this.fromType==PojoConstant.MESSAGE.FROMTYPE_PUBLISHER){
				fromUserName=this.publisherService.queryById(fromUserId).getPublishName();
			}else if(this.fromType==PojoConstant.MESSAGE.FROMTYPE_ADVERTISER){
				fromUserName=this.adAgencyService.queryById(fromUserId).getUserName();
			}
		} catch (Exception e) {
			log.error("", e);
		}
		return "ad_message_detail_success";
	}

	/**
     * 用户注册
     * @return
     */
    public String registerJson(){

        try {
            this.jsonResult = adAgencyService.register(adAgency);
            
        } catch (Exception e) {
            this.jsonResult.setCode(JsonResult.CODE.EXCEPTION);
            this.jsonResult.setMessage(JsonResult.MESSAGE.EXCEPTION);
        }
        
        try {
            //发欢迎邮件
            if(jsonResult.getCode()==JsonResult.CODE.SUCCESS){
//                String text = mailTemplateService.getTemplateStr(PojoConstant.EMAILTEMPLATE.FILE_WELCOME_PUBLISHER);
//                senderMail.sendMail(email, text, PojoConstant.EMAILTEMPLATE.SUBJECT_WELCOME_PUBLISHER, 0);
            }
        } catch (Exception e) {
            log.error("发送邮件错误", e);
        }
//            
       return JSON;
    }
	
    public String  validateAdAgency() {
        try {
            this.jsonResult=adAgencyService.validateAdAgency(adAgency,false);
        } catch (Exception e) {
            log.error("", e);
            this.jsonResult.setCode(JsonResult.CODE.EXCEPTION);
            this.jsonResult.setMessage(JsonResult.MESSAGE.EXCEPTION);
        }
        return JSON;
    }
    
	
	/**
	 * 写消息
	 * @return
	 */
	public String writeMessage(){
        return "ad_writeMessage_success";
    }
	
	private File logoFile;
	private String logoFileFileName;
	private String logoFileContentType;
	
	private Float x;
	private Float y;
	private Float width;
	private Float height;
	
	private String logoFileName;
	
	private String oldPassword;
	private String password2;
	private String fromUserName;
	private List<Message> messageList;
	private Long fromUserId;
	private Integer fromType;
	
	
	
	

	
	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	
	public Long getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(Long fromUserId) {
		this.fromUserId = fromUserId;
	}

	public Integer getFromType() {
		return fromType;
	}

	public void setFromType(Integer fromType) {
		this.fromType = fromType;
	}

	public List<Message> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<Message> messageList) {
		this.messageList = messageList;
	}

	/**
	 * adagency信息
	 */
	private Long id;
	private String userName;
	private String password;
	private String companyName;
	private String email;
	private String contact;
	private String contactPhone;
	private String contactType;
	private String contactNumber;
	private String address;
	private String webSite;
	private String fax;
	
	
	
	

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactType() {
		return contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public File getLogoFile() {
		return logoFile;
	}

	public void setLogoFile(File logoFile) {
		this.logoFile = logoFile;
	}

	public String getLogoFileFileName() {
		return logoFileFileName;
	}

	public void setLogoFileFileName(String logoFileFileName) {
		this.logoFileFileName = logoFileFileName;
	}

	public String getLogoFileContentType() {
		return logoFileContentType;
	}

	public void setLogoFileContentType(String logoFileContentType) {
		this.logoFileContentType = logoFileContentType;
	}

	public Float getX() {
		return x;
	}

	public void setX(Float x) {
		this.x = x;
	}

	public Float getY() {
		return y;
	}

	public void setY(Float y) {
		this.y = y;
	}

	public Float getWidth() {
		return width;
	}

	public void setWidth(Float width) {
		this.width = width;
	}

	public Float getHeight() {
		return height;
	}

	public void setHeight(Float height) {
		this.height = height;
	}

	public String getLogoFileName() {
		return logoFileName;
	}

	public void setLogoFileName(String logoFileName) {
		this.logoFileName = logoFileName;
	}
	
	
	public AdAgency getAdAgency() {
        return adAgency;
    }

    public void setAdAgency(AdAgency adAgency) {
        this.adAgency = adAgency;
    }

    private AdAgency adAgency;
	
	

}
