/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.widget;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionContext;

import cn.magme.util.StringUtil;
import cn.magme.util.oauth.Oauth;
import cn.magme.web.action.BaseAction;

/**
 * 首页
 * 
 * @author jacky_zhou
 * @date 2011-5-27
 * @version $id$
 */
@SuppressWarnings("serial")
@Results({ @Result(name = "redirect", location = "/WEB-INF/pages/widget/widgetPass.ftl") })
public class WidgetPassAction extends BaseAction {

    private String xx;
    
    public String execute() throws Exception {
          HttpServletResponse response = ServletActionContext.getResponse();
//        response.setHeader("P3P", "CP=CAO PSA OUR");
//        //response.setHeader("Content-Type", "text/html;charset=GBK");
//        response.setHeader("Cache-Control", "max-age=" + systemProp.getPageCacheTimeout());
//        
//        if(StringUtil.isNotBlank(xx)&&xx.equalsIgnoreCase(Oauth.SINA)){
//                WebOAuth weboauth = new WebOAuth();
//                RequestToken resToken=weboauth.request("http://pass.magme.cn/widget/widget!magzine.action");
//                if(resToken!=null){
//                    Map session = ActionContext.getContext().getSession();
//                    session.put("resToken",resToken);
//                    response.sendRedirect(resToken.getAuthorizationURL());
//            }
//        }
        
        return "redirect";
    }

    public String getXx() {
        return xx;
    }

    public void setXx(String xx) {
        this.xx = xx;
    }

}
