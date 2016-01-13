package cn.magme.web.manager.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import cn.magme.constants.CacheConstants;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.Category;
import cn.magme.pojo.Issue;
import cn.magme.pojo.IssueComparator;
import cn.magme.pojo.Publication;
import cn.magme.service.CategoryService;
import cn.magme.service.IssueService;
import cn.magme.service.PublicationService;
import cn.magme.util.StringUtil;

import com.danga.MemCached.MemCachedClient;

/**
 * @author fredy.liu
 * @date 2011-5-20
 * @version $id$
 */
public class IssueCacheService {
	
	@Resource
	private IssueService issueService;
	
	@Resource 
	private MemCachedClient memCachedClient;
	@Resource
	private PublicationCacheService publicationCacheService;
	@Resource
	private CategoryService categoryService;
	@Resource
	private PublicationService publicationService;
	
	private static final Logger log=Logger.getLogger(IssueCacheService.class);

	/**
	 * insert 
	 * @param issue
	 * @return
	 */
	public Long insert(Issue issue){
		Long issueId=issueService.insert(issue);
		try {
			if(issueId!=null && issueId>0){
				memCachedClient.set(CacheConstants.ISSUE_PREFIX+issueId, issue);
			}
		} catch (Exception e) {
			log.error("create issue cache error", e);
		}
		return issueId;
	}
	
	
	/**
	 * update
	 * @param issue
	 * @return
	 */
	public int updateById(Issue issue) {
		int updateCount=issueService.updateById(issue);
		try {
			if(updateCount>0){
				memCachedClient.set(CacheConstants.ISSUE_PREFIX+issue.getId(), issue);
			}
		} catch (Exception e) {
			log.error("create issue cache error", e);
		}
		return updateCount;
	}
	
	/**
	 * 通过杂志id更新期刊状态
	 * @param publicaionId
	 * @param status
	 * @return
	 */
	public int updateStatusByPublicationId(Long publicationId,int status){
		int updateCount=issueService.updateStatusByPublicationId( publicationId,status);
		try {
			if(updateCount>0){
				int [] statuses=new int[]{status};
				List<Issue> issuees=issueService.queryByPubIdAndStatuses(publicationId, statuses,-1);
				if(issuees!=null && issuees.size()>0){
					for(Issue issue:issuees){
						memCachedClient.set(CacheConstants.ISSUE_PREFIX+issue.getId(), issue);
					}
				}else{
					log.error("issueService.queryByPubIdAndStatuses(publicationId, statuses)没有找到对应期刊，publicationId"
							+publicationId+" statuses,"+status);
				}
				
			}
		} catch (Exception e) {
			log.error("create issue cache error", e);
		}
		return updateCount;
	}
	
	
	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	public Issue queryById(Long id){
		Issue issue=null;
		try {
			issue=(Issue)memCachedClient.get(CacheConstants.ISSUE_PREFIX+id);
		} catch (Exception e) {
			log.error("", e);
		}
		if(issue==null){
			issue=issueService.queryById(id);
		}else{
			return issue;
		}
		try {
			if(issue!=null){
				memCachedClient.set(CacheConstants.ISSUE_PREFIX+id,issue);
			}
		} catch (Exception e) {
			log.error("", e);
		}
		return issue;
	}

