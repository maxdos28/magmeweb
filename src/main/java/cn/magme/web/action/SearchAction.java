/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.FpageEvent;
import cn.magme.pojo.Issue;
import cn.magme.pojo.Publication;
import cn.magme.pojo.Publisher;
import cn.magme.pojo.User;
import cn.magme.pojo.UserImage;
import cn.magme.pojo.phoenix.PhoenixArticle;
import cn.magme.pojo.sns.Creative;
import cn.magme.result.sns.CreativeListResult;
import cn.magme.service.FpageEventService;
import cn.magme.service.IssueService;
import cn.magme.service.LuceneService;
import cn.magme.service.PublicationService;
import cn.magme.service.PublisherService;
import cn.magme.service.TagService;
import cn.magme.service.UserFollowService;
import cn.magme.service.UserImageService;
import cn.magme.service.UserService;
import cn.magme.service.impl.LuceneServiceImpl;
import cn.magme.service.phoenix.PhoenixArticleService;
import cn.magme.service.sns.CreativeService;
import cn.magme.service.sns.SnsCreativeService;
import cn.magme.service.sns.SnsInviteCodeService;
import cn.magme.util.NumberUtil;
import cn.magme.util.SearchIssueSorter;
import cn.magme.util.StringUtil;
import cn.magme.util.Struts2Utils;

/**
 * @author qiaowei
 * @date 2011-7-14
 * @version $id$
 */
@Results({
	@Result(name = "searchSuccess", location = "/WEB-INF/pages/search.ftl")
})
public class SearchAction extends BaseAction {

    private String searchType;

    private String queryStr;

    private String keyword;

    //查询返回数量
    private Integer limit;

    private Long publicationId;

    private Long publisherId;

    private String tagName;
    
    private Long categoryId;

    private List<Issue> issueList;

    private List<Issue> issueRecommendList;

    private List<UserImage> imageList;
    
    private List<CreativeListResult> creativeList;
    
    private List<Creative> searchCreativeList;
    
    private List<Creative> m1List;

	private List<User> userList;
	
	private List<Publication> publicationList;
	
	private List<FpageEvent> eventList;
	
	private List<PhoenixArticle> phoenixArticleList;

    private Integer issueNum = 0;

    private Integer imageNum = 0;

    private Integer userNum = 0;
    
    private Integer eventNum = 0;
    
    private Integer m1Num =0;
    
    private Integer publicationNum=0;
    
    private Integer pageSize = 20;
    
    private Integer currentPage = 1;

    //private List<TagResult> tagList;

	@Resource
	private PublicationService publicationService;
    @Resource
    private IssueService issueService;

    @Resource
    private UserImageService userImageService;

    @Resource
    private UserService userService;

    @Resource
    private PublisherService publisherService;
    
    @Resource
    private PhoenixArticleService phoenixArticleService;

    @Resource
    private TagService tagService;

    @Resource
    private LuceneService luceneService;
    @Resource
    private SnsCreativeService snsCreativeService;
    @Resource
    private CreativeService creativeService;

    @Resource
    private UserFollowService userFollowService;

    @Resource
	private SnsInviteCodeService snsInviteCodeService;
    
    @Resource
    private FpageEventService fpageEventService;


    private static final long serialVersionUID = 272332340363998948L;

    private static final Logger log = Logger.getLogger(SearchAction.class);

    private static final String SEARCH_SUCCESS = "searchSuccess";
    
