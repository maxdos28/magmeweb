/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.ad;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import cn.magme.common.JsonResult;
import cn.magme.common.SenderMail;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.AdPosition;
import cn.magme.pojo.AdSide;
import cn.magme.pojo.AdUser;
import cn.magme.pojo.Advertise;
import cn.magme.pojo.AdvertiseLog;
import cn.magme.pojo.Issue;
import cn.magme.pojo.Publication;
import cn.magme.pojo.Publisher;
import cn.magme.service.AdPositionService;
import cn.magme.service.AdSideService;
import cn.magme.service.AdvertiseLogService;
import cn.magme.service.AdvertiseService;
import cn.magme.service.IssueService;
import cn.magme.service.MailTemplateService;
import cn.magme.service.PublicationService;
import cn.magme.service.PublisherService;
import cn.magme.util.ConvertVideoToFlv;
import cn.magme.util.FileOperate;
import cn.magme.util.MagPdfChop;
import cn.magme.util.NumberUtil;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

/**
 * @author fredy.liu
 * @date 2011-6-7
 * @version $id$
 */
@Results({@Result(name="jsonNoDownload",type="json",params={"root","jsonResult","contentType","text/html"}),
	      @Result(name="editAdJson",type="json",params={"root","jsonResult","contentType","text/html"})})
