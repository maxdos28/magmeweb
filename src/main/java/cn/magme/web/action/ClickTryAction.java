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
import cn.magme.pojo.FpageEvent;
import cn.magme.pojo.Issue;
import cn.magme.pojo.Publisher;
import cn.magme.pojo.Tag;
import cn.magme.pojo.User;
import cn.magme.pojo.UserImage;
import cn.magme.service.FpageEventService;
import cn.magme.service.IssueService;
import cn.magme.service.MessageService;
import cn.magme.service.PopularService;
import cn.magme.service.PublicationService;
import cn.magme.service.PublisherService;
import cn.magme.service.TagService;
import cn.magme.service.UserEnjoyService;
import cn.magme.service.UserFollowService;
import cn.magme.service.UserImageCommentService;
import cn.magme.service.UserImageService;
import cn.magme.service.UserService;
import cn.magme.service.UserSubscribeService;

/**
 * @author jacky_zhou
 * @date 2011-10-13
 * @version $id$
 */
@SuppressWarnings("serial")
@Results({ @Result(name = "clickMag", location = "/WEB-INF/pages/dialog/clickMag.ftl"),
        @Result(name = "clickPic", location = "/WEB-INF/pages/dialog/clickPic.ftl"),
        @Result(name = "clickEve", location = "/WEB-INF/pages/dialog/clickEve.ftl"),
        @Result(name = "clickLogin", location = "/WEB-INF/pages/dialog/clickLogin.ftl")})
public class ClickTryAction extends BaseAction {
    public static final String CLICK_TYPE_MAG = "mag";
    public static final String CLICK_TYPE_PIC = "pic";
    public static final String CLICK_TYPE_EVE = "eve";

    @Resource
    private UserService userService;

    @Resource
    private PublisherService publisherService;

    @Resource
    private TagService tagService;

    @Resource
    private UserImageService userImageService;

    @Resource
    private UserFollowService userFollowService;

    @Resource
    private UserEnjoyService userEnjoyService;

    @Resource
    private UserSubscribeService userSubscribeService;

    @Resource
    private UserImageCommentService userImageCommentService;

    @Resource
    private PublicationService publicationService;

    @Resource
    private IssueService issueService;

    @Resource
    private FpageEventService fpageEventService;

    @Resource
    private PopularService popularService;

    @Resource
    private MessageService messageService;

