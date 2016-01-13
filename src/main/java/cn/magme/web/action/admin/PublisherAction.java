/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import cn.magme.pojo.Publisher;
import cn.magme.pojo.ext.PublisherIdRenderBean;
import cn.magme.service.PublisherService;
import cn.magme.util.ToJson;

/**
 * 出版商管理
 * @author guozheng
 * @date 2011-5-24
 * @version $id$
 */
@SuppressWarnings("serial")
public class PublisherAction extends BaseAction {
	private Logger log = Logger.getLogger(this.getClass());

	@Resource
	private PublisherService publisherService;
	// 页面信息(查询条件:用户名，状态)
	private Publisher publisher;
	/**
     * 分页查询
     */
    public void page() {
        page = this.publisherService.getPageByCondition(page, publisher);
        String info = ToJson.object2json(page);
        print(info);
    }
	
	/**
	 * 添加或更新
	 */
	public void commit() {
		Object[] arr = super.toJavaArr(info, Publisher.class);
		Publisher[] infos = castToPublisher(arr);
		this.publisherService.commit(infos);
	}

	/**
	 * 通过ids查多个
	 */
	public void getByIds(){
		//this.publisherService.queryById(id)
		String[] arr = ids.split(",");
		List<String> list = new ArrayList<String>();
		for(String id : arr){
			list.add(id);
		}
		List<PublisherIdRenderBean> ret = this.publisherService.getByIds(list);
		String info = ToJson.list2json(ret);
		print(info);
	}
	
//    /**
//     * 删除
//     */
//    public void delete() {
//        String[] strArr = ids.split(",");
//    }

	/**
	 * 类型转换 Object[] => Publisher[]
	 */
	private Publisher[] castToPublisher(Object[] arr) {
		Publisher[] infos = new Publisher[arr.length];
		for (int i = 0; i < infos.length; i++) {
			infos[i] = (Publisher) arr[i];
		}
		return infos;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}
}
