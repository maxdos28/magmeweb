/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.manager.cache;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import cn.magme.constants.CacheConstants;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.FamilyCategory;
import cn.magme.pojo.FamilyIssue;
import cn.magme.pojo.Issue;
import cn.magme.pojo.ext.CategoryIdRenderBean;
import cn.magme.service.CategoryService;
import cn.magme.service.FamilyCategoryService;
import cn.magme.service.FamilyIssueService;
import cn.magme.service.IssueService;

import com.danga.MemCached.MemCachedClient;

/**
 * @author fredy.liu
 * @date 2011-7-27
 * @version $id$
 */
public class FamilyIssueCacheService{
	@Resource
	private FamilyIssueService familyIssueService;
	
	@Resource 
	private MemCachedClient memCachedClient;
	@Resource 
	private IssueService issueService;
	@Resource
	private CategoryService categoryService;
	@Resource
	private FamilyCategoryService familyCategoryService;
	
	private static final Logger log=Logger.getLogger(FamilyIssueCacheService.class);
	
	@SuppressWarnings("unchecked")
	public List<FamilyIssue> queryByFamilyCategoryId(Long familyCategoryId){
		List<FamilyIssue> familyIssueList=null;
		try {
			familyIssueList=(List<FamilyIssue>)this.memCachedClient.get(CacheConstants.FAMILY_ISSUE_PREFIX+familyCategoryId);
		} catch (Exception e) {
			log.error("", e);
		}
		if(familyIssueList==null || familyIssueList.size()<=0){
			familyIssueList=familyIssueService.queryByFamilyCIdAndStatus(familyCategoryId, PojoConstant.FAMILYISSUE.STATUS.VALID.getCode());
			//给issue设值
			for(int i=0;i<familyIssueList.size();i++){
				FamilyIssue familyIssue=familyIssueList.get(i);
				Issue issue=this.issueService.queryById(Long.parseLong(familyIssue.getIssueId()));
				if(issue==null || issue.getStatus()!=PojoConstant.ISSUE.STATUS.ON_SALE.getCode()){
					familyIssueList.remove(i);
					continue;
				}
				if(issue!=null){
					familyIssue.setIssueNum(issue.getIssueNumber());
					familyIssue.setPublicationId(String.valueOf(issue.getPublicationId()));
					familyIssue.setStatus(issue.getStatus());
					familyIssue.setTitle(issue.getPublicationName());
					familyIssue.setSubscribeNum(issue.getSubscribeNum());
					familyIssue.setFavoriteNum(issue.getFavoriteNum());
					familyIssue.setPublicationName(issue.getPublicationName());
				}
				
			}
		}else{
			//cache有值，走cache
			return familyIssueList;
		}
		
		try {
			if(familyIssueList!=null && familyIssueList.size()>0){
				this.memCachedClient.set(CacheConstants.FAMILY_ISSUE_PREFIX+familyCategoryId,familyIssueList);
			}
		} catch (Exception e) {
			log.error("", e);
		}
		return familyIssueList;
		
	}


	public void init() {
		List<CategoryIdRenderBean> categoryList=familyCategoryService.getAllChildIds();
		if(categoryList!=null && categoryList.size()>0){
			for(CategoryIdRenderBean category:categoryList){
				memCachedClient.delete(CacheConstants.FAMILY_ISSUE_PREFIX+category.getId());
				queryByFamilyCategoryId(Long.valueOf(category.getId()));
			}
		}
		
	}

	
}
