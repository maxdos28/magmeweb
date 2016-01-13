package cn.magme.web.action.sns;

import java.util.ArrayList;
import java.util.Date;
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
import cn.magme.pojo.FpageEvent;
import cn.magme.pojo.Issue;
import cn.magme.pojo.Tag;
import cn.magme.pojo.User;
import cn.magme.pojo.sns.Creative;
import cn.magme.pojo.sns.CreativeEx;
import cn.magme.pojo.sns.CreativeUser;
import cn.magme.result.sns.PublicResult;
import cn.magme.result.sns.UserInfoResult;
import cn.magme.service.FpageEventService;
import cn.magme.service.IssueService;
import cn.magme.service.LuceneService;
import cn.magme.service.TagService;
import cn.magme.service.UserService;
import cn.magme.service.sns.SnsCreativeExService;
import cn.magme.service.sns.SnsCreativeService;
import cn.magme.service.sns.SnsCreativeUserService;
import cn.magme.service.sns.SnsInviteCodeService;
import cn.magme.service.sns.SnsUserIndexService;
import cn.magme.service.sns.UserExService;
import cn.magme.web.action.BaseAction;

import com.opensymphony.xwork2.ActionContext;


@Results({
	@Result(name="success",location="/WEB-INF/pages/sns/creative.ftl"),
	@Result(name="index",type="redirect",location="/sns/user-index!home.action"),
	@Result(name="edit",location="/WEB-INF/pages/sns/edit.ftl"),
	@Result(name="view",location="/WEB-INF/pages/sns/view.ftl"),
	@Result(name="no_invite_code",type="redirect",location="/sns/sns-index!invite.action")
	})
/**
 * @see 用户创建发表  包括用户添加修改
 * @author xiaochen
 *
 */
public class CreativeAction extends BaseAction {
	private static final long serialVersionUID = 4859927503118096870L;
	private static final Logger log=Logger.getLogger(CreativeAction.class);
	@Resource
    private FpageEventService fpageEventService;
	@Resource
    private IssueService issueService;
	@Resource
	private SnsCreativeExService snsCreativeExService;
	@Resource
	private SnsCreativeService snsCreativeService;
	@Resource
	private SnsCreativeUserService snsCreativeUserService;
	@Resource
	private UserService userService;
	@Resource
	private UserExService userExService;
	@Resource
	private SnsUserIndexService snsUserIndexService;
	@Resource
    private LuceneService luceneService;
	@Resource
	private TagService tagService;
	@Resource
	private SnsInviteCodeService snsInviteCodeService;
	
	
	// 用户进入发表页面 参数 operate 为相应的操作
	@Override
	public String execute() throws Exception {
		/////////////////////华丽的分割线    内测后舍弃//////////////////////////////////
		//M1频道邀请码判断
		//判断用户是否已经登录  
		if(this.getSessionUser()==null)
			return "sns_login";
		else{
			
			/*Map map = new HashMap();
			map.put("inviteCode", null);
			map.put("use", getSessionUserId());
			int num = snsInviteCodeService.getCkInviteCode(map);
			if(num==0)
				return "no_invite_code";*/
		}
		///////////////////////////////////////////////////////////
		
		//目前只存在作品 不区分  所有链接都为混和作品  
		operate="works";
		
		event=userExService.getUserIsEvent(getSessionUserId());
		return SUCCESS;
	}
	//判断站内 事件 杂志 阅读链接 获取相关信息
	public String magazine(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
		
		try{
			if(magazineUrl!=null && magazineUrl.indexOf("action?id=")>-1){//杂志url判断
				String idstr=magazineUrl.substring(magazineUrl.lastIndexOf("id")+3);
				Long id=Long.valueOf(idstr);
				Issue issue = issueService.queryById(id);
				if(issue!=null){
					this.jsonResult.put("magazineName", issue.getPublicationName());
					this.jsonResult.put("issueid", issue.getId());
					this.jsonResult.put("publicationid", issue.getPublicationId());
					this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				}else
					this.jsonResult.setCode(JsonResult.CODE.FAILURE);
				 
			}else if(magazineUrl!=null && magazineUrl.indexOf("action?eventId=")>-1){//事件判断
				String idstr=magazineUrl.substring(magazineUrl.lastIndexOf("eventId=")+8);
				Long eventId=Long.valueOf(idstr);
				FpageEvent fpageEvent = fpageEventService.getFpageEventById(eventId);
				 if (fpageEvent != null && !fpageEvent.getStatus().equals(PojoConstant.FPAGEEVENT.STATUS_DELETE)) {
					 this.jsonResult.put("magazineName", fpageEvent.getPublicationName());
					 this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				 }else
					 this.jsonResult.setCode(JsonResult.CODE.FAILURE);
			}else if(magazineUrl!=null && magazineUrl.indexOf("&Id=")>-1){//阅读器获取链接判断
				String idstr=magazineUrl.substring(magazineUrl.lastIndexOf("&Id=")+4);
				Long id=Long.valueOf(idstr);
				Issue issue = issueService.queryById(id);
				if(issue!=null){
					this.jsonResult.put("magazineName", issue.getPublicationName());
					this.jsonResult.put("issueid", issue.getId());
					this.jsonResult.put("publicationid", issue.getPublicationId());
					this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				}else
					this.jsonResult.setCode(JsonResult.CODE.FAILURE);
			}
			
		}catch (Exception e) {
			log.error("", e);
		}
		return JSON;
	}
	
