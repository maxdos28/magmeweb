/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.manager.cache;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import cn.magme.common.JsonResult;
import cn.magme.constants.CacheConstants;
import cn.magme.pojo.Category;
import cn.magme.pojo.Publication;
import cn.magme.service.CategoryService;
import cn.magme.service.LuceneService;
import cn.magme.service.PublicationService;

import com.danga.MemCached.MemCachedClient;

/**
 * @author fredy.liu
 * @date 2011-5-19
 * @version $id$
 */
public class PublicationCacheService {
	@Resource 
	private PublicationService publicationService;
	
	@Resource 
	private MemCachedClient memCachedClient;
	@Resource
	private LuceneService luceneService;
	@Resource
	private CategoryService categoryService;
	
	
	private static final Logger log=Logger.getLogger(PublicationCacheService.class);
	
	/**
	 * 查找publication
	 * @param publicationId
	 * @return
	 */
	public Publication queryById(Long publicationId){
		//从cache中获取
		Publication pub=null;
		try {
			pub=(Publication)memCachedClient.get(CacheConstants.PUBLICATION_PREFIX+publicationId);
		} catch (Exception e) {
			log.error("从cache中获取杂志失败，杂志id："+publicationId, e);
		}
		if(pub==null){
			pub=publicationService.queryById(publicationId);
		}else{
			return pub;
		}
		try {
			if(pub!=null){
				memCachedClient.set(CacheConstants.PUBLICATION_PREFIX+publicationId, pub);
			}
		} catch (Exception e) {
			log.error("写入cache失败，杂志id："+publicationId, e);
		}
		return pub;
		
	}
	
	/**
	 * 增加杂志
	 * @param publication
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JsonResult addPublication(Publication publication){
		//插入数据
		JsonResult jsonResult=publicationService.addPublication(publication);
		try {
			//更新cache
			memCachedClient.set(CacheConstants.PUBLICATION_PREFIX+publication.getId(), publication);
			//更新搜索索引
			//List publicationList = publicationService.getAll();
		} catch (Exception e) {
			log.error("从cache中写入杂志失败", e);
		}
		return jsonResult;
	}
	
	/**
	 * 出版商删除自己的杂志 (新版中杂志后台下架）
	 * @param pub
	 * @param publisherId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JsonResult delByPublisher(Long publicationId,Long publisherId){
		try {
			memCachedClient.delete(CacheConstants.PUBLICATION_PREFIX+publicationId);
		} catch (Exception e) {
			log.error("从cache中删除杂志失败", e);
		}
		JsonResult jsonResult=publicationService.delByPublisher(publicationId, publisherId);
//		try {
//			//更新搜索索引
//			List publicationList = publicationService.getAll();
//			luceneService.createIndex(publicationList);
//		} catch (Exception e) {
//			log.error("删除杂志更新索引失败", e);
//		}
		return jsonResult;
		
	}
	
	/**
	 * 杂志下架
	 * @param publicationId
	 * @param publisherId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JsonResult delByPublisherDown_Shelf(Long publicationId,Long publisherId){
		try {
			memCachedClient.delete(CacheConstants.PUBLICATION_PREFIX+publicationId);
		} catch (Exception e) {
			log.error("从cache中删除杂志失败", e);
		}
		JsonResult jsonResult=publicationService.delByPublisherDown_Shelf(publicationId, publisherId);
		return jsonResult;
		
	}
	
	/**
	 * 杂志上架 恢复用户订阅
	 * @param publicationId
	 * @param publisherId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JsonResult restoreByPublisher(Long publicationId,Long publisherId){
		JsonResult jsonResult=publicationService.restoreByPublisher(publicationId, publisherId);
		Publication pub=publicationService.queryById(publicationId);
		try {
			if(pub!=null){
				memCachedClient.set(CacheConstants.PUBLICATION_PREFIX+publicationId, pub);
			}
		} catch (Exception e) {
			log.error("写入cache失败，杂志id："+publicationId, e);
		}
		return jsonResult;
		
	}
	
	/**
	 * 通过id和publisherid来更新杂志
	 * @param publication
	 * @param id
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JsonResult updateByIdAndPublisherId(Publication publication){
		JsonResult jsonResult=publicationService.updateByIdAndPublisherId(publication);
		try {
			memCachedClient.set(CacheConstants.PUBLICATION_PREFIX+publication.getId(),
					this.publicationService.queryById(publication.getId()));
			
			//List publicationList = publicationService.getAll();
			//luceneService.createIndex(publicationList);
		} catch (Exception e) {
			log.error("从cache中增加杂志失败", e);
		}
		return jsonResult;
	}
	
	/**
	 * 更新状态
	 * @param publicationId
	 * @param status
	 * @return
	 */
	public JsonResult updateStatusNoLimitById(Long publicationId,int status){
		/*try {
			memCachedClient.delete(PUBLICATION_PREFIX+publicationId);
		} catch (Exception e) {
			log.error("从cache中删除杂志失败", e);
		}*/
		JsonResult jsonResult=publicationService.updateStatusNoLimitById(publicationId,status);
		try {
			if(jsonResult.getCode()==JsonResult.CODE.SUCCESS){
				memCachedClient.set(CacheConstants.PUBLICATION_PREFIX+publicationId,
						this.publicationService.queryById(publicationId));
			}
			
		} catch (Exception e) {
			log.error("从cache中增加杂志失败", e);
		}
		return jsonResult;
	}
	
