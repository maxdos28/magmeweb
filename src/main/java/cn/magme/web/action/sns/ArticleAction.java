package cn.magme.web.action.sns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant;
import cn.magme.constants.sns.SNSConstant;
import cn.magme.pojo.Article;
import cn.magme.pojo.CreativeArticleRel;
import cn.magme.pojo.User;
import cn.magme.pojo.sns.ArticleEx;
import cn.magme.pojo.sns.Creative;
import cn.magme.result.sns.UserInfoResult;
import cn.magme.service.UserService;
import cn.magme.service.sns.ArticleExService;
import cn.magme.service.sns.ArticleService;
import cn.magme.service.sns.CreativeArticleRelService;
import cn.magme.service.sns.CreativeService;
import cn.magme.service.sns.SnsCreativeCommentService;
import cn.magme.service.sns.SnsUserIndexService;
import cn.magme.util.HtmlParserUtil;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;
/**
 * 
 * @author fredy
 * @since 2012-12-17
 */
@Results({@Result(name="success",location="/WEB-INF/pages/sns/m1userindex.ftl"),
	@Result(name = "index", location = "/WEB-INF/pages/index.ftl"),
	@Result(name="m1square",type="redirect",location="/sns/square.action"),
	@Result(name = "mindeMore", location = "/WEB-INF/pages/sns/m1userindexMore.ftl")})
public class ArticleAction extends BaseAction {
	private static final long serialVersionUID = 8860871924527908919L;
	private static final Logger log=Logger.getLogger(ArticleAction.class);
	
	@Resource
	private ArticleService articleService;
	
	@Resource
	private ArticleExService articleExService;

	@Resource
	private CreativeArticleRelService creativeArticleRelService;
	
	@Resource
	private CreativeService creativeService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private SnsCreativeCommentService snsCreativeCommentService;
	
	@Resource
	private SnsUserIndexService snsUserIndexService;
	
	private static final int MAX_CONTENT_LENGTH=600;
	
	//private static final Pattern p=Pattern.compile(".*<b?r?./?>?");
	
	private static final int PAGE_SIZE=10;
	
	
	
	/**
	 * 删除作品
	 * @return
	 */
	public String delJson(){
		this.jsonResult=JsonResult.getFailure();
		if(this.articleId==null || this.articleId<=0){
			this.jsonResult.setMessage("删除文章失败，文章id必须大于0");
			return JSON;
		}
		CreativeArticleRel creativeArticleRel=creativeArticleRelService.queryByArticleId(articleId);
		if(creativeArticleRel==null){
			this.jsonResult.setMessage("删除文章失败，文章和作品关系不存在");
			return JSON;
		}
		Creative creative=this.creativeService.queryById(creativeArticleRel.getCreativeId());
		if(creative==null || creative.getStatus()==null || creative.getStatus()!=PojoConstant.CREATIVE.STATUS_OK){
			this.jsonResult.setMessage("删除文章失败，文章对应的作品状态不存在或者状态不正确,articleId;"+this.articleId);
			return JSON;
		}
		if(creative.getUserId()==null || creative.getUserId().longValue()!=this.getSessionUserId()){
			this.jsonResult.setMessage("删除文章失败，只能删除自己的文章,articleId;"+this.articleId);
			return JSON;
		}
		if(this.articleService.deleteById(articleId)>0){
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}
	
	public String delCreativeJson(){
		this.jsonResult=JsonResult.getFailure();
		if(this.creativeId==null || this.creativeId<=0){
			this.jsonResult.setMessage("删除作品失败，作品id必须大于0");
			return JSON;
		}
		Creative c=this.creativeService.queryById(creativeId);
		if(c==null || c.getUserId()==null|| c.getUserId()<=0 || c.getUserId().longValue()!=this.getSessionUserId()){
			this.jsonResult.setMessage("删除作品失败,id不正确或者作品不属于这个登录用户");
			return JSON;
		}
		if(this.creativeService.deleteById(creativeId)>0){
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		}
		return JSON;
	}
	/**
	 * m1导航按钮
	 * @return
	 */
	public String execute(){
		if(this.getSessionUser()==null){
			return "m1square";
		}
		this.userId=this.getSessionUserId();
		return mindex();
	}
	
	/**
	 * 个人首页和按照tag查询
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String mindex(){
		if(userId==null || this.userId<=0){
			log.error("查看m1广场首页，用户id");
			return "m1square";
		}
		this.commentFlag();
		user=userService.getUserById(userId);
		if(user==null || user.getStatus()!=PojoConstant.USER.STATUS_ACTIVE.intValue()){
			log.error("查看m1广场首页，用户id不正确或用户状态不正确,userId;"+userId);
			return "m1square";
		}
		//如果是查看自己的作品，则所有都是可见的
		boolean isOnlyPub=true;
		if(this.getSessionUserId()!=null && userId.longValue()==this.getSessionUserId()){
			isOnlyPub=false;
		}
		creativeList=this.creativeService.queryByUserIdAndPloy(userId, isOnlyPub,begin,PAGE_SIZE);
		this.creativeList=cutContent(creativeList);
		/*Map  cnu= new HashMap();
		cnu.put("userid", userId);
		creativeNumUser=snsUserIndexService.getCreativeNumUserTop(cnu);*/
		
		Map m=new HashMap();
		m.put("id", userId);
		m.put("u",this.getSessionUserId()==null?0:this.getSessionUserId());
		userInfo= snsUserIndexService.getUserInfo(m);
		//登陆用户看别人的作品
		if(getSessionUserId()!=null && userId!=getSessionUserId().intValue()){
			m.put("u",this.getSessionUserId());
			similarAttention=snsUserIndexService.getSimilarAttention(m);
		}
		m.put("u",userId);
		similarFans = snsUserIndexService.getSimilarFans(m);
		
		//推荐用户
		Map map = new HashMap();
		map.put("begin", begin);
		map.put("size", 5);
		if(this.getSessionUserId()!=null){
			map.put("userid", this.getSessionUserId());	
		}
		userInfoList = snsUserIndexService.getLoginSquareUserList(map);
		
		return SUCCESS;
	}
	
