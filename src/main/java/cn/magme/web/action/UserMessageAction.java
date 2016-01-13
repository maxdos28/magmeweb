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
import cn.magme.pojo.ContentInfo;
import cn.magme.pojo.Message;
import cn.magme.pojo.User;
import cn.magme.service.MessageService;

/**
 * @author jacky_zhou
 * @date 2011-6-7
 * @version $id$
 */
@Results({@Result(name="read_success",location="/WEB-INF/pages/message/read.ftl"),
    @Result(name="toWrite_success",location="/WEB-INF/pages/message/write.ftl"),
    @Result(name="manage_success",location="/WEB-INF/pages/message/manage.ftl")})
@SuppressWarnings("serial")
public class UserMessageAction extends BaseAction {

    @Resource
    private MessageService messageService;
    
    /**
     * 通过收信人昵称群发消息,多个昵称之间以中文逗号或英文逗号分隔
     * @return
     */
    public String batchSendJson(){
        this.jsonResult=messageService.batchSendMessage(
                PojoConstant.MESSAGE.FROMTYPE_USER,this.getSessionUserId(),
                PojoConstant.MESSAGE.TOTYPE_USER,PojoConstant.MESSAGE.TYPE_NORMAL,nickName,content);
        return JSON;
    }
    
    /**
     * 通过收信人ID群发消息
     * @return
     */
    public String sendJson(){
        Message message=new Message();
        message.setFromUserId(this.getSessionUserId());
        message.setFromType(PojoConstant.MESSAGE.FROMTYPE_USER);
        message.setToType(this.toType);
        message.setType(PojoConstant.MESSAGE.TYPE_NORMAL);
        ContentInfo contentInfo=new ContentInfo();
        contentInfo.setContent(this.content);
        message.setContentInfo(contentInfo);
        this.jsonResult=messageService.insertMessage(message,this.toUserIds);
        return JSON;
    }
    
    /**
     * 通用的发送消息消息方法
     * @return
     */
    public String sendCommonJson(){
    	Message message=new Message();
        message.setFromUserId(this.fromUserId);
        message.setFromType(this.fromType);
        message.setToType(this.toType);
        message.setType(PojoConstant.MESSAGE.TYPE_NORMAL);
        ContentInfo contentInfo=new ContentInfo();
        contentInfo.setContent(this.content);
        message.setContentInfo(contentInfo);
        this.jsonResult=messageService.insertMessage(message,this.toUserIds);
        return JSON;
    }
    
    /**
     * 删除单条消息
     * 删除消息
     * @return
     */
    public String deleteJson(){
        messageService.batchLogicDeleteMessage(
                this.getSessionUserId(), PojoConstant.MESSAGE.TOTYPE_USER, messageIds);
        this.generateJsonResult(JsonResult.CODE.SUCCESS, JsonResult.MESSAGE.SUCCESS);
        return JSON;
    }
    
    /**
     * 删除消息,此请求会把messageId对应的消息的发件人与当前登陆用户之间的所有消息都删除
     * @return
     */
    public String batchDeleteJson(){
        messageService.batchLogicDeleteMessage(
                this.getSessionUserId(), PojoConstant.MESSAGE.TOTYPE_USER, messageId);
        this.generateJsonResult(JsonResult.CODE.SUCCESS, JsonResult.MESSAGE.SUCCESS);
        return JSON;
    }
    
    private String title;
    private String content;
    private Long[] messageIds;
    private Long messageId;
    private Long fromUserId;
    private Integer fromType;
    private Long toUserId;
    private Long[] toUserIds;
    private Integer toType;
    private List<Message> messageList;
    private String nickName;
    
    /**
     * 好友列表
     */
    private List<User> friendList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long[] getMessageIds() {
        return messageIds;
    }

    public void setMessageIds(Long[] messageIds) {
        this.messageIds = messageIds;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
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

    public Integer getToType() {
        return toType;
    }

    public void setToType(Integer toType) {
        this.toType = toType;
    }

    public List<User> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<User> friendList) {
        this.friendList = friendList;
    }

    public Long[] getToUserIds() {
        return toUserIds;
    }

    public void setToUserIds(Long[] toUserIds) {
        this.toUserIds = toUserIds;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
