/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.widget;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.omg.CORBA.PUBLIC_MEMBER;

import weibo4j.AppsOnWeibo;
import cn.magme.common.FileOperate;
import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant;
import cn.magme.constants.WebConstant;
import cn.magme.constants.PojoConstant.SORT;
import cn.magme.pojo.FpageEventDto;
import cn.magme.pojo.Issue;
import cn.magme.pojo.Publisher;
import cn.magme.pojo.Sort;
import cn.magme.pojo.User;
import cn.magme.pojo.UserImage;
import cn.magme.service.FpageEventService;
import cn.magme.service.IssueService;
import cn.magme.service.PublisherService;
import cn.magme.service.SortService;
import cn.magme.service.UserImageService;
import cn.magme.service.UserService;
import cn.magme.util.PageInfo;
import cn.magme.util.StringUtil;
import cn.magme.util.oauth.Oauth;
import cn.magme.web.action.BaseAction;

import com.opensymphony.xwork2.ActionContext;

/**
 * 首页
 * 
 * @author jacky_zhou
 * @date 2011-5-27
 * @version $id$
 */
@SuppressWarnings("serial")
@Results({ @Result(name = "magzine", location = "/WEB-INF/pages/widget/magzine_v2.ftl"),
        @Result(name = "newEvent", location = "/WEB-INF/pages/widget/newEvent.ftl"),
        @Result(name = "newTag", location = "/WEB-INF/pages/widget/newTag.ftl"),
        @Result(name = "hotTag", location = "/WEB-INF/pages/widget/hotTag.ftl"),
        @Result(name = "showTag", location = "/WEB-INF/pages/widget/showTag.ftl"),
        @Result(name = "redirect", type = "redirect", location = "/widget/widget-pass.action?xx=${xx}"),
        @Result(name = "index", type = "redirect", location = "/index.action") })
public class WidgetAction extends BaseAction {

    private final static String MAGZINE = "magzine";
    
    private final static String NEW_EVENT = "newEvent";

    private final static String NEW_TAG = "newTag";

    private final static String HOT_TAG = "hotTag";    

    private final static String SHOW_TAG = "showTag";

    private String xx;
    private String yy;
    private String code;

    //开心网的session_key
    private String session_key;

    //人人网的xn_sig_session_key
    private String xn_sig_session_key;

    private String signed_request;

    @Resource
    private FpageEventService fpageEventService;

    @Resource
    private UserImageService userImageService;

    @Resource
    private IssueService issueService;

    @Resource
    private UserService userService;

    @Resource
    private PublisherService publisherService;

    @Resource
    private SortService sortService;

    private String cache;

    private String pageTitle;

    private Long id;

    private Long sortId;

    private List<Sort> sortList;

    private UserImage tag;

    private List<UserImage> tagList;

    private List<UserImage> newTagList;

    private List<UserImage> hotTagList;

    private PageInfo magPageInfo = new PageInfo();

    private PageInfo evePageInfo = new PageInfo();

    private PageInfo tagPageInfo = new PageInfo();

    private Integer begin;

    private Integer size;

    public String weibo() throws Exception {
        if (StringUtil.isNotBlank(signed_request)) {
            AppsOnWeibo wb = new AppsOnWeibo();
            wb.setAPP_KEY("366059465");
            wb.setAPP_SCRT("e1e192ad9bf1d186df6dead3fa9945ed");
            String access_token = wb.parseSignedRequest(signed_request);
            if ("".equals(access_token) || access_token == null) {
                return "index";
            } else {
                List<Publisher> publisherList = publisherService.getAll();

                for (Publisher publisher : publisherList) {
                    if (StringUtil.isNotBlank(publisher.getWeiboUid()) && StringUtil.isBlank(publisher.getLogo())) {
                        weibo4j.User user = wb.showUser(publisher.getWeiboUid(), access_token);
                        FileOperate fileOp = new FileOperate();
                        String fileName = systemProp.getPublishProfileLocalUrlTmp() + File.separator
                                + System.currentTimeMillis() + ".jpg";
                        URL url = user.getProfileImageURL();
                        URL urlNew = new URL(url.toString().replace("/50/", "/180/"));
                        fileOp.copyFileFromIS(urlNew.openStream(), fileName);
                        this.jsonResult = publisherService.uploadLogo(publisher.getId(), new File(fileName),
                                "image/jpg", ".jpg");
                        String avatarFileName = (String) jsonResult.get("avatarFileName");
                        this.jsonResult = publisherService.saveLogo(publisher.getId(), avatarFileName, 0, 0, 0, 0);
                        Publisher publisherNew = new Publisher();
                        publisherNew.setId(publisher.getId());
                        publisherNew.setLogo((String) jsonResult.get("logoFilePath"));
                        publisherService.updatePublisherById(publisherNew);
                    }
                }
            }
        }
        return magzine();
    }

