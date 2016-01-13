/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import cn.magme.pojo.Category;
import cn.magme.pojo.ext.CategoryIdRenderBean;
import cn.magme.service.CategoryService;
import cn.magme.util.ToJson;

/**
 * 分类管理
 * @author guozheng
 * @date 2011-5-24
 * @version $id$
 */
@SuppressWarnings("serial")
public class CategoryAction extends BaseAction {

	@Resource
	private CategoryService categoryService;

	/**
	 * 一行及多行层级删除
	 */
	public void delete() {
	    if (ids != null){
	        // 分割待删除数据所有id
	        String[] strArr = ids.split(",");
	        this.categoryService.delete(super.strArrToLongArr(strArr));
		}
	}

    /**
     * 一行及多行层级更新
     */
	public void commit() {
		Object[] arr = super.toJavaArr(info, Category.class);
		Category[] infos = castToCategory(arr);
		this.categoryService.commit(infos);
		// if the data has been edit is first level node(parentid=0) ,then
		// reload to refresh sub items' parentName column.(this also can be done
		// on the client side)
		for (Category cate : infos) {
			if (cate.getParentId() == 0 && cate.getId() == null) {
				print("{reload:\"true\"}");
				return;
			}
		}
		print("{reload:\"false\"}");
	}
	
	/**
	 * 层级数据分页查询
	 */
	public void page() {
		page = this.categoryService.getByPage(page);
		String info = ToJson.object2json(page);
		print(info);
	}

	/**
	 * 父级名称对应下拉框
	 */
	public void parent() {
		List<Category> list = this.categoryService.getFirstLevel();
		String info = ToJson.list2json(list);
		print(info);
	}
	
	/**
	 * 获取id对应 list
	 */
	public void getByIds(){
	    // 以,分割出id数组
		String[] arr = ids.split(",");
		List<String> list = new ArrayList<String>();
		for(String s : arr){
			list.add(s);
		}
		List<CategoryIdRenderBean> ret = this.categoryService.getByIds(list);
		String info = ToJson.list2json(ret);
		print(info);
	}
	
	/**
	 * 获取类目下拉框对应 list
	 */
	public void getByChildIds(){
		List<Category> ret = this.categoryService.queryAllChildCategories();
		String info = ToJson.list2json(ret);
		print(info);
	}

	/**
	 * 转换数据类型Object[] => Category[]
	 * @param arr
	 * @return
	 */
	private Category[] castToCategory(Object[] arr) {
		Category[] infos = new Category[arr.length];
		for (int i = 0; i < infos.length; i++) {
			infos[i] = (Category) arr[i];
		}
		return infos;
	}
}
