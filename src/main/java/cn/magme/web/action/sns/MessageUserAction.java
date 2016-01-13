/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.sns;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.constants.PojoConstant;
import cn.magme.pojo.Message;
import cn.magme.pojo.sns.UserEx;
import cn.magme.service.MessageService;
import cn.magme.service.sns.SnsUserIndexService;
import cn.magme.service.sns.UserExService;
import cn.magme.web.action.BaseAction;

/**
 * @author billy
 * @date 2012-06-06
 * @version $id$
 */
@Results({ 
    @Result(name="readMessage",location="/WEB-INF/pages/sns/sns_readMessage.ftl"),
    @Result(name="writeMessage",location="/WEB-INF/pages/sns/sns_writeMessage.ftl"),
	@Result(name = "success", location = "/WEB-INF/pages/sns/sns_message.ftl")
	})
public class MessageUserAction extends BaseAction {
	private static final long serialVersionUID = 2621634495296982720L;

	@Resource
	private UserExService userExService;

    @Resource
    private MessageService messageService;
	
	private UserEx userEx;
    private List<Message> messageList;

    private Long fromUserId;
    private Integer fromType;


	private Integer attention;
	private Integer creative;
	private Integer fans;

	@Resource
	private SnsUserIndexService snsUserIndexService;

	@Override
	public String execute() {
		statsMap();
		Long userId = getSessionUserId();
		userEx = userExService.getByUserId(userId);
		messageList = messageService.getMessageListByToUserIdAndToType(
                		userId, PojoConstant.MESSAGE.TOTYPE_USER, null, null);
		return "success";
	}

    /**
     * 米客中心-读消息
     * @return
     */
    public String readMessage(){
        statsMap();
        messageList = new LinkedList<Message>();
		List<Message> list = messageService.getMessageList(
                this.getFromUserId(), this.getFromType(), 
                this.getSessionUserId(), PojoConstant.MESSAGE.TOTYPE_USER,null, null);  
		while(list.size() > 0){
			messageList.add(list.remove(list.size() - 1));
		}       
        return "readMessage";
    }
    
    /**
     * 米客中心-跳转到写消息的页面
     * @return
     */
    public String writeMessage(){
        statsMap();          
        return "writeMessage";
    }
    
	/**
	 * 查询用户的统计信息
	 */
	private void statsMap() {
		attention = snsUserIndexService.getAttention(this.getSessionUserId());
		creative = snsUserIndexService.getCreativeCount(this.getSessionUserId());
		fans = snsUserIndexService.getFans(this.getSessionUserId());
	}
	
	public void setUserEx(UserEx userEx) {
		this.userEx = userEx;
	}

	public UserEx getUserEx() {
		return userEx;
	}

	public Integer getAttention() {
		return attention;
	}

	public void setAttention(Integer attention) {
		this.attention = attention;
	}

	public Integer getCreative() {
		return creative;
	}

	public void setCreative(Integer creative) {
		this.creative = creative;
	}

	public Integer getFans() {
		return fans;
	}

	public void setFans(Integer fans) {
		this.fans = fans;
	}

	public void setMessageList(List<Message> messageList) {
		this.messageList = messageList;
	}

	public List<Message> getMessageList() {
		return messageList;
	}

	public void setFromUserId(Long fromUserId) {
		this.fromUserId = fromUserId;
	}

	public Long getFromUserId() {
		return fromUserId;
	}

	public void setFromType(Integer fromType) {
		this.fromType = fromType;
	}

	public Integer getFromType() {
		return fromType;
	}
}
