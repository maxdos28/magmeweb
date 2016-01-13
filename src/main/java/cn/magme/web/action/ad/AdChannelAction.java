package cn.magme.web.action.ad;

import javax.annotation.Resource;

import cn.magme.common.JsonResult;
import cn.magme.pojo.ChannelAd;
import cn.magme.service.ChannelAdService;
import cn.magme.web.action.BaseAction;

/**
 * 插页广告获取
 * @author jasper
 * @date 2013.8.12
 *
 */
public class AdChannelAction extends BaseAction {

	@Resource
	private ChannelAdService channelAdService;
	private Long issueId;
	public String execute()
	{
		this.jsonResult = JsonResult.getFailure();
		ChannelAd ca = channelAdService.getBySortId(issueId);
		if(ca==null)
		{
			this.jsonResult.setMessage("无数据");
			return JSON;
		}
		this.jsonResult.put("content", ca.getContent());
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}
	public Long getIssueId() {
		return issueId;
	}
	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}
	
}
