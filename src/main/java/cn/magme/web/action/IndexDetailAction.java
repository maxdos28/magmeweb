package cn.magme.web.action;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.constants.CacheConstants;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.Advertise;
import cn.magme.pojo.FpageEvent;
import cn.magme.pojo.FpageEventComment;
import cn.magme.pojo.HomePageItem;
import cn.magme.pojo.Sort;
import cn.magme.pojo.sns.Creative;
import cn.magme.result.sns.CreativeCommentResult;
import cn.magme.service.AdvertiseService;
import cn.magme.service.DetailPageService;
import cn.magme.service.FpageEventCommentService;
import cn.magme.service.FpageEventService;
import cn.magme.service.TagService;
import cn.magme.service.sns.SnsCreativeService;
import cn.magme.util.StringUtil;
import cn.magme.util.Struts2Utils;
import cn.magme.util.keyword.KeywordFilter;
import cn.magme.web.entity.IndexDetailContent;

import com.danga.MemCached.MemCachedClient;
import com.mysql.jdbc.StringUtils;

@SuppressWarnings("serial")
@Results({ @Result(name = "success", location = "/WEB-INF/pages/indexdetail.ftl"),
	@Result(name = "videoUrl", location = "/WEB-INF/pages/dialog/videocomm.ftl"),
	@Result(name = "getContent", location = "/WEB-INF/pages/detail/content_ajax.ftl"),
	@Result(name = "getCommentList", location = "/WEB-INF/pages/detail/comment_ajax.ftl"),
	@Result(name = "eventcomment", location = "/WEB-INF/pages/sns/eventComment.ftl")
	})
public class IndexDetailAction extends BaseAction  {
	@Resource
	private FpageEventService fpageEventService;
	
	@Resource
	private AdvertiseService advertiseService;

	@Resource
	private DetailPageService detailPageService;
	
	@Resource
    private MemCachedClient memCachedClient;
	
	@Resource
	private TagService tagService;
	
	@Resource
	private SnsCreativeService snsCreativeService;
	
	private static final int MAX_DESCRIPTION_LENGTH=150;
	
	private static final Logger log=Logger.getLogger(IndexDetailAction.class);
	
	private static final int MAX_COMMENT_LENGHT=196;

	
	@Resource
	private FpageEventCommentService fpageEventCommentService;
	
	private List<HomePageItem> itemList;
	private List<Sort> homeSortList;
	private List<CreativeCommentResult> commentLst;
	private List<FpageEventComment> fpageEventComment;
	private List<Advertise> advertiseList;
	private String type;
	// 一级分类(传给flash)
	private Long sortId;
	// 二级分类(传给flash)
	private String tagName;
	private Long itemId;
	private String content;
	private Integer begin;
	private Integer size;
	private Integer total;
	private List<Advertise> adVideo;
	private String urlStr;
	
	private IndexDetailContent indexDetailContent;
	
	public String execute() throws Exception{
		if(this.type!=null && "event".equalsIgnoreCase(this.type)){
			this.itemId=100000+this.itemId;
			return "detail_redirect";
		}
		if(this.type!=null && "creative".equalsIgnoreCase(this.type)){
			return "detail_redirect";
		}
		throw new RuntimeException("这个页面不提供服务了，请切换");
	}
	