    public String searchBak(){
    	HttpServletResponse response = ServletActionContext.getResponse();
        response.setHeader("Cache-Control", "max-age=" + systemProp.getPageCacheTimeout());

        try {
            if (StringUtil.isBlank(queryStr)) {
                log.error("搜索关键词为空");
                return SEARCH_SUCCESS;
            }

            if (StringUtil.isBlank(searchType)) {
                log.error("搜索类型为空");
                return SEARCH_SUCCESS;
            }
            if(searchType.equals("sns")){
            	creativeList= new ArrayList<CreativeListResult>();
            	queryStr = queryStr.trim();
            	if(limit==null || limit<1)
            		limit=0;
                Long[] objIds = luceneService.seacherByPage(queryStr, "Creative", limit,20);
                for (long c:objIds) {
                	Map<String, Object> map = new HashMap<String, Object>();
                	if(getSessionUserId()!=null)
                		map.put("u", getSessionUserId());
                	else
                		map.put("u", 0);
                	map.put("id", c);
                	CreativeListResult crl= snsCreativeService.getCreativeAllByLucene(map);
                	creativeList.add(crl);
				}
                return "sns";
            }else{
            	queryStr = queryStr.trim();
                Long[] objIds = luceneService.seacher(queryStr, searchType, limit);
                
                //出版商的id数组
                Long[] objIds2 = null;
                if (searchType.equalsIgnoreCase(LuceneServiceImpl.CLASS_TYPE_USER)) {
                    objIds2 = luceneService.seacher(queryStr, LuceneServiceImpl.CLASS_TYPE_PUBLISHER, limit);
                }
                
                if (!searchType.equalsIgnoreCase(LuceneServiceImpl.CLASS_TYPE_ISSUE)) {
                    issueNum = luceneService.seacher(queryStr, LuceneServiceImpl.CLASS_TYPE_ISSUE, limit).length;
                }
                if (!searchType.equalsIgnoreCase(LuceneServiceImpl.CLASS_TYPE_USERIMAGE)) {
                    imageNum = luceneService.seacher(queryStr, LuceneServiceImpl.CLASS_TYPE_USERIMAGE, limit).length;
                }
                if (!searchType.equalsIgnoreCase(LuceneServiceImpl.CLASS_TYPE_USER)) {
                    userNum = luceneService.seacher(queryStr, LuceneServiceImpl.CLASS_TYPE_USER, limit).length
                            + luceneService.seacher(queryStr, LuceneServiceImpl.CLASS_TYPE_PUBLISHER, limit).length;
                }

                if ((objIds == null || objIds.length == 0) && (objIds2 == null || objIds2.length == 0)) {

                    //                if(searchType.equalsIgnoreCase(LuceneServiceImpl.CLASS_TYPE_ISSUE)){
                    //无结果则显示推荐杂志
                    //                        issueList = issueService.queryByFpageTypeAndCategory(PojoConstant.FPAGE.TYPE.MAGAZINE_RECOMMEND.getCode(),null,null,null);
                    //                        issueNum = issueList.size();
                    //                        tagList = tagService.findTagsByTypeAndObjectIds(PojoConstant.TAG.TYPE_ISSUE, null, 20);
                    //                }
                    //objIds=publicationService.queryLastestByPubName(queryStr);
                } else {
                    if (searchType.equalsIgnoreCase(LuceneServiceImpl.CLASS_TYPE_ISSUE)) {
                        issueList = new ArrayList<Issue>();
                        for (Long objId : objIds) {
                            Issue issue = this.issueService.queryById(objId);
                            if (issue != null) {
                                issueList.add(issue);
                            }
                        }
                        issueList = SearchIssueSorter.sort(issueList, StringUtil.containsNumber(queryStr) );
                        pubByIssue(issueList);
                        issueNum = issueList.size();

//                        tagList = tagService.findTagsByTypeAndObjectIds(PojoConstant.TAG.TYPE_ISSUE, objIds, 20);
//                        if (tagList == null || tagList.size() == 0) {
//                            tagList = tagService.findTagsByTypeAndObjectIds(PojoConstant.TAG.TYPE_ISSUE, null, 20);
//                        }
                    } else if (searchType.equalsIgnoreCase(LuceneServiceImpl.CLASS_TYPE_USERIMAGE)) {
                        imageList = new ArrayList<UserImage>();
                        for (Long objId : objIds) {
                            UserImage userImage = this.userImageService.getUserImageById(objId);
                            if (userImage != null) {
                                imageList.add(userImage);
                            }
                        }
                        imageNum = imageList.size();

//                        tagList = tagService.findTagsByTypeAndObjectIds(PojoConstant.TAG.TYPE_IMG, objIds, 20);
//                        if (tagList == null || tagList.size() == 0) {
//                            tagList = tagService.findTagsByTypeAndObjectIds(PojoConstant.TAG.TYPE_IMG, null, 20);
//                        }
                    } else if (searchType.equalsIgnoreCase(LuceneServiceImpl.CLASS_TYPE_USER)) {
                        userList = new ArrayList<User>();
                        
                        for (Long objId : objIds) {
                            User user = this.userService.getUserById(objId);
                            userList.add(user);
                        }
                        /*
                         * 洪泉：出版商不纳入用户搜索结果列表 20120612
                        for (Long objId2 : objIds2) {
                            Publisher publisher = this.publisherService.queryById(objId2);
                            if (publisher != null) {
                                userList.add(castToUser(publisher));
                            }
                        }
                        */
                        //end of add
                        //2012-06-12
                        userNum = userList.size();
                    }

                    //                if(issueList.size()>1){
                    //                	Collections.sort(issueList, new IssueComparator());
                    //                }
                }

                if (searchType.equalsIgnoreCase(LuceneServiceImpl.CLASS_TYPE_ISSUE)) {
                    //查询推荐杂志
                    issueRecommendList = issueService.queryByFpageTypeAndCategory(
                            PojoConstant.FPAGE.TYPE.MAGAZINE_RECOMMEND.getCode(), null, 0, 5);
                }
            }
            

            //          //查询推荐杂志
            //			int RECOMMEND_LIMIT=13;
            //			if(issueList!=null && issueList.size()<=NEED_RECOMMEND_THRESHOLD){
            //				if(issueList.size()<=7){
            //					RECOMMEND_LIMIT=13;
            //				}else{
            //					RECOMMEND_LIMIT=6;
            //				}
            //				recommendIssues=this.issueCacheService.queryRecommendPubsByLimits(RECOMMEND_LIMIT);
            //			}

        } catch (Exception e) {
            log.error("", e);
        }
        
        return SEARCH_SUCCESS;
    }

