/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import cn.magme.pojo.AdAgency;
import cn.magme.service.AdAgencyService;
import cn.magme.util.ToJson;

/**
 * @author shenhao
 * @date 2011-10-26
 * @version $id$
 */
@SuppressWarnings("serial")
public class AdAgencyAction extends BaseAction {

    @Resource
    private AdAgencyService adAgencyService;

    private AdAgency adAgency;
    
    /**
     * 分页查询
     */
    public void page() {
        page = this.adAgencyService.getPageByCondition(page, adAgency);
        String info = ToJson.object2json(page);
        print(info);
    }
    
    /**
     * 添加或更新
     */
    public void commit() {
        Object[] arr = super.toJavaArr(info, AdAgency.class);
        AdAgency[] infos = castToAdAgency(arr);
        this.adAgencyService.commit(infos);
    }

    /**
     * 通过ids查多个
     */
    public void getByIds(){
        //this.publisherService.queryById(id)
//        String[] arr = ids.split(",");
//        List<String> list = new ArrayList<String>();
//        for(String id : arr){
//            list.add(id);
//        }
//        List<PublisherIdRenderBean> ret = this.adAgencyService.getByIds(list);
//        String info = ToJson.list2json(ret);
//        print(info);
    }
    
//    /**
//     * 删除
//     */
//    public void delete() {
//        String[] strArr = ids.split(",");
//    }

    /**
     * 类型转换 Object[] => AdAgency[]
     */
    @SuppressWarnings("unused")
    private AdAgency[] castToAdAgency(Object[] arr) {
        AdAgency[] infos = new AdAgency[arr.length];
        for (int i = 0; i < infos.length; i++) {
            infos[i] = (AdAgency) arr[i];
        }
        return infos;
    }
    
    public AdAgency getAdAgency() {
        return adAgency;
    }

    public void setAdAgency(AdAgency adAgency) {
        this.adAgency = adAgency;
    }
}
