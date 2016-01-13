/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action;

import javax.annotation.Resource;

import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.ActionLog;
import cn.magme.pojo.UserSubscribe;
import cn.magme.service.ActionLogService;
import cn.magme.service.UserSubscribeService;

/**
 * @author jacky_zhou
 * @date 2011-6-3
 * @version $id$
 */
@SuppressWarnings("serial")
public class UserSubscribeAction extends BaseAction {

    @Resource
    private UserSubscribeService userSubscribeService;
    
    @Resource
    private ActionLogService actionLogService;
    
    /**
     * 新增订阅
     * @return
     */
    public String addJson(){
        UserSubscribe userSubscribe=new UserSubscribe();
        userSubscribe.setIssueId(issueId);
        userSubscribe.setUserId(this.getSessionUserId());
        this.jsonResult=userSubscribeService.insertUserSubscribe(userSubscribe);
        if(jsonResult.getCode()==JsonResult.CODE.SUCCESS){
            //纪录用户的操作日志
            ActionLog actionLog=this.generateActionLog(
                    PojoConstant.ACTIONLOG.ACTIONTYPEID_SUBSCRIBE,
                    ((UserSubscribe)this.jsonResult.getData().get("userSubscribe")).getId());
            actionLogService.insertActionLog(actionLog); 
        }  
        return JSON;
    }
    
    /**
     * 取消订阅
     * @return
     */
    public String deleteJson(){
        UserSubscribe userSubscribe=new UserSubscribe();
        userSubscribe.setUserId(this.getSessionUserId());
        userSubscribe.setIssueId(this.issueId);
        this.jsonResult=userSubscribeService.deleteSubscribe(userSubscribe);
        return JSON;
    }
    
    private Long id;
    private Long publicationId;
    private Long issueId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(Long publicationId) {
        this.publicationId = publicationId;
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }
}
