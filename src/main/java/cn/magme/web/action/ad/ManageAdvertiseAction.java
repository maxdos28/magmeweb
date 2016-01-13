/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.ad;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.constants.PojoConstant;
import cn.magme.constants.WebConstant;
import cn.magme.pojo.AdUser;
import cn.magme.pojo.Advertise;
import cn.magme.service.AdvertiseService;
import cn.magme.web.action.BaseAction;

import com.opensymphony.xwork2.ActionContext;

/**
 * @author shenhao
 * @date 2011-10-18
 * @version $id$
 * 广告管理
 */
@SuppressWarnings("serial")
@Results({@Result(name="success",location="/WEB-INF/pages/dialog/viewAdvertiseInfo.ftl"),
	@Result(name="success",location="/WEB-INF/pages/dialog/viewAdvertiseInfo.ftl")})
public class ManageAdvertiseAction extends BaseAction {
    
    @Resource
    private AdvertiseService advertiseService;
    
    public String viewAjax(){
        adUser = (AdUser) ActionContext.getContext().getSession().get(WebConstant.SESSION.ADUSER);
        advertise=advertiseService.getById(id);
        Integer userTypeId=null;
        if(PojoConstant.ADUSER.LEVEL_PUBLISHER==adUser.getLevel()){
            userTypeId=PojoConstant.ADVERTISE.USER_TYPE_PUBLISHER;
        }else if(PojoConstant.ADUSER.LEVEL_ADAGENCY==adUser.getLevel()){
            userTypeId=PojoConstant.ADVERTISE.USER_TYPE_ADAGENCY;
        }else if(PojoConstant.ADUSER.LEVEL_ADMAGME==adUser.getLevel()){
            userTypeId=PojoConstant.ADVERTISE.USER_TYPE_ADMAGME;
        }else{
            userTypeId=null;
        }
        this.isModify=0;
        this.isMagme=0;
        //我创建的广告
        if(type!=null&&type.equals(1)){
            if(advertise!=null
                    &&advertise.getUserId().equals(adUser.getId())
                    &&advertise.getUserTypeId().equals(userTypeId)){
                this.isModify=1;
            }
        //麦米广告
        }else if(type!=null&&type.equals(3)){
            if(PojoConstant.ADUSER.LEVEL_ADMAGME==adUser.getLevel()){
                this.isModify=1;
                this.isMagme=1;
            }
        }
        return "success";
    }
    
    /**
     * 修改广告的标题,描述,链接,备注
     * @return
     */
    public String updateJson(){
        adUser = (AdUser) ActionContext.getContext().getSession().get(WebConstant.SESSION.ADUSER);
        Advertise advertise=new Advertise();
        advertise.setId(this.id);
        advertise.setTitle(title);
        advertise.setDescription(description);
        advertise.setLinkurl(linkurl);
        advertise.setRemark(remark);
        Integer userTypeId=null;
        if(PojoConstant.ADUSER.LEVEL_PUBLISHER==adUser.getLevel()){
            userTypeId=PojoConstant.ADVERTISE.USER_TYPE_PUBLISHER;
        }else if(PojoConstant.ADUSER.LEVEL_ADAGENCY==adUser.getLevel()){
            userTypeId=PojoConstant.ADVERTISE.USER_TYPE_ADAGENCY;
        }else if(PojoConstant.ADUSER.LEVEL_ADMAGME==adUser.getLevel()){
            userTypeId=PojoConstant.ADVERTISE.USER_TYPE_ADMAGME;
        }else{
            return null;
        }
        this.jsonResult=advertiseService.update(advertise,adUser.getId(),userTypeId);
        return JSON;
    }
    
    /**
     * 更新广告的状态,广告只能在特定的几种状态之间转换
     * @return
     */
    public String updateStatusJson(){
        adUser = (AdUser) ActionContext.getContext().getSession().get(WebConstant.SESSION.ADUSER);
        Integer userTypeId=null;
        if(PojoConstant.ADUSER.LEVEL_PUBLISHER==adUser.getLevel()){
            userTypeId=PojoConstant.ADVERTISE.USER_TYPE_PUBLISHER;
        }else if(PojoConstant.ADUSER.LEVEL_ADAGENCY==adUser.getLevel()){
            userTypeId=PojoConstant.ADVERTISE.USER_TYPE_ADAGENCY;
        }else if(PojoConstant.ADUSER.LEVEL_ADMAGME==adUser.getLevel()){
            userTypeId=PojoConstant.ADVERTISE.USER_TYPE_ADMAGME;
        }else{
            userTypeId=null;
        }
        
        this.jsonResult=advertiseService.updateStatus(
                this.id,this.status,adUser.getId(),userTypeId);
            return JSON;
    }
    
    private Long id;
    private Integer type;
    private String title;
    private String description;
    private String linkurl;
    private String imgurl;
    private String mediaurl;
    private String remark;
    private AdUser adUser;
    private Advertise advertise;
    private Integer isModify;
    private Integer isMagme;
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLinkurl() {
        return linkurl;
    }

    public void setLinkurl(String linkurl) {
        this.linkurl = linkurl;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getMediaurl() {
        return mediaurl;
    }

    public void setMediaurl(String mediaurl) {
        this.mediaurl = mediaurl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public AdUser getAdUser() {
        return adUser;
    }

    public void setAdUser(AdUser adUser) {
        this.adUser = adUser;
    }

    public Advertise getAdvertise() {
        return advertise;
    }

    public void setAdvertise(Advertise advertise) {
        this.advertise = advertise;
    }

    public Integer getIsModify() {
        return isModify;
    }

    public void setIsModify(Integer isModify) {
        this.isModify = isModify;
    }

    public Integer getIsMagme() {
        return isMagme;
    }

    public void setIsMagme(Integer isMagme) {
        this.isMagme = isMagme;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
