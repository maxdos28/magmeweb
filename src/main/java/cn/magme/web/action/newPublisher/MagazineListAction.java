package cn.magme.web.action.newPublisher;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cn.magme.common.JsonResult;
import cn.magme.common.SenderMail;
import cn.magme.constants.PojoConstant;
import cn.magme.constants.PojoConstant.COMMON;
import cn.magme.constants.PojoConstant.SUBSCRIBE;
import cn.magme.pojo.Admin;
import cn.magme.pojo.Issue;
import cn.magme.pojo.IssueContents;
import cn.magme.pojo.Publication;
import cn.magme.pojo.Publisher;
import cn.magme.pojo.Subscribe;
import cn.magme.pojo.SysParameter;
import cn.magme.pojo.Tag;
import cn.magme.pojo.TextPage;
import cn.magme.pojo.UserSubscribe;
import cn.magme.pojo.ma.MaAdLocation;
import cn.magme.pojo.netease.NeteasePublicationTask;
import cn.magme.service.IosPushService;
import cn.magme.service.IssueContentsService;
import cn.magme.service.IssueService;
import cn.magme.service.LuceneService;
import cn.magme.service.MailTemplateService;
import cn.magme.service.PublicationService;
import cn.magme.service.SubscribeService;
import cn.magme.service.SysParameterService;
import cn.magme.service.TagService;
import cn.magme.service.TextPageService;
import cn.magme.service.UserSubscribeService;
import cn.magme.service.ma.MaAdLocationService;
import cn.magme.service.netease.NeteaseService;
import cn.magme.util.ConvertPojoToMap;
import cn.magme.util.ExtPageInfo;
import cn.magme.util.FileOperate;
import cn.magme.util.ImageUtil;
import cn.magme.util.Md5Util;
import cn.magme.util.StringUtil;
import cn.magme.util.Struts2Utils;
import cn.magme.web.manager.cache.PublicationCacheService;




/**
 * 杂志管理
 * @author devin.song
 * @date 2012-05-02
 * @version $id$
 */
@SuppressWarnings("serial")
@Results({@Result(name="config",location="/WEB-INF/pages/newPublisher/magazineList.ftl"),
			@Result(name="uploadMg",location="/WEB-INF/pages/newPublisher/manageMgzUpload.ftl"),
			@Result(name="textWrite",location="/WEB-INF/pages/newPublisher/textPageRecord.ftl"),
			@Result(name="supermagazinelist",location="/WEB-INF/pages/newPublisher/supermagazinelist.ftl"),
			@Result(name="issueUploadJson",type="json",params={"root","jsonResult","contentType","text/html"})})
public class MagazineListAction extends PublisherBaseAction {
	private Logger log = Logger.getLogger(this.getClass());
	
	private final Long pageSize = 5L;
	private final Long issuePageSize = 10L;
	@Resource
	private PublicationService publicationService;
	@Resource
	private PublicationCacheService publicationCacheService;
	@Resource 
	private IssueService issueService;
	@Resource
	private TextPageService textPageService;
	@Resource
	private TagService tagService;
	@Resource
	private MailTemplateService mailTemplateService;
	@Resource
	private UserSubscribeService userSubscribeService;
	@Resource
    private SenderMail senderMail;
	@Resource
	private LuceneService luceneService;
	@Resource
	private IssueContentsService issueContentsService;
	@Resource
	private IosPushService iosPushService;
	@Resource
	private NeteaseService neteaseService;
	
	private String publicationName;
	
	private static final String NVYOU_PUBLISHER_ID="NVYOU_PUBLISHER_ID";
	
	@Resource
	private SysParameterService sysParameterService;
	
	@Resource
	private SubscribeService subscribeService;
	@Resource
	private MaAdLocationService maAdLocationService;


	/**
	 * 杂志设置的起始页 、以及单个杂志 的数据初始化
	 * @return
	 */
	public String to(){
		Publisher publisher=this.getSessionPublisher();
		//女友的特殊性配置,一旦女友专们页面下架，需要删除这些代码==================start－2012-07-03
		SysParameter superIdParam=this.sysParameterService.queryByParamKey(NVYOU_PUBLISHER_ID);
		if(superIdParam!=null){
			superId=Long.parseLong(superIdParam.getParamValue());
			if(superId!=null && publisher!=null && superId.longValue()==publisher.getId().longValue()){
				superPublisher(superId);
				return "supermagazinelist";
			}else{
				this.superId=null;
			}
		}
		
		//nvyou==========================================================end－2012-07-03
		if(publication == null)
			publication = new Publication();
		else publicationName = publication.getName(); 
		ExtPageInfo page = new ExtPageInfo();
		page.setStart(0);
		page.setLimit(pageSize);
		if(publisher!=null){
			publication.setPublisherId(publisher.getId());
			page = this.publicationService.getPageByCondition(page, publication);
		} else {
			page = this.publicationService.getPageOfLastPublish(page, publication);
		}
		 if(page!=null){
		 //计算总页数数 ---begin
	        if(pageNo<=0){//重新计算总页数，并进行数据库查询
	        	long listCount = page.getTotal();
	        	pageNo = (listCount+pageSize-1)/pageSize;
	        	currentPage=1;
	        }
	        publicationList = page.getData();
		 }
		 if(publicationList!=null&&publicationList.size()>0){
			 gImgPath(publicationList);
			 
			 //杂志的对应期刊
			 //pan duan
			 ExtPageInfo pageIssue = new ExtPageInfo();
			 pageIssue.setStart(0);
			 pageIssue.setLimit(pageSize);
			 Issue issue= new Issue();
			 //publication = publicationList.get(0);
			 //issue.setPublicationId(publication.getId());//需求变更  初始化时不需要知道对应的杂志id
			 if(this.getSessionPublisherId()!=null){
				 issue.setPublisherId(this.getSessionPublisherId());
			 }
			 pageIssue = issueService.searchByCondition(pageIssue, issue);
			 if(pageIssue!=null){
				 issueList=pageIssue.getData();
				 if(issueList!=null){
				 reComm(issueList);
				 if(issuePageNo<=0){
					 long listCount = pageIssue.getTotal();
					 issuePageNo = (listCount+issuePageSize-1)/issuePageSize;
		        	 issueCurrentPage=1;
	        	 }
				 }
			 }
		 }
		return "config";
	}
	
