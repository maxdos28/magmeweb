package cn.magme.web.action.look;

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
import cn.magme.pojo.look.LooStartPic;
import cn.magme.service.look.LookStartPicService;
import cn.magme.util.FileOperate;
import cn.magme.util.ImageUtil;
import cn.magme.util.StringUtil;
import cn.magme.web.action.BaseAction;

/**
 * LOOK开机图片管理
 * @author jasper
 * @date 2013.10.24
 *
 */
@Results({@Result(name="success",location="/WEB-INF/pages/looker/admin/startPicManager.ftl"),
	@Result(name = "fileUploadJson", type = "json", params = { "root",
		"jsonResult", "contentType", "text/html" })})
public class LookStartPicAction extends BaseAction {
	@Resource
	private LookStartPicService lookStartPicService;
	private List<LooStartPic> startPicList;
	private static final Logger log=Logger.getLogger(LookStartPicAction.class);
	
	private String startDate;
	private String endDate;
	private String picTitle;
	private Integer currentPage = 1;
	
	private String picLink;
	private File startLargePic;
	private String startLargePicFileName;
	private File startSmallPic;
	private String startSmallPicFileName;
	private Integer startPicId;
	private Long appId = 903L;//默认APP
	
	
	public String execute()
	{
		return SUCCESS;
	}
	
	//查询开机图片
	public String searchStartPicJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(currentPage==null||currentPage<=0)
			currentPage = 1;
		Page p = lookStartPicService.searchLookStartPic(startDate, endDate, picTitle, currentPage);
		this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
		this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		List<LooStartPic> rl = p.getResults();
		this.jsonResult.put("commondatas", rl);
		this.jsonResult.put("pageNo", p.getTotalPage());
		return JSON;
	}
	//保存开机图片
	public String saveStartPicJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(appId==null||appId<=0)
		{
			log.warn("APP ID为空");
			this.jsonResult.setMessage("APP ID为空");
			return "fileUploadJson";
		}
			
		if(StringUtil.isBlank(this.picTitle))
		{
			log.warn("名称必须填写");
			this.jsonResult.setMessage("名称必须填写");
			return "fileUploadJson";
		}
		if(startPicId==null||startPicId<=0)
		{
			if(this.startLargePic==null||this.startSmallPic==null)
			{
				log.warn("必须上传图片");
				this.jsonResult.setMessage("必须上传图片");
				return "fileUploadJson";
			}
		}
		if(startLargePic!=null&&startLargePicFileName!=null&&!startLargePicFileName.toLowerCase().endsWith(".jpg"))
		{
			log.warn("文件类型必须是jpg");
			this.jsonResult.setMessage("文件类型必须是jpg");
			return "fileUploadJson";
		}
		if(startSmallPic!=null&&startSmallPicFileName!=null&&!startSmallPicFileName.toLowerCase().endsWith(".jpg"))
		{
			log.warn("文件类型必须是jpg");
			this.jsonResult.setMessage("文件类型必须是jpg");
			return "fileUploadJson";
		}
		if((startLargePic==null&&startSmallPic!=null)||(startLargePic!=null&&startSmallPic==null))
		{
			log.warn("必须同时上传PAD和PHONE图片");
			this.jsonResult.setMessage("必须同时上传PAD和PHONE图片");
			return "fileUploadJson";
		}
		if(this.startLargePic!=null&&this.startSmallPic!=null)
		{
			try {
				BufferedImage buff1 = ImageIO.read(startLargePic);
				if (buff1.getHeight() < 2048 || buff1.getWidth() < 1536) {
					log.warn("PAD图片尺寸为1536*2048");
					this.jsonResult.setMessage("PAD图片尺寸为1536*2048");
					return "fileUploadJson";
				}
				BufferedImage buff2 = ImageIO.read(startSmallPic);
				if (buff2.getHeight() < 1136 || buff2.getWidth() < 640) {
					log.warn("PHONE图片尺寸为640*1136");
					this.jsonResult.setMessage("PHONE图片尺寸为640*1136");
					return "fileUploadJson";
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		LooStartPic sp = new LooStartPic();
		if(startPicId!=null&&startPicId>0)
			sp.setId(startPicId);
		else
			sp.setSortOrder(1);
		sp.setTitle(picTitle);
		sp.setPicLink(picLink);
		String staticPath=systemProp.getStaticLocalUrl();
		if(this.startLargePic!=null&&this.startSmallPic!=null)
		{
			
			String relativePath = File.separator +"look"+File.separator+this.appId+File.separator+"start"+File.separator;
			File f=new File(staticPath+relativePath);
			if(!f.exists()){
				f.mkdirs();
			}
			String fileName=relativePath+"startpic_"+System.currentTimeMillis();
			FileOperate.copyFile(startLargePic.getAbsolutePath(), staticPath+fileName+".jpg");
			FileOperate.copyFile(startLargePic.getAbsolutePath(), staticPath+fileName+"_pad.jpg");
			FileOperate.copyFile(startSmallPic.getAbsolutePath(), staticPath+fileName+"_phone.jpg");
			sp.setPicPath(fileName.replaceAll("\\\\", "/")+".jpg");
		}
		//目前只有正常启动的类型
		sp.setType(new Byte("1"));
		int r = lookStartPicService.saveLookStartPic(sp,staticPath);
		if(r<=0)
		{
			this.jsonResult.setMessage("保存失败");
		}
		else
		{
			this.jsonResult.setCode(JsonResult.CODE.SUCCESS);
			this.jsonResult.setMessage(JsonResult.MESSAGE.SUCCESS);
		}
		return "fileUploadJson";
	}
	//删除开机图片
	public String deleteStartPicJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(startPicId==null||startPicId<=0)
		{
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		int r = this.lookStartPicService.deleteLookStartPic(startPicId);
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
	//上架和下架
	public String changeStatusStartPicJson()
	{
		this.jsonResult=JsonResult.getFailure();
		if(startPicId==null||startPicId<=0)
		{
			log.warn("ID为空");
			this.jsonResult.setMessage("ID为空");
			return JSON;
		}
		int r = this.lookStartPicService.changeLookStartPicStatus(startPicId);
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
	
	
	public List<LooStartPic> getStartPicList() {
		return startPicList;
	}
	public void setStartPicList(List<LooStartPic> startPicList) {
		this.startPicList = startPicList;
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
	public String getPicTitle() {
		return picTitle;
	}

	public void setPicTitle(String picTitle) {
		this.picTitle = picTitle;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public String getPicLink() {
		return picLink;
	}

	public void setPicLink(String picLink) {
		this.picLink = picLink;
	}

	public Integer getStartPicId() {
		return startPicId;
	}

	public void setStartPicId(Integer startPicId) {
		this.startPicId = startPicId;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public File getStartLargePic() {
		return startLargePic;
	}

	public void setStartLargePic(File startLargePic) {
		this.startLargePic = startLargePic;
	}

	public String getStartLargePicFileName() {
		return startLargePicFileName;
	}

	public void setStartLargePicFileName(String startLargePicFileName) {
		this.startLargePicFileName = startLargePicFileName;
	}

	public File getStartSmallPic() {
		return startSmallPic;
	}

	public void setStartSmallPic(File startSmallPic) {
		this.startSmallPic = startSmallPic;
	}

	public String getStartSmallPicFileName() {
		return startSmallPicFileName;
	}

	public void setStartSmallPicFileName(String startSmallPicFileName) {
		this.startSmallPicFileName = startSmallPicFileName;
	}
}
