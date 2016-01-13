package cn.magme.web.action.newPublisher;

import java.io.File;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant;
import cn.magme.constants.WebConstant;
import cn.magme.pojo.Publisher;
import cn.magme.service.PublisherService;
import cn.magme.util.Md5Util;
import cn.magme.util.StringUtil;

import com.opensymphony.xwork2.ActionContext;

@Results({@Result(name="config",location="/WEB-INF/pages/newPublisher/publisherConfig.ftl"),
	@Result(name="upload_json",type="json",params={"root","jsonResult","contentType","text/html"})})
public class PublisherConfigAction extends PublisherBaseAction {
	
	private static final long serialVersionUID = 867457713150515125L;
	
	@Resource
	private PublisherService publisherService;
	
	
	public String to(){
		Publisher publisher=this.getSessionPublisher();
		if(publisher!=null
				&&(publisher.getLevel().equals(PojoConstant.PUBLISHER.LEVEL_0)
				||publisher.getLevel().equals(PojoConstant.PUBLISHER.LEVEL_1))){
			return "config";
		}else{
			return "deny";
		}
	}
	
	/**
	 * 更新publisher信息
	 * 
	 * @return
	 */
	public String updatePublisher() {
		try {
			this.jsonResult = new JsonResult();
			Publisher publisher = this.publisherService.queryById(this.getSessionPublisherId());
			if(StringUtil.isNotBlank(this.userName)){
				publisher.setUserName(userName);
			}
			if(StringUtil.isNotBlank(this.publishName)){
				publisher.setPublishName(publishName);
			}
			if(StringUtil.isNotBlank(this.email)){
				publisher.setEmail(email);
			}
			if(StringUtil.isNotBlank(this.contact1)){
				publisher.setContact1(contact1);
			}
			if(StringUtil.isNotBlank(contactPhone1)){
				publisher.setContactPhone1(contactPhone1);
			}
			publisher.setOwner(owner);
			publisher.setContact2(contact2);
			publisher.setContactPhone2(contactPhone2);
			publisher.setCompanyPhone(companyPhone);
			publisher.setFax(fax);
			publisher.setAddress(address);
			publisher.setProvinceId(provinceId);
			publisher.setCityId(cityId);
			publisher.setWebSite(webSite);
			publisher.setNormalContact(normalContact);
			publisher.setNormalContactType(normalContactType);
			publisher.setWeiboUid(weiboUid);
			publisher.setWeiboVerifier(weiboVerifier);
			
			this.jsonResult=this.publisherService.validatePublisher(publisher, false);
			if(this.jsonResult.getCode()!=JsonResult.CODE.SUCCESS){
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
					publisher.setPassword(Md5Util.MD5Encode(this.password));
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
			this.publisherService.updatePublisherNoLimitById(publisher);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			// 更新session
			ActionContext.getContext().getSession().put(
					WebConstant.SESSION.PUBLISHER, publisher);
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
	 * 图片上传
	 * @return
	 */
	public String uploadLogoJson() {
		Publisher publisher = (Publisher) ActionContext.getContext()
				.getSession().get(WebConstant.SESSION.PUBLISHER);
		try {
			this.jsonResult=this.publisherService.uploadLogo(publisher.getId(), logoFile, logoFileContentType, logoFileFileName);

			this.jsonResult.put("logoFileName", this.jsonResult
					.get("avatarFileName"));
			publisher.setLogo((String)this.jsonResult
					.get("tempAvatarUrl"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "upload_json";
	}

	/**
	 * 更新
	 * @return
	 */
	public String updateJson() {
		Publisher publisher = (Publisher) ActionContext.getContext()
				.getSession().get(WebConstant.SESSION.PUBLISHER);
		publisher.setLogo("/" + publisher.getId() + "/"
				+ this.logoFileName);
		//更新publisher的logo路径
		this.publisherService.updatePublisherById(publisher);
		//将文件拷贝到url
		this.jsonResult = this.publisherService.saveLogo(publisher.getId(), this.logoFileName, x, y, width, height);
		return JSON;
	}

	private Long id;
	private String userName;
	private String publishName;
	private String email;
	private String owner;
	private String contact1;
	private String contactPhone1;
	private String contact2;
	private String contactPhone2;
	private String companyPhone;
	private String fax;
	private String address;
	private String provinceId;
	private String cityId;
	private String logo;
	private String oldPassword;
	private String password;
	private String password2;

	private File logoFile;
	private String logoFileFileName;
	private String logoFileContentType;

	private String logoFileName;
	private Float x;
	private Float y;
	private Float width;
	private Float height;
	
    private String weiboUid;
	
	private String weiboVerifier;
	
	private Integer normalContactType;
	
	private String webSite;
	
	private String normalContact;

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

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
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

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
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

	public String getLogoFileName() {
		return logoFileName;
	}

	public void setLogoFileName(String logoFileName) {
		this.logoFileName = logoFileName;
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
}
