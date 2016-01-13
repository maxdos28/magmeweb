/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import cn.magme.pojo.AdAgency;
import cn.magme.pojo.CommonComment;
import cn.magme.service.AdAgencyService;
import cn.magme.service.CommonCommentService;
import cn.magme.util.ExtPageInfo;
import cn.magme.util.ToJson;

/**
 * @author devin.song
 * @date 2013-03-14
 * @version $id$
 */
@SuppressWarnings("serial")
public class CommentAction extends BaseAction {

    @Resource
    private CommonCommentService commonCommentService;
    
    /**
     * 分页查询
     */
    public void page() {
    	int start = 0;
    	int size =50;
    	try{
    	 start = (int) page.getStart();
    	 size = (int) page.getLimit();
    	}catch(Exception e){
    	}
    	List<CommonComment> ccList = commonCommentService.getCommonComment(null, null, null, null, start, size);
    	int total= commonCommentService.getCommonCommentCount(null,null,null,null);
    	page.setTotal(total);
    	page.setData(ccList);
        String info = ToJson.object2json(page);
        print(info);
    }
    
    /**
     * 添加或更新
     */
    public void commit() {
    	String[] strArr = ids.split(",");
    	for(String idStr:strArr){
    		CommonComment commonComent = new CommonComment();
        	commonComent.setId(Long.parseLong(idStr));
        	commonComent.setStatus(0);
        	commonCommentService.updateComment(commonComent);
    	}
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
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	private Long id;
	
	private Integer status;
    
}
