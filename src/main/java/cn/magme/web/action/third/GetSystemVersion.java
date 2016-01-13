package cn.magme.web.action.third;

import cn.magme.common.JsonResult;
import cn.magme.web.action.BaseAction;
/**
 * 给flash用，获取当前系统的js，css版本号
 * @author fredy
 *
 */
public class GetSystemVersion extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1344518091384678458L;

	public String execute(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		this.jsonResult.put("systemVersion", this.systemProp.getSystemVersion());
		return JSON;
	}
	

}
