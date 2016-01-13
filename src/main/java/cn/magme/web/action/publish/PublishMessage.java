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

import cn.magme.common.JsonResult;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.AdAgency;
import cn.magme.pojo.ContentInfo;
import cn.magme.pojo.Message;
import cn.magme.pojo.Publisher;
import cn.magme.service.AdAgencyService;
import cn.magme.service.MessageService;
import cn.magme.service.PublisherService;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

/**
 * @author fredy.liu
 * @date 2011-10-18
 * @version $id$
 */
@Results({@Result(name="pub_message_success",location="/WEB-INF/pages/publishadmin/manageMgzMsg.ftl"),
	      @Result(name="pub_message_detail_success",location="/WEB-INF/pages/publishadmin/manageMgzMsgDetail.ftl"),
	      @Result(name="pub_writeMessage_success",location="/WEB-INF/pages/publishadmin/manageMgzMsgWrite.ftl")})
public class PublishMessage extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5015674222333697118L;
	
	private static final Logger log=Logger.getLogger(PublishMessage.class);
	
	private static final String PATTERN_STR=",|，";
	
	@Resource
	private MessageService messageService;
	
	@Resource
	private PublisherService publisherService;
	
	@Resource
	private AdAgencyService adAgencyService;
	
	/**
	 * 消息列表
	 */
	public String execute(){
		try {
			Integer []fromTypeArr=new Integer[]{PojoConstant.MESSAGE.FROMTYPE_ADVERTISER,PojoConstant.MESSAGE.FROMTYPE_PUBLISHER};
			this.messageList=messageService.getByToTypeToUserIdFromTypeArr(this.getSessionPublisherId(),
					PojoConstant.MESSAGE.TOTYPE_PUBLISHER, fromTypeArr, null, null);
			this.getSessionPublisher().setMessageCount(0);
		} catch (Exception e) {
			log.error("", e);
		}
		return "pub_message_success";
	}
	
	/**
	 * 写消息
	 * @return
	 */
	public String writeMessage(){
        return "pub_writeMessage_success";
    }

	/**
	 * 批量发送消息
	 * @return
	 */
	public String batchSendMessageJson(){
		
		try {
			this.jsonResult=new JsonResult();
			this.jsonResult.setCode(JsonResult.CODE.FAILURE);
			jsonResult.setMessage(JsonResult.MESSAGE.FAILURE);
			if(StringUtil.isBlank(toUserNames)){
				jsonResult.setMessage("发件人为空");
				return JSON;
			}
			String [] userNamesArr=toUserNames.split(PATTERN_STR);
			Long [] publisherUser=new Long[userNamesArr.length];
			int i=0;
			Long [] adAgencyUser=new Long[userNamesArr.length];
			int j=0;
			for(String name:userNamesArr){
				if(StringUtil.isNotBlank(name)){
					Publisher sendPublisher=publisherService.queryByName(name);
					AdAgency adAgency=adAgencyService.queryByUserName(name);
					if(sendPublisher!=null){
						publisherUser[i++]=sendPublisher.getId();
					}
					if(adAgency!=null){
						adAgencyUser[j++]=adAgency.getId();
					}
				}
			}
			
			//发送给广告商
			Message message=new Message();
			message.setFromUserId(this.getSessionAdUserId());
			message.setFromType(fromType);
			message.setToType(PojoConstant.MESSAGE.TOTYPE_ADVERTISER);
			message.setType(PojoConstant.MESSAGE.TYPE_NORMAL);
			ContentInfo contentInfo=new ContentInfo();
			contentInfo.setContent(this.content);
			message.setContentInfo(contentInfo);
			this.jsonResult=messageService.insertMessage(message,adAgencyUser);
			
           //发送给出版商
			Message message2=new Message();
			message2.setFromUserId(this.getSessionAdUserId());
			message2.setFromType(fromType);
			message2.setToType(PojoConstant.MESSAGE.TOTYPE_PUBLISHER);
			message2.setType(PojoConstant.MESSAGE.TYPE_NORMAL);
			ContentInfo contentInfo2=new ContentInfo();
			contentInfo2.setContent(this.content);
			message2.setContentInfo(contentInfo2);
			this.jsonResult=messageService.insertMessage(message2,publisherUser);
		} catch (Exception e) {
			log.error("", e);
		}
		
		return JSON;
	}
	
	/**
     * 删除单条消息
     * 删除消息
     * @return
     */
    public String deleteJson(){
    	if(this.toType==null){
    		this.toType=PojoConstant.MESSAGE.TOTYPE_PUBLISHER;
    	}
        messageService.batchLogicDeleteMessage(
                this.toUserId, toType, messageIds);
        this.generateJsonResult(JsonResult.CODE.SUCCESS, JsonResult.MESSAGE.SUCCESS);
        return JSON;
    }
    
    /**
     * 删除消息,此请求会把messageId对应的消息的发件人与当前登陆用户之间的所有消息都删除
     * @return
     */
    public String batchDeleteJson(){
        messageService.batchLogicDeleteMessage(
                this.toUserId, this.toType, messageId);
        this.generateJsonResult(JsonResult.CODE.SUCCESS, JsonResult.MESSAGE.SUCCESS);
        return JSON;
    }
    
    
	/**
	 * 消息详情
	 * @return
	 */
	public String msgDetail(){
		try {
			this.messageList=messageService.getMessageList(
					    fromUserId, this.fromType, 
			            this.getSessionPublisherId(), PojoConstant.MESSAGE.TOTYPE_PUBLISHER,null, null);     
			
			if(this.fromType==PojoConstant.MESSAGE.FROMTYPE_PUBLISHER){
				fromUserName=this.publisherService.queryById(fromUserId).getPublishName();
			}else if(this.fromType==PojoConstant.MESSAGE.FROMTYPE_ADVERTISER){
				fromUserName=this.adAgencyService.queryById(fromUserId).getUserName();
			}
		} catch (Exception e) {
			log.error("", e);
		}
		return "pub_message_detail_success";
	}
	
	
	

	private Integer toType;
	

	private Integer fromType;
	
	private Long fromUserId;
	
	private String toUserNames;
	
	private String content;
	
	private String fromUserName;
	
	private Long messageId;
	
	private Long[] messageIds;
	
	private Long toUserId;
	
	
	
	
	
	

	public Long getToUserId() {
		return toUserId;
	}

	public void setToUserId(Long toUserId) {
		this.toUserId = toUserId;
	}

	public Integer getToType() {
		return toType;
	}

	public void setToType(Integer toType) {
		this.toType = toType;
	}

	public String getToUserNames() {
		return toUserNames;
	}

	public void setToUserNames(String toUserNames) {
		this.toUserNames = toUserNames;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public Long[] getMessageIds() {
		return messageIds;
	}

	public void setMessageIds(Long[] messageIds) {
		this.messageIds = messageIds;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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


	private List<Message> messageList;

	public List<Message> getMessageList() {
		return messageList;
	}


	public void setMessageList(List<Message> messageList) {
		this.messageList = messageList;
	}
	
	
	
	
}
