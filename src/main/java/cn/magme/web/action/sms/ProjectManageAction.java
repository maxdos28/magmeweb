package cn.magme.web.action.sms;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant;
import cn.magme.dao.sms.SmsPhoneGroupDao;
import cn.magme.pojo.sms.SmsContentEx;
import cn.magme.pojo.sms.SmsPhoneGroup;
import cn.magme.pojo.sms.SmsProject;
import cn.magme.pojo.sms.SmsProjectGroupRel;
import cn.magme.pojo.sms.SmsUser;
import cn.magme.service.sms.SmsContentExService;
import cn.magme.service.sms.SmsPhoneGroupService;
import cn.magme.service.sms.SmsPhoneService;
import cn.magme.service.sms.SmsProjectGroupRelService;
import cn.magme.service.sms.SmsProjectService;
import cn.magme.service.sms.SmsSendReportPhoneService;
import cn.magme.util.StringUtil;
import cn.magme.util.xsl.XslUtil;
import cn.magme.web.action.BaseAction;

@Results({ @Result(name = "success", location = "/WEB-INF/pages/sms/project.ftl")
		,@Result(name = "create_step", location = "/WEB-INF/pages/sms/projectCreate.ftl")
		,@Result(name="downloadsuccess",type="stream",params={"contentType","application/vnd.ms-excel","bufferSize","2048",
		"inputName","inputStream","contentDisposition","attachment;filename=\"exportData.xls\""})
		,@Result(name = "upload_json", type = "json", params = { "root","jsonResult", "contentType", "text/html" })
})
public class ProjectManageAction extends BaseAction{
	private static final Logger log=Logger.getLogger(ProjectManageAction.class);
	
	@Resource 
	private SmsContentExService smsContentExService;
	
	@Resource
	private SmsProjectService smsProjectService;
	
	@Resource
	private SmsPhoneGroupDao smsPhoneGroupDao;
	
	@Resource
	private SmsProjectGroupRelService smsProjectGroupRelService;
	
	@Resource
	private SmsPhoneService smsPhoneService;
	
	@Resource
	private SmsPhoneGroupService smsPhoneGroupService;
	
	@Resource
	SmsSendReportPhoneService smsSendReportPhoneService;
	
	private final Integer pageSize = 10;

	/* 
	 * 项目列表
	 */
	@Override
	public String execute() throws Exception {
		projectList();
		return "success";
	}
	
	public String projectList(){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("smsUserId", this.getSessionSmsUser().getId());
		map.put("status", status);
		 if(pageNo==null || pageNo<=0){//重新计算总页数，并进行数据库查询
			 int listCount = smsProjectService.queryListCount(map);
			 pageNo = (listCount+pageSize-1)/pageSize;
	         currentPage=1;
		 }
		map.put("begin", (currentPage-1)*pageSize);
		map.put("size", pageSize);
		
		projectList = smsProjectService.queryList(map);
		//完成率 (发送结束状态前 ：t_sms_send_report_phone表中的记录数/总手机号数量)
		//成功/失败 (报表查看查看状态：t_sms_send_report_phone表中report_status=0的数量/(总数-等于0的数量))
		if(projectList!=null&&!projectList.isEmpty()){
			for (SmsProject project : projectList) {
				if(project.getStatus()>=PojoConstant.SMS_PROJECT.STATUS_SEND_FINISH){//发送结束或者报表状态:完成率=100% ;
					project.setCompletePercentage("100%");
					//成功/失败
					int sumNum = smsSendReportPhoneService.queryCountByProjectId(project.getId());//发送的总数
					int successNum = smsSendReportPhoneService.queryReportStatusCountByProjectId(project.getId(), PojoConstant.SMS_SEND_REPORT_PHONE.STATUS_REPORT);
					int failNum = sumNum-successNum;
					project.setSuccessPercentage(successNum+"/"+failNum);
				}else{
					int sumNum = smsPhoneService.queryBySmsProjectIdCount(project.getId());
					int successNum = smsSendReportPhoneService.queryCountByProjectId(project.getId());//发送的总数
					if(sumNum<=0) sumNum=1;
					project.setCompletePercentage((successNum*1000)/(sumNum*1000)+"%");//精确定位发送比率
					//成功/失败
					project.setSuccessPercentage("0/0");
				}
			}
		}
		
		this.jsonResult = JsonResult.getSuccess();
		this.jsonResult.put("projectList", projectList);
		this.jsonResult.put("pageNo", pageNo);
		this.jsonResult.put("currentPage", currentPage);
		return JSON;
	}
	
