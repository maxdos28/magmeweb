package cn.magme.web.action.look;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.common.Page;
import cn.magme.constants.CacheConstants;
import cn.magme.pojo.look.LooEggDetail;
import cn.magme.pojo.look.LooEggManager;
import cn.magme.service.look.LookEggService;
import cn.magme.util.DateUtil;
import cn.magme.util.ObjDataCopy;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

import com.danga.MemCached.MemCachedClient;

/**
 * LOOK彩蛋策略管理
 * @author jasper
 * @date 2013.10.24
 *
 */
@Results({@Result(name="success",location="/WEB-INF/pages/looker/admin/eggManager.ftl"),
	@Result(name = "fileUploadJson", type = "json", params = { "root",
		"jsonResult", "contentType", "text/html" })})
public class LookEggManagerAction extends BaseAction {
	@Resource
	private LookEggService lookEggService;
	@Resource 
	private MemCachedClient memCachedClient;
	private static final Logger log=Logger.getLogger(LookEggManagerAction.class);
	
	private String eggCode;
	private String eggName;
	private Integer currentPage = 1;
	
	private Long managerId;
	private String beginTime;
	private String endTime;
	private Integer eggNums;
	private String eggNos;
	private String eggDetail;
	
	
	public String execute()
	{
		return SUCCESS;
	}
	
	//查询彩蛋规则
	public String searchEggManagerJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(currentPage==null||currentPage<=0)
			currentPage = 1;
		Page p = lookEggService.searchLooEggManager(eggCode, eggName, currentPage);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		List<LooEggManager> rl = p.getResults();
		this.jsonResult.put("commondatas", rl);
		this.jsonResult.put("pageNo", p.getTotalPage());
		return JSON;
	}
	//保存彩蛋规则
	public String saveEggManagerJson()
	{
		this.jsonResult=JsonResult.getFailure();
			
		if(StringUtil.isBlank(this.eggName))
		{
			log.warn("名称必须填写");
			this.jsonResult.setMessage("名称必须填写");
			return "fileUploadJson";
		}
		if(this.eggNums==null||this.eggNums<=0)
		{
			log.warn("彩蛋数量必须填写");
			this.jsonResult.setMessage("彩蛋数量必须填写");
			return "fileUploadJson";
		}
		if(StringUtil.isBlank(this.beginTime))
		{
			log.warn("开始时间必须填写");
			this.jsonResult.setMessage("开始时间必须填写");
			return "fileUploadJson";
		}
		if(StringUtil.isBlank(this.endTime))
		{
			log.warn("结束时间必须填写");
			this.jsonResult.setMessage("结束时间必须填写");
			return "fileUploadJson";
		}
		if(StringUtil.isBlank(this.eggNos))
		{
			log.warn("彩蛋号码必须填写");
			this.jsonResult.setMessage("彩蛋号码必须填写");
			return "fileUploadJson";
		}
		if(StringUtil.isBlank(this.eggDetail))
		{
			log.warn("栏目必须填写");
			this.jsonResult.setMessage("栏目必须填写");
			return "fileUploadJson";
		}
		LooEggManager looEggManager = new LooEggManager();
		ObjDataCopy.copy(this, looEggManager);
		looEggManager.setBeginTime(DateUtil.parse(beginTime, "yyyy-MM-dd"));
		looEggManager.setEndTime(DateUtil.parse(endTime, "yyyy-MM-dd"));
		if(managerId!=null&&managerId>0)
			looEggManager.setId(managerId);
		looEggManager.setEggCode(null);
		JSONObject jo = null;
		try {
			jo = JSONObject.fromObject(eggDetail);
		} catch (Exception e) {
			this.jsonResult.setMessage("数据格式错误");
			return JSON;
		}
		List<Map> edList = (List)jo.get("eggDetail");
		if(edList==null||edList.size()==0)
		{
			this.jsonResult.setMessage("栏目未添加");
			return JSON;
		}
		List<LooEggDetail> detailList = new ArrayList();
		for(Map ed:edList)
		{
			LooEggDetail d = new LooEggDetail();
			if(ed.get("itemId")==null)
				continue;
			if(ed.get("type")==null)
				continue;
			d.setItemId(new Long(ed.get("itemId").toString()));
			d.setType(new Byte(ed.get("type").toString()));
			if(ed.get("articleBeginTime")!=null)
				d.setArticleBeginTime(DateUtil.parse(ed.get("articleBeginTime").toString(), "yyyy-MM-dd"));
			detailList.add(d);
		}
		int r = lookEggService.saveLooEggManager(looEggManager, detailList);
		if(r<=0)
		{
			this.jsonResult.setMessage("保存失败");
		}
		else
		{
			memCachedClient.delete(CacheConstants.LOOK_EGG_RULE_CACHE);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return "fileUploadJson";
	}
	//获得彩蛋规则
	public String eggManagerInfoJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(managerId==null||managerId<=0)
		{
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		Map r = this.lookEggService.getLooEggManagerInfo(managerId);
		if(r==null)
		{
			this.jsonResult.setMessage("获得礼品信息失败");
		}
		else
		{
			this.jsonResult.put("eggManagerInfo", r);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}
	//删除彩蛋规则
	public String deleteEggManagerJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(managerId==null||managerId<=0)
		{
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		int r = this.lookEggService.deleteLooEggManager(managerId);
		if(r<=0)
		{
			this.jsonResult.setMessage("删除失败");
		}
		else
		{
			memCachedClient.delete(CacheConstants.LOOK_EGG_RULE_CACHE);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}
	//彩蛋规则上架和下架
	public String changeStatusEggManagerJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(managerId==null||managerId<=0)
		{
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		int r = this.lookEggService.changeLooEggManagerStatus(managerId);
		if(r<=0)
		{
			this.jsonResult.setMessage("操作失败");
		}
		else
		{
			memCachedClient.delete(CacheConstants.LOOK_EGG_RULE_CACHE);
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

	public String getEggCode() {
		return eggCode;
	}

	public void setEggCode(String eggCode) {
		this.eggCode = eggCode;
	}

	public String getEggName() {
		return eggName;
	}

	public void setEggName(String eggName) {
		this.eggName = eggName;
	}

	public Long getManagerId() {
		return managerId;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getEggNums() {
		return eggNums;
	}

	public void setEggNums(Integer eggNums) {
		this.eggNums = eggNums;
	}

	public String getEggNos() {
		return eggNos;
	}

	public void setEggNos(String eggNos) {
		this.eggNos = eggNos;
	}

	public String getEggDetail() {
		return eggDetail;
	}

	public void setEggDetail(String eggDetail) {
		this.eggDetail = eggDetail;
	}

}
