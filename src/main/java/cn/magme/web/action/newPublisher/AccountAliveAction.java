package cn.magme.web.action.newPublisher;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.constants.PojoConstant.ACCOUNT_ALIVE;
import cn.magme.constants.PojoConstant.ACCOUNT_TYPE;
import cn.magme.constants.PojoConstant.PUBLISHER;
import cn.magme.pojo.AccountAlive;
import cn.magme.pojo.Publisher;
import cn.magme.service.AccountAliveService;
import cn.magme.service.PublisherService;
import cn.magme.web.action.BaseAction;

@SuppressWarnings("serial")
@Results({@Result(name="login",location="/WEB-INF/pages/newPublisher/login.ftl")})
public class AccountAliveAction extends BaseAction {
	private static final String LOGIN = "login";
	
	@Resource
	private AccountAliveService accountAliveService;
	@Resource
	private PublisherService publisherService;
	private Long accountId;
	private Integer type;
	private String sec;
	private Boolean aliveSuccess;
	private String message;
	@Override
	public String execute() throws Exception {
		aliveSuccess = false;
		message = "激活失败";
		List<AccountAlive> accountAlives = accountAliveService.getByCondition(accountId, type);
		if (accountAlives != null && !accountAlives.isEmpty() && null != sec) {
			for (AccountAlive accountAlive : accountAlives) {
				if (sec.equals(accountAlive.getSec())) {
					if(ACCOUNT_ALIVE.STATUS_CHECKED.equals(accountAlive.getStatus())){
						message = "您的账号已经激活，无需重复激活，请等待编辑审核！";
					} else {
						//标记为已验证
						accountAlive.setStatus(ACCOUNT_ALIVE.STATUS_CHECKED);
						accountAliveService.updateSale(accountAlive);
						//标记账号状态为已通过验证
						if (ACCOUNT_TYPE.PUBLISHER.equals(type)) {//杂志商
							Publisher publisher = publisherService.queryById(accountId);
							if (publisher != null) {//改成已激活状态
								publisher.setStatus(PUBLISHER.STATUS.ACCOUNT_ALIVE.getCode());
								publisherService.updatePublisherById(publisher);
								message = "恭喜哟！您的出版商账号已激活，请通知编辑进行审核，审核通过后就可以正常登陆发行专家后台了！";
							}
						}
						//标记本次验证成功
						aliveSuccess = true;
					}
					break;
				}
			}
		}
		return LOGIN;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getSec() {
		return sec;
	}
	public void setSec(String sec) {
		this.sec = sec;
	}
	/**
	 * @param aliveSuccess the aliveSuccess to set
	 */
	public void setAliveSuccess(Boolean aliveSuccess) {
		this.aliveSuccess = aliveSuccess;
	}
	/**
	 * @return the aliveSuccess
	 */
	public Boolean getAliveSuccess() {
		return aliveSuccess;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
}
