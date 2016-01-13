/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.newPublisher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.pojo.ChannelEvent;
import cn.magme.pojo.FpageEvent;
import cn.magme.service.ChannelEventService;
import cn.magme.service.FpageEventService;

/**
 * @author jacky_zhou
 * @date 2012-4-1
 * @version $id$
 */
@SuppressWarnings("serial")
@Results({@Result(name="channel",location="/WEB-INF/pages/newPublisher/channel.ftl")})
public class ChannelEventAction extends PublisherBaseAction {
    
    @Resource
    private ChannelEventService channelEventService;
    
    @Resource
    private FpageEventService fpageEventService;    

    public String getListJson(){
        this.channelEventList=channelEventService.getListBySubjectId(channelSubjectId);
        this.generateJsonResult(JsonResult.CODE.SUCCESS, 
                JsonResult.MESSAGE.SUCCESS, "channelEventList", channelEventList); 
        return JSON;
    }
    
    public String saveJson(){
        this.jsonResult=channelEventService.save(channelSubjectId, eventIds);
        return JSON;
    }
    
    public String updateJson(){
       this.jsonResult=channelEventService.update(channelEvent);
        return JSON;
    }
    
    public String deleteJson(){
        this.jsonResult=channelEventService.deleteById(channelEventId);
        return JSON;
    }
    
    /**
     * 事件查询的集合   ---> 改成 给成分页模式
     * @return
     */
    public String getEventListJson(){
        HttpServletRequest req=ServletActionContext.getRequest();
        Map<String,Object> param=new HashMap<String,Object>();
        Map<String,Object> evenPage = new HashMap<String, Object>();//返回的map
        
        param.put("id", req.getParameter("id"));
        param.put("subjectId", req.getParameter("subjectId"));
        param.put("title", req.getParameter("title"));
        param.put("createdTimeStart", req.getParameter("createdTimeStart"));
        param.put("createdTimeEnd", req.getParameter("createdTimeEnd"));
        try{
        	pageNo = Long.parseLong(req.getParameter("pageNo").toString());
        }catch (Exception e) {
        	pageNo=0;
        }
        try{
        	currentPage = Long.parseLong(req.getParameter("currentPage").toString());
        }catch (Exception e) {
        	currentPage=1;
		}
        //计算总页数数 ---begin
        if(pageNo<=0){//重新计算总页数，并进行数据库查询
        	long listCount =fpageEventService.getgetFpageEventListByParamCount(param);
        	pageNo = (listCount+30-1)/30;
        	evenPage.put("pageNo", pageNo);
        	evenPage.put("currentPage", currentPage);
        }else{
        	evenPage.put("pageNo", pageNo);
        	evenPage.put("currentPage", currentPage);
        }
       //计算总页数数 ---begin
        
        param.put("begin", req.getParameter("begin")!=null?req.getParameter("begin"):30*(currentPage-1));
        param.put("size", req.getParameter("size")!=null?req.getParameter("size"):30);//每页30条记录
        //每页显示的集合
        this.eventList=fpageEventService.getFpageEventListByParam(param);
        
        evenPage.put("eventList", eventList);
     
        this.generateJsonResult(JsonResult.CODE.SUCCESS, 
                JsonResult.MESSAGE.SUCCESS, "evenPage", evenPage); 
        return JSON;
    }
    private List<ChannelEvent> channelEventList;
    private List<FpageEvent> eventList;
    private Long channelEventId;
    private Long channelSubjectId;
    private String eventIds;
    private ChannelEvent channelEvent;
    private long currentPage;
    private long pageNo;

    public List<ChannelEvent> getChannelEventList() {
        return channelEventList;
    }
    public void setChannelEventList(List<ChannelEvent> channelEventList) {
        this.channelEventList = channelEventList;
    }
    public Long getChannelEventId() {
        return channelEventId;
    }
    public void setChannelEventId(Long channelEventId) {
        this.channelEventId = channelEventId;
    }
    public Long getChannelSubjectId() {
        return channelSubjectId;
    }
    public void setChannelSubjectId(Long channelSubjectId) {
        this.channelSubjectId = channelSubjectId;
    }
    public ChannelEvent getChannelEvent() {
        return channelEvent;
    }
    public void setChannelEvent(ChannelEvent channelEvent) {
        this.channelEvent = channelEvent;
    }

    public List<FpageEvent> getEventList() {
        return eventList;
    }

    public void setEventList(List<FpageEvent> eventList) {
        this.eventList = eventList;
    }

    public String getEventIds() {
        return eventIds;
    }

    public void setEventIds(String eventIds) {
        this.eventIds = eventIds;
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

    
    
}
