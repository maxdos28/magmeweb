package cn.magme.web.action.sns;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.constants.sns.SNSConstant;
import cn.magme.pojo.sns.InviteCode;
import cn.magme.pojo.sns.UserEx;
import cn.magme.result.sns.CreativeListResult;
import cn.magme.result.sns.PublicResult;
import cn.magme.service.sns.SnsInviteCodeService;
import cn.magme.service.sns.SnsPublicService;
import cn.magme.service.sns.SnsUserIndexService;
import cn.magme.service.sns.UserExService;
import cn.magme.util.ExtPageInfo;
import cn.magme.util.HtmlParserUtil;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Results({
	@Result(name="success",location="/WEB-INF/pages/sns/sns_index.ftl"),
	@Result(name="next",location="/WEB-INF/pages/sns/creativeList.ftl"),
	@Result(name="invite",location="/WEB-INF/pages/sns/ck_invite_code.ftl"),
	@Result(name="sns_login",type="redirect",location="/sns/m1-register-and-login.action#login"),
	@Result(name="user_index",type="redirect",location="/sns/user-index.action"),
	@Result(name="no_invite_code",type="redirect",location="/sns/sns-index!invite.action")
	})
public class SnsIndexAction extends BaseAction{
	private static final long serialVersionUID = 7830730185544040705L;

	private static final Logger log=Logger.getLogger(SnsIndexAction.class);
	
	@Resource
	private SnsUserIndexService snsUserIndexService;
	@Resource
	private SnsPublicService snsPublicService;
	@Resource
	private SnsInviteCodeService snsInviteCodeService;
	@Resource
	private UserExService userExService;
	
	//
	public String invite(){
		Map map = new HashMap();
		if(getSessionUser()==null){
			return "sns_login";
		}else{
			map.put("inviteCode", null);
			map.put("use", getSessionUserId());
		}
		int num = snsInviteCodeService.getCkInviteCode(map);
		if(num>0){
			//ActionContext.getContext().getSession().put("inviteCodeSession",getSessionUserId());
			return "user_index";
		}else{
			recUser();
			//ActionContext.getContext().getSession().put("inviteCodeSession","");
			return "invite";
		}
	}
	
	//第一次用户验证通过 推荐用户
	private void recUser(){
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
	}
	
	public String checked(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		Map map = new HashMap();
		map.put("inviteCode", null);
		map.put("use", getSessionUserId());
		int num = snsInviteCodeService.getCkInviteCode(map);
		/*if(num>0){
			ActionContext.getContext().getSession().put("inviteCodeSession",getSessionUserId());
		}else{
			ActionContext.getContext().getSession().put("inviteCodeSession","");
		}*/
		this.jsonResult.put("num",num);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		return JSON;
	}
	//检查验证码是否可用
	public String ckInvite(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		Map map = new HashMap();
		map.put("inviteCode", inviteCode);
		map.put("use", null);
		int num  = snsInviteCodeService.getCkInviteCode(map);
		this.jsonResult.put("num",num);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		return JSON;
	}
	public String ckUsernameInvite(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		InviteCode code  = null;
		String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*" ;  
	    Pattern   p   =   Pattern.compile(regex);  
	    Matcher   m   =   p.matcher(userName);   
	    if(m.matches()){
	    	code=snsInviteCodeService.getInviteCodeByUseEmail(userName);
	    }else{
	    	code=snsInviteCodeService.getInviteCodeByUse(userName);
	    }
		
		if(code!=null)
			this.jsonResult.put("res",0);
		else
			this.jsonResult.put("res",1);
		
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		return JSON;
	}
	//确认邀请码 并且更新邀请码状态
	public String confirm(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		
		InviteCode code = snsInviteCodeService.getInviteCode(inviteCode);
		if(code!=null && code.getStatus()==0){
			code.setStatus(1);
			code.setUseId(getSessionUserId());
			snsInviteCodeService.update(code);
//			ActionContext.getContext().getSession().put("inviteCodeSession",inviteCode);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		}
		return JSON;
	}
	
	
	@Override
	public String execute() throws Exception {
	
		if(this.getSessionUser()!=null)
				return "user_index";
	
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(begin==null || begin<1)
			begin=0;
		Map condition = new HashMap();
		condition.put("size", SNSConstant.SNS_PAGE_SZIE);
		condition.put("begin", begin);
		if(begin!=0)
			condition.put("timeLock", pageTimeLock);
		else{
			pageTimeLock=null;
			 if(pageTimeLock==null || pageTimeLock.equals("")){
				 java.util.Date now = new Date();
				 pageTimeLock=df.format(now);
			 }
		}
			
		creativeList=snsUserIndexService.getSquareCreativeList(condition);
		if(creativeList!=null){
			for (CreativeListResult c : creativeList) {
				String conTemp = c.getContent();
				if(null!=conTemp && conTemp.length()>SNSConstant.CUT_SIZE)
					c.setCut(true);
				else
					c.setCut(false);
				String temp = HtmlParserUtil.parser(conTemp,SNSConstant.CUT_SIZE);
				c.setContent(temp);
				
				Integer activity = snsUserIndexService.getIsActivityTag(c.getId());
				if(activity>0)
					c.setActivity(true);
				else
					c.setActivity(false);
			}
		}
		if(begin==0)
			PublicRes();
		else
			return "next";
		return SUCCESS;
	}
	
	/**
	 *  广场首页公共资源
	 */
	private void PublicRes(){
		Map condition = new HashMap();
		condition.put("userId",null);
		publicAuthor=snsPublicService.getPubRecUser(condition);
		publicCreative=snsPublicService.getPubCreative();
	}
	
	
	private List<PublicResult> publicAuthor;
	private List<PublicResult> publicCreative;
	private List<CreativeListResult> creativeList;
	private List<UserEx> userExList;
    private UserEx adminUserEx;
	private String pageTimeLock;
	private Integer begin;
	private String inviteCode;
	private String userName;
	

	public List<UserEx> getUserExList() {
		return userExList;
	}

	public UserEx getAdminUserEx() {
		return adminUserEx;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public String getPageTimeLock() {
		return pageTimeLock;
	}

	public void setPageTimeLock(String pageTimeLock) {
		this.pageTimeLock = pageTimeLock;
	}

	public void setBegin(Integer begin) {
		this.begin = begin;
	}
	public List<CreativeListResult> getCreativeList() {
		return creativeList;
	}
	public List<PublicResult> getPublicAuthor() {
		return publicAuthor;
	}
	public List<PublicResult> getPublicCreative() {
		return publicCreative;
	}
}
