/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.newPublisher;

import java.io.File;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.pojo.ChannelBanner;
import cn.magme.service.ChannelBannerService;

/**
 * @author jacky_zhou
 * @date 2012-4-1
 * @version $id$
 */
@SuppressWarnings("serial")
@Results({@Result(name="channel",location="/WEB-INF/pages/newPublisher/channel.ftl"),
    @Result(name = "upload_json", type = "json", params = { "root","jsonResult", "contentType", "text/html" })})
public class ChannelBannerAction extends PublisherBaseAction {
    
    @Resource
    private ChannelBannerService channelBannerService;

    public String saveJson(){
        this.jsonResult=channelBannerService.save(channelBanner, pic, picContentType, picFileName);
        return "upload_json";
    }
    
    public String editJson(){
        this.channelBanner=channelBannerService.getById(id);;
        this.generateJsonResult(JsonResult.CODE.SUCCESS, 
                JsonResult.MESSAGE.SUCCESS, "channelBanner", channelBanner);
        return JSON;
    }
    
    public String deleteJson(){
        this.jsonResult=channelBannerService.deleteById(id);
        return JSON;
    }
    
    private Long id;
    private ChannelBanner channelBanner;
    private File pic;
    private String picContentType;
    private String picFileName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChannelBanner getChannelBanner() {
        return channelBanner;
    }

    public void setChannelBanner(ChannelBanner channelBanner) {
        this.channelBanner = channelBanner;
    }

    public File getPic() {
        return pic;
    }

    public void setPic(File pic) {
        this.pic = pic;
    }

    public String getPicContentType() {
        return picContentType;
    }

    public void setPicContentType(String picContentType) {
        this.picContentType = picContentType;
    }

    public String getPicFileName() {
        return picFileName;
    }

    public void setPicFileName(String picFileName) {
        this.picFileName = picFileName;
    }
}