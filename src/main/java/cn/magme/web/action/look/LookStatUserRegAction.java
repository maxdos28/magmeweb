package cn.magme.web.action.look;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.service.look.LookStatService;
import cn.magme.web.action.BaseAction;

/**
 * LOOK统计,注册用户分布表
 * @author jasper
 * @date 2013.11.7
 *
 */
@Results({@Result(name="success",location="/WEB-INF/pages/looker/stat/statUserReg.ftl")})
public class LookStatUserRegAction extends BaseAction {
	@Resource
	private LookStatService lookStatService;
	private static final Logger log=Logger.getLogger(LookStatUserRegAction.class);
	
	private String startDate;
	private String endDate;
	private Long appId = 903L;//默认APP
	
	
	public String execute()
	{
		return SUCCESS;
	}
	
	//查询
	public String searchUserTypeJson()
	{
		this.jsonResult=JsonResult.getFailure();
		List<Map> l = this.lookStatService.searchUserType(startDate, endDate);
		if(l!=null&&l.size()>0)
		{
			Long total = 0L;
			for(Map m:l)
			{
				Long q = (Long)m.get("num");
				if(q!=null)
					total+=q;
				else
					q = 0L;
				if(m.get("userType").toString().equals("0"))
					this.jsonResult.put("sina", q);
				if(m.get("userType").toString().equals("1"))
					this.jsonResult.put("qq", q);
			}
			this.jsonResult.put("total", total);
			this.jsonResult.put("resultList", l);			
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
}