	/**
	 * 通过类目id查询杂志
	 * @param categoryId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Publication> queryByCategoryId(Long categoryId,Integer begin,Integer size){
		List<Publication> publicationList=null;
		
		try {
			publicationList=(List<Publication>)this.memCachedClient.get(CacheConstants.PUBLICATION_IN_CATEGORY_PREFIX+categoryId);
		} catch (Exception e) {
			log.error("", e);
		}
		
		if(publicationList==null || publicationList.size()<=0  || (size!=null && begin!=null && publicationList.size()<size+begin)){
			publicationList=this.publicationService.queryUpShelfByCategoryId(categoryId,null,null);
			try {
				//放回cache
				if(publicationList!=null && publicationList.size()>0){
					memCachedClient.set(CacheConstants.PUBLICATION_IN_CATEGORY_PREFIX+categoryId, publicationList);
				}
			} catch (Exception e) {
				log.error("", e);
			}
			
		}
		
		if(size!=null && begin!=null && size>0 && begin>=0 && publicationList!=null && publicationList.size()>0){
			int end=begin+size;
			publicationList=publicationList.subList(begin, end>publicationList.size()?publicationList.size():end);
		}
		return publicationList;
	}
	
	
	/**
	 * 通过父类目id查询杂志
	 * @param categoryId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Publication> queryByParentCategoryId(Long categoryId,Integer begin,Integer size){
		List<Publication> publicationList=null;
		
		try {
			publicationList=(List<Publication>)this.memCachedClient.get(CacheConstants.PUBLICATION_IN_CATEGORY_PREFIX+categoryId);
		} catch (Exception e) {
			log.error("", e);
		}
		
		if(publicationList==null || publicationList.size()<=0 || (size!=null && begin!=null && publicationList.size()<size+begin)){
			publicationList=this.publicationService.queryUpShelfByParentCategoryId(categoryId,null,null);
			try {
				//放回cache
				if(publicationList!=null && publicationList.size()>0){
					memCachedClient.set(CacheConstants.PUBLICATION_IN_CATEGORY_PREFIX+categoryId, publicationList);
				}
			} catch (Exception e) {
				log.error("", e);
			}
		}
		if(size!=null && begin!=null && size>0 && begin>=0&& publicationList!=null && publicationList.size()>0){
			int end=begin+size;
			publicationList=publicationList.subList(begin, end>publicationList.size()?publicationList.size():end);
		}
		return publicationList;
	}

	public void init() {

		//分类中的杂志 queryByCategoryId
		List<Category> categoryList=categoryService.queryAllChildCategories();
		if(categoryList!=null && categoryList.size()>0){
			for(Category category:categoryList){
				this.memCachedClient.delete(CacheConstants.PUBLICATION_IN_CATEGORY_PREFIX+category.getId());
				this.queryByCategoryId(category.getId(),null, null);
			}
		}
		List<Category> parentCategory=this.categoryService.queryAllParentCategory();
		if(parentCategory!=null && parentCategory.size()>0){
			//父类
			for(Category category:parentCategory){
				this.memCachedClient.delete(CacheConstants.PUBLICATION_IN_CATEGORY_PREFIX+category.getId());
				this.queryByParentCategoryId(category.getId(),null, null);
			}
		}
		
		//所有正常的杂志queryAllNormalPubs
		this.memCachedClient.delete(CacheConstants.ALL_PUBLICATION_PREFIX);
		queryAllNormalPubs(null,null);
		
		//queryByInitLetter
		for(char i='a';i<='z';i++){
			this.memCachedClient.delete(CacheConstants.PUBLICATION_IN_LETTER_PREFIX+i);
			this.queryByInitLetter(String.valueOf(i), null, null);
		}
		
		
	}
	
	/**
	 * 查询所有的期刊
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Publication> queryAllNormalPubs(Integer begin,Integer size){
		//======================查询杂志
		List<Publication> publicationList=null;
		try {
			publicationList=(List<Publication>)this.memCachedClient.get(CacheConstants.ALL_PUBLICATION_PREFIX);
		} catch (Exception e) {
			log.error("", e);
		}
		
		if(publicationList==null || publicationList.size()<=0 || (size!=null && begin!=null && publicationList.size()<size+begin)){
			publicationList=this.publicationService.queryAllNormalPubs(null,null);
			try {
				//杂志放回cache
				if(publicationList!=null && publicationList.size()>0){
					memCachedClient.set(CacheConstants.ALL_PUBLICATION_PREFIX, publicationList);
				}
			} catch (Exception e) {
				log.error("", e);
			}
		}
		if(begin!=null && size!=null && begin>=0 && size>0 && publicationList!=null && publicationList.size()>0){
			int end=begin+size;
			publicationList=publicationList.subList(begin, end>publicationList.size()?publicationList.size():end);
		}
		//=======================查询杂志结束
		return publicationList;
		
	}
	
	/**
	 * 通过父类目id查询杂志
	 * @param categoryId
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	public List<Publication> queryByInitLetter(String letter,Integer begin,Integer size){
		//======================查询杂志
		List<Publication> publicationList=null;
		try {
			publicationList=(List<Publication>)this.memCachedClient.get(CacheConstants.PUBLICATION_IN_LETTER_PREFIX+letter);
		} catch (Exception e) {
			log.error("", e);
		}
		
		if(publicationList==null || publicationList.size()<=0 || (size!=null && begin!=null && publicationList.size()<size+begin)){
			publicationList=this.publicationService.queryByInitLetter(letter,null,null);
			try {
				//杂志放回cache
				if(publicationList!=null && publicationList.size()>0){
					memCachedClient.set(CacheConstants.PUBLICATION_IN_LETTER_PREFIX+letter, publicationList);
				}
			} catch (Exception e) {
				log.error("", e);
			}
		}
		if(begin!=null && begin>=0 && size!=null && size>0 && publicationList!=null && publicationList.size()>0){
			int end=begin+size;
			publicationList=publicationList.subList(begin, end>publicationList.size()?publicationList.size():end);
		}
			
		//=======================查询杂志结束
		return publicationList;
	}
	
	
	
}
