/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.publish;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.constants.PojoConstant;
import cn.magme.pojo.Advertise;
import cn.magme.pojo.FpageEvent;
import cn.magme.pojo.Issue;
import cn.magme.service.AdvertiseService;
import cn.magme.service.FpageEventService;
import cn.magme.service.IssueService;
import cn.magme.service.PopularService;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

/**
 * @author fredy.liu
 * @date 2011-6-1
 * @version $id$
 */
@Results({ @Result(name = "success", location = "/WEB-INF/pages/publish/reader.ftl"),
        @Result(name = "pubreader_success", location = "/WEB-INF/pages/publish/publisherReader.ftl"),
        @Result(name = "adminreader_success", location = "/WEB-INF/pages/admin/adminReader.ftl")
//@Result(name="pubreader", type="redirectAction", params = {"id" , "pageId"}) 
})
public class MagReadAction extends BaseAction {

    @Resource
    private IssueService issueService;

    private static final Logger log = Logger.getLogger(MagReadAction.class);

    /**
	 * 
	 */
    private static final long serialVersionUID = 2240617706251844531L;
    private static final String PUB_READER = "reader_redirect";
    
    private static final int MAX_ISSUETOTALPAGES=639;//最大的期刊页码

    private Long id;
    private Long pageId;
    private String readerType;
    private Long publishId;

    private Long advertiseId;

    private static final int DES_TYPE_PREVIEW = 2;//预览的方式访问阅读器
    private static final int DES_TYPE_PUBREADER = 1;//出版商的方式访问阅读器
    private int previewType;
    @Resource
    private PopularService popularService;

    private Long issueId;
    private Integer pageNo;
    private Long eventId;
    private Long adId;
    
    @Resource
    private AdvertiseService advertiseService;

    @Resource
    private FpageEventService fpageEventService;

    private int desType = DES_TYPE_PUBREADER;

    private Issue issue;
    
    public Long getAdId() {
		return adId;
	}

	public void setAdId(Long adId) {
		this.adId = adId;
	}

	public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public int getDesType() {
        return desType;
    }

    public void setDesType(int desType) {
        this.desType = desType;
    }

    public Long getAdvertiseId() {
        return advertiseId;
    }

    public void setAdvertiseId(Long advertiseId) {
        this.advertiseId = advertiseId;
    }

    public int getPreviewType() {
        return previewType;
    }

    public void setPreviewType(int previewType) {
        this.previewType = previewType;
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Long getPublishId() {
        return publishId;
    }

    public void setPublishId(Long publishId) {
        this.publishId = publishId;
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

    public String getReaderType() {
        return readerType;
    }

    public void setReaderType(String readerType) {
        this.readerType = readerType;
    }

    /**
     * 读者阅读器
     */
    public String execute() {
        if (this.id == null) {
            FpageEvent fpageEvent = fpageEventService.getFpageEventById(this.eventId);
            if (fpageEvent != null && !fpageEvent.getStatus().equals(PojoConstant.FPAGEEVENT.STATUS_DELETE)) {
                this.id = fpageEvent.getIssueId();
                this.adId=fpageEvent.getAdId();
                if (this.pageId == null)
                    this.pageId = (long) (fpageEvent.getPageNo());
            }
        }
        //对应的点击数+1
        popularService.click(this.id, PojoConstant.POPULAR.TYPE_ISSUE);
        try {
            issue = this.issueService.queryById(id);
        } catch (Exception e) {
            log.error("", e);
        }
        if (this.pageId == null)
            this.pageId = 1L;

        return SUCCESS;
    }

    public String preview() {
        this.desType = DES_TYPE_PREVIEW;
        try {
            issue = this.issueService.queryById(id);
        } catch (Exception e) {
            log.error("", e);
        }
        if (this.pageId == null)
            this.pageId = 1L;
        return PUB_READER;
    }

    public String reader() {
        try {
            issue = this.issueService.queryById(id);
        } catch (Exception e) {
            log.error("", e);
        }
        if (this.pageId == null)
            this.pageId = 1L;
        return "pubreader_success";
    }

    /**
     * 出版商阅读器
     * 
     * @return
     */
    public String pubReader() {
        if (this.advertiseId != null && this.advertiseId > 0) {
            Advertise advertise = advertiseService.queryById(this.advertiseId);
            this.id = advertise.getIssueId();
            this.issueId = advertise.getIssueId();
            this.pageId = advertise.getPageNo();
            this.pageNo = advertise.getPageNo().intValue();
            try {
                issue = this.issueService.queryById(id);
            } catch (Exception e) {
                log.error("", e);
            }
        }
        if (this.pageId == null)
            this.pageId = 1L;
        return PUB_READER;
    }

    /**
     * 管理员（编辑）阅读器
     * 
     * @return
     */
    public String adminReader() {
        try {
            issue = this.issueService.queryById(id);
        } catch (Exception e) {
            log.error("", e);
        }
        if (this.pageId == null)
            this.pageId = 1L;
        return "adminreader_success";
    }

    public String adPreview() {
        this.desType = DES_TYPE_PREVIEW;
        if (this.advertiseId != null && this.advertiseId > 0) {
            Advertise advertise = advertiseService.queryById(this.advertiseId);
            this.previewType = advertise.getAdType();
            this.id = advertise.getIssueId();
            //this.pageId=advertise.getIssueId();
            this.pageId = advertise.getPageNo();
            //假如页码数大于MAX_ISSUETOTALPAGES，认为这个是插页广告中的广告，因此定位到插页广告的前一页
            if(this.pageId>MAX_ISSUETOTALPAGES){
            	Advertise insertAd=advertiseService.queryById(this.pageId);
          		this.pageId=insertAd.getPageNo();
            }
            this.pageNo = advertise.getPageNo().intValue();
            try {
                issue = this.issueService.queryById(id);
            } catch (Exception e) {
                log.error("", e);
            }
        }
        if (this.pageId == null)
            this.pageId = 1L;
        return PUB_READER;
    }

    
}
