/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant.SORT;
import cn.magme.dao.SortDao;
import cn.magme.pojo.ActivityAlbum;
import cn.magme.pojo.Admin;
import cn.magme.pojo.AdvertiseVideo;
import cn.magme.pojo.ChannelBanner;
import cn.magme.pojo.CreativeCategory;
import cn.magme.pojo.FpageEventDto;
import cn.magme.pojo.Issue;
import cn.magme.pojo.Link;
import cn.magme.pojo.PageD;
import cn.magme.pojo.Publication;
import cn.magme.pojo.Sort;
import cn.magme.pojo.Tag;
import cn.magme.pojo.look.LooItem;
import cn.magme.pojo.sns.Creative;
import cn.magme.pojo.sns.CreativeRanking;
import cn.magme.result.sns.IndexSidebarEvent;
import cn.magme.result.sns.PublicResult;
import cn.magme.service.ActivityAlbumService;
import cn.magme.service.AdvertiseVideoService;
import cn.magme.service.ChannelBannerService;
import cn.magme.service.DetailPageService;
import cn.magme.service.FpageEventService;
import cn.magme.service.HomePageItemService;
import cn.magme.service.IssueService;
import cn.magme.service.LinkService;
import cn.magme.service.PageDService;
import cn.magme.service.PublicationService;
import cn.magme.service.SortService;
import cn.magme.service.TagService;
import cn.magme.service.UserSettingsService;
import cn.magme.service.look.LookHomePageService;
import cn.magme.service.sns.CreativeCategoryService;
import cn.magme.service.sns.CreativeRankingService;
import cn.magme.service.sns.SnsPublicService;
import cn.magme.util.StringUtil;

/**
 * 首页<br/>
 * 提供首页页面显示所需信息及对 Ajax 请求的响应
 * @author jacky_zhou
 * @date 2011-5-27
 * @version $id$
 */
@SuppressWarnings("serial")
@Results({ @Result(name = "success", location = "/WEB-INF/pages/index.ftl") })
public class IndexAction extends BaseAction {

	/** 用户定制service<br/>[ioc] */
	@Resource
	private UserSettingsService userSettingsService;
	/** 首页元素service<br/>[ioc] */
	@Resource
	private HomePageItemService homePageItemService;
	/** 事件service<br/>[ioc] */
    @Resource
    private FpageEventService fpageEventService;
    /** 展示分类service<br/>[ioc] */
    @Resource
    private SortService sortService;

    /** 展示分类dao<br/>[ioc] */
    @Resource
    private SortDao sortDao;
    /** 杂志service<br/>[ioc] */
    @Resource
    private PublicationService pubService;
    /** 标签service<br/>[ioc] */
    @Resource
    private TagService tagService;
    
    @Resource
    private ChannelBannerService channelBannerService;
    
    @Resource
    private IssueService issueService;
    
    @Resource
	private SnsPublicService snsPublicService;
    
    @Resource
	private DetailPageService detailPageService;
    
    @Resource
    private AdvertiseVideoService advertiseVideoService;
    
    @Resource
    private LinkService linkService;
    
    @Resource
    private ActivityAlbumService activityAlbumService;
    
    @Resource
    private PageDService pageDService;
    
    @Resource
    private CreativeCategoryService creativeCategoryService;
    
    @Resource
    private CreativeRankingService creativeRankingService;
    @Resource
    private LookHomePageService lookHomePageService;
    
    /** 展示分类id */
    private Long sortId;
    
    /**
     * 二级分类
     */
    private Long secondSortId;
    
    /** 二级标签id */
    private Long tagId;
    /** 杂志id */
    private Long publicationId;
    /** 
     * 设备类型<br/>
     * computer：pc端 <br/>
     * mobile：手机端
     */
    private String client;
    /** 分页查询（首页事件）的起始位置 */
    private Integer begin;
    /** 分页查询（首页事件）的长度 */
    private Integer size;
    /** 首页事件列表*/
    private List<FpageEventDto> eventList;
    /** 一级标签列表*/
    private List<Sort> homeSortList;
    /** 未定制的一级标签列表*/
    private List<Sort> selectableSortList;
    /** <b>不知道做什么用的</b> */
    private String filterName;
    /** 首页元素列表*/ 
    //页面改变 不需要
    //private List<HomePageItem> itemList;
    /** 侧边栏事件  */
    private List<IndexSidebarEvent> sidebarEvent;
    
    /** top10 标签 */
    private List<Tag> tags;
    
    private List<PageD> recommendHot;
    
    private boolean homePage = true;
    
