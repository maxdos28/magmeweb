/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.widget;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import weibo4j.AppsOnWeibo;
import weibo4j.Weibo;

import cn.magme.constants.PojoConstant;
import cn.magme.pojo.FpageEvent;
import cn.magme.pojo.Issue;
import cn.magme.service.FpageEventService;
import cn.magme.service.IssueService;
import cn.magme.service.PopularService;
import cn.magme.util.StringUtil;
import cn.magme.util.oauth.Oauth;
import cn.magme.web.action.BaseAction;

/**
 * @author fredy.liu
 * @date 2011-6-1
 * @version $id$
 */
@Results({
        @Result(name = "success", location = "/WEB-INF/pages/widget/snsReader.ftl"),
        @Result(name = "redirect", type = "redirect", location = "/widget/sns-pass.action?xx=${xx}&appKey=${appKey}&pubId=${publicationId}&pubName=${pubName}") })
public class SnsReadAction extends BaseAction {

    @Resource
    private IssueService issueService;

    @Resource
    private PopularService popularService;

    @Resource
    private FpageEventService fpageEventService;

    private static final Logger log = Logger.getLogger(SnsReadAction.class);

    /**
	 * 
	 */
    private static final long serialVersionUID = 2240617706251844531L;

    private Long id;
    private Long pageId;
    private Issue issue;
    private String backUrl;
    private Integer backPageNo;
    private Long eventId;
    private Long adId;
    private Long publicationId;
    private Integer size;
    private String appKey;
    private String appSecret;
    private String xx;
    private String yy;
    private String signed_request;
    private String pubName;

    /**
     * 插件阅读器
     */
    public String execute() throws Exception {
        if (StringUtil.isNotBlank(xx) && xx.equalsIgnoreCase(Oauth.SINA)) {
            if (StringUtil.isNotBlank(signed_request)) {
                AppsOnWeibo wb = new AppsOnWeibo();
                wb.setAPP_KEY(appKey);
                wb.setAPP_SCRT(appSecret);
                String access_token = wb.parseSignedRequest(signed_request);
                if (StringUtil.isBlank(access_token)) {
                    return "redirect";
                }
            }
        }

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setHeader("P3P", "CP=CAO PSA OUR");

        //        if (this.id == null) {
        //            FpageEvent fpageEvent = fpageEventService.getFpageEventById(this.eventId);
        //            if (fpageEvent != null && !fpageEvent.getStatus().equals(PojoConstant.FPAGEEVENT.STATUS_DELETE)) {
        //                this.id = fpageEvent.getIssueId();
        //                this.adId=fpageEvent.getAdId();
        //                if (this.pageId == null)
        //                    this.pageId = (long) (fpageEvent.getPageNo());
        //            }
        //        }     
        //对应的点击数+1
        //popularService.click(this.id, PojoConstant.POPULAR.TYPE_ISSUE);
        //        try {
        //            //issue = this.issueService.queryById(id);
        //        } catch (Exception e) {
        //            log.error("", e);
        //        }
        //        if (this.pageId == null)
        //            this.pageId = 1L;

        return SUCCESS;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
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

    public String getSigned_request() {
        return signed_request;
    }

    public void setSigned_request(String signed_request) {
        this.signed_request = signed_request;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Long getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(Long publicationId) {
        this.publicationId = publicationId;
    }

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public String getBackUrl() {
        return backUrl;
    }

    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }

    public Integer getBackPageNo() {
        return backPageNo;
    }

    public void setBackPageNo(Integer backPageNo) {
        this.backPageNo = backPageNo;
    }

    public String getPubName() {
        return pubName;
    }

    public void setPubName(String pubName) {
        this.pubName = pubName;
    }

}