	/**
	 * 项目管理的第一步
	 * @return
	 */
	public String stepFirst(){
		this.stepNum=1;
		if(id!=null){
			findProjectById();
		}
		return "create_step";
	}
	
	public String stepFirstSave(){
		this.jsonResult = JsonResult.getFailure();
		SmsProject record = new SmsProject();
		if(StringUtil.isNotBlank(name)){
			record.setName(name);
		}else{
			this.jsonResult.setMessage("项目名不能为空");
			return JSON;
		}
		record.setSmsUserId(this.getSessionSmsUser().getId());
		record.setStatus(PojoConstant.SMS_PROJECT.STATUS_NOT_FINISH);
		record.setStep(1);
		saveComm(record,id);
		return JSON;
	}
	
	
	/**
	 * 项目管理的第二步
	 * @return
	 */
	public String stepSecond(){
		this.stepNum=2;
		findProjectById();
		return "create_step";
	}
	public String stepSecondSave(){
		this.jsonResult = JsonResult.getFailure();
		SmsProject record = new SmsProject();
		if(StringUtil.isNotBlank(template)){
			record.setTemplate(template);
		}else{
			this.jsonResult.setMessage("请选择模板");
			return JSON;
		}
		record.setSmsUserId(this.getSessionSmsUser().getId());
		record.setStatus(PojoConstant.SMS_PROJECT.STATUS_NOT_FINISH);
		record.setStep(2);
		saveComm(record,id);
		return JSON;
	}
	/**
	 * 项目管理的第三步
	 * @return
	 */
	public String stepThird(){
		this.stepNum=3;
		findProjectById();
		findContentEx(PojoConstant.SMS_CONTENT_EX.TYPE_VIDEO);//视频
		if(smsContentExList!=null&&smsContentExList.size()>0){
			videoUrl =smsContentExList.get(0).getUrl();
		}
		findContentEx(PojoConstant.SMS_CONTENT_EX.TYPE_PIC);//图片
		return "create_step";
	}
	public String stepThirdSave(){
		this.jsonResult = JsonResult.getFailure();
		SmsProject record = new SmsProject();
		if(StringUtil.isNotBlank(smsContent)){
			record.setSmsContent(smsContent);
		}else{
			this.jsonResult.setMessage("短信内容不能为空");
			return JSON;
		}
		if(StringUtil.isNotBlank(webTitle)){
			record.setWebTitle(webTitle);
		}else{
			this.jsonResult.setMessage("网页标题不能为空");
			return JSON;
		}
		if(StringUtil.isNotBlank(webContent)){
			record.setWebContent(webContent);	
		}else{
			this.jsonResult.setMessage("网页内容不能为空");
			return JSON;
		}
		
		record.setSmsUserId(this.getSessionSmsUser().getId());
		record.setStatus(PojoConstant.SMS_PROJECT.STATUS_NOT_FINISH);
		record.setStep(3);
		saveComm(record,id);
		
		//===>添加视频
		if(StringUtil.isNotBlank(videoUrl)){
			saveSmsContentExVideo(videoUrl);
		}
		
		return JSON;
	}
	/**
	 * 项目管理的第四步(sms项目电话群组关系表)
	 * @return
	 */
	public String stepFourth(){
		this.stepNum=4;
		findProjectById();
		groupListJson();
		return "create_step";
	}
	
