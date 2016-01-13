package cn.magme.web.action.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import cn.magme.common.JsonResult;
import cn.magme.constants.CacheConstants;
import cn.magme.constants.PojoConstant.ISSUE;
import cn.magme.pojo.FpageEvent;
import cn.magme.pojo.HomePageItem;
import cn.magme.pojo.Issue;
import cn.magme.pojo.IssueContents;
import cn.magme.service.FpageEventService;
import cn.magme.service.HomePageItemService;
import cn.magme.service.IssueContentsService;
import cn.magme.service.IssueService;
import cn.magme.util.Md5Util;
import cn.magme.web.action.BaseAction;

import com.danga.MemCached.MemCachedClient;

@SuppressWarnings("serial")
public class AppUtilAction extends BaseAction {

	@Resource
	private IssueService issueService;
	@Resource
	private FpageEventService fpageEventService;
	@Resource
	private IssueContentsService issueContentsService;
    @Resource
    private MemCachedClient memCachedClient;
    @Resource
    private HomePageItemService homePageItemService;

	private Integer begin;
	private Integer size;
	private Long superId;
	private Long issueId;
	private Long publicationId;
	private String queryString;
	private Long id;
	private String type;
	
	public String homeItem() throws Exception{
		this.jsonResult = JsonResult.getFailure();
		HomePageItem item = homePageItemService.getHomePageItemByIdAndType(id, type);
		if(item != null){
			this.jsonResult = JsonResult.getSuccess();
			this.jsonResult.put("item", item);
		} else {
			this.jsonResult.setMessage("指定的对象不存在");
		}
		return JSON;
	}
	/**
	 * 根据杂志id查询所有期刊
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String allIssue() throws Exception {
		this.jsonResult = JsonResult.getFailure();
		String key = CacheConstants.APP_NVYOU_PREFIX + "all_issues_" + publicationId;
		List<Issue> issueList = getListFromMemCache(key);
		if(issueList == null){
			int[] statuses = {ISSUE.STATUS.ON_SALE.getCode()};
			issueList = issueService.queryByPubIdAndStatuses(publicationId, statuses,-1);
			for (Issue i : issueList) {
				String passWord = i.getPassWord()==null?"0":i.getPassWord();
				if(passWord.length()==0){
					i.setPassWord("0");//0表示没有密码
				}else{
					if(passWord.equals("0")){
						i.setPassWord("0");//0表示没有密码
					}else{
						i.setPassWord("1");//1表示有密码
					}
				}
			}
			putListToMemCache(key, issueList);
		}
		if(issueList == null) issueList = new ArrayList<Issue>();
		this.jsonResult = JsonResult.getSuccess();
		this.jsonResult.put("issueList", issueList);
		return JSON;
	}
	
	/**
	 * 根据期刊id获取对应的期刊密码
	 * @return
	 * @throws Exception
	 */
	public String passWordByIssueId() throws Exception{
		this.jsonResult = JsonResult.getFailure();
		Issue issuePojo = issueService.queryById(issueId);
		String userPassWord = queryString==null?"":this.queryString.trim();//用户传递过来的密码
		if(issuePojo != null){
			Issue passWordPojo = new Issue();
			passWordPojo.setId(issueId);
			if(issuePojo.getPassWord()!=null){
				userPassWord = Md5Util.MD5Encode(userPassWord);
				if(issuePojo.getPassWord().equals(userPassWord)){
					this.jsonResult = JsonResult.getSuccess();
				}
			}
		}
		return JSON;
	}
	
	/**
	 * 根据期刊id查询目录列表
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String allContents() throws Exception {
		this.jsonResult = JsonResult.getFailure();
		String key = CacheConstants.APP_NVYOU_PREFIX + "all_contents_" + issueId;
		List<IssueContents> contents = getListFromMemCache(key);
		if(contents == null){
			contents = issueContentsService.queryAllByIssueId(issueId);
			putListToMemCache(key, contents);
		}
		if(contents == null) contents = new ArrayList<IssueContents>();
		this.jsonResult = JsonResult.getSuccess();
		this.jsonResult.put("contents", contents);
		return JSON;
	}
	/**
	 * 根据期刊id查询所有事件
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String allEvent() throws Exception {
		this.jsonResult = JsonResult.getFailure();
		String key = CacheConstants.APP_NVYOU_PREFIX + "all_event_" + issueId + "_" + begin + "_" + size;
		List<FpageEvent> eventList = getListFromMemCache(key);
		if(eventList == null){
			eventList = fpageEventService.getFpageEventListByIssueId(issueId, begin, size);
			putListToMemCache(key, eventList);
		}
		if(eventList == null) eventList = new ArrayList<FpageEvent>();
		this.jsonResult = JsonResult.getSuccess();
		this.jsonResult.put("eventList", eventList);
		return JSON;
	}
	/**
	 * 根据superId和begin、size查询所有杂志的最新一期期刊，按期刊更新时间倒排
	 * return 所有杂志的最新一期期刊列表
	 */
	@SuppressWarnings("unchecked")
	public String latestIssues() throws Exception {
		this.jsonResult = JsonResult.getFailure();
		String key = CacheConstants.APP_NVYOU_PREFIX + "latest_issues_" + superId + "_" + begin + "_" + size;
		List<Issue> issueList = getListFromMemCache(key);
		if(issueList == null){
			issueList = issueService.queryBySuperId(superId, begin, size);
			putListToMemCache(key, issueList);
		}
		if(issueList == null) issueList = new ArrayList<Issue>();
		this.jsonResult = JsonResult.getSuccess();
		this.jsonResult.put("issueList", issueList);
		return JSON;
	}
	
	@SuppressWarnings("rawtypes")
	private List getListFromMemCache(String key){
        Object obj = memCachedClient.get(key);
        if(obj != null)
        	return (List)obj;
        return null;
	}
	@SuppressWarnings("rawtypes")
	private void putListToMemCache(String key, List list){
		if(list != null){
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.HOUR, CacheConstants.CACHE_TWO_HOUR);
			memCachedClient.set(key, list);
		}
	}
	/**
	 * 搜索期刊，按杂志名和出版商名称搜索
	 * @return
	 * @throws Exception
	 */
	public String search() throws Exception {
		this.jsonResult = JsonResult.getFailure();
		if(queryString == null || "".equals(queryString.trim())){
			this.jsonResult.setMessage("查询条件不能为空");
			return JSON;
		} else {
			//清除所有空格，并将所有字符以%分隔
			queryString = queryString.trim();
			String dirtyString = ",.\\/-+_=~!@#$%^&*()|{}[];\'\"<>?`";
			StringBuffer sb = new StringBuffer("%");
			for (int i = 0; i < queryString.length(); i++) {
				char c = queryString.charAt(i);
				if(dirtyString.indexOf(c) > -1)
					continue;
				if(' ' != c && '%' != c){
					sb.append(c + "%");
				}
			}
			queryString = sb.toString();
		}
		List<Issue> issueList = issueService.searchByQueryStringAndSuperId(superId, queryString, begin, size);
		this.jsonResult = JsonResult.getSuccess();
		this.jsonResult.put("issueList", issueList);
		return JSON;
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
	public Long getIssueId() {
		return issueId;
	}
	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}
	public Long getPublicationId() {
		return publicationId;
	}
	public void setPublicationId(Long publicationId) {
		this.publicationId = publicationId;
	}
	public String getQueryString() {
		return queryString;
	}
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	public void setSuperId(Long superId) {
		this.superId = superId;
	}
	public Long getSuperId() {
		return superId;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}
}