    public String magzine() throws Exception {
        if (StringUtil.isNotBlank(xx) && xx.equalsIgnoreCase(Oauth.SINA)) {
            if (StringUtil.isNotBlank(signed_request)) {
                AppsOnWeibo wb = new AppsOnWeibo();
                wb.setAPP_KEY("366059465");
                wb.setAPP_SCRT("e1e192ad9bf1d186df6dead3fa9945ed");
                String access_token = wb.parseSignedRequest(signed_request);
                if (StringUtil.isBlank(access_token)) {
                    return "redirect";
                }
            }
        } else if (StringUtil.isNotBlank(xx) && xx.equalsIgnoreCase(Oauth.RENREN)) {
            if (StringUtil.isBlank(xn_sig_session_key)) {
                return "redirect";
            }
        }

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setHeader("P3P", "CP=CAO PSA OUR");
        //response.setHeader("Content-Type", "text/html;charset=GBK");
        //response.setHeader("Cache-Control", "max-age=" + systemProp.getPageCacheTimeout());

        Map session = ActionContext.getContext().getSession();
        Oauth auth = (Oauth) session.get(WebConstant.SESSION.WIDGET_AUTH);

        if (StringUtil.isNotBlank(xx)) {
            if (auth == null || !(auth.getType().equalsIgnoreCase(xx))
                    || (xx.equalsIgnoreCase("tencent") && !yy.equalsIgnoreCase(auth.getSubType()))) {
                auth = new Oauth(xx, yy);
                if (xx.equals(Oauth.KAIXIN) && StringUtil.isNotBlank(session_key)) {
                    auth.setSessionKey(session_key);
                } else if (xx.equals(Oauth.RENREN) && StringUtil.isNotBlank(xn_sig_session_key)) {
                    auth.setSessionKey(xn_sig_session_key);
                }

                session.put(WebConstant.SESSION.WIDGET_AUTH, auth);
            }
        }
        return newEvent();
    }
    
    public String  magzineShow() {
    
        //开心网的标签页参数
        if (id != null && id != 0) {
            return this.showTag();
        }

        List<Issue> magList = new ArrayList<Issue>();

        if (sortId == null) {
            magList = issueService.queryAllNormalIssues();
        } else {
            magList = issueService.queryNormalBySortIdAndGroup(sortId, SORT.GROUP_COMPUTER);
        }

        if (magList != null) {
            int totalSize = magList.size();
            magPageInfo.setData(magList);
            magPageInfo.setCurPage(1);
            magPageInfo.setLimit(15);
            magPageInfo.setTotal(totalSize);

            int totalPage = 0;
            if (totalSize % 15 == 0) {
                totalPage = totalSize / 15;
            } else if (totalSize % 15 > 0) {
                totalPage = totalSize / 15 + 1;
            }
            magPageInfo.setTotalPage(totalPage);
        }

        sortList = sortService.getListByGroup(SORT.GROUP_COMPUTER);

        pageTitle = MAGZINE;

        return MAGZINE;
    }
    
