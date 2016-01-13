package cn.magme.web.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant.COMMON;
import cn.magme.constants.PojoConstant.COMMON_COMMENT;
import cn.magme.constants.PojoConstant.SORT;
import cn.magme.pojo.CommonComment;
import cn.magme.pojo.FpageEventComment;
import cn.magme.pojo.Publication;
import cn.magme.pojo.sns.CreativeComment;
import cn.magme.result.sns.CreativeCommentListResult;
import cn.magme.result.sns.CreativeCommentResult;
import cn.magme.service.CommonCommentService;
import cn.magme.service.DetailPageService;
import cn.magme.service.FpageEventCommentService;
import cn.magme.service.FpageEventService;
import cn.magme.service.PublicationService;
import cn.magme.service.sns.SnsCreativeCommentService;
import cn.magme.util.IpUtil;
import cn.magme.util.StringUtil;

@SuppressWarnings("serial")
@Results({
	@Result(name = "comment",location ="/WEB-INF/pages/publication/newmagComment.ftl")
	 })
public class CommonCommentAction extends BaseAction {
	@Resource
	private CommonCommentService commonCommentService;
	@Resource
	private FpageEventCommentService fpageEventCommentService;
	@Resource
	private FpageEventService fpageEventService;
	@Resource
	private DetailPageService detailPageService;
	@Resource
	private SnsCreativeCommentService snsCreativeCommentService;
	@Resource
	private PublicationService publicationService;

	private Long objectId;
	private String type;
	private Integer begin;
	private Integer size=20;
	private Integer topSize=2;
	private String content;
	private String param;
	private Long id;
	private Long publicationId;
	

	//only for flash
	private Integer pageNo;
	private Integer secondPage;
	private Long adId;
	
	public String publicationComment(){
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("publicationId", this.publicationId);
		condition.put("size", 20);
		condition.put("begin", begin);
		comment=publicationService.queryPublicationComment(condition);
		return "comment";
	}
	
	/**
	 * 根据用户获取对应期刊的评论
	 * @return
	 */
	public String issueCommentByUser(){
		this.jsonResult = JsonResult.getFailure();
		if(this.getSessionUserId()!=null){
		}else{
			this.jsonResult.setMessage("用户未登录");
			return JSON;
		}
		List<CommonComment> list = commonCommentService.getCommonComment(this.getSessionUserId(),objectId, COMMON_COMMENT.TYPE_PAGE, param, begin, size);
//		for (CommonComment commonComment : list) {
//			Long issueId = commonComment.getObjectId();
//			if(issueId!=null){
//				Publication pub = publicationService.queryPubByIssueId(issueId);
//				commonComment.setPublicationPo(pub);
//			}
//		}
		this.jsonResult = JsonResult.getSuccess();
		this.jsonResult.put("list1", list);
		return JSON;
	}
	
	/**
	 * 根据用户查询对应的期刊所写的评论
	 * @return
	 * 
	 */
	public String commentByUser(){
		this.jsonResult = JsonResult.getFailure();
		if(this.getSessionUserId()==null){
			this.jsonResult = JsonResult.getNotLogin();
			return JSON;
		}
		List<CommonComment> list_issue_comment =  commonCommentService.getCommentByUser(this.getSessionUserId(), objectId, COMMON_COMMENT.TYPE_PAGE, param, begin, size);
		for (CommonComment commonComment : list_issue_comment) {
			if(commonComment.getObjectId()!=null){
				List<CommonComment> list = commonCommentService.getCommonComment(this.getSessionUserId(),commonComment.getObjectId(),  COMMON_COMMENT.TYPE_PAGE, param, 0, topSize);
				commonComment.setCcList(list);
//				int listCount = commonCommentService.getCommonCommentCount(this.getSessionUserId(),commonComment.getObjectId(),  COMMON_COMMENT.TYPE_PAGE, param);
				int listCount = commonCommentService.getCommonCommentCount(null,commonComment.getObjectId(),  COMMON_COMMENT.TYPE_PAGE, param);
				commonComment.setCommonCommentCount(listCount);
			}
			if(commonComment.getIssuePath()!=null){
				commonComment.setIssuePath(StringUtil.prefix(commonComment.getIssuePath(), "200_"));
			}
		}
		this.jsonResult = JsonResult.getSuccess();
		this.jsonResult.put("list_issue_comment", list_issue_comment);
		
		return JSON;
	}
	
