package cn.magme.web.action.look;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.common.Page;
import cn.magme.pojo.look.LooStartPic;
import cn.magme.service.look.LookEggService;
import cn.magme.web.action.BaseAction;

/**
 * LOOK彩券管理
 * @author jasper
 * @date 2013.10.24
 *
 */
@Results({@Result(name="success",location="/WEB-INF/pages/looker/admin/userEgg.ftl"),
	@Result(name = "fileUploadJson", type = "json", params = { "root",
		"jsonResult", "contentType", "text/html" })})
public class LookUserEggAction extends BaseAction {
	@Resource
	private LookEggService lookEggService;
	private static final Logger log = Logger
			.getLogger(LookUserEggAction.class);
	
	private String nickName;
	private String eggCode;
	private String ticketNum;
	private Long userId;
	private Integer currentPage = 1;
	
	private Long userEggManagerId;
	
	public String execute()
	{
		return SUCCESS;
	}
	
	//查询用户彩蛋
	public String searchUserEggJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(currentPage==null||currentPage<=0)
			currentPage = 1;
		Page p = lookEggService.searchLooUserEgg(userId, nickName, eggCode, ticketNum, currentPage);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		List<Map> rl = p.getResults();
		this.jsonResult.put("commondatas", rl);
		this.jsonResult.put("pageNo", p.getTotalPage());
		return JSON;
	}
	//上架和下架
	public String winningUserEggJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(userEggManagerId==null||userEggManagerId<=0)
		{
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		int r = this.lookEggService.winningLooUserEgg(userEggManagerId);
		if(r<=0)
		{
			this.jsonResult.setMessage("操作失败");
		}
		else
		{
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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEggCode() {
		return eggCode;
	}

	public void setEggCode(String eggCode) {
		this.eggCode = eggCode;
	}

	public String getTicketNum() {
		return ticketNum;
	}

	public void setTicketNum(String ticketNum) {
		this.ticketNum = ticketNum;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserEggManagerId() {
		return userEggManagerId;
	}

	public void setUserEggManagerId(Long userEggManagerId) {
		this.userEggManagerId = userEggManagerId;
	}

}