	public String execute2() throws Exception {
		itemList = fpageEventService.queryFpageEventByIndexMid(itemId, type);//推荐内容 暂时不做缓存
		commentLst = detailPageService.selCommentLst(itemId, type, 0, 10);//最新评论 不要缓存

		total = detailPageService.selCommentLst(itemId, type, null, null).size();
		if("event".equals(type)){
		//图片广告集合
		advertiseList = advertiseService.queryByIndexMid(PojoConstant.ADVERTISE.ADTYPE.IMAGE.getCode(), itemId);
		//视频广告集合
			List<Advertise> tempList = advertiseService.queryByIndexMid(PojoConstant.ADVERTISE.ADTYPE.VEDIO.getCode(), itemId);
			if(tempList!=null && tempList.size()>0){
				for (Advertise advertise : tempList) {
					//视频对应的缩略图片
					String tempPath = advertise.getMediaurl();
					if(tempPath!=null){
						tempPath = tempPath.substring(0,tempPath.lastIndexOf("."))+".jpg";
						advertise.setImgurl(tempPath);
					}
				}
				adVideo = tempList;
			}
		}
		
		//缓存taglist和content，description
		if(memCachedClient!=null){
			if("event".equals(type)){
				indexDetailContent=(IndexDetailContent)this.memCachedClient.get(CacheConstants.INDEX_DETAIL_EVENT_CONTENT+itemId);
			}else{
				indexDetailContent=(IndexDetailContent)this.memCachedClient.get(CacheConstants.INDEX_DETAIL_CREATIVE_CONTENT+itemId);
			}
			
		}
		if(indexDetailContent==null){
			indexDetailContent=new IndexDetailContent();
			
			if("event".equals(type)){
				indexDetailContent.setTagList(this.tagService.getTagListByTypeAndObjectId(PojoConstant.TAG.TYPE_EVENT, itemId, null, null));
				FpageEvent event=fpageEventService.getFpageEventById(itemId);
				indexDetailContent.setTitle(event.getTitle());
				if(event.getDescription()!=null && event.getDescription().length()>0){
					String htmlText=StringUtil.HtmlText(event.getDescription());
					if(htmlText!=null && htmlText.length()>MAX_DESCRIPTION_LENGTH){
						indexDetailContent.setDescription(htmlText.substring(0,MAX_DESCRIPTION_LENGTH));
					}else{
						indexDetailContent.setDescription(htmlText);
					}
				}
				
			}else{
				indexDetailContent.setTagList(this.tagService.getTagListByTypeAndObjectId(PojoConstant.TAG.TYPE_CREATIVE, itemId, null, null));
				Creative creative=snsCreativeService.getEditCreative(itemId);
				if(creative==null){
					log.error("indexdetail 报错，itemid:"+itemId);
				}
				if(creative!=null && creative.getContent()!=null && creative.getContent().length()>0){
					indexDetailContent.setTitle(creative.getTitle());
					String htmlText=StringUtil.HtmlText(creative.getContent());
					if(htmlText!=null && htmlText.length()>MAX_DESCRIPTION_LENGTH){
						indexDetailContent.setDescription(htmlText.substring(0,MAX_DESCRIPTION_LENGTH));
					}else{
						indexDetailContent.setDescription(htmlText);
					}
				}
				
				
			}
		}
		
		
		
		return "success";
	}

	/**
	 * 用户添加评论
	 * @return
	 */
	public String addComment() {
	    KeywordFilter kf = new KeywordFilter();
	    kf.loadKeyword(new File(systemProp.getFileFilterPath()));
	    String tmp = content;
	    tmp = kf.filter(tmp);
	    //增加中间页评论字数限制
	    if(StringUtil.isNotBlank(tmp) && tmp.length()<=MAX_COMMENT_LENGHT){
	    	tmp=StringUtil.escapeHtml(tmp);
	    	detailPageService.insertComment(this.getSessionUserId(), itemId, tmp, type);
	    }
		return getCommentList();
	}
	
	/**
	 * 用户添加评论sns使用
	 * @return
	 */
	public String addCommentSns() {
	    KeywordFilter kf = new KeywordFilter();
	    kf.loadKeyword(new File(systemProp.getFileFilterPath()));
	    String tmp = content;
	    tmp = kf.filter(tmp);
	    if(StringUtil.isNotBlank(tmp) && tmp.length()<=MAX_COMMENT_LENGHT){
	    	tmp=StringUtil.escapeHtml(tmp);
		    detailPageService.insertComment(this.getSessionUserId(), itemId, tmp, type);
	    }
		Map map = new HashMap();
		map.put("eventId", itemId);
        //map.put("begin", 0);
        //map.put("size", 50);
		fpageEventComment=fpageEventCommentService.getFpageEventCommentByEventIdSns(map);
		return "eventcomment";
	}
	
	
	/**
	 * 下半部刷新
	 * @return
	 */
	public String getDetailContent() {
		commentLst = detailPageService.selCommentLst(itemId, type, 0, 10);
		total = detailPageService.selCommentLst(itemId, type, null, null).size();
		itemList = fpageEventService.queryFpageEventByIndexMid(itemId, type);//推荐内容
		if("event".equals(type)){
		//图片广告集合
		advertiseList = advertiseService.queryByIndexMid(PojoConstant.ADVERTISE.ADTYPE.IMAGE.getCode(), itemId);
		//视频广告集合
			List<Advertise> tempList = advertiseService.queryByIndexMid(PojoConstant.ADVERTISE.ADTYPE.VEDIO.getCode(), itemId);
			if(tempList!=null && tempList.size()>0){
				if(tempList!=null && tempList.size()>0){
					for (Advertise advertise : tempList) {
						//视频对应的缩略图片
						String tempPath = advertise.getMediaurl();
						if(tempPath!=null){
							tempPath = tempPath.substring(0,tempPath.lastIndexOf("."))+".jpg";
							advertise.setImgurl(tempPath);
						}
					}
					adVideo = tempList;
				}
			}
		}
		return "getContent";
	}

