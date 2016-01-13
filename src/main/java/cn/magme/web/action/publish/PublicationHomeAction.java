package cn.magme.web.action.publish;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.common.KeyValue;
import cn.magme.constants.CacheConstants;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.Category;
import cn.magme.pojo.Issue;
import cn.magme.pojo.Publication;
import cn.magme.pojo.Publisher;
import cn.magme.result.sns.CreativeCommentListResult;
import cn.magme.result.sns.PublicationOrderResult;
import cn.magme.service.CategoryService;
import cn.magme.service.FpageEventService;
import cn.magme.service.IssueService;
import cn.magme.service.PublicationService;
import cn.magme.service.PublisherService;
import cn.magme.service.SubscribeService;
import cn.magme.service.UserImageService;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;
import cn.magme.web.util.SecondDomainHardCode;

import com.danga.MemCached.MemCachedClient;

@Results({
		@Result(name = "mag", location = "/WEB-INF/pages/publication/mag.ftl"),
		@Result(name = "newmag", location = "/WEB-INF/pages/publication/newmag.ftl"),
		@Result(name = "index", type = "redirect", location = "/index.action"),
		@Result(name = "magList", location = "/WEB-INF/pages/publication/magList.ftl"),
		@Result(name = "image", location = "/WEB-INF/pages/publication/image.ftl") })
public class PublicationHomeAction extends BaseAction {

	private static final long serialVersionUID = 7420788382138734660L;

	@Resource
	private MemCachedClient memCachedClient;
	@Resource
	private UserImageService userImageService;
	@Resource
	private FpageEventService fpageEventService;
	@Resource
	private IssueService issueService;
	@Resource
	private PublicationService publicationService;
	@Resource
	private PublisherService publisherService;
	@Resource
	private CategoryService categoryService;
	@Resource
	private SubscribeService subscribeService;

	private static final String CACHE_ALIKE_PUBLICATION = "page_c_alike_publication";
	private static final String CACHE_OTHER_PUBLICATION = "page_c_other_publication";
	
	private String pubName;
	private Long publicationId;
	private Long issueId;
	private Publication publication;
	private Issue issue;
	private List<Issue> issueList;
	private List<Publication> alikePublicationList;
	private List<Publication> otherPublicationList;
	@SuppressWarnings("unchecked")
	private List imageList;
	private List<KeyValue> imageLists;
	private String type;
	private Integer issueCount;
	private Integer userImageCount;
	private Integer issueImageCount;
	private Publisher publisher;
	private Category category;
	private Map<String,String> hardCodeMap;
	private String sortName;
	private List<PublicationOrderResult> publicationOrder;
	private List<CreativeCommentListResult> comment;
	private Integer begin;
	
	public Integer getBegin() {
		return begin;
	}

