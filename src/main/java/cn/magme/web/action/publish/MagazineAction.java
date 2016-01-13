/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.publish;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.constants.CacheConstants;
import cn.magme.constants.PojoConstant;
import cn.magme.constants.PojoConstant.FPAGE;
import cn.magme.constants.PojoConstant.SORT;
import cn.magme.pojo.ActivityAlbum;
import cn.magme.pojo.Category;
import cn.magme.pojo.CreativeRecommend;
import cn.magme.pojo.Issue;
import cn.magme.pojo.Link;
import cn.magme.pojo.Publication;
import cn.magme.pojo.Sort;
import cn.magme.pojo.Tag;
import cn.magme.pojo.sns.Creative;
import cn.magme.service.ActivityAlbumService;
import cn.magme.service.CreativeRecommendService;
import cn.magme.service.IssueService;
import cn.magme.service.LinkService;
import cn.magme.service.PublicationService;
import cn.magme.service.SortService;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;
import cn.magme.web.util.SecondDomainHardCode;

import com.danga.MemCached.MemCachedClient;

/**
 * @author fredy.liu
 * @date 2011-9-26
 * @version $id$
 */

@Results({@Result(name="magSuccess",location="/WEB-INF/pages/publish/magazine.ftl"),
	@Result(name="magazineAjax",location="/WEB-INF/pages/publish/magazineAjax.ftl"),
	@Result(name="pubSuccess",location="/WEB-INF/pages/publish/magazine.ftl"),
	@Result(name="appJson",type="json",params={"root","jsonResult","contentType","text/html"})})
public class MagazineAction extends BaseAction{

	@Resource
	private PublicationService publicationService;
	
	@Resource
	private SortService sortService;
	
	@Resource
	private IssueService issueService;
	
	@Resource
	private MemCachedClient memCachedClient;
	
	@Resource
	private LinkService linkService;
	
	@Resource
	private CreativeRecommendService creativeRecommendService;
	
	@Resource
	private ActivityAlbumService activityAlbumService;
	
	private static final String PUB_SUCCESS="pubSuccess";
	
	private static final int RECOMMEND_ISSUE=2;
	
	private static final int HOT_ISSUE=3;
	
	private static final int LASTEST_ISSUE=1;
	
	private static final Integer DEFAULT_PAGE_SIZE=20;
	
	private static final Integer RECOMMEND_ISSUE_PAGE_SIZE=20;
	
	private static final String MAG_SUCCESS="magSuccess";
	
	private static final Logger log=Logger.getLogger(MagazineAction.class);
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -7031323632413456217L;
	
	
	public String execute(){
		hardCodeMap=SecondDomainHardCode.hardCodePageInfo(sortName);
		//查询类目
		try {
			sortList = sortService.getDateSortedListByGroupFromDB(SORT.GROUP_COMPUTER);
			sortNameToSortId(sortList,sortName);
			linkTypeBySortId(this.sortId);
			Link link = new Link();
			link.setStatus(PojoConstant.TLINK.STATUS_OK);
			link.setType(linkType);//杂志综合
			footerPubLink = this.linkService.getLink(link);
			this.magJson();
			issueListHot=this.issueService.queryHotIssues(this.sortId,0, 20);
			issueListNew=this.issueService.queryLastestIssues(this.sortId,0, 20);
		} catch (Exception e) {
			log.error("查询杂志失败", e);
		}
		return MAG_SUCCESS;
	}
	
	/**
	 * 杂志对应的分类
	 * @return
	 */
	public String publicationSortList(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("group", PojoConstant.SORT.GROUP_COMPUTER);
		sortList = sortService.getSortList(map);
		this.jsonResult = JsonResult.getSuccess();
		this.jsonResult.put("sortList", sortList);
		return JSON;
	}
	
