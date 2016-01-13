package cn.magme.web.action.newPublisher;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;


import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.Advertise;
import cn.magme.pojo.InteractiveContent;
import cn.magme.pojo.Issue;
import cn.magme.pojo.Publication;
import cn.magme.pojo.Publisher;
import cn.magme.service.AdvertiseService;
import cn.magme.service.FpageEventService;
import cn.magme.service.InteractiveContentService;
import cn.magme.service.IssueService;
import cn.magme.service.PublicationService;
import cn.magme.service.PublisherService;
import cn.magme.util.ExtPageInfo;
import cn.magme.util.ToJson;




/**
 * 广告管理
 * @author devin.song
 * @date 2012-05-02
 * @version $id$
 */
@SuppressWarnings("serial")
@Results({@Result(name="config",location="/WEB-INF/pages/newPublisher/advertisingList.ftl")})
public class AdvertisingListAction extends PublisherBaseAction {
	private Logger log = Logger.getLogger(this.getClass());
	
	private final Long pageSize = 5L;
	private final Long adPageSize =10L;
	
	@Resource
	private PublicationService publicationService;
	@Resource
	private IssueService issueService;
	@Resource
	private AdvertiseService advertiseService;
	
	@Resource
	private InteractiveContentService interactiveContentService;
	
	@Resource
	private FpageEventService fpageEventService;
	

