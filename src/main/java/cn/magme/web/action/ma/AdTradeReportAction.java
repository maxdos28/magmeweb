package cn.magme.web.action.ma;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.service.ma.MaAdTradeService;
import cn.magme.service.ma.MaReportService;
import cn.magme.web.action.BaseAction;

/**
 * 广告交易报表
 * @author jasper
 * @date 2014.3.21
 *
 */
@Results({@Result(name="success",location="/WEB-INF/pages/ma/statAdTrade.ftl")})
public class AdTradeReportAction extends BaseAction {
	
	@Resource
	private MaReportService maReportService;
	@Resource
	private MaAdTradeService maAdTradeService;
	
	private Long adHeadId;
	public String execute()
	{
		Map r = this.maAdTradeService.getAdHeadInfo(adHeadId);
		if(r!=null)
		{
			HttpServletRequest request = ServletActionContext.getRequest();
			request.setAttribute("adHeadInfo",r.get("adHeadInfo"));
			request.setAttribute("adHeadKeyword",r.get("adHeadKeyword"));
			request.setAttribute("adHeadSize",r.get("adHeadSize"));
		}
		return SUCCESS;
	}
	
	/**
	 * 查询广告交易报表
	 * @return
	 */
	public String searchAdTradeReport()
	{
		this.jsonResult=JsonResult.getFailure();
		if(adHeadId==null||adHeadId<=0)
		{
			this.jsonResult.setMessage("id为空");
			return JSON;
		}
		List l = this.maReportService.maAdTradeReport(adHeadId);
		this.jsonResult.put("resultList", l);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}

	public Long getAdHeadId() {
		return adHeadId;
	}

	public void setAdHeadId(Long adHeadId) {
		this.adHeadId = adHeadId;
	}

}