	/**
	 * 根据杂志id获取对应的评论
	 * @return
	 */
	public String commonCommentByPublicationId(){
		this.jsonResult = JsonResult.getFailure();
		if(id!=null){
			List<CommonComment> listComment = commonCommentService.getCommonCommentByPublicationId(id, begin, size);
			if(listComment != null){
				Collections.reverse((List<CommonComment>)listComment);
			}
			this.jsonResult = JsonResult.getSuccess();
			this.jsonResult.put("listComment", listComment);
		}
		return JSON;
	}
	
	/**
	 * 根据杂志id获取对应的评论总数
	 * @return
	 */
	public String commonCommentByPublicationIdCount(){
		this.jsonResult = JsonResult.getFailure();
		if(id!=null){
			Integer listCommentCount = commonCommentService.getCommonCommentByPublicationIdCount(id);
			this.jsonResult = JsonResult.getSuccess();
			this.jsonResult.put("listCommentCount", listCommentCount);
		}
		return JSON;
	}
	
	/**
	 * 删除评论
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {
		Long userId = getSessionUserId();
		if(userId == null){
			this.jsonResult = JsonResult.getNotLogin();
			return JSON;
		}
		this.jsonResult = JsonResult.getFailure();
		try{
			if(COMMON_COMMENT.TYPE_CREATIVE.equals(type)){//指定id的作品的评论
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("userid", getSessionUserId());
				map.put("id", id);
				CreativeComment comment = snsCreativeCommentService.getCreativeComment(map);
				if(comment == null){
					this.jsonResult.setMessage("评论不存在或已删除");
					return JSON;
				} else if(!comment.getUserId().equals(userId)){
					this.jsonResult.setMessage("您只能删除自己的评论");
					return JSON;
				}
				comment.setStatus(COMMON.STATUS_DEL);
				snsCreativeCommentService.updateComment(comment);
			}else if(COMMON_COMMENT.TYPE_GOLF.equals(type)){//指定id的golf的评论
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("userid", getSessionUserId());
				map.put("id", id);
				CreativeComment comment = snsCreativeCommentService.getCreativeComment(map);
				if(comment == null){
					this.jsonResult.setMessage("评论不存在或已删除");
					return JSON;
				} else if(!comment.getUserId().equals(userId)){
					this.jsonResult.setMessage("您只能删除自己的评论");
					return JSON;
				}
				comment.setStatus(COMMON.STATUS_DEL);
				snsCreativeCommentService.updateComment(comment);
			} else {
				boolean valid = this.processCommentType();
				if(!valid){
					return JSON;
				}
				if(SORT.TYPE_EVENT.equals(type)){//指定id的事件
					FpageEventComment comment = fpageEventCommentService.getById(id);
					if(comment == null){
						this.jsonResult.setMessage("评论不存在或已删除");
						return JSON;
					} else if(!comment.getUserId().equals(userId)){
						this.jsonResult.setMessage("您只能删除自己的评论");
						return JSON;
					}
					comment.setStatus(COMMON.STATUS_DEL);
					fpageEventCommentService.updateComment(comment);
				} else {//单页或广告
					CommonComment comment = commonCommentService.getById(id);
					if(comment == null){
						this.jsonResult.setMessage("评论不存在或已删除");
						return JSON;
					} else if(!comment.getUserId().equals(userId)){
						this.jsonResult.setMessage("您只能删除自己的评论");
						return JSON;
					}
					comment.setStatus(COMMON.STATUS_DEL);
					commonCommentService.updateComment(comment);
				}
			}
			jsonResult = JsonResult.getSuccess();
		}catch(Exception e){
		}
		return JSON;
	}
	
	/**
	 * 发表评论
	 * @return
	 * @throws Exception
	 */
	public String insert() throws Exception {
		Long userId = getSessionUserId();
		this.jsonResult = JsonResult.getFailure();
		if(content == null || "".equals(content)){
			this.jsonResult.setMessage("内容不能为空");
		} else if(userId == null){
			this.jsonResult = JsonResult.getNotLogin();
		} else if(COMMON_COMMENT.TYPE_CREATIVE.equals(type) || COMMON_COMMENT.TYPE_EVENT.equals(type)){//指定id的事件或作品的评论
			this.jsonResult = detailPageService.insertComment(this.getSessionUserId(), objectId, StringUtil.escapeHtml(content), type);
		}else if(COMMON_COMMENT.TYPE_GOLF.equals(type)){//指定id的golf的评论
			this.jsonResult = detailPageService.insertComment(this.getSessionUserId(), objectId, StringUtil.escapeHtml(content), type);
		}
		else {
			boolean valid = processCommentType();
			if(!valid) {
				return JSON;
			}
			//事件
			if(COMMON_COMMENT.TYPE_EVENT.equals(type)){
				String ip = ServletActionContext.getRequest().getRemoteAddr();
				FpageEventComment comment = new FpageEventComment();
				comment.setContent(StringUtil.escapeHtml(content));
				comment.setEventId(objectId);
				comment.setUserId(userId);
				comment.setIpaddress(IpUtil.ip2Long(ip));
				comment.setStatus(COMMON.STATUS_OK);
				fpageEventCommentService.insertComment(comment);
			} else {//单页 or 广告
				CommonComment comment = new CommonComment();
				comment.setContent(StringUtil.escapeHtml(content));
				comment.setObjectId(objectId);
				comment.setType(type);
				comment.setUserId(userId);
				comment.setParam(param);
				comment.setStatus(COMMON.STATUS_OK);
				commonCommentService.insertComment(comment);
			}
			this.jsonResult = JsonResult.getSuccess();
		}
		return JSON;
	}

