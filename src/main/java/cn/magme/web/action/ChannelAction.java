/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.constants.CacheConstants;
import cn.magme.pojo.ChannelAd;
import cn.magme.pojo.ChannelBanner;
import cn.magme.pojo.ChannelEvent;
import cn.magme.pojo.ChannelSubject;
import cn.magme.pojo.ChannelView;
import cn.magme.pojo.FpageEvent;
import cn.magme.pojo.Sort;
import cn.magme.pojo.SysParameter;
import cn.magme.service.ChannelAdService;
import cn.magme.service.ChannelBannerService;
import cn.magme.service.ChannelEventService;
import cn.magme.service.ChannelSubjectService;
import cn.magme.service.ChannelViewService;
import cn.magme.service.FpageEventService;
import cn.magme.service.SortService;
import cn.magme.service.SysParameterService;
import cn.magme.util.StringUtil;

import com.danga.MemCached.MemCachedClient;

/**
 * @author jacky_zhou
 * @date 2012-4-6
 * @version $id$
 */
@SuppressWarnings("serial")
@Results({ @Result(name = "success", location = "/WEB-INF/pages/channel.ftl"),
	@Result(name = "activitysuccess", location = "/WEB-INF/pages/activity/westinActivity.ftl") })
public class ChannelAction extends BaseAction {
	
	private static final Logger log=Logger.getLogger(ChannelAction.class);
	
	private static final String SECOND_INDEX_ACTIVITY_KEY="second_index_activity";
	/** 事件service */
    @Resource
    private FpageEventService fpageEventService;
    
    /** 展示分类service */
    @Resource
    private SortService sortService;
    
    /** 二级分类视图类型service */
    @Resource
    private ChannelViewService channelViewService;
    
    /** 二级首页广告service */
    @Resource
    private ChannelAdService channelAdService;
    
    /** 主题service */
    @Resource
    private ChannelSubjectService channelSubjectService;
    
    /** 二级首页banner的service */
    @Resource
    private ChannelBannerService channelBannerService;
    
    /** 事件与主题关联关系service */
    @Resource
    private ChannelEventService channelEventService;
    
    /** memCache缓存 */
    @Resource
    private MemCachedClient memCachedClient;
    
    /** 系统设置参数service */
    @Resource
    private SysParameterService sysParameterService;
    
