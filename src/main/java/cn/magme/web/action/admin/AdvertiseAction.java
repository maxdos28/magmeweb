/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.admin;

import javax.annotation.Resource;
import cn.magme.pojo.Advertise;
import cn.magme.service.AdvertiseService;
import cn.magme.util.ToJson;

/**
 * 广告管理
 * @author guozheng
 * @date 2011-6-24
 * @version $id$
 */
@SuppressWarnings("serial")
public class AdvertiseAction extends BaseAction{
	@Resource
	AdvertiseService advertiseService;
	
	/**
	 * 分页查询
	 */
	public void page(){
		page = this.advertiseService.getByPage(page);
		String info = ToJson.object2json(page);
		print(info);
	}
	
	/**
	 * 广告更新
	 */
	public void commit(){
		Object[] objs = super.toJavaArr(info, Advertise.class);
		Advertise[] arr = this.castToAdvertise(objs);
		this.advertiseService.Commit(arr);
	}
	
	/**
	 * 广告被选中行删除
	 */
	public void delete(){
		String[] strs = ids.split(",");
		Long[] arr = super.strArrToLongArr(strs);
		this.advertiseService.delete(arr);
	}
	
	/**
	 * 数据格式转换Object[] => Advertise[]
	 * @param arr
	 * @return
	 */
	private Advertise[] castToAdvertise(Object[] arr) {
		Advertise[] infos = new Advertise[arr.length];
		for (int i = 0; i < infos.length; i++) {
			infos[i] = (Advertise) arr[i];
		}
		return infos;
	}
}
