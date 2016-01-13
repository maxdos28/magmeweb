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
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.Issue;
import cn.magme.pojo.Publication;
import cn.magme.pojo.stat.DmIssuePage;
import cn.magme.result.stat.StatLineChartResult;
import cn.magme.service.IssueService;
import cn.magme.service.PublicationService;
import cn.magme.service.stat.DmIssuePageStatService;
import cn.magme.util.ExpressDate;
import cn.magme.util.xsl.XslUtil;
import cn.magme.web.action.BaseAction;
import cn.magme.web.util.JudgeStatDate;

/**
 * @author fredy.liu
 * @date 2012-5-24
 * @version $id$
 */
@Results({@Result(name="success",location="/WEB-INF/pages/publish/dmIssuePage.ftl"),
	@Result(name="activeReadsuccess",location="/WEB-INF/pages/publish/dmActiveRead.ftl"),
	@Result(name="newactiveReadsuccess",location="/WEB-INF/pages/publish/newdmActiveRead.ftl"),
	@Result(name="downloadsuccess",type="stream",params={"contentType","application/vnd.ms-excel","bufferSize","2048",
			"inputName","inputStream","contentDisposition","attachment;filename=\"exportData.xls\""})})
public class DmIssuePageAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7547208599813850543L;

	@Resource
	private DmIssuePageStatService dmIssuePageStatService;
	
	@Resource
	private PublicationService publicationService;
	
	@Resource
	private IssueService issueService;
	
	private static final Logger log=Logger.getLogger(DmIssuePageAction.class);
	
	private static final Long MAX_DAY=1000L*60*60*24*31;
	/**
	 * 分页大小
	 */
	private static final int PAGE_SIZE=10;
	
	/**
	 * 分的段数
	 */
	private static final int SEPARATOR_NUM=10;
	
	private ByteArrayInputStream inputStream;
	
	public String execute(){
		pubList=publicationService.queryNormalByPublisherId(this.getSessionAdUserId());
		if(pubList!=null && pubList.size()>0){
			issueList=this.issueService.queryByPubIdAndStatuses(pubList.get(0).getId(),new int[]{ PojoConstant.ISSUE.STATUS.ON_SALE.getCode()},-1);	
		}
		return SUCCESS;
	}
	
	public String pageJson(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		try {
			if(this.issueId==null || this.issueId<=0){
				pubList=publicationService.queryNormalByPublisherId(this.getSessionAdUserId());
				if(pubList!=null && pubList.size()>0){
					issueList=this.issueService.queryByPubIdAndStatuses(pubList.get(0).getId(),new int[]{ PojoConstant.ISSUE.STATUS.ON_SALE.getCode()},-1);	
				}
				if(issueList==null || issueList.size()<=0){
					this.jsonResult.setMessage("没有issueId");
					return JSON;
				}
				this.issueId=issueList.get(0).getId();
			}
			Map<String,Date> dateMap=ExpressDate.judgeStatDate(startDate, endDate);
			this.startDate=dateMap.get("startDate");
			this.endDate=dateMap.get("endDate");
			
			List<DmIssuePage> dmIssuePageList=dmIssuePageStatService.queryByIssueIdAndDate(issueId, startDate, endDate,order,start,size);
			if(dmIssuePageList!=null && dmIssuePageList.size()>0){
				this.jsonResult.put("dmIssuePageList", dmIssuePageList);
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
	
	/**
	 * new活跃阅读的json请求
	 * @return
	 */
	public String newActiveJson(){
		this.jsonResult=this.generateJsonResult(JsonResult.CODE.FAILURE, JsonResult.MESSAGE.FAILURE);
		if(this.issueId==null || this.issueId<=0){
			issueList=this.issueService.queryByStatusAndPublisherId(this.getSessionAdUserId(), PojoConstant.ISSUE.STATUS.ON_SALE.getCode());
			if(issueList==null || issueList.size()<=0){
				this.jsonResult.setMessage("没有issueId");
				return JSON;
			}
			this.issueId=issueList.get(0).getId();
		}
		//convert date
		Map<String,Date> validDate=JudgeStatDate.convertDateToValidDate(startDate, endDate);
		this.startDate=validDate.get("startDate");
		this.endDate=validDate.get("endDate");
		List<StatLineChartResult> titleTimes=this.dmIssuePageStatService.queryTitleTimesByIssueIdAndDate(issueId, startDate, endDate);
		Integer totalTitleTimes=this.dmIssuePageStatService.queryTotalTitleTimesByIssueIdAndDate(issueId, startDate, endDate);
		if(totalTitleTimes!=null && totalTitleTimes>0 && titleTimes!=null && titleTimes.size()>0){
			this.jsonResult.put("lineData", titleTimes);
			this.jsonResult.put("avgData", totalTitleTimes/titleTimes.size());
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		
		return JSON;
	}
	
	public String newActiveExportData(){
		this.newActiveJson();
		if(this.jsonResult.getCode()!=JsonResult.CODE.SUCCESS){
			return JSON;
		}
		
		List<StatLineChartResult> statLineChartList=(List<StatLineChartResult>)this.jsonResult.get("lineData");
		List<String> headers=new ArrayList<String>();
		headers.add("关注热度");
		headers.add("关注文章");
		
		List<List<String>> contents=new ArrayList<List<String>>();
		for(StatLineChartResult statLine:statLineChartList){
				List<String> rowContent=new ArrayList<String>();
				rowContent.add(statLine.getLongitudinalData());
				rowContent.add(statLine.getHorizontalData());
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
	
	/**
	 * 活跃阅读的json请求
	 * @return
	 */
	public String activeReadJson(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		try {
			//转换快速日期
			if(this.expressDate!=null){
				Map<String,Date> dateMap=ExpressDate.convertExpressDate(expressDate,startDate,endDate);
				startDate=dateMap.get("startDate");
				endDate=dateMap.get("endDate");
			}
			
			if(this.issueId==null || this.issueId<=0){
				issueList=this.issueService.queryByStatusAndPublisherId(this.getSessionAdUserId(), PojoConstant.ISSUE.STATUS.ON_SALE.getCode());
				if(issueList==null || issueList.size()<=0){
					this.jsonResult.setMessage("没有issueId");
					return JSON;
				}
				this.issueId=issueList.get(0).getId();
			}
			//判断日期，只能查看MAX_DAY天内数据，并且不能查看5月1日之前的数据
			Calendar cal=Calendar.getInstance();
			cal.set(Calendar.YEAR, 2012);
			cal.set(Calendar.MONTH, 4);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			Date today=new Date();
			//结束日期不能小于2012-05-01
			if(endDate==null || this.endDate.getTime()<cal.getTimeInMillis()){
				this.endDate=today;
			}
			//时间间隔不能超过7天
			if(this.startDate==null || this.endDate.getTime()-this.startDate.getTime()>MAX_DAY){
				this.startDate=new Date(this.endDate.getTime()-MAX_DAY);
			}
			//查看统计的最早时间不能小于2012-05-01
			if(this.startDate.getTime()<cal.getTime().getTime()){
				this.startDate=cal.getTime();
			}
			
			
			List<DmIssuePage> reslist=new ArrayList<DmIssuePage>();
			Issue issue=issueService.queryById(issueId);
			//总得段数
			int totalSectionNum=1;//最少会有一段
			if(issue.getTotalPages()<=SEPARATOR_NUM){
				totalSectionNum=1;
			}else if(issue.getTotalPages()%SEPARATOR_NUM!=0){
				totalSectionNum=issue.getTotalPages()/SEPARATOR_NUM+1;
			}else{
				totalSectionNum=issue.getTotalPages()/SEPARATOR_NUM;
			}
			
			//段数不能超过SEPARATOR_NUM
			if(totalSectionNum>SEPARATOR_NUM){
				totalSectionNum=SEPARATOR_NUM;
			}
			//每段的条数
			int sectionCount=issue.getTotalPages()/totalSectionNum;
			if(issue.getTotalPages()%totalSectionNum!=0){
				sectionCount=issue.getTotalPages()/totalSectionNum+1;
			}
			//总的停留时间
			long totalRetention=0L;
			//分段的停留时间
			long sectionRetention=0L;
			
			//当前段数
			
			List<DmIssuePage> dmIssuePageList=dmIssuePageStatService.queryGroupRetionByIssueAndDate(issueId,startDate, endDate);
			if(dmIssuePageList!=null && dmIssuePageList.size()>0){
				int size=dmIssuePageList.size();
				int currentSectionNum=1;
				int previousSectionNum=1;
				//先写一条0的数据
				completeSectionNum(0,1,totalSectionNum, reslist);
				//计算了多少页的计数
				int pagecount=0;
				for(int i=0;i<size;i++){
					pagecount++;
					totalRetention+=dmIssuePageList.get(i).getRetention();
					sectionRetention+=dmIssuePageList.get(i).getRetention();
					int pageno=dmIssuePageList.get(i).getPageNo();
					//防止异常页码
					if(pageno>issue.getTotalPages()){
						continue;
					}
					//计算当前的段数
					if(pageno%sectionCount==0){
						currentSectionNum=pageno/sectionCount;
					}else{
						currentSectionNum=pageno/sectionCount+1;
					}
					//插入空的数据
					if(previousSectionNum+1<currentSectionNum){
						this.completeSectionNum(previousSectionNum+1, currentSectionNum, totalSectionNum, reslist);
					}
					previousSectionNum=currentSectionNum;
					if(i==size-1 || (pageno<=currentSectionNum*sectionCount && dmIssuePageList.get(i+1).getPageNo()>currentSectionNum*sectionCount )){
						DmIssuePage issuePage=new DmIssuePage();
						//分段计数器
						issuePage.setSeparatorStr(currentSectionNum*100/totalSectionNum+"%");
						issuePage.setRetention(sectionRetention/pagecount/1000);//求平均值
						issuePage.setPageNo(currentSectionNum);
						//重置计数器
						sectionRetention=0L;
						pagecount=0;
						//currentSectionNum+=currentSectionNum;
						reslist.add(issuePage);
					}
				}
				
				
				int lastPageNo=reslist.get(reslist.size()-1).getPageNo();
				//补齐后方残缺的段
				if(lastPageNo<totalSectionNum){
					completeSectionNum(lastPageNo+1, totalSectionNum+1, totalSectionNum, reslist);
				}
				
			}
			
			if(dmIssuePageList!=null && dmIssuePageList.size()>0){
				this.jsonResult.put("dmIssuePageList", reslist);
				this.jsonResult.put("totalAvgRetention", totalRetention/dmIssuePageList.size()/1000);
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
	
	
	private void completeSectionNum(int startSectionNum,int endSectionNum,int totalSectionNum,List<DmIssuePage> resList){
		for(int i=startSectionNum;i<endSectionNum;i++){
			DmIssuePage issuePage=new DmIssuePage();
			//分段计数器
			issuePage.setPageNo(i);
			issuePage.setSeparatorStr(i*100/totalSectionNum+"%");
			issuePage.setRetention(0L);
			resList.add(issuePage);
		}
	}
	
	/**
	 * 活跃阅读
	 * @return
	 */
	public String activeRead(){
		pubList=publicationService.queryNormalByPublisherId(this.getSessionAdUserId());
		if(pubList!=null && pubList.size()>0){
			issueList=this.issueService.queryByPubIdAndStatuses(pubList.get(0).getId(),new int[]{ PojoConstant.ISSUE.STATUS.ON_SALE.getCode()},-1);	
		}
		
		return "activeReadsuccess";
	}
	
	/**
	 * new活跃阅读
	 * @return
	 */
	public String newActiveRead(){
		pubList=publicationService.queryNormalByPublisherId(this.getSessionAdUserId());
		if(pubList!=null && pubList.size()>0){
			issueList=this.issueService.queryByPubIdAndStatuses(pubList.get(0).getId(),new int[]{ PojoConstant.ISSUE.STATUS.ON_SALE.getCode()},-1);	
		}
		
		return "newactiveReadsuccess";
	}
	
	
	@SuppressWarnings("unchecked")
	public String exportData(){
		if(this.endDate==null || this.startDate==null){
			this.endDate=new Date();
			this.startDate=new Date(this.endDate.getTime()-MAX_DAY);
		}
		//去除默认的分页限制
		this.start=null;
		this.size=null;
		this.pageJson();
		if(this.jsonResult.getCode()!=JsonResult.CODE.SUCCESS){
			return JSON;
		}
		
		List<DmIssuePage> dmIssuePageList=(List<DmIssuePage>)this.jsonResult.get("dmIssuePageList");
		List<String> headers=new ArrayList<String>();
		headers.add("期刊名称");
		headers.add("时间");
		headers.add("参考页码");
		headers.add("平均停留时间(毫秒)");
		
		List<List<String>> contents=new ArrayList<List<String>>();
		for(DmIssuePage dmIssue:dmIssuePageList){
			if(dmIssue.getRetention()>0 && dmIssue.getPageNo()!=-1){
				List<String> rowContent=new ArrayList<String>();
				rowContent.add(dmIssue.getPublicationName()+dmIssue.getIssueNumber());
				rowContent.add(DateFormatUtils.format(dmIssue.getDataDate(), "yyyy-MM-dd"));
				rowContent.add(String.valueOf(dmIssue.getPageNo()+1));
				rowContent.add(String.valueOf(dmIssue.getRetention()));
				contents.add(rowContent);
			}
			
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




	private List<Issue> issueList;
	private List<Publication> pubList;
	private Long issueId;
	
	private Date startDate;
	
	private Date endDate;
	
	private String order;
	
	private Integer start=0;
	
	private Integer size=PAGE_SIZE;
	
	private Integer expressDate;
	
	
	

	public List<Publication> getPubList() {
		return pubList;
	}

	public void setPubList(List<Publication> pubList) {
		this.pubList = pubList;
	}

	public Integer getExpressDate() {
		return expressDate;
	}

	public void setExpressDate(Integer expressDate) {
		this.expressDate = expressDate;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public List<Issue> getIssueList() {
		return issueList;
	}

	public void setIssueList(List<Issue> issueList) {
		this.issueList = issueList;
	}

	public Long getIssueId() {
		return issueId;
	}

	public void setIssueId(Long issueId) {
		this.issueId = issueId;
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