	/**
	 * 查询每种分类中最新的期刊，并且按照1 年月优先 2 杂志固定顺序排列期刊
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Issue> queryLastestIssueByCategoryId(Long categoryId,Integer begin,Integer size){
		List<Issue> issueList=null;
		
		Category category=null;
		try {
			category=categoryService.queryById(categoryId);
			issueList=(List<Issue> )this.memCachedClient.get(CacheConstants.LASTEST_ISSUE_IN_CATEGORY_PREFIX+category.getId());
		} catch (Exception e) {
			log.error("", e);
		}
		if(category==null || category.getId()<=0){
			log.error("类目id为空");
			return null;
		}
		if(issueList==null || issueList.size()<=0 || (size!=null && begin!=null && issueList.size()<size+begin)){
			List<Publication> publicationList=null;
			if(category!=null && category.getParentId()==0){
				//父类杂志
				publicationList=this.publicationService.queryUpShelfByParentCategoryId(category.getId(),null,null);
			}else{
				//查找杂志
				publicationList=this.publicationService.queryUpShelfByCategoryId(category.getId(),null,null);
			}
			
			//查询最新期刊
			issueList=this.queryIssuesByPubs(publicationList);
			//放入cache
			try {
				if(issueList!=null && issueList.size()>0){
					memCachedClient.set(CacheConstants.LASTEST_ISSUE_IN_CATEGORY_PREFIX+category.getId(), issueList);
				}
			} catch (Exception e) {
				log.error("", e);
			}
			
		}
		if(size!=null && begin!=null && size>0 && begin>=0 && issueList!=null && issueList.size()>0){
			int end=begin+size;
			issueList=issueList.subList(begin, end>issueList.size()?issueList.size():end);
		}
		return issueList;
	}
	
	
	
	/**
	 * 通过杂志id查询
	 * @param publicationId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Issue> queryNormalByPubIdAndStatuses(Long publicationId){
		List<Issue> issueList=null;
		try {
			issueList=(List<Issue>)this.memCachedClient.get(CacheConstants.ISSUE_IN_PUBLICATION_PREFIX+publicationId);
		} catch (Exception e) {
			log.error("",e);
		}
		if(issueList==null || issueList.size()<=0 ){
			issueList=this.issueService.queryByPubIdAndStatuses(publicationId,new int[]{PojoConstant.PUBLICATION.STATUS.ON_SHELF.getCode()},-1);
		}else{
			return issueList;
		}
		try {
			if(issueList!=null && issueList.size()>0){
				memCachedClient.set(CacheConstants.ISSUE_IN_PUBLICATION_PREFIX+publicationId, issueList);
			}
		} catch (Exception e) {
			log.error("", e);
		}
		return issueList;
	}

	public void init() {
         //所有类目中的期刊queryAllNormalIssues
		this.memCachedClient.delete(CacheConstants.ALL_ISSUE_PREFIX);
		this.queryAllNormalIssues(null, null);
		
		//类目中的期刊queryAllNormalByCategory
		List<Category> categoryList=categoryService.queryAllChildCategories();
		List<Category> parentCategory=this.categoryService.queryAllParentCategory();
		if(categoryList!=null && categoryList.size()>0){
			for(Category category:categoryList){
				memCachedClient.delete(CacheConstants.ISSUE_IN_CATEGORY_PREFIX+category.getId());
				this.queryAllNormalByCategory(category.getId(),null, null);
			}
		}
		
		if(parentCategory!=null && parentCategory.size()>0){
			for(Category category:parentCategory){
				memCachedClient.delete(CacheConstants.ISSUE_IN_CATEGORY_PREFIX+category.getId());
				this.queryAllNormalByCategory(category.getId(),null, null);
			}
		}
		//queryByInitLetter
		for(char i='a';i<='z';i++){
			this.memCachedClient.delete(CacheConstants.ISSUE_IN_LETTER_PREFIX+i);
			this.queryByInitLetter(String.valueOf(i), null, null);
		}
		
		//queryNormalByPubIdAndStatuses
		List<Publication> publicationList=this.publicationCacheService.queryAllNormalPubs(null, null);
		if(publicationList!=null && publicationList.size()>0){
			for(Publication pub:publicationList){
				this.memCachedClient.delete(CacheConstants.ISSUE_IN_PUBLICATION_PREFIX+pub.getId());
				this.queryNormalByPubIdAndStatuses(pub.getId());
			}
		}
		
		//queryLastestIssueByCategoryId
		if(categoryList!=null && categoryList.size()>0){
			//子类
			for(Category category:categoryList){
				memCachedClient.delete(CacheConstants.LASTEST_ISSUE_IN_CATEGORY_PREFIX+category.getId());
				this.queryLastestIssueByCategoryId(category.getId(),null, null);
			}
		}
		
		if(parentCategory!=null && parentCategory.size()>0){
			//父类
			for(Category category:parentCategory){
				memCachedClient.delete(CacheConstants.LASTEST_ISSUE_IN_CATEGORY_PREFIX+category.getId());
				this.queryLastestIssueByCategoryId(category.getId(),null, null);
			}
		}
		
	}
	
	
	/**
	 * 查询所有的期刊
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Issue> queryAllNormalIssues(Integer begin,Integer size){
		List<Issue> issueList=null;
		try {
			issueList=(List<Issue> )this.memCachedClient.get(CacheConstants.ALL_ISSUE_PREFIX);
		} catch (Exception e) {
			log.error("", e);
		}
		
		if(issueList==null || issueList.size()<=0 || (size!=null && begin!=null && issueList.size()<size+begin)){
			//查找杂志
			List<Publication> publicationList=this.publicationService.queryAllNormalPubs(null,null);
			//查询最新期刊
			issueList=this.queryIssuesByPubs(publicationList);
			try {
				if(issueList!=null && issueList.size()>0){
					//把所有放入cache
					memCachedClient.set(CacheConstants.ALL_ISSUE_PREFIX, issueList);
				}
			} catch (Exception e) {
				log.error("", e);
			}
		}
		
		if(size!=null && begin!=null && size>0 && begin>=0 && issueList!=null && issueList.size()>0){
			int end=begin+size;
			issueList=issueList.subList(begin, end>issueList.size()?issueList.size():end);
		}
		return issueList;
	}
	@SuppressWarnings("unchecked")
	public List<Issue> queryAllNormalByCategory(Long categoryId,Integer begin,Integer size){
		List<Issue> issueList=null;
		try {
			issueList=(List<Issue> )this.memCachedClient.get(CacheConstants.ISSUE_IN_CATEGORY_PREFIX+categoryId);
		} catch (Exception e) {
			log.error("", e);
		}
		
		if(issueList==null || issueList.size()<=0  || (size!=null && begin!=null && issueList.size()<size+begin)){
			//查找杂志
			List<Publication> publicationList=this.publicationService.queryUpShelfByCategoryId(categoryId, null, null);
			//查询最新期刊
			issueList=this.queryIssuesByPubs(publicationList);
			try {
				if(issueList!=null && issueList.size()>0){
					memCachedClient.set(CacheConstants.ISSUE_IN_CATEGORY_PREFIX+categoryId, issueList);
				}
			} catch (Exception e) {
				log.error("", e);
			}
		}
		if(size!=null && begin!=null && size>0 && begin>=0 && issueList!=null && issueList.size()>0){
			int end=begin+size;
			issueList=issueList.subList(begin, end>issueList.size()?issueList.size():end);
		}
		return issueList;
		
	}
	@SuppressWarnings("unchecked")
	public List<Issue> queryByInitLetter(String letter,Integer begin,Integer size){
		
		if(StringUtil.isBlank(letter) || letter.length()!=1){
			return null;
		}
		
		List<Issue> issueList=null;
		try {
			issueList=(List<Issue> )this.memCachedClient.get(CacheConstants.ISSUE_IN_LETTER_PREFIX+letter);
		} catch (Exception e) {
			log.error("", e);
		}
		
		if(issueList==null || issueList.size()<=0 || (size!=null && begin!=null && issueList.size()<size+begin)){
			//查找杂志
			List<Publication> publicationList=this.publicationService.queryByInitLetter(letter,null,null);
			//查询最新期刊
			issueList=this.queryIssuesByPubs(publicationList);
			//放回cache
			try {
				if(issueList!=null && issueList.size()>0){
					memCachedClient.set(CacheConstants.ISSUE_IN_LETTER_PREFIX,issueList);
				}
			} catch (Exception e) {
				log.error("", e);
			}
		}
		
		if(size!=null && begin!=null && size>0 && begin>=0 && issueList!=null && issueList.size()>0){
			int end=begin+size;
			issueList=issueList.subList(begin, end>issueList.size()?issueList.size():end);
		}else{
			log.error("no data in letter:"+letter);
		}
		return issueList;
	}
	
	
	private List<Issue> queryIssuesByPubs(List<Publication> publicationList){
		if(publicationList==null || publicationList.size()<=0){
			return null;
		}
		//查询最新期刊
		List<Issue> issueList=new ArrayList<Issue>();
		if(publicationList!=null && publicationList.size()>0){
			for(Publication publication:publicationList){
				Issue issue=this.issueService.queryLastestIssueByPubId(publication.getId());
				if(issue!=null){
					issue.setSortOrder(publication.getSortOrder());
					issueList.add(issue);
				}else{
					log.error("找不到杂志对应的期刊，杂志id："+publication.getId());
				}
			}
		}
		//期刊排序
		Collections.sort(issueList, new IssueComparator());
		return issueList;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Issue> queryRecommendPubsByLimits(int limit){
		if(limit<=0){
			return null;
		}
		List<Issue> issueList=null;
		try {
			 issueList=(List<Issue>)memCachedClient.get(CacheConstants.RECOMMEND_PUBS_PREFIX);
		} catch (Exception e) {
			log.error("", e);
		}
		
		if(issueList==null || issueList.size()<=0){
			List<Publication> pubsList=this.publicationService.queryRecommendPubsByLimits(limit);
			issueList=new ArrayList<Issue>();
			if(pubsList!=null && pubsList.size()>0){
				for(Publication pub:pubsList){
					issueList.add(this.issueService.queryLastestIssueByPubId(pub.getId()));
				}
			}
			
			try {
				if(issueList!=null &&  issueList.size()>0){
					memCachedClient.set(CacheConstants.RECOMMEND_PUBS_PREFIX, issueList);
				}
			} catch (Exception e) {
				log.error("", e);
			}
		}
		
		return issueList;
		
	}

}
