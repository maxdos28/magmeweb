package cn.magme.web.action.ma;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.common.Page;
import cn.magme.pojo.ma.MaAdHead;
import cn.magme.service.ma.MaAdTradeService;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;
/**
 * 广告管理
 * @author jasper
 * @date 2014.3.13
 *
 */
@Results({@Result(name="success",location="/WEB-INF/pages/ma/adTradeList.ftl")})
public class AdTradeAction extends BaseAction {
	
	@Resource
	private MaAdTradeService maAdTradeService;
	
	private Integer currentPage;
	private String statusStr;
	private String title;
	private String startDate;
	private String endDate;
	private Long id;
	
	public String execute()
	{
		return SUCCESS;
	}
	
	/**
	 * 查询广告交易
	 * @return
	 */
	public String searchAdTrade()
	{
		this.jsonResult = JsonResult.getFailure();
		if (currentPage == null || currentPage <= 0)
			currentPage = 1;
		List<Byte> status = null;
		if(StringUtil.isNotBlank(statusStr))
		{
			status = new ArrayList();
			String[] ss = statusStr.split(",");
			for(String s:ss)
				status.add(new Byte(s));
		}
		Page p = maAdTradeService.searchAdTrade(status, title, startDate, endDate, currentPage);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		List<MaAdHead> rl = p.getResults();
		this.jsonResult.put("commondatas", rl);
		this.jsonResult.put("pageNo", p.getTotalPage());
		return JSON;
	}

	/**
	 * 删除广告交易
	 * @return
	 */
	public String deleteAdTrade()
	{
		this.jsonResult = JsonResult.getFailure();
		if(id==null||id<0)
		{
			this.jsonResult.setMessage("id为空");
			return JSON;
		}
		int r = this.maAdTradeService.deleteAd(id);
		if(r<=0)
		{
			this.jsonResult.setMessage("操作失败");
			return JSON;
		}
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}

	/**
	 * 广告交易信息
	 * @return
	 */
	public String adTradeInfo()
	{
		this.jsonResult = JsonResult.getFailure();
		if(id==null||id<0)
		{
			this.jsonResult.setMessage("id为空");
			return JSON;
		}
		Map r = this.maAdTradeService.getAdHeadInfo(id);
		if(r==null)
		{
			this.jsonResult.setMessage("未得到信息");
			return JSON;
		}
		this.jsonResult.put("adHeadInfo",r.get("adHeadInfo"));
		this.jsonResult.put("adHeadKeyword",r.get("adHeadKeyword"));
		this.jsonResult.put("adHeadSize",r.get("adHeadSize"));
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}

	/**
	 * 发布广告交易
	 * @return
	 */
	public String releaseAdTrade()
	{
		this.jsonResult = JsonResult.getFailure();
		if(id==null||id<0)
		{
			this.jsonResult.setMessage("id为空");
			return JSON;
		}
		int r = this.maAdTradeService.releaseAdTrade(id);
		if(r<=0)
		{
			this.jsonResult.setMessage("操作失败");
			return JSON;
		}
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