	private void superPublisher(Long superId){
		Publication	publicationcondition = new Publication();
		ExtPageInfo page = new ExtPageInfo();
		if(currentPage<=0){
			currentPage=1;
		 }
		if(issueCurrentPage<=0){
			 issueCurrentPage=1;
		 }
		page.setStart((this.currentPage-1)*pageSize);
		page.setLimit(pageSize);
		
		page = this.publicationService.getPageBySuperIdAndCondition(page, superId,publicationcondition);
		
		if(page!=null){
		 //计算总页数数 ---begin
	        if(pageNo<=0){//重新计算总页数，并进行数据库查询
	        	long listCount = page.getTotal();
	        	pageNo = (listCount+pageSize-1)/pageSize;
	        	currentPage=1;
	        }
	        publicationList = page.getData();
		 }
		
		 if(publicationList!=null&&publicationList.size()>0){
			 gImgPath(publicationList);
			 publication = publicationList.get(0);
			 
			 //第一本杂志的对应期刊
			 //pan duan
			 ExtPageInfo pageIssue = new ExtPageInfo();
			 pageIssue.setStart(0);
			 pageIssue.setLimit(pageSize);
			 Issue issue= new Issue();
			 issue.setPublicationId(publication.getId());
			 pageIssue = issueService.searchByCondition(pageIssue, issue);
			 if(pageIssue!=null){
				 issueList=pageIssue.getData();
				 if(issueList!=null){
				 reComm(issueList);
				 if(issuePageNo<=0){
					 long listCount = pageIssue.getTotal();
					 issuePageNo = (listCount+issuePageSize-1)/issuePageSize;
		        	 issueCurrentPage=1;
	        	 }
				 }
			 }
		 }
		
	}
	
	/**
	 * 上传期刊
	 * @return
	 */
	public String uploadMg(){
		HttpServletRequest request = Struts2Utils.getRequest();
		String id = (String) request.getParameter("id");
		long publicationId = 0;
		try{
			publicationId = Long.parseLong(id);
		}catch (Exception e) {
		}
		publication = this.publicationService.queryById(publicationId);
		return "uploadMg";
	}
	
	/**
	 * 杂志下架
	 * @return
	 */
	public String doDownPublication(){
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		try {
			Publisher publisher=this.getSessionPublisher();
			if(this.publication!=null){
				Publication publicaion=this.publicationService.queryById(this.publication.getId());
				if(publicaion.getPublisherId().longValue()!=publisher.getId()){
					//只能下架自己的杂志
					this.jsonResult.setCode(JsonResult.CODE.FAILURE);
					this.jsonResult.setMessage("不能下架其他人的杂志");
					return JSON;
				}
				this.jsonResult=publicationCacheService.delByPublisherDown_Shelf(this.publication.getId(), publisher.getId());
			}
		} catch (Exception e) {
			log.error("delPub", e);
		}
		return JSON;
	}
	
	/**
	 * 杂志上架
	 * @return
	 */
	public String doUpPublication(){
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		try {
			Publisher publisher=this.getSessionPublisher();
			if(this.publication!=null){
				Publication publicaion=this.publicationService.queryById(this.publication.getId());
				if(publicaion.getPublisherId().longValue()!=publisher.getId()){
					//只能上架自己的杂志
					this.jsonResult.setCode(JsonResult.CODE.FAILURE);
					this.jsonResult.setMessage("不能上架其他人的杂志");
					return JSON;
				}
				this.jsonResult=publicationCacheService.restoreByPublisher(this.publication.getId(), publisher.getId());
			}
		} catch (Exception e) {
			log.error("delPub", e);
		}
		return JSON;
	}
	/**
	 * ios新刊通知
	 * @return
	 */
	public String doIosNewPublication(){
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		if(this.publication!=null){
			boolean checkflag = false;
			List<Issue> issueList = issueService.getByPublicationId(this.publication.getId());
			if(issueList!=null&&issueList.size()>0){
				Issue issue2 = issueList.get(0);
				int tempStatus = issue2.getStatus();
				if(tempStatus==1){//有已经上架的期刊
					checkflag=true;
				}
			}
			if(checkflag){//必须有已经上架的期刊
				Publication publicaionData=this.publicationService.queryById(this.publication.getId());
				//新刊上架 推送对象的消息
				String iosPushMessage = publicaionData.getName()+"最新一期已经上线，立刻抢鲜阅读。";
				Long[] pubId = new Long[1];
				pubId[0] = this.publication.getId();
				iosPushService.iosPushMessage(1,this.getSessionPublisherId(), iosPushMessage,null,1,pubId);
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			}else{
				this.jsonResult.setCode(JsonResult.CODE.FAILURE);
				this.jsonResult.setMessage("没有上架的期刊");
			}
		}
		return JSON;
	}
	
	/**
	 * 新刊通知
	 * @return
	 */
	public String doNewPublication(){
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		if(this.publication!=null){
			boolean checkflag = false;
			List<Issue> issueList = issueService.getByPublicationId(this.publication.getId());
			if(issueList!=null&&issueList.size()>0){
				Issue issue2 = issueList.get(0);
				int tempStatus = issue2.getStatus();
				if(tempStatus==1){//有已经上架的期刊
					checkflag=true;
				}
			}
			if(checkflag){//必须有已经上架的期刊
				Publication publicaionData=this.publicationService.queryById(this.publication.getId());
				List<UserSubscribe> userList = userSubscribeService.getUserListByPublicationId(publicaionData.getId());
				String text = mailTemplateService.getTemplateStr(PojoConstant.EMAILTEMPLATE.CONTENT.FILE_NEWPUBLICATION.getFileName());
				text=text.replace("{publicationName}", publicaionData.getName());
				//无需登录的订阅信息（c类页中用到  mag.ftl）
				Subscribe subscribe = new Subscribe(this.publication.getId(), SUBSCRIBE.TYPE_PUBLICATION, COMMON.STATUS_OK);
				Map<String, Object> param = ConvertPojoToMap.convert(subscribe);
				List<Subscribe> subList = subscribeService.getByCondition(param);
				for (Subscribe sub : subList) {
					senderMail.sendMail(sub.getEmail(), text, PojoConstant.EMAILTEMPLATE.CONTENT.FILE_NEWPUBLICATION.getSubject(), 0);
				}
				//登录用户的订阅信息
				for (UserSubscribe userSubscribe : userList) {
			        senderMail.sendMail(userSubscribe.getUser().getEmail(), text, PojoConstant.EMAILTEMPLATE.CONTENT.FILE_NEWPUBLICATION.getSubject(), 0);
				}
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			}else{
				this.jsonResult.setCode(JsonResult.CODE.FAILURE);
				this.jsonResult.setMessage("没有上架的期刊");
			}
		}
		return JSON;
	}
	
