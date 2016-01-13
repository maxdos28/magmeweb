/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.admin;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import cn.magme.common.SystemProp;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.FpageItem;
import cn.magme.service.FpageItemService;
import cn.magme.util.ToJson;

/**
 * 首页模板管理
 * 
 * @author guozheng
 * @date 2011-5-20
 * @version $id$
 */
@SuppressWarnings("serial")
public class FPageItemAction extends BaseAction {

    private Logger log = Logger.getLogger(this.getClass());
    private String type;
    private String ids;
    @Resource
    private FpageItemService fpageItemService;
    @Resource
    private SystemProp systemProp;


    /**
     * 首页管理：分页查询
     */
    public void page() {
        log.info("searching page:" + page.getCurPage());
        page = this.fpageItemService.getByPage(page);
        String info = ToJson.object2json(page);
        print(info);
    }

    /**
     * 首页管理：删除
     */
    public void delete() {
        log.info("delete ids:" + ids);
        /*
         * if(ids == null || ids.isEmpty()){
         * print("{success:true,message:ids is empty}"); return; }
         */
        String[] strArr = ids.split(",");
        this.fpageItemService.delete(super.strArrToLongArr(strArr));
    }

    /**
     * 首页管理：插入或更新
     */
    public void commit() {
        Object[] arr = super.toJavaArr(info, FpageItem.class);
        FpageItem[] items = castToFpageItem(arr);
        this.fpageItemService.commit(items);
    }

    /**
     * 数组类型转换 Object[] => FpageInfo[]
     * @param arr
     * @return
     */
    private FpageItem[] castToFpageItem(Object[] arr) {
        FpageItem[] items = new FpageItem[arr.length];
        for (int i = 0; i < items.length; i++) {
            items[i] = (FpageItem) arr[i];
        }
        return items;
    }

    /**
     * 首页管理：生效
     */
    public void valid() {
        String[] ids_arr = ids.split(",");
        for(String id : ids_arr){
            FpageItem fpageItem = new FpageItem();
            fpageItem.setId(Long.parseLong(id.toString()));
            fpageItem.setStatus(PojoConstant.FPAGE.STATUS_VALID);
            this.fpageItemService.update(fpageItem);
        }
    }

    /**
     * 首页管理：失效
     */
    public void invalid() {
        String[] ids_arr = ids.split(",");
        for(String id : ids_arr){
            FpageItem fpageItem = new FpageItem();
            fpageItem.setId(Long.parseLong(id.toString()));
            fpageItem.setStatus(PojoConstant.FPAGE.STATUS_INVALID);
            this.fpageItemService.update(fpageItem);
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}