    /**
     * 新版首页一级和二级分类的集合
     */
    private List<CreativeCategory> creativeCategoryList;
    
    private CreativeCategory seoCreativeCategoryIndex;
    
    /**
     * 本周阅读榜排行
     */
    private List<CreativeRanking> rankingList;
    
    /**
     * 编辑推荐排行
     */
    private List<CreativeRanking> rankingByEditList;
    
    
    /**
     * 作品集合
     */
    private List<Creative> creativeList;
    
    /**
     * 专辑
     */
    private List<ChannelBanner> bannerIndexList;
    
    /**
     * 推荐杂志
     */
    private List<Publication> publicationList;
    
    /**
     * 新刊上线
     */
    private List<Issue> issueNewList;
    private List<PublicResult> publicCreative;
    
    private List<AdvertiseVideo> adVideo;
    
    private Link footerLink;
    
    private List<ActivityAlbum> activityAlbumList;
    

	private String sortIds;
	private String tagName;
	
	private Map hotMap;
	private String sortName;
	private String sortType;
	private static final Integer ITEM_LOAD_NUM = 30;
	
	private List<LooItem> looItemList;
	private List<Map> looArticleList;

    /**
     * 跳转到麦米网首页<br/>
     * 初始化首页信息(事件列表、二级首页列表)<br/>
     * 若有缓存数据可用则 <b>使用缓存</b> 以提高访问效率<br/>
     * 若无缓存，则直接查询数据库，并将查询的结果放入memCache做为缓存备用
     */
    public String execute() {
        HttpServletResponse response = ServletActionContext.getResponse();
        //response.setHeader("Cache-Control", "max-age=" + systemProp.getPageCacheTimeout());
        
        process(true, 0, ITEM_LOAD_NUM);
        //bannerIndexList = channelBannerService.getListByIndexKey();//专辑
        //首页右侧活动专辑
      	activityAlbumList = activityAlbumService.queryActivityAlbumByIndex();
        //publicationList = publicationService.queryRecommendIndex();//推荐杂志 由百度广告管理平台管理
        issueNewList =issueService.queryLastestIssuesWithCategory(0, 5);//新刊上线 
        //detailPageService.selHotEventWithComment(3,6);
//		showUserSettings();
      
        //adVideo = advertiseVideoService.queryByIndexVideo();//首页视频广告
        //最新评论
        //hotMap = detailPageService.selHotEventWithComment(3, 2);
		
		//右侧M1推荐作品
//		publicCreative=snsPublicService.getPubCreative();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("isHot", 1);
        map.put("begin",0);
        map.put("size", 6);
        map.put("order_desc", null);
        //需求不需要改功能在首页显示
//        recommendHot = pageDService.queryByConditionCached(map);
//        if(recommendHot!=null && recommendHot.size()>0){
//        	for (PageD d : recommendHot) {
//				String htmlText=StringUtil.HtmlText(d.getDescription());
//				if(htmlText.length()>20)
//					d.setDescription(htmlText.substring(0,20));
//			}
//        }
        //creativeCategoryList = creativeCategoryService.queryCategoryTree();
        //改为查询LOO客栏目
        this.looItemList = this.lookHomePageService.looItemList();
		//首页的页脚友情链接
		footerLink = linkService.getLinkFooterMemCached();
		
		//首页的排行
		Map<String, Object> rankingMap = new HashMap<String, Object>();
		rankingMap.put("rankingType", "2");//1:编辑推荐;2:本周阅读榜
		rankingMap.put("creativeCategoryId", sortId);
		rankingList = creativeRankingService.queryCreativeRankingList(rankingMap, true);
		rankingMap.put("rankingType", "1");//1:编辑推荐;2:本周阅读榜
		rankingByEditList = creativeRankingService.queryCreativeRankingList(rankingMap, true);
		
		//Seo专用seoCreativeCategoryIndex对象；主要3个属性seoTitle、seoDescription、seoKeyWord====begin
//		if(sortId!=null){
//			if(secondSortId!=null){
//				boolean tmpFor = false;
//				for (CreativeCategory cc : creativeCategoryList) {
//					for (CreativeCategory ccl : cc.getChildCreativeList()) {
//						if(secondSortId.compareTo(ccl.getId())==0 ){
//							seoCreativeCategoryIndex =ccl;
//							tmpFor=true;
//							break;
//						}
//					}
//					if(tmpFor) break;
//				}
//			}else{
//				for (CreativeCategory cc : creativeCategoryList) {
//					if(sortId.compareTo(cc.getId())==0){
//						seoCreativeCategoryIndex=cc;
//						break;
//					}
//				}
//			}
//			
//		}
		//Seo专用seoCreativeCategoryIndex对象；主要3个属性seoTitle、seoDescription、seoKeyWord====end
        return "success";
    }