	/**
	 * 杂志删除-->后台下架
	 * @return
	 */
	public String doDelPublication(){
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		try {
			Publisher publisher=this.getSessionPublisher();
			if(this.publication!=null){
				Publication publicaion=this.publicationService.queryById(this.publication.getId());
				if(publicaion.getPublisherId().longValue()!=publisher.getId()){
					//只能删除自己的杂志
					this.jsonResult.setCode(JsonResult.CODE.FAILURE);
					this.jsonResult.setMessage("不能删除其他人的杂志");
					return JSON;
				}
				this.jsonResult=publicationCacheService.delByPublisher(this.publication.getId(), publisher.getId());
			}
		} catch (Exception e) {
			log.error("delPub", e);
		}
		return JSON;
	}
	
	/**
	 * 文字录入初始化 textPageRecord 
	 * @return
	 */
	public String textWrite(){
		String id= Struts2Utils.getParameter("id");
		try{
			this.issueId = Long.parseLong(id);
		}catch (Exception e) {
			this.issueId = 0L;
		}
		this.issue=issueService.queryById(this.issueId);
		if(this.pageNo<=0){
			this.pageNo=1;
		}
		int pInt = 1;
		try{
			pInt = Integer.parseInt(this.pageNo+"");
			IssueContents ic= new IssueContents();
			ic.setIssueid(this.issueId);
			ic.setPageno(pInt);
			ic.setStatus(PojoConstant.ISSUECONTENTS.STATUS.SHOW.getCode());
			this.issueContents = issueContentsService.queryIssueContents(ic);
		}
		catch (Exception e) {
		}
		this.textPage=textPageService.getByIssueIdAndPageNo(this.issueId, pInt);
		return "textWrite";
	}
	
	/**
	 * 文字录入
	 * @return
	 */
	public String textWriteJson(){
		this.jsonResult = new JsonResult();
		try{
			this.issue=issueService.queryById(this.issueId);
			if(this.pageNo<=0){
				this.pageNo=1;
			}
			int pInt = 1;
			try{
				pInt = Integer.parseInt(this.pageNo+"");
				IssueContents ic= new IssueContents();
				ic.setIssueid(this.issueId);
				ic.setPageno(pInt);
				ic.setStatus(PojoConstant.ISSUECONTENTS.STATUS.SHOW.getCode());
				this.issueContents = issueContentsService.queryIssueContents(ic);
			}
			catch (Exception e) {
			}
			this.textPage=textPageService.getByIssueIdAndPageNo(this.issueId, pInt);
			
			if(this.issueContents!=null){
				this.jsonResult.put("issueContents", this.issueContents);
			}
			this.jsonResult.put("issue", this.issue);
			this.jsonResult.put("textPage", this.textPage);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}catch (Exception e) {
			this.jsonResult.setCode(JsonResult.CODE.FAILURE);
			this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		}
		return JSON;
	}
	
	/**
	 * 添加标签
	 * @return
	 */
	public String addTagJson(){
		if(tag!=null){
			Publisher publisher = this.getSessionPublisher();
			Admin admin = this.getSessionAdmin();
			if(publisher!=null){
				tag.setCreatedBy(publisher.getId());
				this.jsonResult =tagService.insert2(tag);
			}else if(admin!=null){
				tag.setCreatedBy(admin.getId());
				this.jsonResult =tagService.insert2(tag);
			}
			else{
				this.jsonResult = new JsonResult();
				this.jsonResult.setCode(JsonResult.CODE.FAILURE);
				this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
			}
		}else{
			this.jsonResult = new JsonResult();
			this.jsonResult.setCode(JsonResult.CODE.FAILURE);
			this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		}
		return JSON;
	}
	
	 /**
	  * 标签列表
	 * @return
	 */
	public String listTagJson(){
		 this.jsonResult= new JsonResult();
		 if(tag!=null){
			 tag.setType(PojoConstant.TAG.TYPE_ISSUE);
			 tag.setStatus(PojoConstant.TAG.STATUS_OK);
			 this.jsonResult.put("tagList", tagService.getByTag(tag));
			 this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			 this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		 }else{
			this.jsonResult.setCode(JsonResult.CODE.FAILURE);
			this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		}
		 return JSON;
	 }
	
	
	/**
	 * 保存文字录入
	 * @return
	 */
	public String saveJson(){
		this.jsonResult = new JsonResult();
		boolean contentsFlag = false;
		String content=this.textPage.getContent();
		content=content.replaceAll("<", "&lt;");
		content=content.replaceAll(">", "&gt;");
		this.textPage.setContent(content);
		if(this.textPage!=null){
			if(this.getSessionPublisher()!=null){
				textPage.setUserId(this.getSessionPublisherId());
				textPage.setUserType(PojoConstant.TEXTPAGE.USERTYPE_PUBLISHER);
				this.jsonResult=textPageService.save(textPage);
				contentsFlag =true;
			}else if(this.getSessionAdmin()!=null) {
				Admin admin = this.getSessionAdmin();
				textPage.setUserId(admin.getId());
				textPage.setUserType(PojoConstant.TEXTPAGE.USERTYpE_ADMIN);
				this.jsonResult=textPageService.saveAdmin(textPage);
				contentsFlag =true;
			}else{
				this.jsonResult.setCode(JsonResult.CODE.FAILURE);
				this.jsonResult.setMessage("请重新登录");
			}
			if(contentsFlag){
				//添加到目录表 begin
				if(textPage!=null){
					IssueContents ics = new IssueContents();
					ics.setIssueid(textPage.getIssueId());
					ics.setPageno(textPage.getPageNo());
					ics.setTitle(textPage.getTitle());
					ics.setDescription(textPage.getContent());
					//互动杂志的目录和精品页
					if(issueType!=null&&issueType==2)
					{
						ics.setIsBoutique(boutique);
						ics.setIsCatalog(catalog);
						issueContentsService.saveIssueContents(ics);
					}
					else
					{
						ics.setIsBoutique(null);
						ics.setIsCatalog(null);
						issueContentsService.magazineListAction(ics, textPage.getContentsFlag());
					}
					
				}
				//添加目录表 end
			}
		}else{
			this.jsonResult.setCode(JsonResult.CODE.FAILURE);
			this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		}
		return JSON;
	}
	