	public String stepFourthSave(){
		this.jsonResult = JsonResult.getFailure();
		if(phoneGroupId==null||phoneGroupId.length()<=0){
			this.jsonResult.setMessage("请选择通讯录");
			return JSON;
		}
		
		Long[] phoneGroupIdLong = StringUtil.splitToLongArray(phoneGroupId);
		if(phoneGroupIdLong==null){
			this.jsonResult.setMessage("请选择通讯录");
			return JSON;
		}
		if(id==null){
			this.jsonResult.setMessage("项目id不存在");
		}
		saveSmsProjectGroupRel(phoneGroupIdLong);
		
		this.jsonResult = JsonResult.getSuccess();
		this.jsonResult.put("id", id);
		SmsProject record = new SmsProject();
		record.setStep(4);
		saveComm(record,id);
		return JSON;
	}
	/**
	 * 项目管理的第五步
	 * @return
	 */
	public String stepFifth(){
		this.stepNum=5;
		findProjectById();
		
		//要发送的手机号总数 ===1
		phoneNumCount = smsPhoneService.queryBySmsProjectIdCount(id);
		//用户余额     		===2
		SmsUser smsUser = this.getSessionSmsUser();
		
		NumberFormat currentFormat = NumberFormat.getCurrencyInstance(Locale.SIMPLIFIED_CHINESE);
		if(smsUser!=null){
			Long userMoney = smsUser.getBalance();//剩余余额 精确到哩
			userMoneyStr = currentFormat.format(userMoney/1000.0);//显示数字 精确到分 
			Long sym =smsUser.getPrice()*phoneNumCount;
			symStr = currentFormat.format(sym/1000.0);//理论发送短信的总价钱 显示数字 精确到分 
		}
		SmsProject smsProject = new SmsProject();
		smsProject.setId(id);
		smsProject.setStep(5);//发送短信之前的页面
		smsProjectService.updateByPrimaryKeySelective(smsProject);
		return "create_step";
	}
	
	//发送短信
	public String stepFifthSave(){
		SmsProject smsProject = new SmsProject();
		smsProject.setId(id);
//		smsProject.setStatus(PojoConstant.SMS_PROJECT.STATUS_SENDING);
//		smsProjectService.updateByPrimaryKeySelective(smsProject);
		this.jsonResult = smsSendReportPhoneService.sendSms(id);
		if(this.jsonResult.getCode()==200){
			smsProject.setStatus(PojoConstant.SMS_PROJECT.STATUS_SEND_FINISH);
			smsProject.setStep(6);//已发送
			smsProjectService.updateByPrimaryKeySelective(smsProject);
		}
		return JSON;
	}
	
	/**
	 * 更改为项目发送中
	 * @return
	 */
	public String SendingMsgFifth(){
		SmsProject smsProject = new SmsProject();
		smsProject.setId(id);
		smsProject.setStatus(PojoConstant.SMS_PROJECT.STATUS_SENDING);
		smsProjectService.updateByPrimaryKeySelective(smsProject);
		this.jsonResult = JsonResult.getSuccess();
		return JSON;
	}
	
	public String picUpload(){
		this.jsonResult = JsonResult.getFailure();
		if(id==null){
			this.jsonResult.setMessage("项目id获取失败");
			return JSON;
		}
		
		if(picture1!=null){
			picUplaodComm(picture1, picture1ContentType, picture1FileName,1);
		}
		if(picture2!=null){
			picUplaodComm(picture2, picture2ContentType, picture2FileName,2);
		}
		if(picture3!=null){
			picUplaodComm(picture3, picture3ContentType, picture3FileName,3);
		}
		if(picture4!=null){
			picUplaodComm(picture4, picture4ContentType, picture4FileName,4);
		}
		if(picture5!=null){
			picUplaodComm(picture5, picture5ContentType, picture5FileName,5);
		}
		return "upload_json";
	}
	
	public String delPicByExid(){
		this.jsonResult = JsonResult.getFailure();
		if(exId==null){
			this.jsonResult.setMessage("图片资源id获取失败");
			return JSON;
		}
		delSmsContentExPic();
		this.jsonResult  = JsonResult.getSuccess();
		return JSON;
	}
	
	/**
	 * 上传图片
	 * @param pic
	 * @param picContentType
	 * @param picFileName
	 * @return
	 */
	protected String picUplaodComm(File pic,String picContentType,String picFileName,int inputId){
		this.jsonResult = smsContentExService.uploadPicture(pic, picContentType, picFileName);
		if(this.jsonResult.getCode()==200){
			String jpgUrl = (String) this.jsonResult.get("path");
			Long scexId = saveSmsContentExPic(jpgUrl);
			this.jsonResult.put("inputId", inputId);
			this.jsonResult.put("id", scexId);
			this.jsonResult.put("pid", id);
		}
		return JSON;
	}
	
