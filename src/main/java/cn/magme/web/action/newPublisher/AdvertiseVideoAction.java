package cn.magme.web.action.newPublisher;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import cn.magme.common.JsonResult;
import cn.magme.common.SenderMail;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.AdminEmail;
import cn.magme.pojo.AdvertiseVideo;
import cn.magme.pojo.Tag;
import cn.magme.service.AdminEmailService;
import cn.magme.service.AdvertiseVideoService;
import cn.magme.service.MailTemplateService;
import cn.magme.service.TagService;
import cn.magme.util.ConvertVideoToFlv;
import cn.magme.util.ExtPageInfo;
import cn.magme.util.FileOperate;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;
/**
 * 
 * @author fredy
 *
 */
@Results({@Result(name="success",location="/WEB-INF/pages/newPublisher/advertisevideo.ftl")
,@Result(name="addjsonsuccess",type="json",params={"root","jsonResult","contentType","text/html"})
,@Result(name="editjsonsuccess",type="json",params={"root","jsonResult","contentType","text/html"})})
public class AdvertiseVideoAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5437142526634207579L;
	
	private static final Logger log=Logger.getLogger(AdvertiseVideoAction.class);
	
	@Resource
	private AdvertiseVideoService advertiseVideoService;
	
	@Resource
	private TagService tagService;
	
	@Resource
	private MailTemplateService mailTemplateService;
	
	@Resource
	private AdminEmailService adminEmailService;
	
	@Resource
    private SenderMail senderMail;
	
	private static final int SIZE=10;
	
	public String execute(){
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		
		ExtPageInfo page = new ExtPageInfo();
		page.setLimit(SIZE);
		page.setStart((currentPage-1)*SIZE);
		AdvertiseVideo ad=new AdvertiseVideo();
		ad.setStatus(status);
		ad.setStartTime(startTime);
		ad.setEndTime(endTime);
		try {
			page=this.advertiseVideoService.queryByCondition(page,ad);
			long listCount = page.getTotal();
			pageNo = (listCount+SIZE-1)/SIZE;
			this.adList=page.getData();
			this.jsonResult.put("pageNo", pageNo);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		} catch (Exception e) {
			log.error("", e);
		}
		return "success";
	}
	
	/**
	 * 查询
	 * @return
	 */
	public String queryPageJson(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		AdvertiseVideo ad=new AdvertiseVideo();
		ad.setStartTime(startTime);
		ad.setEndTime(endTime);
		ad.setStatus(status);
		ExtPageInfo page = new ExtPageInfo();
		page.setLimit(SIZE);
		page.setStart((this.currentPage-1)*SIZE);
		try {
			page=this.advertiseVideoService.queryByCondition(page,ad);
			long listCount = page.getTotal();
			pageNo = (listCount+SIZE-1)/SIZE;
			this.adList=page.getData();
			this.jsonResult.put("pageNo", pageNo);
			this.jsonResult.put("adList", this.adList);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		} catch (Exception e) {
			log.error("", e);
		}
		return JSON;
	}
	
	public String addJson(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		if(this.adFile==null || this.endTime==null || this.startTime==null || StringUtils.isBlank(this.title) ){
			log.error("参数不正确");
			this.jsonResult.setMessage("参数不正确");
			return "addjsonsuccess";
		}
		AdvertiseVideo ad=new AdvertiseVideo();
		ad.setAdType(adType);
		//ad.setAdType(PojoConstant.ADVERTISEVIDEO.TYPE_INDEX_VIDEO);
		Date d=new Date();
		ad.setStartTime(this.startTime);
		if(this.startTime==null){
			ad.setStartTime(d);
		}
		ad.setEndTime(this.endTime);
		if(this.endTime.getTime()<=this.startTime.getTime()){
			this.jsonResult.setMessage("开始时间不能大于结束时间");
			return "addjsonsuccess";
		}
		ad.setImgUrl(imgUrl);
		ad.setKeyWord(keyWord);
		ad.setMediaUrl(mediaUrl);
		ad.setOpenTime(d);
		ad.setRemark(remark);
		//ad.setStartTime(d);
		ad.setDescription(description);
		if(this.startTime.getTime()<=d.getTime()){
			ad.setStatus(PojoConstant.ADVERTISEVIDEO.STATUS_OK);	
		}else{
			ad.setStatus(PojoConstant.ADVERTISEVIDEO.STATUS_INVILID);	
		}
		
		ad.setTitle(title);
		ad.setUserId(this.getSessionAdUserId());
		ad.setUserTypeId(PojoConstant.ADVERTISEVIDEO.USER_TYPE_EDIT);
		ad.setVerifyBeginTime(d);
		ad.setVerifyEndTime(d);
		try {
			long filename=System.currentTimeMillis();
			String newPath=this.systemProp.getAdLocalUrl()+File.separator+"video"
			+File.separator+this.getSessionAdUserId()+File.separator;
			File inputFilePath=new File(newPath+"tmp");
			if(!inputFilePath.exists()){
				inputFilePath.mkdirs();
			}
			String inputFile=newPath+"tmp"+File.separator+filename+".avi";
			String outputFile=newPath+filename+".flv";
			FileOperate.moveFile(this.adFile.getAbsolutePath(), inputFile);
			
			String serverPath=this.systemProp.getAdServerUrl()+"/video/"+this.getSessionAdUserId()+"/";
			ad.setMediaUrl(serverPath+filename+".flv");
			ad.setImgUrl(serverPath+filename+".jpg");
			//转换文件
			ConvertVideoToFlv cmd=new ConvertVideoToFlv(inputFile,this.systemProp,outputFile);
			cmd.start();
			//保存广告
			Long id=this.advertiseVideoService.insert(ad);
			//保存tag
			if(StringUtil.isNotBlank(this.tags)){
				String [] tagArr=this.tags.split(",");
					for(String tagStr:tagArr){
						Tag tag=new Tag();
						tag.setName(tagStr);
						tag.setObjectId(id);
						tag.setStatus(PojoConstant.TAG.STATUS_OK);
						tag.setType(PojoConstant.TAG.TYPE_VIDEO_AD);
						tag.setCreatedBy(this.getSessionAdUserId());
						tagService.insert(tag);
					}
			}
			
			
			if(id!=null && id>0){
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
				ad.setId(id);
				this.jsonResult.put("ad", ad);
			}
		} catch (Exception e) {
			log.error("",e);
		}
		return "addjsonsuccess";
	}
	
	public String delJson(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		
		AdvertiseVideo ad=this.advertiseVideoService.queryById(id);
		ad.setStatus(PojoConstant.ADVERTISEVIDEO.STATUS_INVILID);
		try {
			if(this.advertiseVideoService.update(ad)){
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
				this.jsonResult.put("ad", ad);
			}
		} catch (Exception e) {
			log.error("", e);
		}
//		try {
//			this.sendMailToPublisher(ad);
//		} catch (Exception e) {
//			log.error("send mail error",e);
//		}
		return JSON;
	}
	
	
