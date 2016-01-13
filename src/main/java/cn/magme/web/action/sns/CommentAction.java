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

import cn.magme.common.JsonResult;
import cn.magme.constants.WebConstant;
import cn.magme.constants.sns.SNSConstant;
import cn.magme.pojo.FpageEventComment;
import cn.magme.pojo.User;
import cn.magme.pojo.sns.CreativeComment;
import cn.magme.pojo.sns.InviteCode;
import cn.magme.pojo.sns.M1CommentReply;
import cn.magme.result.sns.CreativeCommentListResult;
import cn.magme.result.sns.PublicResult;
import cn.magme.service.FpageEventCommentService;
import cn.magme.service.UserService;
import cn.magme.service.sns.M1CommentReplyService;
import cn.magme.service.sns.SnsCreativeCommentService;
import cn.magme.service.sns.SnsInviteCodeService;
import cn.magme.service.sns.SnsPublicService;
import cn.magme.service.sns.SnsUserIndexService;
import cn.magme.web.action.BaseAction;

import com.opensymphony.xwork2.ActionContext;

@Results({
	@Result(name="success",location="/WEB-INF/pages/sns/comment.ftl"),
	@Result(name="list",location="/WEB-INF/pages/sns/comment_list.ftl"),
	@Result(name="to",location="/WEB-INF/pages/sns/comment_to.ftl"),
	@Result(name="page",location="/WEB-INF/pages/sns/comment_page.ftl")
})
@SuppressWarnings({ "unchecked", "rawtypes" })
public class CommentAction  extends BaseAction{
	private static final long serialVersionUID = 2517798315270944705L;
	private static final Logger log=Logger.getLogger(CommentAction.class);
	
	@Resource
	private SnsPublicService snsPublicService;
	@Resource
	private SnsUserIndexService snsUserIndexService;
	@Resource
	private SnsCreativeCommentService snsCreativeCommentService;
	@Resource
	private UserService userService;
	@Resource
	private M1CommentReplyService m1CommentReplyService;
	@Resource
	private FpageEventCommentService fpageEventCommentService;
	/**
	 * 加载别人对我的评论
	 */
	@Override
	public String execute() throws Exception {
		op="from";
		page=1;
		
		if( page<=1 && (pageTimeLock==null || pageTimeLock.equals(""))){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 java.util.Date now = new Date();
			 pageTimeLock=df.format(now);
		 }
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("userid", getSessionUserId());
		map.put("size", SNSConstant.SNS_COMMENT_SIZE);
		map.put("timelock", pageTimeLock);
		if(page>1)
			begin=(page-1)*SNSConstant.SNS_COMMENT_SIZE;
		map.put("begin", begin);
		commentList = snsCreativeCommentService.getCommentListFrom(map);
		
		if(page<=1){
			snsCreativeCommentService.updateCommentByUser(getSessionUserId());
			//更新用户收到的回复
			Map<String, Object> reply=new HashMap<String, Object>();
			reply.put("receiveuser", getSessionUserId());
			reply.put("readed", 0);
			m1CommentReplyService.update(reply);
			
			Integer count =snsCreativeCommentService.getCommentListFromSize(map);
			Integer m=count%SNSConstant.SNS_COMMENT_SIZE;
			
			if(count>SNSConstant.SNS_COMMENT_SIZE && m>1){
				pageCount=(count/SNSConstant.SNS_COMMENT_SIZE)+1;
			}else if(count>SNSConstant.SNS_COMMENT_SIZE){
				pageCount=count/SNSConstant.SNS_COMMENT_SIZE;
			}else{
				pageCount=1;
			}
			
			
			commentListTo = snsCreativeCommentService.getCommentListTo(map);
			
			User user =userService.getUserById(getSessionUserId());
			ActionContext.getContext().getSession().put(WebConstant.SESSION.USER, user);
			
			attention=snsUserIndexService.getAttention(this.getSessionUserId());
			creative=snsUserIndexService.getCreativeCount(this.getSessionUserId());
			fans=snsUserIndexService.getFans(this.getSessionUserId());
			
			PublicRes(this.getSessionUserId());
			
		}else
			return "list";
		
		return SUCCESS;
	}
	
	public String from(){
		if( page<=1 && (pageTimeLock==null || pageTimeLock.equals(""))){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 java.util.Date now = new Date();
			 pageTimeLock=df.format(now);
		}
		if(page<=1){
			snsCreativeCommentService.updateCommentByUser(getSessionUserId());
			//更新用户收到的回复
			Map<String, Object> reply=new HashMap<String, Object>();
			reply.put("receiveuser", getSessionUserId());
			reply.put("readed", 0);
			m1CommentReplyService.update(reply);
		}
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("userid", getSessionUserId());
		map.put("size", SNSConstant.SNS_COMMENT_SIZE);
		map.put("timelock", pageTimeLock);
		if(page>1)
			begin=(page-1)*SNSConstant.SNS_COMMENT_SIZE;
		map.put("begin", begin);
		commentList = snsCreativeCommentService.getCommentListFrom(map);
		
		return "list";
	}
	
