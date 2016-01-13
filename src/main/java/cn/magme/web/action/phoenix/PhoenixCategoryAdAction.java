package cn.magme.web.action.phoenix;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.common.JsonResult;
import cn.magme.common.Page;
import cn.magme.constants.WebConstant;
import cn.magme.pojo.look.LooCategory;
import cn.magme.pojo.phoenix.PhoenixCategoryAd;
import cn.magme.pojo.phoenix.PhoenixUser;
import cn.magme.service.phoenix.PhoenixCategoryAdService;
import cn.magme.util.FileOperate;
import cn.magme.util.ObjDataCopy;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

import com.opensymphony.xwork2.ActionContext;
/**
 * 
 * @author jasper
 * @since 2013-12-23
 */
@Results({@Result(name="success",location="/WEB-INF/pages/phoenix/categoryAdManager.ftl")
,@Result(name="fileUploadJson",type="json",params={"root","jsonResult","contentType","text/html;"})})
public class PhoenixCategoryAdAction extends BaseAction {
	
	private static final Logger log=Logger.getLogger(PhoenixCategoryAdAction.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5273655890999777094L;
	@Resource
	private PhoenixCategoryAdService phoenixCategoryAdService;
	
	private Long appId = 901L;
	private Long categoryId;
	private Long categoryAdId;
	private String title;
	private Integer currentPage = 1;
	private File adPic;
	private String adPicFileName;

	private Integer sortOrder;
	private String link;
	private String tempFile;//临时图片的地址
	
	public String execute(){
		
		return SUCCESS;
	}
	

	//栏目广告查询,返回文章和广告列表
	public String categoryAdJson()
	{
		this.jsonResult = JsonResult.getFailure();
		if(appId==null||appId<=0)
		{
			this.jsonResult.setMessage("appId为空");
			return JSON;
		}
		if(categoryId==null||categoryId<=0)
		{
			this.jsonResult.setMessage("频道ID为空");
			return JSON;
		}
		List l = this.phoenixCategoryAdService.getArticleAndAdOfCategory(appId, categoryId);
		this.jsonResult.put("categoryAdList", l);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		return JSON;
	}

	//查询栏目广告
	public String searchPhoenixCategoryAdJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(currentPage==null||currentPage<=0)
			currentPage = 1;
		getSessionAppId();
		Page p = phoenixCategoryAdService.searchPhoenixCategoryAd(appId, title,categoryId, currentPage);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		List<LooCategory> rl = p.getResults();
		this.jsonResult.put("commondatas", rl);
		this.jsonResult.put("pageNo", p.getTotalPage());
		return JSON;
	}
	//保存栏目广告
	public String savePhoenixCategoryAdJson()
	{
		if(StringUtil.isNotBlank(this.tempFile))
		{
			adPicFileName = this.tempFile.substring(this.tempFile.lastIndexOf("/")+1);
			this.adPic = new File(this.tempFile);
		}
		checkPic();
		if(this.jsonResult.getCode()!=200)
		{
			return "fileUploadJson";
		}
		getSessionAppId();
		PhoenixCategoryAd ad = new PhoenixCategoryAd();
		ObjDataCopy.copy(this, ad);
		ad.setAppId(appId);
		if (categoryAdId != null && categoryAdId > 0)
			ad.setId(categoryAdId);
		else {
			int r = this.phoenixCategoryAdService.savePhoenixCategoryAd(ad);
			categoryAdId = new Long(r);
			ad.setId(categoryAdId);
		}
		//文章使用和互动杂志相同路径
		String staticPath = systemProp.getStaticLocalUrl() ;
		String relativePath =  File.separator
				+ "phoenix" + File.separator + "catead" + File.separator
				+ appId+ File.separator;
		File dir = new File(staticPath + relativePath);
		if (!dir.exists())
			dir.mkdirs();
		String fileName = relativePath + "ad_" + categoryAdId + "-"
				+ System.nanoTime();
		// 上传图片
		if (this.adPic != null) {
			// 文章图片ID，文章ID+－+时间
			FileOperate.copyFile(adPic.getAbsolutePath(), staticPath+fileName+".jpg");
			ad.setPicPath(fileName.replaceAll("\\\\", "/") + ".jpg");
		}
		int r = phoenixCategoryAdService.savePhoenixCategoryAd(ad);
		if(r<=0)
		{
			this.jsonResult.setMessage("保存失败");
		}
		else
		{
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		if(adPic!=null)
			this.adPic.delete();
		return "fileUploadJson";
	}
	
	/**
	 * 检查图片
	 * @return
	 */
	public String checkPic()
	{
		this.jsonResult=JsonResult.getFailure();
		
		if(StringUtil.isBlank(this.title))
		{
			log.warn("名称必须填写");
			this.jsonResult.setMessage("名称必须填写");
			return "fileUploadJson";
		}
		if(StringUtil.isBlank(this.link))
		{
			log.warn("链接必须填写");
			this.jsonResult.setMessage("链接必须填写");
			return "fileUploadJson";
		}
		if(this.sortOrder==null)
		{
			log.warn("排序必须填写");
			this.jsonResult.setMessage("排序必须填写");
			return "fileUploadJson";
		}
		if (this.categoryId == null || this.categoryId <= 0) {
			log.warn("分类必须填写");
			this.jsonResult.setMessage("分类必须填写");
			return "fileUploadJson";
		}
		if (this.categoryAdId == null || this.categoryAdId <= 0) {
			if (this.adPic == null) {
				log.warn("必须上传图片");
				this.jsonResult.setMessage("必须上传图片");
				return "fileUploadJson";
			}
		}
		if (adPic != null && adPicFileName != null
				&& !adPicFileName.toLowerCase().endsWith(".jpg")) {
			log.warn("文件类型必须是jpg");
			this.jsonResult.setMessage("文件类型必须是jpg");
			return "fileUploadJson";
		}

		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		this.jsonResult.put("checkPic", 1);
		//768 x 645
		if (adPic != null) {
			// 图片尺寸检查
			// FileInputStream fis = new FileInputStream(smallPic);
			try {
				BufferedImage buff = ImageIO.read(adPic);
				String msg = "您上传的图片";
				if (buff.getHeight() != 384 || buff.getWidth() != 768) {
					this.jsonResult.put("checkPic", 0);
					log.warn("图片尺寸不符合");
					msg+="尺寸为"+buff.getWidth()+"*"+buff.getHeight()+",不符合标准尺寸(768*384),";
					//return "fileUploadJson";
				}
				if(adPic.length()>(50*1024))
				{
					log.warn("图片大小不符合");
					msg+="大小超过50K,";
					this.jsonResult.put("checkPic", 0);
					//return "fileUploadJson";
				}
				msg+="是否确认?";
				this.jsonResult.setMessage(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String staticPath = systemProp.getStaticLocalUrl()+"/temp/";
		File dir = new File(staticPath);
		if(!dir.exists())
			dir.mkdirs();
		String tempFileName = staticPath+"temp_"+System.nanoTime()+".jpg";
		FileOperate.copyFile(adPic.getAbsolutePath(), tempFileName);
		this.jsonResult.put("tempFile", tempFileName);
		return "fileUploadJson";
	}
	//获得栏目广告信息
	public String phoenixCategoryAdInfoJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(categoryAdId==null||categoryAdId<=0)
		{
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		PhoenixCategoryAd ad = this.phoenixCategoryAdService.getPhoenixCategoryAdInfo(categoryAdId);
		if(ad==null)
		{
			this.jsonResult.setMessage("获得广告信息失败");
		}
		else
		{
			this.jsonResult.put("phoenixCategoryAdInfo", ad);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}
	//删除栏目广告
	public String deletePhoenixCategoryAdJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(categoryAdId==null||categoryAdId<=0)
		{
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		int r = this.phoenixCategoryAdService.deletePhoenixCategoryAd(categoryAdId);
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

	//得到session的appId
	private void getSessionAppId() {
		PhoenixUser phoenixUser = (PhoenixUser)ActionContext.getContext().getSession().get(WebConstant.SESSION.PHOENIX_USER);
		appId = phoenixUser.getAppId();
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getCategoryAdId() {
		return categoryAdId;
	}

	public void setCategoryAdId(Long categoryAdId) {
		this.categoryAdId = categoryAdId;
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

	public File getAdPic() {
		return adPic;
	}

	public void setAdPic(File adPic) {
		this.adPic = adPic;
	}

	public String getAdPicFileName() {
		return adPicFileName;
	}

	public void setAdPicFileName(String adPicFileName) {
		this.adPicFileName = adPicFileName;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}


	public String getTempFile() {
		return tempFile;
	}


	public void setTempFile(String tempFile) {
		this.tempFile = tempFile;
	}
	

}
