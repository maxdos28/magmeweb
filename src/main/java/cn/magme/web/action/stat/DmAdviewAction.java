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
import cn.magme.constants.stat.AdTypeConstant;
import cn.magme.pojo.stat.DmAdView;
import cn.magme.result.stat.StatCommonResult;
import cn.magme.service.stat.DmAdViewService;
import cn.magme.web.action.BaseAction;

/**
 * @author fredy.liu
 * @date 2012-3-22
 * @version $id$
 */
@Results({@Result(name="success",location="/WEB-INF/pages/stat/dmadview.ftl"),@Result(name="embedSuccess",location="/WEB-INF/pages/stat/dmadviewembed.ftl")})
public class DmAdviewAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1236911448045699019L;
	
	private static final Logger log=Logger.getLogger(DmAdviewAction.class);
	
	@Resource
	private DmAdViewService dmAdViewService;
	
	
	public String execute(){
		dmAdviewList=this.dmAdViewService.queryAllDistinctAdByType(AdTypeConstant.TYPE_INSERT_AD);
		return SUCCESS;
	}
	
	public String queryInsertAdJson(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		try {
			List<StatCommonResult> dmAdviewList=dmAdViewService.queryByStartEndDateAdidAdTypeAndPagenator(startDate, endDate, adId, AdTypeConstant.TYPE_INSERT_AD, startRow, pagesize);
			this.jsonResult.put("dmAdviewList", dmAdviewList);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		} catch (Exception e) {
			log.error("", e);
		}
		return JSON;
	}
	
	public String queryEmbed(){
		return "embedSuccess";
	}
	
	public String queryEmbedAdJson(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		try {
			List<StatCommonResult> dmAdviewList=dmAdViewService.queryByStartEndDateAdidAdTypeAndPagenator(startDate, endDate, adId, AdTypeConstant.TYPE_EMBED_AD, startRow, pagesize);
			this.jsonResult.put("dmAdviewList", dmAdviewList);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		} catch (Exception e) {
			log.error("", e);
		}
		return JSON;
	}
	
	private List<DmAdView> dmAdviewList;
	
	public List<DmAdView> getDmAdviewList() {
		return dmAdviewList;
	}

	public void setDmAdviewList(List<DmAdView> dmAdviewList) {
		this.dmAdviewList = dmAdviewList;
	}

	private Date startDate;
	
	private Date endDate;
	
	private Long adId;

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

	public Long getAdId() {
		return adId;
	}

	public void setAdId(Long adId) {
		this.adId = adId;
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
