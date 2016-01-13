/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.common.SenderMail;
import cn.magme.constants.PojoConstant;
import cn.magme.constants.WebConstant;
import cn.magme.constants.sns.SNSConstant;
import cn.magme.pojo.ActionLog;
import cn.magme.pojo.Issue;
import cn.magme.pojo.Publisher;
import cn.magme.pojo.User;
import cn.magme.pojo.UserSubscribe;
import cn.magme.service.ActionLogService;
import cn.magme.service.IssueService;
import cn.magme.service.MailTemplateService;
import cn.magme.service.UserFollowService;
import cn.magme.service.UserService;
import cn.magme.service.UserSubscribeService;
import cn.magme.util.qqip.IpSeeker;

import com.opensymphony.xwork2.ActionContext;

/**
 * 用户管理
 * @author jacky_zhou
 * @date 2011-5-23
 * @version $id$
 */
@Results({@Result(name="center_success",location="/WEB-INF/pages/user/center.ftl"),
    @Result(name="visit_success",location="/WEB-INF/pages/user/visit.ftl"),
    @Result(name="moreFeed_success",location="/WEB-INF/pages/user/newsfeed.ftl")})
@SuppressWarnings("serial")
public class UserAction extends BaseAction {

    @Resource
    private UserService userService;
    
    @Resource
    private UserFollowService userFollowService;
    
    @Resource
    private MailTemplateService mailTemplateService;
    
    @Resource
    private SenderMail senderMail;
    
    @Resource
    private ActionLogService actionLogService;
    
    @Resource
    private UserSubscribeService userSubscribeService;
    
    @Resource 
    private IssueService issueService;
    
    
    /**
     * 校验注册信息(主要是用户名,密码,邮箱)
     * @return
     */
    public String validateUserJson(){
        User user=new User();
        user.setUserName(userName);
        user.setPassword(password);
        user.setPassword2(password2);
        user.setEmail(email);
        this.jsonResult=userService.validateUser(user,false);
        return JSON;
    }
    
    /**
     * 用户注册
     * @return
     */
    public String registerJson(){
    	//新需求，不允许注册用户，泽宇需求20130624
    	this.jsonResult=JsonResult.getFailure();
    	this.jsonResult.setMessage("不允许注册用户了");
    	return JSON;
        /*User user=new User();
        String ipAddr=ServletActionContext.getRequest().getRemoteAddr();
        user.setUserName(userName);
        user.setPassword(password);
        user.setPassword2(password2);
        user.setEmail(email);
        user.setProvince(IpSeeker.getProvince(ipAddr));
        user.setCity(IpSeeker.getCity(ipAddr));
        this.jsonResult=userService.register(user);
        //注册成功
        if(this.jsonResult.getCode()==JsonResult.CODE.SUCCESS){
            //发欢迎邮件
            String text = mailTemplateService.getTemplateStr(PojoConstant.EMAILTEMPLATE.CONTENT.FILE_WELCOME_READER.getFileName());
            senderMail.sendMail(email, text, PojoConstant.EMAILTEMPLATE.CONTENT.FILE_WELCOME_READER.getSubject(), 0);
            //新注册的用户视为已登陆,用户信息保存到SESSION
            ActionContext.getContext().getSession().put(WebConstant.SESSION.USER, this.jsonResult.getData().get("user"));
            //纪录用户的操作日志
            ActionLog actionLog=this.generateActionLog(
                    PojoConstant.ACTIONLOG.ACTIONTYPEID_REGISTER,
                    ((User)this.jsonResult.getData().get("user")).getId());
            actionLogService.insertActionLog(actionLog);
            
            userFollowService.addUserFollow(this.getSessionUserId(), SNSConstant.ADMIN_USER_ID, PojoConstant.USERFOLLOW.TYPE_USER);
        }
        return JSON;*/
    }
    
    /**
     * 用户登陆
     * @return
     */
    public String loginJson(){
        this.jsonResult=userService.login(userName,password);
        if(this.jsonResult.getCode()==JsonResult.CODE.SUCCESS){
            //校验通过,将登陆用户信息存放在SESSION
            ActionContext.getContext().getSession().put(WebConstant.SESSION.USER, this.jsonResult.getData().get("user"));
            //纪录用户的操作日志
            ActionLog actionLog=this.generateActionLog(
                    PojoConstant.ACTIONLOG.ACTIONTYPEID_LOGIN,
                    ((User)this.jsonResult.getData().get("user")).getId());
            actionLogService.insertActionLog(actionLog);                
        }
        return JSON;
    }
    
