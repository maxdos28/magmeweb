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
public class CreativeCategoryRelAction extends BaseAction {

	@Resource
	private CreativeService creativeService;
	private static final long serialVersionUID = -1098967213202087196L;
	private static final Logger log=Logger.getLogger(CreativeCategoryRelAction.class);
	
	public String execute(){
		this.jsonResult=JsonResult.getFailure();
		try {
			List<Creative> creativeList=creativeService.queryLatestCreativeForParentCategory();
			if(creativeList==null || creativeList.size()<=0){
				this.jsonResult.setMessage("查询无数据");
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

}