	/**
	 * 杂志设置的起始页 、以及单个杂志 的数据初始化
	 * @return
	 */
	public String to(){
		Publisher publisher=this.getSessionPublisher();
		publication = new Publication();
		if(publisher!=null){
		publication.setPublisherId(publisher.getId());
		}else{
			return "deny";
		}
		ExtPageInfo page = new ExtPageInfo();
		page.setStart(0);
		page.setLimit(pageSize);
		publication.setPubType(PojoConstant.PUBLICATION.PUBTYPE.MAGAZINE.getCode());//普通的杂志
		 page = this.publicationService.getPageByCondition(page, publication);
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
			 //查询第一本杂志对应的广告 的总页数
				//一线出版商  互动广告
			 	if(publisher.getLevel()==1){
			 		publication = publicationList.get(0);
			 		Map<String,Object> map=new HashMap<String,Object>();
			 		map.put("publicationid", publication.getId());
			 		//互动广告 
				 	map.put("adType", new String[]{"1","2","3"});
				 	int listCount = advertiseService.getListPublicatonCount(map);
				 	this.adPageNo =(listCount+adPageSize-1)/adPageSize;
			 	}else{//非一线出版商 查询互动内容
			 		publication = publicationList.get(0);
			 		Map<String,Object> map=new HashMap<String,Object>();
			 		map.put("publicationid", publication.getId());
			 		int listCount = interactiveContentService.queryPageCount(map);
				 	this.adPageNo =(listCount+adPageSize-1)/adPageSize;
			 	}
		 }
		 return "config";
	}
	
	
	/**
	 * 杂志翻页联动获取第一本杂志的广告的集合
	 * @return
	 */
	public String doJson(){
		this.jsonResult = new JsonResult();
		jsonResult.setCode(JsonResult.CODE.FAILURE);
		jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		//杂志 begin
		Publisher publisher=this.getSessionPublisher();
		if(publisher==null){
			jsonResult.setMessage("会话超时，请重新登录");
			return JSON;
		}
		if(publication==null)
		publication = new Publication();
		if(publisher!=null){
		publication.setPublisherId(publisher.getId());
		}
		ExtPageInfo page = new ExtPageInfo();
		page.setLimit(pageSize);
		page.setStart((currentPage-1)*pageSize);
		publication.setPubType(PojoConstant.PUBLICATION.PUBTYPE.MAGAZINE.getCode());//普通的杂志
		page = this.publicationService.getPageByCondition(page, publication);
		if(page!=null){
			long listCount = page.getTotal();
			pageNo= (listCount+pageSize-1)/pageSize;
	    	publicationList = page.getData();
		}
		jsonResult.put("publicationList",publicationList);
		jsonResult.put("pageNo", pageNo);
		//杂志end
		if(this.status==null || this.status<0)this.status=99;
		if(publicationList!=null&&publicationList.size()>0){
			 gImgPath(publicationList);
			//查询第一本杂志对应的广告 
			 	//一线出版商  （初始化时互动广告）
			 	if(publisher.getLevel()==1){
			 		Publication tempPub = publicationList.get(0);
			 		
			 		//过滤参数 begin
			 		Map<String,Object> map=new HashMap<String,Object>();
			 		map.put("publicationid", tempPub.getId());
			 		if(adCurrentPage<=0) adCurrentPage=1;
			 		map.put("begin",(adCurrentPage-1)*adPageSize);
			 		map.put("size", adPageSize);
			 		if(this.type==null || this.type<0)this.type=1;
			 		if(this.type==1 || this.type==3){//互动广告  插页广告
			 			if(this.type==1){
			 				map.put("adType", new String[]{"1","2","3"});
			 			}else{
			 				map.put("adType", new String[]{"5"});
			 			}
			 			
			 			if(this.status==1){//已审核
			 				map.put("status", new String[]{"2","5","6","8"});
			 			}else if(this.status==2){//待审核
			 				map.put("status", new String[]{"1"});
			 			}
			 			this.jsonResult.put("adList", advertiseService.getListPublicaton(map));
				 		int listCount = advertiseService.getListPublicatonCount(map);
					 	this.adPageNo =(listCount+adPageSize-1)/adPageSize;
					 	this.jsonResult.put("adPageNo", adPageNo);
			 		}else if(this.type==2){//互动内容
			 			if(this.status==2){
			 				map.put("status", 0);
			 			}else if(this.status==1){
			 				map.put("status", 1);
			 			}
			 			this.jsonResult.put("adList", interactiveContentService.queryPage(map));
				 		int listCount = interactiveContentService.queryPageCount(map);
					 	this.adPageNo =(listCount+adPageSize-1)/adPageSize;
					 	this.jsonResult.put("adPageNo", adPageNo);
			 		}	
			 		
			 	}else{//非一线出版商  （初始化时查询互动内容）
			 		Publication tempPub = publicationList.get(0);
			 		
			 		//过滤参数 begin
			 		Map<String,Object> map=new HashMap<String,Object>();
			 		map.put("publicationid", tempPub.getId());
			 		if(this.status==2){
		 				map.put("status", 0);
		 			}else if(this.status==1){
		 				map.put("status", 1);
		 			}
			 		if(adCurrentPage<=0) adCurrentPage=1;
			 		map.put("begin",(adCurrentPage-1)*adPageSize);
			 		map.put("size", adPageSize);
			 		//过滤参数 end
			 		this.jsonResult.put("adList", interactiveContentService.queryPage(map));
			 		int listCount = interactiveContentService.queryPageCount(map);
				 	this.adPageNo =(listCount+adPageSize-1)/adPageSize;
				 	this.jsonResult.put("adPageNo", adPageNo);
			 	}
			 	
		 }
		jsonResult.setCode(JsonResult.CODE.SUCCESS);
		jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		
		return JSON;
	}
	
	/**
	 * 根据杂志id获取对应的广告内容
	 * @return
	 */
	public String doADListJson(){
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		Publisher publisher=this.getSessionPublisher();
		if(publisher==null){
			jsonResult.setMessage("会话超时，请重新登录");
			return JSON;
		}
		if(this.status==null || this.status<0)this.status=99;
		if(publication!=null){
			if(publication.getId()!=null){
				try{
				Long.parseLong(publication.getId()+"");
				}catch (Exception e) {
					jsonResult.setMessage("会话超时，请重新登录");
					return JSON;
				}
			}else{
				jsonResult.setMessage("杂志为空");
				return JSON;
			}
			//一线出版商  互动广告
		 	if(publisher.getLevel()==1){
		 		Map<String,Object> map=new HashMap<String,Object>();
		 		map.put("publicationid", publication.getId());
		 		if(type==null || type<0)type=1;
		 		
	 			if(adCurrentPage<=0) adCurrentPage=1;
		 		map.put("begin",(adCurrentPage-1)*adPageSize);
		 		map.put("size", adPageSize);
		 		
			 	if(this.type==1 || this.type==3){//互动广告  插页广告
			 		if(this.status==1){//已审核
		 				map.put("status", new String[]{"2","5","6"});
		 			}else if(this.status==2){//待审核
		 				map.put("status", new String[]{"1","8"});
		 			}
			 		if(this.type==1){
			 			map.put("adType", new String[]{"1","2","3"});
			 		}else{
			 			map.put("adType", new String[]{"5"});
			 		}
			 		this.jsonResult.put("adList", advertiseService.getListPublicaton(map));
			 		int listCount = advertiseService.getListPublicatonCount(map);
				 	this.adPageNo =(listCount+adPageSize-1)/adPageSize;
				 	this.jsonResult.put("adPageNo", adPageNo);
		 		}else if(this.type==2){//互动内容
		 			if(this.status==2){//待审核
		 				map.put("status", 0);
		 			}else if(this.status==1){//已审核
		 				map.put("status", 1);
		 			}
		 			this.jsonResult.put("adList", interactiveContentService.queryPage(map));
			 		int listCount = interactiveContentService.queryPageCount(map);
				 	this.adPageNo =(listCount+adPageSize-1)/adPageSize;
				 	this.jsonResult.put("adPageNo", adPageNo);
		 		}
	 			
		 	}else{//非一线出版商 查询互动内容
		 		Map<String,Object> map=new HashMap<String,Object>();
		 		map.put("publicationid", publication.getId());
		 		if(this.status==2){//待审核
	 				map.put("status", 0);
	 			}else if(this.status==1){//已审核
	 				map.put("status", 1);
	 			}
		 		if(adCurrentPage<=0) adCurrentPage=1;
		 		map.put("begin",(adCurrentPage-1)*adPageSize);
		 		map.put("size", adPageSize);
		 		this.jsonResult.put("adList", interactiveContentService.queryPage(map));
		 		int listCount = interactiveContentService.queryPageCount(map);
			 	this.adPageNo =(listCount+adPageSize-1)/adPageSize;
			 	this.jsonResult.put("adPageNo", adPageNo);
		 	}
		}
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}
	
	/**
	 * 出版商审核通过编辑的广告
	 * @return
	 */
	public String checkAdJson(){
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		if(this.advertise!=null){
			if(this.type==null || this.type<=0)type=99;
			if(this.type==1||this.type==3){//互动广告或插页广告
				if(advertiseService.updateById(advertise)){
					advertiseService.SendAdminEmail(advertise.getId(), this.type);
					if(this.type==3&&advertise.getStatus()!=null&&advertise.getStatus()==7&&advertise.getId()!=null){//插页广告下架对应的事件也置为无效
						fpageEventService.deleteByAdid(advertise.getId());//删除对应事件
					}
					this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
					this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
				}
			}else if(this.type==2)//互动内容
			{
				this.interactiveContent = new InteractiveContent();
				interactiveContent.setId(advertise.getId());
				interactiveContent.setStatus(advertise.getStatus());
				if(interactiveContentService.updateById(interactiveContent)){
					advertiseService.SendAdminEmail(interactiveContent.getId(), this.type);
					this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
					this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
				}
			}
			
		}
		return JSON;
	}
	
	/**
	 * 填充杂志的封面 图片路径
	 * @param publicationList
	 */
	protected void gImgPath(List<Publication> publicationList){
		String tmpImgPath ="";
		if(publicationList!=null){
			for (Publication p : publicationList) {
					List<Issue> issueList = issueService.getByPublicationId(p.getId());
					for (Issue issue : issueList) {
						int tempStatus = issue.getStatus();
						if(tempStatus==0 || tempStatus==1 || tempStatus==2 || tempStatus==3){//已经转好的期刊
							p.setImgPath("/"+p.getId()+"/200_"+issue.getId()+".jpg");
							break;
						}else{
							p.setImgPath(tmpImgPath);
						}
					}
			}
		}
	}
	
	private Publication publication;
	private List<Publication> publicationList;
	private Advertise advertise;
	private InteractiveContent interactiveContent;
	private List<Advertise> advertiseList;
	private Integer type;
	private Integer status;
	private long currentPage;
    private long pageNo;
    
    private long adCurrentPage;
    private long adPageNo;
	

    
	public InteractiveContent getInteractiveContent() {
		return interactiveContent;
	}


	public void setInteractiveContent(InteractiveContent interactiveContent) {
		this.interactiveContent = interactiveContent;
	}

	private Publisher publisher;
	private List<Publisher> publisherList;

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public List<Publisher> getPublisherList() {
		return publisherList;
	}

	public void setPublisherList(List<Publisher> publisherList) {
		this.publisherList = publisherList;
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
	
	public Advertise getAdvertise() {
		return advertise;
	}


	public void setAdvertise(Advertise advertise) {
		this.advertise = advertise;
	}


	public List<Advertise> getAdvertiseList() {
		return advertiseList;
	}


	public void setAdvertiseList(List<Advertise> advertiseList) {
		this.advertiseList = advertiseList;
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


	public long getAdCurrentPage() {
		return adCurrentPage;
	}


	public void setAdCurrentPage(long adCurrentPage) {
		this.adCurrentPage = adCurrentPage;
	}


	public long getAdPageNo() {
		return adPageNo;
	}


	public void setAdPageNo(long adPageNo) {
		this.adPageNo = adPageNo;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