    public String execute() throws Exception {
        //HttpServletResponse response = ServletActionContext.getResponse();
        //response.setHeader("Cache-Control", "max-age=" + systemProp.getPageCacheTimeout());
        try{
        	if (StringUtil.isBlank(queryStr)) {
                log.error("搜索关键词为空");
                return SEARCH_SUCCESS;
            }

	        if (StringUtil.isBlank(searchType)) {
	            log.error("搜索类型为空");
	            return SEARCH_SUCCESS;
	        }
	        //强制设置为搜索杂志
	        searchType = LuceneServiceImpl.CLASS_TYPE_ISSUE;
        	//Long aTime = System.currentTimeMillis();
        	queryStr = queryStr.trim();
//        	Long[] objIds = luceneService.seacher("婚尚", "Issue", null);
            Long[] objIds = luceneService.seacher(queryStr, searchType, limit);//根据关键字获取对应id集合
            if (!searchType.equalsIgnoreCase(LuceneServiceImpl.CLASS_TYPE_ISSUE)) {
            	Long[] tempSize = luceneService.seacher(queryStr, LuceneServiceImpl.CLASS_TYPE_ISSUE, limit); 
            	tempSize = NumberUtil.longFilter(tempSize);//过滤掉重复的id
            	if(tempSize!=null){
            		publicationNum = tempSize.length;
            	}
//            	 Long bTime = System.currentTimeMillis()-aTime;
//                 System.out.println("杂志索引检索时间bTime:"+bTime);
            }
            if (!searchType.equalsIgnoreCase(LuceneServiceImpl.CLASS_TYPE_USER)) {
            	Long[] tempSize = luceneService.seacher(queryStr, LuceneServiceImpl.CLASS_TYPE_USER, limit); 
            	tempSize = NumberUtil.longFilter(tempSize);//过滤掉重复的id
            	if(tempSize!=null){
            		userNum = tempSize.length;
            	}
//            	 Long bTime = System.currentTimeMillis()-aTime;
//                 System.out.println("用户索引检索时间bTime:"+bTime);
            }
            if (!searchType.equalsIgnoreCase(LuceneServiceImpl.CLASS_TYPE_CREATIVE)) {
                Long[] tempSize = luceneService.seacher(queryStr, LuceneServiceImpl.CLASS_TYPE_CREATIVE, limit);
            	if(tempSize!=null){
            		m1Num = tempSize.length;
            	}
//            	 Long bTime = System.currentTimeMillis()-aTime;
//                 System.out.println("M1索引检索时间bTime:"+bTime);
            }
//            Long bTime = System.currentTimeMillis()-aTime;
//            System.out.println("索引检索时间bTime:"+bTime);
            if(objIds!=null&&objIds.length>0){
//            	 Long cTime = System.currentTimeMillis();
            	if (searchType.equalsIgnoreCase(LuceneServiceImpl.CLASS_TYPE_ISSUE)) {//杂志集合
            		objIds =NumberUtil.longFilter(objIds);//过滤掉重复的id
            		Long pageLongData[] = NumberUtil.longPageFilter(objIds, pageSize, currentPage);
            		issueList = new ArrayList<Issue>();
                    for (Long objId : pageLongData) {
                    	Issue issue = this.issueService.queryById(objId);
                        if (issue != null) {
                        	issueList.add(issue);
                        }
                    }
                    publicationNum = objIds.length;
//                    Long dTime = System.currentTimeMillis()-cTime;
//                    System.out.println("杂志索引检索时间bTime:"+dTime);
                }else if (searchType.equalsIgnoreCase(LuceneServiceImpl.CLASS_TYPE_USER)) {//用户集合
                	objIds =NumberUtil.longFilter(objIds);//过滤掉重复的id
                	Long pageLongData[] = NumberUtil.longPageFilter(objIds, pageSize, currentPage);
                    userList = new ArrayList<User>();
                    for (Long objId : pageLongData) {
                        User user = this.userService.getUserById(objId);
                        if (user != null) {
                        	userList.add(user);
                        }
                    }
                    userNum = objIds.length;
//                    Long dTime = System.currentTimeMillis()-cTime;
//                    System.out.println("用户索引检索时间bTime:"+dTime);
                }else if (searchType.equalsIgnoreCase(LuceneServiceImpl.CLASS_TYPE_CREATIVE)) {//M1作品集合
                	Long pageLongData[] = NumberUtil.longPageFilter(objIds, pageSize, currentPage);
                	queryStr = queryStr.trim();
                	if(limit==null || limit<1)
                		limit=0;
                    searchCreativeList=this.creativeService.queryByIds(pageLongData);
                    m1Num = objIds.length;
//                    Long dTime = System.currentTimeMillis()-cTime;
//                    System.out.println("M1索引检索时间bTime:"+dTime);
                }
            }else{//查无结果
            	
            }
            
            if (searchType.equalsIgnoreCase(LuceneServiceImpl.CLASS_TYPE_ISSUE)) {
                //查询推荐杂志--->热门杂志 修改===========
            	issueRecommendList=this.issueService.queryHotIssues(null,0, 4);
                //issueRecommendList = issueService.queryByFpageTypeAndCategory(
//                        PojoConstant.FPAGE.TYPE.MAGAZINE_RECOMMEND.getCode(), null, 0, 4);
            }
        }catch (Exception e) {
        	 log.error("", e);
		}
        return SEARCH_SUCCESS;
    }
    
