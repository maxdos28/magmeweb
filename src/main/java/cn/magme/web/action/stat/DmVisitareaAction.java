/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.stat;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.result.stat.DmDataMapResult;
import cn.magme.result.stat.DmDataMapResultPvComparator;
import cn.magme.result.stat.DmDataMapResultUvComparator;
import cn.magme.service.stat.DmVisitAreaService;
import cn.magme.web.action.BaseAction;

/**
 * @author fredy.liu
 * @date 2012-3-23
 * @version $id$
 */
@Results({@Result(name="success",location="/WEB-INF/pages/stat/dmvisitarea.ftl")})
public class DmVisitareaAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3494983239345671179L;
	
	private static final Logger log=Logger.getLogger(DmVisitareaAction.class);
	
	private static final Long LAST_7_DAY=7L*24*60*60*1000;
	@Resource
	private DmVisitAreaService dmVisitAreaService;
	
	public String execute(){
		return SUCCESS;
	}
	
	public String queryVisitAreaJson(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		if(startDate==null && endDate==null){
			endDate=new Date();
			startDate=new Date(endDate.getTime()-LAST_7_DAY);
		}
		try {
			List<DmDataMapResult> visitareaList=dmVisitAreaService.queryByDateAndPagenator(startDate, endDate, startRow, pagesize);
			
			Integer pvcount=0,uvcount=0;
			for(DmDataMapResult rs : visitareaList){
				if(rs.getClickNumber()!=null)
					uvcount+=rs.getClickNumber();
				if(rs.getViewNumber()!=null)
					pvcount+=rs.getViewNumber();
			}
			
			
			//排序
			if(this.order==1 || this.order==2){
				Collections.sort(visitareaList,new DmDataMapResultPvComparator());	
			}else{
				Collections.sort(visitareaList,new DmDataMapResultUvComparator());	
			}
			if(this.order==2 || this.order==4){
				Collections.reverse(visitareaList);
			}
			
			
			this.jsonResult.put("datamapList", visitareaList);
			this.jsonResult.put("pvcount", pvcount);
			this.jsonResult.put("uvcount", uvcount);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		} catch (Exception e) {
			log.error("", e);
		}
		return JSON;
	}
	
	private Integer order=1;
	
	
	
	 public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
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
