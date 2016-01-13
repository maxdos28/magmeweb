/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.admin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.apache.struts2.ServletActionContext;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.ContentInfo;
import cn.magme.pojo.Message;
import cn.magme.pojo.User;
import cn.magme.service.MessageService;
import cn.magme.service.UserService;

/**
 * @author shenhao
 * @date 2011-8-16
 * @version $id$
 */
@SuppressWarnings("serial")
public class SendMassMsgAction extends BaseAction {
	@Resource
	private MessageService messageService;
	
	@Resource
	private UserService userService;
	// 信息内容
	private String message;
	// 页面信息(查询条件:用户编号，用户名，昵称，邮箱)
	private User user;
	// 上传文件
	private File userFile;
	// 上传文件对应类型
	private String userFileContentType;
	
	/**
	 * 后台：发站内系统消息
	 */
	public void msgSend() {
		// 读取导入文件内容
		List<String> list = new ArrayList<String>();
		try {
		    // 检查文件及其类型是否正常
			if (userFile!=null && userFileContentType!=null) {
				if (userFileContentType.equals("text/plain")){
					BufferedReader br = new BufferedReader(
							new InputStreamReader(new FileInputStream(userFile), "gb2312"));
					String tmp;
					while ((tmp = br.readLine()) != null) {
					    // 检查用户是否存在
						if (userService.getUserById(Long.parseLong(tmp.trim())) != null){
						    // 将文件中用户追加到发送者名单
							 list.add(tmp.trim());
						 };
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Long[] lngIds = null;
		// 将被选中用户追求到发送者名单
		if (!ids.equals("")){
			String[] strLst = ids.split(",");
			for (int i = 0;i< strLst.length;i++){
				list.add(strLst[i]);
			}
		}

		// 整理参数
		lngIds = new Long[list.size()];
		for (int i =0;i< list.size();i++){
			try{
				lngIds[i] = Long.parseLong(list.get(i));
			}catch (Exception e) {
				continue;
			}
		}

		//发站内消息通知对方
		Message message=new Message();
		//0表示是系统邮件
		message.setFromUserId(0L);
		message.setFromType(PojoConstant.MESSAGE.FROMTYPE_ADMIN);
		message.setToType(PojoConstant.MESSAGE.TOTYPE_USER);
		message.setType(PojoConstant.MESSAGE.TYPE_SYSTEM);
		ContentInfo contentInfo=new ContentInfo();
		contentInfo.setContent(this.message);
		message.setContentInfo(contentInfo);
		messageService.insertMessage(message,lngIds);
		ServletActionContext.getResponse().setHeader("sessionstatus", "ok"); 
		ServletActionContext.getResponse().setContentType("text/html");
		print("{success:true,msg:"+ lngIds.length +"}");
	}

	/**
	 * 后台：向所有人,发站内系统消息
	 */
	public void msgSendAll() {
		Long[] lngIds = null;
		List<User> lstUsers = userService.getAllUserLst(user);
		if (lstUsers.size() == 0) {
			print("{success:true,msg:0}");
		}

		// 整理参数
		lngIds = new Long[lstUsers.size()];
		for (int i =0;i< lstUsers.size();i++){
			lngIds[i] = lstUsers.get(i).getId();
		}

		//发站内消息通知对方
		Message message=new Message();
		//0表示是系统邮件
		message.setFromUserId(0L);
		message.setFromType(PojoConstant.MESSAGE.FROMTYPE_ADMIN);
		message.setToType(PojoConstant.MESSAGE.TOTYPE_USER);
		message.setType(PojoConstant.MESSAGE.TYPE_SYSTEM);
		ContentInfo contentInfo=new ContentInfo();
		contentInfo.setContent(this.message);
		message.setContentInfo(contentInfo);
		messageService.insertMessage(message,lngIds);
		//ServletActionContext.getResponse().setHeader("sessionstatus", "ok"); 
		ServletActionContext.getResponse().setContentType("text/html");
		print("{success:true,msg:"+ lngIds.length +"}");
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public File getUserFile() {
		return userFile;
	}
	public void setUserFile(File userFile) {
		this.userFile = userFile;
	}
	public String getUserFileContentType() {
		return userFileContentType;
	}
	public void setUserFileContentType(String userFileContentType) {
		this.userFileContentType = userFileContentType;
	}
}