	/**
	 * 查询指定杂志的所有期刊
	 * @return
	 */
	public String pub(){
		sortList = sortService.getListByGroupFromDB(SORT.GROUP_COMPUTER);
		sortNameToSortId(sortList,sortName);
		issueListHot=this.issueService.queryHotIssues(this.sortId,0, 20);
		issueListNew=this.issueService.queryLastestIssues(this.sortId,0, 20);
		if(this.pubId==null || this.pubId<=0){
			log.error("参数错误,pubId必需大于0");
			return PUB_SUCCESS;
		}
		int [] statuses=new int[]{PojoConstant.ISSUE.STATUS.ON_SALE.getCode()};
		issueList=issueService.queryByPubIdAndStatuses(this.pubId, statuses,-1);
		pubByIssue(issueList);
		return PUB_SUCCESS;
	}
	
	/**
	 * 根据sortid获取对应的杂志
	 * @return
	 */
	public String publicationBySortJson(){
		jsonResult=new JsonResult();
		//默认失败
		jsonResult.setCode(JsonResult.CODE.FAILURE);
		jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		sortList = sortService.getListByGroupFromDB(SORT.GROUP_COMPUTER);
		if(sortId==null || sortId<=0){
			sortNameToSortId(sortList,sortName);
		}
		if(sortId!=null&&sortId>0){
			for (Sort s : sortList) {
				if(s.getId().equals(sortId)){
					List<Publication> tempList = s.getPublicationList();
					List<Publication> delList = new ArrayList<Publication>();
					for (Publication publication : tempList) {
						if(publication.getPubType()>0){//过滤掉非pc端显示的杂志  PubType=0 是普通杂志
							delList.add(publication);
						}
					}
					tempList.removeAll(delList);
					jsonResult.put("publicationList", tempList);
					jsonResult.setCode(JsonResult.CODE.SUCCESS);
					jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
					break;
				}
			}
		}
		return JSON;
	}
	
	public String magJson(){
		jsonResult=new JsonResult();
		//默认失败
		jsonResult.setCode(JsonResult.CODE.FAILURE);
		jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		try {
			sortList = sortService.getListByGroupFromDB(SORT.GROUP_COMPUTER);
			sortNameToSortId(sortList,sortName);
			this.genInfo();
			//使用标签搜索
			if(StringUtil.isNotBlank(this.tagName)){
				issueList=issueService.queryByTagNameTypeAndFpageType(type, tagName, PojoConstant.TAG.TYPE_ISSUE, begin, size);
			}else{
				if(type==RECOMMEND_ISSUE){
					issueList=issueService.queryByFpageTypeAndCategory(FPAGE.TYPE.MAGAZINE_RECOMMEND.getCode(),null,begin,size);
				}else if(type==HOT_ISSUE){
					issueList=this.issueService.queryHotIssues(this.sortId,begin, size);
				}else if(type==LASTEST_ISSUE){
					issueList=this.issueService.queryLastestIssues(this.sortId,begin, size);
				}else{
					//nothing
					log.error("MagazineAction.execute() nothing to do");
				}
			}
			pubByIssue(issueList);//TODO 太恐怖了，好多查数据库的动作。。。
			jsonResult.put("issueList", issueList);
			jsonResult.setCode(JsonResult.CODE.SUCCESS);
			jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			
			Link link = new Link();
			link.setStatus(PojoConstant.TLINK.STATUS_OK);
			link.setType(linkType);//杂志综合
			footerPubLink = this.linkService.getLink(link);
		} catch (Exception e) {
			log.error("查询杂志失败", e);
		}
		
		return JSON;
	}
	