    public String magzineShowJson(){
    	jsonResult = new JsonResult();
        //默认失败
        jsonResult.setCode(JsonResult.CODE.FAILURE);
        jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
        
        int maxNum = 0;
        if (sortId == null) {
        	maxNum = issueService.queryAllNormalIssuesCount();
        } else {
        	maxNum = issueService.queryNormalBySortIdAndGroupCount(sortId, SORT.GROUP_COMPUTER);
        }

        if (maxNum>0) {
        	List<Issue> magList = issueService.queryNormalBySortIdAndGroupForPage(sortId, SORT.GROUP_COMPUTER, 0, 8);
        	sortList = sortService.getListByGroup(SORT.GROUP_COMPUTER);
        	if(magList!=null){
        	for (Issue issue : magList) {
        		issue.setJpgPath(StringUtil.prefix(issue.getJpgPath(), "200_"));
        		issue.setDescription("");
			}}
        	if(sortList!=null){
	        	for (Sort sort : sortList) {
					sort.setPublicationList(null);
				}
        	}
            int totalSize = maxNum;
            magPageInfo.setData(magList);
            magPageInfo.setCurPage(1);
            magPageInfo.setLimit(8);
            magPageInfo.setTotal(totalSize);

            int totalPage = 0;
            if (totalSize % 8 == 0) {
                totalPage = totalSize / 8;
            } else if (totalSize % 8 > 0) {
                totalPage = totalSize / 8 + 1;
            }
            magPageInfo.setTotalPage(totalPage);
            
            jsonResult.put("magPageInfo", magPageInfo);
            jsonResult.put("sortList", sortList);
            jsonResult.setCode(JsonResult.CODE.SUCCESS);
            jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
        }

        return JSON;
    }

    public String magzineAjax() {
        jsonResult = new JsonResult();
        //默认失败
        jsonResult.setCode(JsonResult.CODE.FAILURE);
        jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
        List<Issue> magList = issueService.queryNormalBySortIdAndGroupForPage(sortId, SORT.GROUP_COMPUTER, begin, size);
        jsonResult.put("magList", magList);
        jsonResult.setCode(JsonResult.CODE.SUCCESS);
        jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
        return JSON;
    }
    
	public String magzineJsonAjax() {
	    jsonResult = new JsonResult();
	    //默认失败
	    jsonResult.setCode(JsonResult.CODE.FAILURE);
	    jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
	    if(size==null || size <0){
	    	size= 8;
	    }
	    begin = (begin-1)*size;
	    List<Issue> magList = issueService.queryNormalBySortIdAndGroupForPage(sortId, SORT.GROUP_COMPUTER, begin, size);
	    if(magList!=null){
	    	for (Issue issue : magList) {
        		issue.setJpgPath(StringUtil.prefix(issue.getJpgPath(), "200_"));
        		issue.setDescription("");
			}
	    }
	    jsonResult.put("magList", magList);
	    jsonResult.setCode(JsonResult.CODE.SUCCESS);
	    jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
	    return JSON;
    }
    
    public String newEvent() throws Exception {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setHeader("P3P", "CP=CAO PSA OUR");
        //        response.setHeader("Cache-Control", "max-age=" + systemProp.getPageCacheTimeout());

        //        boolean useCache = true;
        //        if (cache != null && cache.equalsIgnoreCase("false")) {
        //            useCache = false;
        //        }
        List<FpageEventDto> eventList = fpageEventService.getHomeEventList(sortId, null, SORT.GROUP_COMPUTER, true, 0, 99999);

        if (eventList == null) {
            eventList = new ArrayList<FpageEventDto>();
        }

        int totalSize = eventList.size();
        evePageInfo.setData(eventList);
        evePageInfo.setCurPage(1);
        evePageInfo.setLimit(20);
        evePageInfo.setTotal(totalSize);

        int totalPage = 0;
        if (totalSize % 20 == 0) {
            totalPage = totalSize / 20;
        } else if (totalSize % 20 > 0) {
            totalPage = totalSize / 20 + 1;
        }
        evePageInfo.setTotalPage(totalPage);

        sortList = sortService.getListByGroup(SORT.GROUP_COMPUTER);

        pageTitle = NEW_EVENT;

        return NEW_EVENT;
    }

