/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.newPublisher;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.danga.MemCached.MemCachedClient;

import cn.magme.common.JsonResult;
import cn.magme.constants.CacheConstants;
import cn.magme.pojo.ChannelAd;
import cn.magme.pojo.ChannelBanner;
import cn.magme.pojo.ChannelSubject;
import cn.magme.pojo.ChannelView;
import cn.magme.pojo.Sort;
import cn.magme.service.ChannelAdService;
import cn.magme.service.ChannelBannerService;
import cn.magme.service.ChannelSubjectService;
import cn.magme.service.ChannelViewService;
import cn.magme.service.SortService;

/**
 * @author jacky_zhou
 * @date 2012-4-1
 * @version $id$
 */
@SuppressWarnings("serial")
@Results({@Result(name="channel",location="/WEB-INF/pages/newPublisher/channel.ftl")})
public class ChannelAction extends PublisherBaseAction {
    
    private static final String GROUP="channel";
    
    @Resource
    private SortService sortService;
    @Resource
    private ChannelViewService channelViewService;
    @Resource
    private ChannelAdService channelAdService;
    @Resource
    private ChannelSubjectService channelSubjectService;
    @Resource
    private ChannelBannerService channelBannerService;
    
    @Resource
    private MemCachedClient memCachedClient;
   
    
    public String to(){
        this.sortList=sortService.getListByGroupFromDB(GROUP);
        this.channelView=channelViewService.getBySortId(sortId);
        this.channelBannerList=channelBannerService.getListBySortId(sortId);
        this.channelSubjectList=channelSubjectService.getListBySortId(sortId);
        this.channelAd=channelAdService.getBySortId(sortId);
        return "channel";
    }
    
    public String release(){
        if(memCachedClient!=null){
            Object obj=null;
            String[] keys=new String[]{CacheConstants.CHANNEL_AD+"_"+sortId,
                    CacheConstants.CHANNEL_BANNER+"_"+sortId+"_0",
                    CacheConstants.CHANNEL_BANNER+"_"+sortId+"_1",
                    CacheConstants.CHANNEL_EVENT+"_"+sortId,
                    CacheConstants.CHANNEL_SUBJECT+"_"+sortId,
                    CacheConstants.CHANNEL_VIEW+"_"+sortId};
            for(String key:keys){
                obj=memCachedClient.get(key);
                if(obj!=null) memCachedClient.delete(key);
            }
        }
        this.generateJsonResult(JsonResult.CODE.SUCCESS, JsonResult.MESSAGE.SUCCESS);
        return JSON;
    }
    
    private List<Sort> sortList;
    private Long sortId;
    private ChannelView channelView;
    private ChannelAd channelAd;
    private List<ChannelSubject> channelSubjectList;
    private List<ChannelBanner> channelBannerList;
    private Integer type;

    public ChannelAdService getChannelAdService() {
        return channelAdService;
    }
    public void setChannelAdService(ChannelAdService channelAdService) {
        this.channelAdService = channelAdService;
    }
    public List<Sort> getSortList() {
        return sortList;
    }
    public void setSortList(List<Sort> sortList) {
        this.sortList = sortList;
    }
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
    public ChannelAd getChannelAd() {
        return channelAd;
    }
    public void setChannelAd(ChannelAd channelAd) {
        this.channelAd = channelAd;
    }
    public List<ChannelSubject> getChannelSubjectList() {
        return channelSubjectList;
    }
    public void setChannelSubjectList(List<ChannelSubject> channelSubjectList) {
        this.channelSubjectList = channelSubjectList;
    }
    public List<ChannelBanner> getChannelBannerList() {
        return channelBannerList;
    }
    public void setChannelBannerList(List<ChannelBanner> channelBannerList) {
        this.channelBannerList = channelBannerList;
    }

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
