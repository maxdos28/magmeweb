/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.publish;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.pojo.Category;
import cn.magme.pojo.FamilyCategory;
import cn.magme.pojo.FamilyIssue;
import cn.magme.pojo.Issue;
import cn.magme.service.CategoryService;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;
import cn.magme.web.entity.FamilyNameMapEntity;
import cn.magme.web.manager.cache.FamilyCategoryCacheService;
import cn.magme.web.manager.cache.IssueCacheService;

/**
 * @author fredy.liu
 * @date 2011-5-30
 * @version $id$
 */
@Results({@Result(name="catSuccess",location="/WEB-INF/pages/publish/kanmiCategory.ftl")})
public class KmFamilyIssueAction extends BaseAction{
	
	
	
	/*
	@Resource
	private FamilyIssueService familyIssueService;@Resource
	private SysParameterService sysParameterService;
	
	private final static int FIRST_LINE=6;
	
	private final static int SECOND_LINE=12;
	
	private final static int THIRD_LINE=18;
	
	private final static String FAMILY_ISSUE_LIMIT_KEY="family_issue_limit_key";
	private List<Map> familyIssuesMapList;
	
	


	public List<Map> getFamilyIssuesMapList() {
		return familyIssuesMapList;
	}

	public void setFamilyIssuesMapList(List<Map> familyIssuesMapList) {
		this.familyIssuesMapList = familyIssuesMapList;
	}
	@Resource
	private IssueService issueService; 
	*/
	
	@Resource
	private CategoryService categoryService;
	
	private static final String CAT_SUCCESS="catSuccess";
	@Resource
	private FamilyCategoryCacheService familyCategoryCacheService;
	
	@Resource
	private IssueCacheService issueCacheService;
	
	private static final String SLASH_SEPARETOR="/";
	
	private static final Logger log=Logger.getLogger(KmFamilyIssueAction.class);
	
	private String menuId="kanmi";
	
	/**
	 * 杂志推荐数目
	 */
	//private static final int RECOMMEND_LIMIT=13;
	
	private static final int NEED_RECOMMEND_THRESHOLD=2*7;
	
	
	
	/*private Map<String,String> familyNameMap=new HashMap<String,String>();
	//初始化map
	{
		familyNameMap.put("car", "汽车");
		familyNameMap.put("fashion", "时尚");
		familyNameMap.put("beauty", "美女");
	}*/

	private FamilyNameMapEntity familyNameMapEntity;
	/**
	 * 
	 */
	private static final long serialVersionUID = -3498406089729758398L;
	
