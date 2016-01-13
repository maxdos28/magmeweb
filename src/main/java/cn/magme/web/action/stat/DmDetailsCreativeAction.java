/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.stat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.pojo.stat.DmDetailsCreative;
import cn.magme.pojo.stat.DmPublication;
import cn.magme.service.stat.DmDetailsCreativeService;
import cn.magme.util.xsl.XslUtil;
import cn.magme.web.action.BaseAction;

/**
 * @author devin.song
 * @date 2013-03-06
 */
@SuppressWarnings("serial")
@Results({@Result(name="success",location="/WEB-INF/pages/stat/dmcreativedetail.ftl")
,@Result(name="grand",location="/WEB-INF/pages/stat/dmcreativedetailgrand.ftl")
,@Result(name="downloadsuccess",type="stream",params={"contentType","application/vnd.ms-excel","bufferSize","2048",
		"inputName","inputStream","contentDisposition","attachment;filename=\"exportData.xls\""})})
public class DmDetailsCreativeAction extends BaseAction{
	private static final Logger log=Logger.getLogger(DmReaderActAction.class);
	
	private static final Long MAX_DAY=1000L*60*60*24*31;//31天
	
	@Resource
	private DmDetailsCreativeService dmDetailsCreativeService;
	public String execute(){
		return SUCCESS;
	}
	public String grand(){
		return "grand";
	}
	
	public String detail(){
		this.jsonResult = JsonResult.getFailure();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("startDate",startDate);
		map.put("endDate",endDate);
		map.put("startRow", startRow);
		map.put("pagesize", pagesize);
		creativeList=this.dmDetailsCreativeService.listDmDetailsCreativeByMap(map);
		this.jsonResult = JsonResult.getSuccess();
		this.jsonResult.put("creativeList", creativeList);
		return JSON;
	} 
	
	public String exportData(){
		this.jsonResult = JsonResult.getFailure();
		Map<String,Object> map = new HashMap<String,Object>();
		if(exportType!=null && "grand".equals(exportType)){
		}else{
			if(this.endDate==null || this.startDate==null){
				this.endDate=new Date();
				this.startDate=new Date(this.endDate.getTime()-MAX_DAY);
			}
			map.put("startDate",startDate);
			map.put("endDate",endDate);
		}
		List<DmDetailsCreative> creativeListExport = this.dmDetailsCreativeService.listDmDetailsCreativeByMap(map);
		List<String> headers=new ArrayList<String>();
		headers.add("作品id");
		headers.add("作品标题");
		headers.add("点击次数");
		headers.add("编辑姓名");
		headers.add("出自杂志");
		headers.add("日期");
		List<List<String>> contents=new ArrayList<List<String>>();
		for(DmDetailsCreative creative:creativeListExport){
			List<String> rowContent=new ArrayList<String>();
			rowContent.add(creative.getCreativeId()+"");
			rowContent.add(creative.getCreativeName());
			rowContent.add(creative.getPv()+"");
			rowContent.add(creative.getEditorName());
			rowContent.add(creative.getPublicationName());
			rowContent.add(DateFormatUtils.format(startDate, "yyyy-MM-dd")+"---"+DateFormatUtils.format(endDate, "yyyy-MM-dd"));
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
	
	
	private Date startDate;
	
	private Date endDate;

	private Integer startRow=0;
	
	private Integer pagesize=50;
	
	private Long categoryId;
	
	private List<DmDetailsCreative> creativeList;
	
	private ByteArrayInputStream inputStream;
	
	private Integer expressDate=null;
	
	private String exportType;
	
	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}
	
	
	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
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

	public List<DmDetailsCreative> getCreativeList() {
		return creativeList;
	}

	public void setCreativeList(List<DmDetailsCreative> creativeList) {
		this.creativeList = creativeList;
	}

	public Integer getExpressDate() {
		return expressDate;
	}

	public void setExpressDate(Integer expressDate) {
		this.expressDate = expressDate;
	}

	public String getExportType() {
		return exportType;
	}

	public void setExportType(String exportType) {
		this.exportType = exportType;
	}

}