	private boolean processCommentType() {
		if(type == null){//事件or单页
			Long eventId = fpageEventService.getEventIdByIssueIdAndPageNumber(objectId, pageNo);
			if(eventId == null) {
				type = COMMON_COMMENT.TYPE_PAGE;
				param = pageNo + "";
			} else {
				type = COMMON_COMMENT.TYPE_EVENT;
				objectId = eventId;//事件id
			}
		} else if(COMMON_COMMENT.TYPE_ADVERTISE.equals(type)){//广告
			if(adId != null){
				param = adId + "";
			} else {
				this.jsonResult.setMessage("广告不存在");
				return false;
			}
		}
		return true;
	}

	/**
	 * 查询所有评论<br/>
	 * flash与iOS通用接口<br/>
	 * 获取的结果可能的pojo有：CommonComment、FpageEventComment、CreativeCommentResult
	 */
	@Override
	public String execute() throws Exception {
		this.jsonResult = JsonResult.getSuccess();
		 if(COMMON_COMMENT.TYPE_CREATIVE.equals(type) || COMMON_COMMENT.TYPE_EVENT.equals(type)){//作品  or 事件(直接查询指定id的事件或作品的评论)
			//作品id，‘creative’或‘event’，已加载数量，本次加载数量
			List<CreativeCommentResult> list = detailPageService.selCommentLst(objectId, type, begin, size);
			if(list == null) list = new ArrayList<CreativeCommentResult>();
			this.jsonResult.put("list1", list);
		}else if(COMMON_COMMENT.TYPE_GOLF.equals(type)){//golf(直接查询指定id的事件或作品的评论)
			//作品id，‘golf’，已加载数量，本次加载数量
			List<CreativeCommentResult> list = detailPageService.selCommentLst(objectId, type, begin, size);
			if(list == null) list = new ArrayList<CreativeCommentResult>();
			this.jsonResult.put("list1", list);
		}
		 else {
			if(COMMON_COMMENT.TYPE_ADVERTISE.equals(type)){//广告
				//期刊id，‘ad’，广告id，已加载数量，本次加载数量
				List<CommonComment> list = commonCommentService.getCommonComment(objectId, type, adId + "", begin, size);
				if(list == null) list = new ArrayList<CommonComment>();
				this.jsonResult.put("list1", list);
			}else {//事件 or 单页
				//第一页
				Long eventId = null;
				if(pageNo != null){
					eventId = fpageEventService.getEventIdByIssueIdAndPageNumber(objectId, pageNo);
					if(eventId != null){//属于事件
						eventComment(1, eventId);
					} else {//单页
						singlePageComment(1, pageNo);
					}
				}
				//第二页
				if(secondPage != null){
					Long eventId2 = fpageEventService.getEventIdByIssueIdAndPageNumber(objectId, secondPage);
					if(eventId2 != null){//事件
						if(!eventId2.equals(eventId)){//不是同一个事件
							eventComment(2, eventId2);
						}
					} else {//单页
						singlePageComment(2, secondPage);
					}
				}
			}
		}
		/* 倒序查询结果 */
		reverseList(1);
		reverseList(2);
		return JSON;
	}
	
