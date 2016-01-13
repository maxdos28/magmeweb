/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.newPublisher;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.pojo.ChannelView;
import cn.magme.service.ChannelViewService;

/**
 * @author jacky_zhou
 * @date 2012-4-1
 * @version $id$
 */
@SuppressWarnings("serial")
@Results({@Result(name="channel",location="/WEB-INF/pages/newPublisher/channel.ftl")})
public class ChannelViewAction extends PublisherBaseAction {
    
    @Resource
    private ChannelViewService channelViewService;

    public String saveJson(){
        this.jsonResult=channelViewService.save(channelView);
        return JSON;
    }
    
    private ChannelView channelView;

    public ChannelView getChannelView() {
        return channelView;
    }

    public void setChannelView(ChannelView channelView) {
        this.channelView = channelView;
    }
}