	/**
	 * 杂志翻页联动获取第一本杂志的期刊集合
	 * @return
	 */
	public String doJson(){
		this.jsonResult = new JsonResult();
		//杂志 begin
		Publisher publisher=this.getSessionPublisher();
		if(publication==null)
			publication = new Publication();
		if(publisher!=null){
			publication.setPublisherId(publisher.getId());
		}
		ExtPageInfo page = new ExtPageInfo();
		if(currentPage<=0){
			currentPage=1;
		}
		page.setLimit(pageSize);
		page.setStart((currentPage-1)*pageSize);
		//女友的特殊性配置,一旦女友专们页面下架，需要删除这些代码==================start－2012-07-03
		SysParameter superIdParam=this.sysParameterService.queryByParamKey(NVYOU_PUBLISHER_ID);
		if(superIdParam!=null){
			superId=Long.parseLong(superIdParam.getParamValue());
			if(superId!=null && publisher!=null && superId.longValue()==publisher.getId().longValue()){
				this.superPublisher(superId);
				jsonResult.put("publication",publication);
				jsonResult.put("issueList",issueList);
				jsonResult.put("issuePageNo",issuePageNo);
				jsonResult.put("publicationList",publicationList);
				jsonResult.put("pageNo", pageNo);
				jsonResult.setCode(JsonResult.CODE.SUCCESS);
				jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
				return JSON;
			}else{
				this.superId=null;
			}
		}
		
		//nvyou==========================================================end－2012-07-03
		
		if(publisher!=null){
			page = this.publicationService.getPageByCondition(page, publication);
		} else {
			page = this.publicationService.getPageOfLastPublish(page, publication);
		}
		if(page!=null){
			long listCount = page.getTotal();
			pageNo= (listCount+pageSize-1)/pageSize;
	    	publicationList = page.getData();
		}
		jsonResult.put("publicationList",publicationList);
		jsonResult.put("pageNo", pageNo);
		//杂志end
		//对应的期刊 begin
		if(publicationList!=null&&publicationList.size()>0){
			 gImgPath(publicationList);
			 //杂志的对应期刊
			 //pan duan
			 ExtPageInfo pageIssue = new ExtPageInfo();
			 pageIssue.setStart(0);
			 pageIssue.setLimit(pageSize);
			 Issue issue= new Issue();
			 
			 //publication = publicationList.get(0);
			 //issue.setPublicationId(publication.getId());//需求变更  初始化时不需要知道对应的杂志id
			 if(this.getSessionPublisherId()!=null){
				 issue.setPublisherId(this.getSessionPublisherId());
			 }
			 pageIssue = issueService.searchByCondition(pageIssue, issue);
			 if(pageIssue!=null){
				 issueList=pageIssue.getData();
				 reComm(issueList);
				 if(issuePageNo<=0){
					 long listCount = pageIssue.getTotal();
					 issuePageNo = (listCount+issuePageSize-1)/issuePageSize;
	        	 }
			 }
			jsonResult.put("publication",publication);
			jsonResult.put("issueList",issueList);
			jsonResult.put("issuePageNo",issuePageNo);
		 }
		//对应的期刊 end
		jsonResult.setCode(JsonResult.CODE.SUCCESS);
		jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		
		return JSON;
	}
	
	/**
	 * 根据杂志id获取最新期刊的对象 并且是已经转换好的期刊
	 * @return
	 */
	public String getImgPublication(){
		this.jsonResult = new JsonResult();
		jsonResult.setCode(JsonResult.CODE.FAILURE);
		jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		//杂志 begin
		if(publication!=null){
			List<Issue> issueList = issueService.getByPublicationId(publication.getId());
			for (Issue issue : issueList) {
				int tempStatus = issue.getStatus();
				if(tempStatus==0 || tempStatus==1 || tempStatus==2 || tempStatus==3){//已经转好的期刊
					this.jsonResult.put("issue", issue);
					this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
					this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
					break;
				}
			}
		}
		return JSON;
	}
	
	
	/**
	 * 选择具体杂志时对应的期刊
	 * @return
	 */
	public String listIssue(){
		this.jsonResult = new JsonResult();
		try{
			//女友的特殊性配置,一旦女友专用页面下架，需要删除这些代码==================start－2012-07-05
			SysParameter superIdParam=this.sysParameterService.queryByParamKey(NVYOU_PUBLISHER_ID);
			if(superIdParam!=null){
				superId=Long.parseLong(superIdParam.getParamValue());
				if(superId!=null && this.getSessionAdUserId()!=null && superId.longValue()==this.getSessionAdUserId()){
					//TODO 不做任何事情
				}else{
					this.superId=null;
				}
			}
			//end 2012-07-05
			
			Long publicationId = publication.getId();//页面传入的对象参数
			//首页中的所有期刊
//			if(publicationId==null || publicationId==0){
//				this.jsonResult.setCode(300);
//				this.jsonResult.setMessage("error");
//			}else{
				//选中了杂志
				//issueList=issueService.queryAllIssuesByPublisherIdAndPublisherId(publicationId, publisher.getId());
				 ExtPageInfo pageIssue = new ExtPageInfo();
				 if(issueCurrentPage<=0){
					 issueCurrentPage=1;
				 }
				 pageIssue.setStart((issueCurrentPage-1)*issuePageSize);
				 pageIssue.setLimit(issuePageSize);
				 Issue issue= new Issue();
				 if(publicationId!=null){
					 issue.setPublicationId(publicationId);
				 }
				 if(this.getSessionPublisherId()!=null){
					 if(superId!=null){
						 Publication nvyouPublication=publicationService.queryById(publicationId);
						 issue.setPublisherId(nvyouPublication.getPublisherId());
					 }else{
						 issue.setPublisherId(this.getSessionPublisherId());	 
					 }
					 
				 }
				 pageIssue = issueService.searchByCondition(pageIssue, issue);
				 if(pageIssue!=null){
					 issueList=pageIssue.getData();
					 if(publicationId==null){
						 pubByIssue(issueList);
					 }
					 reComm(issueList);
					 if(issuePageNo<=0){
						 long listCount = pageIssue.getTotal();
						 issuePageNo = (listCount+issuePageSize-1)/issuePageSize;
		        	 }
				 }
				 this.jsonResult.put("superId", superId);
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
				this.jsonResult.put("issueList", issueList);
				this.jsonResult.put("issuePageNo", issuePageNo);
				if(publicationId!=null){
					this.jsonResult.put("publication", this.publicationService.queryById(publicationId));
				}
//			}
			
		}catch (Exception e) {
			this.jsonResult.setCode(300);
			this.jsonResult.setMessage("error");
			log.error("", e);
		}
		return JSON;
	}
	
