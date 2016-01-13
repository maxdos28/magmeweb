/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.common.SenderMail;
import cn.magme.constants.PojoConstant;
import cn.magme.constants.WebConstant;
import cn.magme.service.MailTemplateService;
import cn.magme.service.UserFindpwdService;
import cn.magme.util.StringUtil;

import com.opensymphony.xwork2.ActionContext;

/**
 * 找回密码
 * @author jacky_zhou
 * @date 2011-5-24
 * @version $id$
 */
@Results({@Result(name="toFindpwd_success",location="/WEB-INF/pages/user/findpwd.ftl"),
    @Result(name="toResetPassword_success",location="/WEB-INF/pages/user/resetPassword.ftl")})
@SuppressWarnings("serial")    
public class UserFindpwdAction extends BaseAction {
    @Resource
    private UserFindpwdService userFindpwdService;
    
    @Resource
    private MailTemplateService mailTemplateService;
    
    @Resource
    private SenderMail senderMail;
    
    /**
     * 跳转到找回密码页面
     * @return
     */
    public String toFindpwd(){
        return "toFindpwd_success";
    }
    
    /**
     * 找回密码
     * @return
     */
    public String doFindpwdJson(){
        String session_authcode = (String) (ActionContext.getContext().getSession().get(WebConstant.SESSION.AUTHCODE));
        if(StringUtil.isNotBlank(session_authcode)&&session_authcode.equals(this.authcode)){
            this.jsonResult=userFindpwdService.findPassword(email);
            if(this.jsonResult.getCode()==JsonResult.CODE.SUCCESS){
                //移除校验码,确保校验码使用一次后就失效
                ActionContext.getContext().getSession().remove(WebConstant.SESSION.AUTHCODE);
                //发找回密码邮件到邮箱
                String text = mailTemplateService.getTemplateStr(PojoConstant.EMAILTEMPLATE.CONTENT.FILE_FORGOT_PASSWORD.getFileName());
                text=text.replaceAll("\\$\\{host\\}", "http://www.magme.cn");
                text=text.replaceAll("\\$\\{userName\\}", this.jsonResult.getData().get("userName")+"");
                text=text.replaceAll("\\$\\{keycode\\}", this.jsonResult.getData().get("keycode")+"");
                senderMail.sendMail(email, text, PojoConstant.EMAILTEMPLATE.CONTENT.FILE_FORGOT_PASSWORD.getSubject(), 0);
            }
        }else{
            this.generateJsonResult(JsonResult.CODE.FAILURE, JsonResult.MESSAGE.FAILURE,"authcode","验证码错误");
        }
        return JSON;
    }
    
    /**
     * 跳转到重置密码页
     * @return
     */
    public String toResetPassword(){
        return "toResetPassword_success";
    }
    
    /**
     * 重置密码
     * @return
     */
    public String resetPasswordJson(){
        this.jsonResult=userFindpwdService.resetPassword(password, password2, keycode);
        return JSON;
    }
    
    /**
     * 认证码
     */
    private String authcode;
    
    private String email;
    private String password;
    private String password2;
    private String keycode;

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
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

    public String getKeycode() {
        return keycode;
    }

    public void setKeycode(String keycode) {
        this.keycode = keycode;
    }

    public String getAuthcode() {
        return authcode;
    }

    public void setAuthcode(String authcode) {
        this.authcode = authcode;
    }
    
}
