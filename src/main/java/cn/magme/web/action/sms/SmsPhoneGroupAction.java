package cn.magme.web.action.sms;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.jsoup.helper.StringUtil;

import cn.magme.common.JsonResult;
import cn.magme.pojo.sms.SmsPhone;
import cn.magme.pojo.sms.SmsPhoneGroup;
import cn.magme.service.sms.SmsPhoneGroupService;
import cn.magme.service.sms.SmsPhoneService;
import cn.magme.util.xsl.XslUtil;
import cn.magme.web.action.BaseAction;
/**
 * 
 * @author fredy
 * @since 2013-3-18
 */
@Results({@Result(name="success",location="/WEB-INF/pages/sms/smsPhoneGroup.ftl"),
	      @Result(name="downloadsuccess",type="stream",params={"contentType","application/vnd.ms-excel","bufferSize","2048",
			"inputName","inputStream","contentDisposition","attachment;filename=\"exportData.xls\""}),
		  @Result(name="uploadFileJson",type="json",params={"root","jsonResult","contentType","text/html"})})
public class SmsPhoneGroupAction extends BaseAction {

	private static final long serialVersionUID = 8318721255843408491L;
	
	private static final Logger log=Logger.getLogger(SmsPhoneGroupAction.class);
	
	@Resource
	private SmsPhoneGroupService smsPhoneGroupService;
	
	@Resource
	private SmsPhoneService smsPhoneService;
	
	private static final String FILE_EXTEND=".txt";
	
	private static final Pattern fileExtendPattern=Pattern.compile(".*\\.txt",Pattern.CASE_INSENSITIVE);
	
	private static final Pattern pattern=Pattern.compile("1\\d{10}");
	
	private static final String COMMA=",";
	
	
	/**
	 * 进入操作界面
	 */
	public String execute(){
		groupListJson();
		return SUCCESS;
	}
	
	public String groupListJson(){
		Integer []statuses=new Integer[]{1};
		start=(this.currentPage-1)*limit;
		this.jsonResult=smsPhoneGroupService.queryByNameUidAndStatusPagenator(name, 
				this.getSessionSmsUser().getId(), statuses, start, limit);
		int listCount=(Integer)this.jsonResult.get("totalCount");
		if(pageNo==null || pageNo<=0){//重新计算总页数，并进行数据库查询
			 pageNo = (listCount+pageSize-1)/pageSize;
	         currentPage=1;
		}
		this.jsonResult.put("pageNo", pageNo);
		this.jsonResult.put("currentPage", currentPage);
		return JSON;
	}
	
	/**
	 * 追加,给通讯录增加电话号码
	 * @return
	 */
	public String addPhonesJson(){
		return "downloadsuccess";
	}
	
