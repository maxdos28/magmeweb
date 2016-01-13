package cn.magme.web.action.publish;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.web.action.BaseAction;


@Results({@Result(name="success",location="/WEB-INF/pages/test/test.ftl")})
public class TestSubDomainAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9111774730421399101L;
	private String subDomain;
	
	public String execute(){
		String aa="aa";
		String bb="bb";
		
		return SUCCESS;
	}

	public String getSubDomain() {
		return subDomain;
	}

	public void setSubDomain(String subDomain) {
		this.subDomain = subDomain;
	}
	
	

}
