package cn.magme.web.action.sns;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import cn.magme.common.JsonResult;
import cn.magme.pojo.sns.Creative;
import cn.magme.service.sns.CreativeService;
import cn.magme.web.action.BaseAction;

/**
 * 
 * @author fredy
 * @since 2012-12-24
 */
public class CreativeCategoryAction extends BaseAction {
	
	
	@Resource
	private CreativeService creativeService;
	
	private static final Logger log=Logger.getLogger(CreativeCategoryAction.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -4359288638455171626L;
	
	private static final int DEFAULT_SIZE=20;
	
	private static final int MAX_SIZE=50;
	/**
	 * 按照父类目id查询所有的作品
	 * @return
	 */
	public String queryByPCidJson(){
		this.jsonResult=JsonResult.getFailure();
		if(parentId==null || parentId<=0){
			this.jsonResult.setMessage("传入参数错误，parentId必须大于0");
			return JSON;
		}
		if(begin<=0){
			begin=0;
		}
		if(size<=0 || size>MAX_SIZE){
			size=DEFAULT_SIZE;
		}
		try {
			List<Creative> creativeList=creativeService.queryByParentId(parentId,0,begin,size);
			if(creativeList==null || creativeList.size()<=0){
				this.jsonResult.setMessage("查询没有数据,parentId:"+parentId);
				return JSON;
			}
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			this.jsonResult.put("creativeList", creativeList);
		} catch (Exception e) {
			log.error("", e);
		}
		return JSON;
	}
	
	private Long parentId;
	
	private int begin=0;
	
	private int size=DEFAULT_SIZE;
	
	

	public int getBegin() {
		return begin;
	}



	public void setBegin(int begin) {
		this.begin = begin;
	}



	public int getSize() {
		return size;
	}



	public void setSize(int size) {
		this.size = size;
	}



	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	

}
