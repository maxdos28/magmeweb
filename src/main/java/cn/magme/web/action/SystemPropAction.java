/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

/**
 * @author jacky_zhou
 * @date 2011-5-27
 * @version $id$
 */
@SuppressWarnings("serial")
public class SystemPropAction extends BaseAction {
    
    public String execute() throws Exception{
        HttpServletResponse response=ServletActionContext.getResponse();
        response.setContentType("text/javascript;charset=UTF-8"); 
        PrintWriter out=response.getWriter();
        out.println("var SystemProp={};");
        out.println("//是否统计");
        out.println("SystemProp.isStat="+this.systemProp.getIsStat()+";");   
        out.println("//新版出版商后台上传图片的地址");
        out.println("SystemProp.newPublisherServerUrl=\""+this.systemProp.getNewPublisherServerUrl()+"\";"); 
        out.println("//网站域名");
        out.println("SystemProp.domain=\""+this.systemProp.getDomain()+"\";");
        out.println("//加到所有*.action前面");
        out.println("SystemProp.appServerUrl=\""+this.systemProp.getAppServerUrl()+"\";");
        out.println("//加到所有静态资源前面,包括js,style以及静态的图片,但是/js/systemProp.js除外");
        out.println("SystemProp.staticServerUrl=\""+this.systemProp.getStaticServerUrl()+"\";");
        out.println("//统计的URL");
        out.println("SystemProp.statServerUrl=\""+this.systemProp.getStatServerUrl()+"\";");
        out.println("//加到用户头像前面");
        out.println("SystemProp.profileServerUrl=\""+this.systemProp.getProfileServerUrl()+"\";");
        out.println("//加到用户上传的临时头像前面");
        out.println("SystemProp.profileServerUrlTmp=\""+this.systemProp.getProfileServerUrlTmp()+"\";");
        out.println("//暂未用到");
        out.println("SystemProp.sampleServerUrl=\""+this.systemProp.getSampleServerUrl()+"\";");
        out.println("//加到标签图片的前面");
        out.println("SystemProp.tagServerUrl=\""+this.systemProp.getTagServerUrl()+"\";");
        out.println("//加到杂志的图片和SWF前面");
        out.println("SystemProp.magServerUrl=\""+this.systemProp.getMagServerUrl()+"\";");
        out.println("//供Flash调用");
        out.println("SystemProp.fpageServerUrl=\""+this.systemProp.getFpageServerUrl()+"\";");
        out.println("//供首页图片路径调用");
        out.println("SystemProp.rrAppid=\""+this.thirdConfig.getRrAppid()+"\";");
        out.println("//人人APPID");
        out.println("SystemProp.rrResponseType=\""+this.thirdConfig.getRrResponseType()+"\";");
        out.println("//人人ResponseType");
        out.println("SystemProp.rrRedirectUri=\""+this.thirdConfig.getRrRedirectUri()+"\";");
        out.println("//人人RedirectUri");
        out.println("SystemProp.kxApiKey=\""+this.thirdConfig.getKxApiKey()+"\";");
        out.println("//开心APPKEY");
        out.println("SystemProp.kxSecret=\""+this.thirdConfig.getKxSecret()+"\";");
        out.println("//开心SECRET");
        out.println("SystemProp.kxResponseType=\""+this.thirdConfig.getKxResponseType()+"\";");
        out.println("//开心ResponseType");
        out.println("SystemProp.kxRedirectUri=\""+this.thirdConfig.getKxRedirectUri()+"\";");
        out.println("//开心RedirectUri");
        
        out.println("SystemProp.sinaAppKey=\""+this.thirdConfig.getSinaAppKey()+"\";");
        out.println("SystemProp.baiduApiKey=\""+this.thirdConfig.getBaiduApiKey()+"\";");
        out.println("SystemProp.kaiJieAppUrl=\""+this.systemProp.getKaiJieAppUrl()+"\";");
        out.println("//新浪pKey");
        
        out.println("SystemProp.publishProfileServerUrl=\""+this.systemProp.getPublishProfileServerUrl()+"\";");
        out.println("SystemProp.publishProfileServerUrlTmp=\""+this.systemProp.getPublishProfileServerUrlTmp()+"\";");
        
        out.println("SystemProp.adProfileServerUrl=\""+this.systemProp.getAdProfileServerUrl()+"\";");
        out.println("SystemProp.adProfileServerUrlTmp=\""+this.systemProp.getAdProfileServerUrlTmp()+"\";");
        
        
        out.println("SystemProp.getUrl=function(param){");
        out.println("   var url=eval('SystemProp.'+param);");
        out.println("   return url;");
        out.println("};");
        return null;
    }
}