	//创建修改文字作品
	public String texta(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		try{
			int f = pubInsert(SNSConstant.SNS_CREATIVE_TEXTA);
			cmap.put("tx", null);
			if(f==0){
				Creative creative=snsCreativeService.insertCreative(cmap);
				luceneCreative(creative);
			}
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		}catch (Exception e) {
			log.error("", e);
		}
		return JSON;
	}
	
	//创建修改图片作品
	public String image(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		 
		int f = pubInsert(SNSConstant.SNS_CREATIVE_IMAGE);
		
		List<CreativeEx> exl = new ArrayList<CreativeEx>();
		try{
			List<CreativeEx> exList = new ArrayList<CreativeEx>();
			if(cid!=null && cid>0){
				 exList = snsCreativeExService.getCreativeEx(cid);
			}
			if(null!=conList && conList.size()>0 ){
				for (int i = 0; i < conList.size(); i++) {
					String lstr=conList.get(i);
					String[] list = lstr.split(";");
					boolean exflag=true;
					for(int j=0;j<exList.size();j++){
						CreativeEx e=exList.get(j);
						if(e.getImgPath().equals(list[0])){
							exflag=false;
							e.setContent(list[1]);
							e.setPosition(Integer.valueOf(list[2]));
							
							exl.add(e);
//							snsCreativeExService.updateCreativeEx(e);
							exList.remove(j);
						}
						break;
					}
					if(exflag){
						CreativeEx ex = new CreativeEx();
						ex.setConType(SNSConstant.SNS_CREATIVE_IMAGE);
						ex.setImgPath(list[0]);
						ex.setContent(list[1]);
						ex.setPosition(Integer.valueOf(list[2]));
						ex.setW(Integer.valueOf(list[3]));
						ex.setH(Integer.valueOf(list[4]));
						
						exl.add(ex);
//						snsCreativeExService.insertCreativeEx(ex);
					}
				}
				for (CreativeEx e:exList) {
					e.setStatus(0);
					
					exl.add(e);
//					snsCreativeExService.updateCreativeEx(e);
				}
			}
			cmap.put("ex", exl);
			if(f==0){
				Creative creative=snsCreativeService.insertCreative(cmap);
				luceneCreative(creative);
			}
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		}catch (Exception e) {
			log.error("", e);
		}
		
		return JSON;
	}
	//创建修改音乐作品
	public String music(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		int f = pubInsert(SNSConstant.SNS_CREATIVE_MUSIC);
		
		try{
			List<CreativeEx> exList=new ArrayList<CreativeEx>();
			if(cid==null || cid==0){
				
				if(null!=conList && conList.size()>0 ){
					for (int i = 0; i < conList.size(); i++) {
						String lstr=conList.get(i);
						String[] list = lstr.split(";");
						
						CreativeEx ex = new CreativeEx();
						ex.setCreativeId(creative.getId());
						ex.setConType(SNSConstant.SNS_CREATIVE_MUSIC);
						ex.setImgPath(list[0]);
						ex.setPath(list[1]);
						
						exList.add(ex);
//						snsCreativeExService.insertCreativeEx(ex);
					}
				}
			}
			cmap.put("ex", exList);
			if(f==0){
				Creative creative=snsCreativeService.insertCreative(cmap);
				luceneCreative(creative);
			}
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		}catch (Exception e) {
			log.error("", e);
		}
		
		return JSON;
	}
	//创建修改视频作品
	public String video(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		int f = pubInsert(SNSConstant.SNS_CREATIVE_VIDEO);
		List<CreativeEx> exList = new ArrayList<CreativeEx>();
		try{
			if(cid==null || cid==0){
				if(null!=conList && conList.size()>0 && creative!=null){
					for (int i = 0; i < conList.size(); i++) {
						String lstr=conList.get(i);
						String[] list = lstr.split("#");
						
						CreativeEx ex = new CreativeEx();
						ex.setConType(SNSConstant.SNS_CREATIVE_VIDEO);
						ex.setContent(list[0]);
						ex.setImgPath(list[1]);
						ex.setPath(list[2]);
						
						exList.add(ex);
//						snsCreativeExService.insertCreativeEx(ex);
					}
				}
			}
			cmap.put("ex", exList);
			if(f==0){
				Creative creative=snsCreativeService.insertCreative(cmap);
				luceneCreative(creative);
			}
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		}catch (Exception e) {
			log.error("", e);
		}
		
		return JSON;
	}
	//创建修改作品集
	public String works(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		int f = pubInsert(SNSConstant.SNS_CREATIVE_WORKS);
		List<CreativeEx> exl = new ArrayList<CreativeEx>();
		List<CreativeEx> exList =new ArrayList<CreativeEx>(); 
		if(cid!=null && cid>0){
			exList=snsCreativeExService.getCreativeEx(creative.getId());
		}
		try{
			//解析作品中的 图片、音乐、视频
			if(null!=conList && conList.size()>0 && creative!=null){
				for (int i = 0; i < conList.size(); i++) {
					String lstr=conList.get(i);
					String[] list = lstr.split(";");
					boolean wflag=true;
					for (int j = 0; j < exList.size(); j++) {
						CreativeEx w = exList.get(j);
						if(list[0].equals("video") && (w.getImgPath().equals(list[1]) || list[3].equals(w.getPath()))){
							int eid=Integer.valueOf(list[5]);
							if(w.getId().intValue()==eid && creative.getId().intValue()==w.getCreativeId().intValue()){
								wflag=false;
								w.setContent(list[2]);
								w.setPosition(Integer.valueOf(list[4]));
								
								exl.add(w);
								
								//snsCreativeExService.updateCreativeEx(w);
								exList.remove(j);
								break;
							}
						}else if(list[0].equals("music") && (w.getImgPath().equals(list[1]) || list[3].equals(w.getPath()))){
							int eid=Integer.valueOf(list[5]);
							if(w.getId().intValue()==eid && creative.getId().intValue()==w.getCreativeId().intValue()){
								wflag=false;
								w.setContent(list[2]);
								w.setPosition(Integer.valueOf(list[4]));
								
								exl.add(w);
								
								//snsCreativeExService.updateCreativeEx(w);
								exList.remove(j);
								break;
							}
						}else if(w.getImgPath().equals(list[1].replace(systemProp.getStaticServerUrl(), ""))){
							int eid=Integer.valueOf(list[4]);
							if(w.getId().intValue()==eid && creative.getId().intValue()==w.getCreativeId().intValue()){
								wflag=false;
								w.setContent(list[2]);
								w.setPosition(Integer.valueOf(list[3]));
								
								exl.add(w);
								
								//snsCreativeExService.updateCreativeEx(w);
								exList.remove(j);
								break;
							}
						}
					}
					if(wflag){
						CreativeEx ex = new CreativeEx();
						ex.setCreativeId(creative.getId());
						if(list[0].equals("video")){
							ex.setConType(SNSConstant.SNS_CREATIVE_VIDEO);
							ex.setImgPath(list[1]);
							ex.setContent(list[2]);
							ex.setPath(list[3]);
							ex.setPosition(Integer.valueOf(list[4]));
						}else if(list[0].equals("music")){
							ex.setConType(SNSConstant.SNS_CREATIVE_MUSIC);
							ex.setImgPath(list[1]);
							ex.setContent(list[2]);
							ex.setPath(list[3]);
							ex.setPosition(Integer.valueOf(list[4]));
						}else{
							ex.setConType(SNSConstant.SNS_CREATIVE_IMAGE);
							ex.setImgPath(list[1].replace(systemProp.getStaticServerUrl(), ""));
							ex.setContent(list[2]);
							ex.setPosition(Integer.valueOf(list[3]));
							ex.setW(Integer.valueOf(list[4]));
							ex.setH(Integer.valueOf(list[5]));
						}
						exl.add(ex);
//						snsCreativeExService.insertCreativeEx(ex);
					}
				}
				for(CreativeEx w:exList){
					w.setStatus(0);
					exl.add(w);
					//snsCreativeExService.updateCreativeEx(w);
				}
			}
			cmap.put("ex", exl);
			if(f==0){
				Creative creative=snsCreativeService.insertCreative(cmap);
				//luceneCreative(creative);
			}
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		}catch (Exception e) {
			log.error("", e);
		}
		
		return JSON;
	}
	