	/**
	 * 评论刷新
	 * @return
	 */
	public String getCommentList() {
		commentLst = detailPageService.selCommentLst(itemId, type, begin, size);
		total = detailPageService.selCommentLst(itemId, type, null, null).size();
		return "getCommentList";
	}
	
	/**
	 * M1个人首页事件评论
	 */
	public String addEventComment() {
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		if(StringUtil.isNotBlank(content))
			detailPageService.insertComment(this.getSessionUserId(), itemId, content, PojoConstant.SORT.TYPE_EVENT);
		commentLst=SnsComment();
		this.jsonResult.put("comment", commentLst);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		return JSON;
	}
	
	/**
	 * M1个人首页事件评论加载
	 */
	public String getEventCommentList() {
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		commentLst=SnsComment();
		this.jsonResult.put("comment", commentLst);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		return JSON;
	}
	//获取评论 时间处理
	private List<CreativeCommentResult> SnsComment(){
		 commentLst = detailPageService.selCommentLst(itemId, PojoConstant.SORT.TYPE_EVENT, 0, 4);
	
		List<CreativeCommentResult> result = new ArrayList<CreativeCommentResult>();
		try{
			 java.util.Date now = new Date();
			 for (CreativeCommentResult cr : commentLst) {
				java.util.Date date=cr.getCreateTime();
				long l=now.getTime()-date.getTime();
				long day=l/(24*60*60*1000);
				long hour=(l/(60*60*1000)-day*24);
				long min=((l/(60*1000))-day*24*60-hour*60);
				long s=(l/1000-day*24*60*60-hour*60*60-min*60);
				
				if(day<2 && day>0)
					cr.setTime("一天前");
				else if(day<1){
					if(hour>0){
						cr.setTime(hour+"小时前");
					}else{
						if(min>0)
							cr.setTime(min+"分钟前");
						else
							cr.setTime(s+"秒钟前");
					}
				}
				result.add(cr);
			 }
		}catch (Exception e) {
		}
		return result;
	}
	
	public String toVideoPlay(){
		urlStr="";
		urlStr = Struts2Utils.getParameter("url");
		return "videoUrl";
	}
	
	
	
	public IndexDetailContent getIndexDetailContent() {
		return indexDetailContent;
	}

	public void setIndexDetailContent(IndexDetailContent indexDetailContent) {
		this.indexDetailContent = indexDetailContent;
	}

	public List<Sort> getHomeSortList() {
		return homeSortList;
	}

	public void setHomeSortList(List<Sort> homeSortList) {
		this.homeSortList = homeSortList;
	}
	
	public List<HomePageItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<HomePageItem> itemList) {
		this.itemList = itemList;
	}
	
	public List<CreativeCommentResult> getCommentLst() {
		return commentLst;
	}

	public void setCommentLst(List<CreativeCommentResult> commentLst) {
		this.commentLst = commentLst;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getSortId() {
		return sortId;
	}

	public void setSortId(Long sortId) {
		this.sortId = sortId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public List<Advertise> getAdvertiseList() {
		return advertiseList;
	}

	public void setAdvertiseList(List<Advertise> advertiseList) {
		this.advertiseList = advertiseList;
	}
	
	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
	public List<Advertise> getAdVideo() {
		return adVideo;
	}

	public void setAdVideo(List<Advertise> adVideo) {
		this.adVideo = adVideo;
	}

	public String getUrlStr() {
		return urlStr;
	}

	public void setUrlStr(String urlStr) {
		this.urlStr = urlStr;
	}

	public List<FpageEventComment> getFpageEventComment() {
		return fpageEventComment;
	}

}
