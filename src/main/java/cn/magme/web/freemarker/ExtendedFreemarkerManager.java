/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.freemarker;

import javax.servlet.ServletContext;

import org.apache.struts2.views.freemarker.FreemarkerManager;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

/**
 * 定义一个FreemarkerManager的子类,将自定义的摸板类进行共享
 * @author jacky_zhou
 * @date 2011-10-10
 * @version $id$
 */
public class ExtendedFreemarkerManager extends FreemarkerManager { 
    protected Configuration createConfiguration(ServletContext servletContext) 
    throws TemplateException { 
    Configuration configuration = super.createConfiguration(servletContext); 
    configuration.setSharedVariable( 
    "encode", 
    new EncodeMethodFreeMarker()); 
    // 加入时间日期处理  
    
    //加入头像缩放处理
    configuration.setSharedVariable("avatarResize", new AvatarResizeFreeMarker());
    //加入作品图片宽高计算返回
    configuration.setSharedVariable("creativeResize", new CreativeResizeFreeMarker());
    //昵称等字符处理
    configuration.setSharedVariable("stringSub", new StringSubFreeMarker());
    
    return configuration; 
    } 
} 