    /**
     * 查询用户定制的一级标签列表<br/>
     * 用户登录则显示用户定制的列表，否则显示默认列表
     */
	public String showUserSettings() {
//		List<Sort> allHomeSortList = sortDao.getLstWithLatestEvent(PojoConstant.SORT.NEW_SORT_TYPE);
//		User user = this.getSessionUser();
//		if(user != null){
//			UserSettings userSettings = userSettingsService.getByUserId(user.getId());
//			homeSortList = userSettings.getSorts();
//		} else {
//			List<Sort> userSorts = new LinkedList<Sort>();
//			if(sortIds != null && !"".equals(sortIds)){//有默认数据cookie
//				Long[] arr = strArrToLongArr(sortIds.split("_"));
//				for (Long id : arr) {
//					for (Sort sort : allHomeSortList) {
//						if(sort.getId().equals(id)){
//							userSorts.add(sort);
//							break;
//						}
//					}
//				}
//			} else {
//				for (Sort sort : allHomeSortList) 
//					if(sort.getIsDefault().equals(PojoConstant.SORT.IS_DEFAULT_DEFAULT))
//						userSorts.add(sort);
//			}
//			homeSortList = userSorts;
//		}
//		selectableSortList = new ArrayList<Sort>();
//		for (Sort sort : allHomeSortList) {
//			boolean found = false;
//			for (Sort sort2 : homeSortList) {
//				if(sort2.getId().equals(sort.getId())){
//					found = true;
//					break;
//				}
//			}
//			if(!found)
//				selectableSortList.add(sort);
//		}
//		
//		this.jsonResult = JsonResult.getSuccess();
//		this.jsonResult.put("allNav", creativeCategoryList);
		return JSON;
	}
	
	/**
	 * 定制一级标签
	 * @return
	 */
//	public String saveUserSettings(){
//		User user = this.getSessionUser();
//		if(user != null){
//			Long userId = user.getId();
//			UserSettings userSettings = userSettingsService.getByUserId(userId);
//			userSettings.setSortIds(sortIds);
//			this.jsonResult = userSettingsService.update(userSettings);
////			//成功
////			if(this.jsonResult.getCode() == JsonResult.CODE.SUCCESS){
////				UserSettings settings = userSettingsService.getByUserId(userId);
////				List<Sort> sorts = settings.getSorts();
////				if(sortId != null){//用户的当前展示页面是某个一级标签页
////					boolean found = false;
////					for (Sort sort : sorts) {
////						if(sort.getId().equals(sortId)){
////							found = true;
////							break;
////						}
////					}
////					if(!found){//把当前显示的一级标签删掉了
////					}
////				}
////				if(sorts.size() > 0)
////	        	tags = tagService.getTop10TagListBySortName(sorts.get(0).getName());
////				this.jsonResult.put("tags", tags);
////				this.jsonResult.put("myNav", sorts);
////			}
//		} else {
//			this.jsonResult = JsonResult.getFailure();
//		}
//		return JSON;
//	}
    
