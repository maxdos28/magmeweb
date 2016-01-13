package cn.magme.web.action.look;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.common.Page;
import cn.magme.pojo.look.LooManyLook;
import cn.magme.pojo.look.LooUser;
import cn.magme.service.look.LookUserService;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

/**
 * LOOK用户信息管理
 * 
 * @author jasper
 * @date 2013.10.24
 * 
 */
@Results({ @Result(name = "success", location = "/WEB-INF/pages/looker/admin/userInfo.ftl") })
public class LookUserInfoAction extends BaseAction {
	@Resource
	private LookUserService lookUserService;
	private static final Logger log = Logger
			.getLogger(LookUserInfoAction.class);

	private String uid;
	private String nickName;
	private Long userId;
	private Integer type;
	private Integer currentPage = 1;

	private String message;

	private String name;
	private String mobile;
	private String email;
	private String job;

	public String execute() {
		return SUCCESS;
	}

	// 查询用户
	public String searchUserJson() {
		this.jsonResult = JsonResult.getFailure();
		if (currentPage == null || currentPage <= 0)
			currentPage = 1;
		Page p = lookUserService.searchLooUser(uid, userId, nickName, type,
				currentPage);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		List<Map> rl = p.getResults();
		this.jsonResult.put("commondatas", rl);
		this.jsonResult.put("pageNo", p.getTotalPage());
		return JSON;
	}

	// 用户信息
	public String userInfoJson() {
		this.jsonResult = JsonResult.getFailure();
		if (userId == null || userId <= 0) {
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		LooUser user = this.lookUserService.getLooUserInfo(userId);
		if (user == null) {
			this.jsonResult.setMessage("获取文章信息失败");
		} else {
			this.jsonResult.put("userInfo", user);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}

	// 保存用户信息
	public String saveUserJson() {
		this.jsonResult = JsonResult.getFailure();
		if (userId == null || userId <= 0) {
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		if (StringUtil.isBlank(name) && StringUtil.isBlank(mobile)
				&& StringUtil.isBlank(email) && StringUtil.isBlank(job)) {
			log.warn("请输入信息");
			this.jsonResult.setMessage("请输入信息");
			return JSON;
		}
		LooUser looUser = new LooUser();
		looUser.setId(userId);
		looUser.setName(name);
		looUser.setMobile(mobile);
		looUser.setMail(email);
		looUser.setJob(job);
		int r = this.lookUserService.saveLooUser(looUser);
		if (r <= 0) {
			this.jsonResult.setMessage("操作失败");
		} else {
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}

	// 发送消息
	public String sendMessageJson() {
		this.jsonResult = JsonResult.getFailure();
		if (userId == null || userId <= 0) {
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		if (StringUtil.isBlank(message)) {
			log.warn("消息为空");
			this.jsonResult.setMessage("消息为空");
			return JSON;
		}
		int r = this.lookUserService.sendMessage(userId, message);
		if (r <= 0) {
			this.jsonResult.setMessage("发送失败");
		} else {
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}

	// 删除用户
	public String deleteUserJson() {
		this.jsonResult = JsonResult.getFailure();
		if (userId == null || userId <= 0) {
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		int r = this.lookUserService.deleteLooUser(userId);
		if (r <= 0) {
			this.jsonResult.setMessage("删除失败");
		} else {
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}

	// 锁定用户
	public String changeStatusUserJson() {
		this.jsonResult = JsonResult.getFailure();
		if (userId == null || userId <= 0) {
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		int r = this.lookUserService.changeLooUserStatus(userId);
		if (r <= 0) {
			this.jsonResult.setMessage("操作失败");
		} else {
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

}
