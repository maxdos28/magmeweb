/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.manager.cache;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import cn.magme.constants.CacheConstants;
import cn.magme.pojo.FamilyCategory;
import cn.magme.service.FamilyCategoryService;

import com.danga.MemCached.MemCachedClient;

/**
 * @author fredy.liu
 * @date 2011-7-27
 * @version $id$
 */
public class FamilyCategoryCacheService{
	
	@Resource
	private FamilyCategoryService familyCategoryService;
	
	@Resource 
	private MemCachedClient memCachedClient;
	
	private static final Logger log=Logger.getLogger(FamilyCategoryCacheService.class);
	/**
	 * 按id查询群族分类
	 * @param id
	 * @return
	 */
	public FamilyCategory queryById(Long id){
		FamilyCategory familyCategory=null;
		try {
			familyCategory=(FamilyCategory)memCachedClient.get(CacheConstants.FAMILY_CATEGORY_PREFIX+id);
		} catch (Exception e) {
			log.error("", e);
		}
		
		if(familyCategory==null){
			familyCategory=familyCategoryService.queryById(id);
		}else{
			return familyCategory;
		}
		try {
			//回设cache
			if(familyCategory!=null){
				this.memCachedClient.set(CacheConstants.FAMILY_CATEGORY_PREFIX+id, familyCategory);
			}
		} catch (Exception e) {
			log.error("", e);
		}
	
		return familyCategory;
		
	}
	
	/**
	 * 按父类id查询子类
	 * 1 状态为1
	 * 2 按升序排列
	 * @param parentId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<FamilyCategory> queryNormalSortByParentId(Long parentId){
		List<FamilyCategory> familyCategoryList=null;
		try {
			familyCategoryList=(List<FamilyCategory>)memCachedClient.get(CacheConstants.FAMILY_CHILDREN_CATEGORY_PREFIX+parentId);
		} catch (Exception e) {
			log.error("", e);
		}
		
		if(familyCategoryList==null || familyCategoryList.size()<=0){
			familyCategoryList=familyCategoryService.queryNormalSortByParentId(parentId);
		}else{
			return familyCategoryList;
		}
		try {
			//回填cache
			if(familyCategoryList!=null && familyCategoryList.size()>0){
				memCachedClient.set(CacheConstants.FAMILY_CHILDREN_CATEGORY_PREFIX+parentId, familyCategoryList);
			}
		} catch (Exception e) {
			log.error("", e);
		}
		return familyCategoryList;
		
	}
	
	
	/**
	 * 查询所有父类
	 * 1 状态为1
	 * 2 按升序排列
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<FamilyCategory> queryNormalSortParentCategory(){
		
		List<FamilyCategory> familyCategoryList=null;
		try {
			familyCategoryList=(List<FamilyCategory>)memCachedClient.get(CacheConstants.FAMILY_PARENT_CATEGORY_PREFIX);
		} catch (Exception e) {
			log.error("", e);
		}
		
		if(familyCategoryList==null || familyCategoryList.size()<=0){
			familyCategoryList=familyCategoryService.queryNormalSortParentCategory();
		}else{
			return familyCategoryList;
		}
		
		try {
			if(familyCategoryList!=null && familyCategoryList.size()>0){
				this.memCachedClient.set(CacheConstants.FAMILY_PARENT_CATEGORY_PREFIX, familyCategoryList);
			}
		} catch (Exception e) {
			log.error("", e);
		}
		return familyCategoryList;
		
	}

	public FamilyCategoryService getFamilyCategoryService() {
		return familyCategoryService;
	}

	public void setFamilyCategoryService(FamilyCategoryService familyCategoryService) {
		this.familyCategoryService = familyCategoryService;
	}

	public void init() {
		//queryNormalSortParentCategory
		memCachedClient.delete(CacheConstants.FAMILY_PARENT_CATEGORY_PREFIX);
		queryNormalSortParentCategory();
		
		//queryNormalSortByParentId
		List<FamilyCategory> familyCategoryList=familyCategoryService.queryNormalSortParentCategory();
		if(familyCategoryList!=null && familyCategoryList.size()>0){
			for(FamilyCategory family:familyCategoryList){
				memCachedClient.delete(CacheConstants.FAMILY_CHILDREN_CATEGORY_PREFIX+family.getId());
				queryNormalSortByParentId(family.getId());
				List<FamilyCategory> childFamilys=familyCategoryService.queryNormalSortByParentId(family.getId());
				//queryById
				for(FamilyCategory child:childFamilys){
					memCachedClient.delete(CacheConstants.FAMILY_CATEGORY_PREFIX+child.getId());
					queryById(child.getId());
				}
			}
		}
		
		//
		
	}
	
	

}