    /**
     * 二级首页
     */
    @SuppressWarnings("unchecked")
    public String index(){
    	//memcache缓存已初始化
        if(memCachedClient!=null){
        	this.homeSortList = (List<Sort>) memCachedClient.get(CacheConstants.CHANNEL_VIEW_HOME_SORT);
        	if(homeSortList==null){
        		this.homeSortList=sortService.getListByGroupFromDB("channel");
        		if(this.homeSortList!=null) memCachedClient.set(CacheConstants.CHANNEL_VIEW_HOME_SORT, this.homeSortList);
        	}
            
            if(sortId==null&&homeSortList!=null&&!homeSortList.isEmpty()){
                sortId=homeSortList.get(0).getId();
            }
            
            this.sort = (Sort) memCachedClient.get(CacheConstants.CHANNEL_VIEW_SORT+sortId);
            if(sort==null){
            	this.sort=sortService.getById(sortId);
            	if(sort!=null) memCachedClient.set(CacheConstants.CHANNEL_VIEW_SORT+sortId, this.sort);
            }
            
            this.channelView=(ChannelView)memCachedClient.get(CacheConstants.CHANNEL_VIEW+"_"+sortId);
            if(channelView==null){
                this.channelView=channelViewService.getBySortId(sortId);
                if(channelView!=null) memCachedClient.set(CacheConstants.CHANNEL_VIEW+"_"+sortId, channelView);
            }
            
            //站内
            this.channelBannerList=(List<ChannelBanner>)memCachedClient.get(CacheConstants.CHANNEL_BANNER+"_"+sortId+"_0");
            if(channelBannerList==null){
                this.channelBannerList=channelBannerService.getListBySortId(sortId,0);
                if(channelBannerList!=null) memCachedClient.set(CacheConstants.CHANNEL_BANNER+"_"+sortId+"_0", channelBannerList);
            }
            //站外
            this.channelBannerList2=(List<ChannelBanner>)memCachedClient.get(CacheConstants.CHANNEL_BANNER+"_"+sortId+"_1");
            if(channelBannerList2==null){
                this.channelBannerList2=channelBannerService.getListBySortId(sortId,1);
                if(channelBannerList2!=null) memCachedClient.set(CacheConstants.CHANNEL_BANNER+"_"+sortId+"_1", channelBannerList2);
            }
            
            //主题列表
            this.channelSubjectList=(List<ChannelSubject>)memCachedClient.get(CacheConstants.CHANNEL_SUBJECT+"_"+sortId);
            if(channelSubjectList==null){
                this.channelSubjectList=channelSubjectService.getListBySortId(sortId);
                if(channelSubjectList!=null) memCachedClient.set(CacheConstants.CHANNEL_SUBJECT+"_"+sortId, channelSubjectList);
            }
            //事件集合
            this.channelEventMap=(Map<Long,List<ChannelEvent>>)memCachedClient.get(CacheConstants.CHANNEL_EVENT+"_"+sortId);
            if(this.channelEventMap==null){
                if(this.channelSubjectList!=null){
                    this.channelEventMap=new HashMap<Long,List<ChannelEvent>>();
                    //遍历主题，并取出主题下的所有事件，放入channelEventMap中储存
                    for(ChannelSubject channelSubject:channelSubjectList){
                        if(channelSubject!=null&&channelSubject.getId()!=null){
                            List<ChannelEvent> channelEventList=channelEventService.getListBySubjectId(channelSubject.getId());
                            channelEventMap.put(channelSubject.getId(),channelEventList);
                        }
                    }
                    //放到memCache缓存中
                    if(channelEventMap!=null) memCachedClient.set(CacheConstants.CHANNEL_EVENT+"_"+sortId, channelEventMap);
                }
            }
            //分类广告 二级页面查询广告业务暂时无，暂时注释代码==========2012-07-24====start
            /*this.channelAd=(ChannelAd)memCachedClient.get(CacheConstants.CHANNEL_AD+"_"+sortId);
            if(channelAd==null){
                this.channelAd=channelAdService.getBySortId(sortId);
                if(channelAd!=null) memCachedClient.set(CacheConstants.CHANNEL_AD+"_"+sortId, channelAd);
            }*/
            //分类广告 二级页面查询广告业务暂时无，暂时注释代码==========2012-07-24====start fredy
            //热门文章
            this.dayEventList = (List<FpageEvent>) memCachedClient.get(CacheConstants.CHANNEL_EVENT_RANKING_DAY+"_"+sortId);
            if(dayEventList==null){
            	this.dayEventList=fpageEventService.getPopularFpageEventList(sortId, 1, 0, 5);
            	if(dayEventList!=null) memCachedClient.set(CacheConstants.CHANNEL_EVENT_RANKING_DAY+"_"+sortId,this.dayEventList);
            }
            
            //周排行
            this.weekEventList = (List<FpageEvent>) memCachedClient.get(CacheConstants.CHANNEL_EVENT_RANKING_WEEK+"_"+sortId);
            if(weekEventList==null){
            	this.weekEventList=fpageEventService.getPopularFpageEventList(sortId, 2, 0, 5);
            	if(weekEventList!=null) memCachedClient.set(CacheConstants.CHANNEL_EVENT_RANKING_WEEK+"_"+sortId, this.weekEventList);
            }
            //月排行
            this.monthEventList = (List<FpageEvent>) memCachedClient.get(CacheConstants.CHANNEL_EVENT_RANKING_MONTH+"_"+sortId);
            if(monthEventList==null){
            	 this.monthEventList=fpageEventService.getPopularFpageEventList(sortId, 3, 0, 5);
            	if(monthEventList!=null) memCachedClient.set(CacheConstants.CHANNEL_EVENT_RANKING_MONTH+"_"+sortId, this.monthEventList);
            }
            
            try {
            	//获取活动参数
				SysParameter param=sysParameterService.queryByParamKey(SECOND_INDEX_ACTIVITY_KEY);
				//如果当前分类是活动分类
				if(param!=null && StringUtil.isNotBlank(param.getParamValue()) && this.sortId==Long.parseLong(param.getParamValue())){
					return "activitysuccess";//返回活动页
				}
			} catch (Exception e) {
				log.error("处理活动页面出错", e);
			}
            return "success";
        }else{//memcache未初始化
            return preview();
        }
        
    }
    
