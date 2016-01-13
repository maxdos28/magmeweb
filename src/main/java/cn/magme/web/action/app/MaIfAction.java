package cn.magme.web.action.app;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.service.ma.MaIfService;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;
/**
 * MA广告接口
 * @author jasper
 * @date 2014.2.12
 *
 */
@Results({
	@Result(name = "success", location = "/WEB-INF/pages/ma/maAdPage.ftl") })
public class MaIfAction extends BaseAction {

	@Resource
	private MaIfService maIfService;
	private String adId;
	private String adScript;
	private Integer hasAd;
	private String callback;
	private Long adHeadId;
	
	/**
	 * 根据广告ID得到广告地址
	 * @return
	 */
	public String getMaUrl()
	{
		this.jsonResult = JsonResult.getFailure();
		if(StringUtil.isBlank(adId))
		{
			adScript = null;
			adHeadId = null;
			hasAd = 0;
			return JSON;
		}
		this.jsonResult = maIfService.getAdInfo(adId);
		if(this.jsonResult.getCode()!=200)
		{
			adScript = null;
			adHeadId = null;
			hasAd = 0;
		}
		else
		{
			adScript = (String)this.jsonResult.get("adScript");
			adHeadId = (Long)this.jsonResult.get("adHeadId");
			hasAd = 1;
		}
		return SUCCESS;
	}
	public String getAdId() {
		return adId;
	}
	public void setAdId(String adId) {
		this.adId = adId;
	}
	
	public String getAdScript() {
		return adScript;
	}
	public void setAdScript(String adScript) {
		this.adScript = adScript;
	}
	public String getCallback() {
		return callback;
	}
	public void setCallback(String callback) {
		this.callback = callback;
	}
	public Integer getHasAd() {
		return hasAd;
	}
	public void setHasAd(Integer hasAd) {
		this.hasAd = hasAd;
	}
	public Long getAdHeadId() {
		return adHeadId;
	}
	public void setAdHeadId(Long adHeadId) {
		this.adHeadId = adHeadId;
	}

}
