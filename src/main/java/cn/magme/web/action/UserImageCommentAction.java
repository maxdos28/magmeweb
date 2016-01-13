/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.ActionLog;
import cn.magme.pojo.ContentInfo;
import cn.magme.pojo.User;
import cn.magme.pojo.UserImageComment;
import cn.magme.service.ActionLogService;
import cn.magme.service.UserImageCommentService;

/**
 * @author jacky_zhou
 * @date 2011-10-12
 * @version $id$
 */
@Results({@Result(name="now",location="/WEB-INF/pages/userImage/now.ftl"),
    @Result(name="hot",location="/WEB-INF/pages/userImage/hot.ftl"),
    @Result(name="commend",location="/WEB-INF/pages/userImage/commend.ftl"),
    @Result(name="listAjax",location="/WEB-INF/pages/userImage/listAjax.ftl"),
    @Result(name="show",location="/WEB-INF/pages/userImage/show.ftl")})
@SuppressWarnings("serial")
public class UserImageCommentAction extends BaseAction{

    @Resource
    private UserImageCommentService userImageCommentService;
    
    @Resource
    private ActionLogService actionLogService;
    
    /**
     * 新增用户图片评论
     * @return
     */
    public String addJson(){
        User user=this.getSessionUser();
        UserImageComment comment=new UserImageComment();
        comment.setImageId(this.imageId);
        ContentInfo contentInfo=new ContentInfo();
        contentInfo.setContent(this.content);
        comment.setContentInfo(contentInfo);
        comment.setUserId(user.getId());
        comment.setIpAddress(this.getRequestIpLong());
        comment.setUserAvatar(user.getAvatar());
        comment.setUserNickName(user.getNickName());
        this.jsonResult=userImageCommentService.insert(comment);
        if(jsonResult.getCode()==JsonResult.CODE.SUCCESS){
            //纪录用户的操作日志
            ActionLog actionLog=this.generateActionLog(
                    PojoConstant.ACTIONLOG.ACTIONTYPEID_USERIMAGECOMMENT,
                    ((UserImageComment)this.jsonResult.getData().get("userImageComment")).getId());
            actionLogService.insertActionLog(actionLog); 
        } 
        return JSON;
    } 
    
    private Long imageId;
    private String content;

    public Long getImageId() {
        return imageId;
    }
    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