    /**
     * 分类首页预览
     * @return
     */
    public String preview(){
        this.homeSortList=sortService.getListByGroupFromDB("channel");
        if(sortId==null&&homeSortList!=null&&!homeSortList.isEmpty()){
            sortId=homeSortList.get(0).getId();
        }
        this.sort=sortService.getById(sortId);
        
        this.channelView=channelViewService.getBySortId(sortId);
        this.channelBannerList=channelBannerService.getListBySortId(sortId,0);
        this.channelBannerList2=channelBannerService.getListBySortId(sortId,1);
        this.channelSubjectList=channelSubjectService.getListBySortId(sortId);//取出所有主题
        if(this.channelSubjectList!=null){
            this.channelEventMap=new HashMap<Long,List<ChannelEvent>>();
            //遍历主题，并取出主题下的所有事件，放入channelEventMap中储存
            for(ChannelSubject channelSubject:channelSubjectList){
                if(channelSubject!=null&&channelSubject.getId()!=null){
                    List<ChannelEvent> channelEventList=channelEventService.getListBySubjectId(channelSubject.getId());
                    channelEventMap.put(channelSubject.getId(),channelEventList);
                }
            }
        }
        //分类广告 二级页面查询广告业务暂时无，暂时注释代码==========2012-07-24====start
        //this.channelAd=channelAdService.getBySortId(sortId);
        //end
        
        this.dayEventList=fpageEventService.getPopularFpageEventList(sortId, 1, 0, 5);
        this.weekEventList=fpageEventService.getPopularFpageEventList(sortId, 2, 0, 5);
        this.monthEventList=fpageEventService.getPopularFpageEventList(sortId, 3, 0, 5);
        try {
			SysParameter param=sysParameterService.queryByParamKey(SECOND_INDEX_ACTIVITY_KEY);
			if(param!=null && StringUtil.isNotBlank(param.getParamValue()) && this.sortId==Long.parseLong(param.getParamValue())){
				return "activitysuccess";
			}
		} catch (Exception e) {
			log.error("处理活动页面出错", e);
		}
        
        return "success";
    }
    
    /** 分类id */
    private Long sortId;
    /** 二级分类列表 */
    private List<Sort> homeSortList;
    /** 二级分类的视图类型（mode=1 图片， mode=2 文字） */
    private ChannelView channelView;
    /** 站内广告列表 */
    private List<ChannelBanner> channelBannerList;
    /** 站外广告列表 */
    private List<ChannelBanner> channelBannerList2;
    /** 分类时间map<br/> key: 主题id <br/> value: 对应主题下的所有事件 */
    private Map<Long,List<ChannelEvent>> channelEventMap;
    /** 当前分类下的所有主题 */
    private List<ChannelSubject> channelSubjectList;
    /** 分类广告，content字段的内容为广告的html代码 */
    private ChannelAd channelAd;
    /** 热门文章 */
    private List<FpageEvent> dayEventList;
    /** 周排行 */
    private List<FpageEvent> weekEventList;
    /** 月排行 */
    private List<FpageEvent> monthEventList;
    /** 分类 */
    private Sort sort;

    public Long getSortId() {
        return sortId;
    }

    public void setSortId(Long sortId) {
        this.sortId = sortId;
    }

    public ChannelView getChannelView() {
        return channelView;
    }

    public void setChannelView(ChannelView channelView) {
        this.channelView = channelView;
    }

    public List<ChannelBanner> getChannelBannerList() {
        return channelBannerList;
    }

    public void setChannelBannerList(List<ChannelBanner> channelBannerList) {
        this.channelBannerList = channelBannerList;
    }

    public Map<Long, List<ChannelEvent>> getChannelEventMap() {
        return channelEventMap;
    }

    public void setChannelEventMap(Map<Long, List<ChannelEvent>> channelEventMap) {
        this.channelEventMap = channelEventMap;
    }

    public List<ChannelSubject> getChannelSubjectList() {
        return channelSubjectList;
    }

    public void setChannelSubjectList(List<ChannelSubject> channelSubjectList) {
        this.channelSubjectList = channelSubjectList;
    }

    public ChannelAd getChannelAd() {
        return channelAd;
    }

    public void setChannelAd(ChannelAd channelAd) {
        this.channelAd = channelAd;
    }

    public List<FpageEvent> getDayEventList() {
        return dayEventList;
    }

    public void setDayEventList(List<FpageEvent> dayEventList) {
        this.dayEventList = dayEventList;
    }

    public List<FpageEvent> getWeekEventList() {
        return weekEventList;
    }

    public void setWeekEventList(List<FpageEvent> weekEventList) {
        this.weekEventList = weekEventList;
    }

    public List<FpageEvent> getMonthEventList() {
        return monthEventList;
    }

    public void setMonthEventList(List<FpageEvent> monthEventList) {
        this.monthEventList = monthEventList;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public List<Sort> getHomeSortList() {
        return homeSortList;
    }

    public void setHomeSortList(List<Sort> homeSortList) {
        this.homeSortList = homeSortList;
    }

	public List<ChannelBanner> getChannelBannerList2() {
		return channelBannerList2;
	}

	public void setChannelBannerList2(List<ChannelBanner> channelBannerList2) {
		this.channelBannerList2 = channelBannerList2;
	}
    
}
