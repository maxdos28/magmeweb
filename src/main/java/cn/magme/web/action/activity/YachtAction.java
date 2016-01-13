package cn.magme.web.action.activity;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.web.action.BaseAction;


@Results({@Result(name="success",location="/WEB-INF/pages/activity/yachtActivity.ftl")})
public class YachtAction extends BaseAction {
	
	public String execute(){
		return SUCCESS;
	}

}