	public void setBegin(Integer begin) {
		this.begin = begin;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public Map<String, String> getHardCodeMap() {
		return hardCodeMap;
	}

	public void setHardCodeMap(Map<String, String> hardCodeMap) {
		this.hardCodeMap = hardCodeMap;
	}

	/**
	 * 杂志
	 * 
	 * @return
	 */
	public String mag() {
		if(pubName != null){
			this.publication = this.publicationService.queryPubByEnglishName(pubName);
			if(this.publication==null){
				return "index";
			}
			this.sortName=this.publication.getDomain();
			hardCodeMap=SecondDomainHardCode.hardCodePageInfo(this.publication.getDomain());
			this.publicationId = publication.getId();
		} else if(publicationId != null){
			publication = publicationService.queryById(publicationId);
		}
		if (publication == null) {
			return "index";
		}
		resetDescription();
		this.category = categoryService.queryById(publication.getCategoryId());
		this.issueCount = issueService .getIssueCountByPublicationId(publicationId);
		this.issueImageCount = issueService .getIssueImageCountByPublicationId(publicationId);
		this.userImageCount = issueService .getUserImageCountByPublicationId(publicationId);
		this.publisher = publisherService.queryById(publication == null ? 0L : publication.getPublisherId());
		this.issue = this.issueService.queryById(this.issueId);

		int[] statuses = new int[] { PojoConstant.ISSUE.STATUS.ON_SALE .getCode() };
		issueList = issueService.queryByPubIdAndStatuses(this.publicationId, statuses,-1);
		//you bug 注释 2012-09-28 等待齐翊解决
		//List<Publication> other = getFromMemCache(CACHE_OTHER_PUBLICATION);
		List<Publication> other = null;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("categoryId", this.publication.getCategoryId());
		if(other == null || other.isEmpty()){
			//不同分类杂志列表
			other = publicationService.queryForPageC(param);
			setToMemCache(CACHE_OTHER_PUBLICATION, other);
		}
		this.otherPublicationList = other;
		List<Publication> alike = getFromMemCache(CACHE_ALIKE_PUBLICATION + "_" + this.publicationId);
		if(alike == null || alike.isEmpty()){
			param.put("publicationId", this.publicationId);
			//相同分类杂志列表
			alike = publicationService.queryForPageC(param);
			setToMemCache(CACHE_ALIKE_PUBLICATION + "_" + this.publicationId, alike);
		}
		this.alikePublicationList = alike;
//		recommendIssueList = issueService .queryOtherIssuesByCategoryIdAndPublicationId(param);
		
		this.setDynamicMenu(publication == null ? null : publication.getName());
		return "mag";
	}
	
	

	/**
	 * 新杂志封面
	 * 
	 * @return
	 */
	public String newmag() {
		if(pubName != null){
			this.publication = this.publicationService .queryPubByEnglishName(pubName);
			if(this.publication==null){
				return "index";
			}
			this.sortName=this.publication.getDomain();
			hardCodeMap=SecondDomainHardCode.hardCodePageInfo(this.publication.getDomain());
			this.publicationId = publication.getId();
		} else if(publicationId != null){
			publication = publicationService.queryById(publicationId);
		}
		if (publication == null) {
			return "index";
		}
		resetDescription();
		this.category = categoryService.queryById(publication.getCategoryId());
		this.issueCount = issueService .getIssueCountByPublicationId(publicationId);
		this.issueImageCount = issueService .getIssueImageCountByPublicationId(publicationId);
		this.userImageCount = issueService .getUserImageCountByPublicationId(publicationId);
		this.publisher = publisherService.queryById(publication == null ? 0L : publication.getPublisherId());
		this.issue = this.issueService.queryById(this.issueId);

		int[] statuses = new int[] { PojoConstant.ISSUE.STATUS.ON_SALE .getCode() };
		issueList = issueService.queryByPubIdAndStatuses(this.publicationId, statuses,-1);
		if(issueList!=null && issueList.size()>0)
			publication.setIssueId(issueList.get(0).getId());
		List<Publication> other = null;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("categoryId", this.publication.getCategoryId());
		if(other == null || other.isEmpty()){
			//不同分类杂志列表
			other = publicationService.queryForPageC(param);
			setToMemCache(CACHE_OTHER_PUBLICATION, other);
		}
		this.otherPublicationList = other;
		List<Publication> alike = getFromMemCache(CACHE_ALIKE_PUBLICATION + "_" + this.publicationId);
		if(alike == null || alike.isEmpty()){
			param.put("publicationId", this.publicationId);
			//相同分类杂志列表
			alike = publicationService.queryForPageC(param);
			if(alike!=null)
				for (int i=0;i<alike.size();i++) {
					if(alike.get(i).getSigning()==0){
						List<Issue> list = issueService.queryByPubIdAndStatuses(alike.get(i).getId(), statuses,-1);
						if(list!=null && list.size()>0){
							alike.get(i).setIssueList(list);
						}
					}else
						alike.remove(i);
				}
			setToMemCache(CACHE_ALIKE_PUBLICATION + "_" + this.publicationId, alike);
		}
		this.alikePublicationList = alike;
		
		//杂志排行
		publicationOrder();
		
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("publicationId", this.publicationId);
		condition.put("size", 20);
		comment=publicationService.queryPublicationComment(condition);
		
		this.setDynamicMenu(publication == null ? null : publication.getName());
		return "newmag";
	}
	
	//查看全部
	public String magList(){
		if(pubName != null){
			this.publication = this.publicationService .queryPubByEnglishName(pubName);
			if(this.publication==null){
				return "index";
			}
			this.publicationId = publication.getId();
		}
		int[] statuses = new int[] { PojoConstant.ISSUE.STATUS.ON_SALE .getCode() };
		issueList = issueService.queryByPubIdAndStatuses(this.publicationId, statuses,-1);
		return "magList";
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void publicationOrder(){
		Map map = new HashMap();
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
	    // 减去一个星期
	    c.add(Calendar.WEEK_OF_MONTH, -1);
	    // 上个星期的今天是第几天,星期天是1,所以要减去1
	    int d = c.get(Calendar.DAY_OF_WEEK) - 1;
	    // 上周星期日:
	    c.add(Calendar.DAY_OF_WEEK, 7 - d);
	    map.put("endDate", sdf.format(c.getTime()));
	    //上周星期一
	    c.add(Calendar.DAY_OF_YEAR,-6);
	    map.put("startDate", sdf.format(c.getTime()));
	    List<PublicationOrderResult> last =subscribeService.getOrderSubscrobe(map);
	    
	    //上上周星期日
	    c.add(Calendar.DAY_OF_YEAR,-1);
	    map.put("endDate", sdf.format(c.getTime()));
	    //上上周星期一
	    c.add(Calendar.DAY_OF_YEAR,-6);
	    map.put("startDate", sdf.format(c.getTime()));
	    List<PublicationOrderResult> preLast =subscribeService.getOrderSubscrobe(map);
	    if(last!=null){
	    	int leve=0;
		    for (int i = 0; i < last.size(); i++) {
		    	for (int j = 0; j < preLast.size(); j++) {
					if(last.get(i).getPid()==preLast.get(j).getPid()){
						if(i==j)
							leve=0;
						if(i<j)
							leve=-1;
						if(i>j)
							leve=1;
					}
				}
		    	last.get(i).setLeve(leve);
			}
	    }
	    publicationOrder=last;
	    
	}
	
	private void resetDescription() {
		String desc = publication.getDescription();
		if(desc == null) desc = "";
		desc = desc.replaceAll("\r\n", "<br/>");
		desc = desc.replaceAll("\n", "<br/>");
		desc = desc.replaceAll("<script", "&lt;script");
		publication.setDescription(desc);
	}
	
	private List<Publication> getFromMemCache(String key){
		Object alike = memCachedClient.get(key);
		if(alike != null)
			return (List<Publication>)alike;
		return null;
	}
	
	/**
	 * 将结果保存到memCache 1天刷新1次
	 * @param key
	 * @param items
	 */
	private void setToMemCache(String key, List<Publication> items) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, CacheConstants.CACHE_ONE_DAY);//1天后失效
		memCachedClient.set(key, items, cal.getTime());
	}
	/**
	 * 事件或切米,默认是查事件
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String image() {
		this.publication = this.publicationService .queryById(this.publicationId);
		this.issueCount = issueService .getIssueCountByPublicationId(publicationId);
		this.issueImageCount = issueService .getIssueImageCountByPublicationId(publicationId);
		this.userImageCount = issueService .getUserImageCountByPublicationId(publicationId);
		this.publisher = publisherService.queryById(publication == null ? 0L : publication.getPublisherId());

		this.imageLists = new ArrayList<KeyValue>();
		this.issueId = null;
		Integer cnt = 0;
		for (int i = 0; i < 12; i++) {
			Issue is = issueService.getLastIssue(publicationId, issueId);
			if (is != null) {
				this.issueId = is.getId();
				List iList = null;
				if (StringUtil.isNotBlank(this.type) && this.type.equals("userImage")) {
					// 查询图片
					iList = this.userImageService.getUserImageListByIssueId( issueId, null, null);
					this.setDynamicMenu(publication == null ? null : (publication.getName() + "中的全部图片"));
				} else {
					// 查询事件
					iList = this.fpageEventService.getFpageEventListByIssueId( issueId, null, null);
					this.setDynamicMenu(publication == null ? null : (publication.getName() + "中的全部事件"));
				}
				cnt += iList.size();
				if (iList.size() > 0) {
					this.imageLists.add(new KeyValue(is, iList));
				}
			}
		}

		if (cnt.equals(0)) {
			return mag();
		}
		return "image";
	}

	/**
	 * 图片滚动加载
	 * 
	 * @return
	 */
	public String imageJson() {
		this.issue = issueService.getLastIssue(publicationId, issueId);
		if (this.issue != null) {
			if (StringUtil.isNotBlank(this.type) && this.type.equals("userImage")) {
				// 查询图片
				this.imageList = this.userImageService .getUserImageListByIssueId(this.issue.getId(), null, null);
			} else {
				// 查询事件
				this.imageList = this.fpageEventService .getFpageEventListByIssueId(this.issue.getId(), null, null);
			}
		}
		this.jsonResult = this.generateJsonResult(JsonResult.CODE.SUCCESS, JsonResult.MESSAGE.SUCCESS);
		this.jsonResult.put("issue", this.issue);
		this.jsonResult.put("imageList", this.imageList);
		return JSON;
	}

