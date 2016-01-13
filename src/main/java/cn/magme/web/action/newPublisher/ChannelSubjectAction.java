/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.newPublisher;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.pojo.ChannelSubject;
import cn.magme.service.ChannelSubjectService;

/**
 * @author jacky_zhou
 * @date 2012-4-1
 * @version $id$
 */
@SuppressWarnings("serial")
@Results({@Result(name="channel",location="/WEB-INF/pages/newPublisher/channel.ftl")})
public class ChannelSubjectAction extends PublisherBaseAction {
    
    @Resource
    private ChannelSubjectService channelSubjectService;

    public String saveJson(){
        this.jsonResult=channelSubjectService.save(channelSubject);
        return "json";
    }
    
    public String editJson(){
        this.channelSubject=channelSubjectService.getById(id);;
        this.generateJsonResult(JsonResult.CODE.SUCCESS, 
                JsonResult.MESSAGE.SUCCESS, "channelSubject", channelSubject);
        return JSON;
    }
    
    public String deleteJson(){
        this.jsonResult=channelSubjectService.deleteById(id);
        return JSON;
    }
    
    public String getListJson(){
        this.channelSubjectList=channelSubjectService.getListBySortId(sortId);
        this.generateJsonResult(JsonResult.CODE.SUCCESS, 
                JsonResult.MESSAGE.SUCCESS, "channelSubjectList", channelSubjectList);        
        return JSON;
    }
    
    private Long id;
    private Long sortId;
    private ChannelSubject channelSubject;
    private List<ChannelSubject> channelSubjectList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChannelSubject getChannelSubject() {
        return channelSubject;
    }

    public void setChannelSubject(ChannelSubject channelSubject) {
        this.channelSubject = channelSubject;
    }

    public List<ChannelSubject> getChannelSubjectList() {
        return channelSubjectList;
    }

    public void setChannelSubjectList(List<ChannelSubject> channelSubjectList) {
        this.channelSubjectList = channelSubjectList;
    }

    public Long getSortId() {
        return sortId;
    }

    public void setSortId(Long sortId) {
        this.sortId = sortId;
    }
    
}