	/**
	 * 保存期刊
	 * @return
	 */
	public String doSaveIssue(){
		if(issue!=null){
			this.jsonResult = new JsonResult();
			this.jsonResult.setCode(JsonResult.CODE.FAILURE);
			this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
			if ((this.smallPic1 == null&&this.smallPic2 != null)||(this.smallPic1 != null&&this.smallPic2 == null))
			{
				jsonResult.setMessage("两张图片要同时上传");
	            return "issueUploadJson";
			}
			try{
				//女友的特殊性配置,一旦女友专用页面下架，需要删除这些代码==================start－2012-07-05
				SysParameter superIdParam=this.sysParameterService.queryByParamKey(NVYOU_PUBLISHER_ID);
				if(superIdParam!=null){
					superId=Long.parseLong(superIdParam.getParamValue());
					if(superId!=null && this.getSessionAdUserId()!=null && superId.longValue()==this.getSessionAdUserId()){
						this.jsonResult.put("superId", this.superId);
					}else{
						this.superId=null;
					}
				}
				//end 2012-07-05
				
				if(issue!=null){
					Issue issuedb=this.issueService.queryById(issue.getId());
					boolean b = uploadIssueZip(issue, issue.getId(),issuedb.getPublicationId());
					if(!b)
						return "issueUploadJson";
					//由于issuetype在pojo中有默认值1，因此需要修改这个值----add by fredy in 2013-04-25
					issue.setIssueType(issuedb.getIssueType());
					Publication pub=this.publicationService.queryById(issuedb.getPublicationId());
					if(StringUtil.isNotBlank(issue.getPassWord())){
						issue.setPassWord(Md5Util.MD5Encode(issue.getPassWord()));
					}
					
					if(issuedb.getStatus()==PojoConstant.ISSUE.STATUS.ON_SALE.getCode())//需要上架的期刊
					{
						if(pub==null || pub.getStatus()!=PojoConstant.PUBLICATION.STATUS.ON_SHELF.getCode()){
							this.jsonResult.setCode(JsonResult.CODE.FAILURE);
							this.jsonResult.setMessage("期刊对应的杂志不存在，或者已经下架");
							return "issueUploadJson";
						}
					}
						if(issue.getStatus()!=null&&issue.getStatus()==1){//更新上架
							Calendar calendar = Calendar.getInstance();
							//issue.setPublishDate(calendar.getTime());//需要手动控制
							if(issueService.updateById2(issue)>0){
//								//新刊上架 推送对象的消息
//								String iosPushMessage = issuedb.getPublicationName()+"最新一期已经上线，立刻抢鲜阅读。";
//								iosPushService.iosPushMessage(this.getSessionPublisherId(), iosPushMessage,null);
								issue.setPublicationName(issuedb.getPublicationName());
								if(issue.getIssueType()!=PojoConstant.ISSUE.ISSUETYPE.INTERACTIVEISSUE.getCode()){
									luceneService.addIndex(issue);
								}
								this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
								this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
								}
						}else if(issue.getStatus()!=null&&issue.getStatus()==2){//下架操作
							if(issueService.updateById2(issue)>0){
								if(issue.getIssueType()!=PojoConstant.ISSUE.ISSUETYPE.INTERACTIVEISSUE.getCode()){
									luceneService.deleteIndex(issuedb);
								}
								this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
								this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
								}
						}else{
							if(issueService.updateById2(issue)>0){
								issue.setPublicationName(issuedb.getPublicationName());
								if(issue.getIssueType()!=PojoConstant.ISSUE.ISSUETYPE.INTERACTIVEISSUE.getCode()){
									luceneService.updateIndex(issue);
								}
								this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
								this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
							}
					}
				}
			}catch (Exception e) {
			}
		}
		return "issueUploadJson";
	}
	
