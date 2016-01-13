package cn.magme.web.action.sns;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.result.sns.PublicResult;
import cn.magme.result.sns.UserInfoResult;
import cn.magme.service.sns.SnsPublicService;
import cn.magme.service.sns.SnsUserIndexService;
import cn.magme.web.action.BaseAction;

@Results({
	@Result(name="success",location="/WEB-INF/pages/sns/authorize.ftl")
})
/**
 * @see 点击M图标和P图片跳转
 * @author xiaochen
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class AuthorizeAction extends BaseAction{
	private static final long serialVersionUID = -3502037523483462629L;
	@Resource
	private SnsUserIndexService snsUserIndexService;
	@Resource
	private SnsPublicService snsPublicService;
	
	@Override
	public String execute() throws Exception {
		Map m=new HashMap();
		m.put("id", this.getSessionUserId());
		m.put("u",0);
		userInfo= snsUserIndexService.getUserInfo(m);
		//PublicRes(this.getSessionUserId());
		return SUCCESS;
	}
	
	/**
	 * @see 用户公共资源 加载用户关注，粉丝，发表数量
	 */
	private void PublicRes(Long uid){
		Map condition = new HashMap();
		condition.put("userId", uid);
		publicAuthor=snsPublicService.getPubRecUser(condition);
		publicCreative=snsPublicService.getPubCreative();
		newCreative=snsPublicService.getNewestCreative();
	}
	
	private List<PublicResult> publicAuthor;
	private List<PublicResult> publicCreative;
	private List<PublicResult> newCreative;
	private UserInfoResult userInfo;

	
	public UserInfoResult getUserInfo() {
		return userInfo;
	}
	public List<PublicResult> getPublicAuthor() {
		return publicAuthor;
	}
	public List<PublicResult> getPublicCreative() {
		return publicCreative;
	}
	public List<PublicResult> getNewCreative() {
		return newCreative;
	}
	
}
