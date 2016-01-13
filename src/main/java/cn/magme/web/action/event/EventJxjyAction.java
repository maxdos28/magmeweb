package cn.magme.web.action.event;

import java.io.File;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.pojo.EventJxjy;
import cn.magme.service.EventJxjyService;
import cn.magme.util.FileOperate;
import cn.magme.web.action.BaseAction;
@Results({@Result(name="issueUploadJson",type="json",params={"root","jsonResult","contentType","text/html"})})
public class EventJxjyAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 612547410703128485L;
	
	private static final Logger log=Logger.getLogger(EventJxjyAction.class);
	
	@Resource
	private EventJxjyService eventJxjyService;
	/**
	 * 初始页面
	 */
	public String execute(){
		
		return SUCCESS;
	}
	
	public String addJson(){
		this.jsonResult=JsonResult.getFailure();
		if(StringUtils.isBlank(this.name) || StringUtils.isBlank(address) 
		   || StringUtils.isBlank(this.birthdate)
		   || StringUtils.isBlank(this.birthplace)|| StringUtils.isBlank(this.college)
		   || StringUtils.isBlank(this.declaration)|| StringUtils.isBlank(this.education)
		   || StringUtils.isBlank(this.hobbies)|| StringUtils.isBlank(this.honours)
		   || StringUtils.isBlank(this.idno)|| StringUtils.isBlank(this.major)
		   || StringUtils.isBlank(this.name)|| StringUtils.isBlank(this.role)){
			
			this.jsonResult.setMessage("标*的项目必填");
			return "issueUploadJson";
		}
		
		if(gender==null || age==null || age<=0 ){
			this.jsonResult.setMessage("标*的项目必填,并且性别只能填男，女，年龄必须整数");
			return "issueUploadJson";
		}
		
		if(this.avatarFile==null){
			this.jsonResult.setMessage("必须上传照片");
			return "issueUploadJson";
		}
		File f=new File(this.systemProp.getStaticLocalUrl()+File.separator+"event"+File.separator+"jxjy");
		if(!f.exists()){
			f.mkdirs();
		}
		String fileName=System.currentTimeMillis()+"."+this.filenameext;
		FileOperate.moveFile(avatarFile.getAbsolutePath(), f.getAbsolutePath()+File.separator+fileName);
		
		EventJxjy j=new EventJxjy();
		j.setAddress(address);
		j.setAge(age);
		j.setAvatar("/event/jxjy/"+fileName);
		j.setBirthdate(birthdate);
		j.setBirthplace(birthplace);
		j.setCollege(college);
		j.setContact(contact);
		j.setDeclaration(declaration);
		j.setEducation(education);
		j.setGender(gender);
		j.setHobbies(hobbies);
		j.setHonours(honours);
		j.setIdno(idno);
		j.setMajor(major);
		j.setName(name);
		j.setPassport(passport);
		j.setRole(role);
		j.setStatus(1);
		try {
			if(this.eventJxjyService.insert(j)>0){
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
				this.jsonResult.put("eventJxjy", j);
			}
		} catch (Exception e) {
			log.error("", e);
		}
		
		return "issueUploadJson";
	}
	
	public String queryCount(){
		this.jsonResult =JsonResult.getSuccess();
		long queryCount = eventJxjyService.queryCountAll();
		queryCount = queryCount+9100;
		this.jsonResult.put("count", queryCount);
		return JSON;
	}
	
	private String name;

    private Integer gender;

    private Integer age;

    private Integer status;

    private String birthdate;

    private String birthplace;

    private String education;

    private String major;

    private String college;

    private String idno;

    private String passport;

    private String contact;

    private String address;

    private String role;

    private String hobbies;
    
    private String honours;
    
    private String declaration;
    
    private File avatarFile;
    
    private String filenameext;
    
    
    
	public String getFilenameext() {
		return filenameext;
	}

	public void setFilenameext(String filenameext) {
		this.filenameext = filenameext;
	}

	public File getAvatarFile() {
		return avatarFile;
	}

	public void setAvatarFile(File avatarFile) {
		this.avatarFile = avatarFile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getBirthplace() {
		return birthplace;
	}

	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getIdno() {
		return idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getHobbies() {
		return hobbies;
	}

	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}

	public String getHonours() {
		return honours;
	}

	public void setHonours(String honours) {
		this.honours = honours;
	}


	public String getDeclaration() {
		return declaration;
	}

	public void setDeclaration(String declaration) {
		this.declaration = declaration;
	}
    
    

}
