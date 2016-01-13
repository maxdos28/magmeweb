/**
 * Copyright &reg; 2010 Shanghai Magme Co. Ltd.
 * All right reserved.
 */
package cn.magme.web.action.admin;

import javax.annotation.Resource;

import cn.magme.common.SenderMail;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.User;
import cn.magme.service.MailTemplateService;
import cn.magme.service.UserService;
import cn.magme.util.ToJson;

/**
 * 用户管理
 * @author guozheng
 * @date 2011-5-26
 * @version $id$
 */
@SuppressWarnings("serial")
public class UserAction extends BaseAction {
	@Resource
	private UserService userService;
	@Resource
	private SenderMail senderMail;
	@Resource
	private MailTemplateService mailTemplateService;
	// 页面信息(查询条件:用户名， 昵称，邮箱，状态)
	private User user;
	
	/**
	 * 分页查询
	 */
	public void page() {
		page = this.userService.searchByPage(page, user);
		//List<User> data = page.getData();
		String info = ToJson.object2json(page);
		print(info);
	}

	/**
	 * 添加或更新
	 */
	public void commit() {
		Object[] arr = super.toJavaArr(info, User.class);
		User[] infos = castToUser(arr);
		this.userService.commit(infos);
	}
	
	/**
	 * 重置密码
	 */
	public void reset() {
		Object[] arr = super.toJavaArr(info, User.class);
		User[] infos = castToUser(arr);
		for (User user : infos) {
			String newPassword = this.userService.adminResetPassword(user);
			String text = mailTemplateService.getTemplateStr(PojoConstant.EMAILTEMPLATE.CONTENT.FILE_NEW_PASSWORD.getFileName());
			text = text.replace("${newpassword}", newPassword);
			senderMail.sendMail(user.getEmail(), text, PojoConstant.EMAILTEMPLATE.CONTENT.FILE_NEW_PASSWORD.getSubject(), 0);
		}
	}

//    /**
//     * 删除，暂不需实现
//     */
//    public void delete() {
//        //String[] strArr = ids.split(",");
//    }
	
	/**
	 * 数组类型转换 Object[] => User[]
	 * @param arr
	 * @return
	 */
	private User[] castToUser(Object[] arr) {
		User[] infos = new User[arr.length];
		for (int i = 0; i < infos.length; i++) {
			infos[i] = (User) arr[i];
		}
		return infos;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
