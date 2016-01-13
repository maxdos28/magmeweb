package cn.magme.web.action.sms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.constants.PojoConstant;
import cn.magme.pojo.sms.SmsContentEx;
import cn.magme.pojo.sms.SmsProject;
import cn.magme.service.sms.SmsContentExService;
import cn.magme.service.sms.SmsProjectService;
import cn.magme.web.action.BaseAction;
@Results({
	@Result(name="success",location="/WEB-INF/pages/sms/smsTemplate.ftl")
})
public class SmsSeeAction extends BaseAction{
	
	@Resource
	private SmsProjectService smsProjectService;
	
	@Resource
	private SmsContentExService smsContentExService;
	
	@Override
	public String execute() throws Exception {
		if(id!=null){
			pid = Long.valueOf(id, 36);
		}
		SmsProject smsProject = smsProjectService.selectByPrimaryKey(pid);
		String templateStr = smsProject.getTemplate();
		String[] template = templateStr.split("_");
		classUl = template[0];
		classLi = template[1];
		
		webTitle = smsProject.getWebTitle();
		webContent = smsProject.getWebContent();
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("projectId", pid);
		map.put("type", PojoConstant.SMS_CONTENT_EX.TYPE_PIC);
		map.put("status", PojoConstant.SMS_CONTENT_EX.STATUS_OK);
		
		contentExList = smsContentExService.queryList(map);
		return "success";
	}
	
	
	private String id;
	private Long pid;
	private String classUl;
	private String classLi;
	private String webTitle;
	private String webContent;
	private String phoneNum;
	
	private List<SmsContentEx> contentExList;
	
	
	public String getWebTitle() {
		return webTitle;
	}

	public void setWebTitle(String webTitle) {
		this.webTitle = webTitle;
	}

	public String getWebContent() {
		return webContent;
	}

	public void setWebContent(String webContent) {
		this.webContent = webContent;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}
	public String getClassUl() {
		return classUl;
	}

	public void setClassUl(String classUl) {
		this.classUl = classUl;
	}

	public String getClassLi() {
		return classLi;
	}

	public void setClassLi(String classLi) {
		this.classLi = classLi;
	}

	public List<SmsContentEx> getContentExList() {
		return contentExList;
	}

	public void setContentExList(List<SmsContentEx> contentExList) {
		this.contentExList = contentExList;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = -5201747574895293719L;

}
