/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action;

import java.io.BufferedInputStream;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.constants.CacheConstants;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.ActionLog;
import cn.magme.pojo.Issue;
import cn.magme.pojo.Tag;
import cn.magme.pojo.User;
import cn.magme.pojo.UserImage;
import cn.magme.pojo.UserImageComment;
import cn.magme.service.ActionLogService;
import cn.magme.service.PopularService;
import cn.magme.service.TagService;
import cn.magme.service.UserEnjoyService;
import cn.magme.service.UserFollowService;
import cn.magme.service.UserImageCommentService;
import cn.magme.service.UserImageService;
import cn.magme.service.UserService;

import com.danga.MemCached.MemCachedClient;
import com.opensymphony.xwork2.ActionContext;

/**
 * 用户图片
 * @author jacky_zhou
 * @date 2011-10-8
 * @version $id$
 */
@Results({@Result(name="list",location="/WEB-INF/pages/userImage/list.ftl"),
    @Result(name="show",location="/WEB-INF/pages/userImage/show.ftl")})
@SuppressWarnings("serial")
public class UserImageAction  extends BaseAction {

    @Resource
    private UserImageService userImageService;
    
    @Resource
    private UserImageCommentService userImageCommentService;
    
    @Resource
    private TagService tagService;
    
    @Resource
    private UserService userService;
    
    @Resource
    private UserFollowService userFollowService;
    
    @Resource
    private UserEnjoyService userEnjoyService;    
    
    @Resource 
    private MemCachedClient memCachedClient;
    
    @Resource
    private ActionLogService actionLogService;
    
    @Resource
    private PopularService popularService;
    
    public String addJson(){
        try {
            ActionContext ctx=ActionContext.getContext();
            HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST); 
            BufferedInputStream inputStream = new BufferedInputStream(
                    request.getInputStream());
            User user=this.getSessionUser();
            UserImage userImage=new UserImage();
            userImage.setPageNo(pageNo);
            userImage.setIssueId(issueId);
            userImage.setDescription(description);
            userImage.setKeyword(keyword);
            userImage.setWidth(width);
            userImage.setHeight(height);
            userImage.setUserId(user.getId());
            userImage.setUserAvatar(user.getAvatar());
            userImage.setUserNickName(user.getNickName());
            this.jsonResult=userImageService.insert(userImage,inputStream);
            if(jsonResult.getCode()==JsonResult.CODE.SUCCESS){
            	userService.setUserEnjoyList(user);
                //纪录用户的操作日志
                ActionLog actionLog=this.generateActionLog(
                        PojoConstant.ACTIONLOG.ACTIONTYPEID_USERIMAGE,
                        ((UserImage)this.jsonResult.getData().get("userImage")).getId());
                actionLogService.insertActionLog(actionLog); 
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.generateJsonResult(JsonResult.CODE.EXCEPTION, "服务器内部错误");
        } 
        return JSON;
    }
    
    public String deleteJson(){
        UserImage userImage=new UserImage();
        userImage.setId(id);
        userImage.setUserId(this.getSessionUserId());
        userImage.setStatus(PojoConstant.USERIMAGE.STATUS_DELETE);
        this.jsonResult=userImageService.update(userImage);
        return JSON;
    }
    
    public String updateJson(){
        UserImage userImage=new UserImage();
        userImage=new UserImage();
        userImage.setId(id);
        userImage.setDescription(description);
        userImage.setUserId(this.getSessionUserId());
        this.jsonResult=userImageService.update(userImage);
        return JSON;
    }
    /**
     * 最新用户图片
     * @return
     */
    public String now(){
        //生成时间戳
        Date timestamp=new Date();
        //将时间戳保存到memcache,让所有用户共享
        memCachedClient.set(CacheConstants.USERIMAGE_NEW_TIMESTAMP, timestamp);
        //查询最新图片标签
        tagList=tagService.getNewTagList(timestamp, 0, 30);
        //查询最新用户图片
        userImageList=userImageService.getNewUserImageList(tagName, timestamp, begin, size);
        this.tab="now";
        return "list";
    }
    
    /**
     * 最新用户图片(供滚动加载用)
     * @return
     */
    public String nowJson(){
        //获取时间戳
        Date timestamp=(Date)memCachedClient.get(CacheConstants.USERIMAGE_NEW_TIMESTAMP);
        //查询最新用户图片
        userImageList=userImageService.getNewUserImageList(tagName, timestamp, begin, size);
        this.generateJsonResult(JsonResult.CODE.SUCCESS, JsonResult.MESSAGE.SUCCESS, "userImageList", userImageList);
        return JSON;
    }
    
    /**
     * 最热用户图片
     * @return
     */
    public String hot(){
        Date timestamp=new Date();
        memCachedClient.set(CacheConstants.USERIMAGE_HOT_TIMESTAMP, timestamp);
        tagList=tagService.getHotTagList(timestamp, 0, 30);
        userImageList=userImageService.getHotUserImageList(tagName, timestamp, begin, size);
        this.tab="hot";
        return "list";
    }
    
    /**
     * 最热用户图片(供滚动加载用)
     * @return
     */
    public String hotJson(){
        Date timestamp=(Date)memCachedClient.get(CacheConstants.USERIMAGE_HOT_TIMESTAMP);
        userImageList=userImageService.getHotUserImageList(tagName, timestamp, begin, size);
        this.generateJsonResult(JsonResult.CODE.SUCCESS, JsonResult.MESSAGE.SUCCESS, "userImageList", userImageList);
        return JSON;
    }  
    
