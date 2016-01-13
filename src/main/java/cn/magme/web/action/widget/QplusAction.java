/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import com.opensymphony.xwork2.ActionContext;
import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant;
import cn.magme.constants.PojoConstant.SORT;
import cn.magme.pojo.FeedBack;
import cn.magme.pojo.FeedBackCategory;
import cn.magme.pojo.FpageEventDto;
import cn.magme.pojo.Issue;
import cn.magme.pojo.Sort;
import cn.magme.pojo.UserImage;
import cn.magme.service.FeedBackCategoryService;
import cn.magme.service.FeedBackService;
import cn.magme.service.FpageEventService;
import cn.magme.service.IssueService;
import cn.magme.service.LuceneService;
import cn.magme.service.QplusEnjoyService;
import cn.magme.service.SortService;
import cn.magme.util.NumberUtil;
import cn.magme.util.PageInfo;
import cn.magme.util.SearchIssueSorter;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

/**
 * @author shenhao
 * @date 2012-6-6
 * @version $id$
 */
@SuppressWarnings("serial")
@Results({ @Result(name = "success", location = "/WEB-INF/pages/widget/pub.ftl"),
	@Result(name = "event", location = "/WEB-INF/pages/widget/event.ftl"),
	@Result(name = "pub", location = "/WEB-INF/pages/widget/pub.ftl"),
	@Result(name = "enjoy", location = "/WEB-INF/pages/widget/enjoy.ftl"),
	@Result(name = "read", location = "/WEB-INF/pages/widget/read.ftl"),
	@Result(name = "category", location = "/WEB-INF/pages/dialog/feedbacksmall.ftl")})
public class QplusAction extends BaseAction {

	@Resource
	private FpageEventService fpageEventService;
	@Resource
	private IssueService issueService;
	@Resource
	private SortService sortService;
	@Resource
	private LuceneService luceneService;
	@Resource
	private QplusEnjoyService qplusEnjoyService;
	@Resource
	private FeedBackCategoryService feedBackCategoryService;
	@Resource
	private FeedBackService feedBackService;
	private static final Logger log = Logger.getLogger(QplusAction.class);
	private final static String MAGZINE = "pub";
	private final static String EVENT = "event";
	private final static String ENJOY = "enjoy";
	private final static String READ = "read";

	private String xx;
	private String yy;
	private String code;
	//开心网的session_key
	private String session_key;
	//人人网的xn_sig_session_key
	private String xn_sig_session_key;
	private String signed_request;
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
	private String queryStr;
	private String srcFlg;
	private String app_openid;
	private Long publicationId;
	private int changeFlg;
	private Issue issue;
	private List<FeedBackCategory> feedBackCategoryList;
	private Long categoryId;
	private String content;
	private Integer ran;

	public String execute() throws Exception {
	    HttpServletResponse response = ServletActionContext.getResponse();
	    response.setHeader("P3P", "CP=CAO PSA OUR");
		return pub();
	}
	