public class AdvertiseAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -291517480983542803L;
	
	private static final Logger log=Logger.getLogger(AdvertiseAction.class);
	private static int MAX_JPG_SIZE=1024*1024*8*2;//2M
	private static int MAX_VIDEO_SIZE=1024*1024*8*100;//100M
	private static final String VIDEO_TYPE=".*\\.avi$|.*\\.mpg$|.*\\.mp4$|.*\\.flv|.*\\.mov$";
	private static final String VIDEO_FLV_TYPE=".*\\.flv$";
	//private static final String SWF_TYPE=".*\\.swf$";
	private static final String JPG_TYPE=".*\\.jpeg$|.*\\.jpg$|.*\\.pjpeg$|.*\\.gif$|.*\\.png$";
	private static final String PDF_TYPE=".*\\.pdf$";
	private static final Pattern videoPattern=Pattern.compile(VIDEO_TYPE,Pattern.CASE_INSENSITIVE);
	private static final Pattern videoFlvPattern=Pattern.compile(VIDEO_FLV_TYPE,Pattern.CASE_INSENSITIVE);
	private static final Pattern jpgPattern=Pattern.compile(JPG_TYPE);
	//private static final Pattern swfPattern=Pattern.compile(SWF_TYPE);
	private static final Pattern insertPattern=Pattern.compile(PDF_TYPE);
	private static final int FROM_FLASH=1;//上传文件来自于flash
	private static final String SERVER_SEP="/";
	private static final long DAY_OF_3=3L*24*60*60*1000;
	private static final String COMMA_SEP=",";
	
	private static final String JPG_PATH="jpg";
	private static final String VIDEO_PATH="video";
	private static final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	@Resource
	private AdvertiseService advertiseService;
	
	@Resource
	private AdPositionService adPositionService;
	
	@Resource
	private MailTemplateService mailTemplateService;
	@Resource
	private PublicationService publicationService;
	@Resource
	private IssueService issueService;
	
	@Resource
	private PublisherService publisherService;
	
    @Resource
    private AdSideService adSideService;
	   
    //@Resource
    //private UserImageService userImageService;
    
	@Resource
    private SenderMail senderMail;
	
	@Resource
	private AdvertiseLogService advertiseLogService;
	

	/**
	 * 互动广告的添加
	 * @return
	 */
	public String addAdToPosJson(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		try {
			AdUser adUser=this.getSessionAdUser();
			userId=adUser.getId();
			//默认是广告商上传
			int userTypeId=PojoConstant.ADVERTISE.USER_TYPE_PUBLISHER;
			if(adUser.getLevel()==PojoConstant.ADUSER.LEVEL_ADAGENCY){
				userTypeId=PojoConstant.ADVERTISE.USER_TYPE_ADAGENCY;
			}else if(adUser.getLevel()==PojoConstant.ADUSER.LEVEL_ADMAGME){
				userTypeId=PojoConstant.ADVERTISE.USER_TYPE_ADMAGME;
			}
			
			//检查上传信息
			String judgeMessage=this.checkUploadInfo();
			
			if(judgeMessage!=null){
				jsonResult.setMessage(judgeMessage);
				return "editAdJson";
			}
			//移动文件
			if(this.adType!=PojoConstant.ADVERTISE.ADTYPE.IMAGE.getCode() || StringUtil.isBlank(imgurl)){
				this.moveFile();
			}
			
			String [] adposIdsArr=this.adposIds.split(",");
			
			//增加数据
			for(String adpos:adposIdsArr){
				if(StringUtil.isBlank(adpos)){
					log.error("adpos:"+adpos);
				}else{
					AdPosition adposition=this.adPositionService.queryById(Long.parseLong(adpos));
					//end add in 2011-08-17
					Advertise ad=new Advertise();
					//页面上传的值
					ad.setUserId(userId);
					ad.setAdType(adType);
					ad.setDescription(description);
					ad.setKeyword(keyword);
					ad.setIssueId(adposition.getIssueId());
					ad.setTitle(title);
					ad.setUserTypeId(userTypeId);
					ad.setStartTime(sdf.parse(this.startTime));
					ad.setEndTime(sdf.parse(this.endTime));
					Issue issue=this.issueService.queryById(issueId);
					Publication publication=publicationService.queryById(issue.getPublicationId());
					Publisher publisher=this.publisherService.queryById(publication.getPublisherId());
					if(userTypeId==2){
						ad.setStatus(PojoConstant.ADVERTISE.STATUS.ADMIN_ON.getCode());
					}else{
						if(publisher !=null && publisher.getLevel()==1){
							ad.setStatus(PojoConstant.ADVERTISE.STATUS.VALID.getCode());
						}else{
							ad.setStatus(PojoConstant.ADVERTISE.STATUS.ADMIN_ON.getCode());
						}
					}
					/*//有效期参数，暂时不用
					ad.setStartTime(startTime);
					ad.setEndTime(endTime);
					//默认没有的参数
					ad.setOpenTime(openTime);
					ad.setVerifyBeginTime(verifyBeginTime);
					ad.setVerifyBeginTime(this.verifyEndTime);*/
					
					//以下值计算出来的
					ad.setAdposId(adposition.getId());
					ad.setPageNo(adposition.getPageNo().longValue());
					ad.setLinkurl(linkurl);
					ad.setImgurl(imgurl);
					ad.setMediaurl(mediaurl);
					ad.setLeftSwfPath(this.leftSwfPath);
					ad.setRightSwfPath(this.rightSwfPath);
					adposition.setStatus(2);
					long now=System.currentTimeMillis();
					ad.setVerifyBeginTime(new Date(now));
					ad.setVerifyEndTime(new Date(now+DAY_OF_3));
					this.adPositionService.updateById(adposition);
					advertiseService.insert(ad);
					this.sendMailToPublisher(ad,adposition);
					
                    String msg="对广告位[id="+adposition.getId()+"]执行操作:"+PojoConstant.ADVERTISELOG.ACTIONTYPE.ADD_AD_TO_POS.getMsg()+"[id="+ad.getId()+"]";
                    AdvertiseLog advertiseLog=new AdvertiseLog();
                    advertiseLog.setUserId(userId);
                    advertiseLog.setUserType(userTypeId);
                    advertiseLog.setObjectId(adposition.getId());
                    advertiseLog.setObjectType(PojoConstant.ADVERTISELOG.OBJECTTYPE_ADPOSITION);
                    advertiseLog.setActionType(PojoConstant.ADVERTISELOG.ACTIONTYPE.ADD_AD_TO_POS.getCode());
                    advertiseLog.setDescription(msg);
                    advertiseLogService.insert(advertiseLog);					
				}
			}
			//this.jsonResult.put("adposid", adposIds);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		} catch (Exception e) {
			log.error("", e);
			this.jsonResult.setCode(JsonResult.CODE.FAILURE);
			this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		}
		return "editAdJson";
	}
	
	private void sendMailToPublisher(Advertise ad,AdPosition adposition){
		AdUser adUser=this.getSessionAdUser();
		long issueId=adposition.getIssueId();
		Issue issue=this.issueService.queryById(issueId);
		Publication publication=publicationService.queryById(issue.getPublicationId());
		Publisher publisher=this.publisherService.queryById(publication.getPublisherId());
		//发邮件
		String text = mailTemplateService.getTemplateStr(PojoConstant.EMAILTEMPLATE.CONTENT.FILE_PURCHASEAD.getFileName());
		text=text.replace("{publisherName}", publisher.getPublishName());//
		text=text.replace("{adAgencyName}", adUser.getName());
		text=text.replace("{issueName}", issue.getPublicationName()+issue.getIssueNumber());
		text=text.replace("{adId}", String.valueOf(ad.getId()));
		text=text.replace("{title}", ad.getTitle());
        senderMail.sendMail(publisher.getEmail(), text, PojoConstant.EMAILTEMPLATE.CONTENT.FILE_PURCHASEAD.getSubject(), 0);
		
	}
	/**
	 * 判断上传文件类型
	 * @return 返回空代表正确，返回有信息代表失败
	 */
	private String checkUploadInfo()throws Exception{
		if(this.adType==null || NumberUtil.isLessThanOrEqual0(this.adType) || this.adType<1 || this.adType>5 || this.adType==4){
			log.error("广告类型adtype必需为1,2,3,5");
			return "广告类型adtype必需为1,2,3,5";
		}
		
		if(StringUtil.isBlank(title)){
			return "title不能为空";
		}
		//判断文件类型
		if(this.adType==PojoConstant.ADVERTISE.ADTYPE.IMAGE.getCode()){
			//只有当imgurl为空的时候才判断文件类型，因为imgurl有值的时候认为是外链接,外链接不需要上传文件
			if(StringUtil.isBlank(this.imgurl) && !jpgPattern.matcher(this.fileName).matches()){
				return "图片文件只允许JPG,JPEG,PJPEG,GIF,PNG类型";
			}
			
		}
		if(this.adType==PojoConstant.ADVERTISE.ADTYPE.VEDIO.getCode()){
			if(!videoPattern.matcher(this.fileName).matches()){
				return "视频文件只允许avi,mpg,mp4,flv类型";
			}
		}else if( this.adType==PojoConstant.ADVERTISE.ADTYPE.INSERT.getCode()){
			//插页广告改成由swf类型改成pdf类型 add by liufosheng in 2012-02-21
			if(!insertPattern.matcher(this.fileName).matches()){
				return "插页广告文件只允许pdf类型";
			}
		}
		
		//内容来自于form表单
		if(this.fromFlash!=FROM_FLASH){
			//图片广告
			if(this.adType==PojoConstant.ADVERTISE.ADTYPE.IMAGE.getCode()){
				//只有当imgurl为空的时候才判断文件类型，因为imgurl有值的时候认为是外链接,外链接不需要上传文件
				if(StringUtil.isBlank(this.imgurl) && ( this.jpgFile==null ||  new FileInputStream(this.jpgFile).available()>MAX_JPG_SIZE)){
			 	    return "上传图片文件必需必需小于2M";
				}
			}
			if(this.adType==PojoConstant.ADVERTISE.ADTYPE.VEDIO.getCode()){
				if(this.jpgFile==null ||  new FileInputStream(this.jpgFile).available()>MAX_VIDEO_SIZE){
			 	    return "上传视频文件必需必需小于"+MAX_VIDEO_SIZE+"M";
				}
			}else if( this.adType==PojoConstant.ADVERTISE.ADTYPE.INSERT.getCode()){
				if(this.jpgFile==null ||  new FileInputStream(this.jpgFile).available()>MAX_VIDEO_SIZE){
			 	    return "上传插页视频文件必需必需小于"+MAX_VIDEO_SIZE+"M";
				}
			}
		}else{
			//使用flash上传文件,不做任何判断
		}
		
		return null;
	}
	
	
	
	/**
	 * 插页广告的添加
	 * @return
	 */
	public String addInsertAdvertise(){
		this.jsonResult=new JsonResult();
		jsonResult.setCode(JsonResult.CODE.FAILURE);
		AdUser adUser=this.getSessionAdUser();
		userId=adUser.getId();
		//默认是广告商上传
		int userTypeId=PojoConstant.ADVERTISE.USER_TYPE_PUBLISHER;
		if(adUser.getLevel()==PojoConstant.ADUSER.LEVEL_ADAGENCY){
			userTypeId=PojoConstant.ADVERTISE.USER_TYPE_ADAGENCY;
		}else if(adUser.getLevel()==PojoConstant.ADUSER.LEVEL_ADMAGME){
			userTypeId=PojoConstant.ADVERTISE.USER_TYPE_ADMAGME;
		}
	
		try {
			String checkUploadInfo=this.checkUploadInfo();
			if(StringUtil.isNotBlank(checkUploadInfo)){
				jsonResult.setMessage(checkUploadInfo);
				return JSON;
			}
			//移动文件
			//this.moveFile();
			String insertServerPath=systemProp.getAdServerUrl()+SERVER_SEP+this.leftSwfPath.substring(0, this.leftSwfPath.indexOf("tmp"));
			//String pdfFileName=this.leftSwfPath.substring(this.leftSwfPath.indexOf("tmp/")+4);
			Integer pageNum=MagPdfChop.getPdfPageNum(systemProp.getAdLocalUrl()+SERVER_SEP+this.leftSwfPath);
			if(pageNum==null){
				this.jsonResult.setMessage("服务器错误");
				return JSON;
			}else if(pageNum<2){
				this.jsonResult.setMessage("pdf文件少于两页");
				return JSON;
			}
			//end add in 2011-08-17
			Advertise ad=new Advertise();
			//页面上传的值  
			ad.setUserId(userId);
			ad.setAdType(adType);
			ad.setDescription(description);
			ad.setKeyword(keyword);
			ad.setIssueId(this.issueId);
			ad.setTitle(title);
			ad.setUserTypeId(userTypeId);
			Issue issue=this.issueService.queryById(issueId);
			Publication publication=publicationService.queryById(issue.getPublicationId());
			Publisher publisher=this.publisherService.queryById(publication.getPublisherId());
			if(userTypeId==5){//只有编辑可以进行 插页操作
				if(publisher !=null && publisher.getLevel()==1){
					ad.setStatus(PojoConstant.ADVERTISE.STATUS.VALID.getCode());
				}else{
					ad.setStatus(PojoConstant.ADVERTISE.STATUS.ADMIN_ON.getCode());
				}
			}
			//以下值计算出来的
			ad.setAdposId(null);
			ad.setPageNo(this.pageNo);
			ad.setLeftSwfPath(insertServerPath);
			ad.setRightSwfPath(insertServerPath);
			ad.setStartTime(new Date());
			if(StringUtils.isNotBlank(this.endTime)){
				try {
					ad.setEndTime(sdf.parse(this.endTime));
				} catch (Exception e) {
					log.error("", e);
				}
			}
			
			long now=System.currentTimeMillis();
			ad.setVerifyBeginTime(new Date(now));
			ad.setVerifyEndTime(new Date(now+DAY_OF_3));
			ad.setRemark(this.leftSwfPath);
			Long adid=advertiseService.insert(ad);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			//TODO 调用shell处理pdf 传递参数3个，文件名，广告id，后台用户id;生成结果，把pdf处理成两页swf和相应规则的图片
			new ConvertPdfToInsertAd(adid,this.userId,this.leftSwfPath).start();
		} catch (Exception e) {
			log.error("", e);
			this.jsonResult.setMessage("失败");
		}
		
		return JSON;
	}
	
	/**
	 * 移动广告上传文件到合适的位置
	 */
	private void moveFile() throws Exception{
		String filePath=null;
		File adFileDir=null;
		String serverFilePath=systemProp.getAdServerUrl()+SERVER_SEP;
		//来自于flash或者来自后台的图片文件
		if((this.jpgFile!=null || this.fromFlash==FROM_FLASH) && this.adType==PojoConstant.ADVERTISE.ADTYPE.IMAGE.getCode()){
			filePath=systemProp.getAdLocalUrl()+File.separator+JPG_PATH+File.separator;
			serverFilePath+=JPG_PATH+SERVER_SEP+userId+SERVER_SEP;
			adFileDir=new File(filePath+userId);
		}else if((this.jpgFile!=null || this.fromFlash==FROM_FLASH) && (this.adType==PojoConstant.ADVERTISE.ADTYPE.VEDIO.getCode() ||
				this.adType==PojoConstant.ADVERTISE.ADTYPE.INSERT.getCode())){
			filePath=systemProp.getAdLocalUrl()+File.separator+VIDEO_PATH+File.separator;
			serverFilePath+=VIDEO_PATH+SERVER_SEP+userId+SERVER_SEP;
			adFileDir=new File(filePath+userId);
		}
		
		//来自flash
		if(this.fromFlash==FROM_FLASH){
			/*ActionContext ctx=ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST); 
            BufferedInputStream inputStream = new BufferedInputStream(request.getInputStream());
            String tmpFileName=System.currentTimeMillis()+this.fileName.substring(this.fileName.lastIndexOf("."));
            this.jpgFile=new File(adFileDir.getAbsolutePath()+File.separator+"tmp"+tmpFileName);
            File tmpFile=new File(adFileDir.getAbsolutePath()+File.separator+"tmp");
            if(!tmpFile.exists()){
            	tmpFile.mkdirs();
			}
            
            FileOutputStream outputStream = new FileOutputStream( this.jpgFile );
            byte[] bytes = new byte[1024];
            int v=0;
            while ((   v = inputStream.read(bytes)   ) > 0) {
                outputStream.write(bytes, 0, v);
            }
            outputStream.close();
            inputStream.close();*/
			
			//插页广告左页
			if(this.adType==PojoConstant.ADVERTISE.ADTYPE.INSERT.getCode()){
				this.jpgFile=new File(this.systemProp.getAdLocalUrl()+File.separator+this.leftSwfPath);
			}else{
				this.jpgFile=new File(this.systemProp.getAdLocalUrl()+File.separator+serverPath);
			}
			
		}
 
		if(filePath!=null && adFileDir!=null){
			if(!adFileDir.exists()){
				adFileDir.mkdirs();
			}
			long currentTime=System.currentTimeMillis();
			String tmpFileName=currentTime+this.fileName.substring(this.fileName.lastIndexOf("."));
			//FileOperate fileOperate = new FileOperate();
	        FileOperate.moveFile(jpgFile.getAbsolutePath(), adFileDir.getAbsolutePath()+File.separator+tmpFileName);
	        //fileOperate.delFile(jpgFile.getAbsolutePath());
	        
	        if(this.adType==PojoConstant.ADVERTISE.ADTYPE.IMAGE.getCode()){
	        	this.imgurl=serverFilePath+tmpFileName;
	        }else if(this.adType==PojoConstant.ADVERTISE.ADTYPE.VEDIO.getCode()){
	        	//如果是flv广告则不需要这一步
	        	if(videoFlvPattern.matcher(this.jpgFile.getName()).matches()){
	        		FileOperate.copyFile(adFileDir.getAbsolutePath()+File.separator+tmpFileName, adFileDir.getAbsolutePath());
	        	}else{
	        		//新开一个线程处理视频
		        	//new ConvertVideoToFlv(adFileDir.getAbsolutePath()+File.separator+tmpFileName,this.systemProp).start();
		        	ConvertVideoToFlv convertCmd=new ConvertVideoToFlv(adFileDir.getAbsolutePath()+File.separator+tmpFileName,
		        			this.systemProp,adFileDir.getAbsolutePath()+File.separator+currentTime+".flv");
		        	convertCmd.start();
	        	}
	        	this.mediaurl=serverFilePath+currentTime+".flv";
	        //插页广告
	        }else if(this.adType==PojoConstant.ADVERTISE.ADTYPE.INSERT.getCode()){
	        	this.leftSwfPath=serverFilePath+tmpFileName;
	        	File tmpFile=new File(this.systemProp.getAdLocalUrl()+File.separator+this.rightSwfPath);
	        	String destRightFile=System.currentTimeMillis()+".swf";
	        	FileOperate.moveFile(tmpFile.getAbsolutePath(), adFileDir.getAbsolutePath()+File.separator+destRightFile);
	        	//fileOperate.delFile(tmpFile.getAbsolutePath());
	        	this.rightSwfPath=serverFilePath+destRightFile;
	        }
		}
		
		
	}
	
	
	public String queryAdsById(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		if(this.issueId==null || this.issueId<=0){
			return JSON;
		}
		
		try {
			String [] statuses=null;
			if(StringUtil.isNotBlank(this.status)){
				statuses=status.split(COMMA_SEP);
			}
			
			List<Advertise> adList=this.advertiseService.queryNotInsertAdsByIssueId(issueId,statuses);
			List<Advertise> insertAdList=this.advertiseService.queryInsertAdsByIssueId(issueId,statuses);
			List<AdPosition> pureAdPositionList=this.adPositionService.queryContentAdsByIssueIdWithoutAd(issueId);
			List<AdSide> adSideList=adSideService.getReleaseListByIssueId(issueId);
			//List<UserImage> userImageList=userImageService.getReaderRightUserImageList(issueId,0,40);
			this.jsonResult.put("adList", adList);
			this.jsonResult.put("pureAdPositionList", pureAdPositionList);
			this.jsonResult.put("insertAdList", insertAdList);
			this.jsonResult.put("adSideList", adSideList);
			//this.jsonResult.put("userImageList", userImageList);
			this.jsonResult.put("adServerUrl", systemProp.getAdServerUrl());
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		} catch (Exception e) {
			log.error("", e);
		}
		
		return JSON;
	}
	
	public static void main(String[] args) throws Exception{
		
		Connection conn = new Connection("203.166.162.44");
		conn.connect(); // 连接
		conn.authenticateWithPassword("root", "jAw483Yp");
		//Session session = conn.openSession();
		/*AdvertiseAction ad=new AdvertiseAction();
		SystemProp prop=new SystemProp();
		prop.setMagJobIp("203.166.162.44");
		prop.setMagJobUser("root");
		prop.setMagJobPwd("jAw483Yp");
		ad.setSystemProp(prop);
		ConvertPdfToInsertAd cmd=ad.new ConvertPdfToInsertAd(121L,1111L,"video/1111/tmp/1328601580462_0_1.pdf");
		cmd.start();*/
		
	}
	private class ConvertPdfToInsertAd extends Thread{
		private Long adid;
		private Long userid;
		private String filePath;
		private final Logger log2=Logger.getLogger(ConvertPdfToInsertAd.class);
		public ConvertPdfToInsertAd(Long adid,Long userid,String filePath){
			this.adid=adid;
			this.userid=userid;
			this.filePath=filePath;
		}
		
		@Override
		public void run(){
			String transferCmd="/usr/local/magme_mars/src/insertAdProcess.sh adid filePath userid";
			transferCmd=transferCmd.replaceAll("adid", String.valueOf(this.adid));
			transferCmd=transferCmd.replaceAll("userid", String.valueOf(this.userid));
			transferCmd=transferCmd.replaceAll("filePath", String.valueOf(this.filePath));
			//使用执行远程shell方式执行这段脚本
			try {
				Connection conn = new Connection(systemProp.getMagJobIp());
				conn.connect(); // 连接
				if(conn.authenticateWithPassword(systemProp.getMagJobUser(), systemProp.getMagJobPwd())){
					// 认证
					Session session = conn.openSession(); // 打开一个会话
				    session.execCommand(transferCmd);
				    InputStream in = session.getStdout();
				    log.info("reDealCmd:"+transferCmd);
				    System.out.println(transferCmd);
				    String   result = this.processStdout(in, Charset.defaultCharset().toString());
				    log.info("执行远程shell信息："+result);
				    conn.close();
				}
			} catch (IOException e) {
				log2.error("",e);
			}
		}
		private String processStdout(InputStream in, String charset) {
			   byte[] buf = new byte[1024];
			   StringBuffer sb = new StringBuffer();
			   try {
			    while (in.read(buf) != -1) {
			     sb.append(new String(buf, charset));
			    }
			   } catch (IOException e) {
			    e.printStackTrace();
			   }
			   return sb.toString();
		}
		
	}
	/*private class ConvertVideoToFlv extends Thread{
		private String inputFile;
		private SystemProp systemProp;
		private final Logger log2=Logger.getLogger(ConvertVideoToFlv.class);

		public ConvertVideoToFlv(String inputFile,SystemProp systemProp){
			this.inputFile=inputFile;
			this.systemProp=systemProp;
		}
		@Override
		public void run() {
			String outputFile=this.inputFile.substring(inputFile.lastIndexOf(File.separator)+1,inputFile.lastIndexOf("."));
			String path=this.inputFile.substring(0,inputFile.lastIndexOf(File.separator));
			String flvTransferCmd=systemProp.getFlvTransferCmd();
			flvTransferCmd=flvTransferCmd.replace("inputFile", this.inputFile);
			flvTransferCmd=flvTransferCmd.replace("outputFile",path+File.separator+outputFile+".flv");
			try {
				//使用执行远程shell方式执行这段脚本
				Connection conn = new Connection(systemProp.getMagJobIp());
				conn.connect(); // 连接
				if(conn.authenticateWithPassword(systemProp.getMagJobUser(), systemProp.getMagJobPwd())){
					// 认证
					Session session = conn.openSession(); // 打开一个会话
				    session.execCommand(flvTransferCmd);
				    InputStream in = session.getStdout();
				    log.info("reDealCmd:"+flvTransferCmd);
				    String   result = this.processStdout(in, Charset.defaultCharset().toString());
				    log.info("执行远程shell信息："+result);
				    conn.close();
				}
				//使用执行本地shell的方式执行shell				
				Process process = Runtime.getRuntime().exec(flvTransferCmd);
				int exitVal = process.waitFor();
			} catch (Exception e) {
				log2.error("",e);
			}
		}
		private String processStdout(InputStream in, String charset) {
			   byte[] buf = new byte[1024];
			   StringBuffer sb = new StringBuffer();
			   try {
			    while (in.read(buf) != -1) {
			     sb.append(new String(buf, charset));
			    }
			   } catch (IOException e) {
			    e.printStackTrace();
			   }
			   return sb.toString();
		}
		
	}*/
	
	
	private Long userId;
	private String keyword;
	private String title;
	private String description;
	private String linkurl;
	private String imgurl;
	private String mediaurl;
	private Integer adType;
	private String adposIds;
	private String fileName;
	private File jpgFile;
	private Long addAdId;
	private File videoFile;
	private Long issueId;
	private Long pageNo;
	private String leftSwfPath;
	private String rightSwfPath;
	private int fromFlash=0;
	private String serverPath;
	private String status;
	private String endTime;
	private String startTime;
	
	

	
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getServerPath() {
		return serverPath;
	}


	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}


	public int getFromFlash() {
		return fromFlash;
	}


	public void setFromFlash(int fromFlash) {
		this.fromFlash = fromFlash;
	}


	public AdvertiseService getAdvertiseService() {
		return advertiseService;
	}

	public void setAdvertiseService(AdvertiseService advertiseService) {
		this.advertiseService = advertiseService;
	}

	public AdPositionService getAdPositionService() {
		return adPositionService;
	}

	public void setAdPositionService(AdPositionService adPositionService) {
		this.adPositionService = adPositionService;
	}


	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
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

	public String getLinkurl() {
		return linkurl;
	}

	public void setLinkurl(String linkurl) {
		this.linkurl = linkurl;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getMediaurl() {
		return mediaurl;
	}

	public void setMediaurl(String mediaurl) {
		this.mediaurl = mediaurl;
	}

	public Integer getAdType() {
		return adType;
	}

	public void setAdType(Integer adType) {
		this.adType = adType;
	}

	public String getAdposIds() {
		return adposIds;
	}

	public void setAdposIds(String adposIds) {
		this.adposIds = adposIds;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public File getJpgFile() {
		return jpgFile;
	}

	public void setJpgFile(File jpgFile) {
		this.jpgFile = jpgFile;
	}

	public Long getAddAdId() {
		return addAdId;
	}

	public void setAddAdId(Long addAdId) {
		this.addAdId = addAdId;
	}

	public File getVideoFile() {
		return videoFile;
	}

	public void setVideoFile(File videoFile) {
		this.videoFile = videoFile;
	}

	public Long getIssueId() {
		return issueId;
	}

	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}

	public Long getPageNo() {
		return pageNo;
	}

	public void setPageNo(Long pageNo) {
		this.pageNo = pageNo;
	}

	public String getLeftSwfPath() {
		return leftSwfPath;
	}

	public void setLeftSwfPath(String leftSwfPath) {
		this.leftSwfPath = leftSwfPath;
	}

	public String getRightSwfPath() {
		return rightSwfPath;
	}

	public void setRightSwfPath(String rightSwfPath) {
		this.rightSwfPath = rightSwfPath;
	}

	
	
	
	

}