	//作品通用方法
	private int pubInsert(Integer type){
		delViewSession();
		if(cid!=null && cid>0){
			creative=snsCreativeService.getEditCreative(cid);
		}else{
			creative = new Creative();
		}
		if(creative==null)
			return 1;
		else{
			if(cid!=null && cid>0 && creative.getUserId().intValue()!=this.getSessionUserId().intValue())
				return 2;
		}
		if(cid!=null && cid>0 && creative!=null){
			creative.setId(cid);
		}else{
			creative.setAuthor(0L);
			creative.setComefrom(0L);
		}
		
		creative.setUserId(this.getSessionUserId());
		title=title.replace("<", "&lt;").replace(">", "&gt;").replace("'", "’");
		creative.setTitle(title);
		creative.setContent(content);
		creative.setcType(type);
		creative.setMagazineUrl(magazineUrl);
		creative.setMagazineName(magazineName);
		creative.setIssueid(issueid);
		creative.setPublicationid(publicationid);
		creative.setPloy(ploy);
		
		if(isEvent==1)
			creative.setIshome(2);
		else
			creative.setIshome(0);
		
		if(null!=showType && !showType.equals(""))
			creative.setShowType(Integer.valueOf(showType));
		else
			creative.setShowType(0);
		
		cmap.put("creative", creative);
		
		if(null!=tags && tags.size()>0){
			List<Tag> list = snsCreativeService.getEditCreativeTags(cid);
			List<Tag> tagList = new ArrayList<Tag>();
			
			for (int i = 0; i < tags.size(); i++) {
				boolean tflag=true;
				if(list!=null && list.size()>=0){
					for(Tag t :list){
						if(t.getName().equals(tags.get(i))){
							tflag=false;
							break;
						}
					}
				}
				if(tflag){
					Tag tag = new Tag();
					tag.setCreatedBy(getSessionUserId());
					tag.setName(tags.get(i));
					tag.setType(SNSConstant.SNS_TAG_TYPE);
					tag.setStatus(PojoConstant.TAG.STATUS_OK);
					
					tagList.add(tag);
					
//					tagService.insert(tag);
				}
			}
			cmap.put("tags", tagList);
		}
		if(null!=creativeUser && creativeUser.size()>0){
			List<CreativeUser> list = snsCreativeUserService.getCreativeUser(cid);
			List<CreativeUser> cuList=new ArrayList<CreativeUser>();
			
			for (int i = 0; i < creativeUser.size(); i++) {
				boolean eflag=true;
				if(list!=null && list.size()>0){
					for(int j=0; j<list.size();j++){
						CreativeUser cu = list.get(j);
						if(cu.getUserId().intValue()==Long.valueOf(creativeUser.get(i)).intValue()){
							eflag=false;
							if(cu.getStatus()==0){
								cu.setStatus(1);
								
								cuList.add(cu);
//								snsCreativeUserService.updateCreativeUser(cu);
							}
							list.remove(j);
							break;
						}
					}
				}
				if(eflag){
					CreativeUser cu = new CreativeUser();
					cu.setUserId(Long.valueOf(creativeUser.get(i)));
					
					cuList.add(cu);
//					snsCreativeUserService.insertCreativeUser(cu);
				}
			}
			for(CreativeUser cu:list){
				if(cu.getStatus()>0){
					cu.setStatus(0);
					cuList.add(cu);
				}
//				snsCreativeUserService.updateCreativeUser(cu);
			}
			cmap.put("cu", cuList);
		}
		
		return 0;
	}
	