	/**
	 * 增加通讯录
	 * @return
	 */
	public String addGroupJson(){
		this.jsonResult=JsonResult.getSuccess();
		if(StringUtil.isBlank(this.fileName) || this.uploadFile==null){
			this.jsonResult.setMessage("请选择上传的文件");
			return JSON;
		}
		if(!fileExtendPattern.matcher(this.fileName).matches()){
			this.jsonResult.setMessage("只能上传txt文件");
			return JSON;
		}
		if(id==null && StringUtil.isBlank(name)){
			this.jsonResult.setMessage("新增通讯录，必须输入名称");
			return JSON;
		}
		//解析文件中的电话号码
		Set<SmsPhone> smsPhones=new HashSet<SmsPhone>();
		StringBuilder errorPhones=new StringBuilder("");
		BufferedReader fr=null;
		try {
			fr=new BufferedReader(new FileReader(this.uploadFile));
			String line=fr.readLine();
			while(line!=null){
				if("".endsWith(line)){
					continue;
				}
				//转换电话号码
				try {
					SmsPhone smsPhone=new SmsPhone();
					smsPhone.setStatus(1);
					String phoneNum=line.split(COMMA)[0];
					if(!pattern.matcher(phoneNum).matches()){
						errorPhones.append(line).append(",");
						continue;
					}
					smsPhone.setTelNum(Long.parseLong(phoneNum));
					smsPhones.add(smsPhone);
					line=fr.readLine();
				} catch (Exception e) {
					errorPhones.append(line).append(",");
				}
			}
			
		} catch (Exception e) {
			log.error("解析文件出错", e);
			this.jsonResult.setMessage("文件格式不符合需求");
			return JSON;
		} finally{
			try {
				if(fr!=null){
					fr.close();
				}
			} catch (IOException e) {
				log.error("关闭文件出错", e);
			}
		}
		
		if(!errorPhones.toString().equalsIgnoreCase("")){
			this.jsonResult.setMessage("以下电话号码格式错误:"+errorPhones.toString());
			return JSON;
		}
		//写入数据库
		if(smsPhones.size()<=0){
			this.jsonResult.setMessage("文件中没有合适的电话号码");
			return JSON;
		}
		Long phoneGroupId=id;
		//写入通讯录
		if(id==null){
			SmsPhoneGroup record=new SmsPhoneGroup();
			record.setName(name);
			record.setPhoneCount(smsPhones.size());
			record.setSmsUserId(this.getSessionSmsUser().getId());
			record.setStatus(1);
			phoneGroupId=this.smsPhoneGroupService.insert(record);
		}else{
			SmsPhoneGroup record=this.smsPhoneGroupService.selectByPrimaryKey(id);
			record.setPhoneCount(record.getPhoneCount()+smsPhones.size());
			smsPhoneGroupService.updateByPrimaryKeySelective(record);
		}
		
		//写入电话号码
		for(SmsPhone p:smsPhones){
			p.setPhoneGroupId(phoneGroupId);
			this.smsPhoneService.insert(p);
		}
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return "uploadFileJson";
	}
	
	
	/**
	 * 导出通讯录
	 * @return
	 */
	public String exportData(){
		List<SmsPhone> smsPhoneList=this.smsPhoneService.queryByGroupId(id);
		List<String> headers = new ArrayList<String>();
		headers.add("电话号码");
		
		List<List<String>> contents = new ArrayList<List<String>>();
		if(smsPhoneList!=null && smsPhoneList.size()>0){
			for(SmsPhone data : smsPhoneList){
				List<String> rowContent = new ArrayList<String>();
				rowContent.add(String.valueOf(data.getTelNum()));
				contents.add(rowContent);
			}
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
	
	/**
	 * 合并通讯录
	 * 经过确认，这个需求取消
	 * @return
	 */
	public String combileJson(){
		this.jsonResult=JsonResult.getFailure();
		if(StringUtil.isBlank(ids)){
			this.jsonResult.setMessage("传入id为空");
			return JSON;
		}
		String [] idsStrArr=ids.split(",");
		return JSON;
	}
	
	public String delJson(){
		this.jsonResult=JsonResult.getFailure();
		if(id!=null){
			if(this.smsPhoneGroupService.deleteByPrimaryKey(id)>0){
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
				return JSON;
			}
		}
		
		if(StringUtils.isNotBlank(ids)){
			String [] idsArr=ids.split(",");
			if(idsArr!=null && idsArr.length>0){
				for(String id:idsArr){
					this.smsPhoneGroupService.deleteByPrimaryKey(Long.parseLong(id));
				}
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
				return JSON;
			}
		}
		
		
		return JSON;
	}
	
	/**
	 * id
	 */
	private Long id;
	
	/**
	 * 多个id
	 */
	private String ids;
	/**
	 * 通讯录名称
	 */
	private String name;
	
	/**
	 * 上传的通讯录文件
	 */
	private File uploadFile;
	
	private Integer start=0;
	
	private Integer limit=10;
	
	private final Integer pageSize = 10;
	
	private List<SmsPhoneGroup> phoneGroupList;
	
	private ByteArrayInputStream inputStream;
	
	private Integer begin = 0;
	private Integer currentPage = 1;
	private Integer pageNo;
	private String fileName;
	
	
	
	
	
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getBegin() {
		return begin;
	}

	public void setBegin(Integer begin) {
		this.begin = begin;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}
	

	public List<SmsPhoneGroup> getPhoneGroupList() {
		return phoneGroupList;
	}

	public void setPhoneGroupList(List<SmsPhoneGroup> phoneGroupList) {
		this.phoneGroupList = phoneGroupList;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public File getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}
	
	
	
	
	
}
