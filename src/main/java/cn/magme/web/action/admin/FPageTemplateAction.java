/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.admin;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import cn.magme.common.SystemProp;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.FpageTemplate;
import cn.magme.service.FpageTemplateService;
import cn.magme.util.ToJson;

/**
 * 首页模板管理
 * 
 * @author guozheng
 * @date 2011-5-20
 * @version $id$
 */
@SuppressWarnings("serial")
public class FPageTemplateAction extends BaseAction {

    private Logger log = Logger.getLogger(this.getClass());
    private String type;
    private String ids;
    @Resource
    private FpageTemplateService fpageTemplateService;
    @Resource
    private SystemProp systemProp;


    /**
     * 首页管理：分页查询
     */
    public void page() {
        log.info("searching page:" + page.getCurPage());
        page = this.fpageTemplateService.getByPage(page);
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
        this.fpageTemplateService.delete(super.strArrToLongArr(strArr));
    }

    /**
     * 首页管理：插入或更新
     */
    public void commit() {
        Object[] arr = super.toJavaArr(info, FpageTemplate.class);
        FpageTemplate[] templates = castToFpageTemplate(arr);
        this.fpageTemplateService.commit(templates);
    }

    /**
     * 数组类型转换 Object[] => FpageInfo[]
     * @param arr
     * @return
     */
    private FpageTemplate[] castToFpageTemplate(Object[] arr) {
        FpageTemplate[] templates = new FpageTemplate[arr.length];
        for (int i = 0; i < templates.length; i++) {
            templates[i] = (FpageTemplate) arr[i];
        }
        return templates;
    }

    /**
     * 首页管理：生效
     */
    public void valid() {
        String[] ids_arr = ids.split(",");
        for(String id : ids_arr){
            FpageTemplate fPageTemplate = new FpageTemplate();
            fPageTemplate.setId(Long.parseLong(id.toString()));
            fPageTemplate.setStatus(PojoConstant.FPAGE.STATUS_VALID);
            this.fpageTemplateService.update(fPageTemplate);
        }
    }

    /**
     * 首页管理：失效
     */
    public void invalid() {
        String[] ids_arr = ids.split(",");
        for(String id : ids_arr){
            FpageTemplate fPageTemplate = new FpageTemplate();
            fPageTemplate.setId(Long.parseLong(id.toString()));
            fPageTemplate.setStatus(PojoConstant.FPAGE.STATUS_INVALID);
            this.fpageTemplateService.update(fPageTemplate);
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
