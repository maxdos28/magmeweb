package cn.magme.web.action.newPublisher;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionContext;

import cn.magme.common.JsonResult;
import cn.magme.common.SenderMail;
import cn.magme.constants.PojoConstant;
import cn.magme.constants.PojoConstant.ACCOUNT_TYPE;
import cn.magme.constants.PojoConstant.EMAILTEMPLATE.CONTENT;
import cn.magme.constants.WebConstant;
import cn.magme.pojo.AccountAlive;
import cn.magme.pojo.AdUser;
import cn.magme.pojo.Publisher;
import cn.magme.service.AccountAliveService;
import cn.magme.service.MailTemplateService;
import cn.magme.service.PublisherService;
import cn.magme.util.Md5Util;
import cn.magme.util.StringUtil;

@SuppressWarnings("serial")
@Results({@Result(name="register",location="/WEB-INF/pages/newPublisher/register.ftl"),
	@Result(name="config",location="/WEB-INF/pages/newPublisher/publisherConfig.ftl")})
public class RegisterAction extends PublisherBaseAction {
	
	@Resource
	private PublisherService publisherService;
	
	@Resource
    private MailTemplateService mailTemplateService;
	
    @Resource
    private SenderMail senderMail;
    @Resource
	private AccountAliveService accountAliveService;

	public String to(){
		return "register";
	}
	
	public String doJson(){
		String session_authcode = (String) (ActionContext.getContext().getSession().get(WebConstant.SESSION.PUBLISHER_AUTHCODE));
        if(StringUtil.isNotBlank(session_authcode)&&session_authcode.equals(this.authcode)){
        	this.jsonResult=publisherService.register(publisher); 
        	//注册成功
			if(jsonResult.getCode()==JsonResult.CODE.SUCCESS){
                //移除校验码,确保校验码使用一次后就失效
                ActionContext.getContext().getSession().remove(WebConstant.SESSION.AUTHCODE);
				//将登陆用户写入SESSION
//				Publisher publisher = (Publisher) this.jsonResult.getData().get("publisher");
//				ActionContext.getContext().getSession().put(WebConstant.SESSION.PUBLISHER, publisher);
                //生成一条待认证记录
                String sec = publisher.getId() + publisher.getUserName() + publisher.getPublishName() + publisher.getEmail();
                sec = Md5Util.MD5Encode(sec);
                AccountAlive aa = new AccountAlive();
                aa.setAccountId(publisher.getId());
                aa.setSec(sec);
                aa.setType(ACCOUNT_TYPE.PUBLISHER);
                accountAliveService.insert(aa);
				//发欢迎邮件
				String[] from = {"userName", "type", "sec", "accountId"};
				String[] to = {"" + publisher.getPublishName(), aa.getType() + "", aa.getSec(), aa.getAccountId() + ""};
				
				CONTENT emailTemplate = PojoConstant.EMAILTEMPLATE.CONTENT.FILE_WELCOME_PUBLISHER;
				String text = mailTemplateService.getTemplateStr(emailTemplate.getFileName());
				for (int i = 0; i < from.length; i++) {
					text = text.replaceAll("\\{" + from[i] + "\\}", to[i]);
				}
			    senderMail.sendMail(publisher.getEmail(), text, emailTemplate.getSubject(), 0);
			    
//			    Map<String,Object> session= ActionContext.getContext().getSession();
//				 AdUser adUser = new AdUser();
//				 adUser.setId(publisher.getId());
//                 adUser.setName(publisher.getPublishName());
//                 adUser.setLogo(publisher.getLogo());
//                 adUser.setLevel(PojoConstant.ADUSER.LEVEL_PUBLISHER); 
//                 session.put(WebConstant.SESSION.ADUSER, adUser);
//                 session.remove(WebConstant.SESSION.ADAGENCY);
//            	 session.remove(WebConstant.SESSION.ADMAGEME);
//            	 session.remove(WebConstant.SESSION.USER);
//            	 session.remove(WebConstant.SESSION.ADMIN);
            	 //自动登录
			}
        }else{
        	this.generateJsonResult(JsonResult.CODE.FAILURE, JsonResult.MESSAGE.FAILURE,"authcode","验证码错误");
        }
		return JSON;
	}
	
	private Publisher publisher;
	private String authcode;

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public String getAuthcode() {
		return authcode;
	}

	public void setAuthcode(String authcode) {
		this.authcode = authcode;
	}
	
}