	public String pageCount(){
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("userid", getSessionUserId());
		map.put("timelock", pageTimeLock);
		Integer count =0;
		if(op.equals("from"))
			count =	snsCreativeCommentService.getCommentListFromSize(map);
		else
			count =	snsCreativeCommentService.getCommentListToSize(map);
		
		Integer m=count%SNSConstant.SNS_COMMENT_SIZE;
		
		if(count>SNSConstant.SNS_COMMENT_SIZE && m>1){
			pageCount=(count/SNSConstant.SNS_COMMENT_SIZE)+1;
		}else if(count>SNSConstant.SNS_COMMENT_SIZE){
			pageCount=count/SNSConstant.SNS_COMMENT_SIZE;
		}else{
			pageCount=1;
		}
		
		return "page";
	}
	
	/**
	 * 加载我的评论
	 */
	public String to(){
		op="to";
		try{
			if(pageTimeLock==null || pageTimeLock.equals("")){
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				 java.util.Date now = new Date();
				 pageTimeLock=df.format(now);
			 }
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("userid", getSessionUserId());
			map.put("size", SNSConstant.SNS_COMMENT_SIZE);
			map.put("timelock", pageTimeLock);
			if(page>1)
				begin=(page-1)*SNSConstant.SNS_COMMENT_SIZE;
			map.put("begin", begin);
			commentList = snsCreativeCommentService.getCommentListTo(map);
		}catch (Exception e) {
			log.error("", e);
		}
		return "to";
	}
	
	/**
	 * 删除自己发表的评论
	 */
	public String del(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		
		try{
			if(ct==5){
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("userid", getSessionUserId());
				map.put("id", commentId);
				CreativeComment c = snsCreativeCommentService.getCreativeComment(map);
				//判断该评论是否存在
				if(null !=c )
					snsCreativeCommentService.updateComment(c);
			}else if(ct==6){
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("userid", getSessionUserId());
				map.put("status", 0);
				map.put("id", commentId);
				m1CommentReplyService.update(map);
			}else{
				FpageEventComment f = fpageEventCommentService.getById(commentId);
				if(f.getUserId().intValue()==getSessionUserId().intValue()){
					f.setContent("");
					f.setStatus(0);
					fpageEventCommentService.updateComment(f);
				}
			}
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		}catch (Exception e) {
			log.error("", e);
		}
		return JSON;
	}
	
	public String upd(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		
		try{
			
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		}catch (Exception e) {
			log.error("", e);
		}
		return JSON;
	}
	
	
	private void PublicRes(Long uid){
		Map condition = new HashMap();
		condition.put("userId", uid);
		publicAuthor=snsPublicService.getPubRecUser(condition);
		publicCreative=snsPublicService.getPubCreative();
		newCreative=snsPublicService.getNewestCreative();
	}
	
	
	public String reply(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		if(oid==null || uid==null || cid==null)
			this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		else{
			M1CommentReply m = new M1CommentReply();
			m.setContent(con);
			m.setcType(1);
			m.setObjectId(oid);
			m.setReplyUser(getSessionUserId());
			m.setReceiveUser(uid);
			m.setCreativeId(cid);
			m.setLevel(level);
			m.setStatus(1);
			m.setReaded(1);
			m1CommentReplyService.insert(m);
			
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		}
		
		return JSON;
	}
	
	private Integer begin=0;
	private String op;
	private Integer ct;
	private Integer page=1;
	private Integer pageCount;
	private Long commentId;
	private Integer attention;
	private Integer creative;
	private Integer fans;
	private String pageTimeLock;
	private List<PublicResult> publicAuthor;
	private List<PublicResult> publicCreative;
	private List<PublicResult> newCreative;
	private List<CreativeCommentListResult> commentList;
	private List<CreativeCommentListResult> commentListTo;
	private List<InviteCode> inviteCode;
	private Integer isInviteCode;
	private Long oid;
	private Long cid;
	private Long uid;
	private Integer level;
	private String con;
	
	
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public void setCon(String con) {
		this.con = con;
	}

	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Integer getIsInviteCode() {
		return isInviteCode;
	}

	public Integer getBegin() {
		return begin;
	}
	
	public void setCt(Integer ct) {
		this.ct = ct;
	}

	public void setBegin(Integer begin) {
		this.begin = begin;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}
	
	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
	
	public Integer getPageCount() {
		return pageCount;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
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

	public List<PublicResult> getPublicAuthor() {
		return publicAuthor;
	}

	public List<PublicResult> getPublicCreative() {
		return publicCreative;
	}

	public List<PublicResult> getNewCreative() {
		return newCreative;
	}

	public List<CreativeCommentListResult> getCommentList() {
		return commentList;
	}

	public List<InviteCode> getInviteCode() {
		return inviteCode;
	}

	public String getPageTimeLock() {
		return pageTimeLock;
	}

	public void setPageTimeLock(String pageTimeLock) {
		this.pageTimeLock = pageTimeLock;
	}

	public List<CreativeCommentListResult> getCommentListTo() {
		return commentListTo;
	}
	
	
}
