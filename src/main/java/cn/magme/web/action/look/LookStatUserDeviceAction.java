package cn.magme.web.action.look;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.service.look.LookStatService;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

/**
 * LOOK统计,用户访问设备报表
 * @author jasper
 * @date 2013.11.7
 *
 */
@Results({@Result(name="success",location="/WEB-INF/pages/looker/stat/statUserDevice.ftl")})
public class LookStatUserDeviceAction extends BaseAction {
	@Resource
	private LookStatService lookStatService;
	private static final Logger log=Logger.getLogger(LookStatUserDeviceAction.class);
	
	private Long appId = 903L;//默认APP

	private String startDate;
	private String endDate;
	private String os;
	
	public String execute()
	{
		return SUCCESS;
	}
	
	//查询
	public String searchUserDeviceJson()
	{
		this.jsonResult=JsonResult.getFailure();
		List<Map> l = this.lookStatService.searchUserDevice(startDate, endDate, os, appId);
		if(l!=null&&l.size()>0)
		{
			Long total = 0L;
			for(Map m:l)
			{
				if(m.get("num")!=null)
				{
					total+=new Long(m.get("num").toString());
				}
			}
			if(total==0)
				return JSON;
			List<Map> ll = new ArrayList();
			BigDecimal bt = new BigDecimal(total);
			for(Map m:l)
			{
				Map nm = new HashMap();
				//按系统
				if(StringUtil.isBlank(os))
				{
					nm.put("os", m.get("os"));
				}
				//按版本
				else
				{
					nm.put("os", m.get("osVersion"));
				}
				BigDecimal num = (BigDecimal)m.get("num");
				if(num==null)
					continue;
				nm.put("num", num);
				nm.put("percent", num.multiply(new BigDecimal(100)).divide(bt, 2, BigDecimal.ROUND_UP));
				ll.add(nm);
			}

			this.jsonResult.put("total", total);
			this.jsonResult.put("resultList", ll);
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

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}
}
