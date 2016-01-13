/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.publish;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.pojo.Publication;
import cn.magme.pojo.stat.DmPublication;
import cn.magme.service.PublicationService;
import cn.magme.service.stat.DmPublicationStatService;
import cn.magme.util.ExpressDate;
import cn.magme.util.xsl.XslUtil;
import cn.magme.web.action.BaseAction;

/**
 * @author fredy.liu
 * @date 2012-5-24
 * @version $id$
 */
@Results({@Result(name="success",location="/WEB-INF/pages/publish/dmPublication.ftl"),
	@Result(name="downloadsuccess",type="stream",params={"contentType","application/vnd.ms-excel","bufferSize","2048",
			"inputName","inputStream","contentDisposition","attachment;filename=\"exportData.xls\""})})
public class DmPublicationAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3793418481523939314L;
	
	@Resource
	private DmPublicationStatService dmPublicationStatService;
	
	private static final Logger log=Logger.getLogger(DmPublicationAction.class);
	
	private static final Long MAX_DAY=1000L*60*60*24*31;//31天
	
	@Resource
	private PublicationService publicationService;
	
	private ByteArrayInputStream inputStream;
	
	private Integer expressDate=null;
	
	public String execute(){
		pubList=publicationService.queryNormalByPublisherId(this.getSessionAdUserId());
		return SUCCESS;
	}
	
	public String pubJson(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		//转换快速日期
		if(this.expressDate!=null){
			Map<String,Date> dateMap=ExpressDate.convertExpressDate(expressDate,startDate,endDate);
			startDate=dateMap.get("startDate");
			endDate=dateMap.get("endDate");
		}
		
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
			//时间间隔不能超过MAX_DAY天
			if(this.startDate==null || this.endDate.getTime()-this.startDate.getTime()>MAX_DAY){
				this.startDate=new Date(this.endDate.getTime()-MAX_DAY);
			}
			//查看统计的最早时间不能小于2012-05-01
			if(this.startDate.getTime()<cal.getTime().getTime()){
				this.startDate=cal.getTime();
			}
			
			
			List<DmPublication> dmPubList=this.dmPublicationStatService.getPageByPubIdAndDate(publicationId, startDate, endDate,order);
			if(dmPubList!=null && dmPubList.size()>0){
				this.queryTotalPvJson();
				this.jsonResult.put("dmPubList", dmPubList);
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			}
			this.jsonResult.put("startDate", startDate);
			this.jsonResult.put("endDate", endDate);
		} catch (Exception e) {
			log.error("", e);
		}
		return JSON;
	}
	
	public String queryTotalPvJson(){
		Integer totalPv=0;
		Calendar cal=Calendar.getInstance();
		if(this.year==null || this.month==null){
			this.year=cal.get(Calendar.YEAR);
			this.month=cal.get(Calendar.MONTH);
		}
		if(this.year<2012 || this.year>2015 || this.month<1 || this.month>12){
			log.error("parameters error");
			return  JSON;
		}
		cal.set(Calendar.YEAR,this.year);
		cal.set(Calendar.MONTH, this.month-1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date startDate=cal.getTime();
		
		Calendar cal2=Calendar.getInstance();
		cal2.set(Calendar.YEAR,this.year);
		cal2.set(Calendar.MONTH, this.month);
		cal2.set(Calendar.DAY_OF_MONTH, 1);
		Date endDate=cal2.getTime();
		totalPv=this.dmPublicationStatService.getCountByStartDateAndEndDate(this.publicationId,startDate, endDate);
		if(this.jsonResult==null){
			this.jsonResult=new JsonResult();	
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		this.jsonResult.put("year", this.year);
		this.jsonResult.put("month", this.month);
		this.jsonResult.put("totalPv", totalPv);
		return JSON;
		
	}
	
	
	
	public static void main(String [] args){
		Integer expressDate=3;
	    Date startDate=null;
	    Date endDate=null;
		
	}
	
	public String exportData(){
		if(this.endDate==null || this.startDate==null){
			this.endDate=new Date();
			this.startDate=new Date(this.endDate.getTime()-MAX_DAY);
		}
		this.pubJson();
		if(this.jsonResult.getCode()!=JsonResult.CODE.SUCCESS){
			return JSON;
		}
		
		List<DmPublication> dmPubList=(List<DmPublication>)this.jsonResult.get("dmPubList");
		List<String> headers=new ArrayList<String>();
		headers.add("杂志名称");
		headers.add("时间");
		headers.add("pv");
		
		List<List<String>> contents=new ArrayList<List<String>>();
		for(DmPublication dmPub:dmPubList){
			List<String> rowContent=new ArrayList<String>();
			rowContent.add(dmPub.getPublicationName());
			rowContent.add(DateFormatUtils.format(dmPub.getDataDate(), "yyyy-MM-dd"));
			rowContent.add(String.valueOf(dmPub.getTotalPv()));
			contents.add(rowContent);
		}
		
		HSSFWorkbook wb=XslUtil.exportToExcel(headers, contents);
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		try {
			wb.write(baos);
		} catch (IOException e) {
			log.error("",e);
		}
		byte[] ba=baos.toByteArray();
		this.inputStream=new ByteArrayInputStream(ba);
		return "downloadsuccess";
	}
	
	
	
	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}



	
	public Integer getExpressDate() {
		return expressDate;
	}

	public void setExpressDate(Integer expressDate) {
		this.expressDate = expressDate;
	}




	private Long publicationId;
	
	private Date startDate;
	
	private Date endDate;
	
	private String order;
	
	private Integer year;
	
	private Integer month;
	
	
	private List<Publication> pubList;
	
	
	
	
	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

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
