package cn.magme.web.action;

import javax.annotation.Resource;

import cn.magme.common.JsonResult;
import cn.magme.pojo.SysParameter;
import cn.magme.service.SysParameterService;

/**
 * 
 * @author fredy
 * @since 2013-5-6
 */
public class SysParameterAction extends BaseAction {
    @Resource
	private SysParameterService sysParameterService;
    
    private static final String JIAN_XING_JIAN_YUAN="JIAN_XING_JIAN_YUAN";

	/**
	 * 
	 */
	private static final long serialVersionUID = 157960470492898202L;
	
	/**
	 * 增加值
	 */
	public String incJson(){
		this.jsonResult=JsonResult.getSuccess();
		SysParameter param=sysParameterService.queryByParamKey(JIAN_XING_JIAN_YUAN);
		param=this.sysParameterService.incValue(param);
		this.jsonResult.put("count", param.getParamValue());
		return JSON;
	}
	/**
	 * 查询值
	 */
	public String execute(){
		this.jsonResult=JsonResult.getSuccess();
		SysParameter param=sysParameterService.queryByParamKey(JIAN_XING_JIAN_YUAN);
		this.jsonResult.put("count", param.getParamValue());
		return JSON;
	}

}
