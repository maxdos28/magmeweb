package cn.magme.web.action.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import cn.magme.common.JsonResult;
import cn.magme.pojo.Category;
import cn.magme.service.CategoryService;
import cn.magme.service.SortCategoryRelService;

public class SortCategoryRelAction extends BaseAction {

	private static final long serialVersionUID = 6016152643128038881L;
	
	@Resource
	private SortCategoryRelService sortCategoryRelService;
	
	@Resource
	private CategoryService categoryService;
	
	public String relationJson(){
		this.jsonResult=sortCategoryRelService.relation(this.sortId, this.categoryIds);
		return JSON;
	}
	
	public String getListJson(){
		//查询已经关联到的原始分类
		List<Category> selectedCategoryList=sortCategoryRelService.getCategoryListBySortId(this.sortId);
		//查询全部原始分类
		List<Category> categoryList = this.categoryService.queryAllChildCategories();
		//将全部原始分类中已经关联的原始分类去掉
		if(categoryList!=null&&categoryList.size()>0
				&&selectedCategoryList!=null&&selectedCategoryList.size()>0){
			List<Category> temp=new ArrayList<Category>();
			for(Category category:categoryList){
				if(category==null||selectedCategoryList.contains(category)){
					temp.add(category);
				}
			}
			categoryList.removeAll(temp);
		}
		
		this.jsonResult=this.generateJsonResult(JsonResult.CODE.SUCCESS, JsonResult.MESSAGE.SUCCESS);
		jsonResult.put("selectedCategoryList", selectedCategoryList);
		jsonResult.put("categoryList", categoryList);
		return JSON;
	}
	
	private Long sortId;
	private Long[] categoryIds;

	public Long getSortId() {
		return sortId;
	}

	public void setSortId(Long sortId) {
		this.sortId = sortId;
	}

	public Long[] getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(Long[] categoryIds) {
		this.categoryIds = categoryIds;
	}
}