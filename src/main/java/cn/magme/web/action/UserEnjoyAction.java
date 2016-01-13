/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action;

import javax.annotation.Resource;

import cn.magme.service.UserEnjoyService;
import cn.magme.service.UserService;

/**
 * 用户图片
 * @author jacky_zhou
 * @date 2011-10-8
 * @version $id$
 */
@SuppressWarnings("serial")
public class UserEnjoyAction  extends BaseAction {

    @Resource
    private UserEnjoyService userEnjoyService;
    
    @Resource
    private UserService userService;
    
    public String changeJson(){
        this.jsonResult=userEnjoyService.change(this.getSessionUserId(), objectId, type);
        userService.setUserEnjoyList(this.getSessionUser());
        return JSON;
    }
    
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
}
