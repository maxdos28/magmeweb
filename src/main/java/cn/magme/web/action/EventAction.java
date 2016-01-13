/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant;
import cn.magme.constants.WebConstant;
import cn.magme.pojo.ActionLog;
import cn.magme.pojo.EventComment;
import cn.magme.pojo.EventOpus;
import cn.magme.pojo.EventQa;
import cn.magme.pojo.EventVote;
import cn.magme.pojo.User;
import cn.magme.service.ActionLogService;
import cn.magme.service.EventCommentService;
import cn.magme.service.EventOpusService;
import cn.magme.service.EventQaService;
import cn.magme.service.EventVoteService;
import cn.magme.service.UserService;
import cn.magme.util.StringUtil;

import com.opensymphony.xwork2.ActionContext;

/**
 * 麦米事件
 * @author jacky_zhou
 * @date 2011-7-8
 * @version $id$
 */
@SuppressWarnings("serial")
@Results({@Result(name="index_20110720",location="/WEB-INF/pages/event/20110720/index.ftl"),
    @Result(name="join_20110720",location="/WEB-INF/pages/event/20110720/join.ftl"),
    @Result(name="qa_success",location="/WEB-INF/pages/event/20110720/qa.ftl"),
    @Result(name="opusList_success",location="/WEB-INF/pages/event/20110720/opusList.ftl"),
    @Result(name="toEditOpus_success",location="/WEB-INF/pages/event/20110720/editOpus.ftl"),
    @Result(name="introduction_20110720",location="/WEB-INF/pages/event/20110720/introduction.ftl"),
    @Result(name="opusShow_success",location="/WEB-INF/pages/event/20110720/opusShow.ftl"),
    @Result(name="show_20110720",location="/WEB-INF/pages/event/20110720/show.ftl"),
    @Result(name="read_20110720",location="/WEB-INF/pages/event/20110720/read.ftl")})
public class EventAction extends BaseAction {
    @Resource
    private EventQaService eventQaService;
    
    @Resource
    private EventOpusService eventOpusService; 
    
    @Resource
    private EventCommentService eventCommentService;
    
    @Resource
    private UserService userService;
    
    @Resource
    private EventVoteService eventVoteService;    
    
    @Resource
    private ActionLogService actionLogService;    
    
    /**
     * 活动首页
     * @return
     */
    public String index(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setHeader("Cache-Control", "max-age=" + systemProp.getPageCacheTimeout());         
        if(StringUtil.isBlank(this.pos)){
            this.pos="0";
        }
        return "index_"+this.eventCode;
    }
    
    /**
     * 应聘必读
     * @return
     */
    public String read(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setHeader("Cache-Control", "max-age=" + systemProp.getPageCacheTimeout());         
        if(StringUtil.isBlank(this.pos)){
            this.pos="90px";
        }
        return "read_"+this.eventCode;
    }
    
    /**
     * 活动介绍
     * @return
     */
    public String introduction(){
        if(StringUtil.isBlank(this.pos)){
            this.pos="30px";
        }
        return "introduction_"+this.eventCode;
    }
    
    /**
     * 报名参加
     * @return
     */
    public String join(){
        if(StringUtil.isBlank(this.pos)){
            this.pos="60px";
        }
        return "join_"+this.eventCode;
    }
    
    /**
     * 活动宣传
     * @return
     */
    public String show(){
        if(StringUtil.isBlank(this.pos)){
            this.pos="180px";
        }
        return "show_"+this.eventCode;
    }
    
    /**
     * 用户提问
     * @return
     */
    public String questionJson(){
        EventQa eventQa=new EventQa();
        eventQa.setUserId(this.getSessionUserId());
        eventQa.setContent(this.content);
        eventQa.setEventCode(this.eventCode);
        eventQa.setStatus(PojoConstant.EVENTQA.STATUS_OK);
        eventQa.setType(PojoConstant.EVENTQA.TYPE_QUESTION);
        this.jsonResult=eventQaService.insertEventQa(eventQa);
        return JSON;
    }
    
    /**
     * 用户提问
     * @return
     */
    public String qa(){
        this.eventQaList=eventQaService.getEventQaList(this.eventCode, 
                PojoConstant.EVENTQA.TYPE_QUESTION, PojoConstant.EVENTQA.STATUS_OK, null, null);
        if(StringUtil.isBlank(this.pos)){
            this.pos="120px";
        }
        return "qa_success";
    }    
    