    public String newEventAjax() {
        List<FpageEventDto> eventList = new ArrayList<FpageEventDto>();
        
        jsonResult = new JsonResult();
        //默认失败
        jsonResult.setCode(JsonResult.CODE.FAILURE);
        jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
        eventList = fpageEventService.getHomeEventList(sortId, null, SORT.GROUP_COMPUTER, true, begin, size);
        jsonResult.put("eventList", eventList);
        jsonResult.setCode(JsonResult.CODE.SUCCESS);
        jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
        return JSON;
    }

    public String newTag() throws Exception {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setHeader("P3P", "CP=CAO PSA OUR");
        //        response.setHeader("Cache-Control", "max-age=" + systemProp.getPageCacheTimeout());

        //        boolean useCache = true;
        //        if (cache != null && cache.equalsIgnoreCase("false")) {
        //            useCache = false;
        //        }

        newTagList = userImageService.getNewTagList();

        int totalSize = newTagList.size();
        tagPageInfo.setData(newTagList);
        tagPageInfo.setCurPage(1);
        tagPageInfo.setLimit(8);
        tagPageInfo.setTotal(totalSize);

        int totalPage = 0;
        if (totalSize % 8 == 0) {
            totalPage = totalSize / 8;
        } else if (totalSize % 8 > 0) {
            totalPage = totalSize / 8 + 1;
        }
        tagPageInfo.setTotalPage(totalPage);

        pageTitle = NEW_TAG;

        return NEW_TAG;
    }

    public String hotTag() {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setHeader("P3P", "CP=CAO PSA OUR");
        //        response.setHeader("Cache-Control", "max-age=" + systemProp.getPageCacheTimeout());

        //        boolean useCache = true;
        //        if (cache != null && cache.equalsIgnoreCase("false")) {
        //            useCache = false;
        //        }

        Map paraMap = new HashMap();
        paraMap.put("orderColumn", "browsenum");
        paraMap.put("orderType", "desc");
        paraMap.put("size", 8);
        paraMap.put("minHeight", 200);
        paraMap.put("maxHeight", 270);
        hotTagList = userImageService.getHotTagList(paraMap);

        pageTitle = HOT_TAG;

        return HOT_TAG;
    }

    public String showTag() {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setHeader("P3P", "CP=CAO PSA OUR");
        //                response.setHeader("Cache-Control", "max-age=" + systemProp.getPageCacheTimeout());

        this.tag = userImageService.getUserImageById(id);
        if (this.tag != null && this.tag.getStatus().equals(PojoConstant.USERIMAGE.STATUS_OK)) {
            Issue issue = issueService.queryById(tag.getIssueId());
            tag.setIssue(issue);
            Long userId = this.tag.getUserId();
            this.tagList = userImageService.getUserImageListByUserId(null, userId, 0, 9);

            Integer totalTagNum = userImageService.getUserImageCountByUserId(userId, PojoConstant.TAG.STATUS_OK);
            User user = new User();
            user.setId(userId);
            User temp = userService.getUserById(userId);
            if (temp != null) {
                user.setNickName(temp.getNickName());
                user.setAvatar(temp.getAvatar());
            }
            user.put("totalTagNum", totalTagNum);
            this.tag.setUser(user);

            //                    //更新标签热度
            //                    tagPopularService.addTagPv(id);
        }

        return SHOW_TAG;
    }