   /**
    * 点击更多
 * @return
 */
    public String searchAjax(){
    	//默认失败
        jsonResult = JsonResult.getFailure();
        if(StringUtil.isBlank(this.queryStr)){
        	return JSON;
        }
    	 try{
	 	        //强制设置为搜索杂志
	 	        searchType = LuceneServiceImpl.CLASS_TYPE_ISSUE;
    	        queryStr = queryStr.trim();
                Long[] objIds = null;//根据关键字获取对应id集合
    	        if (searchType.equalsIgnoreCase(LuceneServiceImpl.CLASS_TYPE_PHOENIX_ARTICLE)) {
    	        	objIds = luceneService.searchPhoenixArticle(queryStr, LuceneServiceImpl.CLASS_TYPE_PHOENIX_ARTICLE,this.categoryId, limit);//根据关键字获取对应id集合
    	        }else{
    	        	objIds = luceneService.seacher(queryStr, searchType, limit);//根据关键字获取对应id集合
    	        }
                
                if(objIds!=null&&objIds.length>0){
                	if (searchType.equalsIgnoreCase(LuceneServiceImpl.CLASS_TYPE_ISSUE)) {//杂志集合
                		objIds =NumberUtil.longFilter(objIds);//过滤掉重复的id
                		Long pageLongData[] = NumberUtil.longPageFilter(objIds, pageSize, currentPage);
                		if(pageLongData.length<=0) return JSON;
                		issueList = new ArrayList<Issue>();
                        for (Long objId : pageLongData) {
                        	Issue issue = this.issueService.queryById(objId);
                            if (issue != null) {
                            	issueList.add(issue);
                            }
                        }
                        //成功返回
            	        jsonResult = JsonResult.getSuccess();
            	        jsonResult.put("itemList", issueList);
                    }else if (searchType.equalsIgnoreCase(LuceneServiceImpl.CLASS_TYPE_USER)) {//用户集合
                    	objIds =NumberUtil.longFilter(objIds);//过滤掉重复的id
                    	Long pageLongData[] = NumberUtil.longPageFilter(objIds, pageSize, currentPage);
                    	if(pageLongData.length<=0) return JSON;
                        userList = new ArrayList<User>();
                        for (Long objId : pageLongData) {
                            User user = this.userService.getUserById(objId);
                            if (user != null) {
                            	userList.add(user);
                            }
                        }
                        //成功返回
            	        jsonResult = JsonResult.getSuccess();
            	        jsonResult.put("itemList", userList);
                    }else if (searchType.equalsIgnoreCase(LuceneServiceImpl.CLASS_TYPE_CREATIVE)) {//M1作品集合
                    	Long pageLongData[] = NumberUtil.longPageFilter(objIds, pageSize, currentPage);
                    	if(pageLongData.length<=0) return JSON;
                    	queryStr = queryStr.trim();
                    	if(limit==null || limit<1)
                    		limit=0;
                        searchCreativeList=this.creativeService.queryByIds(pageLongData);
                        //成功返回
            	        jsonResult = JsonResult.getSuccess();
            	        jsonResult.put("itemList", searchCreativeList);
                    }if (searchType.equalsIgnoreCase(LuceneServiceImpl.CLASS_TYPE_PHOENIX_ARTICLE)) {//凤凰周刊文章集合
                		objIds =NumberUtil.longFilter(objIds);//过滤掉重复的id
                		Long pageLongData[] = NumberUtil.longPageFilter(objIds, pageSize, currentPage);
                		if(pageLongData.length<=0) return JSON;
                		//这样效率太慢，增加一个SQL来处理
//                		phoenixArticleList = new ArrayList<PhoenixArticle>();
//                        for (Long objId : pageLongData) {
//                        	Map<String, Object> params = new HashMap<String, Object>();
//                        	params.put("id", objId);
//                        	List<PhoenixArticle> pList = this.phoenixArticleService.query(params);
//                            if (pList != null && pList.size()>0) {
//                            	phoenixArticleList.addAll(pList);
//                            }
//                        }
                        phoenixArticleList = phoenixArticleService.queryByIds(pageLongData);
                        //成功返回
            	        jsonResult = JsonResult.getSuccess();
            	        jsonResult.put("list", phoenixArticleList);
                    }
                }else{//查无结果
                	
                }
    	 }catch (Exception e) {
			e.printStackTrace();
		}
	   return JSON;
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

    @SuppressWarnings("unchecked")
    public String updIdx() throws Exception {
        List objectList = new ArrayList();
//        if (StringUtil.isBlank(searchType) || searchType.equalsIgnoreCase(LuceneServiceImpl.CLASS_TYPE_ISSUE)) {
//            objectList = issueService.getAll();
//            luceneService.createIndex(objectList);
//        }
        //杂志
        if(StringUtil.isBlank(searchType) || searchType.equalsIgnoreCase(LuceneServiceImpl.CLASS_TYPE_ISSUE)) {
        	objectList = issueService.getAll();
        	luceneService.delIndex(objectList);
        	luceneService.createIndex(objectList);
        	luceneService.createIndexSingle(objectList);
        }
        //用户
        if (StringUtil.isBlank(searchType) || searchType.equalsIgnoreCase(LuceneServiceImpl.CLASS_TYPE_USER)) {
            objectList = userService.getAll();
            luceneService.delIndex(objectList);
        	luceneService.createIndex(objectList);
        	luceneService.createIndexSingle(objectList);
        }
        //事件
        if (StringUtil.isBlank(searchType) || searchType.equalsIgnoreCase(LuceneServiceImpl.CLASS_TYPE_FPAGEEVENT)) {
            objectList = fpageEventService.selectAllByStatusOk();
            luceneService.delIndex(objectList);
            luceneService.createIndex(objectList);
        }
        //m1作品
        if (StringUtil.isBlank(searchType) || searchType.equalsIgnoreCase(LuceneServiceImpl.CLASS_TYPE_CREATIVE)) {
            objectList = snsCreativeService.selectCreativeAll();
            luceneService.delIndex(objectList);
            luceneService.createIndex(objectList);
        }
        
        //凤凰周刊作品
        if (StringUtil.isBlank(searchType) || searchType.equalsIgnoreCase(LuceneServiceImpl.CLASS_TYPE_PHOENIX_ARTICLE)) {
        	Map<String, Object> params = new HashMap<String, Object>();
            objectList = phoenixArticleService.query(params);
            luceneService.delIndex(objectList);
            luceneService.createIndex(objectList);
        }
//        if (StringUtil.isBlank(searchType) || searchType.equalsIgnoreCase(LuceneServiceImpl.CLASS_TYPE_USERIMAGE)) {
//            objectList = userImageService.getAll();
//            luceneService.createIndex(objectList);
//        }
        

        Struts2Utils.renderText("success");

        return null;
    }

    public String byPublisherId() {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setHeader("Cache-Control", "max-age=" + systemProp.getPageCacheTimeout());

        if (publisherId == null || publisherId == 0) {
            log.error("出版商ID为空");
            return SEARCH_SUCCESS;
        }

        searchType = LuceneServiceImpl.CLASS_TYPE_USER;
        userList = userFollowService.findFollowersByPublisherId(publisherId);
        userNum = userList.size();
        //queryStr = Long.toString(publisherId);

        return SEARCH_SUCCESS;
    }

    public String byPublicationId() {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setHeader("Cache-Control", "max-age=" + systemProp.getPageCacheTimeout());

        if (publicationId == null || publicationId == 0) {
            log.error("杂志ID为空");
            return SEARCH_SUCCESS;
        }

        searchType = LuceneServiceImpl.CLASS_TYPE_ISSUE;
        int[] status = new int[1];
        status[0] = PojoConstant.ISSUE.STATUS.ON_SALE.getCode();
        issueList = issueService.queryByPubIdAndStatuses(publicationId, status,PojoConstant.ISSUE.ISSUETYPE.NORMAL.getCode());
        issueNum = issueList.size();

        Long[] objIds = new Long[issueList.size()];
        for (int i = 0; i < issueList.size(); i++) {
            objIds[i] = issueList.get(i).getId();
        }

        if (objIds != null && objIds.length > 0) {
            queryStr = issueList.get(0).getPublicationName();
            //tagList = tagService.findTagsByTypeAndObjectIds(PojoConstant.TAG.TYPE_ISSUE, objIds, 20);
        }
//        if (tagList == null || tagList.size() == 0) {
//            tagList = tagService.findTagsByTypeAndObjectIds(PojoConstant.TAG.TYPE_ISSUE, null, 20);
//        }

        issueRecommendList = issueService.queryByFpageTypeAndCategory(
                PojoConstant.FPAGE.TYPE.MAGAZINE_RECOMMEND.getCode(), null, 0, 5);

        return SEARCH_SUCCESS;
    }

    private User castToUser(Publisher publisher) {
        User user = new User();
        user.setType(0);
        user.setId(publisher.getId());
        user.setUserName(publisher.getUserName());
        user.setNickName(publisher.getPublishName());
        user.setAvatar(publisher.getLogo());
        return user;
    }

    public String byTagName() {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setHeader("Cache-Control", "max-age=" + systemProp.getPageCacheTimeout());

        if (StringUtil.isBlank(searchType)) {
            log.error("搜索类型为空");
            return SEARCH_SUCCESS;
        }

        if (StringUtil.isBlank(tagName)) {
            log.error("标签名为空");
            return SEARCH_SUCCESS;
        }
        else{
            queryStr = tagName;
        }

        if (searchType.equalsIgnoreCase(LuceneServiceImpl.CLASS_TYPE_ISSUE)) {
            issueList = tagService.queryIssueListByTagName(tagName);
            issueNum = issueList.size();
            Long[] objIds = luceneService.seacher(queryStr, LuceneServiceImpl.CLASS_TYPE_ISSUE, limit);

//            if (objIds != null && objIds.length > 0) {
//                tagList = tagService.findTagsByTypeAndObjectIds(PojoConstant.TAG.TYPE_ISSUE, objIds, 20);
//            }
//            if (tagList == null || tagList.size() == 0) {
//                tagList = tagService.findTagsByTypeAndObjectIds(PojoConstant.TAG.TYPE_ISSUE, null, 20);
//            }

            issueRecommendList = issueService.queryByFpageTypeAndCategory(
                    PojoConstant.FPAGE.TYPE.MAGAZINE_RECOMMEND.getCode(), null, 0, 5);

        } else if (searchType.equalsIgnoreCase(LuceneServiceImpl.CLASS_TYPE_USERIMAGE)) {
            imageList = tagService.queryImageListByTagName(tagName);
            imageNum = imageList.size();
            Long[] objIds = luceneService.seacher(queryStr, LuceneServiceImpl.CLASS_TYPE_USERIMAGE, limit);

//            if (objIds != null && objIds.length > 0) {
//                tagList = tagService.findTagsByTypeAndObjectIds(PojoConstant.TAG.TYPE_IMG, objIds, 20);
//            }
//            if (tagList == null || tagList.size() == 0) {
//                tagList = tagService.findTagsByTypeAndObjectIds(PojoConstant.TAG.TYPE_IMG, null, 20);
//            }
        }

        return SEARCH_SUCCESS;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getQueryStr() {
        return queryStr;
    }

    public void setQueryStr(String queryStr) {
        this.queryStr = queryStr;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<Issue> getIssueList() {
        return issueList;
    }

    public void setIssueList(List<Issue> issueList) {
        this.issueList = issueList;
    }

    public List<UserImage> getImageList() {
        return imageList;
    }

    public void setImageList(List<UserImage> imageList) {
        this.imageList = imageList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public Integer getIssueNum() {
        return issueNum;
    }

    public void setIssueNum(Integer issueNum) {
        this.issueNum = issueNum;
    }

    public Integer getImageNum() {
        return imageNum;
    }

    public void setImageNum(Integer imageNum) {
        this.imageNum = imageNum;
    }

    public Integer getUserNum() {
        return userNum;
    }

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
    }

    /**
     * 推荐期刊
     */
    private List<Issue> recommendIssues;

    public List<Issue> getRecommendIssues() {
        return recommendIssues;
    }

    public void setRecommendIssues(List<Issue> recommendIssues) {
        this.recommendIssues = recommendIssues;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }

//    public List<TagResult> getTagList() {
//        return tagList;
//    }
//
//    public void setTagList(List<TagResult> tagList) {
//        this.tagList = tagList;
//    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Long getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(Long publicationId) {
        this.publicationId = publicationId;
    }

    public List<Issue> getIssueRecommendList() {
        return issueRecommendList;
    }

    public void setIssueRecommendList(List<Issue> issueRecommendList) {
        this.issueRecommendList = issueRecommendList;
    }

    public List<CreativeListResult> getCreativeList() {
		return creativeList;
	}

	public List<Publication> getPublicationList() {
		return publicationList;
	}

	public void setPublicationList(List<Publication> publicationList) {
		this.publicationList = publicationList;
	}

	public List<FpageEvent> getEventList() {
		return eventList;
	}

	public void setEventList(List<FpageEvent> eventList) {
		this.eventList = eventList;
	}

	public Integer getEventNum() {
		return eventNum;
	}

	public void setEventNum(Integer eventNum) {
		this.eventNum = eventNum;
	}

	public Integer getM1Num() {
		return m1Num;
	}

	public void setM1Num(Integer m1Num) {
		this.m1Num = m1Num;
	}

	public Integer getPublicationNum() {
		return publicationNum;
	}

	public void setPublicationNum(Integer publicationNum) {
		this.publicationNum = publicationNum;
	}

	public List<Creative> getM1List() {
		return m1List;
	}

	public void setM1List(List<Creative> m1List) {
		this.m1List = m1List;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public List<Creative> getSearchCreativeList() {
		return searchCreativeList;
	}

	public void setSearchCreativeList(List<Creative> searchCreativeList) {
		this.searchCreativeList = searchCreativeList;
	}

	public List<PhoenixArticle> getPhoenixArticleList() {
		return phoenixArticleList;
	}

	public void setPhoenixArticleList(List<PhoenixArticle> phoenixArticleList) {
		this.phoenixArticleList = phoenixArticleList;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
	
    
}