	/**
	 * 麦米阅读推荐的作品
	 * @return
	 */
	public String creativeRecommend(){
		this.jsonResult = JsonResult.getFailure();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("status", 1);
		map.put("begin", 0);
		map.put("size", 5);
		List<CreativeRecommend> creativeList = creativeRecommendService.listCreativeRecommend(map);
		if(creativeList!=null&&creativeList.size()>0){
			for (CreativeRecommend creativeRecommend : creativeList) {
				if(creativeRecommend.getType()==0){//期刊
					Issue issuePojo = issueService.queryById(creativeRecommend.getCreativeId());
					creativeRecommend.setIssuePojo(issuePojo);
					
					String pageIdStr = pramByUrl(creativeRecommend.getUrl(),"pageId");
					int pageId = 0;
					if(pageIdStr!=null){
						try{
						pageId = Integer.parseInt(pageIdStr);
						}catch(Exception ex){}
					}
					creativeRecommend.setPageId(pageId);
				}
			}
		}
		this.jsonResult = JsonResult.getSuccess();
		this.jsonResult.put("creativeRecommendList", creativeList);
		return JSON;
	}
	
	protected String pramByUrl(String url,String parmKey) {
		String backStr = "";
		if(StringUtil.isNotBlank(url)&&StringUtil.isNotBlank(parmKey)){
			try{
				String str = url.split("[?]")[1];
				String[] parm = str.split("&");
				if(parm!=null){
					for (int i = 0; i < parm.length; i++) {
						String[] temp_Str = parm[i].split("=");
						String key = temp_Str[0];
						if(parmKey.equals(key)){
							backStr = temp_Str[1];
							break;
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				return backStr;
			}
		}
		return backStr;
	}

	/**
	 * 在Issue对象里存放publication对象
	 * @param issueList
	 */
	protected void pubByIssue(List<Issue> issueList) {
		if(issueList!=null){
			for (Issue ise : issueList) {
				try{
					Publication pub = publicationService.queryById(ise.getPublicationId());
					ise.setPublicationPo(pub);
				}catch (Exception e) {
				}
			}
		}
	}
	
	public String magazineAjax(){
		this.magJson();
		return "magazineAjax";
		
	}
	
	private void genInfo(){
		if(type==0){
			type=LASTEST_ISSUE;
		}
		if(categoryId!=null && categoryId<=0){
			categoryId=null;
		}
		if (cache != null && cache.equalsIgnoreCase("false")) {
	       useCache = false;
	    }
		if(begin==null || begin<=1){
			begin=0;
		}
		if(size==null || size<=0){
			if(type==RECOMMEND_ISSUE){
				size=RECOMMEND_ISSUE_PAGE_SIZE;
			}else{
				size=DEFAULT_PAGE_SIZE;
			}
		}
	}
	/**
	 * 查询期刊的喜欢数
	 * @return
	 */
	public String queryEnjoyNum(){
		this.jsonResult=new JsonResult();
		//默认失败
		jsonResult.setCode(JsonResult.CODE.FAILURE);
		jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		try {
			Integer enjoyNum=(Integer)memCachedClient.get(CacheConstants.ISSUE_ENJOY_NUM_PREFIX+issueId);
			if(enjoyNum==null){
				enjoyNum=this.issueService.queryById(this.issueId).getEnjoyNum();
				this.memCachedClient.set(CacheConstants.ISSUE_ENJOY_NUM_PREFIX+issueId, enjoyNum);
			}
			jsonResult.put("enjoyNum", enjoyNum);
			jsonResult.setCode(JsonResult.CODE.SUCCESS);
			jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		} catch (Exception e) {
			log.error("查询喜欢数出错", e);
		}
		return JSON;
	}
	
	public String appPicStart(){
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		ActivityAlbum aa = new ActivityAlbum();
		aa.setType(9999);
		aa.setStatus(1);
		if(begin==null)begin=0;
		if(size==null)size=5;
		List<ActivityAlbum> listaa  = activityAlbumService.queryActivityAlbumList(aa, begin, size);
		this.jsonResult.put("listPic", listaa);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}
	
	/**
	 * 根据sortName获取对应的sortid
	 * @param sortList
	 * @param sortName
	 */
	protected void sortNameToSortId(List<Sort> sortList,String sortName) {
		if(StringUtil.isNotBlank(sortName)){
			for (Sort sort : sortList) {
				String domain=sort.getDomain();
				if(domain.equals(sortName)){
					this.setSortId(sort.getId());
					break;
				}
			}
		}
	}
	
	protected void linkTypeBySortId(Long sort) {
		if(sort!=null&&sort>0){
			if(sort==6)this.linkType=4;
			if(sort==9)this.linkType=5;
			if(sort==12)this.linkType=6;
			if(sort==16)this.linkType=7;
			if(sort==17)this.linkType=8;
			if(sort==18)this.linkType=9;
			if(sort==19)this.linkType=10;
			if(sort==22)this.linkType=11;
			if(sort==23)this.linkType=12;
			if(sort==24)this.linkType=13;
			if(sort==28)this.linkType=14;
			if(sort==32)this.linkType=15;
			if(sort==35)this.linkType=16;
			if(sort==36)this.linkType=17;
			if(sort==37)this.linkType=18;
		}else{
			this.linkType=3;
		}
	}
	
	private Map<String,String> hardCodeMap;

	private List<Category> childCategoryList;
	
	private List<Issue> issueList;
	
	private List<Issue> issueListHot;
	
	private List<Issue> issueListNew;
	
	private Integer begin;
	
	private Integer size;
	
	private Long issueId;
	
	private String tagName;
	
	private Long pubId;
	
	private List<Sort> sortList;
	
	private Link footerPubLink;
	private Integer linkType = 3;
	
	public Map<String, String> getHardCodeMap() {
		return hardCodeMap;
	}

	public void setHardCodeMap(Map<String, String> hardCodeMap) {
		this.hardCodeMap = hardCodeMap;
	}

	public List<Sort> getSortList() {
		return sortList;
	}

	public void setSortList(List<Sort> sortList) {
		this.sortList = sortList;
	}

	public Long getPubId() {
		return pubId;
	}

	public void setPubId(Long pubId) {
		this.pubId = pubId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public Long getIssueId() {
		return issueId;
	}

	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}


	/**
	 * 类型 1，推荐 2，热门，3， 最新
	 */
	private int type=LASTEST_ISSUE;
	
	private String cache="true";
	
	private boolean useCache=true;
	
	private Long categoryId;
	
	private Long sortId;
	
	private String sortName;
	
	private List<Tag> tagList;
	
	
	public List<Tag> getTagList() {
		return tagList;
	}

	public void setTagList(List<Tag> tagList) {
		this.tagList = tagList;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<Issue> getIssueList() {
		return issueList;
	}

	public void setIssueList(List<Issue> issueList) {
		this.issueList = issueList;
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

	public List<Category> getChildCategoryList() {
		return childCategoryList; 
	}

	public void setChildCategoryList(List<Category> childCategoryList) {
		this.childCategoryList = childCategoryList;
	}

	public String getCache() {
		return cache;
	}

	public void setCache(String cache) {
		this.cache = cache;
	}

	public boolean isUseCache() {
		return useCache;
	}

	public void setUseCache(boolean useCache) {
		this.useCache = useCache;
	}

	public Long getSortId() {
		return sortId;
	}

	public void setSortId(Long sortId) {
		this.sortId = sortId;
	}

	public List<Issue> getIssueListHot() {
		return issueListHot;
	}

	public void setIssueListHot(List<Issue> issueListHot) {
		this.issueListHot = issueListHot;
	}

	public List<Issue> getIssueListNew() {
		return issueListNew;
	}

	public void setIssueListNew(List<Issue> issueListNew) {
		this.issueListNew = issueListNew;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public Link getFooterPubLink() {
		return footerPubLink;
	}

	public void setFooterPubLink(Link footerPubLink) {
		this.footerPubLink = footerPubLink;
	}

	public Integer getLinkType() {
		return linkType;
	}

	public void setLinkType(Integer linkType) {
		this.linkType = linkType;
	}
	
	
}
