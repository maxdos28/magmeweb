/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.admin;

import javax.annotation.Resource;

import cn.magme.pojo.FeedBackCategory;
import cn.magme.service.FeedBackCategoryService;
import cn.magme.util.ToJson;

/**
 * 反馈种类管理
 * @author shenhao
 * @date 2011-8-15
 * @version $id$
 */
@SuppressWarnings("serial")
public class FeedBackCategoryAction extends BaseAction {

	@Resource
	FeedBackCategoryService feedBackCategoryService;

	// 反馈种类(查询条件:状态)
	private FeedBackCategory feedBackCategory;
	
	/**
	 * 反馈种类：分页查询
	 */
	public void page() {
		page = this.feedBackCategoryService.getPageByCondition(page, feedBackCategory);
		String info = ToJson.object2json(page);
		print(info);
	}
	
	/**
	 * 反馈种类：添加或更新
	 */
	public void commit() {
		Object[] arr = super.toJavaArr(info, FeedBackCategory.class);
		FeedBackCategory[] lst = castToFeedBackCategory(arr);
		this.feedBackCategoryService.commit(lst);
	}
	
	/**
	 * 反馈种类：选中数据删除
	 */
	public void delete() { 
	    if (ids != null){
	        String[] strArr = ids.split(",");
	        this.feedBackCategoryService.delete(super.strArrToLongArr(strArr));
	    }
	}
	
	/**
	 * 数组类型转换：Object[] => FeedBackCategory[]
	 * @param arr
	 * @return
	 */
	private FeedBackCategory[] castToFeedBackCategory(Object[] arr) {
		FeedBackCategory[] infos = new FeedBackCategory[arr.length];
		for (int i = 0; i < infos.length; i++) {
			infos[i] = (FeedBackCategory) arr[i];
		}
		return infos;
	}
	
	public FeedBackCategory getFeedBackCategory() {
		return feedBackCategory;
	}

	public void setFeedBackCategory(FeedBackCategory feedBackCategory) {
		this.feedBackCategory = feedBackCategory;
	}
}
