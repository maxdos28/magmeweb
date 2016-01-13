package cn.magme.web.action.sns;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import cn.magme.common.JsonResult;
import cn.magme.pojo.sns.CreativeComment;
import cn.magme.result.sns.CreativeCommentResult;
import cn.magme.result.sns.CreativeExResult;
import cn.magme.result.sns.PublicResult;
import cn.magme.service.sns.SnsCreativeCommentService;
import cn.magme.service.sns.SnsCreativeExService;
import cn.magme.service.sns.SnsInviteCodeService;
import cn.magme.service.sns.SnsPublicService;
import cn.magme.web.action.BaseAction;

import com.opensymphony.xwork2.ActionContext;


public class CreativeCommentAction extends BaseAction{
	private static final long serialVersionUID = -3044920417182238566L;
	private static final Logger log=Logger.getLogger(CreativeCommentAction.class);
	private static final Integer COMMENT=1;
	private static final Integer FORWARD=2;
	
	@Resource
	private SnsPublicService snsPublicService;
	
	@Resource
	private SnsCreativeExService snsCreativeExService;
	
	@Resource
	private SnsCreativeCommentService snsCreativeCommentService;
	@Resource
	private SnsInviteCodeService snsInviteCodeService;
	
	private static final int DEFAULT_SIZE=4;
	
	@Override
	public String execute() throws Exception {
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		try{
			if(tags!=null && !tags.equals(""))
				creativeTag=snsCreativeExService.getCreativeExTop(tags);
			
			Map<String, Object> cMap = new HashMap<String, Object>();
			cMap.put("creativeId", creative);
			cMap.put("size", 4);
			comment=snsCreativeCommentService.getCreativeCommentList(cMap);
			
			creativeEnjoy=snsPublicService.getCreativeEnjoy(creative);
			creativeOther=snsCreativeExService.getUserCreativeExByU(uid);
			if(publisher!=null && !publisher.equals(""))
				creativePublisher=snsCreativeExService.getUserCreativeExByP(publisher);
			
			Map<String, Object> fMap = new HashMap<String, Object>();
			fMap.put("creativeId", creative);
			fMap.put("size", 10);
			forwarding=snsCreativeCommentService.getCreativeForwardList(fMap);
			Integer fcount=snsCreativeCommentService.getForwardCount(fMap);
			
			this.jsonResult.put("creOther", creativeOther);
			this.jsonResult.put("same", creativeTag);
			this.jsonResult.put("comment", comment);
			this.jsonResult.put("publisher", creativePublisher);
			this.jsonResult.put("enjoy", creativeEnjoy);
			this.jsonResult.put("forward", forwarding);
			this.jsonResult.put("forwardCount", fcount);
			this.jsonResult.put("userId", this.getSessionUserId());
			
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		}catch (Exception e) {
			log.error("", e);
		}
		
		return JSON;
	}
	
	public String commentList(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		Map<String, Object> cMap = new HashMap<String, Object>();
		cMap.put("creativeId", creative);
		cMap.put("begin", begin);
		cMap.put("size", size);
		comment=snsCreativeCommentService.getCreativeCommentList(cMap);
		this.jsonResult.put("comment", comment);
		this.jsonResult.put("userId", this.getSessionUserId());
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		return JSON;
	}
	
	@SuppressWarnings("unchecked")
	public String comment()  {
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		if(getSessionUserId()!=null && getSessionUserId()>0 ){
			boolean insert=false;
			/*//判断用户是否有邀请码
			String code = String.valueOf(ActionContext.getContext().getSession().get("inviteCodeSession"));
			if(code==null || "null".equals(code) || "".equals(code)){
				Map map = new HashMap();
				map.put("inviteCode", null);
				map.put("use", getSessionUserId());
				int num = snsInviteCodeService.getCkInviteCode(map);
				if(num==0){
					insert=false;
					this.jsonResult.put("no_invite_code",1);
				}else{
					insert=true;
				}
			}else*/
				insert=true;
			if(insert){
				this.jsonResult.put("no_invite_code",0);
				//ActionContext.getContext().getSession().put("inviteCodeSession",getSessionUserId());
				//添加评论
				CreativeComment c = new CreativeComment();
				c.setContent(commentval);
				c.setCreativeId(creative);
				c.setcType(COMMENT);
				c.setUserId(getSessionUserId());
				c= snsCreativeCommentService.insertCreativeComment(c);
				Map<String, Object> cMap = new HashMap<String, Object>();
				cMap.put("creativeId", creative);
				cMap.put("size", 4);
				comment=snsCreativeCommentService.getCreativeCommentList(cMap);
				this.jsonResult.put("comment", comment);
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				this.jsonResult.put("userId", this.getSessionUserId());
			}
		}
		return JSON;
	}
	
	public String forward(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		if(getSessionUserId()!=null && getSessionUserId()>0 ){
					
			CreativeComment c = new CreativeComment();
			c.setContent(null);
			c.setCreativeId(creative);
			c.setcType(FORWARD);
			c.setUserId(getSessionUserId());
			c= snsCreativeCommentService.insertCreativeComment(c);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		}
		return JSON;
	}
	
	
	private Long creative;
	private String tags;
	private String commentval;
	private Long uid;
	private String publisher;
	private Integer begin;
	private List<CreativeExResult> creativeTag;
	private List<CreativeExResult> creativeOther;
	private List<CreativeExResult> creativePublisher;
	private List<PublicResult> creativeEnjoy;
	private List<CreativeCommentResult> comment;
	private List<CreativeCommentResult> forwarding;
	private int size=DEFAULT_SIZE;
	
	
	
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setBegin(Integer begin) {
		this.begin = begin;
	}

	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public void setCommentval(String commentval) {
		this.commentval =commentval.replace("<", "&lt;").replace(">", "&gt;");
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public void setCreative(Long creative) {
		this.creative = creative;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public List<CreativeCommentResult> getComment() {
		return comment;
	}
	public List<CreativeExResult> getCreativeTag() {
		return creativeTag;
	}
	public List<CreativeExResult> getCreativeOther() {
		return creativeOther;
	}
	public List<CreativeExResult> getCreativePublisher() {
		return creativePublisher;
	}
	public List<CreativeCommentResult> getForwarding() {
		return forwarding;
	}
	public void setCreativeEnjoy(List<PublicResult> creativeEnjoy) {
		this.creativeEnjoy = creativeEnjoy;
	}
	
	
}