	//lucene 更新创建   
	private void luceneCreative(Creative creative ){
		if(cid!=null && cid>0){
			luceneService.updateIndex(creative);
		}else{
			luceneService.addIndex(creative);
		}
	}
	//编辑
	public String edit(){
		if(cid==null || cid<=0)
			return "index";	
		creative=snsCreativeService.getEditCreative(cid);
		if(creative==null || creative.getUserId().intValue()!=getSessionUserId().intValue())
			return "index";
		event=userExService.getUserIsEvent(getSessionUserId());
		
		return "edit";
	}
	//获取作品内容
	public String getEditCreativeEx(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		List<CreativeEx> list = snsCreativeExService.getCreativeEx(cid);
		this.jsonResult.put("list", list);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		return JSON;
	}
	//获取作品标签
	public String getEditCreativeTag(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		List<Tag> list = snsCreativeService.getEditCreativeTags(cid);
		this.jsonResult.put("list", list);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		return JSON;
	}
	//参与者用户
	public String getEditCreativeUser(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		List<PublicResult> list = snsCreativeUserService.getEditCreativeUser(cid);
		this.jsonResult.put("list", list);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		return JSON;
	}
	//删除
	public String del(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		this.jsonResult.setMessage("删除失败");
		if(cid!=null && cid>0){
			creative=snsCreativeService.getEditCreative(cid);
			if(creative.getUserId().intValue()==getSessionUserId().intValue()){
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
				creative.setStatus(0);
				snsCreativeService.updateCreative(creative);
				luceneService.deleteIndex(creative);
			}else
				this.jsonResult.setMessage("不允许删除非作者发表");
		}
		return JSON;
	}
	//预览作品
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String view(){
		Map m=new HashMap();
		m.put("id", this.getSessionUserId());
		m.put("u",0);
		userInfo= snsUserIndexService.getUserInfo(m);
		return "view";
	}
	//保存作品相关内容到session 用于预览
	public String viewData(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		creative=new Creative();
		title=title.replace("<", "&lt;").replace(">", "&gt;").replace("'", "’");
		//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		creative.setUpdateTime(new Date());
		creative.setTitle(title);
		creative.setContent(content);
		creative.setcType(type);
		creative.setMagazineUrl(magazineUrl);
		creative.setMagazineName(magazineName);
		creative.setPloy(ploy);
		if(null!=showType && !showType.equals(""))
			creative.setShowType(Integer.valueOf(showType));
		else
			creative.setShowType(0);
		List<User> userList = new ArrayList<User>();
		if(creativeUser!=null)
			for (String uid : creativeUser) {
				User u=userService.getUserById(Long.valueOf(uid));
				u.setAddress(null);
				u.setEmail(null);
				u.setBirthdate(null);
				userList.add(u);
			}
		
		List<CreativeEx> exList = new ArrayList<CreativeEx>();
		if(null!=conList && conList.size()>0 && creative!=null){
			switch (type) {
			case 2:
				for (int i = 0; i < conList.size(); i++) {
					String lstr=conList.get(i);
					String[] list = lstr.split(";");
					CreativeEx ex = new CreativeEx();
					ex.setCreativeId(creative.getId());
					ex.setConType(SNSConstant.SNS_CREATIVE_IMAGE);
					ex.setImgPath(list[0]);
					ex.setContent(list[1]);
					ex.setPosition(Integer.valueOf(list[2]));
					exList.add(ex);	
				}
				break;
			case 3:
				for (int i = 0; i < conList.size(); i++) {
					String lstr=conList.get(i);
					String[] list = lstr.split(";");
					
					CreativeEx ex = new CreativeEx();
					ex.setCreativeId(creative.getId());
					ex.setConType(SNSConstant.SNS_CREATIVE_MUSIC);
					ex.setImgPath(list[0]);
					ex.setPath(list[1]);
					exList.add(ex);
				}
				break;
			case 4:
				for (int i = 0; i < conList.size(); i++) {
					String lstr=conList.get(i);
					String[] list = lstr.split("#");
					
					CreativeEx ex = new CreativeEx();
					ex.setCreativeId(creative.getId());
					ex.setConType(SNSConstant.SNS_CREATIVE_VIDEO);
					ex.setContent(list[0]);
					ex.setImgPath(list[1]);
					ex.setPath(list[2]);
					exList.add(ex);
				}
				break;
			case 5:
				for (int i = 0; i < conList.size(); i++) {
					String lstr=conList.get(i);
					String[] list = lstr.split(";");
					CreativeEx ex = new CreativeEx();
					ex.setCreativeId(creative.getId());
					if(list[0].equals("video")){
						ex.setConType(SNSConstant.SNS_CREATIVE_VIDEO);
						ex.setImgPath(list[1]);
						ex.setContent(list[2]);
						ex.setPath(list[3]);
						ex.setPosition(Integer.valueOf(list[4]));
					}else if(list[0].equals("music")){
						ex.setConType(SNSConstant.SNS_CREATIVE_MUSIC);
						ex.setImgPath(list[1]);
						ex.setContent(list[2]);
						ex.setPath(list[3]);
						ex.setPosition(Integer.valueOf(list[4]));
					}else{
						ex.setConType(SNSConstant.SNS_CREATIVE_IMAGE);
						ex.setImgPath(list[1]);
						ex.setContent(list[2]);
						ex.setPosition(Integer.valueOf(list[3]));
					}
					exList.add(ex);
				}
				break;
			default:
				
				break;
			}
		}
		
		ActionContext.getContext().getSession().put("sns_creative", creative);
		ActionContext.getContext().getSession().put("sns_creative_ex", exList);
		ActionContext.getContext().getSession().put("sns_creative_tags", tags);
		ActionContext.getContext().getSession().put("sns_creative_user", userList);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		return JSON;
	}
	
	
	private void delViewSession(){
		ActionContext.getContext().getSession().put("sns_creative", null);
		ActionContext.getContext().getSession().put("sns_creative_ex", null);
		ActionContext.getContext().getSession().put("sns_creative_tags", null);
		ActionContext.getContext().getSession().put("sns_creative_user", null);
	}
	
	
	public String forward(){
		this.jsonResult=new JsonResult();
		this.jsonResult.setCode(JsonResult.CODE.FAILURE);
		if( cid!=null && cid>1 ){
			try{
				Creative c = snsCreativeService.getEditCreative(cid);
				c.setId(null);
				Long f =0L;
				Long a =0L;
				if(c.getAuthor()==0 && c.getComefrom()==0){
					a=c.getUserId();
					f=c.getUserId();
				}else{
					a=c.getAuthor();
					f=c.getUserId();
				}
				c.setUserId(getSessionUserId());
				c.setAuthor(a);
				c.setComefrom(f);
				c.setIshome(0);
				c.setDescribed(null);
				
				Creative cre = snsCreativeService.insertCreative(c);
				
				
				List<Tag> list = snsCreativeService.getEditCreativeTags(cid);
				for (Tag t : list) {
					t.setObjectId(cre.getId());
					t.setCreatedBy(getSessionUserId());
					tagService.insert(t);
				}
				
				List<CreativeEx> ex = snsCreativeExService.getCreativeEx(cid);
				for(CreativeEx e : ex){
					e.setCreativeId(cre.getId());
					e.setId(null);
					snsCreativeExService.insertCreativeEx(e);
				}
				
				cid=null;
				luceneCreative(cre);
				this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			}catch (Exception e) {
				log.error(e);
			}
		}
		return JSON;
	}
	