	/*public String execute(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setHeader("Cache-Control", "max-age=" + systemProp.getPageCacheTimeout()); 
		try {
			SysParameter sysParameter=sysParameterService.queryByParamKey(FAMILY_ISSUE_LIMIT_KEY);
			Integer familyIssueLimitNum= Integer.valueOf(sysParameter.getParamValue());
			this.generateHeadInfo();
			//查询所有群族中的数据
			if(StringUtil.isBlank(this.familyId)){
				this.familyId="car";
			}
			//if(familyNames!=null && familyNames.size()>0){
			familyIssuesMap=new HashMap<String,List<FamilyIssue>>();
			//for(String familyName:familyNames){
			List<FamilyIssue> familyIssues=familyIssueService.queryByFamilyNameAndLimit(this.familyId,familyIssueLimitNum);
			if(familyIssues==null || familyIssues.size()<1){
				return SUCCESS;
			}
			this.familyName=familyIssues.get(0).getFamilyName();
			//给familyIssues填入信息
			for(FamilyIssue familyIssue:familyIssues){
				Issue issue=this.issueService.queryById(Long.parseLong(familyIssue.getIssueId()));
				if(issue!=null){
					familyIssue.setIssueNum(issue.getIssueNumber());
					//familyIssue.setLinkUrl()
					familyIssue.setPublicationId(String.valueOf(issue.getPublicationId()));
					familyIssue.setStatus(issue.getStatus());
					familyIssue.setTitle(issue.getPublicationName());
				}
				
			}
			//familyIssuesMap.put(familyName, familyIssues);
			familyIssuesMapList=new ArrayList<Map>();
			int size=familyIssues.size();
			
			//第一行
			Map<String,List<FamilyIssue>>  familyIssuesMapList1=new HashMap<String,List<FamilyIssue>>();
			familyIssuesMapList1.put(familyName, familyIssues.subList(0, size>FIRST_LINE?FIRST_LINE:size));
			familyIssuesMapList.add(familyIssuesMapList1);
			
			//第二行
			if(size>FIRST_LINE){
				Map<String,List<FamilyIssue>>  familyIssuesMapList2=new HashMap<String,List<FamilyIssue>>();
				familyIssuesMapList2.put(familyName, familyIssues.subList(FIRST_LINE, size>SECOND_LINE?SECOND_LINE:size));
				familyIssuesMapList.add(familyIssuesMapList2);
			}
			
			//第三行
			if(size>SECOND_LINE){
				Map<String,List<FamilyIssue>>  familyIssuesMapList3=new HashMap<String,List<FamilyIssue>>();
				familyIssuesMapList3.put(familyName, familyIssues.subList(SECOND_LINE, size>THIRD_LINE?THIRD_LINE:size));
				familyIssuesMapList.add(familyIssuesMapList3);
			}
			
			
		} catch (Exception e) {
			log.error("", e);
		}
		return SUCCESS;
		
	}*/
	
	public String kmCategoryJson(){
		this.jsonResult=new JsonResult();
		try {
			this.generateHeadInfo();
			//没有传类目表示全部
			if(categoryId==null && StringUtil.isBlank(this.letter)){
				issueList=this.issueCacheService.queryAllNormalIssues(begin,size);
			}else if(categoryId!=null ){
				issueList = this.issueCacheService.queryLastestIssueByCategoryId(categoryId,begin,size);
			}else if(StringUtil.isNotBlank(this.letter)){
				issueList=this.issueCacheService.queryByInitLetter(letter,begin,size);
			}else{
				//not thing to do
			}
			//查询推荐杂志
			int RECOMMEND_LIMIT=13;
			if(issueList!=null && issueList.size()<=NEED_RECOMMEND_THRESHOLD){
				if(issueList.size()<=7){
					RECOMMEND_LIMIT=13;
				}else{
					RECOMMEND_LIMIT=6;
				}
				recommendIssues=this.issueCacheService.queryRecommendPubsByLimits(RECOMMEND_LIMIT);
			}
			
			this.jsonResult.put("issueList", issueList);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		} catch (Exception e) {
			log.error("", e);
			this.jsonResult.setCode(JsonResult.CODE.FAILURE);
			this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		}
		return JSON;
	}
	
	public String kanmi2(){
		this.kmCategoryJson();
		return CAT_SUCCESS;
		
	}
	
	/**
	 * 类目id
	 */
	private Long categoryId;
	
	/**
	 * 返回的数据
	 */
	private List<Issue> issueList;
	
	private String letter;
	
	private String familyId;
	
	/**
	 * 请求的开始行
	 */
	private Integer begin=0;
	
	/**
	 * 请求总行数
	 */
	private Integer size=35;
	
	
	

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

	public FamilyNameMapEntity getFamilyNameMapEntity() {
		return familyNameMapEntity;
	}

	public void setFamilyNameMapEntity(FamilyNameMapEntity familyNameMapEntity) {
		this.familyNameMapEntity = familyNameMapEntity;
	}

