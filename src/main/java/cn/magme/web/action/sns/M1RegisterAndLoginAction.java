package cn.magme.web.action.sns;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.common.SenderMail;
import cn.magme.constants.PojoConstant;
import cn.magme.constants.WebConstant;
import cn.magme.constants.sns.SNSConstant;
import cn.magme.pojo.ActionLog;
import cn.magme.pojo.User;
import cn.magme.pojo.sns.InviteCode;
import cn.magme.pojo.sns.UserEx;
import cn.magme.service.ActionLogService;
import cn.magme.service.MailTemplateService;
import cn.magme.service.UserFollowService;
import cn.magme.service.UserService;
import cn.magme.service.sns.SnsInviteCodeService;
import cn.magme.service.sns.UserExService;
import cn.magme.util.ExtPageInfo;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

import com.opensymphony.xwork2.ActionContext;

/**
 * M1版注册&登录页面
 * @author billy.qi
 * @date 2012-06-11
 * 
 */
@SuppressWarnings("serial")
@Results({@Result(name="success",location="/WEB-INF/pages/sns/sns_registerAndLogin.ftl")})
public class M1RegisterAndLoginAction extends BaseAction {
    @Resource
    private UserService userService;
    @Resource
    private MailTemplateService mailTemplateService;
    @Resource
    private SenderMail senderMail;
    @Resource
    private ActionLogService actionLogService;
	@Resource
	private SnsInviteCodeService snsInviteCodeService;
	@Resource
    private UserFollowService userFollowService;
	@Resource
	private UserExService userExService;
	
    private String email;
    private String userName;
    private String password;
    private String password2;
    private String inviteCode;
    
    private List<UserEx> userExList;
    private UserEx adminUserEx;
	
	@Override
	public String execute() throws Exception {
		UserEx userEx = new UserEx();
		userEx.setUserId(SNSConstant.ADMIN_USER_ID);
		userExList = userExService.getByCondition(null, userEx);
		if(userExList != null && userExList.size() > 0)
			adminUserEx = userExList.get(0);
		userEx.setUserId(null);
		userEx.setIsRecommend(1);
		ExtPageInfo page = new ExtPageInfo();
		page.setStart(0);
		page.setLimit(19);
		userExList = userExService.getByCondition(page, userEx);
		return SUCCESS;
	}

    /**
     * 用户注册
     * @return
     */
    public String registerJson(){
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        user.setPassword2(password2);
        user.setEmail(email);
        this.jsonResult = userService.register(user);
        
        //注册成功
        if(this.jsonResult.getCode() == JsonResult.CODE.SUCCESS){
        	user = (User) jsonResult.get("user");
        	//新注册的用户视为已登陆,用户信息保存到SESSION
        	user.setNickName(StringUtil.subString(user.getNickName(), 24));
        	ActionContext.getContext().getSession().put(WebConstant.SESSION.USER, user);
        	boolean inviteSucces = false;
        	if(this.inviteCode != null){
        		inviteSucces = updateInviteCode();
        	}
        	this.jsonResult.put("invite", inviteSucces ? 1 : 0);
            //发欢迎邮件
            String text = mailTemplateService.getTemplateStr(PojoConstant.EMAILTEMPLATE.CONTENT.FILE_WELCOME_READER.getFileName());
            senderMail.sendMail(email, text, PojoConstant.EMAILTEMPLATE.CONTENT.FILE_WELCOME_READER.getSubject(), 0);
            //纪录用户的操作日志
            ActionLog actionLog=this.generateActionLog(
                    PojoConstant.ACTIONLOG.ACTIONTYPEID_REGISTER,
                    user.getId());
            actionLogService.insertActionLog(actionLog);
            userFollowService.addUserFollow(this.getSessionUserId(), SNSConstant.ADMIN_USER_ID, PojoConstant.USERFOLLOW.TYPE_USER);
        }
        return JSON;
    }

	//确认邀请码 并且更新邀请码状态
	public boolean updateInviteCode(){
		InviteCode code = snsInviteCodeService.getInviteCode(inviteCode);
		if(code != null && code.getStatus() == 0){
			code.setStatus(1);
			code.setUseId(getSessionUserId());
			snsInviteCodeService.update(code);
			//ActionContext.getContext().getSession().put("inviteCodeSession",inviteCode);
			return true;
		}
		return false;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public String getInviteCode() {
		return inviteCode;
	}

	public void setUserExList(List<UserEx> userExList) {
		this.userExList = userExList;
	}

	public List<UserEx> getUserExList() {
		return userExList;
	}

	public void setAdminUserEx(UserEx adminUserEx) {
		this.adminUserEx = adminUserEx;
	}

	public UserEx getAdminUserEx() {
		return adminUserEx;
	}
}