    /**
     * 点我试试
     * 
     * @return
     */
    public String execute() {
        String ret = "";
        Long userId = this.getSessionUserId();

        if (clickType.equalsIgnoreCase(CLICK_TYPE_MAG)) {
            this.issue = issueService.queryById(clickId);

            this.publisher = publisherService.queryById(publicationService.queryById(issue.getPublicationId())
                    .getPublisherId());

            if (userId != null) {
                //是否好友关系:1-是,0-否
                this.isFollow = userFollowService.isFollow(userId, this.publisher.getId(),
                        PojoConstant.USERFOLLOW.TYPE_PUBLISHER);

                //是否订阅:1-是,0-否
                this.isSubscribe = userSubscribeService.isSubscribe(userId, issue.getPublicationId());
            }

            //fanNum = userFollowService.getFollowsByObjIdAndType(publisher.getId(), PojoConstant.USERFOLLOW.TYPE_PUBLISHER).size();
            int[] status = new int[1];
            status[0] = PojoConstant.ISSUE.STATUS.ON_SALE.getCode();
            issueNum = issueService.queryByPubIdAndStatuses(issue.getPublicationId(), status,-1).size();

            this.tagList = tagService.getTagListByTypeAndObjectId(PojoConstant.TAG.TYPE_ISSUE, clickId, 0, 20);
            ret = "clickMag";
            //对应的点击数+1
            popularService.click(clickId, PojoConstant.POPULAR.TYPE_ISSUE);
            
        } else if (clickType.equalsIgnoreCase(CLICK_TYPE_PIC)) {
            this.userImage = userImageService.getUserImageById(clickId);
            this.user = userService.getUserById(this.userImage.getUserId());

            //            if (this.user != null) {
            //                //作者的喜欢数
            //                this.user.put("enjoyNum", userService.getEnjoyNumByUserId(this.user.getId()));
            //                //作者的好友数
            //                this.user.put("friendNum", userService.getFriendNumByUserId(this.user.getId()));
            //                //作者的切米数
            //                this.user.put("userImageNum", userService.getUserImageNumByUserId(this.user.getId()));
            //            }

            if (userId != null) {
                //是否好友关系:1-是,0-否
                this.isFollow = userFollowService
                        .isFollow(userId, this.user.getId(), PojoConstant.USERFOLLOW.TYPE_USER);

                //是否喜欢:1-是,0-否
                this.isEnjoy = userEnjoyService.isEnjoy(userId, clickId, PojoConstant.USERENJOY.TYPE_IMAGE);
            }

            //用户图片的评论数
            this.commentNum = userImageCommentService.getList(this.clickId, null, null).size();
            this.tagList = tagService.getTagListByTypeAndObjectId(PojoConstant.TAG.TYPE_IMG, clickId, 0, 20);

            ret = "clickPic";
            //对应的点击数+1
            popularService.click(clickId, PojoConstant.POPULAR.TYPE_IMAGE);
            
        } else if (clickType.equalsIgnoreCase(CLICK_TYPE_EVE)) {
            this.event = fpageEventService.getFpageEventById(clickId);
            this.tagList = tagService.getTagListByTypeAndObjectId(PojoConstant.TAG.TYPE_EVENT, clickId, 0, 20);

            ret = "clickEve";
            //对应的点击数+1
            popularService.click(clickId, PojoConstant.POPULAR.TYPE_EVENT);
        }
        return ret;
    }
    
    public String Json(){
        execute();
        this.jsonResult=this.generateJsonResult(JsonResult.CODE.SUCCESS, JsonResult.MESSAGE.FAILURE);
        this.jsonResult.put("userImage", userImage);
        this.jsonResult.put("user", user);
        this.jsonResult.put("isFollow", isFollow);
        this.jsonResult.put("isEnjoy", isEnjoy);
        this.jsonResult.put("commentNum", commentNum);
        this.jsonResult.put("tagList", tagList);
        return JSON;
    }
    
    
    public String clickLogin(){
        return "clickLogin";
    }

    private String clickType;
    private Long clickId;

    private User user;
    private Publisher publisher;
    private Integer isFollow = 0;
    private Integer isEnjoy = 0;
    private Integer isSubscribe = 0;
    private Integer commentNum = 0;
    private Integer fanNum = 0;
    private Integer issueNum = 0;
    private Issue issue;
    private UserImage userImage;
    private FpageEvent event;
    private List<Tag> tagList;

    public String getClickType() {
        return clickType;
    }

    public void setClickType(String clickType) {
        this.clickType = clickType;
    }

    public Long getClickId() {
        return clickId;
    }

    public void setClickId(Long clickId) {
        this.clickId = clickId;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public UserImage getUserImage() {
        return userImage;
    }

    public void setUserImage(UserImage userImage) {
        this.userImage = userImage;
    }

    public FpageEvent getEvent() {
        return event;
    }

    public void setEvent(FpageEvent event) {
        this.event = event;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(Integer isFollow) {
        this.isFollow = isFollow;
    }

    public Integer getIsEnjoy() {
        return isEnjoy;
    }

    public void setIsEnjoy(Integer isEnjoy) {
        this.isEnjoy = isEnjoy;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Integer getIsSubscribe() {
        return isSubscribe;
    }

    public void setIsSubscribe(Integer isSubscribe) {
        this.isSubscribe = isSubscribe;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Integer getFanNum() {
        return fanNum;
    }

    public void setFanNum(Integer fanNum) {
        this.fanNum = fanNum;
    }

    public Integer getIssueNum() {
        return issueNum;
    }

    public void setIssueNum(Integer issueNum) {
        this.issueNum = issueNum;
    }

}