    /**
     * 用户体验作品
     * @return
     */
    public String opusList(){
        Map<String,Object> param=new HashMap<String,Object>();
        param.put("eventCode", this.eventCode);
        param.put("status",PojoConstant.EVENTOPUS.STATUS_OK);
        param.put("begin", this.begin==null?0:this.begin);
        param.put("size", this.size==null?20:this.size);
        String ot=null;
        if("1".equals(this.orderType)){
            ot=" votenum desc ";
        }else if("2".equals(this.orderType)){
            ot=" clicknum desc ";
        }else if("3".equals(this.orderType)){
            ot=" commentnum desc ";
        }else{
            ot=" opus.createdTime desc ";
        }
        param.put("orderType", ot);
        this.eventOpusList=eventOpusService.getEventOpusList(param);
        if(StringUtil.isBlank(this.pos)){
            this.pos="150px";
        }
        return "opusList_success";
    }  
    
    /**
     * 用户体验作品(滚动加载)
     * @return
     */
    public String opusListJson(){
        Map<String,Object> param=new HashMap<String,Object>();
        param.put("eventCode", this.eventCode);
        param.put("status",PojoConstant.EVENTOPUS.STATUS_OK);
        param.put("begin", this.begin==null?0:this.begin);
        param.put("size", this.size==null?20:this.size);
        String ot=null;
        if("1".equals(this.orderType)){
            ot=" votenum desc ";
        }else if("2".equals(this.orderType)){
            ot=" clicknum desc ";
        }else if("3".equals(this.orderType)){
            ot=" commentnum desc ";
        }else{
            ot=" opus.createdTime desc ";
        }
        param.put("orderType", ot);
        this.eventOpusList=eventOpusService.getEventOpusList(param);
        this.generateJsonResult(JsonResult.CODE.SUCCESS, 
                JsonResult.MESSAGE.SUCCESS, "eventOpusList", this.eventOpusList);
        return JSON;
    }     
    
    /**
     * 跳转到用户上传体验作品
     * @return
     */
    public String toEditOpus(){
        if(StringUtil.isBlank(this.pos)){
            this.pos="60px";
        }           
        return "toEditOpus_success";
    }
    
    /**
     * 保存用户体验作品
     * @return
     */
    public String saveEventOpusJson(){
        EventOpus eventOpus=new EventOpus();
        eventOpus.setEventCode(this.eventCode);
        eventOpus.setTitle(this.title);
        if(StringUtil.isBlank(this.cover)){
            if(StringUtil.isNotBlank(this.content)&&this.content.toLowerCase().contains("embed")){
                this.cover="/event/20110706/iconVideo.gif";
            }
        }
        eventOpus.setCover(this.cover);
        eventOpus.setContent(this.content);
        eventOpus.setUserId(this.getSessionUserId());
        eventOpus.setStatus(PojoConstant.EVENTOPUS.STATUS_OK);
        this.jsonResult=eventOpusService.insertEventOpus(eventOpus);
        this.jsonResult.setData(null);
        return JSON;
    }

    /**
     * 查看用户体验作品
     * @return
     */
    public String opusShow(){
        if(StringUtil.isBlank(this.pos)){
            this.pos="90px";
        }
        
        //提交体验作品的用户信息
        this.actor=userService.getUserById(this.userId);
        
        //查询用户提交的体验作品列表(按创建时间倒序)
        Map<String,Object> param=new HashMap<String,Object>();
        param.put("userId", this.userId==null?0:this.userId);
        param.put("eventCode", "20110720");
        param.put("status", PojoConstant.EVENTOPUS.STATUS_OK);
        this.eventOpusList=eventOpusService.getEventOpusList(param);
        
        if(this.eventOpusList!=null&&!this.eventOpusList.isEmpty()){
            //最新作品的点击数加1
            this.eventOpus=this.eventOpusList.get(0);
            EventOpus tempOpus=new EventOpus();
            tempOpus.setId(this.eventOpus.getId());
            tempOpus.setClickNum(eventOpus.getClickNum()+1);
            eventOpusService.updateEventOpus(tempOpus);
            
            //查询每一个体验作品的前十条评论
            for(EventOpus opus:eventOpusList){
                List<EventComment> commentList=eventCommentService.getEventCommentList(
                        opus.getId(),PojoConstant.EVENTCOMMENT.STATUS_OK, 0, 10);
                Integer eventCommentCount=eventCommentService.getEventCommentCount(
                        opus.getId(),PojoConstant.EVENTCOMMENT.STATUS_OK);
                opus.setEventCommentList(commentList);
                opus.setEventCommentCount(eventCommentCount);
            }
        }
        
        return "opusShow_success";
    }
    
