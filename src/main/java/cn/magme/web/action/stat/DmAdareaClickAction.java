/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.stat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.result.stat.DmDataMapResult;
import cn.magme.result.stat.DmDataMapResultPvComparator;
import cn.magme.result.stat.DmDataMapResultUvComparator;
import cn.magme.service.stat.DmAdAreaClickService;
import cn.magme.service.stat.DmAdAreaViewService;
import cn.magme.web.action.BaseAction;

/**
 * @author fredy.liu
 * @date 2012-3-23
 * @version $id$
 */
@SuppressWarnings("serial")
@Results({@Result(name="success",location="/WEB-INF/pages/stat/dmadareaclick.ftl")})
public class DmAdareaClickAction extends BaseAction {

	private static final Logger log=Logger.getLogger(DmAdareaClickAction.class);
	
	private static final Long LAST_7_DAY=7L*24*60*60*1000;
	
	@Resource
	private DmAdAreaClickService dmAdAreaClickService;
	
	
	@Resource
	private DmAdAreaViewService dmAdAreaViewService;
	
	
	public String execute(){
		
		return SUCCESS;
	}
	
	public String queryAreaAdJson(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		if(startDate==null && endDate==null){
			endDate=new Date();
			startDate=new Date(endDate.getTime()-LAST_7_DAY);
		}
		try {
			
			List<DmDataMapResult> adareaclickList=dmAdAreaClickService.queryByDateAndPagenator(startDate, endDate, startRow, pagesize);
			List<DmDataMapResult> adareaViewList=dmAdAreaViewService.queryByDateAndPagenator(startDate, endDate, startRow, pagesize);
			Map<String,DmDataMapResult> dataMap=new HashMap<String,DmDataMapResult>();
			
			Integer uvcount=0;
			Integer pvcount=0;
			
			for(DmDataMapResult dataClick:adareaclickList){
				dataMap.put(dataClick.getProvince(), dataClick);
				
				if(dataClick.getClickNumber()!=null)
					uvcount+=dataClick.getClickNumber();
			}
			
			for(DmDataMapResult dataView:adareaViewList){
				DmDataMapResult dataClick=dataMap.get(dataView.getProvince());
				if(dataClick!=null){
					dataClick.setViewNumber(dataView.getViewNumber());
				}else{
					dataMap.put(dataView.getProvince(), dataView);
				}
				
				if(dataView.getViewNumber()!=null)
					pvcount+=dataView.getViewNumber();
			}
			
			List<DmDataMapResult> dataList=new ArrayList<DmDataMapResult>();
			for(String str:dataMap.keySet()){
				dataList.add(dataMap.get(str));
			}
			
			//排序
			if(this.order==1 || this.order==2){
				Collections.sort(dataList,new DmDataMapResultPvComparator());	
			}else{
				Collections.sort(dataList,new DmDataMapResultUvComparator());	
			}
			if(this.order==2 || this.order==4){
				Collections.reverse(dataList);
			}
			
			
			this.jsonResult.put("datamapList",dataList );
			this.jsonResult.put("pvcount",pvcount);
			this.jsonResult.put("uvcount",uvcount);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		} catch (Exception e) {
			log.error("", e);
		}
		return JSON;
	}
	
	private Integer menuId;
	
    private Date startDate;
	
	private Date endDate;
	
	private Integer startRow=0;
	
	private Integer pagesize=1000;
	
	private Integer order=1;
	
	
	
	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
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
