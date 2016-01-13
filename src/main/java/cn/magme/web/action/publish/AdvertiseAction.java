///**
// * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
// * All right reserved.
// */
//package cn.magme.web.action.publish;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.util.Date;
//import java.util.Map;
//import java.util.regex.Pattern;
//
//import javax.annotation.Resource;
//
//import org.apache.log4j.Logger;
//import org.apache.struts2.convention.annotation.Result;
//import org.apache.struts2.convention.annotation.Results;
//
//import cn.magme.common.JsonResult;
//import cn.magme.constants.PojoConstant;
//import cn.magme.pojo.AdPosition;
//import cn.magme.pojo.AdUser;
//import cn.magme.pojo.AdposMapping;
//import cn.magme.pojo.Advertise;
//import cn.magme.pojo.Publisher;
//import cn.magme.service.AdPositionService;
//import cn.magme.service.AdvertiseService;
//import cn.magme.util.FileOperate;
//import cn.magme.util.NumberUtil;
//import cn.magme.util.StringUtil;
//import cn.magme.web.action.BaseAction;
//
///**
// * @author fredy.liu
// * @date 2011-6-7
// * @version $id$
// */
//@Results({@Result(name="jsonNoDownload",type="json",params={"root","jsonResult","contentType","text/html"}),
//	      @Result(name="editAdJson",type="json",params={"root","jsonResult","contentType","text/html"})})
//public class AdvertiseAction extends BaseAction {
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = -291517480983542803L;
//	
//	private static final Logger log=Logger.getLogger(AdvertiseAction.class);
//	private static int MAX_JPG_SIZE=1024*1024*8*2;//2M
//	private static int MAX_VIDEO_SIZE=1024*1024*8*15;//15M
//	private static final String VIDEO_TYPE=".*\\.avi$|.*\\.mpg$";
//	private static final String SWF_TYPE=".*\\.swf$";
//	private static final String JPG_TYPE=".*\\.jpeg$|.*\\.jpg$|.*\\.pjpeg$|.*\\.gif$|.*\\.png$";
//	private static final Pattern videoPattern=Pattern.compile(VIDEO_TYPE);
//	private static final Pattern jpgPattern=Pattern.compile(JPG_TYPE);
//	private static final Pattern swfPattern=Pattern.compile(SWF_TYPE);
//	
//	private static final String JPG_PATH="jpg";
//	private static final String VIDEO_PATH="video";
//	@Resource
//	private AdvertiseService advertiseService;
//	
//	@Resource
//	private AdPositionService adPositionService;
//	
//	public String queryAllCategories(){
//		return SUCCESS;
//	}
//	
//	public String addAdToPosJson(){
//		try {
//			AdUser adUser=this.getSessionAdUser();
//			userId=adUser.getId();
//			//默认是广告商上传
//			int userTypeId=PojoConstant.ADVERTISE.USER_TYPE_PUBLISHER;
//			if(adUser.getLevel()==PojoConstant.ADUSER.LEVEL_ADAGENCY){
//				userTypeId=PojoConstant.ADVERTISE.USER_TYPE_ADAGENCY;
//			}else if(adUser.getLevel()==PojoConstant.ADUSER.LEVEL_ADMAGME){
//				userTypeId=PojoConstant.ADVERTISE.USER_TYPE_ADMAGME;
//			}
//			
//			
//			//检查上传信息
//			String judgeMessage=this.checkUploadInfo();
//			
//			if(judgeMessage!=null){
//				jsonResult.setMessage(judgeMessage);
//				return JSON;
//			}
//			//移动文件
//			this.moveFile();
//			
//			String [] adposIdsArr=this.adposIds.split(",");
//			
//			//增加数据
//			for(String adpos:adposIdsArr){
//				AdPosition adposition=this.adPositionService.queryById(Long.parseLong(adpos));
//				//end add in 2011-08-17
//				Advertise ad=new Advertise();
//				//页面上传的值
//				ad.setUserId(userId);
//				ad.setAdType(adType);
//				ad.setDescription(description);
//				ad.setKeyword(keyword);
//				ad.setIssueId(issueId);
//				ad.setTitle(title);
//				ad.setUserTypeId(userTypeId);
//				ad.setStatus(PojoConstant.ADVERTISE.STATUS.VALID.getCode());
//				/*//有效期参数，暂时不用
//				ad.setStartTime(startTime);
//				ad.setEndTime(endTime);
//				//默认没有的参数
//				ad.setOpenTime(openTime);
//				ad.setVerifyBeginTime(verifyBeginTime);
//				ad.setVerifyBeginTime(this.verifyEndTime);*/
//				
//				//以下值计算出来的
//				ad.setAdposId(adposition.getId());
//				ad.setPageNo(adposition.getPageNo().longValue());
//				ad.setLinkurl(linkurl);
//				ad.setImgurl(imgurl);
//				ad.setMediaurl(mediaurl);
//				ad.setLeftSwfPath(this.leftSwfPath);
//				ad.setRightSwfPath(this.rightSwfPath);
//				
//				advertiseService.insert(ad);
//			}
//			
//			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
//			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
//		} catch (Exception e) {
//			log.error("", e);
//			this.jsonResult.setCode(JsonResult.CODE.FAILURE);
//			this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
//		}
//		return JSON;
//	}
//	
//	public String updateAdJson(){
//		try {
//			this.jsonResult=new JsonResult();
//			Advertise ad=this.advertiseService.queryById(adId);
//			Publisher publisher=this.getSessionPublisher();
//			userId=publisher.getId();
//			if(publisher.getId().longValue()!=ad.getUserId()){
//				//不能更新不是自己创建的广告
//				this.jsonResult.setCode(JsonResult.CODE.FAILURE);
//				this.jsonResult.setMessage("不能修改不是自己创建的广告");
//				return JSON;
//			}
//			if(StringUtil.isNotBlank(fileName) && fileName.toLowerCase().lastIndexOf(".jpg")!=fileName.length()-4){
//		    	   jsonResult.setCode(JsonResult.CODE.FAILURE);
//		           jsonResult.setMessage("上传文件类型必需为jpg文件");
//		    	   return "jsonNoDownload";
//		    }
//			if(this.jpgFile!=null && new FileInputStream(this.jpgFile).available()>MAX_JPG_SIZE){
//				jsonResult.setCode(JsonResult.CODE.FAILURE);
//		        jsonResult.setMessage("上传文件必需必需小于1M");
//		    	return "jsonNoDownload";
//			}
//			
//			if(this.jpgFile!=null && StringUtil.isNotBlank(this.fileName)){
//				File adFileDir=new File(systemProp.getAdLocalUrl()+File.separator+userId);
//				if(!adFileDir.exists()){
//					adFileDir.mkdirs();
//				}
//				FileOperate fileOperate = new FileOperate();
//		        fileOperate.moveFile(jpgFile.getAbsolutePath(), adFileDir.getAbsolutePath()+File.separator+this.fileName);
//		        fileOperate.delFile(jpgFile.getAbsolutePath());
//		        this.imgurl=this.systemProp.getAdServerUrl()+"/"+userId+"/"+this.fileName;
//			}
//			
//			ad.setAdType(adType);
//			ad.setDescription(description);
//			ad.setImgurl(imgurl);
//			ad.setKeyword(keyword);
//			ad.setLinkurl(linkurl);
//			ad.setMediaurl(mediaurl);
//			ad.setTitle(title);
//			advertiseService.updateByIdNoNullLimit(ad);
//			
//			//结果
//			this.jsonResult.put("ad", ad);
//			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
//			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
//		} catch (Exception e) {
//			log.error("'", e);
//			this.jsonResult.setCode(JsonResult.CODE.FAILURE);
//			this.jsonResult.setMessage("修改广告出错");
//		}
//		return "editAdJson";
//	}
//	
//	public String queryByIdJson(){
//		this.jsonResult=new JsonResult();
//		try {
//			Advertise ad=this.advertiseService.queryById(adId);
//			//结果
//			this.jsonResult.put("ad", ad);
//			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
//			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
//		} catch (Exception e) {
//			log.error("'", e);
//			this.jsonResult.setCode(JsonResult.CODE.FAILURE);
//			this.jsonResult.setMessage("查询广告出错");
//		}
//		return JSON;
//	}
//	
//	/**
//	 * 判断上传文件类型
//	 * @return 返回空代表正确，返回有信息代表失败
//	 */
//	private String checkUploadInfo()throws Exception{
//		if(NumberUtil.isLessThanOrEqual0(this.adType) || this.adType<1 || this.adType>5 || this.adType==4){
//			log.error("广告类型adtype必需为1,2,3,5");
//			return "广告类型adtype必需为1,2,3,5";
//		}
//		
//		if(NumberUtil.isLessThanOrEqual0(issueId)){
//			return "issueId必需大于0";
//		}
//		
//		if(StringUtil.isBlank(title)){
//			return "title不能为空";
//		}
//		
//		//图片广告
//		if(this.adType==PojoConstant.ADVERTISE.ADTYPE.IMAGE.getCode()){
//			if(!jpgPattern.matcher(this.fileName).matches()){
//				return "图片文件只允许JPG,JPEG,PJPEG,GIF,PNG类型";
//			}
//			if(this.jpgFile==null ||  new FileInputStream(this.jpgFile).available()>MAX_JPG_SIZE){
//		 	    return "上传图片文件必需必需小于2M";
//			}
//		}else if(this.adType==PojoConstant.ADVERTISE.ADTYPE.VEDIO.getCode()){
//			if(!videoPattern.matcher(this.fileName).matches()){
//				return "视频文件只允许avi,mpg类型";
//			}
//			if(this.videoFile==null ||  new FileInputStream(this.videoFile).available()>MAX_VIDEO_SIZE){
//		 	    return "上传视频文件必需必需小于15M";
//			}
//		}else if( this.adType==PojoConstant.ADVERTISE.ADTYPE.INSERT.getCode()){
//			if(!swfPattern.matcher(this.fileName).matches()){
//				return "视频文件只允许swf类型";
//			}
//			if(this.videoFile==null ||  new FileInputStream(this.videoFile).available()>MAX_VIDEO_SIZE){
//		 	    return "上传视频文件必需必需小于15M";
//			}
//		}
//		
//		return null;
//	}
//	
//	
//	
//	/**
//	 * 移动广告上传文件到合适额位置
//	 */
//	private void moveFile(){
//		String filePath=null;
//		File adFileDir=null;
//		if(this.jpgFile!=null){
//			filePath=systemProp.getAdLocalUrl()+File.separator+JPG_PATH+File.separator;
//			adFileDir=new File(filePath+userId);
//		}else if(this.videoFile!=null){
//			filePath=systemProp.getAdLocalUrl()+File.separator+VIDEO_PATH+File.separator;
//			adFileDir=new File(filePath+userId);
//		}
//		
//		if(filePath!=null && adFileDir!=null){
//			if(!adFileDir.exists()){
//				adFileDir.mkdirs();
//			}
//			String tmpFileName=System.currentTimeMillis()+this.fileName.substring(this.fileName.lastIndexOf("."));
//			FileOperate fileOperate = new FileOperate();
//	        fileOperate.moveFile(jpgFile.getAbsolutePath(), adFileDir.getAbsolutePath()+File.separator+tmpFileName);
//	        fileOperate.delFile(jpgFile.getAbsolutePath());
//	        if(this.jpgFile!=null){
//	        	this.imgurl=filePath+tmpFileName;
//	        	
//	        }else if(this.videoFile!=null && this.adType==PojoConstant.ADVERTISE.ADTYPE.VEDIO.getCode()){
//	        	//TODO 新开一个线程处理视频
//	        	this.mediaurl=filePath+tmpFileName;
//	        //插页广告
//	        }else if(this.videoFile!=null  && this.adType==PojoConstant.ADVERTISE.ADTYPE.INSERT.getCode()){
//	        	this.leftSwfPath=filePath+tmpFileName;
//	        	
//	        }
//		}
//		
//		
//	}
//	private Long adId;
//	private Long userId;
//	private String keyword;
//	private String title;
//	private String description;
//	private String linkurl;
//	private String imgurl;
//	private String mediaurl;
//	private Integer adType;
//	private String adposIds;
//	private Date startTime;
//	private Date endTime;
//	private String fileName;
//	//private Map<String,AdPosition> adposMap;
//	private Long id;
//	private File jpgFile;
//	private Long addAdId;
//	private File videoFile;
//	
//	
//	private Long imageId;
//	private Date verifyBeginTime;
//	private Date verifyEndTime;
//	private Date openTime;
//	private Long adposId;
//	private Long issueId;
//	private Long pageNo;
//	private String leftSwfPath;
//	private String rightSwfPath;
//	
//	
//	
//	public File getVideoFile() {
//		return videoFile;
//	}
//
//	public void setVideoFile(File videoFile) {
//		this.videoFile = videoFile;
//	}
//
//	public Long getImageId() {
//		return imageId;
//	}
//
//	public void setImageId(Long imageId) {
//		this.imageId = imageId;
//	}
//
//	public Date getVerifyBeginTime() {
//		return verifyBeginTime;
//	}
//
//	public void setVerifyBeginTime(Date verifyBeginTime) {
//		this.verifyBeginTime = verifyBeginTime;
//	}
//
//	public Date getVerifyEndTime() {
//		return verifyEndTime;
//	}
//
//	public void setVerifyEndTime(Date verifyEndTime) {
//		this.verifyEndTime = verifyEndTime;
//	}
//
//	public Date getOpenTime() {
//		return openTime;
//	}
//
//	public void setOpenTime(Date openTime) {
//		this.openTime = openTime;
//	}
//
//	public Long getAdposId() {
//		return adposId;
//	}
//
//	public void setAdposId(Long adposId) {
//		this.adposId = adposId;
//	}
//
//	public Long getIssueId() {
//		return issueId;
//	}
//
//	public void setIssueId(Long issueId) {
//		this.issueId = issueId;
//	}
//
//	public Long getPageNo() {
//		return pageNo;
//	}
//
//	public void setPageNo(Long pageNo) {
//		this.pageNo = pageNo;
//	}
//
//	public String getLeftSwfPath() {
//		return leftSwfPath;
//	}
//
//	public void setLeftSwfPath(String leftSwfPath) {
//		this.leftSwfPath = leftSwfPath;
//	}
//
//	public String getRightSwfPath() {
//		return rightSwfPath;
//	}
//
//	public void setRightSwfPath(String rightSwfPath) {
//		this.rightSwfPath = rightSwfPath;
//	}
//
//	public Long getAddAdId() {
//		return addAdId;
//	}
//
//	public void setAddAdId(Long addAdId) {
//		this.addAdId = addAdId;
//	}
//
//	public String getFileName() {
//		return fileName;
//	}
//
//	public void setFileName(String fileName) {
//		this.fileName = fileName;
//	}
//
//	public File getJpgFile() {
//		return jpgFile;
//	}
//
//	public void setJpgFile(File jpgFile) {
//		this.jpgFile = jpgFile;
//	}
//
//	/**
//	 * 映射
//	 */
//	private Map<String,AdposMapping> adposmappingMap;
//	
//	
//	
//
//	public Map<String, AdposMapping> getAdposmappingMap() {
//		return adposmappingMap;
//	}
//
//	public void setAdposmappingMap(Map<String, AdposMapping> adposmappingMap) {
//		this.adposmappingMap = adposmappingMap;
//	}
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public String getAdposIds() {
//		return adposIds;
//	}
//
//	public void setAdposIds(String adposIds) {
//		this.adposIds = adposIds;
//	}
//
//	public Date getStartTime() {
//		return startTime;
//	}
//
//	public void setStartTime(Date startTime) {
//		this.startTime = startTime;
//	}
//
//	public Date getEndTime() {
//		return endTime;
//	}
//
//	public void setEndTime(Date endTime) {
//		this.endTime = endTime;
//	}
//	
//	public Long getAdId() {
//		return adId;
//	}
//
//	public void setAdId(Long adId) {
//		this.adId = adId;
//	}
//
//	public Long getUserId() {
//		return userId;
//	}
//	public void setUserId(Long userId) {
//		this.userId = userId;
//	}
//	public String getKeyword() {
//		return keyword;
//	}
//	public void setKeyword(String keyword) {
//		this.keyword = keyword;
//	}
//	public String getTitle() {
//		return title;
//	}
//	public void setTitle(String title) {
//		this.title = title;
//	}
//	public String getDescription() {
//		return description;
//	}
//	public void setDescription(String description) {
//		this.description = description;
//	}
//	public String getLinkurl() {
//		return linkurl;
//	}
//	public void setLinkurl(String linkurl) {
//		this.linkurl = linkurl;
//	}
//	public String getImgurl() {
//		return imgurl;
//	}
//	public void setImgurl(String imgurl) {
//		this.imgurl = imgurl;
//	}
//	public String getMediaurl() {
//		return mediaurl;
//	}
//	public void setMediaurl(String mediaurl) {
//		this.mediaurl = mediaurl;
//	}
//	public Integer getAdType() {
//		return adType;
//	}
//	public void setAdType(Integer adType) {
//		this.adType = adType;
//	}
//
//}
