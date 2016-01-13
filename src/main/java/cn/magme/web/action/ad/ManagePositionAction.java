/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.ad;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.PageBar;
import cn.magme.constants.PojoConstant;
import cn.magme.constants.WebConstant;
import cn.magme.pojo.AdUser;
import cn.magme.pojo.Publication;
import cn.magme.service.AdPositionService;
import cn.magme.service.PublicationService;
import cn.magme.web.action.BaseAction;

import com.opensymphony.xwork2.ActionContext;

/**
 * @author shenhao
 * @date 2011-10-18
 * @version $id$
 * 广告位管理
 */
@SuppressWarnings("serial")
@Results({@Result(name="success",location="/WEB-INF/pages/ad/manageAdPosition.ftl"),
    @Result(name="getAdPositionAjax",location="/WEB-INF/pages/ad/manageAdPosition_ajax.ftl")})
public class ManagePositionAction extends BaseAction {
    
    @Resource
    private AdPositionService adPositionService;
    
    @Resource
    private PublicationService publicationService;
    
    public String execute(){
        adUser = (AdUser) ActionContext.getContext().getSession().get(WebConstant.SESSION.ADUSER);
        if(adUser!=null){
            if(adUser.getLevel()==PojoConstant.ADUSER.LEVEL_PUBLISHER){
                publicationList=publicationService.getListByNameAndPublisherId(this.name, adUser.getId(), null, null,-1);
            }else if(adUser.getLevel()==PojoConstant.ADUSER.LEVEL_ADMAGME){
                publicationList=publicationService.getListByNameAndPublisherId(this.name, null, null, null,-1);
            }else{
                return null;
            }
            
            if(publicationList.size()>0){
                this.publicationId=publicationList.get(0).getId();
                getAdPositionAjax();
            }else{
                this.issueList=new ArrayList<Map>();
            }
            return "success";
        }else{
            return null;
        }
    }
    
    public String getAdPositionAjax(){
        this.issueList=adPositionService.getIssueListByPublicationId(
                this.publicationId, 
                PageBar.getBegin(this.pageNum, this.pageSize), 
                PageBar.getEnd(this.pageSize));
        Integer count=adPositionService.getIssueCountByPublicationId(this.publicationId);
        this.pageBar=new PageBar(this.pageNum, this.pageSize, count, "turnPage", new String[]{this.publicationId+""});
        return "getAdPositionAjax";
    }    
    
    private AdUser adUser;
    private List<Publication> publicationList;
    
    @SuppressWarnings("unchecked")
    private List<Map> issueList;
    private Long publicationId;
    private Integer totalCount;
    private Integer begin;
    private Integer size;
    
    private String name;

    public void setAdUser(AdUser adUser) {
        this.adUser = adUser;
    }

    public List<Publication> getPublicationList() {
        return publicationList;
    }

    public void setPublicationList(List<Publication> publicationList) {
        this.publicationList = publicationList;
    }

    @SuppressWarnings("unchecked")
    public List<Map> getIssueList() {
        return issueList;
    }

    @SuppressWarnings("unchecked")
    public void setIssueList(List<Map> issueList) {
        this.issueList = issueList;
    }

    public Long getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(Long publicationId) {
        this.publicationId = publicationId;
    }

    public Integer getBegin() {
        return begin;
    }

    public void setBegin(Integer begin) {
        this.begin = begin;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AdUser getAdUser() {
        return adUser;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
    
}