	/**
	 * 杂志
	 * @return
	 * @throws Exception
	 */
	public String pub() throws Exception {
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
			magPageInfo.setLimit(8);
			magPageInfo.setTotal(totalSize);
			int totalPage = 0;
			if (totalSize % 8 == 0) {
				totalPage = totalSize / 8;
			} else if (totalSize % 8 > 0) {
				totalPage = totalSize / 8 + 1;
			}
			magPageInfo.setTotalPage(totalPage);
		}
		sortList = sortService.getListByGroup(SORT.GROUP_COMPUTER);
		issue = issueService.queryQplusLastestIssue(sortId);
		pageTitle = MAGZINE;
		if (ActionContext.getContext().getSession().get("app_openid") == null) {
			ActionContext.getContext().getSession().put("app_openid", app_openid);
		}else{
			app_openid = (String) ActionContext.getContext().getSession().get("app_openid");
		}
		return MAGZINE;
	}
	
	/**
	 * 事件
	 * @return
	 * @throws Exception
	 */
	public String event() throws Exception {

		List<FpageEventDto> eventList = fpageEventService.getHomeEventList(sortId, null, SORT.GROUP_COMPUTER, true, 0, 99999);
		if (eventList == null) {
			eventList = new ArrayList<FpageEventDto>();
		}
		int sizeLst[]=new int[]{3,5,4,4,5,4,4,3};
		
		int totalSize = eventList.size();
		evePageInfo.setData(eventList);
		evePageInfo.setCurPage(1);
		ran = (int)(Math.random()*8);
		evePageInfo.setLimit(sizeLst[ran]);
		evePageInfo.setTotal(totalSize);
		sortList = sortService.getListByGroup(SORT.GROUP_COMPUTER);
		pageTitle = EVENT;
		return EVENT;
	}

	/**
	 * 收藏
	 * @return
	 * @throws Exception
	 */
	public String enjoy() throws Exception {
		app_openid = (String) ActionContext.getContext().getSession().get("app_openid");
		if (publicationId != null){
			qplusEnjoyService.delete(app_openid, publicationId);
		}

		List<Issue> magList = issueService.queryQplusIssues(app_openid, null, null);
		if (magList != null) {
			int totalSize = magList.size();
			magPageInfo.setData(magList);
			magPageInfo.setCurPage(1);
			magPageInfo.setLimit(8);
			magPageInfo.setTotal(totalSize);
		}
		pageTitle = ENJOY;
		return ENJOY;
	}

	public String read() throws Exception {
		Long issueId;
		Integer pageNum;
		String date;
		app_openid = (String) ActionContext.getContext().getSession().get("app_openid");
		List<Issue> magList;
		if (changeFlg == 0){
			magList = new ArrayList<Issue>();
		}else{
			JSONArray jsonArray = new JSONArray();
			jsonArray = JSONArray.fromObject("["+cache+"]");
			magList = new ArrayList<Issue>();
			Issue issue;
			for (int i =0;i<jsonArray.size();i++){
				issueId = jsonArray.getJSONObject(i).getLong("issueId");
				pageNum = jsonArray.getJSONObject(i).getInt("pageNum");
				date = jsonArray.getJSONObject(i).getString("date");
				issue = issueService.queryById(issueId);
				if (issue != null){
					issue.setClickNum(pageNum);
					issue.setCategoryName(date);
					magList.add(issue);
				}
			}
		}
		
		if (magList != null) {
			int totalSize = magList.size();
			magPageInfo.setData(magList);
			magPageInfo.setCurPage(1);
			magPageInfo.setLimit(8);
			magPageInfo.setTotal(totalSize);
		}
		pageTitle = READ;
		return READ;
	}

	/**
	 * 获取意见反馈弹出框里的内容
	 * @return
	 */
	public String categoryAjax(){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("status", PojoConstant.FEEDBACKCATEGORY.STATUS_OK);
		this.feedBackCategoryList=feedBackCategoryService.getFeedBackCategoryList(map);
		return "category";
	}
	
	/**
	 * 保存意见
	 * @return
	 */
	public String saveCategory(){
		FeedBack feedBack=new FeedBack();
		feedBack.setCategoryId(this.categoryId);
		feedBack.setContent(this.content);
		//feedBack.setUserId(this.getSessionUserId());
		feedBack.setIpAddress(this.getRequestIpLong());
		feedBack.setProvince(this.getRequestProvince());
		feedBack.setCity(this.getRequestCity());
		// app_openid 作为备注更新入库
		feedBack.setComment((String) ActionContext.getContext().getSession().get("app_openid"));
		this.jsonResult=feedBackService.insertFeedBack(feedBack);
		return JSON;
	}
	
	public String enjoyChangeAjax() {
		jsonResult = new JsonResult();
		//默认失败
		jsonResult.setCode(JsonResult.CODE.FAILURE);
		jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		app_openid = (String) ActionContext.getContext().getSession().get("app_openid");
		if (app_openid == null) {
			return JSON;
		}
		if (changeFlg == 1) {
			qplusEnjoyService.delete(app_openid, publicationId);
		}else{
			qplusEnjoyService.insert(app_openid, publicationId);
		}
		
		jsonResult.setCode(JsonResult.CODE.SUCCESS);
		jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}
	
	public String statusAjax() {
		jsonResult = new JsonResult();
		//默认失败
		jsonResult.setCode(JsonResult.CODE.FAILURE);
		jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		app_openid = (String) ActionContext.getContext().getSession().get("app_openid");
		
		if (app_openid == null) {
			return JSON;
		}
		boolean bol = qplusEnjoyService.select(app_openid, publicationId);
		
		jsonResult.put("status", bol);
		jsonResult.setCode(JsonResult.CODE.SUCCESS);
		jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}

	/**
	 * 搜索
	 * @return
	 */
	public String search() {
		if (StringUtil.isNotBlank(pageTitle)) {
			srcFlg = pageTitle;
		}else{
			srcFlg = MAGZINE;
		}
		pageTitle = MAGZINE;
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setHeader("Cache-Control", "max-age=" + systemProp.getPageCacheTimeout());

		try {
			sortList = sortService.getListByGroup(SORT.GROUP_COMPUTER);
			
			if (StringUtil.isBlank(queryStr)) {
				log.error("搜索关键词为空");
				return pageTitle;
			}

			queryStr = queryStr.trim();
			Long[] objIds = luceneService.seacher(queryStr, "Issue", null);
			objIds = NumberUtil.longFilter(objIds);

			List<Issue> magList = null;
			if (objIds != null && objIds.length > 0) {
				magList = new ArrayList<Issue>();
				for (Long objId : objIds) {
					Issue issue = this.issueService.queryById(objId);
					if (issue != null) {
						magList.add(issue);
					}
				}
				magList = SearchIssueSorter.sort(magList, StringUtil.containsNumber(queryStr));

				int totalSize = magList.size();
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
			}
//			pageTitle = MAGZINE;
			issue = issueService.queryQplusLastestIssue(sortId);
		} catch (Exception e) {
			log.error("", e);
		}

		return pageTitle;
	}
	
	/**
	 * 搜索翻页
	 * @return
	 */
	public String searchAjax() {
		jsonResult = new JsonResult();
		//默认失败
		jsonResult.setCode(JsonResult.CODE.FAILURE);
		jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		
		if (StringUtil.isBlank(queryStr)) {
			log.error("搜索关键词为空");
		}

		queryStr = queryStr.trim();
		Long[] objIds = luceneService.seacher(queryStr, "Issue", null);
		objIds = NumberUtil.longFilter(objIds);

		List<Issue> magList = null;
		if (objIds != null && objIds.length > 0) {
			magList = new ArrayList<Issue>();
			for (Long objId : objIds) {
				Issue issue = this.issueService.queryById(objId);
				if (issue != null) {
					magList.add(issue);
				}
			}
			magList = SearchIssueSorter.sort(magList, StringUtil.containsNumber(queryStr));
			magList = magList.subList(begin, begin+size>magList.size()?magList.size():begin+size);
		}
		
		jsonResult.put("magList", magList);
		jsonResult.setCode(JsonResult.CODE.SUCCESS);
		jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
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

	public String getSession_key() {
		return session_key;
	}

	public void setSession_key(String sessionKey) {
		session_key = sessionKey;
	}

	public String getXn_sig_session_key() {
		return xn_sig_session_key;
	}

	public void setXn_sig_session_key(String xnSigSessionKey) {
		xn_sig_session_key = xnSigSessionKey;
	}

	public String getSigned_request() {
		return signed_request;
	}

	public void setSigned_request(String signedRequest) {
		signed_request = signedRequest;
	}

	public String getCache() {
		return cache;
	}

	public void setCache(String cache) {
		this.cache = cache;
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

	public PageInfo getEvePageInfo() {
		return evePageInfo;
	}

	public void setEvePageInfo(PageInfo evePageInfo) {
		this.evePageInfo = evePageInfo;
	}

	public PageInfo getTagPageInfo() {
		return tagPageInfo;
	}

	public void setTagPageInfo(PageInfo tagPageInfo) {
		this.tagPageInfo = tagPageInfo;
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
	
	public String getQueryStr() {
		return queryStr;
	}

	public void setQueryStr(String queryStr) {
		this.queryStr = queryStr;
	}
	
	public String getSrcFlg() {
		return srcFlg;
	}

	public void setSrcFlg(String srcFlg) {
		this.srcFlg = srcFlg;
	}
	
	public String getApp_openid() {
		return app_openid;
	}

	public void setApp_openid(String app_openid) {
		this.app_openid = app_openid;
	}
	
	public Long getPublicationId() {
		return publicationId;
	}

	public void setPublicationId(Long publicationId) {
		this.publicationId = publicationId;
	}

	public int getChangeFlg() {
		return changeFlg;
	}

	public void setChangeFlg(int changeFlg) {
		this.changeFlg = changeFlg;
	}
	
	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}
	
	public List<FeedBackCategory> getFeedBackCategoryList() {
		return feedBackCategoryList;
	}

	public void setFeedBackCategoryList(List<FeedBackCategory> feedBackCategoryList) {
		this.feedBackCategoryList = feedBackCategoryList;
	}
	
	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public Integer getRan() {
		return ran;
	}

	public void setRan(Integer ran) {
		this.ran = ran;
	}
}
