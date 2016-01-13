package cn.magme.web.action.look;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.common.Page;
import cn.magme.service.look.LookUserService;
import cn.magme.web.action.BaseAction;

/**
 * LOOK用户金币管理
 * @author jasper
 * @date 2013.10.24
 *
 */
@Results({@Result(name="success",location="/WEB-INF/pages/looker/admin/userGold.ftl")})
public class LookUserGoldAction extends BaseAction {
	@Resource
	private LookUserService lookUserService;
	private static final Logger log = Logger
			.getLogger(LookUserGoldAction.class);
	
	private String startDate;
	private String endDate;
	private String nickName;
	private Integer type;
	private Integer addType;
	private Long userId;
	private Integer currentPage = 1;
	
	
	public String execute()
	{
		return SUCCESS;
	}
	
	//查询用户金币历史
	public String searchUserGoldJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(currentPage==null||currentPage<=0)
			currentPage = 1;
		Page p = lookUserService.searchUserGoldHis(startDate, endDate, userId, nickName, addType, type, currentPage);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		List<Map> rl = p.getResults();
		this.jsonResult.put("commondatas", rl);
		this.jsonResult.put("pageNo", p.getTotalPage());
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getAddType() {
		return addType;
	}

	public void setAddType(Integer addType) {
		this.addType = addType;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
