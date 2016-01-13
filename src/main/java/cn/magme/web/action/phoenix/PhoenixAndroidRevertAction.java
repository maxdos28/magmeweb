package cn.magme.web.action.phoenix;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.pojo.phoenix.PhoenixAndroidRevert;
import cn.magme.service.phoenix.PhoenixOrderService;
import cn.magme.web.action.BaseAction;
@Results({ @Result(name = "success", location = "/WEB-INF/pages/phoenix/androidRevert.ftl")})
public class PhoenixAndroidRevertAction extends BaseAction {
	private static final Logger log = Logger
			.getLogger(PhoenixAndroidRevertAction.class);
	@Resource
	private PhoenixOrderService phoenixOrderService;
	private long currentPage;
	private long pageCount;
	private long rowCount;
	private final Long pageSize = 15L;
	
	private Long id;
	
	public String excute()
	{
		return SUCCESS;
	}

	// 后台查询内容
	public String searchRevert() {
		this.jsonResult = JsonResult.getFailure();
		Map<String, Object> map = new HashMap<String, Object>();
		rowCount = phoenixOrderService.countAndroidRevert(map);
		countPage();
		map.put("begin", (currentPage - 1) * pageSize);
		map.put("size", pageSize);
		try {
			if(rowCount>0)
			{
				List<PhoenixAndroidRevert> list = phoenixOrderService
						.queryAndroidRevert(map);
				this.jsonResult.put("list", list);
			}		
			else
				this.jsonResult.put("list", null);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);	
			this.jsonResult.put("currentPage", currentPage);
			this.jsonResult.put("pageCount", pageCount);
		} catch (Exception e) {
			log.error("PhoenixArticleAction.class", e);
			e.printStackTrace();
		}

		return JSON;
	}

	private void countPage() {
		if (rowCount % this.pageSize == 0)
			pageCount = rowCount / this.pageSize;
		else
			pageCount = rowCount / this.pageSize + 1;// 总页数
		if (currentPage <= 0)
			currentPage = 1;
		if (currentPage > pageCount)
			currentPage = pageCount;
	}
	
	//审核还原申请
	public String auditedAndroidRevert()
	{
		this.jsonResult = JsonResult.getFailure();
		if(id==null||id<=0)
		{
			this.jsonResult.setMessage("id为空");
			return JSON;
		}
		phoenixOrderService.auditedAndroidRevert(id);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}

	public long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}

	public long getPageCount() {
		return pageCount;
	}

	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}

	public long getRowCount() {
		return rowCount;
	}

	public void setRowCount(long rowCount) {
		this.rowCount = rowCount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
