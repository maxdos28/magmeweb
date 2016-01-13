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
import cn.magme.pojo.stat.DmVisitOs;
import cn.magme.service.stat.DmVisitOsService;
import cn.magme.web.action.BaseAction;

/**
 * @author fredy.liu
 * @date 2012-3-23
 * @version $id$
 */
@SuppressWarnings("serial")
@Results({@Result(name="success",location="/WEB-INF/pages/stat/dmvisitos.ftl")})
public class DmVisitOsAction extends BaseAction {
	
	private static final Logger log=Logger.getLogger(DmVisitOsAction.class);
	@Resource
	private DmVisitOsService dmVisitOsService;
	
	public String execute(){
		return SUCCESS;
	}
	
	public String queryVisitOsJson(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		try {
			List<DmVisitOs> visitOsList=dmVisitOsService.queryByDateAndPagenator(startDate, endDate, startRow, pagesize);
			Integer totalNum=dmVisitOsService.queryTotalCountByDateAndPagenator(startDate, endDate, startRow, pagesize);
			this.jsonResult.put("visitOsList", visitOsList);
			this.jsonResult.put("totalNum", totalNum);
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
