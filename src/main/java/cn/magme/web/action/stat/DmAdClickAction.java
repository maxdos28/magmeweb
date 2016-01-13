/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.stat;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.pojo.stat.StatDmPagenator;
import cn.magme.result.stat.StatCommonResult;
import cn.magme.service.stat.DmAdClickService;
import cn.magme.web.action.BaseAction;

/**
 * @author fredy.liu
 * @date 2012-3-23
 * @version $id$
 */
@SuppressWarnings("serial")
@Results({@Result(name="success",location="/WEB-INF/pages/stat/dmadclick.ftl")})
public class DmAdClickAction extends BaseAction {
	
	private static final Logger log=Logger.getLogger(DmAdClickAction.class);
	@Resource
	private DmAdClickService dmAdClickService;
	
	public String execute(){
		return SUCCESS;
	}
	
	public String queryAdClickJson(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		try {
			StatDmPagenator pagenator=new StatDmPagenator();
			pagenator.setEndDate(endDate);
			pagenator.setPagesize(pagesize);
			pagenator.setStartDate(startDate);
			pagenator.setStartRow(startRow);
			List<StatCommonResult> adClickList=dmAdClickService.queryByPagenator(pagenator);
			this.jsonResult.put("showType", showType);
			this.jsonResult.put("adClickList", adClickList);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		} catch (Exception e) {
			log.error("", e);
		}
		return JSON;
	}
	
	    private Date startDate;
		
		private Date endDate;
		
		private Integer startRow=0;
		
		private Integer pagesize=1000;
		
		private String showType;
		
		
		public String getShowType() {
			return showType;
		}

		public void setShowType(String showType) {
			this.showType = showType;
		}


		public Date getStartDate() {
			return startDate;
		}

		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}

		public Date getEndDate() {
			return endDate;
		}

		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}

		public Integer getStartRow() {
			return startRow;
		}

		public void setStartRow(Integer startRow) {
			this.startRow = startRow;
		}

		public Integer getPagesize() {
			return pagesize;
		}

		public void setPagesize(Integer pagesize) {
			this.pagesize = pagesize;
		}

}
