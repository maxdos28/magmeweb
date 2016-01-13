/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.servlet;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.magme.dao.sns.SnsCreativeDao;
import cn.magme.service.LuceneService;
import cn.magme.service.sns.SnsInitService;



/**
 * @author qiaowei
 * @date 2011-5-4
 * @version $id$
 */

public class InitDataServlet extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 3964467243795155136L;
    private ServletConfig servletConfig;

    private static Map<String, Object> findBean(ServletContext servletContext, Class beanClass) {
        ApplicationContext appctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
        return appctx.getBeansOfType(beanClass);
    }

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        servletConfig = config;
     /*   IssueService issueService = (IssueService)(findBean(config.getServletContext(),IssueService.class)).get("issueServiceImpl");
        UserImageService userImageService = (UserImageService)(findBean(config.getServletContext(),UserImageService.class)).get("userImageServiceImpl");
        UserService userService = (UserService)(findBean(config.getServletContext(),UserService.class)).get("userServiceImpl");
        PublisherService publisherService = (PublisherService)(findBean(config.getServletContext(),PublisherService.class)).get("publisherServiceImpl");
        List issueList = issueService.getAll();
        List userImageList = userImageService.getAll();
        List userList = userService.getAll();
        List publisherList = publisherService.getAll();*/
        //sns 建立
        
        SnsCreativeDao snsCreativeDao = (SnsCreativeDao)(findBean(config.getServletContext(),SnsCreativeDao.class)).get("snsCreativeDaoImpl");
        List clist= (List) snsCreativeDao.getCreativeAll();
        LuceneService luceneService = (LuceneService)(findBean(config.getServletContext(),LuceneService.class)).get("luceneServiceImpl");
        luceneService.createIndex(clist);
        
//        SnsInitService snsInitService = (SnsInitService)(findBean(config.getServletContext(),SnsInitService.class)).get("snsInitServiceImpl");
//        snsInitService.updateUserInvite();
        /*luceneService.createIndex(issueList);
        luceneService.createIndex(userImageList);
        luceneService.createIndex(userList);
        luceneService.createIndex(publisherList);*/
    }

    public void destroy() {
        //servletConfig.getServletContext().removeAttribute("category");
        //servletConfig.getServletContext().removeAttribute("language");
    }

}
