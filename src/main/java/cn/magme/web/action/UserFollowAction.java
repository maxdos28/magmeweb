/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action;

import javax.annotation.Resource;

import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.ActionLog;
import cn.magme.pojo.UserFollow;
import cn.magme.service.ActionLogService;
import cn.magme.service.UserFollowService;

/**
 * @author jacky_zhou
 * @date 2011-5-27
 * @version $id$
 */
@SuppressWarnings("serial")
public class UserFollowAction extends BaseAction {

    @Resource
    private UserFollowService userFollowService;
    
    @Resource
    private ActionLogService actionLogService;
    
    /**
     * 删除关注
     * @return
     */
    public String deleteJson(){
        this.jsonResult=userFollowService.change(this.getSessionUserId(), objectId, type);
        if(jsonResult.getCode()==JsonResult.CODE.SUCCESS){
            //纪录用户的操作日志
            ActionLog actionLog=this.generateActionLog(
                    PojoConstant.ACTIONLOG.ACTIONTYPEID_FOLLOW,
                    ((UserFollow)this.jsonResult.getData().get("userFollow")).getId());
            actionLogService.insertActionLog(actionLog); 
        }
        return JSON;
    }
    
    public String addFollowJson(){
        this.jsonResult=userFollowService.change(this.getSessionUserId(), objectId, type);
        if(jsonResult.getCode()==JsonResult.CODE.SUCCESS){
            //纪录用户的操作日志
            ActionLog actionLog=this.generateActionLog(
                    PojoConstant.ACTIONLOG.ACTIONTYPEID_FOLLOW,
                    ((UserFollow)this.jsonResult.getData().get("userFollow")).getId());
            actionLogService.insertActionLog(actionLog); 
        }
		return JSON;
	}
    public String addFollowJsons(){
//    	JsonResult result = JsonResult.getSuccess();
    	if(ids != null && !"".equals(ids)){
	    	Long userId = this.getSessionUserId();
	    	String[] arr = ids.split("_");
	    	Long[] idArrs = strArrToLongArr(arr);
	    	for (Long id : idArrs) {
	    		JsonResult rs = userFollowService.addUserFollow(userId, id, PojoConstant.USERFOLLOW.TYPE_USER);
	//    		if(rs.getCode() == JsonResult.CODE.FAILURE){
	//    			result = JsonResult.getFailure();
	//    		}
			}
    	}
//    	
//		this.jsonResult = result;
//    	if(jsonResult.getCode()==JsonResult.CODE.SUCCESS){
//    		//纪录用户的操作日志
//    		ActionLog actionLog=this.generateActionLog(
//    				PojoConstant.ACTIONLOG.ACTIONTYPEID_FOLLOW,
//    				((UserFollow)this.jsonResult.getData().get("userFollow")).getId());
//    		actionLogService.insertActionLog(actionLog); 
//    	}
    	return JSON;
    }

    private String ids;
    private Long objectId;
    private Integer type;

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getIds() {
		return ids;
	}
    
}
