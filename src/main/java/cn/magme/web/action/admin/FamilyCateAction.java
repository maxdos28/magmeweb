/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.admin;

import javax.annotation.Resource;

import cn.magme.pojo.FamilyCategory;
import cn.magme.service.FamilyCategoryService;
import cn.magme.util.ToJson;
import cn.magme.web.manager.cache.FamilyCategoryCacheService;

/**
 * 看米管理：Familycategory
 * @author shenhao
 * @date 2011-8-1
 * @version $id$
 */
@SuppressWarnings("serial")
public class FamilyCateAction extends BaseAction {
	@Resource
	FamilyCategoryService familyCategoryService;
	
	@Resource
	FamilyCategoryCacheService familyCategoryCacheService;
	
	/**
	 * 族群分类：分页查询
	 */
	public void page() {
		page = this.familyCategoryService.getPage(page);
		String info = ToJson.object2json(page);
		print(info);
	}
	
	/**
	 * 族群分类：选中数据删除
	 */
	public void delete() {
	    // 以,分割所有选中数据id
		String[] strArr = ids.split(",");
		this.familyCategoryService.delete(super.strArrToLongArr(strArr));
	}

	/**
	 * 族群分类：添加或更新
	 */
	public void commit() {
		Object[] arr = super.toJavaArr(info, FamilyCategory.class);
		FamilyCategory[] familyCategorys = castToFamilyCate(arr);
		this.familyCategoryService.commit(familyCategorys);
	}
	
    /**
     * 族群分类：更新内存
     */
    public void upMemory() {
        familyCategoryCacheService.init();
        print("{success:true}");
    }
	
	/**
	 * 数据格式转换 Object[] => FamilyCategory[]
	 * @param arr
	 * @return 
	 */
	private FamilyCategory[] castToFamilyCate(Object[] arr) {
		FamilyCategory[] familyCategorys = new FamilyCategory[arr.length];
		for (int i = 0; i < familyCategorys.length; i++) {
			familyCategorys[i] = (FamilyCategory) arr[i];
		}
		return familyCategorys;
	}
}