	private void process(boolean useCache, Integer begin, Integer size ) {
//        Sort sort = null;
//        if (sortId != null && sortId != 0){
//        	sort = sortService.getById(sortId);
//        	if(sort != null){
//        		sortName = sort.getName();
//        		//旅游A | 时尚B | IT  C | 娱乐D | 汽车E | 财经F | 育儿G | 
//        		//43	IT | 44	育儿 | 45	财经 | 46	汽车 | 47	旅游 | 48	娱乐 | 49	时尚 |
//        		sortType = String.valueOf("CGFEADB".charAt((int) (sort.getId() - 43)));
//        	}
//	    }
//        //二级标签列表
//        if(begin == 0){//确定了新的查询条件
//        	tags = tagService.getTop10TagListBySortName(sortName);
//        	
//        	//TODO 侧边栏事件显示加载
//    		Map<String, Object> param = new HashMap<String, Object>();
//    		param.put("sortName", sortName);
//    		sidebarEvent = homePageItemService.getIndexSidebarEvent(param);
//        }else {
//        	//首页滚动无限下拉查询，不用查找二级标签列表
//        }
//        if(tagId != null && tagId != 0){
//        	Tag tag = tagService.getById(getTagId());
//        	if(tag != null)
//        		tagName = tag.getName();
//        }

//        long time = System.currentTimeMillis();
//		if(sortName == null && tagName != null){//在没有选择一级标签时，将二级标签当做一级标签处理
//	    	itemList = homePageItemService.getHomePageItemList(tagName, null, useCache, begin, size);
//	    } else itemList = homePageItemService.getHomePageItemList(sortName, tagName, useCache, begin, size);
		//creativeList = homePageItemService.creativeListByIndex(sortId, secondSortId, begin, size, true);
		//改为查询LOO客文章
		this.looArticleList = this.lookHomePageService.looArticleListByIndex(sortId, secondSortId, begin, size, true);
//		System.out.println("process time=" + (System.currentTimeMillis() - time));
//		time = System.currentTimeMillis();
	
	}
    /**
     * 预览首页<br/>
     * 直接读取DB内容，<b>不使用缓存数据</b>
     * @return
     */
    public String preview() {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setHeader("Cache-Control", "no-cache");

        process(false, 0, ITEM_LOAD_NUM);
        homeSortList = sortService.getListByGroupFromDB(SORT.NEW_SORT_TYPE);
        
        //右侧M1推荐作品   20121015 剔除掉
//        publicCreative=snsPublicService.getPubCreative();
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("isHot", 1);
        map.put("begin",0);
        map.put("size", 6);
        map.put("order_desc", 0);
        recommendHot = pageDService.queryByConditionCached(map);
        if(recommendHot!=null && recommendHot.size()>0){
        	for (PageD d : recommendHot) {
				String htmlText=StringUtil.HtmlText(d.getDescription());
				if(htmlText.length()>20)
					d.setDescription(htmlText.substring(0,20));
			}
        }
        
        return "success";
    }