    /**
     * 用户退出登陆
     * @return
     */
    public String logoutJson(){
    	//米客登出
    	ActionContext.getContext().getSession().remove(WebConstant.SESSION.USER);
        
    	//米商登出
        ActionContext.getContext().getSession().remove(WebConstant.SESSION.PUBLISHER);
        this.generateJsonResult(JsonResult.CODE.SUCCESS, JsonResult.MESSAGE.SUCCESS);
        return JSON;
    }
    
    /**
     * 麦米中心
     * @return
     */
    public String center(){
        Long userId=this.getSessionUserId();
        this.followList=userFollowService.getFollowListByUserId(userId, 0, 8);
        this.followCount=userFollowService.getFollowCountByUserId(userId);
        this.getSessionUser().setStatsMap(userService.getUserStatsMapByUserId(userId));
        this.feedList=actionLogService.getFriendAndFollowFeedListByUserId(userId, 
                new Integer[]{PojoConstant.ACTIONLOG.ACTIONTYPEID_FAVORITE,
                PojoConstant.ACTIONLOG.ACTIONTYPEID_SUBSCRIBE,
                PojoConstant.ACTIONLOG.ACTIONTYPEID_USERIMAGE}, 0, 5);
        this.feedCount=actionLogService.getFriendAndFollowFeedCountByUserId(userId, 
                new Integer[]{PojoConstant.ACTIONLOG.ACTIONTYPEID_FAVORITE,
                PojoConstant.ACTIONLOG.ACTIONTYPEID_SUBSCRIBE,
                PojoConstant.ACTIONLOG.ACTIONTYPEID_USERIMAGE});
        //设置米客中心的菜单显示
        this.setFirstMenu("1");
        this.setSecondMenu("0");
        return "center_success";
    }
    
    /**
     * 麦米中心
     * @return
     */
    public String centerAjax(){
        return center();
    }
    
    /**
     * 更多好友动态
     * @return
     */
    public String moreFeedAjax(){
        Long userId=this.getSessionUserId();
        this.feedList=actionLogService.getFriendAndFollowFeedListByUserId(userId, 
                new Integer[]{PojoConstant.ACTIONLOG.ACTIONTYPEID_FAVORITE,
                PojoConstant.ACTIONLOG.ACTIONTYPEID_SUBSCRIBE,
                PojoConstant.ACTIONLOG.ACTIONTYPEID_USERIMAGE}, this.begin, this.size);
        return "moreFeed_success";
    }
    
    /**
     * 更多好友动态
     * @return
     */
    public String moreUserFeedAjax(){
        this.feedList=actionLogService.getFeedListByUserId(this.userId, 
                new Integer[]{PojoConstant.ACTIONLOG.ACTIONTYPEID_FAVORITE,
                PojoConstant.ACTIONLOG.ACTIONTYPEID_SUBSCRIBE,
                PojoConstant.ACTIONLOG.ACTIONTYPEID_USERIMAGE}, this.begin, this.size);
        return "moreFeed_success";
    }
    
    /**
     * 查看其他用户的麦米中心
     * @return
     */
    public String visit(){
        this.visitUser=userService.getUserById(id);
        this.followList=userFollowService.getFollowListByUserId(id, 0, 8);
        this.followCount=userFollowService.getFollowCountByUserId(id);        
        this.isFollow=userFollowService.isFollow(this.getSessionUserId(), id)+"";
        this.subscribeList=userSubscribeService.getUserSubscribeListByUserId(
                id, PojoConstant.USERSUBSCRIBE.STATUS_OK, 0, 4);
        this.feedList=actionLogService.getFeedListByUserId(id, 
                new Integer[]{PojoConstant.ACTIONLOG.ACTIONTYPEID_FAVORITE,
                PojoConstant.ACTIONLOG.ACTIONTYPEID_SUBSCRIBE,
                PojoConstant.ACTIONLOG.ACTIONTYPEID_USERIMAGE}, 0, 5); 
        this.feedCount=actionLogService.getFeedCountByUserId(id, 
                new Integer[]{PojoConstant.ACTIONLOG.ACTIONTYPEID_FAVORITE,
                PojoConstant.ACTIONLOG.ACTIONTYPEID_SUBSCRIBE,
                PojoConstant.ACTIONLOG.ACTIONTYPEID_USERIMAGE});        
        return "visit_success";
    }
    
