package cn.magme.web.action.sns;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.constants.WebConstant;
import cn.magme.constants.sns.SNSConstant;
import cn.magme.pojo.User;
import cn.magme.pojo.sns.InviteCode;
import cn.magme.pojo.sns.UserEx;
import cn.magme.result.sns.CreativeExResult;
import cn.magme.result.sns.CreativeListResult;
import cn.magme.result.sns.PublicResult;
import cn.magme.result.sns.UserEnjoyResult;
import cn.magme.result.sns.UserInfoResult;
import cn.magme.service.FpageEventCommentService;
import cn.magme.service.UserService;
import cn.magme.service.sns.SnsCreativeCommentService;
import cn.magme.service.sns.SnsCreativeExService;
import cn.magme.service.sns.SnsInviteCodeService;
import cn.magme.service.sns.SnsPublicService;
import cn.magme.service.sns.SnsUserIndexService;
import cn.magme.service.sns.UserExService;
import cn.magme.util.ExtPageInfo;
import cn.magme.util.HtmlParserUtil;
import cn.magme.web.action.BaseAction;

import com.opensymphony.xwork2.ActionContext;

@Results({
	@Result(name="success",location="/WEB-INF/pages/sns/userindex.ftl"),
	@Result(name="home",location="/WEB-INF/pages/sns/sns_user.ftl"),
	@Result(name="_home",location="/WEB-INF/pages/sns/sns_user_creative.ftl"),
	@Result(name="index",type="redirect",location="/sns/sns-index.action"),
	@Result(name="next",location="/WEB-INF/pages/sns/creativeList.ftl"),
	@Result(name="user_next",location="/WEB-INF/pages/sns/sns_user_list.ftl"),
	@Result(name="user_list",location="/WEB-INF/pages/sns/user_list.ftl"),
	@Result(name="sns_login",type="redirect",location="/sns/m1-register-and-login.action#login"),
	@Result(name="no_invite_code",type="redirect",location="/sns/sns-index!invite.action"),
	@Result(name="enjoyCreative",location="/WEB-INF/pages/sns/enjoy.ftl"),
	@Result(name="attention",location="/WEB-INF/pages/sns/attention.ftl"),
	@Result(name="m1square",type="redirect",location="/sns/square.action")
	
})
@SuppressWarnings({ "rawtypes", "unchecked" })
public class UserIndexAction extends BaseAction{
	private static final long serialVersionUID = -5208350463788025800L;
	private static final Logger log=Logger.getLogger(UserIndexAction.class);
	
	@Resource
	private SnsUserIndexService snsUserIndexService;
	@Resource
	private SnsPublicService snsPublicService;
	@Resource
	private SnsInviteCodeService snsInviteCodeService;
	@Resource
	private UserService userService;
	@Resource
	private FpageEventCommentService fpageEventCommentService;
	@Resource
	private UserExService userExService;
	@Resource
	private SnsCreativeCommentService snsCreativeCommentService;
	@Resource
	private SnsCreativeExService snsCreativeExService;
	
	//M1频道邀请码判断
	//判断用户是否已经登录  
	/*private String checkUserInvite(){
		if(this.getSessionUser()==null)
			ActionContext.getContext().getSession().put("inviteCodeSession","");
		else{
			String code = String.valueOf(ActionContext.getContext().getSession().get("inviteCodeSession"));
			if(code==null || "null".equals(code) || "".equals(code)){
				Map map = new HashMap();
				map.put("inviteCode", null);
				map.put("use", getSessionUserId());
				int num = snsInviteCodeService.getCkInviteCode(map);
				if(num==0)
					return "no_invite_code";
				else
					ActionContext.getContext().getSession().put("inviteCodeSession",getSessionUserId());
			}
		}
		return null;
    }*/
	