    /**
     * 正式发布首页<br/>
     * 清除memCache缓存，5秒后，显示首页，为memCache填充新的缓存
     * @return
     * @throws InterruptedException
     */
    public String publish() throws InterruptedException {
    	//如果不是编辑用户，则返回error
		Admin admin = this.getSessionAdmin();
		if(admin == null) {
	        HttpServletResponse response = ServletActionContext.getResponse();
	        try {
				response.getWriter().print("error");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setHeader("Cache-Control", "no-cache");
        fpageEventService.clearMemCached();
        Thread.sleep(5000);
        
        //右侧M1推荐作品
  		//publicCreative=snsPublicService.getPubCreative();
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("isHot", 1);
        map.put("begin",0);
        map.put("size", 6);
        map.put("order_desc", 0);
        recommendHot = pageDService.queryByConditionCached(map);
        if(recommendHot!=null && recommendHot.size()>0){
        	for (PageD d : recommendHot) {
				String htmlText=StringUtil.HtmlText(d.getDescription());
				if(htmlText.length()>20)
					d.setDescription(htmlText.substring(0,20));
			}
        }
  		
        return this.execute();
    }

    /**
     * 响应ajax请求，分页查询事件列表<br/>
     * <b>使用缓存</b>
     * @return jsonResult
     */
    public String eventAjax() {
    	//默认失败
        jsonResult = JsonResult.getFailure();
        try{
	        process(true, begin, size);
	        //成功返回
	        jsonResult = JsonResult.getSuccess();
	        jsonResult.put("looArticleList", looArticleList);
        }catch(Exception e){
        }
        return JSON;
    }

    /**
     * 响应预览时的ajax请求，分页查询事件列表<br/>
     * <b>不使用缓存</b>
     * @return jsonResult
     */
    public String eventAjaxPreview() {
    	//默认失败
        jsonResult = JsonResult.getFailure();
        try{
	        process(false, begin, size);
	        //成功返回
	        jsonResult = JsonResult.getSuccess();
	        jsonResult.put("looArticleList", looArticleList);
        }catch(Exception e){
        }
        return JSON;
    }  
    /**
     * 获取首页用于填补空洞的尺寸为200*200的事件列表
     * @deprecated 改版后，不存在这种空洞了
     * @since 2012-06-28
     * @return
     */
    public String eventAjaxPatch(){
        jsonResult = new JsonResult();
        //默认失败
        jsonResult.setCode(JsonResult.CODE.FAILURE);
        jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
        if (StringUtil.isBlank(client)) {
            client = SORT.GROUP_COMPUTER;
        }
        //分页查询所有长宽均为200的事件列表，不使用缓存
        //TODO 查出来的结果可能与execute方法中查出的结果集元素重复，需要处理
        eventList = fpageEventService.getHomePatchList(sortId, publicationId, client, false, 0, 50);
        jsonResult.put("eventList", eventList);
        jsonResult.setCode(JsonResult.CODE.SUCCESS);
        jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
    	return JSON;
    }
    
    public List<FpageEventDto> getEventList() {
        return eventList;
    }

    public void setEventList(List<FpageEventDto> eventList) {
        this.eventList = eventList;
    }

    public Long getSortId() {
        return sortId;
    }

    public void setSortId(Long sortId) {
        this.sortId = sortId;
    }

    public Long getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(Long publicationId) {
        this.publicationId = publicationId;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public List<Sort> getHomeSortList() {
        return homeSortList;
    }

    public void setHomeSortList(List<Sort> homeSortList) {
        this.homeSortList = homeSortList;
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

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	public List<Tag> getTags() {
		return tags;
	}
	public void setSelectableSortList(List<Sort> selectableSortList) {
		this.selectableSortList = selectableSortList;
	}
	public List<Sort> getSelectableSortList() {
		return selectableSortList;
	}

	public List<ChannelBanner> getBannerIndexList() {
		return bannerIndexList;
	}

	public void setBannerIndexList(List<ChannelBanner> bannerIndexList) {
		this.bannerIndexList = bannerIndexList;
	}

	public List<Publication> getPublicationList() {
		return publicationList;
	}

	public void setPublicationList(List<Publication> publicationList) {
		this.publicationList = publicationList;
	}
	
	public List<Issue> getIssueNewList() {
		return issueNewList;
	}

	public void setIssueNewList(List<Issue> issueNewList) {
		this.issueNewList = issueNewList;
	}

	public void setSortIds(String sortIds) {
		this.sortIds = sortIds;
	}

	public String getSortIds() {
		return sortIds;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getTagName() {
		return tagName;
	}
	public void setSortName(String sortName) {
		this.sortName = sortName;
	}
	
	public String getSortName() {
		return sortName;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	public Long getTagId() {
		return tagId;
	}
	
	public List<PublicResult> getPublicCreative() {
		return publicCreative;
	}

	public void setHomePage(boolean homePage) {
		this.homePage = homePage;
	}

	public boolean isHomePage() {
		return homePage;
	}

	public Map getHotMap() {
		return hotMap;
	}

	public void setHotMap(Map hotMap) {
		this.hotMap = hotMap;
	}

	public List<AdvertiseVideo> getAdVideo() {
		return adVideo;
	}

	public void setAdVideo(List<AdvertiseVideo> adVideo) {
		this.adVideo = adVideo;
	}

	public Link getFooterLink() {
		return footerLink;
	}

	public void setFooterLink(Link footerLink) {
		this.footerLink = footerLink;
	}

	public List<ActivityAlbum> getActivityAlbumList() {
		return activityAlbumList;
	}

	public void setActivityAlbumList(List<ActivityAlbum> activityAlbumList) {
		this.activityAlbumList = activityAlbumList;
	}

	public List<PageD> getRecommendHot() {
		return recommendHot;
	}

	public List<IndexSidebarEvent> getSidebarEvent() {
		return sidebarEvent;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public String getSortType() {
		return sortType;
	}

	public List<CreativeCategory> getCreativeCategoryList() {
		return creativeCategoryList;
	}

	public void setCreativeCategoryList(List<CreativeCategory> creativeCategoryList) {
		this.creativeCategoryList = creativeCategoryList;
	}

	public List<Creative> getCreativeList() {
		return creativeList;
	}

	public void setCreativeList(List<Creative> creativeList) {
		this.creativeList = creativeList;
	}

	public Long getSecondSortId() {
		return secondSortId;
	}

	public void setSecondSortId(Long secondSortId) {
		this.secondSortId = secondSortId;
	}

	public List<CreativeRanking> getRankingList() {
		return rankingList;
	}

	public void setRankingList(List<CreativeRanking> rankingList) {
		this.rankingList = rankingList;
	}

	public List<CreativeRanking> getRankingByEditList() {
		return rankingByEditList;
	}

	public void setRankingByEditList(List<CreativeRanking> rankingByEditList) {
		this.rankingByEditList = rankingByEditList;
	}

	public CreativeCategory getSeoCreativeCategoryIndex() {
		return seoCreativeCategoryIndex;
	}

	public void setSeoCreativeCategoryIndex(CreativeCategory seoCreativeCategoryIndex) {
		this.seoCreativeCategoryIndex = seoCreativeCategoryIndex;
	}

	public List<LooItem> getLooItemList() {
		return looItemList;
	}

	public void setLooItemList(List<LooItem> looItemList) {
		this.looItemList = looItemList;
	}

	public List<Map> getLooArticleList() {
		return looArticleList;
	}

	public void setLooArticleList(List<Map> looArticleList) {
		this.looArticleList = looArticleList;
	}
	
	
	
}
