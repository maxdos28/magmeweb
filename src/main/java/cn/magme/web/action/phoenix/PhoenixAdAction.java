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
import cn.magme.pojo.phoenix.PhoenixAd;
import cn.magme.pojo.phoenix.PhoenixUser;
import cn.magme.service.phoenix.PhoenixAdService;
import cn.magme.util.FileOperate;
import cn.magme.util.ObjDataCopy;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

import com.opensymphony.xwork2.ActionContext;

/**
 * 凤凰广告
 * @author jasper
 * @since 2013-12-25
 */
@Results({@Result(name="success",location="/WEB-INF/pages/phoenix/adManager.ftl")
,@Result(name="fileUploadJson",type="json",params={"root","jsonResult","contentType","text/html;"})
})
public class PhoenixAdAction extends BaseAction{
	@Resource
	private PhoenixAdService phoenixAdService; 
	private static final Logger log=Logger.getLogger(PhoenixAdAction.class);
	
	private String title;
	private Integer currentPage = 1;
	
	private Long id;
	private Integer sortOrder;
	private String link;
	private File adPicPad;
	private String adPicPadFileName;
	private File adPicPhone;
	private String adPicPhoneFileName;
	private Long appId;
	private String tempFile1;//临时图片的地址
	private String tempFile2;//临时图片的地址
	
	
	
	public String execute()
	{
		return SUCCESS;
	}

	//得到session的appId
	private void getSessionAppId() {
		PhoenixUser phoenixUser = (PhoenixUser)ActionContext.getContext().getSession().get(WebConstant.SESSION.PHOENIX_USER);
		appId = phoenixUser.getAppId();
	}
	