    /**
     * 登陆并评论
     * @return
     */
    public String loginCommentJson(){
        String session_authcode = (String) (ActionContext.getContext().getSession().get(WebConstant.SESSION.AUTHCODE));
        if(StringUtil.isNotBlank(session_authcode)&&session_authcode.equals(this.authcode)){
             User user=this.getSessionUser();
             //用户未登陆的情况下要做评论,那么先进行登陆
             if(user==null){
                 this.jsonResult=userService.login(userName,password);
                 //登陆成功
                 if(this.jsonResult.getCode()==JsonResult.CODE.SUCCESS){
                     user=(User)(this.jsonResult.getData().get("user"));
                   //校验通过,将登陆用户信息存放在SESSION
                     ActionContext.getContext().getSession().put(WebConstant.SESSION.USER, this.jsonResult.getData().get("user"));
                     //纪录用户的操作日志
                     ActionLog actionLog=this.generateActionLog(
                             PojoConstant.ACTIONLOG.ACTIONTYPEID_LOGIN,
                             ((User)this.jsonResult.getData().get("user")).getId());
                     actionLogService.insertActionLog(actionLog);
                 //登陆失败    
                 }else{
                     return JSON;
                 }
             }
             Map<String,Object> map=null;
             if(this.jsonResult!=null){
                 map=this.jsonResult.getData();
             }
             //做评论
             EventComment comment=new EventComment();
             comment.setContent(this.content);
             comment.setEventCode(this.eventCode);
             comment.setEventOpusId(this.eventOpusId);
             comment.setStatus(PojoConstant.EVENTCOMMENT.STATUS_OK);
             comment.setUserId(this.getSessionUserId());
             comment.setIpAddress(this.getRequestIpLong());
             
             this.jsonResult=eventCommentService.insertEventComment(comment);
             
             if(jsonResult.getCode()==JsonResult.CODE.SUCCESS){
               //移除校验码,确保校验码使用一次后就失效
                 ActionContext.getContext().getSession().remove(WebConstant.SESSION.AUTHCODE);
             }
             
             if(map!=null){
                 Set<String> keys=map.keySet();
                 for(String key:keys){
                     this.jsonResult.put(key, map.get(key));
                 }
             }
        }else{
            this.generateJsonResult(JsonResult.CODE.FAILURE, JsonResult.MESSAGE.FAILURE,"authcode","验证码错误");
        }
        return JSON;            
    }
    
    /**
     * 体验作品的评论列表(用于滚动加载)
     * @return
     */
    public String commentListJson(){
        this.eventCommentList=eventCommentService.getEventCommentList(
                this.eventOpusId,PojoConstant.EVENTCOMMENT.STATUS_OK, 
                this.begin==null?0:this.begin, this.size==null?10:this.size);
        this.generateJsonResult(JsonResult.CODE.SUCCESS, JsonResult.MESSAGE.SUCCESS, 
                "eventCommentList", this.eventCommentList);
        return JSON;
    }
    
    /**
     * 给体验作品投票
     * @return
     */
    public String voteJson(){
        EventVote eventVote=new EventVote();
        eventVote.setUserId(this.getSessionUserId());
        eventVote.setOpusId(this.eventOpusId);
        eventVote.setIpAddress(this.getRequestIpLong());
        this.jsonResult=eventVoteService.insertEventVote(eventVote);
        return JSON;
    }
    
    private String pos;
    private Long id;
    private String eventCode;
    private Long userId;
    private String content;
    private String title;
    private String cover;
    private Long relationId;
    private List<EventQa> eventQaList;
    private List<EventOpus> eventOpusList;
    private EventOpus eventOpus;
    private List<EventComment> eventCommentList;
    private Integer eventCommentCount;
    private String authcode;
    private String userName;
    private String password;
    private Long eventOpusId;
    private Integer begin;
    private Integer size;
    //排序类型
    private String orderType;
    //提交体验作品的用户
    private User actor;

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public List<EventQa> getEventQaList() {
        return eventQaList;
    }

    public void setEventQaList(List<EventQa> eventQaList) {
        this.eventQaList = eventQaList;
    }

    public List<EventOpus> getEventOpusList() {
        return eventOpusList;
    }

    public void setEventOpusList(List<EventOpus> eventOpusList) {
        this.eventOpusList = eventOpusList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public EventOpus getEventOpus() {
        return eventOpus;
    }

    public void setEventOpus(EventOpus eventOpus) {
        this.eventOpus = eventOpus;
    }

    public List<EventComment> getEventCommentList() {
        return eventCommentList;
    }

    public void setEventCommentList(List<EventComment> eventCommentList) {
        this.eventCommentList = eventCommentList;
    }

    public Integer getEventCommentCount() {
        return eventCommentCount;
    }

    public void setEventCommentCount(Integer eventCommentCount) {
        this.eventCommentCount = eventCommentCount;
    }

    public String getAuthcode() {
        return authcode;
    }

    public void setAuthcode(String authcode) {
        this.authcode = authcode;
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

    public Long getEventOpusId() {
        return eventOpusId;
    }

    public void setEventOpusId(Long eventOpusId) {
        this.eventOpusId = eventOpusId;
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

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public User getActor() {
        return actor;
    }

    public void setActor(User actor) {
        this.actor = actor;
    }
}
