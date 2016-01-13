package cn.magme.web.action.look;

import java.io.File;
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
import cn.magme.common.Page;
import cn.magme.pojo.look.LooStartPic;
import cn.magme.service.look.LookStartPicService;
import cn.magme.service.look.LookStatService;
import cn.magme.util.FileOperate;
import cn.magme.util.ImageUtil;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

/**
 * LOOK统计,用户访问地区报表
 * @author jasper
 * @date 2013.11.7
 *
 */
@Results({@Result(name="success",location="/WEB-INF/pages/looker/stat/statUserArea.ftl")})
public class LookStatUserAreaAction extends BaseAction {
	@Resource
	private LookStatService lookStatService;
	private static final Logger log=Logger.getLogger(LookStatUserAreaAction.class);
	
	private Long appId = 903L;//默认APP

	private String startDate;
	private String endDate;
	
	public String execute()
	{
		return SUCCESS;
	}
	
	//查询
	public String searchUserAreaJson()
	{
		this.jsonResult=JsonResult.getFailure();
		List<Map> l = this.lookStatService.searchUserArea(startDate, endDate, appId);
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
				BigDecimal num = (BigDecimal)m.get("num");
				if(num==null)
					continue;
				nm.put("province", m.get("province"));
				nm.put("viewNumber", num);
				nm.put("percent", num.multiply(new BigDecimal(100)).divide(bt, 2, BigDecimal.ROUND_UP));
				ll.add(nm);
			}

			this.jsonResult.put("pvcount", total);
			this.jsonResult.put("datamapList", ll);
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
