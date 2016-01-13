package cn.magme.web.action.look;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.common.Page;
import cn.magme.constants.PojoConstant;
import cn.magme.service.look.LookUserService;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

/**
 * LOOK用户礼品管理
 * @author jasper
 * @date 2013.10.24
 *
 */
@Results({@Result(name="success",location="/WEB-INF/pages/looker/admin/userGift.ftl")})
public class LookUserGiftAction extends BaseAction {
	@Resource
	private LookUserService lookUserService;
	private static final Logger log = Logger
			.getLogger(LookUserGiftAction.class);
	
	private String startDate;
	private String endDate;
	private String nickName;
	private Long giftId;
	private Integer giftStatus;
	private Long userId;
	private Integer currentPage = 1;
	private Long appId = 903L;
	
	private Long userGiftId;

	private String message;
	
	public String execute()
	{
		return SUCCESS;
	}
	
	//查询用户礼品
	public String searchUserGiftJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(currentPage==null||currentPage<=0)
			currentPage = 1;
		Page p = lookUserService.searchUserGift(startDate, endDate, userId, nickName, giftId, giftStatus, currentPage);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		List<Map> rl = p.getResults();
		this.jsonResult.put("commondatas", rl);
		this.jsonResult.put("pageNo", p.getTotalPage());
		return JSON;
	}
	//发送礼品消息
	public String sendGiftMessageJson()
	{
		this.jsonResult = JsonResult.getFailure();
		if (userGiftId == null || userGiftId <= 0) {
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		int r = this.lookUserService.changeUserGiftStatus(appId,userGiftId, null, PojoConstant.Look.GIFT_STATUS_2);
		if (r <= 0) {
			this.jsonResult.setMessage("发送失败");
		} else {
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}
	//拒绝发送礼品
	public String rejectMessageJson()
	{
		this.jsonResult = JsonResult.getFailure();
		if (userGiftId == null || userGiftId <= 0) {
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		if (StringUtil.isBlank(message)) {
			log.warn("消息为空");
			this.jsonResult.setMessage("消息为空");
			return JSON;
		}
		int r = this.lookUserService.changeUserGiftStatus(appId,userGiftId, message, PojoConstant.Look.GIFT_STATUS_1);
		if (r <= 0) {
			this.jsonResult.setMessage("发送失败");
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Long getGiftId() {
		return giftId;
	}

	public void setGiftId(Long giftId) {
		this.giftId = giftId;
	}

	public Integer getGiftStatus() {
		return giftStatus;
	}

	public void setGiftStatus(Integer giftStatus) {
		this.giftStatus = giftStatus;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getUserGiftId() {
		return userGiftId;
	}

	public void setUserGiftId(Long userGiftId) {
		this.userGiftId = userGiftId;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

}