	@Override
	public String execute() throws Exception {
		if((u==null || "".equals(u)) && (c==null || "".equals(c)) && (this.getSessionUserId()==null))
			return "index";
		Long uid=null;
		Long cid=null;
		//判断如果作品ID转换错误　作品ID为null
		if(c!=null && !"".equals(c)){
			try{
				cid=Long.valueOf(c);
				u=null;
			}catch (Exception e) {
				c=null;
			}
		}
		
		if(u!=null && !"".equals(u)){
			try{
				uid=Long.valueOf(u);
			}catch (Exception e) {
				return "index";
			}
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		//判断用户是否访问单独作品
		if(c!=null && !"".equals(c)){
						
			if(begin==null || begin<1)
				begin=0;
			Map condition = new HashMap();
			condition.put("size", SNSConstant.SNS_PAGE_SZIE);
			condition.put("begin",begin);
			condition.put("u", this.getSessionUserId()==null?0:this.getSessionUserId());
			condition.put("c", c);
			condition.put("ploy", 0);
			condition.put("timeLock", null);
			condition.put("userId", null);
			creativeList=snsUserIndexService.getCreativeHomeList(condition);
			
			if(creativeList!=null && creativeList.size()>0){
				uid=creativeList.get(0).getUserId();
				Integer activity = snsUserIndexService.getIsActivityTag(creativeList.get(0).getId());
				if(activity>0)
					creativeList.get(0).setActivity(true);
				else
					creativeList.get(0).setActivity(false);
			}
			
			
				
			Map m=new HashMap();
			m.put("id", uid);
			m.put("u",this.getSessionUserId()==null?0:this.getSessionUserId());
			userInfo= snsUserIndexService.getUserInfo(m);
			if(userInfo!=null){
				
				Map  cnu= new HashMap();
				cnu.put("userid", uid);
				creativeNumUser=snsUserIndexService.getCreativeNumUserTop(cnu);
				
				PublicRes(this.getSessionUserId());
			}
			if(userInfo==null || userInfo.getId()==null)
				return "index";
			
			if(getSessionUserId()!=null && uid.intValue()!=getSessionUserId().intValue()){
				similarAttention=snsUserIndexService.getSimilarAttention(m);
				similarFans = snsUserIndexService.getSimilarFans(m);
			}
			
			recommend= snsCreativeExService.getRecommendedRead(cid);
			
			return "_home";
		}
		
		if(uid!=null && uid>0 && uid!=getSessionUserId()){
			Map m=new HashMap();
			m.put("id", uid);
			m.put("u",this.getSessionUserId()==null?0:this.getSessionUserId());
			userInfo= snsUserIndexService.getUserInfo(m);
			if(userInfo!=null){
				if(begin==null || begin<1)
					begin=0;
				Map condition = new HashMap();
				condition.put("size", SNSConstant.SNS_PAGE_SZIE);
				condition.put("begin",begin);
				condition.put("u", this.getSessionUserId()==null?0:this.getSessionUserId());
				try{
					Long.valueOf(c);
					condition.put("c", null);
				}catch (Exception e) {
				}
				condition.put("ploy", 0);
				if(begin!=0)
					condition.put("timeLock", pageTimeLock);
				else{
					pageTimeLock=null;
					 if(pageTimeLock==null || pageTimeLock.equals("")){
						 java.util.Date now = new Date();
						 pageTimeLock=df.format(now);
					 }
				}
				condition.put("userId", uid);
				creativeList=snsUserIndexService.getCreativeHomeList(condition);
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
				if(begin==0){
					Map  cnu= new HashMap();
					cnu.put("userid", uid);
					creativeNumUser=snsUserIndexService.getCreativeNumUserTop(cnu);
					
					PublicRes(this.getSessionUserId());
					
					if(getSessionUserId()!=null && uid.intValue()!=getSessionUserId().intValue()){
						similarAttention=snsUserIndexService.getSimilarAttention(m);
						similarFans = snsUserIndexService.getSimilarFans(m);
					}
				}else
					return "user_next";
				return "home";
			}else
				return "index";
		}
		
		if(getSessionUserId()!=null){
			
			if(begin==null || begin<1)
				begin=0;
			
			Map condition = new HashMap();
			condition.put("size", SNSConstant.SNS_PAGE_SZIE);
			condition.put("begin",begin);
			if(begin!=0)
				condition.put("timeLock", pageTimeLock);
			else{
				pageTimeLock=null;
				 if(pageTimeLock==null || pageTimeLock.equals("")){
					 java.util.Date now = new Date();
					 pageTimeLock=df.format(now);
				 }
			}
			condition.put("userId", this.getSessionUserId());
			creativeList=snsUserIndexService.getCreativeList(condition);
			if(creativeList!=null)
				for(int i=0;i<creativeList.size();i++){
					CreativeListResult cr=creativeList.get(i);
					if(cr.getcType()==9){
						Map map = new HashMap();
						map.put("eventId", cr.getId());
				        //map.put("begin", 0);
				        //map.put("size", 50);
						creativeList.get(i).setFpageEventComment(fpageEventCommentService.getFpageEventCommentByEventIdSns(map));
					}else{
						String conTemp = cr.getContent();
						if(null!=conTemp && conTemp.length()>SNSConstant.CUT_SIZE)
							creativeList.get(i).setCut(true);
						else
							creativeList.get(i).setCut(false);
						String temp = HtmlParserUtil.parser(conTemp,SNSConstant.CUT_SIZE);
						creativeList.get(i).setContent(temp);
						
						Integer activity = snsUserIndexService.getIsActivityTag(cr.getId());
						if(activity>0)
							cr.setActivity(true);
						else
							cr.setActivity(false);
					
				}
			}
			PublicRes(this.getSessionUserId());
			
			if(begin==0){
				User user =userService.getUserById(getSessionUserId());
				ActionContext.getContext().getSession().put(WebConstant.SESSION.USER, user);
			
				attention=snsUserIndexService.getAttention(this.getSessionUserId());
				creative=snsUserIndexService.getCreativeCount(this.getSessionUserId());
				fans=snsUserIndexService.getFans(this.getSessionUserId());
				/*List<UserEnjoyResult> enjoy=snsUserIndexService.getUserEnjoy(this.getSessionUserId());
				if(enjoy!=null && enjoy.size()>0){
					for (UserEnjoyResult e : enjoy) {
						if(e.getType().equals("2"))
							enjoyIssue=e.getNum();
						else if(e.getType().equals("3"))
							enjoyEvent=e.getNum();
						else if(e.getType().equals("4"))
							enjoyCreative=e.getNum();
					}
				}*/
				
				/*Map map = new HashMap();
				map.put("inviteCode", null);
				map.put("use", getSessionUserId());
				isInviteCode=snsInviteCodeService.getCkInviteCode(map);*/
				
				if(attention==0){
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
					return "attention";
				}
				
				commentFlag();
			}
			
			if(begin>0)
				return "next";
			
			return SUCCESS;
		}
		return null;
		
	}
	
	public String enjoy(){
		if(begin==null || begin<1)
			begin=0;
		
		Map condition = new HashMap();
		condition.put("size", 5);
		condition.put("begin",begin);
		condition.put("userId", this.getSessionUserId());
		creativeList =snsUserIndexService.getEnjoyCreativeList(condition);
		for(int i=0;i<creativeList.size();i++){
			CreativeListResult cr=creativeList.get(i);
			if(cr.getcType()==9){
				Map map = new HashMap();
				map.put("eventId", cr.getId());
		        //map.put("begin", 0);
		        //map.put("size", 50);
				creativeList.get(i).setFpageEventComment(fpageEventCommentService.getFpageEventCommentByEventIdSns(map));
			}else{
				String conTemp = cr.getContent();
				if(null!=conTemp && conTemp.length()>SNSConstant.CUT_SIZE)
					creativeList.get(i).setCut(true);
				else
					creativeList.get(i).setCut(false);
				String temp = HtmlParserUtil.parser(conTemp,SNSConstant.CUT_SIZE);
				creativeList.get(i).setContent(temp);
				
				Integer activity = snsUserIndexService.getIsActivityTag(creativeList.get(i).getId());
				if(activity>0)
					creativeList.get(i).setActivity(true);
				else
					creativeList.get(i).setActivity(false);
			}
			
		}
		if(begin==0){
			PublicRes(this.getSessionUserId());
			
			attention=snsUserIndexService.getAttention(this.getSessionUserId());
			creative=snsUserIndexService.getCreativeCount(this.getSessionUserId());
			fans=snsUserIndexService.getFans(this.getSessionUserId());
			
			List<UserEnjoyResult> enjoy=snsUserIndexService.getUserEnjoy(this.getSessionUserId());
			if(enjoy!=null && enjoy.size()>0){
				for (UserEnjoyResult e : enjoy) {
					if(e.getType().equals("2"))
						enjoyIssue=e.getNum();
					else if(e.getType().equals("3"))
						enjoyEvent=e.getNum();
					else if(e.getType().equals("4"))
						enjoyCreative=e.getNum();
				}
			}
			commentFlag();
			return "enjoyCreative";
		}
		return "next";
	}
	
	public String home(){
		if(this.getSessionUser()==null)
			return "sns_login";
		/*else{
			//判断用户是否有邀请码
			String _ck_Invite=checkUserInvite();
			if(_ck_Invite!=null)
				return _ck_Invite;
		}*/
		Map m=new HashMap();
		m.put("id", this.getSessionUserId());
		m.put("u",0);
		userInfo= snsUserIndexService.getUserInfo(m);
		
		 
		if(begin==null || begin<1){
			begin=0;
		}
		Map condition = new HashMap();
		condition.put("size", SNSConstant.SNS_PAGE_SZIE);
		condition.put("u", 0);
		condition.put("begin", begin);
		if(begin!=0){
			condition.put("timeLock", pageTimeLock);
		}else{
			pageTimeLock=null;
			 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 if(pageTimeLock==null || pageTimeLock.equals("")){
				 java.util.Date now = new Date();
				 pageTimeLock=df.format(now);
			 }
		}
		condition.put("userId", this.getSessionUserId());
		creativeList=snsUserIndexService.getCreativeHomeList(condition);
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
		if(begin==0){
			Map  cnu= new HashMap();
			cnu.put("userid", this.getSessionUserId());
			creativeNumUser=snsUserIndexService.getCreativeNumUserTop(cnu);
			
			PublicRes(this.getSessionUserId());
		}else
			return "user_next";
		return "home";
	}
	
	/**
	 * @see 用户首页公共资源
	 */
	private void PublicRes(Long uid){
		Map condition = new HashMap();
		condition.put("userId", uid);
		publicAuthor=snsPublicService.getPubRecUser(condition);
		if(u!=null && !"".equals(u) && c==null){
			try{
				Long _uid = Long.valueOf(u);
				for (int i=0;i<publicAuthor.size();i++) {
					PublicResult p=publicAuthor.get(i);
					if(p.getUserId().intValue()==_uid.intValue()){
						publicAuthor.remove(i);
						publicAuthor.add(0, p);
					}
						
				}
			}catch (Exception e) {
			}
			
		}
		publicCreative=snsPublicService.getPubCreative();
		
		if(c!=null && !"".equals(c)){
			try{
				Long _cid = Long.valueOf(c);
				for (int i=0;i<publicCreative.size();i++) {
					PublicResult p=publicCreative.get(i);
					if(p.getCid().intValue()==_cid.intValue()){
						publicCreative.remove(i);
						publicCreative.add(0, p);
					}
						
				}
			}catch (Exception e) {
			}
			
		}
		
		newCreative=snsPublicService.getNewestCreative();
//		System.out.println(newCreative.get(0).getImagepath());
	}
	
	
	//用户关注列表
	public String attention(){
		if((u==null || "".equals(u)) && (this.getSessionUserId()==null || this.getSessionUserId()<=0))
			return "m1square";
		Long uid=0l;
		
		if(u!=null && !"".equals(u)){
			try{
				uid=Long.valueOf(u);
			}catch (Exception e) {
				return "m1square";
			}
		}else{
			uid=this.getSessionUserId();
		}
		Map m=new HashMap();
		m.put("id", uid);
		m.put("u",this.getSessionUserId()==null?0:this.getSessionUserId());
		userInfo= snsUserIndexService.getUserInfo(m);
		
		List<UserEnjoyResult> enjoy=snsUserIndexService.getUserEnjoy(uid);
		if(enjoy!=null && enjoy.size()>0){
			for (UserEnjoyResult e : enjoy) {
				if(e.getType().equals("2"))
					enjoyIssue=e.getNum();
				else if(e.getType().equals("3"))
					enjoyEvent=e.getNum();
				else if(e.getType().equals("4"))
					enjoyCreative=e.getNum();
			}
		}
		
		if(begin==null || begin<1)
			begin=0;
		Map condition=new HashMap();
		condition.put("userId", this.getSessionUserId()==null?0:this.getSessionUserId());
		condition.put("u", uid);
		condition.put("fans", null);
		condition.put("begin", begin);
		condition.put("size", 20);
		
		userList=snsPublicService.getUserAttention(condition);
		
		
		begin+=20;
		
		return "user_list";
	}
	
	//用户粉丝列表
	public String fans(){
		if((u==null || "".equals(u)) && (this.getSessionUserId()==null || this.getSessionUserId()<=0))
			return "m1square";
		Long uid=0l;
		
		if(u!=null && !"".equals(u)){
			try{
				uid=Long.valueOf(u);
			}catch (Exception e) {
				return "m1square";
			}
		}else{
			uid=this.getSessionUserId();
		}
		Map m=new HashMap();
		m.put("id", uid);
		m.put("u",this.getSessionUserId()==null?0:this.getSessionUserId());
		userInfo= snsUserIndexService.getUserInfo(m);
		
		List<UserEnjoyResult> enjoy=snsUserIndexService.getUserEnjoy(uid);
		if(enjoy!=null && enjoy.size()>0){
			for (UserEnjoyResult e : enjoy) {
				if(e.getType().equals("2"))
					enjoyIssue=e.getNum();
				else if(e.getType().equals("3"))
					enjoyEvent=e.getNum();
				else if(e.getType().equals("4"))
					enjoyCreative=e.getNum();
			}
		}
		
		if(begin==null || begin<1)
			begin=0;
		Map condition=new HashMap();
		condition.put("userId", this.getSessionUserId()==null?0:this.getSessionUserId());
		condition.put("u", uid);
		condition.put("fans", "1");
		condition.put("begin", begin);
		condition.put("size", 20);
		userList=snsPublicService.getUserFans(condition);
		fansFlag="true";
		begin+=20;
		return "user_list";
	}
	
	//获取用户新评论数
	private void commentFlag(){
		Map map=new HashMap();
		map.put("userid",this.getSessionUserId()==null?0:this.getSessionUserId());
		map.put("readed","1");
		commentFlag = snsCreativeCommentService.getCommentListFromSize(map);
	}
	
	private String u;
	private String c;
	private String fansFlag;//标记是否粉丝列表
	private Integer attention;
	private Integer creative;
	private Integer fans;
	private Integer enjoyEvent;
	private Integer enjoyCreative;
	private Integer enjoyIssue;
	private Integer isInviteCode;
	private Integer commentFlag;
	private List<InviteCode> inviteCode;
	private List<PublicResult> publicAuthor;
	private List<PublicResult> publicCreative;
	private List<PublicResult> newCreative;
	private List<PublicResult> userList;
	private List<CreativeListResult> creativeList;
	private List<CreativeExResult> recommend;
	private UserInfoResult userInfo;
	private List<UserInfoResult> creativeNumUser;
	private List<UserInfoResult> similarAttention;
	private List<UserInfoResult> similarFans;
	private List<UserEx> userExList;
	private UserEx adminUserEx;
	private String pageTimeLock;
	private Integer begin;
	
	
	public List<CreativeExResult> getRecommend() {
		return recommend;
	}

	public Integer getCommentFlag() {
		return commentFlag;
	}

	public Integer getIsInviteCode() {
		return isInviteCode;
	}

	public Integer getBegin() {
		return begin;
	}

	public void setC(String c) {
		this.c = c;
	}

	public String getFansFlag() {
		return fansFlag;
	}

	public void setBegin(Integer begin) {
		this.begin = begin;
	}

	public String getPageTimeLock() {
		return pageTimeLock;
	}

	public void setPageTimeLock(String pageTimeLock) {
		this.pageTimeLock = pageTimeLock;
	}

	public UserInfoResult getUserInfo() {
		return userInfo;
	}

	public List<UserInfoResult> getCreativeNumUser() {
		return creativeNumUser;
	}

	public void setU(String u) {
		this.u = u;
	}

	public String getU() {
		return u;
	}

	public String getC() {
		return c;
	}
	
	public List<PublicResult> getUserList() {
		return userList;
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
	public List<PublicResult> getNewCreative() {
		return newCreative;
	}
	public Integer getEnjoyEvent() {
		return enjoyEvent;
	}
	public Integer getEnjoyCreative() {
		return enjoyCreative;
	}
	public Integer getEnjoyIssue() {
		return enjoyIssue;
	}
	public Integer getAttention() {
		return attention;
	}
	public Integer getCreative() {
		return creative;
	}
	public Integer getFans() {
		return fans;
	}
	public List<InviteCode> getInviteCode() {
		return inviteCode;
	}

	public List<UserEx> getUserExList() {
		return userExList;
	}

	public UserEx getAdminUserEx() {
		return adminUserEx;
	}

	public List<UserInfoResult> getSimilarAttention() {
		return similarAttention;
	}

	public List<UserInfoResult> getSimilarFans() {
		return similarFans;
	}
	
	
}
