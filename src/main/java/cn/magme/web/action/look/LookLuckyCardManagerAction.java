package cn.magme.web.action.look;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.common.Page;
import cn.magme.pojo.look.LooGift;
import cn.magme.service.look.LookLuckyCardService;
import cn.magme.service.look.LookLuckyService;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

import com.danga.MemCached.MemCachedClient;

/**
 * LOOK刮刮卡管理
 * @author jasper
 * @date 2014.4.29
 *
 */
@Results({@Result(name="success",location="/WEB-INF/pages/looker/admin/luckyCardManager.ftl")})
public class LookLuckyCardManagerAction extends BaseAction {
	@Resource
	private LookLuckyCardService lookLuckyCardService;
	@Resource
	private LookLuckyService lookLuckyService;
	@Resource 
	private MemCachedClient memCachedClient;
	private static final Logger log=Logger.getLogger(LookLuckyCardManagerAction.class);
	
	private Integer type;
	private Integer status;
	private Integer currentPage = 1;
	private Integer sortOrder;
	private Long id;
	private Long appId = 903L;//默认APP
	private List<Map> luckCardUseList;
	private List<LooGift> giftList;
	private String cardstr;
	private Integer total;
	
	
	public String execute()
	{
		this.luckCardUseList = this.lookLuckyCardService.getLooLuckCardUse();
		this.giftList = lookLuckyService.getAllLucky();
		this.sortOrder = this.lookLuckyCardService.getLooLuckCardMaxSort();
		return SUCCESS;
	}
	
	//查询刮刮卡
	public String searchLuckyCardJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(currentPage==null||currentPage<=0)
			currentPage = 1;
		Page p = lookLuckyCardService.searchLooLuckCard(type, status, currentPage);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		List<LooGift> rl = p.getResults();
		this.jsonResult.put("commondatas", rl);
		this.jsonResult.put("pageNo", p.getTotalPage());
		return JSON;
	}
	//删除刮刮卡
	public String deleteLuckyJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(id==null||id<=0)
		{
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		int r = this.lookLuckyCardService.deleteLuckCard(id);
		if(r<=0)
		{
			this.jsonResult.setMessage("删除失败");
		}
		else
		{
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}
	//刮刮卡顺序调整
	public String changeSortJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(id==null||id<=0)
		{
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		if(sortOrder==null||sortOrder<=0)
		{
			log.warn("顺序为空");
			this.jsonResult.setMessage("顺序为空");
			return JSON;
		}
		int r = this.lookLuckyCardService.changeLuckCardSort(id, sortOrder);
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
	//生成刮刮卡
	public String saveLuckyCardJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(total==null||total<=0)
		{
			log.warn("数量为空");
			this.jsonResult.setMessage("数量为空");
			return JSON;
		}
		if(StringUtil.isBlank(cardstr))
		{
			log.warn("刮刮卡类型为空");
			this.jsonResult.setMessage("刮刮卡类型为空");
			return JSON;
		}
		Map param = new HashMap();
		param.put("total", total);
		param.put("cardstr", cardstr);
		this.jsonResult = this.lookLuckyCardService.genericLuckCard(param);
		return JSON;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public List<Map> getLuckCardUseList() {
		return luckCardUseList;
	}

	public void setLuckCardUseList(List<Map> luckCardUseList) {
		this.luckCardUseList = luckCardUseList;
	}

	public List<LooGift> getGiftList() {
		return giftList;
	}

	public void setGiftList(List<LooGift> giftList) {
		this.giftList = giftList;
	}

	public String getCardstr() {
		return cardstr;
	}

	public void setCardstr(String cardstr) {
		this.cardstr = cardstr;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
}
