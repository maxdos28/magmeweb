/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.publish;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.pojo.Publication;
import cn.magme.result.stat.DmDataMapResult;
import cn.magme.service.PublicationService;
import cn.magme.service.stat.DmPublicationAreaService;
import cn.magme.web.action.BaseAction;

/**
 * @author fredy.liu
 * @date 2012-5-24
 * @version $id$
 */
@Results({@Result(name="success",location="/WEB-INF/pages/publish/dmPublicationArea.ftl")})
public class DmPublicationAreaAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1270570654490800437L;

	@Resource
	private DmPublicationAreaService dmPublicationAreaService;
	
	@Resource
	private PublicationService publicationService;
	
	private static final Long SEARCH_DAY_LENGTH=1000L*60*60*24*31;

	private static final Logger log = Logger
			.getLogger(DmPublicationAreaAction.class);

	public String execute() {
		pubList=publicationService.queryNormalByPublisherId(this.getSessionAdUserId());
		return SUCCESS;
	}

	public String pubJson() {
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		try {
			if(this.publicationId==null || this.publicationId<=0){
				pubList=publicationService.queryNormalByPublisherId(this.getSessionAdUserId());
				if(pubList==null || pubList.size()<=0){
					this.jsonResult.setMessage("没有publicationId");
					return JSON;
				}
				this.publicationId=pubList.get(0).getId();
			}
			
			//判断日期，只能查看15天内数据，并且不能查看5月1日之前的数据
			Calendar cal=Calendar.getInstance();
			cal.set(Calendar.YEAR, 2012);
			cal.set(Calendar.MONTH, 4);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			Date today=new Date();
			//结束日期不能小于2012-05-01
			if(endDate==null || this.endDate.getTime()<cal.getTimeInMillis()){
				this.endDate=today;
			}
			//时间间隔不能超过15天
			if(this.startDate==null || this.endDate.getTime()-this.startDate.getTime()>SEARCH_DAY_LENGTH){
				this.startDate=new Date(this.endDate.getTime()-SEARCH_DAY_LENGTH);
			}
			//查看统计的最早时间不能小于2012-05-01
			if(this.startDate.getTime()<cal.getTime().getTime()){
				this.startDate=cal.getTime();
			}
			
			
			List<DmDataMapResult> dmPubAreaList = this.dmPublicationAreaService
					.queryByDateAndPagenator(publicationId, startDate, endDate,
							null, null);
			if (dmPubAreaList != null
					&& dmPubAreaList.size() >= 0) {
				this.jsonResult.put("datamapList", dmPubAreaList);
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			}
		} catch (Exception e) {
			log.error("", e);
		}
		return JSON;
	}

	private Long publicationId;

	private Date startDate;

	private Date endDate;
	
	private List<Publication> pubList;
	
	

	public List<Publication> getPubList() {
		return pubList;
	}

	public void setPubList(List<Publication> pubList) {
		this.pubList = pubList;
	}

	public Long getPublicationId() {
		return publicationId;
	}

	public void setPublicationId(Long publicationId) {
		this.publicationId = publicationId;
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

}