	private Creative creative;
	private String operate;
	private String title;
	private String magazineUrl;
	private String magazineName;
	private List<String> creativeUser; 
	private UserInfoResult userInfo;
	private String content;
	private List<String>  tags;
	private Integer ploy;
	private String showType;
	private List<String> conList;
	private Integer type;
	private Long cid;
	private Integer event;
	private Integer isEvent=0;
	private Long issueid;
	private Long publicationid;
	private Map<String, Object> cmap=new HashMap<String, Object>();
	
	
	public void setIssueid(Long issueid) {
		this.issueid = issueid;
	}
	public void setPublicationid(Long publicationid) {
		this.publicationid = publicationid;
	}
	public Integer getIsEvent() {
		return isEvent;
	}
	public void setIsEvent(Integer isEvent) {
		this.isEvent = isEvent;
	}
	public Integer getEvent() {
		return event;
	}
	
	public UserInfoResult getUserInfo() {
		return userInfo;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public List<String> getConList() {
		return conList;
	}

	public void setConList(List<String> conList) {
		this.conList = conList;
	}

	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

	public Creative getCreative() {
		return creative;
	}

	public void setCreative(Creative creative) {
		this.creative = creative;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMagazineUrl() {
		return magazineUrl;
	}

	public void setMagazineUrl(String magazineUrl) {
		this.magazineUrl = magazineUrl;
	}

	public String getMagazineName() {
		return magazineName;
	}

	public void setMagazineName(String magazineName) {
		this.magazineName = magazineName;
	}


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getPloy() {
		return ploy;
	}

	public void setPloy(Integer ploy) {
		this.ploy = ploy;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public List<String> getCreativeUser() {
		return creativeUser;
	}

	public void setCreativeUser(List<String> creativeUser) {
		this.creativeUser = creativeUser;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
	
}
