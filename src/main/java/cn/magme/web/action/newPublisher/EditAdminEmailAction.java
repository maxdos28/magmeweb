package cn.magme.web.action.newPublisher;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.pojo.AdminEmail;
import cn.magme.pojo.Link;
import cn.magme.service.AdminEmailService;
import cn.magme.service.LinkService;




/**
 * 编辑=友情链接
 * @author devin.song
 * @date 2012-05-02
 * @version $id$
 */
@SuppressWarnings("serial")
@Results({@Result(name="config",location="/WEB-INF/pages/dialog/editAdminEmail.ftl")})
public class EditAdminEmailAction extends PublisherBaseAction {
	private Logger log = Logger.getLogger(this.getClass());
	
	@Resource
	private AdminEmailService adminEmailService;
	
	private List<AdminEmail> emails;
	
	private String emailAddress;
	private Long id;

	/**
	 * 杂志设置的起始页 、以及单个杂志 的数据初始化
	 * @return
	 */
	public String to(){
		getAll();
		return "config";
	}

	private void getAll() {
		emails = adminEmailService.getAdminEmailAll();
	}

	/**
	 * add an admin email to db
	 * @return
	 */
	public String doDelete(){
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		try{
			adminEmailService.delete(id);
			getAll();
			this.jsonResult.put("emails", emails);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}catch(Exception e){
			
		}
		return JSON;
	}
	/**
	 * add an admin email to db
	 * @return
	 */
	public String doAdd(){
		this.jsonResult = new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		if(emailAddress == null){
			jsonResult.setMessage("邮件地址不能为空");
			return JSON;
		}
		Pattern p = Pattern.compile("^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$");
		Matcher matcher = p.matcher(emailAddress);
		if(!matcher.matches()){
			jsonResult.setMessage("请检查邮件格式");
			return JSON;
		}
		getAll();
		Iterator<AdminEmail> it = emails.iterator();
		while(it.hasNext()){
			if(emailAddress.equals(it.next().getEmailAddress())){
				this.jsonResult.setMessage("邮件地址不能重复");
				return JSON;
			}
		}
		AdminEmail adminEmail = new AdminEmail();
		adminEmail.setEmailAddress(emailAddress);
		try{
			adminEmailService.add(adminEmail);
			getAll();
			this.jsonResult.put("emails", emails);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}catch(Exception e){
			
		}
		return JSON;
	}
	

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmails(List<AdminEmail> emails) {
		this.emails = emails;
	}

	public List<AdminEmail> getEmails() {
		return emails;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

}