	/**
	 * 通讯录分页
	 * @return
	 */
	public String groupListJson(){
		Integer []statuses=new Integer[]{1};
		this.jsonResult=smsPhoneGroupService.queryByNameUidAndStatusPagenator(name, 
				this.getSessionSmsUser().getId(), statuses, (this.currentPage-1)*limit, limit);
		int listCount=(Integer)this.jsonResult.get("totalCount");
		if(pageNo==null || pageNo<=0){//重新计算总页数，并进行数据库查询
			 pageNo = (listCount+pageSize-1)/pageSize;
	         currentPage=1;
		}else{
			pageNo = (listCount+pageSize-1)/pageSize;
		}
		findProjectGroupRel();
		this.jsonResult.put("pageNo", pageNo);
		this.jsonResult.put("currentPage", currentPage);
		this.jsonResult.put("projectGroupRelList", projectGroupRelList);
		return JSON;
	}
	
	
	public String exportData(){
		this.jsonResult = JsonResult.getFailure();
		Map<String,Object> map = new HashMap<String,Object>();
		List<String> headers=new ArrayList<String>();
		headers.add("id");
		List<List<String>> contents=new ArrayList<List<String>>();
//		for(DmDetailsCreative creative:creativeListExport){
//			List<String> rowContent=new ArrayList<String>();
//			rowContent.add(creative.getCreativeId()+"");
//			rowContent.add(creative.getCreativeName());
//			rowContent.add(creative.getPv()+"");
//			rowContent.add(creative.getEditorName());
//			rowContent.add(creative.getPublicationName());
//			rowContent.add(DateFormatUtils.format(startDate, "yyyy-MM-dd")+"---"+DateFormatUtils.format(endDate, "yyyy-MM-dd"));
//			contents.add(rowContent);
//		}
		
		HSSFWorkbook wb=XslUtil.exportToExcel(headers, contents);
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		try {
			wb.write(baos);
		} catch (IOException e) {
			log.error("",e);
		}
		byte[] ba=baos.toByteArray();
		this.setInputStream(new ByteArrayInputStream(ba));
		return "downloadsuccess";
	}
	
	protected void saveComm(SmsProject record,Long id) {
		if(id!=null){//修改
			record.setId(id);
			smsProjectService.updateByPrimaryKeySelective(record);
			this.jsonResult = JsonResult.getSuccess();
			this.jsonResult.put("id", id);
		}else{//插入新记录
			id = smsProjectService.insert(record);
			if(id!=null){
				this.jsonResult = JsonResult.getSuccess();
				this.jsonResult.put("id", id);
			}
		}
	}
	
	protected void saveSmsProjectGroupRel(Long[] phoneGroupIdLong) {
		SmsProjectGroupRel spgr = new  SmsProjectGroupRel();
		spgr.setStatus(PojoConstant.SMS_PROJECT_GROUP_REL.STATUS_OK);
		spgr.setSmsProjectId(id);
		//先删除对应的记录
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("smsProjectId", id);
		List<SmsProjectGroupRel> tList = smsProjectGroupRelService.queryListByProjectId(map);
		if(tList!=null&&tList.size()>0){
			for (SmsProjectGroupRel smsProjectGroupRel : tList) {
				smsProjectGroupRelService.deleteByPrimaryKey(smsProjectGroupRel.getId());
			}
		}
		for (int i = 0; i < phoneGroupIdLong.length; i++) {
			long phoneGroupId = phoneGroupIdLong[i];
			spgr.setPhoneGroupId(phoneGroupId);
			smsProjectGroupRelService.insert(spgr);
		}
	}
	
