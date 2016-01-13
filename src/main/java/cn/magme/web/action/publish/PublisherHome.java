/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.publish;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.constants.PojoConstant;
import cn.magme.pojo.Issue;
import cn.magme.pojo.Message;
import cn.magme.pojo.Publisher;
import cn.magme.pojo.Tag;
import cn.magme.pojo.User;
import cn.magme.pojo.UserFollow;
import cn.magme.service.IssueService;
import cn.magme.service.MessageService;
import cn.magme.service.PublisherService;
import cn.magme.service.TagService;
import cn.magme.service.UserEnjoyService;
import cn.magme.service.UserFollowService;
import cn.magme.service.UserImageService;
import cn.magme.util.NumberUtil;
import cn.magme.web.action.BaseAction;

/**
 * @author fredy.liu
 * @date 2011-9-27
 * @version $id$
 */
@Results({@Result(name="publisherHomeSuccess",location="/WEB-INF/pages/publish/publisherHome.ftl"),
	       @Result(name="commentsAjax",location="/WEB-INF/pages/publish/commentsAjax.ftl")})
public class PublisherHome extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4050703803184937358L;
	
	@Resource
	private IssueService issueService;
	
	@Resource
	private UserFollowService userFollowService;
	
	@Resource
	private PublisherService publisherService;
	
	@Resource
	private TagService tagService;
	
	@Resource
	private UserEnjoyService userEnjoyService;
	
	@Resource
	private UserImageService userImageService;
	
	@Resource
	private MessageService messageService;
	
	private static final String PUBLISHER_HOME_SUCCESS="publisherHomeSuccess";
	
	private static final Logger log=Logger.getLogger(PublisherHome.class);
	
    private static final Integer DEFAULT_PAGE_SIZE=20;
	
	public String pubHome(){
		this.showAnswer=1;
		this.execute();
		return PUBLISHER_HOME_SUCCESS;
	}
	
	public String execute(){
		int [] statuses=new int[]{1};
		try {
			//出版商
			publisher=publisherService.queryById(publisherId);
			//查询期刊
			issueList=issueService.queryByPublisherIdAndStatuses(publisherId, statuses);
			//查询粉丝
			userFollowList=userFollowService.getFollowsByObjIdAndType(publisherId, PojoConstant.USERFOLLOW.TYPE_PUBLISHER);
			//期刊标签
			issueTagList=tagService.queryIssueTagByPIdOrderByCount(publisherId);
			//图片标签
			imageTagList=tagService.queryImageTagByPidOrderByCount(publisherId);
			//标签计数
			tagCount=(issueTagList==null || issueTagList.size()<=0)?0:issueTagList.size();
			tagCount+=(imageTagList==null || imageTagList.size()<=0)?0:imageTagList.size();
			//喜欢数
			enjoyNum=this.userEnjoyService.queryEnjoyCountByPublisherId(publisherId);
			//用户所切图片数
			imageNum=this.userImageService.queryImageCountByPublisherId(publisherId);
			
			//是否关注
			User user=this.getSessionUser();
			if(user!=null && !NumberUtil.isLessThanOrEqual0(user.getId())){
				UserFollow userFollow=new UserFollow();
				userFollow.setStatus(PojoConstant.USERFOLLOW.STATUS_OK);
				userFollow.setObjectId(publisherId);
				userFollow.setType(PojoConstant.USERFOLLOW.TYPE_PUBLISHER);
				userFollow.setUserId(user.getId());
				List<UserFollow> userFollowList=userFollowService.getByCondition(userFollow);
				if(userFollowList!=null && userFollowList.size()>0){
					concern=1;
				}
			}
			//留言
			getComments();
			
		} catch (Exception e) {
			log.error("出版商首页出错 PublisherHome.execute()", e);
		}
		return PUBLISHER_HOME_SUCCESS;
	}
	
	private void getComments(){
		try {
			begin=(pageNo-1)*size;
			//消息总数
			messageCount=messageService.getMessageCountByToUserIdAndToType(publisherId,
					PojoConstant.MESSAGE.TOTYPE_PUBLISHER,PojoConstant.MESSAGE.STATUS_OK);
			//留言
			messageList=messageService.getByPublisherId(publisherId, begin, size);
		} catch (Exception e) {
			log.error("", e);
		}
	}
	
	public String comments(){
		getComments();
		return "commentsAjax";
	}
	/**
	 * 出版商id
	 */
	private long publisherId;
	
	private List<Issue> issueList;
	
	private Publisher publisher;
	
	private List<User> userFollowList;
	
	private List<Tag> issueTagList;
	
	private List<Tag> imageTagList;
	
	private int tagCount;
	
	private int enjoyNum;
	
	private int imageNum;
	
	private int concern=0;
	
	private List<Message> messageList;
	
	private Integer begin=0;
	
	private Integer size=DEFAULT_PAGE_SIZE;
	
	private Integer messageCount;
	
	private int pageNo=1;
	
	private int showAnswer=0;
	
	
	
	
	
	
	
	
	public int getShowAnswer() {
		return showAnswer;
	}

	public void setShowAnswer(int showAnswer) {
		this.showAnswer = showAnswer;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getMessageCount() {
		return messageCount;
	}

	public void setMessageCount(Integer messageCount) {
		this.messageCount = messageCount;
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

	public List<Message> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<Message> messageList) {
		this.messageList = messageList;
	}

	public int getConcern() {
		return concern;
	}

	public void setConcern(int concern) {
		this.concern = concern;
	}

	public int getImageNum() {
		return imageNum;
	}

	public void setImageNum(int imageNum) {
		this.imageNum = imageNum;
	}

	public int getEnjoyNum() {
		return enjoyNum;
	}

	public void setEnjoyNum(int enjoyNum) {
		this.enjoyNum = enjoyNum;
	}

	public int getTagCount() {
		return tagCount;
	}

	public void setTagCount(int tagCount) {
		this.tagCount = tagCount;
	}

	public List<Tag> getIssueTagList() {
		return issueTagList;
	}

	public void setIssueTagList(List<Tag> issueTagList) {
		this.issueTagList = issueTagList;
	}

	public List<Tag> getImageTagList() {
		return imageTagList;
	}

	public void setImageTagList(List<Tag> imageTagList) {
		this.imageTagList = imageTagList;
	}

	public List<User> getUserFollowList() {
		return userFollowList;
	}

	public void setUserFollowList(List<User> userFollowList) {
		this.userFollowList = userFollowList;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(long publisherId) {
		this.publisherId = publisherId;
	}

	public List<Issue> getIssueList() {
		return issueList;
	}

	public void setIssueList(List<Issue> issueList) {
		this.issueList = issueList;
	}
	
	
	

}
