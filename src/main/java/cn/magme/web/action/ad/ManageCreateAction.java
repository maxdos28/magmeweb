/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.ad;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import com.opensymphony.xwork2.ActionContext;
import cn.magme.common.JsonResult;
import cn.magme.common.PageBar;
import cn.magme.common.SystemProp;
import cn.magme.pojo.AdPosition;
import cn.magme.pojo.AdUser;
import cn.magme.pojo.Category;
import cn.magme.pojo.Issue;
import cn.magme.pojo.Publication;
import cn.magme.service.AdPositionService;
import cn.magme.service.CategoryService;
import cn.magme.service.IssueService;
import cn.magme.service.PublicationService;
import cn.magme.web.action.BaseAction;

/**
 * @author shenhao
 * @date 2011-10-18
 * @version $id$
 * 创建广告
 */
@SuppressWarnings("serial")
@Results({@Result(name="success",location="/WEB-INF/pages/ad/manageAdCreate.ftl"),
    @Result(name = "jsonsh", type = "json", params = {"root","jsonResult","contentType","text/html"}) })
public class ManageCreateAction extends BaseAction {
	@Resource
	private SystemProp systemProp;
	@Resource
	private CategoryService categoryService;
	@Resource
	private PublicationService publicationService;
	@Resource
	private IssueService issueService;
	@Resource
	private AdPositionService adPositionService;
	
	public AdUser adUser;
	private int level;

	private List<Category> categoryList;
	private List<Publication> publicationList;
	private List<Issue> issueList;
	private Long categoryId;
	private Long pubId;
	private Long issueId;
	private String adDescription;
	private String adKeywords;
	private String adid;
    private List<AdPosition> adpositionList;
    
	public String execute(){
		adUser = (AdUser) ActionContext.getContext().getSession().get("session_aduser");
		
		level = adUser.getLevel();
		
		categoryList = categoryService.queryAllChildCategories();
		
		return "success";
	}
	
	public String pubSearch() {
		publicationList = publicationService.queryUpShelfByCategoryId(categoryId,null,null);
		this.jsonResult = this.generateJsonResult(JsonResult.CODE.SUCCESS, 
		        JsonResult.MESSAGE.SUCCESS, "publicationList", publicationList);
		return JSON;
	}

	public String issueSearch() {
        issueList = issueService.queryByPubIdAndStatuses(pubId,new int[]{1},-1);
        this.jsonResult = this.generateJsonResult(JsonResult.CODE.SUCCESS, 
                JsonResult.MESSAGE.SUCCESS, "issueList", issueList);
        return JSON;
    }
	
    @SuppressWarnings("unchecked")
    public String adpositionListSearch() {
        adUser = (AdUser) ActionContext.getContext().getSession().get("session_aduser");
        Map map = new HashMap();
        map.put("userid", adUser.getId());
        map.put("categoryId", categoryId);
        map.put("pubId", pubId);
        map.put("issueId", issueId);
        map.put("adTitle", adDescription);
        map.put("adKeywords", adKeywords);
        map.put("adid", adid);
        
        map.put("begin", PageBar.getBegin(this.pageNum, this.pageSize));
        map.put("size", PageBar.getEnd(this.pageSize));
        
        adpositionList = adPositionService.queryAdPosByCondition(map);
        this.jsonResult = this.generateJsonResult(JsonResult.CODE.SUCCESS, 
                JsonResult.MESSAGE.SUCCESS, "adpositionList", adpositionList);

        int count = adPositionService.queryAdPosByConditionCount(map);

        this.pageBar=new PageBar(this.pageNum, this.pageSize, count, "turnPage", null);
        this.jsonResult.put("pageBar", this.pageBar);
        return JSON;
    }
    
	public SystemProp getSystemProp() {
		return systemProp;
	}
	
	public void setSystemProp(SystemProp systemProp) {
		this.systemProp = systemProp;
	}
	
	public AdUser getAdUser() {
		return adUser;
	}

	public void setAdUser(AdUser adUser) {
		this.adUser = adUser;
	}
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	public CategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public PublicationService getPublicationService() {
		return publicationService;
	}

	public void setPublicationService(PublicationService publicationService) {
		this.publicationService = publicationService;
	}

	public IssueService getIssueService() {
		return issueService;
	}

	public void setIssueService(IssueService issueService) {
		this.issueService = issueService;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public List<Publication> getPublicationList() {
		return publicationList;
	}

	public void setPublicationList(List<Publication> publicationList) {
		this.publicationList = publicationList;
	}

	public List<Issue> getIssueList() {
		return issueList;
	}

	public void setIssueList(List<Issue> issueList) {
		this.issueList = issueList;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getPubId() {
		return pubId;
	}

	public void setPubId(Long pubId) {
		this.pubId = pubId;
	}

	public Long getIssueId() {
		return issueId;
	}

	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}

	public String getAdDescription() {
		return adDescription;
	}

	public void setAdDescription(String adDescription) {
		this.adDescription = adDescription;
	}

	public String getAdKeywords() {
		return adKeywords;
	}

	public void setAdKeywords(String adKeywords) {
		this.adKeywords = adKeywords;
	}

	public String getAdid() {
		return adid;
	}

	public void setAdid(String adid) {
		this.adid = adid;
	}
    public List<AdPosition> getAdpositionList() {
        return adpositionList;
    }

    public void setAdpositionList(List<AdPosition> adpositionList) {
        this.adpositionList = adpositionList;
    }
}
