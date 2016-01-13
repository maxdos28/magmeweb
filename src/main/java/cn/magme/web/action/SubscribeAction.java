/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action;

import java.util.Map;

import javax.annotation.Resource;

import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant.COMMON;
import cn.magme.pojo.Subscribe;
import cn.magme.service.SubscribeService;
import cn.magme.util.ConvertPojoToMap;

/**
 * @author billy.qi
 * @date 2012-08-24
 * @version $id$
 */
@SuppressWarnings("serial")
public class SubscribeAction extends BaseAction {

	@Resource
	private SubscribeService subscribeService;
	
	private String email;
	private Long objectId;
	private Integer type;
	@Override
    public String execute() {
		this.jsonResult = JsonResult.getFailure();
		Subscribe subscribe = buildSubscribe();
    	Map<String, Object> param = ConvertPojoToMap.convert(subscribe);
    	boolean exist = subscribeService.isExist(param);
    	if(exist){
    		this.jsonResult.setMessage("订阅信息已存在！");
    		return JSON;
    	}
    	subscribeService.add(subscribe);
    	this.jsonResult = JsonResult.getSuccess();
        return JSON;
    }

	private Subscribe buildSubscribe() {
		Subscribe sub = new Subscribe();
		sub.setEmail(email);
		sub.setType(type);
		sub.setObjectId(objectId);
		sub.setStatus(COMMON.STATUS_OK);
		return sub;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
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
