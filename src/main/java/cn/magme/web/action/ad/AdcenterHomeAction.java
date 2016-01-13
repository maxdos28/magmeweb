/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.ad;

import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionContext;

import cn.magme.common.SystemProp;
import cn.magme.constants.WebConstant;
import cn.magme.pojo.AdUser;
import cn.magme.pojo.Publisher;
import cn.magme.pojo.User;
import cn.magme.web.action.BaseAction;

/**
 * 废弃 2012-07-23
 * @author shenhao
 * @date 2011-10-18
 * @version $id$
 * 广告商中心首页
 */
@SuppressWarnings("serial")
@Results({@Result(name="success",location="/WEB-INF/pages/ad/manageAdHome.ftl"),
	@Result(name="position",location="/WEB-INF/pages/ad/manageAdPosition.ftl"),
	@Result(name="adcreate",location="/WEB-INF/pages/ad/manageAdCreate.ftl"),
	@Result(name="myad",location="/WEB-INF/pages/ad/manageAdMyAd.ftl"),
	@Result(name="data",location="/WEB-INF/pages/ad/manageAdData.ftl"),
	@Result(name="mymgzad",location="/WEB-INF/pages/ad/manageAdMyMgzAd.ftl"),
	@Result(name="magmead",location="/WEB-INF/pages/ad/manageAdMagmeAd.ftl")})
public class AdcenterHomeAction extends BaseAction {

	private static final Logger log = Logger.getLogger(AdcenterHomeAction.class);
	
	@Resource
	private SystemProp systemProp;
	
	public AdUser adUser;

	public String execute(){

		//publisher = this.getSessionPublisher();
	    adUser = (AdUser) ActionContext.getContext().getSession().get("session_aduser");
		return "success";
	}

	public SystemProp getSystemProp() {
		return systemProp;
	}
	
	public void setSystemProp(SystemProp systemProp) {
		this.systemProp = systemProp;
	}
	
	public AdUser getAdUser() {
		return adUser;
	}

	public void setAdUser(AdUser adUser) {
		this.adUser = adUser;
	}
}