	public String getFamilyId() {
		return familyId;
	}

	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}

	

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public List<Issue> getIssueList() {
		return issueList;
	}

	public void setIssueList(List<Issue> issueList) {
		this.issueList = issueList;
	}

	/**
	 * 所有的父类目
	 */
	private List<Category> parentCategories;
	/**
	 * 所有的子类目
	 */
	private Map<String,List<Category>> childrenCategoryMap;
	/**
	 * 群族信息
	 */
	private Map<String,List<FamilyIssue>> familyIssuesMap;
	/**
	 * 群族名称
	 */
	private List<String> familyNames;
	
	/**
	 * 群族父类id
	 */
	private String familyCategoryParentId;
	
	/**
	 * 用于上方显示群族的map
	 * key familyCategoryParentId value 子类组合名称
	 */
	private Map<String,String> familyCategoryParentMap;
	
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

	public List<Category> getParentCategories() {
		return parentCategories;
	}
	public void setParentCategories(List<Category> parentCategories) {
		this.parentCategories = parentCategories;
	}
	
	
	
	public Map<String, List<Category>> getChildrenCategoryMap() {
		return childrenCategoryMap;
	}
	public void setChildrenCategoryMap(
			Map<String, List<Category>> childrenCategoryMap) {
		this.childrenCategoryMap = childrenCategoryMap;
	}
	public Map<String, List<FamilyIssue>> getFamilyIssuesMap() {
		return familyIssuesMap;
	}
	public void setFamilyIssuesMap(Map<String, List<FamilyIssue>> familyIssuesMap) {
		this.familyIssuesMap = familyIssuesMap;
	}
	public List<String> getFamilyNames() {
		return familyNames;
	}
	public void setFamilyNames(List<String> familyNames) {
		this.familyNames = familyNames;
	}
	

	public String getFamilyCategoryParentId() {
		return familyCategoryParentId;
	}

	public void setFamilyCategoryParentId(String familyCategoryParentId) {
		this.familyCategoryParentId = familyCategoryParentId;
	}

	public Map<String, String> getFamilyCategoryParentMap() {
		return familyCategoryParentMap;
	}

	public void setFamilyCategoryParentMap(
			Map<String, String> familyCategoryParentMap) {
		this.familyCategoryParentMap = familyCategoryParentMap;
	}

	private void generateHeadInfo(){
		//查询父类
		parentCategories=categoryService.queryAllParentCategory();
		//查询子类目
		childrenCategoryMap=new HashMap<String,List<Category>>();
		for(Category category:parentCategories){
			//查询子类 用于类目显示
			this.childrenCategoryMap.put(category.getName(),categoryService.queryAllChildrenByParentId(category.getId()));
		} 
		List<FamilyCategory> familyParentCategoryList=familyCategoryCacheService.queryNormalSortParentCategory();
		//没有传入familyCategoryParentId，使用默认的第一个
		/*if(StringUtil.isBlank(familyCategoryParentId) && (categoryId==null || categoryId<=0) && StringUtil.isBlank(this.letter)){
			if(familyParentCategoryList!=null && familyParentCategoryList.size()>0){
				int position=(int)(Math.random()*familyParentCategoryList.size());
			    familyCategoryParentId=String.valueOf(familyParentCategoryList.get(position).getId());
			}
		}*/
		familyCategoryParentMap=new TreeMap<String,String>();
		//看米头部显示
		if(familyParentCategoryList!=null && familyParentCategoryList.size()>0){
			for(FamilyCategory familyCategory:familyParentCategoryList){
				StringBuilder assblebleCategoryName=new StringBuilder("");
				List<FamilyCategory> childCategoryList=familyCategoryCacheService.queryNormalSortByParentId(familyCategory.getId());
				for(FamilyCategory childCategory:childCategoryList){
					assblebleCategoryName.append(childCategory.getName()).append(SLASH_SEPARETOR);
				}
				if(StringUtil.isNotBlank(assblebleCategoryName.toString())){
					familyCategoryParentMap.put(String.valueOf(familyCategory.getId()), 
							assblebleCategoryName.toString().substring(0,assblebleCategoryName.toString().length()-1));
				}
				
			}
		}
	}

	
	
	

}
