package cn.magme.web.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import cn.magme.common.JsonResult;
import cn.magme.constants.CacheConstants;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.FpageEvent;
import cn.magme.pojo.HomePageItem;
import cn.magme.pojo.Issue;
import cn.magme.pojo.SecondContentDto;
import cn.magme.pojo.Sort;
import cn.magme.pojo.sns.Creative;
import cn.magme.pojo.sns.CreativeEx;
import cn.magme.service.FpageEventService;
import cn.magme.service.HomePageItemService;
import cn.magme.service.IssueService;
import cn.magme.service.SortService;
import cn.magme.service.TagService;
import cn.magme.service.TextPageService;
import cn.magme.service.UserEnjoyService;
import cn.magme.service.sns.SnsCreativeExService;
import cn.magme.service.sns.SnsCreativeService;
import cn.magme.util.StringUtil;
import cn.magme.web.entity.IndexDetailContent;

import com.danga.MemCached.MemCachedClient;

/**
 * 提供给中间页查询事件或作品信息
 * @author fredy
 * @since 2012-06-28
 *
 */
public class SecondContentAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8560813654892776734L;
	
	@Resource
	private FpageEventService fpageEventService;
	
	@Resource
	private IssueService issueService;
	
	@Resource
	private TagService tagService;
	
	@Resource
	private HomePageItemService homePageItemService;
	
	@Resource
	private SortService sortService;
	
	@Resource
	private TextPageService textPageService;
	
	@Resource
	private SnsCreativeService snsCreativeService;
	
	@Resource
	private SnsCreativeExService snsCreativeExService;
	
	@Resource
	private UserEnjoyService userEnjoyService;
	
	/** memCache缓存 */
    @Resource
    private MemCachedClient memCachedClient;
	/**
	 * 事件类型
	 */
	private static final int TYPE_EVENT=1;
	
	/**
	 * 作品类型
	 */
	private static final int TYPE_CREATIVE=2;
	
	/**
	 * 上一个作品或事件
	 */
	private static final int NEIGHBOR_TYPE_PRE=-1;
	/**
	 * 当前作品或事件
	 */
	private static final int NEIGHBOR_TYPE_CURRENT=0;
	/**
	 * 下一个事件或作品
	 */
	private static final int NEIGHBOR_TYPE_NEXT=1;
	/** 对转义字符进行转码 */
	private static final int ENCODE_TAG_TYPE_ENCODE = 0;
	
	/**
	 * 时间格式化
	 */
	private static final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	private static final Logger log=Logger.getLogger(SecondContentAction.class);
	private static final int MAX_DESCRIPTION_LENGTH=150;
	
	
	public String execute(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		if(id==null || id<=0){
			this.jsonResult.setMessage("事件或者作品id必须大于0");
			log.error("事件或者作品id必须大于0");
			return JSON;
		}
		if(StringUtil.isBlank(tagName)){
			this.tagName=null;
		}
		if(sortId!=null && sortId<=0){
			this.sortId=null;
		}
		try {
			//默认当前事件或作品
			Long realId=id;
			String realType=(type==TYPE_EVENT?PojoConstant.SORT.TYPE_EVENT:PojoConstant.SORT.TYPE_CREATIVE);
			String sortName=null;
			//非当前事件或作品
			if(this.neighborType!=NEIGHBOR_TYPE_CURRENT){
				//初始化条件
				boolean isNext=(this.neighborType==1);
				if(this.sortId!=null && this.sortId>0){
					Sort sort=sortService.getById(sortId);
					sortName=sort.getName();
				}
				String servicetype=(type==TYPE_EVENT?PojoConstant.SORT.TYPE_EVENT:PojoConstant.SORT.TYPE_CREATIVE);
				HomePageItem item=homePageItemService.getNeighborHomePageItem(sortName, tagName,id, servicetype, isNext);
				if(item!=null){
					realId=item.getItemId();
					realType=item.getType();
				}else{
					return JSON;
				}
			}
			
			//已经登录 判断是否喜欢
			this.userId=this.getSessionUserId();
			if(this.userId!=null){
				this.enjoy=this.userEnjoyService.isEnjoy(userId, realId,
						PojoConstant.SORT.TYPE_EVENT.equalsIgnoreCase(realType)?PojoConstant.USERENJOY.TYPE_EVENT:PojoConstant.USERENJOY.TYPE_CREATIVE);
			}
			
			//事件详情
			if(PojoConstant.SORT.TYPE_EVENT.equalsIgnoreCase(realType)){
				getEventDetail(realId,sortName);
			}else{
				//作品详情
				getCreativeDetail(realId,sortName);
			}
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			this.jsonResult.put("contentDto", this.contentDto);
			this.jsonResult.put("userId", this.userId);
			this.jsonResult.put("enjoy", this.enjoy);
		} catch (Exception e) {
			log.error("", e);
		}
		
		return JSON;
	}
	
	/**
	 * 
	 * 查询事件详情
	 * @param id
	 * @return 查询成功返回true,失败返回false
	 */
	private boolean getEventDetail(Long id,String sortName){
		//从cache中取数据
		if(memCachedClient!=null){
			this.contentDto=(SecondContentDto)this.memCachedClient.get(CacheConstants.SECOND_CONTENT_EVENT + encodeTag + "_" + id);
			if(this.contentDto!=null){
				return true;
			}
		}
		FpageEvent event=this.fpageEventService.getFpageEventById(id);
		if(event==null){
			this.jsonResult.setMessage("事件不存在，事件id："+id);
			return false;
		}
		Issue issue=this.issueService.queryById(event.getIssueId());
		this.contentDto=new SecondContentDto();
		String content = this.textPageService.getByIssueIdPageNoAndEndPageNo(event.getIssueId(), event.getPageNo(), event.getEndPageNo());
		this.contentDto.setContent(content);
		this.contentDto.setIssueId(event.getIssueId());
		this.contentDto.setPublicationName(issue.getPublicationName());
		this.contentDto.setPublishDate(sdf.format(issue.getPublishDate()));
		this.contentDto.setPublicationId(event.getPublicationId());
		this.contentDto.setIssueId(event.getIssueId());
		this.contentDto.setPageNo(event.getPageNo());
		this.contentDto.setPicUrl(this.systemProp.getFpageServerUrl()+"/event/"+event.getImgFile());
		this.contentDto.setTitle(event.getTitle());
		this.contentDto.setTagList(this.tagService.getTagListByTypeAndObjectId(PojoConstant.TAG.TYPE_EVENT, id, null, null));
		this.contentDto.setEventOrCreative(TYPE_EVENT);
		this.contentDto.setId(id);
		HomePageItem nextitem=homePageItemService.getNeighborHomePageItem(sortName, tagName,id, PojoConstant.SORT.TYPE_EVENT, true);
		if(nextitem!=null){
			this.contentDto.setNextId(nextitem.getItemId());
		}
		HomePageItem preitem=homePageItemService.getNeighborHomePageItem(sortName, tagName,id, PojoConstant.SORT.TYPE_EVENT, false);
		if(preitem!=null){
			this.contentDto.setPreviousId(preitem.getItemId());
		}
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, CacheConstants.CACHE_TWO_HOUR);
		if(memCachedClient!=null){
			this.memCachedClient.set(CacheConstants.SECOND_CONTENT_EVENT+id, this.contentDto,cal.getTime());
		}
		return true;
	}
	
	/**
	 * 
	 * 查询作品详情
	 * @param id
	 * @return 查询成功返回true,失败返回false
	 */
	private boolean getCreativeDetail(Long id,String sortName){
		if(id==null || id <=0){
			return false;
		}
		if(memCachedClient!=null){
			this.contentDto=(SecondContentDto)this.memCachedClient.get(CacheConstants.SECOND_CONTENT_CREATIVE + encodeTag + "_" + id);
			if(this.contentDto!=null){
				return true;
			}
		}
		Creative creative=snsCreativeService.getEditCreative(id);
		if(creative==null){
			log.error("作品不存在,作品id:"+id);
			return false;
		}
		CreativeEx creativeEx=snsCreativeExService.getCreativeEx(creative.getId(), 0);
		this.contentDto=new SecondContentDto();
		String htmlText = StringUtil.HtmlText(creative.getContent());
		if(encodeTag == ENCODE_TAG_TYPE_ENCODE){
			htmlText = htmlText.replaceAll("&nbsp;", " ").replaceAll("&amp;", "&");
		}
		this.contentDto.setContent(htmlText);
		this.contentDto.setTagList(this.tagService.getTagListByTypeAndObjectId(PojoConstant.TAG.TYPE_CREATIVE, id, null, null));
		this.contentDto.setPicUrl(systemProp.getStaticServerUrl()+StringUtil.prefix(creativeEx.getImgPath(), "500_"));
		this.contentDto.setPublishDate(sdf.format(creative.getCreateTime()));
		this.contentDto.setTitle(creative.getTitle());
		this.contentDto.setEventOrCreative(TYPE_CREATIVE);
		this.contentDto.setId(id);
		HomePageItem nextitem=homePageItemService.getNeighborHomePageItem(sortName, tagName,id, PojoConstant.SORT.TYPE_CREATIVE, true);
		if(nextitem!=null){
			this.contentDto.setNextId(nextitem.getItemId());
		}
		HomePageItem preitem=homePageItemService.getNeighborHomePageItem(sortName, tagName,id, PojoConstant.SORT.TYPE_CREATIVE, false);
		if(preitem!=null){
			this.contentDto.setPreviousId(preitem.getItemId());
		}
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, CacheConstants.CACHE_TWO_HOUR);
		if(memCachedClient!=null){
			this.memCachedClient.set(CacheConstants.SECOND_CONTENT_CREATIVE+id, this.contentDto,cal.getTime());
		}
		return true;
	}
	
	/**
	 * 中间页面对应的事件或作品的dto
	 */
	private SecondContentDto contentDto;
	/**
	 * t_sort的id，对应为标签
	 */
	private Long sortId;
	
	/**
	 * 标签名称
	 */
	private String tagName;
	
	/**
	 * id事件或者作品的id
	 */
	private Long id;
	
	/**
	 * 事件或者作品的类型，1事件，2作品
	 */
	private int type=TYPE_EVENT;
	
	/**
	 * 事件或者作品的位置，－1上一事件或作品，0 当前事件或作品，1下一个事件作品
	 */
	private int neighborType=NEIGHBOR_TYPE_CURRENT;
	
	/**
	 * 是否喜欢,1喜欢 0不喜欢
	 */
	private int enjoy=0;
	
	/**
	 * 用户id，已经登录返回userid，没有登录返回null
	 */
	private Long userId;
	/**
	 * 是否对content内容中的HTML标签进行转码（0不转，1转）
	 */
	private int encodeTag = 1;
	
	
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public int getEnjoy() {
		return enjoy;
	}

	public void setEnjoy(int enjoy) {
		this.enjoy = enjoy;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	

	public int getNeighborType() {
		return neighborType;
	}

	public void setNeighborType(int neighborType) {
		this.neighborType = neighborType;
	}

	public void setEncodeTag(int encodeTag) {
		this.encodeTag = encodeTag;
	}

	public int getEncodeTag() {
		return encodeTag;
	}

	
	

}
