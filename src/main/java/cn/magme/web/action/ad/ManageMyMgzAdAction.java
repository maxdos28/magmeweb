/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.ad;

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
import cn.magme.pojo.AdUser;
import cn.magme.pojo.Advertise;
import cn.magme.service.AdvertiseService;
import cn.magme.web.action.BaseAction;

import com.opensymphony.xwork2.ActionContext;

/**
 * @author shenhao
 * @date 2011-10-18
 * @version $id$
 * 我期刊的广告
 */
@SuppressWarnings("serial")
@Results({@Result(name="success",location="/WEB-INF/pages/ad/manageAdMyMgzAd.ftl"),
    @Result(name="queryAjax",location="/WEB-INF/pages/ad/manageAdMyMgzAd_queryAjax.ftl")})
public class ManageMyMgzAdAction extends BaseAction {
    @Resource
    private AdvertiseService advertiseService;
    
    public String execute(){
        adUser = (AdUser) ActionContext.getContext().getSession().get(WebConstant.SESSION.ADUSER);
        if(adUser!=null){
            Map<String,Object> map=new HashMap<String,Object>();
            if(PojoConstant.ADUSER.LEVEL_PUBLISHER==adUser.getLevel()){
                map.put("publisherId", adUser.getId());
            }else{
                return null;
            }
            map.put("title", this.title);
            map.put("description", this.description);
            map.put("createdTimeBegin", this.createdTimeBegin);
            map.put("createdTimeEnd", this.createdTimeEnd);
            map.put("issueId", issueId);
            map.put("begin", PageBar.getBegin(this.pageNum, this.pageSize));
            map.put("size", PageBar.getEnd(this.pageSize));
            this.advertiseList=advertiseService.getListByMap(map);
            Integer count=advertiseService.getCountByMap(map);
            this.pageBar=new PageBar(this.pageNum, this.pageSize, count, "turnPage", null);
        }
        return "success";
    }
    
    public String queryAjax(){
        execute();
        return "queryAjax";
    }

    private AdUser adUser;
    private List<Advertise> advertiseList;
    private String title;
    private String description;
    private Date createdTimeBegin;
    private Date createdTimeEnd;
    private Long issueId;
    
    public AdUser getAdUser() {
        return adUser;
    }

    public void setAdUser(AdUser adUser) {
        this.adUser = adUser;
    }

    public List<Advertise> getAdvertiseList() {
        return advertiseList;
    }

    public void setAdvertiseList(List<Advertise> advertiseList) {
        this.advertiseList = advertiseList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreatedTimeBegin() {
        return createdTimeBegin;
    }

    public void setCreatedTimeBegin(Date createdTimeBegin) {
        this.createdTimeBegin = createdTimeBegin;
    }

    public Date getCreatedTimeEnd() {
        return createdTimeEnd;
    }

    public void setCreatedTimeEnd(Date createdTimeEnd) {
        this.createdTimeEnd = createdTimeEnd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }
    
}
