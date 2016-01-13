/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import cn.magme.pojo.Publication;
import cn.magme.pojo.ext.PublicationIdRenderBean;
import cn.magme.service.PublicationService;
import cn.magme.util.ToJson;
import cn.magme.web.manager.cache.PublicationCacheService;

/**
 * 杂志管理
 * @author guozheng
 * @date 2011-6-3
 * @version $id$
 */
@SuppressWarnings("serial")
public class PublicationAction extends BaseAction {

    @Resource
	private PublicationService publicationService;

    @Resource
    PublicationCacheService publicationCacheService;
    // 页面信息(查询条件:杂志名)
	private Publication publication;
	/**
	 * 条件分页查询
	 */
	public void page() {
		page = this.publicationService.getPageByCondition(page, publication);
		String info = ToJson.object2json(page);
		print(info);
	}
	//需要时再实现
	public void delete() {}
	/**
	 * 添加或更新
	 */
	public void commit() {
		Object[] arr = super.toJavaArr(info, Publication.class);
		Publication[] pubs = castToPublication(arr);
		this.publicationService.commit(pubs);
	}
	
	/**
	 * 获取publicationid list
	 */
	public void getByIds(){
		String[] arr = ids.split(",");
		List<String> list = new ArrayList<String>();
		for(String s : arr){
			list.add(s);
		}
		List<PublicationIdRenderBean> ret = this.publicationService.getByIds(list);
		String info = ToJson.list2json(ret);
		print(info);
	}

    /**
     * 更新内存
     */
    public void upMemory() {
        publicationCacheService.init();
        print("{success:true}");
    }
	
    /**
     * 数组类型转换
     * @param arr
     * @return
     */
	private Publication[] castToPublication(Object[] arr) {
		Publication[] infos = new Publication[arr.length];
		for (int i = 0; i < infos.length; i++) {
			infos[i] = (Publication) arr[i];
		}
		return infos;
	}

	public Publication getPublication() {
		return publication;
	}

	public void setPublication(Publication publication) {
		this.publication = publication;
	}

}