	protected Long saveSmsContentExVideo(String url) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("projectId", id);
		map.put("type", PojoConstant.SMS_CONTENT_EX.TYPE_VIDEO);
		map.put("status", PojoConstant.SMS_CONTENT_EX.STATUS_OK);
		List<SmsContentEx> tmpList = smsContentExService.queryList(map);
		if(tmpList!=null && tmpList.size()>0){
			for (SmsContentEx smsContentEx2 : tmpList) {
				smsContentExService.deleteByPrimaryKey(smsContentEx2.getId());
			}
		}
		SmsContentEx smsContentEx = new SmsContentEx();
		smsContentEx.setUrl(url);
		smsContentEx.setProjectId(id);
		smsContentEx.setType(PojoConstant.SMS_CONTENT_EX.TYPE_VIDEO);
		smsContentEx.setStatus(PojoConstant.SMS_CONTENT_EX.STATUS_OK);
		return smsContentExService.insert(smsContentEx);
	}
	
	protected Long saveSmsContentExPic(String url) {
		SmsContentEx smsContentEx = new SmsContentEx();
		smsContentEx.setUrl(url);
		smsContentEx.setProjectId(id);
		smsContentEx.setType(PojoConstant.SMS_CONTENT_EX.TYPE_PIC);
		smsContentEx.setStatus(PojoConstant.SMS_CONTENT_EX.STATUS_OK);
		return smsContentExService.insert(smsContentEx);
	}
	
	protected void delSmsContentExPic() {
		smsContentExService.deleteByPrimaryKey(exId);
	}
	
	
	protected void findProjectById() {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("smsUserId",this.getSessionSmsUser().getId());
		List<SmsProject> tempList = smsProjectService.queryList(map);
		if(tempList!=null&&tempList.size()==1){
			smsProject = tempList.get(0);
		}
	}
	
	protected void findProjectGroupRel() {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("smsProjectId", id);
		map.put("status", PojoConstant.SMS_PROJECT_GROUP_REL.STATUS_OK);
		projectGroupRelList = smsProjectGroupRelService.queryListByProjectId(map);
	}
	
	protected void findContentEx(int type) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("projectId", id);
		map.put("type", type);
		map.put("status",PojoConstant.SMS_CONTENT_EX.STATUS_OK);
		smsContentExList = smsContentExService.queryList(map);
		if(type==PojoConstant.SMS_CONTENT_EX.TYPE_PIC){
			int kk=1;
			for (SmsContentEx sce : smsContentExList) {
				sce.getId();
				if(kk==1){
					picture1FileName =sce.getId()+"";
					picture1ContentType=sce.getUrl();
				}else if(kk==2){
					picture2FileName =sce.getId()+"";
					picture2ContentType=sce.getUrl();
				}else if(kk==3){
					picture3FileName =sce.getId()+"";
					picture3ContentType=sce.getUrl();
				}else if(kk==4){
					picture4FileName =sce.getId()+"";
					picture4ContentType=sce.getUrl();
				}else if(kk==5){
					picture5FileName =sce.getId()+"";
					picture5ContentType=sce.getUrl();
				}
				kk++;
			}
		}
	}

	private InputStream inputStream;
	private List<SmsProject> projectList;
	private List<SmsProjectGroupRel> projectGroupRelList;
	private List<SmsContentEx> smsContentExList;
	private SmsProject smsProject;
	private Integer status;
	private Integer begin = 0;
	private Integer currentPage = 1;
	private Integer pageNo;
	private int stepNum;
	
	private Integer start=0;
	
	private Integer limit=10;
	
	private Long exId;
	private String phoneGroupId;
	private Long id;
    private String name;
    private String smsContent;
    private String webTitle;
    private String webContent;
    private String template;
