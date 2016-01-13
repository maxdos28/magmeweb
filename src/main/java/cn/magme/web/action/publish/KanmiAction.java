/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.publish;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
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
import cn.magme.web.manager.cache.FamilyCategoryCacheService;
import cn.magme.web.manager.cache.FamilyIssueCacheService;
import cn.magme.web.manager.cache.IssueCacheService;

/**
 * @author fredy.liu
 * @date 2011-7-27
 * @version $id$
 */
@Results({@Result(name="success",location="/WEB-INF/pages/publish/kanmi2.ftl")})
public class KanmiAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6525333073843294729L;
	@Resource
	private FamilyCategoryCacheService familyCategoryCacheService;
	@Resource
	private FamilyIssueCacheService familyIssueCacheService;
	@Resource
	private IssueCacheService issueCacheService;
	/*@Resource
	private PublicationCacheService publicationCacheService;*/
	
	
	@Resource
	private CategoryService categoryService;
	
	private static final String SLASH_SEPARETOR="/";
	
	private static final Logger log=Logger.getLogger(KanmiAction.class);
	
	public String execute(){
		 HttpServletResponse response = ServletActionContext.getResponse();
	     response.setHeader("Cache-Control", "max-age=" + systemProp.getPageCacheTimeout()); 
		try {
			
			this.generateHeadInfo();
			//familyCategory子类
			List<FamilyCategory> familyCategoryList=familyCategoryCacheService.queryNormalSortByParentId(Long.valueOf(familyCategoryParentId));
			familyIssueMap=new LinkedHashMap<String,List<FamilyIssue>>();
			lastestIssueMap=new HashMap<String,List<Issue>>();
			
			
			for(FamilyCategory familyCategory:familyCategoryList){
				//头部标签
				
				List<FamilyIssue> familyIssueList=familyIssueCacheService.queryByFamilyCategoryId(familyCategory.getId());
				//左侧编辑的期刊
				familyIssueMap.put(String.valueOf(familyCategory.getId()), familyIssueList);
				
				//右侧显示的期刊
				lastestIssueMap.put(String.valueOf(familyCategory.getId()), 
						issueCacheService.queryLastestIssueByCategoryId(familyCategory.getCategoryid(),1,null));
			}
			
		} catch (Exception e) {
			log.error("", e);
		}
		
		return "success";
	}
	
	/**
	 * 查询往期期刊
	 * @return
	 */
	public String reviewIssuesJson(){
		this.jsonResult=new JsonResult();
		if(this.issueId==null || this.issueId<=0){
			this.jsonResult.setCode(JsonResult.CODE.FAILURE);
			this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
			return JSON;
		}
		try {
			Issue issue=this.issueCacheService.queryById(this.issueId);
			if(issue!=null && issue.getPublicationId()>0){
				List<Issue> issueList=issueCacheService.queryNormalByPubIdAndStatuses(issue.getPublicationId());
				this.jsonResult.put("issueList", issueList);
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);	
			}else{
				this.jsonResult.setCode(JsonResult.CODE.FAILURE);
				this.jsonResult.setMessage("找不到对应的期刊");
			}
			
		} catch (Exception e) {
			this.jsonResult.setCode(JsonResult.CODE.EXCEPTION);
			this.jsonResult.setMessage(JsonResult.MESSAGE.EXCEPTION);
			log.error("", e);
		}
		return JSON;
	}
	
	
	
	
	/**
	 * 群族父类id
	 */
	private String familyCategoryParentId;
	
	/**
	 * 左侧显示的群族期刊
	 */
	private Map<String,List<FamilyIssue>> familyIssueMap;
	
	/**
	 * 右侧显示的最新期刊
	 */
	private Map<String,List<Issue>> lastestIssueMap;
	
	/**
	 * 用于上方显示群族的map
	 * key familyCategoryParentId value 子类组合名称
	 */
	private Map<String,String> familyCategoryParentMap;
	
	/**
	 * 所有的父类目
	 */
	private List<Category> parentCategories;
	/**
	 * 所有的子类目
	 */
	private Map<String,List<Category>> childrenCategoryMap;

	/**
	 * 期刊id
	 */
	private Long issueId;
	
	

	public Long getIssueId() {
		return issueId;
	}

	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}

	public String getFamilyCategoryParentId() {
		return familyCategoryParentId;
	}

	public void setFamilyCategoryParentId(String familyCategoryParentId) {
		this.familyCategoryParentId = familyCategoryParentId;
	}

	public Map<String, List<FamilyIssue>> getFamilyIssueMap() {
		return familyIssueMap;
	}

	public void setFamilyIssueMap(Map<String, List<FamilyIssue>> familyIssueMap) {
		this.familyIssueMap = familyIssueMap;
	}

	public Map<String, List<Issue>> getLastestIssueMap() {
		return lastestIssueMap;
	}

	public void setLastestIssueMap(Map<String, List<Issue>> lastestIssueMap) {
		this.lastestIssueMap = lastestIssueMap;
	}

	public Map<String, String> getFamilyCategoryParentMap() {
		return familyCategoryParentMap;
	}

	public void setFamilyCategoryParentMap(
			Map<String, String> familyCategoryParentMap) {
		this.familyCategoryParentMap = familyCategoryParentMap;
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
		if(StringUtil.isBlank(familyCategoryParentId)){
			if(familyParentCategoryList!=null && familyParentCategoryList.size()>0){
				int position=(int)(Math.random()*familyParentCategoryList.size());
				familyCategoryParentId=String.valueOf(familyParentCategoryList.get(position).getId());
			}
			
		}
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
