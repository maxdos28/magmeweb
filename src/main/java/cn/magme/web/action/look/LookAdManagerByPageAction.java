package cn.magme.web.action.look;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.common.Page;
import cn.magme.constants.PojoConstant;
import cn.magme.pojo.IosApp;
import cn.magme.pojo.Issue;
import cn.magme.pojo.look.LooStartPic;
import cn.magme.pojo.mobile.MobileAdDetail;
import cn.magme.pojo.mobile.MobileAdPut;
import cn.magme.pojo.mobile.MobileAdTotal;
import cn.magme.pojo.netease.NeteasePublicationTask;
import cn.magme.service.IosAppService;
import cn.magme.service.look.LookAdService;
import cn.magme.service.netease.NeteaseService;
import cn.magme.util.DateUtil;
import cn.magme.util.FileOperate;
import cn.magme.util.ObjDataCopy;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

/**
 * LOOK插入广告管理
 * @author jasper
 * @date 2013.10.24
 *
 */
@Results({@Result(name="success",location="/WEB-INF/pages/looker/admin/adManagerByPage.ftl"),
	@Result(name = "fileUploadJson", type = "json", params = { "root",
		"jsonResult", "contentType", "text/html" })})
public class LookAdManagerByPageAction extends BaseAction {
	@Resource
	private LookAdService lookAdService;
	@Resource
	private IosAppService iosAppService;
	@Resource
	private NeteaseService neteaseService;
	private static final Logger log = Logger
			.getLogger(LookCategoryAction.class);
	
	private String startDate;
	private String endDate;
	private String title;
	private Integer status;
	private Long adTotalId;
	private Integer currentPage = 1;
	
	private Integer mobileAdDetailId;
	private String dname;
    private String url;
    private String adCompanyUrl;
    private Byte adType;
    private Byte contentType;
    private Byte showType;
    private Integer position;
    private String onlineTime;
    private String offlineTime;
    private Integer totalAddId;
    private String adPutStr;//广告对应的appID和杂志ID
    private Long appId = 903L;
	private File adZip;
	private String adZipFileName;
	private Long categoryId;
	
	
	public String execute()
	{
		return SUCCESS;
	}
	
	//查询插页广告
	public String searchPageAdJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(currentPage==null||currentPage<=0)
			currentPage = 1;
		Page p = this.lookAdService.searchPublicationAd(startDate, endDate, status, adTotalId, title, currentPage);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		List<Map> rl = p.getResults();
		//加入是否有网易云阅读的检查
		if(rl!=null&&rl.size()>0)
		{
			List<Integer> adDetailIdList = new ArrayList();
			for(Map m:rl)
			{
				adDetailIdList.add((Integer)m.get("mobileAdDetailId"));
			}
			Map nm = this.lookAdService.checkNeteaseAd(adDetailIdList, 904L);
			if(nm!=null)
			{
				for(Map m:rl)
				{
					m.put("appId", nm.get(m.get("mobileAdDetailId")));
				}
			}
		}	
		
