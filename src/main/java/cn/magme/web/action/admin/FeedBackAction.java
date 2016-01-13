/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.admin;

import javax.annotation.Resource;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.ContentInfo;
import cn.magme.pojo.FeedBack;
import cn.magme.pojo.Message;
import cn.magme.service.FeedBackService;
import cn.magme.service.MessageService;
import cn.magme.util.ToJson;

/**
 * 反馈内容管理
 * @author shenhao
 * @date 2011-8-15
 * @version $id$
 */
@SuppressWarnings("serial")
public class FeedBackAction extends BaseAction {
	
	@Resource
	FeedBackService feedBackService;
	@Resource
	private MessageService messageService;

	// 页面信息(查询条件:状态)
	private FeedBack feedBack;
	
	// 站内消息内容
	private String message;

	/**
	 * 反馈内容：分页查询
	 */
	public void page() {
		page = this.feedBackService.getPageByCondition(page, feedBack);
		String info = ToJson.object2json(page);
		print(info);
	}
	
	/**
	 * 反馈内容：添加或更新
	 */
	public void commit() {
		Object[] arr = super.toJavaArr(info, FeedBack.class);
		FeedBack[] lst = castToFeedBack(arr);
		this.feedBackService.commit(lst);
	}
	
	/**
	 * 反馈内容：发站内系统消息
	 */
	public void msgSend() {
		Object[] arr = super.toJavaArr(info, FeedBack.class);
		FeedBack[] lst = castToFeedBack(arr);
		FeedBack feedBack = lst[0];
		
		feedBack.setStatus(2);
		//发站内消息，自动更新状态位为 2:已处理
		feedBackService.commit(new FeedBack[]{feedBack});
		
		//发站内消息通知对方
		Message message=new Message();
		//0表示是系统邮件
		message.setFromUserId(0L);
		message.setFromType(PojoConstant.MESSAGE.FROMTYPE_ADMIN);
		message.setToType(PojoConstant.MESSAGE.TOTYPE_USER);
		message.setToUserId(feedBack.getUserId());
		message.setType(PojoConstant.MESSAGE.TYPE_SYSTEM);
		ContentInfo contentInfo=new ContentInfo();
		contentInfo.setContent(this.message);
		message.setContentInfo(contentInfo);
		// 数据库保存该条信息
		messageService.insertMessage(message);
	}
	
	/**
	 * 数组类型转换： Object[] => FeedBack[]
	 * @param arr
	 * @return
	 */
	private FeedBack[] castToFeedBack(Object[] arr) {
		FeedBack[] infos = new FeedBack[arr.length];
		for (int i = 0; i < infos.length; i++) {
			infos[i] = (FeedBack) arr[i];
		}
		return infos;
	}

	/**
	 * 反馈内容取得
	 * @return
	 */
	public FeedBack getFeedBack() {
		return feedBack;
	}

	/**
	 * 反馈内容设定
	 * @param feedBack
	 */
	public void setFeedBack(FeedBack feedBack) {
		this.feedBack = feedBack;
	}
	
	/**
	 * 站内消息内容取得
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 站内消息内容设定
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