	/**
	 * 滚动加载
	 * @return
	 */
	public String mindexMore(){
		boolean isOnlyPub=true;
		if(this.getSessionUserId()!=null && userId.longValue()==this.getSessionUserId()){
			isOnlyPub=false;
		}
		user=userService.getUserById(userId);
		creativeList=this.creativeService.queryByUserIdAndPloy(userId, isOnlyPub,begin,PAGE_SIZE);
		this.creativeList=cutContent(creativeList);
		return "mindeMore";
	}
	
	public String queryByCid(){
		this.jsonResult=JsonResult.getFailure();
		if(this.creativeId==null || creativeId<=0){
			this.jsonResult.setMessage("传入参数错误，creativeId必须大于0");
			return JSON;
		}
		try {
			List<Article> articleList=this.articleService.queryByCreativeId(creativeId);
			if(articleList==null || articleList.size()<=0){
				this.jsonResult.setMessage("查询没有数据,creativeId:"+creativeId);
				return JSON;
			}
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
			this.jsonResult.put("articleList", articleList);
		} catch (Exception e) {
			log.error("", e);
		}
		return JSON;
	}
	
	/**
	 * 按照作品id或者文章id查询图片
	 * @return
	 */
	public String queryPicJson(){
		this.jsonResult=JsonResult.getFailure();
		if((this.articleId==null || this.articleId<=0) && (this.creativeId==null || this.creativeId<=0)){
			this.jsonResult.setMessage("参数错误");
			return JSON;
		}
		List<String> picList=new ArrayList<String>();
		List<ArticleEx> articleExList=null;
		if(articleId!=null && articleId>0){
			articleExList=this.articleExService.getArticleEx(articleId);
		}else if(this.creativeId!=null && this.creativeId>0){
			articleExList=this.articleExService.queryByCreativeId(creativeId);
		}
		
		if(articleExList!=null && articleExList.size()>0){
			for(ArticleEx cEx:articleExList){
				picList.add(cEx.getImgPath());
			}
			this.jsonResult.put("picList", picList);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}else{
			this.jsonResult.setMessage("查询不到图片,articleId:"+articleId);
		}
		return JSON;
	}
	
	//获取用户新评论数
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void commentFlag(){
		Map map=new HashMap();
		map.put("userid",this.getSessionUserId()==null?0:this.getSessionUserId());
		map.put("readed","1");
		commentFlag = snsCreativeCommentService.getCommentListFromSize(map);
	}
	
	
	private List<Creative> cutContent(List<Creative> creativeList){
		if(creativeList==null || creativeList.size()<=0){
			return null;
		}
		for(Creative c:creativeList){
			if(StringUtil.isNotBlank(c.getContent()) && c.getContent().length()>MAX_CONTENT_LENGTH){
				String temp = HtmlParserUtil.parser(c.getContent(),SNSConstant.CUT_SIZE);
				c.setContent(temp);
			}
		}
		return creativeList;
	}
	
	
	
	
	private List<UserInfoResult> userInfoList;
	
	private Long articleId;
	
	private Long userId;
	
	private int begin=0;
	
	private String tagName;
	
	private List<Creative> creativeList;
	
	private User user;
	
	private List<UserInfoResult> similarAttention;
	private List<UserInfoResult> similarFans;
	private List<UserInfoResult> creativeNumUser;
	private UserInfoResult userInfo;
	
	private Long creativeId;
	
	
	
	
	
	public void setCreativeId(Long creativeId) {
		this.creativeId = creativeId;
	}
	public List<UserInfoResult> getUserInfoList() {
		return userInfoList;
	}
	public void setUserInfoList(List<UserInfoResult> userInfoList) {
		this.userInfoList = userInfoList;
	}
	public UserInfoResult getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfoResult userInfo) {
		this.userInfo = userInfo;
	}
	public List<UserInfoResult> getCreativeNumUser() {
		return creativeNumUser;
	}
	public void setCreativeNumUser(List<UserInfoResult> creativeNumUser) {
		this.creativeNumUser = creativeNumUser;
	}
	public List<UserInfoResult> getSimilarAttention() {
		return similarAttention;
	}
	public void setSimilarAttention(List<UserInfoResult> similarAttention) {
		this.similarAttention = similarAttention;
	}
	public List<UserInfoResult> getSimilarFans() {
		return similarFans;
	}
	public void setSimilarFans(List<UserInfoResult> similarFans) {
		this.similarFans = similarFans;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Creative> getCreativeList() {
		return creativeList;
	}
	public void setCreativeList(List<Creative> creativeList) {
		this.creativeList = creativeList;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	/**
	 * 最新评论
	 */
	private Integer commentFlag;
	

	public Integer getCommentFlag() {
		return commentFlag;
	}

	public void setCommentFlag(Integer commentFlag) {
		this.commentFlag = commentFlag;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}
	
	

}
