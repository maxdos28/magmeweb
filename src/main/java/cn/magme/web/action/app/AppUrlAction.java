package cn.magme.web.action.app;

import java.util.List;

import javax.annotation.Resource;

import cn.magme.common.JsonResult;
import cn.magme.pojo.app.AppUrl;
import cn.magme.service.app.AppUrlService;
import cn.magme.web.action.BaseAction;

/**
 * 根据appId返回对应的互动杂志\PDF杂志\文章\图片URL地址
 * 杂志:URL+publicationId+issueId
 * 文章:URL+articleId
 * 图片:URL+接口返回地址
 * @author jasper
 * @date 2013.11.26
 *
 */
public class AppUrlAction extends BaseAction {
	@Resource
	private AppUrlService appUrlService;
	private Long appId;
	public String getAppUrl()
	{
		this.jsonResult = JsonResult.getFailure();
		if(appId==null||appId<=0)
		{
			this.jsonResult.setMessage("APP ID为空");
			return JSON;
		}
		List<AppUrl> l = this.appUrlService.getAppUrlList(appId);
		if(l!=null&&l.size()>0)
		{
			for(AppUrl au:l)
			{
				this.jsonResult.put(au.getType().toString(), au.getAppUrl());
			}
		}
		else
		{
			this.jsonResult.setMessage("未得到URL");
			return JSON;
		}
		
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}
	public Long getAppId() {
		return appId;
	}
	public void setAppId(Long appId) {
		this.appId = appId;
	}

}