    /**
     * 推荐用户图片
     * @return
     */
    public String commend(){
        Date timestamp=new Date();
        memCachedClient.set(CacheConstants.USERIMAGE_COMMEND_TIMESTAMP, timestamp);
        tagList=tagService.getCommendTagList(timestamp, 0, 30);
        userImageList=userImageService.getCommendUserImageList(tagName, timestamp, begin, size);
        this.tab="commend";
        return "list";
    }
    
    /**
     * 推荐用户图片(供滚动加载用)
     * @return
     */
    public String commendJson(){
        Date timestamp=(Date)memCachedClient.get(CacheConstants.USERIMAGE_COMMEND_TIMESTAMP);
        userImageList=userImageService.getCommendUserImageList(tagName, timestamp, begin, size);
        this.generateJsonResult(JsonResult.CODE.SUCCESS, JsonResult.MESSAGE.SUCCESS, "userImageList", userImageList);
        return JSON;
    } 
    
    /**
     * 用户图片最终页
     * @return
     */
    public String show(){
        Long userId=this.getSessionUserId();
        //用户图片的信息
        this.userImage=userImageService.getUserImageById(this.imageId);
        if(this.userImage!=null&&PojoConstant.USERIMAGE.STATUS_OK.equals(this.userImage.getStatus())){
            //用户图片的评论
            this.userImageCommentList=userImageCommentService.getList(this.imageId,null,null);
            //本人查看自己的图片,则把评论置为已读
            if(this.userImage.getUserId().equals(userId)){
                userImageCommentService.updateToReaded(imageId);
            }
            //用户图片的作者
            this.user=userService.getUserById(this.userImage.getUserId());
            if(this.user!=null){
                //作者的喜欢数
                this.user.put("enjoyNum", userService.getEnjoyNumByUserId(this.user.getId()));
                //作者的好友数
                this.user.put("friendNum", userService.getFriendNumByUserId(this.user.getId()));
                //作者的切米数
                this.user.put("userImageNum", userService.getUserImageNumByUserId(this.user.getId()));
                //是否好友关系:1-是,0-否
                this.isFollow=userFollowService.isFollow(userId,this.user.getId(),PojoConstant.USERFOLLOW.TYPE_USER);
            }
            //前6条相关图片
            this.preUserImageList=userImageService.getPreUserImageList(imageId,this.tagName,0,6);
            //后6条相关图片
            this.sufUserImageList=userImageService.getSufUserImageList(imageId,this.tagName,0,6);
            //用户图片的标签,按热度排列
            this.tagList=tagService.getTagListByImageId(imageId,null,null);
            //是否喜欢:1-是,0-否
            this.isEnjoy=userEnjoyService.isEnjoy(userId,imageId,PojoConstant.USERENJOY.TYPE_IMAGE);
            
            this.userImageList=userImageService.getUserImageListByUserId(null, userImage.getUserId(), 0, 8);
            
            this.enjoyUserImageList=userImageService.getAlsoEnjoyUserImageListByImageId(imageId, 0, 8);
            //对应的点击数+1
            popularService.click(imageId, PojoConstant.POPULAR.TYPE_IMAGE);
        }
        return "show";
    }
    
    private String tab;
    private String tagName;
    private Integer begin=0;
    private Integer size=20;
    private List<Tag> tagList;
    private List<UserImage> userImageList;
    private List<UserImage> enjoyUserImageList;
    
    private Long imageId;
    private UserImage userImage;
    private Issue issue;
    private List<UserImage> preUserImageList;
    private List<UserImage> sufUserImageList;
    private User user;
    private List<UserImageComment> userImageCommentList;
    private List<User> userList;
    //是否关注:1-是,0-否
    private Integer isFollow;
    //是否喜欢:1-是,0-否
    private Integer isEnjoy;
    
    private Integer pageNo;
    private Long issueId;
    private String description;
    private String keyword;
    private float width;
    private float height;
    private Long id;

    public String getTagName() {
        return tagName;
    }
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
    public Integer getBegin() {
        return begin;
    }
    public void setBegin(Integer begin) 
    {
        if(begin==null||begin<0){
            begin=0;
        }
        this.begin = begin;
    }
    public Integer getSize() {
        return size;
    }
    
    //限制每次最多查出100条数据
    public void setSize(Integer size) {
        if(size!=null&&size>100){
            size=100;
        }else if(size==null||size<0){
            size=20;
        }
        this.size = size;
    }
    
    public List<Tag> getTagList() {
        return tagList;
    }
    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }
    public List<UserImage> getUserImageList() {
        return userImageList;
    }
    public void setUserImageList(List<UserImage> userImageList) {
        this.userImageList = userImageList;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public UserImage getUserImage() {
        return userImage;
    }

    public void setUserImage(UserImage userImage) {
        this.userImage = userImage;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public List<UserImage> getPreUserImageList() {
        return preUserImageList;
    }

    public void setPreUserImageList(List<UserImage> preUserImageList) {
        this.preUserImageList = preUserImageList;
    }

    public List<UserImage> getSufUserImageList() {
        return sufUserImageList;
    }

    public void setSufUserImageList(List<UserImage> sufUserImageList) {
        this.sufUserImageList = sufUserImageList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<UserImageComment> getUserImageCommentList() {
        return userImageCommentList;
    }

    public void setUserImageCommentList(List<UserImageComment> userImageCommentList) {
        this.userImageCommentList = userImageCommentList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
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

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public String getTab() {
		return tab;
	}

	public void setTab(String tab) {
		this.tab = tab;
	}

	public List<UserImage> getEnjoyUserImageList() {
		return enjoyUserImageList;
	}

	public void setEnjoyUserImageList(List<UserImage> enjoyUserImageList) {
		this.enjoyUserImageList = enjoyUserImageList;
	}
}
