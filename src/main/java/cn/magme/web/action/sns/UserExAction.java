package cn.magme.web.action.sns;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.web.action.BaseAction;


/**
 * @author billy
 * @date 2012-6-1
 */
@Results({
	@Result(name="success",location="/WEB-INF/pages/sns/creativeList.ftl") 
})
public class UserExAction extends BaseAction {

	private static final long serialVersionUID = -3247103574905341738L;
	
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

}
