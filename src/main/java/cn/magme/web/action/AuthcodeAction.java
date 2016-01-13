/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action;

import java.io.ByteArrayInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.constants.WebConstant;
import cn.magme.util.RandomNumUtil;

import com.opensymphony.xwork2.ActionContext;

/**
 * 生成验证码图片
 * @author jacky_zhou
 * @date 2011-5-20
 * @version $id$
 */
@Results({@Result(name="success",type="stream",params={"contentType","image/jpeg","inputName","inputStream"})})
@SuppressWarnings("serial")
public class AuthcodeAction extends BaseAction {
    private static final Log log = LogFactory.getLog(AuthcodeAction.class);
    
    private String time;
    private ByteArrayInputStream inputStream;
    
    @Override
    public String execute() throws Exception {
        RandomNumUtil rdnu = RandomNumUtil.Instance();
        this.setInputStream(rdnu.getImage());
        ActionContext.getContext().getSession().put(WebConstant.SESSION.AUTHCODE, rdnu.getString());
        log.debug("authcode=====" + ActionContext.getContext().getSession().get(WebConstant.SESSION.AUTHCODE));     
        return SUCCESS;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ByteArrayInputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(ByteArrayInputStream inputStream) {
        this.inputStream = inputStream;
    }
}
