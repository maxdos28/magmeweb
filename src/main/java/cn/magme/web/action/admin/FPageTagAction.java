/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.admin;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import cn.magme.pojo.FpageTag;
import cn.magme.service.FpageTagService;
import cn.magme.util.ToJson;

/**
 * 首页tag管理(不用)
 * @author guozheng
 * @date 2011-5-20
 * @version $id$
 */
@SuppressWarnings("serial")
public class FPageTagAction extends BaseAction {

	private Logger log = Logger.getLogger(this.getClass());
	private FpageTag tag;
	@Resource
	private FpageTagService fpageTagService;

	/**
	 * 首页tag管理: 分页查询
	 */
	public void page() {
		page = this.fpageTagService.getPageByCondition(page, tag);
		String info = ToJson.object2json(page);
		print(info);
	}

	/**
	 * 首页tag管理: 删除数据
	 */
	public void delete() {
		String[] strArr = ids.split(",");
		this.fpageTagService.delete(super.strArrToLongArr(strArr));
	}

	public FpageTag getTag() {
		return tag;
	}

	public void setTag(FpageTag tag) {
		this.tag = tag;
	}

}
