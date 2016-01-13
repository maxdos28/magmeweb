/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.publish;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.pojo.Publication;
import cn.magme.pojo.stat.StatDmPagenator;
import cn.magme.result.stat.DmReferResult;
import cn.magme.service.PublicationService;
import cn.magme.service.stat.DmReferPublicationService;
import cn.magme.util.xsl.XslUtil;
import cn.magme.web.action.BaseAction;

/**
 * @author fredy.liu
 * @date 2012-3-22
 * @version $id$
 */
@Results({@Result(name="success",location="/WEB-INF/pages/publish/dmreferpublication.ftl"),
	@Result(name="detailsuccess",location="/WEB-INF/pages/publish/dmreferpublicationdetail.ftl"),
	@Result(name="downloadsuccess",type="stream",params={"contentType","application/vnd.ms-excel","bufferSize","2048",
			"inputName","inputStream","contentDisposition","attachment;filename=\"exportData.xls\""})})
public class DmReferPublicationAction extends BaseAction{

	/** * */
	private static final long serialVersionUID = 5741530507356464017L;

	/** * */
	private static final Logger log=Logger.getLogger(DmReferPublicationAction.class);
	
	private static final String DETAIL_SUCCESS="detailsuccess";
	private static final Integer DEFAULT_SIZE = 20;
	private static final Integer DEFAULT_START = 0;
	
	@Resource
	private DmReferPublicationService dmReferPublicationService;
	@Resource
	private PublicationService publicationService;

	private Date startDate;
	private Date endDate;
	private Integer startRow;
	private Integer pagesize;
	private String domain;
	private Integer type; 
	private Long publicationId;
	private Long issueId;
	private List<Publication> pubList;

	private ByteArrayInputStream inputStream;
	
	public String execute(){
		pubList = publicationService.queryNormalByPublisherId(this.getSessionAdUserId());
		return SUCCESS;
	}

	public String referDetail(){
		return DETAIL_SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String exportData(){
		startRow = null;
		pagesize = null;
		this.pageJson();
		List<DmReferResult> dmReferList = (List<DmReferResult>) this.jsonResult.get("dmReferList");
		Integer totalNum =  (Integer) this.jsonResult.get("totalNum");
		List<String> headers = new ArrayList<String>();
		headers.add("来路域名");
		headers.add("所占比例");
		headers.add("pv");

		List<List<String>> contents = new ArrayList<List<String>>();
		for(DmReferResult dmReferPub : dmReferList){
			List<String> rowContent = new ArrayList<String>();
			rowContent.add(dmReferPub.getDomain());
			rowContent.add("" + dmReferPub.getViewNumber() * 100 / totalNum);
			rowContent.add("" + dmReferPub.getViewNumber());
			contents.add(rowContent);
		}

		HSSFWorkbook wb = XslUtil.exportToExcel(headers, contents);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			wb.write(baos);
		} catch (IOException e) {
			log.error("",e);
		}
		byte[] ba = baos.toByteArray();
		this.inputStream = new ByteArrayInputStream(ba);
		return "downloadsuccess";
	}
	
	public String queryReferJson(){
		if(startRow == null)
			startRow = DEFAULT_START;
		if(pagesize == null)
			pagesize = DEFAULT_SIZE;
		pageJson();
		return JSON;
	}

	/**
	 * 某个来路域名详细数据信息
	 * @return
	 */
	public String queryReferByDomainJson(){
		this.jsonResult = JsonResult.getFailure();
		if(StringUtils.isBlank(domain)){
			this.jsonResult.setMessage("domain不能为空");
			return JSON;
		}
		StatDmPagenator pagenator = getPagenator();
		try {
			Map<String, Object> param = getParam();
			List<DmReferResult> dmReferList = dmReferPublicationService.queryByPagenatorAndCondition(pagenator, param);
			this.jsonResult = new JsonResult("dmReferList", dmReferList);
		} catch (Exception e) {
			log.error("", e);
		}
		return JSON;
	}
	
	private Map<String, Object> getParam(){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("publicationId", publicationId);
		param.put("issueId", issueId);
		param.put("type", type);
		param.put("domain", domain);
		return param;
	}

	/**
	 * 页面展示所需数据
	 */
	private void pageJson() {
		this.jsonResult = JsonResult.getFailure();
		StatDmPagenator pagenator = getPagenator();
		try {
			Map<String, Object> param = getParam();
			List<DmReferResult> dmReferList = dmReferPublicationService.queryByPagenatorAndCondition(pagenator, param);
			Integer totalNum = dmReferPublicationService.queryTotalCountByPagenator(pagenator, param);
			this.jsonResult = new JsonResult("dmReferList", dmReferList);
			this.jsonResult.put("totalNum", totalNum);
		} catch (Exception e) {
			log.error("", e);
		}
	}
	private StatDmPagenator getPagenator() {
		StatDmPagenator pagenator = new StatDmPagenator();
		pagenator.setEndDate(endDate);
		pagenator.setPagesize(pagesize);
		pagenator.setStartDate(startDate);
		pagenator.setStartRow(startRow);
		return pagenator;
	}

	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
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
	public void setPublicationId(Long publicationId) {
		this.publicationId = publicationId;
	}
	public Long getPublicationId() {
		return publicationId;
	}
	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}
	public Long getIssueId() {
		return issueId;
	}
	public List<Publication> getPubList() {
		return pubList;
	}


}