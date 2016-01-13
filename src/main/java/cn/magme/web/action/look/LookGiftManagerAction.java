package cn.magme.web.action.look;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.common.Page;
import cn.magme.constants.CacheConstants;
import cn.magme.pojo.look.LooGift;
import cn.magme.service.look.LookGiftService;
import cn.magme.util.FileOperate;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

import com.danga.MemCached.MemCachedClient;

/**
 * LOOK礼品管理
 * @author jasper
 * @date 2013.10.24
 *
 */
@Results({@Result(name="success",location="/WEB-INF/pages/looker/admin/giftManager.ftl"),
	@Result(name = "fileUploadJson", type = "json", params = { "root",
		"jsonResult", "contentType", "text/html" })})
public class LookGiftManagerAction extends BaseAction {
	@Resource
	private LookGiftService lookGiftService;
	@Resource 
	private MemCachedClient memCachedClient;
	private static final Logger log=Logger.getLogger(LookGiftManagerAction.class);
	
	private String giftCode;
	private String giftName;
	private Integer startGold;
	private Integer endGold;
	private Integer currentPage = 1;
	private Integer goldNum;
	private File giftPic;
	private String giftPicFileName;
	private Integer giftLimit;
	private Integer qty;
	private Integer showQty;
	private Long giftId;
	private Long appId = 903L;//默认APP
	
	
	public String execute()
	{
		return SUCCESS;
	}
	
	//查询礼品
	public String searchGiftJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(currentPage==null||currentPage<=0)
			currentPage = 1;
		Page p = lookGiftService.searchLooGift(giftCode, giftName, startGold, endGold, currentPage);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		List<LooGift> rl = p.getResults();
		this.jsonResult.put("commondatas", rl);
		this.jsonResult.put("pageNo", p.getTotalPage());
		return JSON;
	}
	//保存礼品
	public String saveGiftJson()
	{
		this.jsonResult=JsonResult.getFailure();
			
		if(StringUtil.isBlank(this.giftName))
		{
			log.warn("名称必须填写");
			this.jsonResult.setMessage("名称必须填写");
			return "fileUploadJson";
		}
		if(this.goldNum==null||this.goldNum<=0)
		{
			log.warn("金币数量必须填写");
			this.jsonResult.setMessage("金币数量必须填写");
			return "fileUploadJson";
		}
		if(this.giftLimit==null||this.giftLimit<0)
		{
			log.warn("每天上限必须填写");
			this.jsonResult.setMessage("每天上限必须填写");
			return "fileUploadJson";
		}
		if(this.qty==null||this.qty<=0)
		{
			log.warn("数量必须填写");
			this.jsonResult.setMessage("数量必须填写");
			return "fileUploadJson";
		}
		if((this.giftId==null||this.giftId<=0)&&this.giftPic==null)
		{
			log.warn("需要上传礼品图片");
			this.jsonResult.setMessage("需要上传礼品图片");
			return "fileUploadJson";
		}
		if(giftPic!=null&&giftPicFileName!=null&&!giftPicFileName.toLowerCase().endsWith(".jpg"))
		{
			log.warn("文件类型必须是jpg");
			this.jsonResult.setMessage("文件类型必须是jpg");
			return "fileUploadJson";
		}
		LooGift gift = new LooGift();
		if(giftId!=null&&giftId>0)
			gift.setId(giftId);
		gift.setGiftLimit(giftLimit);
		gift.setQty(qty);
		if(showQty!=null&&showQty>=0)
			gift.setShowQty(showQty);
		gift.setGoldNum(goldNum);
		gift.setGiftName(giftName);
		String staticPath=systemProp.getStaticLocalUrl();
		if(this.giftPic!=null&&this.giftPic!=null)
		{
            
			String relativePath = File.separator +"look"+File.separator+this.appId+File.separator+"gift"+File.separator;
			File f=new File(staticPath+relativePath);
			if(!f.exists()){
				f.mkdirs();
			}
			String fileName=relativePath+"gift_"+System.currentTimeMillis()+".jpg";
			FileOperate.copyFile(giftPic.getAbsolutePath(), staticPath+fileName);

			gift.setPicPath(fileName.replaceAll("\\\\", "/"));
		}
		int r = lookGiftService.saveLooGift(gift);
		if(r<=0)
		{
			this.jsonResult.setMessage("保存失败");
		}
		else
		{
			memCachedClient.delete(CacheConstants.LOOK_GIFT_CACHE);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return "fileUploadJson";
	}
	//获得礼品信息
	public String giftInfoJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(giftId==null||giftId<=0)
		{
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		LooGift gift = this.lookGiftService.getLooGiftInfo(giftId);
		if(gift==null)
		{
			this.jsonResult.setMessage("获得礼品信息失败");
		}
		else
		{
			this.jsonResult.put("giftInfo", gift);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}
	//删除礼品
	public String deleteGiftJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(giftId==null||giftId<=0)
		{
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		int r = this.lookGiftService.deleteLooGift(giftId);
		if(r<=0)
		{
			this.jsonResult.setMessage("删除失败");
		}
		else
		{
			memCachedClient.delete(CacheConstants.LOOK_GIFT_CACHE);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}
	//礼品上架和下架
	public String changeStatusGiftJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(giftId==null||giftId<=0)
		{
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		int r = this.lookGiftService.changeLooGiftStatus(giftId);
		if(r<=0)
		{
			this.jsonResult.setMessage("操作失败");
		}
		else
		{
			memCachedClient.delete(CacheConstants.LOOK_GIFT_CACHE);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}
	//全部礼品列表
	public String allGiftJson()
	{
		this.jsonResult=JsonResult.getFailure();
		List<LooGift> giftList = this.lookGiftService.getAllGift();
		this.jsonResult.put("giftList", giftList);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public String getGiftCode() {
		return giftCode;
	}

	public void setGiftCode(String giftCode) {
		this.giftCode = giftCode;
	}

	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	public Integer getStartGold() {
		return startGold;
	}

	public void setStartGold(Integer startGold) {
		this.startGold = startGold;
	}

	public Integer getEndGold() {
		return endGold;
	}

	public void setEndGold(Integer endGold) {
		this.endGold = endGold;
	}

	public Integer getGoldNum() {
		return goldNum;
	}

	public void setGoldNum(Integer goldNum) {
		this.goldNum = goldNum;
	}

	public File getGiftPic() {
		return giftPic;
	}

	public void setGiftPic(File giftPic) {
		this.giftPic = giftPic;
	}

	public String getGiftPicFileName() {
		return giftPicFileName;
	}

	public void setGiftPicFileName(String giftPicFileName) {
		this.giftPicFileName = giftPicFileName;
	}

	public Integer getGiftLimit() {
		return giftLimit;
	}

	public void setGiftLimit(Integer giftLimit) {
		this.giftLimit = giftLimit;
	}

	public Long getGiftId() {
		return giftId;
	}

	public void setGiftId(Long giftId) {
		this.giftId = giftId;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Integer getShowQty() {
		return showQty;
	}

	public void setShowQty(Integer showQty) {
		this.showQty = showQty;
	}

}