	//查询广告
	public String searchPhoenixAdJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(currentPage==null||currentPage<=0)
			currentPage = 1;
		getSessionAppId();
		Page p = phoenixAdService.searchPhoenixAd(appId, title, currentPage);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		List<LooCategory> rl = p.getResults();
		this.jsonResult.put("commondatas", rl);
		this.jsonResult.put("pageNo", p.getTotalPage());
		return JSON;
	}
	//保存广告
	public String savePhoenixAdJson()
	{
		if(StringUtil.isNotBlank(this.tempFile1))
		{
			adPicPadFileName = this.tempFile1.substring(this.tempFile1.lastIndexOf("/")+1);
			this.adPicPad = new File(this.tempFile1);
		}
		if(StringUtil.isNotBlank(this.tempFile2))
		{
			adPicPhoneFileName = this.tempFile2.substring(this.tempFile2.lastIndexOf("/")+1);
			this.adPicPhone = new File(this.tempFile2);
		}
		checkPic();
		if(this.jsonResult.getCode()!=200)
		{
			return "fileUploadJson";
		}
		getSessionAppId();
		PhoenixAd ad = new PhoenixAd();
		ObjDataCopy.copy(this, ad);
		ad.setAppId(appId);
		if (id != null && id > 0)
			ad.setId(id);
		else {
			int r = this.phoenixAdService.savePhoenixAd(ad);
			id = new Long(r);
			ad.setId(id);
		}
		//文章使用和互动杂志相同路径
		String staticPath = systemProp.getStaticLocalUrl() ;
		String relativePath =  File.separator
				+ "phoenix" + File.separator + "ad" + File.separator
				+ appId+ File.separator;
		File dir = new File(staticPath + relativePath);
		if (!dir.exists())
			dir.mkdirs();
		String fileName = relativePath + "ad_" + id + "-"
				+ System.nanoTime();
		// 上传图片
		if (this.adPicPad != null) {
			// 文章图片ID，文章ID+－+时间
			FileOperate.copyFile(adPicPad.getAbsolutePath(), staticPath+fileName+".jpg");
			FileOperate.copyFile(adPicPad.getAbsolutePath(), staticPath+fileName+"_pad.jpg");
			FileOperate.copyFile(adPicPhone.getAbsolutePath(), staticPath+fileName+"_phone.jpg");
			ad.setPicPath(fileName.replaceAll("\\\\", "/") + ".jpg");
		}
		int r = phoenixAdService.savePhoenixAd(ad);
		if(r<=0)
		{
			this.jsonResult.setMessage("保存失败");
		}
		else
		{
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		if(adPicPad!=null)
			this.adPicPad.delete();
		if(adPicPhone!=null)
			this.adPicPhone.delete();
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
		if (this.id == null || this.id <= 0) {
			if (this.adPicPad == null||this.adPicPhone == null) {
				log.warn("必须上传图片");
				this.jsonResult.setMessage("必须上传图片");
				return "fileUploadJson";
			}
		}
		if((this.adPicPad == null&&this.adPicPhone != null)||(this.adPicPad != null&&this.adPicPhone == null))
		{
			log.warn("两张图片必须同时上传");
			this.jsonResult.setMessage("两张图片必须同时上传");
			return "fileUploadJson";
		}
		if (adPicPad != null && adPicPadFileName != null
				&& !adPicPadFileName.toLowerCase().endsWith(".jpg")) {
			log.warn("文件类型必须是jpg");
			this.jsonResult.setMessage("文件类型必须是jpg");
			return "fileUploadJson";
		}
		if (adPicPhone != null && adPicPhoneFileName != null
				&& !adPicPhoneFileName.toLowerCase().endsWith(".jpg")) {
			log.warn("文件类型必须是jpg");
			this.jsonResult.setMessage("文件类型必须是jpg");
			return "fileUploadJson";
		}

		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		this.jsonResult.put("checkPic", 1);
		//768 x 645
		if (adPicPad != null) {
			// 图片尺寸检查
			// FileInputStream fis = new FileInputStream(smallPic);
			try {
				BufferedImage buff = ImageIO.read(adPicPad);
				String msg = "您上传的PAD图片";
				if (buff.getHeight() != 645 || buff.getWidth() != 768) {
					this.jsonResult.put("checkPic", 0);
					log.warn("图片尺寸不符合");
					msg+="尺寸为"+buff.getWidth()+"*"+buff.getHeight()+",不符合标准尺寸(768*645),";
					//return "fileUploadJson";
				}
				if(adPicPad.length()>(100*1024))
				{
					log.warn("图片大小不符合");
					msg+="大小超过100K,";
					this.jsonResult.put("checkPic", 0);
					//return "fileUploadJson";
				}
				msg+="是否确认?";
				this.jsonResult.put("msg1", msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//720 x 432
		if (adPicPhone != null) {
			// 图片尺寸检查
			// FileInputStream fis = new FileInputStream(smallPic);
			try {
				BufferedImage buff = ImageIO.read(adPicPhone);
				String msg = "您上传的PHONE图片";
				if (buff.getHeight() != 432 || buff.getWidth() != 720) {
					this.jsonResult.put("checkPic", 0);
					log.warn("图片尺寸不符合");
					msg+="尺寸为"+buff.getWidth()+"*"+buff.getHeight()+",不符合标准尺寸(720*432),";
					//return "fileUploadJson";
				}
				if(adPicPhone.length()>(50*1024))
				{
					log.warn("图片大小不符合");
					msg+="大小超过100K,";
					this.jsonResult.put("checkPic", 0);
					//return "fileUploadJson";
				}
				msg+="是否确认?";
				this.jsonResult.put("msg2", msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String staticPath = systemProp.getStaticLocalUrl()+"/temp/";
		File dir = new File(staticPath);
		if(!dir.exists())
			dir.mkdirs();
		String tempFileName1 = staticPath+"temp_1"+System.nanoTime()+".jpg";
		FileOperate.copyFile(adPicPad.getAbsolutePath(), tempFileName1);
		this.jsonResult.put("tempFile1", tempFileName1);
		String tempFileName2 = staticPath+"temp_2"+System.nanoTime()+".jpg";
		FileOperate.copyFile(adPicPhone.getAbsolutePath(), tempFileName2);
		this.jsonResult.put("tempFile2", tempFileName2);
		return "fileUploadJson";
	}
	//获得广告信息
	public String phoenixAdInfoJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(id==null||id<=0)
		{
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		PhoenixAd ad = this.phoenixAdService.getPhoenixAdInfo(id);
		if(ad==null)
		{
			this.jsonResult.setMessage("获得广告信息失败");
		}
		else
		{
			this.jsonResult.put("phoenixAdInfo", ad);
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return JSON;
	}
	//删除广告
	public String deletePhoenixAdJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(id==null||id<=0)
		{
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		int r = this.phoenixAdService.deletePhoenixAd(id);
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public File getAdPicPad() {
		return adPicPad;
	}

	public void setAdPicPad(File adPicPad) {
		this.adPicPad = adPicPad;
	}

	public String getAdPicPadFileName() {
		return adPicPadFileName;
	}

	public void setAdPicPadFileName(String adPicPadFileName) {
		this.adPicPadFileName = adPicPadFileName;
	}

	public File getAdPicPhone() {
		return adPicPhone;
	}

	public void setAdPicPhone(File adPicPhone) {
		this.adPicPhone = adPicPhone;
	}

	public String getAdPicPhoneFileName() {
		return adPicPhoneFileName;
	}

	public void setAdPicPhoneFileName(String adPicPhoneFileName) {
		this.adPicPhoneFileName = adPicPhoneFileName;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public String getTempFile1() {
		return tempFile1;
	}

	public void setTempFile1(String tempFile1) {
		this.tempFile1 = tempFile1;
	}

	public String getTempFile2() {
		return tempFile2;
	}

	public void setTempFile2(String tempFile2) {
		this.tempFile2 = tempFile2;
	}	

}