//    private Long smsUserId;
//    private Long sendSuccess;
//    private Long sendFail;
//    private Long expectAmount;
//    private Long amount;
//    private String weixin;
//    private String qq;
//    private String tel;
//    private String weibo;
//    private String email;
//    private String tencentWeibo;
    private String videoUrl;
    
    private int phoneNumCount=0;
    private String userMoneyStr;
    private String symStr;
    
    private File picture1;
	private String picture1ContentType;
	private String picture1FileName;
	
	private File picture2;
	private String picture2ContentType;
	private String picture2FileName;
	
	private File picture3;
	private String picture3ContentType;
	private String picture3FileName;
	
	private File picture4;
	private String picture4ContentType;
	private String picture4FileName;
	
	private File picture5;
	private String picture5ContentType;
	private String picture5FileName;
    
    public File getPicture1() {
		return picture1;
	}

	public void setPicture1(File picture1) {
		this.picture1 = picture1;
	}

	public String getPicture1ContentType() {
		return picture1ContentType;
	}

	public void setPicture1ContentType(String picture1ContentType) {
		this.picture1ContentType = picture1ContentType;
	}

	public String getPicture1FileName() {
		return picture1FileName;
	}

	public void setPicture1FileName(String picture1FileName) {
		this.picture1FileName = picture1FileName;
	}

	public File getPicture2() {
		return picture2;
	}

	public void setPicture2(File picture2) {
		this.picture2 = picture2;
	}

	public String getPicture2ContentType() {
		return picture2ContentType;
	}

	public void setPicture2ContentType(String picture2ContentType) {
		this.picture2ContentType = picture2ContentType;
	}

	public String getPicture2FileName() {
		return picture2FileName;
	}

	public void setPicture2FileName(String picture2FileName) {
		this.picture2FileName = picture2FileName;
	}

	public File getPicture3() {
		return picture3;
	}

	public void setPicture3(File picture3) {
		this.picture3 = picture3;
	}

	public String getPicture3ContentType() {
		return picture3ContentType;
	}

	public void setPicture3ContentType(String picture3ContentType) {
		this.picture3ContentType = picture3ContentType;
	}

	public String getPicture3FileName() {
		return picture3FileName;
	}

	public void setPicture3FileName(String picture3FileName) {
		this.picture3FileName = picture3FileName;
	}

	public File getPicture4() {
		return picture4;
	}

	public void setPicture4(File picture4) {
		this.picture4 = picture4;
	}

	public String getPicture4ContentType() {
		return picture4ContentType;
	}

	public void setPicture4ContentType(String picture4ContentType) {
		this.picture4ContentType = picture4ContentType;
	}

	public String getPicture4FileName() {
		return picture4FileName;
	}

	public void setPicture4FileName(String picture4FileName) {
		this.picture4FileName = picture4FileName;
	}

	public File getPicture5() {
		return picture5;
	}

	public void setPicture5(File picture5) {
		this.picture5 = picture5;
	}

	public String getPicture5ContentType() {
		return picture5ContentType;
	}

	public void setPicture5ContentType(String picture5ContentType) {
		this.picture5ContentType = picture5ContentType;
	}

	public String getPicture5FileName() {
		return picture5FileName;
	}

	public void setPicture5FileName(String picture5FileName) {
		this.picture5FileName = picture5FileName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	public List<SmsProject> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<SmsProject> projectList) {
		this.projectList = projectList;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public SmsProject getSmsProject() {
		return smsProject;
	}

	public void setSmsProject(SmsProject smsProject) {
		this.smsProject = smsProject;
	}

	public int getStepNum() {
		return stepNum;
	}

	public void setStepNum(int stepNum) {
		this.stepNum = stepNum;
	}


	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getWebTitle() {
		return webTitle;
	}

	public void setWebTitle(String webTitle) {
		this.webTitle = webTitle;
	}

	public String getWebContent() {
		return webContent;
	}

	public void setWebContent(String webContent) {
		this.webContent = webContent;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getPhoneGroupId() {
		return phoneGroupId;
	}

	public void setPhoneGroupId(String phoneGroupId) {
		this.phoneGroupId = phoneGroupId;
	}

	public List<SmsProjectGroupRel> getProjectGroupRelList() {
		return projectGroupRelList;
	}

	public void setProjectGroupRelList(List<SmsProjectGroupRel> projectGroupRelList) {
		this.projectGroupRelList = projectGroupRelList;
	}

	public List<SmsContentEx> getSmsContentExList() {
		return smsContentExList;
	}

	public void setSmsContentExList(List<SmsContentEx> smsContentExList) {
		this.smsContentExList = smsContentExList;
	}

	public Long getExId() {
		return exId;
	}

	public void setExId(Long exId) {
		this.exId = exId;
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

	public int getPhoneNumCount() {
		return phoneNumCount;
	}

	public void setPhoneNumCount(int phoneNumCount) {
		this.phoneNumCount = phoneNumCount;
	}

	public String getUserMoneyStr() {
		return userMoneyStr;
	}

	public void setUserMoneyStr(String userMoneyStr) {
		this.userMoneyStr = userMoneyStr;
	}

	public String getSymStr() {
		return symStr;
	}

	public void setSymStr(String symStr) {
		this.symStr = symStr;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2586038639785018623L;
}
