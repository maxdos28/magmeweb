/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import cn.magme.service.LinkService;

/**
 * 静态页面跳转
 * @author jacky_zhou
 * @date 2011-7-28
 * @version $id$
 */
@SuppressWarnings("serial")
public class StaticPageAction extends BaseAction {
	@Resource
	private LinkService linkService;
	
	private String friendlink;
    @Action(value="/static_*", results = { @Result(name = "success", location = "/WEB-INF/pages/static/{1}.ftl") })
    public String execute(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setHeader("Cache-Control", "max-age=" + systemProp.getPageCacheTimeout());
        friendlink = linkService.getLink().getContent();
        return "success";
    }
	public void setFriendlink(String friendlink) {
		this.friendlink = friendlink;
	}
	public String getFriendlink() {
		return friendlink;
	}
}