	private boolean uploadIssueZip(Issue issue, Long issueId,Long publicationId) {
		//更新期刊包
		if(issueFile!=null)
		{
			String issuePath=this.systemProp.getMagicEditorPath()+File.separator+publicationId+File.separator+issueId+File.separator;
	        File srcIssueFile=new File(issuePath);
	        if(!srcIssueFile.exists()){
	        	srcIssueFile.mkdirs();
	        }
	        File destFile=new File(issuePath+issueId+"_"+System.nanoTime()+issueFileFileName.toLowerCase().substring(issueFileFileName.toLowerCase().lastIndexOf("."), issueFileFileName.toLowerCase().length()));
	        FileOperate.copyFile(issueFile.getAbsolutePath(),destFile.getAbsolutePath());
	        
	        try {
	        	//解压根zip包
				FileOperate.unzipFile(issueFile.getAbsolutePath(), issuePath,false);
				//解压解压后的资源文件
				File [] fList=srcIssueFile.listFiles();
				int pageCount = 0;//重新计算总页数
			    for(File ff:fList){
					if(ff.isDirectory()||!ff.getName().toLowerCase().endsWith(".zip"))
						continue;
				   if(ff.getName().indexOf("assets")>0){
					   FileOperate.unzipFile(ff.getAbsolutePath(),issuePath,true);
					   pageCount++;
				   }
					if (ff.getName().toLowerCase().indexOf("phone") > 0) {
						FileOperate.unzipFile(ff.getAbsolutePath(), issuePath, true);
					}
					if (ff.getName().toLowerCase().indexOf("pad") > 0) {
						FileOperate.unzipFile(ff.getAbsolutePath(), issuePath, true);
					}
			    }
			    //重命名解压后的资源文件
			    File [] assetsFolderList=srcIssueFile.listFiles();
			    int assetsIndex=0;
			    for(File ff:assetsFolderList){
			    	assetsIndex=ff.getName().indexOf("assets");
			    	if(ff.isDirectory() && assetsIndex>0){
			    		FileOperate.moveFolder(ff.getAbsolutePath(), issuePath+ff.getName().substring(0,assetsIndex));
			    	}
			    }
			    //读取配置文件
			    File configFile=new File(issuePath+"config.xml");
				if(configFile.exists()){
					Map<String,Object> config=this.parseConfig(configFile,issueId,publicationId);
				}
				
			    //设置总页数
			    issue.setTotalPages(pageCount);
			    //上传的压缩包保留
				//destFile.delete();
			} catch (IOException e) {
				log.error("", e);
				this.jsonResult.setMessage("unzip file error");
				return false;
			}
	        ChangeJpgThread t=new ChangeJpgThread(issuePath,issueId);
			t.start();
		}
		//处理图片
        String staticPath = systemProp.getStaticLocalUrl();
		String relativePath = File.separator +"appprofile"+File.separator+publicationId+File.separator+issueId+File.separator;
		if (this.smallPic1 != null&&this.smallPic2 != null) {
			// 图片尺寸检查
			// FileInputStream fis = new FileInputStream(smallPic);
			try {
				BufferedImage buff = ImageIO.read(smallPic1);
				if (buff.getHeight() < 472 || buff.getWidth() < 472) {
					log.warn("图片尺寸为472*472");
					this.jsonResult.setMessage("图片尺寸为472*472");
					return false;
				}
				BufferedImage buff2 = ImageIO.read(smallPic2);
				if (buff2.getHeight() < 360 || buff2.getWidth() < 360) {
					log.warn("图片尺寸为360*360");
					this.jsonResult.setMessage("图片尺寸为360*360");
					return false;
				}

				// 图片尺寸转换 pad 470*470 phone 360*360
				// 文章图片ID，文章ID+－+时间
				String fileName = relativePath + "pub_" + issueId + "-"
						+ System.nanoTime();

				FileOperate.copyFile(smallPic1.getAbsolutePath(), staticPath+fileName+".jpg");
				if(buff.getHeight() == 472 && buff.getWidth() == 472)
				{
					FileOperate.copyFile(smallPic1.getAbsolutePath(), staticPath+fileName+ "_pad.jpg");
				}
				else
				{
					ImageUtil.smallerByWidthAndHeight(smallPic1.getAbsolutePath(),
							staticPath + fileName + "_pad.jpg", 472, 472);
				}
				if(buff2.getHeight() == 360 && buff2.getWidth() == 360)
				{
					FileOperate.copyFile(smallPic2.getAbsolutePath(), staticPath+fileName+ "_phone.jpg");
				}
				else
				{
					ImageUtil.smallerByWidthAndHeight(smallPic2.getAbsolutePath(),
							staticPath + fileName + "_phone.jpg", 360, 360);
				}
				issue.setSmallPic(fileName.replaceAll("\\\\", "/") + ".jpg");
			} catch (IOException e) {
				log.error("", e);
				this.jsonResult.setMessage("unzip file error");
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 解析xml配置文件
	 * @return
	 */
	private Map<String,Object> parseConfig(File configFile,Long issueId,Long publicationId){
		Map<String,Object> map =new HashMap<String,Object>();
		List<IssueContents> issueContentList=new ArrayList<IssueContents>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = dbf.newDocumentBuilder();
            InputStream in = new FileInputStream(configFile);
            Document doc = builder.parse(in);
            
            Element root = doc.getDocumentElement();
            if (root == null) return null;
            
            NodeList collegeNodes = root.getChildNodes();
            if (collegeNodes == null) return null;
            
            Node pageCountNode=root.getElementsByTagName("pageCount").item(0);
            System.out.println(pageCountNode.getFirstChild().getNodeName());
            map.put("pageCount", Integer.parseInt(pageCountNode.getFirstChild().getNodeValue()));
            
            //方向，横竖屏
            Node orientationNode=root.getElementsByTagName("orientation").item(0);
            map.put("orientation", Integer.parseInt(orientationNode.getFirstChild().getNodeValue()));
            
            //页码,标题，描述
            NodeList pageNodeList=root.getElementsByTagName("page");
            for(int i = 0; i < pageNodeList.getLength(); i++) {
                Node page = pageNodeList.item(i);
                NodeList pageDetailNodeList=page.getChildNodes();
                IssueContents issueContent=new IssueContents();
                for(int j=0; j<pageDetailNodeList.getLength();j++){
                	Node pageDetail = pageDetailNodeList.item(j);
                	if(pageDetail.getNodeName().equalsIgnoreCase("pageNo")){
                		issueContent.setPageno(Integer.parseInt(pageDetail.getFirstChild().getNodeValue()));
                	}
					if(pageDetail.getNodeName().equalsIgnoreCase("title")){
						issueContent.setTitle(pageDetail.getFirstChild().getNodeValue());            		
					}
					if(pageDetail.getNodeName().equalsIgnoreCase("description")){
						issueContent.setDescription(pageDetail.getFirstChild().getNodeValue());
					}
					//判断是否为目录
					if(pageDetail.getNodeName().equalsIgnoreCase("belongToCatalog")){
						if(pageDetail.getFirstChild().getNodeValue()!=null&&pageDetail.getFirstChild().getNodeValue().equals("1"))
							issueContent.setIsCatalog(1);
						else
							issueContent.setIsCatalog(0);
					}
					//默认无精品页
					issueContent.setIsBoutique(0);
                }
                issueContent.setIssueid(issueId);
                issueContent.setStatus(PojoConstant.ISSUECONTENTS.STATUS.SHOW.getCode());
                issueContentList.add(issueContent);
            }
            //广告信息
            NodeList adNodeList=root.getElementsByTagName("ad");
            if(adNodeList!=null&&adNodeList.getLength()>0)
            {
            	Date date = new Date();
            	for(int i = 0; i < adNodeList.getLength(); i++) {
                	MaAdLocation mal = new MaAdLocation();
                	mal.setType(new Byte("2"));
                	mal.setPublicationId(publicationId);
                	mal.setIssueId(issueId);
                	String keywords = null;
                    Node ad = adNodeList.item(i);
                    NodeList adDetailNodeList=ad.getChildNodes();
                    for(int j=0; j<adDetailNodeList.getLength();j++){
                    	Node adDetail = adDetailNodeList.item(j);
                    	
                    	//广告ID
                    	if(adDetail.getNodeName().equalsIgnoreCase("id")){
                    		mal.setAdId(adDetail.getFirstChild().getNodeValue());                    		
                    	}
                    	//页码
    					if(adDetail.getNodeName().equalsIgnoreCase("pageNo")){      
    						mal.setPageNo(new Integer(adDetail.getFirstChild().getNodeValue()));  
    					}
                    	//广告尺码
    					if(adDetail.getNodeName().equalsIgnoreCase("size")){
    						mal.setAdSize(adDetail.getFirstChild().getNodeValue());  
    					}
    					//广告关键字
    					if(adDetail.getNodeName().equalsIgnoreCase("keyword")){
    						keywords = adDetail.getFirstChild().getNodeValue();  
    					}
    					//设备区分
    					if(adDetail.getNodeName().equalsIgnoreCase("device")){
    						if(adDetail.getFirstChild().getNodeValue().equals("pad"))
    							mal.setDevice(new Byte("1"));  
    						else
    							mal.setDevice(new Byte("2")); 
    					}
                    }
                    this.maAdLocationService.saveAdLocation(mal, keywords);
                }
            }
        } catch (Exception e) {
          log.error("parse config file error", e);
        }
        
        map.put("issueContentsList", issueContentList);
        return map;
		
	}
	
	/**
	 * 设置期刊为免费
	 * @return
	 */
	public String doIssueFree(){
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		if(issueId!=null && issueId>0){
			Issue issue = new Issue();
			issue.setId(issueId);
			issue.setIsFree(1);//设置为免费
			if(issueService.updateById2(issue)>0){
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			}
		}
		return JSON;
	}
	
	/**
	 * 获取期刊对象
	 * @return
	 */
	public String doGetIssue(){
		this.jsonResult = new JsonResult();
		if(issue!=null){
			issue  = issueService.queryById(issue.getId());
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			this.jsonResult.put("issue", issue);
		}else{
			this.jsonResult.setCode(JsonResult.CODE.FAILURE);
			this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		}
		return JSON;
	}
	
	/**
	 * 向网易新增期刊
	 * @return
	 */
	public String toNeteaseAddJson()
	{
		this.jsonResult = JsonResult.getFailure();
		if(issueId==null||issueId<=0)
		{
			this.jsonResult.setMessage("期刊ID为空");
			return JSON;
		}
		Issue issuedb=this.issueService.queryById(issueId);
		if(issuedb==null)
		{
			this.jsonResult.setMessage("期刊未找到");
			return JSON;
		}
		boolean b = this.neteaseService.checkNeteasePublication(issueId);
		//更新期刊
		if(b)
		{
			//返回期刊的页数
			this.jsonResult.put("totalPage", issuedb.getTotalPages());
		}
		//新增期刊
		else
		{
			//得到杂志描述
			Publication pub = this.publicationService.queryById(issuedb.getPublicationId());
			if(pub==null)
			{
				this.jsonResult.setMessage("杂志未找到");
				return JSON;
			}
			//插入到任务列表
			NeteasePublicationTask t = new NeteasePublicationTask();
			t.setPublicationId(issuedb.getPublicationId());
			String publicationName = issuedb.getPublicationName();
			if(publicationName.indexOf("互动")>0)
				publicationName = publicationName.substring(0,publicationName.lastIndexOf("互动"));
			t.setPublicationName(publicationName);
			t.setIssueId(issueId);
			t.setIssueName(issuedb.getKeyword());
			t.setDescription(pub.getDescription());
			t.setUpdateType(new Byte("1"));
			t.setStatus(new Byte(""+PojoConstant.NETEASE.TASK_STATUS_2));
			t.setPageCount(issuedb.getTotalPages());
			Date date = new Date();
			t.setCreateTime(date);
			t.setUpdateTime(date);
			Long id = this.neteaseService.createNeteasePublicationTask(t);
			if(id==null||id<=0)
			{
				this.jsonResult.setMessage("操作失败");
				return JSON;
			}
		}
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}
	/**
	 * 向网易更新期刊
	 * @return
	 */
	public String toNeteaseUpdateJson()
	{
		this.jsonResult = JsonResult.getFailure();
		if(issueId==null||issueId<=0)
		{
			this.jsonResult.setMessage("期刊ID为空");
			return JSON;
		}
		if(StringUtil.isBlank(pages))
		{
			this.jsonResult.setMessage("请选择要更新的页码");
			return JSON;
		}
		Issue issuedb=this.issueService.queryById(issueId);
		if(issuedb==null)
		{
			this.jsonResult.setMessage("期刊未找到");
			return JSON;
		}
		//得到杂志描述
		Publication pub = this.publicationService.queryById(issuedb.getPublicationId());
		if(pub==null)
		{
			this.jsonResult.setMessage("杂志未找到");
			return JSON;
		}
		//插入到任务列表
		NeteasePublicationTask t = new NeteasePublicationTask();
		t.setPublicationId(issuedb.getPublicationId());
		String publicationName = issuedb.getPublicationName();
		if(publicationName.indexOf("互动")>0)
			publicationName = publicationName.substring(0,publicationName.lastIndexOf("互动"));
		t.setPublicationName(publicationName);
		t.setIssueId(issueId);
		t.setPages(pages);
		t.setPageCount(pages.split(",").length);
		t.setIssueName(issuedb.getKeyword());
		t.setDescription(pub.getDescription());
		t.setUpdateType(new Byte("2"));
		t.setStatus(new Byte(""+PojoConstant.NETEASE.TASK_STATUS_2));
		Date date = new Date();
		t.setCreateTime(date);
		t.setUpdateTime(date);
		Long id = this.neteaseService.createNeteasePublicationTask(t);
		if(id==null||id<=0)
		{
			this.jsonResult.setMessage("操作失败");
			return JSON;
		}
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}
	
	/**
	 * 在Issue对象里存放publication对象
	 * @param issueList
	 */
	protected void pubByIssue(List<Issue> issueList) {
		if(issueList!=null){
			
			for (Issue ise : issueList) {
				try{
				Publication pub = publicationService.queryById(ise.getPublicationId());
				ise.setPublicationPo(pub);
				}catch (Exception e) {
				}
			}
		}
		this.issueList = issueList;
	}
	
	/**
	 * 计算期刊的文字比例
	 * @param issueList
	 */
	protected void reComm(List<Issue> issueList){
		for (Issue ise : issueList) {
			 try{
				long adNum = ise.getTextAdNum();//广告页数
				int issuePageNum = ise.getTotalPages();//期刊总页数
				long contentNum = ise.getTextContentNum();//有效文字的页面数
				float k = Float.parseFloat((issuePageNum-adNum)+"");//期刊总页数减去广告页数
				BigDecimal bd1 = new BigDecimal(contentNum/k);//有效文字的页面数/期刊总页数减去广告页数
				BigDecimal after = bd1.setScale(2, BigDecimal.ROUND_HALF_UP);
				int back = (int)(Float.parseFloat(after+"")*100);
				ise.setTextProportion(back);
			}catch (Exception e) {
				ise.setTextProportion(0);
			}
		}
		this.issueList = issueList;
	}
	
	/**
	 * 填充杂志的封面 图片路径
	 * @param publicationList
	 */
	protected void gImgPath(List<Publication> publicationList){
		String tmpImgPath ="";
		if(publicationList!=null){
			for (Publication p : publicationList) {
				//网易云阅读,表示此杂志在网易云阅读中使用
				Long c = issueService.countPublicationBySuperId(904L, p.getId());
				if(c!=null&&c.intValue()>0)
					p.setAppId(904L);
				
				List<Issue> issueList = issueService.getByPublicationId(p.getId());
					for (Issue issue : issueList) {
						int tempStatus = issue.getStatus();
						if(tempStatus==0 || tempStatus==1 || tempStatus==2 ){//已经转好的期刊
							//互动杂志封面图片
							if(p.getPubType()!=null&&p.getPubType()==3)
							{
								p.setImgPath("/"+issue.getJpgPath()+"/200_"+issue.getId()+".jpg");
							}
							else
							{
								p.setImgPath("/"+p.getId()+"/200_"+issue.getId()+".jpg");
							}							
							break;
						}else{
							p.setImgPath(tmpImgPath);
						}
					}
			}
		}
	}
	
	/**
	 * 修改图片
	 * @author fredy
	 * @since 2013-4-23
	 */
	private class ChangeJpgThread extends Thread{
		private String issuePath;
		private Long issueId;
		
		public ChangeJpgThread(String issuePath,Long issueId){
			this.issuePath=issuePath;
			this.issueId=issueId;
		}

		@Override
		public void run() {
			File issueFile=new File(this.issuePath);
			if(!issueFile.exists() || !issueFile.isDirectory() || issueFile.listFiles().length<=0){
				return;
			}
			File []listFile=issueFile.listFiles();
			for(File f:listFile){
				if(f.isDirectory())
				{
					File[] subFileList = f.listFiles(new FilenameFilter() {
						
						@Override
						public boolean accept(File dir, String name) {
							if(name.toLowerCase().endsWith(".jpg")&&(name.indexOf("pad_")==0 || name.indexOf("phone_")==0))
								return true;
							return false;
						}
					});
					if(subFileList!=null&&subFileList.length>0)
					{
						for(File sf:subFileList)
						{
							//期刊内页缩放成120
							ImageUtil.smallerByWidthWithSameRatio(sf.getAbsolutePath(),
									sf.getParentFile().getAbsolutePath()+File.separator+"120_"+sf.getName(), 200);
							//第一张图片需要转封面
							if(sf.getName().toLowerCase().equalsIgnoreCase("pad_q1"+".jpg")){
								String pubPath=sf.getParentFile().getParentFile().getAbsolutePath()+File.separator;
								ImageUtil.smallerByWidthAndHeight(sf.getAbsolutePath(),pubPath+"100_"+issueId+".jpg", 100, 132);
								ImageUtil.smallerByWidthAndHeight(sf.getAbsolutePath(),pubPath+"110_"+issueId+".jpg", 110, 145);
								ImageUtil.smallerByWidthAndHeight(sf.getAbsolutePath(),pubPath+"172_"+issueId+".jpg", 172, 230);
								ImageUtil.smallerByWidthAndHeight(sf.getAbsolutePath(),pubPath+"248_"+issueId+".jpg", 248, 330);
								ImageUtil.smallerByWidthAndHeight(sf.getAbsolutePath(),pubPath+"200_"+issueId+".jpg", 200, 268);
								//320图片是等比例
								ImageUtil.smallerByWidthWithSameRatio(sf.getAbsolutePath(),pubPath+"320_"+issueId+".jpg", 320);
							}
						}
					}
				}
			}
			
		}
		
		
	}
	
	private Publication publication;
	private List<Publication> publicationList;
	private List<Issue> issueList;
	private long currentPage;
    private long pageNo;
    private long issueCurrentPage;
    private long issuePageNo;
    
    private Issue issue;
    private Long issueId;
	private String content;
	private TextPage textPage;
	private IssueContents issueContents;
	private Tag tag;
	private Integer issueType;
	private Integer catalog;
	private Integer boutique;
	private String pages;//网易多页更新
	
	/**
	 * 超级用户id
	 */
	private Long superId;
	
	/**
	 * 上传的文件
	 */
	private File issueFile;
	/**
	 *  上传文件名.
	 */
	private String issueFileFileName;
	

	private File smallPic1;

	private File smallPic2;
	private String smallPic1FileName;
	private String smallPic2FileName;
	
	
	

	public Long getSuperId() {
		return superId;
	}

	public void setSuperId(Long superId) {
		this.superId = superId;
	}

	public Publication getPublication() {
		return publication;
	}
	public void setPublication(Publication publication) {
		this.publication = publication;
	}
	public List<Publication> getPublicationList() {
		return publicationList;
	}
	public void setPublicationList(List<Publication> publicationList) {
		this.publicationList = publicationList;
	}
	
	public List<Issue> getIssueList() {
		return issueList;
	}

	public void setIssueList(List<Issue> issueList) {
		this.issueList = issueList;
	}

	public long getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}
	public long getPageNo() {
		return pageNo;
	}
	public void setPageNo(long pageNo) {
		this.pageNo = pageNo;
	}

	public long getIssueCurrentPage() {
		return issueCurrentPage;
	}

	public void setIssueCurrentPage(long issueCurrentPage) {
		this.issueCurrentPage = issueCurrentPage;
	}

	public long getIssuePageNo() {
		return issuePageNo;
	}

	public void setIssuePageNo(long issuePageNo) {
		this.issuePageNo = issuePageNo;
	}

	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	public Long getIssueId() {
		return issueId;
	}

	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public TextPage getTextPage() {
		return textPage;
	}

	public void setTextPage(TextPage textPage) {
		this.textPage = textPage;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public void setPublicationName(String publicationName) {
		this.publicationName = publicationName;
	}

	public String getPublicationName() {
		return publicationName;
	}

	public IssueContents getIssueContents() {
		return issueContents;
	}

	public void setIssueContents(IssueContents issueContents) {
		this.issueContents = issueContents;
	}

	public Integer getIssueType() {
		return issueType;
	}

	public void setIssueType(Integer issueType) {
		this.issueType = issueType;
	}

	public Integer getCatalog() {
		return catalog;
	}

	public void setCatalog(Integer catalog) {
		this.catalog = catalog;
	}

	public Integer getBoutique() {
		return boutique;
	}

	public void setBoutique(Integer boutique) {
		this.boutique = boutique;
	}

	public File getIssueFile() {
		return issueFile;
	}

	public void setIssueFile(File issueFile) {
		this.issueFile = issueFile;
	}

	public String getIssueFileFileName() {
		return issueFileFileName;
	}

	public void setIssueFileFileName(String issueFileFileName) {
		this.issueFileFileName = issueFileFileName;
	}

	public File getSmallPic1() {
		return smallPic1;
	}

	public void setSmallPic1(File smallPic1) {
		this.smallPic1 = smallPic1;
	}

	public File getSmallPic2() {
		return smallPic2;
	}

	public void setSmallPic2(File smallPic2) {
		this.smallPic2 = smallPic2;
	}

	public String getSmallPic1FileName() {
		return smallPic1FileName;
	}

	public void setSmallPic1FileName(String smallPic1FileName) {
		this.smallPic1FileName = smallPic1FileName;
	}

	public String getSmallPic2FileName() {
		return smallPic2FileName;
	}

	public void setSmallPic2FileName(String smallPic2FileName) {
		this.smallPic2FileName = smallPic2FileName;
	}

	public String getPages() {
		return pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}
	
}
