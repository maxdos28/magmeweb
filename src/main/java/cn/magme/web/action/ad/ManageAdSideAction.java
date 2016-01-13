/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.ad;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.PageBar;
import cn.magme.constants.PojoConstant;
import cn.magme.constants.WebConstant;
import cn.magme.pojo.AdSide;
import cn.magme.pojo.AdUser;
import cn.magme.pojo.Category;
import cn.magme.service.AdSideService;
import cn.magme.service.CategoryService;
import cn.magme.web.action.BaseAction;

import com.opensymphony.xwork2.ActionContext;

/**
 * @author jacky_zhou
 * @date 2011-11-4
 * @version $id$
 */

@SuppressWarnings("serial")
@Results({@Result(name="upload_json",type="json",params={"root","jsonResult","contentType","text/html"}),
    @Result(name="success",location="/WEB-INF/pages/ad/manageAdSide.ftl"),
    @Result(name="getListAjax",location="/WEB-INF/pages/ad/manageAdSide_ajax.ftl"),
    @Result(name="editAjax",location="/WEB-INF/pages/dialog/editAdSide.ftl")})
public class ManageAdSideAction extends BaseAction{

    @Resource
    private AdSideService adSideService;
    
    @Resource
    private CategoryService categoryService;
    
    /**
     * 跳转到新增或修改侧栏广告的页面
     * @return
     */
    public String editAjax(){
        this.adSide=adSideService.getById(id);
        this.categoryList = categoryService.queryAllChildCategories();
        return "editAjax";
    }
    
    /**
     * 新增或修改侧栏广告
     * @return
     */
    public String saveJson(){
        AdUser adUser = (AdUser) ActionContext.getContext().getSession().get(WebConstant.SESSION.ADUSER);
        Integer userType=null;
        if(PojoConstant.ADUSER.LEVEL_PUBLISHER==adUser.getLevel()){
            userType=PojoConstant.ADVERTISE.USER_TYPE_PUBLISHER;
        }else if(PojoConstant.ADUSER.LEVEL_ADAGENCY==adUser.getLevel()){
            userType=PojoConstant.ADVERTISE.USER_TYPE_ADAGENCY;
        }else if(PojoConstant.ADUSER.LEVEL_ADMAGME==adUser.getLevel()){
            userType=PojoConstant.ADVERTISE.USER_TYPE_ADMAGME;
        }else{
            userType=null;
        }
        
        AdSide adSide=new AdSide();
        adSide.setId(id);
        adSide.setCategoryId(categoryId);
        adSide.setDescription(description);
        adSide.setLink(link);
        adSide.setValidBeginTime(validBeginTime);
        adSide.setValidEndTime(validEndTime);
        adSide.setPos(pos);
        adSide.setUserId(adUser.getId());
        adSide.setUserType(userType);
        this.jsonResult=adSideService.save(adSide,imageFile,imageFileContentType,imageFileFileName);
        return "upload_json";
    }
    
    public String updateStatusJson(){
        AdUser adUser = (AdUser) ActionContext.getContext().getSession().get(WebConstant.SESSION.ADUSER);
        Integer userType=null;
        if(PojoConstant.ADUSER.LEVEL_PUBLISHER==adUser.getLevel()){
            userType=PojoConstant.ADVERTISE.USER_TYPE_PUBLISHER;
        }else if(PojoConstant.ADUSER.LEVEL_ADAGENCY==adUser.getLevel()){
            userType=PojoConstant.ADVERTISE.USER_TYPE_ADAGENCY;
        }else if(PojoConstant.ADUSER.LEVEL_ADMAGME==adUser.getLevel()){
            userType=PojoConstant.ADVERTISE.USER_TYPE_ADMAGME;
        }else{
            userType=null;
        }
        
        this.jsonResult=adSideService.updateStatus(id,status,adUser.getId(),userType);        
        return JSON;
    }
    
    public String getList(){
        getListAjax();
        this.categoryList = categoryService.queryAllChildCategories();
        return "success";
    }
    
    public String getListAjax(){
        Map<String,Object> param=new HashMap<String,Object>();
        param.put("description", description);
        param.put("categoryId", categoryId);
        param.put("beginTime", beginTime);
        param.put("endTime", endTime);
        param.put("begin", PageBar.getBegin(this.pageNum, this.pageSize));
        param.put("size", PageBar.getEnd(this.pageSize));
        this.adSideList=adSideService.getListByMap(param);
        Integer count=adSideService.getCountByMap(param);
        this.pageBar=new PageBar(this.pageNum, this.pageSize, count, "turnPage", null);
        return "getListAjax";
    }
    
    private Long id;
    private Long categoryId;
    private Integer pos;
    private String path;
    private Date validBeginTime;
    private Date validEndTime;
    private String link;
    private String description;
    private Integer status;
    private File imageFile;
    private String imageFileContentType;
    private String imageFileFileName;
    private Date beginTime;
    private Date endTime;
    private List<AdSide> adSideList;
    private List<Category> categoryList;
    private AdSide adSide;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    public String getImageFileContentType() {
        return imageFileContentType;
    }

    public void setImageFileContentType(String imageFileContentType) {
        this.imageFileContentType = imageFileContentType;
    }

    public String getImageFileFileName() {
        return imageFileFileName;
    }

    public void setImageFileFileName(String imageFileFileName) {
        this.imageFileFileName = imageFileFileName;
    }

    public Date getValidBeginTime() {
        return validBeginTime;
    }

    public void setValidBeginTime(Date validBeginTime) {
        this.validBeginTime = validBeginTime;
    }

    public Date getValidEndTime() {
        return validEndTime;
    }

    public void setValidEndTime(Date validEndTime) {
        this.validEndTime = validEndTime;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<AdSide> getAdSideList() {
        return adSideList;
    }

    public void setAdSideList(List<AdSide> adSideList) {
        this.adSideList = adSideList;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public AdSide getAdSide() {
        return adSide;
    }

    public void setAdSide(AdSide adSide) {
        this.adSide = adSide;
    }
}
