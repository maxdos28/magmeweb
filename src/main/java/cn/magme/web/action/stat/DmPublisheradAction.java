/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.stat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.constants.stat.AdTypeConstant;
import cn.magme.result.stat.StatCommonResult;
import cn.magme.service.stat.DmPublisherAdService;
import cn.magme.web.action.BaseAction;

/**
 * @author fredy.liu
 * @date 2012-3-22
 * @version $id$
 */
@Results({@Result(name="success",location="/WEB-INF/pages/stat/dmpublisherad.ftl")})
public class DmPublisheradAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2268843899668776667L;
	private static final Logger log=Logger.getLogger(DmPublisheradAction.class);
	
	@Resource
	private DmPublisherAdService dmPublisherAdService;
	
	
	public String execute(){
		return SUCCESS;
	}
	
	public String queryPublisherAdJson(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		try {
			List<StatCommonResult> list = new ArrayList<StatCommonResult>();
			if(null!=ad_type && ad_type>0){
				if(ad_type==1)
					list=dmPublisherAdService.queryByAdTypePublisherIdAndPagenator(startDate, endDate, publisherId, AdTypeConstant.TYPE_INSERT_AD, startRow, pagesize);
				else if(ad_type==2 || ad_type==4)
					list=dmPublisherAdService.queryByAdTypePublisherIdAndPagenator(startDate, endDate, publisherId, AdTypeConstant.TYPE_EMBED_AD, startRow, pagesize);
				else
					list=dmPublisherAdService.queryByAdTypePublisherIdAndPagenator(startDate, endDate, publisherId, AdTypeConstant.TYPE_RIGHT_AD, startRow, pagesize);
			}
			this.jsonResult.put("list", list);
			this.jsonResult.put("ad_tp", ad_type);
//			List<StatCommonResult> embedAdList=dmPublisherAdService.queryByAdTypePublisherIdAndPagenator(startDate, endDate, publisherId, AdTypeConstant.TYPE_EMBED_AD, startRow, pagesize);
//			List<StatCommonResult> insertAdList=dmPublisherAdService.queryByAdTypePublisherIdAndPagenator(startDate, endDate, publisherId, AdTypeConstant.TYPE_INSERT_AD, startRow, pagesize);
//			List<StatCommonResult> rightAdList=dmPublisherAdService.queryByAdTypePublisherIdAndPagenator(startDate, endDate, publisherId, AdTypeConstant.TYPE_RIGHT_AD, startRow, pagesize);
//			this.jsonResult.put("embedAdList", embedAdList);
//			this.jsonResult.put("insertAdList", insertAdList);
//			this.jsonResult.put("rightAdList", rightAdList);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		} catch (Exception e) {
			log.error("", e);
		}
		return JSON;
	}
	
	
	private Date startDate;
	
	private Date endDate;
	
	private Long publisherId;

	private Integer startRow=0;
	
	private Integer pagesize=1000;
	
	private Integer ad_type=0;

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

	public Long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
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
	
	public Integer getAd_type() {
		return ad_type;
	}

	public void setAd_type(Integer adType) {
		ad_type = adType;
	}
	

}