		this.jsonResult.put("commondatas", rl);
		this.jsonResult.put("pageNo", p.getTotalPage());
		return JSON;
	}

	//插页广告信息
	public String pageAdInfoJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(mobileAdDetailId==null||mobileAdDetailId<=0)
		{
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		Map pageAdInfo = this.lookAdService.getPublicationAdInfo(mobileAdDetailId);
		if(pageAdInfo==null)
		{
			this.jsonResult.setMessage("获取文章信息失败");
		}
		else
		{
			this.jsonResult.put("pageAdInfo", pageAdInfo);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}
	//保存插页广告
	public String savePageAdJson()
	{
		this.jsonResult = JsonResult.getFailure();

		if (StringUtil.isBlank(this.dname)) {
			log.warn("名称必须填写");
			this.jsonResult.setMessage("名称必须填写");
			return "fileUploadJson";
		}
		if (this.contentType==null) {
			log.warn("内容类型");
			this.jsonResult.setMessage("内容类型");
			return "fileUploadJson";
		}
//		if (StringUtil.isBlank(this.adCompanyUrl)) {
//			log.warn("公司URL必须填写");
//			this.jsonResult.setMessage("公司URL必须填写");
//			return "fileUploadJson";
//		}
		if(this.totalAddId==null||totalAddId<=0)
		{
			log.warn("广告分类必须填写");
			this.jsonResult.setMessage("广告分类必须填写");
			return "fileUploadJson";
		}
		if((mobileAdDetailId==null||mobileAdDetailId<=0)&&this.adZip==null)
		{
			log.warn("必须上传广告文件");
			this.jsonResult.setMessage("必须上传广告文件");
			return "fileUploadJson";
		}
		if (StringUtil.isBlank(this.onlineTime)) {
			log.warn("开始时间必须填写");
			this.jsonResult.setMessage("开始时间必须填写");
			return "fileUploadJson";
		}
		if (StringUtil.isBlank(this.offlineTime)) {
			log.warn("结束时间必须填写");
			this.jsonResult.setMessage("结束时间必须填写");
			return "fileUploadJson";
		}
		if (StringUtil.isBlank(this.adPutStr)&&contentType.intValue()==1) {
			log.warn("栏目必须填写");
			this.jsonResult.setMessage("栏目必须填写");
			return "fileUploadJson";
		}
		if (StringUtil.isBlank(this.adPutStr)&&contentType.intValue()==2) {
			log.warn("杂志必须填写");
			this.jsonResult.setMessage("杂志必须填写");
			return "fileUploadJson";
		}
		if (((categoryId==null||categoryId<=0)||(position==null||position<=0))&&contentType.intValue()==3) {
			log.warn("分类和页码必须填写");
			this.jsonResult.setMessage("分类和页码必须填写");
			return "fileUploadJson";
		}

		if (adZip != null && adZipFileName != null
				&& !(adZipFileName.toLowerCase().endsWith(".mpres")||adZipFileName.toLowerCase().endsWith(".zip"))) {
			log.warn("文件类型必须是mpres");
			this.jsonResult.setMessage("文件类型必须是mpres或zip");
			return "fileUploadJson";
		}
		MobileAdDetail mobileAdDetail = new MobileAdDetail();
		ObjDataCopy.copy(this, mobileAdDetail);
		mobileAdDetail.setOnlineTime(DateUtil.parse(onlineTime, "yyyy-MM-dd"));
		mobileAdDetail.setOfflineTime(DateUtil.parse(offlineTime, "yyyy-MM-dd"));
		if(this.mobileAdDetailId!=null&&this.mobileAdDetailId>0)
			mobileAdDetail.setId(mobileAdDetailId);
		else
		{
			//以下2个值默认
			mobileAdDetail.setAdType(new Byte("1"));
			mobileAdDetail.setShowType(new Byte("1"));
			mobileAdDetail.setUrl("");
			mobileAdDetailId = lookAdService.savePublicationAd(mobileAdDetail, null);
		}
		List<MobileAdPut> adPutList = new ArrayList();
		//分类
		if(contentType.intValue()==3)
		{
			List<Map> l = lookAdService.getLookItemAndPub(appId, categoryId);
			if(l!=null&&l.size()>0)
			{
				for(Map m:l)
				{
					if(m.get("appId")==null||m.get("type")==null||m.get("itemId")==null)
						continue;
					Integer type = (Integer)m.get("type");
					MobileAdPut ap = new MobileAdPut();
					//文章
					if(type==1)
					{
						ap.setAppid(new Integer(m.get("appId").toString()));
						ap.setPublicationId(new Integer(m.get("itemId").toString()));
						ap.setPosition(position);
					}
					//杂志
					else if(type==2)
					{
						if(m.get("pubId")==null)
							continue;
						ap.setAppid(new Integer(m.get("appId").toString()));
						ap.setPublicationId(new Integer(m.get("pubId").toString()));
						ap.setPosition(position);
					}
					adPutList.add(ap);
				}
			}
		}
		//杂志文章
		else
		{
			String[] puts = this.adPutStr.split(";");
			for(String ps:puts)
			{
				String[] aps = ps.split(",");
				MobileAdPut ap = new MobileAdPut();
				//文章
				if(contentType.intValue()==1)
				{
					if(aps.length!=3)
						continue;
					ap.setAppid(new Integer(aps[0]));
					ap.setPublicationId(new Integer(aps[1]));
					ap.setPosition(new Integer(aps[2]));
				}
				//杂志
				else if(contentType.intValue()==2)
				{
					if(aps.length!=3)
						continue;
					ap.setAppid(new Integer(aps[0]));
					ap.setPublicationId(new Integer(aps[1]));
					ap.setPosition(new Integer(aps[2]));
				}
				adPutList.add(ap);
			}
		}
		
		String staticPath = systemProp.getMagicEditorPath();
		String relativePath = File.separator + "ad" + File.separator + mobileAdDetailId
				+ File.separator;
		File dir = new File(staticPath + relativePath);
		if (!dir.exists())
			dir.mkdirs();
		// 上传文件
		if (this.adZip != null) {
			// 解压文件
			int p = unzip(staticPath + relativePath, dir);
			if (p == 0)
				return "fileUploadJson";
			mobileAdDetail.setUrl(this.systemProp.getStaticServerUrl()+"/appprofile"+relativePath.replaceAll("\\\\", "/"));
		}
		int r = lookAdService.savePublicationAd(mobileAdDetail, adPutList);
		if (r <= 0) {
			this.jsonResult.setMessage("保存失败");
		} else {
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return "fileUploadJson";
	}
	// 保存ZIP并解压
		private int unzip(String path, File dir) {
			int pageSize = 0;// 总页数
			// zip文件保存
			File zipFile = new File(path + mobileAdDetailId + ".mpres");
			FileOperate.copyFile(adZip.getAbsolutePath(),
					zipFile.getAbsolutePath());
			// 解压ZIP
			try {
				// 解压根zip包
				FileOperate.unzipFile(adZip.getAbsolutePath(), path, false);
				// 解压解压后的资源文件
				File[] fList = dir.listFiles();
				for (File ff : fList) {
					if(ff.isDirectory()||!ff.getName().toLowerCase().endsWith(".zip"))
						continue;
					if (ff.getName().toLowerCase().indexOf("assets") > 0) {
						FileOperate.unzipFile(ff.getAbsolutePath(), path, true);
						pageSize++;
					}

					// 把手机的html页面解压出来,作为分享的路径
					// 由于pubassets路径问题,解压出来的路径要放到magic.editor.path下,和互动杂志使用相同的
					if (ff.getName().toLowerCase().indexOf("phone") > 0) {
						FileOperate.unzipFile(ff.getAbsolutePath(), path, true);
					}
					if (ff.getName().toLowerCase().indexOf("pad") > 0) {
						FileOperate.unzipFile(ff.getAbsolutePath(), path, true);
					}
				}
				// 重命名解压后的资源文件
				File[] assetsFolderList = dir.listFiles();
				int assetsIndex = 0;
				for (File ff : assetsFolderList) {
					assetsIndex = ff.getName().indexOf("assets");
					if (ff.isDirectory() && assetsIndex > 0) {
						FileOperate.moveFolder(ff.getAbsolutePath(), path
								+ ff.getName().substring(0, assetsIndex));
					}
				}
				// zipFile.delete();
			} catch (IOException e) {
				log.error("", e);
				this.jsonResult.setMessage("unzip file error");
				return 0;
			}
			return pageSize;
		}
	//删除插页广告
	public String deletePageAdJson()
	{
		this.jsonResult = JsonResult.getFailure();
		if (mobileAdDetailId == null || mobileAdDetailId <= 0) {
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		int r = this.lookAdService.deletePublicationAd(mobileAdDetailId);
		if (r <= 0) {
			this.jsonResult.setMessage("删除失败");
		} else {
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}
	//插页广告上架和下架
	public String changeStatusPageAdJson()
	{
		this.jsonResult = JsonResult.getFailure();
		if (mobileAdDetailId == null || mobileAdDetailId <= 0) {
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		int r = this.lookAdService.changePublicationAdStatus(mobileAdDetailId);
		if (r <= 0) {
			this.jsonResult.setMessage("操作失败");
		} else {
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}

	//得到APP列表
	public String getAppListJson()
	{
		this.jsonResult=JsonResult.getFailure();
		List<IosApp> l = iosAppService.queryIosAppList(new IosApp(),0,0);
	
		this.jsonResult.put("appList", l);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		
		return JSON;
	}

	//根据appId得到杂志
	public String getPublicationListJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(appId==null||appId<=0)
		{
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		List<Map> pl = this.lookAdService.getPublicationListByAppId(appId);
		if(pl==null)
		{
			this.jsonResult.setMessage("获取杂志失败");
		}
		else
		{
			this.jsonResult.put("publicationList", pl);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}
	

	//得到广告分类
	public String getAdTotalListJson()
	{
		this.jsonResult=JsonResult.getFailure();
		List<MobileAdTotal> al = this.lookAdService.getAdTotalList();
		if(al==null)
		{
			this.jsonResult.setMessage("获取广告分类失败");
		}
		else
		{
			this.jsonResult.put("adTotalList", al);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}
	
	/**
	 * 向网易更新广告
	 * @return
	 */
	public String toNeteaseAdJson()
	{
		this.jsonResult = JsonResult.getFailure();
		if(mobileAdDetailId==null||mobileAdDetailId<=0)
		{
			this.jsonResult.setMessage("广告ID为空");
			return JSON;
		}
		//插入到任务列表
		NeteasePublicationTask t = new NeteasePublicationTask();
		
		t.setPageCount(1);
		t.setAdDetailId(new Long(mobileAdDetailId));
		t.setAdDetailName(dname);
		t.setUpdateType(new Byte("3"));
		t.setStatus(new Byte(""+PojoConstant.NETEASE.TASK_STATUS_2));
		Date date = new Date();
		t.setCreateTime(date);
		t.setUpdateTime(date);
		Long id = this.neteaseService.createNeteasePublicationTask(t);
		if(id==null||id<=0)
		{
			this.jsonResult.setMessage("操作失败");
			return JSON;
		}
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getAdTotalId() {
		return adTotalId;
	}

	public void setAdTotalId(Long adTotalId) {
		this.adTotalId = adTotalId;
	}

	public Integer getMobileAdDetailId() {
		return mobileAdDetailId;
	}

	public void setMobileAdDetailId(Integer mobileAdDetailId) {
		this.mobileAdDetailId = mobileAdDetailId;
	}

	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAdCompanyUrl() {
		return adCompanyUrl;
	}

	public void setAdCompanyUrl(String adCompanyUrl) {
		this.adCompanyUrl = adCompanyUrl;
	}

	public Byte getAdType() {
		return adType;
	}

	public void setAdType(Byte adType) {
		this.adType = adType;
	}

	public Byte getShowType() {
		return showType;
	}

	public void setShowType(Byte showType) {
		this.showType = showType;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(String onlineTime) {
		this.onlineTime = onlineTime;
	}

	public String getOfflineTime() {
		return offlineTime;
	}

	public void setOfflineTime(String offlineTime) {
		this.offlineTime = offlineTime;
	}

	public Integer getTotalAddId() {
		return totalAddId;
	}

	public void setTotalAddId(Integer totalAddId) {
		this.totalAddId = totalAddId;
	}

	public String getAdPutStr() {
		return adPutStr;
	}

	public void setAdPutStr(String adPutStr) {
		this.adPutStr = adPutStr;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public File getAdZip() {
		return adZip;
	}

	public void setAdZip(File adZip) {
		this.adZip = adZip;
	}

	public String getAdZipFileName() {
		return adZipFileName;
	}

	public void setAdZipFileName(String adZipFileName) {
		this.adZipFileName = adZipFileName;
	}

	public Byte getContentType() {
		return contentType;
	}

	public void setContentType(Byte contentType) {
		this.contentType = contentType;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

}
