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
import cn.magme.pojo.stat.DmVisitBrowser;
import cn.magme.service.stat.DmVisitBrowserService;
import cn.magme.web.action.BaseAction;

/**
 * @author fredy.liu
 * @date 2012-3-23
 * @version $id$
 */
@Results({@Result(name="success",location="/WEB-INF/pages/stat/dmvisitbrowser.ftl")})
public class DmVisitBrowserAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3494983239345671179L;
	
	private static final Logger log=Logger.getLogger(DmVisitBrowserAction.class);
	@Resource
	private DmVisitBrowserService dmVisitBrowserService;
	
	public String execute(){
		return SUCCESS;
	}
	
	public String queryVisitBrowserJson(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		try {
			List<DmVisitBrowser> visitbrowserList=dmVisitBrowserService.queryByDateAndPagenator(startDate, endDate, startRow, pagesize);
			Integer totalNum=dmVisitBrowserService.queryTotalCountByDateAndPagenator(startDate, endDate, startRow, pagesize);
			this.jsonResult.put("visitbrowserList", visitbrowserList);
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
