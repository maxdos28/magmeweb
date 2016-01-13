/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.ad;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.constants.PojoConstant;
import cn.magme.constants.WebConstant;
import cn.magme.pojo.AdPosition;
import cn.magme.pojo.AdUser;
import cn.magme.service.AdPositionService;
import cn.magme.web.action.BaseAction;

import com.opensymphony.xwork2.ActionContext;

/**
 * @author shenhao
 * @date 2011-10-18
 * @version $id$
 * 广告位管理
 */
@SuppressWarnings("serial")
@Results({@Result(name="queryAjax",location="/WEB-INF/pages/ad/manageAdPositionDetail.ftl")})
public class ManagePositionDetailAction extends BaseAction {
    
    @Resource
    private AdPositionService adPositionService;
    
    public String queryAjax(){
        adUser = (AdUser) ActionContext.getContext().getSession().get(WebConstant.SESSION.ADUSER);
        if(adUser.getLevel()==PojoConstant.ADUSER.LEVEL_PUBLISHER){
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("issueId", this.issueId);
            map.put("userId", adUser.getId());
            map.put("userTypeId", PojoConstant.ADPOSITION.USER_TYPE_PUBLISHER);
            adPositionList=adPositionService.getListByIssueId(map);
        }else if(adUser.getLevel()==PojoConstant.ADUSER.LEVEL_ADMAGME){
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("issueId", this.issueId);
            map.put("userId", adUser.getId());
            map.put("userTypeId", PojoConstant.ADPOSITION.USER_TYPE_ADMAGME);
            adPositionList=adPositionService.getListByIssueId(map);
        }else{
            return null;
        }
        return "queryAjax";
    }
    
    public String updateJson(){
        AdPosition adPosition=new AdPosition();
        adPosition.setId(id);
        adPosition.setTitle(title);
        adPosition.setKeywords(keywords);
        adPosition.setDescription(description);
        adUser = (AdUser) ActionContext.getContext().getSession().get(WebConstant.SESSION.ADUSER);
        Integer userTypeId=null;
        if(adUser.getLevel()==PojoConstant.ADUSER.LEVEL_PUBLISHER){
            userTypeId=PojoConstant.ADPOSITION.USER_TYPE_PUBLISHER;
        }else if(adUser.getLevel()==PojoConstant.ADUSER.LEVEL_ADMAGME){
            userTypeId=PojoConstant.ADPOSITION.USER_TYPE_ADMAGME;
        }            
        this.jsonResult=adPositionService.update(adPosition,adUser.getId(),userTypeId);
        return JSON;
    }
    
    public String deleteJson(){
        adUser = (AdUser) ActionContext.getContext().getSession().get(WebConstant.SESSION.ADUSER);
        Integer userTypeId=null;
        if(adUser.getLevel()==PojoConstant.ADUSER.LEVEL_PUBLISHER){
            userTypeId=PojoConstant.ADPOSITION.USER_TYPE_PUBLISHER;
        }else if(adUser.getLevel()==PojoConstant.ADUSER.LEVEL_ADMAGME){
            userTypeId=PojoConstant.ADPOSITION.USER_TYPE_ADMAGME;
        }            
        this.jsonResult=adPositionService.deleteById(this.id,adUser.getId(),userTypeId);
        return JSON;
    }
    
    private AdUser adUser;
    private List<AdPosition> adPositionList;
    private Long issueId;
    private String title;
    private String keywords;
    private String description;
    private Long id;

    public AdUser getAdUser() {
        return adUser;
    }
    public void setAdUser(AdUser adUser) {
        this.adUser = adUser;
    }
    public List<AdPosition> getAdPositionList() {
        return adPositionList;
    }
    public void setAdPositionList(List<AdPosition> adPositionList) {
        this.adPositionList = adPositionList;
    }
    public Long getIssueId() {
        return issueId;
    }
    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getKeywords() {
        return keywords;
    }
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