    /**
     * 获得当前SESSION中的读者用户
     * @return
     */
    public String getReaderJson(){
        this.generateJsonResult(JsonResult.CODE.SUCCESS, JsonResult.MESSAGE.SUCCESS,"user",this.getSessionUser());
        this.getJsonResult().put("admin", this.getSessionAdmin());
        this.getJsonResult().put("whetherToAllow", false);
        if(this.getSessionPublisher()!=null){
        	this.getJsonResult().put("publisher", this.getSessionPublisher());
        	Publisher publisher = this.getSessionPublisher();
        	//出版商对应的期刊id 
        	if(issueId!=null&&issueId>0){
        		List<Issue> issueList = issueService.queryAllIssuesByPublisherId(publisher.getId());
        		boolean checkUser = false;
        		for (Issue issue : issueList) {
        			String tempLStr = issueId+"";
        			String dbLStr = issue.getId()+"";
					if(dbLStr.equals(tempLStr)){
						checkUser= true;
						break;
					}
				}
        		if(checkUser){
        			this.getJsonResult().put("whetherToAllow", true);
        		}
        	}
        	//出版商对应的期刊id 
        }else if(this.getSessionAdmin()!=null){
        	this.getJsonResult().put("whetherToAllow", true);
        }else{
        	this.getJsonResult().put("publisher", null);
        }
        
        this.getJsonResult().put("ip", this.getRequestIpLong());
        this.getJsonResult().put("province", this.getRequestProvince());
        this.getJsonResult().put("city", this.getRequestCity());
        return JSON;
    }

    /**
     * 认证码
     */
    private String authcode;
    
    /**
     * 好友列表
     */
    private List<User> friendList;
    
    /**
     * 关注列表
     */
    private List<User> followList;
    
    /**
     * 查看其他用户信息
     */
    private User visitUser;
    
    /**
     * 是否好友
     */
    private String isFriend;
    
    /**
     * 是否关注
     */
    private String isFollow;
    
    /**
     * 用户的订阅
     */
    private List<UserSubscribe> subscribeList;
    
    /**
     * 用户动态
     */
    private List<ActionLog> feedList;
    
    //好友动态总数
    private Integer feedCount;
    //好友总数
    private Integer friendCount;
    //关注总数
    private Integer followCount;
    
    private Long issueId;
    
    private Long id;
    private Date createdTime;
    private Date updatedTime;
    private String userName;
    private String password;
    private String nickName;
    private String email;
    private Integer status;
    private Date lastLoginTime;
    private Integer gender;
    private Date birthdate;
    private String occupation;
    private String education;
    private String hobbies;
    private String phone;
    private Integer astro;
    private Integer bloodType;
    private String address;
    private String province;
    private String city;
    private Long recuserId;
    private String avatar;
    private String reserve1;
    private String reserve2;
    private String reserve3;
    private String password2;
    private Integer begin;
    private Integer size;
    private Long userId;
    
    public String getAuthcode() {
        return authcode;
    }

    public void setAuthcode(String authcode) {
        this.authcode = authcode;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getAstro() {
        return astro;
    }

    public void setAstro(Integer astro) {
        this.astro = astro;
    }

    public Integer getBloodType() {
        return bloodType;
    }

    public void setBloodType(Integer bloodType) {
        this.bloodType = bloodType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getRecuserId() {
        return recuserId;
    }

    public void setRecuserId(Long recuserId) {
        this.recuserId = recuserId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getReserve1() {
        return reserve1;
    }

    public void setReserve1(String reserve1) {
        this.reserve1 = reserve1;
    }

    public String getReserve2() {
        return reserve2;
    }

    public void setReserve2(String reserve2) {
        this.reserve2 = reserve2;
    }

    public String getReserve3() {
        return reserve3;
    }

    public void setReserve3(String reserve3) {
        this.reserve3 = reserve3;
    }

    public List<User> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<User> friendList) {
        this.friendList = friendList;
    }

    public List<User> getFollowList() {
        return followList;
    }

    public void setFollowList(List<User> followList) {
        this.followList = followList;
    }

    public User getVisitUser() {
        return visitUser;
    }

    public void setVisitUser(User visitUser) {
        this.visitUser = visitUser;
    }

    public String getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(String isFriend) {
        this.isFriend = isFriend;
    }

    public String getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(String isFollow) {
        this.isFollow = isFollow;
    }

    public List<UserSubscribe> getSubscribeList() {
        return subscribeList;
    }

    public void setSubscribeList(List<UserSubscribe> subscribeList) {
        this.subscribeList = subscribeList;
    }

    public List<ActionLog> getFeedList() {
        return feedList;
    }

    public void setFeedList(List<ActionLog> feedList) {
        this.feedList = feedList;
    }

    public Integer getBegin() {
        return begin;
    }

    public void setBegin(Integer begin) {
        this.begin = begin;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getFeedCount() {
        return feedCount;
    }

    public void setFeedCount(Integer feedCount) {
        this.feedCount = feedCount;
    }

    public Integer getFriendCount() {
        return friendCount;
    }

    public void setFriendCount(Integer friendCount) {
        this.friendCount = friendCount;
    }

    public Integer getFollowCount() {
        return followCount;
    }

    public void setFollowCount(Integer followCount) {
        this.followCount = followCount;
    }

	public Long getIssueId() {
		return issueId;
	}

	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}
    
    
    
}
