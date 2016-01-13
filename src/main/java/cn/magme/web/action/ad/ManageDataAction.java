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
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;

import com.opensymphony.xwork2.ActionContext;

import cn.magme.common.PageBar;
import cn.magme.common.SystemProp;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.AdData;
import cn.magme.pojo.AdUser;
import cn.magme.service.AdStatCvService;
import cn.magme.web.action.BaseAction;

/**
 * @author shenhao
 * @date 2011-10-18
 * @version $id$
 * 广告数据
 */
@SuppressWarnings("serial")
@Results({@Result(name="success",location="/WEB-INF/pages/ad/manageAdData.ftl")})
public class ManageDataAction extends BaseAction {
    
    @Resource
    private AdStatCvService adStatCvService;
    
    public AdUser adUser;
    private String adname;
    private String issuenm;
    private Date begintime;
    private Date endtime;
    private List<AdData> adDataLst;
    private String advertiseId;
    private int type;
    
    public String execute(){
        adUser = (AdUser) ActionContext.getContext().getSession().get("session_aduser");
        return "success";
    }

    @SuppressWarnings("unchecked")
    public String search() {
        adUser = (AdUser) ActionContext.getContext().getSession().get("session_aduser");
        Map map = new HashMap();
        if (adUser != null) {
            map.put("userid", adUser.getId());
            // 出版商
            if (adUser.getLevel() == PojoConstant.ADUSER.LEVEL_PUBLISHER) {
                map.put("usertypeid", PojoConstant.ADVERTISE.USER_TYPE_PUBLISHER);
            // 广告商
            }else if(adUser.getLevel() == PojoConstant.ADUSER.LEVEL_ADAGENCY) {
                map.put("usertypeid", PojoConstant.ADVERTISE.USER_TYPE_ADAGENCY);
            // 麦米商
            }else {
                map.put("usertypeid", PojoConstant.ADVERTISE.USER_TYPE_ADMAGME);
            }
        }
        map.put("adname", adname);
        map.put("issuenm", issuenm);
        map.put("advertiseId", this.advertiseId);
        map.put("begintime", begintime);
        map.put("endtime", endtime);
        map.put("type", this.type);
        
        map.put("begin", PageBar.getBegin(this.pageNum, this.pageSize));
        map.put("size", PageBar.getEnd(this.pageSize));
        adDataLst = adStatCvService.selStatLst(map);
        
        int count = adStatCvService.selStatLstCount(map);

        this.pageBar=new PageBar(this.pageNum, this.pageSize, count, "turnPage", null);

        return "success";
    }
    
    public AdUser getAdUser() {
        return adUser;
    }

    public void setAdUser(AdUser adUser) {
        this.adUser = adUser;
    }
    public String getAdname() {
        return adname;
    }

    public void setAdname(String adname) {
        this.adname = adname;
    }

    public String getIssuenm() {
        return issuenm;
    }

    public void setIssuenm(String issuenm) {
        this.issuenm = issuenm;
    }

    public Date getBegintime() {
        return begintime;
    }

    public void setBegintime(Date begintime) {
        this.begintime = begintime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public List<AdData> getAdDataLst() {
        return adDataLst;
    }

    public void setAdDataLst(List<AdData> adDataLst) {
        this.adDataLst = adDataLst;
    }
    
    public String getAdvertiseId() {
        return advertiseId;
    }

    public void setAdvertiseId(String advertiseId) {
        this.advertiseId = advertiseId;
    }
    
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
