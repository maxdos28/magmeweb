/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.EnjoyImage;
import cn.magme.pojo.FpageEvent;
import cn.magme.pojo.Issue;
import cn.magme.pojo.Message;
import cn.magme.pojo.Publisher;
import cn.magme.pojo.Tag;
import cn.magme.pojo.User;
import cn.magme.pojo.UserImage;
import cn.magme.service.IssueService;
import cn.magme.service.TagService;
import cn.magme.service.UserImageService;
import cn.magme.service.UserService;

/**
 * @author jacky_zhou
 * @date 2011-10-13
 * @version $id$
 */
@SuppressWarnings("serial")
@Results({@Result(name="enjoyImage",location="/WEB-INF/pages/userCenter/enjoyImage.ftl"),
    @Result(name="enjoyIssue",location="/WEB-INF/pages/userCenter/enjoyIssue.ftl"),
    @Result(name="userImage",location="/WEB-INF/pages/userCenter/userImage.ftl"),
    @Result(name="friend",location="/WEB-INF/pages/userCenter/friend.ftl")})
public class UserVisitAction extends BaseAction {
    @Resource
    private UserService userService;
    
    @Resource
    private TagService tagService;
    
    @Resource
    private UserImageService userImageService;
    
    @Resource
    private IssueService issueService;
    
    /**
     * 查询用户的统计信息
     */
    private void statsMap(){
        this.visitUser=userService.getUserById(this.userId);
        if(visitUser!=null){
            //图片数
            visitUser.put("enjoyImageNum", userService.getEnjoyNumByUserId(userId, new Integer[]{PojoConstant.USERENJOY.TYPE_IMAGE,PojoConstant.USERENJOY.TYPE_EVENT}));
            //杂志数
            visitUser.put("enjoyIssueNum", userService.getEnjoyNumByUserId(userId, new Integer[]{PojoConstant.USERENJOY.TYPE_ISSUE}));            
            //好友数
            visitUser.put("friendNum", userService.getFriendNumByUserId(userId));            
        }   
    }
    
    /**
     * 米客中心-首页
     * @return
     */
    public String index(){
        return enjoyImage();
    }
    
    /**
     * 米客中心-喜欢的图片和事件
     * @return
     */
    public String enjoyImage(){
        statsMap();
        //用户喜欢的所有图片对应的标签
        this.tagList=tagService.getEnjoyTagList(new Integer[]{PojoConstant.TAG.TYPE_IMG,PojoConstant.TAG.TYPE_EVENT},this.userId,0,30);
        this.enjoyImageList=userImageService.getEnjoyImageList(tagName,this.userId,begin,size);
        this.tab="enjoyImage";
        return "enjoyImage"; 
    }
    
    /**
     * 米客中心-喜欢的图片和事件(滚动加载)
     * @return
     */
    public String enjoyImageJson(){
        //查询用户喜欢的所有图片
    	this.enjoyImageList=userImageService.getEnjoyImageList(tagName,this.userId,begin,size);
        this.generateJsonResult(JsonResult.CODE.SUCCESS, JsonResult.MESSAGE.SUCCESS);
        this.jsonResult.put("userImageList", enjoyImageList);
        return JSON; 
    }    
    
    /**
     * 米客中心-喜欢的期刊
     * @return
     */
    public String enjoyIssue(){
        statsMap();
        //用户喜欢的所有期刊对应的标签
        this.tagList=tagService.getEnjoyTagList(new Integer[]{PojoConstant.TAG.TYPE_ISSUE},this.userId,0,30);
        //查询用户喜欢的所有期刊
        this.issueList=issueService.getEnjoyIssueList(tagName,this.userId,begin,size);         
        this.tab="enjoyIssue";
        return "enjoyIssue";
    }
    
    /**
     * 米客中心-喜欢的期刊(滚动加载)
     * @return
     */
    public String enjoyIssueJson(){
        //查询用户喜欢的所有期刊
        this.issueList=issueService.getEnjoyIssueList(tagName,this.userId,begin,size);
        this.generateJsonResult(JsonResult.CODE.SUCCESS, JsonResult.MESSAGE.SUCCESS);
        this.jsonResult.put("issueList", issueList);
        return JSON; 
    }
    
    /**
     * 米客中心-我的图片
     * @return
     */
    public String userImage(){
        statsMap();
        //用户制作的所有图片对应的标签
        this.tagList=tagService.getUserImageTagListByUserId(this.userId,0,30);
        //查询用户制作的所有图片
        this.userImageList=userImageService.getUserImageListByUserId(tagName,this.userId,begin,size);
        this.tab="userImage";
        return "userImage";
    }
    
    /**
     * 米客中心-我的图片(滚动加载)
     * @return
     */
    public String userImageJson(){
        //查询用户制作的所有图片
        this.userImageList=userImageService.getUserImageListByUserId(tagName,this.userId,begin,size);      
        this.generateJsonResult(JsonResult.CODE.SUCCESS, JsonResult.MESSAGE.SUCCESS);
        this.jsonResult.put("userImageList", userImageList);
        return JSON; 
    }
    
    /**
     * 米客中心-TA的好友
     * @return
     */
    public String friend(){
        statsMap(); 
        this.userList=userService.getFriendListByUserIdAndVisitUserId(
                            this.userId,this.getSessionUserId(),null, null);
        this.tab="friend";
        return "friend";
    }
    
    private String tagName;
    private List<UserImage> userImageList;
    private List<Issue> issueList;
    private List<Tag> tagList;
    private List<FpageEvent> eventList;
    private Integer begin=0;
    private Integer size=20;
    private User user;
    private String param;
    private List<Message> messageList;
    private List<User> userList;
    private List<Publisher> publisherList;
    private List<EnjoyImage> enjoyImageList;;
    private String tab;
    
    private Long fromUserId;
    private Integer fromType;
    private Long userId;
    private User visitUser;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public List<UserImage> getUserImageList() {
        return userImageList;
    }

    public void setUserImageList(List<UserImage> userImageList) {
        this.userImageList = userImageList;
    }

    public List<Issue> getIssueList() {
        return issueList;
    }

    public void setIssueList(List<Issue> issueList) {
        this.issueList = issueList;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public List<FpageEvent> getEventList() {
        return eventList;
    }

    public void setEventList(List<FpageEvent> eventList) {
        this.eventList = eventList;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public Long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Integer getFromType() {
        return fromType;
    }

    public void setFromType(Integer fromType) {
        this.fromType = fromType;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Publisher> getPublisherList() {
        return publisherList;
    }

    public void setPublisherList(List<Publisher> publisherList) {
        this.publisherList = publisherList;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public User getVisitUser() {
        return visitUser;
    }

    public void setVisitUser(User visitUser) {
        this.visitUser = visitUser;
    }

	public List<EnjoyImage> getEnjoyImageList() {
		return enjoyImageList;
	}

	public void setEnjoyImageList(List<EnjoyImage> enjoyImageList) {
		this.enjoyImageList = enjoyImageList;
	}

	public String getTab() {
		return tab;
	}

	public void setTab(String tab) {
		this.tab = tab;
	}
}