//	private void sendMailToPublisher(AdvertiseVideo ad){
//		List<AdminEmail> adminMailList=adminEmailService.getAdminEmailAll();
//		//发邮件
//		String text = mailTemplateService.getTemplateStr(PojoConstant.EMAILTEMPLATE.FILE_ADVERTISING_VIDEO_DWON);
//		text=text.replace("{title}", ad.getTitle());
//		text=text.replace("{id}", String.valueOf(ad.getId()));
//		if(adminMailList!=null && adminMailList.size()>0){
//			for(AdminEmail email:adminMailList){
//				senderMail.sendMail(email.getEmailAddress(), text, PojoConstant.EMAILTEMPLATE.FILE_ADVERTISING_VIDEO_DWON, 0);
//			}
//			
//		}
//		
//	}
	
	public String getJson(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		AdvertiseVideo ad=this.advertiseVideoService.queryById(id);
		try {
			if(ad!=null && ad.getId()!=null && ad.getId()>0){
				List<Tag> tagList= this.tagService.queryByTypeAndObjectId(PojoConstant.TAG.TYPE_VIDEO_AD, ad.getId());
				this.tags="";
				if(tagList!=null && tagList.size()>0){
					for(Tag  tag:tagList){
						tags+=tag.getName()+",";
					}
				}
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
				this.jsonResult.put("tags", tags);
				this.jsonResult.put("ad", ad);
			}
		} catch (Exception e) {
			log.error("", e);
		}
		return JSON;
	}
	
	public String editJson(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		
		AdvertiseVideo ad=new AdvertiseVideo();
		ad.setId(id);
		//ad.setAdType(PojoConstant.ADVERTISEVIDEO.TYPE_INDEX_VIDEO);
		ad.setAdType(adType);
		ad.setEndTime(this.endTime);
		if(this.startTime==null){
			ad.setStartTime(new Date());
		}else{
			ad.setStartTime(this.startTime);
		}
		ad.setEndTime(this.endTime);
		if(this.endTime.getTime()<=this.startTime.getTime()){
			this.jsonResult.setMessage("开始时间不能大于结束时间");
			return "addjsonsuccess";
		}
		ad.setDescription(this.description);
		ad.setTitle(this.title);
		ad.setUserId(this.getSessionAdUserId());
		ad.setUserTypeId(PojoConstant.ADVERTISEVIDEO.USER_TYPE_EDIT);
		if(this.startTime.getTime()<=new Date().getTime()){
			ad.setStatus(PojoConstant.ADVERTISEVIDEO.STATUS_OK);	
		}else{
			ad.setStatus(PojoConstant.ADVERTISEVIDEO.STATUS_INVILID);	
		}
		try {
			if(this.adFile!=null){
				long filename=System.currentTimeMillis();
				String newPath=this.systemProp.getAdLocalUrl()+File.separator+"video"
				+File.separator+this.getSessionAdUserId()+File.separator;
				String inputFile=newPath+"tmp"+File.separator+filename+".avi";
				String outputFile=newPath+filename+".flv";
				FileOperate.moveFile(this.adFile.getAbsolutePath(), newPath);
				
				String serverPath=this.systemProp.getAdServerUrl()+"/video/"+this.getSessionAdUserId()+"/";
				ad.setMediaUrl(serverPath+filename+".flv");
				ad.setImgUrl(serverPath+filename+".jpg");
				//转换文件
				ConvertVideoToFlv cmd=new ConvertVideoToFlv(inputFile,this.systemProp,outputFile);
				cmd.start();
			}
			
			//保存tag
			if(StringUtil.isNotBlank(this.tags)){
				//删除tag
				String [] tagArr=this.tags.split(",");
					for(String tagStr:tagArr){
						Tag tag=new Tag();
						tag.setName(tagStr);
						tag.setObjectId(id);
						tag.setStatus(PojoConstant.TAG.STATUS_OK);
						tag.setType(PojoConstant.TAG.TYPE_VIDEO_AD);
						tag.setCreatedBy(this.getSessionAdUserId());
						tagService.insert(tag);
					}
			}
			
			
			if(this.advertiseVideoService.update(ad)){
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
				this.jsonResult.put("ad", ad);
			}
		} catch (Exception e) {
			log.error("", e);
		}
		return "editjsonsuccess";
	}

	
	private Long id;
	private Long userId;
	private Integer userTypeId;
	private String keyWord;
	private String title;
	private String description;
	private String mediaUrl;
	private String imgUrl;
	private Integer adType;
	private Date verifyBeginTime;
	private Date verifyEndTime;
	private Date openTime;
	private Date startTime;
	private Date endTime;
	private String remark;
	private Integer status;
	private Integer currentPage=1;
	private Integer pageSize=SIZE;
	private Long pageNo;
	private List<AdvertiseVideo> adList;
	private File adFile;
	
	private String tags;
	
	
	

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public File getAdFile() {
		return adFile;
	}

	public void setAdFile(File adFile) {
		this.adFile = adFile;
	}

	public List<AdvertiseVideo> getAdList() {
		return adList;
	}

	public void setAdList(List<AdvertiseVideo> adList) {
		this.adList = adList;
	}

	public Long getPageNo() {
		return pageNo;
	}

	public void setPageNo(Long pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getUserTypeId() {
		return userTypeId;
	}
	public void setUserTypeId(Integer userTypeId) {
		this.userTypeId = userTypeId;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMediaUrl() {
		return mediaUrl;
	}
	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public Integer getAdType() {
		return adType;
	}
	public void setAdType(Integer adType) {
		this.adType = adType;
	}
	public Date getVerifyBeginTime() {
		return verifyBeginTime;
	}
	public void setVerifyBeginTime(Date verifyBeginTime) {
		this.verifyBeginTime = verifyBeginTime;
	}
	public Date getVerifyEndTime() {
		return verifyEndTime;
	}
	public void setVerifyEndTime(Date verifyEndTime) {
		this.verifyEndTime = verifyEndTime;
	}
	public Date getOpenTime() {
		return openTime;
	}
	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