	public String golfCreativeCount(){
		this.jsonResult = JsonResult.getSuccess();
		//golf(直接查询指定id的事件或作品的评论)
			Integer listCount = detailPageService.selCommentListCount(objectId, COMMON_COMMENT.TYPE_GOLF);
			this.jsonResult.put("listCount", listCount);
		return JSON;
	}

	/**
	 * 倒序
	 * @param index
	 */
	@SuppressWarnings("rawtypes")
	private void reverseList(int index) {
		Object list = this.jsonResult.get("list" + index);
		if(list != null){
			Collections.reverse((List)list);
		}
	}

	/**
	 * put 事件的评论到result中
	 * @param index 1：左页，2：右页
	 * @param eventId
	 */
	private void eventComment(int index, Long eventId) {
		List<FpageEventComment> list = fpageEventCommentService.getFpageEventCommentByEventId(eventId, begin, size);
		if(list == null) list = new ArrayList<FpageEventComment>();
		this.jsonResult.put("list" + index, list);
	}

	/**
	 * put 单页的评论到result中
	 * @param index 1：左页，2：右页
	 * @param pageNum
	 */
	private void singlePageComment(int index, Integer pageNum) {
		if(pageNum < 1) return;
		
		List<CommonComment> list = commonCommentService.getCommonComment(objectId, type, pageNum + "", begin, size);
		if(list == null) list = new ArrayList<CommonComment>();
		this.jsonResult.put("list" + index, list);
	}
	
	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getBegin() {
		return begin;
	}

	public void setBegin(Integer begin) {
		this.begin = begin;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getParam() {
		return param;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setSecondPage(Integer secondPage) {
		this.secondPage = secondPage;
	}

	public Integer getSecondPage() {
		return secondPage;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setAdId(Long adId) {
		this.adId = adId;
	}

	public Long getAdId() {
		return adId;
	}
	
	public Long getPublicationId() {
		return publicationId;
	}

	public void setPublicationId(Long publicationId) {
		this.publicationId = publicationId;
	}

	private List<CreativeCommentListResult> comment;
	
	public List<CreativeCommentListResult> getComment() {
		return comment;
	}

	public Integer getTopSize() {
		return topSize;
	}

	public void setTopSize(Integer topSize) {
		this.topSize = topSize;
	}
}
