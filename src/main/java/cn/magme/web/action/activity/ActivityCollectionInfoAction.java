package cn.magme.web.action.activity;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import cn.magme.common.JsonResult;
import cn.magme.pojo.ActivityCollectInfo;
import cn.magme.service.ActivityCollectInfoService;
import cn.magme.web.action.BaseAction;

public class ActivityCollectionInfoAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2928266334150860003L;
	
	private static final Logger log=Logger.getLogger(ActivityCollectionInfoAction.class);
	
	@Resource
	private ActivityCollectInfoService activityCollectInfoService;
	
	public String execute(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		try {
			ActivityCollectInfo info=new ActivityCollectInfo();
			info.setContent(content);
			info.setType(1);
			activityCollectInfoService.insert(info);
		} catch (Exception e) {
			this.jsonResult.setCode(JsonResult.CODE.FAILURE);
			this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
			log.error("", e);
		}
		return JSON;
	}
	
	/**
	 * 女友的订阅功能
	 * @return
	 */
	public String nvemailSubscribe(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		try {
			ActivityCollectInfo info=new ActivityCollectInfo();
			info.setContent(content);
			info.setType(2);
			activityCollectInfoService.insert(info);
		} catch (Exception e) {
			this.jsonResult.setCode(JsonResult.CODE.FAILURE);
			this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
			log.error("", e);
		}
		return JSON;
	}
	
	private String content;

	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}
	
	
}
