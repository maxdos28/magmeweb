/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.newPublisher;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.pojo.ChannelAd;
import cn.magme.service.ChannelAdService;

/**
 * @author jacky_zhou
 * @date 2012-4-1
 * @version $id$
 */
@SuppressWarnings("serial")
@Results({@Result(name="channel",location="/WEB-INF/pages/newPublisher/channel.ftl")})
public class ChannelAdAction extends PublisherBaseAction {
    
    @Resource
    private ChannelAdService channelAdService;

    public String saveJson(){
        this.jsonResult=channelAdService.save(channelAd);
        return JSON;
    }
    
    private ChannelAd channelAd;

    public ChannelAd getChannelAd() {
        return channelAd;
    }

    public void setChannelAd(ChannelAd channelAd) {
        this.channelAd = channelAd;
    }
}