    private void accessToken() throws Exception {

        Oauth auth = (Oauth) ActionContext.getContext().getSession().get(WebConstant.SESSION.WIDGET_AUTH);
        if (auth == null) {
            auth = new Oauth(xx, yy);
        }

        String apiKey = auth.getApiKey();
        String secretKey = auth.getSecretKey();
        String redirectUri = auth.getRedirectUri();

        Integer statusCode = -1;
        // Create HttpClient Object
        DefaultHttpClient client = new DefaultHttpClient();
        // Send data by post method in HTTP protocol,use HttpPost instead of
        // PostMethod which was occurred in former version
        String url = "";
        //BasicHttpParams params = new BasicHttpParams();
        List<NameValuePair> paraList = new ArrayList<NameValuePair>();
        if (xx.equalsIgnoreCase(Oauth.RENREN)) {
            url = "http://graph.renren.com/oauth/token";

            //            params.setParameter("grant_type","authorization_code");
            //            params.setParameter("code",code);
            //            params.setParameter("client_id",apiKey);
            //            params.setParameter("client_secret",secretKey);
            //            params.setParameter("redirect_uri",redirectUri);

            paraList.add(new BasicNameValuePair("grant_type", "authorization_code"));
            paraList.add(new BasicNameValuePair("code", code));
            paraList.add(new BasicNameValuePair("client_id", apiKey));
            paraList.add(new BasicNameValuePair("client_secret", secretKey));
            paraList.add(new BasicNameValuePair("redirect_uri", redirectUri));
        } else {

        }

        HttpPost post = new HttpPost(url);
        // Construct a string entity
        StringEntity entity = new StringEntity("text/plain;charset=UTF-8");
        post.setEntity(entity);

        // Set content type of request header
        //post.setHeader("accept", "application/json");
        //post.setHeader("Content-Type", "application/json;charset=UTF-8");
        post.setEntity(new UrlEncodedFormEntity(paraList, "UTF-8"));
        //post.setParams(params);

        // Execute request and get the response
        HttpResponse response = client.execute(post);

        // Response Header - StatusLine - status code
        statusCode = response.getStatusLine().getStatusCode();

        if (statusCode != HttpStatus.SC_OK) {
            throw new HttpException("Http Status is error.");
        }

        JSONObject jsonObj = JSONObject.fromObject(EntityUtils.toString(response.getEntity()));
        auth.setAccessToken((String) jsonObj.get("access_token"));
        ActionContext.getContext().getSession().put(WebConstant.SESSION.WIDGET_AUTH, auth);
    }

    public String getCache() {
        return cache;
    }

    public void setCache(String cache) {
        this.cache = cache;
    }

    public List<UserImage> getNewTagList() {
        return newTagList;
    }

    public void setNewTagList(List<UserImage> newTagList) {
        this.newTagList = newTagList;
    }

    public List<UserImage> getHotTagList() {
        return hotTagList;
    }

    public void setHotTagList(List<UserImage> hotTagList) {
        this.hotTagList = hotTagList;
    }

    public PageInfo getMagPageInfo() {
        return magPageInfo;
    }

    public void setMagPageInfo(PageInfo magPageInfo) {
        this.magPageInfo = magPageInfo;
    }

    public PageInfo getTagPageInfo() {
        return tagPageInfo;
    }

    public void setTagPageInfo(PageInfo tagPageInfo) {
        this.tagPageInfo = tagPageInfo;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserImage getTag() {
        return tag;
    }

    public void setTag(UserImage tag) {
        this.tag = tag;
    }

    public List<UserImage> getTagList() {
        return tagList;
    }

    public void setTagList(List<UserImage> tagList) {
        this.tagList = tagList;
    }

    public String getXx() {
        return xx;
    }

    public void setXx(String xx) {
        this.xx = xx;
    }

    public String getYy() {
        return yy;
    }

    public void setYy(String yy) {
        this.yy = yy;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getSortId() {
        return sortId;
    }

    public void setSortId(Long sortId) {
        this.sortId = sortId;
    }

    public List<Sort> getSortList() {
        return sortList;
    }

    public void setSortList(List<Sort> sortList) {
        this.sortList = sortList;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getXn_sig_session_key() {
        return xn_sig_session_key;
    }

    public void setXn_sig_session_key(String xn_sig_session_key) {
        this.xn_sig_session_key = xn_sig_session_key;
    }

    public String getSigned_request() {
        return signed_request;
    }

    public void setSigned_request(String signed_request) {
        this.signed_request = signed_request;
    }

    public PageInfo getEvePageInfo() {
        return evePageInfo;
    }

    public void setEvePageInfo(PageInfo evePageInfo) {
        this.evePageInfo = evePageInfo;
    }

    public Integer getBegin() {
        return begin;
    }

    public void setBegin(Integer begin) {
        this.begin = begin;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

}
