/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action;

import java.io.File;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.constants.WebConstant;
import cn.magme.pojo.User;
import cn.magme.service.UserService;

import com.opensymphony.xwork2.ActionContext;

/**
 * @author jacky_zhou
 * @date 2011-5-26
 * @version $id$
 */
@Results({@Result(name="upload_json",type="json",params={"root","jsonResult","contentType","text/html"})})
@SuppressWarnings("serial")
public class UserUpdateAction extends BaseAction {
    @Resource
    private UserService userService;
    
    /**
     * 编辑用户资料
     * @return
     */
    public String editInfoJson(){
        User user=new User();
        user.setId(this.getSessionUserId());
        user.setEmail(email);
        user.setNickName(nickName);
        user.setGender(gender);
        user.setBirthdate(birthdate);
        user.setOccupation(occupation);
        user.setEducation(education);
        user.setHobbies(hobbies);
        user.setPhone(phone);
        user.setAstro(astro);
        user.setBloodType(bloodType);
        user.setAddress(address);
        user.setProvince(province);
        user.setCity(city);
        user.setPassword(password);
        user.setPassword2(password2);
        user.setOldPassword(oldPassword);
        user.setReserve1(reserve1);
        user.setReserve2(reserve2);
        user.setReserve3(reserve3);
        this.jsonResult=userService.updateUserInfo(user);
        //修改成功,覆盖当前Session中的用户信息
        if(this.jsonResult.getCode()==JsonResult.CODE.SUCCESS){
            ActionContext.getContext().getSession().put(WebConstant.SESSION.USER, this.jsonResult.getData().get("user"));
        }
        return JSON;
    }
    
    /**
     * 上传头像到临时目录
     * @return
     * @throws Exception
     */
    public String uploadAvatarJson() throws Exception{
        this.jsonResult=userService.uploadAvatar(this.getSessionUserId(), avatarFile, avatarFileContentType, avatarFileFileName);
        return "upload_json";
    }
    
    /**
     * 上传头像到临时目录
     * @return
     * @throws Exception
     */
    public String saveAvatarJson() throws Exception{
        this.jsonResult=userService.saveAvatar(this.getSessionUserId(), this.avatarFileName, this.x, this.y, this.width, this.height);
        //修改成功,覆盖当前Session中的用户信息
        if(this.jsonResult.getCode()==JsonResult.CODE.SUCCESS){
            ActionContext.getContext().getSession().put(WebConstant.SESSION.USER, this.jsonResult.getData().get("user"));
        }
        return JSON;
    }
    
    
    //上传的文件.
    private File avatarFile;
    //上传文件类型.
    private String avatarFileContentType;
    //上传文件名.
    private String avatarFileFileName;
    //新生成文件名
    private String avatarFileName;
    
    private Long id;
    private Date createdTime;
    private Date updatedTime;
    private String userName;
    private String password;
    private String nickName;
    private String email;
    private Integer status;
    private Date lastLoginTime;
    private Integer gender;
    private Date birthdate;
    private String occupation;
    private String education;
    private String hobbies;
    private String phone;
    private Integer astro;
    private Integer bloodType;
    private String address;
    private String province;
    private String city;
    private Long recuserId;
    private String avatar;
    private String reserve1;
    private String reserve2;
    private String reserve3;
    private String password2;
    private String oldPassword;
    private Float x;
    private Float y;
    private Float width;
    private Float height;
    
    public File getAvatarFile() {
        return avatarFile;
    }
    public void setAvatarFile(File avatarFile) {
        this.avatarFile = avatarFile;
    }
    public String getAvatarFileFileName() {
        return avatarFileFileName;
    }

    public void setAvatarFileFileName(String avatarFileFileName) {
        this.avatarFileFileName = avatarFileFileName;
    }

    public String getAvatarFileName() {
        return avatarFileName;
    }

    public void setAvatarFileName(String avatarFileName) {
        this.avatarFileName = avatarFileName;
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
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
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
    public Integer getGender() {
        return gender;
    }
    public void setGender(Integer gender) {
        this.gender = gender;
    }
    public Date getBirthdate() {
        return birthdate;
    }
    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
    public String getOccupation() {
        return occupation;
    }
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
    public String getEducation() {
        return education;
    }
    public void setEducation(String education) {
        this.education = education;
    }
    public String getHobbies() {
        return hobbies;
    }
    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public Integer getAstro() {
        return astro;
    }
    public void setAstro(Integer astro) {
        this.astro = astro;
    }
    public Integer getBloodType() {
        return bloodType;
    }
    public void setBloodType(Integer bloodType) {
        this.bloodType = bloodType;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public Long getRecuserId() {
        return recuserId;
    }
    public void setRecuserId(Long recuserId) {
        this.recuserId = recuserId;
    }
    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getReserve1() {
        return reserve1;
    }
    public void setReserve1(String reserve1) {
        this.reserve1 = reserve1;
    }
    public String getReserve2() {
        return reserve2;
    }
    public void setReserve2(String reserve2) {
        this.reserve2 = reserve2;
    }
    public String getReserve3() {
        return reserve3;
    }
    public void setReserve3(String reserve3) {
        this.reserve3 = reserve3;
    }
    public String getPassword2() {
        return password2;
    }
    public void setPassword2(String password2) {
        this.password2 = password2;
    }
    public String getOldPassword() {
        return oldPassword;
    }
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getAvatarFileContentType() {
        return avatarFileContentType;
    }

    public void setAvatarFileContentType(String avatarFileContentType) {
        this.avatarFileContentType = avatarFileContentType;
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
}