	public Long getPublicationId() {
		return publicationId;
	}

	public void setPublicationId(Long publicationId) {
		this.publicationId = publicationId;
	}

	public Long getIssueId() {
		return issueId;
	}

	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}

	public Publication getPublication() {
		return publication;
	}

	public void setPublication(Publication publication) {
		this.publication = publication;
	}

	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	public List<Issue> getIssueList() {
		return issueList;
	}

	public void setIssueList(List<Issue> issueList) {
		this.issueList = issueList;
	}

	@SuppressWarnings("unchecked")
	public List getImageList() {
		return imageList;
	}

	@SuppressWarnings("unchecked")
	public void setImageList(List imageList) {
		this.imageList = imageList;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getIssueCount() {
		return issueCount;
	}

	public void setIssueCount(Integer issueCount) {
		this.issueCount = issueCount;
	}

	public Integer getUserImageCount() {
		return userImageCount;
	}

	public void setUserImageCount(Integer userImageCount) {
		this.userImageCount = userImageCount;
	}

	public Integer getIssueImageCount() {
		return issueImageCount;
	}

	public void setIssueImageCount(Integer issueImageCount) {
		this.issueImageCount = issueImageCount;
	}

	public List<KeyValue> getImageLists() {
		return imageLists;
	}

	public void setImageLists(List<KeyValue> imageLists) {
		this.imageLists = imageLists;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public void setPubName(String pubName) {
		this.pubName = pubName;
	}

	public String getPubName() {
		return pubName;
	}

	public void setOtherPublicationList(List<Publication> otherPublicationList) {
		this.otherPublicationList = otherPublicationList;
	}

	public List<Publication> getOtherPublicationList() {
		return otherPublicationList;
	}

	public void setAlikePublicationList(List<Publication> alikePublicationList) {
		this.alikePublicationList = alikePublicationList;
	}

	public List<Publication> getAlikePublicationList() {
		return alikePublicationList;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Category getCategory() {
		return category;
	}

	public List<PublicationOrderResult> getPublicationOrder() {
		return publicationOrder;
	}

	public List<CreativeCommentListResult> getComment() {
		return comment;
	}
	
}
